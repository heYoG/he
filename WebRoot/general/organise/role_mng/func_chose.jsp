<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/SysFunc.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/menuUtil.js"></script>
		<script src="/Seal/general/organise/role_mng/js/func_chose.js"></script>
	</head>
	<body class="bodycolor" onload="load();">
		<center>
			<table border="0" width="100%" cellspacing="0" cellpadding="3"
				class="small">
				<tr>
					<td class="Big">
						<img src="/Seal/images/edit.gif" WIDTH="22" HEIGHT="20">
						<span class="big3"> 角色权限设置</span>&nbsp;&nbsp;
						<div id="OP_BTN"
							style="width: 150px; top: 5px; right: 20px; position: absolute;">
						</div>
					</td>
				</tr>
			</table>
			<div id="d_list"></div>
			<input type="button" class="BigButton" value="确 定"
				id="bt_yes" onclick="mysubmit();">
			<input type="button" class="BigButton" value="关 闭"
				onclick="myclose();">
		</center>
	</body>
</html>