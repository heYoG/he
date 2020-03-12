<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/2/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util/Map.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/verify/verifyCodebar.js"></script>

		<SCRIPT LANGUAGE=javascript FOR=BarCodeReadCtrl1 EVENT=BarcodeTextChanged>
			//alert(BarCodeReadCtrl1.BarcodeText);
			//bt_changed();
			moniScan(BarCodeReadCtrl1.BarcodeText);
		</SCRIPT>
	</head>
	<body class="bodycolor">
		<script src="/Seal/loadocx/LoadCodeBar.js"></script>
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td width="1">

				</td>
				<td class="Big">
					<img src="/Seal/images/menu/system.gif">
					<span class="big3"> 二维码扫描验证</span>
				</td>
			</tr>
		</table>
		<center>
			<table class="TableBlock" border=0 width="30%" align="center">
				<tr>
					<td class="TableHeader" colspan="2">
						&nbsp;连接扫描枪属性
					</td>
				</tr>
				<tr>
					<td class="TableContent" width="100" align="center">
						每秒位数：
					</td>
					<td class="TableData">
						<select id="Baud">
							<option value="4800">
								4800
							</option>
							<option value="9600" selected>
								9600
							</option>
							<option value="19200" >
								19200
							</option>
							<option value="38400">
								38400
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="TableContent" align="center">
						COM：
					</td>
					<td class="TableData">
						<select id="ComNo">
							<option value="1">
								1
							</option>
							<option value="2" selected>
								2
							</option>
							<option value="3">
								3
							</option>
							<option value="4">
								4
							</option>
							<option value="5">
								5
							</option>
							<option value="6">
								6
							</option>
							<option value="7">
								7
							</option>
							<option value="8">
								8
							</option>
							<option value="9">
								9
							</option>
							<option value="10">
								10
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="TableContent" colspan="2" align="center">
						<input class="SmallButton" type="button" value="启动监听"
							onclick="EnableComCtrl_onclick(1)">
						&nbsp;&nbsp;
						<input class="SmallButton" type="button" value="停止监听"
							onclick="EnableComCtrl_onclick(0)">
					</td>
				</tr>
			</table>
			<br />
			<input style='display:none' class="SmallButton" type="button" value="模拟扫描事件"
				onclick="moniScan();" />
			<br />
			<div id="d_body">
				<table class="TableBlock" align="center" width="60%">
					<tr>
						<td nowrap class="TableControl" colspan="3" align="center">
							扫描信息
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" colspan="1" align="center">
							校验签名结果：
							<input type="hidden" id="result">
						</td>
						<td nowrap class="TableData" id="td_signRusult" colspan="1">
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" colspan="1" align="center">
							签名证书序列号：
						</td>
						<td nowrap class="TableData" colspan="1">
							<input type="text" size="45" id="CertSerial" name="CertSerial">
							<span>
						</td>
					</tr>
					<tr style="display:none">
						<td nowrap class="TableData" colspan="1" align="center">
							文档编号：
						</td>
						<td nowrap class="TableData" colspan="1">
							<input type="text" id="PrintName" name="PrintName">
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" colspan="1" align="center">
							文档签名时间：
						</td>
						<td nowrap class="TableData" colspan="1">
							<input type="text" id="PrintTime" name="PrintTime">
						</td>
					</tr>
					<tr>
						<td nowrap class="TableControl" colspan="2" align="center">
							扫描页内容
						</td>
					</tr>
					<tr>
						<td nowrap class="TableData" colspan="2" align="center">
							<textarea id="PrintContent" name="PrintContent" rows="10"
								cols="100" readonly></textarea>
						</td>
					</tr>
				</table>
			</div>
		</center>
	</body>
</html>