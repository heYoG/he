var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/cert/cert_mng/cert_list.jsp";

function load(){
	if(location==baseUrl(3)+"Seal/certUpd.do"){
		location=thisUrl;
	}
	new_cert_load();
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	showData();
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
}
function cb_certs2(d){
	var certs=d.split(",");
	var sel=$("sign_cert_sch");
	for(var i=0;i<certs.length;i++){
		selectAdd(sel,certs[i],certs[i]);
	}
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
	head.push('证书名称');
	head.push('证书来源');
	head.push('登记人');
	head.push('登记时间');
	head.push('操作');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	CertSrv.showObjBySch(page,pageSize,cdt_f,cb_data);
}
function cb_data(ps){
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;	
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].cert_no,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page;
}
function getCertSrc(d){
  if(d.cert_src=="sing"){
     return "签名服务器";
  }else if(d.cert_src=="server"){
     return "服务器文件证书";
  }else if(d.cert_src=="sign"){
     return "加密机证书";
  }else if(d.cert_src=="clientPFX"){
     return "客户端pfx证书";
  }else if(d.cert_src=="clientCert"){
     return "客户端公钥证书";
  }else if(d.cert_src=="iepfx"){
     return "IE证书";
  }
}
function toArray(d){
	var row=new Array();
	row.push(d.cert_name);
	row.push(getCertSrc(d));
	row.push(d.reg_user_name);
	row.push(d.reg_time_str);
	var opStr=toOprStr(d);
	row.push(opStr);
	return row;
}

function toSealStr(d){
	if(d.seal_num==0){
		return "0";
	}
	var str="";
	str+="<a href='#' onclick=\"showSeal('";
	str+=d.seal_sel+"');flag=true;\" >";
	str+=d.seal_num+"</a>";
	return str;
}

function showSeal(seal_sel){
	var url="/Seal/general/dept_tree.jsp?req=showSeal&&user_no="+user_no+"&&ss="+seal_sel;
	var width=300;
	var height=450;
	newModalDialog(url, width, height);
}

function toOprStr(d){
	var str="";
	//str+="<input type='button' onclick=\"updCert('";
	//str+=d.cert_no;
	//str+="');flag=true;\" class='SmallButton' value='修改' />";
	if(d.cert_src=="sing" || d.cert_src=="server"){
	str+="&nbsp;&nbsp;";
	str+="<input type='button' onclick=\"delCert('";
	str+=d.cert_no+"','"+d.cert_name;
	str+="');flag=true;\" class='SmallButton' value='删除' />";
	}
	
	//if(d.cert_status=="1"){
	///str+="&nbsp;&nbsp;";
	//str+="<input type='button' onclick=\"cheCert('";
	///str+=d.cert_no+"','"+d.cert_name;
	//str+="');flag=true;\" class='SmallButton' value='激活' />";
	//}
	//if(d.cert_status=="2"){
	//if(d.is_Sealbody=="1"){
	//str+="&nbsp;&nbsp;";
	//str+="<input type='button' onclick=\"zhuCert2('";
	//str+=d.cert_no+"','"+d.cert_name;
	//str+="');flag=true;\" class='SmallButton' value='注销' />";
	//}else{
	//str+="&nbsp;&nbsp;";
	//str+="<input type='button' onclick=\"zhuCert('";
	//str+=d.cert_no+"','"+d.cert_name;
	//str+="');flag=true;\" class='SmallButton' value='注销' />";	
	//}
//	}	
	return str;
}

function delCert(no,name){ 
	if(confirm("确定要删除吗？")){
		CertSrv.objDel(no,name);
		LogSys.logAdd(user_no,user_name,user_ip,"证书管理","删除证书："+name);//logOper.js
		location=thisUrl+"?page="+page;
	}
}
function delCert2(no,name){
	alert("该证书已经绑定印章不可删除!");
}
function zhuCert2(no,name){
	alert("该证书已经绑定印章不可注销!");
}
function zhuCert(no,name){
	if(confirm("确定要注销证书吗？")){
		CertSrv.zhuDel(no);
		logDel("证书",no,name);//logOper.js
		location=thisUrl+"?page="+page;
	}
}
function cheCert(no,name){
	if(confirm("确定要激活证书吗？")){
		CertSrv.cheDel(no);
		logDel("证书",no,name);//logOper.js
		location=thisUrl+"?page="+page;
	}
}
function pl_del(){
	var sel=chkSlct();
	if(sel&&confirm("与印章相关联的证书将不被删除，确定要删除吗？")){
		CertSrv.plDel(sel);
		logPlDel("证书",sel,"");//logOper.js
		location=thisUrl+"?page="+page;
	}
}

function updCert(no){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	CertSrv.getObj(no,cb_objs);
	ShowDialog("detail",30);
}
function cb_objs(obj){
	var f=$("f_edit");
	f.reset();
	f.cert_no.value=obj.cert_no;
	f.cert_name.value=f.old_name.value=obj.cert_name;
	f.cert_src.value=obj.cert_src;
	certSrcChg(f.cert_src);
	f.cert_detail.value=obj.cert_detail;
	if(obj.cert_src=="sign"){
		f.sign_cert.value=f.old_cert.value=obj.cert_detail;
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

function objUpd(){
	var f=$("f_edit");
	if(checkForm(f,'edit')&&confirm("确定要修改吗?")){
		logUpd("证书",f.cert_no.value,f.cert_name.value,"原名为:"+f.old_name.value);//logOper.js
		f.submit();
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
		if("cert_name"==k_v[0]){
			f.cert_name.value=k_v[1];
		}
		if("status_cert"==k_v[0]){
			f.status_cert.value=k_v[1];
		}
		if("sign_cert"==k_v[0]){
			f.sign_cert_sch.value=k_v[1];
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
//	alert(c);
	if(c){
		location=thisUrl+"?page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	var cert_name=f.cert_name.value;
	if(!checkStr(cert_name,"证书名称")){
		return false;
	}
	s=""==s?s+"cert_name:="+cert_name:s+"!cert_name:="+cert_name;
	var status_cert=f.status_cert.value;
	s=""==s?s+"status_cert:="+status_cert:s+"!status_cert:="+status_cert;
	var sign_cert=f.sign_cert_sch.value;
	if(!checkStr(sign_cert,"签名证书")){
		return false;
	}
	s=""==s?s+"sign_cert:="+sign_cert:s+"!sign_cert:="+sign_cert;
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("cert_name"==k_v[0]){
			f.cert_name=k_v[1];
		}
		if("status_cert"==k_v[0]){
			f.status_cert=k_v[1];
		}
		if("sign_cert"==k_v[0]){
			f.sign_cert=k_v[1];
		}
	}
	return f;
}

