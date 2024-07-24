package com.bigbank.game.quest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bigbank.game.client.GameClient;
import com.bigbank.game.dto.GameDto;
import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.shop.ShopClient;
import com.bigbank.game.stats.StatsClient;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class QuestClientImplTest {

  @Mock
  private GameClient gameClient;

  @Mock
  private StatsClient statsClient;

  @Mock
  private QuestAnalyzer analyzer;

  @Mock
  private ShopClient shopClient;

  @InjectMocks
  private QuestClientImpl questClient;

  private GameDto gameDto;

  private QuestDto questDto;

  private ResultDto resultDto;

  private AtomicInteger highScore;

  private AtomicInteger lives;

  @BeforeEach
  public void setUp() {
    gameDto = new GameDto("qwerty", 10, 3);
    questDto = new QuestDto(gameDto.getGameId(), "Help", "50");
    resultDto = new ResultDto(2, 5, 5, 10, "Result");
    highScore = new AtomicInteger(gameDto.getHighScore());
    lives = new AtomicInteger(gameDto.getLives());
  }

  @Test
  public void testSolveQuestWithRetry_andSucceed() {
    when(gameClient.solveQuest(anyString(), anyString())).thenReturn(Mono.just(resultDto));

    Mono<ResultDto> result = questClient.solveQuestWithRetry(gameDto.getGameId(), questDto, highScore, lives, gameClient, statsClient, analyzer, shopClient);

    StepVerifier
        .create(result)
        .expectNextMatches(res -> res.getScore() == 5 && res.getLives() == 2)
        .verifyComplete();

    verify(statsClient, times(1)).updateStats(any(), any(), any(), any());
    verify(analyzer, times(1)).analyzeResult(any(), any(), any(), any());
  }

  @Test
  public void testSolveQuestWithRetry_retryAndFail() {
    when(gameClient.solveQuest(anyString(), anyString())).thenReturn(Mono.error(new RuntimeException("Test exception")));

    Mono<ResultDto> result = questClient.solveQuestWithRetry(gameDto.getGameId(), questDto, highScore, lives, gameClient, statsClient, analyzer, shopClient);

    StepVerifier
        .create(result)
        .verifyComplete();

    verify(statsClient, never()).updateStats(any(), any(), any(), any());
    verify(analyzer, never()).analyzeResult(any(), any(), any(), any());
  }
}
