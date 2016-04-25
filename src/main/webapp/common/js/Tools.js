/**
 * 返回path
 * @returns
 */

/**
 * 全局变量，dataOptions的分页以及page集合
 */
var dataOptions = {
		pageSize :15,
		pageList : [10,15,30,50,100]
};

function getPointerX(event) {
	return event.pageX
			|| (event.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft));
}
function getPointerY(event) {
	return event.pageY
			|| (event.clientY + (document.documentElement.scrollTop || document.body.scrollTop));
}

function replaceNull2Space(s) {
    if (s== null || s == 'null'){
    	return "";
    }
    return s;
}

//将form表单元素的值序列化成对象
function serializeObject(form,ignoreBlank){
	var o ={};
	$.each(form.serializeArray(),function(index){
		if(typeof(ignoreBlank) == 'undefined' || (ignoreBlank !=null &&ignoreBlank == false)){
			if(o[this['name']]){
				o[this['name']]=o[this['name']]+","+this['value'];
			}else{
				o[this['name']] = this['value'];
			}
		}else{
			if(this['value']!=null&&this['value']!=""){
				if(o[this['name']]){
					o[this['name']]=o[this['name']]+","+this['value'];
				}else{
					o[this['name']] = this['value'];
				}
			}
		}
	});
	return o;
}

/**
 * ajax请求
 */
function ajaxRequest(type, data, url, dataType, event) {
	var contextPath = getPath();
	var urltranslated = contextPath + "/" + url;
	if(dataType != ''){
		jQuery.ajax({
			type : type,
			data : data,
			url : urltranslated,
			dataType : dataType,
			success : function(msg) {
				if(msg!=null&&msg.error!=null){ //失败
	    			window.alert("Ajax操作时发生异常，地址为：" + urltranslated + "，异常信息为：" + msg.error);
	    			return;
	    		}else{
	    			if(event!=null){
	    				eval(event + "(msg)");
	    			}
	    		}
			},
			error: function(msg){
			//	window.alert("Ajax操作时发生异常，地址为：" + urltranslated + "，异常信息为：" + msg.responseText);
			}
		});
	} else {
		jQuery.ajax({
			type : type,
			data : data,
			url : urltranslated,
			success : function(msg) {
				if(msg!=null&&msg.error!=null){ //失败
	    			window.alert("Ajax操作时发生异常，地址为：" + urltranslated + "，异常信息为：" + msg.error);
	    			return;
	    		}else{
	    			if(event!=null){
	    				eval(event + "(msg)");
	    			}
	    		}
			},
			error: function(msg){
				//window.alert("Ajax操作时发生异常，地址为：" + urltranslated + "，异常信息为：" + msg.responseText);
			}
		});
	}
	
}

/*
 * JsMap对象，实现MAP功能
 * API接口：
 * size()     获取MAP元素个数
 * isEmpty()    判断MAP是否为空
 * clear()     删除MAP所有元素
 * put(key, value)   向MAP中增加元素（key, value) 
 * remove(key)    删除指定KEY的元素，成功返回True，失败返回False
 * get(key)    获取指定KEY的元素值VALUE，失败返回NULL
 * element(index)   获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
 * containsKey(key)  判断MAP中是否含有指定KEY的元素
 * containsValue(value) 判断MAP中是否含有指定VALUE的元素
 * values()    获取MAP中所有VALUE的数组（ARRAY）
 * keys()     获取MAP中所有KEY的数组（ARRAY）
 *
 * var map = new Map();
 * map.put("key", "value");
 * var val = map.get("key")
 */
function Map() {
	this.elements = new Array();

	//获取MAP元素个数
	this.size = function() {
		return this.elements.length;
	};

	//判断MAP是否为空
	this.isEmpty = function() {
		return (this.elements.length < 1);
	};

	//删除MAP所有元素
	this.clear = function() {
		this.elements = new Array();
	};

	//向MAP中增加元素（key, value) 
	this.put = function(_key, _value) {
		this.remove(_key);
		this.elements.push({
			key : _key,
			value : _value
		});
	};

	//删除指定KEY的元素，成功返回True，失败返回False
	this.remove = function(_key) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	};

	//获取指定KEY的元素值VALUE，失败返回NULL
	this.get = function(_key) {
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		} catch (e) {
			return null;
		}
	};

	//获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
	this.element = function(_index) {
		if (_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	};

	//判断MAP中是否含有指定KEY的元素
	this.containsKey = function(_key) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	};

	//判断MAP中是否含有指定VALUE的元素
	this.containsValue = function(_value) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	};

	//获取MAP中所有VALUE的数组（ARRAY）
	this.values = function() {
		var arr = new Array();
		for (var i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	};

	//获取MAP中所有KEY的数组（ARRAY）
	this.keys = function() {
		var arr = new Array();
		for (var i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	};
}
/**
 * 获取radio group 值
 * @param elementName
 * @returns {String}
 */
function getRadioValue(elementName){
	var v = '';
	var r = document.getElementsByName(elementName);
	for ( var i = 0; i < r.length; i++) {
		if(r[i].checked){
			v= r[i].value;
			break;
		}
	}
	return v;
}
/**
 * easyui遮罩
 */
function easyuiMask(){
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
/**
 * 取消easyui遮罩
 */
function easyuiUnMask(){
	$("div.datagrid-mask-msg").remove();  
	$("div.datagrid-mask").remove();  
}

var FormProxyPage = {
	url : "avicit/platform6/bpmclient/bpm/ProcessApprove.jsp"
};

//这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法
Date.prototype.format = function (format) {
     var o = {
         "M+": this.getMonth() + 1, //month 
         "d+": this.getDate(),    //day 
         "h+": this.getHours(),   //hour 
         "m+": this.getMinutes(), //minute 
         "s+": this.getSeconds(), //second 
         "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter 
         "S": this.getMilliseconds() //millisecond 
     };
     if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
     (this.getFullYear() + "").substr(4 - RegExp.$1.length));
     for (var k in o) if (new RegExp("(" + k + ")").test(format))
         format = format.replace(RegExp.$1,
       RegExp.$1.length == 1 ? o[k] :
         ("00" + o[k]).substr(("" + o[k]).length));
     return format;
 };
 //日期格式化函数，用于列单元格的展示数据的格式化，及日期控件显示数据的格式化
 function formatColumnDate(value, row) {
 	//alert("formatColumnDate被调用，value="+value);
 	if (value == null || value == '') {
 		return '';
 	}
 	var dt;
 	if (value instanceof Date) {
 		dt = value;
 	} else {
 		dt = new Date(value);
 		if (isNaN(dt)) {
 			value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); // 标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
 			dt = new Date();
 			dt.setTime(value);
 		}
 	}

 	return dt.format("yyyy-MM-dd"); // 这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法
 }
 //时间格式化函数，用于列单元格的展示数据的格式化，及时间控件显示数据的格式化
 function formatColumnTime(value, row) {
 	//alert("formatColumnTime被调用，value="+value);
 	if (value == null || value == '') {
 		return '';
 	}
 	var dt;
 	if (value instanceof Date) {
 		dt = value;
 	} else {
 		dt = new Date(value);
 		if (isNaN(dt)) {
 			value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); // 标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
 			dt = new Date();
 			dt.setTime(value);
 		}
 	}

 	return dt.format("yyyy-MM-dd hh:mm:ss"); // 这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法
 }

 //日期解析函数，用于日期控件的数据解析
 function parserColumnDate(value){
 	
 	var t = Date.parse(value);
 	if (!isNaN(t)){
 		return new Date(t);
 	} else {
 		return new Date();
 	}

 }
//时间解析函数，用于时间控件的数据解析
function parserColumnTime(value) {
var t = Date.parse(value);
 	if (!isNaN(t)){
 		return new Date(t);
 	} else {
 		var d=parseDate(value);
 		if (d instanceof Date) {
 			return d;
		}else{
			return new Date();
		}
 		
 	}


}

function parseDate(dateStr) {
if(dateStr==""){
	return;
}
var regexDT = /(\d{4})-?(\d{2})?-?(\d{2})?\s?(\d{2})?:?(\d{2})?:?(\d{2})?/g;
var matchs = regexDT.exec(dateStr);
var date = new Array();
for (var i = 1; i < matchs.length; i++) {
    if (matchs[i]!=undefined) {
        date[i] = matchs[i];
    } else {
        if (i<=3) {
            date[i] = '01';
        } else {
            date[i] = '00';
        }
    }
}
return new Date(date[1], date[2]-1, date[3], date[4], date[5],date[6]);
}
