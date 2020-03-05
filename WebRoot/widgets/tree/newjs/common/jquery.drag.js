// JavaScript Document
var _ts;
(function($){
	$.fn.drag=function(json){
		return $(this).each(function(){
			
			//默认配置数据
			var _defaults={
					layout:null,
					data:null,
					option:{
						asyCallBack:null,
						showStyle:null,		//栏目初始方式(保留属性)
						hover:'hover',     	//鼠标划过栏目的class
						reg:/\{\w+\}/gi,
						lc:'{',
						rc:'}',
						rowtpl:'<div id="{id}" class="jQrow {className}"><div class="title"><h2>{title}</h2><div class="tools"><span class="window"></span><span class="lock"></span><span class="close"></span></div></div><div class="content">{src}</div></div>',				//栏目模版
						coltpl:'<div id="{cols}" class="jQcol"></div>'
					}
				};
			//引用this
			var $this=$(this);
			//重设默认配置
			var _data=$.extend(_defaults,json);
			//场景和列对象
			var _table=[],_cols=[];
			//正则匹配模版参数
			var _coltpl=[],_rowtpl=[];
			_coltpl=_data.option.coltpl.match(_data.option.reg);
			_rowtpl=_data.option.rowtpl.match(_data.option.reg);
			
			//初始化
			init();
			
			/**
			 * 初始化：构造场景
			 **/
			function init(){
				var i=0,cols=_data.layout,l=cols.length;
				//生成模版
				var _cache='',el;
				for(i;i<l;i++){
					_cache=getNode(_data.option.coltpl,_coltpl,cols[i]);
					el=$(_cache);
					el.appendTo($this);
					_table.push(el);
				}
				for(i;i>0;i--){
					//创建列元素
					createCols(l-i);
				}
				//绑定事件
				bindClEvent();
				bindElEvent();
				//add(['2003','2004','3003','3004','4003','4004']);
				_ts=_cols;
				//创建添加按钮
				createAddBtn();
			}
			
			/**
			 * 构建列元素
			 **/
			function createCols(j){
				var i=0,_rows=_data.layout[j].rows,l=_rows.length;
				_cols[j]=[];
				var _cache='', v={},el;
				for(i;i<l;i++){
					v=_data.data[_rows[i].id];
					_cache=getNode(_data.option.rowtpl,_rowtpl,v);
					el=$(_cache);
					el.appendTo(_table[j]).data('c',j).data('r',i);
					//for debug
					var isLock=_rows[i]['isLock'],isHide=_rows[i]['isHide'];
					//初始化坐标和属性
					el.attr('c',j).attr('r',i).attr('lock',isLock).attr('window',isHide);
					//如果存在回调，触发回调事件
					var cb=_data.data[_rows[i].id].callBack,content='';
					if(!!cb){
						if(typeof(cb)==='function'){
							//alert('this callBack is a Function!');
							content=cb();
							$('.content',el).html(content);
						}else{
							//alert('this callBack is a String!');
							$.ajax({
								type:'POST',
								url:cb,
								success:function(msg){
									$('.content',el).html(msg);
								}
							});
						};
					}
					//初始化节点状态
					resetElementStage.call(el);
					//end
					_cols[j].push(el);
				}
			}
			
			/**
			 * 初始化节点
			 **/
			function resetElementStage(){
				var isLock=$(this).attr('lock'),isHide=$(this).attr('window'),_this=$(this);
				if(isLock=='true'){
					$(this).find('.tools>span').each(function(){
						if($(this).attr('class')==='lock') $(this).attr('class','ilock');
					});
				}
				if(isHide=='true'){
					$(this).find('.tools>span').each(function(){
						if($(this).attr('class')==='window') $(this).attr('class','iwindow');
					});
					$('.content',_this).css('height','0px');
					//$('.content',_this).animate({height:'0px'},200);
				}
			}
			/**
			 * 获取节点元素
			 **/
			function getNode(tpl,label,tplValue){
				var _t=tpl;						           //模版
				var _r=label,l=_r.length,i=0; 	 //替换标签
				var _v=tplValue;				         //模版变量
				//构造节点				
				for(i;i<l;i++){
					_t=_t.replace(_r[i],_v[_r[i].replace(_data.option.lc,'').replace(_data.option.rc,'')]);
				}
				return _t;
			}
			
			/**
			 * 绑定列事件
			 **/
			function bindClEvent(){
				var i=0,l=_table.length;
				var e;
				for(i;i<l;i++){
					e=_table[i];
					e.hover(function(){
						$(this).addClass('col-hover');
					},function(){
						$(this).removeClass('col-hover');
					});
				}
			}
			
			/**
			 * 拖拽事件
			 **/
			function bindDrag(){
				var $that=$(this);
				//显示工具栏
				$that.find('.tools').css({display:'block',top:'-20px'});
				$('.title',$(this)).hover(
					function(){
						$(this).find('.tools').stop().animate({top:'0px'},200);
					},function(){
						$(this).find('.tools').stop().animate({top:'-20px'},200);
					}
				);
				
				
				//绑定按钮
				$('.tools span',$(this)).hover(function(){
					var cls=$(this).attr('class');
					$(this).attr('class',cls+'Hover');
				},function(){
					var cls=$(this).attr('class');
					$(this).attr('class',cls.replace('Hover',''))
				}).bind('click',function(ev){
					//绑定工具按钮
					var et = ev.target || ev.srcElement;
					var t=$(et).attr('class');
					var isLock=$that.attr('lock'),isHide=$that.attr('window');
					if(t==='windowHover'||t==='iwindowHover'){
						if(isLock==='true') return;
						if(isHide==='true'){
							//显示
							//$('.content',$that).css('display','block');
							$('.content',$that).stop().animate({height:'180px'},200);
							//更改状态
							$that.attr('window','false');
							$(et).attr('class','windowHover');
						}else{
							//隐藏
							//$('.content',$that).css('display','none');
							$('.content',$that).stop().animate({height:'0px'},200);
							//更改状态
							$that.attr('window','true');
							$(et).attr('class','iwindowHover');
						}
					}else if(t==='lockHover'||t==='ilockHover'){
						if(isLock==='true'){
							//更改状态
							$that.attr('lock','false');
							$(et).attr('class','lockHover');
						}else{
							//更改状态
							$that.attr('lock','true');
							$(et).attr('class','ilockHover');
						}
					}else if(t==='closeHover'){
						if(isLock==='true') return;
						var c=$that.attr('c');
						for(var i=0,l=_cols[c].length;i<l;i++){
							if(_cols[c][i].attr('id')==$that.attr('id')){
								_cols[c].splice(i,1);
								$that.remove();
								break;
							}
						}
						//重设元素的坐标
						for(i=0,l=_cols[c].length;i<l;i++){
							_cols[c][i].attr('r',i);
						}
					}
				});
				
				//绑定对象
				$that.hover(function(){
					$(this).addClass('row-hover');
				},function(){
					$(this).removeClass('row-hover');
				}).bind('mousedown',function(ev){
					//锁定时神马都是浮云
					var isLock=$that.attr('lock');
					if(isLock==='true') return;
					//隐藏工具栏
					var et = ev.target || ev.srcElement;
					if(et.tagName!=='H2') return;
					$that.find('.tools').css({display:'none'});
					$('body').css('cursor','move');
					var _nel=$('<div class="jQnel"></div>');
					var _tel=$('<div class="jQtel">jQtel</div>');
					//记录来源位置
					var c=$that.attr('c'),r=$that.attr('r');
					_nel.data('c',c).data('r',r).css({width:$that.width(),height:$that.height()});
					//从原列中删除不参与比较
					var e=_cols[c].splice(r,1);
					var _oc=c,_or=r;
					//创建拖动层
					_tel.appendTo($('body'));
					_tel.css({left:$that.offset().left ,top:$that.offset().top,width:$that.width(),height:$that.height()});
					_tel.html($(this).html());
					//计算鼠标位于元素内部的坐标
					ev = ev || window.event;
					var mousePos = mousePosition(ev);
					var _px=$that.offset().left-mousePos.x,
						_py=$that.offset().top-mousePos.y;
					
					//移除元素
					$(this).before(_nel).remove();
					
					//开始绑定拖拽
					$(document).bind('mousemove',function(ev){
						ev = ev || window.event;
						var mousePos = mousePosition(ev);
						_tel.css({left:mousePos.x+_px,top:mousePos.y+_py});
						//判断坐标
						checkPos(_nel,$that,mousePos.x,mousePos.y);
						
					}).bind('mouseup',function(){
						$('body').css('cursor','default');
						
						/**2011.08.15 end**/
						//当鼠标抬起，位置已经确认
						//加入新的数组
						//coding……
						var _nc=_nel.data('c'),_nr=_nel.data('r');
						resetCol(_nc,_nr,_oc,_or,e);
						//生成返回对象
						//异步保存
						if(_data.option.asyCallBack) _data.option.asyCallBack(returnJSON());
						
						_tel.animate({top:_nel.offset().top,left:_nel.offset().left},{duration: "fast",complete:function(){
							_tel.remove();
							_nel.before($that).remove();							
							$that.removeClass('row-hover');
							//为移除后又加入的元素重新绑定事件
							bindDrag.call($that);
						}});
						$(document).unbind('mousemove').unbind('mouseup');
						//开启可选中
						document.onselectstart = null;
						if(!$.browser.msie) window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
					});
					
					
					//禁止可选中
					document.onselectstart = new Function("return false");
					if(!$.browser.msie) window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
				});
			}
			/**
			 * 判断坐标
			 **/
			 function checkPos(target,el,x,y){
				 var t=target,e=el,_pt=$this.offset().top;

				 //判断元素所在列
				 var _c=_table,_r=_cols;
				 var _cl=_c.length;
				 for(var i=0;i<_cl;i++){
					 var _cx=_c[i].offset().left,_cw=_c[i].width()+_cx;
					 //判断所在列
					 if(x>_cx&&x<_cw){
						 var _rl=_r[i].length;
						 if(_rl>0){
							 //判断所在行
							//第一行不为拖拽对象并且鼠标位置大于第一行
							if(_r[i][0]!==e&&_r[i][0].offset().top>y){
								t.data('c',i).data('r',0).insertBefore(_r[i][0]);
							}
							//第一行为拖拽对象，鼠标位置小于第二行
							else if(_r[i][0]===e&&_rl>1&&_r[i][1].offset().top>y){
								t.data('c',i).data('r',0).insertBefore(_r[i][1]);
							}
							//其他情况
							else{
								for(var j=_rl-1;j>-1;j--){
									if(_r[i][j].offset().top<y){
										t.data('c',i).data('r',j+1).insertAfter(_r[i][j]);
										return;
									}
								}
							}
						 }
						 else{
						 	t.data('c',i).data('r',0).appendTo(_c[i]); 
						 }
					 }
				 }
			 }
			/**
			 * 绑定栏目事件
			 **/
			function bindElEvent(){			
				var i=0,l=_cols.length,_nel,_tel;
				for(i;i<l;i++){
					for(var j=0,k=_cols[i].length;j<k;j++){
						bindDrag.call(_cols[i][j]);
					}
				}
			}
			/**
			 * 重设结构数组
			 **/
			function resetCol(nc,nr,oc,or,e){
				var i,rl,_tr;
				//本列交换
				var d=e;
				if(nr===0){
					if(d[0]) _cols[nc].unshift(d[0]);
				}else{
					if(d[0]) _cols[nc].splice(nr,0,d[0]);
				}
				//其它列
				if(nc!==oc){
					//删除列重设
					//for(i=or,rl=_cols[oc].length;i<rl;i++){
					for(i=0,rl=_cols[oc].length;i<rl;i++){
						_cols[oc][i].attr('c',oc).attr('r',i);
					}
				}
				//新增列重设
				//for(i=nr,rl=_cols[nc].length;i<rl;i++){
				for(i=0,rl=_cols[nc].length;i<rl;i++){
					_cols[nc][i].attr('c',nc).attr('r',i);
				}	
			}
			/**
			 * 生成返回json
			 **/
			function returnJSON(){
				var _json=[],_j='',_c=[];
				for(var i=0,l=_table.length;i<l;i++){
					_j='{cols:"'+_table[i].attr('id')+'", rows:[';
					for(var j=0,k=_cols[i].length;j<k;j++){
						var id=_cols[i][j].attr('id'),isHide=_cols[i][j].attr('window'),isLock=_cols[i][j].attr('lock');
						_c.push('{id:"'+id+'",isHide:"'+isHide+'",isLock:"'+isLock+'"}');
					};
					_j+=_c.join(',')+']}';
					_c=[];
					_json.push(_j);
				}
				return '['+_json.join(',')+']';
			}
			/**
			 * 创建添加按钮
			 */
			function createAddBtn(){
				var html="<div id='jQaddBtn'>添加元素</div>";
				var el=$(html);
				el.appendTo($('body'));
				el.bind('click',function(){
					//循环处理全部数据，抽离出未添加到页面的元素
					var i=j=0,l=_table.length;
					var hased={};
					for(i;i<l;i++){
						var k=_cols[i].length;
						j=0;
						for(j;j<k;j++){
							hased[_cols[i][j].attr('id')]=_data.data[_cols[i][j].attr('id')];
						}
					}
					//待添加的元素
					var selectHTML='<div id="jQselect">';
						for(var o in _data.data){
							if(!hased[o]) selectHTML+='<label for="s_'+o+'"><input name="s_'+o+'" type="checkbox" value="'+o+'">'+_data.data[o].title+'</label>';
						}
						selectHTML+='</div>';
					var jQsed=[];
					$.dialog({
						title:'这是弹出层！',
						content:selectHTML,
						yesFn:function(){
							$('#jQselect input').each(function(){
								if($(this).attr('checked')) jQsed.push($(this).val());
							});
							addNewRows(jQsed);
						},
						noFn:function(){
							//alert('你确定取消吗？');
						}
					});
				});
			}
			/**
			 * 添加新的单元
			 */
			function addNewRows(v){
				var i=0,j=0,l=v.length,cl=_table.length;
				var _cache='',el;
				for(i;i<l;i++){
					_cache=getNode(_data.option.rowtpl,_rowtpl,_data.data[v[i]]);
					el=$(_cache);
					//初始化坐标和属性
					el.appendTo(_table[i%cl]).attr('c',i%cl).attr('r',_cols[i%cl].length).attr('lock',false).attr('window',false);
					//初始化节点状态
					resetElementStage.call(el);
					bindDrag.call(el);
					//end					
					_cols[i%cl].push(el);
				}
			}
		  	//获取鼠标位置
			function mousePosition(ev){
				if(ev.pageX || ev.pageY){
					return {x:ev.pageX, y:ev.pageY};
				}
				return {
					x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
					y:ev.clientY + document.body.scrollTop - document.body.clientTop
				};
			}
			//获取元素的绝对位置
			function rePosition(o) {
				var $x = $y = 0;
				do {
					$x += o.offsetLeft;
					$y += o.offsetTop;
				} while ((o = o.offsetParent)); // && o.tagName != "BODY"
				return { x : $x, y : $y };
			}
		});
	}	
})(jQuery);