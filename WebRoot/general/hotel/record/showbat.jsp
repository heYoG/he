<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.net.*"%>
<html>
	<head>
		<script src="/Seal/js/util.js"></script>
		<script type="text/javascript">
		
			function load(obj){
				var name=(null==getUrlParam("no"))?"test":getUrlParam("no");
				name = name.substring(0, name.lastIndexOf(","));
				var filearray = name.split(",");
				var url=baseUrl(3)+"Seal/general/hotel/record/getFile.jsp?name="+filearray[0];
				var ret = obj.LoadFile(url);
				if(ret==1){
					obj.Login("test", 2, 65535, "test", "");
					var bsurl = "";
					for(var i=1;i<filearray.length;i++){
						bsurl = baseUrl(3)+"Seal/general/hotel/record/getFile.jsp?name="+filearray[i];
						obj.MergeFile(999999,bsurl);
					}
				}else{
					alert("操作失败!");
				}
				
			}
			function printdoc(){
				document.all.HWPostil1.PrintDoc(1,1);
			}
			function aip_init(){
				var obj=$("HWPostil1");
				obj.ShowDefMenu = 0; //隐藏菜单
				obj.ShowToolBar = 0; //隐藏工具条
				obj.ShowScrollBarButton = 1;
				obj.InDesignMode = false;//退出设计模式	
				load(obj);
			}
		</script>

		<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			aip_init();
		</SCRIPT>
	</head>
	<body >
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