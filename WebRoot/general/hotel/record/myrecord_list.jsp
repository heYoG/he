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
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/HotelDic.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/record/js/myrecord_list.js"></script>
		<script src="/Seal/general/hotel/record/js/fromZY.js"></script>
		<script type='text/javascript'
			src='/Seal/dwr/interface/HotelRecord.js'></script>

		<script type="text/javascript">
		var user_id="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		
		function openDeptList() {
		 	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
  		}
  		 
			
		</script>
	</head>
	<body class="bodycolor" onload="list_load();">
				<div id="d_search" >
						<form id="f_sch" action="" method="post" name="form1">
							<table class="TableBlock" id="tb_info" width="100%">
								<tr>
									<td nowrap class="TableData">
										<strong>交易流水: </strong><input type="text" id="serialNo" name="serialNo" />
										<input type="hidden" id="createuserid" name="createuserid" />
									</td>
									<td nowrap class="TableData">
										<strong>业务类型: </strong><input type="text" id="businessType" name="businessType" />
									</td>
								</tr>
								<tr>
								<td nowrap class="TableData">
										<strong>单据状态：</strong><select id="cstatus" name="cstatus">
											<option value="" selected="selected">
												全 部
											</option>
											<option value="1">
												正 常
											</option>
											<option value="0">
												作 废
											</option>
										</select>
									</td>
									<td nowrap colspan="2" class="TableData">
										<strong>查询时限：</strong><select id="date_type" name="date_type" onchange="searchdatechange(this.value)">
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
										<table id="datetable" style="display:none" border="0">
											<tr><td nowrap>
												<input type="text" id="begin_time" name="begin_time" size="12"  onClick="GetDate(1)">
												至
												<input type="text" id="end_time" name="end_time" size="12"  onClick="GetDate(2)">
											</td></tr>
										</table>
									</td>
								</tr>
								<tr align="center">
									<td colspan="4" align="center" class="TableData">
										<input type="button" value="搜索" class="BigButton"
											onclick="searchRecord()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="reset" value="清空" class="BigButton" />
									</td>
								</tr>
							</table>
						</form>
					</div>
		<center>
			<div id="d_mylist">
				<table width="100%">
					<tr>
						<td>
							<div id="div_table" align="center">
								<img src='/Seal/images/loading.gif' align='absMiddle'> 正在加载，请稍候……
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
			<input style="display: none" type="button" name="btch" value="统计查询" onclick="getdateOrderRooId()">
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