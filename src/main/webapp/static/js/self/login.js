$(function(){

    function getParameterByName(name,url) {
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

    $("#password").keydown(function() {
        $("#loginForm").submit();
    });

    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });

    $("form").validate({
        errorElement : "span",
        errorClass : "text-error",
        rules : {
            username : {
                required : true,
                minlength : 3
            },
            password : {
                required : true,
                rangelength: [6,18]
            }
        },
        messages : {
            username : {
                required : "请输入账号",
                minlength : "账号最小长度为3"
            },
            password : {
                required : "请输入密码",
                rangelength : "密码长度为6~18位"
            }
        },
        submitHandler : function(form){
            $.ajax({
                url : "/login",
                type : "post",
                data : $(form).serialize(),
                beforeSend : function () {
                    $("#loginBtn").html("登录中<img src='/static/img/loding.gif'>").attr("disabled","disabled");
                },
                success : function (data) {
                    if(data.state == "success") {

                            var url = getParameterByName("redirect");
                            if(url) {
                                var hash = location.hash;
                                if(hash){
                                    window.location.href = url + hash;
                                } else {
                                    window.location.href = url;
                                }
                            } else {
                                window.location.href = "/home";
                            }
                    } else {
                        alert(data.message);
                    }
                },
                error : function () {
                    alert("服务器访问错误");
                },
                complete : function () {
                    $("#loginBtn").html("登录").removeAttr("disabled");
                }

            });
        }
    })

});
