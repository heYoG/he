function load(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	CertSrv.showPublicCerts(cert_back);
}
function cert_back(d){
	var selcert_no=document.all("cert_no");
	selcert_no.options.length = 0;
	for(var i=0;i<d.length;i=i+1){
		 var varItem = new Option(d[i].cert_name,d[i].cert_no);
		 selcert_no.options.add(varItem);   
	}
	
}
function changeUseCert(){
	var check = document.getElementById("chk_cert").checked;
	if(check==true){
		document.getElementById("tr_select_cert").style.display="";
		document.getElementById("use_cert").value="1";
	}else{
		document.getElementById("tr_select_cert").style.display="none";
		document.getElementById("use_cert").value="2";
	}
}


function roleSet(f_id,s_id){
	var f=$(f_id);
	var url="/Seal/general/organise/role_mng/role_chose.jsp?sel="+f.sel_roles.value;
	var w=500;
	var h=400;
	newModalDialog(url,w,h,f);
	freshNum(f.sel_roles.value,s_id);
}

function freshNum(str,s_id){
	var sum=0;
	if(str!=""&&str!=null){
		var strs=str.split(",");
		for(var i=0;i<strs.length;i=i+1){
			sum=sum+1;
		}
	}
	$(s_id).innerHTML=sum;
}

function newRule(){
	var f=$("f_new");
	if(myCheck(f)&&check("f_new")){
		f.rule_type.value=f.type.value;
		f.arg_desc.value=makeArgDesc(f.id);
		f.seal_id.value=f.seal_no.value;
		dwr.engine.setAsync(false);
		LogSys.logAdd(user_no,user_name,user_ip,"规则管理","添加规则:"+f.rule_name.value+"成功");
		$("f_new").submit();
	}
}

function choseSeal(f_id){
	var f=$(f_id);
	//var url="/Seal/depttree/new_dept_tree.jsp?s=true&&req=new_rule&&user_no="+user_no;
	//var url="/Seal/depttree/dept_tree.jsp?s=true&&user_no="+user_no;
	var url="/Seal/depttree/dept_tree.jsp?s=true&&req=new_rule&&user_no="+user_no;
	var h=450;
	var w=300;
	newModalDialog(url,w,h,f);
}

var nameExist=false;
function myCheck(f,type){
	if(f.rule_name.value.Trim()==""){
		alert("规则名不能为空！");
		return false;
	}
	if (typeof (type) == "undefined") {
		type = "new";
	}
	if(type=="edit"&&f.rule_name.value==f.old_name.value){
	
	}else{
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		GaiZhangRuleSrv.isNameExist(f.rule_name.value,cb_name_exist);
		if(nameExist){
			alert("已经存在的规则名，请重输！");
			f.rule_name.select();
			return false;
		}
	}
	if(!checkStr(f.rule_name.value,"规则名")){
		return false;
	}
	if(f.seal_no.value==""){
		alert("请选择指定公章！");
		return false;
	}
	f.use_cert.value=f.chk_cert.checked?"1":"0";
	return true;
}
function cb_name_exist(b){
	nameExist=b;
}
