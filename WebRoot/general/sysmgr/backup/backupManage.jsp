<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		
		<script type='text/javascript' src='/Seal/dwr/interface/Backup.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		
		<style type="text/css">
		#customers {
			font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
			width: 100%;
			border-collapse: collapse;
		}
		
		#customers td,#customers th {
			font-size: 1em;
			border: 1px solid #99bbe8;
			padding: 3px 7px 2px 7px;
		}
		
		#customers th {
			font-size: 1.1em;
			text-align: left;
			padding-top: 5px;
			padding-bottom: 4px;
			background-color: #E0ECFF;
			color: #000000;
		}
		
		#customers tr.alt td {
			color: #000000;
			background-color: #EAF2D3;
		}
		</style>
		
		<title>数据备份与恢复</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="数据备份恢复">
		
		<script>
			
		function ready(){
			document.backupform.cfrequencysel.value=document.getElementById("cfrequency").value;
			document.backupform.ctimesel.value=document.getElementById("ctime").value;
			document.backupform.cstatussel.value=document.getElementById("cstatus").value;
			document.getElementById("cdatesel").value=document.getElementById("cdate").value;
			changeState();
	   	}
	   	function changeState() {
			var indexvalue = document.backupform.cfrequencysel.value;
			var oTargetSel = document.getElementById("cdatesel");
			if(indexvalue==0){
				oTargetSel.disabled = "disabled";
			}else if(indexvalue==1){
				oTargetSel.disabled = "";
				oTargetSel.options.length = 0; 
				for(var i=1;i<8;i++ ){
					var oOption = document.createElement("option"); 
					oOption.text = "星期"+convert(i);
					oOption.value = i;
					oTargetSel.add(oOption);
				}
			}else if(indexvalue==2){
				oTargetSel.disabled = "";
				oTargetSel.options.length = 0; 
				for(var i=1;i<29;i++ ){
					var oOption = document.createElement("option"); 
					oOption.text = i;
					oOption.value = i;
					oTargetSel.add(oOption);
				}
			}
		}
		function convert(i){
			if(i==1){return "一";
			}else if(i==2){return "二";
			}else if(i==3){return "三";
			}else if(i==4){return "四";
			}else if(i==5){return "五";
			}else if(i==6){return "六";
			}else if(i==7){return "日";
			}else{return "";}
		}
		
		//启动备份计划
		function backup(){
			if(window.confirm("确定要修改备份计划?")){
				var cstatusvalue = document.backupform.cstatussel.value;
				var cfrequencyvalue = document.backupform.cfrequencysel.value;
				var cdatevalue = document.backupform.cdatesel.value;
				var ctimevalue = document.backupform.ctimesel.value;
				Backup.saveBackup(cstatusvalue,cfrequencyvalue,cdatevalue,ctimevalue,function(res){
					alert(res);
				})
			}
		}
		
		//手动备份
		function backupManually(){
			Backup.backup(function(res){
				alert(res);
			})
		}
		
		//数据库还原
		function recoverys(){
			var filepath = document.getElementById("filename").value;
			var backuppath = document.getElementById("backuppath").value;
			if(filepath==""){
	  			 alert("请选择要还原的数据库!");
	  			 return;
	  		 }else{
		  		 if(window.confirm("确定要还原此数据库文件吗?")){
					if(window.confirm("请再次确认要还原此数据库文件吗?")){
						Backup.recoverys(filepath,backuppath,function(res){
							alert(res);
						})
					}
				}
  		 	}
		}
		
		//选择要恢复的数据库文件
		function selectDBFile(){
			var backuppath = document.getElementById("backuppath").value;
			Backup.databaseList(backuppath,function(res){
				var obj = new Object();
				obj.res=res;
				var object = window.showModalDialog("general/sysmgr/backup/databaseList.jsp",obj,"dialogHeight:200px;dialogWidth:200px;scroll:yes","resizable:true");
				if(object){
					document.getElementById("filename").value = object;
				}
			})
		}
			
		</script>
	</head>

	<body onload="ready()">
		<div>
			<div>
				<input type="hidden" id="cstatus" value="${backup.status}"/>
				<input type="hidden" id="cdate" value="${backup.date}"/>
				<input type="hidden" id="ctime" value="${backup.time}"/>
				<input type="hidden" id="cfrequency" value="${backup.frequency}"/>
				<form id="backupform" name="backupform" action="" method="post">
				<table id="customers">
					<tr>
						<th>
							数据库备份
						</th>
						<th>
						</th>
					</tr>
					<tr>
						<td>是否开启定时备份:</td>
						<td>
							<select name="cstatussel" id="cstatussel" >
								<option value="0">启用</option>
								<option value="1" selected="selected">禁用</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>频率:</td>
						<td>
							<select id="cfrequencysel" name="cfrequencysel" onchange="changeState()">
								<option value="0">每天</option>
								<option value="1" selected="selected">每周</option>
								<option value="2">每月</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>日期:</td>
						<td>
							<select name="cdatesel" id="cdatesel">
								<option value="1">星期一</option>
								<option value="2">星期二</option>
								<option value="3">星期三</option>
								<option value="4">星期四</option>
								<option value="5">星期五</option>
								<option value="6">星期六</option>
								<option value="7">星期日</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>时间:</td>
						<td>
							<select name="ctimesel" id="ctimesel">
								<option value="0">0:00</option>
								<option value="1">1:00</option>
								<option value="2">2:00</option>
								<option value="3">3:00</option>
								<option value="4">4:00</option>
								<option value="5">5:00</option>
								<option value="6">6:00</option>
								<option value="7">7:00</option>
								<option value="8">8:00</option>
								<option value="9">9:00</option>
								<option value="10">10:00</option>
								<option value="11">11:00</option>
								<option value="12">12:00</option>
								<option value="13">13:00</option>
								<option value="14">14:00</option>
								<option value="15">15:00</option>
								<option value="16">16:00</option>
								<option value="17">17:00</option>
								<option value="18">18:00</option>
								<option value="19">19:00</option>
								<option value="20">20:00</option>
								<option value="21">21:00</option>
								<option value="22">22:00</option>
								<option value="23">23:00</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							操作:
						</td>
						<td>
							<input type="button"  onclick="backup()" value="保存配置并启动"/> 
						</td>
					</tr>
				</table>
				</form>
			</div>
			<div>
			<form id="recoveryform" name="recoveryform" action="" method="post" enctype="multipart/form-data">
				<table id="customers">
					<tr>
						<th>
							手动备份
						</th>
					</tr>
					<tr>
						<td>
							手动备份:
							<input type="button"  onclick="backupManually()" value="手动备份" />
						</td>
					</tr>
				</table>
			</form>
			</div>
			<div>
			<form id="recoveryform" name="recoveryform"  method="post">
				<table id="customers">
					<tr>
						<th>
							数据恢复
						</th>
					</tr>
					<tr>
						<td>
							<input type="hidden" value="${backuppath}" id="backuppath"/>
							数据恢复:${backuppath}
							<input type="text" id="filename" name="filename" readonly="readonly"/>
							<input type="button"   onclick="selectDBFile()" value="选择文件">
							<input type="button" onclick="recoverys()" value="恢复"/>
						</td>
					</tr>
				</table>
			</form>
			</div>
		</div>
	</body>
</html>
