<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>用户审批</title>
<link rel="stylesheet" type="text/css"
	href="theme/${current_user.user_theme}/style.css">
<link rel="stylesheet" type="text/css"
	href="/Seal/theme/${current_user.user_theme}/dialog.css">
<link rel="stylesheet" type="text/css"
	href="/Seal/theme/pageSplit/pageSplit.css">
<script src="js/ccorrect_btn.js"></script>
<script src="js/utility.js"></script>
<script type="text/javascript" src="js/loadActive.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script src="/Seal/js/logOper.js"></script>
<script src="/Seal/js/util.js"></script>
<script src="/Seal/js/String.js"></script>
<script src="/Seal/js/dialog.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysRole.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script type="text/javascript" src="general/organise/user/user_approve/js/approve_listSer.js"></script>
<script>

	var user_no = "${current_user.user_id}";
	var user_ip = "${user_ip}";

	if (user_no == "") {
		top.location = "/Seal/login.jsp";
	}
	function checkPage() {
		var page = document.getElementById("pageindex");
		var page1 = page.value.toLow();
		if (page1 != 0) {
			var reg = /^[0-9]{1,3}$/g;
			if (!reg.test(page1)
					|| eval(page1) > eval('${pageSplit.totalPage }')
					|| page1.value == 0) {
				alert("输入不是合理的页数或超出范围，请重输！");
				page.select();
				page.focus();
			} else {
				location.href = "serUserApprove.do?type=flag2&&pageIndex="
						+ page1
						+ "&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }";
			}
		}
	}

	function getRoleName(userid) {
		SysRole.showSysRolesByUser_id(userid, cbf_role);
	}
	function cbf_role(d) {
		var rolename = "";
		for ( var i = 0; i < d.length; i++) {
			rolename += d[i].role_name + ",";
		}
		alert(rolename.substring(0, rolename.lastIndexOf(",")));
	}
	
</script>
</head>
<body class="bodycolor" style="margin-top: 0;">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big">&nbsp;<img src="images/menu/comm.gif"
				align="absmiddle"><span class="big3"> <a name="bottom">用户审批列表
				
			</span>
			</td>
			<td align="right" style="display: none"><input type="text"
				value="${dept_no}" id="dept_noS"> <input type="text"
				value="${is_junior}" id="is_juniorS"> <input type="text"
				value="${type}" id="typeS"> <span class="big3"><a
					name="bottom" href="serUserApprove.do?type=all">[所有用户列表]</a> </span>
			</td>
		</tr>
	</table>
	<c:if test="${pageSplit.datas=='[]'}">
		<table class="MessageBox" align="center" width="290">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">暂无相应记录！</div>
				</td>
			</tr>
		</table>
	</c:if>

	<c:if test="${pageSplit.datas!='[]'}">	
		<table class="TableList" width="100%" style="margin-top: 0;">
			<tr class="TableHeader">
				<td nowrap align="center">用户名</td>
				<td nowrap align="center">用户真实姓名</td>
				<td nowrap align="center">部门</td>
				<td nowrap align="center">管理范围</td>
				<td nowrap align="center">角色</td>
				<td nowrap align="center">创建人</td>
				<td nowrap align="center">创建时间</td>
				<td nowrap align="center">审批状态</td>
				<td nowrap align="center">审批意见</td>
				<td nowrap align="center">操作</td>
			</tr>

			<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				<c:if test="${aa.user_id != 'logger'}">
					<tr class="TableLine1">
						<td align="center">${aa.user_id }</td>
						<td nowrap align="center">${aa.user_name }</td>
						<td align="center">${aa.dept_name }</td>
						<td align="center"><c:if test="${aa.rang_type ==0 }">
							本部门
							</c:if> <c:if test="${aa.rang_type ==1 }">
							全体
							</c:if> <c:if test="${aa.rang_type ==2 }">
							指定部门
							</c:if>
						</td>
						<td align="center"><a href="javascript:;"
							onclick="getRoleName('${aa.user_id }')">${aa.roleNum}</a>
						</td>
						<td align="center">${aa.create_name }</td>
						<td align="center">${aa.create_data }</td>
						<td align="center">
						<c:if test="${aa.is_approve==0}">待审批</c:if>
						<c:if test="${aa.is_approve==2}"><span style="color:red">拒绝</span></c:if>
						</td>
						<td align="center">			
						<c:if test="${aa.state!=''}">
						<a href="javascript:;" onclick="approve_reason('${aa.state}')">详细</a>
						</c:if>				
						</td>
						<td nowrap align="center">
						<c:if test="${aa.is_approve==0}"> <a href="javascript:void()"
							onClick="agree_app(${aa.unique_id}); return false;">同意</a>&nbsp;
							<a href="javascript::void()"
							onClick="refuse_app(); return false;">拒绝</a>&nbsp;
						</c:if>
						<c:if test="${aa.is_approve==2}">
						<a href="javascript:void()"
							onClick="edit_app('${aa.user_id }'); return false;">修改</a>&nbsp;
							<a href="javascript::void()"
							onClick="delete_app('${aa.user_id }');return false;">删除</a>&nbsp;
						</c:if>
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
							<a
								href="serUserApprove.do?type=flag2&&pageIndex=1&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">第一页</a>，
										<a
								href="serUserApprove.do?type=flag2&&pageIndex=${pageSplit.nowPage-1}&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">上一页</a>，
									</c:if>
						<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
						<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
							<a
								href="serUserApprove.do?type=flag2&&pageIndex=${pageSplit.nowPage+1}&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">下一页</a>，
										<a
								href="serUserApprove.do?type=flag2&&pageIndex=${pageSplit.totalPage}&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">最后一页</a>
						</c:if>
						转到 <input size="2" id="pageindex" /> 页
						<button width="20" onclick="checkPage();">GO</button>
					</div>
				</td>
			</tr>
		</table>
	</c:if>
	<table width="100%" style="margin-top: 0;">
				<tr>
						<td align="center">
							<br>
							<input type="button" value="返回查询" class="BigButton" onClick="show_sch()"/>
						</td>
					</tr>
		</table>
		
		<c:forEach var="bb" items="${pageSplit.datas }" varStatus="status">
		<div id="overlay"></div>
	<div id="approve" class="ModalDialog" style="width: 500px;display:none">
		<div class="header">
			<span class="title">审批意见</span><a class="operation"
				href="javascript:HideDialog('approve');"><img
				src="/Seal/images/close.png" /> </a>
		</div>
			<div id="approve_body" class="body">
				<span id="confirm"></span>
				<textarea id="context" name="approve_text" cols="60" rows="5"
					style="overflow-y: auto;" class="BigInput" wrap="yes"></textarea>
				<br> &nbsp;&nbsp;
			</div> 
			<input type="hidden" name="approver_reason" id="approver_reason" />
			<div id="footer" class="footer">
			  <input class="BigButton" type="button" value="确定" onclick="doButton(${bb.unique_id})" />
			  <input class="BigButton" type="button" value="关闭" onclick="HideDialog('approve')"/>
			</div>
	</div>
		</c:forEach>	
</body>
</html>
