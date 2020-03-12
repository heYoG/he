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

		function check_dept(old_name)
      	{
      		var dept_name=document.getElementById("dept_name").value;
      		// alert(dept_name);
      		var parent_no=document.form1.dept_parent.value;
            if(dept_name==""||dept_name==old_name)
            	return;
            document.getElementById("dept_name_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
			SysDept.isExistSubDept(dept_name,parent_no,callback);
		}
		function callback(data)
		{  	
		    if(data==1){
		    	document.getElementById("dept_name_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 该部门名与其他部门重名！";
		    	document.getElementById("exist").value="1";
		    }else{
		       	document.getElementById("dept_name_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		       	document.getElementById("exist").value="0";
		    }		     	
		}
		
		function check_user(){
			if('${current_user.user_status}'!='1')
				SysDept.isInManageList('${dept.dept_no}','${current_user.user_id}',call);
		}
		function call(data){
			if(data==1){
				document.getElementById("in_list").value="1";
			}else{
				document.getElementById("in_list").value="0";
			}
		}
		
		
function CheckForm()
{
var f=document.form1;
	
	if(!f.dept_tab.value.isNumeric()||f.dept_tab.value.LengthW()>5){
		alert("请输入正确的排序号，排序号不宜过大!");
		f.dept_tab.select();
		return (false);
	}
  if(document.form1.dept_tab.value=="")
   { alert("部门排序号不能为空！");
     return (false);
   }

   if(document.form1.dept_name.value=="")
   { alert("部门名称不能为空！");
     return (false);
   }
   
   if(document.form1.parent_name.value=="")
   { alert("上级部门不能为空！");
     return (false);
   }else if($("parentIn").value=="0"){
		alert("对不起，您没有权限选择这个父部门！");
     	return (false);
   }
   
   if(document.form1.exist.value=="1")
   { alert("该部门名与其他部门重名！");
   	 document.form1.dept_name.select();
   	 document.form1.dept_name.focus();;
     return (false);
   }
   
   if(document.form1.in_list.value=="0")
   { alert("对不起，您没有修改该部门的权限！");
     return (false);
   }
   //if(!f.tel_no.value.isPhoneCall()&&f.tel_no.value!=""){
   //	alert("请输入正确的电话号码！");
   //	f.tel_no.select();
	//	return (false);
  // }
  // if(!f.fax_no.value.isPhoneCall()&&f.fax_no.value!=""){
  // 	alert("请输入正确的传真号码！");
   //	f.fax_no.select();
	//	return (false);
//   }
    if(f.dept_func.value.LengthW()>=255)
   { alert("部门职能字数不能超过255（中文不超过127）！");
   	 f.dept_func.select();
     return (false);
   }
   if(f.dept_parent.value.indexOf(f.dept_no.value)!=-1){
   	alert("不允许指定下级部门修改为上级部门！");
   	return false;
   }
    LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","部门管理","修改部门:'"+document.form1.dept_name.value);//logSys.js
}

function why_view()
{
  if(why_dept.style.display=='')
     why_dept.style.display='none';
  else
  	 why_dept.style.display='';
}

function openmodwin() {
	var dept_no='${dept.dept_no}';
	var url="/Seal/depttree/dept_tree.jsp?req=dept_chose&user_no=${current_user.user_id}";
	var b = window.showModalDialog(url,form1.dept_parent);	
	if(dept_no==form1.dept_parent.value){
		alert('请不要选择自身为父部门！');
		return false;
	}
	if(b!='default'&&$("parent_name").value!=b){
		document.form1.parent_name.value = b ;
		changeParent();
		check_dept('${dept.dept_name }');
	}
	return false;
}

function call(data){
	if(data==0){
		$("parentIn").value="0";
	}
}

function changeParent(){
	if('${current_user.user_status}'!='1')
		SysDept.isInManageList('${dept_no}','${current_user.user_id}',call);
}

function delete_dept(dept_no){
	if($("in_list").value=="0")
   { alert("对不起，您没有修改该部门的权限！");
     return (false);
   }
	if(confirm('确认要删除该部门/么？')){
		//alert(dept_no);
		LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","部门管理","删除部门:'"+document.form1.dept_name.value+"'");//logSys.js
		location="deleteDept.do?dept_no="+dept_no;
		alert("删除成功");
		window.open("manageDept.do",'main');
		
	} 
	
}
function delete_dept2(dept_no){
	alert("该部门下有子部门或者用户、模板、广告，不可直接删除该部门!");
}
function checkTel(){
	var telNo = document.form1.tel_no.value;
	if(telNo==""){
// 	alert("电话号码不正确！");
		return;
	}else{
	var pattern = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
	 if(!pattern.test(telNo)){
     document.getElementById("tel_no_msg").innerHTML="<img src='images/error.gif' align='absMiddle'> 电话号码不正确";
     document.getElementById("tel_no").value="";
     }else{
     document.getElementById("tel_no_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
     }
	}
	
}

</script>
	</head>
	<base target="dept_main" />
	<body class="bodycolor" topmargin="5" onload="check_user();">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/notify_new.gif" height="22" WIDTH="22">
					<span class="big3"> 编辑部门 - 当前节点：[${dept.dept_name }]</span>
				</td>
			</tr>
		</table>
		<form action="updateDept.do" method="post" name="form1" target="main"
			onsubmit="return CheckForm();">
			<table class="TableBlock" width="450" align="center">

				<tr>
					<td nowrap class="TableData">
						部门排序号：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="dept_tab" class="BigInput" size="10"
							maxlength="200"  value="${dept.dept_tab }">
						 &nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						部门名称：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="dept_name" class="BigInput" size="25"
							maxlength="25" value="${dept.dept_name }"
							onblur="check_dept('${dept.dept_name }')">
						&nbsp;
						<input type="hidden" name="dept_no" value="${dept.dept_no }" />
						<input type="hidden" name="exist" value="0" />
						<input type="hidden" name="in_list" value="1" />
						<span id="dept_name_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						银行序列：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="bank_dept" class="BigInput" size="25"
							value="${dept.bank_dept }" maxlength="25" />
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						电话：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="tel_no" class="BigInput" size="25"
							maxlength="25" value="${dept.tel_no }" onblur="checkTel();">
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
							maxlength="25" value="${dept.fax_no }">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						上级部门：
					</td>
					<td   class="TableData">
						<input type="text" name="parent_name" value="${parent_name }"
							readonly="readonly" />
						<input style="display:none" type="hidden" name="dept_parent" id="dept_parent"
							value="${dept.dept_parent }" />
						<input  style="display:none" type="hidden" name="parentIn" value="1" />
						<a style="display:none" href="" onclick="return openmodwin();">更改</a>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						部门职能：
					</td>
					<td nowrap class="TableData">
						<textarea name="dept_func" class="SmallInput" cols="60" rows="5">${dept.dept_func }</textarea>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="submit" value="保存修改" class="BigButton" title="保存修改"
							name="button">
					</td>
			</table>
		</form>
		<br>

		<table width="95%" border="0" cellspacing="0" cellpadding="0"
			height="3">
			<tr>
				<td background="/images/dian1.gif" width="100%"></td>
			</tr>
		</table>

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/system.gif" WIDTH="22" HEIGHT="20"
						align="absmiddle">
					<span class="big3"> 当前部门 - 相关操作</span>
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<input type="button" value="新建下级部门" class="BigButton"
				onClick="location='showDept.do?dept_no=${dept.dept_no }&&type=sub';"
				title="新建下级部门">
			<br>
			<br>
			 <c:if test="${dept.is_detpflow==2 and dept.is_deptuser==2 and dept.is_depttemp==2 and dept.is_deptseal==2}">
			<input type="button" value="删除当前部门" class="BigButton"
				onClick="delete_dept('${dept.dept_no }')" title="删除当前部门">
		   </c:if>
		    <c:if test="${dept.is_detpflow==1 or dept.is_deptuser==1 or dept.is_depttemp==1 or dept.is_deptseal==1}">
			<input type="button" value="删除当前部门" class="BigButton"
				onClick="delete_dept2('${dept.dept_no }')" title="删除当前部门">
		   </c:if>
		</div>

	</body>
</html>