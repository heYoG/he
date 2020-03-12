<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title></title>

<link rel="stylesheet" type="text/css" href="../../theme/${current_user.user_theme}/menu_top.css">
<script language="JavaScript" src="../../js/menu_top.js"></script>
</head>
<body>
<div id="navPanel">
  <div id="navMenu">
    <a href="../../appSystemList.do" target="menu_main" title="应用系统管理" hidefocus="hidefocus"><span><img src="../../images/menu/system.gif" width="16" height="16" align="absmiddle">应用系统管理</span></a>
    <a href="../../general/appSystem/server_new.jsp" target="menu_main" title="新建应用系统" hidefocus="hidefocus"><span><img src="../../images/menu/system.gif" width="16" height="16" align="absmiddle">新建应用系统</span></a>
  </div>
</div>
</body>
</html>

