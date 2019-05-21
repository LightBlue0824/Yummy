<%--
  Created by IntelliJ IDEA.
  User: 49869
  Date: 2019/2/26
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
%>

<html>
<jsp:useBean id="infoDto" type="edu.nju.yummy.dto.InfoDto" scope="request"/>
<head>
    <base href='<%=basePath %>' />

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <title><jsp:getProperty name="infoDto" property="infoType"/></title>
</head>
<body>
<div class="container">
    <div class="text-center">
        <h1>Yummy!</h1>
        <h2><jsp:getProperty name="infoDto" property="infoType"/></h2>
        <label><jsp:getProperty name="infoDto" property="info"/></label>
        <p><jsp:getProperty name="infoDto" property="comment"/></p>
        <a href="<% out.print(request.getContextPath()); %>" class="btn btn-default">返回主页</a>
        <%--<button class="btn btn-default" onclick="window.history.back();">返回</button>--%>
        <button class="btn btn-default" onclick="window.location.href=document.referrer;">返回</button>
    </div>
</div>
</body>
</html>
