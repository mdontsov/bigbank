package com.bigbank.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ShopDto {

  private String id;

  private String name;

  private int cost;
}
