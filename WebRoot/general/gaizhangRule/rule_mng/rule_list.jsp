<%@page contentType="text/html;charset=utf-8"%>
<%
	//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.println("basePath:" + basePath);
%>
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
		<script type='text/javascript' src='/Seal/dwr/interface/GaiZhangRuleSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/gaizhangRule/rule_mng/js/rule_list.js"></script>
		<script src="/Seal/general/gaizhangRule/rule_mng/js/seal_rule.js"></script>
		<script src="/Seal/general/gaizhangRule/rule_mng/js/new_rule.js"></script>
		<script src="/Seal/general/gaizhangRule/rule_mng/js/fromZY.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		var basepath="<%=basePath%>";
		</script>
	</head>
	<body class="bodycolor" onload="list_load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 规则列表</span>
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
							<input style="display: none" type="button" value="批量停用"
								class="SmallButton" onclick="pl_stat('2');" />
							<input style="display: none" type="button" value="批量启用"
								class="SmallButton" onclick="pl_stat('1');" />
							<input  style="display: none" type="button" value="批量删除" class="SmallButton"
								onclick="pl_delBusi();" />
						</td>
					</tr>
					<tr>
						<td align="center">
							<br>
							<input type="button" value="高级搜索" class="BigButton"
								onclick="show_sch();" />
							<input type="button" value="更新规则到内存中" class="BigButton"
								onclick="tij_sch();" />
						</td>
					</tr>
				</table>
			</div>
			<div id="d_search" style="display: none">
				<form id="f_sch" action="" method="post">
					<table class="TableBlock" id="tb_info">
						<tr>
							<td>
								规则名：
							</td>
							<td nowrap class="TableData">
								<input type="text" name="rule_name" class="BigInput" />
							</td>
						</tr>
						<tr>
							<td>
								规则状态：
							</td>
							<td nowrap class="TableData">
								<select class="BigSelect" name="rule_state">
									<option value="0">
										请选择
									</option>
									<option value="1">
										正常
									</option>
									<option value="2">
										注销
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="搜索" class="BigButton"
									onclick="search();" />
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
					<span id="title" class="title">规则详细信息</span><a class="operation"
						href="javascript:HideDialog('detail');"><img
							src="/Seal/images/close.png" /> </a>
				</div>
				<div id="apply_body" class="body" align="center">
					<form id="f_edit" action="" method="post">
						<input type="hidden" name="rule_no">
						<input type="hidden" name="old_name">
						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
								<td width="21%" class="TableContent">
									规则名称：
								</td>
								<td nowrap class="TableData">
									<input type="text" name="rule_name" class="BigInput" />
								</td>
							</tr>
							<tr id="d_seal2">
								<td class="TableContent">
									指定公章：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="seal_id">
									<input type="hidden" name="seal_no">
									<input type="text" readonly name="seal_name">
									<a href="#" onclick="choseSeal('f_edit');return false;">选择</a>
								</td>
							</tr>
							<tr>
								<td class="TableContent">
									是否使用证书：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="use_cert" />
									<input id="chk_cert" name="chk_cert" type="checkbox"  onclick="changeUseCert();">
									使用证书
								</td>
							</tr>
							<tr id="tr_select_cert" style="display: none">
								<td class="TableContent">
									指定证书：
								</td>
								<td nowrap class="TableData">
									<select name="cert_no" class="BigSelect">
									</select>
								</td>
							</tr>
							<tr class="TableData">
								<td width="15%" class="TableContent">
									<input type="hidden" name="arg_desc">
									盖章位置类型：
								</td>
								<td nowrap class="TableData">
									<input type="hidden" name="rule_type">
									<select class="BigSelect" name="type"
										onchange="check_type('f_edit');">
										<option value="1">
											类型一（绝对坐标）
										</option>
										<option value="2">
											类型二（书签）
										</option>
										<option value="3">
											类型三（骑缝章）
										</option>
										<option value="4">
											类型四（文字-覆盖）
										</option>
										<option value="5">
											类型五（文字-后面）
										</option>
										<option value="6">
											类型六（多页绝对坐标）
										</option>
										<option value="7">
											类型七（多页骑缝章）
										</option>
									</select>
								</td>
							</tr>
							<!--绝对位置-->
							<tr id="type1" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									印章加盖到
									<input name="page" type="text" size="5" value="">
									页数;
									<br />
									<font size="1" color="red">例如：1表示第一页盖章，-1表示最后一页盖章</font>
									<br />
									加盖页面位置：横向
									<input name="x" type="text" size="5">
									，纵向
									<input name="y" type="text" size="5">
									<font size="1" color="red"><br />页面位置的值为1-49999</font>
								</td>
							</tr>
							<!--书签-->
							<tr id="type2" style="display: none" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									书签名：
									<input name="Bookmark" type="text" size="20" value="">
								</td>
							</tr>
							<!--骑缝章-->
							<tr id="type3" style="display: none" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									文档类型
									<select name="doctype">
										<option value="0">
											单面
										</option>
										<option value="10">
											双面
										</option>
									</select>
									骑缝章模式
									<select name="mode" onblur="sealpagechang()">
										<option value="3">
											右骑缝
										</option>
										<!--						<option value="1">左骑缝</option>
						<option value="2">上骑缝</option>
						<option value="4">下骑缝</option>
						<option value="5">右左骑缝</option>
						<option value="6">下上骑缝</option>-->
									</select>
									<br />
									加盖页面位置
									<input name="pos" type="text" size="5" value="">
									<font size="1" color="red">页面位置的值为1-49999</font>
									<br />
									起始页：第
									<input name="spage" type="text" size="5" value="1">
									页;
									<br />
									<font size="1" color="red">例如：1表示第一页盖章，-1表示最后一页盖章</font>
									<br />
									结束页：第
									<input name="sealpage" type="text" size="5" value="-1">
									页。
								</td>
							</tr>
							<!--文字（覆盖）-->
							<tr id="type4" style="display: none" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									详细文字：
									<input name="Booktxt" type="text" size="20" value="">
									<br />
									<font size="1" color="red">只能由数字，字母（区分大小写）或汉字组成</font>
									<br />
									起始页：第
									<input name="tspage" type="text" size="5" value="1">
									页;
									<br />
									<font size="1" color="red">例如：1表示第一页盖章，-1表示最后一页盖章</font>
									<br />
									结束页：第
									<input name="tsealpage" type="text" size="5" value="-1">
									页。<br />
									上下偏移量：
									<input name="tpianyi" type="text" size="5" value="0">
									<font size="1" color="red">值为1-49999，例如：-1000表示向上偏移1000，1000表示向下偏移1000</font>
								     <br />
								        左右偏移量：
							        <input name="spianyi" type="text" size="5" value="0">
							        <font size="1" color="red">值为1-49999，例如：-1000表示向左偏移1000，1000表示向右偏移1000</font>
								</td>
							</tr>
							<!--文字（后面）-->
							<tr id="type5" style="display: none" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									详细文字：
									<input name="Booktxt5" type="text" size="20" value="">
									<br />
									<font size="1" color="red">只能由数字，字母（区分大小写）或汉字组成</font>
									<br />
									起始页：第
									<input name="tspage5" type="text" size="5" value="1">
									页;
									<br />
									<font size="1" color="red">例如：1表示第一页盖章，-1表示最后一页盖章</font>
									<br />
									结束页：第
									<input name="tsealpage5" type="text" size="5" value="-1">
									页。<br />
									上下偏移量：
									<input name="tpianyi5" type="text" size="5" value="0">
									<font size="1" color="red">值为1-49999，例如：-1000表示向上偏移1000，1000表示向下偏移1000</font>     
								</td>
							</tr>
							<!--多页绝对位置-->
							<tr id="type6" style="display: none" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									印章加盖到
									<select name="mode6">
										<option value="1">
											奇数
										</option>
										<option value="2">
											偶数
										</option>
										<option value="3">
											指定间隔
										</option>
									</select>
									页
									<br />
									间隔
									<input name="gap" type="text" size="5" value="">
									页
									<br />
									加盖页面位置：横向
									<input name="x6" type="text" size="5">
									，纵向
									<input name="y6" type="text" size="5">
									<font size="1" color="red"><br />页面位置的值为1-49999</font>
								</td>
							</tr>
							<!--多页骑缝章-->
							<tr id="type7" style="display: none" class="TableData">
								<td width="15%" class="TableContent">
									详细设定：
								</td>
								<td>
									文档类型
									<select name="doctype7">
										<option value="0">
											单面
										</option>
										<option value="10">
											双面
										</option>
									</select>
									骑缝章模式
									<select name="mode7" onblur="sealpagechang()">
										<option value="3">
											右骑缝
										</option>
										<!--						<option value="1">左骑缝</option>
						<option value="2">上骑缝</option>
						<option value="4">下骑缝</option>
						<option value="5">右左骑缝</option>
						<option value="6">下上骑缝</option>-->
									</select>
									<br />
									加盖页面位置
									<input name="pos7" type="text" size="5" value="">
									<font size="1" color="red">页面位置的值为1-49999</font>
									<br />
									起始页：第
									<input name="spage7" type="text" size="5" value="">
									页;
									<br />
									<font size="1" color="red">例如：1表示第一页盖章，-1表示最后一页盖章</font>
									<br />
									结束页：第
									<input name="sealpage7" type="text" size="5">
									页。
									<br />
									每
									<input name="gapall" type="text" size="5">
									页加盖一个章。
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="objUpd();" type="button"
						value="修改" />
					<input class="BigButton" onclick="HideDialog('detail')"
						type="button" value="关闭" />
				</div>
			</div>
			<div id="showSeal" class="ModalDialog" style="width: 300px;">
				<div class="header">
					<span id="title" class="title">印章信息</span><a class="operation"
						href="javascript:HideDialog('showSeal');"><img
							src="/Seal/images/close.png" /> </a>
				</div>
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