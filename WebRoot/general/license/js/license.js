var obj=new QerTable("tb_list");
function load(){
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
//	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
	head.push('授权单位');
	head.push('授权系统有效期');
	head.push('授权用户数量');
	head.push('授权印章数量');
	head.push("已使用用户数量");
	head.push('已使用印章数量');
	obj.SetHead(head);
}

function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	LicenseInfo.getSql(cb_data);
}
function cb_data(d){
	var row=new Array();
	row.push(d.dept);
	row.push(d.able_data);
	row.push(d.usernum);
	row.push(d.sealnum);
	row.push(d.usernumeuse);
	row.push(d.sealnumuse);
	obj.AddRow(row,1,1);
}

