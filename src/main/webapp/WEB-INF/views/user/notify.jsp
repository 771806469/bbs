<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/27 0027
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>凯盛IT-通知中心</title>
    <link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-bell"></i> 通知中心</span>
        </div>
        <button id="markBtn" style="margin-left: 8px;" disabled class="btn btn-info">标记为已读</button>
        <table class="table">
            <thead>
            <tr>
                <th width="30">
                    <c:if test="${not empty notifyList}"><input type="checkbox" id="ckFather"></th>
                </c:if>
                <th width="200">发布日期</th>
                <th>内容</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty notifyList}">
                    <c:forEach items="${notifyList}" var="notify">
                        <c:choose>
                            <c:when test="${notify.state == 1}">
                                <tr class="" style="text-decoration: line-through">
                                    <td></td>
                                    <td>${notify.createTime}</td>
                                    <td>${notify.content}</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td><input value="${notify.id}" type="checkbox" class="ckSon"></td>
                                    <td>${notify.createTime}</td>
                                    <td>${notify.content}</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="3"><p>暂无消息</p></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/self/notify.js"></script>
<script>
    $(function () {
        $("#markBtn").click(function () {
            var sons = $(".ckSon");
            var ids = [];
            for(var i = 0;i < sons.length;i++) {
                if(sons[i].checked == true) {
                    ids.push(sons[i].value);
                }
            }

            $.post("/notifyread",{"ids" : ids.join(",")},function (json) {
                if(json.state == 'success') {
                    window.history.go(0);
                }
            });
            
        });
        $("#ckFather").click(function () {
            var sons = $(".ckSon");
            for(var i = 0;i < sons.length;i++){
                sons[i].checked = $("#ckFather")[0].checked;
            }

            if($(this)[0].checked == true) {
                $("#markBtn").removeAttr("disabled");
            } else {
                $("#markBtn").attr("disabled","disabled");
            }

        });

        $(".ckSon").click(function () {
            var sons = $(".ckSon");
            var num = 0;
            for(var i = 0;i < sons.length;i++) {
                if(sons[i].checked) {
                    num++;
                }
            }
            if(num > 0) {
                $("#markBtn").removeAttr("disabled");
            } else {
                $("#ckFather")[0].checked =false;
                $("#markBtn").attr("disabled","disabled");
            }

            if(num == sons.length) {
                $("#ckFather")[0].checked = true;
            } else {
                $("#ckFather")[0].checked = false;
            }
        });



    });
</script>
</body>
</html>
