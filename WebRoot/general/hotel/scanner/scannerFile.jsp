<%@page contentType="text/html;charset=utf-8"%>
<html>
<%
		String path = request.getContextPath();

		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
<HEAD>
<TITLE>扫描上传</TITLE>

<SCRIPT LANGUAGE="javascript">
	
	
	var mulu = "C:\\CloudCache\\Images\\";//图片文件存放路径，使用此路径加随机字符串
	var compareMulu = "C:\\CloudCache\\Images\\";//待比较的目录路径，用于最后删除文件夹时做比较
	var txtPath = "";
	var jpgPath = "";
	var filedatas = "";
	var url = "<%=basePath%>";
	var user_id="${current_user.user_id}";
	var dept_no="${current_user.dept_no}";
	
	function scanFiles(){
			try{
				var WTwainObj = document.getElementById("WTwain_ActiveX");
				var TH_OCRObj=document.getElementById("TH_OCR2013");
				var AipObj=document.getElementById("HWPostil1");
				
				CtrlStart();//启动扫描
				
				AipObj.CloseDoc(0);//关闭文档
				mulu = compareMulu;
				filedatas = "";
				txtPath = "";
				jpgPath = "";
		//		var sourcrname = WTwainObj.WTGetSourceName();
		//		alert("扫描仪名称:"+sourcrname);
		//		if(sourcrname==""){
		//			alert("没有连接扫描仪，请检查!");
		//			CtrlEnd();
		//			return false;
		//		}
		
		//		var rrr = WTwainObj.WTSelectSourceEx(0, sourcrname);
		//		if(rrr != 0){
		//			alert("设置扫描仪错误: "+rrr);
		//			CtrlEnd();
		//			return false;
		//		}
				
				var	eee = WTwainObj.WTSelectSource(0);
				if(eee!=0){
					alert("选择扫描仪出错："+eee);
					CtrlEnd();
					return false;
				}
		
				mulu += generateMixed(20)+"\\";
				var cret = AipObj.CreateFolder(mulu);
				if(cret<0){
					alert("创建路径失败，请检查'C:/CloudCache/'这个路径是否存在!");
					CtrlEnd();
					return false;
				}
				var filenames = "";
				var nCount = 0;
				var bFlag = true;
			  while(bFlag)
				{
				   nCount++;
			     var strTemp1 = mulu;
			     var strCount = format(nCount, "0000");
			     strTemp1 += strCount;
			     strTemp1 += ".jpg";
			     var nErr = WTwainObj.WTTwainScanImage(strTemp1);
				   if (nErr != 0)
				   {
				   		if(nErr==11){
				   			alert("扫描仪内没有纸,"+nErr);
				   		}else{
				   			alert("扫描失败，返回值："+nErr);
				   		}
				   		CtrlEnd();
					    return false;
					 }
			     bFlag = WTwainObj.WTIsHavePaper();
			     if(nCount==1){
							AipObj.LoadFile(strTemp1);//第一次是打开文件
			     }else{
			     		var pageCount = AipObj.PageCount;
			     		AipObj.MergeFile(pageCount,strTemp1);//其他是合并文件
			     }
			  }
			  CtrlEnd();
			  jpgPath = mulu + "all.jpg";
				AipObj.SaveTo(jpgPath,"jpg",0);
				
				txtPath = mulu + "all.txt";
				var ret = TH_OCRObj.TH_Start(0);
				ret = TH_OCRObj.TH_LoadImage(jpgPath, 24576);
				if(ret != 0){
					alert("OCR加载文件错误: "+ret);
					return;
				}
				ret = TH_OCRObj.TH_SetLanguage(0);
				if(ret != 0){
					alert("OCR设置语言错误: "+ret);
					return;
				}
				ret = TH_OCRObj.TH_OutputBegin(txtPath, 0, 0);
				if(ret != 0){
					alert("OCR识别错误: "+ret);
					return;
				}
				ret = TH_OCRObj.TH_Recognize();
					if(ret != 0)
				{
					alert("错误: TH_Recognize");
					return;
				}
				ret = TH_OCRObj.TH_OutPutFile();
				if(ret != 0)
				{
					alert("错误: TH_OutPutFile");
					return;
				}
				ret = TH_OCRObj.TH_OutputEnd();
				if(ret != 0)
				{
					alert("错误: TH_OutputEnd");
					return;
				}
				TH_OCRObj.TH_End();
			}catch(e) {
			  alert("扫描插件没有安装，请安装扫描仪插件!" );
			}
		
	}

	function uploadFiles(){
		if(txtPath==""){
			alert("请先扫描，再上传!");
			return false;
		}
		var AipObj2=document.getElementById("HWPostil2");
		AipObj2.CloseDoc(0);//关闭文档
		AipObj2.LoadFile(txtPath);
		filedatas = AipObj2.GetDocText();
		var re = /\s/g; 
  		filedatas=filedatas.replace(re,' '); 
		AipObj2.CloseDoc(0);//关闭文档
		alert(filedatas);
		
		var AipObj=document.getElementById("HWPostil1");
		AipObj.HttpInit();
		AipObj.HttpAddPostString("UID",user_id); //设置上传变量文件夹。
		AipObj.HttpAddPostString("deptNo",dept_no); //设置上传变量文件名。
		AipObj.HttpAddPostString("context",filedatas);
	    AipObj.HttpAddPostCurrFile("CurrrentFile");
	    alert(url);
	    AipObj.HttpPost(url+"/Seal/general/interface/uploadscanner.jsp");
	    deleteFolder();
	}
	
function  CtrlStart()
{
	var WTwainObj = document.getElementById("WTwain_ActiveX");
	WTwainObj.WTSetUerID("1007"); //如果是文件加密，需要先调用此函数设置文件加密
	WTwainObj.WTTwainScanStart(0);
}

function  CtrlEnd()
{
	var WTwainObj = document.getElementById("WTwain_ActiveX");
  WTwainObj.WTTwainScanEnd();
}


function deleteFolder(){
	var AipObj=document.getElementById("HWPostil2");
	if(mulu!=compareMulu){
			AipObj.DeleteFolder(mulu);
	}
}

//读文件     
function readFile(filename){
 var fso = new ActiveXObject("Scripting.FileSystemObject");
 var f = fso.OpenTextFile(filename,1);
 var s = "";
  	alert(f.ReadLine());
 while (!f.AtEndOfStream){
 s += f.ReadLine();
}
 f.Close();
 return s;
 }


var format = function (number, form) {
    var forms = form.split('.'), number = '' + number, numbers = number.split('.')
        , leftnumber = numbers[0].split('')
        , exec = function (lastMatch) {
            if (lastMatch == '0' || lastMatch == '#') {
                if (leftnumber.length) {
                    return leftnumber.pop();
                } else if (lastMatch == '0') {
                    return lastMatch;
                } else {
                    return '';
                }
            } else {
                return lastMatch;
            }
    }, string
    
    string = forms[0].split('').reverse().join('').replace(/./g, exec).split('').reverse().join('');
    string = leftnumber.join('') + string;
    
    if (forms[1] && forms[1].length) {
        leftnumber = (numbers[1] && numbers[1].length) ? numbers[1].split('').reverse() : [];
        string += '.' + forms[1].replace(/./g, exec);
    }
	return string.replace(/\.$/, '');
};


var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	
	function generateMixed(n) {
	     var res = "";
	     for(var i = 0; i < n ; i ++) {
	         var id = Math.ceil(Math.random()*35);
	         res += chars[id];
	     }
	     return res;
	}

function aip_init(){
	var obj=document.getElementById("HWPostil1");
	obj.ShowDefMenu = 0; //隐藏菜单
	obj.ShowToolBar = 0; //隐藏工具条
	obj.ShowScrollBarButton = 1;
	obj.InDesignMode = false;				//退出设计模式
	var vRet = obj.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
}

</SCRIPT>


<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			aip_init();
</SCRIPT>

</HEAD>
<BODY>
<table border="1" align="center">
<tr align="center">
  <td align="center">
<form  method="post" name=form1>
	<DIV STYLE="padding:10px; background-color:#eeeeee; border:2px solid #cccccc">
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="scanFiles();"  value="扫描"  ID="BtnStart">
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="uploadFiles();"  value="上传"  ID="BtnStart">
		<br><br>
		
			<OBJECT id=HWPostil1 align='middle' style='LEFT: 0px; WIDTH: 1000px; TOP: 0px; HEIGHT: 600px' classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561></OBJECT>
			<OBJECT id=HWPostil2 align='middle' style='LEFT: 0px; WIDTH: 0px; TOP: 0px; HEIGHT: 0px' classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561></OBJECT>
      <OBJECT ID= "WTwain_ActiveX" 
          CLASSID="CLSID:57B8AA4D-055E-4D14-B7DD-05B7F7A4FA66" 
          width=0 
          height=0>	
      </OBJECT>
    
      <OBJECT ID= "TH_OCR2013" 
          CLASSID="CLSID:6DB7C003-27CB-4198-BBC6-E6252FE5072D" 
          width=0 
          height=0>	
      </OBJECT>
	</DIV>
</form>
</td>
</tr>
</table> 
</BODY>
</html>




