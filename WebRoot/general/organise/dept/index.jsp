<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>部门/成员单位管理</title>
</head>
<frameset rows="40,*"  cols="*" frameborder="NO" border="0" framespacing="0" id="frame1">
    <frame name="dept_title" scrolling="no" noresize src="/Seal/general/organise/dept/title.jsp" frameborder="NO">
    <frameset rows="*"  cols="200,*" frameborder="no" border="0" framespacing="0" id="frame2">
     <!-- <frame name="dept_list" scrolling="auto" noresize src="/Seal/depttree/new_dept_tree.jsp?req=dept_mng&&user_no=${current_user.user_id }" frameborder="no"> -->
       <frame name="dept_list" scrolling="auto" noresize src="/Seal/depttree/dept_tree.jsp?req=dept_mng&&user_no=${current_user.user_id }" frameborder="no">
       <frame name="dept_main" scrolling="auto" src="/Seal/showDept.do" frameborder="no">
    </frameset>
</frameset>
</html>
