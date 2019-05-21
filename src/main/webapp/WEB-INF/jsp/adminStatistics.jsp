<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>
<html>
<head>
    <meta charset="utf-8">
    <base href='<%=basePath %>' />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-datepicker.min.css">

    <link rel="stylesheet" href="css/main.css">

    <title>统计 - Yummy!</title>
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
    <h2>Yummy! 统计信息</h2>
    <div>
        <h3>概览</h3>
        <div class="row">
            <label class="col-sm-3">餐厅总数</label>
            <div class="col-sm-9">${shopNum}</div>
        </div>
        <div class="row">
            <label class="col-sm-3">会员总数</label>
            <div class="col-sm-9">${userNum}</div>
        </div>
        <div class="row">
            <label class="col-sm-3">总收入</label>
            <div class="col-sm-3">${totalIncome}</div>
            <label class="col-sm-3">本月收入</label>
            <div class="col-sm-3">${monthIncome}</div>
        </div>
    </div>
    <div>
        <h3>图表</h3>
        <div id="userLevelChart" style="width: 400px; height: 400px; display: inline-block;"></div>
        <div id="shopTypeChart" style="width: 400px; height: 400px; display: inline-block;"></div>
        <div id="near7DaysIncomeChart" style="width: 500px; height: 400px; display: inline-block;"></div>
    </div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/echarts.js"></script>

<script src="js/adminStatistics.js"></script>

</body>
</html>
