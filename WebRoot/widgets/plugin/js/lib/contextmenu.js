// JavaScript Document
;define(function(require, exports, module){
	var $=require("jquery");
	//会议控制类
	$.extend($.fn,{
		contextmenu:function(options){
			var settings=$.extend({
					context:null,
					events:[{tag:'返回',cls:''}],
					eventsFn:function(){},
					prefix:'mice-map-'
				},options);
			return this.each(function(){
				var $this=$(this),_this=this,$menu,$menubg;
				if($('#'+settings.prefix+'contextmenu').attr('id')===settings.prefix+'contextmenu'){
					$menu=$('#'+settings.prefix+'contextmenu');
					$menubg=$('#'+settings.prefix+'contextmenu-bg');
					//重新指向this
					$('>li',$menu).each(function(i){
						$(this).unbind('click').bind('click',function(){
							settings.eventsFn.call(((settings.context!=null)?settings.context:_this),i);
						})
					});
				}else{
					var _menu='<ul id="'+settings.prefix+'contextmenu" class="mice-contextmenu" style="z-index:999;position:absolute;left:0;top:0;display:none;"></ul>';
					var _menubg='<div id="'+settings.prefix+'contextmenu-bg" class="mice-contextmenu-bg" style="z-index:998;position:absolute;left:0;top:0;display:none;"></div>';
					$menu=$(_menu).appendTo('body');
					$menubg=$(_menubg).appendTo('body');
					var _li=[];
					for(var i=0,l=settings.events.length;i<l;i++){
						_li.push('<li class="'+settings.events[i].cls+'">'+settings.events[i].tag+'</li>');
					}
					$menu.append(_li.join('')).find('>li').each(function(i){
						$(this).bind('click',function(){
							settings.eventsFn.call(((settings.context!=null)?settings.context:_this),i);
						})
					});
					_li=null;
					_menu=null;
					_menubg=null;
				}
				$menu.css({left:($(this).offset().left+$(this).width())+'px',top:($(this).offset().top+$(this).height())+'px'}).fadeIn(50);
				$menubg.css({left:($(this).offset().left+$(this).width()+3)+'px',top:($(this).offset().top+$(this).height()+3)+'px',width:$menu.width()+'px',height:$menu.height()+'px'}).fadeIn(100);
				$(window).bind('click',function(e){
					var e=window.event||e;
					var el=e.target||e.srcElement;
					if(el.id!==settings.prefix+'contextmenu'){
						$menu.fadeOut(100);
						$menubg.fadeOut(15);
					}
				})
			});
		}
	});
});