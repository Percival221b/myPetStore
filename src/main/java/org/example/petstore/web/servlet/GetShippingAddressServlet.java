package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.domain.Order;
import org.example.petstore.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;

public class GetShippingAddressServlet extends HttpServlet {
    OrderService orderService =  new OrderService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrder(orderId);

        // 返回收货地址的HTML内容
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>First name: " + order.getShipToFirstName() + "</p>");
        out.println("<p>Last name: " + order.getShipToLastName() + "</p>");
        out.println("<p>Address 1: " + order.getShipAddress1() + "</p>");
        out.println("<p>Address 2: " + order.getShipAddress2() + "</p>");
        out.println("<p>City: " + order.getShipCity() + "</p>");
        out.println("<p>State: " + order.getShipState() + "</p>");
        out.println("<p>Zip: " + order.getShipZip() + "</p>");
        out.println("<p>Country: " + order.getShipCountry() + "</p>");
    }
}
