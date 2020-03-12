<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/log/js/log_list.js"></script>
		<script src="/Seal/general/hotel/log/js/fromZY.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/ClientOperLog.js'></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		
		</script>
	</head>
	<body class="bodycolor" onload="list_load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/aip_file.gif">
					<span class="big3"> 日志列表 </span>
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
								onclick="show_sch()" />
						</td>
					</tr>
				</table>
			</div>


			<div id="d_search" style="display: none">
				<form id="f_sch" action="" method="post" name="form1">
					<table class="TableBlock" id="tb_info">
						<tr>
							<th>
								用户姓名:
							</th>
							<td width="300px">
								<input name="userName" />
							</td>
							<th>
								对象名称:
							</th>
							<td>
								<input name="objName"  type="text"/>
							</td>
						</tr>
						<tr>
							<th>
								操作类型:
							</th>
							<td>
								<select name="operType">
									<option value="">
										全 部
									</option>
									<option value="1">登录</option>
									<option value="2">上传文件</option>
								</select>
							</td>
							<th>操作结果：</th>
							<td>
							<select name="operRet">
									<option value="">
										全 部
									</option>
									<option value="0"> 成功</option>
									<option value="1"> 失败</option>
					
								</select>
							</td>
						</tr>
						<tr>
							
							<th>IP地址：</th>
							<td><input type="text" name="operIP" id="operIP" />
						</tr>
						<tr>
							<th>查询时限:</th>
							<td>
								<select id="date_type" name="date_type" onchange="searchdatechange(this.value)">
											<option value="1" selected="selected">
												当 天
											</option>
											<option value="2">
												当 月
											</option>
											<option value="7">
												自定义
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
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" value="搜索" class="BigButton"
									onclick="searchRecord()" />
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

				<div id="apply_body" class="body" align="center">
					<form id="f_edit" action="" method="post">

						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
								<td>
									<div id="xiangqing_table"></div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="HideDialog('detail')" type="button" value="关闭" />
				</div>
			</div>

			<div id="showSeal" class="ModalDialog" style="width: 300px;">

				<div id="apply_body" class="body" align="center">
					<table width="90%" height="200">
						<tr>
							<td width="100%" height="100%">

								<div id="seal_body_info" class="body" align="center">

								</div>
							</td>
						</tr>
					</table>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="HideDialog('showSeal')"
						type="button" value="关闭" />
				</div>
			</div>
		</center>
	</body>
</html>