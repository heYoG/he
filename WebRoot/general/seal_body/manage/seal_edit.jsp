<%@page contentType="text/html;charset=utf-8" import="java.util.*,com.dj.seal.cert.web.form.CertForm"%>
<%@ include file="../../../inc/tag.jsp"%>
<html>
	<head>
		<title>印章信息修改</title>
	    <base target="_self" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/inc/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<SCRIPT type='text/javascript' src="/Seal/js/Calendar3.js"></script>
	  <script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
         <script src="/Seal/js/util.js"></script>
		<style>
.input_red {
	border: 0;
	border-bottom: 1px red solid;
}
</style>
		<script src="js/utility.js"></script>
		<script Language="JavaScript">
		 var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}	
		
//验证印模名称
function check_name(seal_name)
{	
   if(seal_name=="")
      return ;
     var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
   if(!pattern.test(document.form1.seal_name.value)){
     alert("印模名称是以中文,英文,数字!");
     return false;
   }
   if(seal_name != '${seal.seal_name }'){
  	 document.getElementById("seal_name_msg").innerHTML="<img src='images/loading_16.gif' align='absMiddle'> 检查中……";
   // SealTemp.isExistTempName(temp_name,function(show_msg){
   //	 	alert(show_msg);
   // });
   
   	 SealBody.isExistSealName(seal_name,show_msg);
   }else{
   		document.getElementById("seal_name_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		name_exist=false;
   }
  
}

function show_msg(req)
{
	if(req==1)
	{
		document.getElementById("seal_name_msg").innerHTML="<img src='images/error.gif' align='absMiddle'>名称已存在";
		name_exist=true;
	} 
	else{
		document.getElementById("seal_name_msg").innerHTML="<img src='images/correct.gif' align='absMiddle'>";
		name_exist=false;
	}
}		
function CheckForm(flag)
{
 //  if(document.form1.seal_name.value=="")
 //   {
 //   alert("印章名称不能为空！");
//    return false;
//    }
	//var seal_name=document.form1.seal_name.value;
	var seal_id=document.form1.seal_id.value;
	//var dept_no=document.form1.dept_no.value;
	var cert_no=document.form1.key_no.value;
   dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
   LogSys.logAdd("${current_user.user_id}","${current_user.user_name}","${user_ip}","印章管理","绑定证书:"+seal_id+"为："+document.form1.key_no.value);//logSys.js
   SealBody.updateSealBody(cert_no,seal_id);
   location="sealShowmanage.do?type=flag1&&is_junior=${is_junior}&&seal_name=${seal_name1 }&&approve_begintime=${approve_begintime }&&approve_endtime=${approve_endtime }&&seal_type=${seal_type}&&dept_no=${dept_no1 }";
}
//部门树

function openmodwin() {
	var b = window.showModalDialog("/Seal/depttree/new_dept_tree.jsp?req=dept_choose&&user_no=${current_user.user_id }",form1);
}
</script>
	</head>

	<body class="bodycolor" topmargin="5">

		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="images/edit.gif" align="absmiddle">
					<span class="big3"> 印章信息修改</span>
				</td>
			</tr>
		</table>

		<form 
			method="post" name="form1">
			<table class="TableBlock" align="center" width="80%">
				<%-- <c:if test="${sealtype=='1'}"> --%>
				<tr>
					<td nowrap class="TableContent" width="80">
						印章绑定证书:
					</td>
					
					<td class="TableData">
					<select name="key_no" id="key_no"> 
					  <% 
					  String cert_no=request.getAttribute("cert_no").toString();
					  String cert_name=request.getAttribute("cert_name").toString();
					  %>
					  <option value="<%=cert_no%>"><%=cert_name%></option> 
					  <%
	                  List<CertForm> c_list=(List<CertForm>)request.getAttribute("c_list");	              
	                  for(CertForm cert:c_list){   
	                  if(!cert_no.equals(cert.getCert_no())){
	                  %>
	                   <option value="<%=cert.getCert_no()%>"><%=cert.getCert_name()%></option> 
	                   <%}}
	                  if(!cert_no.equals("")){%>
	                   <option value=""></option> 
	                  <% }%>
	                </select> 	                   	 				
					</td>
				</tr>
				<%-- </c:if> --%>
				<tr class="TableControl">
					<td nowrap colspan="4" align="center">
					    <input type="hidden" name="seal_status" value="1">
						<input type="hidden" name="seal_id" value="${seal.seal_id}">
						<input type="hidden" name="seal_name2" id="seal_name2" value="${seal.seal_name}">
						<input type="hidden" name="client_system" id="client_system">
						<input type="button" value="保存" class="BigButton"
							onclick="CheckForm('0');">
						&nbsp
						<input type="button" value="返回" class="BigButton"
							onclick="history.back(-1)">
					</td>
				</tr>
			</table>
		</form>

	</body>
</html>