var obj=new QerTable("tb_list");
var ob = new QerTable("xq_list");
var page=1;
var pageSize=15;
var user_no = "";
var cdt_s="";
var cdt_f={};
var reg="";//判断
var bs = "";
var thisUrl=baseUrl(3)+"Seal/general/hotel/record/myrecord_list.jsp";

function list_load(){
	user_no = user_id;
	if(user_no==null||user_no==""){
		user_no=getUrlParam("userId")==null?"":getUrlParam("userId");
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
	cdt_f.createuserid=user_no;
	showData();
	show_sch();
}

//填充数据
function showData(){
	getDatas();
}

function tb_create(hotelDics){
	$("div_table").innerHTML=obj.HtmlCode();
	obj.row_has_chk=false;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
//	head.push('全选');
	head.push('单据名');
	head.push('单据类型');
	head.push('单据状态');
	head.push('单据创建时间');
//	head.push('IP');
//	head.push('房间号');
//	head.push('客户姓名1');
//	head.push("入住时间");
//	head.push('退房时间');
//	head.push('金额');
	for(var i=0;i<hotelDics.length;i=i+1){
		head.push(hotelDics[i].cshowname);
	}
//	head.push('属性详情');
	head.push('操作');
	obj.SetHead(head);
}
function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelRecord.showRecordBySch(page,pageSize,cdt_f,cb_data);
}

function cb_data(ps){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelDic.getPageShowHotelDics(tb_create);
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].cid,i+1);
		}
	}
}

function getdateOrderRooId(){
	var map = new Map();
	var runNum = 90000000;
	var roomId = 0;
	var name = "";
	var arrayObj = new Array();
	for(var i=0;i<bs.length;i++){
		if(bs[i].roomId==null||bs[i].roomId==""){
			runNum++;
			roomId = runNum;
		}else{
			roomId = parseInt(bs[i].roomId);
		}
		arrayObj[i]=roomId;
		map.put(roomId, bs[i]);
	}
	arrayObj.sort();
	for(var i=0;i<arrayObj.length;i++){
		name += map.get(arrayObj[i]).cdata+",";
	}
	showRecordBat("",name);
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&userId="+user_no+"&cdt="+cdt_s;
}

//日历控件
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

//单据类型
function getmymodelType(d){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	var po=modelFileSrv.getModelFile(d.mtplid);
	if(po==null){
		return "";
	}
	var type = po.model_name;
	return type;
}
function toArray(d){
	var row=new Array();
//	row.push('');
	var fileName = toRocordStr(d.cid,d.cfilefilename,d.cdata);
	row.push(fileName);
//	var type = getmymodelType(d);
//	row.push(type);
	row.push(d.mtplname);
	var status = checkStatus(d);
	row.push(status);
	row.push(d.ccreatetime.Format("yyyy-MM-dd hh:mm:ss"));
//	row.push(d.cip);
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

//	var meg = toMeg(d);
//	row.push(meg);
	
	var oprStr=toOprStr(d);
	row.push(oprStr);
	return row;
}

//查看单据详情
function toRocordStr(id,name,cdata){
	var str="";
	str+="<a href='javascript:;' onclick=\"showRecord('";
	str+=id+"','"+cdata+"');\" >"+name+"</a>";
	return str;
}

function showRecord(id,name){
	var url="/Seal/general/hotel/record/show.jsp";
	url+="?no="+name;
	var h=600;
	var w=900;
	newModalDialog(url,w,h);
}

function showRecordBat(id,name){
	var url="/Seal/general/hotel/record/showbat.jsp";
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
		
		str+="<input type='button'  onclick=\"changeStatusIsOn('";
		str+=d.cid+"','"+d.cfilefilename;
		str+="');flag=true;\" class='SmallButton' value='激活' />";
	}else{
		
		str+="<input type='button' onclick=\"changeStatusIsOff('";
		str+=d.cid+"','"+d.cfilefilename;
		str+="');flag=true;\" class='SmallButton' value='作废' />";
	}
	return str;
}

function toMeg(d){
	var str = "";
	str+="<input type='button'  onclick=\"showR('";
	str+=d.cid;
	str+="');flag=true;\" class='SmallButton'  value='详情' />";
	str+="&nbsp;&nbsp;";
	return str;
}


function searchdatechange(vvv){
	if(vvv=="7"){
		document.getElementById("datetable").style.display="block";
	}else{
		document.getElementById("datetable").style.display="none";
	}
}

function changeStatusIsOff(no,name){
	if(confirm("确定要作废吗？")){
		HotelRecord.updateRecordStatusIsOff(no);
		LogSys.logAdd(user_no,user_name,user_ip,"单据管理","作废单据:"+name+"成功");
		location = thisUrl+"?page="+page+"&userId="+user_no+"&cdt="+cdt_s;
	}
}

function changeStatusIsOn(no,name){
	if(confirm("确定要激活吗？")){
		HotelRecord.updateRecordStatusIsOn(no);
		LogSys.logAdd(user_no,user_name,user_ip,"单据管理","激活单据:"+name+"成功");
		location = thisUrl+"?page="+page+"&userId="+user_no+"&cdt="+cdt_s;
	}
}

//返回
function ret(){
	
	hidden("d_mysearch");
	disp("d_mylist");
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
			if(b_begin_time<"2015-09-01"){
				alert("请选择2015年9月以后的日期!");
				return false;
			}
			 var myDate = new Date(); 
			 var nowyear = myDate.getFullYear();
			 var nowmonth = myDate.getMonth()+1;
			 var nowday = myDate.getDate();
			 var nowStr = nowyear + "-" + nowmonth + "-" + nowday;
			 if(b_end_time>nowStr){
			 	alert("请选择当前日期("+nowStr+")之前的日期!");
				return false;
			 }
			var nian1 = b_begin_time.substring(0,4);
			var nian2 = b_end_time.substring(0,4);
			var yue1 = b_begin_time.substring(5,7);
			var yue2 = b_end_time.substring(5,7);
			var niancha = nian2-nian1;//得出年份差距
			yue2 = parseInt(yue2) + parseInt(niancha)*12;
			var yuecha = parseInt(yue2,10)-parseInt(yue1,10);
			if(yuecha>=6){
				if(!confirm("查询的日期跨度超过了半年，查询时间较长，确定要查询吗？")){
					return false;
				}
			}
		}
	}
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?reg="+reg+"&page=1&cdt="+c+"&userId="+user_no;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	
	var b_serialNo = f.serialNo.value;
	if(!checkStr(b_serialNo,"交易流水")){
		return false;
	}
	s=""==s?s+"serialNo:="+b_serialNo:s+"!serialNo:="+b_serialNo;
	
//	var b_name=f.createuserid.value;
//	if(!checkStr(b_name,"上传用户")){
//		return false;
//	}
//	s=""==s?s+"createuserid:="+b_name:s+"!createuserid:="+b_name;
	
//	var b_mtplid=f.mtplid.value;
//	if(!checkStr(b_mtplid,"单据类型")){
//		return false;
//	}
//	s=""==s?s+"mtplid:="+b_mtplid:s+"!mtplid:="+b_mtplid;
	
	var b_status=f.cstatus.value;
	if(!checkStr(b_status,"单据状态")){
		return false;
	}
	s=""==s?s+"cstatus:="+b_status:s+"!cstatus:="+b_status;
	
	var b_businessType = f.businessType.value;
	if(!checkStr(b_businessType,"业务类型")){
		return false;
	}
	s=""==s?s+"businessType:="+b_businessType:s+"!businessType:="+b_businessType;
	
//	var b_cusName = f.cusName.value;
//	if(!checkStr(b_cusName,"客户姓名")){
//		return false;
//	}
//	s=""==s?s+"cusName1:="+b_cusName:s+"!cusName1:="+b_cusName;
	
//	var b_cardNo = f.cardNo.value;
//	if(!checkStr(b_cardNo,"身份证号")){
//		return false;
//	}
//	s=""==s?s+"cardNo:="+b_cardNo:s+"!cardNo:="+b_cardNo;
	
//	var b_shouliNo = f.shouliNo.value;
//	if(!checkStr(b_shouliNo,"受理编号")){
//		return false;
//	}
//	s=""==s?s+"shouliNo:="+b_shouliNo:s+"!shouliNo:="+b_shouliNo;
	
//	var b_phoneNo = f.phoneNo.value;
//	if(!checkStr(b_phoneNo,"手机号码")){
//		return false;
//	}
//	s=""==s?s+"phoneNo:="+b_phoneNo:s+"!phoneNo:="+b_phoneNo;
	
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
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showModelFiles(modelfile_back);
}

function modelfile_back(d){
//	var selcert_no=document.getElementById("mtplid");
//	selcert_no.options.length = 0;
//	selcert_no.options.add(new Option("全部",""));
//	for(var i=0;i<d.length;i=i+1){
//		 var varItem = new Option(d[i].model_name,d[i].model_id);
//		 selcert_no.options.add(varItem);   
//	}
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("tellerNo"==k_v[0]){
			f.tellerNo.value=k_v[1];
		}
		if("serialNo"==k_v[0]){
			f.serialNo.value=k_v[1];
		}
		if("businessType"==k_v[0]){
			f.businessType.value=k_v[1];
		}
		if("keyWord"==k_v[0]){
			f.keyWord.value=k_v[1];
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
		if("createuserid"==k_v[0]){
			f.createuserid.value=k_v[1];
		}
		if("cstatus"==k_v[0]){
			f.cstatus.value=k_v[1];
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
		if("tellerNo"==k_v[0]){
			f.tellerNo=k_v[1];
		}
		if("serialNo"==k_v[0]){
			f.serialNo=k_v[1];
		}
		if("businessType"==k_v[0]){
			f.businessType=k_v[1];
		}
		if("keyWord"==k_v[0]){
			f.keyWord=k_v[1];
		}
		if("cstatus"==k_v[0]){
			f.cstatus=k_v[1];
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
	}

	return f;
}



QerTable.prototype.Sel = function () {
	var res = new Array();
	if (this.row_has_chk) {
		var tb = $(this.id);
		var rs = tb.rows;
		for (var i = rs.length - 1; i > 0; i = i - 1) {
			if ($(this.row_chk_name + rs[i].id).checked) {
				res.push(rs[i].id);
			}
		}
	}
	return res.reverse();
};


function chkSlct(){
	var sel=obj.Sel();
	if(sel==""){
		alert("请选择需要操作的对象！");
		return false;
	}
	return sel.toString();
}

function pl_delDownLoad(){
	var sel=chkSlct();
	if(sel&&confirm("确定要下载吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步	
		HotelDic.plDelDic(sel);
		location=thisUrl+"?page="+page;
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

function pl_DownLoad(){
	var sel=chkSlct();
	if(sel&&confirm("确定要下载吗？")){
		$("f_sch").action="/Seal/recordDownload.do?sel="+sel;
		$("f_sch").submit();
	}
}

function Map(){
	this.container = new Object();
	}


	Map.prototype.put = function(key, value){
	this.container[key] = value;
	}


	Map.prototype.get = function(key){
	return this.container[key];
	}


	Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend') {
	continue;
	}
	keyset[count] = key;
	count++;
	}
	return keyset;
	}


	Map.prototype.size = function() {
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend'){
	continue;
	}
	count++;
	}
	return count;
	}


	Map.prototype.remove = function(key) {
	delete this.container[key];
	}


	Map.prototype.toString = function(){
	var str = "";
	for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
	str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
	}
	return str;
	} 

