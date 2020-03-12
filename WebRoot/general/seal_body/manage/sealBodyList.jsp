<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<title>印章管理</title>
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/dialog.css">
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script>
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
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
function update_temp(seal_id){
	var page=document.getElementById("pageindex");
	location="sealEditGuide.do?seal_id="+seal_id+"&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }&&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }";
}

function set_prv(sealid){
 location="sealSetGuide.do?seal_id="+sealid;
}
function write_key(seal_id,seal_name){
 var str="/Seal/general/seal_body/manage/write_key.jsp?seal_id="+seal_id+"&&seal_name="+seal_name;
 newModalDialog(str,300,200,null);
}
function edit_cert(seal_id){
	
	location="cerModeList.do?seal_id="+seal_id;
	
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
		location="sealShowmanage.do?type=flag1&&pageIndex="+page1+"&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }&&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }";
	}
	}
}
function showUsers(user_id){
		var url="/Seal/general/log/log_oper/showUser.jsp?userId="+user_id;
		var h=150;
		var w=620;
		newModalDialog(url,w,h);
}
function addAreaAn(seal_id){
    SealBody.getSealBodyID(seal_id,seal);
}
function seal(d){
	 //alert("load MakeSeal");
     var obj = document.getElementById("DMakeSealV61");
     var l=obj.LoadData(d.seal_data);
	 var vID = 0; 
	 vID = obj.GetNextSeal(vID);
	 obj.SelectSeal(vID);
	 obj.ShowDialog(2,0);
	 var seal_data= obj.SaveData();//设置印章数据
	  if(d.seal_data.length!=seal_data.length){
	  SealBody.saveSealData(d.seal_id,seal_data,objsave);
	 } 
}
function objsave(){
   LogSys.logAdd(user_no,user_name,user_ip,"印章管理","标记印章添加成功");
   alert("标记印章添加成功");
}
function addKeyWord(seal_id){
    var f=document.form1;
    var str=newModalDialog('/Seal/general/seal_body/manage/addKeyWord.jsp',450,100,f);
    var key_word=document.getElementById("key_word").value;
    if(key_word!=""){
     SealBody.upKeyWord(seal_id,key_word,objKey);
    }
    //alert("标记印章添加成功");
}
function objKey(){
alert("添加关键字成功！");
LogSys.logAdd(user_no,user_name,user_ip,"印章管理","添加关键字成功");
}
function show_keyword(seal_id){
SealBody.getSealBodyID(seal_id,getKeyWord);
}
function getKeyWord(d){
alert(d.key_words);
}
var sealName;
function exportSeal(seal_id,seal_Name){
 sealName=seal_Name;
 SealBody.exportSeal(seal_id,objexport);
}
function objexport(s){
  if(s=="1"){
    alert("导出印章数据成功！");
    var basepath="<%=basePath%>";
    window.location.href=basepath+"doc/downloadSeal.jsp?name="+sealName+".xml";
  }else{
   alert("导出印章数据失败！");
  }
}
function del_seal(id,name){  
	var msg= "确定要删除该印章吗？";
	if(window.confirm(msg)){
	     dwr.engine.setAsync(false); //设置方法调用是否异步，false表示同步
	     LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印章管理","删除印章："+name);//logSys.js
		 SealBody.deleteSeal(id,name,delObj);
	}
}
function delObj(){
 window.location.href="/Seal/sealShowmanage.do?type=flag1";
}

function logOff(id){
	var msg="确定要注销此印章吗？";
	if(window.confirm(msg)){
		dwr.engine.setAsync(false);
		LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印章管理","注销印章："+id);//logSys.js
		SealBody.logOff(id,logOffObj);
	}
}
function logOffObj(data){
	if(data=="1"){
		alert("注销成功！");
	}else {
		alert("注销失败！");
	}
	location="sealShowmanage.do?type=flag1&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }&&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }";
}

function activate(id){
	var msg="确定要激活此印章吗？";
	if(window.confirm(msg)){
		dwr.engine.setAsync(false);
		LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印章管理","激活印章："+id);//logSys.js
		SealBody.activate(id,activateObj);
	}
}
function activateObj(data){ 
	if(data=="1"){
		alert("激活成功！");
	}else {
		alert("激活失败！");
	}
	location="sealShowmanage.do?type=flag1&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }&&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }";
}
function getInfo(seal_id,seal_name){
 
 location="getInfoSeal.do?seal_id="+seal_id+"&&seal_name="+seal_name;
}

</script>
	</head>
	<body class="bodycolor" style="margin-top: 0;">
		<form action="" method="post" name="form1">
			<input type="hidden" name="seal_data" id="seal_data" value="">
			<input type=hidden name="key_word" id="key_word" value="">
			<table border="0" width="100%" cellspacing="0" cellpadding="3"
				class="small">
				<tr>
					<td class="Big">
						<img src="images/menu/seal.gif" HEIGHT="20">
						<span class="big3"> <a name="bottom">印章管理 </a> </span>
					</td>
				</tr>
			</table>
			<c:if test="${pageSplit.datas=='[]' }">
				<table class="MessageBox" align="center" width="280">
					<tr>
						<td class="msg info">
							<div class="content" style="font-size: 12pt">
								暂无相应印章
							</div>
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${pageSplit.datas!='[]' }">
				<table class="TableList" width="100%" style="margin-top: 0;">
					<tr class="TableHeader">
						<td nowrap align="center" id="seal_name">
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
						<!-- <td nowrap align="center">
						关键字
					   </td> -->
						<td nowrap align="center">
							是否绑定证书
						</td>
						<td nowrap align="center">
							操作
						</td>
					</tr>

					<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
						<input type="hidden" name="is_junior" id="is_junior"
							value="${is_junior}">
						<tr class="TableLine1">
							<td nowrap align="center">
								<a href="javascript:;"
									onclick="getInfo('${aa.seal_id }','${aa.seal_name }');">${aa.seal_name
									}</a>
							</td>
							<c:if test="${aa.seal_type=='公章-法人章'}">
								<td nowrap align="center">
									公章-法人章
								</td>
							</c:if>
							<c:if test="${aa.seal_type=='普通公章'}">
								<td nowrap align="center">
									普通公章
								</td>
							</c:if>
							<c:if test="${aa.seal_type=='冻结印章'}">
								<td nowrap align="center">
									查控业务章
								</td>
							</c:if>
					
							<td align="center">
								${aa.dept_name }
							</td>
							<td align="center">
								<a href='#'
									onclick="showUsers('${aa.seal_creator }');flag=true;">${aa.create_name
									}</a>
							</td>
							<td align="center">
								<a href="#" onclick="alert('${aa.create_time }');return false;"
									title="${aa.create_time }">详细</a>
							</td>
							<td nowrap align="center">
								<span style="color: green">已制章</span>
							</td>
							<c:if test="${aa.seal_status=='5'}">
								<td nowrap align="center">
									<span style="color: green">正常</span>
								</td>
							</c:if>
							<c:if test="${aa.seal_status=='7'}">
								<td nowrap align="center">
									<span style="color: red">已注销</span>
								</td>
							</c:if>
							<c:choose>
								<c:when test="${aa.key_sn==''}">
									<td nowrap align="center">
										<span style="color: red">未绑定证书</span>
									</td>
								</c:when>
								<c:otherwise>
									<td nowrap align="center">
										<span style="color: green">已绑定证书</span>
									</td>
								</c:otherwise>
							</c:choose>
							 <%-- <td align="center">
						      <a href="javascript:;" onClick="show_keyword('${aa.seal_id }')">查看</a>&nbsp;
						    </td> --%>
							<td nowrap align="center">
								<a href="javascript:;" onClick="show_temp('${aa.seal_data }');return false;">查看</a>&nbsp;
								<a href="javascript:;" onClick="update_temp('${aa.seal_id }');return false;">绑定证书</a>&nbsp;
								<a href="javascript:;" onClick="del_seal('${aa.temp_id }','${aa.seal_name}');return false;">删除</a>
								<%-- <a
									href="javascript:;" onClick="write_key('${aa.seal_id }','${aa.seal_name }');return false;">写入key</a>&nbsp;
								<a href="javascript:;" onClick="set_prv('${aa.seal_id}');return false;">设置权限</a>&nbsp; --%>
								<c:if test="${aa.seal_status=='5'}">
									<a href="javascript:;" onClick="logOff('${aa.seal_id}');return false;">注销印章</a>&nbsp;
								</c:if>
								<c:if test="${aa.seal_status=='7'}">
									<a href="javascript:;" onClick="activate('${aa.seal_id}');return false;">激活印章</a>&nbsp;
								</c:if>
								<a href="javascript:;" onClick="addAreaAn('${aa.seal_id}')">添加标记</a>&nbsp;
								<%-- <a href="javascript:;" onClick="addKeyWord('${aa.seal_id}')">设置关键字</a>&nbsp; --%>
								<a href="javascript:;" style="display: none"
									onClick="exportSeal('${aa.seal_id}','${aa.seal_name}')">导出印章数据</a>&nbsp;
								<a href="javascript:void" style="display:none">凭证</a>
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
									<a
										href="sealShowmanage.do?type=flag1&&pageIndex=1&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">第一页</a>，
										<a
										href="sealShowmanage.do?type=flag1&&pageIndex=${pageSplit.nowPage-1}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">上一页</a>，
									</c:if>
								<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
								<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
									<a
										href="sealShowmanage.do?type=flag1&&pageIndex=${pageSplit.nowPage+1}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
    &&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no }">下一页</a>，
										<a
										href="sealShowmanage.do?type=flag1&&pageIndex=${pageSplit.totalPage}&&is_junior=${is_junior}&&seal_name=${seal_name }&&approve_begintime=${approve_begintime }
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
				</br>
			</c:if>
			<table width="100%" style="margin-top: 0;">
				<tr>
					<td align="center">
						<input type="button" value="返回查询" class="BigButton"
							onclick="window.location='sealManage.do'" title="返回查询"
							name="button">
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
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
		</form>
	</body>
</html>
