package com.bigbank.game.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Success {
  SUCCESS("You successfully solved the mission!");

  private final String name;
}
