<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>新建用户</title>
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
		}else{
		document.form1.is_junior.value = 0 ;
		}		
      }
     function chkUser(chkuser){
       if(chkuser.checked){
        disp("tr_model1");
		disp("tr_model2");
		disp("zhsh");
		hidden("u_ip");
		hidden("u_ip1");
        document.getElementById("user_type2").checked=false;
       }
     }
   function openDeptList() {
	 var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
	// var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);
   }
   function openRoleList(){
	var b='default';
	b = window.showModalDialog("roleModeList.do?user_status=${current_user.user_status}&&user_id="+user_no,form1);
	if(b!='default'){
		document.form1.role_names.value = b ;
	}
   }
   function ClearUser(){
	var manage_range=document.form1.manage_range;
	manage_range.value="";
	var name_range=document.form1.name_range;
	name_range.value="";
   }
   var flag=true;
   function CheckForm(){
      SysUser.selUserNum(obj_User);
   }
   function obj_User(res){
   
    if(res=="false"){
       alert("用户数量已经超出了最大值！请联系管理员");
       return false;
    }
    if(document.form1.useing_key.value=="1" || document.form1.useing_key.value=="3"){
   		alert(document.form1.key_sn.value);
   		if(document.form1.key_sn.value==""){
   			alert("您选择了绑定证书，请获取证书信息!");
   			return false;
   		}
   }
    var userid=document.form1.user_id.value;
    if(userid==""){ 
     alert("用户名不能为空！");
     return false;
   }else{
     var pattern=/^\w+$/;
     if(!pattern.test(userid)){
     alert("用户名只能是以a-z,0-9或者下划线形式!");
     return false;
     }
   }
   var user_name=document.form1.user_name.value;
   if(user_name==""){ 
     alert("姓名不能为空！");
     return false;
   }else{
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(user_name)){
      alert("姓名只能只能包含中文、字母、数字!");
      return false;
     }
   }
   var user_psd=document.getElementById("user_psd").value;
   var user_psd2=document.getElementById("user_psd2").value;
   //var expr=/^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[+_-].*$){6,}/;//密码必须包含大小写、字母和特殊字符的三种	
   
   if(user_psd==""||user_psd2==""){
   	    alert("密码不能为空!");
       return false;
   }//else if(user_psd==userid){//密码不能和账号一样
   //alert("密码和用户名不能相同,请重新输入密码!");
   //return false;
   //} 
  // if((user_psd!="")&&(user_psd2!="")){
     if((user_psd.length < 6)||(user_psd.length > 15)){
       alert("密码长度在6-15之间!");
       return false;
  		 }else if(user_psd!=user_psd2){
    	  alert("两次输入的密码不一致，请重新输入!");
    	  document.getElementById("user_psd").value="";
   	  	  document.getElementById("user_psd2").value="";
	  	  document.getElementById("user_psd").focus();
      	  return false;
     	 }//else if(!user_psd.match(expr)){
			//alert("密码:"+user_psd+"格式不符!");
			//return false;
    // }	
  // }
  
   if(document.form1.role_nos.value==""){ 
     alert("角色不能为空！");
     return false;
   }
   if(document.form1.dept_name.value==""){ 
     alert("部门名称不能为空！");
     return false;
   }
   var rang_type=document.form1.rang_type.value;
   if(rang_type=="2"){
     if(document.form1.name_range.value==""){ 
        alert("管理范围部门不能为空！");
        return false;
       }
   }
   var user_remark=document.form1.user_remark.value;
   if(user_remark.length > 0){
    //alert("输入的备注框长度不能超过20个字符!");
    //return false;
   //}else{
       user_remark=user_remark.replace(/\'/g,"‘");
       document.form1.user_remark.value=user_remark;
     } 	
    //if((document.form1.mobil_no.value!=""))
   //{
   ///   var reg=/^0{0,1}13[0-9]{9}$/;
   //   var val=reg.test(document.form1.mobil_no.value);//判断布尔值，如果符合表达式则为true
    ///  if (!val)
    //  {
     //  alert("请输入正确的手机号码！");
    ///   }
     // }
    if((document.form1.user_email.value!="")){
      var emailExp = /[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}/;
      if(!document.form1.user_email.value.match(emailExp)){
   	    alert("请输入有效的E-mail地址！");
        return;  
        }
    }
    var myObj=document.getElementsByName("useing_key");
	for(i=0;i<myObj.length;i++){
	 if(myObj[i].checked){
	   if(myObj[i].value=="1" || myObj[i].value=="2" || myObj[i].value=="3"){
	      if(document.form1.begin_time.value=="" || document.form1.end_time.value==""){
           alert("证书有效期不能为空！");
           return false;
           }
	   }
	  }
	}
	
   dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
   //logAdd2("新增用户","",document.form1.user_name.value,"新增用户","用户管理");//logOper.js
   LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","新增用户","新增用户:'"+document.form1.user_name.value+"'成功");//logSys.js
   document.getElementById("form1").submit();
  
  }
function select_dept(){   
   var rang_type=document.form1.rang_type.value;
   if(rang_type=='1'){
     document.form1.is_junior2.checked=true;
     document.form1.is_junior.value = 1 ;
   }if(form1.rang_type.value=="2")
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
   if(dept_other.style.display=="none")
       dept_other.style.display='';
   else
       dept_other.style.display="none";
}
function check_user(){
     var userId=document.getElementById("user_id").value;
     if(userId==""){return;}
     var pattern=/^\w+$/;
     if(!pattern.test(userId)){
	     alert("用户名只能是以a-z,A-Z,0-9或者下划线形式!");
	     document.getElementById("user_id").value="";
	     return false;
     }
     document.getElementById("user_id_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
	 SysUser.isExistUser(userId,callback);
	 }
	 function callback(data){ 
	 if(data==1){
	 document.getElementById("user_id_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 该用户名已存在";
	 document.getElementById("user_id").value="";
	 }else
     document.getElementById("user_id_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
	}
function getCertInfo(){
	var obj = document.getElementById("DMakeSealV61");
	if(!obj){
	  alert("控件装载失败，请先安装控件!");
	  return false;
	}
	var i,myObj;
	myObj=document.getElementsByName("useing_key");
	for(i=0;i<myObj.length;i++){
	 if(myObj[i].checked){
	    // alert(myObj[i].value)
	   if(myObj[i].value=="3"){
	    // document.form1.key_id.value=obj.GetKeyID("");
	       //alert(obj);
	       //alert(obj.GetSerialNumberEx(2));
	       document.form1.key_sn.value=obj.GetSerialNumberEx(2); //1key里,2ie里
	       document.form1.key_dn.value=obj.GetCertDNEx(2);
	       document.form1.key_cert.value=obj.GetCardCert();
	       document.form1.cert_user.value=obj.GetSubjectNameEx(2);
	       //alert(obj.GetCertStartTimeEx(2));
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
	       document.form1.key_id.value=obj.GetKeyID(""); 
	       document.form1.key_sn.value=obj.SerialNumber; 
	       document.form1.key_dn.value=obj.GetCertDN();
	       document.form1.key_cert.value=obj.GetCardCert();
	       document.form1.cert_user.value=obj.SubjectName;
	       document.form1.begin_time.value=obj.GetCertStartTimeEx(0);
	       document.form1.end_time.value=obj.GetCertEndTimeEx(0);
	       if(document.form1.key_sn.value!=null&&document.form1.key_sn.value!=""){
		  //SysUser.getUserIdBy_key(obj.SerialNumber,callback1);
	       SysUser.getUserBy_keySN(document.form1.key_sn.value,callback1);
	      }
	     }
	    }
	   }
	  }
}
function callback1(data){
  if(data!=null){
   alert("key已经注册，请更换其他key!");
   // 	  document.form1.
   	  document.form1.key_id.value=""; 
	  document.form1.key_sn.value=""; 
	  document.form1.key_dn.value="";
	  document.form1.key_cert.value="";
	  document.form1.cert_user.value="";
	  document.form1.begin_time.value="";
	  document.form1.end_time.value="";
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
	 	$("zhsh4").style.display="none";
	 	clearData();
	 	break;
	 case '1':
	 	$("getCertInfoButton").style.display="";
	 	$("zhsh0").style.display="none";
	 	$("zhsh3").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	$("zhsh4").style.display="none";
	 	clearData();
	 	break;
 	case '2':
 		$("zhsh0").style.display="";
 		$("zhsh3").style.display="";
 		$("getCertInfoButton").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	$("zhsh4").style.display="none";
	 	clearData();
	 	break;
	 case '3':
 		$("getCertInfoButton").style.display="";
	 	$("zhsh0").style.display="none";
	 	$("zhsh3").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	$("zhsh4").style.display="none";
	 	clearData();
	 	break;
	 case '4':
 		$("zhsh0").style.display="none";
 		$("zhsh3").style.display="none";
 		$("getCertInfoButton").style.display="none";
	 	$("zhsh").style.display="";
	 	$("zhsh1").style.display="";
	 	$("zhsh2").style.display="";
	 	$("zhsh4").style.display="";
	 	clearData();
	 	break;
	}
}
//清空证书数据
function clearData(){
//   var temp=document.getElementById("pfxPath");
//   temp.select();
   document.getElementById("selectRoleButton").focus();//解决ie9不能获得路径问题
//   document.selection.clear();
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

function getCertInfo2(){
	var temp=document.getElementById("certPath");
	temp.select();
	document.getElementById("selectRoleButton").focus();//解决ie9不能获得路径问题
	var tempPath=document.selection.createRange().text;
	var obj = document.getElementById("DMakeSealV61");
	obj.CertGetInfo(tempPath,"CERTDATAFILE");
	//document.form1.key_id.value=obj.GetKeyID(""); 
    document.form1.key_sn.value=obj.GetSerialNumberEx(0); 
   	document.form1.key_dn.value=obj.GetCertDNEx(0);
   	document.form1.key_cert.value=obj.GetCardCert();
    document.form1.cert_user.value=obj.GetSubjectNameEx(0);
    document.form1.begin_time.value=obj.GetCertStartTimeEx(0);
    document.form1.end_time.value=obj.GetCertEndTimeEx(0);
    if(document.form1.key_sn.value!=null&&document.form1.key_sn.value!=""){
//SysUser.getUserIdBy_key(obj.SerialNumber,callback1);
    SysUser.getUserBy_keySN(document.form1.key_sn.value,callback1);
   }
}
</script>
	</head>
	<body class="bodycolor" topmargin="5"
		onload="document.form1.user_id.focus();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/notify_new.gif" align="absmiddle">
					<span class="big3"> 新建用户 （全局人员）</span>
				</td>
			</tr>
		</table>
		<form action="addUser.do" method="post" id="form1" name="form1" enctype="multipart/form-data"
			target="menu_main">
			<table class="TableBlock" width="95%" align="center">
				<tr style="display:none">
					<td nowrap class="TableHeader" colspan="2">
						<img src="images/green_arrow.gif" align="absMiddle">
						证书基本信息
					</td>
				</tr>
				<tr style="display:none">
					<td nowrap class="TableData">
						是否绑定证书：
					</td>
					<td nowrap class="TableData">
						<input name="useing_key" type="radio"  value="0" onclick="hiddenZS('0');" checked="checked"/>不绑定key证书
						<input name="useing_key" type="radio"  value="1" onclick="hiddenZS('1');" />绑定key证书
						<input name="useing_key" type="radio"  value="2" onclick="hiddenZS('2')"/>pfx文件证书
						<input name="useing_key" type="radio"  value="3" onclick="hiddenZS('3')"/>IE证书
						<input name="useing_key" type="radio"  value="4" onclick="hiddenZS('4')"/>Cert
					</td>
				</tr>
				<tr id="zhsh0" style="display:none">
					<td nowrap class="TableData">
						pfx路径：
					</td>
					<td nowrap class="TableData">
						<div id="fileDiv">
						<input type="file" name="pfxContent" id="pfxPath">	
						</div>				
					</td>
				</tr>
				<tr id="zhsh4" style="display:none">
					<td nowrap class="TableData">
						cert路径：
					</td>
					<td nowrap class="TableData">
						<div id="fileDiv">
						<input type="file" name="certContent" id="certPath">	
						<input type="button" class="SmallButton" value="获取证书信息"
							onclick="getCertInfo2();">
						</div>				
					</td>
				</tr>
				<tr id="zhsh3" style="display:none">
					<td nowrap class="TableData">
						证书密码：
					</td>
					<td nowrap class="TableData">
						<input type="password" name="pfxPsw" id="pfxPsw">	
						<input type="button" class="SmallButton" value="获取证书信息"
							onclick="getPfxInfo();">
					</td>
				</tr>
				<tr id="zhsh" style="display:none">
					<td nowrap class="TableData">
						证书序列号：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="key_sn" readOnly class="BigStatic"
							size="30" id="key_sn" value="">
						&nbsp;
						<input id="getCertInfoButton" type="button" class="SmallButton" value="获取证书信息"
							onclick="getCertInfo();">
					</td>
				</tr>
				<tr id="zhsh1" style="display:none">
					<td nowrap class="TableData">
						证书有效期：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="begin_time" readOnly class="BigStatic"
							size="20" id="begin_time" value="">-
						<input type="text" name="end_time" readOnly class="BigStatic"
							size="20" id="end_time" value="">	
					</td>
				</tr>
				<tr id="zhsh2" style="display:none">
					<td nowrap class="TableData">
						证书DN：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="key_dn" readOnly class="BigStatic"
							size="70" id="key_dn" value="">
						<input type="hidden" name="key_cert" id="key_cert" value="">
						<input type="hidden" name="key_id" id="key_id" value="">
						<input type="hidden" name="cert_user" id="cert_user" value="">
						<input type="hidden" name="cert_issue" id="cert_issue" value="">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableHeader" colspan="2">
						<img src="images/green_arrow.gif" align="absMiddle">
						用户基本信息
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="120">
						用户名：
					</td>
					<td nowrap class="TableContent">
						<input type="text" name="user_id" class="BigInput" size="20"
							maxlength="20" onblur="check_user(this.value)">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						姓名：
					</td>
					<td nowrap class="TableContent">
						<input type="text" name="user_name" class="BigInput" size="20"
							maxlength="30">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						密码：
					</td>
					<td nowrap class="TableContent">
						<input type="password" name="user_psd" id="user_psd"
							class="BigInput" size="20" maxlength="20" title="字母、数字和特殊字符组合">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						确认密码：
					</td>
					<td nowrap class="TableContent">
						<input type="password" name="user_psd2" id="user_psd2"
							class="BigInput" size="20" maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						角色：
						<input type="hidden" name="role_nos" id="role_nos" />
					</td>
					<td nowrap class="TableContent" id="sel_priv">
						<textarea cols=30 name="role_names" rows=2 class="BigStatic"
							wrap="yes" id="role_names" readonly></textarea>
						&nbsp;
						<input id="selectRoleButton" type="button" value="选 择" class="SmallButton"
							onClick="return openRoleList();" title="选择角色" />
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						部门：
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly"
							value="${dept_name }" />
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openDeptList();" title="选择部门">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableHeader" colspan="2">
						<img src="images/green_arrow.gif" align="absMiddle">
						用户权限信息
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						管理范围：
					</td>
					<td nowrap class="TableData">
						<select name="rang_type" id="rang_type" class="BigSelect"
							OnChange="select_dept()">
							<option value="0">
								本部门
							</option>
							<!--<option value="1">
								全体
							</option>
							<option value="2">
								指定部门
							</option> -->		
						</select>
						在管理型模块中起约束作用 &nbsp; &nbsp;
						<input type="hidden" name="is_junior" id="is_junior" value="1">
						<input type="checkbox" name="is_junior2" checked
							onclick="chkbox(this);" id="is_junior2">
						<label for="is_active">
							包含下级
						</label>
					</td>
				</tr>
				<tr id="dept" style="display: none;">
					<td nowrap class="TableData">
						管理范围（部门）：
					</td>

					<td class="TableData">
						<input type="hidden" name="manage_range" value="">
						<textarea cols=30 name="name_range" rows=2 class="BigStatic"
							wrap="yes" id=TO_NAME readonly></textarea>
						&nbsp;
						<input type="button" value="选 择" class="SmallButton"
							onClick="return openmodwin12();" title="选择部门" name="button">
						&nbsp;
						<input type="button" value="清 空" class="SmallButton"
							onClick="ClearUser()" title="清空部门" name="button">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						备注：
					</td>
					<td nowrap class="TableData">
						<textarea name="user_remark" id="user_remark" class="BigInput"
							cols="50" rows="2"></textarea>
					</td>
				</tr>
				<tr
					onclick="if(option2.style.display=='none') option2.style.display=''; else option2.style.display='none';"
					title="点击展开/收缩选项">
					<td nowrap class="TableHeader" colspan="2" style="cursor: pointer;">
						<img src="images/green_arrow.gif" align="absMiddle">
						用户可自定义选项
					</td>
				</tr>
				<tbody id="option2" style="display: none;">
					<tr>
						<td nowrap class="TableData">
							性别：
						</td>
						<td nowrap class="TableData">
							<select name="user_sex" class="BigSelect">
								<option value="0">
									男
								</option>
								<option value="1">
									女
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData">
							生日：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="user_birth" size="10" maxlength="10"
								class="BigInput" onfocus="this.blur()">
							<img onclick="GetDate(1);" src="images/654.gif"
								style="height: 20; cursor: hand" border="0" />
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData">
							界面主题：
						</td>
						<td class="TableData">
							<select name="user_theme" class="BigSelect">
								<option value="1">
									默认主题
								</option>
								<option value="2" selected>
									清新怡然
								</option>
								<option value="3">
									简洁明亮
								</option>
								<option value="4">
									粉红浪漫
								</option>
								<option value="5">
									Vista风格
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData">
							手机：
						</td>
						<td class="TableData">
							<input type="text" name="mobil_no" size="16" maxlength="23"
								class="BigInput" value="">
							<br>
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData">
							电子邮件：
						</td>
						<td class="TableData">
							<input type="text" name="user_email" size="25" maxlength="50"
								class="BigInput" value="">
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData">
							单位电话：
						</td>
						<td class="TableData">
							<input type="text" name="tel_no_dept" size="16" maxlength="23"
								class="BigInput" value="">
						</td>
					</tr>
				</tbody>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="hidden" name="is_active" id="is_active" value="1">
						<input type="hidden" name="type" value="2">
						<input type="hidden" name="dept_no" value="${dept_no }">
						<input type="hidden" name="is_exits" readonly>
						<input type="button" value="新 建" class="BigButton" title="新建用户"
							name="button" onclick="CheckForm()">
						&nbsp;&nbsp;
						<input type="button" value="返回" class="BigButton" title="返回窗口"
							onclick="history.back(-1)">
					</td>
					
			</table>
		</form>
	</body>
</html>