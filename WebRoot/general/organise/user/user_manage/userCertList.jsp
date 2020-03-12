<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户证书历史详细</title>
	<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
	<script src="js/ccorrect_btn.js"></script>
	<script src="js/utility.js"></script>
	<script src="/Seal/js/util.js"></script>
	<script src="/Seal/js/String.js"></script>
	<script>
	function checkPage(){
		var page=document.getElementById("pageindex");
		var page1=page.value.toLow();
		if(page1!=0){
			var reg = /^[0-9]{1,3}$/g;
			if (!reg.test(page1) || eval(page1) > eval('${pageSplit.totalPage }') || page1.value == 0) {
				alert("输入不是合理的页数或超出范围，请重输！");
				page.select();
				page.focus();
			}else{
				location="serUserCert.do?user_id=${user_id}&&user_name=${user_name}&&pageIndex="+page1;
			}
		}
	}
	</script>
  </head>
  
 <body class="bodycolor" style="margin-top: 0;">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big">&nbsp;<img src="images/menu/comm.gif" align="absmiddle"><span class="big3"> <a name="bottom">证书历史记录,当前用户--[${user_name}]</span>
    </td>
  </tr>
</table>
 <c:if test="${pageSplit.datas=='[]' }">
   <table class="MessageBox" align="center" width="290">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						暂无相应记录！
					</div>
				</td>
			</tr>
		</table>
   </c:if>
   <c:if test="${pageSplit.datas!='[]' }">
			<table class="TableList" width="100%" style="margin-top: 0;">
				<tr class="TableHeader">
					<td nowrap align="center">
						证书序列号
					</td>
					<td nowrap align="center">
						证书DN
					</td>
					<td nowrap align="center">
						证书使用者
					</td>
					<td nowrap align="center">
						证书颁发机构
					</td>
					<td nowrap align="center">
						有效起始时间
					</td>
					<td nowrap align="center">
						有效终止时间
					</td>
					<td nowrap align="center">
						使用状态
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				<tr class="TableLine1">
						<td align="center">
							${aa.cert_no }
						</td>
						<td nowrap align="center">
							${aa.cert_dn }
						</td>
						<td align="center">
							${aa.cert_user }
						</td>
						<td align="center">
							${aa.cert_issue }
						</td>
						<td align="center">
							${aa.begin_time }
						</td>
						<td align="center">
							${aa.end_time }
						</td>
						<td nowrap align="center">
						<c:if test="${aa.is_active=='1'}">正在使用</c:if>
						<c:if test="${aa.is_active=='0'}">历史证书</c:if>
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
								<a href="serUserCert.do?user_id=${user_id}&&user_name=${user_name}">第一页</a>，
										<a
									href="serUserCert.do?user_id=${user_id}&&user_name=${user_name}&&pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="serUserCert.do?user_id=${user_id}&&user_name=${user_name}&&pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a
									href="serUserCert.do?user_id=${user_id}&&user_name=${user_name}&&pageIndex=${pageSplit.totalPage}">最后一页</a>
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
  </body>
</html>
