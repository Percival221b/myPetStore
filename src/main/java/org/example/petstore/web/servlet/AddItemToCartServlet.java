package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Item;
import org.example.petstore.service.CatalogService;

import java.io.IOException;

public class AddItemToCartServlet extends HttpServlet {

    private static final String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workingItemId = req.getParameter("workingItemId");
        HttpSession session = req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        if (workingItemId != null) {
            if (cart.containsItemId(workingItemId)) {
                cart.incrementQuantityByItemId(workingItemId);
            } else {
                CatalogService catalogService = new CatalogService();
                boolean isInStock = catalogService.isItemInStock(workingItemId);
                Item item = catalogService.getItem(workingItemId);
                if (item != null) {
                    cart.addItem(item, isInStock);
                }
            }
        }

        session.setAttribute("cart", cart);
        // 改为重定向
        resp.sendRedirect(req.getContextPath() + "/cartForm");
    }
}
