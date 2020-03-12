<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>erorsPage</title>
<script type="text/javascript">
	if("${login}"=="logFailed")
		alert("登录失败,用户名或密码错误!");
	if("${user}"=="outtime")
		alert("登录已失效,请重新登录!");
	window.open("<%=request.getContextPath()%>/index.jsp","_parent","");
</script>
</head>
<body>

</body>
</html>