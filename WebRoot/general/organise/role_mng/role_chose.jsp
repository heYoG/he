<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/SysRole.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/general/organise/role_mng/js/role_chose.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		</script>
	</head>
	<body class="bodycolor" onload="load();">
		<div id="d_busi">
			<table border="0" width="100%" cellspacing="0" cellpadding="3"
				class="small">
				<tr>
					<td class="Big">
						<img src="/Seal/images/menu/seal.gif" align="absmiddle">
						<span class="big3"> 角色选择</span>
					</td>
				</tr>
			</table>
			<center>
				<table class="TableBlock" width="300" align="center">
					<tr>
						<td valign="top" align="center" id="h1">
							可选角色
						</td>
						<td id="h2"></td>
						<td valign="top" align="center">
							已选角色
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" id="h3">
							<select multiple="multiple" id="sel_all"
								style="width: 200; height: 280">
							</select>
						</td>
						<td id="h4" align="center" width="10">
							<input type="button" value=&gt; onclick="javascript:toRight();"
								style="font: 9pt 宋体; width: 30px; height: 30px; margin: 0px; padding: 0px; color: #333333; background: #CCCCCC; border: 0px solid #0000FF; border: 3px double #DDDDDD; cursor: pointer;" />
							<br>
							<input type="button" value=&lt; onclick="javascript:toLeft();"
								style="font: 9pt 宋体; width: 30px; height: 30px; margin: 0px; padding: 0px; color: #333333; background: #CCCCCC; border: 0px solid #0000FF; border: 3px double #DDDDDD; cursor: pointer;" />
						</td>
						<td nowrap class="TableData">
							<select multiple="multiple" id="sel_sel"
								style="width: 200; height: 280">
							</select>
						</td>
					</tr>
					<tr id="tr_ctrl" class="TableControl">
						<td align="center" valign="top" colspan="3">
							<input id="bt_yes" type="button" class="BigButton" value="确 定"
								onclick="mysubmit();">
							<input type="button" class="BigButton" value="关 闭"
								onclick="myclose();">
						</td>
					</tr>
				</table>
			</center>
		</div>
	</body>
</html>