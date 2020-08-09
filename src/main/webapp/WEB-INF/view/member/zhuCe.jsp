<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/6/26
  Time: 0:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/static.jsp"></jsp:include>
</head>
<body>

<div id="addUserDiv" >

    <form id="addUserForm" class="form-horizontal" style="">

        <div class="form-group">
            <label  class="col-sm-2 control-label">会员名</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="memberName" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">真实名</label>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="realName" />
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">密码</label>
            <div class="col-sm-8">
                <input type="password" class="form-control" id="password" />
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">确认密码</label>
            <div class="col-sm-8">
                <input type="password" class="form-control" id="confirmPassword" />
            </div>
        </div>


        <div class="form-group">
            <label  class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-8">
                <input type="password" class="form-control" id="phone" />
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-8">
                <input type="password" class="form-control" id="email" />
            </div>
        </div>
        <div style="float: right;margin-right: 400px" >

            <button type="button" onclick="showAddUserDiv()" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span>确认
            </button> &nbsp;&nbsp;
            <button type="reset" class="btn btn-danger">
                <span class="glyphicon glyphicon-refresh"></span> 重置
            </button>;&nbsp;
            <button type="button" onclick="clearData()" class="btn btn-info">
                <span class="glyphicon glyphicon-plus"></span>返回
            </button>
        </div>
    </form>

</div>

</body>
</html>
<script>
    function showAddUserDiv() {
        var memberName = $("#memberName").val();
        var realName = $("#realName").val();
        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        var phone = $("#phone").val();
        var email = $("#email").val();

        if (memberName.trim() == "") {
            bootbox.alert("用户名不能为空");
        } else if (password.trim() == "") {
            bootbox.alert("密码不能为空");
        } else if (confirmPassword.trim() == "") {
            bootbox.alert("请确认密码");
        } else if (confirmPassword.trim() != password.trim()) {
            bootbox.alert("密码不一致");
        } else if (phone.trim() == "") {
            bootbox.alert("请输入手机号");
        } else if (email.trim() == "") {
            bootbox.alert("请输入邮箱");
        } else {
            var param = {}
            param.memberName = memberName;
            param.realName = realName;
            param.password = password;
            param.phone = phone;
            param.mail = email;
            $.ajax({
               /* url: "addMember",*/
                url: "http://localhost:8484/members",
                type: "post",
                data: param,
                dataType: "json",
                error: function () {
                    alert("失败!");
                },
                success: function (result) {
                    if (result.code != 1) {
                        window.location.href = "<%=request.getContextPath()%>/login.jsp"
                    } else {
                        alert(result.msg)
                    }
                }

            });
        }
    }
</script>
