function aip_init(){
	var obj=$("HWPostil1");
	obj.ShowDefMenu = 0; //隐藏菜单
	obj.ShowToolBar = 0; //隐藏工具条
	obj.ShowScrollBarButton = 1;
	obj.InDesignMode = false;				//退出设计模式
	var vRet = obj.Login("sys_admin", 5, 32767, "", "");
//	addField();
}

function aip_init2(){
	var obj=$("HWPostil2");
	obj.ShowDefMenu = 0; //隐藏菜单
	obj.ShowToolBar = 0; //隐藏工具条
	obj.ShowScrollBarButton = 1;
//	addField();
}

function addField()
{
	var obj = $("HWPostil1");
    var vRet = obj.Login("sys_admin", 5, 32767, "", "");
    if(0 == vRet){
	    obj.InDesignMode = true;
	}
}

function NotifyLineAction(lPage,lStartPos,lEndPos)
{
    var lStartY = (lStartPos>>16)& 0x0000ffff;
	var lStartX = ((lStartPos<<16)>>16) & 0x0000ffff; 

	var lEndY = (lEndPos>>16)& 0x0000ffff;
	var lEndX = ((lEndPos<<16)>>16) & 0x0000ffff; 
//	alert(lStartX);
//	alert(lStartY);
	ShowDialog("setFrm");
    argObj = {"page":lPage,"StartX":lStartX,"StartY":lStartY,"EndX":lEndX,"EndY":lEndY};
}

function ocxPostDoc(ocx,url,name,path){
	url=baseUrl(3)+url;
	ocx.HttpInit();
	ocx.HttpAddPostString("path",path);
	ocx.HttpAddPostString("name",name);
	ocx.HttpAddPostCurrFile("FileContent");
	var p=ocx.HttpPost(url);
	return p;
}

function ocxPostDoc2(ocx,url,name,path){
	url=baseUrl(3)+url;
	ocx.HttpInit();
	ocx.HttpAddPostString("path",path);
	ocx.HttpAddPostString("name",name);
	var tempUrl="c:/temp.pdf";
	ocx.SaveTo(tempUrl,"pdf",0);
	ocx.HttpAddPostFile("FileContent",tempUrl);
	var p=ocx.HttpPost(url);
	return p;
}

function ocxOpen(url){
	var ocx=$("HWPostil1");
	ocx.CloseDoc(0);
//	ocx.LoadFile(url);
	ocx.LoadFileEx(url,"tpl",0,0);
}

var argObj = null;

function NotifyLineAction2(lPage,lStartPos,lEndPos)
{
	try{
		var lStartY = (lStartPos>>16)& 0x0000ffff;
		var lStartX = ((lStartPos<<16)>>16) & 0x0000ffff; 
		var lEndY = (lEndPos>>16)& 0x0000ffff;
		var lEndX = ((lEndPos<<16)>>16) & 0x0000ffff; 
		//alert(lStartX);alert(lStartY);
		ShowDialog2('setFrm');
	  	argObj = {"page":lPage,"StartX":lStartX,"StartY":lStartY,"EndX":lEndX,"EndY":lEndY};
	  //alert(argObj);
  	}catch(e) {
		alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);		
	}
}

function setField(field_name,field_type,font_type,font_size,font_color,border_type,font_x,font_y,IS_READ_ONLY,TIPSINFO,ITEM_DATA_TYPE)
{
	try{
		HideDialog2('setFrm');
		if(argObj == null){
		 	return;
		}
		var obj = $_("HWPostil1");
		var field_width = argObj.EndX - argObj.StartX;
		var field_height = argObj.EndY - argObj.StartY;	
		if(field_type==3){
		var vRet = obj.InsertNote(field_name,argObj.page,field_type,argObj.StartX,argObj.StartY,field_width,field_height);
		if(vRet=="")
		{
			alert("此字段映射已经添加！");
			ShowDialog('setFrm');
			return;
		}
			argObj.page=parseInt(argObj.page)+1;
			
			var new_field_type_str = "";
			if(ITEM_DATA_TYPE=="文本"){
				new_field_type_str = "0";
			}else if(ITEM_DATA_TYPE=="密码"){
				new_field_type_str = "1";
			}else if(ITEM_DATA_TYPE=="日期"){
				new_field_type_str = "2";
			}else if(ITEM_DATA_TYPE=="整数"){
				new_field_type_str = "3";
			}else if(ITEM_DATA_TYPE=="小数"){
				new_field_type_str = "4";
			}else if(ITEM_DATA_TYPE=="大写金额"){
				new_field_type_str = "5";
			}else if(ITEM_DATA_TYPE=="图片"){
				new_field_type_str = "6";
			}else if(ITEM_DATA_TYPE=="印章"){
				new_field_type_str = "7";
			}
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:FACENAME:"+font_type);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:FONTSIZE:"+font_size);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:FRONTCOLOR:"+font_color);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:BORDER:"+border_type);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:HALIGN:"+font_x);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:VALIGN:"+font_y);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:CONTENTTYPE:"+new_field_type_str);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP::LABEL:"+IS_READ_ONLY);
			obj.SetValue("Page"+argObj.page+"."+field_name,":PROP:TIPSINFO:"+TIPSINFO);
		}else if(field_type==2){
			var vRet = obj.InsertNote("PredefineSignArea",argObj.page,field_type,argObj.StartX,argObj.StartY,field_width,field_height);
			if(vRet=="")
			{
				alert("此字段映射已经添加！");
				ShowDialog2('setFrm');
				return;
			}
		}
		argObj = null;
	}catch(e) {
		alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
	}
}

function ShowDialog2(id)
{
	$_(id).style.display = 'block';
	var bb=(document.compatMode && document.compatMode!="BackCompat") ? document.documentElement : document.body;
	$_(id).style.left = ((bb.offsetWidth - $_(id).offsetWidth)/2)+"px";
	$_(id).style.top  = (90 + bb.scrollTop)+"px";
}

var $_ = function(id) {return document.getElementById(id);}
