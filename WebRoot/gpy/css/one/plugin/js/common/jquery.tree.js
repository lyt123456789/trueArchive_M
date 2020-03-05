(function($){
	$.fn.extend({
			creatTree:function(options){
				return $(this).each(function(){
					var defaults={
						eml:'li',
						currentClass:"currentOne",
						trigger:"mouseover"
					}
					var o=$.extend(defaults,options);
					var $this=$(this);
					var items=$(o.eml,$this);
					items.each(function(){
						$(this).bind(o.trigger,function(){
							var that=$(this).find('ul:first');
							if(that.size()>0){
								//alert(that.find('li:first ~ li').size());
								that.css({'display':'block'});
							}
						}).bind('mouseout',function(){
							var that=$(this).find('ul:first');
							if(that.size()>0){
								that.css({'display':'none'});
							}
						});
					});
				});
			}
		});
})(jQuery);