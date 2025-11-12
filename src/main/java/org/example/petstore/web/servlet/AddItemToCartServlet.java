package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Item;
import org.example.petstore.persistence.CartDao;
import org.example.petstore.persistence.impl.CartDaoImpl;
import org.example.petstore.service.CartService;
import org.example.petstore.service.CatalogService;

import java.io.IOException;

public class AddItemToCartServlet extends HttpServlet {

    private static final String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";
    private final CartService cartService = new CartService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workingItemId = req.getParameter("workingItemId");
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        String username = loginAccount.getUsername();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = cartService.getCartByUsername(username);
            if (cart.getCartId() == 0) {
                int cartId = cartService.createCart(username);
                cart.setCartId(cartId);
            }
        }

        if (workingItemId != null) {
            if (cart.containsItemId(workingItemId)) {
                cart.incrementQuantityByItemId(workingItemId);
                int quantity = cart.getQuantityByItemId(workingItemId);
                cartService.updateItemQuantity(cart.getCartId(), workingItemId, quantity);
            } else {
                CatalogService catalogService = new CatalogService();
                boolean isInStock = catalogService.isItemInStock(workingItemId);
                Item item = catalogService.getItem(workingItemId);
                if (item != null) {
                    cart.addItem(item, isInStock);
                    cartService.addItemToCart(cart.getCartId(), item, isInStock);
                }
            }
        }
        session.setAttribute("cart", cart);
        // 改为重定向
        resp.sendRedirect(req.getContextPath() + "/cartForm");
    }
}
