<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>印章查询结果</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/inc/js/ccorrect_btn.js"></script>
		<script>
var $ = function(id) {return document.getElementById(id);};
function load_do()
{
  if($("ORIG_DATA").value!="")
  {
	  var obj = document.getElementById("DMakeSealV61");
	  if(!obj){
		  return false;
	  }
	  obj.SetEncBmp($("ORIG_DATA").value);
	}
	else
	  return;
}
</script>
	</head>
	<body class="bodycolor" onload="load_do()">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/query.gif" align="absmiddle">
					<span class="big3"> 印章详情</span>
				</td>
			</tr>
		</table>
		<table class="TableBlock" align="center" width="100%">
			<tr>
				<td nowrap class="TableContent" width=100>
					申请人：
				</td>
				<td class="TableData">
					${seal.apply_user }
				</td>
				<td nowrap class="TableContent">
					申请时间：
				</td>
				<td class="TableData">
					${seal.seal_applytime }
				</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">
					备注：
				</td>
				<td class="TableData" colspan=3>
					${seal.temp_remark }
				</td>
			</tr>
			<tr>
				<td nowrap class="TableContent" width=100>
					 制章人：
				</td>
				<td class="TableData">
					${seal.seal_creator }
				</td>
				<td nowrap class="TableContent">
					 制章时间：
				</td>
				<td class="TableData">
					${seal.create_time }
				</td>
			</tr>
			<tr>
				<td nowrap class="TableContent" width="80">
					印章名称：
				</td>
				<td class="TableData">
					${seal.seal_name }
				</td>
				<td align="center" class="TableData" width="250" rowspan="8"
					colspan=2>
					<%@include file="../../../inc/makeSealObject.jsp"%>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">
					所属单位：
				</td>
				<td class="TableData">
					${seal.dept_name }
				</td>
			</tr>
			<tr>
				<td nowrap class="TableContent">
					印章类型：
				</td>
				<td nowrap class="TableData">
					${seal.seal_type }
				</td>
			</tr>
		</table>
		<input type="hidden" name="ORIG_DATA" id="ORIG_DATA"
			value="${seal.seal_data }">
			
			<table align="center" >
			<tr> 
			<td nowrap colspan="2" align="center">
			<input type="button" onclick="history.go(-1);" value="返回" class="BigButton" title="返回" name="button">
			&nbsp;&nbsp;
			</td>
			</tr>		
</table>

</body>
</html>