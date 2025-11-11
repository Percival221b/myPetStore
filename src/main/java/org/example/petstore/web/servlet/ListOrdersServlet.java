package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ListOrdersServlet extends HttpServlet {
    private static final String LIST_ORDERS = "/WEB-INF/jsp/order/listOrders.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LIST_ORDERS).forward(req, resp);
    }
}
