function selectAll(obj) {
	var inps = document.getElementsByTagName("input");
	if("0" == obj.value) {
		for(var i = 0; i < inps.length; i++ ) {
			if("checkbox" == inps[i].type) {
				inps[i].checked=true;
			}
		}
		obj.value="1";
	}else {
		for(var i = 0; i < inps.length; i++ ) {
			if("checkbox" == inps[i].type) {
				inps[i].checked=false;
			}
		}
		obj.value="0";
	}
}
/*操作成功跳转*/
var i=3;
function goBack(){
	if(i>0){
		i--;
		document.getElementById("time").innerText=i;
		document.getElementById("info").style.color="#336699";
	}else{
		document.getElementById("info").style.color="#ff0000";
		window.close();
	}
	window.setTimeout("goBack()", 1000);
}
