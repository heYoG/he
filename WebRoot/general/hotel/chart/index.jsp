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
		<script type='text/javascript' src='/Seal/dwr/interface/chartSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/dataUtil/tongji.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script src="/Seal/js/dateUtil/tongji.js"></script>
		<script src="/Seal/general/hotel/chart/js/index.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		</script>
	</head>
	<body class="bodycolor" onload="load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 状态查看</span>
				</td>
			</tr>
		</table>
		<center>
			<br>
			<table align="center" width="80%">
				<tr>
					<td align="center">
						<div id="div_basic"></div>
						<br>
						单据信息
					</td>
					<td align="center">
						<div id="div_busi"></div>
						<br>
						业务信息
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>