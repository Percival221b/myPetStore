package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.*;
import org.example.petstore.persistence.LogDao;
import org.example.petstore.service.CartService;
import org.example.petstore.service.OrderService;

import java.io.IOException;

public class NewOrderConfirmedServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    CartService cartService = new CartService();

    private static final String NEW_ORDER_FORM = "/WEB-INF/jsp/order/newOrderForm.jsp";
    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/confirmOrder.jsp";
    private static final String SHIPPING = "/WEB-INF/jsp/order/shippingForm.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Order order = new Order();
        Account account = (Account) session.getAttribute("loginAccount");
        Cart cart = (Cart) session.getAttribute("cart");
        order.initOrder(account, cart);
        boolean shippingAddressRequired = req.getParameter("shippingAddressRequired") != null;

        // 从表单获取支付信息
        String cardType = req.getParameter("cardType");
        String creditCard = req.getParameter("creditCard");
        String expiryDate = req.getParameter("expiryDate");

        // 设置订单支付信息
        order.setCardType(cardType);
        order.setCreditCard(creditCard);
        order.setExpiryDate(expiryDate);


        // 从表单获取账单信息
        order.setBillToFirstName(req.getParameter("billToFirstName"));
        order.setBillToLastName(req.getParameter("billToLastName"));
        order.setBillAddress1(req.getParameter("billAddress1"));
        order.setBillAddress2(req.getParameter("billAddress2"));
        order.setBillCity(req.getParameter("billCity"));
        order.setBillState(req.getParameter("billState"));
        order.setBillZip(req.getParameter("billZip"));
        order.setBillCountry(req.getParameter("billCountry"));

        // 保存到 session
        session.setAttribute("order", order);

        if (shippingAddressRequired) {
            req.getRequestDispatcher(SHIPPING).forward(req, resp);
            return;
        }
        else{
            order.setShipToFirstName(req.getParameter("billToFirstName"));
            order.setShipToLastName(req.getParameter("billToLastName"));
            order.setShipAddress1(req.getParameter("billAddress1"));
            order.setShipAddress2(req.getParameter("billAddress2"));
            order.setShipCity(req.getParameter("billCity"));
            order.setShipState(req.getParameter("billState"));
            order.setShipZip(req.getParameter("billZip"));
            order.setShipCountry(req.getParameter("billCountry"));
        }

        // 插入每个 LineItem 到数据库
        if (cart != null) {
            // 遍历购物车中的每个商品，生成 LineItem
            for (CartItem cartItem : cart.getCartItemList()) {
                LineItem lineItem = new LineItem();
                lineItem.setOrderId(order.getOrderId());  // 关联订单
                lineItem.setItem(cartItem.getItem());  // 设置商品
                lineItem.setQuantity(cartItem.getQuantity());  // 设置数量
                lineItem.setUnitPrice(cartItem.getItem().getUnitCost());  // 设置单价
                orderService.insertLineItem(lineItem);  // 将 LineItem 插入数据库
            }
        }

        try {
            // 插入订单和订单状态
            orderService.insertOrder(order);
            orderService.insertOrderStatus(order);

            if (cart != null) {
                cart.clear();  // 清空购物车
                cartService.clearCart(cart.getCartId());
                session.setAttribute("cart", cart);  // 更新 session 中的购物车
            }
            // 记录日志
            LogDao.addLog(account.getUsername(), "CREATE_ORDER", null, "created new order");

            // 提示信息并转发到订单确认页面
            req.setAttribute("message", "Thank you, your order has been submitted.");
            req.setAttribute("order", order);
            req.getRequestDispatcher(CONFIRM_ORDER).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while processing your order.");
            req.getRequestDispatcher(NEW_ORDER_FORM).forward(req, resp);
        }
    }

}
