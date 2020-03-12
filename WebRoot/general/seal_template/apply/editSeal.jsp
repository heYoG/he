<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
	<head>
		<title>印模提交</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>

		<style>
.input_red {
	border: 0;
	border-bottom: 1px red solid;
}
</style>
		<script src="js/utility.js"></script>
		<script Language="JavaScript">
//页面加载时调用
function load_do()
{
	//印模类型
	switch("${temp.seal_type}"){
		case "公章-法人章":document.form1.seal_type.options[0].selected="selected";
					break;
		case "普通公章":document.form1.seal_type.options[1].selected="selected";
					break;	
		case "冻结印章":document.form1.seal_type.options[2].selected="selected";
					break;
		case "解冻印章":document.form1.seal_type.options[3].selected="selected";
					break;
					
	}
	// document.form1.seal_type.options[${temp.seal_type }].selected="selected";
	//图片颜色深度
	switch("${temp.seal_bit}"){
		case "1":document.form1.seal_bit.options[0].selected="selected";
					break;
		case "4":document.form1.seal_bit.options[1].selected="selected";
					break;
		case "8":document.form1.seal_bit.options[2].selected="selected";
					break;	
		case "24":document.form1.seal_bit.options[3].selected="selected";
	}
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
function CheckForm(flag)
{
   if(document.form1.seal_type.value=="" || document.form1.temp_name.value=="")
   {
   	alert("必填字段不能为空！");
   	return false;
   }
    

 if(name_exist){
 	alert("印模名称已存在，请更换一个！");
 	return false;
 }
  document.form1.temp_status.value=flag;
  document.form1.submit();
}

var name_exist=false;
//验证印模名称
function check_name(temp_name)
{
   if(temp_name==""||temp_name=="${temp.temp_name }")
      return;
   document.getElementById("temp_name_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中……";
   SealTemp.isExistTempName(temp_name, show_msg);
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
	if(vBmpHeightMM == 0  || vBmpWidthMM == 0){
		alert("BMP图片错误");
		return false;
	}
	//设置页面表单之印章大小
	document.getElementById("BMPWidth").value=document.getElementById("SealWidth").value=vBmpWidthMM;
	document.getElementById("BMPHeight").value=document.getElementById("SealHeight").value=vBmpHeightMM;
	//设置控件之印章大小
	obj.fSealWidthMM = document.getElementById("SealWidth").value;
	obj.fSealHeightMM = document.getElementById("SealHeight").value;
	//设置控件之印章名称
	obj.strSealName = document.getElementById("SealName").value;
	//obj.strSealID = document.getElementById("SealID").value;
 	//设置控件之印章所属部门 
	obj.strCompName = document.getElementById("dept_name").text;
	obj.lBitCount = document.getElementById("SealBitCount").value;
	
	document.getElementById("temp_data").value = obj.GetEncBmp();
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
//选择部门时弹出窗口事件
function openmodwin() {
	var b = window.showModalDialog("/Seal/general/dept_tree.jsp?p=true&&req=seal_temp&&user_no=admin",form1);
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

function openmodwin() {
		var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_temp&&user_no=${current_user.user_id }",form1);
 }
 
function openDeptList() {
	// var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);
	 var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
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

		<form enctype="multipart/form-data" action="updateTempReg.do"
			method="post" name="form1">
			<table class="TableBlock" align="center" width="90%">
				<tr>
					<td nowrap class="TableContent" width=100>
						申请人：
					</td>
					<td class="TableData">
						<input class="BigStatic" name="temp_creator" type="text" readonly
							value="${temp.temp_creator }">
					</td>
					<td nowrap class="TableContent">
						<div>
							创建时间：
						</div>
					</td>
					<td class="TableData">
						<div>
							${temp.create_time }
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						印模名称：
					</td>
					<td class="TableData">
						<input type="text" name="temp_name" id="SealName" size="20"
							maxlength="100" class="BigInput" value="${temp.temp_name }"
							onblur="check_name(this.value)">
						&nbsp;
						<span id="temp_name_msg"></span>
					</td>
					<td align="center" class="TableData" width="250" rowspan="9"
						colspan=2>
						<%@include file="../../../inc/makeSealObject.jsp"%>
						<br>
						<div align="right">
							<input name="SelBMPFile" type="button" class="BigButton"
								id="SelBMPFile" value="选择BMP文件"
								onclick="return SelBMPFile_onclick()">
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						所属单位：
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly"
							value="${temp.dept_name }" />
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openDeptList();" title="选择部门">
						<input type="hidden" name="dept_no" value="${temp.dept_no }">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						印模类别：
					</td>
					<td nowrap class="TableData">
						<select name="seal_type" class="BigSelect">
							<option value="公章-法人章">
								公章-法人章
							</option>
							<option value="普通公章">
								普通公章
							</option>
							<option value="冻结印章">
								冻结印章
							</option>
							<option value="解冻印章">
								解冻印章
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						图片宽度：
					</td>
					<td class="TableData">
						<input type="text" name="image_width" id="BMPWidth" readonly
							size="10" maxlength="10" class="BigStatic"
							value="${temp.image_width }">
						mm
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						图片高度：
					</td>
					<td class="TableData">
						<input type="text" name="image_height" id="BMPHeight" readonly
							size="10" maxlength="10" class="BigStatic"
							value="${temp.image_height }">
						mm
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						按倍数自动调节：
					</td>
					<td class="TableData">
						<select name="AUTOSIZE" onchange="sealAuto()">
							<option value=""></option>
							<option value="1">
								等比例
							</option>
							<option value="2" >
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
					</td>
				</tr>
				<tr id="SealSi" style="display:none">
					<td class="TableContent"  width="100">
					分辨率：
					<br><br></td>
					<td class="TableData">&nbsp;<input type="text" name="seal_si"  size="10" maxlength="10" value="" onchange="">
					像素
					
					<input type="button" class="SmallButton" value="确定"
							onclick="getCertInfo();">
					<br><br></td>
					</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						印章宽度：
					</td>
					<td class="TableData">
						<input type="text" name="seal_width" id="SealWidth" size="10"
							maxlength="10" class="BigInput" value="${temp.seal_width }"
							onchange="sealWidth()">
						mm
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="100">
						印章高度：
					</td>
					<td class="TableData">
						<input type="text" name="seal_height" id="SealHeight" size="10"
							maxlength="10" class="BigInput" value="${temp.seal_height }"
							onchange="sealHeight()">
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
							<option value="4" >
								16色显示
							</option>
							<option value="8">
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
						<input class="BigStatic" name="approve_user" type="hidden" value=${temp.approve_user }>
						<input class="BigStatic" name="approve_name" type="text" readonly value=${userName }>
							<a href="" onclick="openmodwin();return false;">选择审批人</a>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						备注：
					</td>
					<td class="TableData" colspan=3>
						<textarea name="temp_remark" class="BigInput" cols="60" rows="3">${temp.temp_remark }</textarea>
					</td>
				</tr>
				<tr class="TableControl">
					<td nowrap colspan="4" align="center">
						<input type="hidden" value="${temp.temp_id }" name="temp_id">
						<input type="hidden" value="${temp.temp_data }" name="temp_data"
							id="temp_data">
						<input type="hidden" name="temp_status">
						<input type="button" value="提交" class="BigButton"
							onclick="CheckForm('1');">
						&nbsp;&nbsp;
						<input type="reset" value="重填" class="BigButton">
						&nbsp;&nbsp;
						<input type="button" value="返回" class="BigButton"
							onclick="history.go(-1);">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>

	</body>
</html>
