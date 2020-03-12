<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>新建模版</title>
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
		<script src="/Seal/general/hotel/masterplate/js/newxieyi.js"
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
			<table border="0" width="100%" cellspacing="0" cellpadding="3"
				class="small">
				<tr>
					<td class="Big">
						<img src="/Seal/images/notify_new.gif" align="middle">
						<span class="big3"> 新建协议</span>
						<br>
					</td>
				</tr>
			</table>
			<form enctype="multipart/form-data" styleId="form1">
				<table class="TableList" width="80%" align="center">
					<tr>
						<td class="TableContent" width="20%">
							协议名称
						</td>
						<td class="TableData" width="35%">
							<input type="text" class="BigInput" size=30 name="tname"
								id="tname">
						</td>
						<td class="TableControl">
							<input type="button" value="新建协议"
								onclick="loadAip(1);" class="SmallButton" />
						</td>
					</tr>
					<tr style="display: none">
						<td class="TableContent">
							协议文件
						</td>
						<td class="TableData" colspan=2>
							<input style="display: none" class="BigInput" type="file" name="tfile" id="tfile"
								onchange="sel_file();">
							<span id="file_name"><input type="text" /></span>
							<input type="button" value="浏览"
								class="SmallInput" onclick="select_file();">
							（word文档）
						</td>
					</tr>
				</table>
			</form>
			<br>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				height="3">
				<tr>
					<td background="/Seal/images/dian1.gif" width="100%"></td>
				</tr>
			</table>
		</div>

		<br />
		<div id="d_list">
			<table border="0" width="100%" cellspacing="0" cellpadding="3"
				class="small">
				<tr>
					<td class="Big">
						<img src="/Seal/images/menu/info_query.gif" align="middle">
						<span class="big3"> 协议管理</span>
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
				<tr>
					<td>
						<div class="pager" align="left" id="pager">
						</div>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input style="display: none" type="button" onclick="showNew();"
							value="新增文档" />
						<input style="display: none" type="button" value="查询" />
					</td>
				</tr>
			</table>
		</div>

		<div id="d_aip">
			<form  name="form1" id="form1">
				<table border="0" width="100%" cellspacing="0" cellpadding="3"
					class="small">
					<tr>
						<td class="Big">
							<img src="/Seal/images/print.gif" align="middle" align="middle">
							<span class="big3">编辑协议</span><span class="small"
								style="color: red; display: none">(在协议区域内拖动鼠标以添加映射区域)</span>
						</td>
						<td align=right>
							<input style="display: none" type="button" class="SmallButton"
								value="添加映射区域" onclick="addField()" />
							&nbsp;
							<input type="button" class="SmallButton" value="保存"
								onclick="saveFile();" />
							&nbsp;
							<input type="button" class="SmallButton" value="返回"
								onclick="returnNew();" />
						</td>
					</tr>
				</table>
				<table id="t_aip" class="TableList" width="100%" align="center">
					<tr>
						<td class="TableContent" width="10%" align="center">
							协议名称:
						</td>
						<td class="TableData" width="15%">
							<input type="hidden"  id="ishotel" name="ishotel" value="1" />
							<input type="hidden"  id="old_name" name="old_name" />
							<input type="hidden"  id="dept_no" name="dept_no" value="${current_user.dept_no}"/>
							<input type="hidden"  id="dept_name" name="dept_name" value="${current_user.dept_name}"/>
							<input type="hidden"  id="modelorxieyi" name="modelorxieyi" value="1"/>
							<input type="hidden"  id="approve_status" name="approve_status" value="0"/>
							<input type="hidden"  id="identitycard" name="identitycard" value="0"/>
							<input type="text" size=25 class="BigInput" name="edit_tname"
								id="edit_tname" value="">
						</td>
						<td class="TableContent" width="10%" align="center">
							绑定角色:
						</td>
						<td nowrap class="TableContent" id="sel_priv">
							<input type="hidden" name="role_nos" id="role_nos" />
							<textarea cols=25 name="role_names" rows=1 class="BigStatic"
								wrap="yes" id="role_names" readonly></textarea>
							&nbsp;
							<input id="selectRoleButton" type="button" value="选 择" class="SmallButton"
								onClick="return openRoleList();" title="选择角色" />
						</td>
						<td class="TableContent" width="10%" align="center">
							用于:
						</td>
						<td class="TableData" width="15%">
							<select id="printoredit" name="printoredit">
								<option value="1" selected="selected">打印和编辑</option>
								<option value="2">打印</option>
								<option value="3">编辑</option>
							</select>
						</td>
						<td class="TableContent" width="10%" align="center">
							是否套打:
						</td>
						<td class="TableData" width="15%">
							<select id="multipart" name="multipart">
								<option value="0" selected="selected">否</option>
								<option value="1">是</option>
							</select>
						</td>
						
					</tr>
					<tr style="display:none">
						<td class="TableContent" width="100%" align="center" colspan=8>
							是否二次签字:
							<select id="isflow" name="isflow">
								<option value="0" selected="selected">否</option>
								<option value="1">是</option>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<b>签字位置设定:</b>&nbsp;&nbsp;
							页码:
							<input type="text" size=2 class="BigInput" name="page_no"
								id="page_no" value="">
							&nbsp;&nbsp;
							横坐标(X):
							<input type="text" size=5 class="BigInput" name="x_position"
								id="x_position" value="">
							&nbsp;&nbsp;
							纵坐标(Y):
							<input type="text" size=5 class="BigInput" name="y_position"
								id="y_position" value="">
							&nbsp;&nbsp;
							宽度(W):
							<input type="text" size=5 class="BigInput" name="area_width"
								id="area_width" value="">
							&nbsp;&nbsp;
							高度(H):
							<input type="text" size=5 class="BigInput" name="area_height"
								id="area_height" value="">
							&nbsp;&nbsp;
							区域名称:
							<input type="text" size=14 class="BigInput" name="area_name"
								id="area_name" maxlength=30 value="SecondSignArea">
						</td>
					</tr>
					<tr>
						<td class="TableContent" align="center">
							创建人:
						</td>
						<td class="TableData">
							<div id="edit_creater">${current_user.user_name }</div>
						</td>
						<td class="TableContent" align="center">
							创建时间:
						</td>
						<td class="TableData">
							<div id="edit_ctime"></div>
						</td>
						<td class="TableContent" align="center">
							修改时间:
						</td>
						<td class="TableData">
							<div id="edit_mtime"></div>
						</td>
						<td class="TableContent" align="center">
							
						</td>
						<td class="TableData">
							
						</td>
					</tr>
					<tr>
						<td class="TableData" colspan=4>
							<input type="hidden" id="upd_id">
						</td>
					</tr>
					<tr>
						<td id="td_aip" width="1" height="100%" colspan=8>
							<script src="/Seal/loadocx/LoadHWPostil.js"></script>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="overlay"></div>
		<div id="setFrm" class="ModalDialog" style="border: none;">
			<div id="setFrm_body" class="body bodycolor" style="padding: 0px;">
				<input type="hidden" name="FIELD_STR" id="FIELD_STR">
				<iframe frameborder=0 style="width: 320px; height: 330px"
					id="frm_field" src="set_filed.jsp?tpl_id=0"></iframe>
			</div>
		</div>
	</body>
</html>