<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/20 0020
  Time: 23:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/simditor-emoji.css">
    <link rel="stylesheet" href="/static/css/highlight/agate.css">
    <style>

        #sendForm img {
            width: 120px;
        }

    </style>
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form action="" id="sendForm" style="padding: 20px">
            <label class="control-label">主题标题</label>
            <input name="title" id="title" type="text" style="width: 100%;box-sizing: border-box;height: 30px"
                   placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="content" class="content" id="editor"></textarea>

            <select name="node" id="node" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                    <option value="${node.id}">${node.nodeName}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn" class="btn btn-primary">发布主题</button>
        </div>


        <!--box end-->
    </div>
    <!--container end-->
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/simditor-emoji.js"></script>
<script src="/static/js/highlight.pack.js"></script>
<script src="/static/js/self/notify.js"></script>
<%--<script src="/static/js/self/topic/newtopic.js"></script>--%>

<script>
    $(function () {
        var editor = new Simditor({
            textarea: $('#editor'),
            //optional options
            toolbar: ['title','bold','italic','underline','strikethrough',
                'fontScale','color','ol' ,'ul', 'blockquote','code',
                'table','image','emoji'],
            emoji : {
                imagePath: '/static/img/emoji/',
                images:['smile.png' ,'smiley.png' ,'laughing.png' ,'blush.png', 'heart_eyes.png' ,'smirk.png','flushed.png' ,'grin.png' ,'wink.png' ,'kissing_closed_eyes.png' ,'stuck_out_tongue_winking_eye.png' ,'stuck_out_tongue.png' ,'sleeping.png' ,'worried.png' ,'expressionless.png' ,'sweat_smile.png' ,'cold_sweat.png' ,'joy.png' ,'sob.png' ,'angry.png' ,'mask.png' ,'scream.png' ,'sunglasses.png' ,'heart.png' ,'broken_heart.png' ,'star.png' ,'anger.png' ,'exclamation.png' ,'question.png' ,'zzz.png' ,'thumbsup.png' ,'thumbsdown.png' ,'ok_hand.png' ,'punch.png' ,'v.png' ,'clap.png', 'muscle.png' ,'pray.png' ,'skull.png' ,'trollface.png'],
            },
            upload : {
                url :'http://up-z1.qiniu.com/',
                params : {"token" :"${token}"},
                fileKey: "file"
            }
        });

        hljs.initHighlightingOnLoad();

        $("#sendBtn").click(function () {
            $("#sendForm").submit();
        });

        $("#sendForm").validate({
            errorElement : "span",
            errorClass : "text-error",
            rules : {
                title : {
                    required : true,
                    maxlength : 100
                },
                node : {
                    required : true,
                }
            },
            messages : {
                title : {
                    required : "请输入标题",
                    maxlength : "标题长度最长为100个字符"
                },
                node : {
                    required : "请选择发帖类型"
                }
            },
            submitHandler : function (form) {
                $.ajax({
                    url : "/newtopic",
                    type : "post",
                    data : $(form).serialize(),
                    success : function (json) {
                        if(json.state = 'success') {
                            alert("发帖成功，等待人工审核通过后方可在主页显示");
                            window.location.href = "/topicdetail?topicId=" + json.data;
                        } else {
                            alert('发帖失败，请稍候再试！');
                        }
                    },
                    error : function () {
                        alert("服务器访问错误，请稍候再试！");
                    },
                    beforeSend : function () {
                        $("#sendBtn").html("主题发布中<img src='/static/img/loding.gif'>").attr("disabled","disabled");
                    },
                    complete : function () {
                        $("#sendBtn").html("发布主题").removeAttr("disabled");
                    }
                })
            }
        });

    });
</script>

</body>
</html>
