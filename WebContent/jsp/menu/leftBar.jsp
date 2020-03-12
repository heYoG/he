<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单栏</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/menuCss.css">
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="../../js/menu/leftBar.js"></script>
</head>
<body>
	<ul id="menuID"><!-- 后期修改成循环列出菜单 -->
		<li><span onclick="doAction(1,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')" id="spID1"><img alt="部门管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img1">部门管理</span>
			<ul style="display: none" id="u1">
				<li><a href="<%=path%>/deptCtrl/deptInfo.do" target="showPageName">部门管理</a></li>
				<li><a href="../dept/addDept.jsp" target="showPageName">新增部门</a></li>
			</ul></li>
		<li><span onclick="doAction(2,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')" id="spID2"><img alt="用户管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img2">用户管理</span>
			<ul style="display: none" id="u2">
				<li><a href="<%=path%>/user/beforeAddUser.do" target="showPageName">新增用户</a></li>
				<li><a href="<%=path%>/user/approveUser.do" target="showPageName">用户审批</a></li>
				<li><a href="<%=path%>/user/userList.do" target="showPageName">用户管理</a></li>
			</ul></li>
	</ul>
</body>
</html>