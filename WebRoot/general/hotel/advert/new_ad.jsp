<%@page contentType="text/html;charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>无纸化管理平台</title>
<link rel="stylesheet" type="text/css"
	href="/Seal/theme/${current_user.user_theme}/style.css">
<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/AdSystem.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script src="/Seal/js/util.js"></script>
<script src="/Seal/js/Calendar.js"></script>
<script src="/Seal/js/ccorrect_btn.js"></script>
<script type='text/javascript' src='/Seal/js/String.js'></script>
<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
<script type="text/javascript" src='/Seal/js/tableUtil.js'></script>
<script src="/Seal/general/hotel/advert/js/ad_list.js"></script>
<script src="/Seal/general/hotel/advert/js/new_ad.js"></script>
<script src="/Seal/general/hotel/advert/js/fromZY.js"></script>
<script type="text/javascript">
	var user_no = "${current_user.user_id}";
	var user_name = "${current_user.user_name}";
	var user_ip = "${user_ip}";
	if (user_no == "") {
		top.location = "/Seal/login.jsp";
	}

	function openDeptList() {

		var b = window
				.showModalDialog(
						"/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",
						form1);
	}

	function load() {
		var ret = "${ret}";
		if (ret == "failed") {
			document.getElementById("dept").value = "${dept}";
			document.getElementById("title").value = "${title}";
			document.getElementById("name").value = "${name}";
			document.getElementById("dept_no").value = "${dept_no}";
			document.getElementById("approve_user").value = "${approve_user}";
		}
		if (ret == "succes") {
			document.getElementById("dept").value = "${dept}";
			document.getElementById("title").value = "${title}";
			document.getElementById("files").value = "${name}";
			document.getElementById("dept_no").value = "${dept_no}";
			document.getElementById("approve_user").value = "${approve_user}";
			newAdvert();
		}
	}

	function openmodwin() {
		var b = window
				.showModalDialog(
						"/Seal/depttree/dept_tree.jsp?p=true&&req=seal_temp&&user_no=${current_user.user_id }",
						form1);
	}

	function addInput(){//新增节点
 			var dv=document.getElementById("addDoc");  //获取列区域
 			var bu=document.getElementsByName("ad_filename").length;//获取当前file控件数目,不含后面即将新增的1个
 			var newInput=document.createElement("<input type='file' name='ad_filename' id='name"+bu+"'/>");//创建file控件节点
 			var newInput2=document.createElement("<input type='button' id='del"+bu+"' name='dele' value='删除此列' style='height:20' onclick='delrow(this)'/>");//创建按钮节点
 			var newInput1=document.createElement("<input type='text' name='aa' id='aa"+bu+"' value='此文件不符合要求，请重新选择！' style='background-color:#ff0033;width:200;display: none'/>");//创建文件错误提示节点
 			dv.appendChild(document.createElement("<P></P>"));//新增空白行
	 		dv.appendChild(newInput);//新增file节点
	 		dv.appendChild(newInput2);//新增button节点
	 		dv.appendChild(newInput1);//新增text节点
 		}
 		
 	function delrow(obj){//删除节点
 			var dv=document.getElementById("addDOC");//获取列区域
 			var idnum=obj.id.substring(3);//获取按钮所处位置数值
 			dv.removeChild(document.getElementById("name"+idnum+""));//删除file节点
 			dv.removeChild(document.getElementById("del"+idnum+""));//删除按钮
 			dv.removeChild(document.getElementById("aa"+idnum+""));//删除text域
 		}	

	function GetDate(nText) {
		reVal = window
				.showModalDialog(
						"../../../inc/showDate.htm",
						"",
						"status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
		var val1 = null;
		var val2 = null;
		if (reVal != null) {
			if (nText == 1) {
				//document.forms[0].starttime.value = reVal;
				document.getElementById("start_time").value = reVal;
			} else {
				if (nText == 2) {
					//document.forms[0].endtime.value = reVal;
					document.getElementById("end_time").value = reVal;
				}
			}
			val1 = toDate(document.getElementById("start_time").value);
			checkTime(val1, "start_time");
			val2 = toDate(document.getElementById("end_time").value);
			checkTime(val2, "end_time");
			if (val1 > val2) {
				alert("生效日期大于失效日期");
				document.getElementById("start_time").value = "";
				document.getElementById("end_time").value = "";
			}
		}
	}
	function checkTime(val, timename) {
		var myDate = new Date();
		var year = myDate.getFullYear();
		var month = myDate.getMonth() + 1;
		var day = myDate.getDate();
		var checkTimestr = toDate(year + "-" + month + "-" + day);
		if (checkTimestr > val) {
			if (timename == "start_time") {
				alert("生效时间必须大于当前时间，请重新选择生效时间！");
				document.getElementById("start_time").value = "";
			} else {
				alert("失效时间必须大于当前时间，请重新选择失效时间！");
				document.getElementById("end_time").value = "";
			}

		}
	}

	function toDate(str) {
		var sd = str.split("-");
		return new Date(sd[0], sd[1], sd[2]);
	}

	function checkFileNameTest() {
		var fileName = document.getElementsByName("ad_filename");
		for ( var i = 0; i < fileName.length; i++) {
			if (fileName[i].value == "") {
				alert("广告文件不能为空");
				return false;
			} else {
				var file1 = fileName[i].value;//文件绝对路径,下一个控件值
				file1 = file1.substring(file1.lastIndexOf("\\") + 1);//文件名
				fileSuffix1=file1.substring(file1.indexOf("."));//文件后缀
				for(var j=0;j<i;j++) {//仅有一个file控件时不执行
				var file2 = fileName[j].value;//前一个file控件值
				file2 = file2.substring(file2.lastIndexOf("\\") + 1);
				fileSuffix2=file2.substring(file2.indexOf("."));
					if (file2 == file1) {
						document.getElementsByName("aa")[i].value="文件名:"+file1+"重复,请重新选择!";
						document.getElementsByName("aa")[i].style.display="block";
						return false;
					}
					if(fileSuffix2!=".jpg"){
						document.getElementsByName("aa")[i].value="不支持"+file2+"的格式,请重新选择!";
						document.getElementsByName("aa")[i].style.display="block";
						return false;
					}	
				}
				if(fileSuffix1!=".jpg"){
						document.getElementsByName("aa")[i].value="不支持"+file1+"的格式,请重新选择!";
						document.getElementsByName("aa")[i].style.display="block";
						return false;
					}
				document.getElementsByName("aa")[i].style.display="none";					
			}
		}
				alert("验证通过!");

	}

	function checkFileName() {
		var filenames = document.getElementsByName("ad_filename");
		//alert("oldFilename:"+oldFilename[1].value);
		if (filenames[0].value != "") {//判断第一个file控件是否为空
			for ( var i = 0; i < filenames.length; i++) {//循环判断所有file控件
				var file = filenames[i].value;
				file = file.substring(file.lastIndexOf("\\") + 1);//获取文件名
				//alert("file:"+file);
				var suffixname = file.substring(file.indexOf("."));//获取文件格式(.jpg)
				//alert("suffixname:"+suffixname);

				for ( var j = 0; j < i; j++) {//判断下一个file控件中的文件名是否与上一个file控件文件名相同
					if (file == document.getElementsByName("ad_filename")[j].value
							.substring(document
									.getElementsByName("ad_filename")[j].value
									.lastIndexOf("\\") + 1)) {
						document.getElementsByName("aa")[i].value = file
								+ "文件重复，请重新选择!";//相同提示重选(重置提示内容)
						document.getElementsByName("aa")[i].style.display = "block";
						return false;//中断执行
					}
				}

				if (suffixname != ".jpg") {//为空或非jpg格式执行
					if (suffixname == "") {//判断是否为空,针对第二个开始的文件
						//alert("文件不能为空，请选择或删除文件输入框！");
						document.getElementsByName("aa")[i].value = "文件不能为空，请选择文件或删除文件输入框！";
						document.getElementsByName("aa")[i].style.display = "block";
						return false;
					} else {//判断是否为jpg格式
						document.getElementsByName("aa")[i].value = "不支持"
								+ file + "的格式，请重新选择！";
						document.getElementsByName("aa")[i].style.display = "block";
						return false;
					}
				}
				document.getElementsByName("aa")[i].style.display = "none";//第一次选择不符合，第二次符合刷新显示(去除第一次显示不符合提示)
			}
		} else if (filenames[0].value == "") {
			alert("请添加广告文件");
			return false;
		}
		fileUpLoad();
	}
</script>
</head>
<body class="bodycolor" onload="load()">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big"><img src="/Seal/images/menu/seal.gif"> <span
				class="big3"> 新增广告： </span></td>
		</tr>
	</table>
	<center>
		<br>
		<form target="ctz_bi_main" id="f_new" enctype="multipart/form-data"
			action="/Seal/newsAdvert.do" method="post" name="form1">
			<table class="TableBlock" width="80%" id="tb_info">
				<tr>
					<td width="15%" class="TableContent">广告标题：</td>
					<td nowrap class="TableData"><input type="text"
						name="ad_title" id="title" class="BigInput" /></td>
				</tr>
				<tr>
					<td class="TableContent">广告文件：</td>
					<td nowrap class="TableData" id="addDoc"><input type="hidden"
						name="filename" id="files" /> <input type="file"
						name="ad_filename" id="name" class="BigInput"> <input
						type="button" id="add" value="添加文件" style="height:22"
						onclick="addInput()" /> <input type="text" name="aa"
						value="此文件不符合要求，请重新选择！"
						style="background-color:#ff0033;width:200;display: none" /><br>
						<c:if test="${!empty message}">
							<font color='red'>${message}</font>
						</c:if></td>
				</tr>
				<tr id="deptf">
					<td class="TableContent">所属单位：</td>
					<td nowrap class="TableData"><input type="hidden"
						name="ad_dept" /> <input type="hidden" name="dept_no"
						id="dept_no" value="${seal.dept_no }"> <input type="text"
						name="dept_name" id="dept" readonly="readonly"
						value="${seal.dept_name }" /> <input type="button" value="选 择"
						class="SmallButton" onclick="return openDeptList()" title="选择部门">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width=100>审批人：</td>
					<td class="TableData"><input class="BigStatic"
						name="approve_user" id="approve_user" type="hidden"
						value="${current_user.user_id }"> <input class="BigStatic"
						name="approve_name" id="approve_name" type="text" readonly
						value="${current_user.user_name }"> <a href=""
						onclick="openmodwin();return false;">选择审批人</a></td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="120">生效时间：</td>
					<td nowrap class="TableData"><input type="text"
						id="start_time" name="starttime" size="10" maxlength="10"
						class="BigInput" onfocus="this.blur()"> <img
						onclick="GetDate(1);" src="../../../images/menu/calendar.gif"
						style="height: 20; cursor: hand" border="0" /> 至&nbsp; <input
						type="text" id="end_time" name="endtime" size="10" maxlength="10"
						class="BigInput" onfocus="this.blur()"> <img
						onclick="GetDate(2);" src="../../../images/menu/calendar.gif"
						style="height: 20; cursor: hand" border="0" /></td>
				</tr>
				<tr>
					<td nowrap class="TableContent" width=120>广告轮播时间：</td>
					<td class="TableData"><input class="BigStatic"
						name="scorlltime" id="scorlltime" type="text">秒</td>
				</tr>
				<tr>
					<td colspan="4" align="center" nowrap class="TableControl"><input
						type="button" value="确定" onclick="checkFileName()"
						class="BigButton" /> &nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>