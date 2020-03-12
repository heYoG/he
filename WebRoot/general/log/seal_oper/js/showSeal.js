var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var sealId="";
var thisUrl=baseUrl(3)+"Seal/general/log/seal_oper/showSeal.jsp";

function load(){
	//if(location==baseUrl(3)+"Seal/newRule.do"){
	//	location=thisUrl;
	//}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	sealId = getUrlParam("sealId")==null?1:getUrlParam("sealId");
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
	head.push('印章名称');
	head.push('类型');
	head.push('所属部门');
	head.push('制章人');
	head.push('制章时间');
	head.push('状态');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	SealBody.showSealBySeal_Id(page,pageSize,sealId,cb_data);
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
	row.push(d.seal_name);
	row.push(d.seal_type);
	row.push(d.dept_name);
	row.push(d.seal_creator);
	row.push(d.create_time);
	row.push(toStatusStr(d.seal_status));
	return row;
}

function toStatusStr(status){
	if(status=="5"){
		return "正常";
	}else if(status=="6"){
		return "失效";
	}else if(status=="7"){
		return "注销";
	}
}
