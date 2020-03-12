var isLoad = false;

var oprType="add";
var obj=new QerTable("tb_list");
obj.tb_class="TableList";
obj.th_class="TableHeader";

var thisUrl=baseUrl(3)+"Seal/general/hotel/masterplate/newxieyi.jsp";
function load(){
	//防止重复提交
	if(location==baseUrl(4)+"eform/newModel.do"){
		location=baseUrl(4)+"general/hotel/masterplate/newxieyi.jsp";
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	hidden("overlay");
	hidden("d_aip");
	hidden("setFrm");
	disp("d_new");
	disp("d_list");
	$("tname").value="";
	tb_create();
	getDatas();
}

function select_file(){
	var ocxobj = $("HWPostil1");
	ocxobj.InDesignMode=1;
	ocxobj.CloseDoc(0);
	var l=ocxobj.LoadFileEx("","tpl",0,0);

}

//选择协议文件(doc文件)
function sel_file()
{
    var ocxobj = $("HWPostil1");
//    alert(obj);
//    disp("overlay");
	ocxobj.CloseDoc(0);
//    var l=obj.LoadFile("");
//	alert($("tfile").value);
//	var l=obj.LoadFileEx($("tfile").value,"tpl",0,0);
//    alert(l);
//	if(l==1){
//		obj.HttpInit();
//		obj.HttpAddPostCurrFile("FileContent");
//		obj.HttpPost(baseUrl(4)+"general/seal_model/model_file/saveTo.jsp");
//	}else{
//		alert("转化失败！");
//	}
}
var exist=false;

function cb_exist(b){
	exist=b;
}

//上传现有协议
function loadAip(modelToken){
	if($("tname").value=="")
	{
		alert("请输入协议名称！");
		return ;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.isExist($("tname").value,cb_exist);
	if(exist){
		alert("已存在的协议名称，请重输！");
		$("tname").select();
		return false;
	}
    var ocxobj = $("HWPostil1");
    var content = ocxobj.GetCurrFileBase64();
    ocxobj.InDesignMode=1;
	ocxobj.CloseDoc(0);
	$("form1").reset();
	$("overlay").style.display = "";
	$("edit_tname").value=$("tname").value;
	var now = new Date().Format("yyyy-MM-dd hh:mm");
	$("edit_ctime").innerHTML=now;
	$("edit_mtime").innerHTML=now;
	$("modelorxieyi").value=modelToken;
	hidden("d_list");
	hidden("d_new");
	disp("d_aip"); 
	$("td_aip").width=$("t_aip").width;
	$("td_aip").height=600;
	var l=ocxobj.LoadFile($("tfile").value);
	if(l==0){
		returnNew();
		return ;
	}
	oprType="add";
}

//新建协议
function convert(modelToken)
{
	if($("tname").value=="")
	{
		alert("请输入协议名称！");
		return ;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.isExist($("tname").value,cb_exist);
	if(exist){
		alert("已存在的协议名称，请重输！");
		$("tname").select();
		return false;
	}
    var ocxobj = $("HWPostil1");
    var content = ocxobj.GetCurrFileBase64();
    ocxobj.InDesignMode=1;
	ocxobj.CloseDoc(0);
	$("form1").reset();
	$("overlay").style.display = "";
	$("edit_tname").value=$("tname").value;
	$("modelorxieyi").value=modelToken;
	var now = new Date().Format("yyyy-MM-dd hh:mm");
	$("edit_ctime").innerHTML=now;
	$("edit_mtime").innerHTML=now;
	hidden("d_list");
	hidden("d_new");
	disp("d_aip"); 
	$("td_aip").width=$("t_aip").width;
	$("td_aip").height=600;
	var l=ocxobj.LoadFileEx($("tfile").value,"tpl",0,0);
	if(l==0){
		returnNew();
		return ;
	}
	oprType="add";
}

function returnNew(){
	hidden("d_aip");
	disp("d_new");
	disp("d_list");
}

//调用DWR方法填充数据
function loadTplData(){
//	alert();
}
function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
	obj.select_color="#739BED";
	var head=new Array();
	head.push("序号");
	head.push('协议名称');
	head.push("创建人");
	head.push("所属部门");
	head.push('创建时间');
	head.push('修改时间');
	head.push('协议状态');
	head.push('本部门使用状态');
	head.push('操作');
	obj.SetHead(head);
}

var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
function getDatas(){
	var dept_no=$("dept_no").value;
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showObjBySch(page,pageSize,"1",dept_no,cdt_f,cb_data);
}
function cb_data(ps){
	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i],i+1);
			obj.AddRow(row,bs[i].t_id);
		}
	}
}
function toArray(d,i){
	var row=new Array();
	row.push(d.model_id);
	row.push(d.model_name);
	row.push(d.create_user);
	row.push(d.dept_name);
	row.push(d.create_time);  
	row.push(d.modify_time);
	 if(d.model_state=="1"){
		row.push("可用");
	}else if(d.model_state=="2"){
		row.push("注销");
	}else{
		row.push("未知状态");
	}
	row.push(d.feiqi_status);
	var opr=strOpr(d,d.model_id,d.model_name,d.content_data,d.dept_no);
	row.push(opr);
	return row;
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page;
}

function tomultipartStr(multipart){
	var str="";
	if(multipart=='0'){
		str="否";
	}else if(multipart=='1'){
		str="是";
	}else{
		str="未知";
	}
	return str;;
}

function toprintoreditStr(printoredit){
	var str="";
	if(printoredit=='1'){
		str="打印和编辑";
	}else if(printoredit=='2'){
		str="打印";
	}else if(printoredit=='3'){
		str="编辑";
	}else{
		str="未知";
	}
	return str;
}

function toRoleStr(model_id){
	var str="";
	str+="<a href='#' onclick=\"showRoles('";
	str+=model_id+"');\" >查看</a>";
	return str;
}
function showRoles(model_id){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	var role_str = modelFileSrv.getRolesByModelId(model_id);
	if(role_str==null||role_str==""){
		alert("此协议没有绑定角色");
	}else{
		var strs = role_str.split("???");
		alert(strs[1]);
	}
}


function strOpr(d,id,name,text,dept_no){
	var dept_no_user=$("dept_no").value;
	var str="";
	str+="<a href='#' onclick=\"print('";
	str+=id+"','"+name+"','"+text+"');\" >打印</a>";
	str+="&nbsp;&nbsp;<a href='#' onclick=\"showTpl0('";
	str+=id+"','"+name+"');\" >查看</a>";
	str+="&nbsp;&nbsp;<a href='#' onclick=\"peizhimodel('";
	str+=id+"','"+dept_no+"','"+dept_no_user+"');\" >配置模版</a>";
	if(dept_no.indexOf(dept_no_user)==0){
		str+="&nbsp;&nbsp;<a href='#' onclick=\"showTpl('";
		str+=id+"','"+name+"','"+text+"','"+d.printoredit+"','"+d.multipart+"','"+d.isflow+"','"+d.page_no+"','"+d.x_position+"','"+d.y_position+"','"+d.area_width+"','"+d.area_height+"','"+d.area_name+"');\" >修改</a>";
		str+="&nbsp;&nbsp;<a href='#' onclick=\"delTpl('";
		str+=id+"','"+name+"');\" >删除</a>";
		if(d.is_app=="1"){
			str+="&nbsp;&nbsp;<a href='#' onclick=\"delTpl2('";
			str+=id+"','"+name+"');\" >删除</a>";
		}
		if(d.is_app=="2"){
			str+="&nbsp;&nbsp;<a href='#' onclick=\"delTpl('";
			str+=id+"','"+name+"');\" >删除</a>";
		}
		if(d.model_state=="1"){
			if(d.is_app=="1"){
				str+="&nbsp;&nbsp;<a href='#' onclick=\"zhuxiaoTpl2('";
				str+=id+"','"+name+"');\" >注销</a>";
			}else{
				str+="&nbsp;&nbsp;<a href='#' onclick=\"zhuxiaoTpl('";
				str+=id+"','"+name+"');\" >注销</a>"
			}
		}
		if(d.model_state=="2"){
			str+="&nbsp;&nbsp;<a href='#' onclick=\"jihuoTpl('";
			str+=id+"','"+name+"');\" >激活</a>";
		}
	}else{
		str+="&nbsp;&nbsp;<a href='#' onclick=\"feiqi('";
		str+=id+"','"+name+"','"+dept_no_user+"');\" >废弃</a>";
	}
	
//	alert(str);
	return str;
}
function feiqi(id,name,dept_no){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.AddFeiqiXieyi(id,dept_no);
}

function peizhimodel(xieyiid,xieyi_dept_no,user_dept_no){
	b = window.showModalDialog("/Seal/xieyimodelshow.do?xieyi_dept_no="+xieyi_dept_no+"&xieyi_id="+xieyiid+"&user_dept_no="+user_dept_no);
}
function print(id,name,text){
	var ocxobj=$("HWPostil1");
//	alert(ocxobj);
	ocxobj.LoadFileBase64(text);
	ocxobj.PrintDoc(1,1);
//	alert(id);
}

function showTpl0(id,name,text){
//	alert();
	var url="/Seal/general/hotel/masterplate/show.jsp";
	url+="?no="+name;
	var h=600;
	var w=800;
	newModalDialog(url,w,h);
}

function showTpl(id,name,text,printoredit,multipart,isflow,page_no,x_position,y_position,area_width,area_height,area_name){
	hidden("d_list");
	hidden("d_new");
	disp("d_aip"); 
	$("td_aip").width=$("t_aip").width;
	$("td_aip").height=600;
	$("edit_tname").value=$("old_name").value=name;
	$("printoredit").value=printoredit;
	$("multipart").value=multipart;

	$("isflow").value=isflow;
	$("page_no").value=page_no;
	$("x_position").value=x_position;
	$("y_position").value=y_position;
	$("area_width").value=area_width;
	$("area_height").value=area_height;
	$("area_name").value=area_name;
	
	var now = new Date().Format("yyyy-MM-dd hh:mm");
	$("edit_ctime").innerHTML=now;
	$("edit_mtime").innerHTML=now;
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	var role_str = modelFileSrv.getRolesByModelId(id);
	if(role_str==null||role_str==""){
		$("role_nos").value="";
		$("role_names").value="";
	}else{
		var strs = role_str.split("???");
		$("role_nos").value=strs[0];
		$("role_names").value=strs[1];
	}
	var aipObj=$("HWPostil1");
	var l=aipObj.Login("sys_admin", 5, 65535, "", "");
	aipObj.InDesignMode=1;
	var c=aipObj.CloseDoc(0);
	aipObj.LoadFileBase64(text);
	var iframe=$("frm_field");
	oprType="upd";
	$("upd_id").value=id;
}

function delTpl(id,name){
	if(confirm("确定要删除该协议吗？这将同时删除该协议的判定条件等！")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		modelFileSrv.delHotelModelFile(id);
		LogSys.logAdd(user_no,user_name,user_ip,"协议管理","删除协议:"+name+"成功");
		//logDel("协议",id,name);//logOper.js
		load();
	}
}
function zhuxiaoTpl(id,name){
	if(confirm("确定要注销协议吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		modelFileSrv.zhuxiaoModelFile(id);
		LogSys.logAdd(user_no,user_name,user_ip,"协议管理","注销协议:"+name+"成功");
		load();
	}
}
function jihuoTpl(id,name){
	if(confirm("确定要激活协议吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		modelFileSrv.jihuoModelFile(id);
		LogSys.logAdd(user_no,user_name,user_ip,"协议管理","激活协议:"+name+"成功");
		load();
	}
}

function openRoleList(){
	var b='default';
	b = window.showModalDialog("/Seal/roleModeList.do?user_status=1",form1);
	if(b!='default'){
		document.form1.role_names.value = b ;
	}
   }


function saveFile(){
	var isflow=$("isflow").value;
	var page_no=$("page_no").value;
	var x_position=$("x_position").value;
	var y_position=$("y_position").value;
	var area_width=$("area_width").value;
	var area_height=$("area_height").value;
	var area_name=$("area_name").value;
	if(isflow==1){
		if(page_no==""){
			alert("页码不能为空!");
			return false;
		}
		if(isNaN(page_no)){
			alert("页码须为数字!");
			return false;
		}
		if(x_position==""){
			alert("横坐标不能为空!");
			return false;
		}
		if(isNaN(x_position)){
			alert("横坐标须为数字!");
			return false;
		}
		if(y_position==""){
			alert("纵坐标不能为空!");
			return false;
		}
		if(isNaN(y_position)){
			alert("纵坐标须为数字!");
			return false;
		}
		if(area_width==""){
			alert("宽度不能为空!");
			return false;
		}
		if(isNaN(area_width)){
			alert("宽度须为数字!");
			return false;
		}
		if(area_height==""){
			alert("高度不能为空!");
			return false;
		}
		if(isNaN(area_height)){
			alert("高度须为数字!");
			return false;
		}
		if(area_name==""){
			alert("区域名称不能为空!");
			return false;
		}
	}
	if(confirm("确定要提交吗？")){
		var obj=$("HWPostil1");
		var name=$("edit_tname").value;
		var ishotel=$("ishotel").value;
		var role_nos=$("role_nos").value;
		var printoredit=$("printoredit").value;
		var multipart=$("multipart").value;
		var user=$("edit_creater").innerHTML;
		var dept_no=$("dept_no").value;
		var modelorxieyi=$("modelorxieyi").value;
		var approve_status=$("approve_status").value;
		var identitycard=$("identitycard").value;
		var dept_name=$("dept_name").value;
		var text=obj.GetCurrFileBase64();//得到协议的Base64值
		obj.RunCommand(3, 20, 0);//执行全选节点操作
		var field_data=obj.GetValue("PASTE_NODES_TODATA");//获取剪切板节点数据
		if(field_data==null||field_data==""){
			alert("协议中未包含动态区域，不能提交");
			return false;
		}
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		if(oprType=="add"){
			modelFileSrv.addModelFile({model_name:name,create_user:user,content_data:text,field_data:field_data,ishotel:ishotel,role_nos:role_nos,printoredit:printoredit,multipart:multipart,isflow:isflow,page_no:page_no,x_position:x_position,y_position:y_position,area_width:area_width,area_height:area_height,area_name:area_name,dept_no:dept_no,modelorxieyi:modelorxieyi,approve_status:approve_status,identitycard:identitycard,dept_name:dept_name});
			LogSys.logAdd(user_no,user_name,user_ip,"协议管理","添加协议:"+name+"成功");
		}else if(oprType=="upd"){
			var id=$("upd_id").value;
			modelFileSrv.updModelFile({model_id:id,model_name:name,create_user:user,content_data:text,field_data:field_data,ishotel:ishotel,role_nos:role_nos,printoredit:printoredit,multipart:multipart,isflow:isflow,page_no:page_no,x_position:x_position,y_position:y_position,area_width:area_width,area_height:area_height,area_name:area_name,dept_no:dept_no,modelorxieyi:modelorxieyi,approve_status:approve_status,identitycard:identitycard,dept_name:dept_name});
			LogSys.logAdd(user_no,user_name,user_ip,"协议管理","修改协议:"+name+"成功");
		}
		obj.HttpInit();
		obj.HttpAddPostCurrFile("FileContent");
		obj.HttpAddPostString("name",name);
		obj.HttpPost(baseUrl(4)+"general/hotel/masterplate/saveTo.jsp");
		load();
	}
}
    
