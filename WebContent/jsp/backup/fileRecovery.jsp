<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String path=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件还原</title>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.mobile.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/css/fileManage.css">
<style type="text/css">
	table{
		border-collapse:collapse;
	}
	td{
		text-align:center;
	}
</style>
<script type="text/javascript">
	$(function(){
		$("#searchFile").click(function(){
			var v1=recoveryForm.fileName.value;
			var v2=recoveryForm.userName.value;
			var v3=recoveryForm.uploadDate.value;
			/*type=0表示查询已删除文件*/
			location.href="fileManage!recoveryFileList.action?fileName="+v1+"&userNo="+v2+"&date="+v3+"&type=0";
		});
	});
</script>
</head>
<body>
	<center>
		<form action="" method="post" name="recoveryForm">
			<table>
				<tr><td><input type="text" placeholder="输入文件名检索"  title="可为空" name="fileName" disabled="true"></td></tr>
				<tr><td><input type="text" placeholder="当前用户名检索" title="可为空" name="userName" disabled="true"></td></tr>
				<tr><td><input type="text" placeholder="上传日期检索" title="可为空" name="uploadDate" disabled="true"></td></tr><!-- 集成日期表,按年、月、日查 -->
				<tr><td><input type="button" id="searchFile" value="搜索" title="条件为空则默认搜索当前用户所有可恢复文件"></td></tr>
			</table>
		</form>
	</center>
</body>
</html>