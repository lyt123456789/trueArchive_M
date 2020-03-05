/** 
 * @fileoverview 鼠标滑过变换效果
 * @author purecolor@foxmail.com
 */
;define(function(require, exports, module){
	var $=require("jquery");
	$.fn.hoverel=function(options){
		return $(this).each(function(){
			var settings=$.extend({
				currentClass:'hover',
				trigger:'mouseover',
				callback:null,
				auto:false,
				detay:3000,
				index:0
			},options);
			
			var $this=$(this);
			var items=$('.menu',$this),i=0,l=items.size()-1;
			var autoTimer,curro=0;
			items.each(function(){
				$(this).data('no',i);
				if(i==settings.index){
					$(this).addClass(settings.currentClass);
				}
				i++;
				$(this).bind(settings.trigger,function(e){
					if(params.auto){
						if(autoTimer) clearInterval(autoTimer);
					}
					move($(this).data("no"));
				});
			});
			function move(i){
				items.eq(settings.index).removeClass(settings.currentClass);
				items.eq(i).addClass(settings.currentClass);
				settings.index=i;
				if(settings.callback) settings.callback.call($this,i);
			}
			function auto(){
				if(settings.auto){
					autoTimer=setInterval(function(){
						curro=(settings.index>=1)?0:(settings.index+1);
					},settings.detay);
				}else{
					if(autoTimer) clearInterval(autoTimer);
				}
			}
			auto();
		});
	}
	module.exports=$;
});
