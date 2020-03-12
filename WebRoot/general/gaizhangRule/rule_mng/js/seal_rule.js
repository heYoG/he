function makeArgDesc(f_id){
	var f=$(f_id);
	var type=f.type.value;
	var str="";
	if(false){
	}else if(type==1){
		str+=f.page.value+",";
		str+=f.x.value+",";
		str+=f.y.value;
	}else if(type==2){
		str+=f.Bookmark.value;
	}else if(type==3){
		str+=f.doctype.value+",";
		str+=f.mode.value+",";
		str+=f.pos.value+",";
		str+=f.spage.value+",";
		str+=f.sealpage.value;
	}else if(type==4){
		str+=f.Booktxt.value+",";
		str+=f.tspage.value+",";
		str+=f.tsealpage.value+",";
		str+=f.tpianyi.value+",";
		str+=f.spianyi.value;
	}else if(type==5){
		str+=f.Booktxt5.value+",";
		str+=f.tspage5.value+",";
		str+=f.tsealpage5.value+",";
		str+=f.tpianyi5.value;
	}else if(type==6){
		str+=f.mode6.value+",";
		str+=f.gap.value+",";
		str+=f.x6.value+",";
		str+=f.y6.value;
	}else if(type==7){
		str+=f.doctype7.value+",";
		str+=f.mode7.value+",";
		str+=f.pos7.value+",";
		str+=f.spage7.value+",";
		str+=f.sealpage7.value+",";
		str+=f.gapall.value;
	}
	return str;
}

function ruleTypeName(t){
	var str="";
	if(false){
	}else if(t==1){
		str="类型一（绝对坐标）";
	}else if(t==2){
		str="类型二（书签）";
	}else if(t==3){
		str="类型三（骑缝章）";
	}else if(t==4){
		str="类型四（文字-覆盖）";
	}else if(t==5){
		str="类型五（文字-后面）";
	}else if(t==6){
		str="类型六（多页绝对坐标）";
	}else if(t==7){
		str="类型七（多页骑缝章）";
	}
	return str;
}

function ruleDesc(type,desc){
	var str="";
	var args=desc.split(",");
	if(false){
	}else if(type==1){
		str="在第";
		str+=args[0]+"页的（";
		str+=args[1]+",";
		str+=args[2]+"）坐标处盖章（坐标范围：1-49999）";
	}else if(type==2){
		str="在书签\"";
		str+=args[0]+"\"处盖章";
	}else if(type==3){
		var mode=args[0]==0?"单面":"双面";
		var pos=args[1]==3?"右骑缝":"其他骑缝";
		str="在第"+args[3]+"页到";
		if(args[4]=="-1"){
			str+="最后1页";
		}else{
			str+="第"+args[4]+"页";
		}
		str+="的坐标（"+args[2]+"）处加盖";
		str+=mode+pos+"印章（坐标范围：1-49999）";
	}else if(type==4){
		str="在第"+args[1]+"页到";
		if(args[2]=="-1"){
			str+="最后1页";
		}else{
			str+="第"+args[2]+"页";
		}
		str+="中文字\""+args[0]+"\"上：";
		if(args[3]!=undefined&&args[3]!=0){
			if(args[3]<0){
				str+="向上偏移"+args[3];
			}else if(args[3]>0){
				str+="向下偏移"+args[3];
			}
		}
		if(args[4]!=undefined&&args[4]!=0){
			if(args[3]<0){
				str+="向左偏移"+args[3];
			}else if(args[3]>0){
				str+="向右偏移"+args[3];
			}
		}
		str+="加盖印章";
	}else if(type==5){
		str="在第"+args[1]+"页到";
		if(args[2]=="-1"){
			str+="最后1页";
		}else{
			str+="第"+args[2]+"页";
		}
		str+="中文字\""+args[0]+"\"后面";
		if(args[3]!=undefined&&args[3]!=0){
			if(args[3]<0){
				str+="向上偏移"+args[3];
			}else if(args[3]>0){
				str+="向下偏移"+args[3];
			}
		}
		str+="加盖印章";
	}else if(type==6){
		var mode="";
		if(args[0]=="1"){
			mode="奇数";
		}else if(args[0]=="2"){
			mode="偶数";
		}else if(args[0]=="3"){
			mode="每间隔"+args[1];
		}
		str="在"+mode+"页（";
		str+=args[2]+","+args[3]+"）坐标处加盖印章（坐标范围：1-49999）"
	}else if(type==7){
		var mode=args[0]==0?"单面":"双面";
		var pos=args[1]==3?"右骑缝":"其他骑缝";
		str="在第"+args[3]+"页到";
		if(args[4]=="-1"){
			str+="最后1页";
		}else{
			str+="第"+args[4]+"页";
		}
		str+="内每隔";
		str+=args[5]+"页的坐标（"+args[2]+"）处加盖";
		str+=mode+pos+"印章（坐标范围：1-49999）";
	}
	return str;
}

function setTypeValue(f,obj){
	var t=obj.rule_type;
	var argStr=obj.arg_desc;
	var args=argStr.split(",");
	if(false){
	}else if(t=="1"){
		f.page.value=args[0];
		f.x.value=args[1];
		f.y.value=args[2];
	}else if(t=="2"){
		f.Bookmark.value=args[0];
	}else if(t=="3"){
		f.doctype.value=args[0];
		f.mode.value=args[1];
		f.pos.value=args[2];
		f.spage.value=args[3];
		f.sealpage.value=args[4];
	}else if(t=="4"){
		f.Booktxt.value=args[0];
		f.tspage.value=args[1];
		f.tsealpage.value=args[2];
		if(args[3]!=undefined){
			f.tpianyi.value=args[3];
		}else{
			f.tpianyi.value=0;
		}
		if(args[4]!=undefined){
			f.spianyi.value=args[4];
		}else{
			f.spianyi.value=0;
		}
	}else if(t=="5"){
		f.Booktxt5.value=args[0];
		f.tspage5.value=args[1];
		f.tsealpage5.value=args[2];
		if(args[3]!=undefined){
			f.tpianyi5.value=args[3];
		}else{
			f.tpianyi5.value=0;
		}
	}else if(t=="6"){
		f.mode6.value=args[0];
		f.gap.value=args[1];
		f.x6.value=args[2];
		f.y6.value=args[3];
	}else if(t=="7"){
		f.doctype7.value=args[0];
		f.mode7.value=args[1];
		f.pos7.value=args[2];
		f.spage7.value=args[3];
		f.sealpage7.value=args[4];
		f.gapall.value=args[5];
	}
}