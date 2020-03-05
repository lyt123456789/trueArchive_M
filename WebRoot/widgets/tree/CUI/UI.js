/**JavaScript Document
 * Func  : UI组件公式
 * Author: [purecolor@foxmail.com]
 * 2010-12-06
 */
 
 
/**
 *缓动效果
 *params o:移动的对象,size:{from:开始坐标,to:目标坐标,type:操作属性,callback:回调函数},easing:缓动系数,detay:帧频
 *return null
 */
function moveo(o,size,easing,detay){
	window.setTimeout(function (){
		//开始缓动
		var speed=size.from;
		speed +=(size.to-size.from)*easing;
		speed=(size.to<size.from)?Math.floor(speed):Math.ceil(speed);
		o.style[size.type]=speed+'px';
		if (Math.abs(size.to-size.from)<=1){
			o.style[size.type]=size.to+'px';
			if(size.callback) size.callback();
			return false;
		}
		moveo(o,{from:speed,to:size.to,type:size.type,callback:size.callback},easing,detay);
	},detay);
}

/**
 *更加抽象的封装函数
 */

/**
 *弹性效果
 *params o:移动的对象,size:{from:开始坐标,to:目标坐标},vx:初始加速度,spring:弹性系数,detay:帧频
 *return null
 */
function moveotween(o,size,vx,spring,friction,detay){
	window['stime']=window.setTimeout(function (){
		//开始缓动
		var speed=size.from;
		vx+=(size.to-size.from)*spring;
		speed +=(vx*=friction);
		
		speed=(size.to<size.from)?Math.floor(speed):Math.ceil(speed);
		o.style[size.type]=speed+'px';
		
		if (Math.abs(size.to-Math.ceil(size.from))<=1){
			o.style[size.type]=size.to+'px';
			if(size.callback) size.callback();
			return false;
		}
		moveotween(o,{from:speed,to:size.to,type:size.type,callback:size.callback},vx,spring,friction,detay);
	},detay);
}
/**
 *隔行换色
 *params o:对象, tar:目标,class1:样式一,class2:样式二,handleclass:鼠标滑过的样式
 *return null
 */
function tabcolors(id,tag,class1,class2,handleclass){
	var Ptr=_$(id).getElementsByTagName(tag);
	for (i=1;i<Ptr.length+1;i++) { 
    	Ptr[i-1].className = (i%2>0)?class1:class2; 
    }
	if (handleclass!==''){
		for(var i=0;i<Ptr.length;i++) {
			Ptr[i].onmouseover=function(){
				this.tmpClass=this.className;
				this.className = handleclass;
			};
			Ptr[i].onmouseout=function(){
				this.className=this.tmpClass;
			};
		}
	}
}

/**
 *鼠标在菜单的停留位置
 *params id:对象id, tag:目标,handleclass:鼠标停留的样式,curnav:当前页面
 *return null
 */
function navstop(id,tag,handleclass,curnav){
	var leftnavmenu=_$(id).getElementsByTagName(tag),len=leftnavmenu.length;
	if(curnav<1||curnav>len) curnav=1;
	for(var i=0;i<len;i++){
		leftnavmenu[i].onmouseover=function(){
			this.tmpClass=this.className;
			this.className=handleclass;
		}
		leftnavmenu[i].onmouseout=(function(num){
			return function (){
				if ((curnav-1)!==num) this.className=this.tmpClass;
			}
		})(i);
	}
	leftnavmenu[curnav-1].className=handleclass;
}