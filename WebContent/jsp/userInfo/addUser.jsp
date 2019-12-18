<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
<script type="text/javascript" src="../../js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript" src="../../js/jQuery/jquery-3.4.1.min.js"></script>

<script type="text/javascript">
 
	$(function() {
		$("#addUser").click(function() {
			var v1 = addUserForm.userNo.value;
			var v3 = addUserForm.pwd.value;
			var v3_1 = addUserForm.pwd2.value;
			var v5 = addUserForm.selectName.value;
			if (v1 == '') {
				alert("用户名不能为空!");
				return false;
			} else if (v3 == '') {
				alert("密码不能为空!");
				return false;
			} else if (v3 != v3_1) {
				alert("两次输入密码不一致!");
				return false;
			}
			$.get("login!ajaxReturn.action?userNo=" + v1, function(msg) {
				var returnData = eval(msg);//解析后台返回的json数据
				var retVal = "";
				if (returnData != '' && returnData != null) {//根据传到后台的用户名判断返回不为空即已存在此用户名
					for (var i = 0; i < returnData.length; i++) {//循环取值
						retVal = returnData[i].userNo;//取其中的指定字段值
					}
					alert("用户名:"+retVal+"已存在,请重新输入!");
					addUserForm.userNo.value='';
					addUserForm.pwd.value='';
					addUserForm.pwd2.value='';
					return false;
				} else {
					addUserForm.action = "login!addUser.action?deptName=" + v5+"&type=1";
					addUserForm.submit();
				}
			}, "json");
		})
	})
</script>
<style type="text/css">
	td{
		text-align:left
	}
</style>
</head>
<body>
<center>
		<form action="" method="post" name="addUserForm">
			<table>
				<tr>
					<td><input type='text' name="userNo" placeholder="用户名" title="用于登录">*</td>
				</tr>
				<tr>
					<td><input type='text' name="userName" placeholder="真实姓名"></td>
				</tr>
				<tr>
					<td><input type='password' name="pwd" placeholder="用户密码" size="21">*</td>
				</tr>
				<tr>
					<td><input type='password' name='pwd2' placeholder="确认密码" size="21">*</td>
				</tr>
				<tr>
					<td><input type='text' name="age" placeholder="年龄"></td>
				</tr>
				<tr>
					<td><select name="selectName" id="selectID" title="选择部门">
							<c:forEach items="${deptList}" var="dept">
								<option name="dept">${dept.deptName }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr><td style="text-align:center"><input type="button" value="新增用户" id="addUser" title='ajax判断用户是否已存在'></td></tr>
			</table>
		</form>
	</center>
</body>
</html>