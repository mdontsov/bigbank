package com.bigbank.game.shop;

import com.bigbank.game.dto.PurchaseDto;
import com.bigbank.game.dto.ShopDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShopClientImpl implements ShopClient {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Qualifier("gameWebClient")
  private final WebClient webClient;

  @Override
  public List<ShopDto> getItemsAvailableBy(String gameId) {
    Mono<Object[]> items = webClient
        .get()
        .uri(builder -> builder
            .path(String.format("/api/v2/%s/shop", gameId))
            .build(gameId))
        .retrieve()
        .bodyToMono(Object[].class)
        .share();

    Object[] objects = items.block();
    return Arrays
        .stream(Objects.requireNonNull(objects))
        .map(object -> mapper.convertValue(object, ShopDto.class))
        .toList();
  }

  @Override
  public void shopItem(
      String gameId,
      String itemId
  ) {
    webClient
        .post()
        .uri(builder -> builder
            .path(String.format("/api/v2/%s/shop/buy/%s", gameId, itemId))
            .build())
        .retrieve()
        .bodyToMono(PurchaseDto.class)
        .share()
        .block();
  }

  @Override
  public void visitShop(
      String gameId,
      AtomicInteger lives
  ) {
    List<ShopDto> shop = getItemsAvailableBy(gameId);
    log.info("Visiting the shop");
    ShopDto item = shop
        .stream()
        .filter(it -> it
            .getName()
            .equals("Healing potion"))
        .findAny()
        .get();
    shopItem(gameId, item.getId());
    lives.incrementAndGet();
    log.info("Purchased item: {}, price: {}, lives: {}", item.getName(), item.getCost(), lives.get());
  }
}
