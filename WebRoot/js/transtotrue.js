		//STEP1:遍历所有的表单元素，并将名称转换成弹性表单所允许的。
		//STEP2:构造JSON，先在JSON中添加头文件信息。
		//STEP3:将所有的表单元素的位置信息，以及上下关系信息全部取出来。
		function coverFormToTrueform(scope, factor) {
			var truefile = {};
			truefile["version"] = "2.1";
			truefile["width"] = 1024;
			truefile["distance"] = 0; //水平缩进是0,这个参数应该是不会改了。
			truefile["distance_vertical"] = 53; //垂直缩进是0，这个也应该是不会改了。//这个根据转换来的
			truefile["tr_height"] = 40;
			//添加truefile的基本信息
			var page = [];
			var subViews = scope.children();
			// console.log(subViews);
			parserChildren(subViews, page);
			// $('*',scope).each(function(i,element){
			// });
			truefile["page"] = page;
			// alert(JSON.stringify(truefile));
			// console.log(JSON.stringify(truefile));
			// var trueString = JSON.stringify(truefile);
			// console.log(trueString);
			// alert(trueString);
			return truefile;
		}

		function parserChildren(subViews, page, pad) {
			for (var i = 0; i < subViews.length; i++) {
				var element = subViews[i];
				// console.log(element);
				//直接就在这里操作element吧。
				var object = {}; //要添加到page数组中的object。
				var type = element.tagName.toLowerCase();
				switch (type) {
					//TODO:这边的p标签也还有可能是其他的。
					case 'p':
					case 'font':
					case 'strong':
						{
							// debugger;
							object["type"] = "p";
							var p = {};
							object["p"] = p;
							p["height"] = $(element).outerHeight();
							if (p["height"] > 0) {
								p["padding-top"] = converPxStringToInt(element.style.paddingTop); //这边有单位。
								if (!!p["padding-top"] === false || p["padding-top"] === 0) {
									p["padding-top"] = 0;
								}
								p["id"] = getId(element);
								//p标签
								var values = parserElementSubobject(element);
								if (values.length > 0 && values[0]["type"] === 'p') {
									var subValue = values[0]["p"];
									p["values"] = subValue["values"];
									p["padding-top"] = (p["height"] - subValue["height"]) > p["padding-top"] ? p["padding-top"] : (p["height"] - subValue["height"]);
								} else {
									p["values"] = values;
								}
								page.push(object);
							}
							if (!!pad === true) {
								var height = $(element).parent().outerHeight();
								p["padding-top"] = height - p["height"];
								if(p["padding-top"] > pad) {
									p["padding-top"] = pad;
								}
								p["height"] = height - (height - p["height"] - p["padding-top"]);
							}
						}
						break;

					case 'br':
						{
							//caption 解析的时候要注意br。
							object["type"] = "br";
							// console.log('br = ',$(element).parent().css('line-height'),$(element).parent().css('font-size'));
							object["br"] = $(element).parent().css('line-height');
							if (object["br"] === 'normal') {
								//normal 为 font-size * 1.14。
								object["br"] = (converPxStringToInt($(element).parent().css('font-size')) * 1.14).toFixed(0);
							} else {
								object["br"] = converPxStringToInt(object["br"]);
							}
							//判断element是不是parent的最后一个
							if (i != subViews.length - 1) {
								page.push(object);
							} else {
								//最后一个br是不显示的。
							}
						}
						break;

					case 'table':
						{
							object["type"] = "table";
							object["table"] = parserTable(element);
							page.push(object);
						}
						break;

					case 'div':
					case 'center': //对于这个center，是不是会有更好的处理办法？
						{
							// debugger;
							var subs = element.children;
							var pad = converPxStringToInt(element.style.paddingTop);
							if (!!subs && subs.length) {
								parserChildren(subs, page, pad);
							}
							//TODO:这边对checkbox要进行特殊处理。
						}
						break;

					case 'input':
						{
							console.log('暂未解析的数据格式 input');
						}
						break;

					default:
						{
							// debug: 只有在debug模式下才开启。
							// console.log('暂未解析的参数 = ' + type); //没有解析的表单信息
						}
						break;
				}
			}
		}

		//解析text中的文字数据
		function parserText(element) {
			var text = new Object();
			text["type"] = "words";
			text["text"] = $(element).html();
			// text["text"] = text["text"].replace(' ','&nbsp;');
			text["color"] = getHexBackgroundColor(element, "color");
			if (text["color"] == null || text["color"] == undefined || text["color"].length === 0) {
				text["color"] = "#000000"
			}
			text["font-size"] = converPxStringToInt($(element).css("font-size"));
			text["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
			text["font-family"] = $(element).css("font-family");
			var vAlign = element.vAlign;
			if (!!vAlign == false) {
				vAlign = $(element).css("vertical-align");
			}
			// $(element).css("layout-flow");
			if (!!vAlign === false) {
				//虽然是top，但是，字是有内边距的值的
				text["valign"] = "middle"; //默认是居中的。 
			} else {
				text["valign"] = vAlign === 'baseline' ? 'middle' : vAlign; //去除baseline
				text["valign"] = text["valign"] === 'auto' ? 'middle':text["valign"];       //这个也认为是居中吧
			}
			var align = element.align;
			var alig = $(element).css("text-align");
			text["text-align"] = alig.replace(/-webkit-/, "");
			text["text-align"] = text["text-align"].replace(/ /, "");
			text["text-align"] = text["text-align"].replace(/start/, "left");
			return text;
		}

		function parserTable(element) {
			//TODO:id号与input框的整合。
			var table = {};
			table["id"] = "table";
			table["width"] = $(element).outerWidth(); //就是可见宽度
			table["height"] = $(element).outerHeight(); //这个作为子tableView就非常有用了的。
			table["align"] = element.align.length == 0 ? "center" : element.align;
			var style = element.style;
			table["border"] = parserBorder(style, element);
			var values = [];
			for (var i = 0; i < element.children.length; i++) {
				// console.log(element.children[i].tagName.toLowerCase());
				if (element.children[i].tagName.toLowerCase() == 'tbody') {
					var tbody = element.children[i];
					for (var j = 0; j < tbody.children.length; j++) {
						var tr = parserTableTr(tbody.children[j]);
						var t = {};
						t["tr"] = tr;
						values.push(t);
					}
				} else if (element.children[i].tagName.toLowerCase() == 'caption') {
					//解析头文件呢
					var cap = element.children[i];
					var c = {};
					c["height"] = $(cap).outerHeight();
					c["width"] = $(cap).outerWidth();
					c["values"] = [];

					for (var j = 0; j < cap.children.length; j++) {
						// console.log(cap.children[j]);
						//TODO:这边就和table的解析有点像了。
						parserChildren([cap.children[j]], c["values"]);
					}
					table["caption"] = c; //只有一个caption
				}
			}
			// $('tbody',element).each(function(i,tbody) {
			// 	//在此处继续循环
			// 	// console.log(tbody);
			// 	$('tr',tbody).each(function(j,tr){
			// 		// console.log(tr);
			// 		var tr = parserTableTr(tr);
			// 		var t = {};
			// 		t["tr"] = tr;
			// 		values.push(t);
			// 	});
			// });
			table["values"] = values;
			return table;
		}
		
        function parserTableColor(id){
			var rgb = $(id).closest("table").css("border-color");
			if ($.browser.msie && $.browser.version > 8 || $.browser.mozilla || $.browser.webkit) {
				rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
				function hex(x) {
					return ("0" + parseInt(x).toString(16)).slice(-2);
				}
				if(rgb && rgb.length > 2) {
					rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
				} else {
					rgb = "#000000";
				}
				return rgb;
			}
			//优化颜色,垃圾IE，没有办法呀

			if(!!rgb && rgb.length) {
				var strs=rgb.split(" ");
				if(strs.length) {
					var one = strs[0];
					if(one === 'red') {
						rgb = '#ff0000';
					} else if(one === 'blue') {
						rgb = '#0000ff';
					} else if(one === 'black') {
						rgb = '#000000';
					} else if(one === 'white') {
						rgb = '#ffffff';
					} else if(one === 'green') {
						rgb = '#00ff00';
					} else {
						rgb = one;
					}
				}

			}
			return rgb;
		}		

		//解析table的数据
		function parserTableTr(element) {
			var tr = {};
			var values = [];
			//本来设计是tr也要有id的，后来发现不太好弄，现在，td才会有id，因为td会有input框。
			tr["height"] = $(element).outerHeight(); //包含边框在内的高度。
			//TODO:这边还有可能是tr
			for (var i = 0; i < element.children.length; i++) {
				if (element.children[i].tagName.toLowerCase() === 'td') {
					var td = parserTd(element.children[i]);
					var t = {};
					t["td"] = td;
					values.push(t);
				}
			}
			// $('td',element).each(function(i,td){
			// 	//在此解析td的所有数据信息。
			// 	var td = parserTd(td);
			// 	var t = {};
			// 	t["td"] = td;
			// 	values.push(t);
			// });
			tr["values"] = values;
			return tr;
		}

		function getId(element) {
			return !!element.name?element.name:''; //这个是有问题的。 
		}

		function parserTd(element) {
			// debugger;
			//rowspan colspan width border text。大体就这么多的信息。
			var td = {"type":"td"};
			// console.log(element);
			var cols = element.colSpan;
			if (cols > 0) {
				td["colspan"] = cols; //这个参数解析器是不会解析的。但是暂时也转了吧。
			}
			var rows = element.rowSpan;
			if (rows > 0) {
				td["rowspan"] = rows;
			}
			td["width"] = $(element).outerWidth();
			var style = element.style;
			td["border"] = parserBorder(style, element);
			var values = parserElementSubobject(element, td);
			td["values"] = values; //text呀什么的
			return td;
		}

		//解析子元素
		function parserElementSubobject(element, td) {
			var value = [];
			if (!!element.childNodes === true) {
				var mutiText = '';
				var nodes = getInUseNodes(element.childNodes);
				// debugger;
				for (var i = 0; i < nodes.length; i++) {
					var that = nodes[i];
					if (that.nodeType === 3) {
						mutiText = mutiText + '\n' + trimWithIE($(that).text());
						// console.log(typeof(that), mutiText, that.nodeName);
						if (i === nodes.length - 1 && trimWithIE(mutiText).length > 0) {
							var text_obj = parserText(element);
							text_obj["text"] = trimWithIE(text_obj["text"]).replace('<br>','\r\n').replaceAll('&nbsp;',''); //这边的字，要合到一个里面去
							value.push(text_obj);
							mutiText = '';
						}
					} else if (that.nodeType === 1) {
						var innerStyle = $(that).attr("innerStyle");
						if (!!that.type  || (!!that.tagName && that.tagName.toLowerCase() === 'span')) {
							//只有这个情况才要。
							if (trimWithIE(mutiText).length > 0) {
								var text_obj = parserText(element);
								text_obj["text"] = trimWithIE(mutiText); //这边的字，要合到一个里面去
								value.push(text_obj);
							}
							mutiText = '';
							if (!!td === true) {
								td["id"] = getId(that);
							}
							//checkbox 要单独处理
							if(!!that.type && that.type === 'hidden') {
								// console.log('type = ',that.type);
							} else if(!!that.type && that.type.toLowerCase() === 'checkbox') {
								value.push(parserCheckBox(element)); //这边要传element
								break; //跳出循环
							} else if(!!that.type && that.type === 'radio') {
								value.push(parserRadio(element)); //这边要传element
								break; //跳出循环
							} else if(!!that.type && that.type === 'true') {
								value.push(parserTrue(element)); //这边要传element
								break; //跳出循环
							}
							else if(nodes.length === 1 && !!$(element).closest("td")) {
								//充满整个框
								var other = parserTdOnly(that)
								var fsize = converPxStringToInt($(element).css("font-size"));
								
								var fFamily =$(element).css("font-family");
								if (fsize > 0) {
									other["font-size"] = fsize;
									if(innerStyle){
										other["font-family"]=fFamily;
									}
								}
								var alig = $(element).css("text-align");
								if (alig.length) {
									other["text-align"] = replaceTextAlign(alig);
								}
								value.push(other);
							} else {
								var other = parserOther(that);
								var fsize = converPxStringToInt($(element).css("font-size"));
								if (fsize > 0) {
									var alig = $(element).css("text-align");
									other["font-size"] = fsize;
								}

								var alig = $(element).css("text-align");
								if (alig.length) {
									other["text-align"] = replaceTextAlign(alig);
								}
								value.push(other);
							}
						} else {
							if (that.tagName.toLowerCase() === 'table' ||  that.tagName.toLowerCase() === 'td'||that.tagName.toLowerCase() === 'font' || that.tagName.toLowerCase() === 'div' || that.tagName.toLowerCase() === 'strong' || that.tagName.toLowerCase() === 'p') {
								//这边统一交给上面去处理。
								//TODO:如果是DIV，可能是会有点问题的。
								if (trimWithIE(mutiText).length > 0) {
									var text_obj = parserText(element);
									text_obj["text"] = trimWithIE(mutiText); //这边的字，要合到一个里面去
									value.push(text_obj);
								}
								if (!!td === true) {
									td["id"] = getId(that);
								}
								mutiText = '';
								if (!!td && td["type"] !== 'table') {
									value = value.concat(parserElementSubobject(that)); //在strong里面的
								} else {
									parserChildren([that], value);
								}
							} else if (that.tagName.toLowerCase() === 'strong') {
								//strong 应该如何处理呢。。(这边不会进来了)
								if (trimWithIE(mutiText).length > 0) {
									var text_obj = parserText(element);
									text_obj["text"] = trimWithIE(mutiText); //这边的字，要合到一个里面去
									value.push(text_obj);
								}
								mutiText = '';
								value = value.concat(parserElementSubobject(that)); //在strong里面的
							} else {
								// console.log('not well', that.tagName.toLowerCase());
							}
						}
					}
				}
				if (trimWithIE(mutiText).length > 0) {
					var text_obj = parserText(element);
					text_obj["text"] = trimWithIE(mutiText); //这边的字，要合到一个里面去
					value.push(text_obj);
				}
				mutiText = '';

			}
			return value;
		}

		function getInUseNodes(nodes) {
			//表单写的真的是太他妈的不规范了。。
			var inNodes = [];
			for(var i = 0; i < nodes.length; i++) {
				var that = nodes[i];
				if(that.nodeType === 1 && !!that.type === false && that.tagName.toLowerCase() !== 'table' 
					&& that.tagName.toLowerCase() !== 'font' && that.tagName.toLowerCase() !== 'div' &&
					 that.tagName.toLowerCase() !== 'strong' && that.tagName.toLowerCase() !== 'p' && 
					 that.tagName.toLowerCase() !== 'span') {
						//去除完全没有用的数据
				} else {
					if(that.nodeType === 3 && 
						trimWithIE($(that).text()) === '') {
						//去除空字符串
					} else {
						if (!!that.tagName && that.tagName.toLowerCase() === 'p' && that.childNodes.length) {
							var subNodes = getInUseNodes(that.childNodes);
							inNodes = inNodes.concat(subNodes); 
						} else {
							inNodes.push(that);
						}
					}
				}
			}
			return inNodes;
		}

		function parserBorder(style, element) {
			// debugger;
			var border = {};
			var bo = style.border;
			// console.log('border:'+ bo, 'style:' + $(element).css('border-style'));
			if (bo.length > 0 && parseInt(bo) === 0) {
				border["border"] = 0;
				border["color"] = "#ffffff";
			} else {
				border["style"] = $(element).css('border-style'); //border 的style。
				if(!!border["style"] && (border["style"] === 'none' || border["style"] === 'hidden')) {
					border["border"] = 0;
					border["color"] = "#ffffff";
				} else {
					border["border"] = 1; //这个只能说明，有border是需要画的，其他啥也不能说明。
					border["top"] = $(element).css('border-top-style') + ' ' + $(element).css('border-top-width') ;
					border["right"] = $(element).css('border-right-style') + ' ' + $(element).css('border-right-width') ;
					border["bottom"] = $(element).css('border-bottom-style') + ' ' + $(element).css('border-bottom-width') ;
					border["left"] = $(element).css('border-left-style') + ' ' + $(element).css('border-left-width') ;
					border["color"] = parserTableColor(element);
					if (border["color"] == null || border["color"] == undefined || border["color"].length === 0) {
						border["color"] = "#000000"
					}
				}
			}
			return border;
		}

		function parserTdOnly(element) {
			var ot=$(element).closest("td")
			var other = {};
			other["type"] = !!element.type?element.type:element.tagName.toLowerCase();
			other["height"] = ot.height();
			other["width"] = ot.width();
			other["id"] = getId(element);
				//用不上
				other["color"] = getHexBackgroundColor(element, "color");
				if (other["color"] == null || other["color"] == undefined || other["color"].length === 0) {
					other["color"] = "#000000"
				}
				other["font-size"] = converPxStringToInt($(element).css("font-size"));
				other["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
				other["valign"] = "middle";
				var alig = $(element).css("text-align");
				other["text-align"] = replaceTextAlign(alig);
			return other;
		}

		//就是和大家在一起的。
		function parserOther(element) {
			var other = {};
			other["type"] = !!element.type?element.type:element.tagName.toLowerCase();
			other["height"] = $(element).height();
			other["width"] = $(element).width();
			other["id"] = getId(element);
			//用不上的
			other["color"] = getHexBackgroundColor(element, "color");
			if (other["color"] == null || other["color"] == undefined || other["color"].length === 0) {
				other["color"] = "#000000"
			}
			other["font-size"] = converPxStringToInt($(element).css("font-size"));
			other["font-family"] = converPxStringToInt($(element).css("font-family"));
			
			other["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
			other["valign"] = "middle";
			var alig = $(element).css("text-align");
			other["text-align"] = replaceTextAlign(alig);
			return other;
		}

		function parserCheckBox(element) {
			var other = {'type':'checkbox'};
			$('input[type=checkbox]',element).each(function(){
				other["id"] = this.name;
				other["height"] = $(element).height();
				other["width"] = $(element).width();

				//用不上的
				other["color"] = getHexBackgroundColor(element, "color");
				if (other["color"] == null || other["color"] == undefined || other["color"].length === 0) {
					other["color"] = "#000000"
				}
				other["font-size"] = converPxStringToInt($(element).css("font-size"));
				other["font-family"] = converPxStringToInt($(element).css("font-family"));
				other["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
				other["valign"] = "middle";
				other["color"] = getHexBackgroundColor(element, "color");
				if (other["color"] == null || other["color"] == undefined || other["color"].length === 0) {
					other["color"] = "#000000"
				}
				other["font-size"] = converPxStringToInt($(element).css("font-size"));
				other["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
				other["valign"] = "middle";
				var alig = $(element).css("text-align");
				other["text-align"] = replaceTextAlign(alig);
				
			});
			return other;
		}

		function parserRadio(element) {
			var other = {'type':'radio'};
			$('input[type=radio]',element).each(function(){
				other["id"] = this.name;
				other["height"] = $(this).height();
				other["width"] = $(this).width();
				//用不上的
				other["color"] = getHexBackgroundColor(element, "color");
				if (other["color"] == null || other["color"] == undefined || other["color"].length === 0) {
					other["color"] = "#000000"
				}
				other["font-size"] = converPxStringToInt($(element).css("font-size"));
				other["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
				other["valign"] = "middle";
				var alig = $(element).css("text-align");
				other["text-align"] = replaceTextAlign(alig);
			});
			return other
		}
		
		
		function parserTrue(element) {
			var other = {'type':'true'};
			$('input[type=true]',element).each(function(){
				other["id"] = this.name;
				other["height"] = $(this).height();
				other["width"] = $(this).width();
				//用不上的
				other["color"] = getHexBackgroundColor(element, "color");
				if (other["color"] == null || other["color"] == undefined || other["color"].length === 0) {
					other["color"] = "#000000"
				}
				other["font-size"] = converPxStringToInt($(element).css("font-size"));
				other["bold"] = $(element).css("font-weight") === "normal" ? 0 : 1;
				other["valign"] = "middle";
				var alig = $(element).css("text-align");
				other["text-align"] = replaceTextAlign(alig);
			});
			return other
		}

		function replaceTextAlign(str) {
			str = str.replace(/-webkit-/, "");
			str = str.replace(/ /, "");
			str = str.replace(/start/, "left");
			return str;
		}
		//将带单位的string变成int型的。
		function converPxStringToInt(px) {
			return parseInt(px);
		}

		//rgb转16进制
		function rgb2hex(rgb) {
			if (rgb.charAt(0) == '#')
				return rgb;
			var ds = rgb.split(/\D+/);
			var decimal = Number(ds[1]) * 65536 + Number(ds[2]) * 256 + Number(ds[3]);
			return "#" + zero_fill_hex(decimal, 6);
		}

		function getHexBackgroundColor(id, property) {
			var rgb = $(id).css(property);
			if ($.browser.msie && $.browser.version > 8 || $.browser.mozilla || $.browser.webkit) {
				rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
				function hex(x) {
					return ("0" + parseInt(x).toString(16)).slice(-2);
				}
				if(rgb && rgb.length > 2) {
					rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
				} else {
					rgb = "#000000";
				}
				return rgb;
			}
			//优化颜色,垃圾IE，没有办法呀

			if(!!rgb && rgb.length) {
				var strs=rgb.split(" ");
				if(strs.length) {
					var one = strs[0];
					if(one === 'red') {
						rgb = '#ff0000';
					} else if(one === 'blue') {
						rgb = '#0000ff';
					} else if(one === 'black') {
						rgb = '#000000';
					} else if(one === 'white') {
						rgb = '#ffffff';
					} else if(one === 'green') {
						rgb = '#00ff00';
					} else {
						rgb = one;
					}
				}

			}
			return rgb;
		}

		//垃圾IE实在是没有办法呀
		function trimWithIE(str) {
			str = str.replaceAll(' ',' '); //必须，中文空格的问题。
			str = $.trim(str);
			return str;
		}

		String.prototype.replaceAll = function(s1,s2) { 
			// g 执行全局匹配（查找所有匹配而非在找到第一个匹配后停止）。
			// m 执行多行匹配
			return this.replace(new RegExp(s1,"gm"),s2); 
		}