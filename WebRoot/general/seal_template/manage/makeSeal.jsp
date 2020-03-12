<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>

<html>
	<head>
		<title>印章制作</title>
		<style>
.input_red {
	border: 0;
	border-bottom: 1px red solid;
}
</style>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
	    <script type='text/javascript' src='/Seal/dwr/interface/LogSrv.js'></script>
         <script src="/Seal/js/logOper.js"></script>
         <script src="/Seal/js/util.js"></script>
		<script Language="JavaScript">
		 var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
var $=function(id){return document.getElementById(id);};

//印章装载		
function load_do()
{
   //alert(document.getElementById("temp_data").value);
  if(document.getElementById("temp_data").value!="")
  {
	  var obj = document.getElementById("DMakeSealV61");
	  //alert(obj)
	  if(!obj){
		  return false;
	  }
	  var l=obj.LoadData(document.getElementById("temp_data").value);
	  var vID = 0; 
	  vID = obj.GetNextSeal(vID);
	  obj.SelectSeal(vID)
	}
	else
	  return;
}


/*验证表单*/
function CheckForm(flag)
{
checkSeal_name();

  if(document.form1.seal_name.value=="")
   {
   	alert("印章名称不能为空！");
   	return false;
   }else{  
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(document.form1.seal_name.value)){
      alert("印章名称是以中文,英文,数字!");
      return false;
    }
   }
    var temp_remark=document.form1.temp_remark.value;
      if(temp_remark.length > 0)
      {
      // alert("输入的备注信息长度不能超过20个字符!");
     //  return false;
    ///}else{
       temp_remark=temp_remark.replace(/\'/g,"‘");
       document.form1.temp_remark.value=temp_remark;
     } 	
   /*将相关信息写入控件*/
    var m_ctrl = document.getElementById("DMakeSealV61");
	m_ctrl.fSealWidthMM = document.getElementById("seal_width").value;
	m_ctrl.fSealHeightMM = document.getElementById("seal_height").value;
	m_ctrl.strSealName = document.getElementById("seal_name").value;
	m_ctrl.strSealID =document.getElementById("temp_id").value;
	m_ctrl.strCompName =  document.getElementById("seal_name").value;
	m_ctrl.lBitCount = document.getElementById("seal_bit").value;
	m_ctrl.NewSealStart();
	if(0 != m_ctrl.NewSealEnd()){
		alert("载入印模失败");
		return false;
	}
	document.form1.seal_data.value = m_ctrl.SaveData();//设置印章数据
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    logAdd2("新增印章","",document.form1.seal_name.value,"新增印章","印章管理");//logOper.js
    document.form1.submit();
}
/*验证表单*/
function CheckForm2(flag)
{

checkSeal_name();
  if(document.form1.seal_name.value=="")
   {
   	alert("印章名称不能为空！");
   	return false;
   }else{  
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(document.form1.seal_name.value)){
      alert("印章名称是以中文,英文,数字!");
      return false;
    }
   }
    var temp_remark=document.form1.temp_remark.value;
      if(temp_remark.length > 0)
      {
      // alert("输入的备注信息长度不能超过20个字符!");
     //  return false;
    ///}else{
       temp_remark=temp_remark.replace(/\'/g,"‘");
       document.form1.temp_remark.value=temp_remark;
     } 	
 if(confirm("确定要选择防伪五角星图案并制章?")){
   /*将相关信息写入控件*/
    var m_ctrl = document.getElementById("DMakeSealV61");
    m_ctrl.SetEncBmp(document.getElementById("seal_data").value);
    m_ctrl.SetFangWeiBmpPath("");
	m_ctrl.fSealWidthMM = document.getElementById("seal_width").value;
	m_ctrl.fSealHeightMM = document.getElementById("seal_height").value;
	m_ctrl.strSealName = document.getElementById("seal_name").value;
	m_ctrl.strSealID =document.getElementById("temp_id").value;
	m_ctrl.strCompName =  document.getElementById("seal_name").value;
	m_ctrl.lBitCount = document.getElementById("seal_bit").value;
	m_ctrl.NewSealStart();
	if(0 != m_ctrl.NewSealEnd()){
		alert("载入印模失败");
		return false;
	}
	document.form1.seal_data.value = m_ctrl.SaveData();//设置印章数据
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    //logAdd2("新增印章","",document.form1.seal_name.value,"新增印章","印章管理");//logOper.js
    //alert(document.form1.seal_data.value);
    document.form1.submit();
    }
}
/*检查印章是否存在*/
 function checkSeal_name()
      		{
      		var seal_name=document.getElementById("seal_name").value;
            if(seal_name=="")
            return;
            var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
            if(!pattern.test(document.form1.seal_name.value)){
            alert("印章名称是以中文,英文,数字!");
            return false;
            }
            document.getElementById("user_id_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
			dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
			SealBody.isExistSealName(seal_name,callback)
		    }
		  function callback(data){	
		   if(data==1)
		    {
		    document.getElementById("user_id_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 该印章名称已存在";
		    document.getElementById("seal_name").value="";
		    } 
		     else
		     document.getElementById("user_id_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		   }

//部门树
function openmodwin() {
	var b = window.showModalDialog("/Seal/general/dept_tree.jsp?req=seal_temp3&&user_no=${current_user.user_id }",form1);
}
function openmodwin55() {
	var b = window.showModalDialog("/Seal/general/dept_tree2.jsp?p=true&&req=seal_temp18&&user_no=${current_user.user_id }",form1);;
}
function openmodwin3() {
	var b='default';
	// b = window.showModalDialog("roleModeList.do?user_status=${user_status }",form1);
	b = window.showModalDialog("roleModeList.do?user_status=1",form1);
	if(b!='default'){
		document.form1.role_names.value = b ;
	}
}
function ClearUser(){
	var user_id=document.form1.user_id;
	user_id.value="";
	var user_name=document.form1.user_name;
	user_name.value="";
}
//页面相关项装载

function load(){
	// document.form1.seal_type.options[${template.seal_type}].selected="selected";
	//客户端软件
	var clients="${template.client_system }";
	var cs=clients.split(",");
	for(i=0;i<cs.length;i++){
		if(cs[i]!=""){
			document.getElementById(cs[i]).checked=true;
		}
	}
}

var certdata_array = new Array(); //添加的证书绑定的数量
/*添加区域并提交*/
function addAreaAnSubmit()
{
	 checkSeal_name();

  if(document.form1.seal_name.value=="")
   {
   	alert("印章名称不能为空！");
   	return false;
   }else{  
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(document.form1.seal_name.value)){
      alert("印章名称是以中文,英文,数字!");
      return false;
    }
   }
    var temp_remark=document.form1.temp_remark.value;
      if(temp_remark.length > 0)
      {
      // alert("输入的备注信息长度不能超过20个字符!");
     //  return false;
    ///}else{
       temp_remark=temp_remark.replace(/\'/g,"‘");
       document.form1.temp_remark.value=temp_remark;
     } 	
   /*将相关信息写入控件*/
    var m_ctrl = document.getElementById("DMakeSealV61");
    alert(m_ctrl);
	m_ctrl.fSealWidthMM = document.getElementById("seal_width").value;
	m_ctrl.fSealHeightMM = document.getElementById("seal_height").value;
	m_ctrl.strSealName = document.getElementById("seal_name").value;
	m_ctrl.strSealID =document.getElementById("temp_id").value;
	m_ctrl.strCompName =  document.getElementById("seal_name").value;
	m_ctrl.lBitCount = document.getElementById("seal_bit").value;
	m_ctrl.NewSealStart();
	if(0 != m_ctrl.NewSealEnd()){
		alert("载入印模失败");
		return false;
	}
	m_ctrl.ShowDialog(2,0);
	
	document.form1.seal_data.value = m_ctrl.SaveData();//设置印章数据
	//dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    //logAdd2("新增印章","",document.form1.seal_name.value,"新增印章","印章管理");//logOper.js
    document.form1.submit();
}
function create_seal(tempid,tempname){
  if(confirm("确定要制章吗?")){
   LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理","制作印章："+tempname);//logSys.js
   SealTemp.CreateSeal(tempid,user_no,crsObj);
  }
}
function crsObj(data){
	var is_junior=document.getElementById("is_junior").value;
	var temp_name=document.getElementById("temp_name").value;
	var start_time=document.getElementById("start_time").value;
	var end_time=document.getElementById("end_time").value;
	var seal_type=document.getElementById("seal_type").value;
	var dept_no=document.getElementById("dept_no").value;
    location="/Seal/tempManageList.do?type=flag1&&is_junior="+is_junior+"&&temp_name="+temp_name+"&&start_time="+start_time+
    "&&end_time="+end_time+"&&seal_type="+seal_type+"&&dept_no="+dept_no;
}
</script>
	</head>

	<body class="bodycolor" topmargin="5" onload="load_do();load();">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 印章制作</span>
				</td>
			</tr>
		</table>
		<form action="sealBodySave.do" method="post" name="form1" >
			<table class="TableBlock" align="center" width="80%">
				<tr>
					<td nowrap class="TableContent" width="80">
						印模名称：
					</td>
					<td class="TableData">
						<font color=red size=4>${template.temp_name}</font>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="80">
						印章名称：
					</td>
					<td class="TableData">
						<input type="text" id="seal_name" name="seal_name" size="20" maxlength="100"
							class="BigInput" value="${template.temp_name}" onblur="checkSeal_name(this.value)">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>	
				<tr>
					<td nowrap class="TableContent">
						备注：
					</td>
					<td class="TableData">
						<textarea name="temp_remark" class="BigInput" cols="60" rows="3">${template.temp_remark }</textarea>
					</td>
				</tr>
				<tr>
					<td class="TableContent">
						印章：
					</td>
					<td align="center" class="TableData" width="250">
						<%@include file="../../../inc/makeSealObject.jsp"%>
					</td>
				</tr>
				<tr class="TableControl">
					<td nowrap colspan="4" align="center">
						
						<input type="hidden" name="temp_data" id="temp_data" value="${template.temp_data }">
						<!-- <input type="hidden" value="admin" name="CREATOR">-->
						<input type="hidden" name="client_system" id="client_system" value="${template.temp_data }">
						<input type="hidden" name="preview_img" id="preview_img" >
						<input type="hidden" id="seal_width" name="seal_width" value="${template.seal_width }">
						<input type="hidden" id="seal_height" name="seal_height" value="${template.seal_height }">
						<input type="hidden" id="seal_bit" name="seal_bit" value="${template.seal_bit}">
                        <input type="hidden" id="temp_id" name="temp_id" value="${template.temp_id}">
						<input type="button" value="保存" class="BigButton"
							onclick="create_seal;">
						<input style="display:none" type="button" value="选择防伪五角星图案并制章" class="BigButton"
							onclick="CheckForm2('0');">
						&nbsp;
						<input type="button" value="添加区域并提交" class="BigButton"
							onclick="addAreaAnSubmit();">
						&nbsp;
						<input type="button" value="返回" class="BigButton"
							onclick="history.back(-1)">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>