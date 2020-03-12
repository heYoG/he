<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>


<html>
	<head>
		<title>印章查询</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/module.js"></script>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/dialog.css">
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script Language="JavaScript">
function CheckForm(form_action)
{
   document.form1.action=form_action;
   document.form1.submit();
}
//日历控件
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

function GetDate(nText) {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	var val1 = null;
	var val2 = null;
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].create_time_start.value = reVal;
		} else {
			if (nText == 2) {
				document.forms[0].create_time_end.value = reVal;
		  }
		}
		val1 = toDate(document.forms[0].create_time_start.value);
		val2 = toDate(document.forms[0].create_time_end.value);
			 	if(val1 > val2){
					alert("开始日期大于结束日期");
					document.forms[0].create_time_start.value = "";
					document.forms[0].create_time_end.value = "";
		}
	}
	
}
	

function toDate(str){
	var sd = str.split("-");
	return new Date(sd[0],sd[1],sd[2]);
}

	//部门树
function openmodwin2() {
	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
}
	
function show_seal(ID){
	SealTemp.showData(ID,callback);
}

function callback(data){
	if(data!=""){
		var obj = document.getElementById("DMakeSealV61");
		if(!obj){
			alert("控件加载失败!");
			return false;
		}
		obj.SetEncBmp(data);
		ShowDialog('apply');
		obj.SetEncBmp("");
	}else
		alert("无图片信息!");
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

	<body class="bodycolor" topmargin="0">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/query.gif" align="absmiddle">
					<span class="big3"> 印章查询</span>
				</td>
			</tr>
		</table>
		<form action="sealShow.do" method="post" name="form1">
			<table class="TableBlock" width="600px" align="center">
				<tr>
					<td nowrap class="TableData" width="120">
						印章名称：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="seal_name" class="BigInput" size="20"
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
							onclick="return openmodwin2();" title="选择部门">
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
					<td nowrap class="TableData">
						印章类型：
					</td>
					<td nowrap class="TableData">
						<select name="seal_type" class="BigSelect">
							<option value="">
								全部类别
							</option>
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
					<td nowrap class="TableData" width="120">
						制章日期：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="create_time_start" size="10"
							maxlength="10" class="BigInput" onfocus="this.blur()" value="">
						<img onclick="GetDate(1);" src="../../../images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />
						至&nbsp;
						<input type="text" name="create_time_end" size="10" maxlength="10"
							class="BigInput" onfocus="this.blur()" value="">
						<img onclick="GetDate(2);" src="../../../images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />

					</td>
				</tr>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="button" value="查询" class="BigButton"
							onclick="CheckForm('../../../sealShow.do?type=flag2');"
							title="印章查询" name="button">
						&nbsp;&nbsp;
						<input type="reset" value="清空" class="BigButton" />
					</td>
			</table>
		</form>
		<br>
	</body>
</html>

