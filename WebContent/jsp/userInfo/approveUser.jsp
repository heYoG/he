<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户审批</title>
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
</style>
<script type="text/javascript">
	if("${updateInfo}"=="update_fail")
		alert("修改密码失败!");

function sum(totalPage){//页码跳转
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
	location="login!userInfo.action?currentPage="+jumpPage;
}

function agree(userID){//通过审批
	if(confirm("确定通过审批吗?"))
		location="login!activeUser.action?userID="+userID+"&type=1";
}

function refuse(userID){//审批不通过
	if(confirm("拒绝通过审批?"))
		location="login!refuseUser.action?userID="+userID+"&type=3";
}
</script>
</head>
<body>
	<center>
		<c:choose>
			<c:when test="${userList!='[]'}">
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
							<td><c:if test="${list.status==2 }">待审批</c:if> <c:if
									test="${list.status==3 }">可修改</c:if></td>
							<td nowrap><c:if
									test="${USERSESSION.av.authVal==1 and list.status!=3}">
									<a href="javascript:void(0)" target="showPageName"
										onclick="agree(${list.id})">同意</a>
									<a href="javascript:void(0)" onclick="refuse(${list.id})">拒绝</a>
								</c:if> <c:if test="${USERSESSION.av.authVal!=1 and list.status!=3}">
									<input type="button" disabled="true" class="disableChange"
										value="同意" title="普通用户不可操作">
									<input type="button" disabled="true" class="disableChange"
										value="拒绝" title="普通用户不可操作">
								</c:if> <c:if test="${list.status==3 }">
									<a
										href="login!updateUser.action?userNo=${list.userNo}&isAppro=3">修改</a>
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
						<c:when test="${pageData.currentPage!=pageData.totalPage }">
							<!-- 即不为最后一页 -->
							<a
								href="login!userInfo.action?currentPage=${pageData.currentPage+1}&nextOrPre=next&start=${pageData.start}&end=${pageData.end}"><input
								type="button" name="nextPage" value="下一页"></a>
						</c:when>
						<c:otherwise>
							<input type="button" disabled="true" value="下一页">
						</c:otherwise>
					</c:choose>
					_ 共${pageData.totalCount}条数据|共${pageData.totalPage }页|当前第${pageData.currentPage}页&nbsp;&nbsp;跳到第<input
						type="text" size="4" id="jumpPage">页&nbsp;<input
						type="button" id="jumpBt" onclick="sum(${pageData.totalPage})"
						value="确定">&nbsp;&nbsp;&nbsp;
				</div>
			</c:when>
			<c:otherwise>
				<span style="text-align: center; font-size: 15pt; color: red"><h3>没有待审批用户</h3></span>
			</c:otherwise>
		</c:choose>


	</center>
</body>
</html>