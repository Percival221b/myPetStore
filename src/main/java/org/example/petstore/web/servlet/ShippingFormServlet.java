package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ShippingFormServlet extends HttpServlet {
    private static final String SHIPPING = "/WEB-INF/jsp/order/shippingForm.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SHIPPING).forward(req, resp);
    }
}
