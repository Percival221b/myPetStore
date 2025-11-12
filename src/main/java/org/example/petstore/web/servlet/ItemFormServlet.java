package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Item;
import org.example.petstore.domain.Product;
import org.example.petstore.persistence.LogDao;
import org.example.petstore.service.CatalogService;

import java.io.IOException;
import java.util.List;

public class ItemFormServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String ITEM_FORM = "/WEB-INF/jsp/catalog/item.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("itemId");
        catalogService = new CatalogService();
        Item item = catalogService.getItem(itemId);
        Product product = item.getProduct();

        HttpSession session = req.getSession();
        session.setAttribute("product", product);
        session.setAttribute("item", item);
        Account account = (Account) session.getAttribute("account");
        String username = account.getUsername();
        LogDao.addLog(username, "VIEW_ITEM", itemId, "browsed item " + itemId);
        req.getRequestDispatcher(ITEM_FORM).forward(req, resp);
    }
}
