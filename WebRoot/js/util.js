
function test1() {
	alert("\u6d4b\u8bd5\u4e00\u4e0b\u4e0b\uff01");
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
}

//得到页面元素
var $ = function (id) {
	return document.getElementById(id);
};

//得到页面元素
var $s = function (name) {
	return document.getElementsByName(name);
};

//得到页面元素
var $t = function (tag) {
	return document.getElementsByTagName(tag);
};

//得到页面表单元素类型为tp的元素集合
var $i = function (tp) {
	var objs = new Array();
	var inputs = $t("input");
	for (var i = 0; i < inputs.length; i = i + 1) {
		if (inputs[i].type == tp) {
			objs.push(inputs[i]);
		}
	}
	return objs;
};

//显示页面元素
function disp(id) {
	$(id).style.display = "block";
}
//隐藏页面元素
function hidden(id) {
	$(id).style.display = "none";
}
//清空页面元素值
function clean(id) {
	$(id).value = "";
}
//获取当前加密时间
function getDate() {
	var date = new Date();
	// (tm.wYear - 2006) * 3214080 + (tm.wMonth - 1) * 267840 + (tm.wDay - 1) *
	// 8640 + tm.wHour * 360 + tm.wMinute * 6 + tm.wSecond / 10;
	var ret = (date.getFullYear() - 2006) * 3214080 + (date.getMonth()) * 267840 + (date.getDate() - 1) * 8640 + date.getHours() * 360 + date.getMinutes() * 6 + Math.floor(date.getSeconds() / 10);
	return ret;
}
//反向解密时间
function formatDate(arg) {
	var year = Math.floor(arg / 3214080) + 2006;//年
	var yYear = arg % 3214080;//求年后的余数
	var month = Math.floor(yYear / 267840) + 1;
	var yMonth = yYear % 267840;
	var day = Math.floor(yMonth / 8640) + 1;
	var yDay = yMonth % 8640;
	var hour = Math.floor(yDay / 360);
	var yHour = yDay % 360;
	var minute = Math.floor(yHour / 6);
	var yMinute = yHour % 6;
	var secound = yMinute * 10;// 秒数舍去个位	
	var date_str = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + secound;
	return date_str;
}
//得到URL上的请求参数，相当于request.getPara...这个方法
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = location.search.substr(1).match(reg);
	if (r !== null) {
		return unescape(r[2]);
	}
	return null;
}
//响应回车按钮
function keydown(id) {
	var e = event.srcElement;
	if (event.keyCode == 13) {
		$(id).click();
		return false;
	}
}
//测试元素
function test(v) {
	var name = v.id;
	alert(name + "\u7684\u529f\u80fd\u8fd8\u6ca1\u505a\u5462\uff01");
}
//换图（鼠标位于其上）
function changeImgOver(bt) {
	$(bt.id).src = "images/buttons/" + bt.id + "2.png";
}
//换图（鼠标不在其上）
function changeImgOut(bt) {
	$(bt.id).src = "images/buttons/" + bt.id + ".png";
}
//控制n秒后页面跳转
function goBack(id, url) {
	var i = $(id).innerHTML;
	if (i > 1) {
		i = i - 1;
		$(id).innerText = i;
	} else {
		if (url == "") {
			close();
		} else {
			if (url == "-1") {
				history.go(-1);
			} else {
				location = url;
			}
		}
	}
	window.setTimeout("goBack('" + id + "','" + url + "')", 1000);
}
//清空下拉列表
function selectClean(sel) {
	var line = sel.length;
	for (var i = line - 1; i >= 0; i = i - 1) {
		sel.remove(i);
	}
}

//为下拉列表末尾添加新选项
function selectAdd(sel, v, t) {
	var op = document.createElement("option");
	if (t == null) {
		t = v;
	}
	op.value = v;
	op.text = t;
	try {
		sel.add(op, null); // standards compliant
	}
	catch (ex) {
		sel.add(op); // IE only
	}
}
//为下拉列表末尾删除指定选项
function selectDel(sel, no) {
	sel.remove(no);
}

//清空下拉列表中已经选中的项
function selectDelSel(sel) {
	var line = sel.length;
	for (var i = line - 1; i >= 0; i = i - 1) {
		if (sel[i].selected) {
			sel.remove(i);
		}
	}
}
function selectSum(sel) {
	var sum = 0;
	for (var i = 0; i < sel.length; i = i + 1) {
		sum += Number(sel[i].value);
	}
//	alert(sum);
	return sum;
}

//删除下拉列表2有的并且下拉列表1中选中的项
function selectDelSel2(sel1, sel2) {
	var line = sel1.length;
	for (var i = line - 1; i >= 0; i = i - 1) {
		if (sel1[i].selected & selectExist(sel2, sel1[i].value)) {
			sel1.remove(i);
		}
	}
}

//把下拉列表1中选择的项复制到下拉列表2中
function selectCope(sel1, sel2) {
	var line = sel1.length;
	for (var i = line - 1; i >= 0; i = i - 1) {
		if (sel1[i].selected & !selectExist(sel2, sel1[i].value)) {
			selectAdd(sel2, sel1[i].value, sel1[i].text);
		}
	}
}

//判断下拉列表中是否存在值
function selectExist(sel, v) {
	var line = sel.length;
	for (var i = line - 1; i >= 0; i = i - 1) {
		if (v == sel[i].value) {
			return true;
		}
	}
	return false;
}

//得到列表的所有值的字符串，以逗号相隔
function selectedValues(sel) {
	var str = "";
	for (var i = 0; i < sel.length; i = i + 1) {
		if (sel[i].selected) {
			str = str == "" ? str + sel[i].value : str + "," + sel[i].value;
		}
	}
	return str;
}

//得到列表的所有值的字符串，以逗号相隔
function selectValues(sel) {
	var str = "";
	for (var i = 0; i < sel.length; i = i + 1) {
		str = str == "" ? str + sel[i].value : str + "," + sel[i].value;
	}
	return str;
}


//得到列表的所有text的字符串，以逗号相隔
function selectTexts(sel) {
	var str = "";
	for (var i = 0; i < sel.length; i = i + 1) {
		str += sel[i].text + ",";
	}
	return str;
}

//弹出新窗口
function newWindow(url, width, height) {
	var myleft = (screen.availWidth - width) / 2;
	var mytop = (screen.availHeight - height) / 2;
	//showModalDialog
	//open
	window.open(url, "", "height=" + height + ",width=" + width + ",status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top=" + mytop + ",left=" + myleft + ",resizable=yes");
}
function newModalDialog(url, width, height, objId) {
	window.showModalDialog(url, objId, "status:no;center:yes;scroll:yes;resizable:yes;help:no;dialogWidth:" + width + "px;dialogHeight:" + height + "px");
}

//比较两个字符串先后
function strCompare(t1, t2) {
	if (t1 == t2) {
		return 0;
	} else {
		for (var i = 0; i < t1.length; i += 1) {
			if (t1.charAt(i) != t2.charAt(i)) {
				var ret = eval(t1.charAt(i) + "-" + t2.charAt(i));
				return ret;
			}
		}
	}
}

//全选
function checkAll(chk) {
	var chks = $s("chk");
	for (var i = 0; i < chks.length; i = i + 1) {
		chks[i].checked = chk.checked;
	}
}

//得到所有选中的checkbox的值，以逗号相隔
function checkStr(id) {
	var str = "";
	var chks = $s(id);
	for (var i = 0; i < chks.length; i = i + 1) {
		if (chks[i].checked) {
			str += chks[i].value + ",";
		}
	}
	return str;
}

//得到基本路径 
function baseUrl(num) {
//	alert(22);
	var vHref = location.href;
	var strs = vHref.split("/");
	var baseUrl = "";
	for (var i = 0; i < num; i = i + 1) {
		baseUrl += strs[i] + "/";
	}
	return baseUrl;
}
function basePath(a) {
	var vHref = location.href;
	var strs = vHref.split("/");
	var baseUrl = "";
	for (var i = a; i < strs.length - 1; i = i + 1) {
		baseUrl += strs[i] + "/";
	}
	return baseUrl;
}
function findPath(a) {
	var vHref = location.href;
	var strs = vHref.split("/");
	return strs[a];
}

//判断v元素在不在数据a里
function isIn(a, v) {
	for (var i = 0; i < a.length; i = i + 1) {
		if (v == a[i]) {
			return true;
		}
	}
	return false;
}
//判断v元素在数据a里第几个位置，不在为-1
function indexOfA(a, v) {
	for (var i = 0; i < a.length; i = i + 1) {
		if (v == a[i]) {
			return i;
		}
	}
	return -1;
}
//获得集合s1和s2的并集
function strBing(s1, s2) {
	var re = s2;
	var ss1 = s1.split(",");
	var ss2 = s2.split(",");
	for (var i = 0; i < ss1.length; i = i + 1) {
		if (!isIn(ss2, ss1[i]) && ss1[i] != "") {
			if (re == "") {
				re += ss1[i];
			} else {
				re += "," + ss1[i];
			}
		}
	}
	return re;
}
//获得集合s1减去s2的集合
function strJian(s1, s2) {
	var ss1 = s1.split(",");
	var ss2 = s2.split(",");
	for (var i = 0; i < ss2.length; i = i + 1) {
		var m = indexOfA(ss1, ss2[i]);
		if (m != -1) {
			ss1.splice(m, 1);
		}
	}
	return ss1.toString();
}
function randomStr(len, str) {
	if (typeof (str) == "undefined") {
		str = "abcdefghijklmnopqrstuvwxyz01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}
	var ret = "";
	for (var i = 0; i < len; i = i + 1) {
		var n = Math.floor(Math.random() * (str.length));
		ret += str.charAt(n);
	}
	return ret;
}

//解决IE8 C:fakePath问题的方法
//
//IE8，没想到一个网站里面的上传图片时用JavaScript预览本地图片的功能失效了，图片总是显示错误，用alert()看了下图片的路径居然变成了C:fakepath*.jpg，真实的路径被C:fakepath取代了，于是在网上开始找解决方案。原来是因为IE8增加了安全选项，默认情况下不显示上传文件的真实路径，进入internet选项，修改下设置即可显示真实的文件路径。
//　　以下就是操作步骤：
//
//工具 -> Internet选项 -> 安全 -> 自定义级别 -> 找到“其他”中的“将本地文件上载至服务器时包含本地目录路径”，选中“启用”即可。
//
//附带不用修改浏览器安全配置的javascript代码，兼容ie， firefox全系列
//
//参数obj为input file对象
function getPath(obj) {
	if (obj) {
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			obj.select();
			return document.selection.createRange().text;
		} else {
			if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
				if (obj.files) {
					return obj.files.item(0).getAsDataURL();
				}
				return obj.value;
			}
		}
		return obj.value;
	}
} 

//限制输入的长度,写在onkeydown事件里
function checkLength(obj, len) {
//		alert(event.keyCode);//8为<-backspce,46为delete
	var keys = new Array();
	keys.push(8);//<-backspce
	keys.push(46);//delete
	keys.push(37);//左
	keys.push(38);//右
	keys.push(39);//下
	keys.push(40);//上
	if (obj.value.length >= len && !keys.Contains(event.keyCode)) {
		obj.value=obj.value.substr(0,len);
		event.returnValue = false;
	}
}

Array.prototype.Contains=function (v){
	for(var i=0;i<this.length;i=i+1){
		if(this[i]==v)
			return true;
	}
	return false;
}

//解决表单输入特殊字符的限制
function checkStr(str, strName) {
	var regArray = new Array("\u25ce", "\u25a0", "\u25cf", "\u2116", "'", "\"", "?", ",", "{", "}", ":", ";", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "[", "]", "\uff1f", "~", "`", "<", ">", "\u2030", "\u2192", "\u2190", "\u2191", "\u2193", "\xa4", "\xa7", "\uff03", "\uff06", "\uff06", "\uff3c", "\u2261", "\u2260", "\u2248", "\u2208", "\u222a", "\u220f", "\u2211", "\u2227", "\u2228", "\u22a5", "\u2016", "\u2220", "\u2299", "\u224c", "\u221a", "\u221d", "\u221e", "\u222e", "\u222b", "\u226f", "\u226e", "\uff1e", "\u2265", "\u2264", "\u2260", "\xb1", "\uff0b", "\xf7", "\xd7", "/", "\u2161", "\u2160", "\u2162", "\u2163", "\u2164", "\u2165", "\u2166", "\u2167", "\u2169", "\u216b", "\u2544", "\u2545", "\u2547", "\u253b", "\u253b", "\u2507", "\u252d", "\u2537", "\u2526", "\u2523", "\u251d", "\u2524", "\u2537", "\u2537", "\u2539", "\u2549", "\u2547", "\u3010", "\u3011", "\u2460", "\u2461", "\u2462", "\u2463", "\u2464", "\u2465", "\u2466", "\u2467", "\u2468", "\u2469", "\u250c", "\u251c", "\u252c", "\u253c", "\u250d", "\u2515", "\u2517", "\u250f", "\u2505", "\u2014", "\u3016", "\u3017", "\u3013", "\u2606", "\xa7", "\u25a1", "\u2030", "\u25c7", "\uff3e", "\uff20", "\u25b3", "\u25b2", "\uff03", "\u2103", "\u203b", ".", "\u2248", "\uffe0");
	for (var i = 1; i <= regArray.length; i = i + 1) {
		if (str.indexOf(regArray[i]) != -1) {
//	    	alert(i+":"+regArray[i]);
			alert(strName + "\u4e0d\u53ef\u4ee5\u5305\u542b\uff1a\"" + regArray[i] + "\"\u3002");
			return false;
		}
	}
	return true;
}
function numJia(a) {
	var r = Math.floor(Math.random() * 100);
	return 367 * r + (a - 0);
}
function numJie(a) {
	return a % 367;
}

