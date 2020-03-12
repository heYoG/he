function load(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelDic.showHotelDics(cb_data);
}

function cb_data(bs){
		var selcert_no=document.all("ITEM_NAME");
		selcert_no.options.length = 0;
		for(var i=0;i<bs.length;i=i+1){
			 var varItem = new Option(bs[i].cshowname,bs[i].cname);
		 	 selcert_no.options.add(varItem);   
		}
}

function setParent(){
	var field_name;
  	var sss = document.getElementsByName('is_tongyong');
    if(sss[0].checked==true){
		field_name = document.getElementById('ITEM_NAME').value;
		
	}else if(sss[1].checked==true){
		field_name = document.getElementById('writeName').value;
	}
  	parent.setField(field_name,document.getElementById('ITEM_TYPE').value,document.getElementById('FONT_TYPE').value,document.getElementById('FONT_SIZE').value,document.getElementById('FONT_COLOR').value,document.getElementById('BORDER_TYPE').value,document.getElementById('FONT_X').value,document.getElementById('FONT_Y').value,document.getElementById('IS_READ_ONLY').value,document.getElementById('TIPSINFO').value,document.getElementById('ITEM_DATA_TYPE').value);
}

function change_ITEM_TYPE(){
  	var typevalue = document.getElementById('ITEM_TYPE').value;
  	if(typevalue==2){
		document.all.tr_is_tongyong.style.display="none";
		document.all.tongyong.style.display="none";
		document.all.nottongyong.style.display="none";
		document.all.tr_ITEM_DATA_TYPE.style.display="none";
		document.all.tr_IS_READ_ONLY.style.display="none";
		document.all.tr_TIPSINFO.style.display="none";
		document.all.tr_FONT_TYPE.style.display="none";
		document.all.tr_FONT_SIZE.style.display="none";
		document.all.tr_FONT_COLOR.style.display="none";
		document.all.tr_BORDER_TYPE.style.display="none";
		document.all.tr_FONT_X.style.display="none";
		document.all.tr_FONT_Y.style.display="none";
  	}else if(typevalue==3){
  		document.all.tr_is_tongyong.style.display="";
		var sss = document.all.is_tongyong;
		if(sss[0].checked==true){
			document.all.tongyong.style.display="";
			document.all.nottongyong.style.display="none";
		}else if(sss[1].checked==true){
			document.all.tongyong.style.display="none";
			document.all.nottongyong.style.display="";
		}
		document.all.tr_ITEM_DATA_TYPE.style.display="";
		document.all.tr_IS_READ_ONLY.style.display="";
		document.all.tr_TIPSINFO.style.display="";
		document.all.tr_FONT_TYPE.style.display="";
		document.all.tr_FONT_SIZE.style.display="";
		document.all.tr_FONT_COLOR.style.display="";
		document.all.tr_BORDER_TYPE.style.display="";
		document.all.tr_FONT_X.style.display="";
		document.all.tr_FONT_Y.style.display="";
  	}
  }
  
  
  function changeTongYong(){
  	var sss = document.all.is_tongyong;
	if(sss[0].checked==true){
		document.all.tongyong.style.display="";
		document.all.nottongyong.style.display="none";
	}else if(sss[1].checked==true){
		document.all.tongyong.style.display="none";
		document.all.nottongyong.style.display="";
	}else{
		alert("选择类型出错!请重新操作!");
	}
  }