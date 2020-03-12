<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发送公告</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	var saveRet="${saveNoticeData}";
	if(saveRet=='0000'){
		alert("公告发送成功!");
	}else if(saveRet=='0001'){
		alert("公告发送失败!");
	}
	
	function sendNotice(){
			var themeVal=$("#themeID").val();
			var textVal=$("#textID").val();
			if(themeVal==''){
				alert("主题不能为空!");
				return false;
			}else if(textVal==''){
				alert("发送内容不能为空!");
				return false;
			}
			//因为设置了enctype，必须通过url传参，否则后台无法取到值
			newEmailForm.action="<%=path%>/notice/sendNotice.do?theme="+themeVal+"&text="+textVal;
			newEmailForm.submit();
	}
	
	function saveToDraft(){
		alert("功能暂未开放!");
	}
	</script>
</head>
<body>
<center>
		<form action="" method="post" name="newEmailForm" enctype="multipart/form-data">
			<table>											
				<tr><td>Theme:&nbsp;&nbsp;<input type="text" name="theme" id="themeID" placeholder="主题"></td></tr>
				<tr><td><hr></td></tr>
				<tr><td>Content:&nbsp;&nbsp;<textarea name="text" id="textID" rows="10" cols="50" placeholder="公告内容" style="vertical-align:top"></textarea></td></tr>
				<tr><td>Accessory:&nbsp;&nbsp;<input type="file" name="accessoryFile" disabled="disabled"></td></tr>
				<tr></tr>
				<tr><td nowrap="nowrap"><input type="button" value="发送" onclick="sendNotice()" id="bt">&nbsp;&nbsp;<input type="reset" value="重填">&nbsp;&nbsp;<input type="button" value="保存草稿" onclick="saveToDraft()"></td></tr>
			</table>
		</form>
	</center>
</body>
</html>