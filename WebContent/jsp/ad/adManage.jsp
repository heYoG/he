<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>广告管理</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	function sum(totalPage){//跳转页面
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
			location="fileManage.action?currentPage="+jumpPage;
		}	
	
	function view(adID){
		alert("暂不支持此操作!");
	}
	
	function delAd(adID){
		alert("暂不支持此操作!");
	}
</script>
<style type="text/css">
table {
	border: 1px solid #B1CDE3;
	padding: 0;
	margin: 0 auto;
	margin-left: 10px;
	margin-right: 10px;
	width: 98%;
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

#checkDiv { /*全选、反选*/
	width: 20%;
	border: solid 0px;
	margin-top: 10px;
	margin-left: 10px;
	text-align: left;
	float: left
}

#pageDiv { /*页码*/
	width: 50%;
	margin-top: 10px;
	border: solid 0px;
	text-align: right;
	float: right
}

.currentPage { /*当前页*/
	font-size: 20px;
	color: red;
	border: 0px;
	cursor: pointer;
}

a:link {
	text-decoration: none
}
</style>
</head>
<body>
	<center>
		<table>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>上传时间</th>
				<th>上传者</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${adList}" var="ad" varStatus="v">
				<tr>
					<td>${v.count}</td>
					<td>${ad.adName }</td>
					<td>${ad.uploadtime }</td>
					<td>${ad.operator }</td>
					<td nowrap="nowrap"><a href="#" onclick="view(${ad.id})">预览</a>&nbsp;&nbsp;<a
						href="javascript:void(0)" onclick="delAd(${ad.id})">删除</a></td>
				</tr>
			</c:forEach>
		</table>
		<!-- 页码列表 -->
		<div id="pageDiv">
			<!-- 上一页 -->
			<c:choose>
				<c:when test="${pageData.currentPage!=1}">
					<a
						href="fileManage.action?currentPage=${pageData.currentPage-1}&type=1&nextOrPre=previous&start=${pageData.start}&end=${pageData.end}"><input
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
						<a href="fileManage.action?currentPage=${p}&type=1">${p}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- 下一页 -->
			<c:choose>
				<c:when test="${pageData.currentPage!=pageData.totalPage }">
					<!-- 即不为最后一页 -->
					<a
						href="fileManage.action?currentPage=${pageData.currentPage+1}&type=1&nextOrPre=next&start=${pageData.start}&end=${pageData.end}"><input
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
	</center>
</body>
</html>