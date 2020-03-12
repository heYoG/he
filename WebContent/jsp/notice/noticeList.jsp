<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path=request.getContextPath(); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告管理</title>
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
	width: 150px;
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

	function cancel(id){//注销，type=0表示注销
		if(confirm("确定注销吗?")){
			location.href="<%=path%>/notice/updateNoticeStatus.do?noticeID="+id+"&type=0";			
		}
	}
	
	function active(id){//激活,type=1表示激活
		if(confirm("确定激活吗?")){
			location="<%=path%>/notice/updateNoticeStatus.do?noticeID="+id+"&type=1";
		}
	}
	
	function deleteCompletely(id){//彻底删除
		if(confirm("删除将无法恢复,确定继续执行吗?")){
			location="<%=path%>/notice/deleteCompletely.do?noticeID="+id;
		}	
	}
	
	
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
		location="<%=path%>/notice/noticeList.do?currentPage="+jumpPage;
	}

</script>
</head>
<body>
<center>
	<form action="" method="post" name="noticeForm">
		<table>
			<tr>
				<th>序号</th>
				<th>主题</th>
				<th>发送者</th>
				<th>发送内容</th>
				<th>发送时间</th>
				<th>发送状态</th>
				<th>公告状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${noticeList}" var="list" varStatus="vs">
				<tr>
					<td>${vs.count }</td>
					<td>${list.theme }</td>
					<td>${list.sender }</td>
					<td>${list.text }</td>
					<td>${list.createTime }</td>
					<td><c:if test="${list.sendStatus==1 }">发送成功</c:if><c:if test="${list.sendStatus==0 }">发送失败</c:if></td>
					<td><c:if test="${list.noticeStatus==1 }">正常</c:if><c:if test="${list.noticeStatus==0 }">已注销</c:if></td>
					<td>
					<c:if test="${list.noticeStatus==1 }"><a href="javascript:void(0)" onclick="cancel(${list.id})">注销</a></c:if>
					<c:if test="${list.noticeStatus==0 }"><a href="javascript:void(0)" onclick="active(${list.id})">激活</a></c:if>
					<a href="javascript:void(0)" onclick="deleteCompletely(${list.id})">彻底删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<!-- 页码列表 -->
		<div id="pageDiv">
			<!-- 上一页 -->
			<c:choose>
				<c:when test="${pageData.currentPage!=1}">
					<a
						href="<%=path%>/notice/noticeList.do?currentPage=${pageData.currentPage-1}&nextOrPre=previous&start=${pageData.start}&end=${pageData.end}"><input
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
						<a href="<%=path%>/notice/noticeList.do?currentPage=${p}">${p}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- 下一页 -->
			<c:choose>
				<c:when test="${pageData.currentPage < pageData.totalPage }">
					<!-- 当前页小于总页数,即不为最后一页 ,条件判断也可用lt-->
					<a
						href="<%=path%>/notice/noticeList.do?currentPage=${pageData.currentPage+1}&nextOrPre=next&start=${pageData.start}&end=${pageData.end}"><input
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
				type="button" id="jumpBt" onclick="sum(${pageData.totalPage})"
				value="确定">&nbsp;&nbsp;&nbsp;
		</div>
	</form>
</center>
</body>
</html>