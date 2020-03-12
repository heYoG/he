<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.net.*"%>
<html>
	<head>
		<title>稽核办理</title>
		<script src="/Seal/js/util.js"></script>
		<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/String.js"></script>
		<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/dialog.css">
		<script src="/Seal/js/utility.js"></script>
		<script src="/Seal/js/dialog.js"></script>
        <script src="/Seal/js/util.js"></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/HotelRecord.js'></script>
		<script type="text/javascript">
			var user_no="${current_user.user_id}";
			if(user_no==""){
				top.location="/Seal/login.jsp";
			}
			var recordid = "";
			function load(){
				var name=(null==getUrlParam("no"))?"test":getUrlParam("no");
				recordid=(null==getUrlParam("id"))?"":getUrlParam("id");
				var url=baseUrl(3)+"Seal/general/hotel/record/getFile.jsp?name="+name;
				document.all.HWPostil1.LoadFile(url);
			}
			function tongguo(){
				if(window.confirm("确认稽核通过?")){
					var reason = "";
					HotelRecord.updateRecordCheckStatus(recordid,user_no,"2",reason,callback1);
				}
			}
			function callback1(d){
				if(d==1){
						window.returnValue="success";
						window.close();
				}else{
					alert("操作失败!");
				}
			}
			function butongguo(){
			 var str=prompt("请填写不通过原因：","");
			 if(str){
			 	var reason = str;
				HotelRecord.updateRecordCheckStatus(recordid,user_no,"a",reason,callback1);
			 }
			}
			
			function aip_init(){
				var obj=$("HWPostil1");
				obj.ShowDefMenu = 0; //隐藏菜单
				obj.ShowToolBar = 0; //隐藏工具条
				obj.ShowScrollBarButton = 1;
				obj.InDesignMode = false;				//退出设计模式		
			}
		</script>

		<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			aip_init();
		</SCRIPT>
	</head>
	<body class="bodycolor" topmargin="0" onload="load();">
		<center>
			<table width="100%" height="100%" class="small">
				<tr>
					<td height="20px;" align="center">
						<input type="button" value="通过" class="BigButton" onclick="tongguo()" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="不通过" class="BigButton" onclick="butongguo()" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="name" name="name" />
						<script src="/Seal/loadocx/LoadHWPostil.js"></script>
					</td>
				</tr>
			</table>
		</center>										
	</body>
</html>