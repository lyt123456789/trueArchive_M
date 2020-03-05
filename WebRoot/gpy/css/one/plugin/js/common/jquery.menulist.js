// JavaScript Document
/**
 *	jQuery插件
 *	Author:	purecolor@foxmail.com
 *	Date  :	2011-01-06
 *	Params: el:Element ,option:	{outAnimateStyle:[Array,……],overAnimateStyle:[Array,……],outUnanimateStyle:[Array,……],overUnanimateStyle:[Array,……]}
 *			Array:[属性名,值]
 *  Note  : AnimateStyle遇到CSS属性中有“-”时需转化模式，如：background-color 转变为 backgroundColor 
 *		    UnanimateStyle则使用标准模式，如：background-color 
 */
(function($){
	$.fn.extend({
		animateListMenu:function(el,option){
			//存储展开节点
			var _temp='';
			//自定义接口
			var _default={
				outAnimateStyle:[],
				overAnimateStyle:[],
				outUnanimateStyle:[],
				overUnanimateStyle:[]
			};
			var option=$.extend(_default,option);
			
			//组合属性
			var _outAnimateStyle={};
			var _overAnimateStyle={};
			var _outUnanimateStyle={};
			var _overUnanimateStyle={};
			
			for(var i=0,len=option.outAnimateStyle.length;i<len;i++){
				_outAnimateStyle[option.outAnimateStyle[i][0]]=option.outAnimateStyle[i][1];
			}
			for(var i=0,len=option.overAnimateStyle.length;i<len;i++){
				_overAnimateStyle[option.overAnimateStyle[i][0]]=option.overAnimateStyle[i][1];
			}
			for(var i=0,len=option.outUnanimateStyle.length;i<len;i++){
				_outUnanimateStyle[option.outUnanimateStyle[i][0]]=option.outUnanimateStyle[i][1];
			}
			for(var i=0,len=option.overUnanimateStyle.length;i<len;i++){
				_overUnanimateStyle[option.overUnanimateStyle[i][0]]=option.overUnanimateStyle[i][1];
			}
			//alert(_overAnimateStyle.toSource());
			//注册方法
			return this.each(function(){
				var o=option;
				var obj=$(this);
				var items=$(el,obj);
				items.click(function(){
					_temp=(_temp=='')?$(this):_temp;
					if(_temp!=$(this)){
						_temp.animate(_outAnimateStyle,300).css(_outUnanimateStyle);
						_temp.parent().animate({height:_temp.height()},300);
						_temp=$(this);	
					}
					if($(this).parent().height()>$(this).height()){
						$(this).animate(_outAnimateStyle,300).css(_outUnanimateStyle);
						$(this).parent().animate({height:$(this).height()},300);
					}else{
						$(this).animate(_overAnimateStyle,300).css(_overUnanimateStyle);
						$(this).parent().animate({height:$(this).next().innerHeight()+$(this).height()},300);
					}
				});
			});
		}
	});
})(jQuery);