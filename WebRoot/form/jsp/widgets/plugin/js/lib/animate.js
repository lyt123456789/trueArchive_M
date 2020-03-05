;define(function(require, exports, module){
	var $=require('jquery');
	/** 微型动画引擎
	 * @param {HTMLElement} 元素
	 * @param {Number} 开始数值
	 * @param {Number} 结束数值
	 * @param {Function} 运动中不断执行设置元素状态的函数. “this”指针指向变化的数值
	 * @param {Function} 执行完毕后的函数
	 * @param {Number} 速度. 默认300
	 * @return {undefined}
	 */
	function effect(elem, start, end, change, callback, speed){
		speed = speed || 300;
		var sTime = + new Date(),
		eTime,
		val,
		iTimer = setInterval(function(){
			eTime = (+new Date()-sTime)/speed;
			if(eTime>=1){
				change.call(end);
				callback&&callback.call(elem);
				
				return clearInterval(iTimer);
			}
			val=start+(end-start)*((-Math.cos(eTime*Math.PI)/2)+0.5);
			change.call(val);
		},1);
	}
	/** 透明渐变动画
	 * @param {Number} 结束的透明度
	 * @param {Function} 回调函数
	 * @param {Number} 速度
	 * @return {Object}
	 */
	$.fn.opactiyFlash=function(){
		var elem = this[0],
			start=end===0?1:0,
			change=elem.filters?function(){
				elem.filters.alpha.opacity=this*100;
			}:function(){
				elem.style.opactiy=this;
			};
		effect(elem, start, end, change, fn, speed);
		return this;
	}
	/** CSS常规动画
	 * @param {String} CSS属性名
	 * @param {Number} 结束的值
	 * @param {Function} 回调函数
	 * @param {Number} 速度. 默认300
	 * @return {Object}
	 */
	$.fn.cssFlash = function(name, end, fn, speed){
		var elem=this[0],
			start=parseInt(this.css(name)),
			end=parseInt(end),
			change=function(){
				try{
					elem.style[name]=this+'px';
				}catch(_){
					
				}
			};
		effect(elem, start, end, change, fn, speed);
		return this;
	};
	/** 元素可挪动边界算法
	 * @param {Boolean} 是否静止定位，默认否
	 * @param {Number} 指定其他宽度
	 * @param {Number} 指定其他高度
	 * @return {Object} 将返回最小、最大的Left与Top的值与居中的Left、Top值
	 */
	$.fn.limit=function(fixed, width, height){
		var minX, minY, maxX, maxY, centerX, centerY;
		var win = $.win(),
			doc = $.doc();
		var winWidth=win.width,
			winHeight=win.height,
			docLeft=doc.left,
			docTop=doc.top,
			boxWidth=width||this[0].offsetWidth,
			boxHeight=height||this[0].offsetHeight;
		if(fixed){
			minX=0;
			maxX=winWidth-boxWidth;
			centerX = maxX/2;
			minY=0;
			maxY=winHeight-boxHeight;
			var hc=winHeight*0.382-boxHeight/2;
			centerY=(boxHeight<4*winHeight/7)?hc:maxY/2;
		}else{
			minX=docLeft;
			maxX=winWidth-boxHeight;
			centerX=maxX/2;
			minY=docTop;
			maxY=winheight+minY-boxHeight;
			var hc=winHeight*0.382-boxHeight/2+minY;
			centerY=(boxHeight<4*winHeight/7)?hc:(maxY+minY)/2;
		}
	}
	module.exports=$;
});
