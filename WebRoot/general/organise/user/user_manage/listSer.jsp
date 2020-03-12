<%@ page contentType="text/html;charset=utf-8"%>
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
        <script src="/Seal/js/String.js"></script>
        <script src="/Seal/general/organise/user/user_approve/js/showUser.js"></script>
        <script type='text/javascript' src='/Seal/dwr/interface/SysRole.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		
		<script>
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
function show_sch(){
	window.location.href='general/organise/user/user_manage/query.jsp';
}

function edit_temp(ID,create_name){
	if(user_no!='admin'&create_name!='系统管理员'&ID==user_no){
 		alert("当前登录用户不可修改此用户！");
 	}else{
		if(confirm('确认要修改该用户么？')){
			location="editUser.do?user_id="+ID;
		}; 
 	};
}
function showUserCert(ID,name){
	window.location.href="serUserCert.do?user_id="+ID+"&user_name="+name;
}

function delete_temp(ID,create_name){
	if(user_no!='admin'&create_name!='系统管理员'&ID==user_no){
 		alert("当前登录用户不可删除此用户！");
 	}else{
		if(confirm('确认要删除该用户么？')){
			dwr.engine.setAsync(true); //设置方法调用是否同步，false表示同步
			// logDel2("删除用户",ID,name,"删除用户","用户管理");//logOper.js
			LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","用户管理","删除用户:'"+ID+"'成功");//logSys.js
			SysUser.deleteSysUser(ID,callback);
		} 
 	}
}
function callback(data){
	window.location.href="/Seal/general/organise/user/user_manage/query.jsp";
}
var dept_noS="";
var is_juniorS="";
var typeS="";
function zhuxiao_temp(user_id,user_name){
    dept_noS=document.getElementById("dept_noS").value;
    is_juniorS=document.getElementById("is_juniorS").value;
    typeS=document.getElementById("typeS").value;
	if(confirm('确认要注销用户吗?')){
		//alert(dept_no);
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		SysUser.zhuxiaoList(user_id,objUser);
		// logUpd2("注销用户",ID,name,"注销用户","用户管理");//logOper.js		
	}	 
}
function zhuxiao_temp_simple(user_id,create_name){
    dept_noS=document.getElementById("dept_noS").value;
    is_juniorS=document.getElementById("is_juniorS").value;
    typeS=document.getElementById("typeS").value;
    if(user_no!='admin'&create_name!='系统管理员'&user_id==user_no){
 		alert("当前登录用户不可注销此用户！");
 	}else{
		if(confirm('确认要注销用户吗?')){
			//alert(dept_no);
			dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
			SysUser.zhuxiaoSimple(user_id,objUser);
			// logUpd2("注销用户",ID,name,"注销用户","用户管理");//logOper.js		
		}
 	}
}

function objUser(vr){
 if(vr="1"){
  alert("用户已注销!");
  location="serUserManager.do?type="+typeS+"&dept_no="+dept_noS+"&is_junior="+is_juniorS;
 }else{
  alert("用户注销失败！");
  return false;
 }
}
function Jihuo_User(ID,name){
    var type=document.getElementById("typeS").value;
	if(confirm('确认要激活用户吗?')){
		location="JihuoUser.do?user_id="+ID+"&type="+type;
	}; 
}

function Jihuo_User_simple(ID,create_name){
	dept_noS=document.getElementById("dept_noS").value;
    is_juniorS=document.getElementById("is_juniorS").value;
    typeS=document.getElementById("typeS").value;
    var type=document.getElementById("typeS").value;
 	if(user_no!='admin'&create_name!='系统管理员'&ID==user_no){
 		alert("当前登录用户不可激活此用户！");
 	}else{
		if(confirm('确认要激活用户吗?')){
			dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
			SysUser.jihuoSysUserSimple(ID,jihuoUser);
		} 
 	}
}
function jihuoUser(vr){
 if(vr="1"){
  alert("用户已激活!");
  location="serUserManager.do?type="+typeS+"&dept_no="+dept_noS+"&is_junior="+is_juniorS;
 }else{
  alert("用户激活失败！");
  return false;
 }
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
		location="serUserManager.do?type=flag2&&pageIndex="+page1+"&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }";
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
function reset_password(userid,initial_pwd){
if(confirm("此操作不可恢复,确认重置密码?"))
	if(user_no=='admin'){
		SysUser.retPWD(userid,initial_pwd);
		alert("密码重置成功！");
	}else{
		alert("当前登录用户无此权限！！！");
	}
}

function showInf_app(user_id){
		//var url="/Seal/general/organise/user/user_manage/showUser.jsp?userId="+user_id;
		url="/Seal/showUser.do?userId="+user_id;
		var h=120;
		var w=620;
		location.action=newModalDialog(url,w,h); 
}
</script>	
	</head>
	<body class="bodycolor" style="margin-top: 0;">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big">&nbsp;<img src="images/menu/comm.gif" align="absmiddle"><span class="big3"> <a name="bottom">用户列表--[${dept_name}]</span>
    </td>
     <td align="right" style="display: none">
       <input type="text" value="${dept_no}" id="dept_noS">
       <input type="text" value="${is_junior}" id="is_juniorS">
        <input type="text" value="${type}" id="typeS">
		<span class="big3"><a name="bottom" href="serUserManager.do?type=all">[所有用户列表]</a></span>
	</td>
  </tr>
</table>
 <c:if test="${pageSplit.datas=='[]'}">
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
		<c:if test="${pageSplit.datas!='[]'}">
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
					<!-- <td nowrap align="center">
						审批信息
					</td> -->
					<td nowrap align="center">
						备注
					</td>
					<td nowrap align="center">
						用户状态
					</td>
					<td nowrap align="center">
						操作
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				<c:if test="${aa.user_id != 'logger'}">
					<tr class="TableLine1">
						<td align="center">
							${aa.user_id}
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
							${aa.create_name }
						</td>
					    <td align="center">
							${aa.create_data }
						</td>
						<%-- <td align="center">
						  <a href="javascript:;" onclick="showInf_app('${aa.user_id}');flag=true;">查看</a>
						</td> --%>
						
						 <td nowrap align="center">
						 <c:if test="${aa.user_remark!=''}">	
						 <a href="#" onclick="alert('${aa.user_remark }');return false;" title="${aa.user_remark }">详细</a>
						 </c:if>
						 </td>
						 <c:if test="${aa.user_type !='4'}">
						  <td align="center">
							<a style="color:green">正常</a>
						</td>	
						</c:if>
						 <c:if test="${aa.user_type =='4'}">
						  <td align="center">
							<a style="color:red">注销</a>
						</td>	
						</c:if>
						<c:if test="${aa.user_id !='admin' and aa.user_id !='logger'}">
						<td nowrap align="center">
						<c:if test="${aa.user_type !='4'}">					
						<a style="display:none;" href="javascript:;"
									onClick="zhuxiao_temp('${aa.user_id }','${aa.user_name }'); return false;">注销</a>&nbsp;
						<a href="javascript:;"
									onClick="zhuxiao_temp_simple('${aa.user_id }','${aa.user_name }','${aa.create_user }'); return false;">注销</a>&nbsp;
						<a href="javascript:;"
									onClick="edit_temp('${aa.user_id }','${aa.user_name }','${aa.create_user }'); return false;">修改</a>&nbsp;
						<a href="javascript:;"
									onClick="delete_temp('${aa.user_id }','${aa.user_name }','${aa.create_user }');return false;">删除</a>&nbsp;
						<a style="display:none;" href="javascript:;"
									onClick="reset_password('${aa.user_id }','${aa.initial_password }'); return false;">重置密码</a>&nbsp;
						</c:if>	
						<c:if test="${aa.user_type =='4'}">	
						<a style="display:none;" href="javascript:;"
									onClick="Jihuo_User('${aa.user_id }','${aa.user_name }'); return false;">激活</a>&nbsp;
						<a href="javascript:;"
									onClick="Jihuo_User_simple('${aa.user_id }','${aa.user_name }','${aa.create_user }'); return false;">激活</a>&nbsp;
						<a style="display:none" href="javascript:;"
									onClick="showUserCert('${aa.user_id }','${aa.user_name }'); return false;">证书</a>&nbsp;
						</c:if>
						</td>
						</c:if>				
					    <c:if test="${aa.user_id =='admin' or aa.user_id =='logger'}">
					    <td nowrap align="center">
					    <a style="display:none" href="javascript:;"
									onClick="showUserCert('${aa.user_id }','${aa.user_name }'); return false;">证书</a>&nbsp;
						<c:if test="${current_user.user_id=='admin'}">
						<a href="../../Seal/personInfoIndex.do" target="main">帐户信息</a>&nbsp;
						<a style="display:none;" href="javascript:;"
									onClick="reset_password('${aa.user_id }','${aa.initial_password}}'); return false;">重置密码</a>&nbsp;
						</c:if>
		                 </td>
						</c:if>
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
								<a href="serUserManager.do?type=flag2&&pageIndex=1&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">第一页</a>，
										<a
									href="serUserManager.do?type=flag2&&pageIndex=${pageSplit.nowPage-1}&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="serUserManager.do?type=flag2&&pageIndex=${pageSplit.nowPage+1}&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">下一页</a>，
										<a
									href="serUserManager.do?type=flag2&&pageIndex=${pageSplit.totalPage}&&dept_no=${dept_no}&&is_junior=${is_junior}&&user_id=${user_id }&&user_name=${user_name }&&create_name=${create_name }&&start_time=${start_time }&&end_time=${end_time }">最后一页</a>
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
							<input type="button" value="返回查询" class="BigButton"
								onclick="show_sch();" />
						</td>
					</tr>
		</table>
	</body>
</html>
