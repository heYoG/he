<%@page contentType="text/html;charset=utf-8"%>
<%@include file="../../inc/tag.jsp"%>


<html>
	<head>
		<title>新建应用系统</title>
		<link rel="stylesheet" type="text/css"
			href="../../theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script type="text/javascript" src="js/loadActive.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/AppSystem.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script src="/js/logOper.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script Language="JavaScript">	
		var user_no="${current_user.user_id}";
		var current_user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		function delIp(){
		var f=document.form1;
		selectDelSel(f.select_ip);
		document.form1.app_ip.value="";
		var strall=selectValues(f.select_ip);
		document.form1.app_ip.value=strall;
		}
		function addIp(){
	     var f=document.form1;
		 var str=newModalDialog('/Seal/general/appSystem/addIp.jsp',300,100,f);
		 if(str !=""){
		   if($("user_ip").value!=""){
		    if(selectExist(f.select_ip,$("user_ip").value)==false){   
		       selectAdd(f.select_ip,$("user_ip").value,$("user_ip").value);		   
		     }	
		   }  
		 }
		   var strall=selectValues(f.select_ip);
		   document.form1.app_ip.value=strall;
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
     // document.getElementById("useing_key").checked=false;
    }
}
function chkServer(chkuser){
    if(chkuser.checked){
        disp("u_ip");
		disp("u_ip1");
		hidden("tr_model1");
		hidden("tr_model2");
		hidden("zhsh");
		// document.form1.useing_key.checked
		document.getElementById("user_type").checked=false;
        document.getElementById("useing_key").checked=false;
    } 
}
//部门树
function openmodwin() {
	var b = window.showModalDialog("deptTree.do?type=sealBody",form1.dept_no);
	if(b!='default'){
		document.form1.dept_name.value = b ;
	}
	return false;
}

function openmodwin2() {
	var manage_range=document.form1.manage_range;
	var b = window.showModalDialog("deptTree.do?type=mode2",manage_range);
	if(b!='default'){
		document.form1.name_range.value = b ;
	}
	return false;
}

function openmodwin3() {
	 var f=document.form1;
     var str=newModalDialog('/Seal/general/gaizhang_app/appl_chose2.jsp',500,500,f);
}


function ClearUser(){
	var manage_range=document.form1.manage_range;
	manage_range.value="";
}

var flag=true;
function CheckForm()
{   
   if(document.getElementById("app_ip").value=="")
   { alert("应用系统IP不能为空！");
    return;
   }
   check_user();
    var userid=document.form1.app_no.value;
   if(userid=="")
   { //alert("应用系统编号不能为空！");
     return false;
   }else{
      var pattern=/^\w+$/;
      	if(!pattern.test(userid)){
      	 alert("应用系统编号只能是以a-z,0-9或者下划线形式!");
      	 return false;
      }
    }
    var user_name=document.form1.app_name.value;
    if(user_name=="")
   { alert("应用系统名称不能为空！");
     return;
   }else{
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(user_name)){
      alert("应用系统名称只能包含中文、字母、数字!");
      return false;
     }
    }
    var xt_pwd=document.form1.app_pwd.value;
    var xt_pwd1=document.form1.xt_pwd1.value;
    var len = xt_pwd.length;
     if(xt_pwd !=xt_pwd1){
          alert("两次输入的应用系统密码不一致，请重新输入 !");
          document.form1.app_pwd.value="";
          document.form1.xt_pwd1.value="";
          return false;
      }else{
   		if(xt_pwd.length>0){
   		  if((xt_pwd.length< 6)||(xt_pwd.length >15)){
       		 alert("应用系统密码长度在6-15之间 !");
       		 document.form1.app_pwd.value="";
         	 document.form1.xt_pwd1.value="";
       		 return false;
    	 }
	 }
   }
 	   	 
 
    dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    //logAdd("新增应用系统","",document.form1.user_name.value,"新增应用系统","应用系统管理");//logOper.js
    LogSys.logAdd(user_no,current_user_name,user_ip,"应用系统管理","新增系统名称："+document.form1.app_name.value);
    document.getElementById("form1").submit();
   
}

		
function select_dept()
{
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
      		var userId=document.getElementById("app_no").value;
      		
            if(userId==""){
            return;
            }
            var pattern=/^\w+$/;
         	if(!pattern.test(userId)){
      	     alert("应用系统名只能是以a-z,0-9或者下划线形式!");
      	     return false;
             }
            document.getElementById("user_id_msg").innerHTML="<img src='../../images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
			AppSystem.isExistServer(userId,callback)
		    }
		  function callback(bo)
		    {
		    var data;
		    if(bo==true){
		    	data=1;
		    }else{
		    	data=2;
		    }  	
		    if(data==1)
		    {
		    document.getElementById("user_id_msg").innerHTML="<img src='../../images/error.gif' align='absMiddle'> 该应用系统编号已存在";
		    document.getElementById("app_no").value="";
		    } 
		     else
		     document.getElementById("user_id_msg").innerHTML="<img src='../../images/correct.gif' align='absMiddle'>";
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

function hiddenZS(){
	if($("zhsh").style.display==""){
		$("zhsh").style.display="none";
	}else{
		$("zhsh").style.display="";
	}
}

</script>
	</head>

	<body class="bodycolor" topmargin="5">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="../../images/menu/system.gif" align="absmiddle">
					<span class="big3"> 新建应用系统</span>
				</td>
			</tr>
		</table>
		<form action="../../addAppSystem.do" method="post" name="form1"
			target="menu_main">
			<table class="TableBlock" width="95%" align="center">
				<tr id="u_ip">
					<td nowrap class="TableHeader" colspan="2">
						<img src="../../images/green_arrow.gif" align="absMiddle">
						应用系统IP:
					</td>
				</tr>
				<tr id="u_ip1">
					<td nowrap class="TableContent" width="120">
						应用系统IP：
					</td>
					<td nowrap class="TableContent">
						<select multiple="multiple" name="select_ip" id="select_ip"
							size="6" style="width: 170; height: 120">
						</select>
						<input type="hidden" name="user_ip" id="user_ip" />
						<input type="hidden" name="app_ip" id="app_ip" />
						<input type="button" value="添加" class="SmallButton"
							onclick="addIp();" title="添加">
						<input type="button" value="删除" class="SmallButton"
							onclick="delIp();" title="删除">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableHeader" colspan="2">
						<img src="../../images/green_arrow.gif">
						应用系统信息
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="120">
						应用系统编号：
					</td>
					<td nowrap class="TableContent">
						<input type="text" name="app_no" class="BigInput" size="10"
							maxlength="20" onblur="check_user(this.value)">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						应用系统名称：
					</td>
					<td nowrap class="TableContent">
						<input type="text" name="app_name" class="BigInput" size="10"
							maxlength="30">
						&nbsp;
					</td>
				</tr>
				<tr style="display: none">
					<td nowrap class="TableContent" width="120">
						应用系统用户名：
					</td>
					<td nowrap class="TableContent">
						<input type="text" name="xt_user" class="BigInput" size="10"
							maxlength="20">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="120">
						应用系统密码：
					</td>
					<td nowrap class="TableContent">
						<input type="password" name="app_pwd" class="BigInput" size="10"
							maxlength="20">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="120">
						应用系统确认密码：
					</td>
					<td nowrap class="TableContent">
						<input type="password" name="xt_pwd1" class="BigInput" size="10"
							maxlength="20">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
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