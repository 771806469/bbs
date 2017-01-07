<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<div>


</div>
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>日期</th>
            <th>新主题数</th>
            <th>新回复数</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items = "${page.pageList}" var="item">
        <tr>
            <td>${item.time}</td>
            <td>${item.topicnum}</td>
            <td>${item.replynum}</td>

        </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination pull-right">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function(){
        $("#pagination").twbsPagination({
            totalPages:${page.pageCount},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}'
        });
    });
</script>
</body>
</html>
