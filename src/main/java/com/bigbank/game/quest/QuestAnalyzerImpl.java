package com.bigbank.game.quest;

import com.bigbank.game.config.Failure;
import com.bigbank.game.config.Success;
import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.shop.ShopClient;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuestAnalyzerImpl implements QuestAnalyzer {

  @Override
  public void analyzeResult(
      String gameId,
      ResultDto result,
      AtomicInteger lives,
      ShopClient shopClient
  ) {
    if (shoppingIsRequired(result)) {
      shopClient.visitShop(gameId, lives);
    }
  }

  @Override
  public void analyzeQuest(
      QuestDto quest,
      ResultDto result,
      AtomicInteger lives
  ) {
    if (isQuestFailed(result)) {
      result.setScore(0);
      result.setGold(0);
      result.setHighScore(0);
      lives.decrementAndGet();
      result.setLives(lives.get());
    }
    if (isQuestSolved(result)) {
      result.setGold(Integer.parseInt(quest.getReward()));
      result.setScore(result.getGold());
      result.setLives(lives.get());
    }
  }

  @Override
  public boolean isQuestFailed(ResultDto result) {
    return Arrays
        .stream(Failure.values())
        .anyMatch(it -> it
            .getName()
            .contains(result.getMessage()));
  }

  @Override
  public boolean isQuestSolved(ResultDto result) {
    return Arrays
        .stream(Success.values())
        .anyMatch(it -> it
            .getName()
            .contains(result.getMessage()));
  }

  @Override
  public boolean shoppingIsRequired(
      ResultDto result
  ) {
    int highScore = result.getHighScore();
    int lives = result.getLives();
    if (highScore < 1000 && lives == 2) {
      log.info("Shopping required - high score: {}, lives: {}", highScore, lives);
    }
    return highScore < 1000 && lives == 2;
  }
}
