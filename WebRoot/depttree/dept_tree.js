var tree=new QerTree("tree");// 树
var hasPersons=false;// 是否包含人员
var hasSeal=false;// 是否包含印章
var req="";// 请求的业务类型
var persons="";
var users="";
var seals="";
var flowCtrl="";
var afrom;
var user_no="";
function load() {
	hasPersons=getUrlParam("p")!=null?getUrlParam("p"):false;// 是否包含人员
	hasSeal=getUrlParam("s")!=null?getUrlParam("s"):false;// 是否包含印章
	req=getUrlParam("req")!=null?getUrlParam("req"):req;// 类型
	user_no=getUrlParam("user_no")!=null?getUrlParam("user_no"):user_no;// 用户id
	persons=getUrlParam("ps")!=null?getUrlParam("ps"):persons;
	seals=getUrlParam("ss")!=null?getUrlParam("ss"):seals;
	afrom=window.dialogArguments;
	if((req=="dept_mng")||(req=="dept_temp")||(req=="dept_chose")||(req=="dept_list")||(req=="search_user")||(req=="search_user_mng")||(req=="search_user_app")){// 部门
		dwr.engine.setAsync(false);// 同步
	    SysDept.showDeptByUser(user_no,cb_all);// 根据当前用户得到部门，只显示部门
	}
	else if((req=="seal_temp")||(req=="seal_user")||(req=="check_user")||(req=="seal_user")){
	    dwr.engine.setAsync(false);// 同步
	    SysDept.getUserList(user_no,useridStr);
	}else if(req=="showUser"){
	    dwr.engine.setAsync(false);// 同步
		SysDept.showDeptByUsers(persons,cb_all);// 根据用户ID列表字符串得到部门
	}else if(req=="showUser1"){
	    dwr.engine.setAsync(false);// 同步
		SysDept.showDeptByUsers(persons,cb_all_1);// 根据用户ID列表字符串得到部门
	}
	else if(hasSeal=="true"){
	    dwr.engine.setAsync(false);// 同步
		SysDept.showDeptByUser(user_no,cb_all_s);// 根据当前用户得到部门，并显示部门下印章
	}
}
function useridStr(strid){
   persons=strid;
   SysDept.showDeptByUsers(strid,cb_all);// 根据用户ID列表字符串得到部门
   
}
function chkBid(bid){
	if(bid == null){
		return "-999";
	}else{
		return bid;
	}
}
function cb_all(d) {// 得到部门列表，遍历加入树
	var data1 = new Date();
	for (var i = 0; i <d.length; i = i + 1) {
		var id=d[i].dept_no;
		// var pid=d[i].dept_parent.substr(d[i].dept_parent.length-3);
		var pid=d[i].dept_parent;
		var bid = chkBid(d[i].bank_dept);
		var name=d[i].dept_name;
		var url="#";
		var title=d[i].dept_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/linkman.gif";
		icon="";
		tree.add(id,pid,bid,name+"("+bid+")",url, title+"("+bid+")", target, onclick, icon);
		
	}
	if((req=="seal_temp")||(req=="seal_user")||(req=="showUser")||(req=="check_user")||(req=="seal_use")){
	    dwr.engine.setAsync(false);// 同步
		SysUser.showUsersByNos(persons,cb_persons);// 根据用户ID列表字符串得到用户
	}else if(req=="showUser1"){
	    dwr.engine.setAsync(false);// 同步
		SysUser.showUsersByNos(persons,cb_persons1);// 根据用户ID列表字符串得到用户
	}
	$("tree").innerHTML=tree.toString();// 显示树

}
function cb_all_1(d) {// 得到部门列表，遍历加入树
	for (var i = 0; i <d.length; i = i + 1) {
		var id=d[i].dept_no;
		// var pid=d[i].dept_parent.substr(d[i].dept_parent.length-3);
		var pid=d[i].dept_parent;
		var bid = chkBid(d[i].bank_dept);
		var name=d[i].dept_name;
		var url="";
		var title=d[i].dept_name;
		var target="right_main";
		var onclick="";
		var icon="/Seal/dtree/img/linkman.gif";
		icon="";
		tree.add(id,pid,bid,name+"("+bid+")",url, title+"("+bid+")", target, onclick, icon);
	}
    SysUser.showUsersByNos(persons,cb_persons1);// 根据用户ID列表字符串得到用户
	$("tree").innerHTML=tree.toString();// 显示树
}
function cb_persons(d){// 根据部门得到用户列表
	for (var i = 0; i <d.length; i = i + 1) {
		var id="u_"+d[i].unique_id;
		// var pid=d[i].dept_no.substr(d[i].dept_no.length-3);
		var pid=d[i].dept_no;
		var bid = chkBid(d[i].bank_dept);
		var name=d[i].user_name;
		var url="#";
		var title=d[i].user_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/node_user.gif";
		tree.add(id,pid,bid,name+"("+bid+")",url, title+"("+bid+")", target, onclick, icon);
	}
}
function cb_persons1(d){
	for (var i = 0; i <d.length; i = i + 1) {
		var id="u_"+d[i].unique_id;
		// var pid=d[i].dept_no.substr(d[i].dept_no.length-3);
		var pid=d[i].dept_no;
		var bid = chkBid(d[i].bank_dept);
		var name=d[i].user_name;
		var url="";
		var title=d[i].user_name;
		var target="right_main";
		var onclick="";
		var icon="/Seal/dtree/img/node_user.gif";
		tree.add(id,pid,bid,name+"("+bid+")",url, title+"("+bid+")", target, onclick, icon);
	}
}
function toClickStr(d){
     if(req=="dept_mng"){  
	 	if(d.dept_no=="000")return "return false;";
	 	var url="/Seal/showDept.do?dept_no="+d.dept_no;
		return "goTo('"+url+"');return false;";
	 }else if(req =='dept_temp'){  
	 	return "dept_temp('"+d.dept_no+"','"+d.dept_name+"');return false;"; 
	 }else if(req=="dept_chose"){
	 	return "returnf('"+d.dept_name+"','"+d.dept_no+"');return false;";
	 }else if(req =='seal_temp'){  
	 	return "seal_temp('"+d.user_id+"','"+d.user_name+"');return false;"; 
	 }else if(req=='seal_user'){
	   return "seal_user('"+d.user_id+"','"+d.user_name+"');return false;"; 
	 }else if(req=='dept_list'){
	    var url="/Seal/tempAppuse.do?type=flag1&&dept_no="+d.dept_no;
	 	return "goTo('"+url+"');return false;"; 
	 }else if(req=='check_user'){
	 	return "check_user('"+d.user_id+"','"+d.user_name+"');return false";
	 }else if(req=='search_user'){
	 	var url="/Seal/serUserlist.do?type=flag1&&dept_no="+d.dept_no;
	 	return "goTo('"+url+"');return false;";
	 }else if(req=='search_user_mng'){
	 	var url="/Seal/serUserManager.do?type=flag1&&dept_no="+d.dept_no;
	 	return "goTo('"+url+"');return false;";
	 }else if(req=='seal_use'){
	 	return "seal_use('"+d.user_id+"','"+d.user_name+"');return false;"; 
	 }else if(req=="new_rule"){
	 	return "seal('"+d.seal_id+"','"+d.seal_name+"');return false;";
	 }else if(req=="search_user_app"){
		 var url="/Seal/serUserApprove.do?type=flag1&&dept_no="+d.dept_no;
		 return "goTo('"+url+"');return false;";
	 }
}
function seal(seal_id,seal_name){
	if(seal_id!="undefined"){
		afrom.seal_no.value=seal_id;
		afrom.seal_name.value=seal_name;
		this.close();
	}
}
function dept_temp(dept_no,dept_name){
    if((dept_no!="undefined")||(dept_name!="undefined")){
	 afrom.dept_no.value=dept_no;
	 afrom.dept_name.value=dept_name;
	 this.close();
  } 
}
function seal_user(userid,username){
   var MuserNo=afrom.MuserNo.value;
   var MuserName=afrom.MuserName.value;
   if(userid!="undefined"){
   if(MuserNo.indexOf(userid+",")==-1){
	    afrom.MuserNo.value+=userid+",";
		afrom.MuserName.value+=username+",";
	}else{
		var p=MuserNo.indexOf(userid+",");
		var n=p+userid.length;
		var pStr=MuserNo.substr(0,p);
		var nStr=MuserNo.substr(n+1,MuserNo.length);
		afrom.MuserNo.value=pStr+nStr;
		var p1=MuserName.indexOf(username+",");
		var n1=p1+username.length;
		var pStr1=MuserName.substr(0,p1);
		var nStr1=MuserName.substr(n1+1,MuserName.length);
		afrom.MuserName.value=pStr1+nStr1;
	}
  }
}
function cb_func(b){func=b;}
function seal_temp(user_id,user_name){
    if(user_id!="undefined"){
		dwr.engine.setAsync(false);
// if(user_no==user_id){
// alert("对不起，印模审批人不能选自己！");
// return ;
// }
		SysFunc.isFuncAble(1,0x100,user_id,cb_func);
		/*
		 * if(!func){ alert("对不起，该用户没有印模审批权，请重选！"); return ; }
		 */
	 afrom.approve_user.value=user_id;
	 afrom.approve_name.value=user_name;
	 this.close();
    } 
}
function goTo(url){
	parent.dept_main.location=url;
}

function ret(){
	if(!window.returnValue)
		window.returnValue='default';
}
function returnf(dept_name,dept_no) {
	var no=window.dialogArguments;
	if(no!=undefined){
		no.value=dept_no;
	}
	// 模式窗口的返回值
	
	window.returnValue=dept_name;
	self.close();
	return;
}

function check_user(user_id,user_name){
	 if(user_id!="undefined"){
	 afrom.create_name2.value=user_name;
	 afrom.create_name.value=user_id;
	 this.close();
    } 
    
}
function cb_all_s(d){// 得到部门列表，并同时根据部门得到部门下印章列表
	for (var i = 0; i <d.length; i = i + 1) {// 遍历部门列表
		var id=d[i].dept_no;
		// var pid=d[i].dept_parent.substr(d[i].dept_parent.length-2);
		var pid=d[i].dept_parent;
		var bid = chkBid(d[i].bank_dept);
		var name=d[i].dept_name;
		var url="#";
		var title=d[i].dept_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/dtree/img/node_user.gif";
		icon="";
        tree.add(id,pid,bid,name,url, title, target, onclick, icon);
		dwr.engine.setAsync(false); // 设置方法调用是否同步，false表示同步
		// SealBody.showSealsByDept(d[i].dept_no,cb_seals);
		SealBody.showSealsByDept5(d[i].dept_no,cb_seals);
	}
	if(req!="showUser"){
		$("tree").innerHTML=tree.toString();
	}
}
function cb_seals(d){// 得到印章列表，遍历加入树
	for (var i = 0; i <d.length; i = i + 1) {
		var id="s_"+d[i].seal_id;
		// var pid=d[i].dept_no.substr(d[i].dept_no.length-2);
		var pid=d[i].dept_no;
		var bid = chkBid(d[i].bank_dept);
		var name=d[i].seal_name;
		var url="#";
		var title=d[i].seal_name;
		var target="right_main";
		var onclick=toClickStr(d[i]);
		var icon="/Seal/images/menu/seal.gif";
		tree.add(id,pid,bid,name,url, title, target, onclick, icon);
	}
}
function selectNode(){
	var bank_id = document.getElementById("bank_id").value;
	tree.openTos(bank_id);
}
function seal_use(userid,username){
   var MuserNo1=afrom.MuserNo1.value;
   var MuserName1=afrom.MuserName1.value;
   if(userid!="undefined"){
	    afrom.MuserNo1.value=userid;
		afrom.MuserName1.value=username;
  }
}