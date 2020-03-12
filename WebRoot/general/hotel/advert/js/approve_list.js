var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/hotel/advert/approve_list.jsp";
var user_id = "";

function list_load(u){
//	if(location==baseUrl(3)+"Seal/newAdvert.do"){
//		location=thisUrl;
//	}
	user_id = u;
	page=getUrlParam("page")==null?1:getUrlParam("page");
	cdt_s=(null==getUrlParam("cdt"))?cdt_s:getUrlParam("cdt");
	cdt_f=cdtToF(cdt_s);
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
	head.push('选');
	head.push('广告标题');
	head.push('文件名称');
	head.push('广告状态');
	head.push('所属部门');
	head.push('创建时间');
	head.push('创建人');
	head.push('生效时间');
	head.push('失效时间');
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

	var inp='<input type="checkbox" name="seal_select" value="'+d.ad_title+'" onClick="check_one(self);">';
	//alert(inp);
	row.push(inp);

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
	if(d.ad_state==0){
		state = "待审批";
	}
	if(d.ad_state==1) state="审核未通过";
	if(d.ad_state==2) state="激活中";
	if(d.ad_state==3) state="已注销";
	row.push(state);
	row.push(d.ad_deptname);
	row.push(d.ad_ctime);
	row.push(d.approve_user);
	row.push(d.ad_starttime);
	row.push(d.ad_endtime);
	var oprStr=toOprStr(d);
	var previewStr=toPreview(d);
	row.push(previewStr);
	row.push(oprStr);
	return row;
}



function toPreview(d){
	var str="";
	str+="<input type='button' onclick=\"advPreview('";
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
	var url="img_dialog.jsp?name="+fileNames+"&scorlltime="+scorlltime+"&id="+id+"";
	window.open(url,"", "scrollbars=yes,resizable=no,width=900,height=700");
}

function toOprStr(d){
	var str="";
		str+="<input type='button' onclick=\"approve1('";
		str+=d.ad_id;
		str+="','";
		str+=d.ad_title;
		str+="','2');flag=true;\" class='SmallButton'  value='同意' />";
		str+="<input type='button' onclick=\"approve1('";
		str+=d.ad_id;
		str+="','";
		str+=d.ad_title;
		str+="','1');flag=true;\" class='SmallButton'  value='拒绝' />";
	
	return str;
}

function approve1(ID,title,flag)
{   
	if(flag==2){
	  document.getElementById("leixing").value="批准广告";
	  var msg="确认要批准广告申请吗？请填写批准意见：";
	}  
	if(flag==1)
	{
	  document.getElementById("leixing").value="退回修改广告";
	  var msg="确认要退回广告申请吗？请填写退回意见：";
	}
	document.getElementById("ad_id").value=title;
	document.getElementById("this_title").value=title;
	 $("confirm").innerHTML="<font color=red>"+msg+"</font>";
    $("ad_statu").value=flag;
    ShowDialog('approve'); 
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
	var f=$("f_edit");
	f.ad_filename.style.display="inline";
	f.filebutton.style.display="inline";
	f.ad_name.value="";
	f.ad_name.style.display="none";

	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	AdSystem.getHotelAdvertVOById(no,ad_objs);
	ShowDialog("detail",30);
}

function approve(){
	
}

function ad_objs(obj){
	var f=$("f_edit");
	f.reset();
	f.ad_id.value = obj.ad_id;
	f.ad_title.value=f.old_title.value=obj.ad_title;
	f.ad_filename.value=f.old_filename.value=obj.ad_filename;
	f.ad_ctime.value=f.old_ctime.value=obj.ad_ctime;
	f.dept_name.value=f.old_dept.value=obj.ad_deptname;
	f.dept_no.value=obj.ad_dept;
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
function cdtToF(cdt){
	var f={};
	if(cdt==0){
		f.ad_state=0;
	
		f.approve_user=user_id;
	}
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
			alert(k_v[1]);
			f.approve_user=k_v[1];
		}
	}
	return f;
}



function formToVo(f){
	
	var obj={};
	obj.ad_id = f.ad_id.value;
	obj.ad_title=f.ad_title.value;
	if(f.ad_name.value.Trim()!=null&&f.ad_name.value.Trim()!=""){
		f.ad_filename.value = f.ad_name.value;
		var ad_name = f.ad_name.value;
		ad_name = ad_name.substring(ad_name.lastIndexOf("\\")+1);
		
		obj.ad_filename = ad_name;
	}
	if(f.ad_filename.value==f.old_filename.value){
		obj.ad_filename=f.old_filename.value;
	}
	
	obj.ad_dept=f.dept_no.value;
	
	return obj;
}

function updState(id,state){
	var obj={};
	if(state==0) obj.ad_state = 1;
    if(state==1) obj.ad_state = 0;
	obj.ad_id = id;
	var vo = obj;
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
    AdSystem.updateAdvert(vo);

	location=thisUrl+"?page="+page;
}
/**
 * 修改文件
 * @return
 */
function changeFile(){
	var f=$("f_edit");
	f.ad_filename.style.display="none";
	f.filebutton.style.display="none";
	f.ad_name.style.display="block";
	
}

/**
 * 
 * 删除文件，删除广告信息
 * @param id
 * @return
 */
function delAdvert(id,filePath){

	if(confirm("确定要修改吗？")){
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		AdSystem.deleteAdvert(id);
		AdSystem.deleteFile(filePath);
		
		location=thisUrl+"?page="+page;
	}
	
}

function GetDate() {
	reVal = window.showModalDialog("/Seal/inc/showDate.htm", "", "status:no;center:yes;scroll:no;resizable:no;help:no;dialogWidth:255px;dialogHeight:260px");
	if (reVal != null) {
			document.forms[0].ad_ctime.value = reVal;
	}
}

function doButton(){
	  if($("context").innerText==""){
			alert("请填写审批意见！");
			return  false;
	  }else{
		var approve_text=document.getElementById("context").value;
	     if(approve_text.length > 20){
	        alert("输入的理由长度不能超过20个字符!");
	        return false;
	     }else{
	        approve_text=approve_text.replace(/\'/g,"‘");
	        document.getElementById("ad_opinion").value=approve_text;
	     }
	       var lx=document.getElementById("leixing").value;
		   var this_title=document.getElementById("this_title").value;
		   LogSys.logAdd(user_no,user_name,user_ip,"广告审批",lx+":"+this_title);//logSys.js
		   document.getElementById("form2").action="/Seal/adApps.do";
		   document.getElementById("form2").submit();
	   }
	  // var adId=document.getElementById("ad_id").value;
	  // AdSystem.getHotelAdvertById(adId,callback); 
}
	
function callback(temp_name){
	   var lx=document.getElementById("leixing").value;
	   var this_title=document.getElementById("this_title").value;
	   LogSys.logAdd(user_no,user_name,user_ip,"广告审批",lx+temp_name);//logSys.js
	   document.getElementById("form2").action="/Seal/adApps.do";
	   document.getElementById("form2").submit();
	}


function check_one(el)
{
   if(!el.checked)
      document.all("allbox").checked=false;
}

function check_all()
{
 for (i=0;i<document.all("seal_select").length;i++)
 {
   if(document.all("allbox").checked)
      document.all("seal_select").item(i).checked=true;
   else
      document.all("seal_select").item(i).checked=false;
 }

 if(i==0)
 {
   if(document.all("allbox").checked)
      document.all("seal_select").checked=true;
   else
      document.all("seal_select").checked=false;
 }
}

function get_checked()
{
  checked_str="";
  for(i=0;i<document.all("seal_select").length;i++)
  {

      el=document.all("seal_select").item(i);
      if(el.checked)
      {  val=el.value;
         checked_str+=val + ",";
      }
  }

  if(i==0)
  {
      el=document.all("seal_select");
      if(el.checked)
      {  val=el.value;
         checked_str+=val + ",";
      }
  }
  return checked_str;
}

function approve2(ID,flag,range)
{   
   //  alert(flag);
	if(flag==2){
		 document.getElementById("leixing").value="批准广告";
	  var msg="确认要同意广告申请吗？请填写批准意见："
	}  
	if(flag==1)
	{
		 document.getElementById("leixing").value="退回修改广告";
	  var msg="确认要拒绝广告申请吗？请填写退回意见：";
	}
	 $("confirm").innerHTML="<font color=red>"+msg+"</font>";
	if(range=="ALL") {
		if(get_checked()==""){
			alert("请先选择您要操作的广告！");
			return;
		   }
		document.getElementById("ad_id").value = get_checked();
	}
	else{
	  //$("ID").value=ID+",";
		document.getElementById("ad_id").value=get_checked();
	 }
	document.getElementById("this_title").value= get_checked();
	document.getElementById("ad_statu").value=flag;
    ShowDialog('approve'); 
}