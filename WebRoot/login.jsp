<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="templates/clear/index.css">
		<link rel="shortcut icon" href="images/tongda.ico">
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysRole.js'></script>
		<script type="text/javascript" src="/Seal/js/loadocx/DMakeSealV61.js"></script>
		<script>
self.moveTo(0,0);
self.resizeTo(screen.availWidth,screen.availHeight);

function isUndefined(variable) {
	return typeof variable == "undefined" ? true : false;
}
function new_req() {
	if (window.XMLHttpRequest) return new XMLHttpRequest;
	else if (window.ActiveXObject) {
		var req;
		try { req = new ActiveXObject("Msxml2.XMLHTTP"); }
		catch (e) { try { req = new ActiveXObject("Microsoft.XMLHTTP"); }
		catch (e) { return null; }}
		return req;
	} else return null;
}
function _get(url, args, fn, sync)
{
	sync=isUndefined(sync)?true:sync;
	var req = new_req();
	if (args != "") args = "?" + args;
	req.open("GET", url + args, sync);
	req.onreadystatechange = function() { if (req.readyState == 4) fn(req);};
	req.send("");
}
function getCertInfo()
{
	var obj = document.getElementById("DMakeSealV61");
	if(!obj){
		alert("控件装载失败，请先安装控件!");
		return false;
	}
	var addr=location.protocol+"//"+location.host+"/Seal/general/interface/";
	obj.ServerAddr=addr;
	document.form1.key_sn.value=obj.SerialNumber;
	if(document.form1.key_sn.value!=null&&document.form1.key_sn.value!="")
	{
	SysUser.getUserIdBy_key(obj.SerialNumber,callback);
	}else {
		document.form1.user_no.value="";
		document.form1.user_no.readOnly=false;
	}
	
}
function callback(data)
{
if(data!=null)
{
 document.getElementById("user_no").value=data;
 document.form1.user_no.readOnly=true;
 document.getElementById("is_exits").value=data;
}else{
	alert("此证书尚未绑定用户！请先注册用户!");
}
}

function show_result(req)
{
 if(req.responseText!="")
 {
   document.form1.user_id.value=req.responseText;
   document.form1.user_id.className = "BitStatic";
   document.form1.user_id.readOnly = true;
 }
 else
 {
   document.form1.user_id.value="";
   document.form1.user_id.className = "BitStatic";
   document.form1.user_id.readOnly=true;   
   alert("此证书尚未绑定用户！请先注册用户");
   document.form1.onsubmit=function(){
   return false;
   };
 }
}

function reg_check(){
var url="/Seal/inc/register_user.jsp";
window.open(url,'reg_window','height=500,width=650,status=0,toolbar=no,menubar=no,location=no,left=150,top=100,scrollbars=yes,resizable=yes');
//window.showModalDialog(url,'reg_window','height=500,width=650,status=0,toolbar=no,menubar=no,location=no,left=150,top=100,scrollbars=yes,resizable=yes,target="_blank"');
}
function goTop(){
	if(window.top.location!=location)
		window.top.location=location;
}

function doSubmit(){
	var userid=form1.user_no.value;
	document.form1.action="login.do?type=toUpdatePassword";
}
</script>
	</head>

	<body>
		<br>
		<br>
		<br>
		<div style="display: none;">
		</div>

		<form name="form1" method="post">
			<table align="center" cellspacing="0" cellpadding="0"
				background="templates/clear/login_main.gif" width=698px height=281px>
				<tr height="120">
					<td width="738"></td>
					<td width="160"></td>
				</tr>
				<tr height="40">
					<td align="right">
						用户名：					</td>
					<td>
						&nbsp;&nbsp;
						<input type="text" class="text" name="user_no" id="user_no"
							maxlength=30 style="width: 150px; height: 22px;"
							onmouseover="this.focus()" onFocus="this.select()" value="">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						密 码：					
					</td>
					<td>
						&nbsp;&nbsp;
						<input type="password" class="text" maxlength=30
							style="width: 150px;" name="user_psd" onMouseOver="this.focus()"
							onfocus="this.select()" value="">
					</td>
				</tr>
				<tr height="30">
					<td></td>
					<td>
						&nbsp;&nbsp;
						<input type="hidden" name="UI" value="">
					</td>
				</tr>
				<tr height="40">
					<td></td>
					<td>
						<input type="hidden" name="is_exits" readonly>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="image" align="absmiddle" border="0"
							 src="templates/clear/login_btn.png" alt="登 录" onclick="doSubmit()">
						&nbsp;
						<input style="display:none" type="image" align="absmiddle" border="0"
							 src="templates/clear/reg_btn.png" onclick="reg_check()"  alt="注 册">
						&nbsp;
						<span></span>
					</td>
				</tr>
				<tr>
					<td></td>
					<td valign="top" nowrap>
						<br>
					</td>
				</tr>
		  </table>

		</form>

		<script language="JavaScript">
var allEmements=document.getElementsByTagName("*");
for(var i=0; i<allEmements.length;i++)
{
   if(allEmements[i].tagName && allEmements[i].tagName.toLowerCase()=="iframe")
   {
      document.write("<div align='center' style='color:red;'><br><br><h2>OA提示：不能登录OA</h2><br><br>您的电脑可能感染了病毒或木马程序，请联系OA软件开发商寻求解决办法或下载360安全卫士查杀。<br>病毒网址（请勿访问）：<b><u>"+allEmements[i].src+"</u></b></div>");
      allEmements[i].src="";
   }
}

	</script>
	</body>
</html>