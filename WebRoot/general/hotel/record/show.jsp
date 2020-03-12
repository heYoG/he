<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.net.*"%>
<html>
	<head>
		<script src="/Seal/js/util.js"></script>
		<script type="text/javascript">
			var aipLoadResult = 1;
			function load(){
				var name=(null==getUrlParam("no"))?"test":getUrlParam("no");
				var url=baseUrl(3)+"Seal/general/hotel/record/getFile.jsp?name="+name;
				document.getElementById("HWPostil1").LoadFile(url);
				
				//添加打印文件
				if(name != null && name != ""){
					name = name+".aip";
				}
				var aipUrl=baseUrl(3)+"Seal/general/hotel/record/getFile.jsp?name="+name;
				aipLoadResult = document.getElementById("HWPostil2").LoadFileEx(aipUrl,'aip',0,0);
				//document.getElementById("HWPostil2").LoadFile(aipUrl);
				//document.getElementById("HWPostil2").SaveTo("c:\\print.pdf","pdf",0); 
				//document.getElementById("HWPostil2").LoadFile("c:\\print.pdf");
				
			}
			function printdoc(){
				//document.getElementById("HWPostil1").PrintDoc(1,1);
				//document.getElementById("HWPostil2").PrintDoc(1,1);
				/* document.getElementById("HWPostil2").SetValue("PREDEF_PRN_OFFSETX","200");
				document.getElementById("HWPostil2").SetValue("PREDEF_PRN_OFFSETY","200");
				document.getElementById("HWPostil2").PrintDoc(1,0); */
				if(aipLoadResult == 0){
					alert("流程银行凭证不支持在该系统打印，请到流程银行单据重打进行打印！");
				}else{
					var pageNum = document.getElementById("HWPostil2").PageCount(); 
					document.getElementById("HWPostil2").PrintDocEx('',1,0,1,1,pageNum,0,1,1,0,0);
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
					<td>
						<OBJECT id=HWPostil2 classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561 style='width:1px;height:1px'></OBJECT>
					</td>
				</tr>
			</table>
		</center>										
	</body>
</html>