
$(document).ready(function() {
	
	setTimeout(function(){
		initLayout();
		// initUI();
	},500)
	// $(".wf-list-wrap").find("[layoutH]").layoutH()


	



	


	$(window).resize(function(){
		// $(".wf-layout").height($(window).height());
		$(".wf-list-wrap").find("[layoutH]").layoutH()
	});
	
	inputDateInit(".wf-form-date")



});


function initLayout(){
	$(".wf-layout").height($(window).height());
	

	// $('.wf-layout').height($(window).height())

	if ($.fn.fixTable) $("table.wf-fixtable").fixTable();
	var _heightTop = $(".wf-list-top").outerHeight(true);
	var _heightFoot = $(".wf-list-ft").outerHeight(true);
	var _heightHead = $(".wf-fixtable-header").outerHeight(true);
	var _layoutH = _heightTop + _heightFoot + _heightHead + 10;
	if($(".wf-fixtable-wrap").length){
		$(".wf-fixtable-scroller").attr('layoutH',_layoutH)
	}

	$(".wf-list-wrap").find("[layoutH]").layoutH();
	$(".wf-form-date").datePick();
	$(".wf-table-fixed").tdAutoHide();
	
		$(".loading").addClass("undis");//2017-11-10
}


function initListUI(){

	//tables
	
}

function inputDateInit(obj){
	$(obj).click(function(){
		laydate();
	})
}


var gdz=20;
(function($){
	$.fn.fixTable = function(options){
		return this.each(function(){
		 	var $table = $(this), nowrapTD = $table.attr("nowrapTD");
		 	var tlength = $table.width();
			var theight=$table.height();
			if(theight<=$(window).height()-110){
				gdz=0;
			}
			var aStyles = [];
			var $tc = $table.parent().addClass("j-resizeGrid"); // table parent container
			var layoutH = $(this).attr("layoutH");

			var oldThs = $table.find("thead>tr:last-child").find("th");

			for(var i = 0, l = oldThs.size(); i < l; i++) {
				var $th = $(oldThs[i]);
				var style = [];
				var width = $th.innerWidth()-gdz/tlength;
				style[0] = parseInt(width);
				style[1] = $th.attr("align");
				aStyles[aStyles.length] = style;
			}
			$(this).wrap("<div class='wf-fixtable-wrap'></div>");
			var $grid = $table.parent().html($table.html());
			var thead = $grid.find("thead");
			thead.wrap("<div class='wf-fixtable-header'><div class='wf-fixtable-thead'><table id='main'  style='width:" + (tlength - gdz) + "px;'></table></div></div>");

			var lastH = $(">tr:last-child", thead);
			var ths = $(">th", lastH);
			$("th",thead).each(function(){
				var $th = $(this);
				$th.html("<div class='wf-fixtable-col' title='"+$th.text()+"'>"+ $th.html() +"</div>");	
			});
			
			ths.each(function(i){
				var $th = $(this), style = aStyles[i];
				$th.addClass(style[1]).hoverClass("wf-hover").removeAttr("align").removeAttr("width").width(style[0]);
			});

			var tbody = $grid.find(">tbody");
			var layoutStr = layoutH ? " layoutH='" + layoutH + "'" : "";


			tbody.wrap("<div class='wf-fixtable-scroller'" + layoutStr + " style='width:" + $tc.width() + "px;'><div class='wf-fixtable-tbody'><table style='width:" + (tlength - gdz) + "px;'></table></div></div>");
			var ftr = $(">tr:first-child", tbody);
			var $trs = tbody.find('>tr');
			
			$trs.hoverClass().each(function(){
				var $tr = $(this);
				var $ftds = $(">td", this);

				for (var i=0; i < $ftds.size(); i++) {
					var $ftd = $($ftds[i]);
					if (nowrapTD != "false") $ftd.html("<div>" + $ftd.html() + "</div>");
					if (i < aStyles.length) $ftd.addClass(aStyles[i][1]);
				}		
				$tr.click(function(){
					$trs.filter(".wf-actived").removeClass("wf-actived");
					$tr.addClass("wf-actived");
					var sTarget = $tr.attr("target");
					if (sTarget) {
						if ($("#"+sTarget, $grid).size() == 0) {
							$grid.prepend('<input id="'+sTarget+'" type="hidden" />');
						}
						$("#"+sTarget, $grid).val($tr.attr("rel"));
					}
				});
			});
			
			$(">td",ftr).each(function(i){
				if (i < aStyles.length) $(this).width(aStyles[i][0]);
			});			
			$grid.append("<div class='wf-fixtable-resizeMarker' style='height:300px; left:57px;display:none;'></div><div class='wf-fixtable-resizeProxy' style='height:300px; left:377px;display:none;'></div>");
	
			var scroller = $(".wf-fixtable-scroller", $grid);
			scroller.scroll(function(event){
				var header = $(".wf-fixtable-thead", $grid);
				if(scroller.scrollLeft() > 0){
					header.css("position", "relative");
					var scroll = scroller.scrollLeft();
					header.css("left", scroller.cssv("left") - scroll);
				}
				if(scroller.scrollLeft() == 0) {
					header.css("position", "relative");
					header.css("left", "0px");
				}
		        return false;
			});		
			
			
			$(">tr", thead).each(function(){

				$(">th", this).each(function(i){
					var th = this, $th = $(this);
					/*$th.mouseover(function(event){
						var offset = $.fixTableTool.getOffset(th, event).offsetX;
						if($th.outerWidth() - offset < 5) {
							$th.css("cursor", "col-resize").mousedown(function(event){
								$(".wf-fixtable-resizeProxy", $grid).show().css({
									left: $.fixTableTool.getRight(th)- $(".wf-fixtable-scroller", $grid).scrollLeft(),
									top:$.fixTableTool.getTop(th),
									height:$.fixTableTool.getHeight(th,$grid),
									cursor:"col-resize"
								});
								$(".wf-fixtable-resizeMarker", $grid).show().css({
										left: $.fixTableTool.getLeft(th) + 1 - $(".wf-fixtable-scroller", $grid).scrollLeft(),
										top: $.fixTableTool.getTop(th),
										height:$.fixTableTool.getHeight(th,$grid)									
								});
								$(".wf-fixtable-resizeProxy", $grid).jDrag($.extend(options, {scop:true, cellMinW:20, relObj:$(".wf-fixtable-resizeMarker", $grid)[0],
										move: "horizontal",
										event:event,
										stop: function(){
											var pleft = $(".wf-fixtable-resizeProxy", $grid).position().left;
											var mleft = $(".wf-fixtable-resizeMarker", $grid).position().left;
											var move = pleft - mleft - $th.outerWidth() -9;

											var cols = $.fixTableTool.getColspan($th);
											var cellNum = $.fixTableTool.getCellNum($th);
											var oldW = $th.width(), newW = $th.width() + move;
											var $dcell = $(">td", ftr).eq(cellNum - 1);
											
											$th.width(newW + "px");
											$dcell.width(newW+"px");
											
											var $table1 = $(thead).parent();
											$table1.width(($table1.width() - oldW + newW)+"px");
											var $table2 = $(tbody).parent();
											$table2.width(($table2.width() - oldW + newW)+"px");
											
											$(".wf-fixtable-resizeMarker,.wf-fixtable-resizeProxy", $grid).hide();
										}
									})
								);
							});
						} else {
							$th.css("cursor", $th.attr("orderField") ? "pointer" : "default");
							$th.unbind("mousedown");
						}
						return false;
					});*/
				});
			});
		
			function _resizeGrid(){
				$("div.j-resizeGrid").each(function(){
					var width = $(this).innerWidth();
					if (width){
						$("div.gridScroller", this).width(width+"px");
					}
				});
			}
			// $(window).unbind(DWZ.eventType.resizeGrid).bind("resizeGrid", _resizeGrid);
		});
	};
	
	
	$.fixTableTool = {
		getLeft:function(obj) {
			var width = 0;
			$(obj).prevAll().each(function(){
				width += $(this).outerWidth();
			});
			return width - 1;
		},
		getRight:function(obj) {
			var width = 0;
			$(obj).prevAll().andSelf().each(function(){
				width += $(this).outerWidth();
			});
			return width - 1;
		},
		getTop:function(obj) {
			var height = 0;
			$(obj).parent().prevAll().each(function(){
				height += $(this).outerHeight();
			});
			return height;
		},
		getHeight:function(obj, parent) {
			var height = 0;
			var head = $(obj).parent();
			head.nextAll().andSelf().each(function(){
				height += $(this).outerHeight();
			});
			$(".wf-fixtable-tbody", parent).children().each(function(){
				height += $(this).outerHeight();
			});
			return height;
		},
		getCellNum:function(obj) {
			return $(obj).prevAll().andSelf().size();
		},
		getColspan:function(obj) {
			return $(obj).attr("colspan") || 1;
		},
		getStart:function(obj) {
			var start = 1;
			$(obj).prevAll().each(function(){
				start += parseInt($(this).attr("colspan") || 1);
			});
			return start;
		},
		getPageCoord:function(element){
			var coord = {x: 0, y: 0};
			while (element){
			    coord.x += element.offsetLeft;
			    coord.y += element.offsetTop;
			    element = element.offsetParent;
			}
			return coord;
		},
		getOffset:function(obj, evt){
			if(/msie/.test(navigator.userAgent.toLowerCase())) {
				var objset = $(obj).offset();
				var evtset = {
					offsetX:evt.pageX || evt.screenX,
					offsetY:evt.pageY || evt.screenY
				};
				var offset ={
			    	offsetX: evtset.offsetX - objset.left,
			    	offsetY: evtset.offsetY - objset.top
				};
				return offset;
			}
			var target = evt.target;
			if (target.offsetLeft == undefined){
			    target = target.parentNode;
			}
			var pageCoord = $.fixTableTool.getPageCoord(target);
			var eventCoord ={
			    x: window.pageXOffset + evt.clientX,
			    y: window.pageYOffset + evt.clientY
			};
			var offset ={
			    offsetX: eventCoord.x - pageCoord.x,
			    offsetY: eventCoord.y - pageCoord.y
			};
			return offset;
		}
	};

	$.fn.extend({
		isTag:function(tn) {
			if(!tn) return false;
			return $(this)[0].tagName.toLowerCase() == tn?true:false;
		},
		layoutH: function($refBox){
			return this.each(function(){
				var $this = $(this);
				if (! $refBox) $refBox = $this.parents("div.wf-layout:first");
				var iRefH = $refBox.height();
				var iLayoutH = parseInt($this.attr("layoutH"));
				var iH = iRefH - iLayoutH > 50 ? iRefH - iLayoutH : 50;
				
				if ($this.isTag("table")) {
					$this.removeAttr("layoutH").wrap('<div layoutH="'+iLayoutH+'" style="overflow:auto;height:'+iH+'px"></div>');
				} else {
					$this.height(iH).css("overflow","auto");
				}
			});
		},
		hoverClass: function(className, speed){
			var _className = className || "wf-hover";
			return this.each(function(){
				var $this = $(this), mouseOutTimer;
				$this.hover(function(){
					if (mouseOutTimer) clearTimeout(mouseOutTimer);
					$this.addClass(_className);
				},function(){
					mouseOutTimer = setTimeout(function(){$this.removeClass(_className);}, speed||10);
				});
			});
		},
		focusClass: function(className){
			var _className = className || "textInputFocus";
			return this.each(function(){
				$(this).focus(function(){
					$(this).addClass(_className);
				}).blur(function(){
					$(this).removeClass(_className);
				});
			});
		},
		datePick: function(options){
			return this.each(function() {
				var $this = $(this);
				var _iWidth = $(this).width();
				var _width = $(this).outerWidth();
				$this.css('width',_iWidth)
				$this.wrap('<div class="wf-input-datepick" style="width:'+ _width +'px"></div>');
				$this.parent('.wf-input-datepick').append("<i class='wf-icon-calendar wf-icon-lg'></i>");
			});
		},
		tdAutoHide: function(options){
			return this.each(function() {
				var $table = $(this);
				var tbody = $table.find("tbody");
				$("td",tbody).each(function(){
					var $td = $(this);
					if(!$td.hasClass('wf-nohide')){
						$td.html("<div class='wf-text-hide' title='"+$td.text()+"'>"+ $td.html() +"</div>");	
					}
				});
			});
		}

	})

})(jQuery);




(function($){
	/*$.fn.cssv = function(pre){
		var cssPre = $(this).css(pre);
		return cssPre.substring(0, cssPre.indexOf("px")) * 1;
	};
	$.fn.jDrag = function(options){
		if (typeof options == 'string') {
			if (options == 'destroy') 
				return this.each(function(){
					$(this).unbind('mousedown', $.rwdrag.start);
					$.data(this, 'pp-rwdrag', null);
				});
		}
		return this.each(function(){
			var el = $(this);
			$.data($.rwdrag, 'pp-rwdrag', {
				options: $.extend({
					el: el,
					obj: el
				}, options)
			});
			if (options.event) 
				$.rwdrag.start(options.event);
			else {
				var select = options.selector;
				$(select, obj).bind('mousedown', $.rwdrag.start);
			}
		});
	};*/
	/*$.rwdrag = {
		start: function(e){
			document.onselectstart=function(e){return false};//禁止选择

			var data = $.data(this, 'pp-rwdrag');
			var el = data.options.el[0];
			$.data(el, 'pp-rwdrag', {
				options: data.options
			});
			if (!$.rwdrag.current) {
				$.rwdrag.current = {
					el: el,
					oleft: parseInt(el.style.left) || 0,
					otop: parseInt(el.style.top) || 0,
					ox: e.pageX || e.screenX,
					oy: e.pageY || e.screenY
				};
				$(document).bind("mouseup", $.rwdrag.stop).bind("mousemove", $.rwdrag.drag);
			}
		},
		drag: function(e){
			if (!e)  var e = window.event;
			var current = $.rwdrag.current;
			var data = $.data(current.el, 'pp-rwdrag');
			var left = (current.oleft + (e.pageX || e.clientX) - current.ox);
			var top = (current.otop + (e.pageY || e.clientY) - current.oy);
			if (top < 1) top = 0;
			if (data.options.move == 'horizontal') {
				if ((data.options.minW && left >= $(data.options.obj).cssv("left") + data.options.minW) && (data.options.maxW && left <= $(data.options.obj).cssv("left") + data.options.maxW)) 
					current.el.style.left = left + 'px';
				else if (data.options.scop) {
					if (data.options.relObj) {
						if ((left - parseInt(data.options.relObj.style.left)) > data.options.cellMinW) {
							current.el.style.left = left + 'px';
						}
					} else 
						current.el.style.left = left + 'px';
				}
			} else if (data.options.move == 'vertical') {
					current.el.style.top = top + 'px';
			} else {
				var selector = data.options.selector ? $(data.options.selector, data.options.obj) : $(data.options.obj);
				if (left >= -selector.outerWidth() * 2 / 3 && top >= 0 && (left + selector.outerWidth() / 3 < $(window).width()) && (top + selector.outerHeight() < $(window).height())) {
					current.el.style.left = left + 'px';
					current.el.style.top = top + 'px';
				}
			}
			
			if (data.options.drag) {
				data.options.drag.apply(current.el, [current.el, e]);
			}
			
			return $.rwdrag.preventEvent(e);
		},
		stop: function(e){
			var current = $.rwdrag.current;
			var data = $.data(current.el, 'pp-rwdrag');
			$(document).unbind('mousemove', $.rwdrag.drag).unbind('mouseup', $.rwdrag.stop);
			if (data.options.stop) {
				data.options.stop.apply(current.el, [current.el, e]);
			}
			$.rwdrag.current = null;

			document.onselectstart=function(e){return true};//启用选择
			return $.rwdrag.preventEvent(e);
		},
		preventEvent:function(e){
			if (e.stopPropagation) e.stopPropagation();
			if (e.preventDefault) e.preventDefault();
			return false;			
		}
	};*/
})(jQuery);