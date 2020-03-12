<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>test to upload File</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	td{
		text-align:center
	}
</style>
<script type="text/javascript">
	function doSubmit(){
		var val=testFormName.testFile.value;
		if(val==''||val==null){
			alert("请选择要上传的文件!");
			return false;
		}
		//testFormName.action="testServlet?filePath="+val;
		//testFormName.submit();
		location.href="testServlet?filePath="+val;
	}
</script>
  </head>
  
  <body>
   <center>
   	<form action="" method="post" name="testFormName" enctype="multipart/form-data">
   		<table>
   			<tr><td>选择文件:<input type="file" name="testFile" ></td></tr>
   			<tr><td><input type="button" value="提交" onclick="doSubmit()"></td></tr>
   		</table>
   	</form>
   </center>
  </body>
</html>
