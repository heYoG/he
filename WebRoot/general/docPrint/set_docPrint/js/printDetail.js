var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var logUser="";
var thisUrl=baseUrl(3)+"Seal/general/docPrint/set_docPrint/printDetail.jsp";
function load(){
//alert(location);
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	showData();
}

//填充数据
function showData(){
	// alert("填充数据");
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
//	obj.row_has_chk=true;
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('打印份数');
	head.push('打印时间');
	head.push("操作IP");
	head.push('用户');
	head.push('用户证书');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	LogSys.logPrintOperLog(page,pageSize,cdt_f,cb_data);
}
function cb_data(ps){
//alert(ps);
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,i,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page;
//	UserSrv.showUsers(target,pageSize,cb_data);
}

function toArray(d){
	var row=new Array();
	row.push(d.log_value);
	row.push(d.create_time.Format("yyyy-MM-dd hh:mm:ss"));
	row.push(d.ip);
	row.push(d.user_name);
	row.push(d.key_dn);
	return row;
}
function toLogType(type){
	if(type==3){
	  return "加盖印章";
	}else if(type==4){
	  return "删除印章";
	}
}
function toSealStr(d){
	if(d.seal_type=="private"){
		return "个人章";
	}
	if(d.seal_detail=="0"){
		return "本业务公章";
	}
	var str="<a href='#' onclick=\"alert('未关联印章图片');flag=true;\">";
//	str+=d.seal_name;
	str+="测试用章"
	str+="</a>";
	return str;
}

function toTypeStr(d){
	var desc=ruleDesc(d.rule_type,d.arg_desc);//seal_rule.js
	var str="<a href='#' title='";
	str+=desc+"' onclick=\"alert(this.title);";
	str+="flag=true;\">";
	str+=ruleTypeName(d.rule_type);//seal_rule.js
	str+="</a>";
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

function chgStat(no,stat){
//	alert(fbd);
//	if(confirm("确定要修改吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		GaiZhangRule.chgStatus(no,stat);
		location=thisUrl+"?page="+page;
//	}
}

function toOprStr(d){
	var str="";
	str+="<input type='button' onclick=\"updRule('";
	str+=d.rule_no;
	str+="');flag=true;\" class='SmallButton' value='修改' />";
	str+="&nbsp;&nbsp;";
	str+="<input type='button' onclick=\"delRule('";
	str+=d.rule_no+"','"+d.rule_name;
	str+="');flag=true;\" class='SmallButton' value='删除' />";
	return str;
}

function delRule(no,name){
	if(confirm("确定要删除吗？")){
		GaiZhangRule.ruleDel(no);
		logDel("业务",no,name);//logOper.js
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
		GaiZhangRule.plStat(sel,t)
		location=thisUrl+"?page="+page;
	}
}

function pl_delBusi(){
	var sel=chkSlct();
	if(sel&&confirm("确定要删除吗？")){
		GaiZhangRule.plDelRule(sel);
		logPlDel("规则",sel,"");//logOper.js
		location=thisUrl+"?page="+page;
	}
}

//返回
function ret(){
	hidden("d_search");
	disp("d_list");
}

function updRule(no){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	GaiZhangRule.getRuleVo(no,cb_objs);
	ShowDialog("detail",30);
}
function cb_objs(obj){
	var f=$("f_edit");
	f.reset();
	f.rule_no.value=obj.rule_no;
	f.rule_name.value=f.old_name.value=obj.rule_name;
	f.busi_no.value=obj.busi_no;
	f.seal_type.value=obj.seal_type;
	if(obj.seal_type=="public"){
		if(obj.seal_detail!="0"){
			f.s2.value="z";
		}else{
			f.s2.value="b";
		}
		f.seal_detail.value=f.seal_no.value=obj.seal_detail;
		f.seal_name.value=obj.seal_name;
	}
	sealChg(f.id);//
	f.use_cert.value=obj.use_cert;
	f.chk_cert.checked=obj.use_cert=="1";
	f.type.value=f.rule_type.value=obj.rule_type;
	check_type(f.id);//fromZY.js
	setTypeValue(f,obj);//seal_rule.js
}

//搜索
function search(){	
alert(1);
	var f=$("f_sch");
	var c=fToCdt(f);
//	alert(c);
	if(c){
		location=thisUrl+"?page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	var seal_name=f.seal_name.value;
	s=""==s?s+"seal_name:="+seal_name:s+"!seal_name:="+seal_name;
	var user_name=f.user_name.value;
	s=""==s?s+"user_name:="+user_name:s+"!user_name:="+user_name;
	var user_ip=f.user_ip.value;
	s=""==s?s+"user_ip:="+user_ip:s+"!user_ip:="+user_ip;
	var begin_time=f.begin_time.value;
	s=""==s?s+"begin_time:="+begin_time:s+"!begin_time:="+begin_time;
	var end_time=f.end_time.value;
	s=""==s?s+"end_time:="+end_time:s+"!end_time:="+end_time;
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("obj_no"==k_v[0]){
			f.obj_no=k_v[1];
		}
		if("obj_name"==k_v[0]){
			f.obj_name=k_v[1];
		}
		if("obj_type"==k_v[0]){
			f.obj_type=k_v[1];
		}
		if("user_ip"==k_v[0]){
			f.user_ip=k_v[1];
		}
		if("create_name"==k_v[0]){
			f.create_name=k_v[1];
		}
		if("begin_time"==k_v[0]){
			f.begin_time=k_v[1];
		}
		if("end_time"==k_v[0]){
			f.end_time=k_v[1];
		}
	}
	return f;
}
//高级搜索
function show_sch(){
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("obj_no"==k_v[0]){
			f.obj_no.value=k_v[1];
		}
		if("obj_name"==k_v[0]){
			f.obj_name.value=k_v[1];
		}
		if("obj_type"==k_v[0]){
			f.obj_type.value=k_v[1];
		}
		if("user_ip"==k_v[0]){
			f.user_ip.value=k_v[1];
		}
		if("create_name"==k_v[0]){
			f.create_name.value=k_v[1];
		}
		if("begin_time"==k_v[0]){
			f.begin_time.value=k_v[1];
		}
		if("end_time"==k_v[0]){
			f.end_time.value=k_v[1];
		}
	}
	hidden("d_list");
	disp("d_search");
}
function objUpd(){
	var f=$("f_edit");
	if(myCheck(f)&&check("f_edit")&&confirm("确定要修改吗?")){
		f.rule_type.value=f.type.value;
		f.arg_desc.value=makeArgDesc(f.id);//seal_rule.js
		f.seal_detail.value=f.seal_no.value;
		var vo=formToVo(f);
		GaiZhangRule.updAppVo(vo);
		logUpd("规则",f.rule_no.value,f.rule_name.value,"原名为:"+$("old_name").value);//logOper.js
		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	var obj={};
	obj.rule_no=f.rule_no.value;
	obj.rule_name=f.rule_name.value;
	obj.busi_no=f.busi_no.value;
	obj.seal_type=f.seal_type.value;
	obj.seal_detail=f.seal_detail.value;
	obj.use_cert=f.use_cert.value;
	obj.rule_type=f.rule_type.value;
	obj.arg_desc=f.arg_desc.value;
	return obj;
}