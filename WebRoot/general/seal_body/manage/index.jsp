<%@page contentType="text/html;charset=utf-8"%>

<html>
 <title>可用印章</title>
    <frameset rows="*"  cols="200,*" frameborder="no" border="0" framespacing="0" id="frame2">
       <frame name="dept_list" scrolling="auto" noresize src="/Seal/depttree/dept_tree.jsp?req=dept_list&&user_no=${current_user.user_id }" frameborder="no">
       <frame name="dept_main" scrolling="auto" src="tempAppuse.do?type=all&&userid=${current_user.user_id}" frameborder="no">
    </frameset>
</html>