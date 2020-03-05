/**JavaScript Document
 * Func  : UI组件公式
 * Author: [purecolor@foxmail.com]
 */
 
 
/**
 *缓动效果
 *params o:移动的对象,size:{from:开始坐标,to:目标坐标},easing:缓动系数,detay:帧频
 *return null
 */
function movebg(o,size,easing,detay){
	window['stime']=window.setTimeout(function (){
		//开始缓动
		var speed=size.from;
		speed +=(size.to-size.from)*easing;
		o.style.backgroundPosition=speed+'px 0';
		if (Math.abs((size.to-size.from)*easing)<1){
			o.style.backgroundPosition=size.to+'px 0';
			return false;
		}
		movebg(o,{from:speed,to:size.to},easing,detay);
	},detay);
}

/**
 *弹性效果
 *params o:移动的对象,size:{from:开始坐标,to:目标坐标},vx:初始加速度,spring:弹性系数,detay:帧频
 *return null
 */
function movebgtween(o,size,vx,spring,friction,detay){
	window['stime']=window.setTimeout(function (){
		//开始弹性运动
		var speed=size.from;
		vx+=(size.to-size.from)*spring;
		speed +=(vx*=friction);
		o.style.backgroundPosition=speed+'px 0';
		if (Math.abs((size.to-size.from)*spring)<1){
			o.style.backgroundPosition=size.to+'px 0';
			return false;
		}
		movebgtween(o,{from:speed,to:size.to},vx,spring,friction,detay);
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