<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/21 0021
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>凯盛IT-${topic.title}</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/highlight/agate.css">
    <link rel="stylesheet" href="/static/css/simditor-emoji.css">
    <style>
        body {
            background-image: url(/static/img/bg.jpg);
        }
        .topic-body img {
            width: 200px;
        }

        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<!--header-bar end-->
<c:choose>
    <c:when test="${not empty requestScope.topic}">
        <div class="alert alert-error">${requestScope.error}</div>
        <div class="container">
        <div class="box">

            <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
                <li><a href="/home">首页</a> <span class="divider">/</span></li>
                <li class="active">${requestScope.nodeName}</li>
            </ul>
            <div class="topic-head">
                <img class="img-rounded avatar"
                     src="http://ohwtqwe8j.bkt.clouddn.com/${requestScope.userAvatar}?imageView2/1/w/60/h/60"
                     alt="">
                <h3 class="title">${requestScope.topic.title}</h3>
                <p class="topic-msg muted"><a href="">${requestScope.userName}</a> · <span id="topicTime"></span></p>
            </div>
            <div class="topic-body">
                    ${requestScope.topic.content}
            </div>
            <div class="topic-toolbar">
                <ul class="unstyled inline pull-left">
                    <li><a href="">加入收藏</a></li>
                    <li><a href="">感谢</a></li>
                    <li><a href=""></a></li>
                </ul>
                <ul class="unstyled inline pull-right muted">
                    <li>${requestScope.topic.clickNum}次点击</li>
                    <li>${requestScope.topic.favNum}人收藏</li>
                    <li>${requestScope.topic.thanksNum}人感谢</li>
                </ul>
            </div>
        </div>
        <!--box end-->
        <div class="box" style="margin-top:20px;">
            <div class="talk-item muted" style="font-size: 12px">
                    ${fn:length(replyList)}个回复 |最近回复时间：<span id="lastReplyTime"></span>
            </div>
            <c:forEach items="${replyList}" var="reply" varStatus="vs">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar" src="${reply.user.avatar}?imageView2/1/w/50/h/50" alt="用户头像">
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px" class="reply">${reply.createTime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${vs.count}楼</span>
                        </td>
                    </tr>
                </table>
            </div>
            </c:forEach>
        </div>
        <c:choose>
            <c:when test="${not empty sessionScope.curr_user}">
                <div class="box" style="margin:20px 0px;">
                    <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                    <form id="replyForm" action="/reply" method="post" style="padding: 15px;margin-bottom:0px;">
                        <input type="hidden" name="topicId" value="${topic.id}">
                        <textarea name="content" id="editor"></textarea>
                    </form>
                    <div class="talk-item muted" style="text-align: right;font-size: 12px">
                        <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                        <button id="replyBtn" class="btn btn-primary">发布</button>
                    </div>
                </div>
                </div>
                <a name="reply"></a>
            </c:when>
            <c:otherwise>
                <div class="box" style="margin:10px 0px;">
                    <div class="talk-item"> 请<a href="/login?redirect=/topicdetail?topicId=${topic.id}#reply">登录</a>后再回复</div>
                </div>
            </c:otherwise>
        </c:choose>

    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="box">
                <div class="box-header">
                    <span class="title"><i class="fa fa-sign-in"></i> 系统提示</span>
                </div>
                <div class="box-padding">
                    <h4 style="font-size: 18px">${requestScope.message}</h4>
                </div>
                <div class="form-actions">
                    <a class="pull-right" href="/home">回到首页</a>
                </div>
            </div>
            <!--box end-->
        </div>
    </c:otherwise>
</c:choose>


<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/simditor-emoji.js"></script>
<script src="/static/js/highlight.pack.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<script></script>
<script>
    $(function () {

<c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            var editor = new Simditor({
                textarea: $('#editor'),
                toolbar: ['emoji'],
                //optional options
                emoji: {
                    imagePath: "/static/img/emoji/",
                    images: ['smile.png', 'smiley.png', 'laughing.png', 'blush.png', 'heart_eyes.png', 'smirk.png', 'flushed.png', 'grin.png', 'wink.png', 'kissing_closed_eyes.png', 'stuck_out_tongue_winking_eye.png', 'stuck_out_tongue.png', 'sleeping.png', 'worried.png', 'expressionless.png', 'sweat_smile.png', 'cold_sweat.png', 'joy.png', 'sob.png', 'angry.png', 'mask.png', 'scream.png', 'sunglasses.png', 'heart.png', 'broken_heart.png', 'star.png', 'anger.png', 'exclamation.png', 'question.png', 'zzz.png', 'thumbsup.png', 'thumbsdown.png', 'ok_hand.png', 'punch.png', 'v.png', 'clap.png', 'muscle.png', 'pray.png', 'skull.png', 'trollface.png'],
                }
            });
        </c:when>
        </c:choose>

        var topicTime = moment("${topic.createTime}");
        var lastReplyTime = moment("${topic.lastReplyTime}");
        $("#topicTime").text(topicTime.fromNow());
        $("#lastReplyTime").text(lastReplyTime.format("yyyy年mm月dd日 hh:mm:ss"));
        $(".reply").text(function () {
            var time = moment($(this).text());
            return time.fromNow();
        });

        $("#replyBtn").click(function () {

            $("#replyForm").submit();
        });


//        $("#replyForm").validate({
//            errorElement : "span",
//            errorClass : "text-error",
//            rules : {
//                content : {
//                    required : true,
//                    maxlength : 200
//                }
//            },
//            messages : {
//                content : {
//                    required : "请输入回复内容",
//                    maxlength : "最多回复200个字符"
//                }
//            },
//            submitHandler : function (form) {
//                $.ajax({
//                    url : "/reply",
//                    type : "post",
//                    data : $(form).serialize(),
//                    beforeSend : function () {
//                        $("#replyBtn").html("发布中<img src='/static/img/loding.gif'>").attr("disabled,disabled");
//                    },
//                    success : function (data) {
//                        if(data.state == 'success') {
//
//                        } else {
//
//                        }
//                    },
//                    error : function () {
//                        alert("服务器访问错误！");
//                    },
//                    complete : function () {
//                        $("#replyBtn").html("发布").removeAttr("disabled");
//                    }
//                });
//            }
//        });
//
//        hljs.initHighlightingOnLoad();
        });
</script>

</body>
</html>
