<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/Seal/theme/6/topbar.css">
</head>

<body  STYLE="margin:0pt;padding:0pt" >
<table class="topbar" height="50" width="100%" border=0 cellspacing=0 cellpadding=0>
  <tr >
	<td width="70%">
		<div class="banner_text" >
		  &nbsp;&nbsp;${licenseUnitName}电子签章管理系统
		</div>
	</td>
	<td  style="text-align:right">	
      	{总授权数：${sealNum }，本系统已有印章：${usedSealNum }   }&nbsp;&nbsp;
    </td>
    <td width="115" >	
      	<a href="../personInfoIndex.do" target="main" ><img src="/Seal/theme/6/userSet.png" ></a>
    </td>
  </tr>
</table>
</body>
</html>


