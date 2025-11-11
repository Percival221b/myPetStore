package org.example.petstore.persistence.impl;

import org.example.petstore.domain.LineItem;
import org.example.petstore.persistence.LineItemDao;

import java.util.List;

public class LineItemDaoImpl implements LineItemDao {
    @Override
    public List<LineItem> getLineItemsByOrderId(int orderId) {
        return List.of();
    }

    @Override
    public void insertLineItem(LineItem lineItem) {

    }
}
