function god(){};
var g1=new god();
//g.prototype.g=function(i){return document.getElementById(i)?document.getElementById(i):alert('g('+i+') is null')};
//g.prototype.gbn=function(n){return document.getElementsByName(n).length>0?document.getElementsByName(n):alert('gbn('+n+') is null')};
//g.prototype.gbt=function(t){return document.getElementsByTagName(t).length>0?document.getElementsByTagName(t):alert('gbt('+t+') is null')};
god.prototype.g=function(i){return document.getElementById(i)};
god.prototype.G=function(o){return o?(typeof(o)=='object'?o:(typeof(o)=='string'?(typeof(this.g(o))=='object'?this.g(o):null):null)):null;};
god.prototype.gbn=function(n){return document.getElementsByName(n)};
god.prototype.gbt=function(t){return document.getElementsByTagName(t)};
god.prototype.trim=function(s){return s.replace(/^\s*/,'').replace(/\s*$/,'');};
god.prototype.chat=function(s){return parseInt(s.replace(/\D/g,''),10)}
god.prototype.getObjCurrentStyle=function(obj){return obj.currentStyle?obj.currentStyle:document.defaultView.getComputedStyle(obj,null)};
god.prototype.getObjPos=function(obj){
    var p=obj;t=0;l=0;
	while(p.tagName!='BODY'&&p.tagName!='HTML'){
		t+=p.offsetTop+p.clientTop;
		l+=p.offsetLeft+p.clientLeft;
		p=p.offsetParent;
	}
	
	var w=obj.offsetWidth;
	var h=obj.offsetHeight;
	return {left:l,top:t,width:w,height:h};
}
god.prototype.getMousePos=function(e){
    e=e?e:event;
    if(e.pageX){return {ox:e.pageX, oy:e.pageY,x:e.clientX,y:e.clientY};}//IE
    return {ox:e.clientX + document.documentElement.scrollLeft,oy:e.clientY + document.documentElement.scrollTop,x:e.clientX,y:e.clientY};
}
god.prototype.getBrowserInfo=function(){
    var ua=window.navigator.userAgent.toLowerCase();
    var t='',v='';
    if(ua.search(/msie/)!=-1){//IE
        t='ie';v = ua.match(/msie ([\d.]+)/)[0].replace(/msie /,'');
    }else if (ua.indexOf("firefox")>0){//FF
        t='firefox';v= ua.match(/firefox\/([\d.]+)/)[0].replace(/firefox\//,'');
    }else if(window.opera){//opera
        t='opera';v=ua.match(/opera.([\d.]+)/)[0].replace(/opera\//,'');
    }else if (window.MessageEvent && !document.getBoxObjectFor){//chrome 
        t='chrome';v = ua.match(/chrome\/([\d.]+)/)[0].replace(/chrome\//,'');
    }else if (window.openDatabase){ //safari
        t='safari';v= ua.match(/version\/([\d.]+)/)[0].replace(/version\//,'');
    }else{
        t='unkown Type';v='unkown Version';
    }
    return {type:t,version:v};
}
