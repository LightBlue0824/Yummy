<%@ page import="java.util.List" %>
<%@ page import="edu.nju.yummy.dto.CartAndOrderItemDto" %>
<%@ page import="edu.nju.yummy.dto.DeliveryAddressDto" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>

<html>
<head>
    <meta charset="utf-8">

    <base href='<%=basePath %>' />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <title>Yummy!-购物车</title>

    <link rel="stylesheet" href="css/main.css">
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Yummy!</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="nav navbar-nav">
                <li><a href="user/index">主页</a></li>
                <li><a href="user/info">个人中心</a></li>
                <li><a href="user/order">我的订单</a></li>
                <li><a href="user/statistics">统计信息</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><% out.print(session.getAttribute("id")); %></a></li>
                <li><a href="user/logout">登出</a></li>

            </ul>
        </div>
    </div>
</nav>

<jsp:useBean id="cartDto" type="edu.nju.yummy.dto.CartDto" scope="request"/>
<div class="container" style="padding-top: 60px;">
    <div class="my_container center-block">
        <h2>购物车</h2>
        <h3>
            <% out.print(cartDto.getShopName()); %>
            <a class="btn btn-default pull-right" href="user/shopDetail?shopid=<jsp:getProperty name="cartDto" property="shopid"/>">返回商家</a>
        </h3>
        <table class="table">
            <thead>
            <tr>
                <th>商品名</th>
                <th>数量</th>
                <th>单价</th>
                <th>总价</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%
                List<CartAndOrderItemDto> cartItemDtoList = cartDto.getCartItemDtoList();
                for(CartAndOrderItemDto cartItemDto : cartItemDtoList){
            %>
            <tr>
                <td><% out.print(cartItemDto.getDishName()); %></td>
                <td>
                    <form method="post" action="user/subDishNumOfCart" style="display: inline;">
                        <input name="shopid" value="<jsp:getProperty name="cartDto" property="shopid"/>" hidden>
                        <input name="dishid" value="<% out.print(cartItemDto.getDishid()); %>" hidden>
                        <button class="btn_add-sub"><span aria-hidden="true">－</span></button>
                    </form>
                    <span><% out.print(cartItemDto.getNum()); %></span>
                    <form method="post" action="user/addDishNumOfCart" style="display: inline;">
                        <input name="shopid" value="<jsp:getProperty name="cartDto" property="shopid"/>" hidden>
                        <input name="dishid" value="<% out.print(cartItemDto.getDishid()); %>" hidden>
                        <button class="btn_add-sub"><span aria-hidden="true">＋</span></button>
                    </form>
                </td>
                <%
                    if(cartItemDto.getActual() != -1){
                %>
                <td><small><del>￥<% out.print(cartItemDto.getPrice()); %></del></small> ￥<% out.print(cartItemDto.getActual()); %></td>
                <%
                    } else {
                %>
                <td>￥<% out.print(cartItemDto.getPrice()); %></td>
                <%
                    }
                %>
                <td><% out.print(cartItemDto.getSum()); %></td>
                <td>
                    <form method="post" action="user/delDishOfCart">
                        <input name="shopid" value="<jsp:getProperty name="cartDto" property="shopid"/>" hidden>
                        <input name="dishid" value="<% out.print(cartItemDto.getDishid()); %>" hidden>
                        <button class="close"><span aria-hidden="true">&times;</span></button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <label class="pull-right">总计：<span><jsp:getProperty name="cartDto" property="total"/></span></label>
        <div class="clearfix"></div>
        <form class="form-inline" method="post" action="user/addOrder">
            <input name="shopid" value="<% out.print(cartDto.getShopid()); %>" hidden>
            <div class="form-group">
                <label class="control-label" for="address">收货地址</label>
                <select name="address" class="form-control" id="address" required onchange="self.location.href=options[selectedIndex].id">

                    <%
                        List<DeliveryAddressDto> addressDtoList = cartDto.getAddressDtoList();
                        for(DeliveryAddressDto address : addressDtoList){
                    %>
                    <option value="<% out.print(address.getAddressString()); %>"
                            id="user/cartDetail?shopid=<% out.print(cartDto.getShopid()); %>&addressid=<% out.print(address.getAddressid()); %>"
                            <% if(cartDto.getSelectedAddressId() == address.getAddressid()) out.print("selected"); %>>
                        <pre><% out.print(address.getName()+"  "+address.getPhone()+"  ("+address.getX()+", "+address.getY()+")"); %></pre>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label>距离: </label> <span><jsp:getProperty name="cartDto" property="distance"/></span>
            </div>

            <div class="form-group">
                <%
                    if(cartDto.getDeliveryTime() == -1){
                %>
                <label class="text-danger">超出配送范围</label>
                <%
                    }else{
                %>
                <label>预计配送时间: </label> <span><jsp:getProperty name="cartDto" property="deliveryTime"/>分钟</span>
                <input name="deliveryTime" value="<jsp:getProperty name="cartDto" property="deliveryTime"/>" required hidden>
                <%
                    }
                %>
            </div>
            <div class="clearfix"></div>
            <%
                if(cartItemDtoList.size() > 0){
                    if(cartDto.getDeliveryTime() == -1){
            %>
                <button type="button" class="btn btn-primary pull-right" disabled>支付</button>
            <%
                    } else {
            %>
                <button class="btn btn-primary pull-right">支付</button>
            <%
                    }
                }
            %>
        </form>
    </div>
</div>
</body>
</html>
