package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;

import java.io.IOException;

public class NewOrderFormServlet extends HttpServlet {

    private static final String New_OrderForm = "/WEB-INF/jsp/order/newOrder.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        if (loginAccount == null) {
            resp.sendRedirect("signOnForm");
        } else {
            req.getRequestDispatcher(New_OrderForm).forward(req, resp);
        }
    }
}