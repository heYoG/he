<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>应用系统IP</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="js/utility.js"></script>
		<script>
function test(){
        var userip=document.form1.user_ip2.value 
        if(userip=="")
        {alert("IP地址不能为空!");
        return false;
        }else{
         var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
          flag_ip=pattern.test(userip);
         if(!flag_ip)
        { alert("请输入正确的IP地址!");  
        return false;
        }else{
          var a=window.dialogArguments;
		  a.user_ip.value=$("user_ip2").value;
		  this.close();
        }
     }   
 }
 //提交绑定回车事件      
function onkey()
{
    if(window.event.keyCode==13){
       var userip=document.form1.user_ip2.value 
        if(userip=="")
        {alert("IP地址不能为空!");
        return false;
        }else{
         var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
          flag_ip=pattern.test(userip);
         if(!flag_ip)
        { alert("请输入正确的IP地址!");  
        return false;
        }else{
          var a=window.dialogArguments;
		  a.user_ip.value=$("user_ip2").value;
		  this.close();
        }
     }   
    }       
}
		</script>
	</head>
	<body onkeydown=" return onkey('doLogin')" class="bodycolor" topmargin="5" >
	<form name="form1">
	       <table border="0" cellspacing="0" cellpadding="3"
			class="small" align="center">
			<tr>
				<td class="Big">
					<img src="../../images/query.gif" align="absmiddle">
					<span class="big3"> 应用系统IP</span>
				</td>
			</tr>
		  </table>
			<table border="0" cellspacing="0" cellpadding="3"
			class="small" align="center">
			<tr >
			<td >应用系统IP：</td>
			<td><input name="user_ip2" type="text" id="user_ip2">
			</td>
			</tr>		
			<tr>
			<td align="center" colspan="3">
			<input type="button" id="doLogin" value="确定" onclick="return test();" class="BigButton">
			<input type="reset" value="重填" class="BigButton">
			</tr>
		  </table>
		  </form>
	</body>
</html>
