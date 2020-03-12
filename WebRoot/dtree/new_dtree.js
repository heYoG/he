var strSource ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; 
/*把字符类型转换成数字类型*/
function change(str){
	var re=0;
	var j=1;
	for(var i=str.length-1;i>=0;i=i-1){
		re=re+strSource.indexOf(str.charAt(i))*j;
		j=j*100;
	}
	return re;
}

//function change2(num){
//	var strSource ="0123456789abcdefghijklmnopqrstuvwxyz"; 
//	var s1=num/100;
//	var s2=num%100;
//	var str=strSource.charAt(s1)+strSource.charAt(s2);
//	return str;
//}

function change2(num){
	var s1=num/1000000;
	var y1=num%1000000;
	var s2=y1/10000;
	var y2=y1%10000;
	var s3=y2/100;
	var y3=y2%100;
	var s4=y3/1;
	var str=strSource.charAt(s1)+strSource.charAt(s2)+strSource.charAt(s3)+strSource.charAt(s4);
	return str;
}

function QerTree(id){
	this.id=id;
	this.count=0;
	this.clk_id=0;
	this.clk_status=1;
	this.trees=new Array();
}

QerTree.prototype.getClkId=function (){
	return this.clk_id;
};

QerTree.prototype.setClkId= function (clk_id){
	if(this.clk_id==clk_id&this.clk_status!==0){
		this.clk_id=clk_id;
		this.clk_status=0;
		return false;
	}else{
		this.clk_id=clk_id;
		this.clk_status=1;
		return true;
	}
};

QerTree.prototype.clear=function (d){
	this.count=d.length;
	this.trees=new Array();
};

QerTree.prototype.closeAll= function (){
	for(var i=0;i<this.trees.length;i=i+1){
		this.trees[i].closeAll();
	}
};

QerTree.prototype.openAll= function (){
	for(var i=0;i<this.trees.length;i=i+1){
		this.trees[i].openAll();
	}
};

QerTree.prototype.toString = function (){
	var str="";
	for(var i=0;i<this.trees.length;i=i+1){
		str+=this.trees[i].toString();
	}
	return str;
};

/**
* id 节点编号
* pid 父节点编号
* name 节点名称
* url 节点链接
* title 节点显示标题
* target 链接访问的目标页面名称
* onclick 单击事件
* icon 图标路径
* iconOpen 有子节点图标路径
* open 是否打开
**/
QerTree.prototype.add = function (id, pid, name, url, title, target, onclick, icon, iconOpen, open){
//	id=change(id);
//	pid=change(pid);
	var index=this.findTreeIndex(pid);
	if(index!=-1){
		this.trees[index].add(id, pid, name, url, title, target, onclick, icon, iconOpen, open);
	}else{
		var tree=new dTree("d"+id,this);
		tree.add(id, -1, name, url, title, target, onclick, icon, iconOpen, open);
		this.trees.push(tree);
	}
};

QerTree.prototype.findTree = function (id){
	for(var i=0;i<this.trees.length;i=i+1){
		if(this.trees[i].obj==id){
			return this.trees[i];
		}
	}
	return null;
};

QerTree.prototype.findTreeIndex = function (id) {
	for(var i=0;i<this.trees.length;i=i+1){
		if(this.trees[i].isExist(id)){
			return i;
		}
	}
	return -1;
};

function Node(id, pid, name, url, title, target, onclick, hc, icon, iconOpen, open) {
	this.id = id;
	this.pid = pid;
	this.name = name;
	this.url = url;
	this.title = title;
	this.target = target;
	this.onclick = onclick;
	this.icon = icon;
	this.iconOpen = iconOpen;
	this._io = true || false;//节点是否打开
	this._is = false;//是否被选中
	this._ls = false;//是否同父节点中最后一个节点 last sibling
	this._hc = true;//是否有子节点 has any children
	this._ai = 0;//节点在节点列表中的排序号
	this._p=null;//父节点对象
}

// Tree object

function dTree(objName,pName) {
	this.config = {
		target : null,
		folderLinks	: true,
		useSelection : true,
		useCookies : true,
		useLines : false,//是否使用线条图片
		useIcons : true,//是否使用图标
		useStatusText : false,
		closeSameLevel : false,
		inOrder : true//是否有顺序
	};

	this.icon = {
		root : '/Seal/dtree/img/linkman.gif',
		folder : '/Seal/dtree/img/jiantou2.gif',
		folderOpen : '/Seal/dtree/img/jiantou1.gif',
		node : '/Seal/dtree/img/jiantou1.gif',
		empty : '/Seal/dtree/img/empty.gif',
		line : '/Seal/dtree/img/line.gif',
		join : '/Seal/dtree/img/join.gif',
		joinBottom : '/Seal/dtree/img/joinbottom.gif',
		plus : '/Seal/dtree/img/plus.gif',
		plusBottom : '/Seal/dtree/img/plusbottom.gif',
		minus : '/Seal/dtree/img/minus.gif',
		minusBottom	: '/Seal/dtree/img/minusbottom.gif',
		nlPlus : '/Seal/dtree/img/nolines_plus.gif',
		nlMinus : '/Seal/dtree/img/nolines_minus.gif'
	};
	this.parent=pName;
	this.obj = objName;
	this.aNodes = [];
	this.aIndent = [];
	this.root = new Node(-1);
	this.selectedNode = null;
	this.selectedFound = false;
	this.completed = false;
}

// Adds a new node to the node array

dTree.prototype.add = function(id, pid, name, url, title, target, onclick, icon, iconOpen, open) {
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, target, onclick, icon, iconOpen, open);
};

// Open/close all nodes

dTree.prototype.openAll = function() {
	this.oAll(true);
};

dTree.prototype.closeAll = function() {
	this.oAll(false);
};

dTree.prototype.isExist = function(id) {
	for(var i=0;i<this.aNodes.length;i=i+1){
		if(this.aNodes[i].id==id){
			return true;
		}
	}
	return false;
};

// Outputs the tree to the page

dTree.prototype.toString = function() {
//alert("dTree.prototype.toString");
	var str = '<div class="dtree">\n';
	if (document.getElementById) {
		if (this.config.useCookies){ 
			this.selectedNode = this.getSelected();
		}
		str += this.addNode(this.root);
	} else{
		str += 'Browser not supported.';
	}
	str += '\n</div>';
	if (!this.selectedFound){
		this.selectedNode = null;
	}
	this.completed = true;
	return str;
};

// Creates the tree structure
// 设置节点的相关属性
dTree.prototype.addNode = function(pNode) {
	var str = '';
	var n=0;
	if (this.config.inOrder){
		n = pNode._ai;//0
	}
	for (n; n<this.aNodes.length; n=n+1) {
		var cn = this.aNodes[n];
		cn._ai = n;
		if (cn.pid == pNode.id) {
			cn._p = pNode;
			this.setCS(cn);
			if (!cn.target && this.config.target){
				cn.target = this.config.target;
			}
			if (cn._hc && !cn._io && this.config.useCookies){
				cn._io = this.isOpen(cn.id);
			}
			if (!this.config.folderLinks && cn._hc){
				cn.url = null;
			}
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {
					cn._is = true;
					this.selectedNode = n;
					this.selectedFound = true;
			}
			str += this.node(cn, n);
			str += '\n';
			if (cn._ls){//如果是末节点，可不再查找
				break;
			}
		}
	}
	return str;
};

// Creates the node icon, url and text
// 得到节点的html语句
dTree.prototype.node = function(node, nodeId) {
	var str = '<div class="dTreeNode">' + this.indent(node, nodeId);
	if (this.config.useIcons) {
		if (!node.icon){
			node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);
		}
		if (!node.iconOpen){
			node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;
		}
		if (this.root.id == node.pid) {
			node.icon = this.icon.root;
			node.iconOpen = this.icon.root;
		}
		str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
	}

	if (node.url) {
		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';
		if (node.title){
			str += ' title="' + node.title + '"';
		}
		if (node.target){
			str += ' target="' + node.target + '"';
		}
		if (this.config.useStatusText){
			str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
		}
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc)){
			if(node.onclick){
				str += ' onclick="' + node.onclick + '"';
			} else {
				str += ' onclick="javascript: ' + this.parent + '.findTree(\''+this.obj+'\').s(' + nodeId + ');"';
			}
		} else {
			if(this.onclick){
				str += ' onclick="' + node.onclick + '"';
			}
		}
		str += '>';
	}else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id){
//		str += '<a href="javascript: ' + this.parent + '.findTree(\''+this.obj+'\').o(' + nodeId + ');" class="node">';
		str += '<a href="javascript: clk(\'' + node.id + '\');" class="node">';
	}
	str += node.name;
	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)){
		str += '</a>';
	}
	str += '</div>';
	if (node._hc) {
		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';
		str += this.addNode(node);
		str += '</div>';
	}
	this.aIndent.pop();
//	alert(str);
	return str;
};

// Adds the empty and line icons
// 加入空白及线条图片
dTree.prototype.indent = function(node, nodeId) {
	var str = '';
	if (this.root.id != node.pid) {
		for (var n=0; n<this.aIndent.length; n=n+1){
			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';
		}
		if(node._ls){
			this.aIndent.push(0);
		}else{
			this.aIndent.push(1);
		}
		if(this.parent.clk_id==node.id){
			if(this.parent.clk_status==1){
				str += '<a href="javascript: clk(\'' + node.id + '\');"><img id="j' + this.obj + nodeId + '" src="';
				str +=this.icon.nlMinus;
				str += '" alt="" onclick="clk(\'' + node.id + '\');return false;'+'" /></a>';	
			}else if(this.parent.clk_status==2){
				str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
			}else{
				//str += '<a href="javascript: ' + this.parent + '.findTree(\''+this.obj+'\').o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';
				str += '<a href="javascript: clk(\'' + node.id + '\');"><img id="j' + this.obj + nodeId + '" src="';
				if (!this.config.useLines){
	//				str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
					str +=this.icon.nlPlus;
				}else{
					str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
				}
				//str += '" alt="" onclick="'+this.parent + '.findTree(\''+this.obj+'\').o(' + nodeId + ');return false;'+'" /></a>';
				str += '" alt="" onclick="clk(\'' + node.id + '\');return false;'+'" /></a>';
			}
		}else if (node._hc) {
//			str += '<a href="javascript: ' + this.parent + '.findTree(\''+this.obj+'\').o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';
			str += '<a href="javascript: clk(\'' + node.id + '\');"><img id="j' + this.obj + nodeId + '" src="';
			if (!this.config.useLines){
//				str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;
				str +=this.icon.nlPlus;
			}else{
				str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );
			}
			//str += '" alt="" onclick="'+this.parent + '.findTree(\''+this.obj+'\').o(' + nodeId + ');return false;'+'" /></a>';
			str += '" alt="" onclick="clk(\'' + node.id + '\');return false;'+'" /></a>';
		} else{
			str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';
		}
	}
	return str;
};

// Checks if a node has any children and if it is the last sibling

dTree.prototype.setCS = function(node) {
	var lastId;
	for (var n=0; n<this.aNodes.length; n=n+1) {
		if (this.aNodes[n].pid == node.id){
			node._hc = true;//非叶子节点
		}
		if (this.aNodes[n].pid == node.pid){
			lastId = this.aNodes[n].id;
		}
	}
	if (lastId==node.id){
		node._ls = true;//末节点
	}
};

// Returns the selected node

dTree.prototype.getSelected = function() {
	var sn = this.getCookie('cs' + this.obj);
	return (sn) ? sn : null;
};

// Highlights the selected node

dTree.prototype.s = function(id) {
	if (!this.config.useSelection){
		return;
	}
	var cn = this.aNodes[id];
	if (cn._hc && !this.config.folderLinks){
		return;
	}
	if (this.selectedNode != id) {
		if (this.selectedNode || this.selectedNode===0) {
			var eOld = document.getElementById("s" + this.obj + this.selectedNode);
			eOld.className = "node";
		}
		var eNew = document.getElementById("s" + this.obj + id);
		eNew.className = "nodeSel";
		this.selectedNode = id;
		if (this.config.useCookies){
			this.setCookie('cs' + this.obj, cn.id);
		}
	}
};

// Toggle Open or close

dTree.prototype.o = function(id) {
	var cn = this.aNodes[id];
	this.nodeStatus(!cn._io, id, cn._ls);
	cn._io = !cn._io;
	if (this.config.closeSameLevel){
		this.closeLevel(cn);
	}
	if (this.config.useCookies){
		this.updateCookie();
	}
};

// Open or close all nodes

dTree.prototype.oAll = function(status) {
	for (var n=0; n<this.aNodes.length; n=n+1) {
		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {
			this.nodeStatus(status, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = status;
		}
	}
	if (this.config.useCookies){
		this.updateCookie();
	}
};

// Opens the tree to a specific node

dTree.prototype.openTo = function(nId, bSelect, bFirst) {
	if (!bFirst) {
		for (var n=0; n<this.aNodes.length; n=n+1) {
			if (this.aNodes[n].id == nId) {
				nId=n;
				break;
			}
		}
	}
	var cn=this.aNodes[nId];
	if (cn.pid==this.root.id || !cn._p){
		return;
	}
	cn._io = true;
	cn._is = bSelect;
	if (this.completed && cn._hc){
		this.nodeStatus(true, cn._ai, cn._ls);
	}
	if (this.completed && bSelect){
		this.s(cn._ai);
	}else if (bSelect){
		this._sn=cn._ai;
	}
	this.openTo(cn._p._ai, false, true);
};

// Closes all nodes on the same level as certain node

dTree.prototype.closeLevel = function(node) {
	for (var n=0; n<this.aNodes.length; n=n+1) {
		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {
			this.nodeStatus(false, n, this.aNodes[n]._ls);
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);
		}
	}
};

// Closes all children of a node
// 关闭指定节点的所有子节点
dTree.prototype.closeAllChildren = function(node) {
	for (var n=0; n<this.aNodes.length; n=n+1) {
		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {
			if (this.aNodes[n]._io){
				this.nodeStatus(false, n, this.aNodes[n]._ls);
			}
			this.aNodes[n]._io = false;
			this.closeAllChildren(this.aNodes[n]);		
		}
	}
};

// Change the status of a node(open or closed)
// 改变节点的关闭和打开
dTree.prototype.nodeStatus = function(status, id, bottom) {
	var eDiv	= document.getElementById('d' + this.obj + id);
	var eJoin	= document.getElementById('j' + this.obj + id);
	if (this.config.useIcons) {
		var eIcon	= document.getElementById('i' + this.obj + id);
		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;
	}
	eJoin.src = (this.config.useLines)?
	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):
	((status)?this.icon.nlMinus:this.icon.nlPlus);
	eDiv.style.display = (status) ? 'block': 'none';
};

// [Cookie] Clears a cookie

dTree.prototype.clearCookie = function() {
	var now = new Date();
	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);
	this.setCookie('co'+this.obj, 'cookieValue', yesterday);
	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);
};

// [Cookie] Sets value in a cookie

dTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {
	document.cookie =escape(cookieName) + '=' + escape(cookieValue)+ (expires ? '; expires=' + expires.toGMTString() : '')+ (path ? '; path=' + path : '')+ (domain ? '; domain=' + domain : '')+ (secure ? '; secure' : '');
};

// [Cookie] Gets a value from a cookie

dTree.prototype.getCookie = function(cookieName) {
	var cookieValue = '';
	var posName = document.cookie.indexOf(escape(cookieName) + '=');
	if (posName != -1) {
		var posValue = posName + (escape(cookieName) + '=').length;
		var endPos = document.cookie.indexOf(';', posValue);
		if (endPos != -1){
			cookieValue = unescape(document.cookie.substring(posValue, endPos));
		}else{
			cookieValue = unescape(document.cookie.substring(posValue));
		}
	}
	return (cookieValue);
};

// [Cookie] Returns ids of open nodes as a string

dTree.prototype.updateCookie = function() {
	var str = '';
	for (var n=0; n<this.aNodes.length; n=n+1) {
		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {
			if (str){
				str += '.';
			}
			str += this.aNodes[n].id;
		}
	}
	this.setCookie('co' + this.obj, str);
};

// [Cookie] Checks if a node id is in a cookie

dTree.prototype.isOpen = function(id) {
	var aOpen = this.getCookie('co' + this.obj).split('.');
	for (var n=0; n<aOpen.length; n=n+1){
		if (aOpen[n] == id){
			return true;
		}
	}
	return false;
};

// If Push and pop is not implemented by the browser

if (!Array.prototype.push) {
	Array.prototype.push = function array_push() {
		for(var i=0;i<arguments.length;i=i+1){
			this[this.length]=arguments[i];
		}
		return this.length;
	};
}

if (!Array.prototype.pop) {
	Array.prototype.pop = function array_pop() {
		var lastElement = this[this.length-1];
		this.length = Math.max(this.length-1,0);
		return lastElement;
	};
}