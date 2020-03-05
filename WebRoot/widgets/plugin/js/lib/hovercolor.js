;define(function(require, exports, module){
	var $=require('jquery');
	$.fn.hovercolor=function(options){
		return $(this).each(function(){
			var settings=$.extend({
				currentClass:'hover',
				trigger:'click',
				callback:null,
				event:'even',
				odd:'odd',
				target:null
			},options);
			var $this=$(this);
			var items=$(settings.target,$this),i=1;
			var autoTimer,curro=0;
			items.each(function(){
				(i%2==0)?
					$(this).addClass(settings.event).data('data-cls',settings.event)
					:$(this).addClass(settings.odd).data('data-cls',settings.odd);
				i++;
				$(this).bind(settings.trigger,function(e){
					if(settings.callback) settings.callback.call($this,e);
				}).hover(function(){
					$(this).removeClass($(this).data('data-cls')).addClass(settings.currentClass);
				},function(){
					$(this).removeClass(settings.currentClass).addClass($(this).data('data-cls'));
				});
			})
		});
	};
	
	//接口入口
	module.exports=$;
});
