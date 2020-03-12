<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>用户列表结果</title>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script src="/Seal/js/util.js"></script>
	<script>
function checkPage(){
	var page=document.getElementById("pageindex");
	var reg = /^[0-9]{1,3}$/g;
	if (!reg.test(page.value) || eval(page.value) > eval('${pageSplit.totalPage}') || page.value == 0) {
		alert("输入不是合理的页数或超出范围，请重输！");
		page.select();
		page.focus();
	}else{
		location="showUserlist.do?pageIndex="+page.value;
	}
}
function show_sch(){
   disp("d_search");
   hidden("d_list");
}
</script>
	</head>
	<body class="bodycolor" style="margin-top: 0;">
		<table class="MessageBox" align="center" width="290">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						没有符合条件的记录！
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
