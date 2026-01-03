package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Order;
import org.example.petstore.service.OrderService;

import java.io.IOException;

public class ViewOrderFormServlet extends HttpServlet {

    OrderService orderService = new OrderService();

    private static final String VIEW_FORM = "/WEB-INF/jsp/order/viewOrder.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取订单ID
        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            // 获取订单对象并设置到请求中
            Order order = orderService.getOrder(orderId);
            if (order == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
                return;
            }
            request.setAttribute("order", order);
            request.getSession().setAttribute("order", order);
            request.getSession().setAttribute("orderId", orderId);
            request.getRequestDispatcher(VIEW_FORM).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID.");
        }
    }
}



