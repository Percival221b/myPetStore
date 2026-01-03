package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.petstore.domain.Cart;
import org.example.petstore.domain.CartItem;
import org.example.petstore.service.CartService;

import java.io.IOException;
import java.util.Iterator;

public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String username = (String) session.getAttribute("username");

        String itemId = req.getParameter("itemId");
        String quantityStr = req.getParameter("quantity");

        CartService cartService = new CartService();

        int quantity = Integer.parseInt(quantityStr);

        if (quantity < 1) {
            cart.removeItemById(itemId);
            cartService.removeItemFromCart(username, itemId);
        } else {
            cart.setQuantityByItemId(itemId, quantity);
            cartService.updateItemQuantity(username, itemId, quantity);
        }

        CartItem cartItem = null;
        Iterator<CartItem> iterator = cart.getAllCartItems();
        while (iterator.hasNext()) {
            CartItem ci = iterator.next();
            if (ci.getItem().getItemId().equals(itemId)) {
                cartItem = ci;
                break;
            }
        }

        session.setAttribute("cart", cart);

        resp.setContentType("application/json;charset=UTF-8");

        String json = "{"
                + "\"success\":true,"
                + "\"subtotal\":" + (cartItem == null ? 0 : cartItem.getTotal()) + ","
                + "\"totalPrice\":" + cart.getSubTotal()
                + "}";

        resp.getWriter().write(json);
    }
}
