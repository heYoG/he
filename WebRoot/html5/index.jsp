<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  	<head>
	    <base href="<%=basePath%>">
	 
	    <title>Html5测试页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="html5">
		<meta http-equiv="description" content="html5">
		
		<link rel="stylesheet" type="text/css" href="html5/css.css">
		<script src="html5/jquery.min.js"></script>
		<script src="html5/multitouch.js"></script>
		<script src="html5/sign.js"></script>
		<script type="text/javascript">
			function getData(){
				Save();
				document.getElementById('texta').value=document.getElementById('xy').value;
			}
		</script>
		
	</head>
	<body topmargin="0" leftmargin="0" style="background:#ccc" onload="init()">
		<input type="button" value="get" onclick="getData()" />
		<textarea rows="5" cols="100" id="texta"></textarea><div>
		<div align="center" id="div_book">
			<canvas id='page'></canvas>
		</div>
		<input type="hidden" id="xy">
	</body>
</html>