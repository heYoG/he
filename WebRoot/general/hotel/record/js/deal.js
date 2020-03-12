var page_no;
var x_position;
var y_position;
var area_width;
var area_height;
var area_name;
var modelfilename;
var no;
var name;
var mptID;
var role_name;
function load(){
	no=(null==getUrlParam("no"))?"":getUrlParam("no");
	name=(null==getUrlParam("name"))?"test":getUrlParam("name");
	mptID=(null==getUrlParam("mptID"))?"":getUrlParam("mptID");
	role_name=(null==getUrlParam("roleName"))?"":getUrlParam("roleName");
	if(role_name=="yiji"){
		document.getElementById("lqianzi").style.display="none";//隐藏二级稽核按钮
	}else if(role_name=="erji"){
		document.getElementById("qianzi").style.display="none";//隐藏一级稽核按钮
	}else{
	}
	var url=baseUrl(3)+"Seal/doc/hotelDocs/"+name;
	document.all.HWPostil1.LoadFile(url);
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	modelFileSrv.getModelFile(mptID,cd_modelfile);
}

function cd_modelfile(d){
	if(d==null){
		alert("查找签字位置失败！");
	}else{
		page_no = d.page_no;
		x_position = d.x_position;
		y_position = d.y_position;
		area_width = d.area_width;
		area_height = d.area_height;
		area_name = d.area_name;
		modelfilename = d.model_name;
	}
}

function HWPostil1_NotifyCtrlReady(){
	var objAIP=document.getElementById("HWPostil1");
	objAIP.JSEnv = 1;
	objAIP.ShowDefMenu = 0 ;
	objAIP.ShowScrollBarButton =2;
	objAIP.ShowToolBar = 0;	
	objAIP.SilentMode = 1;	
	objAIP.SetPageMode(2,0);
	objAIP.Login(user_no,2, 65535,"",""); 
}


function handWrite(){
	var objAIP=document.getElementById("HWPostil1");
	var secondsignarea = objAIP.GetValueEx("Page"+page_no+"."+area_name,12,"",0,"");
	if(secondsignarea==""){
		var loginresult = objAIP.Login("sys_admin",5, 65535, "DEMO", "");
		if(loginresult!=0){
			alert("模板用户登录失败");
			return false;
		}
		var inpage = page_no - 1;
		var inresult = objAIP.InsertNote(area_name,inpage,2,x_position,y_position,area_width,area_height);
		if(inresult==null||inresult==""){
			alert("插入签字区域出错，请检查模板配置是否正确");
			objAIP.Logout();
			return false;
		}
		objAIP.SetValue("Page"+page_no+"."+area_name,":PROP:BORDER:0");//设置边框为透明
		objAIP.SetValue("Page"+page_no+"."+area_name,":PROP::CLICKPOP:1");//设置为弹出款手写
		objAIP.Logout();
		loginresult = objAIP.Login(user_no,2, 65535,"","");
		if(loginresult!=0){
			alert("用户登录失败");
			return false;
		}
//		objAIP.SetValue("LABEL_ALLNOTES_STATUS","3");//设置所有节点不可选 
		objAIP.ShowFullScreen = 1;
		objAIP.SetValue("Page"+page_no+"."+area_name,":PROP:ACTIVATE:1");
	}else{
		objAIP.ShowFullScreen = 1;
		objAIP.SetValue("Page"+page_no+"."+area_name,":PROP:ACTIVATE:1");
	}
}


function SecondHandWrite(){
	var objAIP=document.getElementById("HWPostil1");
	var secondsignarea = objAIP.GetValueEx("Page1.LLRSignArea",12,"",0,"");
	if(secondsignarea==""){
		var loginresult = objAIP.Login("sys_admin",5, 65535, "DEMO", "");
		if(loginresult!=0){
			alert("模板用户登录失败");
			return false;
		}
		var inresult = objAIP.InsertNote("LLRSignArea",0,2,27000,44000,4000,1300);
		if(inresult==null||inresult==""){
			alert("插入签字区域出错，请检查模板配置是否正确");
			objAIP.Logout();
			return false;
		}
		objAIP.SetValue("Page1.LLRSignArea",":PROP:BORDER:0");//设置边框为透明
		objAIP.SetValue("Page1.LLRSignArea",":PROP::CLICKPOP:1");//设置为弹出款手写
		objAIP.Logout();
		loginresult = objAIP.Login(user_no,2, 65535,"","");
		if(loginresult!=0){
			alert("用户登录失败");
			return false;
		}
//		objAIP.SetValue("LABEL_ALLNOTES_STATUS","3");//设置所有节点不可选 
		objAIP.ShowFullScreen = 1;
		objAIP.SetValue("Page1.LLRSignArea",":PROP:ACTIVATE:1");
	}else{
		objAIP.ShowFullScreen = 1;
		objAIP.SetValue("Page1.LLRSignArea",":PROP:ACTIVATE:1");
	}
}


function saveUpload(){
	var objAIP=document.getElementById("HWPostil1"); 
	var myDate = new Date();
	var filename = modelfilename +"_"+ user_no +"_"+myDate.getFullYear()+(myDate.getMonth()+1)+myDate.getDate()+myDate.getHours()+myDate.getMinutes()+myDate.getSeconds();
//	filename = filename+".pdf";
	filename = name;
	objAIP.ForceSignType2 = objAIP.ForceSignType2 | 0x08000; 
	objAIP.HttpInit();
	objAIP.HttpAddPostCurrFile("FileContent");
	objAIP.HttpAddPostString("name",filename);
	var rrr = objAIP.HttpPost(baseUrl(3)+"Seal/general/hotel/record/saveTo.jsp");
	if(rrr=="kkkkk"){
	//	alert("上传完成");
	}else{
		alert("上传失败!");
		return false;
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	HotelRecord.dealRecord(user_no,no,filename,user_ip,role_name,cb_dealResult);
}

function cb_dealResult(r){
	if(r=="success"){
		var objAIP=document.getElementById("HWPostil1"); 
		objAIP.CloseDoc(0);
		window.opener=null;
		window.open('', '_self', '');
		window.close();
	}else{
		alert(r);
		var objAIP=document.getElementById("HWPostil1"); 
		objAIP.CloseDoc(0);
		window.opener=null;
		window.open('', '_self', '');
		window.close();
	}
}

function printdoc(){
	document.getElementById("HWPostil1").PrintDoc(1,1);
}

function setbikuan(){
		var objAIP=document.getElementById("HWPostil1"); 
		objAIP.CurrPenWidth = -1; 
} 

function setyanse(){
		var objAIP=document.getElementById("HWPostil1"); 
		objAIP.CurrPenColor = -1; 
} 

function setqueren(){
		var objAIP=document.getElementById("HWPostil1"); 
		objAIP.ShowFullScreen = 0;  
		objAIP.SetValue("LABEL_ALLNOTES_STATUS","0");//设置所有节点可选 
  		objAIP.CurrAction=1; 
} 

function HWPostil1_NotifyFullScreen(){
	var objAIP=document.getElementById("HWPostil1");  
	if(objAIP.ShowFullScreen == 0){
		objAIP.CurrAction=1;  
		objAIP.SetValue("LABEL_ALLNOTES_STATUS","0");//设置所有节点可选 
		objAIP.JSValue = 0;  
	}
} 

function HWPostil1_NotifyBeforeAction(lActionType,lType,strName,strValue){
	if(lActionType==9){
		var objAIP=document.getElementById("HWPostil1");
//		if(strName=="Page1.PredefineSignArea"){
//				var phonearea = objAIP.GetValueEx("Page1.PhoneWriteArea",12,"",0,""); 
//				if(phonearea==""){ 
//					objAIP.CurrAction=1;  
//					objAIP.JSValue = 0; 
//					objAIP.ShowFullScreen = 0; 
//				}else{ 
//					objAIP.CurrAction=1;  
//					objAIP.JSValue = 0; 
//					objAIP.SetValue("Page1.PhoneWriteArea",":PROP:ACTIVATE:1"); 
//				} 
//			}else{ 
//				objAIP.CurrAction=1;  
//				objAIP.JSValue = 0; 
//				objAIP.ShowFullScreen = 0; 
//			} 
			objAIP.CurrAction=1;
			objAIP.JSValue = 0;
			objAIP.ShowFullScreen = 0;
	}  
} 

