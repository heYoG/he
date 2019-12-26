<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增印模</title>
<script type="text/javascript" src="<%=path%>/js/jQuery/jquery-3.4.1.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery/jquery-3.4.1.min.js"></script>
<style type="text/css">
table {
	border: 0px solid
}

td {
	text-align: center
}
</style>
<script type="text/javascript">
	$(function(){
		$("#adBT").click(function(){
			var path=$("#sealImgID").val().trim();
			var format=path.substring(path.lastIndexOf(".")+1);
			var reg=/^(jpg|bmp|png|gif)$/;//字符不需要双引号""
			if(format==''||format==null){
				alert("请选择要上传的图片!");
				return false;
			}else if(!format.match(reg)){
				alert("图片格式不对,请重新选择!");
				document.getElementById("sealImgID").value='';
				return false;
			}
			location.href="";
		});
	})
</script>
</head>
<body>
	<center>
		<form action="" method="post" name="adForm">
			<table>
				<tr>
					<td>选择印模图片<input type="file" name="sealImgFile" id="sealImgID"></td>
				</tr>
				<tr>
					<td><input type="button" id="adBT" value="上传"></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>