<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>无纸化管理平台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="/Seal/theme/${current_user.user_theme}/style.css">
<link rel="stylesheet" type="text/css"
	href="/Seal/theme/pageSplit/pageSplit.css">
<link rel="stylesheet" type="text/css"
	href="/Seal/theme/${current_user.user_theme}/dialog.css">
<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script src="/Seal/js/ccorrect_btn.js"></script>
<script src="/Seal/js/util.js"></script>
<script src="/Seal/js/String.js"></script>
<script src="/Seal/js/dateUtil.js"></script>
<script src="/Seal/js/tableUtil.js"></script>
<script src="/Seal/js/dialog.js"></script>
<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script type="text/javascript">
	var user_no = "${current_user.user_id}";
	var current_user_name = "${current_user.user_name}";
	var user_ip = "${user_ip}";
	if (user_no == "") {
		top.location = "/Seal/login.jsp";
	}
</script>
</head>
<body class="bodycolor">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big"><img src="/Seal/images/menu/comm.gif"><span
				class="big3">审批信息</span>
			</td>
		</tr>
	</table>
<div style="margin-left:7px">
		<table width="100%" class="tab" >

			<tr class="TableHeader">
				<td nowrap align="center" >审批时间</td>
				<td nowrap align="center">审批人</td>
			</tr>
			<c:forEach var="aa" items="${showUser.datas}" varStatus="status">
				<tr class="tableLine">
					<td nowrap align="center">${aa.operate_time}</td>
					<td nowrap align="center">${aa.operate_user}</td>
				</tr>
			</c:forEach>
			<tr>
				<td>
			</tr>
		</table>
</div>
</body>
</html>
