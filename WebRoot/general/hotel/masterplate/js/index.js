var isLoad = false;

var oprType="add";
var obj=new QerTable("tb_list");
obj.tb_class="TableList";
obj.th_class="TableHeader";

var thisUrl=baseUrl(3)+"Seal/general/hotel/masterplate/index.jsp";
function load(){
	//防止重复提交
	if(location==baseUrl(4)+"eform/newModel.do"){
		location=baseUrl(4)+"general/hotel/masterplate/index.jsp";
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	hidden("overlay");
	disp("d_list");
	tb_create();
	getDatas();
}

function returnNew(){
	HideDialog('setFrm');
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
	head.push('选');
	head.push("序号");
	head.push('模板名称');
	head.push("创建人");
	head.push('所属部门');
	head.push('创建时间');
	head.push('修改时间');
	head.push('用途');
	head.push('是否套打');
	head.push('授权角色');
	head.push('模板状态');
	head.push('查看');
	head.push('操作');
	obj.SetHead(head);
}

var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
function getDatas(){
//	var dept_no=$("dept_no").value;
	var dept_no = "";
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showObjBySch(page,pageSize,"0",dept_no,user_no,null,"0",cdt_f,cb_data);
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
	var inp='<input type="checkbox" name="model_select" value="'+d.model_id+'" onClick="check_one(self);">';
	//alert(inp);
	row.push(inp);
	row.push(d.model_id);
	row.push(d.model_name);
	row.push(d.create_user);
	row.push(d.dept_name);
	//alert(d.create_user);
	row.push(d.create_time);  
		//alert(d.create_time); 
	row.push(d.modify_time);
	row.push(toprintoreditStr(d.printoredit));
	row.push(tomultipartStr(d.multipart));
	row.push(toRoleStr(d.model_id));
	row.push("待审批");
	row.push(strOpr(d,d.model_id,d.model_name,d.content_data));
	row.push(toOprStr(d));
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
		alert("此模板没有绑定角色");
	}else{
		var strs = role_str.split("???");
		alert(strs);
	}
}

function strOpr(d,id,name,text){
	var str="";
	str+="&nbsp;&nbsp;<a href='#' onclick=\"showTpl0('";
	str+=id+"','"+name+"');\" >查看</a>";
	return str;
}

function toOprStr(d){
	var str="";
		str+="<input type='button' onclick=\"approve1('";
		str+=d.model_id;
		str+="','";
		str+=d.model_name;
		str+="','1');flag=true;\" class='SmallButton'  value='同意' />";
		str+="<input type='button' onclick=\"approve1('";
		str+=d.model_id;
		str+="','";
		str+=d.model_name;
		str+="','2');flag=true;\" class='SmallButton'  value='拒绝' />";
	
	return str;
}
function approve1(ID,name,flag)
{   
	if(flag==1){
	  document.getElementById("leixing").value="批准模板";
	  var msg="确认要批准模板申请吗？请填写批准意见：";
	}  
	if(flag==2)
	{
	  document.getElementById("leixing").value="退回修改模板";
	  var msg="确认要退回模板申请吗？请填写退回意见：";
	}
	document.getElementById("model_id").value=ID;
	document.getElementById("this_name").value=name;
	 $("confirm").innerHTML="<font color=red>"+msg+"</font>";
    $("approve_status").value=flag;
    ShowDialog('approve'); 
}
function showTpl0(id,name,text){
	var url="/Seal/general/hotel/masterplate/show.jsp";
	url+="?id="+id;
	var h=600;
	var w=800;
	newModalDialog(url,w,h);
}
function doButton(){
	  if($("context").innerText==""){
			alert("请填写审批意见！");
			return  false;
	  }else{
		var approve_text=document.getElementById("context").value;
	     if(approve_text.length > 20){
	        alert("输入的理由长度不能超过20个字符!");
	        return false;
	     }else{
	        approve_text=approve_text.replace(/\'/g,"‘");
	        document.getElementById("approver_reason").value=approve_text;
	     }
	       var lx=document.getElementById("leixing").value;
		   var this_name=document.getElementById("this_name").value;
		   var id=document.getElementById("model_id").value;
		   var status=document.getElementById("approve_status").value;;
	   }
	  modelFileSrv.approveModel(id,approve_text,user_no,status);
	  location=thisUrl+"?page="+page;
}
	
function callback(temp_name){
	   var lx=document.getElementById("leixing").value;
	   var this_name=document.getElementById("this_name").value;
	   LogSys.logAdd(user_no,user_name,user_ip,"广告审批",lx+temp_name);//logSys.js
	   document.getElementById("form2").action="/Seal/adApps.do";
	   document.getElementById("form2").submit();
	}


function check_one(el)
{
 if(!el.checked)
    document.all("allbox").checked=false;
}

function check_all()
{
for (i=0;i<document.getElementsByName("model_select").length;i++)
{
 if(document.all("allbox").checked)
    document.getElementsByName("model_select")[i].checked=true;
 else
    document.getElementsByName("model_select")[i].checked=false;
}

if(i==0)
{
 if(document.all("allbox").checked)
	 document.getElementsByName("model_select")[i].checked=true;
 else
	 document.getElementsByName("model_select")[i].checked=false;
}
}

function get_checked()
{
checked_str="";
for(i=0;i<document.getElementsByName("model_select").length;i++)
{
    el=document.getElementsByName("model_select")[i];
    if(el.checked)
    {  val=el.value;
       checked_str+=val + ",";
    }
}

if(i==0)
{
    el=document.getElementsByName("model_select")[i];
    if(el.checked)
    {  val=el.value;
       checked_str+=val + ",";
    }
}
return checked_str;
}

function approve2(ID,flag,range)
{   
 //  alert(flag);
	if(flag==1){
		 document.getElementById("leixing").value="批准模板";
	  var msg="确认要同意模板申请吗？请填写批准意见：";
	}  
	if(flag==2)
	{
		 document.getElementById("leixing").value="退回修改模板";
	  var msg="确认要拒绝模板申请吗？请填写退回意见：";
	}
	$("confirm").innerHTML="<font color=red>"+msg+"</font>";
	if(range=="ALL") {
		if(get_checked()==""){
			alert("请先选择您要操作的模板！");
			return;
		   }
		document.getElementById("model_id").value = get_checked();
	}
	else{
	  //$("ID").value=ID+",";
		document.getElementById("model_id").value=get_checked();
	 }
	document.getElementById("this_name").value= get_checked();
	document.getElementById("approve_status").value=flag;
	ShowDialog('approve'); 
}  

function searchModel(){
	var model_name=document.getElementById("model_name").value;
	var dept_no=document.getElementById("sdept_name").value;
	modelFileSrv.showObjBySch(page,pageSize,"0",dept_no,user_no,model_name,0,cdt_f,cb_data);
	document.getElementById("model_name").value="";
	document.getElementById("sdept_name").value="";
}
