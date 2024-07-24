package com.bigbank.game.stats;

import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import java.util.concurrent.atomic.AtomicInteger;

public interface StatsClient {

  void updateStats(
      AtomicInteger highScore,
      AtomicInteger lives,
      QuestDto quest,
      ResultDto result
  );
}
