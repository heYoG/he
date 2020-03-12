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
		<li><span onclick="doAction(1,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')" id="spID1"><img alt="部门管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img1">公告管理</span>
			<ul style="display: none" id="u1">
				<li><a href="../notice/sendNotice.jsp" target="showPageName">发布公告</a></li>
				<li><a href="<%=path%>/notice/noticeList.do" target="showPageName">公告管理</a></li>
			</ul>
		</li>
	</ul>
</body>
</html>