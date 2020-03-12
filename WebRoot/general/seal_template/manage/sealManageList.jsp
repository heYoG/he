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
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script>
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	


function checkPage(){
	var page=document.getElementById("pageindex");
	var is_junior=document.getElementById("is_junior").value;
	var temp_name=document.getElementById("temp_name").value;
	var start_time=document.getElementById("start_time").value;
	var end_time=document.getElementById("end_time").value;
	var seal_type=document.getElementById("seal_type").value;
	var dept_no=document.getElementById("dept_no").value;
	var page1=page.value.toLow();
	if(page1!=0){
	var reg = /^[0-9]{1,3}$/g;
	if (!reg.test(page1) || eval(page1) > eval("${pageSplit.totalPage }") || page1.value == 0) {
		alert("输入不是合理的页数或超出范围，请重输！");
		page.select();
		page.focus();
	}else{
		location="tempManageList.do?type=flag1&&pageIndex="+page1+"&&is_junior="+is_junior+"&&temp_name="+temp_name+"&&start_time="+start_time+
    "&&end_time="+end_time+"&&seal_type="+seal_type+"&&dept_no="+dept_no;;
	}
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
function create_seal(tempid,tempname){
  if(confirm("确定要制章吗?")){
   LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理","制作印章："+tempname);//logSys.js
   SealTemp.CreateSeal(tempid,user_no,crsObj);
  }
}
function create_sealCZ(tempid,tempname,sealtype){
  if(confirm("确定要制章吗?")){
     var f=document.form1;
     var str=newModalDialog('/Seal/general/seal_template/manage/inCertName.jsp',450,100,f);
     var certAlign=document.getElementById("certAlign").value;
     LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理","制作印章："+tempname);//logSys.js
     SealTemp.CreateSealCZ(tempid,user_no,certAlign,crsObj);
  }
}

function crsObj(data){
	var is_junior=document.getElementById("is_junior").value;
	var temp_name=document.getElementById("temp_name").value;
	var start_time=document.getElementById("start_time").value;
	var end_time=document.getElementById("end_time").value;
	var seal_type=document.getElementById("seal_type").value;
	var dept_no=document.getElementById("dept_no").value;
    location="/Seal/tempManageList.do?type=flag1&&is_junior="+is_junior+"&&temp_name="+temp_name+"&&start_time="+start_time+
    "&&end_time="+end_time+"&&seal_type="+seal_type+"&&dept_no="+dept_no;
}
function del_seal(id,name){  
	var msg= "确定要删除该印模吗？";
	if(window.confirm(msg)){
	     dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	    //logDel2("删除印模",id,name,"删除印模","印模管理");//logOper.js
	     LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理","删除印模："+name);//logSys.js
		 SealTemp.DeleteTeam(id,delObj);
	}
}
function delObj(){
    var is_junior=document.getElementById("is_junior").value;
	var temp_name=document.getElementById("temp_name").value;
	var start_time=document.getElementById("start_time").value;
	var end_time=document.getElementById("end_time").value;
	var seal_type=document.getElementById("seal_type").value;
	var dept_no=document.getElementById("dept_no").value;
	alert("删除成功！");
    location="/Seal/tempManageList.do?type=flag1&&is_junior="+is_junior+"&&temp_name="+temp_name+"&&start_time="+start_time+
    "&&end_time="+end_time+"&&seal_type="+seal_type+"&&dept_no="+dept_no;
}
function createALLSeal(range){   
    var msg= "确定要全部制章吗？";
	if(window.confirm(msg)){
	get_checked();
	var id=document.getElementById("ID").value;
	SealTemp.CreateSeal($("ID").value,user_no,crsObj);
	}
}
function deleteALLSeal(range){   
    var msg= "确定要全部删除吗？";
	if(window.confirm(msg)){
	get_checked();
	var id=document.getElementById("ID").value;
	SealTemp.DeleteTeam($("ID").value,delObj);
	}
}
function check_one(el){
   if(!el.checked)
      document.all("allbox").checked=false;
}
function get_checked(){
  var checked_str="";
  for(i=0;i<document.all("seal_select").length;i++)
  {
      el=document.all("seal_select").item(i);
      if(el.checked){  
         val=el.value;
         checked_str+=val + ",";
      }
  }
  if(i==0){
      el=document.all("seal_select");
      if(el.checked){ 
         val=el.value;
         checked_str+=val + ",";
      }
  }
  document.getElementById("ID").value=checked_str;
}
function check_all(){
 for (i=0;i<document.all("seal_select").length;i++)
 {
   if(document.all("allbox").checked)
      document.all("seal_select").item(i).checked=true;
   else
      document.all("seal_select").item(i).checked=false;
 }

 if(i==0)
 {
   if(document.all("allbox").checked)
      document.all("seal_select").checked=true;
   else
      document.all("seal_select").checked=false;
 }
}
function edit_temp(ID){   
	location="editTemp.do?type=edit&&temp_id="+ID;
}
function showUsers(user_id){
	var url="/Seal/general/log/log_oper/showUser.jsp?userId="+user_id;
	var h=150;
	var w=620;
	newModalDialog(url,w,h);
}
function makeSeal(ID){
 URL="sealBodyAdd.do?temp_id="+ID;
 window.location=URL;
}
</script>
	</head>
	<body class="bodycolor" style="margin-top: 0;">
	<form action="" method="post" name="form1">
	<input type="hidden" id="certAlign" name="certAlign">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal_orig.gif" HEIGHT="20">
					<span class="big3"> <a name="bottom">印模管理 </a> </span>
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
						选
					</td>
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
						状态
					</td>
					<td nowrap align="center">
						操作
					</td>
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
				
					<tr class="TableLine1">
						<td>
							&nbsp;
							<input type="checkbox" name="seal_select" value="${aa.temp_id }"
								onClick="check_one(self);">
						</td>
						<td nowrap align="center">
						${aa.temp_name}
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
					  <c:if test="${aa.approve_text!=''}">
					   <a href="#" onclick="alert('${aa.approve_text }');return false;" title="${aa.approve_text }">详细</a>
					 </c:if>
					 </td> 
					  <c:if test="${aa.is_maked=='4'}">
							<td nowrap align="center">
								<span style="color: red">待制章</span>
							</td>
						</c:if>
						<c:if test="${aa.is_maked=='5'}">
							<td nowrap align="center">
								<span style="color: green">已制章</span>
							</td>
						</c:if>
						<td nowrap align="center">
						<span style="color: gray">${aa.status_name }</span>
						</td>
						<td nowrap align="center">
						<c:if test="${aa.is_maked=='4'}">
						<a href="javascript:;" onClick="show_temp('${aa.temp_data}')">查看</a>&nbsp;
					    <a href="javascript:;" style="display:none" onClick="edit_temp('${aa.temp_id }')">编辑</a>&nbsp;
						<a href="javascript:;" onClick="del_seal('${aa.temp_id }','${aa.temp_name}')">删除</a>
						<a href="javascript:;" onClick="create_seal('${aa.temp_id }','${aa.temp_name}')">制章</a>
						<a href="javascript:;" style="display:none" onClick="create_sealCZ('${aa.temp_id }','${aa.temp_name }','${aa.seal_type }')">财政制章</a>
						<a href="javascript:;" style="display:none" onclick="makeSeal('${aa.temp_id}');">添加区域并提交</a>
						</c:if>
						<c:if test="${aa.is_maked=='5'}">
						<a href="javascript:;" onClick="show_temp('${aa.temp_data }')">查看</a>&nbsp;
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
								<a href="tempManageList.do?type=flag1&&pageIndex=1&&is_junior=${is_junior }&&temp_name=${temp_name }&&start_time=${start_time}
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">第一页</a>，
										<a
									href="tempManageList.do?type=flag1&&pageIndex=${pageSplit.nowPage-1}&&is_junior=${is_junior }&&temp_name=${temp_name }&&start_time=${start_time}
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a href="tempManageList.do?type=flag1&&pageIndex=${pageSplit.nowPage+1}&&is_junior=${is_junior }&&temp_name=${temp_name }&&start_time=${start_time }
    &&end_time=${end_time }&&seal_type=${seal_type}&&dept_no=${dept_no }">下一页</a>，
										<a
									href="tempManageList.do?type=flag1&&pageIndex=${pageSplit.totalPage}&&is_junior=${is_junior }&&temp_name=${temp_name }&&start_time=${start_time }
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
               <tr class="TableControl">
					<td colspan="14">
						<input type="checkbox" name="allbox" id="allbox_for"
							onClick="check_all();">
						<label for="allbox_for">
							全选
						</label>
						&nbsp;
						<form action="">
						<input type="hidden" name="ID" id="ID">
						<input type="hidden" name="is_junior" id="is_junior" value="${is_junior}">
						<input type="hidden" name="start_time" id="start_time" value=${start_time }>
						<input type="hidden" name="end_time" id="end_time" value=${end_time }>
						<input type="hidden" name="dept_no" id="dept_no" value=${dept_no }>
						<input type="hidden" name="seal_type" id="seal_type" value=${seal_type }>
						<input type="hidden" name="temp_name" id="temp_name" value=${temp_name }>
						<input  style="display:none" type="button" class="SmallButton" value="全部制章"
							onClick="createALLSeal('ALL')">
					   <input  type="button" class="SmallButton" value="全部删除"
							onClick="deleteALLSeal('ALL')">
					</td>
				</tr>
			</table>
			</br>
		</c:if>
		<table width="100%" style="margin-top: 0;">
			<tr>
				<td align="center">
					<input type="button" value="返回查询" class="BigButton"
						onclick="window.location='general/seal_template/manage/query/query.jsp'"
						title="返回查询" name="button">
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
		</form>
	</body>
</html>
