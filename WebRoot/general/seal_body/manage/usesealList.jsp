<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.println("basePath:" + basePath);
%>
<html>
	<head>
		<title>印模管理</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/dialog.css">
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script>
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
function show_temp(data){
	if(data!=""){
		//document.getElementById("info_body").innerHTML="<img src='data:image/bmp;base64,"+data+"'/>";
		var obj = document.getElementById("DMakeSealV61");
		var l = obj.LoadData(data);
		var vID = 0;
		vID = obj.GetNextSeal(vID);
		obj.SelectSeal(vID);
		ShowDialog("info");
	}else{
		alert("无图片信息!");
	}
}
function set_prv(sealid){
 location="sealSetGuide.do?seal_id="+sealid;
}
function write_key(seal_id,seal_name){
 var str="/Seal/general/seal_body/manage/write_key.jsp?seal_id="+seal_id+"&&seal_name="+seal_name;
 newModalDialog(str,300,200,null);
}
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
		location="tempAppuse.do?type=all&&pageIndex="+page1;
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
	<body class="bodycolor" style="margin-top: 0;" onload="load()">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal.gif" HEIGHT="20">
					<span class="big3"> <a name="bottom">可用印章列表 </a> </span>
				</td>
			</tr>
		</table>
		<c:if test="${pageSplit.datas=='[]' }">
		<table class="MessageBox" align="center" width="280">
        <tr>
          <td class="msg info">
		 <div class="content" style="font-size:12pt">暂无相应印章</div>
		   </td>
        </tr>
         </table>
		</c:if>
		<c:if test="${pageSplit.datas!='[]' }">
			<table class="TableList" width="100%" style="margin-top: 0;">
				<tr class="TableHeader">
					<td nowrap align="center">
						印章名称
					</td>
					<td nowrap align="center">
						类型
					</td>
					<td nowrap align="center">
						所属部门
					</td>
					<td nowrap align="center">
						制章人
					</td>
					<td nowrap align="center">
						制章时间
					</td>
					<td nowrap align="center">
						是否制章
					</td>
					<td nowrap align="center">
						状态
					</td>
					<td nowrap align="center">
						操作
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">			
				<input type="hidden" name="is_junior" id="is_junior" value="${is_junior}">	
					<tr class="TableLine1">
						<td nowrap align="center">
						${aa.seal_name}
						</td>
						<td nowrap align="center">
							${aa.seal_type }
						</td>
						<td align="center">
							${aa.dept_name }
						</td>
						<td align="center">
							${aa.seal_creator }
						</td>
						<td align="center">
						  <a href="#" onclick="alert('${aa.create_time }');return false;" title="${aa.create_time }">详细</a>
						</td>
					 <td nowrap align="center">
						<span style="color: green">已制章</span>
						</td>
				     <c:if test="${aa.seal_status=='5'}">
				     <td nowrap align="center">
						<span style="color: green">正常</span>
						</td>
				     </c:if>
					 <c:if test="${aa.seal_status=='6'}">
				     <td nowrap align="center">
						<span style="color: green">失效</span>
						</td>
				     </c:if>
				      <c:if test="${aa.seal_status=='7'}">
				     <td nowrap align="center">
						<span style="color: green">注销</span>
						</td>
				     </c:if>
					<td nowrap align="center">
						<a href="javascript:;" onClick="show_temp('${aa.seal_data}')">查看</a>&nbsp;
						</td>
					</tr>
				</c:forEach>
				<tr class="TableLine1">
					<td colspan="12" class="pager">
						<div class="pager" align="right">
							共${pageSplit.totalCount }条记录 每页 ${pageSplit.pageSize } 条
							第${pageSplit.nowPage } 页/共${pageSplit.totalPage }页
							<c:if test="${pageSplit.nowPage==1}">
										第一页，上一页，
									</c:if>
							<c:if test="${pageSplit.nowPage!=1}">
								<a href="tempAppuse.do?type=all&&pageIndex=1">第一页</a>，
										<a
									href="tempAppuse.do?type=all&&pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a href="tempAppuse.do?type=all&&pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a
									href="tempAppuse.do?type=all&&pageIndex=${pageSplit.totalPage}">最后一页</a>
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
			</br>
		</c:if>
		<!--  <table width="100%" style="margin-top: 0;">
			<tr>
				<td align="center">
					<input type="button" value="返回查询" class="BigButton"
						onclick="window.location='general/seal_template/manage/query/query.jsp'"
						title="返回查询" name="button">
					&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
		</table>-->
		
		<div id="overlay"></div>
		<div id="info" class="ModalDialog" style="width: 400px;">
			<div class="header">
				<span class="title">印章信息</span><a class="operation"
					href="javascript:HideDialog('info');"><img
						src="images/close.png" /> </a>
			</div>
			<div id="info_body" class="body" align="center">
				<%@include file="../../../inc/makeSealObject.jsp"%>
			</div>
			<div id="footer" class="footer">
				<input class="BigButton" onclick="HideDialog('info')" type="button"
					value="关闭" />
			</div>
		</div>
	</body>
</html>
