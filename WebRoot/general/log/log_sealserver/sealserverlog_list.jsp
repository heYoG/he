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
		<script src="/Seal/general/log/log_sealserver/js/sealserverlog_list.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		function openmodwin() {
		alert("aa");
	    var b = window.showModalDialog("/Seal/general/dept_tree.jsp?p=true&&req=seal_temp11&&user_no=${current_user.user_id }",form1);
       }
       
       function GetDate(nText) {
			reVal = window.showModalDialog("../../../inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
			var val1 = null;
			var val2 = null;
			if (reVal != null) {
				if (nText == 1) {
					//document.forms[0].starttime.value = reVal;
					document.getElementById("start_time").value=reVal;
					var str1=document.getElementById("end_time").value;
					if(str1.length>10){
						var str2="";
						for(var i=0;i<str1.length;i++){
							if(str1.charAt(i)>=0&&str1.charAt(i)<=9){
								if(isNaN(str1.charAt(i+1))&&!isNaN(str1.charAt(i-1))){
									str2=str2+str1.charAt(i)+"-";
								}else if(isNaN(str1.charAt(i+1))&&isNaN(str1.charAt(i-1))){
									str2=str2+"0"+str1.charAt(i)+"-";
								}else{
									str2=str2+str1.charAt(i);
								}
							};
						}
						str2=str2.substring(0,str2.length-1);
						document.getElementById("end_time").value=str2;
					}	
				} else {
					if (nText == 2) {
						//document.forms[0].endtime.value = reVal;
						document.getElementById("end_time").value=reVal;
						var str1=document.getElementById("start_time").value;
						if(str1.length>10){
							var str2="";
							for(var i=0;i<str1.length;i++){
								if(str1.charAt(i)>=0&&str1.charAt(i)<=9){
									if(isNaN(str1.charAt(i+1))&&!isNaN(str1.charAt(i-1))){
									str2=str2+str1.charAt(i)+"-";
								}else if(isNaN(str1.charAt(i+1))&&isNaN(str1.charAt(i-1))){
									str2=str2+"0"+str1.charAt(i)+"-";
								}else{
									str2=str2+str1.charAt(i);
								}
							};
						}
						str2=str2.substring(0,str2.length-1);
						document.getElementById("start_time").value=str2;
						}	
		  			}
				}
			val1 = toDate(document.getElementById("start_time").value);
			val2 = toDate(document.getElementById("end_time").value);
			if(val1 > val2){
				alert("生效日期大于失效日期");
				document.getElementById("start_time").value = "";
				document.getElementById("end_time").value = "";
			}
			};
	
		}
 		
 		function toDate(str){
			var sd = str.split("-");
			return new Date(sd[0],sd[1],sd[2]);
		}
		</script>
	</head>
	<body class="bodycolor" onload="load();">
		<div id="d_search">
			<form id="f_sch" action="" method="post" name="form1">
				<table class="TableBlock" id="tb_info" width="100%" cellpadding="0" cellspacing="0">
					<tr>			
						<td><center>查询时限:
							<input type="text" id="start_time" name="starttime" size="10" maxlength="10"
								class="BigInput" onfocus="this.blur()">
							<img onclick="GetDate(1);" src="../../../images/menu/calendar.gif"
								style="height: 20; cursor: hand" border="0" />
						至&nbsp;
							<input type="text" id="end_time" name="endtime" size="10" maxlength="10"
								class="BigInput" onfocus="this.blur()">
							<img onclick="GetDate(2);" src="../../../images/menu/calendar.gif"
								style="height: 20; cursor: hand" border="0" />
						</td>
						<td align="center" class="TableData">
							<input type="button" value="搜索" class="BigButton"
									onclick="get_count()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="reset" value="清空" class="BigButton" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<table class="TableBlock" border="1" width="100%">
			<tr align="center">
				<td></td>
				<td>当天</td>
				<td>当月</td>
				<td>自定义时限</td>
			</tr>
			<tr align="center">
				<td>盖章成功次数</td>
				<!-- <td colspan="3" rowspan="3"><div id="count_table"></div></td> -->
				<td><input type="text" id="daySuccess"></td>
				<td><input type="text" id="monthSuccess"></td>
				<td><input type="text" id="ziSuccess"></td>
			</tr>
			<tr align="center">
				<td>盖章失败次数</td>
				<td><input type="text" id="dayFail"></td>
				<td><input type="text" id="monthFail"></td>
				<td><input type="text" id="ziFail"></td>
			</tr>
			<tr align="center">
				<td>盖章总次数</td>
				<td><input type="text" id="dayCount"></td>
				<td><input type="text" id="monthCount"></td>
				<td><input type="text" id="ziCount"></td>
			</tr>
			<!-- <tr>
				<td>
					<div id="count_table"></div>
				</td>
			</tr> -->
		</table>
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3">服务端盖章操作日志列表</span>
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
					<tr style="display:none">
						<td align="center">
							<br>
							<input type="button" value="返回查询" class="BigButton"
								onclick="show_sch();" />
						</td>
					</tr>
				</table>
			</div>
			<div id="d_search" style="display: none">
				<form id="f_sch" action=""  name="form1" method="post">
					<%@include file="/inc/calender.jsp"%>
					<table class="TableBlock" width="90%" align="center">
					<tr>
					<td nowrap class="TableData" width="120">
						对象编号：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="obj_no" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						对象名称：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="obj_name" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						对象类型：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="obj_type" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData">
						IP：
					</td>
					<td nowrap class="TableData">
						<input type="text" name="user_ip" class="BigInput" size="20"
							maxlength="20">
						&nbsp;
					</td>
				</tr>
				<tr>
						<td nowrap class="TableData" width="120">
								用户名:
							</td>
						<td nowrap class="TableData">
						 <input type="text" name="create_name2"  readonly class="BigInput" size="20"
							maxlength="20">
						<input type="hidden" name="create_name"  class="BigInput" size="20"
							maxlength="20">
							<a href="" onclick="openmodwin();return false;">选择用户</a>
						&nbsp;
					</td>
						</tr>
				<tr>
					<td nowrap class="TableData" width="120">
						时间：
					</td>
					<td nowrap class="TableData">

						<input type="text" name="begin_time" size="20" maxlength="20"
							class="BigInput">
						<img onclick="setday(this,document.getElementById('begin_time'));"
							src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />
						至&nbsp;
						<input type="text" name="end_time" size="20" maxlength="20"
							class="BigInput">
						<img onclick="setday(this,document.getElementById('end_time'));"
							src="/Seal/images/menu/calendar.gif"
							style="height: 20; cursor: hand" border="0" />

					</td>
				</tr>
						<tr>
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="查询" class="BigButton"
									onclick="search();" />
								<input type="reset" value="清空" class="BigButton" />
								<input type="button" value="返回" class="BigButton"
									onclick="ret();" />
							</td>
						</tr>
					</table>
				</form>
			</div>
	</body>
</html>