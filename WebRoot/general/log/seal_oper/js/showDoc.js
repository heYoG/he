var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var doc_id="";
var thisUrl=baseUrl(3)+"Seal/general/log/seal_oper/showDoc.jsp";

function load(){
	//if(location==baseUrl(3)+"Seal/newRule.do"){
	//	location=thisUrl;
	//}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	doc_id = getUrlParam("doc_id")==null?1:getUrlParam("doc_id");
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
	head.push('文档编号');
	head.push('文档名称');
	head.push('文档标题');
	head.push('文档类型');
	head.push('文档所属部门');
	head.push('文档录入系统的时间');
	head.push('文档录入系统的ip');
	head.push('文档关键字');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	DocSrv.getDocById(pageSize,page,doc_id,cb_data);
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
	row.push(d.doc_no);
	row.push(d.doc_name);
	row.push(d.doc_title);
	row.push(d.doc_type);
	row.push(d.dept_name);
	row.push(d.create_time);
	row.push(d.create_ip);
	row.push(d.doc_keys);
	return row;
}
