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
    private static final String MAIN_FORM = "/WEB-INF/jsp/catalog/main.jsp";
    private static final String VIEW_FORM = "/WEB-INF/jsp/order/viewOrder.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW_FORM).forward(request, response);
    }
}



