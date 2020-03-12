var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/modelFileRule/modelFileRule_list.jsp";

function list_load(){
	if(location==baseUrl(3)+"Seal/newMFR.do"){
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
//	obj.row_has_chk=true;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	//head.push('模板规则编号');
	head.push('模板名称');
	head.push('规则名称');
	head.push('操作');
	obj.SetHead(head);
}


function getDatas(){

	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	MFRSrv.showMFRBySch(page,pageSize,cdt_f,cb_data);
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
	//row.push(d.mid);
	row.push(d.model_name);
	row.push(d.rule_name);
	
	var oprStr=toOprStr(d);
	row.push(oprStr);
	return row;
}


function toOprStr(d){
	
	var str="";
		str+="<input type='button' onclick=\"updMFR('";
		str+=d.mid;
		str+="');flag=true;\" class='SmallButton' value='修改' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"delMFR('";
		str+=d.mid;
		str+="');flag=true;\" class='SmallButton' value='删除' />";
	
	return str;
}

function delMFR(no){
	if(confirm("确定要删除吗？")){
		MFRSrv.delModelFileRuleDao(no,c_ret);
		LogSys.logAdd(user_no,user_name,user_ip,"模板规则管理","删除模板规则:"+no+"成功");
		location=thisUrl+"?page="+page;
	}
}

function c_ret(num){
	if(num==0){
		alert("删除成功！");
	} 
	if(num=="1"){
		alert("删除失败！");
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
		MFRSrv.plStat(sel,t)
		location=thisUrl+"?page="+page;
	}
}



//高级搜索
function show_sch(){
	addRuleOption();
	addModelOption();
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

function updMFR(no){
	
	
	
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	
	addRulesOption();
	addModelsOption();
	MFRSrv.getModelFileRuleByMid(no,cb_objs);
	ShowDialog("detail",30);
}

function cb_objs(obj){
	var f=$("f_edit");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	//CertSrv.showPublicCerts(cert_back);
	
	f.reset();
	
	f.models_id.value=f.old_model.value=obj.model_id;
	f.rules_no.value = f.old_rule.value=obj.rule_no;
	f.mid.value = obj.mid;

//	check_type(f.id);//fromZY.js
}


//搜索
function searchMFR(){	
	
	var f=$("f_sch");
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){ 
	var s="";
	
	var b_model=f.model_id.value;
	if(!checkStr(b_model,"模板名称")){
		return false;
	}
	s=""==s?s+"model_id:="+b_model:s+"!model_id:="+b_model;
	
	var b_rule=f.rule_no.value;
	if(!checkStr(b_rule,"规则名称")){
		return false;
	}
	s=""==s?s+"rule_no:="+b_rule:s+"!rule_no:="+b_rule;
	
	
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		
		var k_v=k_vs[i].split(":=");
		if("model_id"==k_v[0]){
			//alert(k_v[0]);
			f.model_id=k_v[1];
		}
		if("rule_no"==k_v[0]){
			f.rule_no=k_v[1];
		}
		
	}
	return f;
}

function objUpd(){
	var f=$("f_edit");
	var m = $("edit_model");
	var r = $("edit_rule");
	if(myCheck(m,r,"edit")&&confirm("确定要修改吗?")){

		var vo=formToVo(f);
		MFRSrv.updateModelFileRule(vo);

		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	var obj={};
	obj.mid=f.mid.value;
	obj.model_id=f.models_id.value;
	obj.rule_no=f.rules_no.value;
	return obj;
}


function addModelsOption(){
	var array = modelFileSrv.showModelFiles(cb_modeldatas);
}

function cb_modeldatas(ps){
	var objOption = "";
	var objSelect=document.getElementById("edit_model"); 
	objSelect.length=0;
	for(var i=0;i<ps.length;i=i+1){
	
		objOption = new Option(ps[i].model_name,ps[i].model_id);
		objSelect.add(objOption, i+1); 
	
	}
	
}

function addRulesOption(){  
	
	var array = GaiZhangRuleSrv.showRules(cb_ruledata);
 
} 

function cb_ruledata(ps){

	var objOption = "";
	var objSelect=document.getElementById("edit_rule");  
	objSelect.length=0;
	for(var i=0;i<ps.length;i=i+1){

		objOption = new Option(ps[i].rule_name,ps[i].rule_no);
		objSelect.add(objOption, i+1); 
	}
}
	
