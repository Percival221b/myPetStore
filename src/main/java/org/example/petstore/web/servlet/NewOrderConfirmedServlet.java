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
        } else {
            order.setShipToFirstName(req.getParameter("billToFirstName"));
            order.setShipToLastName(req.getParameter("billToLastName"));
            order.setShipAddress1(req.getParameter("billAddress1"));
            order.setShipAddress2(req.getParameter("billAddress2"));
            order.setShipCity(req.getParameter("billCity"));
            order.setShipState(req.getParameter("billState"));
            order.setShipZip(req.getParameter("billZip"));
            order.setShipCountry(req.getParameter("billCountry"));
        }

        try {
            // 先插入订单，以便获得生成的 orderId
            orderService.insertOrder(order); // 这里插入订单并生成 orderId
            // 在插入 LineItem 之前，确保 orderId 已经被设置
            int lineNumber = 1;  // 用于生成唯一的 lineNumber
            for (CartItem cartItem : cart.getCartItemList()) {
                LineItem lineItem = new LineItem();
                lineItem.setOrderId(order.getOrderId());  // 关联订单，确保 orderId 已经正确赋值
                lineItem.setLineNumber(lineNumber);  // 设置lineNumber，确保每个商品唯一
                lineItem.setItem(cartItem.getItem());  // 设置商品
                lineItem.setQuantity(cartItem.getQuantity());  // 设置数量
                lineItem.setUnitPrice(cartItem.getItem().getUnitCost());  // 设置单价

                // 计算总价
                lineItem.calculateTotal();  // 计算每个 LineItem 的 total

                // 将 LineItem 插入数据库
                orderService.insertLineItem(lineItem);

                // 增加lineNumber
                lineNumber++;
            }

            // 插入订单状态
            orderService.insertOrderStatus(order);

            // 清空购物车
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
