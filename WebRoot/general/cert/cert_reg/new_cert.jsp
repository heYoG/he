<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/cert/cert_reg/js/new_cert.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		</script>
	</head>
	<body class="bodycolor" onload="new_cert_load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif">
					<span class="big3"> 新增证书登记</span>
				</td>
			</tr>
		</table>
		<center>
			<br>
			<form enctype="multipart/form-data" name="f_new"
				action="/Seal/certAdd.do" method="post">
				<input type="hidden" name="reg_user" />
				<table class="TableBlock" width="80%" id="tb_info">
					<tr>
						<td width="15%" class="TableContent">
							证书名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" maxlength="30" name="cert_name" class="BigInput" />
						</td>
					</tr>
					<tr>
						<td width="15%" class="TableContent">
							证书密码：
						</td>
						<td nowrap class="TableData">
							<input type="password" maxlength="30" name="cert_psd" class="BigInput" />
						</td>
					</tr>
					<tr>
						<td width="15%" class="TableContent">
							证书确认密码：
						</td>
						<td nowrap class="TableData">
							<input type="password" maxlength="30" name="cert_psd1" class="BigInput" />
						</td>
					</tr>
					<tr style="display:none">
						<td width="15%" class="TableContent">
							证书类型：
						</td>
						<td nowrap class="TableData">
							<select class="BigSelect" name="cert_type">
								<option value="private">
									个人证书
								</option>
								<option value="public" selected="selected">
									公用证书
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="15%" class="TableContent">
							来源类型：
							<input type="hidden" name="cert_detail" />
						</td>
						<td nowrap class="TableData">
							<select name="cert_src" class="BigSelect"
								onchange="certSrcChg(this);">
								<option value="server" selected="selected">
									服务器证书
								</option>
								<option value="sign" >
									加密机证书
								</option>
							</select>
						</td>
					</tr>
					<tr id="tr_sign" style="display: none">
						<td class="TableContent">
							指定签名证书：
						</td>
						<td nowrap class="TableData">
							<input  type="text" name="sign_cert">
						</td>
					</tr>
					<tr id="tr_server">
						<td class="TableContent">
							证书路径：
						</td>
						<td nowrap class="TableData">
							<input type="file" name="cert_path" id="cert_path" class="BigInput"
								onchange="chkServerCert(this);" />
						</td>
					</tr>
					<tr id="tr_key" style="display: none">
						<td class="TableContent">
							客户端获取：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="cert_no" readonly="readonly"
								class="BigInput" />
							<input type="button" value="自动获取" class="SmallButton"
								onclick="getCertNo();" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" nowrap class="TableControl">
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