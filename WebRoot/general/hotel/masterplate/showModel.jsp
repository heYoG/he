<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv=“X-UA-Compatible” content=“IE=6″>
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
		<script src="/Seal/general/hotel/masterplate/js/showModel.js"
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
  		function openmodwin() {
			var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_temp&&user_no=${current_user.user_id }",form1);
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
			<br>
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
						<td align="center" nowrap class="TableControl">
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
						<span class="big3"> 模板管理</span>
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
							<span class="big3">编辑模板</span><span class="small"
								style="color: red; display: none">(在模板区域内拖动鼠标以添加映射区域)</span>
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
						<td class="TableContent" width="7%" align="center">
							模板名称:
						</td>
						<td class="TableData" width="15%">
							<input type="hidden"  id="ishotel" name="ishotel" value="1" />
							<input type="hidden"  id="old_name" name="old_name" />
							<input type="hidden"  id="modelorxieyi" name="modelorxieyi" value="0"/>
							<input type="hidden"  id="approve_status" name="approve_status" value="0"/>
							<input type="hidden"  id="identitycard" name="identitycard" value="0"/>
							<input type="text" size=25 class="BigInput" name="edit_tname"
								id="edit_tname" value="">
						</td>
						<td class="TableContent" width="7%">
								所属部门：
						</td>
						<td nowrap class="TableData" width="15%">
								<input type="hidden"  id="dept_no" name="dept_no" value="${current_user.dept_no}"/>
								<input type="text" name="dept_name" id="dept_name" readonly="readonly"
									value="${current_user.dept_name }" />
								<input type="button" value="选 择" class="SmallButton"
									onclick="return openDeptList(form1)" title="选择部门">
						</td>
						<td class="TableContent" width="7%" align="center">
							绑定角色:
						</td>
						<td nowrap class="TableContent" id="sel_priv" width="15%">
							<input type="hidden" name="role_nos" id="role_nos" />
							<textarea cols=25 name="role_names" rows=1 class="BigStatic"
								wrap="yes" id="role_names" readonly></textarea>
							&nbsp;
							<input id="selectRoleButton" type="button" value="选 择" class="SmallButton"
								onClick="return openRoleList();" title="选择角色" />
						</td>
						<td class="TableContent" width="7%" align="center">
							用于:
						</td>
						<td class="TableData" width="15%">
							<select id="printoredit" name="printoredit">
								<option value="1" selected="selected">打印和编辑</option>
								<option value="2">打印</option>
								<option value="3">编辑</option>
							</select>
						</td>
						<td class="TableContent" width="7%" align="center">
							是否套打:
						</td>
						<td class="TableData" width="10%">
							<select id="multipart" name="multipart">
								<option value="0" selected="selected">否</option>
								<option value="1">是</option>
							</select>
						</td>
						
					</tr>
					<tr style="display:none">
						<td class="TableContent" width="100%" align="center" colspan=10>
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
						<td class="TableContent" align="center" style="display: none">
							创建人:
						</td>
						<td class="TableData" style="display: none">
							<div id="edit_creater">${current_user.user_name }</div>
						</td>
						<td class="TableContent" align="center" style="display: none">
							创建时间:
						</td>
						<td class="TableData"  style="display: none">
							<div id="edit_ctime"></div>
						</td>
						<td class="TableContent" align="center">
							修改时间:
						</td>
						<td class="TableData">
							<div id="edit_mtime"></div>
						</td>
						<td class="TableContent" align="center">
							打印纸张来源规格：
						</td>
						<td class="TableData" align="center" >
							<select id="modelSepc" onchange="changeSepc()">
								<option value="0">--请选择--</option>
								<option value="1">A4</option>
								<option value="2">A5</option>
								<option value="3">B5</option>
								<option value="4">自定义</option>
							</select>
						</td>
						<td class="TableContent" align="center" style="display:none;" id="sepc-name">
							自定义宽高：
						</td>
						<td class="TableData" align="center" style="display:none;" id="sepc-value">
							宽：<input type="text" value="" id="mwidth" name="mwidth" size="12"/>mm<br>
							高：<input type="text" value="" id="mheight" name="mheight" size="12"/>mm
						</td>
						<td class="TableContent" align="center" id="sepc-name">
							校对方式
						</td>
						<td class="TableData" align="center"  id="sepc-value">
							X轴：<select name="x_modelType" id="x_modelType"  onchange="x_modelChange()">
									<option value="0">--不校对--</option>
									<option value='left'>靠左</option>
									<option value='right'>靠右</option>
									<option value='center'>居中</option>
							   </select><br>
							Y轴：<select name="y_modelType" id="y_modelType"   onchange="y_modelChange()">
									<option value="0">--不校对--</option>
									<option value="top">靠上</option>
									<option value="bottom">靠下</option>
									<option value="center">居中</option>
							   </select>
							   <input type="hidden" value='0' id="model_xtype" name="model_xtype"/>
							   <input type="hidden" value='0' id="model_ytype" name="model_ytype"/>
						</td>
						<td class="TableContent" >
							偏移量设置：
						</td>
						<td class="TableData" >
							X轴偏移量：<input type="text" value="" id="modelchangex" name="modelchangex" size="8"/>ppi<br>
							Y轴偏移量：<input type="text" value="" id="modelchangey" name="modelchangey" size="8"/>ppi
						</td>
						<td nowrap class="TableContent" width=100>
						复核人：
					</td>
					<td class="TableData">
						<input class="BigStatic" name="approve_user" id="approve_user" type="hidden"
							value="${current_user.user_id }">
						<input class="BigStatic" name="approve_name" id="approve_name" type="text" readonly
							value="${current_user.user_name }">
						<a href="" onclick="openmodwin();return false;">选择复核人</a>
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
				<input type="hidden" id="modelwidth" name="modelwidth"/>
				<input type="hidden" id="modelheight" name="modelheight"/>
				<input type="hidden" id="printsize_width" name="printsize_width"/>
				<input type="hidden" id="printsize_height" name="printsize_height"/>
			</form>
		</div>
		<div id="overlay"></div>
		<div id="setFrm" class="ModalDialog" style="border: none;">
			<div id="setFrm_body" class="body bodycolor" style="padding: 0px;">
				<input type="hidden" name="FIELD_STR" id="FIELD_STR">
				<iframe frameborder=0 style="width: 320px; height: 400px"
					id="frm_field" src="set_filed.jsp?tpl_id=0"></iframe>
			</div>
		</div>
	</body>
</html>