package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Product;
import org.example.petstore.service.CatalogService;

import java.io.IOException;
import java.util.List;

public class SearchFormServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String SEARCH_FORM = "/WEB-INF/jsp/catalog/searchResult.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        catalogService = new CatalogService();

        List<Product> searchResults = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            searchResults = catalogService.searchProductList(keyword.trim());
        }

        HttpSession session = req.getSession();
        session.setAttribute("searchResults", searchResults);
        session.setAttribute("keyword", keyword);

        req.getRequestDispatcher(SEARCH_FORM).forward(req, resp);
    }
}