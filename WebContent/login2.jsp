<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="/sh/src/dao/fileDao/impl/FileManageDaoImpl.java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>token test</title><base>
<script type="text/javascript">
	function getLength(){
		var l=document.getElementsByName("ad_fileName").length;
		var name=document.getElementsByName("ad_fileName").value;
		var n=document.getElementById("fileId");
		
		alert("length:"+l+",name:"+name+",n:"+n.value+",id:"+n.id.substring(0));
	}
</script>
</head>
<body>
<%
	FileManageDaoImpl fd=new FileManageDaoImpl();
	
	
%>
<%=fd.uploadFiles() %>
</body>
</html>