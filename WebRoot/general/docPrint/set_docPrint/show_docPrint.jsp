<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<%String doc_no=request.getParameter("doc_no"); %>

<html>
<head>
<title>设置详情</title>
<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
<script src="/Seal/js/ccorrect_btn.js"></script>
<script src="/Seal/js/module.js"></script>
<script language="JavaScript" src="/Seal/js/util.js"></script>
<script type="text/javascript">
function openRoleList(){
	var b='default';
	b = window.showModalDialog("/Seal/roleModeList.do?user_status=1",form1);
	if(b!='default'){
		document.form1.role_names.value = b ;
	}
 }
function ClearUser(){
 document.getElementById("MuserNo").value="";
 document.getElementById("MuserName").value="";
}
function ClearRole(){
document.getElementById("role_nos").value="";
document.getElementById("role_names").value="";
}
function openperson() {
	var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_user&&user_no=${current_user.user_id }",form1);
}
</script>
</head>

<body class="bodycolor" topmargin="5">


<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="small"><img src="/Seal/images/node_user.gif" WIDTH="22" HEIGHT="20" align="absmiddle"><span class="big3"> 打印权限设置</span>
    </td>
  </tr>
</table>
  <form action="../../../addDocPrint.do" method="post" name="form1">
<table width="85%" class="TableList" align="center" >
	<tr>
      <td nowrap class="TableContent"" align="center">打印份数：</td>
      <td class="TableData">
        <input type="text" name="printNum" id="printNum" value="1">
      </td>
   </tr>
    <tr>
      <td nowrap class="TableContent"" align="center">授权范围：<br>（人员）</td>
      <td class="TableData">
        <input type="hidden" name="MuserNo" id="MuserNo" value="">
        <textarea cols=40 name="MuserName" id="MuserName" rows=8 class="BigStatic" wrap="yes" readonly></textarea>
        <a href="javascript:;" class="orgAdd" onClick="return openperson();">添加</a>
        <a href="javascript:;" class="orgClear" onClick="ClearUser()">清空</a>
      </td>
   </tr>
   <tr>
      <td nowrap class="TableContent"" align="center">授权范围：<br>（角色）</td>
      <td class="TableData">
        <input type="hidden" name="role_nos" id="role_nos"  value="" />
        <textarea cols=40 name="role_names" id="role_names" rows=8 class="BigStatic" wrap="yes" readonly></textarea>
        <a href="javascript:;" class="orgAdd" onClick="return openRoleList();">添加</a>
        <a href="javascript:;" class="orgClear" onClick="ClearRole()">清空</a>
      </td>
   </tr>
   <tr>
    <td nowrap  class="TableControl" colspan="2" align="center">
    <input type="hidden" name="doc_id" value="<%=doc_no %>">
        <input type="submit" value="确定" class="BigButton">&nbsp;&nbsp;
       <input type="button" value="返回" class="BigButton"
							onclick="history.back(-1)">
    </td>
  
</table>
</form>
</body>
</html>
