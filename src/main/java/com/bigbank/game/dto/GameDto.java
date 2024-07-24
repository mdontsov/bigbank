package com.bigbank.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class GameDto {

  private final String gameId;

  private int highScore;

  private int lives;
}
