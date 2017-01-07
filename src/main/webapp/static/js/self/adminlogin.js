$(function () {

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $("#password").keydown(function () {
        if(event.keyCode == '13'){
            $("#loginBtn").click();
        }
    });

    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });

    $("#loginForm").validate({
        errorElement : "span",
        errorClass : "error-text",
        rules : {
            adminName : {
                required : true,
                minlength : 3,
                remote : "/validate/adminname"
            },
            password : {
                required : true,
                rangelength : [6,18]
            }
        },
        messages : {
            adminName : {
                required : "请输入账户名称",
                minlength : "账户名长度最少3位",
                remote : "该用户名不存在"
            },
            password : {
                required : "请输入密码",
                rangelength : "密码长度为6-18位之间"
            }
        },
        submitHandler : function () {
            $.ajax({
                url : "/admin/login",
                type : "post",
                data: $("#loginForm").serialize(),
                beforeSend : function () {
                    $("#loginBtn").html("登录中<img src='/static/img/loding.gif'>").attr("disabled","disabled");
                },
                success : function (json) {
                    if(json.state == 'success') {
                        window.location.href = "/admin/home";
                    } else {
                        alert("message");
                        alert(json.message);
                    }
                },
                error : function () {
                    alert("服务器访问错误！");
                },
                complete : function () {
                    $("#loginBtn").html("登录").removeAttr("disabled");
                }
            });
        }
    })

})
