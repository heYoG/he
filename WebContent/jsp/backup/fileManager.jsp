<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
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
<title>文件管理</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/fileManage.css">
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

#pageDiv {
	margin-top: 10px;
	border: solid 0px;
	text-align: right
}

.currentPage {
	font-size: 20px;
	color: red;
	border:0px;
	cursor:pointer;
}

a:link{
	text-decoration:none
}
</style>
<script type="text/javascript">
	function del(obj){
		alert("value:"+$("#dl").val());
		$.ajax({
			type:"post",
			url:"fileManage!deleteFile.action",
			data:{"id":$("#dl").val()},
			dataType:"json",
			success:function(data){
				location.href="fileInfo.action";
			}
	});
}
	function viewOnline(){
		alert("暂不支持的操作,敬请期待...");
	}
	
	function del(fileId){
		if(confirm("确定删除此文件吗?"))
			location.href="fileManage!delete_update.action?id="+fileId+"&type=1";
	}
	
	function viewOperator(){
		alert("暂不支持的操作,敬请期待...");
	}
	
	function rec(fileId){
		if(confirm("确认恢复该文件？"))
			location.href="fileManage!recoveryFile.action?id="+fileId;
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
		location="fileManage.action?currentPage="+jumpPage+"&type=1";
	}
	
</script>
</head>
<body>
	<center>
		<c:if test="${fileList=='[]'}">
			<!-- fileList是集合(可变数组) -->
			<!-- 查询为空 -->
			<span style="text-align: center; font: red 15pt">此处空空如也</span>
			<br>
			<a href="../backup/fileBackup.jsp" targer="showPageName">前往文件备份...</a>
		</c:if>
		<c:if test="${fileList!='[]'}">
			<!-- 查询不为空 -->
			<table>
				<tr>
					<th>序号</th>
					<th>文件名称</th>
					<th>文件大小</th>
					<th>上传时间</th>
					<th><a href="javascript:void(0)" onclick="viewOperator()">上传者</a></th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${fileList}" var="fileIn" varStatus="s">
					<tr>
						<td>${s.count}</td>
						<td>${fileIn.originalFileName }</td>
						<td>${fileIn.fileSize }</td>
						<td>${fileIn.uploadTime }</td>
						<td>${fileIn.operator }</td>
						<td><c:if test="${fileIn.status==1}">正常</c:if> <c:if
								test="${fileIn.status==0}">已注销</c:if></td>
						<td nowrap><a href="javascript:void" onclick="viewOnline()">在线查看</a>&nbsp;&nbsp;
							<a href="fileManage!downloadFile.action?id=${fileIn.id}"
							target="_self">下载</a>&nbsp;&nbsp; <a href="javascript:void(0)"
							target="showPageName" onclick="del(${fileIn.id})">删除</a></td>
					</tr>
				</c:forEach>
			</table>

			<!-- 页码列表 -->
			<div id="pageDiv">
				<!-- 上一页 -->
				<c:choose>
					<c:when test="${currentPage!=1}">
						<a href="fileManage.action?currentPage=${currentPage-1}&type=1&nextOrPre=previous&start=${start}&end=${end}"><input
							type="button" name="previousPage" value="上一页"></a>
					</c:when>
					<c:otherwise>
						<input type="button" disabled="true" name="previousPage"
							value="上一页">
					</c:otherwise>
				</c:choose>
				<!-- 循环列出所有页数 -->
				<c:forEach items="${itemList}" var="p" varStatus="vs">
					<!-- items的集合必须有多条记录才能列表显示,如其中含数组，而集合只有一条数据，则只显示一条 -->
					<c:choose>
						<c:when test="${p==currentPage}">
							<!-- 是当前页突出显示-->
							<span class="currentPage">${p }</span><!-- 禁止点击当前页 -->
						</c:when>
						<c:otherwise>
							<a href="fileManage.action?currentPage=${p}&type=1">${p}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<!-- 下一页 -->
				<c:choose>
					<c:when test="${currentPage!=totalPage }">
						<!-- 即不为最后一页 -->
						<a href="fileManage.action?currentPage=${currentPage+1}&type=1&nextOrPre=next&start=${start}&end=${end}"><input
							type="button" name="nextPage" value="下一页"></a>
					</c:when>
					<c:otherwise>
						<input type="button" disabled="true" value="下一页">
					</c:otherwise>
				</c:choose>
				_ 共${totalCount}条数据|共${totalPage }页|当前第${currentPage}页&nbsp;&nbsp;跳到第<input
					type="text" size="4" id="jumpPage">页&nbsp;<input
					type="button" id="jumpBt" onclick="sum(${totalPage})" value="确定">&nbsp;&nbsp;&nbsp;
			</div>
		</c:if>
	</center>
</body>
</html>