var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var userId="";
var thisUrl=baseUrl(3)+"Seal/general/log/log_oper/showUser.jsp";

function load(){
	//if(location==baseUrl(3)+"Seal/newRule.do"){
	//	location=thisUrl;
	//}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	userId = getUrlParam("userId")==null?1:getUrlParam("userId");
	showData();
}

//填充数据
function showData(){
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
	obj.row_has_chk=false;
	obj.select_color="#739BED";
	var head=new Array();
	head.push('用户名');
	head.push('用户真实姓名');
	head.push('部门');
//	head.push('是否使用证书');
	head.push('创建人');
	head.push('创建时间');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	SysUser.showUserByUserId(page,pageSize,userId,cb_data);
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

function toArray(d){
	var row=new Array();
	row.push(d.user_id);
	row.push(d.user_name);
	row.push(d.dept_name);
//	row.push(oprKeyStr(d.useing_key));
	row.push(d.create_user);
	row.push(d.create_data);
	return row;
}
function oprKeyStr(type){
	if(type=="0"){
		return "不使用";
	}else{
		return  "使用";
	}
}
