<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>数据清理</title>
<script src="js/module.js"></script>
<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme }/style.css">
<script src="js/ccorrect_btn.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script>
function submit(){
	var v=confirm('确定要清楚数据吗？ 清除后将不可恢复！'); 
	if(!v){
		return false;
	}
	if((!document.getElementById("sys_log").checked)&&(!document.getElementById("sealkey_log").checked)&&(!document.getElementById("server_log").checked)&&(!document.getElementById("client_log").checked)){
	    alert("请选择要清理的数据");
		return false;
	}else{
	   var syslog="0";
	   var seallog="0";
	   var server_log="0";
	   var client_log="0";
	   if(document.all.sys_log.checked==true){
	     syslog=document.all.sys_log.value;
	   }
	   if(document.all.sealkey_log.checked==true){
	     seallog=document.all.sealkey_log.value;
	   }
	   if(document.all.server_log.checked==true){
	     server_log=document.all.server_log.value;
	   }
	   if(document.all.client_log.checked==true){
	     client_log=document.all.client_log.value;
	   }
	   LogSys.cleanData(syslog,seallog,server_log,client_log,objcts);
	}
}
function objcts(){
  alert("清理成功！");
}
</script>
</head>
<body class="bodycolor" topmargin="5">

<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><img src="../../../images/sys_config.gif" WIDTH="22" HEIGHT="20" align="absmiddle"><span class="big3">系统数据清理</span>
    </td>
  </tr>
</table>
<br/>
<table class="TableBlock" width="600" align="center">
   <tr>
    <td nowrap class="TableContent">清理内容</td>
    <td nowrap class="TableData">
      <input type="checkbox" name="sys_log" id="sys_log" value="1"><label for="sys_log">系统操作日志</label>
      <!-- <input type="checkbox" name="sealkey_log" id="sealkey_log" value="2"><label for="sealkey_log">印章写入key日志</label> -->
      <input type="checkbox" name="server_log" id="server_log" value="3"><label for="server_log">服务端盖章日志</label>
      <!-- <input type="checkbox" name="client_log" id="client_log" value="4"><label for="client_log">客户端盖章日志</label> -->
      <input type="checkbox" name="server_log" id="server_log" value="3"><label for="server_log">评价日志</label>
    </td>
   </tr>
   <tr class="TableControl">
      <td nowrap colspan="2" align="center">
          <input type="button"  value="提交" class="BigButton" title="删除" onclick="return submit();">
      </td>
    </tr>
</table>
<br>
</body>
</html>