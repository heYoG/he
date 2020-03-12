<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>印模查询</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/module.js"></script>
		<script Language="JavaScript">
		
	//部门树
function openDeptList() {
	 var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);
 }
function CheckForm(form_action)
{
   document.form1.action=form_action;
   document.form1.submit();
}

//日历控件
function GetDate(nText) {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].start_time.value = reVal;
		} else {
			if (nText == 2) {
				document.forms[0].end_time.value = reVal;
			}
		}
	}
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
		onload="document.form1.temp_name.focus();">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/query.gif" align="absmiddle">
					<span class="big3"> 印模查询</span>
				</td>
			</tr>
		</table>
		<form action="tempManageList.do?type=flag1" method="post" name="form1">
			<table class="TableBlock" width="90%" align="center">
				<tr>
					<td nowrap class="TableData" width="120">
						印模名称：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="temp_name" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						印模类别：
					</td>
					<td nowrap class="TableData">
						<select name="seal_type" class="BigSelect">
							<option value=""></option>
							<option value="公章">
								公章
							</option>
							<option value="个人章">
								个人章
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						所属单位：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="dept_name" readonly="readonly" value="${current_user.dept_name }"/>
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openDeptList();" title="选择部门">
						<input type="hidden"  name="dept_no" value="${current_user.dept_no }">
					   &nbsp;
					<input type="hidden" name="is_junior"  id="is_junior" value="1">
					<input type="checkbox" name="is_junior2" checked  onclick="chkbox(this);" id="is_junior2">
					<label for="is_active">
					包含下级
					</label>
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
						<input type="button" value="查询" class="BigButton"
							onclick="CheckForm('/Seal/tempManageList.do?type=flag1');" title="印模查询" name="button">
						&nbsp;&nbsp;
					</td>
			</table>
		</form>
	</body>
</html>