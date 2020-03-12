<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="../../../theme/${current_user.user_theme}/menu_top.css">
<script language="JavaScript" src="../../../js/menu_top.js"></script>
</head>
<body>
<div id="navPanel">
  <div id="navMenu">
    <a href="../../../userList.do" target="menu_main" title="创建记录" hidefocus="hidefocus"><span><img src="../../../images/menu/comm.gif" width="16" height="16" align="absmiddle">创建记录</span></a>
    <a href="../../../addUser.do?type=1" target="menu_main" title="新增用户" hidefocus="hidefocus"><span><img src="../../../images/menu/comm.gif" width="16" height="16" align="absmiddle">新增用户</span></a>
  </div>
</div>
</body>
</html>

