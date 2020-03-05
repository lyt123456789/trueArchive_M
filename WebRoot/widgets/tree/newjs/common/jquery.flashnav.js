// JavaScript Document
(function($){
	var timer;
	$.fn.easNaver=function(el,options){
			var defaults={
				el:'li',
				hoverClass:'hover',
				trigger:'mouseover'
			}
			var o=$.extend(defaults,options);
			var $this=$(this);
			var items=$(o.eml,$this);
	}
	/**
	 *缓动效果
	 *params o:移动的对象,size:{from:开始坐标,to:目标坐标},easing:缓动系数,detay:帧频
	 *return null
	 */
	function movebg(o,size,easing,detay){
		timer=setTimeout(function(){
			var speed=size.from;
			speed+=(size.to-size.from)*easing;
			if (Math.abs((size.to-size.from)*easing*10)<1){
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
		timer=setTimeout(function (){
			//开始弹性运动
			var speed=size.from;
			vx+=(size.to-size.from)*spring;
			speed +=(vx*=friction);
			o.style.backgroundPosition=speed+'px 0';
			if (Math.abs((size.to-size.from)*spring*10)<1){
				o.style.backgroundPosition=size.to+'px 0';
				return false;
			}
			movebgtween(o,{from:speed,to:size.to},vx,spring,friction,detay);
		},detay);
	}
	
})(jQuery);

(function($){
	$.fn.easNaver2=function(el,dety){
		var $this=$(this);
		$el=$(el,$this);
		$el.each(function(){
			$(this).hover(function(){
				move($(this),0,0,-47,dety,'g');
			},function(){
				move($(this),0,0,-47,dety,'b');
			});
		});
		//缓动函数
		var Tween={
			Cubic: {
				easeIn: function(t,b,c,d){
					return c*(t/=d)*t*t + b;
				},
				easeOut: function(t,b,c,d){
					return c*((t=t/d-1)*t*t + 1) + b;
				},
				easeInOut: function(t,b,c,d){
					if ((t/=d/2) < 1) return c/2*t*t*t + b;
					return c/2*((t-=2)*t*t + 2) + b;
				}
			}
		};
		//运动函数
		function move(o,t,b,c,d,s){
			var t=t,b=b,c=c,d=d;
			function a(){
				var speed=Math.ceil(Tween.Cubic.easeInOut(t,b,c,d));
				if(s=='g')
					o.css({'background-position':'0px '+(speed-b)+'px'});
				else
					o.css({'background-position':'0px '+(c-speed+b)+'px'});
				if(t<d){t++;setTimeout(arguments.callee, 10);}
			}
			a();
		}
		
	}	
})(jQuery);