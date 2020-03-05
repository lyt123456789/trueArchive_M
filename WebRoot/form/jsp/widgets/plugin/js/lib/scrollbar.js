/**
 *@fileoverview A tools of scroll bar for overflow element
 *@author:purecolor@foxmail.com
 */
;define(function(require,exports,module){
	var $=require('jquery');
	$.fn.scrollbar=function(options){
		return $(this).each(function(){
			var setting=$.extend({
							id:!!$(this).attr('id')?$(this).attr('id'):'scrollbar_'+(new Date).getTime(),
							height:$(this).height(),
							containerHeight:0							
						},options);
			var scrolls={
					scrollHeight:0,
					scrollScale:0,
					scrollBtnHeight:30
				};
			var $this=$(this),$that,$st,$timer;
			$this.css('position','relative');
			//创建遮罩层
			$mask=$('<div id="'+setting.id+'_mask" class="containermask" style="overflow:hidden;"></div>');
			$mask.append('<div class="scrollContainer">'+$this.html()+'<div>');
			$this.html('').append($mask);
			//绑定$that
			$that=document.getElementById(setting.id+'_mask');
			
			//滚动条容器
			$scrollBar=$('<div class="scrollBar" style="height:'+(setting.height-10)+'px;width:10px;position:absolute;display:none;top:5px;right:5px;z-index:88888;"><div class="scrollmask" style="position:relative;"></div></div>');
			//背景
			$scrollBg=$('<div class="scrollBg" style="height:'+(setting.height-10)+'px;background-color:#666;filter:alpha(opacity=30);-moz-opacity:0.3;-khtml-opacity: 0.3;opacity: 0.3;"></div>');
			//按钮
			$scrollBtn=$('<span class="scrollBtn" style="background-color:#000;filter:alpha(opacity=60);-moz-opacity:0.6;-khtml-opacity: 0.6;opacity: 0.6;width:100%;display:block;height:'+scrolls.scrollBtnHeight+'px;position:absolute;top:0px;left:0px;z-index:99999;"></span>');
			//组合控件
			$scrollBar.find('.scrollmask').append($scrollBtn).append($scrollBg);
			//加入监听
			addEvent($that,'mousewheel',function(e){
				stopEvent(e);
				var delta = getWheelValue(e);
				var sTop=$that.scrollTop;
				sTop=range(parseInt(sTop)+(-1*delta*20),scrolls.scrollHeight*scrolls.scrollScale,0);
				$that.scrollTop=sTop;
				if(sTop<1)
					$scrollBtn.css('top',0+'px');
				else if(sTop>scrolls.scrollHeight-scrolls.scrollBtnHeight)
					$scrollBtn.css('top',scrolls.scrollHeight+'px');
				else
					$scrollBtn.css('top',range(sTop/scrolls.scrollScale,scrolls.scrollHeight,0));
				return false;
			});
			//显示滚动条
			$this.append($scrollBar).bind('mouseover',function(){
				//内容高度
				$mask.css('height',$(this).height());
				setting.containerHeight = $mask.find('>.scrollContainer').height();
				//可滑动高度
				scrolls.scrollBtnHeight=30;
				scrolls.scrollHeight=setting.height-10-scrolls.scrollBtnHeight;
				//滚动比例
				scrolls.scrollScale=(setting.containerHeight-setting.height)/scrolls.scrollHeight;
				$scrollBar.fadeIn(200);
				//if(setting.containerHeight>setting.height) 
			}).bind('mouseleave',function(){
				$scrollBar.fadeOut(200);
			});
			//拖拽事件
			$scrollBtn.bind('mouseover',function(){
				$(this).css('background-color','#F30');
			}).bind('mouseout',function(){
				$(this).css('background-color','#000');
			}).bind('mousedown',function(e){
				$st=new Date().getTime();
				$($that).attr('ot',$that.scrollTop);
				//独占事件
				this.setCapture();
				var dv=getMousePos(e).y-$this.offset().top+5;
				var th=$(this).css('top');
				th=th.replace(/px+/g,'');
				$(this).bind('mousemove',function(e){
					var thisT=getMousePos(e).y-dv+(+th);	
					thisT=range(thisT,(setting.height-10-scrolls.scrollBtnHeight),0);
					$(this).css('top',thisT);
					$that.scrollTop=Math.floor(thisT*scrolls.scrollScale);
				})
			}).bind('mouseup',function(){
				$(this).unbind('mousemove').trigger('mouseout');
				this.releaseCapture();
				$end=new Date().getTime()-$st;
				var speed=$that.scrollTop-$($that).attr('ot');
				$timer=setInterval(function(){
					speed=speed/($end/50);
					$that.scrollTop+=speed
					if(Math.abs(speed)<4){
						clearInterval($timer);
					}
				},15);
				
			});
		});
		//获取鼠标绝对位置
		function getMousePos(e){
			var e=e||window.event;
			var x,y;
			if(e.pageX&&e.pageY){
				x = e.pageX;
				y = e.pageY;
			}
			else if (e.clientX&&e.clientY){
				x = e.clientX + (document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft);
				y = e.clientY + (document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop);		
			}else{
				x = 0;
				y = 0;
			}
			return {'x':x,'y':y};
		}
		//取得滚动值
		function getWheelValue( e )
		{
			e = e||event;
			return ( e.wheelDelta ? e.wheelDelta/120 : -( e.detail%3 == 0 ? e.detail/3 : e.detail ) ) ;
		}
		function stopEvent(e)
		{
			e = e||event;
			if( e.preventDefault )e.preventDefault();
			e.returnValue = false;
		} 
		//绑定事件,这里对mousewheel做了判断,注册时统一使用mousewheel
		function addEvent( obj,type,fn )
		{
			var isFirefox = typeof document.body.style.MozUserSelect != 'undefined';
			if( obj.addEventListener )
				obj.addEventListener( isFirefox ? 'DOMMouseScroll' : type,fn,false );
			else
				obj.attachEvent( 'on'+type,fn );
			return fn;
		}
		//移除事件,这里对mousewheel做了兼容,移除时统一使用mousewheel
		function delEvent( obj,type,fn )
		{
			var isFirefox = typeof document.body.style.MozUserSelect != 'undefined';
			if( obj.removeEventListener )
				obj.removeEventListener( isFirefox ? 'DOMMouseScroll' : type,fn,false );
			else
				obj.detachEvent( 'on'+type,fn );
		}
		/*限制范围函数,
		参数是三个数字,如果num 大于 max, 则返回max， 如果小于min，则返回min,如果在max和min之间，则返回num
		*/
		function range( num, max,min )
		{
			return Math.min( max, Math.max( num,min ) );
		}
	}
	module.exports=$;
});
