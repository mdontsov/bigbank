package com.bigbank.game.client;

import com.bigbank.game.dto.GameDto;
import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GameClientImpl implements GameClient {

  @Qualifier("gameWebClient")
  private final WebClient webClient;

  @Override
  public Mono<GameDto> getInitialGameData() {
    return webClient
        .post()
        .uri(builder -> builder
            .path("/api/v2/game/start")
            .build())
        .retrieve()
        .bodyToMono(GameDto.class);
  }

  @Override
  public Mono<List<QuestDto>> getMessagesBy(String gameId) {
    return webClient
        .get()
        .uri(builder -> builder
            .path(String.format("/api/v2/%s/messages", gameId))
            .build(gameId))
        .retrieve()
        .bodyToFlux(QuestDto.class)
        .collectList();
  }

  @Override
  public Mono<ResultDto> solveQuest(
      String gameId,
      String adId
  ) {
    return webClient
        .post()
        .uri(builder -> builder
            .path(String.format("/api/v2/%s/solve/%s", gameId, adId))
            .build())
        .retrieve()
        .bodyToMono(ResultDto.class);
  }
}
