<%@ page import="edu.nju.yummy.dto.CartAndOrderItemDto" %>
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
    <title>Yummy!-支付</title>

    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/orderDetail.css">
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

<jsp:useBean id="orderDetailDto" type="edu.nju.yummy.dto.OrderDetailDto" scope="request"/>
<div class="container" style="padding-top: 60px;">
    <div class="my_container center-block">
        <%
            String orderState = orderDetailDto.getState();
            if(orderState.equals("未支付")){
        %>
        <h3 class="text-success">已生成订单
            <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#payModal" onclick="loadBalance()">支付</button>
        </h3>
        <label>请在15分钟内进行支付，超时自动取消订单</label>
        <!-- Modal -->
        <div class="modal fade" id="payModal" tabindex="-1" role="dialog" aria-labelledby="payModalLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">支付</h4>
                    </div>
                    <form method="post" action="user/pay">
                        <div class="modal-body">
                            <%
                                if(orderDetailDto.isHasPayPassword()){
                            %>
                            <label class="control-label">账户余额: </label> <span id="balance">loading...</span>
                            <input name="orderid" value="<jsp:getProperty name="orderDetailDto" property="orderid"/>" hidden>
                            <div class="form-group">
                                <label class="control-label" for="payPassword">支付密码</label>
                                <input class="form-control" type="password" id="payPassword" name="payPassword" required>
                            </div>
                            <%
                                }
                                else{
                            %>
                            <div>
                                <label class="text-warning">请先设置支付密码</label>
                                <div><a class="btn btn-primary" href="user/info">前往设置</a></div>
                            </div>
                            <%
                                }
                            %>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <%
                                if(orderDetailDto.isHasPayPassword()){
                            %>
                            <button type="submit" class="btn btn-primary">支付</button>
                            <%
                                }
                            %>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%
            } else if(orderState.equals("已取消")){
        %>
        <h3 class="text-danger">已取消</h3>
        <%
            } else if(orderState.equals("已支付")){
        %>
        <h3 class="text-success">已支付</h3>
        <p>等待商品送达</p>
        <label>预计送达: </label> <span><jsp:getProperty name="orderDetailDto" property="deliveryTime"/>分钟</span>
        <div class="pull-right">
            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#sureToCancelModal">取消订单</button>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#sureToFinishModal">确认送达</button>
        </div>
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
                            <input name="orderid" value="<jsp:getProperty name="orderDetailDto" property="orderid"/>" hidden>
                            <button type="submit" class="btn btn-primary">确认</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%--sureToCancelModal--%>
        <div class="modal fade" id="sureToCancelModal" tabindex="-1" role="dialog" aria-labelledby="sureToCancelModalLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="sureToCancelModalLabel">取消订单</h4>
                    </div>
                    <div class="modal-body">
                        <label class="text-danger">取消订单会根据时间返还不同比例的金额</label>
                        <p><small>* 未超过配送时间的10%，返还所有金额<br>* 未超过配送时间的30%，返还金额的80%<br>* 超过配送时间的30%，不返还</small></p>
                    </div>
                    <div class="modal-footer">
                        <form method="post" action="user/cancel">
                            <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
                            <input name="orderid" value="<jsp:getProperty name="orderDetailDto" property="orderid"/>" hidden>
                            <button type="submit" class="btn btn-danger">确定</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%
            } else if (orderState.equals("已送达")){
        %>
        <h3 class="text-success">已送达</h3>
        <p>感谢您使用Yummy! 祝您用餐愉快:)</p>
        <%
            }
        %>
        <br>
        <h4><jsp:getProperty name="orderDetailDto" property="shopName"/></h4>
        <table class="table table_orderItem">
            <thead>
            <tr>
                <th></th>
                <th>商品名</th>
                <th>数量</th>
                <th>单价</th>
                <th>总价</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<CartAndOrderItemDto> orderItemDtoList = orderDetailDto.getOrderItemDtoList();
                for(CartAndOrderItemDto orderItemDto : orderItemDtoList){
            %>
            <tr>
                <td class="td_img">
                    <div class="img_container">
                        <a class="thumbnail pull-left">
                            <img src="image/yummy.jpg" alt="">
                        </a>
                    </div>
                </td>
                <td><% out.print(orderItemDto.getDishName()); %></td>
                <td>
                    <span>×<% out.print(orderItemDto.getNum()); %></span>
                </td>
                <%
                    if(orderItemDto.getActual() != -1){
                %>
                <td><small><del>￥<% out.print(orderItemDto.getPrice()); %></del></small> ￥<% out.print(orderItemDto.getActual()); %></td>                <%
                } else {
                %>
                <td>￥<% out.print(orderItemDto.getPrice()); %></td>
                <%
                    }
                %>
                <td><% out.print(orderItemDto.getSum()); %></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <div class="pull-right">
            <label>总计：<span><jsp:getProperty name="orderDetailDto" property="total"/></span></label><br>
            <label>实付：<span><jsp:getProperty name="orderDetailDto" property="actual"/></span></label>
        </div>
        <div class="clearfix"></div>
        <div>
            <h4>配送信息</h4>
            <label>送餐地址: </label> <span><jsp:getProperty name="orderDetailDto" property="deliveryAddress"/></span><br>
            <%
                if(orderDetailDto.getFinishTime() != null){
            %>
            <label>送达时间: </label> <span><jsp:getProperty name="orderDetailDto" property="finishTime"/></span><br>
            <%
                }
            %>
        </div>
        <br>
        <div>
            <h4>订单信息</h4>
            <label>订单编号: </label> <span><jsp:getProperty name="orderDetailDto" property="orderid"/></span><br>
            <label>下单时间: </label> <span><jsp:getProperty name="orderDetailDto" property="orderTime"/></span><br>
            <%
                if(orderDetailDto.getPayTime() != null){
            %>
            <label>支付时间: </label> <span><jsp:getProperty name="orderDetailDto" property="payTime"/></span><br>
            <%
                }
            %>
        </div>
    </div>
</div>

<script src="js/orderDetail.js"></script>

</body>
</html>
