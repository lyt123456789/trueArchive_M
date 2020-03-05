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
 *		     
 */
(function($){
	$.fn.extend({
		parseXML:function(dom,option){
			var that=$(dom);
			var html={};
			var tpl='';
			
			//配置数据
			var _default={
				reg:/\{\w+\}/gi,
				lc:'{',
				rc:'}',
				script:true,
				scriptID:'script',
				tplBox:'<ul class="indexiframelist skin3">{item}</ul>{more}{nodata}',
				tplhtml:{
					item:'<li title="{title2}"><a href="{link}"><span class="timer">{pubDate}</span>{title}<span class="{new}"></span></a></li>',
					more:'<div class="moreLink"><a href="{link}">{title}</a></div>',
					script:'function {funcationname}{{funcationcode}}',
					nodata:'<div class="nodata">{title}</div>'
				}
			}		
			var option=$.extend(_default,option);
			
			//开始处理tplBody
			$.each(option.tplhtml,function(o,str){
				var rego=str.match(option.reg);
				html[o]=[];
				$(o,that).each(function(){
					var D=$(this),shtml=str;
					$.each(rego,function(i,c){
						if(o=="item"&&c=="{title}"){
							shtml=shtml.replace(c,cutstr(D.find(c.replace(option.lc,'').replace(option.rc,'')).text(),13));
						}else{
							shtml=shtml.replace(c,D.find(c.replace(option.lc,'').replace(option.rc,'')).text());
						}
					});	
					html[o].push(shtml);
				});
			});	
			
			//开始处理tplBox
			var tplHtml=option.tplBox;
			tpl=option.tplBox.match(option.reg);
			$.each(tpl,function(i,c){
				tplHtml=tplHtml.replace(c,html[c.replace(option.lc,'').replace(option.rc,'')].join(''));			
			});	
			//alert(html.item);
			//向页面添加script
			if(option.script){			
				var script=document.createElement('script');
				script.type="text/javascript";
				script.text=html[option.scriptID].join('');
				$('html > head').append(script);
			}	
			
			return tplHtml;
		}
	});
})(jQuery);
function cutstr(str,strlen){
    var temp_i=0;
	var temp=0;
	var temp_str="";
	str=str.replace(/[\r\n]/g,'').replace(/<[^>]+>/g,"");//去掉所有的html标记 
	for(temp_i=0;temp_i<str.length;temp_i++){
	   if(Math.abs(str.charCodeAt(temp_i))>255)
	      temp+=2;
	   else 
	      temp+=1;
	   
	   if(temp>strlen*2) {
	       temp_str=str.substr(0,temp_i);//+"...";
		   break;
		   }
		else{
		   temp_str=str;
		}
	}
   return  temp_str;   
}