<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>无纸化管理平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/dialog.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/AdSystem.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/advert/js/approve_list.js"></script>
		<script src="/Seal/general/hotel/advert/js/new_ad.js"></script>
		<script src="/Seal/general/hotel/advert/js/fromZY.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
	
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
	</head>
	<body class="bodycolor" onload="list_load('${current_user.user_id }');">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 待审批列表</span>
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
					<tr class="TableControl">
					<td colspan="11">
						<input type="checkbox" name="allbox" id="allbox_for"
							onClick="check_all();">
						<label for="allbox_for">
							全选
						</label>
						&nbsp;
						<input type="button" class="SmallButton" value="同意"
							onClick="approve2('',2,'ALL')">
						<input type="button" class="SmallButton" value="拒绝"
							onClick="approve2('',1,'ALL')">
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
							<input type="button" value="高级搜索" class="BigButton"
								onclick="show_sch()" />
						</td>
					</tr>
				</table>
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
				<input type="hidden" name="ad_id" id="ad_id">
				<input type="hidden" name="this_title" id="this_title">
				<input type="hidden" name="ad_statu" id="ad_statu">
				<input type="hidden" name="ad_opinion" id="ad_opinion"/>
				<input type="hidden" name="approve_user" id="approve_user"/>
				<div id="footer" class="footer">
					<input class="BigButton" type="button" onclick="doButton()" value="确定" />
					<input class="BigButton" onclick="HideDialog('approve')"
						type="button" value="关闭" />
				</div>
			</form>
		</div>
			<div id="d_search" style="display: none" >
				<form id="ad_sch" action="" method="post" name="form2">
					<table class="TableBlock" id="tb_info">
						<tr>
						<td width="20%" class="TableContent">
							广告标题：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_title" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							文件名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_filename" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							创建时间：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_ctime" class="BigInput" onclick="GetDate()"/>
						</td>
					</tr>
					<tr id="s_dept">
							<td class="TableContent">
								所属单位：
							</td>
							<td nowrap class="TableData">
								<input type="hidden" name="ad_dept" />
								<input type="hidden" name="dept_no" value="${seal.dept_no }">
								<input type="text" name="dept_name" readonly="readonly"
									value="${seal.dept_name }" />
								<input type="button" value="选 择" class="SmallButton"
									onclick="return openDeptList(form2);" title="选择部门">
							</td>
						</tr>
					<tr>
						<tr>
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="搜索" class="BigButton"
									onclick="searchAD()" />
								<input type="reset" value="清空" class="BigButton" />
								<input type="button" value="返回" class="BigButton"
									onclick="ret();" />
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div id="overlay"></div>


			<div id="detail" class="ModalDialog" style="width: 400px;">
				<div class="header">
					<span id="title" class="title">广告详细信息</span><a class="operation"
						href="javascript:HideDialog('detail');"><img
							src="/Seal/images/close.png" /> </a>
				</div>
				<div id="apply_body" class="body" align="center">
					<form id="f_edit" enctype="multipart/form-data" action="" method="post" name="form1">
						<input type="hidden" name="old_title" >
						<input type="hidden" name="old_filename"/> 
						<input type="hidden" name="old_ctime"/>
						<input type="hidden" name="old_dept"/>
						<input type="hidden" name="ad_id"/>
						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
						<td width="20%" class="TableContent">
							广告标题：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_title" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							文件名称：
						</td>
						<td nowrap class="TableData">
							<input type="hidden" name="old_filename"/>
							<input type="text" name="ad_filename" readonly="readonly" class="BigInput"/><input name="filebutton" type="button" value="修改" onclick="changeFile()" />
							<input type="file" name="ad_name" style="display: none" >
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							创建时间：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_ctime" readonly="readonly" class="BigInput"/><font style="color: red">创建时间不可修改</font>
						</td>
					</tr>
					<tr id="u_dept">
							<td class="TableContent">
								所属单位：
							</td>
							<td nowrap class="TableData">
								<input type="hidden" name="ad_dept" />
								<input type="hidden" name="dept_no" value="${seal.dept_no }">
								<input type="text" name="dept_name" readonly="readonly"
									value="${seal.dept_name }" />
								<input type="button" value="选 择" class="SmallButton"
									onclick="return openDeptList(form1);" title="选择部门">
							</td>
						</tr>
					<tr>
						</table>
					</form>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="objUpd();" type="button"
						value="修改" />
					<input class="BigButton" onclick="HideDialog('detail');" type="button" value="关闭" />
				</div>
			</div>
		
			<div id="showSeal" class="ModalDialog" style="width: 300px;">

				<div id="apply_body" class="body" align="center">
					<table width="90%" height="200">
						<tr>
							<td width="100%" height="100%">

								<div id="seal_body_info" class="body" align="center">

								</div>
							<br></td>
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