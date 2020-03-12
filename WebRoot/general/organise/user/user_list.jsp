<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>用户创建列表记录</title>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
	    <script src="/Seal/js/util.js"></script>
	    <script src="/Seal/js/String.js"></script>
	    <script type='text/javascript' src='/Seal/dwr/interface/SysRole.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
	<script>
function show_sch(){
	window.location.href='general/usersearch/query2.jsp';
}
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
		location="userList.do?pageIndex="+page1;
	}
	}
}
function getRoleName(userid){
  SysRole.showSysRolesByUser_id(userid,cbf_role);
}
function cbf_role(d){
  var rolename="";
  for(var i=0;i<d.length;i++){
    rolename+=d[i].role_name+",  ";
  }
  alert(rolename);
}

function showUserCert(ID,name){
	window.location.href="serUserCert.do?user_id="+ID+"&user_name="+name;
}
</script>		
	</head>
	<body class="bodycolor" style="margin-top: 0;">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/comm.gif">
					<span class="big3">用户创建列表记录</span>
				</td>
			</tr>
		</table>
		<c:if test="${pageSplit.datas!='[]' }">
			<table class="TableList" width="100%" style="margin-top: 0;">
				<tr class="TableHeader">
					<td nowrap align="center">
						用户名
					</td>
					<td nowrap align="center">
						用户真实姓名
					</td>
					<td nowrap align="center">
						部门
					</td>
					<td nowrap align="center">
						管理范围
					</td>
					<td nowrap align="center">
						角色
					</td>
					<td nowrap align="center">
						创建人
					</td>
					<td nowrap align="center">
						新建时间
					</td>
					<td nowrap align="center">
						备注
					</td>
					<td nowrap align="center" style="display:none">
						操作
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				<c:if test="${aa.user_id != 'logger'}">
					<tr class="TableLine1">
						<td align="center">
							${aa.user_id }
						</td>
						<td nowrap align="center">
							${aa.user_name }
						</td>
						<td align="center">
							${aa.dept_name }
						</td>
						<td align="center">
							<c:if test="${aa.rang_type ==0 }">
							本部门
							</c:if>
							<c:if test="${aa.rang_type ==1 }">
							全体
							</c:if>
							<c:if test="${aa.rang_type ==2 }">
							指定部门
							</c:if>
						</td>
						<td align="center">
						   <a href="javascript:;" onclick="getRoleName('${aa.user_id }')">${aa.roleNum }</a>
						</td>
						<td align="center">
							${aa.create_user }
						</td>
					    <td align="center">
							${aa.create_data }
						</td>
						 <td nowrap align="center">
						 <c:if test="${aa.user_remark!=''}">	
						 <a href="#" onclick="alert('${aa.user_remark }');return false;" title="${aa.user_remark }">详细</a>
						 </c:if>
						 </td> 
						 <td nowrap align="center" style="display:none">
							<a href="javascript:;"
									onClick="showUserCert('${aa.user_id }','${aa.user_name }'); return false;">证书</a>&nbsp;
						</td>
					</tr>
					</c:if>
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
								<a href="userList.do?pageIndex=1">第一页</a>，
										<a
									href="userList.do?pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="userList.do?pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a
									href="userList.do?pageIndex=${pageSplit.totalPage}">最后一页</a>
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
		<!-- <table width="100%" style="margin-top: 0;">
				<tr>
						<td align="center">
							<br>
							<input type="button" value="返回查询" class="BigButton"
								onclick="show_sch();" />
						</td>
					</tr>
					</table> -->	
		<table class="MessageBox" align="center" width="290">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						共有记录 ${pageSplit.totalCount } 个！
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
