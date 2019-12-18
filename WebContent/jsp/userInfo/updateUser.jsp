<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息修改</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/user/updateUser.js"></script>
<style type="text/css">
	table{
		width:400px;
		border:0px solid red;
		text-align:center;
	}
</style>
</head>
<body>
	<center>
	<form action="login!updateUserRet.action?userNo=${userVo.userNo}&?type=1&isAppro=${isAppro}" name="formName" id="formID" method="post">
		<table>
			<tr>
				<td>用户名:<input type="text" name="userNo" value="${userVo.userNo}" disabled="disabled" title="不可更改!"></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;姓名:<input type="text"
					name="userName" value="${userVo.userName}"></td><!-- 模型驱动取值 -->
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;年龄:<input type="text"
					name="userAge" value="${userVo.age}"></td><!-- 属性驱动取值 -->
			</tr>
			<tr>
				<td>
				旧密码:<input type="password" size="21" id="oldPWD">
				</td>
			</tr>
			<tr>
				<td>
					新密码:<input type="password" size="21" name="newPWDName" id="newPWD">
				</td>
			</tr>
			<tr>
				<td style="text-align:left;padding-left:79px">
					确认密码:<input type="password" size="21" id="repeatPWD">
				</td>
			</tr>
			<tr>
				<td style="padding-left:60px"><input type="button" value="放弃修改" onclick="giveUpUpdate()">&nbsp;&nbsp;&nbsp;<input
					type="button" id="upID" value="确认修改" onclick="updateUser('${userVo.pwd}')"></td>
			</tr>
		</table>
		</form>
</center>
</body>
</html>