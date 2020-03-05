$(document).ready(function() {
	GetBrowserType();

	setTimeout(function(){
		initUI();
		initLayout();
	}, 10);



	$(window).resize(function(){
		initLayout();
	});

	

});


function initLayout(){
		

		$(".tw-layout").eq(0).height($(window).height()-2);
		var _heightTop = $(".tw-layout>.tw-list-top").outerHeight(true);
		var _heightFoot = $(".tw-layout>.tw-list-ft").length ? 45 : 0;
		var _heightHead = $(".tw-fixtable-header").outerHeight(true);
		var _layoutH = _heightTop + _heightFoot ;
		if($(".tw-fixtable-wrap").length){
			_layoutH = _heightTop + _heightFoot + _heightHead + 10;
			$(".tw-fixtable-scroller").attr('layoutH',_layoutH)
		}
		// var _layoutHC = _heightTop + _heightFoot;
		var containerePT = parseInt($(".tw-layout>.tw-container").css("padding-top"));
		var containerePB = parseInt($(".tw-layout>.tw-container").css("padding-bottom"));
		_layoutH = _layoutH + containerePT + containerePB;
		$(".tw-layout>.tw-container").attr('layoutH',_layoutH);
		$(".tw-layout").find("[layoutH]").layoutH();
	}

	function initUI(){

		if ($.fn.fixTable) $("table.tw-fixtable").fixTable();

		$("div.tw-tabs").each(function(){
			var $this = $(this);
			var options = {};
			options.currentIndex = $this.attr("currentIndex") || 0;
			options.eventType = $this.attr("eventType") || "click";
			$this.tabs(options);
		});

		$("input[readonly], textarea[readonly]").addClass("tw-form-readonly");
		$("input[disabled=true], textarea[disabled=true]").addClass("tw-form-disabled");

		if ($.fn.datePick){
			$('input.tw-form-date').datePick();
		}

		if ($.fn.tdAutoHide){
			$('table.tw-table-fixed').tdAutoHide();
		}

		if ($.fn.trSelected){
			$('table.tw-table-select').trSelected();
		}

		if ($.fn.checkboxCtrl){
			$(":button.tw-checkbox-ctrl, :checkbox.tw-checkbox-ctrl").checkboxCtrl();
		}


	}


(function($){
	$.fn.fixTable = function(options){
		return this.each(function(){
		 	var $table = $(this), nowrapTD = $table.attr("nowrapTD");
		 	var tlength = $table.width();
			var aStyles = [];
			var $tc = $table.parent().addClass("j-resizeGrid"); // table parent container
			var layoutH = $(this).attr("layoutH");

			var oldThs = $table.find("thead>tr:last-child").find("th");

			for(var i = 0, l = oldThs.size(); i < l; i++) {
				var $th = $(oldThs[i]);
				var style = [];
				var width = $th.innerWidth() - (100 * $th.innerWidth() / tlength)-2;
				style[0] = parseInt(width);
				style[1] = $th.attr("align");
				aStyles[aStyles.length] = style;
			}
			$(this).wrap("<div class='tw-fixtable-wrap'></div>");
			var $grid = $table.parent().html($table.html());
			var thead = $grid.find("thead");
			thead.wrap("<div class='tw-fixtable-header'><div class='tw-fixtable-thead'><table style='width:" + (tlength - 20) + "px;'></table></div></div>");

			var lastH = $(">tr:last-child", thead);
			var ths = $(">th", lastH);
			$("th",thead).each(function(){
				var $th = $(this);
				$th.html("<div class='tw-fixtable-col' title='"+$th.text()+"'>"+ $th.html() +"</div>");	
			});
			
			ths.each(function(i){
				var $th = $(this), style = aStyles[i];
				$th.addClass(style[1]).hoverClass("tw-hover").removeAttr("align").removeAttr("width").width(style[0]);
			});

			var tbody = $grid.find(">tbody");
			var layoutStr = layoutH ? " layoutH='" + layoutH + "'" : "";


			tbody.wrap("<div class='tw-fixtable-scroller'" + layoutStr + " style='width:" + $tc.width() + "px;'><div class='tw-fixtable-tbody'><table style='width:" + (tlength - 20) + "px;'></table></div></div>");
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
					if(!$tr.hasClass('tw-disabled')){
						$trs.filter(".tw-actived").removeClass("tw-actived");
						$tr.addClass("tw-actived");
						var sTarget = $tr.attr("target");
						if (sTarget) {
							if ($("#"+sTarget, $grid).size() == 0) {
								$grid.prepend('<input id="'+sTarget+'" type="hidden" />');
							}
							$("#"+sTarget, $grid).val($tr.attr("rel"));
						}
					}
				});
			});
			
			$(">td",ftr).each(function(i){
				if (i < aStyles.length) $(this).width(aStyles[i][0]);
			});			
			$grid.append("<div class='tw-fixtable-resizeMarker' style='height:300px; left:57px;display:none;'></div><div class='tw-fixtable-resizeProxy' style='height:300px; left:377px;display:none;'></div>");
	
			var scroller = $(".tw-fixtable-scroller", $grid);
			scroller.scroll(function(event){
				var header = $(".tw-fixtable-thead", $grid);
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
					$th.mouseover(function(event){
						var offset = $.fixTableTool.getOffset(th, event).offsetX;
						if($th.outerWidth() - offset < 5) {
							$th.css("cursor", "col-resize").mousedown(function(event){
								$(".tw-fixtable-resizeProxy", $grid).show().css({
									left: $.fixTableTool.getRight(th)- $(".tw-fixtable-scroller", $grid).scrollLeft(),
									top:$.fixTableTool.getTop(th),
									height:$.fixTableTool.getHeight(th,$grid),
									cursor:"col-resize"
								});
								$(".tw-fixtable-resizeMarker", $grid).show().css({
										left: $.fixTableTool.getLeft(th) + 1 - $(".tw-fixtable-scroller", $grid).scrollLeft(),
										top: $.fixTableTool.getTop(th),
										height:$.fixTableTool.getHeight(th,$grid)									
								});
								$(".tw-fixtable-resizeProxy", $grid).jDrag($.extend(options, {scop:true, cellMinW:20, relObj:$(".tw-fixtable-resizeMarker", $grid)[0],
										move: "horizontal",
										event:event,
										stop: function(){
											var pleft = $(".tw-fixtable-resizeProxy", $grid).position().left;
											var mleft = $(".tw-fixtable-resizeMarker", $grid).position().left;
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
											
											$(".tw-fixtable-resizeMarker,.tw-fixtable-resizeProxy", $grid).hide();
										}
									})
								);
							});
						} else {
							$th.css("cursor", $th.attr("orderField") ? "pointer" : "default");
							$th.unbind("mousedown");
						}
						return false;
					});
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
			$(".tw-fixtable-tbody", parent).children().each(function(){
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
				if (! $refBox) $refBox = $this.parents("div.tw-layout:first");
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
			var _className = className || "tw-hover";
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
		datePick: function(options){
			return this.each(function() {
				var $this = $(this);
				// var _iWidth = $(this).width();
				// var _width = $(this).outerWidth();
				var _width;
				if($this.attr("isTime")){
					_width = 150;
					$this.click(function(){laydate({
						istime: true, 
						format: 'YYYY-MM-DD hh:mm:ss'
					});})
				} else {
					_width = 120;
					$this.click(function(){laydate();})
				}

				$this.css('width',_width)
				$this.wrap('<div class="tw-input-datepick" style="width:'+ _width +'px"></div>');
				$this.parent('.tw-input-datepick').append("<i class='tw-icon-calendar tw-icon-lg'></i>");
				

				// need laydate
			});
		},
		tdAutoHide: function(options){
			return this.each(function() {
				var $table = $(this);
				var tbody = $table.find("tbody");
				$("td",tbody).each(function(){
					var $td = $(this);
					if(!$td.hasClass('tw-nohide')){
						$td.html("<div class='text-hide' title='"+$td.text()+"'>"+ $td.html() +"</div>");	
					}
				});
			});
		},
		trSelected: function(options){
			return this.each(function() {
				var $table = $(this);
				var tbody = $table.find("tbody");
				var $trs = tbody.find('>tr');
				$trs.each(function(){
					var $tr = $(this);
					if(!$tr.hasClass('tw-disabled')){
						$tr.click(function(){
							$trs.filter(".tw-actived").removeClass("tw-actived");
							$tr.addClass("tw-actived");
							var sTarget = $tr.attr("target");
							if (sTarget) {
								if ($("#"+sTarget, $table).size() == 0) {
									$table.prepend('<input id="'+sTarget+'" type="hidden" />');
								}
								$("#"+sTarget, $table).val($tr.attr("rel"));
							}
						});
					}
				});
			});
		},
		tabs: function(options){
			var op = $.extend({
				eventType: "click",
				currentIndex: 0,
				tabNav: '.tw-tabs-nav',
				tabNavItem : '.tw-nav-item',
				tabCnt: '.tw-tabs-bd',
				tabPanel: '.tw-tab-panel',
				activeClass: 'tw-active',
				callback:function(){return false;}
			}, options);
			return this.each(function(){
				initTab($(this));

			});

			

			function initTab(twT){
				var twSelector = twT.add($("> *", twT));
				var twTabNav = $('>' + op.tabNav, twSelector);
				var twTabs = $('>' + op.tabNavItem, twTabNav);
				var twGroups = $('>' + op.tabPanel, twSelector);
				var activeClass = op.activeClass;


				twTabs.each(function(tabIndex) {
					var tabIdx = randomWord(false, 10);
					if (typeof($(this).attr('data-tab-idx')) == 'undefined' || $(this).attr('data-tab-idx') ==''){
						
						$(this).attr('data-tab-idx',tabIdx);
						twGroups.eq(tabIndex).attr('data-tab-idx',tabIdx);
						// console.log($(this).attr('data-tab-idx'))
					}
				});

				var startTab = twTabs.eq(op.currentIndex).attr('data-tab-idx');
				// console.log(startTab)
				switchTab(twT, startTab)

				
				if (op.eventType == "hover") {
					twTabNav.on('mouseover', op.tabNavItem, function(event){
						var tabIndex = $(this).attr('data-tab-idx');
						switchTab(twT, tabIndex)
					})
				}
				else {
					twTabNav.on('click', op.tabNavItem, function(event){
						var tabIndex = $(this).attr('data-tab-idx');
				        event.cancelBubble = true;
				        if (event.stopPropagation) event.stopPropagation();
						
							switchTab(twT, tabIndex)
						
					})
				}
			}

			function switchTab(twT, tabIndex){
				var twSelector = twT.add($("> *", twT));
				var twTabNav = $('>' + op.tabNav, twSelector);
				var twTabs = $('>' + op.tabNavItem, twTabNav);
				var twGroups = $('>' + op.tabPanel, twSelector);
				var activeClass = op.activeClass;
				var twTab = twTabs.eq(tabIndex);
				var twGroup = twGroups.eq(tabIndex);
				twTabs.each(function() {
					if($(this).attr('data-tab-idx') == tabIndex){
						twTab = $(this);
					}
				})
				twGroups.each(function() {
					if($(this).attr('data-tab-idx') == tabIndex){
						twGroup = $(this);
					}
				})
				if (op.reverse && (twTab.hasClass(activeClass) )) {
					twTabs.removeClass(activeClass);
					twGroups.hide();
				} else {
					// op.currentIndex = tabIndex;
					twTabs.removeClass(activeClass);
					twTab.addClass(activeClass);
					twGroups.hide();
					twGroup.show();
				}
				if (typeof op.callback == 'function') { 
			        op.callback.call(this); 
			  	}
			}


			function destroyTab(twT, tabIndex){
				if (confirm('确定要删除此选项卡？')){
					var twSelector = twT.add($("> *", twT));
					var twTabNav = $('>' + op.tabNav, twSelector);
					var twTabs = $('>' + op.tabNavItem, twTabNav);
					var twGroups = $('>' + op.tabPanel, twSelector);
					var activeClass = op.activeClass;

					twTabs.each(function() {
						if($(this).attr('data-tab-idx') == tabIndex){
							twTab = $(this);
						}
					})
					twGroups.each(function() {
						if($(this).attr('data-tab-idx') == tabIndex){
							twGroup = $(this);
						}
					})
					twTab.remove();
					twGroup.remove();
					twTabs = $('>' + op.tabNavItem, twTabNav);
					if(twTabs.length == 1){
						twTabs.find(".tw-tab-del").hide();
					}
					if (twTab.hasClass(activeClass)) {
						var _eq = tabIndex-1 >= 0 ? tabIndex-1 : 0;
						twTabs.eq(_eq).trigger('click');
					} 
				}
				
			}

		}

	})

})(jQuery);


(function($){
	$.fn.extend({
		
		checkboxCtrl: function(parent){
			return this.each(function(){
				var $trigger = $(this);
				$trigger.click(function(){
					var group = $trigger.attr("group");
					if ($trigger.is(":checkbox")) {
						var type = $trigger.is(":checked") ? "all" : "none";
						if (group) $.checkbox.select(group, type, parent);
					} else {
						if (group) $.checkbox.select(group, $trigger.attr("selectType") || "all", parent);
					}
					
				});
			});
		}
	});
	
	$.checkbox = {
		selectAll: function(_name, _parent){
			this.select(_name, "all", _parent);
		},
		unSelectAll: function(_name, _parent){
			this.select(_name, "none", _parent);
		},
		selectInvert: function(_name, _parent){
			this.select(_name, "invert", _parent);
		},
		select: function(_name, _type, _parent){
			var $parent = $(_parent || document),
				$checkboxLi = $parent.find(":checkbox[name='"+_name+"']");
			switch(_type){
				case "invert":
					$checkboxLi.each(function(){
						this.checked = !this.checked;
					});
					break;
				case "none":
					$checkboxLi.removeAttr('checked');
					break;
				default:
					$checkboxLi.each(function(){
						this.checked = true;
					});

					break;
			}

			$checkboxLi.trigger('change');
		}
	};
})(jQuery);



function GetBrowserType(){  
    var u_agent = navigator.userAgent;  
    var browser_name='Failed to identify the browser';  
    var error_msg = '<div class="tw-error tw-ie-warning">'+
      '<button type="button" class="tw-close">&times;</button>'+
      '<div class="">友情提醒：你的浏览器太古董了，<a href="http://windows.microsoft.com/ie" title="Microsoft Internet Explorer" target="_blank">点击换一个</a>！</div></div>';
    if(u_agent.indexOf('Firefox')>-1){  
        browser_name='Firefox';  
    }else if(u_agent.indexOf('Chrome')>-1){  
        browser_name='Chrome';  
    }else if(u_agent.indexOf('Trident')>-1&&u_agent.indexOf('rv:11')>-1){  
        browser_name='IE11';  
    }else if(u_agent.indexOf('MSIE')>-1&&u_agent.indexOf('Trident')>-1){  
        browser_name='IE(8-10)';  
    }else if(u_agent.indexOf('MSIE')>-1){  
        browser_name='IE(6-7)';  
        $('body').prepend(error_msg);
    }else if(u_agent.indexOf('Opera')>-1){  
        browser_name='Opera';  
    }else{  
        browser_name+=',info:'+u_agent;  
    }  
    
    // console.log('browser_name:'+browser_name+'<br>');  
    // console.log('u_agent:'+u_agent+'<br>');  
    $(".tw-close").on('click', function(){
    	$(this).parent(".tw-ie-warning").hide();
    })
}


function randomWord(randomFlag, min, max){
    var str = "",
        range = min,
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
 
    // 随机产生
    if(randomFlag){
        range = Math.round(Math.random() * (max-min)) + min;
    }
    for(var i=0; i<range; i++){
        pos = Math.round(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
}


(function($){
	$.fn.cssv = function(pre){
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
	};
	$.rwdrag = {
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
	};
})(jQuery);
