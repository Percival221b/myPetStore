package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Category;
import org.example.petstore.domain.Product;
import org.example.petstore.service.CatalogService;

import java.io.IOException;
import java.util.List;

public class CategoryFormServlet extends HttpServlet {

    private CatalogService catalogService;

    private static final String MAIN_FORM = "/WEB-INF/jsp/catalog/main.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("categoryId");
        catalogService = new CatalogService();
        Category category = catalogService.getCategory(categoryId);
        List<Product> productList= catalogService.getProductListByCategory(categoryId);
        HttpSession session = req.getSession();
        session.setAttribute("category", category);
        session.setAttribute("productList", productList);
        req.getRequestDispatcher(MAIN_FORM).forward(req, resp);
    }
}
