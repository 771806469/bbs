<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/19 0019
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>重置密码错误</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 系统提示</span>
        </div>
        <div class="box-padding">
            <h4 style="font-size: 18px">${requestScope.message}</h4>
        </div>
        <div class="form-actions">
            <button id="loginBtn" type="button" class="btn btn-primary">登录</button>

            <a class="pull-right" href="/reg">注册账号</a>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
</body>
</html>
