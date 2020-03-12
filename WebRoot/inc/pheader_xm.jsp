<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>登录信息</title>

<link rel="stylesheet" type="text/css" href="../theme/6/style.css">
<link rel="stylesheet" type="text/css" href="../theme/6/pheader.css">
<script src="/Seal/js/ccorrect_btn.js"></script>
<script src="/Seal/js/sterm.js"></script>
<script src="/Seal/js/utility.js"></script>
<script language="JavaScript">
var timerID = null;
var timerRunning = false;
function showDate(){
	var now = new Date();
	document.getElementById("date").innerHTML=(now.getYear())+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日   ";
}

function stopclock(){
	if(timerRunning)
	clearTimeout(timerID);
	timerRunning = false;
}

function startclock(){
	stopclock();
	showtime();
}

function showtime(){
	var now = new Date();
	var hours = now.getHours();
	var minutes = now.getMinutes();
	var seconds = now.getSeconds();
	var timeValue=hours;
	timeValue += ((minutes < 10) ? "时0" : "时") + minutes;
	timeValue += ((seconds < 10) ? "分0" : "分") + seconds+"秒";
	document.getElementById("time_area").innerHTML=timeValue;
	timerID = setTimeout("showtime()",1000);
	timerRunning = true;
}

</script>
</head>
<body style="background-color:f1f1f1;" onload="showDate();startclock();;">
<div style="margin:20px 20px;line-height:200%;">
欢迎您：${current_user.user_name}<br>
<span id="date"></span> <span id="time_area"></span> 
</div>

</body>
</html>
