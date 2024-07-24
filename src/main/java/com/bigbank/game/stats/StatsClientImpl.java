package com.bigbank.game.stats;

import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatsClientImpl implements StatsClient {

  @Override
  public void updateStats(
      AtomicInteger highScore,
      AtomicInteger lives,
      QuestDto quest,
      ResultDto result
  ) {
    if (result.getLives() > lives.get()) {
      result.setLives(lives.get());
    }
    highScore.addAndGet(result.getScore());
    result.setHighScore(highScore.get());
    log.info("{} {}, lives: {}, gold: {}, score: {}, high score: {}",
        result.getMessage(),
        quest.getMessage(),
        result.getLives(),
        result.getGold(),
        result.getScore(),
        result.getHighScore());
  }
}
