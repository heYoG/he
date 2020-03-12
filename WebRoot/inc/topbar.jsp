<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/Seal/theme/2/topbar.css">
<link rel="stylesheet" type="text/css" href="/Seal/theme/2/style.css">
<script src="/Seal/js/ccorrect_btn.js"></script>
<script src="/Seal/js/sterm.js"></script>
<script src="/Seal/js/utility.js"></script>
<script src="/Seal/js/nongli.js"></script>
<script language="JavaScript">
var timerID = null;
var timerRunning = false;
function mdate()
{
	var now = new Date();
  var solarTerm=sTerm(parseInt(now.getFullYear()),parseInt(now.getMonth()+1),parseInt(now.getDate()));
   if(solarTerm != "")
      document.getElementById('mdate').innerHTML = solarTerm;
   else
   	{
      showDate();
    }
}
function stopclock (){
if(timerRunning)
clearTimeout(timerID);
timerRunning = false;
}

function startclock() {
stopclock();
showtime();
}

function showtime () {
var now = new Date();
var hours = now.getHours();
var minutes = now.getMinutes();
var seconds = now.getSeconds()
var timeValue=hours
timeValue += ((minutes < 10) ? ":0" : ":") + minutes
timeValue += ((seconds < 10) ? ":0" : ":") + seconds
	var xingqi=null;
 	switch(parseInt(now.getDay()))
 	 {
 	 	case 0:
 	 	xingqi="星期日";break;
 		case 1:
 	 	xingqi="星期一";break;
 	 	case 2:
 	 	xingqi="星期二";break;
 	 	case 3:
 	 	xingqi="星期三";break;
 	 	case 4:
 	 	xingqi="星期四";break;
 		case 5:
 	 	xingqi="星期五";break;
 	 	case 6:
 	 	xingqi="星期六";break;
 	 }
document.getElementById("date").innerHTML=now.getFullYear()+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日   "+xingqi;  
document.getElementById("time_area").innerHTML=timeValue;
timerID = setTimeout("showtime()",1000);
timerRunning = true;
}

</script>

</head>

<body topmargin="0" leftmargin="0" STYLE="margin:0pt;padding:0pt" onLoad="mdate();startclock();">
<table class="topbar" height="50" width="100%" border=0 cellspacing=0 cellpadding=0>
  <tr height=40>
  <!-- <td width="320" ><img src="../theme/2/logo.png" width="320" height="46" align="left"></td> -->
	<td width="400"><div id="banner_text" >
	  &nbsp;&nbsp;无纸化系统管理平台
	</div></td>
	<td width="200" valign="top">
      <div id="time"><span class="time_left"><span class="time_right">
       <span id="date"></span>&nbsp;&nbsp;</span></span><div style="margin-top:10px;"><span class="time_left"><span class="time_right"><span id='mdate'></span>       
       <img src="../images/time.gif" align="absmiddle">
        <span id="time_area"></span>&nbsp;
       </span></span></div>
      </div>
    </td>
  </tr>
</table>
</body>
</html>


