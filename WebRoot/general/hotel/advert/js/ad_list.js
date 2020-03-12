var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/hotel/advert/ad_list.jsp";

function list_load(dept_no){
	if(location==baseUrl(3)+"Seal/newAdvert.do"){
		location=thisUrl;
	}
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s,dept_no);
	showData();
}

//填充数据
function showData(){
	tb_create();
	getDatas();
}

function tb_create(){
	$("div_table").innerHTML=obj.HtmlCode();
	//obj.row_has_chk=true;
	obj.row_color1="white";
	obj.select_color="#739BED";
	var head=new Array();
//	head.push('字典号');
	head.push('广告标题');
	head.push('文件名称');
	head.push('广告状态');
	head.push('所属部门');
	head.push('创建时间');
	head.push('审批时间');
//	head.push('审批意见');
	head.push('修改时间');
	head.push('生效日期');
	head.push('失效日期');
	head.push('广告预览');
	head.push('操作');
	obj.SetHead(head);
}


function getDatas(){
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	AdSystem.showAdBySch(page,pageSize,cdt_f,cb_data);

}

function cb_data(ps){

	if(ps.datas){
		obj.Clean();
		loadData(ps);
		var bs=ps.datas;
		for(var i=0;i<bs.length;i=i+1){
			var row=toArray(bs[i]);
			obj.AddRow(row,bs[i].ad_id,i+1);
		}
	}
}

function reloadpage(target){
	page=target;
	location=thisUrl+"?page="+page+"&cdt="+cdt_s;
}


function toArray(d){
	var row=new Array();
	var state="";
	var format_name= "";
	row.push(d.ad_title);

	if(d.ad_filename.length>=20){
		//d.ad_filename=d.ad_filename.substring(0,d.ad_filename.length-1);
		var filenames = d.ad_filename.split(",");
		for(var i=0;i<filenames.length;i++){
			format_name += filenames[i]+",";
			if(i!=0&&i%3==0){
				format_name +="\r\n";
			}
		}
		format_name = format_name.substring(0, format_name.lastIndexOf(","));
		row.push(format_name);
	}else{
		row.push(d.ad_filename);
	}


	//var fileNames = d.ad_filename;
	//var scorlltime=d.ad_scorlltime;

	if(d.ad_state==0){
		state = "待审批";
	}
	if(d.ad_state==1) state="审核未通过";
	if(d.ad_state==2) state="激活中";
	if(d.ad_state==3) state="已注销";
	row.push(state);
	row.push(d.ad_deptname);
	row.push(d.ad_ctime);
	row.push(d.ad_approvetime);
//	row.push(d.ad_opinion);
	row.push(d.ad_updatetime); 
	row.push(d.ad_starttime);
	row.push(d.ad_endtime);
	var oprStr=toOprStr(d);
	//var str = "<input type='button' value='预览' onclick='advPreview(\""+fileNames+"\")'>";
	//alert(str);
	var previewStr=toPreview(d);
	row.push(previewStr);
	row.push(oprStr);
	return row;
}
function toPreview(d){
	var str="";
	str+="<input type='button' onclick=\"advPreview('";//advPreview(...)方法必须用双引号"",且要用\解析
	str+=d.ad_filename;
	str+="','";
	str+=d.ad_scorlltime;
	str+="','";
	str+=d.ad_id;
	str+="');flag=true;\" class='SmallButton'  value='预览' />";
	return str;
}
function advPreview(fileNames,scorlltime,id){
	scorlltime = parseInt(scorlltime)*1000;
	var url="img_dialog.jsp?name="+fileNames+"&scorlltime="+scorlltime+"";
	window.open(url,"", "scrollbars=yes,resizable=no,width=900,height=700");
}


function toOprStr(d){
	var str="";
	if(d.ad_state==2){
		str+="<input type='button' onclick=\"updAdvert('";
		str+=d.ad_id;
		str+="');flag=true;\" class='SmallButton'  value='修改' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"updState('";
		str+=d.ad_id+"','"+d.ad_state+"','"+d.ad_title;
		str+="');flag=true;\" class='SmallButton' value='注销' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"delAdvert('";
		str+=d.ad_id+"','"+d.ad_filename+"','"+d.ad_title;
		str+="');flag=true;\" class='SmallButton' value='删除' />";
		
	}
	if(d.ad_state == 3){
		str+="<input type='button' onclick=\"updAdvert('";
		str+=d.ad_id;
		str+="');flag=true;\" class='SmallButton' value='修改' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"updState('";
		str+=d.ad_id+"','"+d.ad_state+"','"+d.ad_title;
		str+="');flag=true;\" class='SmallButton' value='激活' />";
		str+="&nbsp;&nbsp;";
		str+="<input type='button' onclick=\"delAdvert('";
		str+=d.ad_id+"','"+d.ad_filename+"','"+d.ad_title;
		str+="');flag=true;\" class='SmallButton' value='删除' />";
		
	}
	return str;
}



//高级搜索
function show_sch(){

	var f=$("ad_sch");
	var k_vs=cdt_s.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
		var k_v=k_vs[i].split(":=");
		if("ad_title"==k_v[0]){
			f.ad_title.value=k_v[1];
		}
		if("ad_filename"==k_v[0]){
			f.ad_filename.value=k_v[1];
		}
		if("ad_dept"==k_v[0]){
			f.ad_dept.value=k_v[1];
		}
		if("ad_state"==k_v[0]){
			f.ad_state.value=k_v[1];
		}
	}
	hidden("d_list");
	disp("d_search");
}

//返回
function ret(){
	hidden("d_search");
	disp("d_list");
}

function updAdvert(no){
	var f=$("f_editad");
	//f.ad_filename.style.display="inline";
	//f.filebutton.style.display="inline";
	var addFileDiv = document.getElementById("add_fileInput");
	addFileDiv.innerHTML="";
	//f.ad_name.value="";
	//document.getElementsByName("ad_name")[0].value="";
	//f.ad_name.style.display="none";
	//document.getElementsByName("ad_name")[0].style.display="none";
	//f.add.style.display="none";

	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	AdSystem.getHotelAdvertVOById(no,ad_objs);
	ShowDialog("detail",30);
}

function ad_objs(obj){
	var f=$("f_editad");
	f.reset();
	f.ad_id.value = obj.ad_id;
	f.ad_title.value=f.old_title.value=obj.ad_title;
	f.old_filename.value=obj.ad_filename;
	filenamelist(obj.ad_filename);
	f.ad_ctime.value=f.old_ctime.value=obj.ad_ctime;
	f.dept_name.value=f.old_dept.value=obj.ad_deptname;
	f.dept_no.value=obj.ad_dept;
	f.starttime.value=obj.ad_starttime;
	f.endtime.value=obj.ad_endtime;
	f.scorlltime.value=obj.ad_scorlltime;
}


//搜索
function searchAD(){	

	var f=$("ad_sch");
	var c=fToCdt(f);
	if(c){
		
		location=thisUrl+"?page=1&cdt="+c;
	}
}

//得到搜索条件的字符串
function fToCdt(f){
	
	var s="";
	
	var ad_title=f.ad_title.value;
	if(!checkStr(ad_title,"广告标题")){
		return false;
	}
	s=""==s?s+"ad_title:="+ad_title:s+"!ad_title:="+ad_title;
	
	var ad_filename=f.ad_filename.value;
	if(!checkStr(ad_filename,"广告文件名称")){
		return false;
	}
	s=""==s?s+"ad_filename:="+ad_filename:s+"!ad_filename:="+ad_filename;
	var ad_dept=f.dept_no.value;
	if(!checkStr(ad_dept,"所属部门")){
		return false;
	}
	s=""==s?s+"ad_dept:="+ad_dept:s+"!ad_dept:="+ad_dept;
	
	var ad_ctime = f.ad_ctime.value;
	if(!checkStr(ad_ctime,"广告创建时间")){
		return false;
	}
	s=""==s?s+"ad_ctime:="+ad_ctime:s+"!ad_ctime:="+ad_ctime;
	
	var ad_state = f.ad_state.value;
	if(!checkStr(ad_state,"广告状态")){
		return false;
	}
	s=""==s?s+"ad_state:="+ad_state:s+"!ad_state:="+ad_state;
	
	return s;
}

//根据字符串得到搜索条件
function cdtToF(cdt,dept_no){
	var f={};
	f.ad_dept = dept_no;
	f.ad_state = 2;
	var k_vs=cdt.split("!");
	for(var i=0;i<k_vs.length;i=i+1){
	
		var k_v=k_vs[i].split(":=");
		if("ad_title"==k_v[0]){
			f.ad_title=k_v[1];
		}
		if("ad_filename"==k_v[0]){
			f.ad_filename=k_v[1];
		}
		if("ad_state"==k_v[0]){
			f.ad_state=k_v[1];
		}
		if("ad_ctime"==k_v[0]){
			
			f.ad_ctime=k_v[1];
		}
		if("ad_dept"==k_v[0]){
			f.ad_dept=k_v[1];
		}
		if("approve_user"==k_v[0]){
			f.approve_user=k_v[1];
		}
		
	}
	return f;
}

function objUpd(){
	var f=document.getElementById("f_editad");
	var newfilenames="";
	var filenames=document.getElementsByName("ad_name");
	//if(document.getElementById("adfilename").value==""){
		for(var i=0;i<filenames.length;i++){
			//if(filenames[1].value==""){
				//alert("广告文件不能为空！");
			//}else{
					dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
					var file = filenames[i].value;
					file = file.substring(file.lastIndexOf("\\")+1);
					newfilenames=newfilenames+file+",";
					//alert(file);
					AdSystem.checkFileName(file,cb_file_exist);
					if(fileExixt&&f.old_filename.value.indexOf(file)<=-1){
						//alert(file+"图片已经存在，请重新选择！"); 
						document.getElementsByName("aa")[i].value=file+"图片已经存在，请重新选择！";
						document.getElementsByName("aa")[i].style.display="block";
						return false;
					}
					document.getElementsByName("aa")[i].style.display="none";
					var suffixname=file.substring(file.indexOf("."));
					//alert(suffixname);
					if(suffixname!=".jpg"){
						alert("不支持"+file+"的格式，请重新选择！");
						return false;
					}
		}
	//}
	if(myCheck(f,"edit")&&confirm("确定要修改吗?")){
		var delfilename="";
		var addfilename="";
		var oldfile="";
		var newfile="";
		newfilenames=newfilenames.substring(0,newfilenames.length-1);
		var newfilename=newfilenames.split(",");
		var oldfilenames=f.old_filename.value.split(",");
		for(var m=0;m<oldfilenames.length;m++){
			oldfile = oldfilenames[m];
			var count=0;
			for(var n=0;n<newfilename.length;n++){
				newfile=newfilename[n];
				if(newfile==oldfile){
					count++;
				};
			}
			if(count==0){
				delfilename=delfilename+oldfile+",";
			}
		}
		delfilename=delfilename.substring(0,delfilename.length-1);
		if(delfilename!=""){
			AdSystem.deleteFile(delfilename,f.ad_id.value);
		}
		var scrollTime = parseInt(f.scorlltime.value);
		if(scrollTime <= 1){
			alert("轮播时间必须大于1秒");
		}
		
		f.action="saveTo.jsp?old_file="+f.old_filename.value+"&id="+f.ad_id.value+"&title="+f.ad_title.value
					+"&dept="+f.dept_no.value+"&scorlltime="+scrollTime+"&approve_user="+f.approve_user.value;
		f.submit();
		//}
		//alert("filename:"+f.ad_name.value);
		/*var vo=formToVo(f);
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		AdSystem.deleteFile(f.old_filename.value,vo.ad_id);
		AdSystem.updateAdvert(vo,f.starttime.value,f.endtime.value,f.scorlltime.value,document.getElementById("approve_user").value);
		LogSys.logAdd(user_no,user_name,user_ip,"广告管理","修改广告:"+f.ad_title.value);//logSys.js*/
		//location=thisUrl+"?page="+page;
	}
}


function formToVo(f){
	var objPo={};
	objPo.ad_id = f.ad_id.value;
	objPo.ad_title=f.ad_title.value;
	var name="";
	var names=document.getElementsByName("ad_name");
	for ( var i = 0; i < names.length; i++) {
		var item = names[i].value.substring(names[i].value.lastIndexOf("\\")+1);
		name = name+ item + "," ;
	}
	name=name.substring(0,name.length-1);
	objPo.ad_filename=name;
	objPo.ad_dept=f.dept_no.value;
	return objPo;
}

function updState(id,state,title){
	var obj={};
	var opertype;
	if(state==2){
		opertype="注销广告";
		obj.ad_state = 3;
	}
    if(state==3){
    	opertype="激活广告";
    	obj.ad_state = 2;
    }
	obj.ad_id = id;
	var vo = obj;
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    AdSystem.updateAdvertstate(obj.ad_state,obj.ad_id);
	LogSys.logAdd(user_no,user_name,user_ip,"广告管理",opertype+":"+title);//logSys.js
	location=thisUrl+"?page="+page;
}
/**
 * 修改文件
 * @return
 */
function changeFile(){
	var f=$("f_editad");
	f.ad_filename.style.display="none";
	f.ad_filename.value="";
	//document.getElementsByName("ad_filename")[1].value="";
	f.filebutton.style.display="none";
	//f.ad_name.style.display="block";
	document.getElementsByName("ad_name")[0].style.display="block";
	f.add.style.display="block";
}

/**
 * 
 * 删除文件，删除广告信息
 * @param id
 * @return
 */
function delAdvert(id,filePath,title){
	if(confirm("确定要删除吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		AdSystem.deleteAdvert(id);
		AdSystem.deleteFile("",id);
		LogSys.logAdd(user_no,user_name,user_ip,"广告管理","删除广告:"+title);//logSys.js
		location=thisUrl+"?page="+page;
	}
}

function GetDate() {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
			document.forms[0].ad_ctime.value = reVal;
	}
}