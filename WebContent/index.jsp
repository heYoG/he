<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ssMVCh</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/user/login.js"></script>
<link rel="stylesheet" type="text/css" href="css/menuCss.css">
</head>
<body>
<form action="<%=request.getContextPath() %>/user/login.do" id="formId" name="form_login" method="post">
	<table align="center">
		<tr><td><input type="text" placeholder="用户名称" id="userId" name="userNo" size="20"></td></tr>
		<tr><td><input type="password" placeholder="用户密码" id="pwdId" name="pwd" size="21"></td></tr>
		<tr><td align="center"><input type="button" id="btID" value="登录" onclick="login()"></td></tr>
	</table>
</form>
</body>
</html>