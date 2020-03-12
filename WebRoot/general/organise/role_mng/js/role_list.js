var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/organise/role_mng/role_list.jsp";

function load(){

//alert(location);
	if(location==baseUrl(3)+"Seal/roleAdd2.do"){
		location=thisUrl;
	}

//	GaiZhangApp.showApps(cb_apps);
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
//	alert(cdt_s);
	cdt_f=cdtToF(cdt_s);
	cdt_f.type_busi=3;
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
	obj.row_has_chk=false;
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('角色排序号');
	head.push('角色名');
	head.push('用户');
	//head.push('规则');
	head.push("菜单权限");
	head.push('操作');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
//	UserSrv.showUsersByArea(page,pageSize,area_no,cb_data);
//   alert(cdt_f.status_busi);
	SysRole.showObjBySch(page,pageSize,cdt_f,cb_data);
}
function cb_data(ps){
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].role_id,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&cdt="+cdt_s;
//	UserSrv.showUsers(target,pageSize,cb_data);
}

function toArray(d){
	var row=new Array();
	row.push(d.role_tab);
	row.push(d.role_name);
	row.push(toUserStr(d));
	//row.push(toRuleStr(d));
	row.push(toFuncStr(d));
	row.push(toOprStr(d));
	return row;
}

function toUserStr(d){
	if(d.user_num==0){
		return "0";
	}
	var str="";
	str+="<a href='#' onclick=\"showUser('";
	str+=d.sel_users+"');flag=true;\" >";
	str+=d.user_num+"</a>";
	return str;
}
function showUser(sel){
//	alert(sel);
	var url="/Seal/depttree/dept_tree.jsp?req=showUser1&ps="+sel;
	var h=450;
	var w=120;
	newModalDialog(url,w,h);
}

function toRuleStr(d){
	if(d.rule_num==0){
		return "0";
	}
	var str="";
	str+="<a href='#' onclick=\"showRule('";
	str+=d.sel_rules+"');flag=true;\" >";
	str+=d.rule_num+"</a>";
	return str;
}
function showRule(sel){
//	alert(sel);
	var url="/Seal/general/gaizhang_app/rule_chose.jsp?view=show&sel="+sel;
	var h=400;
	var w=480;
	newModalDialog(url,w,h);
}

function toAStr(d){
	var str="";
	str+="<a href=\"#\" onclick=\"showRoles('";
	str+=d.rule_no+"');flag=true;\">";
	str+=d.role_num;
	str+="</a>";
	return str;
}
function showRoles(no){
	var url="/Seal/general/organise/role_mng/role_chose.jsp?type=rule&view=show&rule_no="+no;
	var w=500;
	var h=400;
	newModalDialog(url,w,h);
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

function toFuncStr(d){
	var str="";
	str+="<a href='#' onclick=\"showFunc('";
	str+=d.role_fun1+"','"+d.role_fun2+"');flag=true;\">详细</a>";
	return str;
}
function showFunc(f_v,f_v2){
//	alert(f_v);
	var url="/Seal/general/organise/role_mng/func_chose.jsp?view=show&sel="+f_v;
	url +="&&sel2="+f_v2;
	var h=420;
	var w=800;
	newModalDialog(url,w,h);
}

function toOprStr(d){
//alert(d.role_status);
	if(d.role_status=="0"){
		return "";
	}
	var str="";
	str+="<input type='button' onclick=\"updObj('";
	str+=d.role_id;
	str+="');flag=true;\" class='SmallButton' value='修改' />";
	str+="&nbsp;&nbsp;";
	if((d.user_num >0)||(d.role_num >0)){	
	str+="<input type='button' onclick=\"delObj2('";
	str+=d.role_id+"','"+d.role_name;
	str+="');flag=true;\" class='SmallButton' value='删除' />";
	}else{
	str+="<input type='button' onclick=\"delObj('";
	str+=d.role_id+"','"+d.role_name;
	str+="');flag=true;\" class='SmallButton' value='删除' />";
	}
	return str;
}

function delObj(no,name){
	if(confirm("确定要删除吗？")){
		SysRole.objDel(no);
		//logDel("角色",no,name);//logOper.js
		LogSys.logAdd(user_no,user_name,user_ip,"删除角色","删除角色:'"+name+"'成功");//logSys.js
		location=thisUrl+"?page="+page;
	}
}
function delObj2(no,name){
	alert("该角色包含用户或者规则不可删除!");
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

function pl_delObj(){
	var sel=chkSlct();
	if(sel&&confirm("确定要删除吗？內置角色将不被删除。")){
		SysRole.plDel(sel);
		logPlDel("角色",sel,"");//logOper.js
		location=thisUrl+"?page="+page;
	}
}

//高级搜索
function show_sch(){
//	alert("待开发。。。");
//	return false;
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("role_tab"==k_v[0]){
			f.role_tab.value=k_v[1];
		}
		if("role_name"==k_v[0]){
			f.role_name.value=k_v[1];
		}
//		if("user_num"==k_v[0]){
//			f.user_num.value=k_v[1];
//		}
//		if("role_num"==k_v[0]){
//			f.role_num.value=k_v[1];
//		}
	}
	hidden("d_list");
	disp("d_search");
}

//返回
function ret(){
	hidden("d_search");
	disp("d_list");
}

function updObj(no){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	SysRole.getObj(no,cb_objs);
	ShowDialog("detail",30);
}
function cb_objs(obj){
	var f=$("f_edit");
	f.reset();
	f.role_id.value=obj.role_id;
	f.role_name.value=f.old_name.value=obj.role_name;
	f.role_tab.value=f.old_tab.value=obj.role_tab;
	f.func_v.value=obj.role_fun1;
	f.func_v2.value=obj.role_fun2;
	f.sel_rules.value=obj.sel_rules;
	freshNum(obj.sel_rules,"rule_num");
	f.sel_users.value=obj.sel_users;
}

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
	var p = f.role_tab.value;
	var role_tab=p.toLow().Trim();
	if(!checkStr(role_tab,"角色排序号")){
		return false;
	}
	s=""==s?s+"role_tab:="+role_tab:s+"!role_tab:="+role_tab;
//	alert(s);
	var role_name=f.role_name.value;
	var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
	if(f.role_name.value.Trim()!=""){
	  if(!pattern.test(f.role_name.value.Trim())){
        alert("角色名是以中文,英文,数字!");
        return false;
      }
	}
	if(!checkStr(role_name,"角色名称")){
		return false;
	}
	s=""==s?s+"role_name:="+role_name:s+"!role_name:="+role_name;
	
//	var user_num=f.user_num.value;
//	if(!checkStr(user_num,"用户")){
//		return false;
//	}
//	s=""==s?s+"user_num:="+user_num:s+"!user_num:="+user_num;
//	var role_num=f.role_num.value;
//	if(!checkStr(role_num,"规则")){
//		return false;
//	}
//	s=""==s?s+"role_num:="+role_num:s+"!role_num:="+role_num;

	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("role_tab"==k_v[0]){
			f.role_tab=k_v[1];
		}
		if("role_name"==k_v[0]){
			f.role_name=k_v[1];
		}
//		if("user_num"==k_v[0]){
//			f.user_num=k_v[1];
//		}
//		if("role_num"==k_v[0]){
//			f.role_num=k_v[1];
//		}
	}
	return f;
}

function objUpd(){
	var f=$("f_edit");
	if(checkForm(f.id,"edit")&&confirm("确定要修改吗?")){
		var vo=formToVo(f);
		SysRole.updObj(vo);
		// logUpd("角色",f.role_id.value,f.role_name.value,"原名为:"+f.old_name.value);//logOper.js
		LogSys.logAdd(user_no,user_name,user_ip,"修改角色","修改角色:'"+f.old_name.value+"'成功");//logSys.js
		location=thisUrl+"?page="+page;
	}
}

function formToVo(f){
	var obj={};
	obj.role_id=f.role_id.value;
	obj.role_name=f.role_name.value;
	obj.role_tab=f.role_tab.value;
	obj.func_v=f.func_v.value;
	obj.func_v2=f.func_v2.value;
	obj.sel_rules=f.sel_rules.value;
	obj.sel_users=f.sel_users.value;
	return obj;
}