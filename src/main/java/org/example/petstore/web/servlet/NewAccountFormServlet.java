package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NewAccountFormServlet extends HttpServlet {
    public static final String NEW_ACCOUNT_FORM = "/WEB-INF/jsp/account/newAccountForm.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(NEW_ACCOUNT_FORM).forward(req, resp);
    }
}
