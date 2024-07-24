package com.bigbank.game.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bigbank.game.dto.QuestDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuestFilterImplTest {

  private QuestFilterImpl questFilter;

  @BeforeEach
  public void setUp() {
    questFilter = new QuestFilterImpl();
  }

  @Test
  public void shouldFilterFromMixedRewardQuests() {
    List<QuestDto> allQuests = Arrays.asList(new QuestDto("abc", "trace", "10"),
        new QuestDto("def", "steal", "50"),
        new QuestDto("ghi", "help", "100"),
        new QuestDto("jkl", "find", "75"));

    List<QuestDto> expectedSolvableQuests = Arrays.asList(new QuestDto("ghi", "help", "100"), new QuestDto("jkl", "find", "75"));

    List<QuestDto> solvableQuests = questFilter.filterSolvableQuests(allQuests);

    assertEquals(expectedSolvableQuests, solvableQuests);
  }

  @Test
  public void shouldFilterFromLowRewardQuests() {
    List<QuestDto> allQuests = Arrays.asList(new QuestDto("abc", "trace", "10"), new QuestDto("def", "steal", "20"), new QuestDto("ghi", "help", "30"));

    List<QuestDto> expectedSolvableQuests = Arrays.asList(new QuestDto("def", "steal", "20"), new QuestDto("ghi", "help", "30"));

    List<QuestDto> solvableQuests = questFilter.filterSolvableQuests(allQuests);

    assertEquals(expectedSolvableQuests, solvableQuests);
  }

  @Test
  public void shouldFilterFromHighRewardQuests() {
    List<QuestDto> allQuests = Arrays.asList(new QuestDto("abc", "trace", "80"), new QuestDto("def", "steal", "90"), new QuestDto("ghi", "help", "100"));

    List<QuestDto> expectedSolvableQuests = Arrays.asList(new QuestDto("abc", "trace", "80"),
        new QuestDto("def", "steal", "90"),
        new QuestDto("ghi", "help", "100"));

    List<QuestDto> solvableQuests = questFilter.filterSolvableQuests(allQuests);

    assertEquals(expectedSolvableQuests, solvableQuests);
  }
}
