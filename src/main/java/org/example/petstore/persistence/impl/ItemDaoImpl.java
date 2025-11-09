package org.example.petstore.persistence.impl;

import org.example.petstore.domain.Item;
import org.example.petstore.persistence.ItemDao;

import java.util.List;
import java.util.Map;

public class ItemDaoImpl implements ItemDao {


    @Override
    public void updateInventoryQuantity(Map<String, Object> param) {

    }

    @Override
    public int getInventoryQuantity(String itemId) {
        return 0;
    }

    @Override
    public List<Item> getItemListByProduct(String productId) {
        return List.of();
    }

    @Override
    public Item getItem(String itemId) {
        return null;
    }
}
