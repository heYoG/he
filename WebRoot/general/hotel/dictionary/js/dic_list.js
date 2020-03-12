var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/hotel/dictionary/dic_list.jsp";

function list_load(){
	if(location==baseUrl(3)+"Seal/newDic.do"){
		location=thisUrl;
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	showData();
}

//填充数据
function showData(){
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
	//obj.row_has_chk=true;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
//	head.push('字典号');
	head.push('名称');
	head.push('显示名称');
	head.push('字段类型');
	head.push('显示顺序');
	head.push('显示状态');
	head.push('操作');
	obj.SetHead(head);
}


function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelDic.showDicBySch(page,pageSize,cdt_f,cb_data);

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
	row.push(d.cname);
	row.push(d.cshowname);
	row.push(d.cdatatype);
	if(d.c_sort==0){
		row.push("");
	}else{
		row.push(d.c_sort);
	}
	
	var sta = status(d);
	row.push(sta);
	var oprStr=toOprStr(d);
	row.push(oprStr);
	return row;
}

function status(d){
	var str = "";
	if(d.c_status==0){
		str = "显示";
	}else{
		str = "隐藏";
	}
	return str;
}


function toOprStr(d){
	var str="";
	
	if(d.sys==0){
		str+="<input type='button' disabled='disabled' title='此为系统内置数据,不可修改或者删除' onclick=\"updDic('";
		str+=d.cid+"','"+d.sys;
		str+="');flag=true;\" class='SmallButton'  value='修改' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' disabled='disabled' title='此为系统内置数据,不可修改或者删除' onclick=\"delDic('";
		str+=d.cid+"','"+d.cname+"','"+d.sys;
		str+="');flag=true;\" class='SmallButton' value='删除' />";
		
	}else{
		str+="<input type='button' onclick=\"updDic('";
		str+=d.cid+"','"+d.sys;
		str+="');flag=true;\" class='SmallButton' value='修改' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"delDic('";
		str+=d.cid+"','"+d.cname+"','"+d.sys;
		str+="');flag=true;\" class='SmallButton' value='删除' />";
		
	}
	return str;
}

function changeStatusBlock(no,name,sta){
	HotelDic.changeStatusIsBlock(no);
	LogSys.logAdd(user_no,user_name,user_ip,"通用字典管理","更改字典:"+name+"状态显示成功");
	location=thisUrl+"?page="+page;
}

function changeStatusDis(no,name,sta){
	
		HotelDic.changeStatusIsDisplay(no);
		LogSys.logAdd(user_no,user_name,user_ip,"通用字典管理","更改字典:"+name+"状态隐藏成功");
		location=thisUrl+"?page="+page;
	
}

function delDic(no,name,sta){
	
	if(chSysData(sta)&&confirm("确定要删除吗？")){
		HotelDic.delHotelDic(no);
		LogSys.logAdd(user_no,user_name,user_ip,"通用字典管理","删除通用字典:"+name+"成功");
		location=thisUrl+"?page="+page;
	}
}

function chSysData(sta){
	if(sta==0){
		alert("此数据为系统内置，不可删除！");
		return false;
	}
	return true;
}


function chkSlct(){
	var sel=obj.Selected();
	if(sel==""){
		alert("请选择需要操作的对象！");
		return false;
	}
	alert(sel);
	return sel.toString();
}


function pl_stat(t){
	var sel=chkSlct();
	if(sel&&confirm("确定要修改吗？")){
		HotelDic.plStat(sel,t)
		location=thisUrl+"?page="+page;
	}
}

function pl_delDic(){
	var sel=chkSlct();
	if(sel&&confirm("确定要删除吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步	
		HotelDic.plDelDic(sel);
		location=thisUrl+"?page="+page;
	}
}

//高级搜索
function show_sch(){

	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("cname"==k_v[0]){
			f.cname.value=k_v[1];
		}
		if("cshowname"==k_v[0]){
			f.cshowname.value=k_v[1];
		}
		if("cdatatype"==k_v[0]){
			f.cdatatype.value=k_v[1];
		}
	}
	hidden("d_list");
	disp("d_search");
}

//返回
function ret(){
	hidden("d_search");
	disp("d_list");
}

function updDic(no,sys){
	
	if(sys==0){
		alert("此数据为系统内置，不能修改！");
		return false;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelDic.getHotelDic(no,cb_objs);
	ShowDialog("detail",30);
}

function cb_objs(obj){
	var f=$("f_edit");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	//CertSrv.showPublicCerts(cert_back);
	f.reset();
	f.cid.value=obj.cid;
	f.cname.value=f.old_name.value=obj.cname;
	f.cshowname.value=f.old_showname.value=obj.cshowname;
	f.type.value=f.cdatatype.value=obj.cdatatype;
	f.c_sort.value=f.old_c_sort.value = obj.c_sort;
	f.sys.value = obj.sys;
	f.c_status.value = obj.c_status;
	if(obj.c_status==0){
		$("r1").checked =true;
		$("sort_id").style.display = "block";
	}else{
		$("r2").checked = true;
		$("sort_id").style.display = "none";
	}
	check_type(f.id);//fromZY.js
}


//搜索
function searchDic(){	

	var f=$("f_sch");
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	

	var s="";
	
	var b_name=f.cname.value;
	if(!checkStr(b_name,"通用字典名称")){
		return false;
	}
	s=""==s?s+"cname:="+b_name:s+"!cname:="+b_name;
	
	var b_showname=f.cshowname.value;
	if(!checkStr(b_showname,"通用字典名称")){
		return false;
	}
	s=""==s?s+"cshowname:="+b_showname:s+"!cshowname:="+b_showname;
	var b_status=f.cdatatype.value;
	if(!checkStr(b_status,"字典类型")){
		return false;
	}
	s=""==s?s+"cdatatype:="+b_status:s+"!cdatatype:="+b_status;
	
	
	return s;
}

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

function objUpd(){
	var f=$("f_edit");
	var val = f.c_sort.value;
	if(checkSort(val)&& myCheck(f,"edit")&&check("f_edit")&&confirm("确定要修改吗?")){
		f.cdatatype.value=f.type.value;
		
		//f.arg_desc.value=makeArgDesc(f.id);//seal_rule.js
		var vo=formToVo(f);
		HotelDic.updateHotelDic(vo);
		
		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	
	var obj={};
	obj.cid=f.cid.value;
	obj.cname=f.cname.value;
	obj.cshowname=f.cshowname.value;
	obj.cdatatype=f.cdatatype.value;
	obj.c_sort = f.c_sort.value;
	
	if($("r1").checked){
		obj.c_status='0';
	}else{
		obj.c_status='1';
		obj.c_sort = 0;
	}
	
	return obj;
}