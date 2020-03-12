<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>新建用户</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link rel="stylesheet" type="text/css"
			href="../theme/2/style.css">
		<script src="../js/utility.js"></script>
		<script src="../inc/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
        <script src="/Seal/js/util.js"></script>
		<script Language="JavaScript">
//获得部门的js

function openmodwin2() {
	var b = window.showModalDialog("/Seal/general/dept_tree3.jsp?req=seal_temp3&&user_no=admin",form1);
}

//用户验证
           function check_user()
      		{
      		var userId=document.getElementById("user_id").value;
            if(userId=="")
            return;
            document.getElementById("user_id_msg").innerHTML="<img src='../images/loading_16.gif' align='absMiddle'> 检查中，请稍候……";
			SysUser.isExistUser(userId,callback)
		    }
		  function callback(data)
		    {  	
		    if(data==1)
		    {
		    document.getElementById("user_id_msg").innerHTML="<img src='../images/error.gif' align='absMiddle'> 该用户名已存在";
		    document.getElementById("user_id").value="";
		    } 
		     else
		     document.getElementById("user_id_msg").innerHTML="<img src='../images/correct.gif' align='absMiddle'>";
		   }
		
function CheckForm()
{
   if(document.form1.user_id.value=="")
   { alert("用户名不能为空！");
     return (false);
   }

   if(document.form1.dept_no.value=="")
   { alert("部门不能为空！");
     return (false);
   }

   if(document.form1.user_name.value=="")
   { alert("真实姓名不能为空！");
     return (false);
   }
  
   if(document.form1.user_email.value!="")
   {
   	 var emailExp = /[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+\.[a-zA-Z]{2,4}/;
   	 if(!document.form1.user_email.value.match(emailExp))
   	 {
   	 	  alert("请输入有效的E-mail地址！");
        return (false);  
     }
   }
}
//日期控件
function GetDate(nText) {
	reVal = window.showModalDialog("showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].user_birth.value = reVal;
		} else {
			if (nText == 2) {
				document.forms[0].checkTime.value = reVal;
			}
		}
	}
}
function getCertInfo()
{
	var obj = document.getElementById("DMakeSealV61");
	if(!obj){
		alert("控件装载失败，请先安装控件!")
		window.close();
		return false;
	}
	document.form1.key_sn.value=obj.SerialNumber;
	document.form1.VERIFY_DATA.value=obj.GetCardCert();
}
</script>
	</head>
	<body class="bodycolor" topmargin="5"
		onload="document.form1.user_id.focus();getCertInfo();">
		<div style="display: none">
			<OBJECT id=DMakeSealV61
				classid="clsid:3F1A0364-AD32-4E2F-B550-14B878E2ECB1" VIEWASTEXT
				width=250 height=200
				codebase='/module/seal_maker/MakeSealV6.ocx#version=1,0,1,6'>
				<PARAM NAME="_Version" VALUE="65536">
				<PARAM NAME="_ExtentX" VALUE="2646">
				<PARAM NAME="_ExtentY" VALUE="1323">
				<PARAM NAME="_StockProps" VALUE="0">
			</OBJECT>
		</div>
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="../images/notify_new.gif" align="absmiddle">
					<span class="big3"> 用户注册</span>
				</td>
			</tr>
		</table>
		<form action="../regist.do" method="post" name="form1"
			onsubmit="return CheckForm();">
			<table class="TableBlock" width="95%" align="center">

				<tr>
					<td nowrap class="TableContent">
						证书序列号：
						<span style="color: red;">*</span>
					</td>
					<td nowrap class="TableData">
						<input type="text" readonly name="key_sn" class="BigStatic"
							size="10" id="key_sn" value="">
						&nbsp;
						<input type="button" class="SmallButton" value="获取证书信息"
							onclick="getCertInfo();">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width="120">
						用户名：
						<span style="color: red;">*</span>
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_id" class="BigInput" size="10"
							maxlength="20" onblur="check_user(this.value)">
						&nbsp;
						<span id="user_id_msg"></span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						真实姓名：
						<span style="color: red;">*</span>
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_name" class="BigInput" size="10"
							maxlength="30">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						密码：
						<span style="color: red;">*</span>
					</td>
					<td nowrap class="TableData">
						<input type="password" name="user_psd" class="BigInput" size="10"
							maxlength="10">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						部门：
						<input type="hidden" name="dept_no" />
					</td>
					<td class="TableData">
						<input type="text" name="dept_name" readonly="readonly" />
						<input type="button" value="选 择" class="SmallButton"
							onclick="return openmodwin2();" title="选择部门">
					</td>
				</tr>
				<tr>
				<tr>
					<td nowrap class="TableContent">
						性别：
					</td>
					<td nowrap class="TableData">
						<select name="user_sex" class="BigSelect">
							<option value="0">
								男
							</option>
							<option value="1">
								女
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						生日：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_birth" size="10" maxlength="23"
							class="BigInput" onfocus="this.blur()">
						<img onclick="GetDate(1);" src="../images/654.gif"
							style="height: 20; cursor: hand" border="0" />
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						手机：
					</td>
					<td class="TableData">
						<input type="text" name="mobil_no" size="16" maxlength="23"
							class="BigInput">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						单位电话：
					</td>
					<td class="TableData">
						<input type="text" name="tel_no_dept" size="16" maxlength="23"
							class="BigInput">
					</td>
				</tr>


				<tr>
					<td nowrap class="TableContent">
						电子邮件：
					</td>
					<td class="TableData">
						<input type="text" name="user_email" size="25" maxlength="50"
							class="BigInput">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent">
						备注：
					</td>
					<td nowrap class="TableData">
						<textarea name="user_remark" class="BigInput" cols="50" rows="2"></textarea>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableControl" colspan="2" align="center">
						<input type="hidden" name="VERIFY_DATA">
						<input type="submit" value="提 交" class="BigButton" title="用户注册"
							name="button">
						&nbsp;&nbsp;
						<input type="button" value="关 闭" class="BigButton" title="关闭窗口"
							onclick="window.close();">
					</td>
				</tr>

			</table>
		</form>
	</body>
</html>