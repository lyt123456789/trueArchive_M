// JavaScript Document
;define(function(require, exports, module){
	var $=require("jquery");
	$.extend($.fn,{
		tips:function(options){
			var settings=$.extend({
					trigger:'mouseover', //触发事件
					autohide:true, //是否自动隐藏
					flowmouse:true, //是否跟随鼠标
					detay:20,	//延迟关闭事件
					maxw:300, //最大宽度
					layout:'auto' //tips显示方位[top, right, bottom, left, auto]
				},options);
			return this.each(function(){
				var $this=$(this).attr('abletips','true');
				var id=($(this).attr('id')!=undefined)?$(this).attr('id'):'tips-'+new Date().getTime(),dataTips=$(this).attr('data-tips');
				
				$(this).bind(settings.trigger,function(e){
					if($this.attr('abletips')==='false') return ;
					$this.attr('abletips','false');				
					var e=e||window.event ,mousePos;
					if(settings.flowmouse){
						mousePos=mousePosition(e);
					}else{
						mousePos=$(this).offset();
					}
					//取得屏幕限制
					function getPos(pos){
						var l=t=0,c='',e='';
						if(settings.flowmouse){
							if($(window).width()-pos.left>settings.maxw+40){
								l=30;
								c="l";
							}else if(pos.left>settings.maxw+40){
								l=-30-this.width();
								c="r";
							}else{
								l=30;
								c='l';
							}
							t=-8;
						}else{
							if($(window).height()-pos.top-$this.height()>this.height()+8){
								t=pos.top+$this.height()+8;
								e="t";
							}else if(pos.top>this.height()+8){
								t=pos.top-this.height()-8;
								e="b";
							}
							l=pos.left;
						}
						return {'left':l,'top':t, 'c':c,'e':e}
					}
					
					var $tpl=$('<div class="mice-tips" style="position:absolute;z-index:999;top:'+(mousePos.top)+'px;left:'+(mousePos.left+30)+'px;"></div>').append('<span class="mice-arror-icon"></span><div class="mice-top"><div class="c">&nbsp;</div><span class="l"></span><span class="r"></span></div><div class="mice-content"><div>'+dataTips+'</div></div><div class="mice-bottom"><div class="c">&nbsp;</div><span class="l"></span><span class="r"></span></div>');
					//当前屏幕的高宽
					var winPos={mw:$(window).width() ,mh:$(window).height()};
					$tpl.appendTo($('body')).css({'left':0,'top':-1000}).each(function(){
						var lt=getPos.call($tpl,mousePos),_left=_top=0;
						if(settings.flowmouse){
							_left=mousePos.left+lt.left;
							_top=mousePos.top+lt.top;
						}else{
							_left=lt.left;
							_top=lt.top;
						}
						$(this).attr('c',lt.c).css({'left':_left+'px','top':_top+'px'}).find('> .mice-arror-icon').addClass('mice-arror-icon-'+lt.c+lt.e);
					}).fadeIn();		
					//是否跟随鼠标
					if(settings.flowmouse){
						//绑定移动
						$(document).bind('mousemove',function(e){
							e = e || window.event;
							var mousePos = mousePosition(e);
							$tpl.each(function(){
								var lt=$(this).offset(),_left=_top=0;
								if(settings.flowmouse){
									if($(this).attr('c')==='r'){
										_left=mousePos.left-30-$(this).width();
									}else{
										_left=mousePos.left+30;
									}
									_top=mousePos.top-8;
									$(this).css({'left':_left+'px','top':_top+'px'});
								}
							});
						});
					}
					//移除事件
					$(this).bind('mouseout',function(){
						if(settings.autohide){
							$tpl.fadeOut('fast',function(){
								$tpl.remove();
								$(document).unbind('mousemove');
								$this.attr('abletips','true');
							});
						}
					});
					//end code								
				})
			});
			
		}
	});
	//获取鼠标位置
	function mousePosition(ev){
		if(ev.pageX || ev.pageY){
			return {left:ev.pageX, top:ev.pageY};
		}
		return {
			left:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
			top:ev.clientY + document.body.scrollTop - document.body.clientTop
		};
	}
	//加入modlue
	module.exports=$;
});