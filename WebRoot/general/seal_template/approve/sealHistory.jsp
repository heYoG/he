<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>审批记录</title>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/attach.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script type="text/javascript">
function checkPage(){
	var page=document.getElementById("pageindex");
	var page1=page.value.toLow();
	if(page1!=0){
	var reg = /^[0-9]{1,3}$/g;
	if (!reg.test(page1) || eval(page1) > eval("${pageSplit.totalPage }") || page1.value == 0) {
		alert("输入不是合理的页数或超出范围，请重输！");
		page.select();
		page.focus();
	}else{
		location="tempAppHis.do?pageIndex="+page1;
	}
	}
}
	function showUsers(user_id){
		var url="/Seal/general/log/log_oper/showUser.jsp?userId="+user_id;
		var h=150;
		var w=620;
		newModalDialog(url,w,h);
	}
		</script>
	</head>
	<body class="bodycolor" topmargin="5">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal_orig.gif" align="absmiddle">
					<span class="big3"> 印模审批记录</span>&nbsp;
				</td>

			</tr>
		</table>
		<c:if test="${pageSplit.datas!='[]' }">
		<table class="TableList" width="100%">
			<tr class="TableHeader">
				<td nowrap align="center">
					印模名称
				</td>
				<td nowrap align="center">
					类型
				</td>
				<td nowrap align="center">
					所属部门
				</td>
				<td nowrap align="center">
					申请人
				</td>
				<td nowrap align="center">
					申请时间
				</td>
				<td nowrap align="center">
					审批时间
				</td>
				<td nowrap align="center">
					审批结果
				</td>
				<td nowrap align="center">
					审批意见
				</td>
				<td nowrap align="center">
				    备注
				</td>
			</tr>

			<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				<tr class="TableLine1">
					<td nowrap align="center">
						${aa.temp_name }
					</td>
					<td nowrap align="center">
						${aa.seal_type }
					</td>
					<td nowrap align="center">
						${aa.dept_name }
					</td>
					<td nowrap align="center">
						<a href='#' onclick="showUsers('${aa.user_apply }');flag=true;" >${aa.user_name }</a>
					</td>
					<td align="center">
						${aa.create_time }
					</td>
					<td nowrap align="center">
						${aa.approve_time }
					</td>
				
					<td nowrap align="center">
							<c:if test="${aa.temp_status=='1'}">
								<span style="color: gray">${aa.status_name }</span>
							</c:if>
							<c:if test="${aa.temp_status=='2'}">
								<span style="color: green">${aa.status_name }</span>
							</c:if>
							<c:if test="${aa.temp_status=='3'}">
								<span style="color: red">${aa.status_name }</span>
							</c:if>
						</td>
					 <td nowrap align="center">
					  <c:if test="${aa.approve_text!=''}">
					 <a href="#" onclick="alert('${aa.approve_text }');return false;" title="${aa.approve_text }">详细</a>
					</c:if>
					 </td> 
					 <td nowrap align="center">
					 <c:if test="${aa.temp_remark!=''}">
						 <a href="#" onclick="alert('${aa.temp_remark }');return false;" title="${aa.temp_remark }">详细</a> 
						 </c:if>
					 </td> 
				</tr>
			</c:forEach>
			<tr class="TableLine1">
				<td colspan="10" class="pager">
					<div class="pager" align="right">
						共${pageSplit.totalCount }条记录 每页 ${pageSplit.pageSize } 条
						第${pageSplit.nowPage } 页/共${pageSplit.totalPage }页
						<c:if test="${pageSplit.nowPage==1}">
										第一页，上一页，
									</c:if>
						<c:if test="${pageSplit.nowPage!=1}">
							<a href="tempAppHis.do?pageIndex=1">第一页</a>，
										<a
								href="tempAppHis.do?pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
						<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
						<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
							<a
								href="tempAppHis.do?pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a
								href="tempAppHis.do?pageIndex=${pageSplit.totalPage}">最后一页</a>
						</c:if>
						转到
						<input size="2" id="pageindex" />
						页
						<button width="20" onclick="checkPage();">
							GO
						</button>
					</div>
				</td>
			</tr>
		</table>
		</c:if>
		<c:if test="${pageSplit.datas=='[]' }">
		<table class="MessageBox" align="center" width="290">
        <tr>
        <td class="msg info">
       <div class="content" style="font-size:12pt">共有审批印模记录 ${pageSplit.totalCount} 个！</div>
        </td>
       </tr>
       </table>
		</c:if>
	</body>
</html>

