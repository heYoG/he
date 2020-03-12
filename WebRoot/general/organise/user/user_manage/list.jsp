<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>用户列表结果</title>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
			<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script type="text/javascript" src="js/loadActive.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
         <script src="/Seal/js/logOper.js"></script>
         <script src="/Seal/js/util.js"></script>
		<script>
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
function show_sch(){
	window.location.href='general/usersearch/query2.jsp';
}
function edit_temp(ID){
	location="editUser2.do?user_id="+ID;
}
function delete_temp(ID,name){
	if(confirm('确认要删除该用户记录么？')){
		//alert(dept_no);
		logDel("删除用户",ID,name);//logOper.js
		location="deleteUser2.do?user_id="+ID;
	} 
}
function checkPage(){
	var page=document.getElementById("pageindex");
	var reg = /^[0-9]{1,3}$/g;
	if (!reg.test(page.value) || eval(page.value) > eval('${pageSplit.totalPage }') || page.value == 0) {
		alert("输入不是合理的页数或超出范围，请重输！");
		page.select();
		page.focus();
	}else{
		location="serUserManager.do?type=all&&pageIndex="+page.value;
	}
}
</script>	
	</head>
	<body class="bodycolor" style="margin-top: 0;">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big">&nbsp;<img src="images/menu/seal.gif" align="absmiddle"><span class="big3"> <a name="bottom">用户列表--[${dept_name}]</span>
    </td>
     <td align="right">
		<span class="big3"><a name="bottom" href="serUserManager.do?type=all">[所有用户列表]</a></span>
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
						用户类型
					</td>
					<td nowrap align="center">
						部门
					</td>
					<td nowrap align="center">
						是否使用证书
					</td>
					<td nowrap align="center">
						创建人
					</td>
					<td nowrap align="center">
						创建时间
					</td>
					<td nowrap align="center">
						操作
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
					<tr class="TableLine1">
						<td align="center">
							${aa.user_id }
						</td>
						<td nowrap align="center">
							${aa.user_name }
						</td>
						<td align="center">
							${aa.user_type }
						</td>
						<td align="center">
							${aa.dept_name }
						</td>
						<c:if test="${aa.useing_key ==1 }">						
						<td align="center">
							使用
						</td>
						</c:if>
						<c:if test="${aa.useing_key ==0 }">						
						<td align="center">
							不使用
						</td>
						</c:if>
						<td align="center">
							${aa.create_name }
						</td>
					    <td align="center">
							${aa.create_data }
						</td>				
						<c:if test="${aa.user_id !='admin' and aa.user_id !='logger'}">
						<td nowrap align="center">
						<a href="javascript:;"
									onClick="edit_temp('${aa.user_id }'); return false;">修改</a>&nbsp;
						<a href="javascript:;"
									onClick="delete_temp('${aa.user_id }','${aa.user_name }');return false;">删除</a>&nbsp;
		                 </td>
						</c:if>						
					    <c:if test="${aa.user_id =='admin' or aa.user_id =='logger'}">
					    <td nowrap align="center">
						<a href="../../Seal/personInfoIndex.do" target="main">控制面板</a>&nbsp;
		                 </td>
						</c:if>
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
								<a href="serUserManager.do?type=all&&pageIndex=1">第一页</a>，
										<a
									href="serUserManager.do?type=all&&pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="serUserManager.do?type=all&&pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a
									href="serUserManager.do?type=all&&pageIndex=${pageSplit.totalPage}">最后一页</a>
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
							<br>
							<input type="button" value="高级搜索" class="BigButton"
								onclick="show_sch();" />
						</td>
					</tr>
		</table>
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
