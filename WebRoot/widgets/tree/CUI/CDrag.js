/**
 * Function:
 *
 *
 */
var Class = {
    //创建类
    	create : function () {
    		return function () {
    			this.initialize.apply(this, arguments);
    		};
    	}
    };
    
    var $A = function (a) {
    //转换数组
    	return a ? Array.apply(null, a) : new Array;
    };
    
    var $ = function (id) {
    //获取对象
    	return document.getElementById(id);
    };
    
    var Try = {
    //检测异常
    	these : function () {
    		var returnValue, arg = arguments, lambda, i;
    	
    		for (i = 0 ; i < arg.length ; i ++) {
    			lambda = arg[i];
    			try {
    				returnValue = lambda();
    				break;
    			} catch (exp) {}
    		}
    	
    		return returnValue;
    	}
    	
    };
    
    Object.extend = function (a, b) {
    //追加方法
    	for (var i in b) a[i] = b[i];
    	return a;
    };
    
    Object.extend(Object, {
    
    	addEvent : function (a, b, c, d) {
    	//添加函数
    		if (a.attachEvent) a.attachEvent(b[0], c);
    		else a.addEventListener(b[1] || b[0].replace(/^on/, ""), c, d || false);
    		return c;
    	},
    	
    	delEvent : function (a, b, c, d) {
    		if (a.detachEvent) a.detachEvent(b[0], c);
    		else a.removeEventListener(b[1] || b[0].replace(/^on/, ""), c, d || false);
    		return c;
    	},
    	
    	reEvent : function () {
    	//获取Event
    		return window.event ? window.event : (function (o) {
    			do {
    				o = o.caller;
    			} while (o && !/^\[object[ A-Za-z]*Event\]$/.test(o.arguments[0]));
    			return o.arguments[0];
    		})(this.reEvent);
    	}
    	
    });
    
    Function.prototype.bind = function () {
    //绑定事件
    	var wc = this, a = $A(arguments), o = a.shift();
    	return function () {
    		wc.apply(o, a.concat($A(arguments)));
    	};
    };
    
    var CDrag = Class.create();
    
    CDrag.IE = /MSIE/.test(window.navigator.userAgent);
    
    CDrag.load = function (obj_string, func, time) {
    //加载对象
    	var index = 0, timer = window.setInterval(function () {
    		try {
    			if (eval(obj_string + ".loaded")) {
    				window.clearInterval(timer);
    				func(eval("new " + obj_string));
    			}
    		} catch (exp) {}
    
    		if (++ index == 20) window.clearInterval(timer);
    	}, time + index * 3);
    };
    
    CDrag.database = {
    //数据存储
    	json : null,
		icoCurNum:1,
    	parse : function (id) {
    	//查找资源
    		var wc = this, json = wc.json, i;
    		for (i in json) {
    			if (json[i].id == id)
    				return json[i];
    		}
    	}
    };
    
	CDrag.option={
		url:null,
		paramName:null,
		defaultJson:null,
		layoutJson:null,
		icos:null
	};
	
	
    //简易的AJAX封装
    CDrag.Ajax = Class.create();
    
    Object.extend(CDrag.Ajax, {
    
    	getTransport: function() {
    		return Try.these(
    			function () { return new ActiveXObject('Msxml2.XMLHTTP') },
    			function () { return new ActiveXObject('Microsoft.XMLHTTP') },
    			function () { return new XMLHttpRequest }//FF,function () { return new XMLHttpRequest()} 
    			
    		) || false;
    	}
    	
    });
    
    CDrag.Ajax.prototype = {
    
    	initialize : function (url) {
    	//初始化
    		var wc = this;
    		wc.ajax = CDrag.Ajax.getTransport();
    	},
    	
    	load : function (func) {
    		var wc = this, ajax = wc.ajax;
    		if (ajax.readyState == 4 && ajax.status == 200)
    			func(ajax.responseText);
    	},
    	
    	send : function (url,params, func) {
    		var wc = this, ajax = wc.ajax;
    		ajax.open("post", url+ "?time=" + new Date().getTime() + (10000 + parseInt(Math.random() * 10000)), true);
    		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    		ajax.onreadystatechange = wc.load.bind(wc, func);
    		ajax.send(params);
    	}
    	
    };
    
    //列对象
    CDrag.Table = Class.create();
    
    CDrag.Table.prototype = {
    	/**
    	 *Func    列的构造函数
    	 *params  
    	 *return  
    	 */
    	initialize : function () {
    	//初始化
    		var wc = this;
    		wc.items = []; //创建列组
    	},
    	
    	/**
    	 *Func    添加列
    	 *params  
    	 *return  返回列元素对象
    	 */
    	add : function () {
    		var wc = this, id = wc.items.length, arg = arguments;
    		return wc.items[id] = new CDrag.Table.Cols(id, wc, arg[0]);
    	}
    	
    };
    
    CDrag.Table.Cols = Class.create();
    
    CDrag.Table.Cols.prototype = {
    	/**
    	 *Func    列的构造函数
    	 *params  
    	 *return  
    	 */
    	initialize : function (id, parent, element) {
    		var wc = this;
    		wc.items = []; //创建列组
    		wc.id = id;
    		wc.parent = parent;
    		wc.element = element;
    	},
    	
    	/**
    	 *Func    添加栏目
    	 *params  
    	 *return  被添加的栏目的对象
    	 */
    	add : function () {
    		var wc = this, id = wc.items.length, arg = arguments;
    		return wc.items[id] = new CDrag.Table.Rows(id, wc, arg[0], arg[1], arg[2]);
    	},
    	
    	/**
    	 *Func    插入栏目
    	 *params  第几个元素，需要插入的栏目
    	 *return  插入的栏目对象
    	 */
    	ins : function (num, row) {
    		var wc = this, items = wc.items, i;
    		
    		if (row.parent == wc && row.id < num) num --; //同列向下移动的时候
    		for (i = num ; i < items.length ; i ++) items[i].id ++;
    		
    		items.splice(num, 0, row);
    		row.id = num, row.parent = wc;
    		
    		return row;
    	},
    	
    	/**
    	 *Func    删除栏目
    	 *params  第几个元素
    	 *return  含有被删除元素的数组
    	 */
    	del : function (num) {
    		var wc = this, items = wc.items, i;
    		
    		if (num >= items.length) return;
    		for (i = num + 1; i < items.length ; i ++) items[i].id = i - 1;
    		return items.splice(num, 1)[0];
    	}
    	
    };
    
    //栏目对象
    CDrag.Table.Rows = Class.create();
    
    CDrag.Table.Rows.prototype = {
    	
    	/**
    	 *Func    CDrag.Table.Rows的构造函数
    	 *params  
    	 *return  
    	 */
    	initialize : function (id, parent, element, window, locks) {
    		var wc = this, temp;
    		
    		wc.id = id;
    		wc.parent = parent;
    		wc.root_id = element;
    		wc.window = window;
    		wc.element = wc.element_init();
    		//头部
    		temp = wc.element.childNodes[1];
    		//标题
    		wc.title = temp.childNodes[3];
    		//放大缩小
    		wc.reduce = temp.childNodes[1];
    		wc.reduce.title='缩小';
    		//锁定
    		wc.lock = temp.childNodes[2], wc.locks = locks;
    		wc.lock.title='锁定';
    		//关闭
    		wc.close = temp.childNodes[0];
    		wc.close.title='关闭';
			//ICO无相连重复
			wc.ico = temp.childNodes[4];
			//生成ICO
			CDrag.database.icoCurNum=(CDrag.database.icoCurNum>=CDrag.option.icos)?1:CDrag.database.icoCurNum+1;
			bgico="ico"+CDrag.database.icoCurNum;
			wc.ico.className="tipico "+bgico;
    		//内容
    		wc.content = wc.element.childNodes[2];
    		wc.Class = wc.mousedown = wc.reduceFunc = wc.lockFunc  = wc.closeFunc = null;
    		wc.init();
    		wc.load();
    	},
    	
    	/**
    	 *Func    加入显示列表
    	 *params  
    	 *return  该显示对象
    	 */
    	element_init : function () {
    		var wc = this, div = $("tempbox").cloneNode(true);
    		wc.parent.element.appendChild(div);
    		div.style.display = "block";
    		return div;
    	},
    	
    	/**
    	 *Func    初始化栏目信息
    	 *params  
    	 *return  
    	 */
    	init : function () {
    		var wc = this;
    		if (wc.window == 0) {
    			wc.content.style.display = "none";
    			wc.reduce.className = "zoom-b";
    		} else {
    			wc.content.style.display = "block";
    			wc.reduce.className = "zoom-s";
    		}
    		
    		wc.lock.className = !wc.locks ? "lock" : "unlock";
    	},
    	
    	/**
    	 *Func    写入标题，内容
    	 *params  
    	 *return  
    	 */
    	load : function () {
    		var wc = this, info = CDrag.database.parse(wc.root_id), script;
    		wc.title.innerHTML = info.title;
    		//定义自定义图标[需要定义图片的绝对地址，格式为gif透明无锯齿]
			//wc.ico.style.background = "url("+info.imgsrc+") no-repeat center";
    		if (info.src) {
    			wc.content.innerHTML = "loading";
    			script = document.createElement("script");
    			script.src = info.src + ".js"//, script.defer = true;
    			document.getElementsByTagName("head")[0].appendChild(script);
    			CDrag.load(info.className, wc.upload.bind(wc), 5);
    		} else	wc.content.innerHTML = info.className;
    	},
    	
    	upload : function (obj) {
    	/*加载类信息
    		注：这里给行加入了一个扩展类，这里行的内容可以通过扩展类来控制^o^
    		不过扩展类的格式必须有open方法和edit方法，还有类名.静态成员loaded = true；为了检测是否加载完毕
    		扩展类需放到单独的.js文件里，然后从database结构体内设定其参数即可
    	*/
    		var wc = this;
    		wc.Class = obj;
    		wc.Class.parent = wc;
    		wc.Class.open();
    	},
    	
    	/**
    	 *Func    检索锁定
    	 *params  
    	 *return  
    	 */
    	lockF : function () {
    		var wc = this, arg = $A(arguments), root = arg.shift(), func = arg.shift();
    		return function () {
    			if (!wc.locks) func.apply(root, arg.concat($A(arguments)));
    		};
    	}
    	
    };
    
    CDrag.Add = Class.create();
    
    CDrag.Add.prototype = {
    	
    	//初始化参数
    	initialize : function (parent) {
    		var wc = this;
    		wc.div = document.createElement("div"); //最外层div
    		wc.iframe = document.createElement("iframe"); //协助wc.div盖select的iframe
    		wc.node = document.createElement("div"); //内容底层div
    		wc.titles = document.createElement("div"); //内容层div
    		wc.content = document.createElement("div"); //内容层div
    		wc.button = document.createElement("div"); //内容处理按钮
    		wc.button1 = document.createElement("div"); //内容处理按钮
    		wc.button2 = document.createElement("div"); //内容处理按钮
    		wc.button3 = document.createElement("a"); //关闭窗口
    		wc.parent = parent;
    		wc.json = null;
    		wc.button1.onclick = wc.execute.bind(wc, wc.content); //向按钮指向方法
    		wc.button3.onclick = wc.close.bind(wc); //向按钮指向方法
    		wc.button2.onclick =function(){
    				window['wc'].del_cookie();
    				window.location.reload();
    		}
    		wc.init_element();		
    	},
    	
    	//初始化元素
    	init_element : function () {
    		var wc = this;
			wc.div.className="tools-bg";
			wc.titles.className="tools-title";
    		wc.iframe.className="tools-iframe";
    		wc.node.className="tools-box boxshadow";
    		wc.content.className="tools-form";
    		wc.button.className="tools-btnbox";
    		wc.button1.className="tools-btn";
    		wc.button2.className="tools-btn";
    		wc.button1.innerHTML = "保存";
    		wc.button2.innerHTML = "重置";
    		wc.titles.innerHTML  = "栏目设置";
    		wc.titles.appendChild(wc.button3);
    		wc.button.appendChild(wc.button1);
    		wc.button.appendChild(wc.button2);
    		wc.node.appendChild(wc.titles);
    		wc.node.appendChild(wc.content);
    		wc.node.appendChild(wc.button);
    	},
    	
    	//初始化原始数据信息串
    	init_json : function () {
    		var wc = this, parent = wc.parent,
    			cols = parent.table.items, new_json = {}, init_json = CDrag.database.json, r, i, j;
    			
    		for (i = 0 ; i < init_json.length ; i ++) //便利原始数据
    			new_json[init_json[i].id] = { id : init_json[i].id, row : null, title : init_json[i].title };
    			
    		for (i = 0 ; i < cols.length ; i ++) //便利修改生成的串的属性
    			for (r = cols[i].items, j = 0 ; j < r.length ; j ++)
    				new_json[r[j].root_id].row = r[j];
    		return new_json;
    	},
    	
    	//初始化内容层div的数据
    	init_node : function () {
    		var wc = this, json = wc.json = wc.init_json(), boxary = [], i,m=1,tr;
    		for (i in json){
    			tr=(m%2===0)?'<\/tr><tr>':'';
    			boxary[boxary.length] = [
    				'<td><input type="checkbox"', json[i].row ? 'checked="checked"' : "",
    				' value="', json[i].id, '" />&nbsp;&nbsp;', json[i].title, '<\/td>',tr
    			].join("");
    			m++;
    		}
    		wc.content.innerHTML = '<table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'+boxary.join("")+'<tr\/><\/table>'; //写入内容层
    	},
    	
    	//处理table类结构
    	execute : function (div) {
    		var wc = this, parent = wc.parent, json = wc.json, items = div.getElementsByTagName("input"), row, c, i;
    		//新增判断是否确认或取消
    		window['isbtnsubmitok']=true;
    		try {
    			for (i = 0 ; i < items.length ; i ++) {
    				if (items[i].type != "checkbox") continue;
    				row = json[items[i].value];
    				
    				if ((!!row.row) != items[i].checked) {
    					if (row.row) parent.remove(row.row); //删除
    					else {
							window['items']=isNaN(parseInt(window['items']))?0:window['items'];
							parent.add(parent.table.items[window['items']].add(row.id, 1, false)); //向第一行追加数据
							window['items']=(window['items']<2)?window['items']+1:0;
						}
    					c = true;
    				}
    			}
    			c = true;
    			div.innerHTML = "";
    			if (c) parent.set_cookie();
    			
    		} catch (exp) {}
    		wc.close();
    	},
    	
    	//添加数据
    	add : function () {
    		var wc = this, div = wc.div, iframe = wc.iframe;
    		wc.init_node();
    		div.style.height = iframe.style.height = Math.max(document.documentElement.scrollHeight, document.documentElement.offsetHeight) + "px";
    		div.style.width = iframe.style.width = Math.max(document.documentElement.scrollWidth, document.documentElement.offsetWidth) + "px";
    		//document.getElementsByTagName("html")[0].style.overflow = "hidden";
    		document.body.appendChild(iframe);
    		document.body.appendChild(div);
    		document.body.appendChild(wc.node);
    	},
    	
    	//关闭添加框
    	close : function () {
    		var wc = this, div = wc.div, iframe = wc.iframe;
    		document.getElementsByTagName("html")[0].style.overflow = CDrag.IE ? "" : "auto";
    		document.body.removeChild(iframe);
    		document.body.removeChild(div);
    		document.body.removeChild(wc.node);
    	}
    	
    };
    
    CDrag.prototype = {
    	
    	//初始化成员
    	initialize : function () {
    		var wc = this;
    		wc.table = new CDrag.Table; //建立表格对象
    		wc.addc = new CDrag.Add(wc); //建立添加对象
    		wc.iFunc = wc.eFunc = null;
    		wc.obj = { on : { a : null, b : "" }, row : null, left : 0, top : 0 };
    		wc.temp = { row : null, div : document.createElement("div") };
    		wc.temp.div.setAttribute(CDrag.IE ? "className" : "class", "CDrag_temp_div");
    		wc.temp.div.innerHTML = "&nbsp;";
    	},
    	
    	//获取鼠标位置
    	reMouse : function (a) {
    		var e = Object.reEvent();
    		return {
    			x : document.documentElement.scrollLeft + e.clientX,
    			y : document.documentElement.scrollTop + e.clientY
    		};
    	},
    	
    	//获取元素绝对位置
    	rePosition : function (o) {
    		var $x = $y = 0;
    		do {
    			$x += o.offsetLeft;
    			$y += o.offsetTop;
    		} while ((o = o.offsetParent)); // && o.tagName != "BODY"
    		return { x : $x, y : $y };
    	},
    	
    	//处理拖拽过程细节
    	execMove : function (status, on_obj, in_obj, place) {
    		var wc = this, obj = wc.obj.on, temp = wc.temp, px;
    		
    		obj.a = on_obj, obj.b = status;
    		
    		if (place == 0) {
    		//向上
    			px = in_obj.element.clientWidth;
    			in_obj.element.parentNode.insertBefore(temp.div, in_obj.element);
    		} else if (place == 1) {
    		//新加入
    			px = in_obj.element.clientWidth - 2;
    			in_obj.element.appendChild(temp.div);
    		} else {
    		//向下
    			px = in_obj.element.clientWidth;
    			in_obj.element.parentNode.appendChild(temp.div);
    		}
    		var maxw=$('col-mid').offsetWidth;
			px=(px<maxw)?px-4:maxw-4;
    		wc.obj.left = Math.ceil(px / temp.div.offsetWidth * wc.obj.left); //处理拖拽换行后宽度变化，鼠标距离拖拽物的距离的误差.
    		temp.row.style.width = temp.div.style.width = px + "px"; //处理换列后对象宽度变化
    	},
    	
    	//当拖动开始时设置参数
    	sMove : function (o) {
    		
    		var wc = this;
    		if (o.locks || wc.iFunc || wc.eFinc) return;//
    		
    		var mouse = wc.reMouse(), obj = wc.obj, temp = wc.temp, div = o.element, position = wc.rePosition(div);
    		obj.row = o;
    		obj.on.b = "me";
    		obj.left = mouse.x - position.x;
    		obj.top = mouse.y - position.y;
    		
    		temp.row = document.body.appendChild(div.cloneNode(true)); //复制预拖拽对象
    		temp.row.style.width = div.clientWidth + "px";
    		with (temp.row.style) {
    		//设置复制对象
    			position = "absolute";
    			left = mouse.x - obj.left + "px";
    			top = mouse.y - obj.top + "px";
    			zIndex = "100";
    			opacity = "0.3";
				overflow="hidden";
    			height=div.clientHeight + "px";
    			filter = "alpha(opacity:30)";
    		}
    		
    		with (temp.div.style) {
    		//设置站位对象
    			height = div.clientHeight + "px";
    			//width = div.clientWidth + "px";
				border="#999 dashed 1px";
    		}
    		
    
    		div.parentNode.replaceChild(temp.div, div);
    		
    		wc.iFunc = Object.addEvent(document, ["onmousemove"], wc.iMove.bind(wc));
    		wc.eFunc = Object.addEvent(document, ["onmouseup"], wc.eMove.bind(wc));
    		document.onselectstart = new Function("return false");
    	},
    	
    	//当鼠标移动时设置参数
    	iMove : function () {
    		var wc = this, mouse = wc.reMouse(), cols = wc.table.items, obj = wc.obj, temp = wc.temp,
    			row = obj.row, div = temp.row, t_position, t_cols, t_rows, i, j;
    		
    		with (div.style) {
    			left = mouse.x - obj.left + "px";
    			top = mouse.y - obj.top + "px";
    		}
    		/**
    		 *purecolor@foxmail.com
    		 *使划过一半就替换位置
    		 */
    		window['oldMy']=window['oldMy']?window['oldMy']:mouse.y;
			var typeint=(window['oldMy']>mouse.y)?90:-70;
			window['oldMy']=mouse.y;
    						 
    		for (i = 0 ; i < cols.length ; i ++) {
    			t_cols = cols[i];
    			//if (t_cols != obj.row.parent) continue;
    			t_position = wc.rePosition(t_cols.element);
    			if (t_position.x < mouse.x && t_position.x + t_cols.element.offsetWidth > mouse.x) {
    				if (t_cols.items.length > 0) { //如果此列行数大于0
    					/**
    					 *purecolor@foxmail.com
    					 *插入最上列
    					 */
    					if (t_cols.items[0] != obj.row && wc.rePosition(t_cols.items[0].element).y +typeint > mouse.y) {
    						//如果第一行不为拖拽对象并且鼠标位置大于第一行的位置即是最上。。
    						//向上
    						wc.execMove("up", t_cols.items[0], t_cols.items[0], 0);
    					} else if (t_cols.items.length > 1 && t_cols.items[0] == row &&
    						wc.rePosition(t_cols.items[1].element).y +typeint > mouse.y) {
    						//如果第一行是拖拽对象而第鼠标大于第二行位置则，没有动。。
    						//向上
    						wc.execMove("me", t_cols.items[1], t_cols.items[1], 0);
    					} else {
    						/**
    						 *purecolor@foxmail.com
    						 *向下插入数据
    						 */
    						for (j = t_cols.items.length - 1 ; j > -1 ; j --) {
    							//重最下行向上查询
    							t_rows = t_cols.items[j];
    							if (t_rows == obj.row) {
    								if (t_cols.items.length == 1) {
    								//如果拖拽的是此列最后一行
    									wc.execMove("me", t_cols, t_cols, 1);
    								}
    								continue;
    							}
    							if (wc.rePosition(t_rows.element).y+typeint < mouse.y) {
    								//如果鼠标大于这行则在这行下面
    								if (t_rows.id + 1 < t_cols.items.length && t_cols.items[t_rows.id + 1] != obj.row) {
    									//如果这行有下一行则重这行下一行的上面插入
    									wc.execMove("down", t_rows, t_cols.items[t_rows.id + 1], 0);
    								} else if (t_rows.id + 2 < t_cols.items.length) {
    									//如果这行下一行是拖拽对象则插入到下两行，即拖拽对象返回原位
    									wc.execMove("me", null, t_cols.items[t_rows.id + 2], 0);
    								} else {
    									//前面都没有满足则放在最低行
    									wc.execMove("down", t_rows, t_rows, 2);
    								}
    								return;
    							}
    						}
    					}
    					
    				} else {
    				//此列无内容添加新行
    					wc.execMove("new", t_cols, t_cols, 1);
    				}
    			}
    		}
    	},
    	
    	//当鼠标释放时设置参数
    	eMove : function () {
    		var wc = this, obj = wc.obj, temp = wc.temp, row = obj.row, div = row.element, o_cols, n_cols, number;
    		
    		if (obj.on.b != "me") {
    			number = (obj.on.b == "down" ? obj.on.a.id + 1 : 0);
    			n_cols = (obj.on.b != "new" ? obj.on.a.parent : obj.on.a);
    			o_cols = obj.row.parent;
    			n_cols.ins(number, o_cols.del(obj.row.id));
    			
    			wc.set_cookie();
    		}
    		
    		temp.div.parentNode.replaceChild(div, temp.div);
    		temp.row.parentNode.removeChild(temp.row);
    		delete temp.row;
    		
    		Object.delEvent(document, ["onmousemove"], wc.iFunc);
    		Object.delEvent(document, ["onmouseup"], wc.eFunc);
    		document.onselectstart = wc.iFunc = wc.eFunc = null;
    	},
    	
    	//变大变小
    	reduce : function (o) {
    		var wc = this;
    		if ((o.window = (o.window == 1 ? 0 : 1))) {
    			o.content.style.display = "block";
    			o.reduce.className = "zoom-s";
    			o.reduce.title='缩小';
    		} else {
    			o.content.style.display = "none";
    			o.reduce.className = "zoom-b";
    			o.reduce.title='放大';
    		}
    		wc.set_cookie();
    	},
    	
    	//锁定
    	lock : function (o) {
    		var wc = this;
    		if (o.locks) {
    			o.locks = false;
    			o.lock.className = "lock";
    			o.lock.title='锁定';
    		} else {
    			o.locks = true;
    			o.lock.className = "unlock";
    			o.lock.title='解锁';
    		}
    		wc.set_cookie();
    	},
    	
    	//关闭窗口
    	close : function (o) {
    		var wc = this;
    		wc.remove(o);
    		wc.set_cookie();
    	},
    	
    	//移除对象
    	remove : function (o) {
    	
    		var wc = this, parent = o.parent;
    		Object.delEvent(o.close, ["onclick"], o.closeFunc);
    		Object.delEvent(o.lock, ["onclick"], o.lockFunc);
    		Object.delEvent(o.reduce, ["onclick"], o.reduceFunc);
    		Object.delEvent(o.title, ["onmousedown"], o.mousedown);
    		
    		o.mousedown = o.reduceFunc = o.lockFunc = o.closeFunc = null;
    		parent.element.removeChild(o.element);
    		parent.del(o.id);
    		delete wc.Class;
    		delete o;
    	},
    	
    	//生成json串
    	create_json : function () {
    		var wc = this, cols = wc.table.items, a = [], b = [], i, j, r,poststr=[];
    		/*//原始JSON：请勿删除
    		for (i = 0 ; i < cols.length ; i ++) {
    			for (r = cols[i].items, j = 0 ; j < r.length ; j ++)
    				b[b.length] = "{id:'" + r[j].root_id + "',window:" + r[j].window + ",locks:" + r[j].locks + "}";
    			a[a.length] = "cols:'" + cols[i].element.id + "',rows:[" + b.splice(0, b.length).join(",") + "]";
    		}*/
    		for (i = 0 ; i < cols.length ; i ++) {
    			for (r = cols[i].items, j = 0 ; j < r.length ; j ++){
    				b[b.length] = "{id:'" + r[j].root_id + "',window:" + r[j].window + ",locks:" + r[j].locks + "}";
    				//poststr[poststr.length]= r[j].root_id + "," + i + "," +j;
					poststr[poststr.length]= r[j].root_id + "," + i + "," +j+','+r[j].locks+','+r[j].window;
    			}
    			
    			a[a.length] = "cols:'" + cols[i].element.id + "',rows:[" + b.splice(0, b.length).join(",") + "]";
    		}
    		/*确定和重置时触发ajax*/
    		if(window['isbtnsubmitok']){
				//alert(CDrag.option.url);
    			var ajax=new CDrag.Ajax;
    			ajax.send(CDrag.option.url,CDrag.option.paramName+'='+poststr.join('|'),function(){
    				return false;
    			});
				//alert(poststr);
    			window['isbtnsubmitok']=false;
    		}
    		return escape("[{" + a.join("},{") + "}]");
    	},
    	
    	//解释json成对象
    	parse_json : function (cookie) {
    		return eval("(" + cookie + ")");
    	},
    	
    	//获取COOKIE
    	get_cookie : function () {
    		return (/CDrag=([^;]+)(?:;|$)/.exec(document.cookie) || [,])[1];
    	},
    	
    	//设置COOKIE
    	set_cookie : function () {
    		var wc = this, date = new Date;
    		date.setDate(date.getDate() + 1);
    		document.cookie = "CDrag=" + wc.create_json() + ";expires=" + date.toGMTString();
    	},
    	
    	//删除COOKIE
    	del_cookie : function () {
    	
    		var wc = this, cookie = wc.get_cookie(), date;
    		if (cookie) {
    			date = new Date;
    			date.setTime(date.getTime() - 1);
    			document.cookie = "CDrag=" + cookie + ";expires=" + date.toGMTString();
    		}
    	},
    	
    	//初始化成员
    	parse : function (o) {
    	
    		try {
    			var wc = this, table = wc.table, cols, rows, div, i, j;
    			for (i = 0 ; i < o.length ; i ++) {
    				div = $(o[i].cols), cols = table.add(div);
    				for (j = 0 ; j < o[i].rows.length ; j ++){
    					wc.add(cols.add(o[i].rows[j].id, o[i].rows[j].window, o[i].rows[j].locks));
    				}
    				
    			}
    		} catch (exp) {
    			wc.del_cookie();
    		}
    	},
    	
    	//添加对象
    	add : function (o) {
    		var wc = this;
    		o.mousedown = Object.addEvent(o.title, ["onmousedown"], o.lockF(wc, wc.sMove, o));
    		o.reduceFunc = Object.addEvent(o.reduce, ["onclick"], o.lockF(wc, wc.reduce, o));
    		o.lockFunc = Object.addEvent(o.lock, ["onclick"], wc.lock.bind(wc, o));
    		o.closeFunc = Object.addEvent(o.close, ["onclick"], o.lockF(wc, wc.close, o));
    	},
    	
    	append : function () {
    		var wc = this;
    		wc.addc.add();
    	}
    	
 };

 
 
 Object.addEvent(window, ["onload"], function () {
	CDrag.database.json =CDrag.option.layoutJson;	
	var wc = new CDrag, cookie = wc.get_cookie();
	json = CDrag.option.defaultJson;//cookie ? wc.parse_json(unescape(cookie)) : 
	
	wc.parse(json);
	
	(function (wc) {
			
		$("ADD_CDrag").onclick = function () {
			wc.append();
		};	
		
	})(wc);
	window['wc']=wc;
	wc = null;
});
/*
	使用说明：
		//配置信息
		//页面元素ajax提交接口
		CDrag.option.url="/oa_tidy/ElementAction_saveLayout.do";
		//页面元素ajax提交接口
		CDrag.option.url="/oa_tidy/ElementAction_saveLayout.do";
		//页面元素ajax提交接口参数名称
		CDrag.option.paramName="elementData";
		//用户所有元素JSON对象
		CDrag.option.layoutJson=[
			{id:'栏目唯一编号',title:'栏目标题',imgsrc:'图标地址',className :'栏目信息',src :'AJAX接口[外部js]'},
		];
		//界面列表存在元素
		CDrag.option.defaultJson=[
			//左侧栏目
			{ cols : "col-left", rows : [
				{id:"栏目唯一编号", window : 栏目窗口是否展开[1 or 0], locks : 栏目窗口是否锁定[true,false]},
			]},
			//中间栏目
			{ cols : "col-mid", rows : [
				{id:"栏目唯一编号", window : 栏目窗口是否展开[1 or 0], locks : 栏目窗口是否锁定[true,false]},
			]},
			//右侧栏目
			{ cols : "col-right", rows : [
				{id:"栏目唯一编号", window : 栏目窗口是否展开[1 or 0], locks : 栏目窗口是否锁定[true,false]},
			]}
		];
*/