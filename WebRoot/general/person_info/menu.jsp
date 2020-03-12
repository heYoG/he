<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>帐户信息菜单</title>
<link rel="stylesheet" type="text/css" href="../../theme/2/style.css">
<script src="../../inc/js/ccorrect_btn.js"></script>
</head>
<body class="bodycolor" topmargin="5">


<table border="0" width="90%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><img src="../../images/menu/person_info.gif" WIDTH="22" HEIGHT="20" align="absmiddle"><span class="big3">帐户信息</span><br>
    </td>
  </tr>
</table>
<br>
<TABLE class="small" width="100%" align="center" style='border-collapse:collapse' border=1 cellspacing=0 cellpadding=3 bordercolor='#000000'>
  <TR class="TableContent">
  <TD><img border=0 src="../../images/node_user.gif" WIDTH="18" HEIGHT="18" align="absmiddle"> <b>个人信息</b></TD>
  </tr>
 <TR class="TableData" height=30>
  <TD>
    <a href="../../getPersonInfo.do" target="c_main"><img border=0 src="../../images/node_user.gif" align="absmiddle">个人资料</a><br>
  </TD>
 </tr>

 <TR class="TableContent">
  <TD><img border=0 src="../../images/login.gif" WIDTH="18" HEIGHT="18" align="absmiddle"> <b>帐号与安全</b></TD>
 </tr>
 <TR class="TableData" height=60>
  <TD>
    <a href="../../updatePsdGuide.do" target="c_main"><img border=0 src="../../images/login.gif" align="absmiddle"> 修改密码</a><br>
    <!-- <a href="log.jsp" target="c_main"><img border=0 src="../../images/menu/system.gif" align="absmiddle"> 安全日志</a><br> -->
  </TD>
 </tr>
</TABLE>
</body>
</html>