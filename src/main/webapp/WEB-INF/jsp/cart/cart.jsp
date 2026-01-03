<%@ include file="../common/top.jsp"%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div id="BackLink">
    <a href="mainForm">Return to Main Menu</a>
</div>

<div id="Catalog">

    <div id="Cart">
        <h2>Shopping Cart</h2>

        <form action="updateCart" method="post">
            <table>
                <tr>
                    <th>Item ID</th>
                    <th>Product ID</th>
                    <th>Description</th>
                    <th>In Stock?</th>
                    <th>Quantity</th>
                    <th>List Price</th>
                    <th>Total Cost</th>
                    <th>&nbsp;</th>
                </tr>

                <tr>
                    <td colspan="8">username: ${sessionScope.username}</td>
                </tr>

                <c:if test="${sessionScope.cart.numberOfItems == 0}">
                    <tr>
                        <td colspan="8"><b>Your cart is empty.</b></td>
                    </tr>
                </c:if>

                <c:forEach var="cartItem" items="${sessionScope.cart.cartItemList}">
                    <tr>
                        <td>
                            <a href="itemForm?itemId=${cartItem.item.itemId}">
                                    ${cartItem.item.itemId}
                            </a>
                        </td>
                        <td>${cartItem.item.product.productId}</td>
                        <td>
                                ${cartItem.item.attribute1}
                                ${cartItem.item.attribute2}
                                ${cartItem.item.attribute3}
                                ${cartItem.item.attribute4}
                                ${cartItem.item.attribute5}
                                ${cartItem.item.product.name}
                        </td>
                        <td>${cartItem.inStock}</td>


                        <td>
                            <input type="number"
                                   class="quantity-input"
                                   data-itemid="${cartItem.item.itemId}"
                                   value="${cartItem.quantity}"
                                   min="1"/>
                        </td>

                        <td>
                            <fmt:formatNumber value="${cartItem.item.listPrice}"
                                              pattern="$#,##0.00"/>
                        </td>


                        <td>
                            $<span id="subtotal-${cartItem.item.itemId}">
                                <fmt:formatNumber value="${cartItem.total}"
                                                  pattern="#,##0.00"/>
                            </span>
                        </td>

                        <td>
                            <a href="javascript:void(0);"
                               class="Button remove-btn"
                               data-itemid="${cartItem.item.itemId}">
                                Remove
                            </a>

                        </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td colspan="7">
                        Sub Total:
                        $<span id="totalPrice">
                            <fmt:formatNumber value="${sessionScope.cart.subTotal}"
                                              pattern="#,##0.00"/>
                        </span>


                    </td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </form>

        <c:if test="${sessionScope.cart.numberOfItems > 0}">
            <a href="newOrderForm" class="Button">Proceed to Checkout</a>
        </c:if>
    </div>

    <div id="MyList">
        <c:if test="${sessionScope.loginAccount != null}">
            <c:if test="${!empty sessionScope.loginAccount.listOption}">
                <%@ include file="includeMyList.jsp"%>
            </c:if>
        </c:if>
    </div>

    <div id="Separator">&nbsp;</div>
</div>

<script>
    $(function () {

        $(".quantity-input").on("change", function () {

            let itemId = $(this).data("itemid");
            let quantity = $(this).val();

            if (quantity <= 0) {
                alert("Quantity must be greater than 0");
                return;
            }

            $.ajax({
                url: "updateCart",
                type: "POST",
                data: {
                    itemId: itemId,
                    quantity: quantity
                },
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        $("#subtotal-" + itemId).text(result.subtotal);
                        $("#totalPrice").text(result.totalPrice);
                    } else {
                        alert("Update failed");
                    }
                },
                error: function () {
                    alert("Server error");
                }
            });
        });

        $(".remove-btn").on("click", function () {

            let itemId = $(this).data("itemid");
            let row = $(this).closest("tr");

            if (!confirm("Are you sure you want to remove this item?")) {
                return;
            }

            $.ajax({
                url: "removeCartItem",
                type: "POST",
                data: {
                    itemId: itemId
                },
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        // 删除当前行
                        row.remove();

                        // 更新总价
                        $("#totalPrice").text(result.totalPrice);

                        // 如果购物车空了
                        if (result.cartEmpty) {
                            $("#Cart table").append(
                                "<tr><td colspan='8'><b>Your cart is empty.</b></td></tr>"
                            );
                        }
                    } else {
                        alert("Remove failed");
                    }
                },
                error: function () {
                    alert("Server error");
                }
            });
        });
    });
</script>

<%@ include file="../common/bottom.jsp"%>

