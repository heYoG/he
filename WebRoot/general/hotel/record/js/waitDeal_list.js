var obj=new QerTable("tb_lists");
var ob = new QerTable("xq_list");
var page=1;
var pageSize=10;
var user_no = "";
var role_name = "";
var cdt_s="";
var cdt_f={};
var reg="";//判断
var thisUrl=baseUrl(3)+"Seal/general/hotel/record/waitDeal_list.jsp";

function list_load(d){

	user_no = d;
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	cdt_f.user_no = user_no;
	cdt_f.deptId = dept_no;
	if(user_no=="yiji"){
		cdt_f.role_name="yiji";
		role_name = "yiji";
	}else if(user_no=="erji"){
		cdt_f.role_name="erji";
		role_name = "erji";
	}
	showData();
	show_sch();
}


//填充数据
function showData(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelDic.getPageShowHotelDics(tb_create);
	getDatas();
}

function tb_create(hotelDics){
	$("div_table").innerHTML=obj.HtmlCode();
	
	obj.row_has_chk=false;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
//	head.push("单据编号");
//	head.push('全选');
	head.push('单据名');
	head.push('用户名称');
	head.push('单据类型');
	head.push('单据状态');
	head.push('单据创建时间');
	//head.push('单据号');
	head.push('IP');
//	head.push('房间号');
//	head.push('客户姓名1');
//	head.push("入住时间");
//	head.push('退房时间');
//	head.push('金额');
	for(var i=0;i<hotelDics.length;i=i+1){
		head.push(hotelDics[i].cshowname);
	}
	head.push('属性详情');
	head.push('操作');
	obj.SetHead(head);
}


function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelRecord.showWaitDealRecordBySch(page,pageSize,cdt_f,cb_data);
}

function cb_data(ps){
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].cid,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&cdt="+cdt_s;
}

//日历控件
function GetDate(nText) {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
		if (nText == 1) {
			document.forms[0].insTime.value = reVal;
		} else if (nText == 2) {
				document.forms[0].ineTime.value = reVal;
			}else if(nText == 3){
				document.forms[0].start_time.value=reVal;
			}else if(nText == 4){
				document.forms[0].end_time.value=reVal;
			}
	}
}

	
	 
function toArray(d){
	var row=new Array();
//	row.push(d.cid);
//	row.push("");
	var fileName = toRocordStr(d.cid,d.cfilefilename);
	row.push(fileName);
//	row.push(d.createuserid);
	row.push(d.createUserName);
//	var type = getmodelType(d);
//	row.push(type);
	row.push(d.mtplname);
	var status = checkStatus(d);
	row.push(status);
	row.push(d.ccreatetime.Format("yyyy-MM-dd hh:mm:ss"));
//	row.push(d.cid);
	row.push(d.cip);
	var shuxinglist = d.affiliateList;
	for(var i=0;i<shuxinglist.length;i=i+1){
		if(shuxinglist[i].value==null){
			row.push("");
		}else{
			row.push(shuxinglist[i].value);
		}
	}
//	if(d.roomId==null){
//		row.push("");
//	}else{
//		row.push(d.roomId);
//	}
//	if(d.guestname1==null){
//		row.push("");
//	}else{
//		row.push(d.guestname1);
//	}
//	if(d.indate==null){
//		row.push("");
//	}else{
//		row.push(d.indate);
//	}
//	if(d.outdate==null){
//		row.push("");
//	}else{
//		row.push(d.outdate);
//	}
//	if(d.totalmoney==null){
//		row.push("");
//	}else{
//		row.push(d.totalmoney);
//	}
	var meg = toMeg(d);
	row.push(meg);
	
	var oprStr=toOprStr(d);
	row.push(oprStr);
	
	return row;
}

//单据类型
function getmodelType(d){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	var po=modelFileSrv.getModelFile(d.mtplid);
	if(po==null){
		return "";
	}
	var type = po.model_name;
	return type;
}

//查看单据详情
function toRocordStr(id,name){
	var str="";
	str+="<a href='#' onclick=\"showRecord('";
	str+=id+"','"+name+"');\" >"+name+"</a>";
	return str;
}

function showRecord(id,name){
//	alert();
	var url="/Seal/general/hotel/record/show.jsp";
	url+="?no="+name;
	var h=600;
	var w=900;
	newModalDialog(url,w,h);
}

//校正单据状态
function checkStatus(d){
	var str = "";
	if(d.cstatus=="0"){
		str="作废";
	}
	if(d.cstatus=="1"){
		str="正常";
	}
	return str;
}


function toOprStr(d){
	var str="";
	if(d.cstatus==0){
	}else{
		str+="<input type='button' onclick=\"dealrecord('";
		str+=d.cid+"','"+d.cfilefilename+"','"+d.mtplid;
		str+="');flag=true;\" class='SmallButton' value='办理' />";
	}
	return str;
}

function toMeg(d){
	var str = "";
	str+="<input type='button'  onclick=\"showR('";
	str+=d.cid;
	str+="');flag=true;\" class='SmallButton'  value='详情' />";
	return str;
}

//办理
function dealrecord(no,name,mptID){
	var url="/Seal/general/hotel/record/deal.jsp";
	url+="?no="+no+"&name="+name+"&mptID="+mptID+"&roleName="+role_name;
	var h=600;
	var w=900;
	newModalDialog(url,w,h);
	window.location.reload();
}



function chkSlct(){
	var sel=obj.Selected();
	if(sel==""){
		alert("请选择需要操作的对象！");
		return false;
	}
	return sel.toString();
}



//返回
function ret(){
	hidden("d_search");
	disp("d_list");
}


/**
 * 生出图表
 * @return
 */

//填充数据
function showDatas(no){

	xiangqingtb_create();
	getRecordDatas(no);
}



function xiangqingtb_create(){
	
	$("xiangqing_table").innerHTML=ob.HtmlCode();
	ob.row_color1="white";
	ob.select_color="#739BED";
	var head=new Array();
	
	head.push('属性名');
	head.push('属性值');
	ob.SetHead(head);
}

function getRecordDatas(no){
	
	
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	
	HotelRecord.getRecordAffiliatePOByRid(no,cb_datas);

}

function cb_datas(ps){

	var list = new Array();
	list = ps;
	var str="";
	for(var i=0;i<list.length;i=i+1){
		
		var row=toArrays(list[i]);
		
		ob.AddRow(row,i,i+1);
		
	}
	
}

function toArrays(d){
	var row=new Array();

	row.push(d.cname);
	row.push(d.cvalue);

	return row;
}

//显示详细属性
function showR(no,sys){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	showDatas(no);
	ShowDialog("detail",30);
}

function cb_objs(obj){
	var f=$("f_edit");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	f.reset();

	check_type(f.id);//fromZY.js
}




//搜索
function searchRecord(){	
	var f=$("f_sch");
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?reg="+reg+"&page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	
	var b_name=f.createuserid.value;
	if(!checkStr(b_name,"上传用户")){
		return false;
	}
	s=""==s?s+"createuserid:="+b_name:s+"!createuserid:="+b_name;
	
	var b_mtplid=f.mtplid.value;
	if(!checkStr(b_mtplid,"单据类型")){
		return false;
	}
	s=""==s?s+"mtplid:="+b_mtplid:s+"!mtplid:="+b_mtplid;
	
//	var b_roomid = f.roomId.value;
//	if(!checkStr(b_roomid,"房间号")){
//		return false;
//	}
//	s=""==s?s+"roomId:="+b_roomid:s+"!roomId:="+b_roomid;
//	
//	var b_insTime = f.insTime.value;
//	if(!checkStr(b_insTime,"入住开始时间")){
//		return false;
//	}
//	s=""==s?s+"insTime:="+b_insTime:s+"!insTime:="+b_insTime;
//	
//	var b_ineTime = f.ineTime.value;
//	if(!checkStr(b_ineTime,"入住结束时间")){
//		return false;
//	}
//	s=""==s?s+"ineTime:="+b_ineTime:s+"!ineTime:="+b_ineTime;
//	
//	var b_outsTime = f.outsTime.value;
//	if(!checkStr(b_outsTime,"离店开始时间")){
//		return false;
//	}
//	s=""==s?s+"outsTime:="+b_outsTime:s+"!outsTime:="+b_outsTime;
//	
//	var b_outeTime = f.outeTime.value;
//	if(!checkStr(b_outeTime,"离店结束时间")){
//		return false;
//	}
//	s=""==s?s+"outeTime:="+b_outeTime:s+"!outeTime:="+b_outeTime;
	
	var b_cname = f.cname.value;
	if(!checkStr(b_cname,"属性名")){
		return false;
	}
	s=""==s?s+"cname:="+b_cname:s+"!cname:="+b_cname;
	
	var b_cvalue = f.cvalue.value;
	if(!checkStr(b_cvalue,"属性值")){
		return false;
	}
	s=""==s?s+"cvalue:="+b_cvalue:s+"!cvalue:="+b_cvalue;

	var b_status=f.cstatus.value;
	if(!checkStr(b_status,"单据状态")){
		return false;
	}
	s=""==s?s+"cstatus:="+b_status:s+"!cstatus:="+b_status;
	
//	var b_cardNo = f.cardNo.value;
//	if(!checkStr(b_cardNo,"证件号码")){
//		return false;
//	}
//	s=""==s?s+"cardNo:="+b_cardNo:s+"!cardNo:="+b_cardNo;
	
	var b_cusName = f.cusName.value;
	if(!checkStr(b_cusName,"客户姓名")){
		return false;
	}
	s=""==s?s+"cusName1:="+b_cusName:s+"!cusName1:="+b_cusName;
	
	var b_cardNo = f.cardNo.value;
	if(!checkStr(b_cardNo,"身份证号")){
		return false;
	}
	s=""==s?s+"cardNo:="+b_cardNo:s+"!cardNo:="+b_cardNo;
	
//	var b_deptId = f.dept_no.value;
//	if(!checkStr(b_deptId,"部门名称")){
//		return false;
//	}
//	s=""==s?s+"deptId:="+b_deptId:s+"!deptId:="+b_deptId;
	
	return s;
}



//高级搜索按钮
function show_sch(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showModelFiles(modelfile_back);
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("cusName1"==k_v[0]){
			f.cusName.value=k_v[1];
		}
		if("cardNo"==k_v[0]){
			f.cardNo.value=k_v[1];
		}
		if("createuserid"==k_v[0]){
			f.createuserid.value=k_v[1];
		}
		if("mtplid"==k_v[0]){
			f.mtplid.value=k_v[1];
		}
		if("cstatus"==k_v[0]){
			f.cstatus.value=k_v[1];
		}
		if("cname"==k_v[0]){
			f.cname.value=k_v[1];
		}
		if("cvalue"==k_v[0]){
			f.cvalue.value=k_v[1];
		}
	}
//	hidden("d_list");
//	disp("d_search");
}

function modelfile_back(d){
	var selcert_no=document.getElementById("mtplid");
	selcert_no.options.length = 0;
	selcert_no.options.add(new Option("全部",""));
	for(var i=0;i<d.length;i=i+1){
		 var varItem = new Option(d[i].model_name,d[i].model_id);
		 selcert_no.options.add(varItem);   
	}
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("createuserid"==k_v[0]){	
			f.createuserid=k_v[1];
		}
		if("mtplid"==k_v[0]){
			f.mtplid=k_v[1];
		}
		if("cstatus"==k_v[0]){
			f.cstatus=k_v[1];
		}
//		if("roomId"==k_v[0]){
//			f.roomId=k_v[1];
//		}
//		if("insTime"==k_v[0]){
//			f.insTime=k_v[1];
//		}
//		if("ineTime"==k_v[0]){
//			f.ineTime=k_v[1];
//		}
//		if("outsTime"==k_v[0]){
//			f.outsTime=k_v[1];
//		}
//		if("outeTime"==k_v[0]){
//			f.outeTime=k_v[1];
//		}
		if("cname"==k_v[0]){
			f.cname=k_v[1];
		}
		if("cvalue"==k_v[0]){
			f.cvalue=k_v[1];
		}
//		if("cardNo"==k_v[0]){
//			f.cardNo=k_v[1];
//		}
		if("cusName1"==k_v[0]){
			f.cusName1=k_v[1];
		}
		if("cardNo"==k_v[0]){
			f.cardNo=k_v[1];
		}
//		if("deptId"==k_v[0]){
//			f.deptId=k_v[1];
//		}
	}

	return f;
}




function pl_DownLoad(){
	var sel=chkSlct();
	if(sel&&confirm("确定要下载吗？")){
		
		$("f_sch").action="/Seal/recordDownload.do?sel="+sel+"&url="+baseUrl(4);
		
		$("f_sch").submit();
	}
}





