<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>系统登录</title>

		<link rel="stylesheet" type="text/css" href="theme/2/style.css">
	</head>

	<body class="bodycolor" topmargin="5">
	<form name="form1" method="post" action="login.do?type=updatePassword">
		<table class="MessageBox" align="center" width="430">
			<tr>
				<td class="msg error">
					<h4 class="title">
						错误：
					</h4>
					<div class="content" style="font-size: 12pt">
						${fail_msg }
					</div>
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<input type="submit" value="前往修改" class="BigButton">
			<input type="button" value="暂不修改" class="BigButton" onClick="location='login.jsp'" title="未修改密码无法登录系统">
		</div>
		</form>
	</body>
</html>