<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/23 0023
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 系统提示</span>
        </div>
        <div class="box-padding">
            <c:choose>
                <c:when test="${not empty requestScope.error}">
                    <h4 style="font-size: 18px">${requestScope.error}</h4>
                </c:when>
            </c:choose>
        </div>
        <div class="form-actions">
            <a class="pull-right" href="/home">回到首页</a>
        </div>
    </div>
    <!--box end-->
</div>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>
