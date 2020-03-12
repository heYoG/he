<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>印章查询</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/module.js"></script>
		<script Language="JavaScript">
		
function Submit(){
	document.form1.submit();
}
</script>
	</head>
	<body class="bodycolor" topmargin="0">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/query.gif" align="absmiddle">
					<span class="big3">导入印章数据</span>
				</td>
			</tr>
		</table>
		<form action="importSeal.do?type=1" method="post" name="form1" enctype="multipart/form-data">
			<table class="TableBlock" width="90%" align="center">
				<tr>
					<td nowrap class="TableData" width="120">
						请选择导入的文件：
					</td>
					
					<td nowrap class="TableData">
							<input type="file" name="seal_path" class="BigInput">
						</td>
						&nbsp;
				</tr>
				<tr nowrap class="TableData" width="120">
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="button" value="导入印章数据" class="BigButton"
							onclick="Submit();" title="导入印章数据" name="button">
						&nbsp;&nbsp;
					</td>
			</table>
		</form>
	</body>
</html>