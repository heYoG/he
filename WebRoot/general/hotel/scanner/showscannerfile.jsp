<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.net.*"%>
<html>
	<head>
		<script src="/Seal/js/util.js"></script>
		<script type="text/javascript">
		
			function load(){
				var name=(null==getUrlParam("no"))?"test":getUrlParam("no");
				var url=baseUrl(3)+"Seal/general/hotel/scanner/getFile.jsp?name="+name;
				document.all.HWPostil1.LoadFile(url);
			}
			function printdoc(){
				document.all.HWPostil1.PrintDoc(1,1);
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
	<body onload="load();">
		<center>
			<table width="100%" height="100%">
				<tr>
					<td height="20px;">
						<input type="button" value="打印" class="BigButton" onclick="printdoc()" />
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