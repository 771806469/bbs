<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/2 0002
  Time: 22:31
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
    <link href="/static/css/sweetalert.css" rel="stylesheet" >
</head>
<body>
<%@include file="../include/adminNavbar.jsp" %>
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userVoPage.pageList}" var="item">
            <tr>
                <td>${item.userName}</td>
                <td>${item.createTime}</td>
                <td>${item.lastLoginTime}</td>
                <td>${item.lastLoginIp}</td>
                <td>
                    <a href="javascript:;" class="update" onClick="update(${item.id},${item.state})"
                       rel="${item.state},${item.id}">${item.state == '1'?'禁用':'恢复'}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${userVoPage.pageCount},
            visiblePages: 5,
            first: '首页',
            last: '末页',
            prev: '上一页',
            next: '下一页',
            href: '?p={{number}}'
        });

    });

    function update(userId, userState) {
        $.post("/admin/user", {"userId": userId, "userState": userState}, function (json) {
            if (json.state == 'success') {
                swal({title:"修改成功"},function () {
                    window.history.go(0);

                });
            } else {
                swal(json.message)
            }
        });
    }
</script>
</body>
</html>

