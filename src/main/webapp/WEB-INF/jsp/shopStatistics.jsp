<%@ page import="edu.nju.yummy.util.ShopType" %>
<%@ page import="edu.nju.yummy.dto.UserOrderDto" %>
<%@ page import="edu.nju.yummy.util.UserLevel" %>
<%@ page import="edu.nju.yummy.dto.CartAndOrderItemDto" %>
<%@ page import="edu.nju.yummy.dto.ShopOrderDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<%--<%@ taglib prefix="mytag" uri="http://mycompany.com" %>--%>
<%--<mytag:checkUserSession/>--%>
<html>
<head>
    <meta charset="utf-8">
    <base href='<%=basePath %>' />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-datepicker.min.css">

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/shopStatistics.css">

    <title>统计 - 餐厅</title>
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
                <li><a href="shop/index">主页</a></li>
                <li><a href="shop/info">商户信息</a></li>
                <li><a href="shop/statistics">统计信息</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><% out.print(session.getAttribute("id")); %></a></li>
                <li><a href="shop/logout">登出</a></li>

            </ul>
        </div>
    </div>
</nav>

<div class="container" style="padding-top: 60px;">
    <h3>餐厅统计信息</h3>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs tabs_statistics" role="tablist">
            <li role="presentation" class="active"><a href="#filter" aria-controls="filter" role="tab" data-toggle="tab">筛选</a></li>
            <li role="presentation"><a href="#statistics" aria-controls="statistics" role="tab" data-toggle="tab">统计</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="filter">
                <div class="">
                    <label>筛选条件</label>
                    <form class="form-inline">
                        <div class="form-group">
                            <div class="form-group-sm date_container">
                                <label class="control-label">时间: </label>
                                <div class="input-group">
                                    <input type="text" class="input-sm form-control datepicker" name="startTime" placeholder="开始时间" value="<% if(request.getParameter("startTime") != null) out.print(request.getParameter("startTime")); %>"/>
                                    <span class="input-group-addon">至</span>
                                    <input type="text" class="input-sm form-control datepicker" name="endTime" placeholder="结束时间" value="<% if(request.getParameter("endTime") != null) out.print(request.getParameter("endTime")); %>"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label">价格: </label>
                            <div class="input-group">
                                <input type="number" class="input-sm form-control" name="minPrice" value="<% if(request.getParameter("minPrice") != null) out.print(request.getParameter("minPrice")); %>"/>
                                <span class="input-group-addon">至</span>
                                <input type="number" class="input-sm form-control" name="maxPrice" value="<% if(request.getParameter("maxPrice") != null) out.print(request.getParameter("maxPrice")); %>"/>
                            </div>
                        </div>
                        <div class="form-group" >
                            <label class="control-label">用户等级: </label>
                            <select class="form-control" name="userLevel">
                                <option value="" selected>全部</option>
                                <%
                                    Integer selectedUserLevel = request.getParameter("userLevel") == null || request.getParameter("userLevel").equals("") ? -1 : Integer.valueOf(request.getParameter("userLevel"));
                                    for(int i = 1; i <= UserLevel.USER_LEVEL.length; i++){
                                        String userLevel = UserLevel.USER_LEVEL[i-1];
                                %>
                                <option value="<% out.print(i); %>" <% if(i == selectedUserLevel) out.print("selected"); %>><% out.print(userLevel); %></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-sm btn-default" type="submit">查询</button>
                        </div>
                    </form>
                </div>
                <%--TODO--%>
                <jsp:useBean id="shopOrderDtoList" type="java.util.List<edu.nju.yummy.dto.ShopOrderDto>" scope="request"/>
                <div>共计: <% out.print(shopOrderDtoList.size()); %> 单</div>
                <div class="">
                    <%
                        for(ShopOrderDto orderDto : shopOrderDtoList){
                    %>
                    <div class="tab_shopOrder">
                        <h4><% out.print(orderDto.getOrderid()); %> <small><% out.print(orderDto.getOrderState()); %></small></h4>
                        <div><% out.print(orderDto.getOrderTime()); %></div>
                        <div>
                            <ul>
                                <%
                                    for(CartAndOrderItemDto orderItemDto : orderDto.getOrderItemList()){
                                %>
                                <li>
                                    <% out.print(orderItemDto.getDishName() + " " + "×" + orderItemDto.getNum()); %>
                                    <span>¥<% out.print(orderItemDto.getSum()); %></span>
                                </li>
                                <%
                                    }
                                %>
                            </ul>
                        </div>
                        <div class="row"><label class="col-sm-3">总计: </label> ¥<% out.print(orderDto.getActual()); %></div>
                        <div class="row"><label class="col-sm-3">用户: </label><% out.print(orderDto.getEmail()); %></div>
                        <div class="row"><label class="col-sm-3">送餐地址: </label><% out.print(orderDto.getDeliveryAddress()); %></div>
                        <div class="row"><label class="col-sm-3">预计送餐时间: </label><% out.print(orderDto.getDeliveryTime()); %> 分钟</div>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="statistics">
                <div id="orderStateChart" style="width: 300px; height: 300px; display: inline-block;"></div>
                <div id="userLevelChart" style="width: 300px; height: 300px; display: inline-block;"></div>
                <div id="near7DaysIncomeChart" style="width: 400px; height: 300px; display: inline-block;"></div>
            </div>
        </div>
    </div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-datepicker.min.js"></script>
<script src="js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="js/echarts.js"></script>

<script src="js/shopStatistics.js"></script>
</body>
</html>
