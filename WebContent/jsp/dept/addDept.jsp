<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增部门</title>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/jQuery_easyui/jquery.easyui.mobile.js"></script>
<script type="text/javascript">
	
	$(function() {
		$("#bt").click(
				function() {
					if ($("#deptID").val().trim() == ''
							|| $("#deptID").val().trim() == null) {
						alert("新增部门名称不能为空,请重新输入!");
						return;
					}
					var deptName=encodeURI(addDeptForm.deptName.value);
					deptName=encodeURI(deptName);//在前端对中文用浏览器编码进行二次编码后传送到后台
					$.ajax({
						type : "post",
						url : "<%=path%>/deptCtrl/deptNameIfExit.do?deptNm="+deptName,
						data : "",
						dataType : "json",
						//contentType:"text/json,charset=gbk",//设置请求编码,不设置默认使用浏览器当前编码
						success : function(data) {
							var retVal = eval(data);
							var dept = "";
							if (retVal != '' && retVal != null) {
								for (var i = 0; i < retVal.length; i++) {
									dept = retVal[i].deptName;
								}
								alert("'"+dept+"'已存在,请重新输入！");
								document.getElementById("deptID").value='';
								return false;
							}
							addDeptForm.submit();
						},
						error : function(error) {
							alert("ajax处理请求异常!");
						}
					});
				});
	});
</script>
<style type="text/css">
	table{
		border:0;
	}
	td{
		text-align:center;
	}
</style>

</head>
<body>
	<center>
		<form action="<%=path %>/deptCtrl/addDept.do" method="post" name="addDeptForm">
			<table>
				<tr>
					<td>部门名称:<input type="text" name="deptName" id="deptID"></td>
				</tr>
				<tr><td><input type="button" id="bt" value="确定"></td></tr>
			</table>
		</form>
	</center>
</body>
</html>