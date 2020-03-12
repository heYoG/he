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
    
    <title>文档列表</title>
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
				location="docQuery.do?type=flag3&&pageIndex="+page1;
			}
		}
    }
	function show_printDetail(doc_no){
		location="general/docPrint/set_docPrint/printDetail.jsp?doc_no="+doc_no;	
	}
	function show_print(doc_no){
 		location="general/docPrint/set_docPrint/show_docPrintSet.jsp?doc_no="+doc_no;
	}
	function set_print(doc_no){
 		location="general/docPrint/set_docPrint/set_docPrint.jsp?doc_no="+doc_no;
	}
	function show_detail(text){
		var hiddenId=text+"Hidden";
		alert($(hiddenId).value);
	}
	</script>
  </head>
  
 <body class="bodycolor" style="margin-top: 0;">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big">&nbsp;<img src="images/menu/comm.gif" align="absmiddle"><span class="big3"> <a name="bottom">文档列表</span>
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
						文档编号
					</td>
					<td nowrap align="center">
						文档名称
					</td>
					<td nowrap align="center">
						文档标题
					</td>
					<td nowrap align="center">
						文档类型
					</td>
					<td nowrap align="center">
						文档所属部门
					</td>
					<td nowrap align="center">
						文档录入系统时间
					</td>
					<td nowrap align="center">
						文档录入系统的ip
					</td>
					<td nowrap align="center">
						文档关键字
					</td>
					<td nowrap align="center" width="100">
						文档内容
					</td>
					<td nowrap align="center">
						操作
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				<tr class="TableLine1">
						<td align="center">
							${aa.doc_no }
						</td>
						<td nowrap align="center">
							${aa.doc_name }
						</td>
						<td align="center">
							${aa.doc_title }
						</td>
						<td align="center">
							${aa.doc_type }
						</td>
						<td align="center">
							${aa.dept_name }
						</td>
						<td nowrap align="center">
							${aa.create_time }
						</td>
						<td nowrap align="center">
							${aa.create_ip }
						</td>
						<td nowrap align="center">
							${aa.doc_keys }
						</td>
						<td  align="center" width="100" >
						<input type="hidden" id="${aa.doc_no}Hidden" value="${aa.doc_content}">
							<a href="javascript:;" onClick="show_detail('${aa.doc_no}');return false;">详细</a>
						</td>
						<td nowrap align="center">
							<a href="javascript:;" onClick="show_printDetail('${aa.doc_no}');return false;">打印详情</a>&nbsp;
							<a href="javascript:;" onClick="show_print('${aa.doc_no}');return false;">设置详情</a>&nbsp;
							<a href="javascript:;" onClick="set_print('${aa.doc_no}');return false;">设置打印份数</a>&nbsp;
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
								<a href="docQuery.do?type=flag3">第一页</a>，
										<a
									href="docQuery.do?type=flag3&&pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="docQuery.do?type=flag3&&pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a
									href="docQuery.do?type=flag3&&pageIndex=${pageSplit.totalPage}">最后一页</a>
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
		<table width="100%" style="margin-top: 0;">
			<tr>
				<td align="center">
					<input type="button" value="返回查询" class="BigButton"
						onclick="window.location='general/docPrint/manage/query/query.jsp'"
						title="返回查询" name="button">
					&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
		</table>
  </body>
</html>
