<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>

<html>
<head>
<title>设置详情</title>
<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
<script src="/Seal/js/ccorrect_btn.js"></script>
<script src="/Seal/js/module.js"></script>
<script language="JavaScript" src="/Seal/js/util.js"></script>
<script src="/Seal/js/tableUtil.js"></script><!-- 用于使用DWR查询时创建表格 -->
<script src="/Seal/js/pageSplit.js"></script><!-- 用于使用DWR查询时分页 -->
<script src="/Seal/js/String.js"></script>
<script type='text/javascript' src='/Seal/dwr/interface/DocPrintService.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script src="/Seal/general/docPrint/set_docPrint/js/show_docPrintUserSet.js"></script><!-- 用于使用DWR查询时分页 -->
<script type="text/javascript">

</script>
</head>

<body class="bodycolor" topmargin="5" onload="load()">


<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="small"><img src="/Seal/images/node_user.gif" WIDTH="22" HEIGHT="20" align="absmiddle"><span class="big3"> 打印设置详情</span>
    </td>
  </tr>
</table>
<table border="0" width="100%" cellspacing="10" cellpadding="3">
	<tr>
		<td width="50%">
		按用户设置情况
		</td>
	</tr>
	<tr>
		<td>
			<div id="div_Usertable">
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="pager" align="left" id="pager"><!-- 用户的翻页部分 -->
			</div>
		</td>
	</tr>
</table>
</body>
</html>
