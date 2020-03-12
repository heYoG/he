
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
function check(f_id){
	var f=$(f_id);
	if(f.type.value==1){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.page.value ==""){
			alert("页码不可为空");
			return false;
		}else
		if(isNaN(f.page.value)){
			alert("页码应为数字");
			return false;
		}else
		if(f.x.value ==""){
			alert("横向位置不可为空");
			return false;
		}else
		if(isNaN(f.x.value)){
			alert("横向位置应为数字");
			return false;
		}else
		if(f.x.value > 49999 || f.x.value < 1){
			alert("横向位置无效数字");
			f.x.value=49999;
			return false;
		}else
		if(f.y.value ==""){
			alert("纵向位置不可为空");
			return false;
		}else
		if(isNaN(f.y.value)){
			alert("纵向位置应为数字");
			return false;
		}else
		if(f.y.value > 49999 || f.y.value < 1){
			alert("纵向位置无效数字");
			f.y.value = 49999;
			return false;
		}
	}else
	if(f.type.value==2){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.Bookmark.value==""){
			alert("书签名不能为空");
			return false;
		}
	}else
	if(f.type.value==3){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.pos.value==""){
			alert("页面位置不能为空");
			return false;
		}else
		if(f.pos.value > 49999 || f.pos.value < 1){
			alert("页面位置无效数字");
			f.pos.value=49999;
			return false;
		}else
		if(f.spage.value==""){
			alert("起始页不能为空");
			return false;
		}else
		if(isNaN(f.spage.value)){
			alert("起始页应为数字");
			return false;
		}else
		if(f.sealpage.value==""){
			alert("骑缝章加盖页数不能为空");
			return false;
		}else
		if(isNaN(f.sealpage.value)){
			alert("骑缝章加盖页数应为数字");
			return false;
		}
	}else
	if(f.type.value==4){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.Booktxt.value ==""){
			alert("详细文字不可为空");
			return false;
		}else if(f.tspage.value ==""){
			alert("起始页不可为空");
			return false;
		}else if(f.tsealpage.value ==""){
			alert("结束页不可为空");
			return false;
		}else if(f.tpianyi.value ==""){
			alert("上下偏移量不可为空");
			return false;
		}else if(isNaN(f.tpianyi.value)){
			alert("上下偏移量应为数字");
			return false;
		}else if(f.tpianyi.value > 49999 || f.tpianyi.value < -49999){
			alert("上下偏移量无效数字");
			f.tpianyi.value=0;
			return false;
		}
	}else
	if(f.type.value==5){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.Booktxt5.value ==""){
			alert("详细文字不可为空");
			return false;
		}else if(f.tspage5.value ==""){
			alert("起始页不可为空");
			return false;
		}else if(f.tsealpage5.value ==""){
			alert("结束页不可为空");
			return false;
		}else if(f.tpianyi5.value ==""){
			alert("上下偏移量不可为空");
			return false;
		}else if(isNaN(f.tpianyi5.value)){
			alert("上下偏移量应为数字");
			return false;
		}else if(f.tpianyi5.value > 49999 || f.tpianyi5.value < -49999){
			alert("上下偏移量无效数字");
			f.tpianyi5.value=0;
			return false;
		}
	}else
	if(f.type.value==6){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.gap.value ==""){
			alert("间隔页数不可为空");
			return false;
		}else
		if(isNaN(f.gap.value)){
			alert("间隔页数应为数字");
			return false;
		}else
		if(f.x6.value ==""){
			alert("横向位置不可为空");
			return false;
		}else
		if(isNaN(f.x6.value)){
			alert("横向位置应为数字");
			return false;
		}else
		if(f.x6.value > 49999 || f.x6.value < 1){
			alert("横向位置无效数字");
			f.x6.value=49999;
			return false;
		}else
		if(f.y6.value ==""){
			alert("纵向位置不可为空");
			return false;
		}else
		if(isNaN(f.y6.value)){
			alert("纵向位置应为数字");
			return false;
		}else
		if(f.y6.value > 49999 || f.y6.value < 1){
			alert("纵向位置无效数字");
			f.y6.value = 49999;
			return false;
		}
	}else
	if(f.type.value==7){
//		if(f.SEAL_ID.value ==""){
//			alert("印章不可为空");
//			return false;
//		}else
		if(f.pos7.value==""){
			alert("页面位置不能为空");
			return false;
		}else
		if(f.pos7.value > 49999 || f.pos7.value < 1){
			alert("页面位置无效数字");
			f.pos7.value=49999;
			return false;
		}else
		if(f.spage7.value==""){
			alert("起始页不能为空");
			return false;
		}else
		if(isNaN(f.spage7.value)){
			alert("起始页应为数字");
			return false;
		}else
		if(f.sealpage7.value==""){
			alert("骑缝章加盖页数不能为空");
			return false;
		}else
		if(isNaN(f.sealpage7.value)){
			alert("骑缝章加盖页数应为数字");
			return false;
		}else
		if(f.gapall.value==""){
			alert("间隔页数不能为空");
			return false;
		}else
		if(isNaN(f.gapall.value)){
			alert("间隔页数应为数字");
			return false;
		}
	}
	if(!isEnAndNum(f.Booktxt) || !isEnAndNum(f.Booktxt5)){
		return false;
	}
	return true;
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