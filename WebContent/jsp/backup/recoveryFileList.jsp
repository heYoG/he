<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>可恢复文件列表</title>
<script type="text/javascript">
	function recovery(fileId){
		if(confirm("确定恢复该文件吗？"))
			location.href="fileManage!recoveryFile.action?id="+fileId+"&type=1";
	}
	
	function del(obj){
		if(confirm("are you sure to delete this file completely?")){
			
		}
	}
	
	function viewOperator(){
		alert("暂不支持的操作,敬请期待...");
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
</style>
</head>
<body>
	<center>
		<c:if test="${fileList=='[]'}">
			<!-- fileList是集合(可变数组) -->
			<!-- 查询为空 -->
			<span style="text-align:center;color:#72C992;font-size:20pt;"><div style="border:solid 0px;margin-top:20px">没有可恢复文件</div></span>
		</c:if>
		<c:if test="${fileList!='[]'}">
			<!-- 查询不为空 -->
			<table>
				<tr>
					<th>序号</th>
					<th>id</th>
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
						<td>${fileIn.id }</td>
						<td>${fileIn.originalFileName }</td>
						<td>${fileIn.fileSize }</td>
						<td>${fileIn.uploadTime }</td>
						<td>${fileIn.operator }</td>
						<td><c:if test="${fileIn.status==0}">已删除</c:if>
						<c:if test="${fileIn.status==1}">正常</c:if>
						</td><td nowrap><a href="javascript:void" onclick="recovery(${fileIn.id})">恢复</a>
						<a href="javascript:void(0)" onclick="del(${fileIn.id})">彻底删除</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</center>
</body>
</html>