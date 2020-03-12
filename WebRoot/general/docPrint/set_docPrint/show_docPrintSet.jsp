<%@page contentType="text/html;charset=utf-8"%>
<%String doc_no=request.getParameter("doc_no"); %>
<%@ include file="../../../inc/tag.jsp"%>

<html>
<head>
<title>设置详情</title>
<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">

<script type="text/javascript">

</script>
</head>
<frameset rows="*" cols="*,*" frameborder="no" border="0"
		framespacing="0">
		<frame name="left_main" noresize="noresize"
			src="/Seal/general/docPrint/set_docPrint/show_docPrintUserSet.jsp?doc_no=<%=doc_no %>"
			frameborder="0">
		<frame name="right_main" noresize="noresize"
			src="/Seal/general/docPrint/set_docPrint/show_docPrintRoleSet.jsp?doc_no=<%=doc_no %>"
			frameborder="0">
	</frameset>
	<div align="center">
	<input type="button" value="返回" class="BigButton"
						onclick="history.go(-1);"
						title="返回" name="button">
					&nbsp;&nbsp;
					</div>
</html>
