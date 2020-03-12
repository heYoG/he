<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath:"+basePath);

%>
<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/2/style.css">
		<script type='text/javascript'
			src='/Seal/dwr/interface/GaiZhangApp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/EformTpl.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/cert/cert_reg/js/new_cert.js"></script>
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function doSubmit(){
      var docName=document.getElementById("pdf_name").value;
      var basepath="<%=basePath%>";
      window.location.href=basepath+"doc/download.jsp?name="+docName+".pdf";
}
</script>
	</head>
	<body>
	<center>
				<table>
					<tr>
					  <td width="30%" class="TableContent">
							PDF名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" maxlength="30" id="pdf_name" name="pdf_name" class="BigInput" value="123"/>
						</td>
						<td>
							<input type="button" value="下载文档" onClick="doSubmit()">
						</td>
					</tr>
				</table>
      </center>
	</body>
</html>
