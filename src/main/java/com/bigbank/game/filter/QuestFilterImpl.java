package com.bigbank.game.filter;

import com.bigbank.game.dto.QuestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuestFilterImpl implements QuestFilter {

  @Override
  public List<QuestDto> filterSolvableQuests(List<QuestDto> allQuests) {
    List<QuestDto> solvableQuests = filterBestQuests(allQuests);
    log.info("Getting the best possible quests to solve: {}", solvableQuests);
    return solvableQuests;
  }

  @Override
  public List<QuestDto> filterBestQuests(List<QuestDto> allQuests) {
    int bestReward = allQuests
        .stream()
        .mapToInt(quest -> Integer.parseInt(quest.getReward()))
        .max()
        .orElse(0);

    double threshold = bestReward * 0.55;
    return allQuests
        .stream()
        .filter(quest -> Integer.parseInt(quest.getReward()) >= threshold)
        .collect(Collectors.toList());
  }
}
