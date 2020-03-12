var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/cert/cert_reg/cert_list.jsp";

function load(){
//alert(location);
	if(location==baseUrl(3)+"Seal/certAdd.do"){
		location=thisUrl;
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
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
//	obj.row_has_chk=true;
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('证书名称');
	head.push('证书来源');
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
	row.push(getCertSrc(d));
	row.push(d.reg_user_name);
	row.push(d.reg_time_str);
	return row;
}


//高级搜索
function show_sch(){
	alert("待开发。。。");
	return false;
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("busi_name"==k_v[0]){
			f.busi_name.value=k_v[1];
		}
		if("status_busi"==k_v[0]){
			f.status_busi.value=k_v[1];
		}
		if("busi_discript"==k_v[0]){
			f.busi_discript.value=k_v[1];
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
	var b_name=f.busi_name.value;
	if(!checkStr(b_name,"业务名")){
		return false;
	}
	s=""==s?s+"busi_name:="+b_name:s+"!busi_name:="+b_name;
	var b_status=f.status_busi.value;
	s=""==s?s+"status_busi:="+b_status:s+"!status_busi:="+b_status;
	var b_discrpt=f.busi_discript.value;
	if(!checkStr(b_discrpt,"业务描述")){
		return false;
	}
	s=""==s?s+"busi_discript:="+b_discrpt:s+"!busi_discript:="+b_discrpt;
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("busi_name"==k_v[0]){
			f.busi_name=k_v[1];
		}
		if("status_busi"==k_v[0]){
			f.status_busi=k_v[1];
		}
		if("busi_discript"==k_v[0]){
			f.busi_discript=k_v[1];
		}
	}
	return f;
}

