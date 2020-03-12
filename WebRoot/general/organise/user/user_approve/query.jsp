<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>用户查询</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/module.js"></script>
		<script Language="JavaScript">
		
function openmodwin() {
	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
}
	//部门树
function openmodwin2() {
	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=check_user&&user_no=${current_user.user_id }",form1);
}		
function CheckForm(form_action)
{
   document.form1.action=form_action;
   document.form1.submit();
}


function GetDate(nText) {
	reVal = window.showModalDialog("../../../../inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	var val1 = null;
	var val2 = null;
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].start_time.value = reVal;
		} else {
			if (nText == 2) {
				document.forms[0].end_time.value = reVal;
		  }
		}
		val1 = toDate(document.forms[0].start_time.value);
		val2 = toDate(document.forms[0].end_time.value);
			 	if(val1 > val2){
					alert("开始日期大于结束日期");
					document.forms[0].start_time.value = "";
					document.forms[0].end_time.value = "";
		}
	}
	
}

function toDate(str){
	var sd = str.split("-");
	return new Date(sd[0],sd[1],sd[2]);
}
function chkbox(is_junior2){
		if(is_junior2.checked){
		document.form1.is_junior.value = 1 ;
		}else{
		document.form1.is_junior.value = 0 ;
		}
		
}
</script>
	</head>

	<body class="bodycolor" topmargin="0"
		onload="document.form1.user_id.focus();">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/query.gif" align="absmiddle">
					<span class="big3"> 用户查询</span>
				</td>
			</tr>
		</table>
		<form action="serUserApprove.do?type=flag2" method="post" name="form1">
			<table class="TableBlock" width="90%" align="center">
				<tr>
					<td nowrap class="TableData" width="120">
						用户名：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_id" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						用户姓名：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_name" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						部门：
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly"
							value="${current_user.dept_name }" />
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openmodwin();" title="选择部门">
						<input type="hidden" name="dept_no"
							value="${current_user.dept_no }">
						&nbsp;
						<c:if test="${current_user.is_junior =='0'}">
							<input type="hidden" name="is_junior" id="is_junior" value="0">
						</c:if>
						<c:if test="${current_user.is_junior =='1'}">
							<input type="hidden" name="is_junior" id="is_junior" value="1">
							<input type="checkbox" checked name="is_junior2" onclick="chkbox(this);"
								id="is_junior2">
							<label for="is_active">
								包含下级
							</label>
						</c:if>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						创建人：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="create_name2" readonly class="BigInput"
							size="20" maxlength="20">
						<input type="hidden" name="create_name" class="BigInput" size="20"
							maxlength="20">
						<a href="" onclick="openmodwin2();return false;">选择创建人</a> &nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						创建日期：
					</td>
					<td nowrap class="TableData">

						<input type="text" name="start_time" size="10" maxlength="10"
							class="BigInput" onfocus="this.blur()">
						<img onclick="GetDate(1);" src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />
						至&nbsp;
						<input type="text" name="end_time" size="10" maxlength="10"
							class="BigInput" onfocus="this.blur()">
						<img onclick="GetDate(2);" src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />

					</td>
				</tr>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="hidden" name="type" value="普通">
						<input type="submit" value="查询" class="BigButton"
							onclick="CheckForm('/Seal/serUserApprove.do?type=flag2');"
							title="用户查询" name="button">
						&nbsp;&nbsp;
						<input type="reset" value="清空" class="BigButton" />
					</td>
			</table>
		</form>
	</body>
</html>