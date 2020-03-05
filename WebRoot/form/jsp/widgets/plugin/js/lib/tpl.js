/** 微型模板引擎
 * Simple JavaScript Templating
 * Copyright (c) John Resig
 * MIT Licensed
 * http://ejohn.org/
 * @param {String} 可以是模板字符串也可以是装载模板HTML标签的ID
 * @param {Object} 给模板附加数据
 * @return {String} 解析好的模板
 */
;define(function(require, exports, module){
	var $=require("jquery"),cache = {};
	$.tpl=function tpl(str,data){
		var fn=!/\W/.test(str)?
			cache[str]=cache[str]||
						tpl(document.getElementById(str).innerHTML):
			new Function("obj",
				"var p=[],print=function(){p.push.apply(p,arguments);};"+
				"with(obj{p.push('" +
				str
					.replace(/[\r\t\n]/g," ")
					.split("<%").join("\t")
					.replace(/((^|%>)[^\t]*)'/g,"$1\r")
					.replace(/\t=(.*?)%>/g,"',$1,'")
					.split("\t").join("');")
					.split("%>").join("p.push('")
					.split("\r").join("\\'")
				+"');}return p.join('');"
			);
			return data?fn(data):fn;
	};
	$.XMLtpl=function(xml,options){
		var dom=$(xml),html={},tmpls;
		var settings=$.extend({
			reg:/\{\w+\}/gi,
			lc:'{',
			rc:'}',
			tplBody:'<div>{item}</div>',
			tplhtml:{
				item:'{title}'
			},
		},options);
		//开始处理tplBody
		$.each(settings.tplhtml,function(o,str){
			var rego=str.match(settings.reg);
			html[o]=[];
			$(o,dom).each(function(){
				var D=$(this),_str=str;
				$.each(rego,function(i,c){
					_str=_str.replace(c,D.find(c.replace(settings.lc,'').replace(settings.rc,'')).text());
					html[o].push(_str);
				});
			});
		});
		//开始处理tplPags
		var tmpls=settings.tplBody,regs=_str.match(settings.reg);
		$.each(regs,function(i,c){
			tmpls=tmpls.replace(c,html[c.replace(settings.lc,'').replace(settings.rc,'')].join(''));	
		});
		return tmpls;
	};
});
