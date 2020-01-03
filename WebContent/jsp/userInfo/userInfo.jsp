<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<style type="text/css">
table {
	border: 1px solid #B1CDE3;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #B1CDE3;
	width: 100px;
	height: 20px;
	text-align: center;
}

#pageDiv { /*分页属性*/
	margin-top: 10px;
	border: solid 0px;
}

.currentPage { /*当前页属性*/
	font-size: 20px;
	color: red;
	border: 0px;
	cursor: pointer;
}

.disableChange {
	border: none;
}

#normalOrCancel {
	margin-bottom: 10px
}
</style>
<script type="text/javascript">
	if("${updateInfo}"=="update_fail")
		alert("修改密码失败!");

	<!--obj1:用户id,obj2:权限值-->
	function delUser(obj1,obj2){//注销用户
		if(confirm("确定注销该用户吗?")){
			if(obj2==1){
				alert("当前用户为管理员,无法注销！");
				return false;			
			}
			location.href="login!delUser.action?userID="+obj1+"&type=1";			
		}
	}
	
	function active(userID){//激活用户
		if(confirm("确定激活该用户吗?")){
			location="login!activeUser.action?userID="+userID+"&type=1";
		}
	}
	
	function del_Complete(userID){//彻底删除
		if(confirm("删除将无法恢复,确定继续执行吗?")){
			location="login!delComplete?id="+userID+"&type=1";
		}	
	}
	
	
	function sum(totalPage,type){//页码跳转
		var jumpPage=$("#jumpPage").val().trim();
		if(jumpPage==''){
			alert("请输入要跳转页码!");
			return false;
		}else if(isNaN(jumpPage)){
			alert("跳转页码必须是数字!");
			document.getElementById("jumpPage").value='';
			return false;			
		}else if(jumpPage>totalPage){
			alert("输入页码超出范围,请重新输入!");
			document.getElementById("jumpPage").value='';
			return false;
		}
		location="login!userInfo.action?currentPage="+jumpPage+"&type="+type;
	}
	
	function setSelect(){//设置下拉列表
		var type=${pageData.type};
		var status=document.getElementById("normalOrCancel");
		status.innerHTML="";
		if(type==0)
			status.innerHTML="<option name='op1' value='normal'>正常用户</option><option name='op2' value='cancel' selected='selected'>注销用户</option>";
		else
			status.innerHTML="<option name='op1' value='normal' selected='selected'>正常用户</option><option name='op2' value='cancel'>注销用户</option>"	;
	}
	
	function show(){//显示可选项
		var selectedVal=$("#normalOrCancel").val();
		if(selectedVal=="normal")	
			location.href="login!userInfo.action?type=1";			
		else
			location.href="login!userInfo.action?type=0";	
	}
</script>
</head>
<body onload="setSelect()">
	<center>
		<div id="selectDoc">
			<select id="normalOrCancel" onchange='show()'></select>
		</div>
		<table border="1px" border-color="#72D4F4">
			<tr>
				<th>序号</th>
				<th>用户名</th>
				<th>真实姓名</th>
				<th>年龄</th>
				<th>角色</th>
				<th>所属部门</th>
				<th>用户状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${userList}" var="list" varStatus="st">
				<tr>
					<td>${st.count}</td>
					<td>${list.userNo }</td>
					<td>${list.userName }</td>
					<td>${list.age }</td>
					<td>${list.av.authName}</td>
					<td>${list.dept.deptName}</td>
					<td><c:if test="${list.status==1}">正常</c:if> <c:if
							test="${list.status==0 }">已注销</c:if></td>
					<td nowrap><c:if test="${USERSESSION.av.authVal==1}">
							<a href="login!updateUser.action?userNo=${list.userNo}"
								target="showPageName">修改</a> &nbsp;<a href="javascript:void(0)"
								target="showPageName"
								onclick="delUser(${list.id},${list.av.authVal})"> <c:if
									test="${list.status==1 }">注销</c:if></a>
							<a href="javascript:void(0)" onclick="active(${list.id})"><c:if
									test="${list.status==0 }">激活</c:if></a>
							<!-- 当前记录已删除且当前登录用户是管理员 -->
							<c:if test="${list.status==0}">
								<a href="javascript:void(0)" onclick="del_Complete(${list.id})">彻底删除</a>
							</c:if>
						</c:if> <c:if
							test="${USERSESSION.av.authVal!=1 and list.userNo==USERSESSION.userNo}">
							<a href="login!updateUser.action?userNo=${list.userNo}"
								target="showPageName">修改</a>
						</c:if> <c:if
							test="${USERSESSION.av.authVal!=1 and list.userNo!=USERSESSION.userNo}">
							<input type="button" disabled="true" class="disableChange"
								title="普通用户无权操作" value="不可操作">
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
		<!-- 页码列表 -->
		<div id="pageDiv">
			<!-- 上一页 -->
			<c:choose>
				<c:when test="${pageData.currentPage!=1}">
					<a
						href="login!userInfo.action?currentPage=${pageData.currentPage-1}&nextOrPre=previous&start=${pageData.start}&end=${pageData.end}"><input
						type="button" name="previousPage" value="上一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" disabled="true" name="previousPage"
						value="上一页">
				</c:otherwise>
			</c:choose>
			<!-- 循环列出所有页数 -->
			<c:forEach items="${pageData.itemList}" var="p" varStatus="vs">
				<!-- items的集合必须有多条记录才能列表显示,如其中含数组，而集合只有一条数据，则只显示一条 -->
				<c:choose>
					<c:when test="${p==pageData.currentPage}">
						<!-- 是当前页突出显示-->
						<span class="currentPage">${p }</span>
						<!-- 禁止点击当前页 -->
					</c:when>
					<c:otherwise>
						<a href="login!userInfo.action?currentPage=${p}">${p}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- 下一页 -->
			<c:choose>
				<c:when test="${pageData.currentPage < pageData.totalPage }">
					<!-- 当前页小于总页数,即不为最后一页 ,条件判断也可用lt-->
					<a
						href="login!userInfo.action?currentPage=${pageData.currentPage+1}&nextOrPre=next&start=${pageData.start}&end=${pageData.end}"><input
						type="button" name="nextPage" value="下一页"></a>
				</c:when>
				<c:otherwise>
					<input type="button" disabled="true" value="下一页">
				</c:otherwise>
			</c:choose>
			_ 共${pageData.totalCount}条数据|共${pageData.totalPage }页|当前第
			<c:choose>
				<c:when test="${pageData.totalPage==0}">
					<!-- 没有数据 -->
					0
				</c:when>
				<c:otherwise>
					${pageData.currentPage}
				</c:otherwise>
			</c:choose>
			页&nbsp;&nbsp;跳到第<input type="text" size="4" id="jumpPage">页&nbsp;<input
				type="button" id="jumpBt" onclick="sum(${pageData.totalPage},${pageData.type})"
				value="确定">&nbsp;&nbsp;&nbsp;
		</div>
	</center>
</body>
</html>