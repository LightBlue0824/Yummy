<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
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
    <title>登录</title>

    <link rel="stylesheet" href="css/login.css">
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
                <li><a href="#">主站</a></li>
                <li><a href="user/index">会员</a></li>
                <li><a href="shop/index">商户</a></li>
                <li><a href="admin/index">管理员</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container" style="padding-top: 60px;">
    <div class="form_container center-block">
        <h2>登录<small>欢迎使用Yummy!</small></h2>
        <form class="form" method="post">
            <div class="form-group">
                <label class="control-label" for="email">邮箱</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
            </div>
            <div class="form-group">
                <label class="control-label" for="password">密码</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="password" required>
            </div>
            <div class="form-group">
                <span id="helpRegisterBlock" class="help-block">没有Yummy账户？</span>
                <a class="btn btn-default pull-left" aria-describedby="helpRegisterBlock" href="user/register">注册</a>
                <button type="submit" class="btn btn-primary pull-right">登录</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
