var type=getUrlParam("type")==null?"0":getUrlParam("type");
var view=getUrlParam("view")==null?"set":getUrlParam("view");
var sel=getUrlParam("sel")==null?"0":getUrlParam("sel");
function load(){
//	alert();
	var fv=getUrlParam("fv");
	if(view=="show"){
		hidden("bt_yes");
		hidden("h1");
		hidden("h2");
		hidden("h3");
		hidden("h4");
	}
//	alert(type);
//	SysRole.showSysRolesByUser_id(user_no,cb_menu);
	SysRole.showRoles(cb_menu);
	if(false){
	}else if(type=="rule"){
		var rule_no=getUrlParam("rule_no");
		SysRole.showSysRoleByRule_no(rule_no,cb_menu2)
	}else{
		SysRole.showSysRoleByRole_ids(sel,cb_menu2);
	}
//	alert(fv);
}

function cb_menu(list){
//	alert(list);
	var sel_all=$("sel_all");
	for(var i=0;i<list.length;i=i+1){
		selectAdd(sel_all,list[i].role_id,list[i].role_name);
//		alert(list[i].menu_name);
	}
}

function cb_menu2(list){
	if(list==null){
		return ;
	}
	var sel_all=$("sel_sel");
	for(var i=0;i<list.length;i=i+1){
		selectAdd(sel_all,list[i].role_id,list[i].role_name);
	}
}

function toRight(){
	var sel1=$("sel_all");
	var sel2=$("sel_sel");
	selectCope(sel1,sel2);
}

function toLeft(){
	var sel1=$("sel_sel");
	var sel2=$("sel_all");
	selectDelSel2(sel1,sel2);
}

function mysubmit(){
	var sel_value=selectValues($("sel_sel"));
//	if(sel_value==0){
//		alert("角色不能为空！");
//		return false;
//	}
	if(type=="0"){
		var a=window.dialogArguments;
//		alert(sel_value);
		a.sel_roles.value=sel_value;
		this.close();
	}
}

function myclose(){
	this.close();
}