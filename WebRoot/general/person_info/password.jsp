<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>修改密码</title>
<link rel="stylesheet" type="text/css" href="/Seal/theme/2/style.css">
<script src="js/ccorrect_btn.js"></script>
<script src="js/utility.js"></script>
<script type="text/javascript" src="js/loadActive.js"></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
<script type="text/javascript" src="/Seal/dwr/interface/MD5Util.js"></script>
<script src="/Seal/js/logOper.js"></script>
<script src="/Seal/js/util.js"></script>
<script type="text/javascript">
	var user_no = "${current_user.user_id}";
	var user_ip = "${user_ip}";
	if (user_no == "") {
		top.location = "/Seal/login.jsp";
	}
	var $ = function(id) {
		return document.getElementById(id);
	};
	function obj(result) {
		if (result == "false") {
			alert("原密码错误，请注意大小写！");
			return false;
		}
	}
	function checkForm() {
		var oldPsd = document.getElementById("old_psd").value;
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		var flag=SysUser.checkPsd(user_no, oldPsd, obj);//设置方法返回值
		if(flag=="false")
		return false;//原始密码错误终止程序
		var user_psd2 = document.getElementById("new_psd").value;
		var user_psd3 = document.getElementById("new_psd1").value;
		dwr.engine.setAsync(false);
		var MD5psd2=MD5Util.MD5(user_psd2);
		var expr = /^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()=\/|+_-].*$){6,}/;//密码必须包含大小写、字母和特殊字符的三种
		if ((user_psd2 == "") || (user_psd2 == null)) {
			alert("密码不能为空 !");
			return false;
		} else if (MD5psd2 == "${current_user.password1md5}"
				|| MD5psd2 == "${current_user.password2md5}"
				|| MD5psd2 == "${current_user.user_psd}") {
			alert("新密码与最近使用过的三次密码相同,请重新输入!");
			return false;
		} else if (!user_psd2.match(expr)) {
			alert("新的密码必须是字母、数字和特殊字符的组合且至少6位长度");
			return false;
		} else {
			if ((user_psd2.length < 6) || (user_psd2.length > 15)) {
				alert("密码长度在6-15之间 !");
				return false;
			}
			if (user_psd2 != user_psd3) {
				alert("两次输入的密码不一致，请重新输入 !");
				document.getElementById("new_psd").value = "";
				document.getElementById("new_psd1").value = "";
				document.getElementById("new_psd").focus();
				return false;
			}
		}
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		//   logUpd2("管理员更新密码",document.form1.user_id.value,document.form1.user_name.value,"管理员更新密码","系统管理");//logOper.js
		document.form1.submit();
		//  document.getElementById("form1").submit();   
	}
</script>
</head>
<body class="bodycolor" topmargin="5"
	onload="document.form1.old_psd.focus();">

	<table border="0" width="90%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big"><img src="/Seal/images/login.gif"> <span
				class="big3"> 修改密码</span> <br></td>
		</tr>
	</table>
	<form method="post" action="/Seal/personPsdUpdate.do" name="form1"
		onsubmit="return checkForm();">
		<table class="TableBlock" width="500" align="center">

			<tr class="Big">
				<td class="TableData" width="120"><b>用户名：</b></td>
				<td class="TableData"><b>${current_user.user_id }</b></td>
			</tr>
			<tr>
				<td class="TableData">原密码：</td>
				<td class="TableData"><input type="password" name="old_psd"
					class="BigInput" size="20"></td>
			</tr>

			<tr>
				<td class="TableData">新密码：</td>
				<td class="TableData"><input type="password" name="new_psd"
					class="BigInput" size="20" maxlength="20"> 密码长度在6-15之间</td>
			</tr>

			<tr>
				<td class="TableData">确认新密码：</td>
				<td class="TableData"><input type="password" name="new_psd1"
					class="BigInput" size="20" maxlength="20"> 密码长度在6-15之间</td>
			</tr>

			<tr style="display:none">
				<td class="TableData">密码过期：</td>
				<td class="TableData">密码永不过期</td>
			</tr>

			<tr align="center">
				<td class="TableControl" colspan="2"><input type="hidden"
					name="user_id" value="${current_user.user_id }" /> <input
					type="hidden" name="user_name" value="${current_user.user_name }" />
					<input type="button" value="保存修改" onclick="checkForm()" class='BigButton'></td>
			</tr>

		</table>
	</form>

	<!--<table border="0" width="90%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/green_arrow.gif" align="absmiddle">
					<span class="big3"> 最近10次修改密码日志</span>
					<br>
				</td>
			</tr>
		</table>
		<table class="MessageBox" align="center" width="320">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						无修改密码日志记录
					</div>
				</td>
			</tr>
		</table>  -->