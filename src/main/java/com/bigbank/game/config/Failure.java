package com.bigbank.game.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Failure {
  FAIL("You failed on the mission!"),
  DEFEAT("You were defeated on your last mission!"),
  TRAP("You fell into a trap");

  private final String name;
}
