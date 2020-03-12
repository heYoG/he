<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/MFRSrv.js'></script>
		 <script type='text/javascript' src='/Seal/dwr/interface/GaiZhangRuleSrv.js'></script>
		 <script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/modelFileRule/js/new_modelFileRule.js"></script>
		<script src="/Seal/general/modelFileRule/js/fromZY.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
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
					<img src="/Seal/images/menu/seal.gif">
					<span class="big3"> 新增规则</span>
				</td>
			</tr>
		</table>
		<center>
			<br>
			<form id="f_new" action="/Seal/newMFR.do" method="post">
				<table class="TableBlock" width="30%" id="tb_info">

					<tr>
						<td width="20%" class="TableContent">
							模板名称：
						</td>
						<td nowrap class="TableData">
							<select id="model" name="model_id">
							
							</select>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							规则名称：
						</td>
						<td nowrap class="TableData">
							<select id="rule" name="rule_no">
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center" nowrap class="TableControl">
							<input type="button" value="确定" onclick="newMFR();"
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