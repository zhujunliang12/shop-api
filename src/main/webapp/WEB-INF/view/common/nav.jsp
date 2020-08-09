<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!-- 导航栏 -->
<nav class="navbar navbar-inverse" role="navigation">
<div class="container-fluid">
	<div class="col-md-8">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">电影后台管理系统</a>
		</div>
		<div>
			<ul class="nav navbar-nav" id="menuUl">
			
			</ul>
		</div>
	</div>
	
	<div class="col-md-4" style="color:#fff">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<img style="margin-top:5px" src="<%=request.getContextPath()%>/${user.avatarPath}" class="img-circle" width="40" height="40"/>
				</td>
				<td>
					&nbsp;&nbsp;<font color="#fff">${user.username},欢迎您!</font>
					&nbsp;&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/userController/zhuXiao">注销</a>
					&nbsp;|&nbsp;
					<a href="javascript:void(0)" onclick="showUpdatePasswordDiv()">修改密码</a>
					<br/>
					您今天是第<%-- ${user.loginCount} --%>次登录,上次登录时间为
					<%-- ${lastLoginTime} --%>
				</td>
			</tr>
		</table>
	</div>
	
</div>
</nav>
<!-- 修改用户密码的div -->
<div id="updatePasswordDiv" style="display:none">
	<form id="updatePasswordForm" class="form-horizontal" style="">
		<div class="form-group">
			<label  class="col-sm-2 control-label">旧密码</label>
			<div class="col-sm-8">
			<input type="password" class="form-control" id="oldPassword" />
			</div>
		</div>
		<div class="form-group">
			<label  class="col-sm-2 control-label">新密码</label>
			<div class="col-sm-8">
			<input type="password" class="form-control" id="newPassword" />
			</div>
		</div>
		<div class="form-group">
			<label  class="col-sm-2 control-label">确认密码</label>
			<div class="col-sm-8">
			<input type="password" class="form-control" id="confirmNewPassword" />
			</div>
		</div>
	</form>
</div>


