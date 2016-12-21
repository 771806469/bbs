$(function () {

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
                maxlength : $.validator.format("标题长度最长为100个字符")
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
                success : function (data) {
                    if(data.state = 'success') {
                        alert("发帖成功，等待人工审核通过后方可查看");
                        window.location.href = "/home";
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
