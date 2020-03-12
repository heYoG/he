function load(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
}


function roleSet(f_id,s_id){
	var f=$(f_id);
	var url="/Seal/general/organise/role_mng/role_chose.jsp?sel="+f.sel_roles.value;
	var w=500;
	var h=400;
	newModalDialog(url,w,h,f);
	freshNum(f.sel_roles.value,s_id);
}

function clickme(no){
	if(no==0){
		$("sort_id").style.display = "block";
	}else{
		$("sort_id").style.display = "none";
		$("sortno").value=0;
	}
	
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

function newDic(){

	var f=$("f_new");
	var val = f.c_sort.value;
	if(checkSort(val)&&myCheck(f)){
		f.cdatatype.value=f.type.value;
		//f.arg_desc.value=makeArgDesc(f.id);
//		f.seal_id.value=f.seal_no.value;
		dwr.engine.setAsync(false);
		LogSys.logAdd(user_no,user_name,user_ip,"通用字典管理","添加通用字典:"+f.cname.value+"成功");
		$("f_new").submit();
	}
}

function checkSort(val){
	
	if(!$("r2").checked){
		if(isNaN(val)||val<=0){
			alert("显示顺序必须为大于零的正整数！");
			$("f_new").c_sort.select();
			return false;
		}else{
			return true;
		}
	}else{
		return true;	
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
	f.cname.value = f.cname.value.Trim();
	if(f.cname.value==""){
		alert("通用字典名不能为空！");
		return false;
	}
	f.cshowname.value = f.cshowname.value.Trim();
	if(f.cshowname.value==""){
		alert("显示名称不能为空！");
		return false;
	}
	if (typeof(type) == "undefined") {
		type = "new";
	}
	if(type=="edit"&&f.cname.value==f.old_name.value){
	}else{
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		HotelDic.isNameExist(f.cname.value,cb_name_exist);
		if(nameExist){
			alert("已经存在的通用字典名，请重输！");
			f.cname.select();
			return false;
		}
	}
	if(type=="edit"&&f.cshowname.value==f.old_showname.value){
		
	}else{
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		
		HotelDic.isShowNameExist(f.cshowname.value,cb_name_exist);
		if(nameExist){
			alert("已经存在的通用字典显示名，请重输！");
			f.cshowname.select();
			return false;
		}
	}
	if(type=="edit"&&f.c_sort.value==f.old_c_sort.value){
		}else{
			if(f.c_sort.value==0){
				return true;
			}
			dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
			HotelDic.isExistSort(f.c_sort.value,cb_sort_exist);
			if(sortExist){
			alert("已经存在的顺序号，请重输！");
			f.c_sort.select();
			return false;
		}
	}

	return true;
}
var sortExist = "";

function cb_sort_exist(b){
	sortExist=b;
}
function cb_name_exist(b){
	nameExist=b;
}
