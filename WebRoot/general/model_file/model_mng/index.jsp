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
		<script src="/Seal/general/model_file/model_mng/js/index.js"
			type="text/javascript"></script>
		<script src="/Seal/general/model_file/model_mng/js/seal.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		
function setField(field_name,field_type,font_family,font_size,font_color,border_style,halign,valign)
{
	HideDialog('setFrm');
	if(argObj == null)
	    return;
	var obj = ${"HWPostil1"};
	var field_width = argObj.EndX - argObj.StartX;
	var field_height = argObj.EndY - argObj.StartY;	
	var vRet = obj.InsertNote(field_name,argObj.page,field_type,argObj.StartX,argObj.StartY,field_width,field_height);
	if(vRet=="")
    {
  	    alert("此字段映射已经添加！");
        ShowDialog('setFrm');
  	    return;
    }
    font_size = font_size.replace(/pt/ig,"");
    font_color = font_size.replace(/#/ig,"0x00");
    obj.SetValue(field_name,":PROP::LABEL:3");
    obj.SetValue(field_name,":PROP:BORDER:"+border_style);
    obj.SetValue(field_name,":PROP:FACENAME:"+font_family);
    obj.SetValue(field_name,":PROP:FONTSIZE:"+font_size);
    obj.SetValue(field_name,":PROP:FRONTCOLOR:"+font_color);
    obj.SetValue(field_name,":PROP:HALIGN:"+halign);
    obj.SetValue(field_name,":PROP:VALIGN:"+valign);
  
	$("FIELD_STR").value += field_name+",";
	argObj = null;
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
						<span class="big3"> 新建模板</span>
						<br>
					</td>
				</tr>
			</table>
			<form enctype="multipart/form-data" styleId="form1">
				<table class="TableList" width="80%" align="center">
					<tr>
						<td class="TableContent">
							模板名称
						</td>
						<td class="TableData">
							<input type="text" class="BigInput" size=25 name="tname"
								id="tname">
						</td>
					</tr>
					<tr style="display: none">
						<td class="TableContent">
							模板文件
						</td>
						<td class="TableData">
							<input style="display: none" class="BigInput" type="file" name="tfile" id="tfile"
								onchange="sel_file();">
							<span id="file_name"><input type="text" /></span>
							<input type="button" value="浏览"
								class="SmallInput" onclick="select_file();">
							（word文档）
						</td>
					</tr>
					<tr>
						<td class="TableControl" align="center" colspan=2>
							<input type="button" value="新建静态模板"
								onclick="loadAip();" class="SmallButton" />
							&nbsp;&nbsp;
							<input type="button" value="新建动态模板" class="SmallButton"
								onclick="convert();">
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
					<td class="TableContent" width="15%">
						模板名称
					</td>
					<td class="TableData" width="35%">
						<input type="hidden"  id="old_name" name="old_name" />
						<input type="text" size=25 class="BigInput" name="edit_tname"
							id="edit_tname" value="">
					</td>
					<td class="TableContent" width="15%">
						创建人
					</td>
					<td class="TableData" width="35%">
						<div id="edit_creater">
							${current_user.user_name }
						</div>
					</td>
				</tr>
				<tr>
					<td class="TableContent">
						创建时间
					</td>
					<td class="TableData">
						<div id="edit_ctime"></div>
					</td>
					<td class="TableContent">
						修改时间
					</td>
					<td class="TableData">
						<div id="edit_mtime"></div>
					</td>
				</tr>
				<tr>
					<td class="TableData" colspan=4>
						<input type="hidden" id="upd_id">
					</td>
				</tr>
				<tr>
					<td id="td_aip" width="1" height="100%" colspan=4>
						<script src="/Seal/loadocx/LoadHWPostil.js"></script>
					</td>
				</tr>
			</table>
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