<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
	<head>
		<title>部门管理</title>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/module.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/js/util.js'></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script Language="JavaScript">
        var $=function(id){return document.getElementById(id);};
		function check_dept()
      	{
      		var dept_name=document.getElementById("dept_name").value;
      		var parent_no=document.form1.parent_no.value;
             if(dept_name=="")
            	return;
             document.getElementById("dept_name_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
			 SysDept.isExistSubDept(dept_name,parent_no,callback);
		}
		function callback(data)
		{  	
		    if(data==1)
		    {
		    	document.getElementById("dept_name_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 该部门名已存在";
		    	document.getElementById("exist").value="1";
		    }else{
		       	document.getElementById("dept_name_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		       	document.getElementById("exist").value="0";
		    }		     	
		}

function filter(val){
	if(val.indexOf("'")!=-1){
		return false;
	}else if(val.indexOf("\\")!=-1){
		return false;
	}
	return true;
}

function CheckForm()
{
	var f=document.form1;
	
	if(!f.dept_tab.value.isNumeric()||f.dept_tab.value.LengthW()>5){
		alert("请输入正确的排序号，排序号不宜过大!");
		f.dept_tab.select();
		return (false);
	}
   if(document.form1.parent_name.value=="")
   { alert("上级部门不能为空！");
     return (false);
   }
   //else if($("inManage").value=="0"){
	//	alert("对不起，您没有权限在这个父部门下新增部门！");
   //  	return (false);
   //}
   if(document.form1.dept_tab.value=="")
   { alert("部门排序号不能为空！");
     return (false);
   }

   if(document.form1.dept_name.value=="")
   { alert("部门名称不能为空！");
     return (false);
   }
 //  if(!f.tel_no.value.isPhoneCall()&&f.tel_no.value!=""){
  // 	alert("请输入正确的电话号码！");
  // 	f.tel_no.select();
//		return (false);
  // }

 //  if(!f.fax_no.value.isPhoneCall()&&f.fax_no.value!=""){
  // 	alert("请输入正确的传真号码！");
  /// 	f.fax_no.select();
//	return (false);
 ///  }
   if(document.form1.exist.value=="1")
   { alert("该部门名已存在！");
   	 document.form1.dept_name.select();
   	 document.form1.dept_name.focus();;
     return (false);
   }
   if(f.dept_func.value.LengthW()>=255)
   { alert("部门职能字数不能超过255（中文不超过127）！");
   	 f.dept_func.select();
     return (false);
   }
   
    LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","新增部门","新增部门:'"+document.form1.dept_name.value+"'成功");//logSys.js
}


function call(data){
	if(data==0){
		$("inManage").value="0";
	}
}
function checkTel(){
	var telNo = document.form1.tel_no.value;
	if(telNo==""){
// 	alert("电话号码不正确！");
		return;
	}else{
	var pattern = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
	var pattern1 = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	 if(!pattern.test(telNo)){
     document.getElementById("tel_no_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 电话号码不正确";
     document.getElementById("tel_no").value="";
     }else{
     document.getElementById("tel_no_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
     }
	}
	
}
function why_view()
{
  if(why_dept.style.display=='')
     why_dept.style.display='none';
  else
  	 why_dept.style.display='';
}

function openmodwin() {
	var url="/Seal/depttree/dept_tree.jsp?req=dept_chose&user_no=${current_user.user_id}";
	var b = window.showModalDialog(url,form1.parent_no);
	if(b!='default'&&b!='${dept.dept_name}'){
		document.form1.parent_name.value = b ;
		changeParent();
		check_dept();
	}
	return false;
}

function load(){
	$("dept_name").focus();
	if('${current_user.user_status}'!='1')
		SysDept.isInManageList('${dept_no}','${current_user.user_id}',call);
}

function changeParent(){
	if('${current_user.user_status}'!='1')
		SysDept.isInManageList('${dept_no}','${current_user.user_id}',call);
}

</script>
	</head>

	<body class="bodycolor" topmargin="5" onload="load();">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/notify_new.gif" height="22" WIDTH="22">
					<span class="big3"> 新建部门 - 当前节点：[${dept_name }]</span>
				</td>
			</tr>
		</table>
		<form action="addDept.do" method="post" name="form1" target="main"
			onsubmit="return CheckForm();return false;">
			<table class="TableBlock" width="450" align="center">

				<tr>
					<td nowrap class="TableData">
						部门排序号：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="dept_tab" class="BigInput" size="10"
							maxlength="200">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						部门名称：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="dept_name" class="BigInput" size="25"
							maxlength="25" onblur="check_dept(this.value)" />
						<input type="hidden" name="exist" value="0" />
						<span id="dept_name_msg"></span>
					</td>
				</tr>
				<tr>
				<tr>
					<td nowrap class="TableData">
						银行序列：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="bank_dept" class="BigInput" size="25"
							maxlength="25" />
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						电话：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="tel_no" class="BigInput" size="25"
							maxlength="25" onblur="checkTel();">
						&nbsp;
						<span id="tel_no_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						传真：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="fax_no" class="BigInput" size="25"
							maxlength="25">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						上级部门：
					</td>
					<td class="TableData">
						<input type="text" name="parent_name" value="${dept_name }"
							readonly="readonly" />
						<input type="hidden" name="parent_no" id="parent_no"
							value="${dept_no }" />
						<input type="hidden" name="inManage" value="1" />
						<a href="" onclick="return openmodwin();">更改</a>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						部门职能：
					</td>
					<td nowrap class="TableData">
						<textarea name="dept_func" class="SmallInput" cols="60" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="submit" value="新 建" class="BigButton" title="新建"
							name="button">
					</td>
			</table>
		</form>
		<br>
		<div class="small" align=center style="font-color: white">
			<a href="javascript:why_view()">为什么无法新建部门？</a>
			<div>
				<br>
				<div id="why_dept" class="small1" style="display: none">
					您可能使用了遨游浏览器，遨游浏览器有一个广告猎手功能，其网址屏蔽规则设置的过于严格，某些字母组合开头的网址（如ad开头的页面网址）会被屏蔽，建议合理进行相关设置，或关闭其广告猎手功能，或使用IE浏览器。
					<div>
	</body>
</html>