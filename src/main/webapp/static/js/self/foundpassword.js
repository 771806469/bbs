$(function () {
    $("#type").change(function () {
        var type = $(this).val();
        if ("email" == type) {
            $("#typename").text("电子邮件");
        } else if ("phone" == type) {
            $("#typename").text("手机号码");
        }
    });

    $("#btn").click(function () {
        $("#form").submit();
    });


    $("#form").validate({
        errorElement: "span",
        errorClass: "text-error",
        rules: {
            value: {
                required: true
            }
        },
        messages: {
            value: {
                required: "该项必填"
            }
        },
        submitHandler: function () {
            $.ajax({
                url: "/foundpassword",
                type: "post",
                data: $("#form").serialize(),
                beforeSend: function () {
                    $("btn").html("提交中<img src='/static/img/loding.gif'>").attr("disabled", "disabled");
                },
                success: function (data) {
                    if (data.state == "success") {
                        if ('email' == $("#type").val()) {
                            alert("提交成功，请注意查看邮件");
                        } else if ('phone' == $("#type").val()) {
                            alert("提交成功，请注意查收短信");
                            window.location.href = "/login";
                        }
                    } else if (data.state == "error") {
                        alert(data.message);
                    }
                },
                error: function () {
                    alert("服务器访问错误！");
                },
                complete : function() {
                    $("btn").html("提交").removeAttr("disabled");
                }
            });
        }
    });


});