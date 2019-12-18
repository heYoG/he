<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path=request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>备份文件</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/fileManage.css">
<script type="text/javascript">
	function addInput(){//添加控件域
		var doc=document.getElementById("addDoc");
		var originName=document.getElementsByName("upFile").length;
		var newInput1=document.createElement("<input type='file' name='upFile' id='upId"+originName+"'>");
		var newInput2=document.createElement("<input type='button' name='delName' id='delId"+originName+"' value='删除此列' style='height:20px;line-height:10px' onclick='del(this)'>");
		doc.appendChild(document.createElement("<P id='p"+originName+"'></P>"));//定义id用于删除最后的空格行
		doc.appendChild(newInput1);
		doc.appendChild(newInput2);
	}
	
	function del(obj){//删除控件域
		var doc=document.getElementById("addDoc");
		var delInput1=obj.id.substring(5);//删除file域
		doc.removeChild(document.getElementById("p"+delInput1));//删除空格行
		doc.removeChild(document.getElementById("upId"+delInput1));
		doc.removeChild(document.getElementById("delId"+delInput1));
	}
	
	function uploadFile(){//上传文件
		var file=document.getElementById("upId");
		if(file.value==null||file.value==""){
			alert("要上传的文件不能为空!");
			return false;
		}
		formUploadFile.submit();
	}
</script>
<style type="text/css">
	table{
	border:0px solid
}
td{
	text-align:center
}
</style>
</head>
<body>
<center>
	<form action="fileManage!uploadFile.action" method="post" name="formUploadFile" enctype="multipart/form-data">
		<table>
			<tr>
				<td nowrap id="addDoc">备份文件：<input type="file" name="myFile" id="upId"></td>
				<td><input type="button" value="添加文件" onclick="addInput()" style="height:20px;line-height:10px"></td>
			</tr>
			<tr>
				<td><input type="button" value="上传备份" onclick="uploadFile()"></td>
			</tr>
		</table>
	</form>
</center>
</body>
</html>