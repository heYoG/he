<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>用户添加成功</title>

<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
<script src="js/handler.js"></script>
	<script type="text/javascript">
		window.onload=function() {
			goBack();
		};
/*操作成功跳转*/
var i=3;
function goBack(){
	if(i>0){
		i--;
		document.getElementById("time").innerText=i;
		document.getElementById("info").style.color="#336699";
	}else{
		document.getElementById("info").style.color="#ff0000";
		window.location="showServerUserlist.do?query=25";
	}
	window.setTimeout("goBack()", 1000);
}
	</script>
</head>
<body class="bodycolor">
    <table class="MessageBox" align="center" width="430">
  <tr>
    <td class="msg ok">
      <div id="success" class="content">
		<div id="success_top"></div>
		<div id="success_bottom">
			<span id="info">新建成功!</span><font color=red><span id="time">3</span></font>秒种后自动返回到应用系统新建页面...
			
		</div>
		<br>
       <div id="success_top">	
       <input type="button" value="继续添加" class="BigButton" title="返回添加页面"
							onclick="location='addServerUser1.do?type=13'"></div>
    </div>
    </td>
  </tr>
</table>
<br>

</body>
</html>