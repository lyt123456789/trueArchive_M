/**
 * Mice UI Framework
 * Trueway UED Team
 * Based on jQuery 1.4 or later
 * (c) 2011-2012 Trueway.com.cn
 **/
 
;define(function(require, exports, module){
	var $=require('jquery');
	var Mice=window['Mice']||{};
	/** Mice Interface **/
	//Mice.base	={};		//Foundation information 
	//Mice.util	={};		//Commonly used tools module
	//Mice.ui		={};		//User interface module
	//Mice.lang	={};		//Commonly used constants
	//Mice.events	={};		//Extended event
	
	/** Mice base module  **/
	Mice.base={
		//Access policy
		baseUrl:'',
		//Debug is on?
		debug:false,
		//Namespace
		prefix:'mice-'
	};
	
	/** Mice lang module **/
	Mice.lang={
		//Commonly used the keyboard value
		keycode:{
			'ENTER':13,
			'ESC':27,
			'END':35,
			'HOME':36,
			'SHIFT':16,
			'TAB':9,
			'LEFT':37,
			'RIGHT':39,
			'UP':38,
			'DOWN':40,
			'DELETE':46,
			'BACKSPACE':8
		},
		//Status code
		statuscode:{
			'ok':200,
			'error':500,
			'timeout':301,
			'nofound':404
		}
	};
	
	/** Mice util module **/
	Mice.util={
		debug:function(str){
			if(this.base.debug) this.util.firebug(str);
		},
		firebug:function(str){
			alert(str);
		},
		isElem:function(obj){
			return obj&&obj.nodeType===1;
		},
		isArray:function(obj){
			return Object.prototype.toString.call(obj)==='[object Array]';
		},
		evalJson:function(json){
			try{
				return eval('('+json+')');
			}catch(e){
				return {};
			}
		},
		getCharset:function(){
			return document.characterSet || document.charset;
		},
		isIE:!-[1,],
		isIE6:this.isIE&&!window.XMLHttpRequest,
		getCss:function(name, fn, doc){
			var doc=doc||document;
			var link=document.createElement('link');
			link.charset=this.getCharset();
			link.rel='stylesheet';
			link.type='text/css';
			link.href=name;
			doc.getElementsByTagName('head')[0].appendChild(link);
			var styles=doc.styleSheets,
			load=function(){
				for(var i=0,l=styles.length;i<l;i++){
					if(link===(style[i].ownerNode || styles[i].owningElement))
						return fn();
				}
				setTimeout(arguments.callee,5);
			}
		},
		clone:function(obj,deep){
			var $mice=this;
			if(!!deep){
				function cloneprototype(){};
				cloneprototype.prototype=obj;
				var nobj=new cloneprototype();
				for(var i in nobj){
					if(typeof nobj[i]==='object') nobj[i]=$mice.clone(nobj[i],true);
				}
			}else{
				var nobj={};
				for(var i in obj){
					nobj[i]=obj[i];
				}
				return nobj;
			}
		},
		A:function(o){
			var rt=[];
			for(var i=0,l=o.length;i<l;i++){
				rt.push(o[i]);
			}
			return rt;
		},
		extend:function(obj,src){
			if(!src) return obj;
			for(var i in obj){
				obj[i]=src[i];
			}
			return obj;
		},
		//动态加载外部css文件
		getCss:function(name,fn,doc){
			doc=doc || document;
			var link = document.createElement('link');
			link.charset = Mice.charset;
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = name;
			doc.getElementsByTagName('head')[0].appendChild(link);
			
			var styles = doc.styleSheets,
				load = function(){
					for(var i = 0;i<styles.length;i++){
						if(link===(styles[i].ownerNode || styles[i].owningElement))
							return fn();
					};
					setTimeout(arguments.callee,5);
				};
		},
		//config的setter方法
		setConfig:function(options){
			this.config=$.extend(this.config,options);
		}
	};
	
	/** Mice events module **/
	//劫持a标签
	Mice.events={
		agentEvents:function(obj){
			this.util.extend(obj,{
				addAgentEvent:function(type, func){
					if(!this._agentEventListeners) this._agentEventListeners={};
					var funcs=this._agentEventListeners;
					funcs[type]	? funcs[type].push(func) : funcs[type] = [func];
				},
				delAgentEvent:function(type, func){
					var funcs = this._agentEventListeners;
					if(funcs){
						for(var i=funcs.length-1;i>=0;i--){
							if(funcs[i]==func){
								funcs[i]=null;
								break;
							}
						}
					}
				},
				fireAgentEvent:function(type){
					if(!this._agentEventListeners[type]) return;
					var funcs=this._agentEventListeners[type],s=this,args=this.util.A(arguments);
					args.shift();
					for(var i = funcs.length-1; i>=0;i--){
						if(funcs[i])
							funcs[i].apply(s,args);
					}
				}
			});
		}
	};

	/**
	 * 构建主题框架
	 **/
	Mice.ui={
		//是否有侧栏导航
		elements:[],
		hasSbar:true,
		//{'id',fn}
		addEventLayout:function(obj){
			if(!!obj.id) this.elements.push(obj);
		},
		//{id:'',fn:''}
		delEventLayout:function(obj){
			for(var i=0,l=this.elements.length;i<l;i++){
				if(this.elements[i].id==obj.id&&this.elements[i].fn==obj.fn) this.elements.splice(i,1);
			}
		},
		initLayout:function(){
			var scroll=$('body').attr('mice-scroll');
			(scroll==='yes')?$('body').css({'overflow':'auto'}):$('body').css({'overflow':'hidden'});
			
			//设置位置大小
			var iContentW=$(window).width()-(this.hasSbar?$('#sidebar').width()+10:35)-5;
			var iContentH=$(window).height()-$('#header').height()-35;
			var iContentL=this.hasSbar?$('#sidebar').width()+10:35;
			//console.log($(window).height()+', '+$('#header').height()+', '+iContentH);
			
			$('#container').animate({'left':iContentL},200);
			$('#container').width(iContentW).height(iContentH).find('.tabsPageScroll').width(iContentW-100);
			$('#container .tabsPageContainer').height(iContentH-5).find('.tabsPageContent').height(iContentH-5-$('#container .tabsPageHeader').height()).find('[filltarget]').each(function(i){
				$(this).height($($(this).attr('filltarget')).height());
			});
			$('#sidebar,#sidebar_s>.collapse,#splitbar,#splitbarProxy').height(iContentH-5);		
			$('#sidebar .accordion').height(iContentH-5-$('.toggleCollapse').height());
			for(var i=0,l=this.elements.length;i<l;i++){
				//$('.conslog').append('<span>'+this.elements[i].id+':'+this.elements[i].fn+'</span>');
				$(this.elements[i].id)[this.elements[i].fn]();
			}
		},
		initEvent:function(){
			var $this=this;
			//监听自定义的hover
			$('*[mice-e="hover"]').hover(function(){
				$(this).addClass('hover');
			},function(){
				$(this).removeClass('hover');
			});
			//监听sidebar
			$('#sidebar .toggleCollapseButton').bind('click',function(){
				$('#sidebar').animate({'left':$('#sidebar').width()*-1},200,function(){
					$('#sidebar_s').animate({'left':5},100);
				});	
				$this.hasSbar=false;
				$this.initLayout();
			});
			$('#sidebar_s').bind('click',function(){
				$(this).animate({'left':$(this).width()*-1},100,function(){
					$('#sidebar').animate({'left':5},200);
				});	
				$this.hasSbar=true;
				$this.initLayout();
			});
		}
	};
	
	//初始化
	Mice.init=function(settings){
		//滚动条
		Mice.ui.initLayout();
		Mice.ui.initEvent();
		//window改变时
		window.onresize=function(){
			Mice.ui.initLayout();
		}
	}

	$.fn.isTag=function(tn) {
		if(!tn) return false;
		return $(this)[0].tagName.toLowerCase() == tn?true:false;
	}
	//window['Mice']=Mice;
	module.exports=Mice;
});