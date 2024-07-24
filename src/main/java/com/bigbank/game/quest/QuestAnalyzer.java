package com.bigbank.game.quest;

import com.bigbank.game.dto.QuestDto;
import com.bigbank.game.dto.ResultDto;
import com.bigbank.game.shop.ShopClient;
import java.util.concurrent.atomic.AtomicInteger;

public interface QuestAnalyzer {

  boolean isQuestFailed(ResultDto result);

  boolean isQuestSolved(ResultDto result);

  boolean shoppingIsRequired(
      ResultDto result
  );

  void analyzeQuest(
      QuestDto quest,
      ResultDto result,
      AtomicInteger lives
  );

  void analyzeResult(
      String gameId,
      ResultDto result,
      AtomicInteger lives,
      ShopClient shopClient
  );
}
