<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
	</head>
	<frameset id="frame1" rows="*" cols="0,*" frameborder="no" border="0"
		framespacing="0" >
		<frame src="/Seal/depttree/dept_tree.jsp?req=cert_query&user_no=admin"
			frameborder="0">
		<frame name="right_main" noresize="noresize"
			src="/Seal/general/cert/cert_query/cert_list.jsp" frameborder="0">
	</frameset>
</html>