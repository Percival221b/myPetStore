package org.example.petstore.service;

import org.example.petstore.domain.LineItem;
import org.example.petstore.domain.Order;
import org.example.petstore.persistence.LineItemDao;
import org.example.petstore.persistence.OrderDao;
import org.example.petstore.persistence.impl.LineItemDaoImpl;
import org.example.petstore.persistence.impl.OrderDaoImpl;

import java.util.List;

public class OrderService {
    private OrderDao orderDao;
    private LineItemDao lineItemDao;
    public OrderService() {
        orderDao = new OrderDaoImpl();
        lineItemDao = new LineItemDaoImpl();
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

    public void insertLineItem(LineItem lineItem){lineItemDao.insertLineItem(lineItem);}

    public List<LineItem> getLineItemsByOrderId(int orderId){return lineItemDao.getLineItemsByOrderId(orderId);}

}
