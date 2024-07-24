package com.bigbank.game.processor;

import com.bigbank.game.client.GameClient;
import com.bigbank.game.dto.GameDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.filter.QuestFilter;
import com.bigbank.game.quest.QuestAnalyzer;
import com.bigbank.game.quest.QuestClient;
import com.bigbank.game.shop.ShopClient;
import com.bigbank.game.stats.StatsClient;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameProcessor {

  private final GameClient gameClient;

  private final QuestFilter questFilter;

  private final ShopClient shopClient;

  private final StatsClient statsClient;

  private final QuestClient questClient;

  private final QuestAnalyzer questAnalyzer;

  public void processGame() {
    gameClient
        .getInitialGameData()
        .doOnNext(gameDto -> log.info("Received initial data: {}", gameDto))
        .flatMapMany(this::processGameLoop)
        .blockLast();
  }

  Mono<List<ResultDto>> processGameLoop(GameDto game) {
    AtomicInteger highScore = new AtomicInteger(game.getHighScore());
    AtomicInteger lives = new AtomicInteger(game.getLives());

    return Mono.defer(() -> gameClient
        .getMessagesBy(game.getGameId())
        .flatMapMany(quests -> Flux.fromIterable(questFilter.filterSolvableQuests(quests)))
        .concatMap(quest -> questClient.solveQuestWithRetry(game.getGameId(), quest, highScore, lives, gameClient, statsClient, questAnalyzer, shopClient))
        .doOnComplete(() -> log.info("Round ended. High score: {}, lives: {}", highScore.get(), lives.get()))
        .repeat(() -> lives.get() > 0)
        .takeUntil(result -> lives.get() <= 0)
        .delayElements(Duration.ofSeconds(1))
        .collectList()
        .doAfterTerminate(() -> log.info("Game ended. High Score: {}, lives: {}", highScore.get(), lives.get())));
  }
}
