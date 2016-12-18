<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/16 0016
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册用户</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 注册账号</span>
        </div>

        <form id="regForm" action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input name="username" type="text">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input id="password" name="password" type="password">
                </div>
            </div>
            <div class="control-group">
                <label   class="control-label">重复密码</label>
                <div class="controls">
                    <input name="rePassword" type="password">
                </div>
            </div>
            <div class="control-group">
                <label  class="control-label">电子邮件</label>
                <div class="controls">
                    <input name="email" type="text">
                </div>
            </div>
            <div class="control-group">
                <label  class="control-label">电话号码</label>
                <div class="controls">
                    <input name="phone" type="text">
                </div>
            </div>
            <div class="form-actions">
                <button id="regBtn" class="btn btn-primary">注册</button>
                <a href="/home">退出</a>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="/static/js/editer/scripts/jquery.validate.min.js"></script>
<script src="/static/js/self/validate.js"></script>
</body>
</html>