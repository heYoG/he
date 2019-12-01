<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>struts2&hibernate整合</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/user/login.js"></script>
<link rel="stylesheet" type="text/css" href="css/menuCss.css">
<script type="text/javascript">
	
</script>
</head>
<body>
<!-- 国际化 -->
<div id="loginID"><a href="login!i18n.action?request_locale=zh_CN">中文</a>&nbsp;&nbsp;
<a href="login!i18n.action?request_locale=en_US">English</a></div>
<form action="login.action" id="formId" name="form_login" method="post">
	<table align="center">
		<!-- <tr><td><input type="text" placeholder="用户名称" id="userId" name="userNo" size="20"></td></tr>
		<tr><td><input type="password" placeholder="用户密码" id="pwdId" name="pwd" size="21"></td></tr>
		<tr><td align="center"><input type="button" id="btID" value="登录" onclick="login()"></td></tr> -->
		<tr><td><s:textfield name="userNo" id="userId"  key="userName"></s:textfield></td></tr>
		<tr><td><s:password name="pwd" id="pwdId"  key="userPassword" size="21"></s:password></td></tr>
		<tr><td colspan="2" style="padding-left:130px"><s:submit name="sb" key="submit" theme="simple"></s:submit></td></tr>
		<s:token/><!-- 令牌 -->
	</table>
</form>
</body>
</html>