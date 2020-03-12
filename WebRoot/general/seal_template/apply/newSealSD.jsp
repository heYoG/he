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
		<script type='text/javascript' src='/Seal/loadocx/MakeSealV6.js'></script>
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
        var user_no="${current_user.user_id}";
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
   document.getElementById("SealName").focus();
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
   }if(name_exist){
    var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
    if(name_exist){
 	  alert("印模名称已存在，请更换一个！");
 	  return false;
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
   }
    SealTemp.getMaxIDs('t_ba','c_baa',callback);
   }
}
function callback(tempid){
	
	var tempname=document.form1.temp_name.value;
	/*将相关信息写入控件*/
	document.getElementById("image_width").value=document.getElementById("sealmodel").value;
	document.getElementById("image_height").value=document.getElementById("sealmodel").value;
	document.getElementById("seal_width").value=document.getElementById("sealmodel").value;
	document.getElementById("seal_height").value=document.getElementById("sealmodel").value;
	document.all.MakeSeal.SealID=tempid;
	document.all.MakeSeal.SealName= document.getElementById("temp_name").value;
	document.all.MakeSeal.CompanyName=document.getElementById("dept_name").value;
	document.all.MakeSeal.SealBpp=8;
	document.form1.temp_data.value =document.all.MakeSeal.GetSealBase64();
	/*
	var m_ctrl = document.getElementById("DMakeSealV61");
	alert("m_ctrl:"+m_ctrl);
	m_ctrl.fSealWidthMM=document.getElementById("seal_width").value;
	m_ctrl.fSealHeightMM=document.getElementById("seal_height").value;
	m_ctrl.strSealName=document.getElementById("temp_name").value;
	m_ctrl.strSealID=tempid;
	m_ctrl.strCompName=document.getElementById("temp_name").value;
	m_ctrl.lBitCount=document.getElementById("seal_bit").value;
	var start=m_ctrl.NewSealStart();
	alert("start:"+start);
	var end= m_ctrl.NewSealEnd();
	alert("end:"+end);
	var k=m_ctrl.LoadData(document.all.MakeSeal.GetSealBase64());
	alert(k)
	alert("缩略图："+m_ctrl.GetPreviewImg(0));
	  var imagePath=document.all.MakeSeal.GetTempFile("bmp");
    alert(imagePath);
    var imageData= document.all.MakeSeal.GetSealBmp(imagePath,"bmp");
    alert(imageData);
	*/
	//alert(document.all.MakeSeal.GetPreviewImg(0));
    document.form1.preview_img.value = document.all.MakeSeal.GetPreviewImg(0);//设置印章缩略图数据
    if(confirm('确定要提交印模申请吗?请确保信息正确!')){  
     SealTemp.isExistTempName(tempname,subshow_msg);
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
function Seal_init(){
	document.all.MakeSeal.ViewFlag=1;
	document.all.MakeSeal.SealTemplate=1;//0,1 圆形；2 椭圆；3,4 双边圆形；5 双边椭圆；6 右左，7 左右，8 上下
	document.all.MakeSeal.TopArcText="演示印章样式图样";
	document.all.MakeSeal.MiddleLineText="演示章";
	document.all.MakeSeal.TopTextAngle="210";
	document.all.MakeSeal.RegenSealBmp();
}
function setTopArcText(){
	document.all.MakeSeal.TopArcText=document.all.TopArcText.value;
	document.all.MakeSeal.RegenSealBmp();
}
function setMiddleLineText(){
	document.all.MakeSeal.MiddleLineText=document.all.MiddleLineText.value;
	document.all.MakeSeal.RegenSealBmp();
}
function setTopTextAngle(){
	document.all.MakeSeal.TopTextAngle=document.all.TopTextAngle.value;
	document.all.MakeSeal.RegenSealBmp();
}
function setMoban(){
	document.all.MakeSeal.SealHeight=(document.all.MakeSeal.SealHeight/document.all.MakeSeal.SealWidth)*document.all.sealmodel.value*100;
	document.all.MakeSeal.SealWidth=document.all.sealmodel.value*100;
	document.all.MakeSeal.RegenSealBmp();
}
function setSize(){
	document.all.MakeSeal.SealTemplate=document.all.setsize.value;
	if(document.all.setsize.value==3){
		document.all.MakeSeal.InsideEllipseWidth=document.all.setsize.value;
	}
	if(document.all.setsize.value>5){
		document.all.MakeSeal.TopArcText="演示人";
	}else{
		document.all.MakeSeal.TopArcText="演示印章样式图样";
	}
	document.all.MakeSeal.RegenSealBmp();
}
function setV(){
document.all.MakeSeal.MidBmpIndex=document.getElementById("sealv").value;
document.all.MakeSeal.RegenSealBmp();
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
				<tr>
					<td nowrap class="TableContent" width=100>
						申请人：
						<br>
					</td>
					<td class="TableData">
						<input class="BigStatic" name="user_apply" type="hidden"
							value="${current_user.user_id }">
						<input class="BigStatic" name="user_apply2" type="text" readonly
							value="${current_user.user_name }">
						<br>
					</td>
					<td align="center" class="TableData" width="250" rowspan="11"
						colspan=2>
						<%@include file="../../../inc/makeSeal.jsp"%>
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
				</tr>
				<tr>
					<td nowrap class="TableContent">
						所属单位：
						<br>
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly"
							value="${current_user.dept_name }" />&nbsp;<input type="button" value="选 择" class="SmallButton"
							onclick="return openDeptList();" title="选择部门">
						<input type="hidden" name="dept_no"
							value="${current_user.dept_no }">
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
					<td class="TableContent">
						印章形状:
					</td>
					<td class="TableData">
						<select id="setsize" onchange="setSize()">
							<option value="1">
								圆形
							</option>
							<option value="2">
								椭圆
							</option>
							<option value="6">
								方形右左
							</option>
							<option value="7">
								方形左右
							</option>
							<option value="8">
								方形上下
							</option>
						</select>
					</td>
				</tr>
					<tr>
					<td nowrap class="TableContent">
						印章中间图形：
						<br>
					</td>
					<td nowrap class="TableData">
						<select name="sealv" class="BigSelect" onchange="setV()">
							<option value="0">红五角星</option>
							<option value="1">空五角星</option>						
							<option value="3">党徽1</option>
							<option value="4">党徽2</option>
							<option value="9">党徽3</option>
							<option value="10">党徽4</option>
							<option value="11">党徽5</option>
							<!--  
							<option style="display:none" value="2">国徽1</option>
							<option style="display:none" value="12">国徽2</option>
							<option style="display:none" value="33">八一五角星1</option>
							<option style="display:none" value="34">八一五角星2</option>
							<option style="display:none" value="35">八一五角星3</option>
							<option style="display:none" value="36">八一五角星4</option>
							<option style="display:none" value="37">八一五角星5</option>
							<option style="display:none" value="38">八一五角星6</option>
							<option style="display:none" value="39">八一五角星7</option>
							-->
					</select>
					</td>
				</tr>
				<tr>
					<td class="TableContent">
						印章尺寸:
					</td>
					<td class="TableData">
						<input id="sealmodel" name="sealmodel" value="42" size="5" onchange="setMoban()">
						mm
					</td>
				</tr>
				<tr>
					<td class="TableContent">
						印章主文字:
					</td>
					<td class="TableData">
						<input type="text" value="演示印章样式图样" id="TopArcText"
							onchange="setTopArcText()">
					</td>
				</tr>
				<tr>
					<td class="TableContent">
						名称弧度:
					</td>
					<td class="TableData">
						<input id="TopTextAngle" value="160" size="10"
							onchange="setTopTextAngle()">
					</td>
				</tr>
				<tr>
					<td class="TableContent">
						横线文字:
					</td>
					<td class="TableData">
						<input type="text" value="演示章" id="MiddleLineText"
							onchange="setMiddleLineText()">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width=100>
						审批人：
					</td>
					<td class="TableData">
						<input class="BigStatic" name="approve_user" type="hidden"
							value=${appMan_id }>
						<input class="BigStatic" name="approve_name" type="text" readonly
							value=${appMan_name }>
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
						<input type="hidden" value="" name="image_width" id="image_width">
						<input type="hidden" value="" name="image_height" id="image_height">
						<input type="hidden" value="" name="seal_width" id="seal_width">
						<input type="hidden" value="" name="seal_height" id="seal_height">
						<input type="hidden" value="8" name="seal_bit" id="seal_bit">
						<input type="hidden" name="type" value="3">
						<input type="button" value="提交" class="BigButton" id="tijiao"
							onclick="CheckForm();">
						&nbsp;&nbsp;
						<input type="reset" value="重填" onclick="setSize()" class="BigButton">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
