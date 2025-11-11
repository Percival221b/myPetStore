package org.example.petstore.persistence;

import org.example.petstore.domain.Order;

import java.util.List;

public interface OrderDao {
    public List<Order> getOrdersByUsername(String username);

    public Order getOrder(int orderId);

    public void insertOrder(Order order);

    public void insertOrderStatus(Order order);
}
