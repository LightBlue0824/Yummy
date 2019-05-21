<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<html>
<head>
    <base href="<%=basePath %>" />
    <!-- Bootstrap-->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <title>Yummy!</title>
</head>
<body>
<div class="container">
    <h2>欢迎使用Yummy!</h2>
    <a class="btn btn-primary btn-lg" href="user/index">会员</a>
    <a class="btn btn-lg btn-info" href="shop/index">商户</a>
    <a class="btn btn-lg btn-default" href="admin/index">管理员</a>
</div>
</body>
</html>
