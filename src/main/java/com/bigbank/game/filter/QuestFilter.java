package com.bigbank.game.filter;

import com.bigbank.game.dto.QuestDto;
import java.util.List;

public interface QuestFilter {

  List<QuestDto> filterSolvableQuests(List<QuestDto> allQuests);

  List<QuestDto> filterBestQuests(List<QuestDto> allQuests);
}
