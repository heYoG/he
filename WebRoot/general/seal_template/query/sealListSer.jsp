<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>印模查询结果</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/dialog.css">
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script src="/Seal/js/util.js"></script>	
		<script src="js/dialog.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script>
function show_info(ID)
{
 location="getTempInfo.do?temp_id="+ID;
}
function edit_info(ID)
{
	var b=window.showModalDialog("editTempInfo.do?temp_id="+ID);
	if(b!="close"){
		alert("恭喜修改成功！");
		window.location.reload();
	}
}
function del_seal(id)
{
	var msg= "确定要删除该印模吗？";
	if(window.confirm(msg)){
		location="delteTemp.do?temp_id="+id;
		window.location.reload();
	}
}

function checkPage(){
	var page=document.getElementById("pageindex");
	var page1=page.value.toLow();
	var reg = /^[0-9]{1,3}$/g;
	if (!reg.test(page1) || eval(page1) > eval("${pageSplit.totalPage }") || page1.value == 0) {
		alert("输入不是合理的页数或超出范围，请重输！");
		page.select();
		page.focus();
	}else{
		location="tempSearchList.do?type=flag2&&pageIndex="+page1+"&&is_junior=${is_junior}&&temp_name=${temp_name }&&start_time=${start_time }&&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }";
	}
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
function showUsers(user_id){
		var url="/Seal/general/log/log_oper/showUser.jsp?userId="+user_id;
		var h=150;
		var w=620;
		newModalDialog(url,w,h);
	}
</script>
	</head>
	<body class="bodycolor" style="margin-top: 0;">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal_orig.gif" HEIGHT="20">
					<span class="big3"> <a name="bottom">印模查询结果--[${dept_name
							}] </a> </span>
				</td>
				<td align="right" style="display:none">
					<span class="big3"><a name="bottom"
						href="tempSearchList.do?type=all">[所有可查询印模]</a> </span>
				</td>
			</tr>
		</table>
		<c:if test="${pageSplit.datas=='[]' }">
		<table class="MessageBox" align="center" width="280">
        <tr>
          <td class="msg info">
		 <div class="content" style="font-size:12pt">暂无相应印模</div>
		   </td>
        </tr>
         </table>
		</c:if>
		<c:if test="${pageSplit.datas!='[]' }">
			<table class="TableList" width="100%" style="margin-top: 0;">
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
					审批人
				</td>
				<td nowrap align="center">
					审批时间
				</td>
				<td nowrap align="center">
					审批结果
				</td>
				<td nowrap align="center">
					是否制章
				</td>
				<td nowrap align="center">
					审批意见
				</td>
			     <td nowrap align="center">
					操作
				</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
						<tr class="TableLine1">
						<td align="center">
						${aa.temp_name }
						</td>
						<td nowrap align="center">
							${aa.seal_type }
						</td>
						<td align="center">
							${aa.dept_name }
						</td>
						<td align="center">
							<a href='#' onclick="showUsers('${aa.user_apply }');flag=true;" >${aa.user_name }</a>
						</td>
						<td align="center">
						  <a href="#" onclick="alert('${aa.create_time }');return false;" title="${aa.create_time }">详细</a>
						</td>
						<td nowrap align="center">
							<a href='#' onclick="showUsers('${aa.approve_user }');flag=true;" >${aa.approve_user }</a>
						</td>
						<td nowrap align="center">
						<a href="#" onclick="alert('${aa.approve_time }');return false;" title="${aa.approve_time }">详细</a>
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
						
						<c:if test="${aa.is_maked=='4'}">
							<span style="color: red">未制章</span>
						</c:if>
						<c:if test="${aa.is_maked=='5'}">
							<span style="color: green">已制章</span>
						</c:if>
						
						</td>
					<td nowrap align="center">
					 	 <c:if test="${aa.approve_text!=''}">
						    <a href="#" onclick="alert('${aa.approve_text }');return false;" title="${aa.approve_text }">详细</a>
						</c:if>
					 </td> 
						<td nowrap align="center">
							<a href="javascript:;" onClick="show_temp('${aa.temp_data }');return false;">查看</a>&nbsp;
						</td>
					</tr>
				</c:forEach>

				<tr class="TableLine1">
					<td colspan="11" class="pager">
						<div class="pager" align="right">
							共${pageSplit.totalCount }条记录 每页 ${pageSplit.pageSize } 条
							第${pageSplit.nowPage } 页/共${pageSplit.totalPage }页
							<c:if test="${pageSplit.nowPage==1}">
										第一页，上一页，
									</c:if>
							<c:if test="${pageSplit.nowPage!=1}">
								<a href="tempSearchList.do?type=flag2&&pageIndex=1&&is_junior=${is_junior}&&temp_name=${temp_name }&&start_time=${start_time }
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">第一页</a>，
										<a
									href="tempSearchList.do?type=flag2&&pageIndex=${pageSplit.nowPage-1}&&is_junior=${is_junior}&&temp_name=${temp_name }&&start_time=${start_time }
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="tempSearchList.do?type=flag2&&pageIndex=${pageSplit.nowPage+1}&&is_junior=${is_junior}&&temp_name=${temp_name }&&start_time=${start_time }
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">下一页</a>，
										<a
									href="tempSearchList.do?type=flag2&&pageIndex=${pageSplit.totalPage}&&is_junior=${is_junior}&&temp_name=${temp_name }&&start_time=${start_time }
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">最后一页</a>
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
			</br>
<table width="100%" style="margin-top: 0;">
 <tr>
<td  align="center">
<input type="button" value="返回查询" class="BigButton"
onclick="window.location='general/seal_template/query/query.jsp'" title="返回查询" name="button">
	&nbsp;&nbsp;
</td>
</tr>
<tr>
</table>
<div id="overlay"></div>
		<div id="info" class="ModalDialog" style="width: 400px;">
			<div class="header">
				<span class="title">印模信息</span><a class="operation"
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
