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
		<script type='text/javascript' src='/Seal/dwr/interface/LogSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript'
			src='/Seal/dwr/interface/GaiZhangApp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/cert/cert_reg/js/new_cert.js"></script>
		<script src="/Seal/general/cert/cert_query/js/cert_list.js"></script>
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
					<img src="/Seal/images/menu/seal.gif">
					<span class="big3"> 证书查询</span>
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
								证书名称：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="cert_name" class="BigInput" />
							</td>
						</tr>
						<tr>
							<td>
								证书状态：
							</td>
							<td nowrap class="TableData">
								<select class="BigSelect" name="status_cert">
									<option value="0">
										请选择
									</option>
									<option value="2">
										正常
									</option>
									<option value="1">
										注销
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								指定签名证书：
							</td>
							<td nowrap class="TableData">
								<select name="sign_cert" class="BigSelect">
									<option value="0">
										请选择
									</option>
								</select>
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
			<div id="detail" class="ModalDialog" style="width: 500px;">
				<div class="header">
					<span id="title" class="title">业务详细信息</span><a class="operation"
						href="javascript:HideDialog('detail');"><img
							src="/Seal/images/close.png" /> </a>
				</div>
				<div id="apply_body" class="body" align="center">
					<form id="f_edit" action="" method="post">
						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
								<td class="TableContent">
									业务编号：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="busi_no" />
									<input type="text" name="app_type" class="BigInput"
										onblur="checkApp(this);" />
								</td>
							</tr>
							<tr>
								<td class="TableContent">
									业务名称：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="old_name" />
									<input type="text" name="busi_name" class="BigInput" />
								</td>
							</tr>
							<tr>
								<td class="TableContent">
									是否绑定模板：
								</td>
								<td nowrap class="TableData">
									<input type="checkbox" name="band_chk" checked="checked"
										onclick="chkChg(this);" />
									绑定模板
								</td>
							</tr>
							<tr id="tr_model">
								<td class="TableContent">
									业务模板：
								</td>
								<td nowrap class="TableData">
									<select name="model_no" class="BigSelect">
									</select>
								</td>
							</tr>
							<tr>
								<td class="TableContent">
									业务状态：
								</td>
								<td nowrap class="TableData">
									<select class="BigSelect" name="status_busi">
										<option value="1">
											正常
										</option>
										<option value="2">
											停用
										</option>
									</select>
								</td>
							</tr>
							<tr id="tr_certDN">
								<td class="TableContent">
									业务描述：
								</td>
								<td nowrap class="TableData">
									<textarea name="busi_discript" rows="6" cols="43"
										class="BigInput"></textarea>
									<br>
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