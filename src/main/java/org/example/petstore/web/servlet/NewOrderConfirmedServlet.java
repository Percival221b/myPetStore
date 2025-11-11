package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Order;
import org.example.petstore.service.OrderService;

import java.io.IOException;

public class NewOrderConfirmedServlet extends HttpServlet {
    OrderService orderService = new OrderService();

    private static final String NEW_ORDER_FORM = "/WEB-INF/jsp/order/newOrderForm.jsp";
    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/confirmOrder.jsp";
    private static final String LIST_ORDERS = "/WEB-INF/jsp/order/listOrders.jsp";
    private static final String SHIPPING = "/WEB-INF/jsp/order/shippingForm.jsp";
    private static final String VIEW_ORDER = "/WEB-INF/jsp/order/viewOrder.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        Order order = (Order) session.getAttribute("order");
        boolean shippingAddressRequired = req.getParameter("shippingAddressRequired") != null;
        boolean confirmed = session.getAttribute("confirmed")!= null;
        String cardType = req.getParameter("cardType");
        String creditCard = req.getParameter("creditCard");
        String expiryDate = req.getParameter("expiryDate");
        String billToFirstName = req.getParameter("billToFirstName");
        String billToLastName = req.getParameter("billToLastName");
        String billAddress1 = req.getParameter("billAddress1");
        String billAddress2 = req.getParameter("billAddress2");
        String billCity = req.getParameter("billCity");
        String billState = req.getParameter("billState");
        String billZip = req.getParameter("billZip");
        String billCountry = req.getParameter("billCountry");
        session.setAttribute("cardType", cardType);
        session.setAttribute("creditCard", creditCard);
        session.setAttribute("expiryDate", expiryDate);
        session.setAttribute("billToFirstName", billToFirstName);
        session.setAttribute("billToLastName", billToLastName);
        session.setAttribute("billAddress1", billAddress1);
        session.setAttribute("billAddress2", billAddress2);
        session.setAttribute("billCity", billCity);
        session.setAttribute("billState", billState);
        session.setAttribute("billZip", billZip);
        session.setAttribute("billCountry", billCountry);
        if (shippingAddressRequired){
            session.setAttribute("shippingAddressRequired", false);
            req.getRequestDispatcher(SHIPPING).forward(req, resp);
            return;
        }else if(!confirmed){
            req.getRequestDispatcher(CONFIRM_ORDER).forward(req, resp);
            return;
        }
        else if(order != null){
            try {
            // 插入订单到数据库
            orderService.insertOrder(order);
            orderService.insertOrderStatus(order);

            // 清空购物车
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                //cart.clear();
            }

            // 提示信息
            req.setAttribute("message", "Thank you, your order has been submitted.");
            req.setAttribute("order", order);

            // 转发到查看订单页面
            req.getRequestDispatcher(VIEW_ORDER).forward(req, resp);

            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "An error occurred while processing your order.");
                req.getRequestDispatcher(NEW_ORDER_FORM).forward(req, resp);
            }
        }
        else {
            // 没有订单对象
            req.setAttribute("error", "An error occurred processing your order (order was null).");
            req.getRequestDispatcher(NEW_ORDER_FORM).forward(req, resp);
        }
    }
}
