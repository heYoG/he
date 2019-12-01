<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript">
if("${updateInfo}"=="update_fail")
	alert("修改密码失败!");
</script>
<style type="text/css">
table{
	border:1px solid #B1CDE3;
	border-collapse:collapse;
}
th,td{
	border:1px solid #B1CDE3;
	width:100px;
	height:20px;
	text-align:center;
}
</style>
<script type="text/javascript">
	/*obj1:用户id,obj2:权限值*/
	function delUser(obj1,obj2){
		if(confirm("确定删除当前用户?")){
			if(obj2==1){
				alert("当前用户为管理员,无法删除！");
				return false;			
			}
			location.href="login!delUser.action?id="+obj1;			
		}
			
	}
</script>
</head>
<body>
	<center>
		<table border="1px" border-color="#72D4F4">
			<tr>
				<th>序号</th>
				<th>用户名</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>用户状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${userList}" var="list" varStatus="st">
				<tr>	
					<td>${st.count}</td>
					<td>${list.userNo }</td>
					<td>${list.userName }</td>
					<td>${list.age }</td>
					<td><c:if test="${list.status==1}">正常</c:if><c:if test="${list.status==0 }">已注销</c:if></td>
					<td nowrap><a href="login!updateUser.action?userNo=${list.userNo}" target="showPageName">修改</a>
					&nbsp;<a href ="javascript:void(0)" target="showPageName" onclick="delUser(${list.id},${list.av.authVal})">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
</html>