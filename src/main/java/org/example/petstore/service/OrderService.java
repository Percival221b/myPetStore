package org.example.petstore.service;

import org.example.petstore.domain.Order;
import org.example.petstore.persistence.OrderDao;
import org.example.petstore.persistence.impl.OrderDaoImpl;

import java.util.List;

public class OrderService {
    private OrderDao orderDao;
    public OrderService() {
        orderDao = new OrderDaoImpl();
    }
    public List<Order> getOrdersByUsername(String username){
        return orderDao.getOrdersByUsername(username);
    }

    public Order getOrder(int orderId){
        return orderDao.getOrder(orderId);
    }

    public void insertOrder(Order order){
        orderDao.insertOrder(order);
    }

    public void insertOrderStatus(Order order){
        orderDao.insertOrderStatus(order);
    }
}
