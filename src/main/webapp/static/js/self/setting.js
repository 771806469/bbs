$(function () {

    $("#emailBtn").click(function () {
        $("#emailForm").submit();
    });

    $("#emailForm").validate({
        errorElement: "span",
        errorClass: "text-error",
        rules: {
            email: {
                required: true,
                email: true,
                remote: "/validate/email?type=1"
            }
        },
        messages: {
            email: {
                required: "请输入电子邮件",
                email: "请输入正确的电子邮件格式",
                remote: "该邮箱已被占用！"
            }
        },
        submitHandler : function (form) {
            $.ajax({
                url : "/setting?action=email",
                type : "post",
                data : $(form).serialize(),
                beforeSend : function(){
                    $("#emailBtn").html("提交中<img src='/static/img/loding.gif'>").attr("disabled","disabled");
                },
                success : function (data) {
                    if(data.state == 'success') {
                        alert("电子邮件修改成功！");
                    } else if(data.state == 'error') {
                        alert(data.message);
                    }
                },
                error : function() {
                    alert("服务器访问错误!");
                },
                complete : function () {
                    $("#emailBtn").html("提交").removeAttr("disabled");
                }
            })
        }
    });

    $("#passwordBtn").click(function () {
        $("#passwordForm").submit();
    });

    $("#passwordForm").validate({
        errorElemennt : "span",
        errorClass : "text-error",
        rules : {
            oldpassword : {
                required : true,
                rangelength : [6,18]
            },
            newpassword : {
                required : true,
                rangelength : [6,18]
            },
            repassword : {
                required : true,
                rangelength : [6,18],
                equalTo : "#newpassword"
            }
        },
        messages : {
            oldpassword : {
                required :  "请输入密码",
                rangelength : "密码长度为6~18位"
            },
            newpassword : {
                required :  "请输入密码",
                rangelength : "密码长度为6~18位"
            },
            repassword : {
                required :  "请确认密码",
                rangelength : "两次输入密码不一致",
                equalTo : "两次输入密码不一致"
            }
        },
        submitHandler : function(form) {
            $.ajax({
                url : "/setting?action=password",
                type : "post",
                data : $(form).serialize(),
                beforeSend : function() {
                    $("#passwordBtn").html("提交中<img src='/static/img/loding.gif'>").attr("disabled","disabled");
                },
                success : function(data) {
                    if(data.state == 'success') {
                        alert("修改密码成功,请重新登录！");
                        window.location.href = "/login";
                    } else if(data.state == 'error') {
                        alert(data.message);
                    }
                },
                error : function () {
                    alert("服务器访问错误！");
                },
                complete : function () {
                    $("#passwordBtn").html("提交").removeAttr("disabled");
                }
            });
        }
    });

});
