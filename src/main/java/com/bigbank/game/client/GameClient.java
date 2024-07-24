package com.bigbank.game.client;

import com.bigbank.game.dto.GameDto;
import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import java.util.List;
import reactor.core.publisher.Mono;

public interface GameClient {

  Mono<GameDto> getInitialGameData();

  Mono<List<QuestDto>> getMessagesBy(String gameId);

  Mono<ResultDto> solveQuest(
      String gameId,
      String adId
  );
}
