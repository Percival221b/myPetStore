<%@ include file="../common/top.jsp"%>

<div id="Catalog">

    <!-- 选项卡 -->
    <div class="tabs">
        <button class="tablinks" onclick="openTab(event, 'PaymentDetails')">Payment Details</button>
        <button class="tablinks" onclick="openTab(event, 'BillingAddress')">Billing Address</button>
        <button class="tablinks" onclick="openTab(event, 'ShippingAddress')">Shipping Address</button>
        <button class="tablinks" onclick="openTab(event, 'Items')">Items</button>
    </div>


    <!-- Tab内容 -->
    <div id="PaymentDetails" class="tabcontent">
        <!-- 通过AJAX加载 -->
    </div>

    <div id="BillingAddress" class="tabcontent">
        <!-- 通过AJAX加载 -->
    </div>

    <div id="ShippingAddress" class="tabcontent">
        <!-- 通过AJAX加载 -->
    </div>

    <div id="Items" class="tabcontent">
        <!-- 通过AJAX加载 -->
    </div>

</div>

<%@ include file="../common/bottom.jsp"%>

<script>
    // 用来切换tab
    function openTab(evt, tabName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.className += " active";
    }

    // 加载订单的不同部分
    function loadOrderDetails() {
        // Load Payment Details
        loadTabContent('PaymentDetails', 'getPaymentDetails');
        // Load Billing Address
        loadTabContent('BillingAddress', 'getBillingAddress');
        // Load Shipping Address
        loadTabContent('ShippingAddress', 'getShippingAddress');
        // Load Items
        loadTabContent('Items', 'getItems');
    }

    // 通用AJAX加载函数
    function loadTabContent(tabId, endpoint) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '<%=request.getContextPath()%>/' + endpoint + '?orderId=${sessionScope.order.orderId}', true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                document.getElementById(tabId).innerHTML = xhr.responseText;
            } else {
                document.getElementById(tabId).innerHTML = 'Failed to load content.';
            }
        };
        xhr.send();
    }

    // 当页面加载时自动调用
    window.onload = function () {
        loadOrderDetails();
    };
</script>
