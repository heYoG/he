<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>数据库列表</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="数据库列表">
		<script type="text/javascript"> 
			function ready(){
				var o = window.dialogArguments;
				var str = o.res;
				var array = new Array();
				array = str.split(",");
				var dblist = "";
				for(var i = 0;i<array.length;i++){
					dblist+="<a href='javascript:void(0);' onclick=\"getValue('"+array[i]+"')\">"+array[i]+"</a><br>";
				}
				document.getElementById("dblist").innerHTML = dblist;
			}
			
			function getValue(value){
				window.returnValue  =  value;
				window.close();
			}
		</script>
	</head>
	<body onload="ready()">
		<div id="dblist" style="padding: 5px; overflow: hidden;">
		</div>
	</body>
</html>
