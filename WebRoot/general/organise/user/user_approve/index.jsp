<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<title>用户审批</title>
    <frameset rows="*"  cols="20%,*" frameborder="no" border="0" framespacing="0" id="frame2">
       <frame name="dept_tree" scrolling="auto" src="/Seal/depttree/dept_tree.jsp?req=search_user_app&&user_no=${current_user.user_id }" frameborder="no">
       <frame name="dept_main" scrolling="auto" src="general/organise/user/user_approve/query.jsp" frameborder="no">
    </frameset>
</html>