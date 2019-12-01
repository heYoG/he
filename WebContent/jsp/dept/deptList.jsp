<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.mobile.js"></script>
<style type="text/css">
table {
	border: 1px solid #B1CDE3;
	padding: 0;
	margin: 0 auto;
	margin-left: 10px;
	margin-right: 10px;
	width: 78%;
	border-collapse: collapse;
}
td, th {
	text-align: center;
	border: 1px solid #B1CDE3;
	background: #fff;
	font-size: 14px;
	padding: 3px 3px 3px 8px;
	color: #4f6b72;
	height: 22px;
}
</style>
<script type="text/javascript">
	function delDept(obj){
		if(confirm("确定删除此部门?"))
			location.href="DeptAction_delDept?id="+obj;
 	}
</script>
</head>
<body>
<center>
		<form action="" method="post" name="deptForm">
			<c:if test="${deptList=='[]'}">
		<span sytle="text-align:center;font:red 15pt">此处空空如也</span><br>
		<a href="<%=request.getContextPath() %>/jsp/dept/addDept.jsp" target="showPageName">前往添加部门...</a>
			</c:if>
			<c:if test="${deptList!='[]'}">
				<table>
					<tr>
						<th>序号</th>
						<th>部门编号</th>
						<th>部门名称</th>
						<th>操作</th>
					</tr>
					<c:forEach var="dept" items="${deptList}" varStatus="st">
						<tr>
							<td>${st.count}</td>
							<td>${dept.deptID }</td>
							<td>${dept.deptName }</td>
							<td><a href="javascript:void(0)"
								onclick="delDept(${dept.deptID})">删除</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</form>
	</center>
</body>
</html>