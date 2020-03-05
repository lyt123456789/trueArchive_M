/**JavaScript Document
 * Func  : 仿Flash导航效果
 * Author: [purecolor@foxmail.com]
 */
var navparams=getJsSrc('navmenu',['start','id','tag','bgw']);

(function(start,obj,tag,objw){
	var navmenu=_$(obj).getElementsByTagName(tag);
	if(start<1||start>navmenu.length) start=1;
	for(var i=1,len=navmenu.length;i<len-1;i++){
		navmenu[i].onmouseover=function(){
			window.clearTimeout(window['stime']);
			var size={},from=parseInt((_$(obj).style.backgroundPosition).split(' ')[0]);
			from=isNaN(from)?0:from;
			size.from=from;
			size.to=getAbsObjPoint(this)[0]-(Math.floor((objw/2)) - Math.floor((this.offsetWidth/2)))-getAbsObjPoint(_$(obj))[0];
			if(Math.abs(size.to-size.from)<1) return false;
			//缓动效果
			movebg(_$(obj),size,0.1,5);
			//弹性效果
			//movebgtween(_$('navbox'),size,0,0.8,0.8,20)
		}
		/*
		navmenu[i].onmouseout=function(){
			goback();
		}
		*/
	}
	//初始导航背景坐标
	var goback=function (){
		window.clearTimeout(window['stime']);
		var size={},from=parseInt((_$(obj).style.backgroundPosition).split(' ')[0]);
		from=isNaN(from)?0:from;
		size.from=from;
		size.to=getAbsObjPoint(navmenu[start-1])[0]-(Math.floor((objw/2)) - Math.floor((navmenu[start-1].offsetWidth/2)))-getAbsObjPoint(_$(obj))[0];
		if(Math.abs(size.to-size.from)<1) return false;
		//缓动效果
		movebg(_$(obj),size,0.1,5);
	};
	goback();
})(navparams[0],navparams[1],navparams[2],navparams[3]);