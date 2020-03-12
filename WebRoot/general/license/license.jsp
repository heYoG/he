<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/dialog.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LicenseInfo.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script><!-- 用于使用DWR查询时创建表格 -->
		<script src="/Seal/js/pageSplit.js"></script><!-- 用于使用DWR查询时分页 -->
		<script src="/Seal/js/dialog.js"></script>
		<script src="/Seal/general/license/js/license.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		var user_name="${current_user.user_name}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		</script>
	</head>
	<body class="bodycolor" onload="load()">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 授权信息查询</span>
				</td>
			</tr>
		</table>
				<table width="100%">
					<tr>
						<td>
							<div id="div_table">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="pager" align="left" id="pager">
							</div>
						</td>
					</tr>
				</table>
	</body>
</html>