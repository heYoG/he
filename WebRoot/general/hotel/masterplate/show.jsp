<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/general/model_file/model_mng/js/seal.js"
			type="text/javascript"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script type="text/javascript">
function load(){
	var name=(null==getUrlParam("no"))?"null":getUrlParam("no");
	var id=(null==getUrlParam("id"))?"null":getUrlParam("id");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.getModelFileByName(id,null,cb_data);//dwr装载数据,模板名称默认为null,
}
function cb_data(d){
	obj=$("HWPostil1");
	obj.InDesignMode=0;
	obj.LoadFileBase64(d.content_data);
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
					<td colspan="2">
						<script src="/Seal/loadocx/LoadHWPostil.js"></script>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>