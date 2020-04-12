<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="util.CommenClass" %>
 <% String path=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基本信息</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
	<script type="text/javascript">
		function loginout(){
			if(confirm("确定退出吗？")){		
				$.ajax({
					url:"login!destroySession.action",
					type:"post",
					data:{"userNo":"${USERSESSION.userNo}"},
					//dataType:"json",//没有返回值不能设置，否则会走error
					success:function(successData){//设置登录状态成功跳转到登录页面
						location.href=open("<%=request.getContextPath()%>/index.jsp","_parent");
					},
					error:function(errorData,status,e){
						alert("退出异常，请稍后再试！errorData:"+JSON.stringify(errorData)+",status:"+status+",e:"+e);
					}	
				});
						
			}
		}
	</script>
</head>
<body>
<div style="float:right;color:red;font:10; border:0px solid">
	<a href="javascript:void(0)" onclick="loginout()">退出</a>
</div>
<marquee scrollamount="10" scrolldelay="50" direction="left" onmouseover="this.stop()" onmouseout="this.start()"><span style="text-align:center;"><h2>Welcome you  ${USERSESSION.userNo}</h2></span></marquee>
</body>
</html>