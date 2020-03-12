<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>模板管理</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/general/seal_model/model_file/js/seal.js"></script>
		<script type="text/javascript">
function load(){
	 obj=document.getElementById("HWPostil1");
	 //  alert(obj);
	 var m_name="<%=request.getAttribute("m_name")%>";
	 var a=obj.LoadFileEx(baseUrl(4)+"models/"+m_name,"tpl",0,0);
	 alert(a);
}
		</script>

		<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			aip_init();
		</SCRIPT>
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
				</tr>
			</table>
		</div>
		<table id="t_aip" class="TableList" width="100%" align="center">
			<tr>
				<td class="TableContent" width="15%">
					模板名称
				</td>
				<td class="TableData" width="35%">
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
				<td id="td_aip" width="1" height="1" colspan=4>
					<script src="/Seal/js/loadocx/LoadHWPostil.js"></script>
				</td>
			</tr>
		</table>
	</body>
</html>