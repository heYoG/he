<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/SysRole.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/organise/role_mng/js/new_role.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		var user_name="${current_user.user_name}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		</script>
	</head>
	<body class="bodycolor" onload="new_role_load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif">
					<span class="big3"> 新增角色</span>
				</td>
			</tr>
		</table>
		<center>
			<br>
			<form id="f_new" action="/Seal/roleAdd.do" method="post">
				<table class="TableBlock" width="80%" id="tb_info">
					<tr>
						<td width="15%" class="TableContent">
							角色排序号：
						</td>
						<td nowrap class="TableData">
							<input type="text" maxlength="3" name="role_tab" class="BigInput" />
						</td>
					</tr>
					<tr>
						<td width="15%" class="TableContent">
							角色名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="role_name" onblur="chkName(this);"
								class="BigInput" maxlength="30" />
						</td>
					</tr>
					<tr>
						<td width="15%" class="TableContent">
							菜单权限：
						</td>
						<td nowrap class="TableData">
							<input type="hidden" name="func_v" value="0" />
							<input type="hidden" name="func_v2" value="0" />
							<a href="#" onclick="funcChose('f_new');return false;">详细</a>
						</td>
					</tr>
					<tr style="display: none">
						<td width="15%" class="TableContent">
							角色用户数：
						</td>
						<td nowrap class="TableData">
							<input type="hidden" name="sel_users" />
							<span id="user_num">0</span>&nbsp;&nbsp;
							<a href="#" onclick="userChose('f_new','user_num');return false;">更改设置</a>
						</td>
					</tr>
					<tr style="display: none">
						<td width="15%" class="TableContent">
							角色规则数：
						</td>
						<td nowrap class="TableData">
							<input type="hidden" name="sel_rules" />
							<span id="rule_num">0</span>&nbsp;&nbsp;
							<a href="#" onclick="ruleChose('f_new','rule_num');return false;">更改设置</a>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center" nowrap class="TableControl">
							<input type="button" value="确定" onclick="newObj();"
								class="BigButton" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="history.go(-1);"
								class="BigButton" />
						</td>
					</tr>
				</table>
			</form>
		</center>
	</body>
</html>