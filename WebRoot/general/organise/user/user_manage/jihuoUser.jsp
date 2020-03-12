<%@page contentType="text/html;charset=utf-8"%>
<%@page import="com.dj.seal.structure.dao.po.SysUser"%>
<%@include file="../../../../inc/tag.jsp"%>
<html>
<head>
<title>更改用户</title>
<link rel="stylesheet" type="text/css"
	href="theme/${current_user.user_theme}/style.css">
<script src="js/ccorrect_btn.js"></script>
<script src="js/utility.js"></script>
<script type="text/javascript" src="/Seal/loadocx/MakeSealV6.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script src="/Seal/js/util.js"></script>

<script Language="JavaScript">
		 var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
		function chkbox(is_junior2){
		if(is_junior2.checked){
		document.form1.is_junior.value = 1 ;
		// alert("aaaa"+document.form1.is_junior.value);
		}else{
		document.form1.is_junior.value = 0 ;
		// alert("bbbbb"+document.form1.is_junior.value);
		}
		
		}
function openmodwin() {
	var b='default';
	b = window.showModalDialog("roleModeList.do?user_status=1",form1);
	if(b!='default'){
		document.form1.role_names.value = b ;
	}
}
function openmodwin2() {
    var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
	//var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);
}
function openmodwin12() {
    var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
	//var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);;
}
function doreset(){
    document.form1.user_name.value="";
    document.form1.user_psd.value="";
    document.form1.user_ip.value="";
}
function delIp(){
        document.form1.user_ip.value="";
		var f=document.form1;
		selectDelSel(f.select_ip);
		}
function addIp(){
		 var f=document.form1;
		 var str=newModalDialog('/Seal/general/user/addIp1.jsp',200,100,f);
		 if(str !=""){	   
		   if(selectExist(f.select_ip,$("user_ip").value)==false){   
		    selectAdd(f.select_ip,$("user_ip").value,$("user_ip").value);		   
		   }	  
		 }
		 var user_ip5=document.getElementById("user_ip").value;
		 var str6="";
		 // alert('f.select_ip'+f.select_ip.length);
		 for(var i=0;i<f.select_ip.length;i=i+1){
		 ///   alert('strmm'+str6);
		     if(f.select_ip[i].value !=""){
		      str6 +=f.select_ip[i].value+",";
		    //   alert('str'+str6);
		     }		  
	     }
		  document.form1.user_allip.value=str6;
	}

function dosubmit(){
  function dosubmit(){
 
   var user_name=document.form1.user_name.value;
   var user_remark2 = document.form1.user_remark2.value;
   if(user_name=="")
   { alert("姓名不能为空！");
     return false;
   }else{
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(user_name)){
      alert("姓名只能只能包含中文、字母、数字!");
      return false;
     }
    }
   var user_psd2=document.getElementById("user_psd").value;
   var user_psd3=document.getElementById("user_psd2").value;
   if((user_psd2=="")||(user_psd2==null)){
     alert("密码不能为空 !");
     return false;
    }else{
      if((user_psd2.length < 6)||(user_psd2.length > 15)){
        alert("密码长度在6-15之间 !");
      	return false;
      }if(user_psd2!=user_psd3){
      	 alert("两次输入的密码不一致，请重新输入 !");
      	 document.getElementById("user_psd").value="";
      	 document.getElementById("user_psd2").value="";
         document.getElementById("user_psd").focus();
      	 return false;
      	}	
    }  
   if(document.form1.role_nos.value=="")
   { alert("角色不能为空！");
     return false;
   }
    if(document.form1.dept_name.value=="")
   { alert("部门名称不能为空！");
     return false;
   }
    
   var rang_type=document.form1.rang_type.value;
   if(rang_type=="2"){
   if(document.form1.name_range.value=="")
   { 
   alert("管理范围部门不能为空！");
   return false;
   }
   }
    var user_remark2=document.form1.user_remark2.value;
   if(user_remark2.length > 0)
   {
   // alert("输入的备注框长度不能超过20个字符!");
   // return false;
  // }else{
       user_remark2=user_remark2.replace(/\'/g,"‘");
       document.form1.user_remark2.value=user_remark2;
     } 
     if(!document.form1.useing_key.checked){
     
      document.form1.key_id.value=""; 
	  document.form1.key_sn.value=""; 
	  document.form1.key_dn.value="";
	  document.form1.key_cert.value="";
	  document.form1.cert_user.value="";
	  document.form1.begin_time.value="";
	  document.form1.end_time.value="";
	  
     }	
  	 dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
 	 LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","用户管理","更新用户:'"+document.form1.user_name.value+"'成功,原名为:"+document.form1.user_name2.value);//logSys.js
  	 var dept_no = document.getElementById("dept_no").value;
  	if (typeof window['DWRUtil'] == 'undefined') { //解决DWRUtile未注册的错误
  	 	window.DWRUtil = dwr.util; 
  	 } 
  	 // DWRUtil.useLoadingMessage("正在提交...");       
  	 var formMap = DWRUtil.getValues("form1");
  	 //alert("useing_key"+formMap.useing_key);
  	// alert("user_type"+formMap.user_type);
  	 var user_id_old=document.getElementById("user_id_old").value;
  	 var user_name_old=document.getElementById("user_name_old").value;
  	 //alert(user_id_old);
  	//SysUser.updateSysUser(formMap,dept_no,user_remark2,call);
  	 SysUser.jihuoSysUser(formMap,user_id_old,user_name_old,dept_no,user_remark2,call);
    //document.form1.submit();
}
function call(data){
	alert("修改成功");
	history.go(-1);
// 	parent.document.location = parent.document.location;刷新父页面，如果刷新本页document.location = document.location 
}

function ClearUser(){
	var manage_range=document.form1.manage_range;
	manage_range.value="";
	var name_range=document.form1.name_range;
	name_range.value="";
}
		
function select_dept()
{  
   var rang_type=document.form1.rang_type.value;
   if(rang_type=='1'){
    document.form1.is_junior2.checked=true;
    document.form1.is_junior.value = 1 ;
   }  
   if (form1.rang_type.value=="2")
       dept.style.display='';
   else
       dept.style.display="none";
}

function select_priv()
{
   if(priv.style.display=="none")
      priv.style.display="";
   else
   	  priv.style.display="none";
}
function select_dept_other()
{
   if (dept_other.style.display=="none")
       dept_other.style.display='';
   else
       dept_other.style.display="none";
}
         function check_user()
      		{
      		var userId=document.getElementById("user_id").value;
            if(userId=="")
            return;
            document.getElementById("user_id_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
			SysUser.isExistUser(userId,callback)
		    }
		  function callback(data)
		    {  	
		    if(data==1)
		    {
		    document.getElementById("user_id_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 该用户名已存在";
		    document.getElementById("user_id").value="";
		    } 
		     else
		     document.getElementById("user_id_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		   }

//获取证书信息
var isCertExit=false;
function getCertInfo(){
      document.form1.key_id.value=""; 
	  document.getElementById("key_sn").value=""; 
	  document.form1.key_dn.value="";
	  document.form1.key_cert.value="";
	  document.form1.cert_user.value="";
	  document.form1.begin_time.value="";
	  document.form1.end_time.value="";
	var obj = document.getElementById("DMakeSealV61");
	if(!obj){
	  alert("控件装载失败，请先安装控件!");
	  return false;
	}
	var i,myObj;
	myObj=document.getElementsByName("useing_key");
	for(i=0;i<myObj.length;i++){
	 if(myObj[i].checked){
	   if(myObj[i].value=="3"){
	       document.form1.key_id.value=obj.GetKeyID(""); 
	       document.getElementById("key_sn").value=obj.GetSerialNumberEx(2); //1key里,2ie里
	       document.form1.key_dn.value=obj.GetCertDNEx(2);
	       document.form1.key_cert.value=obj.GetCardCert();
	       document.form1.cert_user.value=obj.SubjectName;
	       document.form1.begin_time.value=obj.GetCertStartTimeEx(2);
	       document.form1.end_time.value=obj.GetCertEndTimeEx(2);
	       if(document.form1.key_sn.value!=null&&document.form1.key_sn.value!=""){
		  //SysUser.getUserIdBy_key(obj.SerialNumber,callback1);
	       SysUser.getUserBy_keySN(document.form1.key_sn.value,callback1);
	       }
	   }else if(myObj[i].value=="1"){
	      if((obj.SerialNumber=="")||(obj.SerialNumber==null)){
	        alert("读取证书信息失败，请检查key是否插入!");
	      }else{
	        if(obj.SerialNumber!=null&&obj.SerialNumber!=""){
	   	     dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		     //SysUser.getUserIdBy_key(obj.SerialNumber,callback1);
		     SysUser.getUserBy_keySN(obj.SerialNumber,callback1);
	        }
	        if(!isCertExit){
		      document.form1.key_id.value=obj.GetKeyID(""); 
		      document.getElementById("key_sn").value=obj.SerialNumber; 
		      document.form1.key_dn.value=obj.GetCertDN();
		      document.form1.key_cert.value=obj.GetCardCert();
		      document.form1.cert_user.value=obj.SubjectName;
		      document.form1.begin_time.value=obj.GetCertStartTimeEx(0);
		      document.form1.end_time.value=obj.GetCertEndTimeEx(0);
		      
	        }
	     } 
	   }
	 }
    }
}
//判断证书是否存在的DWR回调
function callback1(data){
  if(data!=null){
  	isCertExit=true;
   	alert("key已经注册，请更换其他key!");
   	document.form1.key_id.value=""; 
    document.form1.key_sn.value=""; 
    document.form1.key_dn.value="";
    document.form1.key_cert.value="";
    document.form1.cert_user.value="";
	document.form1.begin_time.value="";
    document.form1.end_time.value="";
    document.form1.pfxPsw.value="";
  }
}
//日历控件
function GetDate(nText) {
	reVal = window.showModalDialog("inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].user_birth.value = reVal;
		} else {
			if (nText == 2) {
				document.forms[0].checkTime.value = reVal;
			}
		}
	}
}

function hiddenZS(flag){
	switch(flag){
	 case '0':
		$("zhsh0").style.display="none";
	 	$("zhsh").style.display="none";
	 	$("zhsh1").style.display="none";
	 	$("zhsh2").style.display="none";
	 	$("zhsh3").style.display="none";
	 	clearData();
	 	break;
	 case '1':
	 	$("getCertInfoButton").style.display="";
	 	$("zhsh0").style.display="none";
	 	$("zhsh3").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	clearData();
	 	break;
 	case '2':
 		$("zhsh0").style.display="";
 		$("zhsh3").style.display="";
 		$("getCertInfoButton").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	clearData();
	 	break;
	 case '3':
 		$("getCertInfoButton").style.display="";
	 	$("zhsh0").style.display="none";
	 	$("zhsh3").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	clearData();
	 	break;
	}
}
//清空证书数据
function clearData(){
//  var temp=document.getElementById("pfxPath");
//  temp.select();
  document.getElementById("selectRoleButton").focus();//解决ie9不能获得路径问题
//  document.selection.clear();
  document.form1.key_id.value=""; 
  document.form1.key_sn.value=""; 
  document.form1.key_dn.value="";
  document.form1.key_cert.value="";
  document.form1.cert_user.value="";
  document.form1.begin_time.value="";
  document.form1.end_time.value="";	
  document.form1.cert_issue.value="";
  document.form1.pfxPsw.value="";
  document.getElementById("fileDiv").innerHTML=document.getElementById("fileDiv").innerHTML;
}
function getPfxInfo(){
	var temp=document.getElementById("pfxPath");
	temp.select();
	document.getElementById("selectRoleButton").focus();//解决ie9不能获得路径问题
	var tempPath=document.selection.createRange().text;
	var obj = document.getElementById("DMakeSealV61");
	var certstr=obj.GetInfoFromPfx(tempPath,document.form1.pfxPsw.value);
	if(certstr.indexOf("errorcode")>-1){
		 alert("证书无法解析，请确认是pfx文件，并且密码正确");
		 clearData();
		 return (false);
	}else{
		var key_sn=certstr.split("->");
	  	document.form1.key_sn.value=key_sn[0].split("<+N=")[1]; 
	  	document.form1.key_dn.value=key_sn[5].split("<+D=")[1];
	  	document.form1.cert_user.value=key_sn[1].split("<+S=")[1];
	  	document.form1.begin_time.value=key_sn[2].split("<+B=")[1];
	  	document.form1.end_time.value=key_sn[3].split("<+A=")[1];
	  	document.form1.key_cert.value=key_sn[4].split("<+C=")[1];
	  	if(document.form1.key_sn.value!=null&&document.form1.key_sn.value!=""){
		  SysUser.getUserBy_keySN(document.form1.key_sn.value,callback1);
	  	}
	}
	
}

</script>
</head>
<%
                       String unitname=session.getAttribute("unitname").toString();
                       String rolename=session.getAttribute("rolename").toString();
                       String roleno7=session.getAttribute("roleno7").toString();
                       if(rolename.indexOf(",")==0){
			           rolename = rolename.substring(1, rolename.length());
		               }
		               if(rolename.lastIndexOf(",")==rolename.length()){
		               rolename = rolename.substring(0, rolename.length()-1);
		               }
                       String[] rolename2=rolename.split("\\.");
                       String rolename3="";
                       for(int i=0;i<rolename2.length;i++){
                       rolename3+=rolename2[i];
                       }
                     %>
<body class="bodycolor" topmargin="5"
	onload="document.form1.user_id.focus();">

	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big"><img src="images/menu/comm.gif"
				align="absmiddle"> <span class="big3"> 更改普通用户 （全局人员）</span></td>
		</tr>
	</table>
	<form action="updateSysUser.do" method="post" name="form1"
		enctype="multipart/form-data">
		<table class="TableBlock" width="95%" align="center">
			<tr>
				<td nowrap class="TableHeader" colspan="2"><img
					src="images/green_arrow.gif" align="absMiddle"> 证书基本信息</td>
			</tr>
			<tr>
				<td nowrap class="TableData">是否绑定证书：</td>
				<td nowrap class="TableData"><label for="USEING_KEY">
						绑定证书: </label> <input id="noKey" name="useing_key" type="radio" value="0"
					onclick="hiddenZS('0');" checked="checked" />不绑定key证书 <input
					id="isKey" name="useing_key" type="radio" value="1"
					onclick="hiddenZS('1');" />绑定key证书 <input id="isPFX"
					name="useing_key" type="radio" value="2" onclick="hiddenZS('2')" />pfx文件证书
					<input id="isIE" name="useing_key" type="radio" value="3"
					onclick="hiddenZS('3')" />IE证书</td>
			</tr>
			<tr>
			<tr id="zhsh">
				<td nowrap class="TableData">证书序列号：</td>
				<td nowrap class="TableData"><input type="text" name="key_sn"
					readOnly class="BigStatic" size="30" id="key_sn"
					value="${userCurrCert.cert_name}"> &nbsp; <input
					id="getCertInfoButton" type="button" class="SmallButton"
					value="获取证书信息" onclick="getCertInfo();"></td>
			</tr>
			<tr id="zhsh0" style="display:none">
				<td nowrap class="TableData">pfx路径：</td>
				<td nowrap class="TableData">
					<div id="fileDiv">
						<input type="file" name="pfxContent" id="pfxPath">
					</div></td>
			</tr>
			<tr id="zhsh3" style="display:none">
				<td nowrap class="TableData">证书密码：</td>
				<td nowrap class="TableData"><input type="password"
					name="pfxPsw" id="pfxPsw"> <input type="button"
					class="SmallButton" value="获取证书信息" onclick="getPfxInfo();">
				</td>
			</tr>
			<tr id="zhsh1">
				<td nowrap class="TableData">证书有效期：</td>
				<td nowrap class="TableData"><input type="text"
					name="begin_time" readOnly class="BigStatic" size="30"
					id="begin_time" value="${userCurrCert.begin_time}"> - <input
					type="text" name="end_time" readOnly class="BigStatic" size="20"
					id="end_time" value="${userCurrCert.end_time}"></td>
			</tr>
			<tr id="zhsh2">
				<td nowrap class="TableData">证书DN：</td>
				<td nowrap class="TableData"><input type="text" name="key_dn"
					readOnly class="BigStatic" size="70" id="key_dn"
					value="${userCurrCert.cert_dn}"> <input type="hidden"
					name="key_cert" id="key_cert" value="${userCurrCert.file_content}">
					<input type="hidden" name="key_id" id="key_id"
					value="${sysuser.key_id}"> <input type="hidden"
					name="cert_user" id="cert_user" value="${userCurrCert.cert_user}">
					<input type="hidden" name="cert_issue" id="cert_issue"
					value="${userCurrCert.cert_issue}"></td>
			</tr>

			<tr>
				<td nowrap class="TableHeader" colspan="2"><img
					src="images/green_arrow.gif" align="absMiddle"> 用户基本信息</td>
			</tr>
			<tr>
				<td nowrap class="TableContent" width="120">用户名：</td>
				<td nowrap class="TableContent"><input type="text"
					name="user_id" class="BigInput" size="10" readonly maxlength="20"
					value="${sysuser.user_id }"> &nbsp; <span id="user_id_msg"></span>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">姓名：</td>
				<td nowrap class="TableContent"><input type="text"
					name="user_name" class="BigInput" size="10"
					value="${sysuser.user_name }" maxlength="30"> &nbsp;</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">密码：</td>
				<td nowrap class="TableContent"><input type="password"
					name="user_psd" id="user_psd" value="${sysuser.user_psd }"
					class="BigInput" size="10" maxlength="15"> &nbsp;</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">确认密码：</td>
				<td nowrap class="TableContent"><input type="password"
					name="user_psd2" id="user_psd2" value="${sysuser.user_psd }"
					class="BigInput" size="10" maxlength="15"> &nbsp;</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">角色： <input type="hidden"
					name="role_nos" id="role_nos" value="<%=roleno7%>" /></td>
				<td nowrap class="TableContent" id="sel_priv"><textarea cols=30
						name="role_names" rows=2 class="BigStatic" wrap="yes"
						id="role_names" readonly><%=rolename3%></textarea> <input
					id="selectRoleButton" type="button" value="选 择" class="SmallButton"
					onClick="return openmodwin();" title="选择角色" /></td>
			</tr>
			<tr>
				<td nowrap class="TableData">部门：</td>
				<td class="TableData"><input type="text" name="dept_name"
					readonly="readonly" value="<%=unitname%>" /> <input type="button"
					value="选 择" class="SmallButton" onclick="return openmodwin2();"
					title="选择部门"></td>
			</tr>

			<tr>
				<td nowrap class="TableHeader" colspan="2"><img
					src="images/green_arrow.gif" align="absMiddle"> 用户权限信息</td>
			</tr>
			<tr>
				<td nowrap class="TableData" width="120">管理范围：</td>
				<td nowrap class="TableData"><select name="rang_type"
					class="BigSelect" OnChange="select_dept()">
						<c:if test="${sysuser.rang_type == '2'}">
							<!--  <option value="2" selected >
								指定部门
							</option>
							<option value="1" >
								全体
							</option>-->

							<option value="0">本部门</option>
						</c:if>
						<c:if test="${sysuser.rang_type == '1'}">
							<!--<option value="2"  >
								指定部门
							</option>
							<option value="1" selected>
								全体
							</option>-->
							<option value="0">本部门</option>
						</c:if>
						<c:if test="${sysuser.rang_type == '0'}">
							<!--<option value="2"  >
								指定部门
							</option>
							<option value="1" >
								全体
							</option>-->

							<option value="0" selected>本部门</option>
						</c:if>
				</select> 在管理型模块中起约束作用 &nbsp; &nbsp; <c:if test="${sysuser.is_junior == '1'}">
						<input type="hidden" name="is_junior" id="is_junior" value="1">
						<input type="checkbox" name="is_junior2" checked
							onclick="chkbox(this);" id="is_junior2">
					</c:if> <c:if test="${sysuser.is_junior == '0'}">
						<input type="hidden" name="is_junior" id="is_junior" value="0">
						<input type="checkbox" name="is_junior2" onclick="chkbox(this);"
							id="is_junior2">
					</c:if> <label for="is_active"> 包含下级 </label></td>
			</tr>
			<c:if test="${sysuser.rang_type == '2'}">
				<tr id="dept" style="display: display;">
					<td nowrap class="TableData">管理范围（部门）：</td>
					<td class="TableData"><input type="hidden" name="manage_range"
						value="${sysuser.manage_range}"> <textarea cols=30
							name="name_range" rows=2 class="BigStatic" wrap="yes" id=TO_NAME
							readonly>${sysuser.manage_fw}</textarea> &nbsp; <input
						type="button" value="选 择" class="SmallButton"
						onClick="return openmodwin12();" title="选择部门" name="button">
						&nbsp; <input type="button" value="清 空" class="SmallButton"
						onClick="ClearUser()" title="清空部门" name="button"></td>
				</tr>
			</c:if>
			<c:if test="${sysuser.rang_type != '2'}">
				<tr id="dept" style="display: none;">
					<td nowrap class="TableData">管理范围（部门）：</td>
					<td class="TableData"><input type="hidden" name="manage_range"
						value="${sysuser.manage_froleid}"> <textarea cols=30
							name="name_range" rows=2 class="BigStatic" wrap="yes" id=TO_NAME
							readonly>${sysuser.manage_fw}</textarea> &nbsp; <input
						type="button" value="选 择" class="SmallButton"
						onClick="return openmodwin12();" title="选择部门" name="button">
						&nbsp; <input type="button" value="清 空" class="SmallButton"
						onClick="ClearUser()" title="清空部门" name="button"></td>
				</tr>
			</c:if>
			<tr>
				<td nowrap class="TableData">备注：</td>
				<td nowrap class="TableData"><textarea name="user_remark2"
						id="user_remark2" class="BigInput" cols="50" rows="2">${sysuser.user_remark}</textarea>
				</td>
			</tr>
			<tr
				onclick="if(option2.style.display=='none') option2.style.display=''; else option2.style.display='none';"
				title="点击展开/收缩选项">
				<td nowrap class="TableHeader" colspan="2" style="cursor: pointer;">
					<img src="images/green_arrow.gif" align="absMiddle"> 用户可自定义选项
				</td>
			</tr>
			<tbody id="option2" style="display: none;">
				<tr>
					<td nowrap class="TableData">性别： <br>
					<br>
					<br>
					<br>
					</td>
					<td nowrap class="TableData"><select name="user_sex"
						class="BigSelect">
							<c:if test="${sysuser.user_sex =='0'}">
								<option value="0" selected>男</option>
								<option value="1">女</option>
							</c:if>
							<c:if test="${sysuser.user_sex =='1'}">
								<option value="0">男</option>
								<option value="1" selected>女</option>
							</c:if>
					</select> <br>
					<br>
					<br>
					<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">生日： <br>
					<br>
					<br>
					<br>
					</td>
					<td nowrap class="TableData"><input type="text"
						name="user_birth" size="10" maxlength="10"
						value="${sysuser.user_birth }" class="BigInput"
						onfocus="this.blur()"> <img onclick="GetDate(1);"
						src="images/654.gif" style="height: 20; cursor: hand" border="0" />
						<br>
					<br>
					<br>
					<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">界面主题： <br>
					<br>
					<br>
					<br>
					</td>
					<td class="TableData"><select name="user_theme"
						class="BigSelect">
							<option value="1">默认主题</option>
							<option value="2" selected>清新怡然</option>
							<option value="3">简洁明亮</option>
							<option value="4">粉红浪漫</option>
							<option value="5">Vista风格</option>
					</select> <br>
					<br>
					<br>
					<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">手机： <br>
					<br>
					<br>
					<br>
					</td>
					<td class="TableData"><input type="text" name="mobil_no"
						size="16" maxlength="23" class="BigInput"
						value="${sysuser.mobil_no }"> <br> <br>
					<br>
					<br>
					<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">电子邮件： <br>
					<br>
					<br>
					<br>
					</td>
					<td class="TableData"><input type="text" name="user_email"
						size="25" maxlength="50" class="BigInput"
						value="${sysuser.user_email }"> <br>
					<br>
					<br>
					<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">单位电话： <br>
					<br>
					<br>
					<br>
					</td>
					<td class="TableData">&quot;<input type="text"
						name="tel_no_dept" size="16" maxlength="23" class="BigInput"
						value="${sysuser.tel_no_dept }"> <br>
					<br>
					<br>
					<br>
					</td>
				</tr>
			</tbody>
			<tr>
				<td nowrap colspan="4" align="center"><input type="hidden"
					name="is_active" id="IS_ACTIVE" value="1"> <input
					type="hidden" value="${sysuser.user_name }" name="user_name2"
					id="user_name2"> <input type="hidden" name="dept_no"
					value="${sysuser.dept_no }"> <input type="hidden"
					name="is_exits" readonly> <input type="button" value="激活"
					onclick="dosubmit();" class="BigButton"> <!-- <input type="reset" onclick="doreset();" value="重填" class="BigButton"> -->
					<input type="button" value="返回" class="BigButton"
					onclick="history.go(-1);"> &nbsp;&nbsp;</td>
			</tr>
		</table>
	</form>

</body>
<script type="text/javascript">
	var flag="${sysuser.useing_key }"
	switch(flag){
	 case '0':
	 	document.all.noKey.checked=true;
		$("zhsh0").style.display="none";
	 	$("zhsh").style.display="none";
	 	$("zhsh1").style.display="none";
	 	$("zhsh2").style.display="none";
	 	$("zhsh3").style.display="none";
	 	break;
	 case '1':
	 	document.all.isKey.checked=true;
	 	$("getCertInfoButton").style.display="";
	 	$("zhsh0").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	break;
 	case '2':
 		document.all.isPFX.checked=true;
 		$("zhsh0").style.display="";
 		$("zhsh3").style.display="";
 		$("getCertInfoButton").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	break;
	 case '3':
	 	document.all.isIE.checked=true;
	 	$("getCertInfoButton").style.display="";
	 	$("zhsh0").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	break;	
	}}
</script>
</html>