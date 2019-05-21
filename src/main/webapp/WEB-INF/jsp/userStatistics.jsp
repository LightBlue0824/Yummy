<%@ page import="edu.nju.yummy.util.ShopType" %>
<%@ page import="edu.nju.yummy.dto.UserOrderDto" %>
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

    <title>统计 - 会员</title>
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
    <h3>会员统计信息</h3>
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
                            <label>订单分类: </label>
                            <select class="form-control" name="orderType">
                                <option value="" <% if(request.getParameter("orderType") == null || request.getParameter("orderType").equals("")) out.print("selected"); %>>全部</option>
                                <option value="点餐" <% if("点餐".equals(request.getParameter("orderType"))) out.print("selected"); %>>点餐</option>
                                <option value="退订" <% if("退订".equals(request.getParameter("orderType"))) out.print("selected"); %>>退订</option>
                            </select>
                        </div>
                        <div class="form-group" >
                            <label class="control-label">商家分类: </label>
                            <select class="form-control" name="shopType">
                                <option value="" selected>全部</option>
                                <%
                                    String selectedShopType = request.getParameter("shopType");
                                    for(String shopType : ShopType.shopTypes){
                                %>
                                <option value="<% out.print(shopType); %>" <% if(shopType.equals(selectedShopType)) out.print("selected"); %>><% out.print(shopType); %></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <br>
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
                        <div class="form-group">
                            <button class="btn btn-sm btn-default" type="submit">查询</button>
                        </div>
                    </form>
                </div>
                <jsp:useBean id="userOrderDtoList" type="java.util.List<edu.nju.yummy.dto.UserOrderDto>" scope="request"/>
                <div>共计: <% out.print(userOrderDtoList.size()); %> 单</div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>时间</th>
                        <th>商户名</th>
                        <th>内容</th>
                        <th>状态</th>
                        <th>价格</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for(UserOrderDto userOrderDto : userOrderDtoList){
                    %>
                    <tr>
                        <td><% out.print(userOrderDto.getOrderTime()); %></td>
                        <td><% out.print(userOrderDto.getShopName()); %></td>
                        <td>
                            <span class="brief"><% out.print(userOrderDto.getBrief());%></span>
                            <%
                                if(userOrderDto.getItemNum() > 1){
                            %>
                            <span class="num">等<% out.print(userOrderDto.getItemNum()); %>件商品</span>
                            <%
                                }
                            %>
                        </td>
                        <td><% out.print(userOrderDto.getOrderState()); %></td>
                        <td>¥<% out.print(userOrderDto.getActual()); %></td>
                        <td><a class="btn btn-sm btn-default" href="user/orderDetail?orderid=<% out.print(userOrderDto.getOrderid()); %>">详情</a></td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
            <div role="tabpanel" class="tab-pane" id="statistics">
                <div id="orderStateChart" style="width: 300px; height: 300px; display: inline-block;"></div>
                <div id="shopTypeChart" style="width: 300px; height: 300px; display: inline-block;"></div>
                <div id="near7DaysOrderNum" style="width: 400px; height: 300px; display: inline-block;"></div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-datepicker.min.js"></script>
<script src="js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="js/echarts.js"></script>

<script src="js/userStatistics.js"></script>
</html>
