/**
 * 2016.7.5
 * 为了解决awesome在ie8下面显示异常
 * 原理是通过轮询等待css加载完成后触发回调函数
 * ex:↓
 */
//<script type="text/javascript">
//	$(function(){
//		new myFixedClass().init({
//			cssUrl:'//libs.baidu.com/fontawesome/4.0.3/css/font-awesome.min.css',
//			fontAweSomeDom:'[class*=fa-]'
//		});
//	})
//</script>
 
function myFixedClass(){}
myFixedClass.prototype = {
	//配置
	option:{
		cssUrl:'',//css路径
		fontAweSomeDom:'[class*=fa-]',//字体图标选择器
		lnkId:false,//id
		charset:false,//编码
		media:false//多媒体查询
	},
	//初始化函数
	init : function(option){
		var that = this;
		this.option = $.extend(true, this.option, option);
		//创建link节点
		var styleNode = this.createLink(this.option.cssUrl, this.option.lnkId, this.option.charset, this.option.media);
		//轮询加载样式
		this.styleOnload(styleNode, function() {
//		    console.log('loaded');
		    //加载完成回调函数
		    //通过重新添加classname重载字体样式
		    setTimeout(function(){that.fixBefore(that.option.fontAweSomeDom)},1);
		});
	},
	//创建link节点
	createLink : function(cssURL, lnkId, charset, media){
	  var head = document.getElementsByTagName('head')[0],
	    linkTag = null;
	  if (!cssURL) {
	    return false;
	  }
	
	  linkTag = document.createElement('link');
//	  linkTag.setAttribute('id', (lnkId || 'dynamic-style'));
	  linkTag.setAttribute('rel', 'stylesheet');
	  linkTag.setAttribute('charset', (charset || 'utf-8'));
//	  linkTag.setAttribute('media', (media || 'all'));
	  linkTag.setAttribute('type', 'text/css');
	  linkTag.href = cssURL;
	  //加到head区域
	  head.appendChild(linkTag);
	  return linkTag;
	},
	//下面部分截取自seajs
	styleOnload : function(node, callback){
		var that = this;
	  // for IE6-9 and Opera
	  if (node.attachEvent) {
	    node.attachEvent('onload', callback);
	    // NOTICE:
	    // 1. "onload" will be fired in IE6-9 when the file is 404, but in
	    // this situation, Opera does nothing, so fallback to timeout.
	    // 2. "onerror" doesn't fire in any browsers!
	  }
	  // polling for Firefox, Chrome, Safari
	  else {
	    setTimeout(function() {
	      that.poll(node, callback);
	    }, 0); // for cache
	  }
	},
	poll : function(node, callback){
		var that = this;
		if (callback.isCalled) {
		    return;
		  }
		  var isLoaded = false;
		  if (/webkit/i.test(navigator.userAgent)) { //webkit
		    if (node['sheet']) {
		      isLoaded = true;
		    }
		  }
		  // for Firefox
		  else if (node['sheet']) {
		    try {
		      if (node['sheet'].cssRules) {
		        isLoaded = true;
		      }
		    } catch (ex) {
		      // NS_ERROR_DOM_SECURITY_ERR
		      if (ex.code === 1000) {
		        isLoaded = true;
		      }
		    }
		  }
		  if (isLoaded) {
		    // give time to render.
		    setTimeout(function() {
		      callback();
		    }, 1);
		  } else {
		    setTimeout(function() {
		      that.poll(node, callback);
		    }, 1);
		  }
	},
	fixBefore : function(dom){
		var obj = $(dom);
		if(obj.length>0){
			obj.each(function(i,e){
				var tmp = $(e).attr("class");
				$(e).removeClass();
				setTimeout(function(){
					$(e).attr('class',tmp);
				},1);
			})
		}
	}
}
