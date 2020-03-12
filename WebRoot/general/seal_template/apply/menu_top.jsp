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
    <a href="../../../tempRegList.do" target="menu_main" title="印模申请记录" hidefocus="hidefocus"><span><img src="../../../images/menu/seal_orig.gif" width="16" height="16" align="absmiddle">印模申请记录</span></a> 
    <a href="../../../sealTempAdd.do?type=1" target="menu_main" title="新申请" hidefocus="hidefocus"><span><img src="../../../images/menu/seal_orig.gif" width="16" height="16" align="absmiddle"> 新申请</span></a>
    <!-- 
     <a href="../../../sealTempAdd.do?type=2" target="menu_main" title="新申请财政" hidefocus="hidefocus"><span><img src="../../../images/menu/seal_orig.gif" width="16" height="16" align="absmiddle">新申请财政</span></a>   
     -->
      <a href="../../../sealTempAdd.do?type=2" target="menu_main" title="手动制作印章" hidefocus="hidefocus"><span><img src="../../../images/menu/seal_orig.gif" width="16" height="16" align="absmiddle">手动制作印章</span></a>   
  </div>
</div>
</body>
</html>

