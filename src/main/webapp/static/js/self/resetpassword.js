$(function(){

    $("#resetBtn").click(function () {
        $("#resetForm").submit();
    });

    $("#resetForm").validate({
        errorElement : "span",
        errorClass : "text-error",
        rules : {
            password : {
                required : true,
                rangelength:[6,18]
            },
            repassword : {
                required : true,
                equalTo : "#password",
                rangelength : [6,18]
            }
        },
        messages : {
            password : {
                required : "请输入新密码！",
                rangelength : "密码长度为6~18位！"
            },
            repassword : {
                required : "请输入确认密码！",
                equalTo : "两次输入密码不一致",
                rangelength : "两次输入密码不一致"
            }
        },
        submitHandler : function (form) {
            $.ajax({
                url : "resetpassword",
                type : "post",
                data : $("#resetForm").serialize(),
                beforeSend : function () {
                    $("#resetBtn").html("提交中<img src='/static/img/loding.gif'>").attr("disabled","disabled");
                },
                success : function (data) {
                    if(data.state == 'success') {
                        alert("密码修改成功，请重新登录！");
                        window.location.href = "/login";
                    } else if(data.state == 'error') {
                        alert(data.message);
                    }
                },
                error : function () {
                    alert("服务器访问错误");
                },
                complete : function () {
                    $("#resetBtn").html("提交").removeAttr("disabled");
                }
            });
        }

    });

});