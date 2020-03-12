var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/hotel/log/log_list.jsp";

function list_load(){
// alert(location);
	if(location==baseUrl(3)+"Seal/neLog.do"){
		location=thisUrl;
	}

	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	
	showData();
}

// 填充数据
function showData(){
// alert("填充数据");
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
// obj.row_has_chk=true; 多选框
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('用户名称');
	head.push('操作时间');
	head.push('IP地址');
//	head.push('MAC地址');
	head.push('操作类型');
	head.push('对象名称');
	head.push('结果');
	obj.SetHead(head);
}


function getDatas(){
	dwr.engine.setAsync(false); // 设置方法调用是否同步，false表示同步
	ClientOperLog.showLogBySch(page,pageSize,cdt_f,cb_data);
}

function cb_data(ps){
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;

		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].rule_no,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&cdt="+cdt_s;
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

function searchdatechange(vvv){
	if(vvv=="7"){
		document.getElementById("datetable").style.display="block";
	}else{
		document.getElementById("datetable").style.display="none";
	}
}
	 
function toArray(d){
	var row=new Array();
	row.push(d.operuserid);
	row.push(d.copertime.Format("yyyy-MM-dd hh:mm:ss"));
	row.push(d.cip);
//	row.push(d.cmac);
	var typename = thischangeType(d.opertype);
	row.push(typename);
	row.push(d.objname);
	var ret = changeRet(d);
	row.push(ret);
	return row;
}

// 操作类型
function thischangeType(opertype){
	var typename = "";
	if(opertype==1){
		typename="登录";
	}
	if(opertype==2){
		typename="上传文件";
	}
	return typename;
}

function changeRet(d){
	var ret = "";
	if(d.result==0){
		ret="成功";
	}
	if(d.result==1){
		ret="失败";
	}
	return ret;
}



function chkSlct(){
	var sel=obj.Selected();
	if(sel==""){
		alert("请选择需要操作的对象！");
		return false;
	}
	return sel.toString();
}



// 返回
function ret(){
	hidden("d_search");
	disp("d_list");
}



function cb_objs(obj){
	var f=$("f_edit");
	dwr.engine.setAsync(false); // 设置方法调用是否同步，false表示同步
	f.reset();

	check_type(f.id);// fromZY.js
}

function checkIP(userip){
        if(userip==""){
	        return true;
        }else{
          var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
          flag_ip=pattern.test(userip);
          if(!flag_ip){ 
            alert("请输入正确的IP地址!");  
        	return false;
          }else{
             return true;
         }
     }   
 }

// 搜索
function searchRecord(){
	var f=$("f_sch");
	if(!checkIP(f.operIP.value)){
		return false;
	}
	var b_date_type = f.date_type.value;
	if(b_date_type=="7"){
		var b_begin_time = f.begin_time.value;
		var b_end_time = f.end_time.value;
		if(b_begin_time==""||b_end_time==""){
			alert("开始时间及结束时间不能为空!");
			return false;
		}else{
			if(b_begin_time>b_end_time){
				alert("开始时间不能大于结束时间!");
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
				if(!confirm("查询的时间跨度超过了半年，查询时间较长，确定要查询吗？")){
					return false;
				}
			}
		}
	}
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?page=1&cdt="+c;
	}
}



// 得到搜索条件的字符串
function fToCdt(f){
	var s="";
	var b_name=f.userName.value;
	
	if(!checkStr(b_name,"用户姓名")){
		return false;
	}
	s=""==s?s+"operuserid:="+b_name:s+"!operuserid:="+b_name;
	
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
	
	var b_operIP = f.operIP.value;
	s=""==s?s+"cip:="+b_operIP:s+"!cip:="+b_operIP;
	
	var b_operType = f.operType.value;
	if(!checkStr(b_operType,"操作类型")){
		return false;
	}
	s=""==s?s+"opertype:="+b_operType:s+"!opertype:="+b_operType;
	
	var b_objName = f.objName.value;
	if(!checkStr(b_objName,"对象名称")){
		return false;
	}
	s=""==s?s+"objname:="+b_objName:s+"!objname:="+b_objName;
	
	var b_operRet = f.operRet.value;
	if(!checkStr(b_operRet,"操作结果")){
		return false;
	}
	s=""==s?s+"result:="+b_operRet:s+"!result:="+b_operRet;
	
	//s=""==s?s+"coperetime:="+b_copereTime:s+"!coperetime:="+b_copereTime;
	
	return s;
}

// 高级搜索按钮
function show_sch(){
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("operuserid"==k_v[0]){
			f.userName.value=k_v[1];
		}
		if("date_type"==k_v[0]){
			f.date_type.value=k_v[1];
		}
		if("begin_time"==k_v[0]){
			f.begin_time.value=k_v[1];
		}
		if("end_time"==k_v[0]){
			f.end_time.value=k_v[1];
		}
		if("cip"==k_v[0]){
			f.operIP.value=k_v[1];
		}
		if("objname"==k_v[0]){
			f.objName.value=k_v[1];
		}
		if("opertype"==k_v[0]){
			f.operType.value=k_v[1];
		}
		if("result"==k_v[0]){
			f.operRet.value=k_v[1];
		}
	}
	searchdatechange(f.date_type.value);
	hidden("d_list");
	disp("d_search");
}
// 根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		
		var k_v=k_vs[i].split(":=");
		
		if("operuserid"==k_v[0]){	
			f.operuserid=k_v[1];
		}
		if("date_type"==k_v[0]){
			f.date_type=k_v[1];
		}
		if("begin_time"==k_v[0]){
			f.begin_time=k_v[1];
		}
		if("end_time"==k_v[0]){
			f.end_time=k_v[1];
		}
		if("cip"==k_v[0]){
			f.cip=k_v[1];
		}
		if("opertype"==k_v[0]){
			f.opertype=k_v[1];
		}
		if("objname"==k_v[0]){
			f.objname=k_v[1];
		}
		if("result"==k_v[0]){
			f.result=k_v[1];
		}
	}

	return f;
}

function openDeptList() {
	 var b = window.showModalDialog("/Seal/depttree/dept_tree.jsp?req=dept_temp&&user_no=${current_user.user_id }",form1);
		 }


 function addIp(){
    var f=document.form1;
	var str=newModalDialog('/Seal/general/hotel/log/addIp.jsp',300,100,f);
	 
}

