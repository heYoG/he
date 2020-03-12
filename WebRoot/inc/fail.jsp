<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>系统登录</title>

		<link rel="stylesheet" type="text/css" href="theme/2/style.css">
	</head>

	<body class="bodycolor" topmargin="5" onkeydown="keyReturn();"><!-- 加载按下键事件 -->

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
			<input type="button" value="返回" id="bt" class="BigButton" onclick="location='login.jsp'">
		</div>
		<script type="text/javascript">
		function keyReturn(){
		if(event.keyCode==13){//回车键值为13
			document.getElementById("bt").click();//按下回车调用按钮返回事件
		}
		}
		</script>
	</body>
</html>