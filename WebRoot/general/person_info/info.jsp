<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>编辑个人资料</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script type="text/javascript" src="js/loadActive.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
         <script src="/Seal/js/logOper.js"></script>
         <script src="/Seal/js/util.js"></script>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

		<script Language="JavaScript">
		 var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
      var $=function(id){return document.getElementById(id);};
      function load(){
	    $('user_sex').options["${current_user.user_sex}"].selected="selected";
        }
  
    function dotj(){
    if(document.form1.USER_NAME.value=="")
    { alert("姓名不能为空！");
     return false ;
    }
    if((document.form1.user_email.value!=""))
    {
    var emailExp = /[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}/;
    if(!document.form1.user_email.value.match(emailExp))
   	{
   	alert("请输入有效的E-mail地址！");
    return false ;  
    }
    }
    dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
   //  logUpd2("管理员更新信息",document.form1.user_id.value,document.form1.USER_NAME.value,"管理员更新信息","系统管理");//logOper.js
    document.form1.submit();
  }
		</script>
	</head>
	<body class="bodycolor" topmargin="5" onload="load();">
		<%@include file="/inc/calender.jsp"%>
		<table border="0" width="90%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/node_user.gif" WIDTH="16" HEIGHT="16"
						align="absmiddle">
					<span class="big3"> 个人资料</span>
					<br>
				</td>
			</tr>
		</table>

		<html:form action="personInfoUpdate.do" method="post" styleId="form1"
			onsubmit="return CheckForm();">
			<table class="TableBlock" width="450" align="center">
				<tr>
					<td nowrap class="TableHeader" colspan="2">
						<b>&nbsp;基本信息</b>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						姓名：
					</td>
					<td class="TableData">
						<input type="text" name="USER_NAME" size="10" maxlength="10"
							class="BigStatic" readonly value="${current_user.user_name }">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						性别：
					</td>
					<td class="TableData">
						<select name="user_sex" class="BigSelect">
							<option value="0" selected>
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
					<td class="TableData">
						<input type="text" name="user_birth" size="20" maxlength="20"
							class="BigInput" value="${current_user.user_birth }">
						<img onclick="setday(this,document.getElementById('user_birth'));"
							src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />
					</td>
				</tr>
				<tr>
					<td nowrap class="TableHeader" colspan="2">
						<b>&nbsp;联系方式</b>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						单位电话：
					</td>
					<td class="TableData">
						<input type="text" name="tel_no_dept" size="25" maxlength="25"
							class="BigInput" value="${current_user.tel_no_dept }">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						手机：
					</td>
					<td class="TableData">
						<input type="text" name="mobil_no" size="23" maxlength="23"
							class="BigInput" value="${current_user.mobil_no }">
					<!-- <input type="checkbox" name="MOBIL_NO_HIDDEN" id="MOBIL_NO_HIDDEN">
						<label for="MOBIL_NO_HIDDEN">
							手机号码不公开
						</label> -->	
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						电子邮件：
					</td>
					<td class="TableData">
						<input type="text" name="user_email" size="25" maxlength="50"
							class="BigInput" value="${current_user.user_email }">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableHeader" colspan="2">
						<b>&nbsp;家庭信息</b>
					</td>
				</tr>

				<tr>
					<td nowrap class="TableData">
						家庭住址：
					</td>
					<td class="TableData">
						<input type="text" name="add_home" size="40" maxlength="100"
							class="BigInput" value="${current_user.add_home }">
					</td>
				</tr>

				<tr>
					<td nowrap class="TableData">
						家庭邮编：
					</td>
					<td class="TableData">
						<input type="text" name="post_no_home" size="25" maxlength="25"
							class="BigInput" value="${current_user.post_no_home }">
					</td>
				</tr>

				<tr>
					<td nowrap class="TableData">
						家庭电话：
					</td>
					<td class="TableData">
						<input type="text" name="tel_no_home" size="25" maxlength="25"
							class="BigInput" value="${current_user.tel_no_home }">
					</td>
				</tr>

				<tr align="center" class="TableControl">
					<td colspan="2" nowrap>
						<input type="hidden" name="user_id"
							value="${current_user.user_id }" />
						<input type="button" value="保存修改" onclick="dotj();" class="BigButton">
					</td>
				</tr>
			</table>
		</html:form>

	</body>
</html>