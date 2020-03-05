// map
var Map = function(){
	var a = this;
	a.map = new Object();
    a.length = 0;
};
Map.prototype = {
    size: function() {
        return this.length;
    },
    clear: function() {
        var a = this;
        a.map = new Object();
        a.length = 0;
    },
    put: function(b, c) {
        var a = this;
        if (!a.map["_" + b]) {
        	++a.length
        }
        a.map["_" + b] = c;
    },
    putAll: function(a) {
        var f = a.getKeys(),
        d = f.length,
        c = this;
        for (var e = d; e--;) {
            var b = f[e];
            c.put(b, a.get(b));
        }
    },
    remove: function(b) {
        var a = this;
        if (a.map["_" + b]) {
        	--a.length;
            return delete a.map["_" + b]
        } else {
            return false;
        }
    },
    containsKey: function(a) {
        return this.map["_" + a] ? true: false;
    },
    get: function(b) {
        var a = this;
        return a.map["_" + b] ? a.map["_" + b] : null;
    },
    inspect: function() {
        var c = "",
        a = this;
        for (var b in a.map) {
            c += "\n" + b + "  Value:" + a.map[b]
        }
        return c;
    },
    getKeys: function() {
        var d = new Array(),
        b = 0,
        a = this;
        for (var c in a.map) {
            d[b++] = c.replace("_", "")
        }
        return d;
    }
};

// 全局变量
var Global = {
	// 全局id控制器
	defIndex: 1,
	// 控制冒泡事件
	stopEvent: false,
	// 当前选中组建
	nowModuleId: null,
	// 一个视图中只能有一个唯一的from表单
	onlyFrom: null,
	// 设计区，应该为可以设置
	designContent: $("#contextBody"),
	// 是否可以新建form
	// isCreatForm: false,
	// 全部元素数组
	elementMap: new Map(),
	// 控制器
	checkControl: null,
	// 绑定元素，不可以多元素匹配
	bindObj: $(".formobj a"),
	// 被选中元素
	selectedElement: null
};

/*
// 
var exports = this;
(function($){
	var mod = {};

	mod.create = function(includes){
		var result = function(){
			this.init.apply(this, arguments);
		};

		result.fn = result.prototype;
		result.fn.init = function(){};

		result.proxy = function(func){return $.proxy(func , this); };
		result.fn.proxy = result.proxy;

		result.include = function(ob){ $.extend(this.fn,ob); };
		result.extend = function(ob){ $.extend(this,ob); };
		if(includes) result.include(includes);

		return result;
	};

	exports.Controller = mod;
})(jQuery);

var eventView = Controller.create({
	init:function(view){
		this.view = $(view);
		this.view.mouseover(this.proxy(this.toggleClass));
	},
	toggleClass: function(e){
		console.log(e);
		console.log(this);
	}
});
new checkDiv("#historyMessage");
*/

var Class = function(parent){
	var klass = function(){
		this.init.apply(this, arguments);
	};

	if(parent){
		var subclass = function(){};
		subclass.prototype = parent.prototype;
		klass.prototype = new subclass;
	}

	klass.prototype.init = function(){};

	klass.fn = klass.prototype;
	klass.fn.parent = klass;
	klass._super = klass.__proto__;

	klass.extend = function(obj){
		var extended = obj.extended;
		for(var i in obj){
			klass[i] = obj[i];
		}
		if(extended) extended(klass);
	};

	klass.include = function(obj){
		var included = obj.included;
		for(var i in obj){
			klass.fn[i] = obj[i];
		}
		if(included) included(klass);
	};

	klass.proxy = function(func){
		var self = this;
		return (function(){
			return func.apply(self, arguments);
		});
	};

	klass.fn.proxy = klass.proxy;

	return klass;
};
// 容器
var Container = new Class;

Container.include({
	// 带类型id
	_id: null,
	_type: null,
	_multiple: null,
	// 层级关系，纯数字
	_index: null,
	_width: "auto",
	_height: "auto",
	_left: 0,
	_top: 0,
	create: function(){
		return $(this._original).attr("id",this._id)
								.attr("type",this._type)
								.attr("multiple",this._multiple)
								.width(this._width)
								.height(this._height)
								.css("left",this._left)
								.css("top",this._top)
								.css("background-color","#FFF")
								.css("position","absolute")
								.css("border",this._border)
								.appendTo(this._parent);
	},
	drag:function(bool){
		var d = this._el,self = this,g = Global;
		d.bind('mousedown',function(e){
			if(g.stopEvent) return;
			g.stopEvent = true;
			// 选中效果完成TODO
			Global.checkControl.show();
			// 将所有的颜色还原
			Global.checkControl._obj && Global.checkControl._obj.defaultBorder();
			Global.checkControl.remove(self);
			
			// 选中时，边框为蓝色虚线
			self._el.css("border","1px dashed #0031DE");
			// 相对于元素原点，坐标差距
			var difX = e.layerX, difY = e.layerY, f;
			if(!!bool){
				f = g.designContent;
			}else{
				f = g.onlyFrom._el;
			}
			var w = self._el.width(),
				h = self._el.height(),
				fw = f.width() - w - 2,
				fh = f.height() - h - 2;
			// 获取对齐线数组
			self.getLine();

			// 移动事件
			$(document).bind('mousemove',function(e){
				var eX = e.pageX, eY = e.pageY;

				// 元素对齐线 TODO
				var list = self.isAlign(eX - difX, eY - difY, d.width() + d[0].clientLeft, d.height() + d[0].clientTop, 3);
				if(!!list){
					eX = list[0] + difX;
					eY = list[1] + difY;
				}

				var x = eX - f.offset().left - difX,
					y = eY - f.offset().top - difY;
					

				// 元素拖动限制
				if(x < 5) x = 0;
				else if(x > fw-5) x = fw;
				d.css("left", x);
				if(y < 5) y = 0;
				else if(y > fh -5) y = fh;
				d.css("top", y);
				Global.checkControl.remove(self);
			});
			
			// 移除绑定事件
			$(document).bind('mouseup',function(){
				// 最后重新定位，以免拖动带来影响
				Global.checkControl.remove(self);

				$(document).unbind('mousemove');
				$(document).unbind('mouseup');

				g.stopEvent = false;
				// 清空方法
				$("#leftCross").css("visibility","hidden");
				$("#topCross").css("visibility","hidden");
				// 设置属性
				self.getValue();
			});
		});
	},
	getLine:function(){
		var map = Global.elementMap;
		// 返回数组
		var idList = map.getKeys(), xList = [], yList = [];
		for(var i = 0, l = idList.length;i < l;i++){
			if(idList[i] !== this._id){
				var element = map.get(idList[i]);
				xList.push(element.offset().left - element[0].clientLeft);
				xList.push(element.offset().left + element.width());
				yList.push(element.offset().top - element[0].clientTop);
				yList.push(element.offset().top + element.height());
			}
		}
		this._xList = xList;
		this._yList = yList;
	},
	isAlign:function(x,y,w,h,dif){
		// 这里改变了x，y，在后面是否不需要判断元素移除onlyform
		var onlyFrom = Global.onlyFrom._el,
			fMinl = onlyFrom.offset().left,
			fMaxl = fMinl + onlyFrom.width() - this._el.width(),
			fMint = onlyFrom.offset().top,
			fMaxt = fMint + onlyFrom.height() - this._el.height();
		if(x < fMinl){
			x = fMinl;
		}else if (x > fMaxl){
			x = fMaxl;
		}
		if(y < fMint){
			y = fMint;
		}else if(y > fMaxt){
			y = fMaxt;
		}
		var xMin = x - dif,
			xAdd = x + dif,
			yMin = y - dif,
			yAdd = y + dif,
			xMinW = xMin + w,
			xAddW = xAdd + w,
			yMinH = yMin + h,
			yAddH = yAdd + h,
			xList = this._xList,
			yList = this._yList,
			dc = Global.designContent,
			dcl = dc.offset().left,
			dct = dc.offset().top - 1,
			xbool = true,
			ybool = true;


		// 仅用x轴数组的长度计算，正常情况下x，y数组长度应该一样，暂时未出现BUG
		for(var i = 0, l = xList.length; i < l; i++){
			if(xList[i] > xMin && xList[i] < xAdd && xbool){
				x = xList[i];
				xbool = false;
				$("#leftCross").css("left",x - dcl);
				$("#leftCross").css("visibility","visible");
			}else if(xList[i] > xMinW && xList[i] < xAddW && xbool){
				x = xList[i] - w;
				xbool = false;
				$("#leftCross").css("left",x + w - dcl);
				$("#leftCross").css("visibility","visible");
			}else{
				if(xbool)
					$("#leftCross").css("visibility","hidden");
			}
			
			if(yList[i] > yMin && yList[i] < yAdd && ybool){
				y = yList[i];
				ybool = false;
				$("#topCross").css("top",y - dct);
				$("#topCross").css("visibility","visible");
			}else if(yList[i] > yMinH && yList[i] < yAddH && ybool){
				y = yList[i] - h;
				ybool = false;
				$("#topCross").css("top",y + h - dct);
				$("#topCross").css("visibility","visible");
			}else{
				if(ybool)
					$("#topCross").css("visibility","hidden");
			}
		}
		if(!xbool || !ybool){
			return [x,y];
		}
	},
	getValue:function(){
		$("#changeId input").val(this._el.attr("id"));
		$("#changeName input").val(this._el.attr("name"));
		$("#changeValue input").val(this._el.html());
		$("#changeClass input").val(this._el.attr("class"));
		$("#changeStyle textarea").val(this._el.attr("style"));
	},
	setValue:function(){
		// BUG 设置大小时，可能超过当前区域
		var el = this._el;
		el.attr("id",$("#changeId input").val());
		el.attr("name",$("#changeName input").val());
		el.html($("#changeValue input").val());
		el.attr("class",$("#changeClass input").val())
	},
	defaultBorder:function(){
		this._el.css("border",this._border);
	},
	getMax:function(){

	}
});

// form
var form = new Class(Container);

form.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._index = id;
		this._left = x;
		this._top = y;
		this._id = "formdiv" + id;
		this._minWidth = 360;
		this._minHeight = 120;
		this._original = "<div></div>";
		this._width = 760;
		this._height = 200;
		this._border = "1px dashed #999";
		this._parent = Global.designContent;
		var d = this.create();
		var formid = "form" + id;
		d.wrap("<form action=\"\" target=\"\" id=\""+formid+"\" name=\""+formid+"\"></form>");
		this._el = d;
		this._type = "form";
		this.drag(true);
		g.onlyFrom = this;
		console.log(this);
	},
	isAlign:function(){

	},
	getValue:function(){
		var el = this._el, parent = el.parent();
		// 控制可以见
		$("#changeValue").css("display","none");
		// 获取值
		$("#changeId input").val(parent.attr("id"));
		$("#changeName input").val(parent.attr("name"));
		$("#changeClass input").val(el.attr("class"));
		$("#changeStyle textarea").val(el.attr("style"));
		$("#changeAction input").val(parent.attr("action"));
		$("#changeTarget input").val(parent.attr("target"));
	},
	setValue:function(){
		var el = this._el, parent = el.parent();
		parent.attr("id",$("#changeId input").val());
		parent.attr("name",$("#changeName input").val());
		el.attr("class",$("#changeClass input").val());
		var style = $("#changeStyle textarea").val();
		var styles = style.split(";");
		for(var i = 0, l = styles.length;i < l;i++){
			var single = styles[i];
			if(!!styles[i]){
				var singles = single.split(":");
				if(singles.length === 2){
					el.css(singles[0].trim(), singles[1].trim())
				}
			}
		}
		console.log(styles);
		parent.attr("action",$("#changeAction input").val());
		parent.attr("target",$("#changeTarget input").val());
	}
});

// label
var label = new Class(Container);

label.extend({
});

label.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._index = id;
		this._left = x;
		this._top = y;
		this._id = "label" + id;
		this._minWidth = 40;
		this._minHeight = 20;
		this._original = "<label></label>";
		this._width = 100;
		this._height = 20;
		this._border = "1px solid #FFF",
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		d.html("label");
		this._el = d;
		this.drag();
		g.elementMap.put('label' + id,d);
	}
});

// button
var button = new Class(Container);

button.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "button" + id;
		this._minWidth = 40;
		this._minHeight = 20;
		this._original = "<button></button>";
		this._type = "button";
		this._width = 60;
		this._height = 20;
		this._border = "2px outset buttonface";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		this._el = d;
		d.css("background-color","buttonface")
		d.html("1234");
		this.drag();
		g.elementMap.put('button' + id,d);
	}
});

// text
var text = new Class(Container);

text.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "text" + id;
		this._minWidth = 40;
		this._minHeight = 20;
		this._original = "<input>";
		this._type = "text";
		this._width = 100;
		this._height = 20;
		this._border = "1px solid #7B9CBD",
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		this._el = d;
		this.drag();
		g.elementMap.put('text' + id,d);
	}
})

// textarea
var textarea = new Class(Container);

textarea.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "textarea" + id;
		this._minWidth = 40;
		this._minHeight = 20;
		this._original = "<textarea>";
		this._width = 200;
		this._height = 50;
		this._border = "1px solid #7B9CBD",
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		this._el = d;
		this.drag();
		g.elementMap.put('textarea' + id,d);
	}
});

// radio
var radio = new Class(Container);

radio.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "radio" + id;
		this._original = "<div></div>";
		this._width = 100;
		this._border = "1px solid #FFF";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		// 创建元素
		$("<input>").attr("type","radio").appendTo(d);
		$("<label></label>").html(1234).appendTo(d);
		this._type = "radio";
		this._el = d;
		this.drag();
		g.elementMap.put('radio' + id,d);
	}
});

// checkbox
var checkbox = new Class(Container);

checkbox.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "checkbox" + id;
		this._original = "<div></div>";
		this._width = 100;
		this._border = "1px solid #FFF";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		// 创建元素
		$("<input>").attr("type","checkbox").appendTo(d);
		$("<label></label>").html(1234).appendTo(d);
		this._type = "checkbox";
		this._el = d;
		this.drag();
		g.elementMap.put('checkbox' + id,d);
	}
});

// list
var list = new Class(Container);

list.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "list" + id;
		this._original = "<select></select>";
		this._multiple = "multiple";
		this._width = 200;
		this._height = 50;
		this._border = "1px solid #7B9CBD";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		$("<option>1</option>").appendTo(d);
		$("<option>2</option>").appendTo(d);
		$("<option>3</option>").appendTo(d);
		$("<option>4</option>").appendTo(d);
		this._type = "list";
		this._el = d;
		this.drag();
		g.elementMap.put('list' + id,d);
	}
});

// select
// 点击事件有问题
var select = new Class(Container);

select.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "select" + id;
		this._original = "<select></select>";
		this._border = "1px solid #7B9CBD";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		$("<option>1</option>").appendTo(d);
		$("<option>2</option>").appendTo(d);
		$("<option>3</option>").appendTo(d);
		$("<option>4</option>").appendTo(d);
		this._type = "select";
		this._el = d;
		this.drag();
		g.elementMap.put('select' + id,d);
	}
});

// image
var image =  new Class(Container);
image.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "image" + id;
		this._original = "<img>";
		this._width = 100;
		this._height = 100;
		this._border = "1px solid #FFF";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		this._type = "image";
		this._el = d;
		this.drag();
		g.elementMap.put('image' + id,d);
	}
});

// file
var file =  new Class(Container);
file.include({
	init:function(x,y){
		var g = Global, id = g.defIndex++;
		this._left = x;
		this._top = y;
		this._index = id;
		this._id = "file" + id;
		this._minWidth = 40;
		this._minHeight = 20;
		this._original = "<input>";
		this._type = "file";
		this._width = 130;
		this._height = 26;
		this._border = "1px solid #FFF";
		this._parent = Global.onlyFrom._el;
		var d = this.create();
		this._el = d;
		this.drag();
		g.elementMap.put('file' + id,d);
	}
})

var aid = new Class;

aid.include({
	_id: null,
	_el: null,
	_changeObj: null,
	hide: function(){
		this._el.css("visibility", "hidden");
	},
	show: function(){
		this._el.css("visibility", "visible");
	},
	remove: function(x,y){
		this._el.css("left", x);
		this._el.css("top", y);
	}
})

var checkDiv = new Class(aid);
checkDiv.include({
	init: function(className){
		this._id = className;
		this._el = $("<div></div>").addClass(className)
						.attr("id",className)
						.css("visibility", "hidden")
						.appendTo(Global.designContent);
		this.change();
	},
	// BUG 拖动时没有考虑内部元素变化 解决
	change: function(){
		var d = this._el, self = this, className = this._id;
		d.bind('mousedown',function(e){
			var g = Global,
				obj = Global.checkControl._obj,
				el = obj._el,
				minw = obj._minWidth,
				minh = obj._minHeight,
				onlyFrom = Global.onlyFrom._el,
				oldX = e.pageX,
				oldY = e.pageY,
				w = el.width(),
				h = el.height(),
				l = el.offset().left,
				t = el.offset().top,
				clientLeft = el[0].clientLeft,
				clientTop = el[0].clientTop;

			if(obj._type == "form"){
				onlyFrom = g.designContent;
				var min = self.getMin();
				if(minw < min[0]) minw = min[0];
				if(minh < min[1]) minh = min[1];
			}
			var	fw = onlyFrom.width(),
				fh = onlyFrom.height(),
				fl = onlyFrom.offset().left,
				ft = onlyFrom.offset().top;
				console.log(obj);
			if(obj._type == "radio" || obj._type == "checkbox"){
				return;
			}
			$(document).bind('mousemove', function(e){
				var difX = oldX - e.pageX, difY = oldY - e.pageY;
				if ("bottom_right" == className){
					self.setWidth(el, w - difX, minw, fl + fw - clientLeft - l);
					self.setHeight(el, h - difY, minh, ft + fh - clientTop - t);
				}else if ("bottom_middle" == className){
					self.setHeight(el, h - difY, minh, ft + fh - clientTop - t);
				}else if ("bottom_left" == className){
					self.setWidth(el, w + difX, minw, l + w - fl);
					self.setHeight(el, h - difY, minh, ft + fh - clientTop - t);
					self.setLeft(el, l - fl - difX, 0, l + w - minw - fl);
				}else if ("middle_right" == className) {
					self.setWidth(el, w - difX, minw, fl + fw - clientLeft - l);
				}else if ("middle_left" == className) {
					self.setWidth(el, w + difX, minw, l + w - fl);
					self.setLeft(el, l - fl - difX, 0, l + w - minw - fl);
				}else if ("top_right" == className){
					self.setWidth(el, w - difX, minw, fl + fw - clientLeft - l);
					self.setHeight(el, h + difY, minh, t + h - ft);
					self.setTop(el,t -ft - difY, 0, t + h - minh - ft);
				}else if ("top_middle" == className){
					self.setHeight(el, h + difY, minh, t + h - ft);
					self.setTop(el,t -ft - difY, 0, t + h - minh - ft);
				}else if ("top_left" == className){
					self.setWidth(el, w + difX, minw, l + w - fl);
					self.setHeight(el, h + difY, minh, t + h - ft);
					self.setLeft(el, l - fl - difX, 0, l + w - minw - fl);
					self.setTop(el,t -ft - difY, 0, t + h - minh - ft);
				}
				g.checkControl.remove(obj);
			});
			$(document).bind('mouseup', function(){
				$(document).unbind('mousemove');
				$(document).unbind('mouseup');

				// 属性刷新
				obj.getValue();
			})
		});
	},
	setWidth:function(el,nw,minw,maxw){
		if(nw < minw){
			el.width(minw);
		}else if(nw > maxw){
			el.width(maxw);
		}else{
			el.width(nw);
		}
	},
	setHeight:function(el,nh,minh,maxh){
		if(nh < minh){
			el.height(minh);
		}else if(nh > maxh){
			el.height(maxh);
		}else{
			el.height(nh)
		}
	},
	setLeft:function(el,nl,minl,maxl){
		if(nl < minl){
			el.css("left",minl);
		}else if(nl > maxl){
			el.css("left",maxl);
		}else{
			el.css("left",nl);
		}
	},
	setTop:function(el,nt,mint,maxt){
		if(nt < mint){
			el.css("top",mint);
		}else if(nt > maxt){
			el.css("top",maxt);
		}else{
			el.css("top",nt);
		}
	},
	getMin:function(){
		var map = Global.elementMap;
		// 返回数组
		var idList = map.getKeys(), minWidth = 0, minHeight = 0,  onlyFrom = Global.onlyFrom._el;
		for(var i = 0, l = idList.length;i < l;i++){
				var element = map.get(idList[i]);
				// xList.push(element.offset().left - element[0].clientLeft);
				var temp = element.offset().left + element.width() + 2*element[0].clientLeft;
				if(temp > minWidth) minWidth = temp;
				// yList.push(element.offset().top - element[0].clientTop);
				temp = element.offset().top + element.height() + 2*element[0].clientTop;
				if(temp > minHeight) minHeight = temp;
		}
		minWidth = minWidth - onlyFrom.offset().left - onlyFrom[0].clientLeft;
		minHeight = minHeight - onlyFrom.offset().top - onlyFrom[0].clientTop;
		return [minWidth, minHeight];
	}
});

var mouseDiv = new Class(aid);
mouseDiv.include({
	init:function(){
		this._el = $("<div></div>").attr("id","mousediv")
									.width(10)
									.height(10)
									.css("left",-10)
									.css("top",-10)
									.css("background-color","#000")
									.css("position","absolute")
									.css("z-index",1000)
									.css("visibility", "hidden")
									.appendTo(Global.designContent);
	}
})

var checkControl = new Class;
checkControl.include({
	divList: {},
	_obj: null,
	init:function(){
		this.divList["top_left"] = new checkDiv("top_left");
		this.divList["top_middle"] = new checkDiv("top_middle");
		this.divList["top_right"] = new checkDiv("top_right");
		this.divList["middle_left"] = new checkDiv("middle_left");
		this.divList["middle_right"] = new checkDiv("middle_right");
		this.divList["bottom_left"] = new checkDiv("bottom_left");
		this.divList["bottom_middle"] = new checkDiv("bottom_middle");
		this.divList["bottom_right"] = new checkDiv("bottom_right");
		this.mousediv = new mouseDiv();
	},
	show:function(){
		var obj = this.divList;
		for(i in obj){
			obj[i].show();
		}
	},
	hide:function(){
		var obj = this.divList;
		for(i in obj){
			obj[i].hide();
		}
	},
	remove:function(obj){
		var dc = Global.designContent.offset(),
			el = obj._el,
			w = el.width(),
			h = el.height(),
			l = el.offset().left - dc.left,
			t = el.offset().top - dc.top;
		this.divList["top_left"].remove((l-4), (t-3));
		this.divList["top_middle"].remove((l+w/2-4), (t-3));
		this.divList["top_right"].remove((l+w-3), (t-3));
		this.divList["middle_left"].remove((l-4), (t+h/2-3));
		this.divList["middle_right"].remove((l+w-3), (t+h/2-3));
		this.divList["bottom_left"].remove((l-4), (t+h-2));
		this.divList["bottom_middle"].remove((l+w/2-4), (t+h-2));
		this.divList["bottom_right"].remove((l+w-3), (t+h-2));
		this._obj = obj;
	},
	change:function(){

	}
});
Global.checkControl = new checkControl();

(function(){
	var g = Global, mousediv = g.checkControl.mousediv;
	g.bindObj.bind('mousedown',function(){
		var self = this;
		$(this).addClass("mousedowna");
		mousediv.show();
		$(document).bind('mousemove',function(e){
			var x = e.pageX - $("#contextBody").offset().left,y = e.pageY - $("#contextBody").offset().top;
			mousediv.remove(x,y);
		});


		$(document).bind('mouseup',function(e){
			var x = e.pageX,y = e.pageY;

			// 创建元素
			if($(self).attr("type") == "form"){
				if(x - $("#contextBody").offset().left > 0 && y - $("#contextBody").offset().top > 0)
					if(!!Global.onlyFrom){
						jQuery.messager.alert('提示:','一个视图中只能有一个唯一的from表单！');
					}else{
						new form(x - $("#contextBody").offset().left,y - $("#contextBody").offset().top);
					}
			}else{
				var xx = x - Global.onlyFrom._el.offset().left,yy = y - Global.onlyFrom._el.offset().top;
				if(xx > 0 && yy > 0){
					if($(self).attr("type") == "label") new label(xx,yy);
					if($(self).attr("type") == "button") new button(xx,yy);
					if($(self).attr("type") == "text") new text(xx,yy);
					if($(self).attr("type") == "textarea") new textarea(xx,yy);
					if($(self).attr("type") == "radio") new radio(xx,yy);
					if($(self).attr("type") == "checkbox") new checkbox(xx,yy);
					if($(self).attr("type") == "list") new list(xx,yy);
					if($(self).attr("type") == "select") new select(xx,yy);
					if($(self).attr("type") == "image") new image(xx,yy);
					if($(self).attr("type") == "file") new file(xx,yy);
				}
			}
			$(self).removeClass("mousedowna");

			mousediv.hide();
			mousediv.remove(-10,-10);
			// 解除绑定
			$(document).unbind('mousemove').unbind('mouseup');
		})
	});
	$(document).bind('keydown',function(e){
		if(e.which == 13 || e.which == 18){
			var obj = Global.checkControl._obj;
			obj.setValue();
		}
		console.log(e)
	})
})();