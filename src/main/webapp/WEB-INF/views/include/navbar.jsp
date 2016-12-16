<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/15 0015
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li>
                        <a href="#">
                            <img src="/static/img/avatra.jpg" width="20" height="20" class="img-circle" alt="">
                        </a>
                    </li>
                    <li>
                        <a href=""><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bell"></i></a>
                    </li>
                    <li>
                        <a href="setting.html"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sign-out"></i></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/setting"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/login"><i class="fa fa-sign-in"></i></a>
                    </li>
                </c:otherwise>
            </c:choose>


        </ul>
    </div>
</div>
