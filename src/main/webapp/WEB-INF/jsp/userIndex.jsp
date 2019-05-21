<%@ page import="edu.nju.yummy.util.ShopType" %>
<%@ page import="edu.nju.yummy.dto.ShopDto" %>
<%@ page import="java.util.List" %>
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
    <title>Yummy!-用户</title>


    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/userIndex.css">

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

<div class="container" style="padding-top: 60px;">
    <%--<label><% out.print(session.getAttribute("id")); %></label>--%>
    <%--<a class="btn btn-primary" href="user/logout">登出</a>--%>
    <%--<a class="btn btn-default" href="user/info">个人中心</a>--%>
    <%--<a class="btn btn-default" href="user/order">我的订单</a>--%>
    <%--<a class="btn btn-default" href="user/statistics">统计信息</a>--%>

    <div>
        <jsp:useBean id="shopListDto" type="edu.nju.yummy.dto.ShopListDto" scope="request"/>
        <h3>
            商店列表
            <%--<small class="pull-right">--%>
                <%--配送至:--%>
                <%--<select onchange="self.location.href=options[selectedIndex].value">--%>
                    <%--<option value="user/index?<% if (shopListDto.getTypeToShow() != null) out.print("?shopType="+shopListDto.getTypeToShow()+"&");%>addressid=1">(100, 100)</option>--%>
                    <%--<option value="user/index?addressid=2">(40, 30)</option>--%>
                <%--</select>--%>
            <%--</small>--%>
        </h3>
        <ul class="nav nav-tabs">
            <li role="presentation" <% if(shopListDto.getTypeToShow() == null) out.print("class=\"active\""); %>><a href="user/index">全部</a></li>
            <%
                for(String type : ShopType.shopTypes){
            %>
            <li role="presentation" <% if(type.equals(shopListDto.getTypeToShow()))  out.print("class=\"active\""); %>><a href="<% out.print("user/index?shopType="+type); %>"><% out.print(type); %></a></li>
            <%
                }
            %>
        </ul>

        <div class="shop_container">
            <%
                List<ShopDto> shopDtoList = shopListDto.getShopDtoList();
                for (int i = 0; shopDtoList != null && i < shopDtoList.size(); i++){
                    ShopDto shop = shopDtoList.get(i);
            %>
            <div class="tab_shop" href="user/shopDetail?shopid=<% out.print(shop.getShopid()); %>">
                <div class="img_container pull-left">
                    <a class="thumbnail pull-left" href="user/shopDetail?shopid=<% out.print(shop.getShopid()); %>">
                        <img src="image/yummy.jpg" alt="">
                    </a>
                </div>
                <div class="caption col-md-3">
                    <a class="name" href="user/shopDetail?shopid=<% out.print(shop.getShopid()); %>"><label><% out.print(shop.getName()); %></label></a>
                    <span class="pull-right"><small><% out.print(shop.getType()); %></small></span>
                    <p class="brief"><% out.print(shop.getBrief()); %></p>
                    <p class="location"><% out.print("("+shop.getX()+", "+shop.getY()+")"); %></p>
                    <%--<label class="distance">距离: </label><span><% out.print(300); %></span>--%>
                    <a class="btn btn-sm btn-default pull-right" href="user/shopDetail?shopid=<% out.print(shop.getShopid()); %>">进入</a>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>
</body>
</html>
