<%@ page import="edu.nju.yummy.dto.ModifyShopInfoDto" %>
<%@ page import="edu.nju.yummy.util.ShopType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<%--<%@ taglib prefix="mytag" uri="http://mycompany.com" %>--%>
<%--<mytag:checkShopSession/>--%>

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

    <link rel="stylesheet" href="css/main.css">

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
    <jsp:useBean id="shopInfoDto" type="edu.nju.yummy.dto.ShopInfoDto" scope="request"/>
    <div class="my_container center-block">
        <h2>商户信息</h2>

        <h3>基本信息</h3>
        <div class="row">
            <label class="col-sm-2">识别码</label>
            <div class="col-sm-10"><jsp:getProperty name="shopInfoDto" property="shopId"/></div>
        </div>
        <div class="row">
            <label class="col-sm-2">名称</label>
            <div class="col-sm-10"><jsp:getProperty name="shopInfoDto" property="name"/></div>
        </div>
        <div class="row">
            <label class="col-sm-2">地点</label>
            <div class="col-sm-10"><% out.print("("+shopInfoDto.getX()+", "+shopInfoDto.getY()+")"); %></div>
        </div>
        <div class="row">
            <label class="col-sm-2">类型</label>
            <div class="col-sm-10"><jsp:getProperty name="shopInfoDto" property="type"/></div>
        </div>
        <div class="row">
            <label class="col-sm-2">简介</label>
            <div class="col-sm-10"><jsp:getProperty name="shopInfoDto" property="brief"/></div>
        </div>
        <!-- Button trigger modal -->
        <%
            if(shopInfoDto.getModifyShopInfoDto() != null){
        %>
        <div class="row">
            <label class="col-sm-2">状态</label>
            <div class="col-sm-10">审核中</div>
        </div>
        <button type="button" class="btn btn-default pull-right" data-toggle="modal" data-target="#modifyDetailModal">
            查看修改申请
        </button>
        <%
            } else {
        %>
        <button type="button" class="btn btn-warning pull-right" data-toggle="modal" data-target="#myModal">
            修改
        </button>
        <%
            }
        %>
    </div>
    <!-- Modal -->
    <%
        if (shopInfoDto.getModifyShopInfoDto() == null){
    %>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改商户信息</h4>
                    <label>修改商户信息需要总经理审批后才生效</label>
                </div>
                <form class="form" method="post" action="shop/modifyShopInfo">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label" for="name">名称</label>
                            <input class="form-control" id="name" name="name" value="<jsp:getProperty name="shopInfoDto" property="name"/>" required>
                        </div>
                        <%--老地点在最后的script中画出--%>
                        <div>
                            <label class="control-label">地址</label>
                        </div>
                        <canvas id="cvs" width="260" height="240"></canvas>
                        <label>所选位置：<span class="address_selected"></span></label>
                        <input type="number" name="x" id="addInput_x" value="<jsp:getProperty name="shopInfoDto" property="x"/>" hidden required>
                        <input type="number" name="y" id="addInput_y" value="<jsp:getProperty name="shopInfoDto" property="y"/>" hidden required>
                        <div class="form-group">
                            <label class="control-label" for="type">类型</label>
                            <select class="form-control" id="type" name="type" required>
                                <%
                                    for(String shopType : ShopType.shopTypes){
                                %>
                                <option><% out.print(shopType); %></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="brief">简介</label>
                            <textarea id="brief" name="brief" class="form-control" rows="3" placeholder="brief"><jsp:getProperty name="shopInfoDto" property="brief"/></textarea>
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
    <%
        } else {
    %>
    <div class="modal fade" id="modifyDetailModal" tabindex="-1" role="dialog" aria-labelledby="modifyDetailModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="modifyDetailModalLabel">已申请的修改商户信息</h4>
                    <label>修改商户信息需要总经理审批后才生效</label>
                </div>
                <div class="modal-body">
                    <% ModifyShopInfoDto modifyShopInfoDto = shopInfoDto.getModifyShopInfoDto(); %>
                    <div class="row">
                        <label class="col-sm-2">名称</label>
                        <div class="col-sm-10"><% out.print(modifyShopInfoDto.getName()); %></div>
                    </div>
                    <div class="row">
                        <label class="col-sm-2">地点</label>
                        <div class="col-sm-10"><% out.print("("+modifyShopInfoDto.getX()+", "+modifyShopInfoDto.getY()+")"); %></div>
                    </div>
                    <div class="row">
                        <label class="col-sm-2">种类</label>
                        <div class="col-sm-10"><% out.print(modifyShopInfoDto.getType()); %></div>
                    </div>
                    <div class="row">
                        <label class="col-sm-2">简介</label>
                        <div class="col-sm-10"><% out.print(modifyShopInfoDto.getBrief()); %></div>
                    </div>
                    <div class="row">
                        <label class="col-sm-2">状态</label>
                        <div class="col-sm-10">审核中</div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
                </div>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>

<script src="js/coordinate.js"></script>
<script>
    drawPoint({x: <jsp:getProperty name="shopInfoDto" property="x"/>+20, y: <jsp:getProperty name="shopInfoDto" property="y"/>});
    $('#type').val('<jsp:getProperty name="shopInfoDto" property="type"/>');
</script>

</body>
</html>
