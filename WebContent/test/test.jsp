<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>测试页面</title>
</head>
<body>
	<center>
		<form action="testAct" method="post">
			<table>
				<tr>
					<td><input type="text" name="dv.name" placeholder="姓名"></td>
				</tr>
				<tr>
					<td><input type="text" name="dv.age" placeholder="年龄"></td>
				</tr>
				<tr>
					<td><input type="text" name="dv.degress" placeholder="学历"></td>
				</tr>
				<tr>
					<td style="text-align:center"><input type="submit" value="提交"></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>