var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/cert/cert_query/cert_list.jsp";

function load(){
//alert(location);
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
//	cdt_f.type_busi=3;
	if(cdt_s==""){
		hidden("d_list");
		disp("d_search");
	}
	showData();
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	CertSrv.getSignCertStr(cb_certs);
}
function cb_certs(d){
	var certs=d.split(",");
	var sel=$("sign_cert");
	for(var i=0;i<certs.length;i++){
		selectAdd(sel,certs[i],certs[i]);
	}
}


//填充数据
function showData(){
//	alert("填充数据");
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
//	obj.row_has_chk=true;
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('证书名称');
	//head.push('证书来源');
	//head.push('指定签名证书');
	//head.push('印章数量');
//	head.push('证书路径');
	head.push('登记人');
	head.push('登记时间');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
//	UserSrv.showUsersByArea(page,pageSize,area_no,cb_data);
//alert(cdt_f.status_busi);
	CertSrv.showObjBySch(page,pageSize,cdt_f,cb_data);
}
function cb_data(ps){
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].busi_no,i+1);
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
	row.push(d.cert_name);
	//var srcName=d.cert_src=="sign"?"签名服务器":"服务器文件证书";
	//row.push(srcName);
//	if(d.cert_src=="sign"){
		//row.push(d.cert_detail);
//		row.push("");
//	}else{
//		row.push("");
//		row.push("/certs/"+d.cert_detail+".pfx");
//	}
	//row.push(toSealStr(d));
	row.push(d.reg_user_name);
	row.push(d.reg_time);
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
			f.sign_cert.value=k_v[1];
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
	var sign_cert=f.sign_cert.value;
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

