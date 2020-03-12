<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>部门管理</title>
		<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/module.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/js/util.js'></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
	<script Language="JavaScript">
		function uploadFile(){
			var f = document.getElementById("myForm");
			if(f.file_path.value.Trim()==""){
			    alert("请选择文件！");
				return false;
			}else if(f.file_path.value.Trim().split(".")[1]!="xls"){
			    f.file_path.value="";
			    alert("请选择Excel格式文件xls2003！");
			    return false;
			}
			f.submit();
		}
	</script>
	</head>

	<body class="bodycolor" topmargin="5">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/notify_new.gif" height="22" WIDTH="22">
					<span class="big3"> 批量导入用户</span>
				</td>
			</tr>
		</table>
	<form method="post" encType="multipart/form-data" id="myForm" action="/Seal/uploadDeptAndUser.do">
	<table class="TableBlock" width="450" align="center">
		<tr>
					<td nowrap class="TableData">选择用户列表Excel文件：</td>
					<td nowrap class="TableData">
						<input type="file" id="file_path" name="file_path" />
						<input type="hidden" id="type" name="type" value="user"/>
					</td>
					<td nowrap class="TableData">
						<input type="button" onclick="uploadFile()" value="导入">
					</td>
		</tr>
	</table>
    </form>
	<br>
	</body>
</html>