//self.moveTo(0,0);
//self.resizeTo(screen.availWidth,screen.availHeight);
var onm=0;//鼠标按键状态 0是无按键，1是鼠标右键
var x=0;
var y=0;
var pagewrite="";//签名内容
var ones=1;//起笔状态

/***********************************************
******************获取http对象*******************
************************************************/
function gethttps(){
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}

/***********************************************
******************获取url变量*******************
************************************************/
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = location.search.substr(1).match(reg);
	if (r !== null) {
		return unescape(r[2]);
	}
	return null;
}

/***********************************************
*******************笔迹坐标获取******************
************************************************/
function mouseCoords(ev,page)
{
	if(ev.offsetX || ev.offsetY){ 
		return {x:ev.offsetX, y:ev.offsetY}; 
	}
	return {
		x:ev.layerX-document.getElementById("page"+page).offsetLeft, 
		y:ev.layerY-document.getElementById("page"+page).offsetTop
	};
}

/***********************************************
************判断浏览器是否支持html5*************
************************************************/

function checkobj(){
	try{
		var b=document.getElementById("page");
		var cxtb=b.getContext("2d");
	}catch(e){
		document.getElementById("div_book").innerHTML="您的浏览器还不支持html5，请更换浏览器再试";
		return false;
	}
}

/***********************************************
********************初始化对象*****************
************************************************/

function init()
{
	var filename=getUrlParam("FileName");
	var pagenum=getUrlParam("PageNum");
	var htmlcaons="";
	for(var i=0;i<pagenum;i++){
		htmlcaons+="<canvas id='page"+i+"' width='800' height='1100' class='div_cans' onmouseout='onm=0;' onmousedown='onm=1;' onmouseup='mup()' onmousemove='sign(event,"+i+")'></canvas>";
	}
	document.getElementById("div_book").innerHTML=htmlcaons;
	for(var i=0;i<pagenum;i++){
		var cname="page"+i;
		var asd = new CanvasDrawr({id:cname, size: 2 });
		showtxt(i);
		showbook(filename,i);
	}
}

/***********************************************
***************设置预设显示内容*****************
************************************************/

function showtxt(page){
	var b=document.getElementById("page"+page);
	var cxtb=b.getContext("2d");
	cxtb.font="60px impact";
	cxtb.fillStyle="#CCCCCC";
	cxtb.textAlign="center";
	cxtb.fillText('努力加载中。。。',450,500,400);
	cxtb.restore();
	cxtb.closePath();
}

/***********************************************
***************设置显示文档内容*****************
************************************************/

function showbook(filename,page){
	var book=new Image();
	book.src="pic/"+filename+"_"+page+".gif";
	book.onload=function(){
		var b=document.getElementById("page"+page);
		var cxtb=b.getContext("2d");
		cxtb.drawImage(book,0,0,800,1100);
	}
}

/***********************************************
*******************手写笔迹******************
************************************************/

function sign(ev,page){
	ev= ev || window.event; 
	var mousePos = mouseCoords(ev,page); 
	if(onm==1){
		var b=document.getElementById("page"+page);
		var cxtb=b.getContext("2d");
		cxtb.strokeStyle="#FF0000";
		cxtb.lineWidth=2;
		cxtb.miterLimit=0;
		if(ones==1){
			pagewrite+="<"+page+","+document.getElementById("page"+page).width+","+document.getElementById("page"+page).height+","+cxtb.strokeStyle+"("+mousePos.x+","+mousePos.y+","+cxtb.lineWidth+";";
		}else{
			pagewrite+=mousePos.x+","+mousePos.y+","+cxtb.lineWidth+";";
		}
		if(x==0 && y==0){
			cxtb.moveTo(mousePos.x,mousePos.y);
		}
		x=mousePos.x;
		y=mousePos.y;
		cxtb.lineTo(x,y);
		cxtb.stroke();
		ones=0;
	}else{
		x=0;
		y=0;
	}
}

/***********************************************
*******************落笔结束********************
************************************************/

function mup(){
	onm=0;
	ones=1;
	pagewrite+=")>";
}

/***********************************************
*******************保存笔迹******************
************************************************/

function Save(){
	document.getElementById("xy").value=pagewrite;
}