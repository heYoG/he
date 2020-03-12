<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="../../../theme/${current_user.user_theme}/style.css">
<script src="../../../js/ccorrect_btn.js"></script>
</head>

<body class="bodycolor" topmargin="5">

<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><img src="../../../images/menu/system.gif" WIDTH="22" HEIGHT="20" align="absmiddle"><span class="big3"> 部门管理</span>
    </td>
    <td align="right">
      <input type="button" value="批量导入部门" class="BigButton" onClick="parent.dept_main.location='../../../general/organise/dept/importDeptExcel.jsp';" title="批量导入部门">&nbsp;&nbsp;
      <input type="button" value="批量导入用户" class="BigButton" onClick="parent.dept_main.location='../../../general/organise/dept/importUserExcel.jsp';" title="批量导入用户">&nbsp;&nbsp;
      <input type="button" value="新建部门" class="BigButton" onClick="parent.dept_main.location='../../../showDept.do';" title="新建部门">&nbsp;&nbsp;
    </td>
    </tr>
</table>

</body>
</html>
