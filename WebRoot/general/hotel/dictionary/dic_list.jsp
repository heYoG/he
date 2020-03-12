<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/dialog.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/dictionary/js/dic_list.js"></script>
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
	<body class="bodycolor" onload="list_load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 字典列表</span>
				</td>
			</tr>
		</table>
		<center>

			<div id="d_list">
				<table width="100%">
					<tr>
						<td>
							<div id="div_table">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="pager" align="left" id="pager">
							</div>
						</td>
					</tr>
					<tr>
						<td align="center">
							<input style="display: none" type="button" value="批量删除"
								class="SmallButton" onclick="pl_delDic()" />
						</td>
					</tr>
					<tr>
						<td align="center">
							<br>
							<input type="button" value="高级搜索" class="BigButton" style="display: none"
								onclick="show_sch()" />
						</td>
					</tr>
				</table>
			</div>


			<div id="d_search" style="display: none">
				<form id="f_sch" action="" method="post">
					<table class="TableBlock" id="tb_info">
						<tr>
							<td>
								通用字典名：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="cname" class="BigInput" />
							</td>
						</tr>
						<tr>
							<td>
								显示名称：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="cshowname" class="BigInput" />
							</td>
						</tr>
						<tr>
							<td>
								字符类型：
							</td>
							<td nowrap class="TableData">
								<select class="BigSelect" name="cdatatype">
									<option value="" selected>
										无限制
									</option>
									<option value="文本">
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
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="搜索" class="BigButton"
									onclick="searchDic()" />
								<input type="reset" value="清空" class="BigButton" />
								<input type="button" value="返回" class="BigButton"
									onclick="ret();" />
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div id="overlay"></div>


			<div id="detail" class="ModalDialog" style="width: 600px;">
			
			
				<div class="header">
					<span id="title" class="title">字典详细信息</span><a class="operation"
						href="javascript:HideDialog('detail');"><img
							src="/Seal/images/close.png" /> </a>
				</div>

				<div id="apply_body" class="body" align="center">
					<form id="f_edit" action="" method="post">
						<input type="hidden" name="cid">
						<input type="hidden" name="old_name">
						<input type="hidden" name="old_showname" />
						<input type="hidden" name="sys" />
						<input type="hidden" name="old_c_sort"/>
						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
								<td width="21%" class="TableContent">
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
							
							
							<tr >
								<td class="TableContent">
									是否显示：
								</td>
								<td nowrap class="TableData">
									<input type="radio" name="c_status" id="r1" value="0" onclick="clickme(0)" />
									显示 &nbsp;&nbsp;
									<input type="radio" name="c_status" id="r2" value="1" onclick="clickme(1)" />
									隐藏&nbsp;&nbsp;
								</td>
							</tr>
							
							<tr id="sort_id">
								<td class="TableContent">显示顺序：</td>
								<td nowrap class="TableData">
									<input type="text" name="c_sort" class="BigInput" />
								</td>
							</tr>

							
							
							<tr>
								<td class="TableContent">
									字符类型：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="cdatatype" />
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
						</table>
					</form>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="objUpd();" type="button"
						value="修改" />
					<input class="BigButton" onclick="HideDialog('detail');" type="button" value="关闭" />
				</div>
			</div>

			<div id="showSeal" class="ModalDialog" style="width: 300px;">

				<div id="apply_body" class="body" align="center">
					<table width="90%" height="200">
						<tr>
							<td width="100%" height="100%">

								<div id="seal_body_info" class="body" align="center">

								</div>
							</td>
						</tr>
					</table>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="HideDialog('showSeal')"
						type="button" value="关闭" />
				</div>
			</div>
		</center>
	</body>
</html>