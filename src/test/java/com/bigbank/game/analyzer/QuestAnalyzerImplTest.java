package com.bigbank.game.analyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bigbank.game.dto.GameDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.quest.QuestAnalyzerImpl;
import com.bigbank.game.shop.ShopClient;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuestAnalyzerImplTest {

  @Mock
  private ShopClient shopClient;

  @InjectMocks
  private QuestAnalyzerImpl questAnalyzer;

  private GameDto gameDto;

  private ResultDto resultDto;

  private AtomicInteger lives;

  @BeforeEach
  public void setUp() {
    gameDto = new GameDto("abc", 10, 3);
    resultDto = new ResultDto();
    lives = new AtomicInteger(3);
  }

  @Test
  public void shouldVisitShopAfterAnalyze() {
    resultDto.setMessage("You fell into a trap");
    resultDto.setHighScore(500);
    resultDto.setLives(2);
    lives.decrementAndGet();

    questAnalyzer.analyzeResult(gameDto.getGameId(), resultDto, lives, shopClient);

    assertEquals(0, resultDto.getScore());
    verify(shopClient, times(1)).visitShop(gameDto.getGameId(), lives);
  }

  @Test
  public void shouldUpdateLives_ifQuestFailed() {
    resultDto.setMessage("You fell into a trap");
    resultDto.setLives(2);

    questAnalyzer.analyzeResult(gameDto.getGameId(), resultDto, lives, shopClient);

    assertEquals(0, resultDto.getScore());
    assertEquals(3, lives.get());
  }

  @Test
  public void shouldNotDecreaseLives_ifQuestSucceeded() {
    resultDto.setMessage("You successfully solved the mission!");
    resultDto.setLives(3);

    questAnalyzer.analyzeResult(gameDto.getGameId(), resultDto, lives, shopClient);

    assertEquals(3, resultDto.getLives());
  }

  @Test
  public void shouldNotIncreaseLives_ifQuestSucceeded() {
    resultDto.setMessage("You successfully solved the mission!");
    resultDto.setLives(2);

    questAnalyzer.analyzeResult(gameDto.getGameId(), resultDto, lives, shopClient);

    assertEquals(2, resultDto.getLives());
  }

  @Test
  public void shouldConsiderTestAsFailed() {
    resultDto.setMessage("You fell into a trap");

    boolean isFailed = questAnalyzer.isQuestFailed(resultDto);

    assertTrue(isFailed);
  }

  @Test
  public void shouldConsiderQuestAsSolved() {
    resultDto.setMessage("You successfully solved the mission!");

    boolean isSolved = questAnalyzer.isQuestSolved(resultDto);

    assertTrue(isSolved);
  }

  @Test
  public void shouldRequireShopping() {
    resultDto.setHighScore(500);
    resultDto.setLives(2);

    boolean isRequired = questAnalyzer.shoppingIsRequired(resultDto);

    assertTrue(isRequired);
  }

  @Test
  public void shouldNotRequireShopping() {
    resultDto.setHighScore(1000);
    resultDto.setLives(2);

    boolean isRequired = questAnalyzer.shoppingIsRequired(resultDto);

    assertFalse(isRequired);
  }
}
