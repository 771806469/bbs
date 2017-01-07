<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/18 0018
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title">找回密码</span>
        </div>

        <form action="" id="form" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">选择找回方式</label>
                <div class="controls">
                    <select name="type" id="type">
                        <option value="email">电子邮件</option>
                        <option value="phone">手机号码</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" id="typename">电子邮件</label>
                <div class="controls">
                    <input type="text" name="value">
                </div>
            </div>
            <div class="form-actions">
                <button id="btn" type="button" class="btn btn-primary">提交</button>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/self/foundpassword.js"></script>
</body>
</html>
