<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>erorsPage</title>
<script type="text/javascript">
	if("${user}"=="user")
		alert("用户名${errorUser}不存在,请核对后再输！");
	if("${pwd}"=="password")
		alert("用户密码错误!");
	if("${user}"=="outtime")
		alert("请重新登录再操作!");
	window.open("<%=request.getContextPath()%>/index.jsp","_parent","");
</script>
</head>
<body>

</body>
</html>