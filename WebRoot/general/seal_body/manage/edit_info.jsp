<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<html>
	<head>
		<title>印模提交</title>
		<base target="_self" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/inc/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/inc/js/utility.js"></script>
		<script src="js/utility.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		 <script type='text/javascript' src='/Seal/dwr/interface/LogSrv.js'></script>
         <script src="/Seal/js/logOper.js"></script>
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
var $ = function(id) {return document.getElementById(id);};

function openmodwin2() {
	var b = window.showModalDialog("/Seal/general/dept_tree.jsp?req=seal_temp3&&user_no=${current_user.user_id }",form1);
}
var old_name;		
function load_do()
{    
  if($("temp_data").value!="")
  { 
	  var obj = document.getElementById("DMakeSealV61");
	  if(!obj){
		  return false;
	  }
	   obj.SetEncBmp($("temp_data").value);
	}
	else
	  return;
}
function CheckForm()
{
   dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
  // logUpd2("更新印模",document.form1.temp_id.value,document.form1.SealName.value,"原名为:"+document.form1.temp_name2.value,"印模管理");//logOper.js
   document.form1.submit();
   self.close();
}

var name_exist=false;
//验证印模名称
function check_name(temp_name)
{
   if(temp_name==""||temp_name==old_name)
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
function openDeptList() {
	 var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
   }
</script>
	</head>

	<body class="bodycolor" topmargin="5"  onload="load_do()">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/edit.gif" align="absmiddle">
					<span class="big3"> 印模信息修改</span>
				</td>
			</tr>
		</table>

		<form  action="editTemp.do?type=update" method="post" name="form1">
			<table class="TableBlock" align="center" width="80%">
				<tr>
					<td nowrap class="TableContent" width="80">
						印章名称：
					</td>
					<td class="TableData">
						<input type="text" name="temp_name" id="SealName" size="20"
							maxlength="100" class="BigInput" value="${seal.seal_name }"
							readonly>
						&nbsp;
						<span id="temp_name_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						所属单位：
						<input type="hidden" name="dept_no" value="${seal.dept_no }">
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly"
							value="${seal.dept_name }" />
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openDeptList();" title="选择部门">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						印章类别：
					</td>
					<td nowrap class="TableData">
					<select name="seal_type" class="BigSelect">
					     <c:if test="${seal.seal_type=='公章'}">
							<option value="1" >
								公章
							</option>
							<option value="2">
								个人章
							</option>
							</c:if>
							 <c:if test="${seal.seal_type=='个人章'}">
							 <option value="2">
								个人章
							</option>
							<option value="1"  >
								公章
							</option>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						印章信息：
					</td>
					<td align="center" class="TableData" width="200">
						<%@include file="../../../inc/makeSealObject.jsp"%>
					</td>
				</tr>
				<tr class="TableControl">
					<td nowrap colspan="4" align="center">
						<input type="hidden" value="${seal.seal_id }" name="seal_id">
						<input type="hidden" value="update" name="type">
						<input type="hidden" value="${seal.seal_data }" name="seal_data"
							id="seal_data">
						<input type="button" value="保存" class="BigButton"
							onclick="CheckForm();">
						&nbsp
						<input type="button" value="返回" class="BigButton"
							onclick="history.go(-1);">
					</td>
				</tr>
			</table>
		</form>

	</body>
</html>