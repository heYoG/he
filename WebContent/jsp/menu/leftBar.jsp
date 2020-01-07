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
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.mobile.js"></script>
<script type="text/javascript" src="../../js/menu/leftBar.js"></script>
<script type="text/javascript">
	function fileRec() {
		alert("此区域暂未开放,敬请期待...");
	}
</script>
</head>
<body>
	<ul id="menuID"><!-- 后期修改成循环列出菜单 -->
		<li><span onclick="doAction(1,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')" id="spID1"><img alt="部门管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img1">部门管理</span>
			<ul style="display: none" id="u1">
				<li><a href="DeptAction.action" target="showPageName">部门管理</a></li>
				<li><a href="../dept/addDept.jsp" target="showPageName">新增部门</a></li>
			</ul></li>
		<li><span onclick="doAction(2,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')" id="spID2"><img alt="用户管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img2">用户管理</span>
			<ul style="display: none" id="u2">
				<li><a href="login!beforeAddUser.action" target="showPageName">新增用户</a></li>
				<li><a href="login!approveUser.action" target="showPageName">用户审批</a></li>
				<li><a href="login!userInfo.action" target="showPageName">用户管理</a></li>
			</ul></li>
		<li><span onclick="doAction(3,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')" id="spID3"><img alt="权限管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img3">权限管理</span>
			<ul style="display: none" id="u3">
				<li><a href="auth_authList" target="showPageName">权限管理</a></li>
			</ul>
		</li>
		<li><span onclick="doAction(4,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')"><img alt="文件管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img4">文件管理</span>
			<ul style="display: none" id="u4">
				<!-- type=1表示查询状态为1的正常文件,type=0表示查询已删除的文件 -->
				<li><a href="fileManage.action?type=1" target="showPageName">文件管理</a></li>
				<li><a href="../backup/fileBackup.jsp" target="showPageName">新增备份</a></li>
				<li><a href="../backup/searchRecoveryFile.jsp" target="showPageName">备份还原</a></li>
			</ul>
		</li>
		<li><span onclick="doAction(5,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')"><img alt="广告管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img5">广告管理</span>
			<ul style="display: none" id="u5">
				<li><a href="../ad/newAd.jsp" target="showPageName">新增广告</a></li>
				<li><a href="adManage.servletM" target="showPageName">广告管理</a></li>
			</ul>
		</li>
		<li><span onclick="doAction(6,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')"><img alt="印模管理" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img6">印模管理</span>
			<ul style="display: none" id="u6">
				<li><a href="../sealImage/newSealImage.jsp" target="showPageName">印模申请</a></li>
				<li><a href="sealImage!approveSealImage.action" target="showPageName">印模审批</a></li>
				<li><a href="sealImage.action" target="showPageName">印模管理</a></li>
			</ul>
		</li>
		<li><span onclick="doAction(7,'../../images/menu/tree_minus.gif','../../images/menu/tree_plus.gif')"><img alt="电子邮件" src="<%=request.getContextPath()%>/images/menu/tree_plus.gif" id="img7">邮件管理</span>
			<ul style="display: none" id="u7">
				<li>新建邮件</li>
				<li>收件箱</li>
				<li>发送箱</li>
				<li>草稿箱</li>
				<li>已删除邮件</li>
			</ul>
		</li>
	</ul>
</body>
</html>