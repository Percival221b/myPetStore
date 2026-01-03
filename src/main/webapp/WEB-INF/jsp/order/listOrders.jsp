
<%@ include file="../common/top.jsp" %>

<div id="BackLink">
    <a href="categoryForm?categoryId=${sessionScope.category.categoryId}">Return to ${sessionScope.category.name}</a>
</div>

<div id="Catalog">
    <h2>My Orders</h2>

    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
        <tr>
            <th>Order ID</th>
            <th>Order Date</th>
            <th>Total Price</th>
            <th>View Details</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td>${order.orderId}</td>
                <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
                <td><fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00" /></td>
                <td>
                    <a href="viewOrder?orderId=${order.orderId}">View Order</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../common/bottom.jsp" %>

