<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<title>管理权限设置</title>
<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
<script src="js/ccorrect_btn.js"></script>
<script src="js/module.js"></script>
<script language="JavaScript" src="/Seal/js/util.js"></script>
<script type="text/javascript">
function openRoleList(){
	var b='default';
	b = window.showModalDialog("roleModeList.do?user_status=1",form1);
	if(b!='default'){
		document.form1.role_names.value = b ;
	}
 }
function ClearUser(){
 document.getElementById("MuserNo").value="";
 document.getElementById("MuserName").value="";
}
function ClearUser1(){
 document.getElementById("MuserNo1").value="";
 document.getElementById("MuserName1").value="";
}
function ClearRole(){
document.getElementById("role_names").value="";
document.getElementById("role_nos").value="";
}
function openpersons() {
	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_user&&user_no=${current_user.user_id }",form1);
}
function openperson() {
	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_use&&user_no=${current_user.user_id }",form1);
}
</script>
</head>

<body class="bodycolor" topmargin="5">


<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="small"><img src="images/node_user.gif" WIDTH="22" HEIGHT="20" align="absmiddle"><span class="big3"> 印章权限设置</span>
    </td>
  </tr>
</table>
  <form action="addRoleUser.do" method="post" name="form1">
<table width="85%" class="TableList" align="center" >
    <c:if test="${seal_types=='1'}">
    <tr>
      <td nowrap class="TableContent"" align="center">授权范围：<br>（人员）</td>
      <td class="TableData">
        <input type="hidden" name="MuserNo" id="MuserNo" value="${user_id}">
        <textarea cols=40 name="MuserName" id="MuserName" rows=8 class="BigStatic" wrap="yes" readonly>${user_name }</textarea>
        <a href="javascript:;" class="orgAdd" onClick="return openpersons();">添加</a>
        <a href="javascript:;" class="orgClear" onClick="ClearUser()">清空</a>
      </td>
   </tr>
   
   <tr>
      <td nowrap class="TableContent"" align="center">授权范围：<br>（角色）</td>
      <td class="TableData">
        <input type="hidden" name="role_nos" id="role_nos"  value="${role_nos}" />
        <textarea cols=40 name="role_names" rows=8 class="BigStatic" wrap="yes" readonly>${role_names }</textarea>
        <a href="javascript:;" class="orgAdd" onClick="return openRoleList();">添加</a>
        <a href="javascript:;" class="orgClear" onClick="ClearRole('role_names')">清空</a>
      </td>
   </tr>
   </c:if>
   <c:if test="${seal_types=='2'}">
    <tr>
      <td nowrap class="TableContent"" align="center">授权范围：<br>（人员）</td>
      <td class="TableData">
        <input type="hidden" name="MuserNo1" id="MuserNo1" value="${user_id}">
        <textarea cols=40 name="MuserName1" id="MuserName1" rows=8 class="BigStatic" wrap="yes" readonly>${user_name }</textarea>
        <a href="javascript:;" class="orgAdd" onClick="return openperson();">添加</a>
        <a href="javascript:;" class="orgClear" onClick="ClearUser1()">清空</a>
      </td>
   </tr>
   
   </c:if>
   <tr>
    <td nowrap  class="TableControl" colspan="2" align="center">
    <input type="hidden" name="seal_id" value="${seal_id}">
        <input type="submit" value="确定" class="BigButton">&nbsp;&nbsp;
       <input type="button" value="返回" class="BigButton"
							onclick="history.back(-1)">
    </td>
  
</table>
</form>
</body>
</html>
