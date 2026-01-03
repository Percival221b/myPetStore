package org.example.petstore.web.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.domain.LineItem;
import org.example.petstore.domain.Order;
import org.example.petstore.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetItemsServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            System.out.println("orderId = " + orderId);

            List<LineItem> items = orderService.getLineItemsByOrderId(orderId);
            System.out.println("items = " + items);

            PrintWriter out = response.getWriter();
            out.println("<table border='1'>");
            out.println("<tr><th>Item ID</th><th>Description</th><th>Quantity</th><th>Price</th><th>Total</th></tr>");

            if (items != null) {
                for (LineItem lineItem : items) {
                    out.println("<tr>");
                    out.println("<td>" + (lineItem.getItem() != null ? lineItem.getItem().getItemId() : "null") + "</td>");
                    out.println("<td>" + (lineItem.getItem() != null ? lineItem.getItem().getAttribute1() : "null") + "</td>");
                    out.println("<td>" + lineItem.getQuantity() + "</td>");
                    out.println("<td>" + lineItem.getUnitPrice() + "</td>");
                    out.println("<td>" + lineItem.getTotal() + "</td>");
                    out.println("</tr>");
                }
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();   // ⭐ 关键
            response.getWriter().println("ERROR: " + e.getMessage());
        }
    }

}
