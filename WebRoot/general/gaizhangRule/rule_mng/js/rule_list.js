var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/gaizhangRule/rule_mng/rule_list.jsp";

function list_load(){
//alert(location);
	if(location==baseUrl(3)+"Seal/newRule.do"){
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
	obj.row_has_chk=false;    //是否要复选框
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('规则号');
	head.push('规则名称');
	head.push('印章名称');
	head.push("使用证书");
	head.push('盖章位置');
	head.push('状态');
	head.push('操作');
	obj.SetHead(head);
}
function tij_sch(){
 if(confirm('确认要提交规则信息吗？')){
   dwr.engine.setAsync(true); //设置方法调用是否同步，false表示同步
   GaiZhangRuleSrv.GetRuleListP(rsObj);
  }
}
function rsObj(s){
if(s==0){
 alert("提交规则信息成功!");
}else{
 alert("提交规则信息失败!");
}  
}
function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	GaiZhangRuleSrv.showRuleBySch(page,pageSize,cdt_f,cb_data);
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
	row.push(d.rule_no);
	row.push(d.rule_name);
	var sealStr=toSealStr(d);
	row.push(sealStr);
	row.push(d.use_cert==1?"是":"否");
	var typeStr=toTypeStr(d);
	row.push(typeStr);
   if(d.rule_state=="1"){
		row.push("正常");
	}else if(d.rule_state=="2"){
		row.push("注销");
	}else{
		row.push("未知状态");
	}
	var oprStr=toOprStr(d);
	row.push(oprStr);
	return row;
}


function toSealStr(d){
	var str="<a href='#' onclick=\"showSeal('";
	str+=d.seal_id+"');flag=true;\">";
	str+=d.seal_name;
	str+="</a>";
	return str;
}

function showSeal(id){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
//	SealBody.getSealDataBySealId(id,cb_tempData);
	SealBody.getPreviewImgDataBySealId(id,cb_tempData);
	ShowDialog("showSeal",30);
}

function cb_tempData(preview_img_data){
//	var obj = document.getElementById("MakeSealV6");
//	obj.LoadData(seal_data);
//	vID = obj.GetNextSeal(0);
//	obj.SelectSeal(vID);
	if(preview_img_data!=null&&preview_img_data!=""){
		//document.getElementById("seal_body_info").innerHTML="<img src='data:image/bmp;base64,"+preview_img_data+"'/>";
		var url=basepath+"servlet/ShowImgServlet?data="+preview_img_data;
		document.getElementById("seal_body_info").innerHTML="<img src='"+url+"'/>";
		ShowDialog("showSeal",30);
	}else{
		document.getElementById("seal_body_info").innerHTML="";
		alert("未查找到该印章图片信息!");
	}
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
		GaiZhangRuleSrv.chgStatus(no,stat);
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
	if(d.rule_state=="1"){
	str+="&nbsp;&nbsp;";
	str+="<input type='button' onclick=\"zhuxiaoRule('";
	str+=d.rule_no+"','"+d.rule_name;
	str+="');flag=true;\" class='SmallButton' value='注销' />";
	}if(d.rule_state=="2"){
	str+="&nbsp;&nbsp;";
	str+="<input type='button' onclick=\"jihuoRule('";
	str+=d.rule_no+"','"+d.rule_name;
	str+="');flag=true;\" class='SmallButton' value='激活' />";
	}
	return str;
}

function delRule(no,name){
	if(confirm("确定要删除吗？")){
		GaiZhangRuleSrv.delRule(no);
		LogSys.logAdd(user_no,user_name,user_ip,"规则管理","删除规则:"+name+"成功");
		location=thisUrl+"?page="+page;
	}
}
function zhuxiaoRule(no,name){
	if(confirm("确定要注销盖章规则吗？")){
		GaiZhangRuleSrv.ruleZhuxiao(no);
		location=thisUrl+"?page="+page;
	}
}
function jihuoRule(no,name){
	if(confirm("确定要激活盖章规则吗？")){
		GaiZhangRuleSrv.rulejihuo(no);
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

function updRule(no){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	GaiZhangRuleSrv.getRuleVo(no,cb_objs);
	ShowDialog("detail",30);
}
function cb_objs(obj){
	var f=$("f_edit");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	CertSrv.showPublicCerts(cert_back);
	f.reset();
	f.rule_no.value=obj.rule_no;
	f.rule_name.value=f.old_name.value=obj.rule_name;
	f.seal_id.value=f.seal_no.value=obj.seal_id;
	f.seal_name.value=obj.seal_name;
	f.use_cert.value=obj.use_cert;
	f.chk_cert.checked=obj.use_cert=="1";
	if(obj.use_cert=="1"){
		f.cert_no.value=obj.cert_no;
		document.getElementById("tr_select_cert").style.display="";
	}else{
		document.getElementById("tr_select_cert").style.display="none";
	}
	f.type.value=f.rule_type.value=obj.rule_type;
	check_type(f.id);//fromZY.js
	setTypeValue(f,obj);//seal_rule.js
}


//搜索
function search(){	
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
	
	var b_name=f.rule_name.value;
	if(!checkStr(b_name,"规则名")){
		return false;
	}
	s=""==s?s+"rule_name:="+b_name:s+"!rule_name:="+b_name;
	var b_status=f.rule_state.value;
	if(!checkStr(b_status,"规则状态")){
		return false;
	}
	s=""==s?s+"rule_state:="+b_status:s+"!rule_state:="+b_status;
	
//	alert(s);
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("rule_name"==k_v[0]){
			f.rule_name=k_v[1];
		}
		if("rule_state"==k_v[0]){		
			f.rule_state=k_v[1];			
		}
	}
	return f;
}

function objUpd(){
	var f=$("f_edit");
	if(myCheck(f,'edit')&&check("f_edit")&&confirm("确定要修改吗?")){
		f.rule_type.value=f.type.value;
		f.arg_desc.value=makeArgDesc(f.id);//seal_rule.js
		var vo=formToVo(f);
		GaiZhangRuleSrv.updRuleVo(vo);
		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	var obj={};
	obj.rule_no=f.rule_no.value;
	obj.rule_name=f.rule_name.value;
	obj.seal_id=f.seal_no.value;
	obj.use_cert=f.use_cert.value;
	obj.cert_no=f.cert_no.value;
	obj.rule_type=f.rule_type.value;
	obj.arg_desc=f.arg_desc.value;
	return obj;
}