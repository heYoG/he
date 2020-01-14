<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新建邮件</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	var saveRet="${saveEmailData}";
	if(saveRet=='0000'){
		alert("邮件发送成功!");
	}else if(saveRet=='0003'){
		alert("邮件发送失败!");
	}
	
	function sendEmai(){
			var addrVal=$("#addresseeID").val();
			var themeVal=$("#themeID").val();
			var textVal=$("#textID").val();
			if(addrVal==''){
				alert("收件人地址不能为空!");
				return false;
			}else if(themeVal==''){
				alert("主题不能为空!");
				return false;
			}else if(textVal==''){
				alert("发送内容不能为空!");
				return false;
			}
			newEmailForm.action="emailAct.action";
			newEmailForm.submit();
	}
	
	function saveToDraft(){
		alert("功能暂未开放!");
	}
</script>
<style type="text/css">
	td{
		text-align:center
	}
</style>
</head>
<body>
	<center>
		<form action="" method="post" name="newEmailForm" enctype="multipart/form-data">
			<table>											
				<tr><td style="padding-left:28px">To:&nbsp;&nbsp;<input type="text" name="addressee" id="addresseeID" placeholder="收件人"></td></tr>
				<tr><td>Theme:&nbsp;&nbsp;<input type="text" name="theme" id="themeID" placeholder="主题"></td></tr>
				<tr><td><hr></td></tr>
				<tr><td>Content:&nbsp;&nbsp;<textarea name="text" id="textID" rows="10" cols="50" placeholder="邮件内容" style="vertical-align:top"></textarea></td></tr>
				<tr><td>Accessory:&nbsp;&nbsp;<input type="file" name="accessoryFile"></td></tr>
				<tr></tr>
				<tr><td nowrap="nowrap"><input type="button" value="发送" onclick="sendEmai()" id="bt">&nbsp;&nbsp;<input type="reset" value="重填">&nbsp;&nbsp;<input type="button" value="保存草稿" onclick="saveToDraft()"></td></tr>
			</table>
		</form>
	</center>
</body>
</html>