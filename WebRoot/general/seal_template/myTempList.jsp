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
		<title>我的印模</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/dialog.css">
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script>
function show_temp(data){
	if(data!=""){
		//document.getElementById("info_body").innerHTML="<img src='data:image/bmp;base64,"+data+"'/>";
		var obj = document.getElementById("DMakeSealV61");
		var l = obj.LoadData(data);
		var vID = 0;
		vID = obj.GetNextSeal(vID);
		obj.SelectSeal(vID);
		ShowDialog("apply");
	}else{
		alert("无图片信息!");
	}
}

function edit_temp(ID){
	location="editTempReg.do?temp_id="+ID;
}

function delete_temp(ID){
	if(confirm('确认要删除该印模申请么？')){
		//alert(dept_no);
		location="deleteTempReg.do?temp_id="+ID;
	} 
}
function checkPage(){
	var page=document.getElementById("pageindex");
	var reg = /^[0-9]{1,3}$/g;
	if (!reg.test(page.value) || eval(page.value) > eval("${pageSplit.totalPage }") || page.value == 0) {
		alert("输入不是合理的页数或超出范围，请重输！");
		page.select();
		page.focus();
	}else{
		location="mytempReg.do?pageIndex="+page.value;
	}
}

</script>
	</head>
	<body class="bodycolor" topmargin="5">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal_orig.gif">
					<span class="big3"> 我的印模</span>
				</td>
			</tr>
		</table>
		<c:if test="${pageSplit.datas!='[]' }">
			<table class="TableList" width="95%">
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
						申请时间
					</td>
					<td nowrap align="center">
						图片尺寸(mm)
					</td>
					<td nowrap align="center">
						印章大小(mm)
					</td>
					<td nowrap align="center">
						色彩位数
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
						<td nowrap align="center">
							${aa.create_time }
						</td>
						<td nowrap align="center">
							${aa.image_width }×${aa.image_height }
						</td>
						<td nowrap align="center">
							${aa.seal_width }×${aa.seal_height }
						</td>
						<td nowrap align="center">
							${aa.bit_name }
						</td>
						<td nowrap align="center">
					 	 <c:if test="${aa.approve_text!=''}">
						    <a href="#" onclick="alert('${aa.approve_text }');return false;" title="${aa.approve_text }">详细</a>
						</c:if>
					 </td> 
						<td nowrap align="center">
							<a href="javascript:;" onClick="show_temp('${aa.temp_data }');">查看</a>&nbsp;
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
								<a href="mytempReg.do?pageIndex=1">第一页</a>，
										<a href="mytempReg.do?pageIndex=${pageSplit.nowPage-1}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a href="mytempReg.do?pageIndex=${pageSplit.nowPage+1}">下一页</a>，
										<a href="mytempReg.do?pageIndex=${pageSplit.totalPage}">最后一页</a>
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
		<table class="MessageBox" align="center" width="290">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						共有印模 ${pageSplit.totalCount } 个！
					</div>
				</td>
			</tr>
		</table>

		<div id="overlay"></div>
		<div id="apply" class="ModalDialog" style="width: 400px;">
			<div class="header">
				<span id="title" class="title">印模信息</span><a class="operation"
					href="javascript:HideDialog('apply');"><img
						src="images/close.png" /> </a>
			</div>
			<div id="info_body" class="body" align="center">
					<%@include file="../../inc/makeSealObject.jsp"%>
			</div>
			<div id="footer" class="footer">
				<input class="BigButton" onclick="HideDialog('apply')" type="button"
					value="关闭" />
			</div>
		</div>
	</body>
</html>
