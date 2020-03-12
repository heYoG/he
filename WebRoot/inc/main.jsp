<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>${view_face.ie_title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<script src="../js/ccorrect_btn.js"></script>
<script src="../js/utility.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script src="/Seal/js/logOper.js"></script>
<script src="/Seal/js/util.js"></script>
<script type="text/javascript">
self.moveTo(0,0);
self.resizeTo(screen.availWidth,screen.availHeight);
self.focus();

relogin=0;
function exit()
{
   return;
   if(relogin) return;

   var req = new_req();
	req.open("GET", "relogin.php", true);
	req.send('');
}
</script>
</head>

<script language="JavaScript">
var allEmements=document.getElementsByTagName("*");
for(var i=0;i<allEmements.length;i++)
{
   if(allEmements[i].tagName && allEmements[i].tagName.toLowerCase()=="iframe")
   {
      document.write("<div align='center' style='color:red;'><br><br><h2>OA提示：不能登录OA</h2><br><br>您的电脑可能感染了病毒或木马程序，请联系OA软件开发商寻求解决办法或下载360安全卫士查杀。<br>病毒网址（请勿访问）：<b><u>"+allEmements[i].src+"</u></b></div>");
      allEmements[i].src="";
   }
}
var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			// top.location="/Seal/login.jsp";
	 }
	 dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
     LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","系统管理","系统登录成功");//logSys.js
</script>
<frameset rows="${view_face.img_height},*,20"  cols="*" frameborder="no" border="0" framespacing="0" id="frame1" onbeforeunload="exit();">
    <frame name="banner" id="banner" scrolling="no" noresize="noresize" src="/Seal/inc/topbar.jsp" frameborder="0">
    <frameset rows="*"  cols="200,*" frameborder="no" border="0" framespacing="0" id="frame2">
       <frame name="leftmenu" id="leftmenu" scrolling="no" noresize="noresize" src="/Seal/inc/left.jsp" frameborder="0">
       <frame name="right" id="table_index" scrolling="no" src="/Seal/inc/right.jsp" frameborder="0">
    </frameset>
    <frame name="status_bar" id="status_bar" scrolling="no" noresize="noresize" src="/Seal/inc/status_bar.jsp" frameborder="0">
</frameset>


</html>
