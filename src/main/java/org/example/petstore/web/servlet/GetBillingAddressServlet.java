package org.example.petstore.web.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.domain.Order;
import org.example.petstore.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;
public class GetBillingAddressServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrder(orderId);  // 假设有一个获取订单的方法

        // 返回账单地址的HTML内容
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>First name: " + order.getBillToFirstName() + "</p>");
        out.println("<p>Last name: " + order.getBillToLastName() + "</p>");
        out.println("<p>Address 1: " + order.getBillAddress1() + "</p>");
        out.println("<p>Address 2: " + order.getBillAddress2() + "</p>");
        out.println("<p>City: " + order.getBillCity() + "</p>");
        out.println("<p>State: " + order.getBillState() + "</p>");
        out.println("<p>Zip: " + order.getBillZip() + "</p>");
        out.println("<p>Country: " + order.getBillCountry() + "</p>");
    }
}
