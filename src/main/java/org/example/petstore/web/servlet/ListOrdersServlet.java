package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Order;
import org.example.petstore.persistence.OrderDao;
import org.example.petstore.persistence.impl.OrderDaoImpl;
import org.example.petstore.service.OrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListOrdersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 获取当前登录的用户
        Account account = (Account) session.getAttribute("loginAccount");

        // 检查用户是否登录
        if (account == null) {
            resp.sendRedirect("login.jsp");  // 重定向到登录页面
            return;
        }

        // 获取用户的所有订单
        OrderService orderService = new OrderService();
        List<Order> orders = orderService.getOrdersByUsername(account.getUsername());

        // 将订单列表传递到前端
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/jsp/order/listOrders.jsp").forward(req, resp);  // 转发到 JSP 页面
    }
}

