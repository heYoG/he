var obj=new QerTable("tb_lists");
var ob = new QerTable("xq_list");
var page=1;
var pageSize=10;
var user_no = "";
var cdt_s="";
var cdt_f={};
var reg="";//判断
var thisUrl=baseUrl(3)+"Seal/general/hotel/record/tongji1.jsp";

function list_load(d){
	user_no = d;
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	cdt_f.user_no=user_no;
	showData();
	show_sch();
	document.getElementById("showname").innerHTML=user_name;
}


//填充数据
function showData(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
	obj.row_has_chk=false;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('类型');
	head.push('数量');
	obj.SetHead(head);
}


function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelRecord.getTongJi1(cdt_f,cb_data);
}

function cb_data(ps){
		for(var i=0;i<ps.length;i=i+1){
			var row=toArray(ps[i]);
			obj.AddRow(row,"",i+1);
	}
}

// 日历控件
function GetDate(nText) {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].begin_time.value = reVal;
		}
		if(nText == 2){
			document.forms[0].end_time.value = reVal;
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&cdt="+cdt_s;
}


function searchdatechange(vvv){
	if(vvv=="7"){
		document.getElementById("datetable").style.display="block";
	}else{
		document.getElementById("datetable").style.display="none";
	}
}
	 
function toArray(d){
	var row=new Array();
	row.push(d.model_name);
	row.push(d.number);
	return row;
}




//搜索
function searchRecord(){
	var f=$("f_sch");
	var b_date_type = f.date_type.value;
	if(b_date_type=="7"){
		var b_begin_time = f.begin_time.value;
		var b_end_time = f.end_time.value;
		if(b_begin_time==""||b_end_time==""){
			alert("开始日期及结束日期不能为空!");
			return false;
		}else{
			if(b_begin_time>b_end_time){
				alert("开始日期不能大于结束日期!");
				return false;
			}
			if(b_begin_time<"2014-06-01"){
				alert("请选择2014年6月以后的日期!");
				return false;
			}
			var nian1 = b_begin_time.substring(0,4);
			var nian2 = b_end_time.substring(0,4);
			var yue1 = b_begin_time.substring(5,7);
			var yue2 = b_end_time.substring(5,7);
			var niancha = nian2-nian1;//得出年份差距
			yue2 = parseInt(yue2) + parseInt(niancha)*12;
			var yuecha = parseInt(yue2)-parseInt(yue1);
			if(yuecha>=6){
				if(!confirm("查询的日期跨度超过了半年，查询时间较长，确定要查询吗？")){
					return false;
				}
			}
		}
	}
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	
	var b_date_type = f.date_type.value;
	s=""==s?s+"date_type:="+b_date_type:s+"!date_type:="+b_date_type;
	
	var b_begin_time = f.begin_time.value;
	if(!checkStr(b_begin_time,"开始时间")){
		return false;
	}
	s=""==s?s+"begin_time:="+b_begin_time:s+"!begin_time:="+b_begin_time;
	
	var b_end_time = f.end_time.value;
	if(!checkStr(b_end_time,"结束时间")){
		return false;
	}
	s=""==s?s+"end_time:="+b_end_time:s+"!end_time:="+b_end_time;
	
	
	return s;
}


//高级搜索按钮
function show_sch(){
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("date_type"==k_v[0]){
			f.date_type.value=k_v[1];
		}
		if("begin_time"==k_v[0]){
			f.begin_time.value=k_v[1];
		}
		if("end_time"==k_v[0]){
			f.end_time.value=k_v[1];
		}
	}
	searchdatechange(f.date_type.value);
}


//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("date_type"==k_v[0]){
			f.date_type=k_v[1];
		}
		if("begin_time"==k_v[0]){
			f.begin_time=k_v[1];
		}
		if("end_time"==k_v[0]){
			f.end_time=k_v[1];
		}
	}

	return f;
}

