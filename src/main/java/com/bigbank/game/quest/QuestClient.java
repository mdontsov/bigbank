package com.bigbank.game.quest;

import com.bigbank.game.client.GameClient;
import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.shop.ShopClient;
import com.bigbank.game.stats.StatsClient;
import java.util.concurrent.atomic.AtomicInteger;
import reactor.core.publisher.Mono;

public interface QuestClient {

  Mono<ResultDto> solveQuest(
      GameClient gameClient,
      String gameId,
      String adId
  );

  Mono<ResultDto> solveQuestWithRetry(
      String gameId,
      QuestDto quest,
      AtomicInteger highScore,
      AtomicInteger lives,
      GameClient gameClient,
      StatsClient statsClient,
      QuestAnalyzer analyzer,
      ShopClient shopClient
  );
}
