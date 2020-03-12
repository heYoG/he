var obj=new QerTable("tb_list");
var ob = new QerTable("xq_list");
var page=1;
var pageSize=10;
var user_no = "";
var cdt_s="";
var cdt_f={};
var reg="";//判断
var thisUrl=baseUrl(3)+"Seal/general/hotel/scanner/scanner_list.jsp";

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
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	//HotelDic.getPageShowHotelDics(tb_create);
	tb_create();
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
	head.push('单据创建时间');
	head.push('IP');
//	head.push('房间号');
//	head.push('客户姓名1');
//	head.push("入住时间");
//	head.push('退房时间');
//	head.push('金额');
//	for(var i=0;i<hotelDics.length;i=i+1){
//		head.push(hotelDics[i].cshowname);
//	}
//	head.push('属性详情');
//	head.push('操作');
	obj.SetHead(head);
}
function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	ScannerHotelRecord.showScannerRecordBySch(page,pageSize,cdt_f,cb_data);
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
	var fileName = toRocordStr(d.id,d.filename,d.filepath);
	row.push(fileName);
//	var type = getmymodelType(d);
//	row.push(type);
	row.push(d.createtime.Format("yyyy-MM-dd hh:mm:ss"));
	row.push(d.ip);
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
	
	//var oprStr=toOprStr(d);
	//row.push(oprStr);
	
	return row;
}

//查看单据详情
function toRocordStr(id,name,filepath){
	var str="";
	str+="<a href='#' onclick=\"showRecord('";
	str+=id+"','"+filepath+"');\" >"+name+"</a>";
	return str;
}

function showRecord(id,name){

	var url="/Seal/general/hotel/scanner/showscannerfile.jsp";
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
	var keyName = f.keyName.value;
	var c=fToCdt(f);
	if(c){
		location=thisUrl+"?reg="+reg+"&page=1&cdt="+c+"&userId="+user_no;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	var s="";
	
	var keyName=f.keyName.value;
	if(!checkStr(keyName,"关键字")){
		return false;
	}
	s=""==s?s+"context:="+keyName:s+"!context:="+keyName;
	
	return s;
}

//高级搜索按钮
function show_sch(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showModelFiles(modelfile_back);
}

function modelfile_back(d){
	var f=$("f_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("context"==k_v[0]){
			f.keyName.value=k_v[1];
		}
	}
}

//根据字符串得到搜索条件
function cdtToF(cdt){
	var f={};
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		
		var k_v=k_vs[i].split(":=");
		if("context"==k_v[0]){	
			f.scannerContext=k_v[1];
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

