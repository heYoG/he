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
		<script type='text/javascript' src='/Seal/dwr/interface/AdSystem.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/String.js"></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/hotel/advert/js/ad_list_history.js"></script>
		<script src="/Seal/general/hotel/advert/js/new_ad.js"></script>
		<script src="/Seal/general/hotel/advert/js/fromZY.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
	
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		
		function openDeptList(form) {
		 var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form);
  		 }
  		 
  		  function openmodwin() {
			var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?p=true&&req=seal_temp&&user_no=${current_user.user_id }",form1);
 		}
  		 
  		 function addInput(){
 			var dv=document.getElementById("add_fileInput");  //获取列区域
 			var bu=document.getElementsByName("ad_name").length;
 			var newInput=document.createElement("<input type='file' name='ad_name' id='ad_name"+bu+"'/>");
 			var newInput2=document.createElement("<input type='button' id='del"+bu+"' name='dele' value='删除此列' style='height:20' onclick='delrow(this)'/>");
 			var newInput1=document.createElement("<input type='text' name='aa' id='aa"+bu+"' value='此文件不符合要求，请重新选择！' style='background-color:#ff0033;display: none'/>");
 			dv.appendChild(document.createElement("<P></P>"));
	 		dv.appendChild(newInput);
	 		dv.appendChild(newInput2);
	 		dv.appendChild(newInput1);
 		}
 		
 		function delrow(obj){
 			var dv=document.getElementById("add_fileInput");
 			var idnum=obj.id.substring(3);
 			dv.removeChild(document.getElementById("ad_name"+idnum+""));
 			dv.removeChild(document.getElementById("del"+idnum+""));
 			dv.removeChild(document.getElementById("upd"+idnum+""));
 			dv.removeChild(document.getElementById("aa"+idnum+""));
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
					if(str1.indexOf("-")==-1){
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
						if(str1.indexOf("-")==-1){
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
			checkTime(val1, "start_time");
			val2 = toDate(document.getElementById("end_time").value);
			checkTime(val2, "end_time");
			if(val1 > val2){
				alert("生效日期大于失效日期");
				document.getElementById("start_time").value = "";
				document.getElementById("end_time").value = "";
			}
			};
	
		}
		function checkTime(val,timename){
			var myDate=new Date();
			var year=myDate.getFullYear();
			var month=myDate.getMonth()+1;
			var day=myDate.getDate();
			var checkTimestr=toDate(year+"-"+month+"-"+day);
			if(checkTimestr>val){
				if(timename=="start_time"){
					alert("生效时间必须大于当前时间，请重新选择生效时间！");
					document.getElementById("start_time").value = "";
				}else{
					alert("失效时间必须大于当前时间，请重新选择失效时间！");
					document.getElementById("end_time").value = "";
				}
				
			}
		}
 		
 		function toDate(str){
			var sd = str.split("-");
			return new Date(sd[0],sd[1],sd[2]);
		}
		
		function checkFileName(){
			var filenames=document.getElementsByName("ad_name");
			var oldFilename=document.getElementsByName("ad_filename");
			if(filenames.length!=0){
				for(var i=0;i<filenames.length;i++){
					var file = filenames[i].value;
					file = file.substring(file.lastIndexOf("\\")+1);
					//alert("file:"+file);
						var suffixname=file.substring(file.indexOf("."));
						//alert("suffixname:"+suffixname);
						for(var j=0;j<i;j++){
							if(file==document.getElementsByName("ad_name")[j].value.substring(document.getElementsByName("ad_name")[j].value.lastIndexOf("\\")+1)){
								//alert(file+"文件重复，请重新选择");
								document.getElementsByName("aa")[i].value=file+"文件重复，请重新选择!";
								document.getElementsByName("aa")[i].style.display="block";
								return;
							}
						}
						if(suffixname!=".jpg"){
							if(suffixname==""){
								//alert("文件不能为空，请选择或删除文件输入框！");
								document.getElementsByName("aa")[i].value="文件不能为空，请选择文件或删除文件输入框！";
								document.getElementsByName("aa")[i].style.display="block";
								return false;
							}else{
								//alert("不支持"+file+"的格式，请重新选择！");
								document.getElementsByName("aa")[i].value="不支持"+file+"的格式，请重新选择！";
								document.getElementsByName("aa")[i].style.display="block";
								return false;
							}
						}
						document.getElementsByName("aa")[i].style.display="none";
				}
			}else{
				alert("请添加广告文件");
				return false;
			}
		formatTime();
		objUpd();
		}
		
		function formatTime(){
			var str1=document.getElementById("start_time").value;
			if(str1.indexOf("-")==-1){
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
			checkTime(toDate(str2), "start_time");
			}
			var str3=document.getElementById("end_time").value;
			if(str3.indexOf("-")==-1){
				var str4="";
				for(var i=0;i<str3.length;i++){
					if(str3.charAt(i)>=0&&str3.charAt(i)<=9){
						if(isNaN(str3.charAt(i+1))&&!isNaN(str3.charAt(i-1))){
							str4=str4+str3.charAt(i)+"-";
						}else if(isNaN(str3.charAt(i+1))&&isNaN(str3.charAt(i-1))){
							str4=str4+"0"+str3.charAt(i)+"-";
						}else{
							str4=str4+str3.charAt(i);
						}
					};
				}
			str4=str4.substring(0,str4.length-1);
			document.getElementById("end_time").value=str4;
			checkTime(toDate(str4), "end_time");
			}
		}
		function filenamelist(filenames){
			var filenamelist=filenames.split(",");
			for(var i=0;i<filenamelist.length;i++){
				filename=filenamelist[i];
				var dv=document.getElementById("add_fileInput");  //获取列区域
	 			var bu=document.getElementsByName("ad_name").length;
	 			var newInput=document.createElement("<input type='text' name='ad_name' id='ad_name"+bu+"' value='"+filename+"'/>");
	 			var newInput3=document.createElement("<input type='button' name='upd' id='upd"+bu+"' value='修改' onclick='updfile(this)'/>");
	 			var newInput2=document.createElement("<input type='button' id='del"+bu+"' name='dele' value='删除此列' style='height:20' onclick='delrow(this)'/>");
	 			var newInput1=document.createElement("<input type='text' name='aa' id='aa"+bu+"' value='此文件不符合要求，请重新选择！' style='background-color:#ff0033;display: none'/>");
	 			dv.appendChild(document.createElement("<P></P>"));
		 		dv.appendChild(newInput);
		 		dv.appendChild(newInput2);
		 		dv.appendChild(newInput3);
		 		dv.appendChild(newInput1);
			}
		}
		
		function updfile(obj){
			var dv=document.getElementById("add_fileInput");
 			var idnum=obj.id.substring(3);
 			var newInput=document.createElement("<input type='file' name='ad_name' id='ad_name"+idnum+"'/>");
	 		dv.replaceChild(newInput,document.getElementById("ad_name"+idnum+""));
		}
		</script>
	</head>
	<body class="bodycolor" onload="list_load();">
		<table border="0" width="100%" cellspacing="0" cellpadding="3"
			class="small">
			<tr>
				<td class="Big">
					<img src="/Seal/images/menu/seal.gif" align="absmiddle">
					<span class="big3"> 广告列表</span>
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
							<br>
							<input type="button" value="高级搜索" class="BigButton"
								onclick="show_sch()" />
						</td>
					</tr>
				</table>
			</div>


			<div id="d_search" style="display: none" >
				<form id="ad_sch" action="" method="post" name="form2">
					<table class="TableBlock" id="tb_info">
						<tr>
						<td width="20%" class="TableContent">
							广告标题：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_title" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							文件名称：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_filename" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							创建时间：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_ctime" class="BigInput" onclick="GetDate()"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							广告状态：
						</td>
						<td nowrap class="TableData">
							<select name="ad_state">
								<option value="-1">--请选择--</option>
								<option value="0">--已激活--</option>
								<option value="1">--已注销--</option>
							</select>
						</td>
					</tr>
					<tr id="s_dept">
							<td class="TableContent">
								所属单位：
							</td>
							<td nowrap class="TableData">
								<input type="hidden" name="ad_dept" />
								<input type="hidden" name="dept_no" value="${seal.dept_no }">
								<input type="text" name="dept_name" readonly="readonly"
									value="${seal.dept_name }" />
								<input type="button" value="选 择" class="SmallButton"
									onclick="return openDeptList(form2);" title="选择部门">
							</td>
						</tr>
					<tr>
						<tr>
							<td colspan="2" align="center" nowrap class="TableControl">
								<input type="button" value="搜索" class="BigButton"
									onclick="searchAD()" />
								<input type="reset" value="清空" class="BigButton" />
								<input type="button" value="返回" class="BigButton"
									onclick="ret();" />
							</td>
						</tr>
					</table>
				</form>
			</div>

			<div id="overlay"></div>


			<div id="detail" class="ModalDialog" style="width: 550px;">
				<div class="header">
					<span id="title" class="title">广告详细信息</span><a class="operation"
						href="javascript:HideDialog('detail');"><img
							src="/Seal/images/close.png" /> </a>
				</div>
				<div id="apply_body" class="body" align="center">
					<form id="f_edit" enctype="multipart/form-data" action="" method="post" name="form1">
						<input type="hidden" name="old_title" >
						<input type="hidden" name="old_filename"/> 
						<input type="hidden" name="old_ctime"/>
						<input type="hidden" name="old_dept"/>
						<input type="hidden" name="ad_id"/>
						<table class="TableBlock" width="95%" id="tb_info">
							<tr>
						<td width="20%" class="TableContent">
							广告标题：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_title" class="BigInput"/>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							文件名称：
						</td>
						<td nowrap class="TableData" id="addDOC">
							<input type="button" id="ad" name="add" value="添加文件" onclick="addInput()" style="width:70" />
							<!-- <input name="filebutton" type="button" value="修改" onclick="changeFile()" />
							<input type="file" name="ad_name" style="display: none" >
							<input type="button" id="ad" name="add" value="添加文件" onclick="addInput()" style="display: none;width:70" />
							<input type="text" name="aa" value="此文件不符合要求，请重新选择！" style="background-color:#ff0033;width:200;display: none"/></br> -->
							<div id="add_fileInput">
							<input type="hidden" id="adfilename" name="ad_filename" readonly="readonly" class="BigInput"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="TableContent">
							创建时间：
						</td>
						<td nowrap class="TableData">
							<input type="text" name="ad_ctime" readonly="readonly" class="BigInput"/><font style="color: red">创建时间不可修改</font>
						</td>
					</tr>
					<tr id="u_dept">
							<td class="TableContent">
								所属单位：
							</td>
							<td nowrap class="TableData">
								<input type="hidden" name="ad_dept" />
								<input type="hidden" name="dept_no" value="${seal.dept_no }">
								<input type="text" name="dept_name" readonly="readonly"
									value="${seal.dept_name }" />
								<input type="button" value="选 择" class="SmallButton"
									onclick="return openDeptList(form1);" title="选择部门">
							</td>
						</tr>
					<tr>
					<td nowrap class="TableContent" width=100>
						审批人：
					</td>
					<td class="TableData">
						<input class="BigStatic" name="approve_user" id="approve_user" type="hidden"
							value="${current_user.user_id }">
						<input class="BigStatic" name="approve_name" id="approve_name" type="text" readonly
							value="${current_user.user_name }">
						<a href="" onclick="openmodwin();return false;">选择审批人</a>
					</td>
				</tr>
					<tr>
					<td nowrap class="TableData" width="120">
						生效时间：
					</td>
					<td nowrap class="TableData">

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
				</tr>
				<tr>
					<td nowrap class="TableContent" width=120>
						广告轮播时间：
					</td>
					<td class="TableData">
						<input class="BigStatic" name="scorlltime" id="scorlltime" type="text">秒
					</td>
				</tr>
						</table>
					</form>
				</div>
				<div id="footer" class="footer">
					<input class="BigButton" onclick="checkFileName();" type="button"
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
							<br></td>
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