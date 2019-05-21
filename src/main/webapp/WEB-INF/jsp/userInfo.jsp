<%@ page import="edu.nju.yummy.dto.DeliveryAddressDto" %>
<%@ page import="edu.nju.yummy.util.UserLevel" %>
<%@ page import="edu.nju.yummy.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<%--<%@ taglib prefix="mytag" uri="http://mycompany.com" %>--%>
<%--<mytag:checkUserSession/>--%>
<html>
<head>
    <meta charset="utf-8">
    <!-- Bootstrap-->
    <%--<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
    <%--<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>--%>
    <%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>

    <base href='<%=basePath %>' />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="css/userInfo.css">
    <link rel="stylesheet" href="css/main.css">
    <script src="js/userInfo.js"></script>

    <title>个人中心</title>
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
    <div class="form_container my_container center-block">
        <h2>个人信息</h2>

        <h3>基本信息</h3>
        <form class="form-horizontal form_basicInfo" method="post" action="user/modifyUserInfo">
            <jsp:useBean id="userInfoDto" type="edu.nju.yummy.dto.UserInfoDto" scope="request"/>
            <div class="form-group">
                <label class="control-label col-sm-2">Email</label>
                <div class="col-sm-10">
                    <label class="control-label"><jsp:getProperty name="userInfoDto" property="email"/></label>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2">会员等级</label>
                <div class="col-sm-10">
                    <label class="control-label"><jsp:getProperty name="userInfoDto" property="level"/>级</label>
                    <% out.print(UserLevel.USER_LEVEL[userInfoDto.getLevel()-1] + " " + UserLevel.USER_DISCOUNT[userInfoDto.getLevel()-1]); %>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="input_name">姓名</label>
                <div class="col-sm-10">
                    <input class="form-control input" id="input_name" type="text" name="name" value="<jsp:getProperty name="userInfoDto" property="name"/>" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="input_phone">电话</label>
                <div class="col-sm-10">
                    <input class="form-control input" id="input_phone" type="text" name="phone" value="<jsp:getProperty name="userInfoDto" property="phone"/>" readonly>
                </div>
            </div>
            <div class="pull-right">
                <button type="button" class="btn btn-default btn_modify" onclick="modifyUserInfo()">修改</button>
                <a type="button" class="btn btn-danger btn_cancel" href="user/info" style="display: none;">取消</a>
                <button type="submit" class="btn btn-success btn_submit" style="display: none;">完成</button>
            </div>
        </form>
    </div>
    <div class="clearfix"></div>
    <div class="my_container center-block">
        <h3>送餐地址</h3>
        <jsp:useBean id="addressDtoList" type="java.util.List<edu.nju.yummy.dto.DeliveryAddressDto>" scope="request"/>
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>姓名</th>
                <th>电话</th>
                <th>地址</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%
                for(int i = 0; i < addressDtoList.size(); i ++){
                    DeliveryAddressDto temp = addressDtoList.get(i);
            %>
            <tr>
                <td><% out.print(i+1); %></td>
                <td><% out.print(temp.getName()); %></td>
                <td><% out.print(temp.getPhone()); %></td>
                <td><% out.print("("+temp.getX()+", "+temp.getY()+")"); %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-default pull-right" data-toggle="modal" data-target="#myModal">
            新增地址 +
        </button>

        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">新增送餐地址</h4>
                    </div>
                    <form class="form" method="post" action="user/addDeliveryAddress">
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="control-label" for="addInput_name">姓名</label>
                                <input class="form-control" id="addInput_name" name="name" required>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="addInput_phone">电话</label>
                                <input class="form-control" id="addInput_phone" name="phone" required>
                            </div>
                            <div>
                                <label class="control-label">地址</label>
                            </div>
                            <canvas id="cvs" width="260" height="240"></canvas>
                            <label>所选位置：<span class="address_selected"></span></label>
                            <input type="number" name="x" id="addInput_x" value="0" hidden required>
                            <input type="number" name="y" id="addInput_y" value="0" hidden required>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="my_container">
        <h3>
            支付密码<small>Tip: 新注册账号需要设置支付密码后才能支付</small>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-default pull-right" data-toggle="modal" data-target="#modifyPayPasswordModal">
                修改支付密码
            </button>
        </h3>
        <!-- Modal -->
        <div class="modal fade" id="modifyPayPasswordModal" tabindex="-1" role="dialog" aria-labelledby="modifyPayPasswordModalLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modifyPayPasswordModalLabel">修改支付密码</h4>
                    </div>
                    <form class="form" method="post" action="user/modifyPayPassword">
                        <div class="modal-body">
                            <%
                                if(userInfoDto.isHasPayPassword()){
                            %>
                            <div class="form-group">
                                <label class="control-label" for="oldPassword">原密码</label>
                                <input class="form-control" type="password" id="oldPassword" name="oldPassword">
                            </div>
                            <% } %>
                            <div class="form-group">
                                <label class="control-label" for="newPassword">新密码</label>
                                <input class="form-control" type="password" id="newPassword" name="newPassword" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确定</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="clearfix"></div>
        <h3>
            注销账户<small class="text-danger">! 注销后不可恢复</small>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-danger pull-right" data-toggle="modal" data-target="#sureToDestroyModal">
                注销账户
            </button>
        </h3>
        <!-- Modal -->
        <div class="modal fade" id="sureToDestroyModal" tabindex="-1" role="dialog" aria-labelledby="sureToDestroyModalLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="sureToDestroyModalLabel">注销账户</h4>
                    </div>
                    <div class="modal-body">
                        <p class="text-danger">注销后不可恢复, 且该邮箱不能再注册! !</p>
                        <p>确认注销吗? </p>
                    </div>
                    <div class="modal-footer">
                        <form class="form" method="post" action="user/destroy">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确定</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/coordinate.js"></script>

</body>
</html>
