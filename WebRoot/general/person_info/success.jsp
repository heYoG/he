<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>修改成功</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link rel="stylesheet" type="text/css" href="theme/1/style.css">
		<script src="js/handler.js"></script>
	</head>
	<body class="bodycolor">
		<table class="MessageBox" align="center" width="430">
			<tr>
				<td class="msg error">
					<div id="success" class="content">
						<div id="success_top"></div>
						<div id="success_bottom">
							<span id="info">密码修改成功！</span>
							<br>
							<a href="javascript:history.back();">返回</a>&nbsp;&nbsp;<a
								href="javascript:top.location='login.jsp'">重新登录</a>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<br>

	</body>
</html>