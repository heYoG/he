<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>印模管理</title>
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
</style>
</head>
<body>
	<center>
		<c:choose>
			<c:when test="${sealImageList=='[]'}">
				<span style="text-align: center; font-size:15pt;color:red">此处空空如也</span>
			</c:when>
			<c:otherwise>
				<table>
					<tr>
						<th>序号</th>
						<th>原名称</th>
						<th>保存文件名</th>
						<th>大小</th>
						<th>上传时间</th>
						<th>上传者</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${sealImageList}" var="si" varStatus="vs">
						<tr>
							<td>${vs.count }</td>
							<td>${si.originalName }</td>
							<td>${si.saveName }</td>
							<td>${si.imgSize }</td>
							<td>${si.uploadtime }</td>
							<td>${si.operator }</td>
							<td><c:if test="${si.status==0 }">已注销</c:if><c:if test="${si.status==1 }">正常</c:if></td>
							<td></td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
	</center>
</body>
</html>