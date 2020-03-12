<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<html>
	<head>
		<title>印模提交</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="js/utility.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script src="/Seal/js/util.js"></script>

		<style>
.input_red {
	border: 0;
	border-bottom: 1px red solid;
}
</style>
		<script Language="JavaScript">	
        var user_no="theme:"+"${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
function openmodwin() {
		var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_temp&&user_no=${current_user.user_id }",form1);
 }
function openDeptList() {
	// var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);
	 var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
 }
//页面加载时调用
function load_do()
{
	//如果存在数据源
  if(document.getElementById("temp_data").value!="")
  {
	  var obj = document.getElementById("DMakeSealV61");//获得印模控件
	  if(!obj){
		  return false;
	  }
	  //为印模控件填充数据
	  obj.SetEncBmp(document.getElementById("temp_data").value);
	}
	else
	  return;
}
//页面提交前调用，验证表单
function CheckForm(){ 	
    dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    SealTemp.selTempNum(f_data);  
    
}
function f_data(ps){
  if(ps=="false"){
    alert("已经超过了最大印章数，请联系管理员！");
    return false;
  }else{
  	
   if(document.form1.temp_name.value=="" || document.form1.temp_name.value=="")
   {
   	alert("印模名称不能为空！");
   	return false;
   }else{  
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
     if(!pattern.test(document.form1.temp_name.value)){
       alert("印模名称是以中文,英文,数字!");
       return false;
     }
   } if(name_exist){
    var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
    if(name_exist){
 	  alert("印模名称已存在，请更换一个！");
 	  return false;
    }
    }
   if(document.form1.dept_name.value=="" || document.form1.dept_name.value=="")
   {
   	alert("单位名称不能为空！");
   	return false;
   }
   if(document.form1.seal_type.value=="" || document.form1.seal_type.value=="")
   {
   	alert("印模类别不能为空！");
   	return false;
   }
   if(document.form1.approve_name.value=="" || document.form1.approve_name.value=="")
   {
   	alert("审批人不能为空！");
   	return false;
   }
   var temp_remark=document.form1.temp_remark.value;
   if(temp_remark.length > 0)
   {
 	 temp_remark=temp_remark.replace(/\'/g,"‘");
     document.form1.temp_remark.value=temp_remark;
    }    
    SealTemp.getMaxIDs('t_ba','c_baa',callback);
   }
}
function callback(tempid){
	
	var tempname=document.form1.temp_name.value;
	/*将相关信息写入控件*/
    var m_ctrl = document.getElementById("DMakeSealV61");
	m_ctrl.fSealWidthMM = document.getElementById("seal_width").value;
	m_ctrl.fSealHeightMM = document.getElementById("seal_height").value;
	m_ctrl.strSealName = document.getElementById("temp_name").value;
	m_ctrl.strSealID =tempid;
	m_ctrl.strCompName =  document.getElementById("dept_name").value;
	m_ctrl.lBitCount = document.getElementById("seal_bit").value;
	m_ctrl.NewSealStart();
	if(0 != m_ctrl.NewSealEnd()){
		alert("载入印模失败");
		return false;
	}
	document.form1.temp_data.value = m_ctrl.SaveData();//设置印章数据
	document.form1.preview_img.value = m_ctrl.GetPreviewImg(0);//设置印章缩略图数据
   if(confirm('确定要提交印模申请吗?请确保信息正确!')){  
   
    //logAdd2("新增印模","",document.form1.temp_name.value,"增加印模","印模管理");//logOper.js
     SealTemp.isExistTempName(tempname,subshow_msg);
   // LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理","新增印模:'"+document.form1.temp_name.value+"'");//logSys.js
   // document.form1.submit();
   }

}
function subshow_msg(req)
{  
	if(req==1)
	{
		document.getElementById("temp_name_msg").innerHTML="<img src='images/error.gif' align='absMiddle'>名称已存在";
		name_exist=true;
		return false;
	} 
	else{
	   dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
       //logAdd2("新增印模","",document.form1.temp_name.value,"增加印模","印模管理");//logOper.js
       LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理","新增印模:'"+document.form1.temp_name.value+"'");//logSys.js
       document.form1.submit();
	}
}
var name_exist=false;
//验证印模名称
function check_name(temp_name)
{
   if(temp_name=="")
      return ;
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
   if(!pattern.test(document.form1.temp_name.value)){
     //alert("印模名称是以中文,英文,数字!");
     return false;
   }
   document.getElementById("temp_name_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中……";
   // SealTemp.isExistTempName(temp_name,function(show_msg){
   //	 	alert(show_msg);
   // });
   SealTemp.isExistTempName(temp_name,show_msg)
}
function show_msg(req)
{
	if(req==1)
	{
		document.getElementById("temp_name_msg").innerHTML="<img src='images/error.gif' align='absMiddle'>名称已存在";
		name_exist=true;
	} 
	else{
		document.getElementById("temp_name_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		name_exist=false;
	}
}
//选择BMP文件时单击事件
function SelBMPFile_onclick(){
	var obj;
	obj = document.getElementById("DMakeSealV61");//获得印模控件
	if(!obj){
		return false;
	}
	//在本地选择BMP文件，如果未选择则返回false
	if(0 != obj.SelectBmpFile())
		return false;
	//从控件中获得BMP文件的高度和宽度，如果为0则返回false
	var vBmpHeightMM = parseInt(obj.fBmpHeightMM*100)/100; 
	var vBmpWidthMM = parseInt(obj.fBmpWidthMM*100)/100;
	var BmpPath = obj.strBmpPath;
	SealTemp.getBmpName(BmpPath,function bmpName(BmpName){
		document.getElementById("temp_name").value=BmpName;
	});
	if(vBmpHeightMM == 0  || vBmpWidthMM == 0){
		alert("BMP图片错误");
		return false;
	}
	//设置页面表单之印章大小
	
	document.getElementById("BMPWidth").value=vBmpWidthMM;
	document.getElementById("BMPHeight").value=vBmpHeightMM;
	sealAuto();
	//设置控件之印章大小
	//obj.fSealWidthMM = document.getElementById("SealWidth").value;
// 	obj.fSealHeightMM = document.getElementById("SealHeight").value;
	//设置控件之印章名称
// 	obj.strSealName = document.getElementById("SealName").value;
	//obj.strSealID = document.getElementById("SealID").value;
 	//设置控件之印章所属部门 
// 	obj.strCompName = document.getElementById("dept_name").text;
// 	obj.lBitCount = document.getElementById("SealBitCount").value;
	// alert(document.getElementById("temp_data").value);
	//var data=document.getElementById("temp_data").value;	
	//alert("之前:"+data.substring(data.length-4,data.length));
	//obj.SetEncBmp(data);
	//alert("之后:"+data.substring(data.length-4,data.length));
	//alert("载入图片成功");
	check_name(document.form1.temp_name.value);
}

function sealWidth(){
	document.form1.seal_height.value=(document.form1.image_height.value/document.form1.image_width.value)*document.form1.seal_width.value;
}
function sealHeight(){
	document.form1.seal_width.value=(document.form1.image_width.value/document.form1.image_height.value)*document.form1.seal_height.value;
}
function sealAuto(){
	if(document.form1.AUTOSIZE.value=="5"){
		$("SealSi").style.display="";
		
	}else{
		$("SealSi").style.display="none";
		document.form1.seal_height.value=document.form1.image_height.value/document.form1.AUTOSIZE.value;
		document.form1.seal_width.value=document.form1.image_width.value/document.form1.AUTOSIZE.value;
	}
	
}

function getCertInfo(){
	var dpi=document.form1.seal_si.value;
	var  pattern =/^[0-9]*[1-9][0-9]*$/i;
	
	if(!pattern.test(dpi)){
		alert("请输入正整数");
		return false;
	}else{
		document.form1.seal_height.value=document.form1.image_height.value*96/dpi;
		document.form1.seal_width.value=document.form1.image_width.value*96/dpi;
	}
}	
/*日历控件*/
function GetDate(nText) {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].able_btime.value = reVal;
		} else {
			if (nText == 2) {
				document.forms[0].able_etime.value = reVal;
			}
		}
	}
}
</script>
	</head>
	<body class="bodycolor" topmargin="5" onload="load_do();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/notify_new.gif" align="absmiddle">
					<span class="big3"> 印模提交</span>
				</td>
			</tr>
		</table>

		<html:form action="sealTempAdd.do" method="post" styleId="form1"
			target="menu_main">
			<table class="TableBlock" align="center" width="90%">
				<tr style="display: none">
					<td nowrap class="TableContent" width=100>
						印章ID：
						<br>
					</td>
					<td class="TableData">
						<input class="BigInput" id="seal_czid" type="text"
							name="seal_czid">
						<br>
					</td>
				</tr>
				<tr style="display: none">
					<td nowrap class="TableContent" width="120">
						印章有效期：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="able_btime" size="10" maxlength="10"
							class="BigInput" onfocus="this.blur()" value="">
						<img onclick="GetDate(1);" src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />
						至&nbsp;
						<input type="text" name="able_etime" size="10" maxlength="10"
							class="BigInput" onfocus="this.blur()" value="">
						<img onclick="GetDate(2);" src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />

					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width=100>
						申请人：
						<br>
						<br>
					</td>
					<td class="TableData">
						<input class="BigStatic" name="user_apply" type="hidden"
							value="${current_user.user_id }">
						<input class="BigStatic" name="user_apply2" type="text" readonly
							value="${current_user.user_name }">
						<br>
						<br>
					</td>
					<td nowrap class="TableContent">
						<div style="display: none;">
							申请时间：
						</div>
						<br>
						<br>
					</td>
					<td class="TableData">

						<br>
						<br>
					</td>

				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						印模名称：
						<br>
					</td>
					<td class="TableData">
						<input type="text" name="temp_name" id="SealName" size="20"
							maxlength="100" class="BigInput" value=""
							onblur="check_name(this.value);">
						&nbsp;
						<span id="temp_name_msg"></span>
						<br>
					</td>
					<td align="center" class="TableData" width="250" rowspan="10"
						colspan=2>
						<%@include file="../../../inc/makeSealObject.jsp"%>
						<br>
						<div align="right">
							<input name="SelBMPFile" type="button" class="BigButton"
								id="SelBMPFile" value="选择BMP文件"
								onclick="return SelBMPFile_onclick()">
						</div>
						<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						所属单位：
						<br>
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly"
							value="${current_user.dept_name }" />
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openDeptList();" title="选择部门">
						<input type="hidden" name="dept_no"
							value="${current_user.dept_no }">
						<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						印模类别：
						<br>
					</td>
					<td nowrap class="TableData">
						<select name="seal_type" class="BigSelect">
							<option value="gzfrz">
								公章-法人章
							</option>
							<option value="ptgz">
								普通公章
							</option>
							<option value="djyz">
								冻结印章
							</option>
							<option value="jdyz">
								解冻印章
							</option>
						</select>
						<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						图片宽度：
						<br>
					</td>
					<td class="TableData">
						<input type="text" name="image_width" id="BMPWidth" readonly
							size="10" maxlength="10" class="BigStatic" value="">
						mm
						<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						图片高度：
						<br>
					</td>
					<td class="TableData">
						<input type="text" name="image_height" id="BMPHeight" readonly
							size="10" maxlength="10" class="BigStatic" value="">
						mm
						<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						按倍数自动调节：
						<br>
					</td>
					<td class="TableData">
						<select name="AUTOSIZE" onchange="sealAuto()">
							<option value="1">
								等比例
							</option>
							<option value="2" selected="selected">
								图片2倍于印章
							</option>
							<option value="3">
								图片3倍于印章
							</option>
							<option value="4">
								图片4倍于印章
							</option>
							<option value="5">
								自定义像素大小
							</option>
						</select>
						<br>
					</td>
				</tr>
				<tr id="SealSi" style="display: none">
					<td class="TableContent" width="100">
						分辨率：
						<br>
						<br>
					</td>
					<td class="TableData">
						&nbsp;
						<input type="text" name="seal_si" size="10" maxlength="10"
							value="" onchange="">
						像素

						<input type="button" class="SmallButton" value="确定"
							onclick="getCertInfo();">
						<br>
						<br>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						印章宽度：
					</td>
					<td class="TableData">
						<input type="text" name="seal_width" id="SealWidth" size="10"
							maxlength="10" class="BigInput" value="" onchange="sealWidth()">
						mm
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						印章高度：
					</td>
					<td class="TableData">
						<input type="text" name="seal_height" id="SealHeight" size="10"
							maxlength="10" class="BigInput" value="" onchange="sealHeight()">
						mm
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						图片颜色深度：
					</td>
					<td class="TableData">
						<select name="seal_bit" id="SealBitCount">
							<option value="1">
								单色显示
							</option>
							<option value="4">
								16色显示
							</option>
							<option value="8" selected>
								256色显示
							</option>
							<option value="24">
								24位真彩色
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width=100>
						审批人：
					</td>
					<td class="TableData">
						<input class="BigStatic" name="approve_user" type="hidden"
							value="${appMan_id }">
						<input class="BigStatic" name="approve_name" type="text" readonly
							value="${appMan_name }">
						<a href="" onclick="openmodwin();return false;">选择审批人</a>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						备注：
					</td>
					<td class="TableData" colspan=3>
						<textarea name="temp_remark" class="BigInput" cols="60" rows="3"></textarea>
					</td>
				</tr>
				<tr class="TableControl">
					<td nowrap colspan="4" align="center">
						<input type="hidden" value="" name="ID" id="ID">
						<input type="hidden" name="client_system">
						<input type="hidden" value="" name="temp_data" id="temp_data">
						<input type="hidden" value="" name="preview_img" id="preview_img">
						<input type="hidden" name="temp_status">
						<input type="hidden" name="type" value="0">
						<input type="button" value="提交" class="BigButton" id="tijiao"
							onclick="CheckForm();">
						&nbsp;&nbsp;
						<input type="reset" value="重填" class="BigButton">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>

	</body>
</html>
