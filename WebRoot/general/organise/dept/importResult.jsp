<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>导入结果</title>
		<link rel="stylesheet" type="text/css" href="theme/2/style.css">
	</head>
	<body class="bodycolor" topmargin="5">
		<table class="MessageBox" align="center" width="430">
			<tr>
				<td>
					<h4 class="title">
						结果：
					</h4>
					<div class="content" style="font-size: 12pt">
						${fail_msg }
					</div>
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<input type="button" value="返回" class="BigButton"
				onclick="parent.location='/Seal/manageDept.do'">
		</div>
	</body>
</html>