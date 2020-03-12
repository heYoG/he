

function QerXML(str){
//	var head=str.substring(str.indexOf("<?"), str
//				.indexOf("?>") + 2);
//	alert(head);
//	str=str.substring(str.indexOf(head)+head.length);
	this.childs=new Array();
	this.name;
	this.value;
	if(str.indexOf("<?")!=-1){
		this.toQerXML(this.noHead(str));
	}else{
		this.toQerXML(str);
	}
}

QerXML.prototype.noHead=function(str) {
	var head = str.substring(str.indexOf("<?"), str
			.indexOf("?>") + 2);
	return str.substring(str.indexOf(head) + head.length);
}

QerXML.prototype.toQerXML =function(str){
	var name=str.substring(str.indexOf("<")+1,str.indexOf(">"));// 找到第一个标签名
	this.name=name;
	var begin="<"+name+">";//起始标签
	var end="</"+name+">";//结束标签
	var nstr=str.substring(str.indexOf(begin)+begin.length,str.indexOf(end));
	if(""==nstr){
		this.value=nstr;
	}else if("<"!=nstr.charAt(0)){
		if(nstr.indexOf("\r\n")!=-1){
			nstr=nstr.replace("\r\n","<br>");
		}
		this.value=nstr;
	}else{
		this.childs=this.toListNote(nstr);
	}
};

QerXML.prototype.toListNote=function (str){
	while (""!=str) {
		var name=str.substring(str.indexOf("<")+1,str.indexOf(">"));
		var begin="<" + name + ">";
		var end="</" + name + ">";
		var nstr=str.substring(str.indexOf(begin),str.indexOf(end)+end.length);
		var obj=new QerXML(nstr);
		this.childs.push(obj);
		str=str.substr(str.indexOf(end)+end.length);
	}
	return this.childs;
}

QerXML.prototype.toString =function (i){
	var blank = "";
	for (var j = 0; j < i; j++) {
		blank += "  ";
	}
	var sb = "";
	if (this.value != null) {
		return "\r\n" + blank + " " + this.name + ":" + this.value;
	} else {
		sb+=("\r\n")+(blank)+("-")+(this.name);
		for (var k=0;k<this.childs.length;k++) {
			sb+=(this.childs[k].toString(i + 1));
		}
	}
	return sb;
}

QerXML.prototype.getValue=function (str){
	var name=(str.indexOf(".") == -1) ? str : str.substring(0, str
				.indexOf("."));
	var obj=this.getByName(name);
	if(name==this.name){
		obj=this;
	}else{
		if(null==obj.value){
			return obj.getValue(str.substring(str.indexOf(".") + 1));
		}else{
			return obj.value;
		}
	}
}

QerXML.prototype.getByName =function (name){
	var i=0;
	var begin=name.indexOf("[");
	if(begin!=-1){
		var end=name.indexOf("]");
		i=name.substring(begin+1, end)+0;
		name=name.substring(0, begin);
	}
	var k=0;
	for (var j=0;j<this.childs.length;j=j+1) {
		if (name==this.childs[j].name) {
			if(k==i){
				return this.childs[j];
			}
			k=k+1;
		}
	}
	return null;
}