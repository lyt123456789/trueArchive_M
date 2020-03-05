// JavaScript Document
var News = Class.create();

News.prototype = {
	initialize : function (params) {
		this.params=params;
		var wc = this;
		wc.parent = null;
	},	
	open : function () {
		var wc = this;
		var id=wc.parent.content.id;
		var url=this.params.replace('&','#o#');
		
		if(getSWFAJAX(url,id)){
			wc.parent.content.innerHTML = "loading";
			var href=this.params;
			$.ajax({
			   type: "POST",
			   url: href,
			   dataType:'xml',
			   success: function(msg){
			     var content=jQuery(document).parseXML(msg);
			     	content=(content!='<ul></ul>')?content:'<p style="line-height:150px;text-align:center;">栏目暂时没有信息！</p>';
					wc.parent.content.innerHTML =content;
			   }
			}); 
		}
	}
};
News.loaded = true;
//swf调用方法
function getSWFAJAX(url,target){
	var returnval=true,href=url;
	if(/^http(s*?):\/\//.test(href)){
		href=href.replace(/^(http(s*?):\/\/)/,'');
		if(/\//.test(href)){
			href=href.substring(0,href.indexOf('/'));
		}
		//alert(href+'!=='+location.host+' :'+(href!==location.host));
		if(href!==location.host){
			returnval=false;
			var fvs=flashChecker();
			if(fvs.v<10){
				/**必要条件检测**/
				$.dialog({
					title:'系统提示：',
					content:'<p>你的系统版本flashplayer低于系统要求，请点击下方链接下载新的flashplayer</p><h3><a href="${ctx}/install_flash_player_ax.exe">&raquo;点击下载IE浏览器内核版本</a></h3><h3><a href="${ctx}/install_flash_player.exe">&raquo;点击下载其他浏览器版本</a></h3>'
				});
			}else{
				url=url.replace('&','#o#');
				document.getElementById('ajax_as').AS_TO_AJAX(target,url);
			}
		};
	}
	return returnval;
}
//AS 回调方法
function GET_AS_MESSAGE(el,data){
	 var xml,opt;
		if($.browser.msie & parseInt($.browser.version) < 9){
			xml= new ActiveXObject("Microsoft.XMLDOM");
			xml.async = false;
			xml.loadXML(data);
			xml = $(xml).children('channel'); //这里的nodes为最顶级的节点
		} else {
			xml= (new DOMParser()).parseFromString(data, 'text/xml');
		}
	if(el.indexOf('col-dbsx-1')>-1||el.indexOf('col-dbsx-2')>-1){
		opt={tplBox:'<div class="num-vpr"><span class="num-vm">{count}</span></div><ul>{item}</ul>',tplhtml:{count:'{number}',item:'<li class="cbo"><a href="{link}" class="doc-level-{level}">{title}</a><span class="timer">{pubDate}</span></li>',script:'function {funcationname}{{funcationcode}}'}};
	}else if(el.indexOf('col-dbsx-1')>-3){
		opt={tplBox:'<div class="num-vpr"><span class="num-vm">{count}</span></div><ul>{item}</ul>{more}',tplhtml:{more:'<div class="moreLink"><a href="{link}" class="doc-level-{level}">{title}</a></div>',count:'{number}',item:'<li class="cbo"><a href="{link}">{title}</a><span class="timer">{pubDate}</span></li>',script:'function {funcationname}{{funcationcode}}'}};
	}else{
		opt={'id':Math.random()};
	}
	var content=jQuery(document).parseXML(xml,opt);
	content=(content!='<ul></ul>')?content:'<p style="line-height:150px;text-align:center;">栏目暂时没有信息！</p>';
	$('#'+el).html(content);
}