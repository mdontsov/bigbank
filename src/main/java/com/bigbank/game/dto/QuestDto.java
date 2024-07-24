package com.bigbank.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class QuestDto {

  private final String adId;

  private final String message;

  private final String reward;
}
