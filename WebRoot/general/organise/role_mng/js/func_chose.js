var type=getUrlParam("type")==null?"0":getUrlParam("type");
var fv=getUrlParam("fv");
var sel=getUrlParam("sel")==null?"0":getUrlParam("sel");
var sel2=getUrlParam("sel2")==null?"0":getUrlParam("sel2");
var view=getUrlParam("view")==null?"set":getUrlParam("view");
function load(){
//	alert();
//	alert(fv+":"+sel);
	if(view=="show"){
		hidden("bt_yes");
	}
	var user_no="admin";
	SysFunc.showMenusByUser_id(user_no,cb_sysMenu);
}

var m=new QerMenu("m");
function cb_sysMenu(list){
	for(var i=0;i<list.length;i=i+1){
//		alert(list[i].view_menu);
		var sels=new Array(sel,sel2);
		var item=new QerItem(toMenu(list[i].view_menu),sels,"num",m);
		for(var j=0;j<list[i].list_func.length;j=j+1){
			item.addSubItem(toMenu2(list[i].list_func[j]), sels)
		}
		m.ms.push(item);
	}
	var div=$("d_list");
	m.SetLineNum(6);
	div.innerHTML=m.ListCode();
}

function toMenu(d){
	var menu={};
	menu.menu_no=d.menu_no;
	menu.menu_name=d.menu_name;
	menu.father_no=-1;
	menu.func_value=0;
	menu.icon="/Seal/images/menu/"+d.menu_image+".gif";
	return menu;
}

function toMenu2(d){
	var menu={};
	menu.menu_no=d.func_id;
	menu.menu_name=d.func_name;
	menu.father_no=d.menu_no;
	menu.func_value=d.func_value;
	menu.func_group=d.func_group;
	menu.icon="/Seal/images/menu/"+d.func_image+".gif";
	return menu;
}

function mysubmit(){
	var sel_value=m.CheckSumByGroup();
	var sel_value2=m.CheckSumByGroup(2);
	if(sel_value+sel_value2==0){
		alert("权限不能为空！");
		return false;
	}
	if(type=="0"){
		var a=window.dialogArguments;
		a.func_v.value=sel_value;
		a.func_v2.value=sel_value2;
		this.close();
	}
}

function myclose(){
	this.close();
}