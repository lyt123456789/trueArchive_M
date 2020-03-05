/**
 *	jQuery插件
 *	Author:	purecolor@foxmail.com
 *	Date  :	2011-02-25
 *	Params: 
 *			defaults:{
					currentClass:当前样式,
					trigger:触发方式,
					callBack:回调函数,
					auto:是否自运行,
					detay:延迟时间,
					index:当前位置
 *			}
 *	Return: null
 *  Note  : Tab选项框插件
 *		     
 */
(function($){
	$.fn.extend({
		tabBuild:function(options){
			return $(this).each(function(){
				var defaults={
					currentClass:"currentOne",
					trigger:"mouseover",
					callBack:null,
					auto:false,
					detay:3000,
					index:0
				}
				var params=$.extend(defaults,options);
				var $this=$(this);
				var items=$('.tab-menu',$this),i=0,l=items.size()-1;
				var autoTimer,curro=0;
				
				items.each(function(){
					$(this).data('lvl',i);
					if(i==params.index){
						$('.tab-content',$this).eq(i).show();
						$(this).addClass(params.currentClass);
					}else{
						$('.tab-content',$this).eq(i).hide();
					}
					i++;
					$(this).bind(params.trigger,function(e){
						//移除自动运行
						if(params.auto){
							if(autoTimer) clearInterval(autoTimer);
						}
						move($(this).data("lvl"));
						//清除冒泡
						if (e.stopPropagation) { 
							e.stopPropagation(); // for Mozilla and Opera 
						} 
						else if (window.event) { 
							window.event.cancelBubble = true; // for IE 
						}
					});
				});
				//移动播放
				function move(i){
					//移除上一个效果
					items.eq(params.index).removeClass(params.currentClass);
					$('.tab-content',$this).eq(params.index).hide();
					//移至当前位置
					items.eq(i).addClass(params.currentClass);
					$('.tab-content',$this).eq(i).show();
					params.index=i;
				}
				//自动运行
				function auto(){
					if(params.auto){
						autoTimer=setInterval(function(){
							curro=(params.index>=l)?0:(params.index+1);
							move(curro);
						},params.detay);
					}else{
						if(autoTimer) clearInterval(autoTimer);
					}
				}
				auto();
			}); 
		}
	});	
})(jQuery);