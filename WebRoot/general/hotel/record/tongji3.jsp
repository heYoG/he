<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/2/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/2/dialog.css">
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/record/js/tongji3.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/HotelRecord.js'></script>

		<script type="text/javascript">

		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var dept_no="${current_user.dept_no}";
		var dept_name="${current_user.dept_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		
			
		</script>
	</head>
	<body class="bodycolor" onload="list_load('${current_user.user_id}');">
					<div id="d_search">
						<form id="f_sch" action="" method="post" name="form1">
							<table class="TableBlock" id="tb_info" width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td nowrap class="TableData">
										<strong>统计时限：</strong>
										<select id="date_type" name="date_type" onchange="searchdatechange(this.value)">
											<option value="1" selected="selected">
												当 天
											</option>
											<option value="2">
												当 月
											</option>
											
										</select>
										<table id="datetable" style="display:none" border="0" cellpadding="0" cellspacing="0">
											<tr><td nowrap>
												<input type="text" id="begin_time" name="begin_time" size="12"  onClick="GetDate(1)">
												至
												<input type="text" id="end_time" name="end_time" size="12"  onClick="GetDate(2)">
											</td></tr>
										</table>
									</td>
									<td nowrap class="TableData" id="showname" name="showname"></td>
								</tr>
								<tr align="center">
									<td align="center" colspan=2 class="TableData">
										<input type="button" value="确定" class="BigButton"
											onclick="searchRecord()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="reset" value="清空" class="BigButton" />
									</td>
								</tr>
							</table>
						</form>
					</div>
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
				</table>
			</div>

		</center>
	</body>
</html>