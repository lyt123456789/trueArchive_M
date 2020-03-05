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
	$.fn.tabBuild=function(options){
		return $(this).each(function(){
			var defaults={
				currentClass:"jQtabone",
				trigger:"mouseover",
				callBack:null,
				auto:false,
				detay:3000,
				index:0,
				timer:null
			}
			var params=$.extend(defaults,options);
			var $this=$(this);
			var items=$('.jQtabmenu',$this),i=0,l=items.size()-1;
			var autoTimer,curro=0;
			
			items.each(function(){
				$(this).data('lvl',i);
				if(i==params.index){
					$('.jQtabcontent',$this).eq(i).show();
					$('.jQtabmore',$this).eq(i).show();
					$(this).addClass(params.currentClass);
				}else{
					$('.jQtabcontent',$this).eq(i).hide();
					$('.jQtabmore',$this).eq(i).hide();
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
				$(this).bind("mouseout",function(e){
					if(params.timer!==null){
						clearTimeout(params.timer);
						params.timer=null;
					}
				});
			});
			//移动播放
			function move(i){
				if(params.timer!==null){
					clearTimeout(params.timer);
					params.timer=null;
				}
				params.timer=setTimeout(function(){
					//移除上一个效果
					items.eq(params.index).removeClass(params.currentClass);
					$('.jQtabcontent',$this).eq(params.index).hide();
					$('.jQtabmore',$this).eq(params.index).hide();
					//移至当前位置
					items.eq(i).addClass(params.currentClass);
					$('.jQtabcontent',$this).eq(i).show();
					$('.jQtabmore',$this).eq(i).show();
					params.index=i;
					if(params.callBack) params.callBack.call($this,i);
				},180); 
				
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
	};
	
	
	




	$.fn.tabBuild_to=function(options){
		return $(this).each(function(){
			var defaults={
				currentClass:"jQtabone",
				trigger:"mouseover",
				callBack:null,
				auto:false,
				detay:3000,
				index:0,
				timer:null
			}
			var params=$.extend(defaults,options);
			var $this=$(this);
			var items=$('.tabmenu',$this),i=0,l=items.size()-1;
			var autoTimer,curro=0;
			
			items.each(function(){
				$(this).data('lvl',i);
				if(i==params.index){
					$('.tabcontent',$this).eq(i).show();
					$('.tabmore',$this).eq(i).show();
					$(this).addClass(params.currentClass);
				}else{
					$('.tabcontent',$this).eq(i).hide();
					$('.tabmore',$this).eq(i).hide();
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
				$(this).bind("mouseout",function(e){
					if(params.timer!==null){
						clearTimeout(params.timer);
						params.timer=null;
					}
				});
			});
			//移动播放
			function move(i){
				if(params.timer!==null){
					clearTimeout(params.timer);
					params.timer=null;
				}
				params.timer=setTimeout(function(){
					//移除上一个效果
					items.eq(params.index).removeClass(params.currentClass);
					$('.tabcontent',$this).eq(params.index).hide();
					$('.tabmore',$this).eq(params.index).hide();
					//移至当前位置
					items.eq(i).addClass(params.currentClass);
					$('.tabcontent',$this).eq(i).show();
					$('.tabmore',$this).eq(i).show();
					params.index=i;
					if(params.callBack) params.callBack.call($this,i);
				},180); 
				
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
	};
	
	
	
	
	//鼠标移过变换class
	$.fn.classBuild=function(options){
		return $(this).each(function(){
			var defaults={
				currentClass:"hover",
				trigger:"mouseover",
				callBack:null,
				auto:false,
				detay:3000,
				index:0
			}
			var params=$.extend(defaults,options);
			var $this=$(this);
			var items=$('.menu',$this),i=0,l=items.size()-1;
			var autoTimer,curro=0;
			
			items.each(function(){
				$(this).data('lvl',i);
				if(i==params.index){
					$(this).addClass(params.currentClass);
				}
				i++;
				$(this).bind(params.trigger,function(e){
					//移除自动运行
					if(params.auto){
						if(autoTimer) clearInterval(autoTimer);
					}
					move($(this).data("lvl"));
				});
			});
			//移动播放
			function move(i){
				//移除上一个效果
				items.eq(params.index).removeClass(params.currentClass);
				//移至当前位置
				items.eq(i).addClass(params.currentClass);
				params.index=i;
				if(params.callBack) params.callBack.call($this,i);
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
	};
	
	//隔行换色
	$.fn.swapRowColor=function(options){
		return $(this).each(function(){
			var defaults={
				currentClass:"hover",
				trigger:"click",
				callBack:null,
				even:'even',
				odd:'odd',
				target:null
			}
			var params=$.extend(defaults,options);
			var $this=$(this);
			var items=$(params.target,$this),i=1;
			var autoTimer,curro=0;
			
			items.each(function(){
				(i%2==0)?$(this).addClass(params.even).data('cachs',params.even):$(this).addClass(params.odd).data('cachs',params.odd);
				i++;
				$(this).bind(params.trigger,function(e){
					if(params.callBack) params.callBack.call($this,e);
				}).hover(function(){
					$(this).removeClass($(this).data('cachs')).addClass(params.currentClass);
				},function(){
					$(this).removeClass(params.currentClass).addClass($(this).data('cachs'));
				});
			});
		});
	};
})(jQuery);

/**
 * @description 设置图片按钮切换效果
 * @param {String} key  元素钩子
 * @return {Null} null null
 */
function bindImgButtonEvents(key){
	$(key).each(function(){
		var src=$(this).attr('src');
		$(this).data('p',src.replace(src.split('/')[src.split('/').length-1],'')).data('i',src.split('/')[src.split('/').length-1]);
		$(this).hover(function(){
			$(this).attr('src',$(this).data('p')+'o.'+$(this).data('i'));
		},function(){
			$(this).attr('src',$(this).data('p')+$(this).data('i'));
		});
	});
}
bindImgButtonEvents('.jQibtn');