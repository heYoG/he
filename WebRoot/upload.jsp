<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
String path = request.getContextPath();
System.out.println("path:"+path);
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath:"+basePath);
System.out.println(request.getLocalAddr());
%>
<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
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
	</head>
	<body>
			<form action="uploadPdf.do" method="post"
				enctype="multipart/form-data">
				<center>
				<table>
					<tr>
					  <td width="30%" class="TableContent">
							PDF名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" maxlength="30" name="pdf_name" class="BigInput" value="123"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							上传PDF:
						</td>
						<td nowrap class="TableData">
							<input type="file" name="pdf_path" class="BigInput">
						</td>
					</tr>	
					<tr>
						 <td width="15%" class="TableContent">
							规则号：
						</td>
						<td nowrap class="TableData">
							<input type="text" maxlength="30" name="rule_no" class="BigInput" value="1" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="上传PDF盖章">
						</td>
					</tr>
				</table>
				</center>
			</form>
	</body>
</html>
