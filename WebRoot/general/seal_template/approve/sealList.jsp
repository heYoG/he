<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>
		<title>待批印模列表</title>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/dialog.css">
		<script src="js/utility.js"></script>
		<script src="js/dialog.js"></script>
        <script src="/Seal/js/util.js"></script>
        <script type='text/javascript' src='/Seal/dwr/interface/SealTemp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/String.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script>
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
function check_all()
{
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

function check_one(el)
{
   if(!el.checked)
      document.all("allbox").checked=false;
}
function get_checked()
{
  checked_str="";
  for(i=0;i<document.all("seal_select").length;i++)
  {

      el=document.all("seal_select").item(i);
      if(el.checked)
      {  val=el.value;
         checked_str+=val + ",";
      }
  }

  if(i==0)
  {
      el=document.all("seal_select");
      if(el.checked)
      {  val=el.value;
         checked_str+=val + ",";
      }
  }
  return checked_str;
}
function approve(ID,flag,range)
{   
   //  alert(flag);
	if(flag==2){
	  document.form1.leixing.value="批准印模";
	  var msg="确认要批准印模申请吗？请填写批准意见："
	}  
	if(flag==3)
	{
	  document.form1.leixing.value="退回修改印模";
	  var msg="确认要退回印模申请吗？请填写退回意见：";
	}
	 $("confirm").innerHTML="<font color=red>"+msg+"</font>";
	if(range=="ALL") {
		if(get_checked()==""){
			alert("请先选择您要操作的印模！");
			return;
		   }
	     $("ID").value = get_checked();
	}
	else{
	  $("ID").value=ID+",";
	 }
    $("STATUS").value=flag;
    ShowDialog('approve'); 
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

function doButton(){
  if($("content").innerText=="")
	{
		alert("请填写审批意见！");
		return(false);
	}else{
	  var approve_text=document.form1.approve_text.value;
      if(approve_text.length > 20)
      {
       alert("输入的理由长度不能超过20个字符!");
       return false;
     }else{
       approve_text=approve_text.replace(/\'/g,"‘");
       document.form1.approve_text.value=approve_text;
   } 	
    //  return(true);
   }
   var tempid=document.getElementById("ID").value;
  SealTemp.showTempByTemp_id(tempid,callback); 
}
function callback(temp_name){
   var tempid=document.getElementById("ID").value;
   var lx=document.getElementById("leixing").value;
   dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
 //   logsPl(lx,tempid,temp_name,"","印模管理");//logOper.js
  LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印模管理",lx+temp_name);//logSys.js
   document.form1.submit();
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
		location="tempAppList.do?pageIndex="+page1+"&&dept_no=${dept.dept_no }";
	}
	}
}
</script>
	</head>

	<body class="bodycolor" topmargin="0">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/menu/seal_orig.gif" align="absmiddle">
					<span class="big3"> <a name="bottom">待批印模列表 </a>
					</span>
				</td>
				<td align="right">
				
				<br></td>
			</tr>
		</table>

		<c:if test="${pageSplit.datas!='[]' }">

			<table class="TableList" width="100%">
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
						尺寸(mm)
					</td>
					<td nowrap align="center">
						大小(mm)
					</td>
					<td nowrap align="center">
						色彩位数
					</td>
					<td nowrap align="center">
						备注
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
							${aa.user_name }
						</td>
						<td align="center">
							<fmt:formatDate value="${aa.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
					    <c:if test="${aa.temp_remark!=''}">
						 <a href="#" onclick="alert('${aa.temp_remark }');return false;" title="${aa.temp_remark }">详细</a> 
						 </c:if>
					 </td> 
						<td nowrap align="center">
							<a href="javascript:;" onClick="show_temp('${aa.temp_data }')">查看</a>&nbsp;
							<a href="javascript:approve('${aa.temp_id }','2');">批准</a>&nbsp;
							<a href="javascript:approve('${aa.temp_id }','3');">退回</a>
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
								<a href="tempAppList.do?pageIndex=1&&dept_no=${dept.dept_no }">第一页</a>，
										<a
									href="tempAppList.do?pageIndex=${pageSplit.nowPage-1}&&dept_no=${dept.dept_no }">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="tempAppList.do?pageIndex=${pageSplit.nowPage+1}&&dept_no=${dept.dept_no }">下一页</a>，
										<a
									href="tempAppList.do?pageIndex=${pageSplit.totalPage}&&dept_no=${dept.dept_no }">最后一页</a>
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
					<td colspan="11">
						<input type="checkbox" name="allbox" id="allbox_for"
							onClick="check_all();">
						<label for="allbox_for">
							全选
						</label>
						&nbsp;
						<input type="button" class="SmallButton" value="批准"
							onClick="approve('',2,'ALL')">
						<input type="button" class="SmallButton" value="拒绝"
							onClick="approve('',3,'ALL')">
					</td>
				</tr>

			</table>
		</c:if>
		<table class="MessageBox" align="center" width="350">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						共有待审批印模 ${pageSplit.totalCount } 个！
					</div>
				</td>
			</tr>
		</table>
		<div id="overlay"></div>
		<div id="approve" class="ModalDialog" style="width: 500px;">
			<div class="header">
				<span class="title">审批意见</span><a class="operation"
					href="javascript:HideDialog('approve');"><img
						src="images/close.png" /> </a>
			</div>
			<form name="form1" method="post" action="tempApp.do" onsubmit="return doButton();" >
				<div id="approve_body" class="body">
					<span id="confirm"></span>
					<textarea id="content" name="approve_text" cols="60" rows="5"
						style="overflow-y: auto;" class="BigInput" wrap="yes"></textarea>
					<br>
					&nbsp;&nbsp;
				</div>
				<input type="hidden" name="leixing" id="leixing">
				<input type="hidden" name="ID" id="ID">
				<input type="hidden" name="temp_status" id="STATUS">
				<div id="footer" class="footer">
					<input class="BigButton" type="button" onclick="doButton()" value="确定" />
					<input class="BigButton" onclick="HideDialog('approve')"
						type="button" value="关闭" />
				</div>
			</form>
		</div>
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

