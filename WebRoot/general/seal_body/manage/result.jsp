<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
<head>
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
		window.location="importSeal.do?type=0";
	}
	window.setTimeout("goBack()", 1000);
}
	</script>
</head>
<html>
<%
	String result=(String)request.getAttribute("result");
	%>
<body class="bodycolor">
    <table class="MessageBox" align="center" width="430">
  <tr>
    <td class="msg ok">
      <div id="success" class="content">
		<div id="success_top"></div>
		<div id="success_bottom">
			<span id="info"><%=result%></span><font color=red><span id="time">3</span></font>秒种后自动返回到导入印章数据页面...
			
		</div>
		<br>
       <div id="success_top">	
       <input type="button" value="导入印章数据页面" class="BigButton" title="返回导入印章数据页面"
							onclick="location='importSeal.do?type=0'"></div>
    </div>
    </td>
  </tr>
</table>
<br>

</body>
</html>
