<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<%
	//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
	System.out.println("basePath:" + basePath1);
%>
<html>
	<head>
		<title>印章列表</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="/js/ccorrect_btn.js"></script>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/dialog.css">
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSrv.js'></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script>
var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
function show_seal(data){
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


/*写入key*/
function write_key(SID)
{
  URL="writeKey.do?seal_id="+SID;
  myleft=(screen.availWidth-300)/2;
  mytop=200
  mywidth=300;
  myheight=150;
  window.open(URL,"","height="+myheight+",width="+mywidth+",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");

}
function del_seal(id)
{
	var msg= "确定要删除该印章吗？";
	if(window.confirm(msg))
	   location="sealDelete.do?seal_id="+id+"&&type=${type}&&pageIndex=${pageSplit.nowPage}";
}
function update_seal(id)
{
	   location="sealEditGuide.do?seal_id="+id+"&&type=${type}&&pageIndex=${pageSplit.nowPage}";
}
function show_seal2(id)
{
	   location="sealEditGuide7.do?seal_id="+id;
}
function shenqing(seal_id,seal_name){
       dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	   logUpd2("申请印章操作",seal_id,seal_name,"申请印章是:"+seal_name,"印章管理");//logOper.js
       location="shenqingSeal.do?type=index&&seal_id="+seal_id+"&&seal_name="+seal_name;
}
function qxshenqing(seal_id,seal_name){
       var msg= "确定要取消该印章申请吗?";
	   if(window.confirm(msg)){
	    dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	    logUpd2("取消申请印章操作",seal_id,seal_name,"取消申请印章是:"+seal_name,"印章管理");//logOper.js
       location="shenqingSeal.do?type=quxiao&&seal_id="+seal_id;
       }
}
function refuse_liyou(seal_id){
    var time =new Date();
    var str="shenqingSeal.do?type=refuseliyou&&sealid="+seal_id+"&&time="+time;
    window.showModalDialog(str, null, "status:no;center:yes;scroll:yes;resizable:yes;help:no;dialogWidth:"+300+"px;dialogHeight:"+200+"px");
}
function getInfo(seal_id){
 
 location="getInfoSeal2.do?seal_id="+seal_id;
}
function editRole(id) {

    location="SearchRoleinc2.do?roleid="+id;
   //  newModalDialog(str,650,400,null);
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
		location="sealShow.do?type=flag2&&pageIndex="+page1+"&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }&&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }";
	}
	}
}
function getInfo(seal_id,seal_name){
 
 location="getInfoSeal.do?seal_id="+seal_id+"&&seal_name="+seal_name;
}

function showUsers(user_id){
		var url="/Seal/general/log/log_oper/showUser.jsp?userId="+user_id;
		var h=150;
		var w=620;
		newModalDialog(url,w,h);
	}
</script>
	</head>

	<body class="bodycolor" topmargin="0">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					&nbsp;
					<img src="images/menu/seal.gif" align="absmiddle">
					<span class="big3"> <a name="bottom">印章列表--[${dept_name}]
					</span>
				</td>
				<td align="right" style="display: none">
					&nbsp;
					<span class="big3"><a name="bottom"
						href="sealShow2.do?type=all">[所有印章列表]</a>
					</span>
					<br>
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
			<form action="sealShow.do?type=flag2" name="form1" method="post">
				<table class="TableList" width="100%">
					<tr class="TableHeader">

						<td nowrap align="center">
							印章名称
						</td>
						<td nowrap align="center">
							类型
						</td>
						<td nowrap align="center">
							部门
						</td>
						<td nowrap align="center">
							制章人
						</td>
						<td nowrap align="center">
							制章时间
						</td>
					
						<td nowrap align="center">
							使用状态
						</td>
						<td nowrap align="center">
							操作
						</td>
					</tr>
					<c:forEach var="seal" items="${pageSplit.datas }"
						varStatus="status">
						<tr class="TableLine1">
							<td nowrap align="center">
								<a href="javascript:;"
									onclick="getInfo('${seal.seal_id }','${seal.seal_name }');return false;">${seal.seal_name
									}</a>
							</td>
							<td nowrap align="center">
								${seal.seal_type }
							</td>
							<td nowrap align="center">
								${seal.dept_name }
							</td>
							<td nowrap align="center">
								<a href='#' onclick="showUsers('${seal.seal_creator }');flag=true;" >${seal.create_name }</a>
							</td>
							<td nowrap align="center">
								<a href="#"
									onclick="alert('${seal.create_time }');return false;"
									title="${seal.create_time }">详细</a>
							</td>
							
							<td nowrap align="center">
								<c:if test="${seal.seal_status==5}">
									<font color=green>正常</font>
								</c:if>
								<c:if test="${seal.seal_status==6}">
									<font color=red>失效</font>
								</c:if>
								<c:if test="${seal.seal_status==7}">
									<font color=blue>已注销</font>
								</c:if>
							</td>
							<td nowrap align="center">
								<c:if test="${seal.seal_status==5}">
									<a href="javascript:;" onClick="show_seal('${seal.seal_data}');return false;">查看</a>&nbsp;
       </c:if>
							</td>
						</tr>
					</c:forEach>
					<tr class="TableLine1">
						<td colspan="14" class="pager">
							<div class="pager" align="right">
								共${pageSplit.totalCount }条记录 每页 ${pageSplit.pageSize } 条
								第${pageSplit.nowPage } 页/共${pageSplit.totalPage }页
								<c:if test="${pageSplit.nowPage==1}">
										第一页，上一页，
									</c:if>
								<c:if test="${pageSplit.nowPage!=1}">
									<a
										href="sealShow.do?type=flag2&&pageIndex=1&&is_junior=${is_junior}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">第一页</a>，
										<a
										href="sealShow.do?type=flag2&&pageIndex=${pageSplit.nowPage-1}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">上一页</a>，
									</c:if>
								<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
								<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
									<a
										href="sealShow.do?type=flag2&&pageIndex=${pageSplit.nowPage+1}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">下一页</a>，
										<a
										href="sealShow.do?type=flag2&&pageIndex=${pageSplit.totalPage}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">最后一页</a>
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
			</form>
		</c:if>
		</br>
		<table width="100%" style="margin-top: 0;">
			<tr>
				<td align="center">
					<input type="button" value="返回查询" class="BigButton"
						onclick="window.location='general/seal_body/query/query.jsp'"
						title="返回查询" name="button">
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
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
