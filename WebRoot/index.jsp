<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
//获取项目总路径 path: /TestBD  basePath: http://localhost:7008/TestBD/ 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath:"+basePath);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
 
    <title>服务端盖章测试页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function doSubmit2(opr)
{
 window.location.href='01.jsp';
}
	//提交触发事件 
function doSubmit(opr){         
    var obj=document.getElementById("yewudata").value;
    if((obj=="")||(obj==null)){
    alert("XML内容不能为空");
    return false;
    }
    var address=document.getElementById("address").value;
    if((address=="")||(address==null)){
    alert("IP地址不能为空");
    return false;
    }else{
    var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
    flag_ip=pattern.test(address);
    if(!flag_ip){ 
    alert("请输入正确的IP地址!");  
    return false;
    }
    }
    var port=document.getElementById("port").value;
    if((port=="")||(port==null)){
    alert("端口号不能为空");
    return false;
    }
	document.getElementById("hc").value=obj;
    document.forms[0].action += "?operate=" + opr;
	document.forms[0].submit();	 
	document.forms[0].action="show.jsp";
}

//获取xml内容
function sel_file(){
var fso;
var tf;
var s="";
var tfile=document.getElementById("tfile").value; //获取xml路径
fso = new ActiveXObject("Scripting.FileSystemObject"); //创建一个可以将文件翻译成文件流的对象。

tf=fso.OpenTextFile(tfile,1); // 用于创建一个textStream 对象
// fso.setRequestHeader("Content-Type","utf-8"); //设置读取文件的编码方式
while (!tf.AtEndOfStream)//判断是否读取到最后一行
{
s+=tf.ReadLine()+"\r";
}
document.getElementById("yewudata").value=s; //获取xml内容
tf.close(); //关闭textStream 对象
}

/*提交*/
function doSubmit2(){
var docName=document.getElementById("docName").value;
window.location.href="http://127.0.0.1:8080/Seal/docs/download3.jsp?name="+docName+".pdf";
}
</script>
 </head>
  <body>
   <form action="show.jsp" method="post" name="Regsiter" target="_blank">
   <table border="0"  align="center">
  <tr>
	<td>
	    <input type="hidden" name="hc">
		<textarea rows="24"  name="yewudata" cols="87" ></textarea>
	</td>
  </tr> 
   <tr>
	<td>
	    IP：<input type="text" name="address" value="127.0.0.1" /><font color=red>*</font>
	    端口：<input type="text" name="port" value="8080" /><font color=red>*</font>
	</td>
  </tr> 
  <tr>
	<td>
 <input name="button" width="500px" height="500px" type="button" value="PDF签章" onclick="doSubmit('sealpdf');" />
 <input name="button" width="500px" height="500px" type="button" value="模板合成" onclick="doSubmit('megerpdf');" />
 <input name="button" width="500px" height="500px" type="button" value="PDF验证" onclick="doSubmit('varitypdf');" />
 <input name="button" width="500px" height="500px" type="button" value="HTML5文档转化" onclick="doSubmit('html5syn');" />
 <input name="button" width="500px" height="500px" type="button" value="HTML5文档保存" onclick="doSubmit('html5save');" />

   <!--
    <input name="button" width="500px" height="500px" type="button" value="http接口盖章" onclick="doSubmit('httpsealpdf');" />
   <input name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
    <input  name="button" width="500px" height="500px" type="button" value="测试签章" onclick="doSubmit('docSeal');" />
  <input  name="button" width="500px" height="500px" type="button" value="doc转换pdf" onclick="doSubmit('dowdzh');" />
  <input name="button" width="500px" height="500px" type="button" value="天威盖章" onclick="doSubmit('pdfSealN1');" />
  <input name="button" width="500px" height="500px" type="button" value="CFCA盖章" onclick="doSubmit('pdfSeal');" />
  <input name="button" width="500px" height="500px" type="button" value="moban盖章" onclick="doSubmit('docSeal');" />
  <input  name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
   
   
  <input  name="button" width="500px" height="500px" type="button" value="moban盖章" onclick="doSubmit('docSeal');" />
  <input  name="button" width="500px" height="500px" type="button" value="测试签章" onclick="doSubmit('docSeal');" />
  <input  name="button" width="500px" height="500px" type="button" value="CFCA" onclick="doSubmit('pdfSeal');" />
  <input  name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
	 <input name="button" width="500px" height="500px" type="button" value="电子商务盖章" onclick="doSubmit('dzswgz');" />
	 <input  name="button" width="500px" height="500px" type="button" value="下载盖章后的文档" onclick="doSubmit2('downPdf');" />
		    <input class="BigInput" type="file" name="tfile" id="tfile"  onchange="sel_file();">
	    <input  name="button" width="500px" height="500px" type="button" value="Linux签章" onclick="doSubmit('linuxseal');" />
 <input  name="button" width="500px" height="500px" type="button" value="测试签章" onclick="doSubmit('docSeal');" />
 <input  name="button" width="500px" height="500px" type="button" value="moban盖章" onclick="doSubmit('docSeal');" />
 <input  name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
 <input  name="button" width="500px" height="500px" type="button" value="CFCA" onclick="doSubmit('pdfSeal');" />
	<input name="button" width="500px" height="500px" type="button" value="投保文档盖章" onclick="doSubmit('dogz');" />
    <input name="button" width="500px" height="500px" type="button" value="电子商务盖章" onclick="doSubmit('dzswgz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
	<input name="button" width="500px" height="500px" type="button" value="其他业务盖章" onclick="doSubmit('docSeal');" />
	<input name="button" width="500px" height="500px" type="button" value="文档下载" onclick="doSubmit('dowdxz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
	<input name="button" width="500px" height="500px" type="button" value="HT文档盖章" onclick="doSubmit('docSeal');" />
	<input name="button" width="500px" height="500px" type="button" value="HT文档生成" onclick="doSubmit('makedoc');" />
	<input name="button" width="500px" height="500px" type="button" value="HT补打文档" onclick="doSubmit('toprint');" />
	<input name="button" width="500px" height="500px" type="button" value="用已有的Pdf签章" onclick="doSubmit('seallist');" />
		<input name="button" width="500px" height="500px" type="button" value="文档生成" onclick="doSubmit('dosc');" />
	<input name="button" width="500px" height="500px" type="button" value="文档盖章" onclick="doSubmit('dogz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档生成" onclick="doSubmit('dosc');" />
	<input name="button" width="500px" height="500px" type="button" value="文档盖章" onclick="doSubmit('dogz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档验证" onclick="doSubmit('doyz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档下载" onclick="doSubmit('dowdxz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档下载" onclick="doSubmit('dowdxz');" />

	<input name="button" width="500px" height="500px" type="button" value="盖章状态查询" onclick="doSubmit('sealStatus');" />
	<input name="button" width="500px" height="500px" type="button" value="印章列表" onclick="doSubmit('seallist');" />
	<input name="button" width="500px" height="500px" type="button" value="文档转换" onclick="doSubmit('dowdzh');" />
	<input name="button" width="500px" height="500px" type="button" value="文档盖章" onclick="doSubmit('dogz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档下载" onclick="doSubmit('dowdxz');" />
	<input name="button" width="500px" height="500px" type="button" value="文档下载" onclick="doSubmit('dowdxz');" />
	<!--<input name="button" width="500px" height="500px" type="button" value="只盖章" onclick="doSubmit('zhigz');" />-->
	</td>
  </tr>
</table>
	</form>
  </body>
</html>
