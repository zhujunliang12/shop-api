<%--
  Created by IntelliJ IDEA.
  User: zhujunliang
  Date: 2020/6/26
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.dropdown.hack.css" rel="stylesheet" />

    <script src="<%=request.getContextPath()%>/bootstrap/js/jquery-3.3.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>

    <!-- 引入boobox弹框插件的css文件和js文件 -->
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootbox.min.js"></script>
    <script src="<%=request.getContextPath()%>/bootstrap/js/bootbox.locales.min.js"></script>

    <!-- 引入datatables表格插件的css文件和js文件 -->
    <link href="<%=request.getContextPath()%>/js/DataTables/css/dataTables.bootstrap.min.css" rel="stylesheet" />
    <script src="<%=request.getContextPath()%>/js/DataTables/js/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/DataTables/js/dataTables.bootstrap.min.js"></script>

    <!-- 引入datetimepicker日期插件的css文件和js文件 -->
    <link href="<%=request.getContextPath()%>/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    <script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script>
        //ajax全局设置
        $.ajaxSetup({
            //在ajax成功回调函数或失败回调函数被调用都会调用这个函数
            complete:function(request){
                if(request.responseJSON.code == 12345){
                    location.href = "<%=request.getContextPath()%>/login.jsp";
                }else if(request.responseJSON.code == 2000){
                    location.href = "<%=request.getContextPath()%>/no-permission.jsp";
                }
            }
        });
    </script>
</head>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        用户登录
    </div>
    <div class="panel-body">
        <form id="" class="form-horizontal" style="">

            <div class="form-group">
                <label  class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="userName" />
                </div>
            </div>

            <div class="form-group">
                <label  class="col-sm-2 control-label">密码</label>
                <div class="col-sm-6">
                    <input type="password" class="form-control" id="password" />
                </div>
            </div>

            <div class="form-group">
                <label  class="col-sm-2 control-label"></label>
                <div style="padding-left:120px">
                    <button type="button" onclick="login()" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok"></span> 登录
                    </button>
                    &nbsp;
                    <button type="reset" class="btn btn-danger">
                        <span class="glyphicon glyphicon-refresh"></span> 重置
                    </button>
                    <button type="reset" class="btn btn-danger" onclick="zhuCe()">
                        <span class="glyphicon glyphicon-refresh"></span> 会员注册
                    </button>
                    <button type="reset" class="btn btn-warning">
                        <a href="<%=request.getContextPath()%>/forget-password.jsp">修改密码</a>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>

</html>
<script>

    function login(){
        var userName=$("#userName").val();
        var password=$("#password").val();
        if(userName.trim()==""){
            bootbox.alert("用户名不能为空");
        }else if(password.trim()==""){
            bootbox.alert("密码不能为空");
        }else{
            var param={};
            param.userName=userName;
            param.password=password;
            $.ajax({
                url:"<%=request.getContextPath()%>/user/login",
                type:"post",
                data:param,
                dataType:"json",
                error:function(){
                    alert("失败!");
                },
                success:function(result){
                    if(result.code == 200){
                        window.location.href="<%=request.getContextPath()%>/member/toMember";
                    }else{
                        alert(result.msg);
                    }
                }

            });
        }
    }

    function zhuCe() {
        window.location.href="<%=request.getContextPath()%>/member/toZhuCe"
    }
</script>
