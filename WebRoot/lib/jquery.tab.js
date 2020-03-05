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
				index:0
			}
			var params=$.extend(defaults,options);
			var $this=$(this);
			var items=$('.jQtabmenu',$this),i=0,l=items.size()-1;
			var autoTimer,curro=0;
			
			items.each(function(){
				$(this).data('lvl',i);
				if(i==params.index){
					$('.jQtabcontent',$this).eq(i).show();
					$(this).addClass(params.currentClass);
				}else{
					$('.jQtabcontent',$this).eq(i).hide();
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
				$('.jQtabcontent',$this).eq(params.index).hide();
				//移至当前位置
				items.eq(i).addClass(params.currentClass);
				$('.jQtabcontent',$this).eq(i).show();
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
	//
	$.fn.easNaver=function(el,_l,_t,dety){
		var $this=$(this),$el;
		if(!!el) $el=$(el,$this);
		else $el=$this;
		$el.each(function(){
			$(this).hover(function(){
				move($(this),0,0,_t,dety,'g');
			},function(){
				move($(this),0,0,_t,dety,'b');
			});
		});
		//缓动函数
		var Tween={
			Cubic: {
				easeIn: function(t,b,c,d){
					return c*(t/=d)*t*t + b;
				},
				easeOut: function(t,b,c,d){
					return c*((t=t/d-1)*t*t + 1) + b;
				},
				easeInOut: function(t,b,c,d){
					if ((t/=d/2) < 1) return c/2*t*t*t + b;
					return c/2*((t-=2)*t*t + 2) + b;
				}
			}
		};
		//运动函数
		function move(o,t,b,c,d,s){
			var t=t,b=b,c=c,d=d;
			function a(){
				var speed=Math.ceil(Tween.Cubic.easeInOut(t,b,c,d));
				if(s=='g')
					o.css({'background-position':_l+'px '+(speed-b)+'px'});
				else
					o.css({'background-position':_l+'px '+(c-speed+b)+'px'});
				if(t<d){t++;setTimeout(arguments.callee, 10);}
			}
			a();
		}
		
	}
	/**
	 *	jQuery插件
	 *	Author:	purecolor@foxmail.com
	 *	Date  :	2011-01-06
	 *	Params: el:Element ,option:	{outAnimateStyle:[Array,……],overAnimateStyle:[Array,……],outUnanimateStyle:[Array,……],overUnanimateStyle:[Array,……]}
	 *			Array:[属性名,值]
	 *  Note  : AnimateStyle遇到CSS属性中有"-"时需转化模式，如：background-color 转变为 backgroundColor 
	 *		    UnanimateStyle则使用标准模式，如：background-color 
	 */
	//二级下拉菜单
	$.fn.animateListMenu=function(el,option){
			//注册方法
			return this.each(function(){
				//存储展开节点
				var _temp='';
				//自定义接口
				var _default={
					trigger:"click",
					detay:300,
					index:0,
					opened:'open',
					closed:'close',
					outAnimateStyle:[],
					overAnimateStyle:[],
					outUnanimateStyle:[],
					overUnanimateStyle:[],
					unel:''
				};
				var params=$.extend(_default,option);
				
				//组合属性
				var _outAnimateStyle={};
				var _overAnimateStyle={};
				var _outUnanimateStyle={};
				var _overUnanimateStyle={};
				
				for(var i=0,len=params.outAnimateStyle.length;i<len;i++){
					_outAnimateStyle[params.outAnimateStyle[i][0]]=params.outAnimateStyle[i][1];
				}
				for(var i=0,len=params.overAnimateStyle.length;i<len;i++){
					_overAnimateStyle[params.overAnimateStyle[i][0]]=params.overAnimateStyle[i][1];
				}
				for(var i=0,len=params.outUnanimateStyle.length;i<len;i++){
					_outUnanimateStyle[params.outUnanimateStyle[i][0]]=params.outUnanimateStyle[i][1];
				}
				for(var i=0,len=params.overUnanimateStyle.length;i<len;i++){
					_overUnanimateStyle[params.overUnanimateStyle[i][0]]=params.overUnanimateStyle[i][1];
				}
				//alert(_overAnimateStyle.toSource());
				
				var o=option;
				var $this=$(this);
				var items=$(el,$this);
				items.each(function(){
					$(this).bind(params.trigger,function(e){
						_temp=(_temp=='')?$(this):_temp;
						if(_temp!=$(this)){
							_temp.animate(_outAnimateStyle,params.detay).css(_outUnanimateStyle).removeClass(params.opened).addClass(params.closed);
							_temp.parent().animate({height:_temp.height()},params.detay);
							_temp=$(this);	
						}
						if($(this).parent().height()>$(this).height()){
							$(this).animate(_outAnimateStyle,params.detay).css(_outUnanimateStyle).removeClass(params.opened).addClass(params.closed);
							$(this).parent().animate({height:$(this).height()},params.detay);
						}else{
							$(this).animate(_overAnimateStyle,params.detay).css(_overUnanimateStyle).removeClass(params.closed).addClass(params.opened);
							$(this).parent().animate({height:$(this).next().innerHeight()+$(this).height()},params.detay);
						}
					});
				}).eq(params.index).trigger(params.trigger);
			});
		}
	/**
	全局展开
	**/
	$.fn.treePanel=function(el,option){
		return this.each(function(){
			//存储展开节点
			var _temp='';
			//自定义接口
			var _default={
				trigger:"click",
				detay:300,
				index:0,
				opened:'open',
				closed:'close',
				childrenel:'div'
			};
			var params=$.extend(_default,option);
			var o=option;
			var $this=$(this);
			var items=$(el,$this);
			items.each(function(){
				$(this).bind(params.trigger,function(e){
					_temp=(_temp=='')?$(this):_temp;
					/**if(_temp!=$(this)){
						_temp.removeClass(params.opened).addClass(params.closed);
						_temp.parent().animate({height:_temp.height()},params.detay);
						_temp=$(this);	
					}**/
					if($(this).parent().height()>$(this).height()){
						$(this).removeClass(params.opened).addClass(params.closed);
						$(this).parent().animate({height:$(this).height()},params.detay);
					}else{
						$(this).removeClass(params.closed).addClass(params.opened);
						$(this).parent().animate({height:$(this).next(params.childrenel).innerHeight()+$(this).height()},params.detay);
						/**if($(this).attr('exception')==='true'){
							$(this).parent().css({'overflow':'visible'});
						}**/
					}
				});
			}).eq(params.index).trigger(params.trigger);
		});
	}
	/**
	treeLayout
	**/
	$.fn.treeLayout=function(el,option){
		return this.each(function(){
			//存储展开节点
			var _temp='';
			//自定义接口
			var _default={
				trigger:"click",
				detay:300,
				index:0,
				opened:'open',
				closed:'close',
				childrenel:'div',
				moreHeight:-30
			};
			var params=$.extend(_default,option);
			var o=option;
			var $this=$(this);
			var items=$(el,$this);
			var canvasHeight=$this.innerHeight();
			var windowHeight=Math.max($(window).height(),$(document).height());
			var LayoutHeight=windowHeight-canvasHeight-params.moreHeight;
			items.each(function(){
				$(this).bind(params.trigger,function(e){
					_temp=(_temp=='')?$(this):_temp;
					if(_temp!=$(this)){
						_temp.removeClass(params.opened).addClass(params.closed);
						_temp.parent().animate({height:_temp.height()},params.detay);
						_temp=$(this);	
					}
					if($(this).parent().height()>$(this).height()){
						$(this).removeClass(params.opened).addClass(params.closed);
						$(this).parent().animate({height:$(this).height()},params.detay);
					}else{
						$(this).removeClass(params.closed).addClass(params.opened);
						$(this).parent().animate({height:LayoutHeight},params.detay);//$(this).next(params.childrenel).innerHeight()+$(this).height()
						$(this).next(params.childrenel).css({height:LayoutHeight-30+'px'});
					}
				});
			}).eq(params.index).trigger(params.trigger);
		});
	}
	
	
	//script end
})(jQuery);