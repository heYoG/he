<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/Calendar.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/general/hotel/dictionary/js/new_dic.js"></script>
		<script src="/Seal/general/hotel/dictionary/js/fromZY.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/HotelDic.js'></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		</script>
	</head>
	<body class="bodycolor" onload="load()">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif">
					<span class="big3"> 新增规则</span>
				</td>
			</tr>
		</table>
		<center>
			<br>
			<form id="f_new" action="/Seal/newDic.do" method="post">
				<table class="TableBlock" width="80%" id="tb_info">

					<tr>
						<td width="15%" class="TableContent">
							通用字典名：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="cname" class="BigInput" />
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							显示名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="cshowname" class="BigInput" />
						</td>
					</tr>
					
					<tr>
						<td class="TableContent">是否显示：</td>
						<td nowrap class="TableData">
							<input type="radio" name="c_status"  value="0" onclick="clickme(0)" />显示 &nbsp;&nbsp;
							<input type="radio" name="c_status" id="r2" value="1" checked="checked" onclick="clickme(1)" />隐藏&nbsp;&nbsp;
						</td>
					</tr>
					
					<tr id="sort_id" style="display: none">
						<td class="TableContent">显示顺序：</td>
						<td nowrap class="TableData">
							<input type="text" name="c_sort" id="sortno" class="BigInput" />
						</td>
					</tr>
					
					
					
					<tr>
					
						<td class="TableContent">
							字符类型：
						</td>
						<td nowrap class="TableData">
							<input type="hidden" name="cdatatype"/>
							<select class="BigSelect" name="type">
								<option value="文本" selected>
									文本
								</OPTION>
								<option value="密码">
									密码
								</OPTION>
								<option value="整数">
									整数
								</OPTION>
								<option value="小数">
									小数
								</OPTION>
								<option value="大写金额">
									大写金额
								</OPTION>
								<option value="日期">
									日期
								</OPTION>
								<option value="图片">
									图片
								</OPTION>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center" nowrap class="TableControl">
							<input type="button" value="确定" onclick="newDic();"
								class="BigButton" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="返回" onclick="history.go(-1);"
								class="BigButton" />
						</td>
					</tr>
				</table>
			</form>
		</center>
	</body>
</html>