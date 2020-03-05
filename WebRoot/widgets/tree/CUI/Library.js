// JavaScript Document
/* 注册监听事件[对象,触发事件，回调事件] */
function addEvent(obj, type, fn){
	if (obj.addEventListener) {
		obj.addEventListener(type, fn, false);
		EventCache.add(obj, type, fn);
	}
	else 
		if (obj.attachEvent) {
			obj["e" + type + fn] = fn;
			obj[type + fn] = function(){
				obj["e" + type + fn](window.event);
			}
			obj.attachEvent("on" + type, obj[type + fn]);
			EventCache.add(obj, type, fn);
		}
		else {
			obj["on" + type] = obj["e" + type + fn];
		}
}
/*监听事件缓存对象*/
var EventCache = function(){
	var listEvents = [];
	return {
		listEvents : listEvents,
		add : function(node, sEventName, fHandler){
			listEvents.push(arguments);
		},
		flush : function(){
			var i, item;
			for(i = listEvents.length - 1; i >= 0; i = i - 1){
				item = listEvents[i];
				if(item[0].removeEventListener){
					item[0].removeEventListener(item[1], item[2], item[3]);
				};
				if(item[1].substring(0, 2) != "on"){
					item[1] = "on" + item[1];
				};
				if(item[0].detachEvent){
					item[0].detachEvent(item[1], item[2]);
				};
				item[0][item[1]] = null;
			};
		}
	};
}();
/*清除监听事件缓存*/
addEvent(window,'unload',EventCache.flush);

/* 注册onload事件 */
function addLoadEvent(func) {
	var oldonload = window.onload;
	if (typeof window.onload != 'function') {
		window.onload = func;
	}
	else {
		window.onload = function() {
			oldonload();
			func();
		}
	}
}

/* 获取相同className名的函数[class,节点,标签] */
function getElementsByClass(searchClass,node,tag) {
	var classElements = new Array();
	if ( node == null )
		node = document;
	if ( tag == null )
		tag = '*';
	var els = node.getElementsByTagName(tag);
	var elsLen = els.length;
	var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
	for (i = 0, j = 0; i < elsLen; i++) {
		if ( pattern.test(els[i].className) ) {
			classElements[j] = els[i];
			j++;
		}
	}
	return classElements;
}
/* insert an element after a particular node */
function insertAfter(parent, node, referenceNode) {
	parent.insertBefore(node, referenceNode.nextSibling);
}


/* get, set, and delete cookies */
function getCookie( name ) {
	var start = document.cookie.indexOf( name + "=" );
	var len = start + name.length + 1;
	if ( ( !start ) && ( name != document.cookie.substring( 0, name.length ) ) ) {
		return null;
	}
	if ( start == -1 ) return null;
	var end = document.cookie.indexOf( ";", len );
	if ( end == -1 ) end = document.cookie.length;
	return unescape( document.cookie.substring( len, end ) );
}
function setCookie( name, value, expires, path, domain, secure ) {
	var today = new Date();
	today.setTime( today.getTime() );
	if ( expires ) {
		expires = expires * 1000 * 60 * 60 * 24;
	}
	var expires_date = new Date( today.getTime() + (expires) );
	document.cookie = name+"="+escape( value ) +
		( ( expires ) ? ";expires="+expires_date.toGMTString() : "" ) + 
		( ( path ) ? ";path=" + path : "" ) +
		( ( domain ) ? ";domain=" + domain : "" ) +
		( ( secure ) ? ";secure" : "" );
}
function deleteCookie( name, path, domain ) {
	if ( getCookie( name ) ) document.cookie = name + "=" +
			( ( path ) ? ";path=" + path : "") +
			( ( domain ) ? ";domain=" + domain : "" ) +
			";expires=Thu, 01-Jan-1970 00:00:01 GMT";
}

/* quick getElement reference */
function _$() {
	var elements = new Array();
	for (var i = 0; i < arguments.length; i++) {
		var element = arguments[i];
		if (typeof element == 'string')
			element = document.getElementById(element);
		if (arguments.length == 1)
			return element;
		elements.push(element);
	}
	return elements;
}

/* Object-oriented Helper functions. */
function clone(object) {
	function F() {}
	F.prototype = object;
	return new F;
}

function extend(subClass, superClass) {
  var F = function() {};
  F.prototype = superClass.prototype;
  subClass.prototype = new F();
  subClass.prototype.constructor = subClass;

	subClass.superclass = superClass.prototype;
	if(superClass.prototype.constructor == Object.prototype.constructor) {
		superClass.prototype.constructor = superClass;
  }
}

function augment(receivingClass, givingClass) {
  if(arguments[2]) { // Only give certain methods.
    for(var i = 2, len = arguments.length; i < len; i++) {
      receivingClass.prototype[arguments[i]] = givingClass.prototype[arguments[i]];
    }
  } 
  else { // Give all methods.
    for(methodName in givingClass.prototype) { 
      if(!receivingClass.prototype[methodName]) {
        receivingClass.prototype[methodName] = givingClass.prototype[methodName];
      }
    }
  }
}

function formCache(obj){
	var listItems=[];
	listItems=obj.elements;
 	for(var i=0,len=listItems.length;i<len;i++){
 		var el = listItems[i];
		switch(el.type){
			case "text":
			el.value = ""
			break;
			case "checkbox":
			el.checked = false
			break;
			case "radio":
			el.checked = false
			break;
		}    		
 	}	
}

String.prototype.cut = function(strlen){
    var temp_i=0;
	var temp=0;
	var temp_str="";
	str=str.replace(/[\r\n]/g,'').replace(/<[^>]+>/g,"");//去掉所有的html标记 
	for(temp_i=0;temp_i<this.length;temp_i++){
	
	   if(Math.abs(this.charCodeAt(temp_i))>255)
	      temp+=2;
	   else 
	      temp+=1;
	   
	   if(temp>strlen*2) {
	       temp_str=this.substr(0,temp_i);//+"...";
		   break;
		   }
		else{
		   temp_str=this;
		}
	}
   return  temp_str;   
}
String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.LTrim = function()
{
	return this.replace(/(^\s*)/g, "");
}
String.prototype.RTrim = function()
{
	return this.replace(/(\s*$)/g, "");
} 

//获取JS传参
function getJsSrc(name,paras){
	var scripts=document.getElementsByTagName('script'),i,n,slen=scripts.length,plen=paras.length,returns=[];
	for(i=0;i<slen;i++){
		var src=scripts[i].src;
		if(src.indexOf(name)>-1){
			for(n=0;n<plen;n++){
				returns.push(src.getjsparas(paras[n]))
			}
			return returns;
		}
	}
}
//获取js的参数
String.prototype.getjsparas=function (name){     
	var paraString = this.substring(this.indexOf("?")+1,this.length).split("&");     
	var paraObj = {}     
	for (i=0; j=paraString[i]; i++){     
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);     
	}     
	var returnValue = paraObj[name.toLowerCase()];     
	if(typeof(returnValue)=="undefined"){     
		return "";     
	}else{     
		return returnValue;     
	}     
}

function sendForm(obj,str){
	if(str!='') obj.act.value=str;
	obj.target = "";
	obj.submit();
}

function selectall(objform){
	for(var i=0;i<objform.elements.length;i++){
    	var box = objform.elements[i];
   		if (box.name == 'sBox')
    		box.checked = objform.chkall.checked;
 	}
}
function switchall(objform){
	for(var i=0;i<objform.elements.length;i++){
    	var box = objform.elements[i];
   		if (box.name == 'sBox')
    		box.checked = !box.checked;
 		}
 	var chkall = $("chkall");
 	if (chkall.checked)
 		chkall.checked = !chkall.checked;
}
//—查找相关元素的前一个兄弟元素—//  
function prev(elem){
	do {
		elem = elem.previousSibling;
	}
	while (elem && elem.nodeType != 1);
	return elem;
}
//—查找相关元素的下一个兄弟元素—//  
function next(elem){
	do {
		elem = elem.nextSibling;
	}
	while (elem && elem.nodeType != 1);
	return elem;
}
//—查找第一个子元素的函数—//  
function first(elem){
	elem = elem.firstChild;
	return elem && elem.nodeType != 1 ? next(elme) : elem;
}
//—查找最后一个子元素的函数—//  
function last(elem){
	elem = elem.lastChild;
	return elem && elem.nodeType != 1 ? prev(elme) : elem;
}
//—查找父级元素的函数—//  
//num是父级元素的级次，parent(elem,2)等价于parent(parent(elem))  
function parent(elem, num){
	num = num || 1;
	for (var i = 0; i < num; i++) {
		if (elem != null) {
			elem = elem.parentNode;
		}
	}
	return elem;
}

//获取鼠标的绝对坐标
function mousePosition(ev){
	ev = ev || window.event;
	if(ev.pageX || ev.pageY){
		return {x:ev.pageX, y:ev.pageY};
	}
	return {
		x:ev.clientX + document.documentElement.scrollLeft,
		y:ev.clientY + document.documentElement.scrollTop
	};
}
//获取事件的绝对坐标
function getAbsEventPoint(e){
	var e = e||window.event;
	var x = e.offsetLeft, y = e.offsetTop;
	while (e = e.offsetParent) {
		x += e.offsetLeft;
		y += e.offsetTop;
	}
	return [x, y];
}
//获取事件对象
function getEventObj(e){
	var e = e||window.event;
	var o = event.srcElement ? event.srcElement : event.target;
	return o;
}
/**
 *获取对象的绝对坐标
 */
function getAbsObjPoint(o){
	var x = y = 0;
	do {
		x += o.offsetLeft;
		y += o.offsetTop;
	} while ((o = o.offsetParent)); // && o.tagName != "BODY"*/
	return [x , y ];
}

function getBrowserWidth() 
{ 
if(window.innerWidth) 
return window.innerWidth; 
else if(document.body.clientWidth) 
return document.body.clientWidth; 
else return -1; 
} 

function getBrowserHeight() 
{ 
return (window.innerHeight?window.innerHeight:document.documentElement.clientHeight); 
} 

function getBodyWidth() 
{ 
if(document.body.clientWidth) 
return document.body.clientWidth; 
else return -1; 
} 

function getBodyHeight() 
{ 
if(document.body.clientHeight) 
return document.body.clientHeight; 
else return -1; 
} 

function getObjectWidth(i) 
{ 
return i.offsetWidth; 
}

var browserName = navigator.userAgent.toLowerCase();
mybrowser = {
	version: (browserName.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [0, '0'])[1],
	safari: /webkit/i.test(browserName) && !this.chrome,
	opera: /opera/i.test(browserName),
        firefox:/firefox/i.test(browserName),
	ie: /msie/i.test(browserName) && !/opera/.test(browserName),
	mozilla: /mozilla/i.test(browserName) && !/(compatible|webkit)/.test(browserName) && !this.chrome,
        chrome: /chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)
}