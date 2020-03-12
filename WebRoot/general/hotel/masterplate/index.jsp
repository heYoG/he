<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>模板管理</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/dialog.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script src="/Seal/js/utility.js" type="text/javascript"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/masterplate/js/index.js"
			type="text/javascript"></script>
		<script src="/Seal/general/hotel/masterplate/js/seal.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		function openDeptList(form) {
			var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form);
  		}
		</script>

		<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			aip_init();
		</SCRIPT>
		<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyLineAction(lPage,lStartPos,lEndPos)>
          NotifyLineAction2(lPage,lStartPos,lEndPos);
		</script>
	</head>
	<body class="bodycolor" topmargin="5" onload="load();">
		<div id="d_new">
		</div><br />
		<div id="d_search">
			<form id="ad_sch" action=""  method="post" name="form2">
				<table class="TableList" id="tb_info" align="center">						
					<tr>
						<td class="TableContent">
							模板名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" id="model_name" name=model_name class="BigInput"/>
						</td>
						<td class="TableContent">
							所属单位：
						</td>
						<td nowrap class="TableData">
							<input type="hidden"  id="sdept_no" name="dept_no" value="${current_user.dept_no}"/>
							<input type="text" name="dept_name" id="sdept_name" readonly="readonly"
								value="${current_user.dept_name }" />
							<input type="button" value="选 择" class="SmallButton"
								onclick="return openDeptList(form2)" title="选择部门">
						</td>
						<td colspan="4" align="center" nowrap class="TableControl">
							<input type="button" value="搜索" class="BigButton"
								onclick="searchModel()" />
							<input type="reset" value="清空" class="BigButton" />
						</td>
					</tr>
				</table>
			</form>
		</div>

		<br />
		<div id="d_list">
			<table border="0" width="100%" cellspacing="0" cellpadding="3"
				class="small">
				<tr>
					<td class="Big">
						<img src="/Seal/images/menu/info_query.gif" align="middle">
						<span class="big3"> 模板审批</span>
					</td>
				</tr>
			</table>
			<table width="100%" align="center">
				<tr>
					<td>
						<div id="div_table">
						</div>
					</td>
				</tr>
				<tr class="TableControl">
					<td colspan="11">
						<input type="checkbox" name="allbox" id="allbox_for"
							onClick="check_all();">
						<label for="allbox_for">
							全选
						</label>
						&nbsp;
						<input type="button" class="SmallButton" value="同意"
							onClick="approve2('',1,'ALL')">
						<input type="button" class="SmallButton" value="拒绝"
							onClick="approve2('',2,'ALL')">
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
		<div id="d_aip">
		</div>
		<div id="overlay"></div>
		<div id="approve" class="ModalDialog" style="width: 500px;">
			<div class="header">
				<span class="title">审批意见</span><a class="operation"
					href="javascript:HideDialog('approve');"><img
						src="/Seal/images/close.png" /> </a>
			</div>
			<form id="form2" method="post" action="adApp.do" onsubmit="return doButton();" >
				<div id="approve_body" class="body">
					<span id="confirm"></span>
					<textarea id="context" name="approve_text" cols="60" rows="5"
						style="overflow-y: auto;" class="BigInput" wrap="yes"></textarea>
					<br>
					&nbsp;&nbsp;
				</div>
				<input type="hidden" name="leixing" id="leixing">
				<input type="hidden" name="model_id" id="model_id">
				<input type="hidden" name="this_name" id="this_name">
				<input type="hidden" name="approve_status" id="approve_status">
				<input type="hidden" name="approver_reason" id="approver_reason"/>
				<input type="hidden" name="approver" id="approver"/>
				<div id="footer" class="footer">
					<input class="BigButton" type="button" onclick="doButton()" value="确定" />
					<input class="BigButton" onclick="HideDialog('approve')"
						type="button" value="关闭" />
				</div>
			</form>
		</div>
	</body>
</html>