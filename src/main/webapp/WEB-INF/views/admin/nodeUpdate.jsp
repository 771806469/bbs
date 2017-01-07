<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/sweetalert.css" rel="stylesheet">
</head>
<body>
<%@include file="../include/adminNavbar.jsp" %>
<div class="container-fluid" style="margin-top:20px">
    <form id="updateForm" action="">
        <legend>编辑节点</legend>
        <label>节点名称</label>
        <input type="hidden" name="nodeid" value="${node.id}">
        <input type="text" name="nodenames" value="${node.nodeName}">
        <div class="form-actions">
            <button id="saveBtn" type="button" class="btn btn-primary">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function () {

        $("#saveBtn").click(function () {
            $("#updateForm").submit();
        });

        $("#updateForm").validate({
            errorElement: "span",
            errorClass: "text-error",
            rules: {
                nodenames: {
                    required: true,
                    remote: "/admin/nodeValidate?nodeId=${node.id}"
                }
            },
            messages: {
                nodenames: {
                    required: "请输入节点名称",
                    remote: "节点已存在"
                }
            },
            submitHandler: function () {
                $.ajax({
                    url: "/admin/nodeupdate?nodeId=${node.id}",
                    type: 'post',
                    data: $("#updateForm").serialize(),
                    success: function (json) {
                        if (json.state == "success") {
                            swal({title: "修改成功"}, function () {
                                window.location.href = "/admin/node";
                            });
                        } else {
                            swal(json.message);
                        }

                    }, error: function () {
                        swal("修改失败,服务器异常");
                    }
                });
            }
        })

    });


</script>
</body>
</html>

