var obj=new QerTable("tb_list");
var page=1;
var pageSize=10;
var cdt_s="";
var cdt_f={};
var thisUrl=baseUrl(3)+"Seal/general/verify/verifyCodebar.jsp";
var prnStatus=new Map();

function EnableComCtrl_onclick(iIsEnable){
//alert(iIsEnable);
	try{ 
		var obj;
		obj = $("BarCodeReadCtrl1");
//		alert(obj);
		if(obj){
			if(iIsEnable==1){
				obj.EnableComCtrl(0);
				obj.Baud=$("Baud").value;
				obj.ComNo=$("ComNo").value;
			}
			var e=obj.EnableComCtrl(iIsEnable);
			if(e==1){
				if(iIsEnable==1){
					alert("启动监听成功!");
				}else{
					alert("停止监听成功!");
				}
			}else{
				alert("失败!code="+e);
			}
		}
	}catch(e){
		alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
		return -1;
	}
}

/**
扫描二维码，信息改变事件
**/
function bt_changed(){
	var obj = $("BarCodeReadCtrl1");
//	alert(obj.BarcodeText);
	moniScan(obj.BarcodeText);
}

function cb_barv(d){
	if(d){
//		alert(d);
		$("result").value=d;
		if(d=="s"){
			document.getElementById("td_signRusult").innerHTML="<font color='green' size='5'>验证通过!</font>";
		}else{
			document.getElementById("td_signRusult").innerHTML="<font color='red' size='5'>证书验证失败!</font>";
		}
	}
}

function fullS(){
	var obj=$("HWPostil1");
	obj.ShowFullScreen = 1;
}

function moniScan(text){
//var obj=$("BarCodeReadCtrl1");
//bt_changed();
//return;
	var str="DI=1231,123,123,141;CT=测试123456789!@#$%^&*abcdef/,./lkj[]\\=-+_<~>;DT=2011-08-24 18:51:54;CE=6140 6663 0000 0000 02D5";
	if(typeof (text) == "undefined"){
//		str="TM=2011.08.26 16:59;DI=5;CT=张华测试对外公文流程2011082686ITOR20110826000203张华2011年08月26日16时27分;CE=6140 6663 0000 0000 02D5";
		str="";
	}else{
		str=text;
	}
	$("PrintTime").value="";
	$("PrintName").value="";
	$("CertSerial").value="";
	$("PrintContent").value=str;
	var ss = str.split(";");
	for(var i=0;i<ss.length;i=i+1){
		var begin=ss[i].substr(0,3);
		if(begin=="TM="){
			$("PrintTime").value=ss[i].substr(3);
		}
		if(begin=="DI="){
			$("PrintName").value=ss[i].substr(3);
		}
		if(begin=="CE="){
			$("CertSerial").value=ss[i].substr(3);
		}
	}
	var obj;
	obj = $("BarCodeReadCtrl1");
	var certdata = "STRDATA:MIIEozCCA4ugAwIBAgIKYZqMJQAAAAAAgTANBgkqhkiG9w0BAQUFADAjMSEwHwYDVQQDDBjmlbDlrZfor4HkuaborqTor4HkuK3lv4MwIBcNMDkxMDE5MDQxMzE5WhgPMjIwOTA3MDgxMDMwMjVaMDkxDzANBgNVBAoMBua1i+ivlTEPMA0GA1UECwwG5rWL6K+VMRUwEwYDVQQDDAzmtYvor5XkuJPnlKgwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKkkp60o6k0FkXVToFeoY5wu+W8JgCxCaF2UQ7FyBBM7rdfBi4GBccIkF88pdDqpSgdxCL+G4X9nU7PrmJpkMicnMxP+l1OlpRXAaCOlicLonnsWPEXgzfamW//P8DgLbiyEX7izSDzqqHps/masJuEa2iSPUDEozy6y+LJLiPtJAgMBAAGjggJDMIICPzAOBgNVHQ8BAf8EBAMCBPAwRAYJKoZIhvcNAQkPBDcwNTAOBggqhkiG9w0DAgICAIAwDgYIKoZIhvcNAwQCAgCAMAcGBSsOAwIHMAoGCCqGSIb3DQMHMB0GA1UdDgQWBBRAi1pON1FWWqwSWcx6U2THoO8lVDATBgNVHSUEDDAKBggrBgEFBQcDAjAfBgNVHSMEGDAWgBQrN7rvSu+GZLzra+nAxaY6dcDLcjCBsAYDVR0fBIGoMIGlMIGioIGfoIGchktodHRwOi8vYWRtaW4tc2VydmVyL0NlcnRFbnJvbGwvITY1NzAhNWI1NyE4YmMxITRlNjYhOGJhNCE4YmMxITRlMmQhNWZjMy5jcmyGTWZpbGU6Ly9cXEFkbWluLVNlcnZlclxDZXJ0RW5yb2xsXCE2NTcwITViNTchOGJjMSE0ZTY2IThiYTQhOGJjMSE0ZTJkITVmYzMuY3JsMIHeBggrBgEFBQcBAQSB0TCBzjBkBggrBgEFBQcwAoZYaHR0cDovL2FkbWluLXNlcnZlci9DZXJ0RW5yb2xsL0FkbWluLVNlcnZlcl8hNjU3MCE1YjU3IThiYzEhNGU2NiE4YmE0IThiYzEhNGUyZCE1ZmMzLmNydDBmBggrBgEFBQcwAoZaZmlsZTovL1xcQWRtaW4tU2VydmVyXENlcnRFbnJvbGxcQWRtaW4tU2VydmVyXyE2NTcwITViNTchOGJjMSE0ZTY2IThiYTQhOGJjMSE0ZTJkITVmYzMuY3J0MA0GCSqGSIb3DQEBBQUAA4IBAQBk8M5fQk1ksHcY5TUJ6K+fq4Acu2QgHlMTpSMaOTfF+bh0FCCj1eTnbyRACSstyJiNp/+XYKe89VzniTD4AZtTJEPrZrQIFIS6vXpBOkQ3GwT2VbkptFuGbV6as1sDcjL+TPFw9vMylqoM8bjKqU8cJH4AegxfTJ96tzfl7G3CG4rbXdAEzvDMijUxsudNlnSx7/XZPtQrqYAZAfNOqoDtxR9aKMmYd9hS/ubQ5zbDXrFWHhAY6O15aHsiBBuPFX7Puo+KwYsRDizr1HJc3QHQF4o4IdIylDIBQpPp6wUyNLYNx6ndimVUeqKZG7C8dilklzgsBwl73qadR6jF6AAO";
	if($("PrintTime").value==""||$("CertSerial").value==""){
		cb_barv("f");
	}else if(obj.VerifySign(certdata)==0){
		cb_barv("s");
	}else{
		cb_barv("f");
	}
}

