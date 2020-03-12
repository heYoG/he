<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>注册成功</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="theme/1/style.css">
<script src="js/handler.js"></script>
	<script type="text/javascript">
		window.onload=function() {
			goBack();
		};
	</script>
</head>
<body class="bodycolor">
    <table class="MessageBox" align="center" width="430">
  <tr>
    <td class="msg error">
      <div id="success" class="content">
		<div id="success_top"></div>
		<div id="success_bottom">
			<span id="info">注册用户成功！</span><br><span id="time">3</span>秒种后自动跳转到登陆页面...
		</div>
       
    </div>
    </td>
  </tr>
</table>
<br>

</body>
</html>