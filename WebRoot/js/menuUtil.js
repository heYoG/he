
function check_all(obj,name){
	var chks=$s(name);
	for(var i=0;i<chks.length;i=i+1){
		chks[i].checked=obj.checked;
	}
}

function isNoCheck(name){
	var chks=$s(name);
	for(var i=0;i<chks.length;i=i+1){
		if(chks[i].checked){
			return false;
		}
	}
	return true;
}

function isStrInStrs(str,strs){
	var s=strs.split(",");
	for(var i=0;i<s.length;i=i+1){
		if(str==s[i])
			return true;
	}
	return false;
}

function check_one(obj,name){
	var chk=$("m"+name);
	if(obj.checked||isNoCheck("f"+name)){
		chk.checked=obj.checked;
	}
}

function check_value_sum(){
	var chks=$t("input");
	var sum=0;
	for(var i=0;i<chks.length;i=i+1){
		if(chks[i].type=="checkbox"&&chks[i].checked){
			sum-=(-chks[i].value);
		}
	}
	return sum;
}

function check_value_strs(){
	var chks=$t("input");
	var strs="";
	for(var i=0;i<chks.length;i=i+1){
		if(chks[i].type=="checkbox"&&chks[i].checked&&chks[i].value!=""){
			strs=strs==""?chks[i].value:strs+","+chks[i].value;
		}
	}
	return strs;
}

/**
**	
	QerMenu为总菜单对象，即整体，包含多组菜单；				爷爷级
	QerItem为菜单组对象，属于总菜单，每一组菜单包含多个菜单项；	父级
	QerSubItem为菜单项，属于一个菜单组对象；				子级
**
**/

/**
**	爷爷级对象：QerMenu
**/
function QerMenu(id) {
	this.id = id;//自身引用，如var m=new QerMenu("m");变量名(m)与参数("m")需要一致
	this.ms = new Array();//菜单组对象
	this.html = "";//html代码
	this.right_main = "";
	this.line_num=6;
}
QerMenu.prototype.ChgDisp = function (name){
	var trs=$t("tr");
	for(var i=0;i<trs.length;i=i+1){
		if(trs[i].name=="tr"+name){
			trs[i].style.display=trs[i].style.display=="none"?"block":"none";
		}
	}
}
//响应菜单组对象前的全选事件
QerMenu.prototype.CheckAll = function (obj,name){
	var chks=$s("f"+name);
	var item=this.findByNo(name);
	for(var i=0;i<chks.length;i=i+1){
		chks[i].checked=item.sub_items[i].selected=obj.checked;
	}
}
//响应菜单项对象前的选择及取消事件
QerMenu.prototype.CheckOne = function (obj,f_n,n) {
	var chk=$("m"+f_n);
	if(obj.checked||isNoCheck("f"+f_n)){
		chk.checked=obj.checked;
	}
	this.findByNo(f_n).findByNo(n).selected=obj.checked;
}
//根据权限组得到每组已选的所有权限总和
QerMenu.prototype.CheckSumByGroup = function (group) {
	if (typeof (group) == "undefined") {
		group = 1;
	}
	var sum=0;
	for(var i=0;i<this.ms.length;i=i+1){
		sum+=this.ms[i].CheckSumByGroup(group);
	}
	return sum;
}
//得到总菜单的html代码
QerMenu.prototype.HtmlCode = function () {
	var str = "";
	for (var i = 0; i < this.ms.length; i = i + 1) {
		if (this.ms[i].sub_items.length != 0) {
			str += this.ms[i].HtmlCode();
		}
	}
	this.html = str;
	return this.html;
};
//得到总菜单的列表代码
QerMenu.prototype.ListCode = function () {
	var str = "<table border=\"0\" cellspacing=\"2\" class=\"small\" cellpadding=\"3\"";
	str += " align=\"center\"><tr class=\"TableContent\">";
	for (var i = 0; i < this.ms.length; i = i + 1) {
		if(i!=0&&i%this.line_num==0){
			str += "</tr><tr class=\"TableContent\">";
		}
		if (this.ms[i].sub_items.length != 0) {
			str += this.ms[i].ListCode();
		}
	}
	str += "</tr></table>";
	return str;
};
QerMenu.prototype.SetLineNum = function (n){
	this.line_num=n;
}
//向总菜单添加菜单组或菜单项
QerMenu.prototype.AddMenu = function (menu, v_sel,type, m) {
	if (typeof (v_sel) == "undefined") {
		v_sel = 0;
	}
	if (typeof (type) == "undefined") {
		type = "num";
	}
	if (typeof (m) == "undefined") {
		m = {};
	}
	var f_no = menu.father_no;
	if (f_no == -1) {
		this.ms.push(new QerItem(menu, v_sel, type, this));
	} else {
		this.right_main = this.right_main != "/Seal/general/gvmt/etpr_mng/index.jsp" ? menu.menu_href : this.right_main;
		var item = this.findByNo(f_no);
		item.addSubItem(menu, v_sel);
	}
};
//根据菜单组名查找菜单组对象
QerMenu.prototype.findByNo = function (no) {
	for (var i = 0; i < this.ms.length; i = i + 1) {
		if (this.ms[i].menu_no == no) {
			return this.ms[i];
		}
	}
};
/**
**	父级对象：QerItem
**/
function QerItem(menu,v_sel,type,m) {
	if (typeof (type) == "undefined") {
		type = "num";
	}
	if (typeof (v_sel) == "undefined") {
		v_sel = type=="num"?new Array(0):"";
	}
	if (typeof (m) == "undefined") {
		m = {};
	}
	this.p_id=m.id;
	this.menu_no = menu.menu_no;
	this.father_no = menu.father_no;
	this.menu_name = menu.menu_name;
	this.func_value = menu.func_value;
	this.menu_href = menu.menu_href;
	this.func_group = menu.func_group==null?1:menu.func_group;
	if(type=="num"){
		this.selected = (Number(menu.func_value) & Number(v_sel[this.func_group-1])) == Number(menu.func_value);
	}
	this.sub_items = new Array();
	this.icon=menu.icon==null?"/Seal/images/menu/@asset.gif":menu.icon;
}
QerItem.prototype.CheckSumByGroup = function (group){
	var sum=this.selected?this.func_value:0;
	for(var i=0;i<this.sub_items.length;i=i+1){
		var obj=this.sub_items[i];
		sum+=obj.selected&&(obj.func_group==group)?obj.func_value:0;
	}
	return sum;
}
QerItem.prototype.addSubItem = function (menu, v_sel,type) {
	if (typeof (type) == "undefined") {
		type = "num";
	}
	if (typeof (v_sel) == "undefined") {
		v_sel = type=="num"?new Array(0):"";
	}
	this.sub_items.push(new QerSubItem(menu, v_sel,type,this));
};
QerItem.prototype.HtmlCode = function () {
	var str = "<li class=\"L1\"><a href=\"";
	str += this.Href(this.menu_no) + "\" id=\"m" + this.menu_no;
	str += "\"><span><img src=\""+this.icon+"\" align=\"absMiddle\" /> ";
	str += this.menu_name + " </span> </a></li>";
	str += "<ul id=\"m" + this.menu_no + "_m\" name=\"ul_obj\" class=\"U1\">";
	for (var i = 0; i < this.sub_items.length; i = i + 1) {
		str += this.sub_items[i].HtmlCode();
	}
	str += "</ul>";
//	str+="<ul id="m"+this.menu_no+"_m" style="display: none;" class="U1"></ul>";
//  alert(str);
	return str;
};
QerItem.prototype.ListCode = function () {
	this.Check();
	var str = "<td valign=\"top\">";
	str += "<table class=\"TableBlock\" align=\"center\">";
	str += "<tr ondblclick=\""+this.p_id+".ChgDisp('"+this.menu_no;
	str += "');\" class=\"TableHeader\" title=\"" + this.menu_name + "\">";
	str += "<td nowrap>";
	str += "<input title=\""+this.menu_name+"\" type=\"checkbox\" name=\"m" + this.menu_no;
	str += "\" id=\"m" + this.menu_no + "\" onClick=\""+this.p_id+".CheckAll(this,'";
	str += this.menu_no + "');\" value=\"" + this.func_value + "\"";
	if (this.selected) {
		str += " checked>";
	} else {
		str += " >";
	}
	str += " <img src=\""+this.icon+"\" width=19 height=17> <label onclick=\"";
	str += this.p_id+".ChgDisp('"+this.menu_no;
	str += "');\" ><b>" + this.menu_name + "</b></label>";
	str += "</td>";
	str += "</tr>";
	for (var i = 0; i < this.sub_items.length; i = i + 1) {
		str += this.sub_items[i].ListCode();
	}
	str += " </table>  </td>";
	return str;
};
QerItem.prototype.Href = function (menu_no) {
	var str = "javascript:menu_click('m";
	str += menu_no + "');";
	return str;
};
QerItem.prototype.Check = function (){
	this.selected=false;
	for(var i=0;i<this.sub_items.length;i=i+1){
		this.selected=this.selected||this.sub_items[i].selected;
	}
}
QerItem.prototype.findByNo = function (no) {
	for (var i = 0; i < this.sub_items.length; i = i + 1) {
		if (this.sub_items[i].menu_no == no) {
			return this.sub_items[i];
		}
	}
};

function QerSubItem(menu, v_sel,type,item) {
	if (typeof (type) == "undefined") {
		type = "num";
	}
	if (typeof (v_sel) == "undefined") {
		v_sel = type=="num"?new Array(0):"";
	}
	if (typeof (item) == "undefined") {
		item = {};
	}
	this.p_p_id=item.p_id;
	this.menu_no = menu.menu_no;
	this.father_no = menu.father_no;
	this.menu_name = menu.menu_name;
	this.func_value = menu.func_value;
	this.menu_href = menu.menu_href;
	this.func_group = menu.func_group==null?1:menu.func_group;
	if(type=="num"){
		this.selected = (Number(menu.func_value) & Number(v_sel[this.func_group-1])) == Number(menu.func_value);
	}else if(type=="str"){
		this.selected=isStrInStrs(this.menu_no,v_sel);
	}
	this.icon=menu.icon==null?"/Seal/images/menu/@bbs.gif":menu.icon;
}
QerSubItem.prototype.HtmlCode = function () {
	var str = "<li class=\"L22\"><a href=\"";
	str += this.menu_href + "\" target=\"right_main\" id=\"f3\"><span>";
	str += "<img src=\""+this.icon+"\" align=\"absMiddle\" /> ";
	str += this.menu_name + " </span> </a></li>";
//	str+="<ul id="m"+this.menu_no+"_m" style="display: none;" class="U1"></ul>";
	return str;
};
QerSubItem.prototype.ListCode = function () {
	var str = "<tr name=\"tr"+this.father_no+"\" title=\"" + this.menu_name + "\">";
	str += "<td class=\"TableData\" nowrap>";
	str += "<input title=\""+this.menu_name+"\" type=\"checkbox\" name=\"f";
	str += this.father_no + "\" id=\"sm" + this.menu_no + "\" onClick=\""+this.p_p_id+".CheckOne(this,'";
	str += this.father_no + "','"+this.menu_no+"');\" value=\"" + this.func_value + "\"";
	if (this.selected) {
		str += " checked>";
	} else {
		str += " >";
	}
	str += "<img src=\""+this.icon+"\" width=19 height=17> <label for=\"sm";
	str += this.menu_no + "\">" + this.menu_name + "</label>";
	str += "</td>";
	str += "</tr>";
	return str;
};

