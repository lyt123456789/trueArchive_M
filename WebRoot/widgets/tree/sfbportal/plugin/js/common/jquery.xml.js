/**
 *	jQuery插件
 *	Author:	purecolor@foxmail.com
 *	Date  :	2011-02-16
 *	Params: dom：XMLDocument ,
 *			_default:{
 *				reg:匹配标签正则,
 *				lc:标签左结束符,
 *				rc:标签右结束符,
 *				script:是否包含script,
 *				scriptID:包含script的节点,
 *				tplBox:模板,
 *				tplhtml:信息条目
 *			}
 *	Return: HTMLDocument
 *  Note  : XML处理插件
 *	XML示例：
		 <root>
			<script>
				<funcationname></funcationname>
				<funcationcode></funcationcode>
			</script>
			<item>
				<title><![CDATA[ 标题 ]]></title>
				<link><![CDATA[ 链接 ]]></link>
				<pubDate><![CDATA[ 时间 ]]></pubDate>
			</item>
			<more>
				<title><![CDATA[ 标题 ]]></title>
				<link><![CDATA[ 链接 ]]></link>
			</more>
		 </root>	     
 */

(function($){
	$.fn.extend({
		parseXML:function(dom,option){
			var that=$(dom);
			var _html={};
			var tpl='';
			
			//配置数据
			var _default={
				reg:/\{\w+\}/gi,
				lc:'{',
				rc:'}',
				script:true,
				scriptID:'script',
				tplBox:'<ul>{item}</ul>{more}',
				tplhtml:{
					item:'<li class="cbo"><a href="{link}">{title}</a><span class="timer">{pubDate}</span></li>',
					more:'<div class="moreLink"><a href="{link}">{title}</a></div>',
					script:'function {funcationname}{{funcationcode}}'
				}
			}		
			var option=$.extend(_default,option);
			
			//开始处理tplBody
			$.each(option.tplhtml,function(o,str){
				var rego=str.match(option.reg);
				_html[o]=[];
				$(o,that).each(function(){
					var D=$(this),shtml=str;
					$.each(rego,function(i,c){
						shtml=shtml.replace(c,D.find(c.replace(option.lc,'').replace(option.rc,'')).text());
					});	
					_html[o].push(shtml);
				});
			});	
			
			//开始处理tplBox
			var tplHtml=option.tplBox;
			tpl=option.tplBox.match(option.reg);
			$.each(tpl,function(i,c){
				tplHtml=tplHtml.replace(c,_html[c.replace(option.lc,'').replace(option.rc,'')].join(''));
			});	
			
			//向页面添加script
			if(option.script){			
				//var _script=document.createElement('script');
				//_script.type="text/javascript";
				//_script.text=_html[option.scriptID].join('');
				$('html>head').append('<script>'+_html[option.scriptID].join('')+'</'+'script>');
			}	
			return tplHtml;
		}
	});
})(jQuery);