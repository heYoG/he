function load(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.showModelFiles(modelfile_back);
}
function modelfile_back(d){
	
	var selcert_no=document.all("master_platecid");
	selcert_no.options.length = 0;
	for(var i=0;i<d.length;i=i+1){
		 var varItem = new Option(d[i].model_name,d[i].model_id);
		 selcert_no.options.add(varItem);   
	}
	
}

function change(s)
{
	var c_value = document.getElementById("c_value");
	if(s == 3){
    	c_value.style.visibility = "visible" ;
	}else{
		c_value.style.visibility = "hidden" ;
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

function newjud(){

	var f=$("f_new");
	if(myCheck(f)){
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

var nameExist=false; //字典名称


function myCheck(f,type){
	
	
	if(f.c_name.value.Trim()==""){
		alert("属性名不能为空！");
		return false;
	}
	
	if (typeof(type) == "undefined") {
		type = "new";
	}


	return true;
}
function cb_name_exist(b){
	nameExist=b;
}
