/**
name:图片轮播
mial:xuxh@trueway.com.cn
UI:www.trueway.com.cn
**/

(function ($) {
 $.fn.picturePl = function(options) {
 var dft = {
 width:350,//宽度
 height:250,//高度
 proportion:false,//是否等比例缩放：true | false
 numberWs:16,//标题字数
 fontSize:12,//标题字体大小:12 | 14 | 16 | 18
 fontBoder:"bold",//标题字是否加粗:bold | normal
 time:3000,//切换时间
 //样式
 style:'<style>'
 +'.picturePl_img{position:absolute;z-index:10;}' 
 +'.picturePl_btn{background:#969696;color:#000000;cursor:pointer;font-size:10px;float:left;margin-right:10px;display:block;border-radius:5px;width:10px;height:10px;line-height:15px;text-align:center;FILTER:alpha(opacity=80);opacity: 0.8;text-indent:100px;}'
 +'.picturePl_btno{background:#ffffff;color:#000000;}'
 +'.picturePl_titcon{text-indent:1em;background:#000000;FILTER:alpha(opacity=60);opacity: 0.6;color:#ffffff;height:40px;line-height:40px;position:absolute;z-index:20;bottom:0px;}'
 +'.picturePl_btncon{overflow:hidden;*zoom:1;height:22px;position:absolute;z-index:30;bottom:0px;right:20px;}'
 +'.picturePl_nrcon{overflow:hidden;*zoom:1;position:relative;margin:0 auto;}'
 +'</style>'
 };
 var ops = $.extend(dft,options);
 $('head').append(ops.style);//添加样式
 var count=$(this).find('img').length;
 var _width=count*ops.width;//计算总宽度
 var _atit='';
 var _btn='';
 var _imgWidth=ops.width
 if(!ops.proportion)
 $(this).find('img').width(ops.width);
 $(this).find('img').height(ops.height).addClass("picturePl_img");//控制图片位置和比例	
 for(i=0;i<count;i++){//处理IMG style
	
	var _title=$(this).find('a').eq(i).attr('title');//获取标题名称
	if(_title.length>ops.numberWs){
	_title=_title.substring(0,ops.numberWs)+'...';//截字
	}
	_atit+='<span style="display:none;">'+_title+'</span>';//拼接标题名称
	_btn+='<span class="picturePl_btn">'+(i+1)+'</span>';//生成切换按钮【1】【2】【3】...
 }
 var _imgObj=$(this).html();//创建图片对象
 var _title='<div class="picturePl_titcon" style="font-size:'+ops.fontSize+'px;font-weight:'+ops.fontBoder+';width:'+ops.width+'px;">'+
 _atit+
 '</div>';//创建标题框
 var _tabBtn='<div class="picturePl_btncon">'+_btn+'</div>';//创建切换按钮
 var ojbcon='<div class="picturePl_nrcon" style="width:'+ops.width+'px;height:'+ops.height+'px;">'+_imgObj+_title+_tabBtn+'</div>';//创建内容框
 $(this).html(ojbcon);//加载对象内容
 //初始化第一项
 $(this).find('a').hide();
 $(this).find('a').eq(0).show();
 $(this).find('div').find('div').eq(1).find('span').eq(0).addClass("picturePl_btno");
 $(this).find('div').find('div').eq(0).find('span').eq(0).show();
 var n=0;
 //切换效果
 $(this).find('div').find('div').eq(1).find('span').each(function() {
   $(this).click(function(){
    $(this).parent().parent().parent().find('a').hide();
    $(this).parent().parent().parent().find('a').eq(parseInt($(this).html())-1).show();
	$(this).parent().prev('div').find('span').hide();
    $(this).parent().prev('div').find('span').eq(parseInt($(this).html())-1).show();
	$(this).siblings("span").removeClass("picturePl_btno");
	$(this).addClass("picturePl_btno");
	n=parseInt($(this).html());
	if(n>=count)n=0;
   });
 });
    //图片轮播
	var ojb=$(this);
	var t=true;
	function timedCount()
	{
		if(n>=count)
		n=0;
		ojb.find('a').hide();
		ojb.find('a').eq(n).show();	
		ojb.find('div').find('div').eq(1).find("span").removeClass("picturePl_btno");
		ojb.find('div').find('div').eq(1).find("span").eq(n).addClass("picturePl_btno");
		ojb.find('div').find('div').eq(0).find('span').hide();
		ojb.find('div').find('div').eq(0).find('span').eq(n).show();
		n++;			
	}
	//自动运行
	setInterval(function(){
		if(t==true) timedCount();
	},ops.time);
	//鼠标停留暂停
	$(this).hover(function(){
		t=false;
	},function(){
		t=true;
	});
}
})(jQuery);