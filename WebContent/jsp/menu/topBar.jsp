<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本信息</title>
	<script type="text/javascript">
		function loginout(){
			if(confirm("确定退出吗？"))
				location.href=open("<%=request.getContextPath()%>/index.jsp","_parent");
		}
	</script>
</head>
<body>
<div style="float:right;color:red;font:10; border:0px solid">
	<a href="javascript:void(0)" onclick="loginout()">退出</a>
</div>
<marquee scrollamount="10" scrolldelay="50" direction="left" onmouseover="this.stop()" onmouseout="this.start()"><span style="text-align:center;"><h2>Welcome you  ${userVo.userNo}</h2></span></marquee>
</body>
</html>