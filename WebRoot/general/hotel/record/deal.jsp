<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.net.*"%>
<html>
	<head>
	<base target="_self" />
	<style> 
	     .btn{ 
	    BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 16px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid 
	    } 
	    .btn1_mouseout{ 
	    height:35px;BORDER-RIGHT: #9f9fa0 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #9f9fa0 1px solid; PADDING-LEFT: 2px; font-weight: bold;FONT-SIZE: 16px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#f3f3f3, EndColorStr=#d9d9d9); BORDER-LEFT: #9f9fa0 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #9f9fa0 1px solid 
	    } 
	    .btn1_mouseover{ 
	    height:35px;BORDER-RIGHT: #7EBF4F 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7EBF4F 1px solid; PADDING-LEFT: 2px; font-weight: bold;FONT-SIZE: 16px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#8dda00, EndColorStr=#639800); BORDER-LEFT: #7EBF4F 1px solid; CURSOR: hand; COLOR: #FFFFFF; PADDING-TOP: 2px; BORDER-BOTTOM: #7EBF4F 1px solid 
	    } 
	    .btn1_mousedown 
	    { 
	    height:35px;BORDER-RIGHT: #FFE400 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #FFE400 1px solid; PADDING-LEFT: 2px; font-weight: bold;FONT-SIZE: 16px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#CC3300, EndColorStr=#CC3300); BORDER-LEFT: #FFE400 1px solid; CURSOR: hand; COLOR: #FFFFFF; PADDING-TOP: 2px; BORDER-BOTTOM: #FFE400 1px solid 
	    } 
	    .btn1_mouseup{ 
	    height:35px;BORDER-RIGHT: #7EBF4F 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7EBF4F 1px solid; PADDING-LEFT: 2px; font-weight: bold;FONT-SIZE: 16px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#8dda00, EndColorStr=#639800); BORDER-LEFT: #7EBF4F 1px solid; CURSOR: hand; COLOR: #FFFFFF; PADDING-TOP: 2px; BORDER-BOTTOM: #7EBF4F 1px solid 
	    } 
    </style>
		<script src="/Seal/js/util.js"></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/HotelRecord.js'></script>
		<script src="/Seal/general/hotel/record/js/deal.js"></script>
		<script type="text/javascript">
			var user_no="${current_user.user_id}";
			var user_name="${current_user.user_name}";
			var user_ip="${user_ip}";
			if(user_no==""){
				top.location="/Seal/login.jsp";
			}
		</script>

  	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyFullScreen>
  		 HWPostil1_NotifyFullScreen();
  	</SCRIPT>
  	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
  		 HWPostil1_NotifyCtrlReady();
  	</SCRIPT>
   	<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=JSNotifyBeforeAction(lActionType,lType,strName,strValue)> 
  		 HWPostil1_NotifyBeforeAction(lActionType,lType,strName,strValue); 
  	</SCRIPT> 
	</head>
	<body onload="load();">
		<center>
			<table width="100%" height="100%">
				<tr>
					<td>
						<input type="hidden" id="name" name="name" />
						<script src="/Seal/loadocx/LoadHWPostil.js"></script>
					</td>
				</tr>
				<tr>
					<td height="20px;" align="center">
						<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" name="setbikuan" value="笔宽" onclick="setbikuan()">  
     					&nbsp;&nbsp;<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" name="setyanse" value="颜色" onclick="setyanse()">  
      		&nbsp;&nbsp;&nbsp;&nbsp;<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" id="qianzi" name="qianzi" value="一次稽核签字" onclick="handWrite()">
      		&nbsp;&nbsp;&nbsp;&nbsp;<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" id="lqianzi" name="lqianzi" value="二次稽核签字" onclick="SecondHandWrite()">  
     					&nbsp;&nbsp;<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" name="setqueren" value="确认" onclick="setqueren()">  
		 	&nbsp;&nbsp;&nbsp;&nbsp;<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" name="upload" value="上传" onclick="saveUpload()"> 
     					&nbsp;&nbsp;<input class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'" type="button" name="printdoc" value="打印" onclick="printdoc()">  
					</td>
				</tr>
			</table>
		</center>										
	</body>
</html>