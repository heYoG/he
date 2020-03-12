<%@page contentType="text/html;charset=utf-8"%>

<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
<script src="/Seal/js/ccorrect_btn.js"></script>
<script src="/Seal/js/util.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script>
var user_no="${current_user.user_id}";
var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		var seal_name="";	
		var seal_id="";
function SetSealData_onclick(){
	seal_id=getUrlParam("seal_id");
	seal_name=getUrlParam("seal_name");
	SealBody.getSealBodyID(seal_id,seal);
	// var seal_data='${seal_data}';
}
function seal(d){
var obj;
	obj = document.getElementById("DMakeSealV61");
	if(!obj){
		return false;
	}
  
  if(0 == obj.LoadData(d.seal_data)){ 
	  var flag=obj.SaveSeal(2,"",0);
		if(0 != flag){
			alert("写入印章失败,请检查是否插入USB KEY！"+flag);
			return false;
		}else{
		   var keysn=obj.SerialNumber;
		   LogSys.logAddSealWrite(obj.SubjectName,user_ip,"1",user_name+"操作"+seal_name+"写入了"+obj.SubjectName+"的key",keysn,seal_id);//logOper.js
		   alert("写入印章成功");
		   return true;
       }
	}else{
		alert("写入印章失败,请检查是否插入USB KEY");
	}
}
</script>
</head>
<body class="bodycolor" onload="SetSealData_onclick()">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><img src="../../../images/menu/system.gif" align="absmiddle"><span class="big3"> 写入印章到USBKEY</span>&nbsp;
    </td>
  </tr>
</table>
<div align="center" id="info"><img src="../../../images/loading.gif" alt="正在写入,请不要拔出USBKEY"></div><br>
<div align="center">
	<input type="button" type="BigButton" value="继续写入" onclick="SetSealData_onclick()">&nbsp;
	<input type="button" type="BigButton" value="关闭" onclick="window.close();">
</div>
<div style="display:none">
<%@include file="../../../inc/makeSealObject.jsp"%>
</div>
</body>
</html>
