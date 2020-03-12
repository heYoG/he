function new_role_load(){
//	alert();
}

function funcChose(f_id){
	var f=$(f_id);
	var url="/Seal/general/organise/role_mng/func_chose.jsp?sel=";
	url += f.func_v.value+"&&sel2="+f.func_v2.value;
	var h=420;
	var w=800;
	newModalDialog(url,w,h,f);
}

function ruleChose(f_id,s_id){
	var f=$(f_id);
	var url="/Seal/general/gaizhang_app/rule_chose.jsp?sel=";
	url += f.sel_rules.value;
	var h=400;
	var w=480;
	newModalDialog(url,w,h,f);
	freshNum(f.sel_rules.value,s_id);
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

function userChose(f_id,s_id){
	alert("待开发。。。");
	return ;
}

function chkName(t){
	SysRole.isExistRole(t.value,cb_exist);
}
function cb_exist(b){
	if(b==1){
		alert("已经存在的角色！");
		$("f_new").role_name.select();
	}
}

function checkForm(f_id,type){
	var f=$(f_id);
	if(f.role_tab.value==""){
	 alert("角色排序号不允许为空！");
	 return false;
	}
	if(!f.role_tab.value.isNumeric()){
		alert("请输入正确的排序号！");
		f.role_tab.select();
		return false;
	}
	if (typeof (type) == "undefined") {
		type = "new";
	}
	if(type=="edit"&&f.role_tab.value==f.old_tab.value){
	
	}else{
		var roleno=f.role_tab.value;
		dwr.engine.setAsync(false);
		SysRole.isExistRoleno(roleno,IsExist);
		if(noExist){
			alert('角色排序号已存在,请重新输入!');
			f.role_tab.select();
			return false;
		}
	}
	if(f.role_name.value.Trim()==""){
		alert("角色名不能为空！");
		f.role_name.select();
		return false;
	}
   var pattern=/^[0-9a-zA-Z\u4e00-\u9fa5]+$/i;
   if(!pattern.test(f.role_name.value.Trim())){
     alert("角色名是以中文,英文,数字!");
     return false;
   }
	if(type=="edit"&&f.role_name.value==f.old_name.value){
	
	}else{
		dwr.engine.setAsync(false);
		SysRole.isExistRolename(f.role_name.value,IsNameExist);
		if(nameExist){
			alert('角色名已存在,请重新输入!');
			f.role_name.select();
			return false;
		}
	}
	return true;
}

var noExist=nameExist=false;
function newObj(){
	var f=$("f_new");
	 if(checkForm(f.id)){
		dwr.engine.setAsync(false);
		//logAdd("角色","",f.role_name.value);//logOper.js
		LogSys.logAdd(user_no,user_name,user_ip,"新建角色","新建角色:"+f.role_name.value+"成功");
		f.submit();
	 }
}
function IsExist(i){
   if(i=='1'){
   	noExist=true;
   }else{
   	noExist=false;
   }
}
function IsNameExist(i){
   if(i=='1'){
   	nameExist=true;
   }else{
   	nameExist=false;
   }
}