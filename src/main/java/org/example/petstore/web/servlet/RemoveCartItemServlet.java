package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.petstore.domain.Cart;
import org.example.petstore.service.CartService;

import java.io.IOException;

public class RemoveCartItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String username = (String) session.getAttribute("username");

        String itemId = req.getParameter("itemId");

        CartService cartService = new CartService();

        cart.removeItemById(itemId);

        cartService.removeItemFromCart(username, itemId);

        session.setAttribute("cart", cart);

        resp.setContentType("application/json;charset=UTF-8");

        boolean cartEmpty = cart.getNumberOfItems() == 0;

        String json = "{"
                + "\"success\":true,"
                + "\"totalPrice\":" + cart.getSubTotal() + ","
                + "\"cartEmpty\":" + cartEmpty
                + "}";

        resp.getWriter().write(json);
    }
}
