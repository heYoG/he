function load(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	addModelOption();
	addRuleOption();
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

function newMFR(){
	var f= $("f_new");
	var m=$("model");
	var r=$("rule");
	if(myCheck(m,r)){
		//f.cdatatype.value=f.type.value;
		//f.arg_desc.value=makeArgDesc(f.id);
//		f.seal_id.value=f.seal_no.value;
		dwr.engine.setAsync(false);
		LogSys.logAdd(user_no,user_name,user_ip,"通用字典管理","添加通用字典:成功");
		$("f_new").submit();
	}
}

function choseSeal(f_id){
	var f=$(f_id);
	var url="/Seal/depttree/new_dept_tree.jsp?s=true&&req=new_rule&&user_no="+user_no;
	var h=450;
	var w=300;
	newModalDialog(url,w,h,f);
}

var modelExist=false; //


function myCheck(m,r,type){
	
	
	if(m.value==""){
		alert("请选择模板名称！");
		return false;
	}
	if (typeof(type) == "undefined") {
		type = "new";
	}
	
	if(r.value==""){

		alert("请选择规则名称！");
		return false;
	}
	
	var f = $("f_edit");
	
	if(type=="edit" && m.value==f.old_model.value && r.value == f.old_rule.value){
	}else{
	
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		MFRSrv.isModelFileRuleExist(m.value,r.value,cb_model_exist);
		if(modelExist){
			alert("已经存在的模板规则！");
			return false;
		}
	}
		
	return true;
}

function cb_model_exist(b){
	modelExist=b;
}


function addRuleOption(){  
	
	var array = GaiZhangRuleSrv.showRules(cb_datas);
 
} 

function cb_datas(ps){
	var objOption = "";
	var objSelect=document.getElementById("rule");  
	objSelect.length=0;
	objOption = new Option("--请选择--","");
	objSelect.add(objOption,-1);
	for(var i=0;i<ps.length;i=i+1){

		objOption = new Option(ps[i].rule_name,ps[i].rule_no);
		objSelect.add(objOption, i+1); 
	}
}
	
	function addModelOption(){
		var array = modelFileSrv.showModelFiles(model_datas);
	}
	
	function model_datas(ps){
		var objOption = "";
		var objSelect=document.getElementById("model"); 
		objSelect.length=0;
		objOption = new Option("--请选择--","");
		objSelect.add(objOption,-1);
		for(var i=0;i<ps.length;i=i+1){
		
			objOption = new Option(ps[i].model_name,ps[i].model_id);
			objSelect.add(objOption, i+1); 
		
		}
		
}

