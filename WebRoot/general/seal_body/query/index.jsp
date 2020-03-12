<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<title>印章查询</title>
    <frameset rows="*"  cols="200,*" frameborder="no" border="0" framespacing="0" id="frame2">
       <frame name="dept_list" scrolling="auto" noresize src="/Seal/depttree/dept_tree.jsp?req=dept_list&&user_no=${current_user.user_id }" frameborder="no">
       <frame name="dept_main" scrolling="auto" src="general/seal_body/query/query.jsp" frameborder="no">
    </frameset>
</html>