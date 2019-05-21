<%@ page import="edu.nju.yummy.model.Dish" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.nju.yummy.dto.ShopDto" %>
<%@ page import="edu.nju.yummy.dto.DishListDto" %>
<%@ page import="java.util.Map" %>
<%@ page import="edu.nju.yummy.model.Cart" %>
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
    <title>Yummy!-商户详情</title>

    <link rel="stylesheet" href="css/shopIndex.css">
    <script src="js/shopDetail.js"></script>
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

<jsp:useBean id="shopDetailDto" type="edu.nju.yummy.dto.ShopDetailDto" scope="request"/>
<%
    ShopDto shopDto = shopDetailDto.getShopDto();
%>
<div class="container" style="padding-top: 60px;">
    <div>
        <h3><% out.print(shopDto.getName()); %></h3>
        <%
            //购物车中的数量
            Cart cart = ((Cart)session.getAttribute("cart"));
            int numOfCart = cart == null ? 0 : cart.itemSumOfThisShop(shopDto.getShopid());
        %>
        <a class="btn btn-primary pull-right btn_cart" href="user/cartDetail?shopid=<% out.print(shopDto.getShopid()); %>">
            购物车 <span class="badge"><% out.print(numOfCart); %></span>
        </a>
    </div>
    <%
        DishListDto dishListDto = shopDetailDto.getDishListDto();
        List<String> typeList = dishListDto.getTypeList();
    %>
    <ul class="nav nav-tabs">
        <li role="presentation" <% if(dishListDto.getTypeToShow() == null) out.print("class=\"active\""); %>><a href="user/shopDetail?shopid=<% out.print(shopDto.getShopid()); %>">全部</a></li>
        <%
            for(String type : typeList){
        %>
        <li role="presentation" <% if(type.equals(dishListDto.getTypeToShow()))  out.print("class=\"active\""); %>><a href="<% out.print("user/shopDetail?shopid="+shopDto.getShopid()+"&dishType="+type); %>"><% out.print(type); %></a></li>
        <%
            }
        %>
    </ul>
    <%--商品列表--%>
    <div class="dish_container">
        <%
            List<Dish> dishList = dishListDto.getDishList();
            for (int i = 0; dishList != null && i < dishList.size(); i++){
                Dish dish = dishList.get(i);
        %>
        <div class="tab_dish">
            <div class="img_container pull-left">
                <a class="thumbnail pull-left">
                    <img src="image/yummy.jpg" alt="">
                </a>
            </div>
            <div class="caption col-md-3">
                <label><% out.print(dish.getName()); %></label>
                <span class="pull-right"><small><% out.print(dish.getType()); %></small></span>
                <p class="brief"><% out.print(dish.getBrief()); %></p>
                <p class="time" style="color: red;">
                    <%
                        if (dish.getIslimited() == 1){
                            out.print(dish.getStarttime().toString()+" 至 "+dish.getEndtime());
                        }
                    %>
                </p>
                <label>数量: </label><span><% out.print(dish.getNum()); %></span>
                <%
                    if(dish.getActual() != -1){
                %>
                <small><del>￥<% out.print(dish.getPrice()); %></del></small><label> ￥<% out.print(dish.getActual()); %></label>
                <%
                    } else {
                %>
                <label>￥<% out.print(dish.getPrice()); %></label>
                <%
                    }
                %>
                <button class="btn btn-sm btn-default pull-right" onclick="addDishToCart(<% out.print(shopDto.getShopid()); %>, <% out.print(dish.getDishid()); %>)">＋</button>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
<script>
    var numOfCart = <% out.print(numOfCart); %>
</script>
</body>
</html>
