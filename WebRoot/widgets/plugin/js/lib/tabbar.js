/** 
 * @fileoverview tabbar选项卡
 * @author purecolor@foxmail.com
 */
;define(function(require, exports, module){
	var $=require('lib/easing');
	$.fn.tabbar=function(options){
		//配置信息
		var settings=$.extend({
			hoverClass			: "hover", //当前元素的css
			clickClass			: "current", //当前帧的样式
			hoverTrigger		: "mouseover", //触发方式
			playTrigger			: "click", //播放事件
			callBack			: null, //触发时的回调函数
			auto				: true, //是否自动播放
			detay				: 5000, //自动播放延时
			index				: 0, //初始位置
			containerDistance	: 0, //容器尺寸
			dir					: 'left', //容器运动方向
			easing				: $.easing.easeInOutCubic,
			animate				: true
		},options);
		
		$(this).each(function(){
			
			var $this=$(this);
			
			if(settings.containerDistance==0){
				if(settings.dir==='top') 
					settings.containerDistance=$('.jQtabbar-container',$this).height();
				else
					settings.containerDistance=$('.jQtabbar-container',$this).width();
			}
			//缓存settings
			$this.data('setting',settings);
			
			//匹配按钮
			var $buttons=$('.jQtabbar-button > .jQtabbar-item',$this),
				i=0,l=$buttons.size()-1;
			var $container=$('.jQtabbar-container > .jQtabbar-item',$this);
			//延时执行器
			var autoTimer,conTimer,subTimer,curro=0;
			//绑定鼠标事件
			$container.each(function(){
				if(settings.dir==='top') 
					$(this).css({'height':settings.containerDistance+'px'});
				else
					$(this).css({'width':settings.containerDistance+'px'});
			});
			$buttons.each(function(){
				$(this).data('index',i);
				if(i==settings.index){
					$container.eq(i).css(settings.dir,0);
					$(this).addClass('current');
				}else{
					$container.eq(i).css(settings.dir,settings.containerDistance);
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
				if(j>k){
					if(settings.dir==='top'){
						//重设元素的坐标
						$container.eq(k).css({'top':-1*settings.containerDistance});
						effect(null,0,0,settings.containerDistance,50,function(speed){
							//当前的item
							$container.eq(j).css('top',speed);
							$container.eq(k).css('top',-1*settings.containerDistance+speed);
						});
					}else{
						//重设元素的坐标
						$container.eq(k).css({'left':-1*settings.containerDistance});
						effect(null,0,0,settings.containerDistance,50,function(speed){
							//当前的item
							$container.eq(j).css('left',speed);
							$container.eq(k).css('left',-1*settings.containerDistance+speed);
						});
					}
				}else{
					if(settings.dir==='top'){
						//重设元素的坐标
						$container.eq(k).css({'top':settings.containerDistance});
						effect(null,0,0,settings.containerDistance,50,function(speed){
							//当前的item
							$container.eq(j).css('top',-1*speed);
							$container.eq(k).css('top',settings.containerDistance-speed);
						});
					}else{
						//重设元素的坐标
						$container.eq(k).css({'left':settings.containerDistance});
						effect(null,0,0,settings.containerDistance,50,function(speed){
							//当前的item
							$container.eq(j).css('left',-1*speed);
							$container.eq(k).css('left',settings.containerDistance-speed);
						});
					}
				}
			}
			//播放button
			function playButton(j,k){
				$buttons.eq(j).removeClass('current');
				$buttons.eq(k).addClass('current');
			}
			//动画函数
			function effect(x, t, b, c, d,callback){
				var x=x||null,t=t||0,b=b,c=c,d=d;
				var a=function(){
					var speed=Math.ceil(settings.easing(x,t,b,c,d));
					if(!!callback) callback.call($this,speed);
					if(t<d){t++;setTimeout(arguments.callee, 15);}
					else{clearTimeout(a)};
				}();
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
	};
	module.exports=$;
});
