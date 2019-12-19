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
<script type="text/javascript">
	var ret="${downloadError}";
	if(ret!=""){//下载文件出错(文件不存在)
		alert("下载文件出错,错误码:${downloadError}");
		window.history.go(-1);//返回上一页
	}

	$(function(){//复选框全选
		$("#selectAllID").click(function(){
			var ch=$(":checkbox[name='chName']");
			var deselectAll=$(":checkbox[name='deselectAllName']");
			for(var i=0;i<ch.length;i++)
				ch[i].checked=true;
			for(var i=0;i<deselectAll.length;i++)
				deselectAll[i].checked=false;
		});
	
		$("#deselectAllID").click(function(){//复选框反选
			var ch=$(":checkbox[name='chName']");
			var selectAll=$(":checkbox[name='selectAllName']");
			for(var i=0;i<ch.length;i++){
				ch[i].checked=false;
			}
			for(var i=0;i<selectAll.length;i++)
				selectAll[i].checked=false;
		});
		
		$("#deleteSelectedID").click(function(){//批量虚拟删除
			var ids=$(":checkbox[name='chName']");
			var delAllIds="";
			for(var i=0;i<ids.length;i++){
				if(ids[i].checked)
					delAllIds+=","+ids[i].value;
			}
			if(delAllIds==""){
				alert("至少选择一个要删除的文件!");
				return false;
			}else{
				if(confirm("确定删除所有已选文件吗?")){
					location.href="fileManage!delete_updateBatch.action?ids="+delAllIds.substring(1)+"&type=1";
				}				
			}
		});
	})


	function viewOnline(){//在线查看文件
		alert("暂不支持的操作,敬请期待...");
	}
	
	function del(fileId){//虚拟删除文件
		if(confirm("确定删除此文件吗?"))
			location.href="fileManage!delete_update.action?id="+fileId+"&type=1";
	}
	
	function viewOperator(){//查看文件上传者
		alert("暂不支持的操作,敬请期待...");
	}
	
	function rec(fileId){//恢复虚拟删除文件
		if(confirm("确认恢复该文件？"))
			location.href="fileManage!recoveryFile.action?id="+fileId;
	}
	
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
		location="fileManage.action?currentPage="+jumpPage+"&type=1";
	}
	
</script>
</head>
<body>
	<center>
		<c:if test="${fileList=='[]'}">
			<!-- fileList是集合(可变数组) -->
			<!-- 查询为空 -->
			<span style="text-align: center; font-size:15pt;color:red">此处空空如也</span>
			<br>
			<a href="../backup/fileBackup.jsp" targer="showPageName">前往文件备份...</a>
		</c:if>
		<c:if test="${fileList!='[]'}">
			<!-- 查询不为空 -->
			<table>
				<tr>
					<!-- <th>表示标题列 -->
					<th>选择</th>
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
						<td><input type="checkbox" name="chName" id="chID"
							value="${fileIn.id}"></td>
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
			<div id="checkDiv">
				<input type="checkbox" name="selectAllName" id="selectAllID">&nbsp;全选&nbsp;&nbsp;
				<input type="checkbox" name="deselectAllName" id="deselectAllID">&nbsp;反选&nbsp;&nbsp;
				<input type="button" name="deleteSelectedName" id="deleteSelectedID" value="删除所选">
			</div>
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
		</c:if>
	</center>
</body>
</html>