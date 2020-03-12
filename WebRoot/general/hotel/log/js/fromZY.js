
function check_type(f_id){
	var f=$(f_id);
	if(f.type.value==1){
		type1.style.display="";
		type2.style.display="none";
		type3.style.display="none";
		type4.style.display="none";
		type5.style.display="none";
		type6.style.display="none";
		type7.style.display="none";
	}else if(f.type.value==2){
		type1.style.display="none";
		type2.style.display="";
		type3.style.display="none";
		type4.style.display="none";
		type5.style.display="none";
		type6.style.display="none";
		type7.style.display="none";
	}else if(f.type.value==3){
		type1.style.display="none";
		type2.style.display="none";
		type3.style.display="";
		type4.style.display="none";
		type5.style.display="none";
		type6.style.display="none";
		type7.style.display="none";
	}else if(f.type.value==4){
		type1.style.display="none";
		type2.style.display="none";
		type3.style.display="none";
		type4.style.display="";
		type5.style.display="none";
		type6.style.display="none";
		type7.style.display="none";
	}else if(f.type.value==5){
		type1.style.display="none";
		type2.style.display="none";
		type3.style.display="none";
		type4.style.display="none";
		type5.style.display="";
		type6.style.display="none";
		type7.style.display="none";
	}else if(f.type.value==6){
		type1.style.display="none";
		type2.style.display="none";
		type3.style.display="none";
		type4.style.display="none";
		type5.style.display="none";
		type6.style.display="";
		type7.style.display="none";
	}else if(f.type.value==7){
		type1.style.display="none";
		type2.style.display="none";
		type3.style.display="none";
		type4.style.display="none";
		type5.style.display="none";
		type6.style.display="none";
		type7.style.display="";
	}
}

function isEnAndNum(s){
	 var reg = /^[a-z0-9（）：.-_\u4e00-\u9fa5]*$/gi;
	 if(reg.test(s.value)){
		 return true;
	 }else{
	 	 alert("文字含有非法字符！");
		 s.focus();
		 s.select();
		 return false;
	 }
 }


function sealpagechang(){
	if(f.mode.value==5 || f.mode.value==6){
		f.sealpage.value=2;
		f.doctype.value=0;
		f.sealpage.disabled="disabled";
		f.doctype.disabled="disabled";
	}else{
		f.sealpage.value="";
		f.sealpage.disabled="";
		f.doctype.disabled="";
	}
}