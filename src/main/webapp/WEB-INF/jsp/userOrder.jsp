<%@ page import="edu.nju.yummy.dto.UserOrderDto" %>
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
    <link rel="stylesheet" href="css/userOrder.css">
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

<jsp:useBean id="userOrderDtoList" type="java.util.List<edu.nju.yummy.dto.UserOrderDto>" scope="request"/>
<div class="container" style="padding-top: 60px;">
    <div class="my_container">
        <h3>我的订单</h3>
        <div class="order_container center-block">
            <%
                for(UserOrderDto userOrderDto : userOrderDtoList){
            %>
            <div class="tab_order">
                <div class="img_container pull-left">
                    <a class="thumbnail pull-left" href="user/orderDetail?orderid=<% out.print(userOrderDto.getOrderid()); %>">
                        <img src="image/yummy.jpg" alt="">
                    </a>
                </div>
                <div class="caption">
                    <a class="name" href="user/shopDetail?shopid=<% out.print(userOrderDto.getShopid()); %>"><label><% out.print(userOrderDto.getShopName()); %></label></a>
                    <span class="pull-right"><small><% out.print(userOrderDto.getOrderState()); %></small></span>
                    <p class="orderTime"><% out.print(userOrderDto.getOrderTime()); %></p>
                    <p class="briefAndPrice">
                        <span class="brief"><% out.print(userOrderDto.getBrief());%></span>
                        <%
                            if(userOrderDto.getItemNum() > 1){
                        %>
                        <span class="num">等<% out.print(userOrderDto.getItemNum()); %>件商品</span>
                        <%
                            }
                        %>
                        <span class="price pull-right">¥<% out.print(userOrderDto.getActual()); %></span>
                    </p>
                    <%
                        if(userOrderDto.getOrderState().equals("已支付")){
                    %>
                    <span>预计 <strong><% out.print(userOrderDto.getDeliveryTime()); %></strong> 分钟后送达</span>
                    <%--sureToFinishModal--%>
                    <div class="modal fade" id="sureToFinishModal" tabindex="-1" role="dialog" aria-labelledby="sureToFinishModalLabel">
                        <div class="modal-dialog modal-sm" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="sureToFinishModalLabel">取消订单</h4>
                                </div>
                                <div class="modal-body">
                                    <label>确认订单已送达？</label>
                                </div>
                                <div class="modal-footer">
                                    <form method="post" action="user/sureToFinishOrder">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                        <input name="orderid" value="<% out.print(userOrderDto.getOrderid()); %>" hidden>
                                        <button type="submit" class="btn btn-primary">确认</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                    <div class="pull-right">
                        <a class="btn btn-sm btn-default" href="user/orderDetail?orderid=<% out.print(userOrderDto.getOrderid()); %>">详情</a>
                        <%
                            if(userOrderDto.getOrderState().equals("已支付")){
                        %>
                        <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#sureToFinishModal">确认送达</button>
                        <%
                            }
                        %>
                    </div>
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
