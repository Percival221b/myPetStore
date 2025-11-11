<%@ include file="../common/top.jsp"%>

<div id="Catalog">
    <form action="shippingSubmit" method="post">
    <table>
        <tr>
            <th colspan="2">Shipping Address</th>
        </tr>

        <tr>
            <td>First name:</td>
            <td><input type="text" name="shipToFirstName" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipToFirstName() : "" %>" /></td>
        </tr>

        <tr>
            <td>Last name:</td>
            <td><input type="text" name="shipToLastName" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipToLastName() : "" %>" /></td>
        </tr>

        <tr>
            <td>Address 1:</td>
            <td><input type="text" size="40" name="shipAddress1" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipAddress1() : "" %>" /></td>
        </tr>

        <tr>
            <td>Address 2:</td>
            <td><input type="text" size="40" name="shipAddress2" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipAddress2() : "" %>" /></td>
        </tr>

        <tr>
            <td>City:</td>
            <td><input type="text" name="shipCity" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipCity() : "" %>" /></td>
        </tr>

        <tr>
            <td>State:</td>
            <td><input type="text" size="4" name="shipState" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipState() : "" %>" /></td>
        </tr>

        <tr>
            <td>Zip:</td>
            <td><input type="text" size="10" name="shipZip" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipZip() : "" %>" /></td>
        </tr>

        <tr>
            <td>Country:</td>
            <td><input type="text" size="15" name="shipCountry" value="<%= (request.getAttribute("order") != null) ? ((org.example.petstore.domain.Order)request.getAttribute("order")).getShipCountry() : "" %>" /></td>
        </tr>

        <tr>
            <td colspan="2" style="text-align:center;">
                <input type="submit" name="newOrder" value="Continue" />
            </td>
        </tr>

    </table>

    </form>
    </div>

<%@ include file="../common/bottom.jsp"%>