<%@ include file="../common/top.jsp"%>
<div id="BackLink">
    <a href="">return to Main Menu</a>
</div>

<div id="Catalog">

    <table>
        <tr>
            <th>&nbsp;</th>
            <th>Product ID</th>
            <th>Name</th>
        </tr>
        <c:forEach var="product" items="${sessionScope.searchResults}">
            <tr>
                <td>
                        ${product.name}
                </td>
                <td>${product.description}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/productForm?productId=${product.productId}">View Details</a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>

<%@ include file="../common/bottom.jsp"%>