<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/19 0019
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人资料修改</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/webuploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" id="emailForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" readonly value="${sessionScope.curr_user.username}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input name="email" type="text" placeholder="${sessionScope.curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button id="emailBtn" class="btn btn-primary">提交</button>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action="" id="passwordForm" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">原始密码</label>
                <div class="controls">
                    <input type="password" id="oldpassword" name="oldpassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">新密码</label>
                <div class="controls">
                    <input type="password" id="newpassword" name="newpassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" id="passwordBtn" class="btn btn-primary">提交</button>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img id="avatar" src="http://ohwtqwe8j.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/50/h/50" class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="avatarBtn" >上传新头像</div>
            </div>


        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/webuploader/webuploader.min.js"></script>
<script src="/static/js/self/setting.js"></script>
<script src="/static/js/self/notify.js"></script>
<script>

    $(function(){
        //头像上传
        var token = "${token}";
        var uploader = WebUploader.create({
            swf : "/static/js/webuploader/Uploader.swf",
            server: "http://up-z1.qiniu.com/",
            pick: '#avatarBtn',
            auto : true,
            fileVal:'file',
            formData:{'token':'${requestScope.token}'}
            /*accept: {
             title: 'Images',
             extensions: 'gif,jpg,jpeg,bmp,png',
             mimeTypes: 'image/!*'
             }*/
        });

        //上传成功
        uploader.on('uploadSuccess',function(file,data){
            var fileKey = data.key;
            //修改数据库中的值
            $.post("/setting?action=avatar",{'fileKey':data.key}).done(function (data) {
                if(data.state == "success") {
                    var url = "http://ohwtqwe8j.bkt.clouddn.com/";
                    $("#avatar").attr("src",url + fileKey +"?imageView2/1/w/50/h/50");
                    $("#navbarAvatar").attr("src",url + fileKey +"?imageView2/1/w/20/h/20");
                    alert("头像上传成功！");
                }
            }).error(function () {
                alert("服务器访问错误！");
            })
        });
        //上传失败
        uploader.on('uploadError',function(){
            alert("上传失败,请稍后再试");
        });

    });


</script>
</body>
</html>
