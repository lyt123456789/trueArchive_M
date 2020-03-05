/** 
 * @fileoverview 仿苹果视图的左右切换效果
 * @author purecolor@foxmail.com
 */
;define(function(require, exports, module){
	var $=require('lib/easing');
	$.fn.slides=function(options){
		$(this).each(function(){
			var $this=$(this);
			var eventAble=true;
			//配置信息
			var settings=$.extend({
				id					: $this.attr('id'),//对象ID
				hoverClass			: "hover",//当前元素的css
				clickClass			: "current",//当前帧的样式
				hoverTrigger		: "mouseover",//触发方式
				playTrigger			: "click",//播放事件
				callBack			: null,//触发时的回调函数
				auto				: true,//是否自动播放
				detay				: 5000,//自动延迟时间
				index				: 0,//初始位置
				containerDistance	: 200,//容器尺寸
				dir					: 'left',//容器运动方向
				easing				: $.easing.easeInOutCubic,
				stepTimer			: 50
			},options);
			//缓存settings
			$this.data("setting",settings);
			//匹配按钮
			var $buttons=$('.jQslides-button > li',$this),
					i=0,l=$buttons.size()-1;
			var $container=$('.jQslides-container > li',$this);
			var step=0;
			if(settings.dir==='left'){
				step=$('.jQslides-container').width();
			}else{
				step=$('.jQslides-container').height();
			}
				
			//延时执行器
			var autoTimer,conTimer,subTimer,curro=0;
			//绑定鼠标事件
			$buttons.each(function(){
				$(this).data('index',i);
				if(i==settings.index){
					$container.eq(i).css(settings.dir,0);
					$(this).addClass('current');
				}else{
					$container.eq(i).css(settings.dir,step);
				}
				i++;
				
				$(this).bind(settings.hoverTrigger,function(e){
					//移除自动运行
					if(settings.auto){
						if(autoTimer) clearTimeout(autoTimer);
					}
					$(this).addClass('hover');
				}).bind('mouseleave',function(){
					if(settings.auto){
						auto();
					}
					$(this).removeClass('hover');
				}).bind(settings.playTrigger,function(e){
					//移除自动运行
					if(settings.auto){
						if(autoTimer) clearTimeout(autoTimer);
					}
					if($(this).data('index')!=settings.index){
						playContainer(settings.index,$(this).data('index'));
						playButton(settings.index,$(this).data('index'));
					}
					settings.index=$(this).data('index');
				});
			});
			//播放container
			function playContainer(j,k){
				if(!eventAble) return;
				if(settings.dir==='top'){
					//重设元素的坐标
					$container.eq(k).css({'top':step});
					
					effect(null,0,0,step,settings.stepTimer,function(speed){
						//当前的item
						$container.eq(j).css('top',-1*speed);
						$container.eq(k).css('top',step-speed);
					});
					
				}else{
					//重设元素的坐标
					$container.eq(k).css({'left':step});
					
					effect(null,0,0,step,settings.stepTimer,function(speed){
						//当前的item
						$container.eq(j).css('left',-1*speed);
						$container.eq(k).css('left',step-speed);
					});
					
				}
			}
			//播放button
			function playButton(j,k){
				if(!eventAble) return;
				$buttons.eq(j).removeClass('current');
				$buttons.eq(k).addClass('current');
			}
			//动画函数
			function effect(x, t, b, c, d,callback){
				var x=x||null,t=t||0,b=b,c=c,d=d;
				var a=setInterval(function(){
					var speed=settings.easing(x,t,b,c,d);
					if(!!callback) callback.call($this,speed);
					if(t<d){
						t++
					}else{
						clearTimeout(a);
					};
				},1);
			}
			//自动运行
			function auto(){
				if(settings.auto){
					autoTimer=setTimeout(function(){
						curro=(settings.index>=l)?0:(settings.index+1);
						if(curro!=settings.index){
							playContainer(settings.index,curro);
							playButton(settings.index,curro);
						}
						settings.index=curro;
						if(autoTimer) auto();
					},settings.detay);
				}else{
					if(autoTimer) clearTimeout(autoTimer);
				}
			}
			auto();
			//end code
		});
	}
	module.exports=$;
});
