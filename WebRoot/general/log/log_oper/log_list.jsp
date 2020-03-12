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
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
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
		<script src="/Seal/general/log/log_oper/js/log_list.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		function openmodwin() {
		alert("aa");
	    var b = window.showModalDialog("/Seal/general/dept_tree.jsp?p=true&&req=seal_temp11&&user_no=${current_user.user_id }",form1);
       }
		</script>
	</head>
	<body class="bodycolor" onload="load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 系统操作日志列表</span>
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
					<tr style="display:none">
						<td align="center">
							<br>
							<input type="button" value="返回查询" class="BigButton"
								onclick="show_sch();" />
						</td>
					</tr>
				</table>
			</div>
			<div id="d_search" style="display: none">
				<form id="f_sch" action=""  name="form1" method="post">
					<%@include file="/inc/calender.jsp"%>
					<table class="TableBlock" width="90%" align="center">
					<tr>
					<td nowrap class="TableData" width="120">
						对象编号：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="obj_no" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						对象名称：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="obj_name" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						对象类型：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="obj_type" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						IP：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_ip" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
						<td nowrap class="TableData" width="120">
								用户名:
							</td>
						<td nowrap class="TableData">
						 <input type="text" name="create_name2"  readonly class="BigInput" size="20"
							maxlength="20">
						<input type="hidden" name="create_name"  class="BigInput" size="20"
							maxlength="20">
							<a href="" onclick="openmodwin();return false;">选择用户</a>
						&nbsp;
					</td>
						</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						时间：
					</td>
					<td nowrap class="TableData">

						<input type="text" name="begin_time" size="20" maxlength="20"
							class="BigInput">
						<img onclick="setday(this,document.getElementById('begin_time'));"
							src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />
						至&nbsp;
						<input type="text" name="end_time" size="20" maxlength="20"
							class="BigInput">
						<img onclick="setday(this,document.getElementById('end_time'));"
							src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />

					</td>
				</tr>
						<tr>
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="查询" class="BigButton"
									onclick="search();" />
								<input type="reset" value="清空" class="BigButton" />
								<input type="button" value="返回" class="BigButton"
									onclick="ret();" />
							</td>
						</tr>
					</table>
				</form>
			</div>
	</body>
</html>