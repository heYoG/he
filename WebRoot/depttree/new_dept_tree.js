var tree=new QerTree("tree");//树
var hasPersons=false;//是否包含人员
var hasSeal=false;//是否包含印章
var req="";//请求的业务类型
var persons="";
var sealidstr="";
var users="";
var seals="";
var flowCtrl="";
var afrom;
var user_no="";

function load() {
	hasPersons=getUrlParam("p")!==null?getUrlParam("p"):false;//是否包含人员
	hasSeal=getUrlParam("s")!==null?getUrlParam("s"):false;//是否包含印章
	req=getUrlParam("req")!==null?getUrlParam("req"):req;//请求类型
	user_no=getUrlParam("user_no")!==null?getUrlParam("user_no"):user_no;
	persons=getUrlParam("ps")!==null?getUrlParam("ps"):persons;
	seals=getUrlParam("ss")!==null?getUrlParam("ss"):seals;
	flowCtrl=getUrlParam("fc")!==null?getUrlParam("fc"):false;//是否流程控制
	afrom=window.dialogArguments;
//	alert(req);
	if(hasSeal=="true"){
		SysDept.showNewDeptTreeByUser(user_no,"",true,cb_all_s);
	}else if(hasPersons=="true"){
		dwr.engine.setAsync(false);
		SysDept.getUserList(user_no,useridStr);
	}else{
		SysDept.showNewDeptTreeByUser(user_no,"",true,cb_all);//根据当前用户得到部门，只显示部门
	}
//	if(req=="dept_mng"||req=="dept_choose"||(req=="search_user")||(req=="search_user_mng")){
//		//部门管理、选择部门、用户查询、用户管理
//	    dwr.engine.setAsync(false);
//		SysDept.showNewDeptTreeByUser(user_no,"",true,cb_all);//根据当前用户得到部门，只显示部门
//	}else if((req=="seal_temp")){
//	    dwr.engine.setAsync(false);
//	    SysDept.getUserList(user_no,useridStr);
//	}
}

function clk(id){
	if(hasSeal=="true"){
		dwr.engine.setAsync(false);
		SysDept.showNewDeptTreeByUser(user_no,change2(id),tree.setClkId(id),cb_all_s);
	}else if(hasPersons=="true"){
		dwr.engine.setAsync(false);
		SysDept.getUserList(user_no,useridStr);
	}else{
		SysDept.showNewDeptTreeByUser(user_no,change2(id),tree.setClkId(id),cb_all);
	}
}

function useridStr(strid){
   persons=strid;
   SysDept.showDeptByUsers(persons,cb_all);//根据用户ID列表字符串得到部门
}

function cb_sealidstr(d){
	sealidstr=d;
	dwr.engine.setAsync(false);
	SysDept.showDeptBySeals(sealidstr,cb_all_s);//根据印章ID列表字符串得到部门
}

function cb_users(d){
	users=d.substr(0,d.length-1);
}

function cb_all(d) {//得到部门列表，遍历加入树
	tree.clear(d);
//	alert(d.length);
	for (var i = 0; i <d.length; i = i + 1) {
		var id=d[i].priv_no;
		id=d[i].tree_id;
		var pid=d[i].dept_parent.substr(d[i].dept_parent.length-3);
		pid=d[i].p_tree_id;
		var name=d[i].dept_name;
		var url="#";
		var title=d[i].dept_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/linkman.gif";
		icon="";
		tree.add(id,pid,name,url, title, target, onclick, icon);
	}
	if(req=="showUser"){
		dwr.engine.setAsync(false);
		SysUser.showUsersByNos(persons,cb_persons);//根据用户ID列表字符串得到用户
	}else if(req=="showSeal"){
		dwr.engine.setAsync(false);
		SealBody.showSealsByNos(seals,cb_seals);//根据印章ID列表字符串得到印章
	}
	$("tree").innerHTML=tree.toString();//显示树
}
function cb_all_p(d){//得到部门列表，遍历加入树，并同时根据部门得到部门下用户
	for (var i = 0; i <d.length; i = i + 1) {
		var id=d[i].priv_no;
		var pid=d[i].dept_parent.substr(d[i].dept_parent.length-2);
		var name=d[i].dept_name;
		var url="#";
		var title=d[i].dept_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/node_user.gif";
		icon="";
        tree.add(id,pid,name,url, title, target, onclick, icon);
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		SysUser.showSysUsersByDept_no(d[i].dept_no,cb_persons);
	}
	if(req!="showUser"){
		$("tree").innerHTML=tree.toString();
	}
}

function cb_persons(d){//得到用户列表，并加入树
	for (var i = 0; i <d.length; i = i + 1) {
		var id="u_"+d[i].unique_id;
		var pid=d[i].dept_no.substr(d[i].dept_no.length-3);
		var name=d[i].user_name;
		var url="#";
		var title=d[i].user_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/node_user.gif";
		tree.add(id,pid,name,url, title, target, onclick, icon);
	}
}


var current_tree_id="";

function cb_all_s(d){//得到部门列表，并同时根据部门得到部门下印章列表
	tree.clear(d);
	for (var i = 0; i <d.length; i = i + 1) {
		var id=d[i].priv_no;
		id=d[i].tree_id;
		var pid=d[i].dept_parent.substr(d[i].dept_parent.length-3);
		pid=d[i].p_tree_id;
		var name=d[i].dept_name;
		var url="#";
		var title=d[i].dept_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/linkman.gif";
		icon="";
		tree.add(id,pid,name,url, title, target, onclick, icon);
		current_tree_id=id;
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		SealBody.showPublicSealsByDept(d[i].dept_no,cb_seals);
	}
//	alert(tree.toString());
	$("tree").innerHTML=tree.toString();
}

function cb_seals(d){//得到印章列表，遍历加入树
	for (var i = 0; i <d.length; i = i + 1) {
		var id="s_"+d[i].seal_id;
		var pid=d[i].dept_no.substr(d[i].dept_no.length-3);
		pid=current_tree_id;
		var name=d[i].seal_name;
		var url="#";
		var title=d[i].seal_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/seal.gif";
		tree.add(id,pid,name,url, title, target, onclick, icon);
	}
}

function toClickStr(d){
	 if(req=="showUser"){
	 	return "return false;";
	 }else if(req=="showSeal"){
	 	return "return false;";
	 }else if(req=="dept_mng"){
	 	if(d.dept_no=="000"){return "return false;";}
	 	var url="/Seal/showDept.do?dept_no="+d.dept_no;
		return "goTo('"+url+"');return false;";
	 }else if(req=="dept_choose"){
	 	return "dept_choose('"+d.dept_name+"','"+d.dept_no+"');return false;";
	 }else if(req=="search_user"){
	 	var url="/Seal/serUserlist.do?type=flag1&&dept_no="+d.dept_no;
	 	return "goTo('"+url+"');return false;";
	 }else if(req=="search_user_mng"){
	 	var url="/Seal/serUserManager.do?type=flag1&&dept_no="+d.dept_no;
	 	return "goTo('"+url+"');return false;";
	 }else if(req =="user_chose"){
		var url="/Seal/general/organise/user_mng/user_list.jsp?dept_no="+d.dept_no;
		return "goTo('"+url+"');return false;";
	 }else if(req =="new_rule"){
		return "seal('" + d.seal_id + "','" + d.seal_name + "');return false;";
	 }
}

function goTo(url){
	parent.dept_main.location=url;
}

function ret(){
	if(!window.returnValue)
		window.returnValue='default';
}

function dept_choose(dept_name,dept_no) {
//	var no=window.dialogArguments;
//	if(no!=undefined){
//		no.value=dept_no;
//	}
//	//模式窗口的返回值
//	window.returnValue=dept_name;
//	self.close();
//	return;
 if((dept_no!="undefined")||(dept_name!="undefined")){
	 afrom.dept_no.value=dept_no;
	 afrom.dept_name.value=dept_name;
	 this.close();
  } 
}

function seal(seal_id, seal_name) {
	if (seal_id != "undefined") {
		afrom.seal_no.value = seal_id;
		afrom.seal_name.value = seal_name;
		this.close();
	}
}

var func=true;
function cb_func(b){func=b;}

function new_app(user_id,user_name){
	if(user_id!="undefined"){
		dwr.engine.setAsync(false);
	    if(user_no==user_id){
	    	alert("对不起，办理人不能选自己！");
			return ;
	    }
		SysFunc.isFuncAble(2,0x2,user_id,cb_func);
		if(!func){
			alert("对不起，该用户没有业务办理权限，请重选！");
			return ;
		}
		afrom.banli_user.value=user_id;
		afrom.user_name.value=user_name;
		this.close();
 	} 
}

function nextFlow(user_id,user_name){
	if(user_id!="undefined"){
		if(user_no==user_id){
	    	alert("对不起，办理人不能选自己！");
			return ;
	    }
		afrom.user_no.value=user_id;
		afrom.user_name.value=user_name;
		this.close();
 	} 
}
