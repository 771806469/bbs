<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="include/navbar.jsp" %>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="talk-item">
            <ul class="topic-type unstyled inline" style="margin-bottom:0px;">
                <li class="${empty param.nodeId?'active':''}"><a href="/home">全部</a></li>
                <c:forEach items="${nodeList}" var="node">
                    <li class="${node.id == param.nodeId ? 'active' : ''}"><a
                            href="/home?nodeId=${node.id}">${node.nodeName}</a></li>
                </c:forEach>
            </ul>
        </div>
        <c:forEach items="${page.pageList}" var="topic">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50" height="50">
                            <img class="avatar" src="http://ohwtqwe8j.bkt.clouddn.com/${topic.user.avatar}?imageView2/1/w/40/h/40" width="50"
                                 height="50" alt="">
                        </td>
                        <td width="80">
                            <a href="">${topic.user.username}</a>
                        </td>
                        <td width="auto">
                            <a href="/topicdetail?topicId=${topic.id}">${topic.title}</a>
                        </td>
                        <td width="50" align="center">
                            <span class="badge">${topic.replyNum}</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>

        <div class="pagination pagination-mini pagination-centered">
            <ul id="pagination" style="margin-bottom:20px;"></ul>
        </div>
    </div>
    <!--box end-->
</div>
<!--container end-->
<div class="footer">
    <div class="container">
        Copyright © 2016 kaishengit
    </div>
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script src="/static/js/self/notify.js"></script>
<script>

    $(function () {
        <c:if test="${page.pageCount > 1}">
            $("#pagination").twbsPagination({
                totalPages: ${page.pageCount},
                visiblePages: 5,
                first: "首页",
                last: "末页",
                prev: "上一页",
                next: "下一页",
                href: '?p={{number}}&nodeId=${param.nodeId}'
            });
        </c:if>
    });

</script>
</body>
</html>
