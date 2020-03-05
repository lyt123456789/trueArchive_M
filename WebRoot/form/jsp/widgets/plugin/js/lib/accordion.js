// JavaScript Document
;define(function(require, exports, module){
	var $=require('jquery');
	$.extend($.fn,{
		//init
		accordion:function(options){
			var settings=$.extend({
				trigger:'click',
				detay:300,
				index:0,
				opened:'collapsable',
				ableSelf:false,
				render:'',
				completeFn:function(){},
				triggerFn:function(){}
			},options);
			
			return this.each(function(){
				var $this=$(this);
				$('.accordionContent',$this).each(function(){
					$(this).css({'display':'none'});
				});
				$('.accordionHeader',$this).each(function(){
					$(this).bind(settings.trigger, function(){
						var $next=$(this).next('.accordionContent:hidden');
						if($next.css('display')==='none'){
							$('.'+settings.opened, $this).removeClass(settings.opened);
							$('.accordionContent:visible', $this).slideUp('normal');
							$next.slideDown('normal').css('overflow','auto');
							$(this).find('h2').addClass(settings.opened);
						}
						//点击的回调
						settings.triggerFn.call(this);
					}).hover(function(){
						$(this).addClass('hover');
					},function(){
						$(this).removeClass('hover');
					});
				}).eq(settings.index).next('.accordionContent').slideDown('normal',function(){
					$this.initAccordionLayout();
				}).end().each(function(){
					settings.triggerFn.call(this);
				});
				settings.completeFn.call(this);
			});
		},
		//layout
		initAccordionLayout:function(){
			return this.each(function(){
				var $this=$(this);
				var aH=$this.height(), atH=$('.accordionHeader', $this).height(), aiSize=$('.accordionHeader', $this).size(), acH=aH-aiSize*atH;
				$('.accordionContent', $this).each(function(i){
					$(this).css({'height':acH}).css('overflow','auto');
				});
			});
		},
		//json=[{title:String ,classname:String ,fillObject:Object}];
		addAccordion:function(options){
			var settings=$.extend({
				render:'',
				data:null,
				completeFn:function(){$(this).accordion();}
			},options)
			if(!settings.data) return ;
			var tpl='<div class="accordionHeader {classname}" data-id="{id}"><h2><span>Folder</span>{title}</h2></div>';
			return this.each(function(){
				var $this=$(this),json=settings.data;
				var $build=function(json){
					for(var i=0,l=json.length;i<l;i++){
						var $tplc=$('<div class="accordionContent" style="overflow:hidden;"></div>');
						$this.append(tpl.replace('{classname}',json[i].classname).replace('{id}',json[i].id).replace('{title}',json[i].title));
						$this.append($tplc);
					}
				}(json);
				settings.completeFn.call(this);
			});
		}
	});
	module.exports=$;
});