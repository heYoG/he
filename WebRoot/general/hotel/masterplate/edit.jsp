<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>编辑打印模板</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css" />
		<script src="/Seal/js/utility.js"></script>
		<script src="/Seal/js/module.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript">
var argObj = null;

function addField()
{
	var obj = ${HWPostil1};
    var vRet = obj.Login("sys_admin", 5, 32767, "", "");
    if(0 == vRet);
	    obj.InDesignMode = true;
}
function NotifyLineAction(lPage,lStartPos,lEndPos)
{
    var lStartY = (lStartPos>>16)& 0x0000ffff;
	var lStartX = ((lStartPos<<16)>>16) & 0x0000ffff;
	var lEndY = (lEndPos>>16)& 0x0000ffff;
	var lEndX = ((lEndPos<<16)>>16) & 0x0000ffff; 
	//alert(lStartX);alert(lStartY);
	ShowDialog('setFrm',30);
    argObj = {"page":lPage,"StartX":lStartX,"StartY":lStartY,"EndX":lEndX,"EndY":lEndY};
}
function setField(field_name,field_type,font_family,font_size,font_color,border_style,halign,valign)
{
alert(field_name);
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
function saveFile()
{
    var obj = ${HWPostil1};
	if($("tname").value=="")
	{
		alert("请输入模板名称！");
		return;
	}
    
    var content = obj.GetCurrFileBase64();
    _post("upload.php","tid=<?=$tid?>&tname="+${tname}.value+"&tcontent="+content.replace(/\+/g, '%2B'),function(req){
    		window.location='edit.php?tid=<?=$tid?>';
    },false);
}
</script>
		<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
var obj = $("HWPostil1");
obj.ShowDefMenu = false; //隐藏菜单
obj.ShowToolBar = false; //隐藏工具条
obj.ShowScrollBarButton = 1;
//alert("<?=base64_encode($TPL_FILE)?>");
obj.LoadFileBase64("${tcontent}");
addField();
		</SCRIPT>
		<SCRIPT LANGUAGE=javascript FOR=HWPostil1
			EVENT=NotifyLineAction(lPage,lStartPos,lEndPos)>
NotifyLineAction(lPage,lStartPos,lEndPos);
		</SCRIPT>
	</head>

	<body class="bodycolor" topmargin="5" scroll=no>
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/images/print.gif" align="middle" align="middle">
					<span class="big3">编辑模板</span><span class="small"
						style="color: red;">(在模板区域内拖动鼠标以添加映射区域)</span>
				</td>
				<td align=right>
					<input type="button" class="SmallButton" value="添加映射区域"
						onclick="addField()" />
					&nbsp;
					<input type="button" class="SmallButton" value="保存"
						onclick="saveFile();" />
					&nbsp;
					<input type="button" class="SmallButton" value="返回"
						onclick="location='index.jsp?FLOW_ID=${FLOW_ID}" />
				</td>
			</tr>
		</table>

		<table class="TableList" width="100%" align="center">
			<tr>
				<td class="TableContent" width=100>
					模板名称
				</td>
				<td class="TableData">
					<input type="text" size=25 class="BigInput" name="tname"
						value="${tname}">
				</td>
				<td class="TableContent" width=100>
					创建人
				</td>
				<td class="TableData">
					${user_name}
				</td>
			</tr>
			<tr>
				<td class="TableContent" width=100>
					创建时间
				</td>
				<td class="TableData">
					<?=$create_time?>
				</td>
				<td class="TableContent" width=100>
					修改时间
				</td>
				<td class="TableData">
					${modify_time}
				</td>
			</tr>
			<tr>
				<td class="TableData" colspan=4>
				</td>
			</tr>
		</table>

		<div id="overlay"></div>
		<div id="setFrm" class="ModalDialog" style="border: none;">
			<div id="setFrm_body" class="body bodycolor" style="padding: 0px;">
				<input type="hidden" name="FIELD_STR" id="FIELD_STR">
				<iframe frameborder=0 style="width: 320px; height: 300px"
					id="frm_field" src="set_filed.jsp"></iframe>
			</div>
		</div>

	</body>
</html>