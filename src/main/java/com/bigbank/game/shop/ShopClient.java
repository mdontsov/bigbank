package com.bigbank.game.shop;

import com.bigbank.game.dto.ShopDto;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface ShopClient {

  List<ShopDto> getItemsAvailableBy(String gameId);

  void shopItem(
      String gameId,
      String itemId
  );

  void visitShop(
      String gameId,
      AtomicInteger lives
  );
}
