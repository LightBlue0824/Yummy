<%@ page import="edu.nju.yummy.dto.ModifyShopInfoDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Yummy!-管理员</title>

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
                <li><a href="admin/index">主页</a></li>
                <li><a href="admin/statistics">统计信息</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a><% out.print(session.getAttribute("id")); %></a></li>
                <li><a href="admin/logout">登出</a></li>

            </ul>
        </div>
    </div>
</nav>

<div class="container" style="padding-top: 60px;">
    <%--<a class="btn btn-primary" href="admin/logout">登出</a>--%>
    <%--<a class="btn btn-default" href="admin/statistics">统计信息</a>--%>

    <h3>商户修改信息申请列表</h3>
    <jsp:useBean id="modifyShopInfoDtoList" type="java.util.List<edu.nju.yummy.dto.ModifyShopInfoDto>" scope="request"/>
    <table class="table">
        <thead>
        <tr>
            <th>时间</th>
            <th>商店ID</th>
            <th>名称</th>
            <th>位置</th>
            <th>类型</th>
            <th>简介</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for(ModifyShopInfoDto modifyShopInfoDto : modifyShopInfoDtoList){
        %>
        <tr>
            <td><% out.print(modifyShopInfoDto.getTime()); %></td>
            <td><% out.print(modifyShopInfoDto.getShopid()); %></td>
            <td><% out.print(modifyShopInfoDto.getName()); %></td>
            <td><% out.print("("+modifyShopInfoDto.getX()+", "+modifyShopInfoDto.getY()+")"); %></td>
            <td><% out.print(modifyShopInfoDto.getType()); %></td>
            <td><% out.print(modifyShopInfoDto.getBrief()); %></td>
            <td>
                <form method="post">
                    <input name="modifyShopInfoId" value="<% out.print(modifyShopInfoDto.getModifyId()); %>" hidden>
                    <button class="btn btn-sm btn-primary" formaction="admin/approveModifyShopInfo">同意</button>
                    <button class="btn btn-sm btn-default" formaction="admin/rejectModifyShopInfo">拒绝</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
