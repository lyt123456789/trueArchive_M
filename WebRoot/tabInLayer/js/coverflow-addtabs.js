/**
 * Version : 1.0
 *
 * Created by WHorse on 2017-4-18.
 */

$.fn.coverflowtabs = function (options) {
    obj = $(this);
    Coverflowtabs.options = $.extend({
        content: '', //直接指定所有页面TABS内容
        close: true, //是否可以关闭
        monitor: 'body', //监视的区域
        iframeHeight: $(document).height(), //固定TAB中IFRAME高度,根据需要自己修改
        method:  'init',
        callback: function () {}
    }, options || {});
    
    obj.on('click', '.coverflow-max', function () {
        var id = $(this).closest("li").attr("id");
        Coverflowtabs.toMax(id);
    });
    obj.on('click', '.coverflow-min', function () {
        layer.min(taskid);
		$('.layui-layer').css('background-color','#eee');
		$('.coverflow_max').show();
    });   
    obj.on('click', '.coverflow-close', function () {
        var id = $(this).closest("li").attr("id");
        Coverflowtabs.close(id);
    });
    obj.on('dblclick', '.tab-pane', function () {
        var id = $(this).closest("li").attr("id");
        Coverflowtabs.toMax(id);
    });    

    $(window).resize(function () {
        obj.find('iframe').attr('height', Coverflowtabs.options.iframeHeight);
    });

};

window.Coverflowtabs = {
    options:{},
    add: function (opts) {
        var id = 'tab_' + opts.id;
        //如果TAB不存在，创建一个新的TAB
        if (!$("#" + id)[0]) {
            //创建新TAB的内容
            var content = $('<li>', {
                'class': 'tab-pane',
                'id': id,
                'role': 'tabpanel',
                'data-url' : opts.url
            });
            //是否指定TAB内容
            if (opts.content) {
                content.append(opts.content);
            } else {//没有内容，使用IFRAME打开链接
                content.append('<div class="coverflow-content"><img width="800px" height="'+Coverflowtabs.options.iframeHeight+'" class="imgClass" border="0" src="'+opts.imgPath+'"><span class="coverflow-setwin"><a class="coverflow-min" href="javascript:;" style="margin-right:20px;"><cite></cite></a><a class="coverflow-ico coverflow-max" href="javascript:;"></a><a class="coverflow-ico coverflow-close" href="javascript:;"></a></span></div>');
            	//content.append('<div class="coverflow-content"><img width="800px" height="'+Coverflowtabs.options.iframeHeight+'" class="imgClass" border="0" src="http://192.168.5.99:7080/trueWorkFlowV3.2_basic/form/html/workflow/pdf/2017/04/21/1492745083600merge/1.png"><span class="coverflow-setwin"><a class="coverflow-min" href="javascript:;" style="margin-right:20px;"><cite></cite></a><a class="coverflow-ico coverflow-max" href="javascript:;"></a><a class="coverflow-ico coverflow-close" href="javascript:;"></a></span></div>');
            }
            //加入TABS
            obj.children(".coverflow-items").append(content);
        }      
    },
    close: function (id) {
        $("#" + id).remove();
        Coverflowtabs.options.callback();
    },
    toMax: function (id) {
    	$('.iframeContent').show();
    	$('.layui-layer-setwin').hide();
    	var data_url = $("#" + id).attr('data-url');
    	$('.coverflow-iframe').attr('src',data_url);
    }
}
function maxmin(){
	if($('.coverflow-items li').length===1){
        layer.min(taskid);
		$('.layui-layer').css('background-color','#eee');
		$('.coverflow_max').show();        
	}
	$('.coverflow-iframe').attr('src','');
    $('.iframeContent').hide();
	$('.layui-layer-setwin').show();
}