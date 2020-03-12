var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/appSystem/serverlist.jsp";

function load(){
	if(location==baseUrl(3)+"Seal/newRule.do"){
		location=thisUrl;
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	cdt_f.type_busi=3;
	showData();
}

//填充数据
function showData(){
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
	obj.row_has_chk=false;
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('应用系统编号');
	head.push('应用系统名');
	head.push('应用系统IP');
	head.push('创建人');
	head.push('创建时间');
	head.push('操作');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
//	UserSrv.showUsersByArea(page,pageSize,area_no,cb_data);
//alert(cdt_f.status_busi);
	AppSystem.showAppSystems(page,pageSize,cdt_f,cb_data);
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
	row.push(d.app_no);
	row.push(d.app_name);
	row.push(oprIP(d));
	row.push(d.create_username);
	row.push(d.createdate1);
	var oprStr=toOprStr(d);
	row.push(oprStr);
	return row;
}

function oprIP(d){
var str=" <a href=\"#\" onclick=\"alert('"+d.app_ip+"');return false;\" title=\"IP详情\">IP详情</a>";
return str;
}
function toStatStr(d){
	var str="<select onchange=\"chgStat('";
	str+=d.busi_no+"',this.value);\" onclick=\"flag=true;\">";
	if(d.status_busi=="2"){
		str+="<option value='2' style=\"color:red\" selected=\"selected\">停用</option>";
		str+="<option value='1' style=\"color:green\">正常</option></select>";
	}else{
		str+="<option value='2' style=\"color:red\">停用</option>";
		str+="<option value='1' style=\"color:green\" selected=\"selected\">正常</option></select>";
	}
	return str;
}


function toOprStr(d){
	var str="";
	str+="<input type='button' onclick=\"upApp('";
	str+=d.app_id;
	str+="');flag=true;\" class='SmallButton' value='修改' />";
	str+="&nbsp;&nbsp;";
	str+="<input type='button' onclick=\"delApp('";
	str+=d.app_id+"','"+d.app_name;
	str+="');flag=true;\" class='SmallButton' value='删除' />";
	if(d.rule_state=="1"){
	str+="&nbsp;&nbsp;";
	}
	return str;
}
function upApp(id){
	var url="/Seal/general/appSystem/server_update.jsp?id="+id;
	var w=500;
	var h=400;
	newModalDialog(url,w,h);
	location=thisUrl+"?page="+page;
}
function delApp(id,name){
	if(confirm("确定要删除吗？")){
		AppSystem.delAppSystem(id,cts_obj);
	}
}
function cts_obj(){
alert("删除应用系统成功！");
LogSys.logAdd(user_no,current_user_name,user_ip,"应用系统管理","删除系统名称："+name);
location=thisUrl+"?page="+page;
}
function delRule(no,name){
	if(confirm("确定要删除吗？")){
		GaiZhangRuleSrv.ruleDel(no);
		logDel("业务",no,name);//logOper.js
		location=thisUrl+"?page="+page;
	}
}
function zhuxiaoRule(no,name){
	if(confirm("确定要注销盖章规则吗？")){
		GaiZhangRuleSrv.ruleZhuxiao(no);
		logUpd("业务",no,name);//logOper.js
		location=thisUrl+"?page="+page;
	}
}
function jihuoRule(no,name){
	if(confirm("确定要激活盖章规则吗？")){
		GaiZhangRuleSrv.rulejihuo(no);
		logUpd("业务",no,name);//logOper.js
		location=thisUrl+"?page="+page;
	}
}
function chkSlct(){
	var sel=obj.Selected();
	if(sel==""){
		alert("请选择需要操作的对象！");
		return false;
	}
	return sel.toString();
}

function pl_stat(t){
	var sel=chkSlct();
	if(sel&&confirm("确定要修改吗？")){
		GaiZhangRuleSrv.plStat(sel,t)
		location=thisUrl+"?page="+page;
	}
}

function pl_delBusi(){
	var sel=chkSlct();
	if(sel&&confirm("确定要删除吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步	
		GaiZhangRuleSrv.plDelRule(sel);
		logPlDel("规则",sel,"");//logOper.js
		location=thisUrl+"?page="+page;
	}
}

//高级搜索
function show_sch(){
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("rule_name"==k_v[0]){
			f.rule_name.value=k_v[1];
		}
		if("busi_name"==k_v[0]){
			f.busi_name.value=k_v[1];
		}
		if("rule_state"==k_v[0]){
			f.rule_state.value=k_v[1];
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

//搜索
function search(){	
	var f=$("f_sch");
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	var app_name=f.app_name.value;
	if(!checkStr(app_name,"应用系统名称")){
		return false;
	}
	s=""==s?s+"app_name:="+app_name:s+"!app_name:="+app_name;
	
	var app_no=f.app_no.value;
	if(!checkStr(app_no,"应用系统编号")){
		return false;
	}
	s=""==s?s+"app_no:="+app_no:s+"!app_no:="+app_no;
	
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("app_name"==k_v[0]){
			f.app_name=k_v[1];
		}
		if("app_no"==k_v[0]){
			f.app_no=k_v[1];
		}
	}
	return f;
}

function objUpd(){
	var f=$("f_edit");
	if(myCheck(f,'edit')&&check("f_edit")&&confirm("确定要修改吗?")){
		f.rule_type.value=f.type.value;
		f.arg_desc.value=makeArgDesc(f.id);//seal_rule.js
		f.seal_detail.value=f.seal_no.value;
		var vo=formToVo(f);
		GaiZhangRuleSrv.updRuleVo(vo);
		logUpd("规则",f.rule_no.value,f.rule_name.value,"原名为:"+$("old_name").value);//logOper.js
		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	var obj={};
	obj.app_name=f.app_name.value;
	obj.app_no=f.app_no.value;
	return obj;
}