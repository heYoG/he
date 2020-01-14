<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String path=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发件箱</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
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
	var deleteRet="${deleteRet}";
	if(deleteRet=='0000'){
		alert("删除邮件成功!");
	}else if(deleteRet=='0004'){
		alert("删除邮件失败!");
	}
	
	function sum(totalPage,type){//跳转页面
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
		location="emailAct!boxManage.action?currentPage="+jumpPage+"&type="+type;
	}

	//收件箱或发件箱
	function del(emailID,type){
		if(confirm("确定删除此记录吗?"))
			location="emailAct!deleteEmail.action?id="+emailID+"&type="+type;
	}
	
	//已删除邮件
	function delComplete(emailID,type){
		if(confirm("操作不可恢复,确定执行?"))
			location="emailAct!deleteEmailComplete.action?id="+emailID+"&type="+type;
	}
</script>
</head>
<body>
	<center>
		<c:choose>
			<c:when test="${emailList=='[]'}">
				<span style="text-align: center; font-size: 15pt; color: red">发件箱为空...</span>
			</c:when>
			<c:otherwise>
				<table>
					<tr>
						<th>序号</th>
						<th>发件人</th>
						<th>发件时间</th>
						<th>邮件主题</th>
						<th>邮件内容</th>
						<th>接收人</th>
						<th>附件</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${emailList}" var="el" varStatus="vs">
						<tr>
							<td>${vs.count }</td>
							<td>${el.sender}</td>
							<td>${el.sendTime }</td>
							<td>${el.theme }</td>
							<td>${el.text }</td>
							<td>${el.addressee }</td>
							<td>${el.accessory }</td>
							<td>
								<c:if test="${el.type==0 || el.type==1 }">
									<a href="javascript:void(0)" onclick="del(${el.id},${el.type})">删除</a>
								</c:if>
								<c:if test="${el.type==3 }">
									<a href="javascript:void(0)" onclick="delComplete(${el.id},${el.type})">彻底删除</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pageDiv">
					<!-- 上一页 -->
					<c:choose>
						<c:when test="${pageData.currentPage!=1}">
							<a
								href="emailAct!boxManage.action?currentPage=${pageData.currentPage-1}&nextOrPre=previous&start=${pageData.start}&end=${pageData.end}&type=${pageData.type}"><input
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
								<a href="emailAct!boxManage.action?currentPage=${p}&type=${pageData.type}">${p}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<!-- 下一页 -->
					<c:choose>
						<c:when test="${pageData.currentPage!=pageData.totalPage }">
							<!-- 即不为最后一页 -->
							<a
								href="emailAct!boxManage.action?currentPage=${pageData.currentPage+1}&nextOrPre=next&start=${pageData.start}&end=${pageData.end}&type=${pageData.type}"><input
								type="button" name="nextPage" value="下一页"></a>
						</c:when>
						<c:otherwise>
							<input type="button" disabled="true" value="下一页">
						</c:otherwise>
					</c:choose>
					_ 共${pageData.totalCount}条数据|共${pageData.totalPage }页|当前第${pageData.currentPage}页&nbsp;&nbsp;跳到第<input
						type="text" size="4" id="jumpPage">页&nbsp;<input
						type="button" id="jumpBt" onclick="sum(${pageData.totalPage},${pageData.type })"
						value="确定">&nbsp;&nbsp;&nbsp;
				</div>
			</c:otherwise>
		</c:choose>	
	</center>
</body>
</html>