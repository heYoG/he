
//鼠标移进
function gotfocus(id) {
	$(id).runtimeStyle.backgroundColor = "yellow";
}

//鼠标移出
function lostfocus(id) {
	if ($(id).rowIndex % 2 != 0) {
		$(id).runtimeStyle.backgroundColor = "#DAEFFF";
	} else {
		$(id).runtimeStyle.backgroundColor = "#dddeee";
	}
}

//清空表格
function tb_clear(tb) {
	for (var i = tb.rows.length - 1; i > 0; i = i - 1) {
		tb.deleteRow(i);
	}
}
var $ = function (id) {
	return document.getElementById(id);
};
//得到页面元素
var $s = function (name) {
	return document.getElementsByName(name);
};
/**
**	自定义表格工具类
**/
function QerTable(id) {
	this.id = id;
	this.border = 1;
	this.tb_class = "TableBlock";
	this.width = "100%";
	this.cellspacing = "0";
	this.cellpadding = "3";
	this.bordercolor = "#cccccc";
	this.tr_head_name = "tr_head";//表头名字
	this.tr_bgcolor = "#cccccc";//表头背景色
	this.cell_align = "center";//表格对齐方式
	this.row_color1 = "#D6E4cF";//数据行颜色一
	this.row_color2 = "#F4FBE1";//数据行颜色二
	this.focus_color = "yellow";//数据行颜色(焦点状态)
	this.select_color = "#96ACE5";//数据行颜色(选中状态)
	this.row_has_chk = false;//数据行是否有选择框
	this.row_chk_name = "ch";
	this.head_has_sort = false;//表头是否支持排序
	this.html = "";
}
/**
**	设置表格的ID
**/
QerTable.prototype.SetId = function (id) {
	this.id = id;
};
/**
**	得到表格的html代码
**/
QerTable.prototype.HtmlCode = function () {
	var str = "";
	str += "<table ";
	str += "id='" + this.id + "' ";
	str += "width='" + this.width + "' ";
	str += "border='" + this.border + "' ";
	str += "class='" + this.tb_class + "' ";
	str += "cellspacing='" + this.cellspacing + "' ";
	str += "cellpadding='" + this.cellpadding + "' ";
	str += "bordercolor='" + this.bordercolor + "' ";
	str += "></table>";
	this.html = str;
	return this.html;
};
/**
**	插入表头
**/
QerTable.prototype.SetHead = function (head) {
	var tr_head = $(this.id).insertRow(0);
	tr_head.id = this.tr_head_name;
	tr_head.align="center";
	tr_head.className="TableHeader";
	tr_head.runtimeStyle.backgroundColor = this.tr_bgcolor;
	if(this.head_has_sort){
		for(var i=0;i<head.length;i=i+1){
			head[i]="<a href='#' onclick=\"alert("+i+");\" >"+head[i]+"</a>";
		}
	}
	for (var i = 0; i < head.length; i = i + 1) {
		var newCell = tr_head.insertCell(i);
		if (i == 0 && this.row_has_chk) {
			var chk_str = "";
			chk_str += "<input name='" + this.row_chk_name + "' ";
			chk_str += "id='" + this.row_chk_name + "" + tr_head.id + "' ";
			chk_str += "type='checkbox' ";
			chk_str += "onclick=\"chk_all('" + this.row_chk_name + "','" + tr_head.id + "');\"";
			chk_str += "/>";
			newCell.innerHTML = chk_str + head[i];
		} else {
			newCell.innerHTML = head[i];
		}
	}
};
//全选，插入表头时可能用到的方法
function chk_all(chk_name, id) {
	var chk = $(chk_name + tr_head.id);
	var ch = $s(chk_name);
	for (var i = 1; i < ch.length; i = i + 1) {
		ch[i].checked = chk.checked;
		$(ch[i].id.substr(chk_name.length)).onmouseout();
	}
}
/**
**	插入数据行
**/
var flag = false;
QerTable.prototype.AddRow = function (row, id,index,b) {
	if (typeof (id) == "undefined") {
		id = row[0];
	}
	if (typeof (index) == "undefined") {
		index = "";
	}
	if (typeof (b) == "undefined") {
		b = true;
	}
	var tb = $(this.id);
	var len = tb.rows.length;
	var newTr = tb.insertRow(len);
	newTr.id = id;
	for (var i = 0; i < row.length; i = i + 1) {
		var newCell = newTr.insertCell(i);
		if (i == 0 && this.row_has_chk) {
			if(b){
				var chk_str = "";
				chk_str += index+"<input name='" + this.row_chk_name + "' ";
				chk_str += "id='" + this.row_chk_name + "" + newTr.id + "' ";
				chk_str += "type='checkbox' ";
				chk_str += "onclick=\"flag=true;\"";
				chk_str += "/>";
				newCell.innerHTML = chk_str + row[i];
			}else{
				newCell.innerHTML = index+" "+row[i];
			}
			newCell.align="left";
		} else {
			newCell.innerHTML = row[i];
			newCell.align = this.cell_align;
		}
	}
	var color = len % 2 != 0 ? this.row_color1 : this.row_color2;
	var focus_color = this.focus_color;
	var select_color = this.select_color;
	var chk_name = this.row_chk_name;
	var row_has_chk = this.row_has_chk;
	newTr.runtimeStyle.backgroundColor = color;
	newTr.onmouseover = function () {
		newTr.runtimeStyle.backgroundColor = focus_color;
	};
	newTr.onmouseout = function () {
		newTr.runtimeStyle.backgroundColor = (row_has_chk && b && $(chk_name + newTr.id).checked) ? select_color : color;
	};
	newTr.onclick = function () {
		if (row_has_chk && b && !flag) {
			$(chk_name + newTr.id).checked = !$(chk_name + newTr.id).checked;
		}
		flag = false;
	};
};
/**
**	清空表格
**/
QerTable.prototype.Clean = function () {
	var tb = $(this.id);
	var rs = tb.rows;
	for (var i = rs.length - 1; i > 0; i = i - 1) {
		tb.deleteRow(i);
	}
};
/**
**	得到选择的数据行主键集合
**/
QerTable.prototype.Selected = function () {
	var res = new Array();
	if (this.row_has_chk) {
		var tb = $(this.id);
		var rs = tb.rows;
		for (var i = rs.length - 1; i > 0; i = i - 1) {
			if ($(this.row_chk_name + rs[i].id).checked) {
				res.push(rs[i].id);
			}
		}
	}
	return res.reverse();
};
/**
**	排序
**/
QerTable.prototype.Sort = function () {
	var tb = $(this.id);
	var rs = tb.rows;
	var rows=new Array();
	for(var i=1;i<rs.length;i=i+1){
		rows.push(rs[i]);
	}
	this.Clean();
	alert(rows[0].id);
	rows.sort();
	alert(rows[0].id);
	tb.rows=rows;
};

