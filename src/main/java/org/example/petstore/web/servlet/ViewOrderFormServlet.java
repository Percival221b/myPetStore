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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 从 session 中获取登录账户信息
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            request.setAttribute("error", "Missing order ID.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderService.getOrder(orderId);

            if (order == null) {
                request.setAttribute("error", "Order not found.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // 判断是否是自己的订单
            if (account.getUsername().equals(order.getUsername())) {
                request.setAttribute("order", order);
                request.getRequestDispatcher("viewOrder.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "You may only view your own orders.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid order ID.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error viewing order", e);
        }
    }
}



