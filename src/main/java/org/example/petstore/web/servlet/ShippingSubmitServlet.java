package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Order;
import org.example.petstore.persistence.LogDao;
import org.example.petstore.persistence.OrderDao;
import org.example.petstore.service.OrderService;

import java.io.IOException;

public class ShippingSubmitServlet extends HttpServlet {
    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/confirmOrder.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String shipToFirstName = req.getParameter("shipToFirstName");
        String shipToLastName = req.getParameter("shipToLastName");
        String shipAddress1 = req.getParameter("shipAddress1");
        String shipAddress2 = req.getParameter("shipAddress2");
        String shipToCity = req.getParameter("shipToCity");
        String shipToState = req.getParameter("shipToState");
        String shipToZip = req.getParameter("shipToZip");
        String shipToCountry = req.getParameter("shipToCountry");
        HttpSession session = req.getSession();
        session.setAttribute("shipToFirstName", shipToFirstName);
        session.setAttribute("shipToLastName", shipToLastName);
        session.setAttribute("shipAddress1", shipAddress1);
        session.setAttribute("shipAddress2", shipAddress2);
        session.setAttribute("shipCity", shipToCity);
        session.setAttribute("shipState", shipToState);
        session.setAttribute("shipZip", shipToZip);
        session.setAttribute("shipCountry", shipToCountry);
        // 插入订单到数据库
        OrderService orderService=new OrderService();
        Order order = (Order) session.getAttribute("order");
        orderService.insertOrder(order);
        orderService.insertOrderStatus(order);

        // 清空购物车
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            //cart.clear();
        }

        Account account = (Account) session.getAttribute("account");
        String username = account.getUsername();
        LogDao.addLog(username, "CREATE_ORDER", null, "created new order");
        // 提示信息
        req.setAttribute("message", "Thank you, your order has been submitted.");
        req.setAttribute("order", order);

        // 转发到查看订单页面
        req.getRequestDispatcher(CONFIRM_ORDER).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
