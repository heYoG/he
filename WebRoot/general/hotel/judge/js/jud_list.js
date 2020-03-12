var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/hotel/judge/jud_list.jsp";

function list_load(){
//alert(location);
	
	if(location==baseUrl(3)+"Seal/newjud.do"){
		location=thisUrl;
	}
	
	page=getUrlParam("page")==null?1:getUrlParam("page");
	
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	showData();
}

//填充数据
function showData(){
//	alert("填充数据");
	
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
//	obj.row_has_chk=true;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
//	head.push('字典号');
	head.push('模版');
	head.push('属性名');
	head.push('属性值');
	head.push('操作');
	obj.SetHead(head);
}


function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步

	Masterplatejudge.showJudBySch(page,pageSize,cdt_f,cb_data);

}

function cb_data(ps){
	

	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].rule_no,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&cdt="+cdt_s;
}

	
	 
function toArray(d){
	var row=new Array();
//	row.push(d.cid);
	row.push(d.masterplate_name);
	row.push(d.c_name);
//	var sealStr=toSealStr(d);
	if(d.c_valuetype=="1"){
		row.push("非空");
	}
	if(d.c_valuetype=="2"){
		row.push("空");
	}
	if(d.c_valuetype=="3"){
		row.push(d.c_value);
	}
	var oprStr=toOprStr(d);
	row.push(oprStr);
	return row;
}



function chgStat(no,stat){
//	alert(fbd);
//	if(confirm("确定要修改吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		HotelDic.chgStatus(no,stat);
		location=thisUrl+"?page="+page;
//	}
}

function toOprStr(d){
	var str="";
//	if(d.sys==0){
//		str+="<input type='button' disabled='disabled' title='此为系统内置数据,不可修改或者删除' onclick=\"updDic('";
//		str+=d.cid+"','"+d.sys;
//		str+="');flag=true;\" class='SmallButton'  value='修改' />";
//		str+="&nbsp;&nbsp;";
//		str+="<input type='button' disabled='disabled' title='此为系统内置数据,不可修改或者删除' onclick=\"delDic('";
//		str+=d.cid+"','"+d.cname+"','"+d.sys;
//		str+="');flag=true;\" class='SmallButton' value='删除' />";
//	}else{
		str+="<input type='button' onclick=\"updjud('";
		str+=d.c_id;
		str+="');flag=true;\" class='SmallButton' value='修改' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"deljud('";
		str+=d.c_id+"','"+d.c_name;
		str+="');flag=true;\" class='SmallButton' value='删除' />";
//	}
	return str;
}

function deljud(no,name,sta){
	if(confirm("确定要删除吗？")){
		Masterplatejudge.delMasterplatejudge(no);
		LogSys.logAdd(user_no,user_name,user_ip,"判定条件管理","删除判定条件:"+name+"成功");
		location=thisUrl+"?page="+page;
	}
}

//function chSysData(sta){
//	if(sta==0){
//		alert("此数据为系统内置，不可删除！");
//		return false;
//	}
//	return true;
//}


function chkSlct(){
	var sel=obj.Selected();
	if(sel==""){
		alert("请选择需要操作的对象！");
		return false;
	}
	return sel.toString();
}

//
//function pl_stat(t){
//	var sel=chkSlct();
//	if(sel&&confirm("确定要修改吗？")){
//		HotelDic.plStat(sel,t)
//		location=thisUrl+"?page="+page;
//	}
//}
//
//function pl_delDic(){
//	var sel=chkSlct();
//	if(sel&&confirm("确定要删除吗？")){
//		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步	
//		HotelDic.plDelDic(sel);
//		location=thisUrl+"?page="+page;
//	}
//}

////高级搜索
//function show_sch(){
//
//	var f=$("f_sch");
//	var k_vs=cdt_s.split("!");
//	for(var i=0;i<k_vs.length;i=i+1){
//		var k_v=k_vs[i].split(":=");
//		if("cname"==k_v[0]){
//			f.cname.value=k_v[1];
//		}
//		if("cshowname"==k_v[0]){
//			f.cshowname.value=k_v[1];
//		}
//		if("cdatatype"==k_v[0]){
//			f.cdatatype.value=k_v[1];
//		}
//	}
//	hidden("d_list");
//	disp("d_search");
//}

//返回
function ret(){
	hidden("d_search");
	disp("d_list");
}

function updjud(no){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	Masterplatejudge.getMasterplatejudgeVo(no,cb_objs);
	ShowDialog("detail",30);
}

function cb_objs(obj){ 
	var f=$("f_edit");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	//CertSrv.showPublicCerts(cert_back);
	f.reset();
	f.c_id.value=obj.c_id;
	f.c_name.value=f.old_name.value=obj.c_name;
	f.masterplate_name.value=obj.masterplate_name;
	f.masterplatecid.value=obj.master_platecid;
	change(obj.c_valuetype);
	f.c_valuetype.value=f.type.value=obj.c_valuetype;
	if(f.type.value=="3"){
		f.c_value.value=f.cvalue.value=obj.c_value;
		
	}
//	
	
	//check_type(f.id);//fromZY.js
}


////搜索
//function searchDic(){	
//
//	var f=$("f_sch");
//	var c=fToCdt(f);
//	if(c){
//		location=thisUrl+"?page=1&cdt="+c;
//	}
//}
//
////得到搜索条件的字符串
//function fToCdt(f){
//	
//
//	var s="";
//	
//	var b_name=f.cname.value;
//	if(!checkStr(b_name,"通用字典名称")){
//		return false;
//	}
//	s=""==s?s+"cname:="+b_name:s+"!cname:="+b_name;
//	
//	var b_showname=f.cshowname.value;
//	if(!checkStr(b_name,"通用字典名称")){
//		return false;
//	}
//	s=""==s?s+"cshowname:="+b_showname:s+"!cshowname:="+b_showname;
//	var b_status=f.cdatatype.value;
//	if(!checkStr(b_status,"字典类型")){
//		return false;
//	}
//	s=""==s?s+"cdatatype:="+b_status:s+"!cdatatype:="+b_status;
//	
//	return s;
//}
//
//根据字符串得到搜索条件
function cdtToF(cdt){

	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		
		var k_v=k_vs[i].split(":=");
		if("cname"==k_v[0]){	
			f.cname=k_v[1];
		}
		if("cshowname"==k_v[0]){
			f.cshowname=k_v[1];
		}
		if("cdatatype"==k_v[0]){
			f.cdatatype=k_v[1];
		}
	}
	return f;
}

function change(s)
{
	var c_value = document.getElementById("c_value");
	if(s == 3){
    	c_value.style.visibility = "visible" ;
	}else{
		c_value.style.visibility = "hidden" ;
	}
}

function objUpd(){
	var f=$("f_edit");
	if(myCheck(f)//&&check("f_edit")
			&&confirm("确定要修改吗?")){
		//sf.cdatatype.value=f.type.value;
		//f.arg_desc.value=makeArgDesc(f.id);//seal_rule.js
		var vo=formToVo(f);
		Masterplatejudge.updateMasterplatejudge(vo);
		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	var obj={};
	obj.c_id=f.c_id.value;
	obj.c_name=f.c_name.value;
	obj.master_platecid=f.masterplatecid.value;
	obj.c_valuetype=f.c_valuetype.value;
	if(f.c_valuetype.value=="3"){
		obj.c_value=f.c_value.value;
	}
	return obj;
}