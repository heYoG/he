<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/hotel/judge/js/new_jud.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
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
					<span class="big3"> 新增条件</span>
				</td>
			</tr>
		</table>
		<center>
			<br>
			<form id="f_new" action="/Seal/newjud.do" method="post">
				<table class="TableBlock" width="80%" id="tb_info">

					<tr>
						<td width="15%" class="TableContent">
							判定属性：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="c_name" class="BigInput" />
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							所属模版：
						</td>
						<td nowrap class="TableData">
							<select name="master_platecid" class="BigSelect">
							</select>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							属性值：
						</td>
						<td nowrap class="TableData">
						<select class="BigSelect" name="c_valuetype" onchange="change(this.options[this.options.selectedIndex].value)">
						<option value="1" selected="selected">非空</option>
						<option value="2">空</option>
						<option value="3">特定值</option>
						</select>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							特定值：
						</td>
						<td nowrap class="TableData">
							<input id="c_value" type="text" name="c_value" style="visibility:hidden" class="BigInput" />
						</td>
					</tr>
					
					<tr>
						<td colspan="4" align="center" nowrap class="TableControl">
							<input type="button" value="确定" onclick="newjud();"
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