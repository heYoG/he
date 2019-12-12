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
<title>可恢复文件列表</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	function recovery(fileId){//单个文件恢复
		if(confirm("确定恢复该文件吗？"))
			location.href="fileManage!recoveryFile.action?id="+fileId+"&type=1";
	}
	
	function del(fileId){//单个文件彻底删除
		if(confirm("are you sure to delete this file completely?")){
			location="fileManage!delete_complete.action?id="+fileId+"&type=0";
		}
	}
	
	function viewOperator(){//查看上传文件者
		alert("暂不支持的操作,敬请期待...");
	}
	
	function sum(totalPage){//跳转页码
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
		location="fileManage!recoveryFileList.action?currentPage="+jumpPage+"&type=0";
	}
	
	$(function(){//jQuery函数
		$("#selectAllID").click(function(){//复选框全选
			var ch=$(":checkbox[name='chName']");
			var deselectAll=$(":checkbox[name='deselectAllName']");
			for(var i=0;i<ch.length;i++){//全选
				ch[i].checked=true;
			}
			for(var j=0;j<deselectAll.length;j++){//反选框不选
				deselectAll[j].checked=false;
			}
		});
		
		$("#deselectAllID").click(function(){//复选框反选
			var ch=$(":checkbox[name='chName']");
			var selectAll=$(":checkbox[name='selectAllName']");
			for(var i=0;i<ch.length;i++){//反选
				ch[i].checked=false;
			}
			for(var j=0;j<selectAll.length;j++){//全选框反选
				selectAll[j].checked=false;
			}
		});
		
		$("#recoveryID").click(function(){//批量恢复文件
			var ch=$(":checkbox[name='chName']");
			var rec="";
			for(var i=0;i<ch.length;i++){
				if(ch[i].checked){
					rec+=","+ch[i].value;
				}
			}
			if(rec==""){
				alert("至少选择一项恢复!");
				return false;
			}else{
				if(confirm("确定恢复所选文件吗?")){
					location.href="fileManage!recoveryFiles.action?ids="+rec.substring(1)+"&type=1";//操作后跳转到文件管理页面					
				}
			}
		});
		
		$("#deleteSelectedID").click(function(){//批量彻底删除文件
			var ch=$(":checkbox[name='chName']");
			var deleteComplete="";
			for(var i=0;i<ch.length;i++){
				if(ch[i].checked){
					deleteComplete+=","+ch[i].value;
				}
			}
				if(deleteComplete==""){
					alert("至少选择一项删除!");
					return false;
				}else{
					if(confirm("确定永久删除所选项吗?")){
						location.href="fileManage!delete_completeBatch.action?ids="+deleteComplete.substring(1)+"&type=0";//操作后跳转到可恢复文件页面						
					}
				}
		});
	})
	
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
	width: 25%;
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

.currentPage {
	font-size: 20px;
	color: red;
	border:0px;
	cursor:pointer;
}

#delCom{
	color:red
}

.recover{
	color:green
}
a:link{
	text-decoration:none
}
</style>
</head>
<body>
	<center>
		<c:if test="${fileList=='[]'}">
			<!-- fileList是集合(可变数组) -->
			<!-- 查询为空 -->
			<span style="text-align: center; color: #72C992; font-size: 20pt;"><div
					style="border: solid 0px; margin-top: 20px">没有可恢复文件</div></span>
		</c:if>
		<c:if test="${fileList!='[]'}">
			<!-- 查询不为空 -->
			<table>
				<tr>
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
						<td><input type="checkbox" name="chName" id="chID" value="${ fileIn.id}"></td>
						<td>${s.count}</td>
						<td>${fileIn.originalFileName }</td>
						<td>${fileIn.fileSize }</td>
						<td>${fileIn.uploadTime }</td>
						<td>${fileIn.operator }</td>
						<td><c:if test="${fileIn.status==0}">已删除</c:if> <c:if
								test="${fileIn.status==1}">正常</c:if></td>
						<td nowrap><a href="javascript:void"
							class="recover" onclick="recovery(${fileIn.id})">恢复</a> <a
							href="javascript:void(0)" id="delCom" onclick="del(${fileIn.id})">彻底删除</a></td>
					</tr>
				</c:forEach>
			</table>
			<div id="checkDiv">
				<input type="checkbox" name="selectAllName" id="selectAllID">&nbsp;全选&nbsp;&nbsp;
				<input type="checkbox" name="deselectAllName" id="deselectAllID">&nbsp;反选&nbsp;&nbsp;
				<input type="button" name="recoveryName" id="recoveryID" value="恢复所选">&nbsp;&nbsp;
				<input type="button" name="deleteSelectedName" id="deleteSelectedID" value="删除所选">
			</div>
			<!-- 页码列表 -->
			<div id="pageDiv">
				<!-- 上一页 -->
				<c:choose>
					<c:when test="${pageData.currentPage!=1}">
						<a href="fileManage!recoveryFileList.action?currentPage=${pageData.currentPage-1}&type=0&nextOrPre=previous&start=${pageData.start}&end=${pageData.end}"><input
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
							<span class="currentPage">${p }</span><!-- 禁止点击当前页 -->
						</c:when>
						<c:otherwise>
							<a href="fileManage!recoveryFileList.action?currentPage=${p}&type=0">${p}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<!-- 下一页 -->
				<c:choose>
					<c:when test="${pageData.currentPage!=pageData.totalPage }">
						<!-- 即不为最后一页 -->
						<a href="fileManage!recoveryFileList.action?currentPage=${pageData.currentPage+1}&type=0&nextOrPre=next&start=${pageData.start}&end=${pageData.end}"><input
							type="button" name="nextPage" value="下一页"></a>
					</c:when>
					<c:otherwise>
						<input type="button" disabled="true" value="下一页">
					</c:otherwise>
				</c:choose>
				_ 共${pageData.totalCount}条数据|共${pageData.totalPage }页|当前第${pageData.currentPage}页&nbsp;&nbsp;跳到第<input
					type="text" size="4" id="jumpPage">页&nbsp;<input
					type="button" id="jumpBt" onclick="sum(${pageData.totalPage})" value="确定">&nbsp;&nbsp;&nbsp;
			</div>
		</c:if>
	</center>
</body>
</html>