;define(function(require, exports, module){
	var $=require('jquery');
	$.extend($.fn,{
		tab:function(options){
			return this.each(function(){
				//配置信息
				var settings=$.extend({
						hoverClass:"hover",
						currentClass:"current",
						trigger:"click",
						callback:null,
						auto:false,
						detay:3000,
						index:0
					},options);
				var $this=$(this),autoTimer;
				//匹配按钮
				var $buttons=$('.jQtabs-naverPanel > .jQtabmenu',$this),
					i=0,l=$buttons.size()-1;
				var $containers=$('.jQtabs-containerPanel > .jQtabcontent',$this);
				
				$buttons.each(function(i){
					
					if(i==settings.index){
						$containers.eq(i).show();
						$(this).addClass(settings.currentClass);
						settings.index=i;
					}else{
						$containers.eq(i).hide();
					}
					
					$(this).attr('ads',i).bind(settings.trigger,function(){
						var ads=$(this).attr('ads');
						//移除自动运行
						if(settings.auto){
							if(autoTimer) clearInterval(autoTimer);
						}
						$(this).addClass(settings.currentClass);
						$buttons.eq(settings.index).removeClass(settings.currentClass);
						$containers.eq(settings.index).hide().end().eq(ads).show();
						settings.index=ads;
						
						if(settings.callback) settings.callback.call($this,ads,$buttons,$containers);
					}).hover(function(){
							$(this).addClass(settings.hoverClass);
						},function(){
							$(this).removeClass(settings.hoverClass);	
						});
					
				});
				
				
			});
		}
	});
	module.exports=$;
});
