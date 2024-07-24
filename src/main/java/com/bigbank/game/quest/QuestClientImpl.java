package com.bigbank.game.quest;

import com.bigbank.game.client.GameClient;
import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.shop.ShopClient;
import com.bigbank.game.stats.StatsClient;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Component
public class QuestClientImpl implements QuestClient {

  @Override
  public Mono<ResultDto> solveQuest(
      GameClient gameClient,
      String gameId,
      String adId
  ) {
    return gameClient
        .solveQuest(gameId, adId)
        .delayElement(Duration.ofSeconds(1));
  }

  @Override
  public Mono<ResultDto> solveQuestWithRetry(
      String gameId,
      QuestDto quest,
      AtomicInteger highScore,
      AtomicInteger lives,
      GameClient gameClient,
      StatsClient statsClient,
      QuestAnalyzer analyzer,
      ShopClient shopClient
  ) {
    log.info("Trying to solve the quest: {}", quest.getMessage());
    return solveQuest(gameClient, gameId, quest.getAdId())
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
        .doOnNext(result -> {
          analyzer.analyzeQuest(quest, result, lives);
          statsClient.updateStats(highScore, lives, quest, result);
          analyzer.analyzeResult(gameId, result, lives, shopClient);
        })
        .onErrorResume(e -> {
          log.error("WebClient error: {}", e.getMessage());
          return Mono.empty();
        });
  }
}
