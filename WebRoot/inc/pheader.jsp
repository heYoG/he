<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>登录信息</title>

<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/style.css">
<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/pheader.css">
</head>
<script src="../js/utility.js"></script>
<script src="../js/mytable.js"></script>


<body>
<div id="on_status" class="small" title="姓名：${current_user.user_name}
部门：${current_user.dept_name}
角色：${current_user.role_name}
">
<span id="my_info">
<span>
<img src="../images/avatar/1.gif" width="18" height="18" align="absmiddle" /> ${current_user.user_name}</span>

</span>
<input type="text" id="my_status" name="my_status" value="我的留言" onblur="update_my_status();" onkeypress="input_my_status()" class="SmallInput" maxlength="100" style="display:none;" />
</div>

<div id="tabs">
  <ul id="nav_menu">
    <li><a id="menu_1" href="menu.jsp" target="menu" title="导航菜单" class="active"><span>导航</span></a></li>
    <!-- <li><a id="menu_2" href="user_all.jsp" target="menu" title="组织机构及人员"><span>组织</span></a></li>
    <li><a id="menu_4" href="smsbox.jsp" target="menu" title="短信箱"><span>短信</span></a> 
    </li>-->
  </ul>
</div>

</body>
</html>
