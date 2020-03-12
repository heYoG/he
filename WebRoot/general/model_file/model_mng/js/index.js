var isLoad = false;

var oprType="add";
var obj=new QerTable("tb_list");
obj.tb_class="TableList";
obj.th_class="TableHeader";

var thisUrl=baseUrl(3)+"Seal/general/model_file/model_mng/index.jsp";
function load(){
	//防止重复提交
	if(location==baseUrl(4)+"eform/newModel.do"){
		location=baseUrl(4)+"general/model_file/model_mng/index.jsp";
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
	alert(l);
}

//选择模板文件(doc文件)
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

//上传现有模板
function loadAip(){
	if($("tname").value=="")
	{
		alert("请输入模板名称！");
		return ;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.isExist($("tname").value,cb_exist);
	if(exist){
		alert("已存在的模板名称，请重输！");
		$("tname").select();
		return false;
	}
    var ocxobj = $("HWPostil1");
    var content = ocxobj.GetCurrFileBase64();
    ocxobj.InDesignMode=1;
	ocxobj.CloseDoc(0);
	$("overlay").style.display = "";
	$("edit_tname").value=$("tname").value;
	var now = new Date().Format("yyyy-MM-dd hh:mm");
	$("edit_ctime").innerHTML=now;
	$("edit_mtime").innerHTML=now;
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

//新建模板
function convert()
{
	if($("tname").value=="")
	{
		alert("请输入模板名称！");
		return ;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.isExist($("tname").value,cb_exist);
	if(exist){
		alert("已存在的模板名称，请重输！");
		$("tname").select();
		return false;
	}
    var ocxobj = $("HWPostil1");
    var content = ocxobj.GetCurrFileBase64();
    ocxobj.InDesignMode=1;
	ocxobj.CloseDoc(0);
	$("overlay").style.display = "";
	$("edit_tname").value=$("tname").value;
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
	head.push('模板名称');
	head.push("创建人");
	head.push('创建时间');
	head.push('修改时间');
	head.push('模板状态');
	head.push('操作');
	obj.SetHead(head);
}

var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showObjBySch(page,pageSize,cdt_f,cb_data);
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
	row.push(d.createTime);
	row.push(d.modifyTime);
	 if(d.model_state=="1"){
		row.push("可用");
	}else if(d.model_state=="2"){
		row.push("注销");
	}else{
		row.push("未知状态");
	}
	var opr=strOpr(d,d.model_id,d.model_name,d.content_data);
	row.push(opr);
	return row;
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page;
}

function strOpr(d,id,name,text){
	var str="";
	str+="<a href='#' onclick=\"print('";
	str+=id+"','"+name+"','"+text+"');\" >打印</a>";
	str+="&nbsp;&nbsp;<a href='#' onclick=\"showTpl0('";
	str+=id+"','"+name+"');\" >查看</a>";
	str+="&nbsp;&nbsp;<a href='#' onclick=\"showTpl('";
	str+=id+"','"+name+"','"+text+"');\" >修改</a>";
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
//	alert(str);
	return str;
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
	var url="/Seal/general/model_file/model_mng/show.jsp";
	url+="?no="+name;
	var h=600;
	var w=800;
	newModalDialog(url,w,h);
}

function showTpl(id,name,text){
	hidden("d_list");
	hidden("d_new");
//	hidden("overlay");
	disp("d_aip"); 
	$("td_aip").width=$("t_aip").width;
	$("td_aip").height=600;
	$("edit_tname").value=$("old_name").value=name;
	var now = new Date().Format("yyyy-MM-dd hh:mm");
	$("edit_ctime").innerHTML=now;
	$("edit_mtime").innerHTML=now;
//	aip_init();
	var aipObj=$("HWPostil1");
	var l=aipObj.Login("sys_admin", 5, 65535, "", "");
//	alert(l);
	aipObj.InDesignMode=1;
//	alert(aipObj.InDesignMode);
//	aipObj.CloseDoc(0);
//	aipObj.LoadFileEx(baseUrl(4)+"upload/init.doc","tpl",0,0);
	var c=aipObj.CloseDoc(0);
//	alert(c);
	aipObj.LoadFileBase64(text);
//	aipObj.LoadFileEx("http://localhost:7003/Seal/upload/test.aip","",0,0);
	var iframe=$("frm_field");
	oprType="upd";
	$("upd_id").value=id;
//	iframe.src="set_filed.jsp?tpl_id="+id;
}

function delTpl(id,name){
	if(confirm("确定要删除该模板吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		modelFileSrv.delModelFile(id);
		LogSys.logAdd(user_no,user_name,user_ip,"模板管理","删除模板:"+name+"成功");
		//logDel("模板",id,name);//logOper.js
		load();
	}
}
function zhuxiaoTpl(id,name){
	if(confirm("确定要注销模板吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		modelFileSrv.zhuxiaoModelFile(id);
		LogSys.logAdd(user_no,user_name,user_ip,"模板管理","注销模板:"+name+"成功");
		load();
	}
}
function jihuoTpl(id,name){
	if(confirm("确定要激活模板吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		modelFileSrv.jihuoModelFile(id);
		LogSys.logAdd(user_no,user_name,user_ip,"模板管理","激活模板:"+name+"成功");
		load();
	}
}
function saveFile(){
	var obj=$("HWPostil1");
	var name=$("edit_tname").value;
	var user=$("edit_creater").innerHTML;
	var text=obj.GetCurrFileBase64();//得到模板的Base64值
	obj.RunCommand(3, 20, 0);//执行全选节点操作
	var field_data=obj.GetValue("PASTE_NODES_TODATA");//获取剪切板节点数据
	if(field_data==""){
		alert("当前AIP控件不是最新版本，请更新!");
		return false;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	if(oprType=="add"){
		modelFileSrv.addModelFile({model_name:name,create_user:user,content_data:text,field_data:field_data});
		LogSys.logAdd(user_no,user_name,user_ip,"模板管理","添加模板:"+name+"成功");
	}else if(oprType=="upd"){
		var id=$("upd_id").value;
		modelFileSrv.updModelFile({model_id:id,model_name:name,create_user:user,content_data:text,field_data:field_data});
		LogSys.logAdd(user_no,user_name,user_ip,"模板管理","修改模板:"+name+"成功");
	}
	obj.HttpInit();
	obj.HttpAddPostCurrFile("FileContent");
	obj.HttpAddPostString("name",name);
	obj.HttpPost(baseUrl(4)+"general/model_file/model_mng/saveTo.jsp");
	load();
}
    
