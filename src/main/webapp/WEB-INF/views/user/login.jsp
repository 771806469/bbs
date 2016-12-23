<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/16 0016
  Time: 9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 登录</span>
        </div>

        <c:choose>
            <c:when test="${not empty requestScope.logout}">
                <div class="alert alert-success">
                        ${requestScope.logout}
                </div>


            </c:when>
            <c:when test="${not empty param.redirect}">
                <div id="redirectURL" class="alert alert-error">
                        请登录后再操作
                </div>

            </c:when>
        </c:choose>

                <form action="" id="loginForm" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label">账号</label>
                        <div class="controls">
                            <input name="username" type="text">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">密码</label>
                        <div class="controls">
                            <input name="password" type="password">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <a href="/foundpassword">忘记密码</a>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button id="loginBtn" type="button" class="btn btn-primary">登录</button>

                        <a class="pull-right" href="/reg">注册账号</a>
                    </div>

                </form>




    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="/static/js/editer/scripts/jquery.validate.min.js"></script>
<script src="/static/js/self/login.js"></script>
</body>
</html>
