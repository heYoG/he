<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.mobile.js"></script>
<style type="text/css">
table {
	border: 1px solid #B1CDE3;
	padding: 0;
	margin: 0 auto;
	margin-left: 10px;
	margin-right: 10px;
	width: 78%;
	border-collapse: collapse;
}

td, th {
	text-align: center;
	border: 1px solid #B1CDE3;
	background: #fff;
	font-size: 14px;
	padding: 3px 3px 3px 8px;
	color: #4f6b72;
	height: 22px;
}

#pageDiv {
	margin-top: 10px;
	border: solid 0px;
}

.currentPage {
	font-size: 20px;
	color: red;
}

#delID{
	background-color:#FFFFFF;<%--去除背景颜色--%>
	border: 0px;
	text-decoration:underline;<%--添加下划线--%>
	color:red;<%--字体颜色--%>
	cursor:pointer;<%--添加手状属性--%>
}

a:link{
	text-decoration:none
}
</style>
<script type="text/javascript">
	function delDept(obj){
		if(confirm("确定删除此部门?"))
			location.href="DeptAction_delDept?id="+obj;
 	}
	
	function sum(totalPage){
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
		location="DeptAction.action?currentPage="+jumpPage;
	}
</script>
</head>
<body>
	<center>
		<form action="" method="post" name="deptForm">
			<c:if test="${deptList=='[]'}">
				<span sytle="text-align:center;font:red 15pt">此处空空如也</span>
				<br>
				<a href="<%=request.getContextPath()%>/jsp/dept/addDept.jsp"
					target="showPageName">前往添加部门...</a>
			</c:if>
			<c:if test="${deptList!='[]'}">
				<table>
					<tr>
						<th>序号</th>
						<th>部门名称</th>
						<th>操作</th>
					</tr>
					<c:forEach var="dept" items="${deptList}" varStatus="st">
						<tr>
							<td>${st.count}</td>
							<td>${dept.deptName }</td>
							<td><c:choose>
									<c:when test="${userVo.av.authVal!=1}"><!-- 非管理员不可删除 -->
										<input type="button" disabled='true' value="删除" title="普通用户无权操作">
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" onclick="delDept(${dept.deptID})"><input
											type="button" id="delID" value="删除"></a>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</table>
				<div id="pageDiv">
					<!-- 上一页 -->
					<c:choose>
						<c:when test="${pageData.currentPage!=1}">
							<a href="DeptAction.action?currentPage=${pageData.currentPage-1}"><input
								type="button" name="previousPage" value="上一页"></a>
						</c:when>
						<c:otherwise>
							<input type="button" disabled="true" value="上一页">
						</c:otherwise>
					</c:choose>
					<!-- 页码 -->
					<c:forEach items="${pageData.itemList }" var="p">
						<c:choose>
							<c:when test="${pageData.currentPage==p}">
								<!-- 突出显示当前页 -->
								<a href="DeptAction.action?currentPage=${p}" class="currentPage">${p }</a>
							</c:when>
							<c:otherwise>
								<a href="DeptAction.action?currentPage=${p}">${p }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<!-- 下一页 -->
					<c:choose>
						<c:when test="${pageData.currentPage!=pageData.totalPage }">
							<a href="DeptAction.action?currentPage=${pageData.currentPage+1}"><input
								type="button" name='nextPage' value='下一页'></a>
						</c:when>
						<c:otherwise>
							<input type="button" disabled='true' value='下一页'>
						</c:otherwise>
					</c:choose>
					_ 共${pageData.totalCount}条数据|共${pageData.totalPage }页|当前第${pageData.currentPage}页&nbsp;&nbsp;跳到第<input
						type="text" size="4" id="jumpPage">页&nbsp;<input
						type="button" id="jumpBt" onclick="sum(${pageData.totalPage})" value="确定">&nbsp;&nbsp;&nbsp;
				</div>
			</c:if>
		</form>
	</center>
</body>
</html>