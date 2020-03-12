<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
	<head>
		<title>状态栏</title>
		<link rel="stylesheet" type="text/css"
			href="../theme/${current_user.user_theme}/status_bar.css">
		<link rel="stylesheet" type="text/css"
			href="../theme/${current_user.user_theme}/style.css">
		<script src="../js/ccorrect_btn.js"></script>
		<script src="../js/utility.js"></script>
		<script src="../js/marquee.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
	</head>

	<body class="statusbar" topmargin="0" leftmargin="0" marginwidth="0"
		marginheight="0" >
		<table border="0" width="100%" cellspacing="1" cellpadding="0"
			class="small">
			<tr>
				<td align="center" width="120" style="display:none">
					共
					<input type="text" id="user_count1" size="3" readonly>
					人在线
				</td>
				<td align="center" width="80">
					&nbsp;
					<span id="new_sms"></span>
				</td>
				<td id="status_text_container" align="center"
					style="font-weight: bold;">
					&nbsp;
				</td>
				<td align="center" width="80">
					&nbsp;
					<span id="new_letter"></span>
				</td>
			</tr>
		</table>
	</body>
</html>