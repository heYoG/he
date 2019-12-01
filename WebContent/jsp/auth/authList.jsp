<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限信息</title>
<style type="text/css">
table{
	border:1px solid #B1CDE3;
	width:400px;
	height:20px;
	text-align:center;
	border-collapse:collapse;
	}
th,td{
	border:1px solid #B1CDE3
}
</style>
</head>
<body>
	<center><form action="auth_authInfo" method="post">
		<table>
			<tr>
				<th>权限id</th>
				<th>权限值</th>
				<th>权限名</th>
			</tr>
			<c:forEach var="list" items="${listInfo}">
				<tr>
					<td>${list.id}</td>
					<td>${list.authVal }</td>
					<td>${list.authName }</td>
				</tr>
			</c:forEach>
		</table></form>
	</center>
</body>
</html>