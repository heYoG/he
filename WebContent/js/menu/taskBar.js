/**
 * 获取系统时间
 */
function getTime(){
	var dt=new Date();
	document.getElementById("dvTaskBar").innerHTML='现在是北京时间:'+dt.getFullYear()+"年"+(dt.getMonth()+1)+"月"
	+dt.getDate()+"日"+dt.getHours()+"时"+dt.getMinutes()+"分"+dt.getSeconds()+"秒";
}

setInterval(getTime,1000);