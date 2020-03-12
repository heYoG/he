
function QerPic(id) {
	this.id = id;
	this.x_text;
	this.y_text;
	this.y_max=100;
	this.x_length = 100;
	this.y_length = 100;
	this.td_width=50;
	this.div_width=30;
	this.div_color="blue";
	this.x_list = new Array();
}

QerPic.prototype.HtmlCode = function(){
	this.y_max=this.AvgY()*4;
	var str="";
	str+='<table border="0" cellpadding="0" cellspacing="0" align="center">';
	str+='<tr>';
	str+='<td valign="top" rowspan="2">';
	str+='<p align="right" style="line-height: 12px; margin-right:2">';
	for(var i=4;i>0;i=i-1) str+=this.AvgY()*i+this.BrStr();
	str+='0</p>';
	str+='</td>';
	str+='<td><div style="height:'+this.y_length+';width:1; BACKGROUND-COLOR: black"></div></td>';
	str+=this.ValueTd();
	str+="</tr><tr><td></td>";
	str+=this.TextTd();
	str+='</tr></table>';
	return str;
}
QerPic.prototype.BrStr = function (){
	var str="";
	for(var i=Math.round(this.y_length/50);i>0;i=i-1){
		str+="<br>";
	}
	return str;
}
QerPic.prototype.AddItem = function (item){
	this.x_list.push(item);
}
QerPic.prototype.AvgY = function (){
	var max=this.MaxOfData();
	var avg=max%4==0?max/4:Math.floor(max/4+1);
	return avg;
}
QerPic.prototype.ValueTd = function (){
	var str="";
	for(var i=0;i<this.x_list.length;i=i+1){
		str+='<td width="'+this.td_width+'" align="center" valign="bottom" title="';
		str+=this.x_list[i].value+'" style="BORDER-BOTTOM: #000000 1px solid;">';
		str+='<div style="width: ';
		str+=this.div_width+'; height: ';
		str+=this.DivHeight(this.x_list[i].value,this.y_max)+'; FONT-SIZE: 1px;color:white; BACKGROUND-COLOR: ';
		str+=this.x_list[i].color+'"></div>';
		str+='</td>';
	}
	return str;
}
QerPic.prototype.TextTd = function (){
	var str="";
	for(var i=0;i<this.x_list.length;i=i+1){
		str+='<td align="center" title="';
		str+=this.x_list[i].value+'">';
		str+=this.x_list[i].text;
		str+='<br><font size=1>('+this.x_list[i].value+')</font></td>';
	}
	return str;
}
QerPic.prototype.DivHeight = function (v,m){
	return (v/m)*this.y_length;
}
QerPic.prototype.MaxOfData = function (){
	var max=0;
	for(var i=0;i<this.x_list.length;i=i+1){
		if(this.x_list[i].value>max){
			max=this.x_list[i].value;
		}
	}
	return max;
}

function QerPicItem(v,t,color){
	if (typeof (color) == "undefined") {
		color = "blue";
	}
	this.value=v;
	this.text=t;
	this.color=color;
}

