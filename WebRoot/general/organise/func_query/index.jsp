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
		<script type='text/javascript' src='/Seal/dwr/interface/SysFunc.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/organise/func_query/js/func_list.js"></script>
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
					<span class="big3"> 菜单权限列表</span>
				</td>
			</tr>
		</table>
		<center>
			<div id="d_list">
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
					<tr>
						<td align="center">
							<input style="display: none" type="button" value="批量停用"
								class="SmallButton" onclick="pl_stat('2');" />
							<input style="display: none" type="button" value="批量启用"
								class="SmallButton" onclick="pl_stat('1');" />
							<input style="display: none" type="button" value="批量删除" class="SmallButton"
								onclick="pl_delObj();" />
						</td>
					</tr>
					<tr>
						<td align="center">
							<br>
							<input type="button" value="高级搜索" class="BigButton"
								onclick="show_sch();" />
						</td>
					</tr>
				</table>
			</div>
			<div id="d_search" style="display: none">
				<form id="f_sch" action="" method="post">
					<table class="TableBlock" id="tb_info">
						<tr>
							<td>
								菜单模块：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="menu_name" class="BigInput" />
							</td>
						</tr>
						<tr>
							<td>
								子菜单菜单权限：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="func_name" class="BigInput" />
							</td>
						</tr>
						<tr style="display:none">
							<td>
								可见用户数：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="user_num" class="BigInput" />
							</td>
						</tr>
						<tr style="display:none">
							<td>
								可见角色数：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="role_num" class="BigInput" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="搜索" class="BigButton"
									onclick="search();" />
								<input type="reset" value="清空" class="BigButton" />
								<input type="button" value="返回" class="BigButton"
									onclick="ret();" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="overlay"></div>
			<div id="detail" class="ModalDialog" style="width: 600px;">
				<div class="header">
					<span id="title" class="title">角色详细信息</span><a class="operation"
						href="javascript:HideDialog('detail');"><img
							src="/citizen/images/close.png" /> </a>
				</div>
				<div id="apply_body" class="body" align="center">
					<form id="f_edit" action="/Seal/ruleUpd.do" method="post">
						<input type="hidden" name="role_id" />
						<input type="hidden" name="old_name" />
						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
								<td class="TableContent">
									角色排序号：
								</td>
								<td nowrap class="TableData">
									<input type="text" name="role_tab" class="BigInput" />
								</td>
							</tr>
							<tr>
								<td class="TableContent">
									角色名称：
								</td>
								<td nowrap class="TableData">
									<input type="text" name="role_name" onblur="chkName(this);"
										class="BigInput" />
								</td>
							</tr>
							<tr>
								<td width="15%" class="TableContent">
									菜单权限：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="func_v" value="0" />
									<a href="#" onclick="funcChose('f_edit');return false;">详细</a>
								</td>
							</tr>
							<tr>
								<td width="15%" class="TableContent">
									角色用户数：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="sel_users" />
									<span id="user_num">0</span>&nbsp;&nbsp;
									<a href="#"
										onclick="userChose('f_edit','user_num');return false;">更改设置</a>
								</td>
							</tr>
							<tr>
								<td width="15%" class="TableContent">
									角色规则数：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="sel_rules" />
									<span id="rule_num">0</span>&nbsp;&nbsp;
									<a href="#"
										onclick="ruleChose('f_edit','rule_num');return false;">更改设置</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="objUpd();" type="button"
						value="修改" />
					<input class="BigButton" onclick="HideDialog('detail')"
						type="button" value="关闭" />
				</div>
			</div>
		</center>
	</body>
</html>