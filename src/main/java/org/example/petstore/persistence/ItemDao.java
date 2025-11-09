package org.example.petstore.persistence;

import org.example.petstore.domain.Item;

import java.util.List;
import java.util.Map;

public interface ItemDao {
    void updateInventoryQuantity(Map<String, Object> param);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);
}
