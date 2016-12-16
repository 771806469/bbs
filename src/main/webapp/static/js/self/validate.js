$(function () {
    $("#regBtn").click(function () {
        $("#regForm").submit();
    });

    $("#regForm").validate({
        errorElement: "span",
        errorClass: "text-error",
        rules: {
            username: {
                required: true,
                minlength:3,
                remote:"/validate/username"
            },
            password: {
                required: true,
                rangelength: [6,18]
            },
            rePassword: {
                required: true,
                rangelength: [6,18],
                equalTo: "#password"
            },
            email: {
                required: true,
                email:true,
                remote:"/validate/email"
            },
            phone: {
                required: true,
                rangelength: [11,11],
                digits: true
            }
        },
        messages: {
            username: {
                required:"请输入账号",
                minlength:"账号至少3位",
                remote:"账号已被占用"
            },
            password: {
                required:"请输入密码",
                rangelength: "密码长度6-18位"
            },
            rePassword: {
                required:"请输入密码",
                equalTo: "两次输入账号不一致",
                rangelength: "两次输入账号不一致"
            },
            email: {
                required: "请输入邮件",
                email: "请输入正确的邮件格式",
                remote:"该邮件已被占用，请更换邮箱"
            },
            phone: {
                required: "请输入手机号码",
                rangelength: "请输入正确的手机号码",
                digits: "请输入正确的手机号码"
            },
        },
        submitHandler: function() {
            $.ajax({
                url: "/reg",
                type: "post",
                data:$("#regForm").serialize(),
                beforeSend: function () {
                    $("#regBtn").text("注册中。。。").attr("disabled","disabled");
                },
                success: function(data){
                    if(data.state == 'success') {
                        alert("注册成功！请登录");
                        window.location.href = "/login";
                    } else {
                        alert(data.message);
                    }
                },
                error: function () {
                    alert("服务器访问错误");
                },
                complete: function () {
                    $("#regBtn").text("注册").removeAttr("disabled");
                }
            });
        }
    });

});