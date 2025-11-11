<%@ include file="../common/top.jsp"%>

<div id="BackLink">
    <a href="categoryForm?categoryId=${sessionScope.category.categoryId}">Return to ${sessionScope.category.name}</a>
</div>

<div id="Catalog">Please confirm the information below and then
    press continue...

    <table>
        <tr>
            <th align="center" colspan="2"><font size="4"><b>Order</b></font>
                <br />
                <font size="3"><b> <fmt:formatDate
                        value="${sessionScope.order.orderDate}" pattern="yyyy/MM/dd hh:mm:ss" /></b></font>
            </th>
        </tr>

        <tr>
            <th colspan="2">Billing Address</th>
        </tr>
        <tr>
            <td>First name:</td>
            <td><c:out value="${sessionScope.billToFirstName}" /></td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td><c:out value="${sessionScope.billToLastName}" /></td>
        </tr>
        <tr>
            <td>Address 1:</td>
            <td><c:out value="${sessionScope.billAddress1}" /></td>
        </tr>
        <tr>
            <td>Address 2:</td>
            <td><c:out value="${sessionScope.billAddress2}" /></td>
        </tr>
        <tr>
            <td>City:</td>
            <td><c:out value="${sessionScope.billCity}" /></td>
        </tr>
        <tr>
            <td>State:</td>
            <td><c:out value="${sessionScope.billState}" /></td>
        </tr>
        <tr>
            <td>Zip:</td>
            <td><c:out value="${sessionScope.billZip}" /></td>
        </tr>
        <tr>
            <td>Country:</td>
            <td><c:out value="${sessionScope.billCountry}" /></td>
        </tr>
        <tr>
            <th colspan="2">Shipping Address</th>
        </tr>
        <tr>
            <td>First name:</td>
            <td><c:out value="${sessionScope.shipToFirstName}" /></td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td><c:out value="${sessionScope.shipToLastName}" /></td>
        </tr>
        <tr>
            <td>Address 1:</td>
            <td><c:out value="${sessionScope.shipAddress1}" /></td>
        </tr>
        <tr>
            <td>Address 2:</td>
            <td><c:out value="${sessionScope.shipAddress2}" /></td>
        </tr>
        <tr>
            <td>City:</td>
            <td><c:out value="${sessionScope.shipCity}" /></td>
        </tr>
        <tr>
            <td>State:</td>
            <td><c:out value="${sessionScope.shipState}" /></td>
        </tr>
        <tr>
            <td>Zip:</td>
            <td><c:out value="${sessionScope.shipZip}" /></td>
        </tr>
        <tr>
            <td>Country:</td>
            <td><c:out value="${sessionScope.shipCountry}" /></td>
        </tr>

    </table>

    <a href="viewOrder" class="Button">Confirm</a>

</div>

<%@ include file="../common/bottom.jsp"%>





