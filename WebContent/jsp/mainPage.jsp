<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试系统</title>
<script type="text/javascript">
	function getRet(){
		window.history.go(-1);
	}
</script>
</head>
<frameset rows="120,80%,50" cols="*" frameborder="yes">
	<frame id="toolsID" name="toolsName" src="jsp/menu/topBar.jsp" frameborder="1" noresize="noresize">
	<frameset rows="*" cols="11%,*">
		<frame id="menuID" name="menuName" src="jsp/menu/leftBar.jsp" frameborder="1" noresize="noresize">
		<frame id="showPageID" name="showPageName" src="jsp/menu/rightBar.jsp" frameborder="1" noresize="noresize">
	</frameset>
	<frame id="bakID" name="bakName" src="jsp/menu/taskBar.jsp" frameborder="1" noresize="noresize" scrolling="yes">
</frameset>
</html>