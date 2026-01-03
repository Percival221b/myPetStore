package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.domain.Order;
import org.example.petstore.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;

public class GetPaymentDetailsServlet extends HttpServlet {
    OrderService orderService =  new OrderService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr != null) {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderService.getOrder(orderId);
            // 返回订单支付信息的HTML内容
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<p>Card Type: " + order.getCardType() + "</p>");
            out.println("<p>Card Number: " + order.getCreditCard() + " * Fake number!</p>");
            out.println("<p>Expiry Date: " + order.getExpiryDate() + "</p>");
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Order ID is missing.");
        }
    }
}
