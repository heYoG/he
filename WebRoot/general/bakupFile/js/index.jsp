<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="../../theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="js/utility.js"></script>
		<script type="text/javascript" src="/Seal/general/bakupFile/js/jquery-1.7.2.min.js"></script>
		<style>
.btn {
	BORDER-RIGHT: #7b9ebd 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7b9ebd 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform . Microsoft .
		Gradient(GradientType = 0, StartColorStr = #ffffff, EndColorStr =
		#cecfde);
	BORDER-LEFT: #7b9ebd 1px solid;
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7b9ebd 1px solid
}

.btn1_mouseout {
	height: 25px;
	BORDER-RIGHT: #9f9fa0 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #9f9fa0 1px solid;
	PADDING-LEFT: 2px;
	font-weight: bold;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform . Microsoft .
		Gradient(GradientType = 0, StartColorStr = #f3f3f3, EndColorStr =
		#d9d9d9);
	BORDER-LEFT: #9f9fa0 1px solid;
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #9f9fa0 1px solid
}

.btn1_mouseover {
	height: 25px;
	BORDER-RIGHT: #7EBF4F 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7EBF4F 1px solid;
	PADDING-LEFT: 2px;
	font-weight: bold;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform . Microsoft .
		Gradient(GradientType = 0, StartColorStr = #8dda00, EndColorStr =
		#639800);
	BORDER-LEFT: #7EBF4F 1px solid;
	CURSOR: hand;
	COLOR: #FFFFFF;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7EBF4F 1px solid
}

.btn1_mousedown {
	height: 25px;
	BORDER-RIGHT: #FFE400 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #FFE400 1px solid;
	PADDING-LEFT: 2px;
	font-weight: bold;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform . Microsoft .
		Gradient(GradientType = 0, StartColorStr = #CC3300, EndColorStr =
		#CC3300);
	BORDER-LEFT: #FFE400 1px solid;
	CURSOR: hand;
	COLOR: #FFFFFF;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #FFE400 1px solid
}

.btn1_mouseup {
	height: 25px;
	BORDER-RIGHT: #7EBF4F 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7EBF4F 1px solid;
	PADDING-LEFT: 2px;
	font-weight: bold;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform . Microsoft .
		Gradient(GradientType = 0, StartColorStr = #8dda00, EndColorStr =
		#639800);
	BORDER-LEFT: #7EBF4F 1px solid;
	CURSOR: hand;
	COLOR: #FFFFFF;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7EBF4F 1px solid
}


</style>
		<script type="text/javascript">
		var ftpNum = 1;
		function addFtp(val){
			var table_0 = jQuery("#ftpId").html();
			$("#ftpId").html("");
			var table0 = "<table id='ftpId"+ftpNum+"'  border=1 class='TableBlock'>";
			var table1 = "<tr><td>FTP_IP:</td><td><input type='text' onblur='checkIp(this.value)' id='ftpIP"+ftpNum+"'  name='ftpIp"+ftpNum+"'/></td></tr>";
			var table2 = "<tr><td>FTP_端口:</td><td><input type='text' onblur='checkPort(this.value)' id='ftpPort"+ftpNum+"'  name='ftpPort"+ftpNum+"'/></td></tr>";
			var table3 = "<tr><td>FTP_用户名:</td><td><input type='text' id='ftpUserName"+ftpNum+"' name='ftpUserName"+ftpNum+"'/></td></tr>";
			var table4 = "<tr><td>FTP_密码:</td><td><input type='text'  id='ftpUserPwd"+ftpNum+"' name='ftpUserPwd"+ftpNum+"'/></td></tr>";
			var table5 = "<tr><td>添加ftp路径:</td><td><textarea rows='3' cols='17' id='ftpPath"+ftpNum+"' onblur='checkFtpPath(this.value)'  name='ftpPath"+ftpNum+"'></textarea><font style='color: red;'>注>格式：原文件路径  , 备份指定路径;(多个用;分隔)</font></td></tr>"
			var table6 = "<tr><td><input type='button' value='删除上面的配置' onclick='delFtpConfig(\"ftpId"+ftpNum+"\")'/></td></tr>"
			var table7 = "</table>";
			var table = table_0+table0+table1+table2+table3+table4+table5+table7+"</br>";
			$("#ftpId").html(table);
			$("#ftpNum").val(ftpNum);
			ftpNum++;
		}
		function checkIp(ftpIp){
			var exp = /^([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.){2}([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
		    var reg = ftpIp.match(exp); 
			 if(reg==null) 
			 { 
			 	alert("IP地址输入不合法");
				return false;
			 }
		}
		function checkPort(port){
			var exp = "/^[0-9]*[1-9][0-9]*$";
			var reg = port.match(exp);
			//if(reg==null){
			//	alert("端口号输入不合法");
			//	return false;
			//}
		}
		function checkFtpPath(path){
			if(path==""){
				alert("添加ftp路径不能为空");
				return false;
			}
		}
		
		function delFtpConfig(ftpId){
			$("#"+ftpId).html("");
		}
		var localNum = 1;
		function addLocal(){
			var table_0 = $("#localId").html();
			$("#localId").html("");
			var table0 = "<table id='localId"+localNum+"' border=1 class='TableBlock' >";
			var table1 = "<tr><td>备份路径：</td><td><input type='text' name='sourcePath"+localNum+"' size='50'/></td></tr>";
			var table2 = "<tr><td>原文件路径：</td><td><input type='text' name='bakupPath"+localNum+"' size='50'/></td></tr>";
			var table3 = "</table>";
			var table = table_0+table0+table1+table2+table3+"</br>";
			$("#localId").html(table);
			$("#localNum").val(localNum);
			localNum++;
		}
		function checkSub(){
			var ftpNum = $("#ftpNum").val();
			var localNum = $("#localNum").val();
			if(ftpNum>0||localNum>0){
				for(var i=1;i<ftpNum+1;i++){
					if($("#ftpIP"+i).val()==""||$("#ftpPort"+i).val()==""||$("#ftpUserName"+i).val()==""||$("#ftpUserPwd"+i).val()==""||$("#ftpPath"+i).val()==""){
						alert("请填写完整");
						return false;
					}
				}
				return true;
			}else{
				alert("请先添加");
				return false;
			}
		}
	</script>
	</head>
	<body class="bodycolor" topmargin="5">
		<form action="/Seal/bakupFile" method="post" onsubmit="return checkSub();">
			<table width="100%" height="90%" 
				 border="1" cellspacing="0" cellpadding="3">
				<tr width="100%" height="100%">
					<td width="60%" valign="top">
						<table  align="center">
							<tr>
								<td>
									<input type="button" value="添加Ftp配置" onclick="addFtp();" class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'"/>
								</td>
							</tr>
						</table>
						<div id="ftpId"></div>
					</td>
					<td width="40%">
						<table width="100%" height="100%" border=1>
							<table  align="center">
								<tr>
									<td >
										<input type="button" value="添加本地配置"  onclick="addLocal();" class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'"/>
									</td>
								</tr>
							</table>
							<div id="localId"></div>
						</table>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td><input type="hidden" id="ftpNum" name="ftpNum"/></td>
					<td><input type="hidden" id="localNum" name="localNum"/></td>
					<td><input type="submit" value="备份文件" class='btn1_mouseout' onMouseOver="this.className='btn1_mouseover'" onmouseout="this.className='btn1_mouseout'" onmousedown="this.className='btn1_mousedown'" onmouseup="this.className='btn1_mouseup'"/></td>
				</tr>
			</table>
		</form>

	</body>
</html>
