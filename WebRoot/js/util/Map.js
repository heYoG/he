function Map() { 

//数据结构  
	var struct = function (key, value) {
		this.key = key;
		this.value = value;
	};   
    
 //加入键值对
	var put = function (key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	};   
    
 //通过键获得得值
	var get = function (key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};   
 
 //根据键移除键值对
	var remove = function (key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	};   
    
 //获得大小
	var size = function () {
		return this.arr.length;
	};   
 
 //判断是否为空   
	var isEmpty = function () {
		return this.arr.length <= 0;
	};
	
	//获得键的字符串
	var strOfKeys = function () {
		var str = "";
		for (var i = 0; i < this.arr.length; i++) {
			str += this.arr[i].key + ",";
		}
		return str;
	};
	
	//获得值的字符串
	var strOfValues = function () {
		var str = "";
		for (var i = 0; i < this.arr.length; i++) {
			str += this.arr[i].value + ",";
		}
		return str;
	};
	this.arr = new Array();
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
	this.strOfKeys = strOfKeys;
	this.strOfValues = strOfValues;
}