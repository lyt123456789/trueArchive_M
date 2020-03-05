<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <style>
        body{-moz-user-select: none;font-size:14px;}
        body {
            background: #E8F0F1;
            margin: 0;
        }

        #fw_left label span {
            color: #FF0000;
            visibility: hidden;
            display:none;
        }

        .fenlei input {
            margin-right: 6px;
        }

        .fenlei_children table {
            margin-bottom: 6px;
            width: 410px;
            margin-left: 2px
        }

        .fenlei_children td {
            vertical-align: top;
        }

        #fw_right {
            height: 280px;
            width: 600px;
            overflow: auto;
            overflow-x: hidden;
            border: 1px #999999 solid;
            background: #FFF
        }


        #fw_middle{
            height: 20px;
            width: 220px;
            overflow: auto;
            overflow-x: hidden;
            border: 1px #999999 solid;
            background: #FFF
        }

        #fw_left{
            height: 400px;
            width: 600px;
            overflow: auto;
            overflow-x: hidden;
            border: 1px #999999 solid;
            background: #FFF
        }
        #fw_last{
            height: 100px;
            width: 220px;
            overflow: auto;
            overflow-x: hidden;
            border: 1px #999999 solid;
            background: #FFF
        }
        #fw_right {
            width: 220px;
            padding-left: 10px
        }

		#fw_right2 {
            padding-left: 10px;
             width: 40pxx;
        }
        .button input {
            height: 24px;
            background: #EAEAEA;
            cursor: hand;
        }
        .nbutton{border:1px solid #ccc; background-color:#EFEFEF; width:100px; text-align:center;}
        .title{ padding:5px 10px 5px 5px;background-color:#EFEFEF; border-bottom:1px solid #CCC; color:#0C5DAC;}
        .content{ border-bottom:1px solid #CCC; padding:5px; overflow:hidden; zoom:1;}
        .secondlayout{
            width:49%; float:left; display:inline-block; line-height:24px; margin-right:1%;
        }
        .content label{/*overflow: hidden;text-overflow: ellipsis;white-space:nowrap;*/width:100%; display:block; line-height:24px; margin-right:1%;}
        .deeplayout{
            padding:5px;
            background-color:gray;
            color:#ffffff;
        }
        .none{
            display:none;
        }
        #fw_right input{vertical-align:middle; margin-right:2px;}
        #fw_right label{display:block; line-height:24px;}
        #fw_left input{vertical-align:middle; margin-right:5px;}
        .nums{width:30px;}
        .serial{width:50px;}
        .nums{width:30px;}
        .fcc{color:#CCC;}
        .nbutton{cursor:pointer;}
        .tempbox{position:absolute; left:0; top:0; z-index:3; width:auto; display:block; background-color:#FFF; padding:5px;}
        .tempboxbg{position:absolute; left:0; top:0; z-index:2; width:100%; display:block; background-color:#FFF;opacity:0.7;_filter:alpha(opacity=70)}
        button{width:50px;line-height:18px;background:#5876AD;color:#FFF;border:1px solid #15274B;margin-left:10px;}
    </style>
</head>
<body>
<!--new html start-->
<table width="100% cellpadding="0" cellspacing="0" border="0">
<tr>
    <td width="350px;">
        <div id="fw_left"></div>
    </td>
    <td valign="top" align="center" width="100px;"  class="button">
        <p style="margin-top:120px;" >
            <input class="btn" type="button" name="button" class="nbutton" value="添加&raquo;" onclick='_add();' />
        </p>
        <p>
            <input class="btn" type="button" name="button" class="nbutton" value="&laquo;删除" onclick='_del();' />
        </p>
        <p>
            <input class="btn" type="button" name="button" class="nbutton" value="全部删除"	onclick='_allDel();' />
        </p>
    </td>
    <td>
        <div id="fw_middle">
            带红章可打印份数:<input type="text" name="fs" id="fs" value="6" onblur="setNum();" size="5">
        </div>
        <div id="fw_right"></div>
        手动添加部门
        <div id="fw_last">
            <textarea style="height: 90px;width: 215px;" id="sdtjbm">${depNames}</textarea>
        </div>
    </td>
    <c:if test="${isSend == 1}">
    <td valign="top" align="left">
	    <input class="btn" type="button" name="button" class="nbutton" value="发送" onclick='send();' />
    </td>
    </c:if>
</tr>
</table>
<c:if test="${isHtml!=null && isHtml=='1'}">
	<div align="center" style="margin-top: 25px" class="button">
		<input class="btn" name="确定" type="button" value=" 确 定 " class="nbutton" onclick="getHtmlRetValue()" />
	</div>
</c:if>
</body>
<script src="${curl}/widgets/plugin/js/base/jquery.js"></script>
<script language="javascript">
complete=true;
/**新增代码 purecolor@foxmail.com 2011.11.02**/
(function($){
    function getOffsetTop(){
        var endid=this,st=0;
        while("fw_left"!=endid.attr('id')){
            endid.each(function(){
                st+=this.offsetTop;
            });
            endid=endid.parent();
        };
        return st;
    }
    var $next=$('<button style="display:none;">下一个</button>'),
            $prev=$('<button style="display:none;">上一个</button>');
    $.fn.searchpos=function(target){
        return $(this).each(function(){
            var cachePos=[],index=-1,$this=$(this),$t=$(target),oid=null;

            $('#keypos',$this).append($prev).append($next);
            $prev.bind('click',function(){
                index=range(--index,cachePos.length-1,0);
                var id=document.getElementById(cachePos[index]);
                var y=getOffsetTop.call($(id));
                y=(id.offsetTop>5)?id.offsetTop:y;
                callback(y,$(id));
            });
            $next.bind('click',function(){
                index=range(++index,cachePos.length-1,0);
                var id=document.getElementById(cachePos[index]);
                var y=getOffsetTop.call($(id));
                y=(id.offsetTop>5)?id.offsetTop:y;
                callback(y,$(id));
            });
            function callback(y,o){
                if(oid){
                    oid.parent().css({'color':'#333','font-weight':'normal'});
                }
                if(cachePos.length>1) oid=o;
                $t.each(function(){
                    o.parent().css({'color':'#c00000','font-weight':'bold'});
                    this.scrollTop=y-$(this).height()/2;
                });
            }
            $('#keywords').bind('focus',function(){
                var val=$(this).val();
                if(val=='输入单位名称查询')
                    $(this).css({'color':'#333333'}).val('');
                else
                    $(this).css({'color':'#333333'});
            }).bind('blur',function(){
                        var val=$(this).val();
                        if(val==''){
                            $(this).css({'color':'#cccccc'}).val('输入单位名称查询');
                            $('#keybutton').trigger('click');
                        }
                    });
            //绑定操作开始
            $('#keybutton',$this).bind('click',function(){
                //复位操作
                $next.hide();
                $prev.hide();
                $(document.getElementById(cachePos[index])).parent().css({'color':'#333','font-weight':'normal'});
                cachePos=[],index=-1,oid=null;
                var keyws=$('#keywords',$this).val();
                if(keyws!='输入单位名称查询'&&keyws!=''){
                    for(var j in _temps){
                        if(_temps[j]['name'].indexOf(keyws)>-1)
                            cachePos.push(_temps[j].id);
                    }
                    if(cachePos.length>0&&cachePos.length<2){
                        $next.show().trigger('click');
                    }else if(cachePos.length>1){
                        $prev.show();$next.show().trigger('click');
                    }
                }
            });
        });

        function range( num, max,min )
        {
            return Math.min( max, Math.max( num,min ) );
        }
    }
    //绑定enter
    if (document.addEventListener){
        //如果是Firefox
        document.addEventListener("keypress", enterHandler, true);
    } else{
        document.attachEvent("onkeypress", enterHandler);
    }
    function enterHandler(e){
        var e=e||event;
        if(e.keyCode==13) {
            $('#keybutton').trigger('click').focus();
        }
    };
})(jQuery);
/**新增代码 purecolor@foxmail.com 2011.08.08**/
var resultbox={}; //待发送的对象
var checkboxs={}; //左侧选中的对象
var delboxs={}; //右侧待删除的对象
var _temps={}; //缓存对象
var defalut_nums=6;
var unNode = function(id,html){
	this.id = id;
	this.html = html;
};
var node=function(guid,id,name,checked,parentid,nums,gzname,csnum,greek,seconddept){
    this.guid = guid;
	this.id=id;					//主键
    this.name=name;				//节点名
    this.checked=!!checked;		//是否选中
    this.parentid=parentid;		//父级id
    this.nums=nums;				//发件数
    this.serial='';				//扩展的编号
    this.gzname=gzname;
    this.csnum =csnum;
    this.greek = !!greek;
    this.seconddept = !!seconddept; // 二级部门
};
var json=${json};
var js=0,jl=json.length;
for(js;js<jl;js++){
    var pid=json[js].id;
    var pname=json[js].name;
    var csnum = json[js].num;
    var children=json[js].children;
    var si=0,sl=children.length;
    var checked = json[js].checked;
    var greek = json[js].greek;
    var guid = json[js].guid;
    _temps[guid]=new node(guid,pid,pname,checked,null,null,'',csnum,greek,false);
    for(si;si<sl;si++){
        var sid=children[si].id;
        var sname=children[si].name;
        var schecked = children[si].checked;
        var ssnum = children[si].num;
        var sgreek = children[si].greek;
        var cchildren=children[si].children;
        var sguid = children[si].guid;
        _temps[sguid]=new node(sguid,sid,sname,schecked,pid,null,children[si].gzname, ssnum,sgreek,true);
        
        if(cchildren != null && cchildren != ""){
            var csi = 0, csl= cchildren.length;

            for(csi;csi<csl;csi++){
                var csid=cchildren[csi].id;
                var csname=cchildren[csi].name;
                var cschecked = cchildren[csi].checked;
                var cssnum = cchildren[csi].num;
                var csgreek = cchildren[csi].greek;
                var csguid = cchildren[csi].guid;
                //	var threedept = cchildren[csi].threedept;
                _temps[csguid]=new node(csguid,csid,csname,cschecked,sid,null,cchildren[csi].gzname, cssnum,csgreek,false);
            }
        }

    }
}

var depids = "";
var depnames = "";
//事件优化
$('DIV[sdata]').live('click',function(e){
    //全选事件
    //var t=e.srcElement||e.target;
    var pi=$(this).find('input').attr('checked');
    var id=$(this).attr('sdata');
    if(pi){
        checkboxs[id]=_temps[id];
    }else{
        delete checkboxs[id];
    }
    //设置父checkbox状态 $(this).find('input').attr('disabled',pi);
    //选中全部子checkbox
    $('.c_'+id).find('label').each(function(){
        var id=$(this).attr('sdata');
        var guid = $(this).attr('guid');
        if(!_temps[guid].checked ){
        	 if(!$(this).find('input').attr('disabled')){
            $(this).find('input').attr('checked',pi);
            	 if(!pi){
                     delete checkboxs[id];
                 }else{
                     checkboxs[id]=_temps[guid];
                 }
            }
        	 searchGuid(_temps[guid].id);
        	 for(var n in guids){
        		 if(guid != n){
                     $('label[guid='+n+']').find('input').attr('disabled',true);
        		 }
        	 }
        }
    });
});
/* .live('mousedown',function(ev){
            //拖拽全选事件
            var t=ev.srcElement||ev.target;
            var disalbe=$(this).find('input').attr('checked');
            if(t.nodeName=='LABEL'&&!disalbe){
                var id=$(this).attr('sdata');
                var guid = $(this).attr('guid');
                var name=_temps[guid].name;
                var html='<label for="_r_'+id+'" sdata="'+id+'" class="tempbox" guid="'+guid+'"><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'" />'+name+'</label>';
                var _el=$(html).appendTo('body');

                ev = ev || window.event;
                var mousePos = mousePosition(ev);
                _el.css({left:mousePos.x-30,top:mousePos.y-10});
                $(document).bind('mousemove',function(ev){
                    ev = ev || window.event;
                    var mousePos = mousePosition(ev);
                    _el.css({left:mousePos.x-30,top:mousePos.y-10});
                }).bind('mouseup',function(ev){
                            ev = ev || window.event;
                            var mousePos = mousePosition(ev);
                            var _x=mousePos.x ,_y=mousePos.y;
                            //minL:540 minT:40 maxL:
                            var minL=$('#fw_right').position().left ,minT=$('#fw_right').position().top;
                            var maxL=minL+$('#fw_right').width(),maxT=minT+$('#fw_right').height();

                            if(_x>minL&&_x<maxL&&_y>minT&&_y<maxT){
                                var id=_el.attr('sdata');
                                $('.c_'+id).find('label').each(function(){
                                    var id=$(this).attr('sdata');
                                    var guid = $(this).attr('guid');
                                    var disalbe=$(this).find('input').attr('disabled');
                                    if(!disalbe){
                                    	 $(this).find('input').attr('checked',true);
                                         _addOne(_temps[guid]);
                                    }
                                   
                                    // 根据id search 数据
                                    /* searchGuid(id);
                                    for(var ss in guids){
                                    	if(!_temps[ss].checked){
                                            $(this).find('input').attr('checked',true);
                                            _addOne(_temps[ss]);
                                        }
                                    } */
                                    
                               /* });
                                //
                            }
                            $(document).unbind('mousemove').unbind('mouseup');
                            _el.remove();
                            delete _el;

                            //开启可选中
                            document.onselectstart = null;
                            if(!$.browser.msie) window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
                        });
                //禁止可选中
                document.onselectstart = new Function("return false");
                if(!$.browser.msie) window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
            }
        }) */
        
//单元素
$('label[pdata]',$('#fw_left')).live('click',function(){
    //单元素选择事件
    var pi=$(this).find('input').attr('checked');
    var id=$(this).attr('guid');
    searchGuid(_temps[id].id);
    for (var n in guids){
    	if(n != id){
       	 $('label[guid='+n+']').find('input').attr('disabled',pi);
    	}
    }
    if(pi){
        /******三级处理*********************/
        if($(this).parent().hasClass("deeplayout")){
            //点中的是三级
            if($("label[pdata]",$(this).parent()).find("input").not(":checked").length == 0){
                var pp = $(this).parent().prev();
                // 单独选中子部门的时候 不要选中父部门
               // pp.find("input").attr("checked",pi);
               /*  searchGuid(_temps[id].id);
                for (var n in guids){
                	if(n != id){
                   	 $('label[guid='+n+']').find('input').attr('checked',true)
                	}
                }
                checkboxs[pp.attr("sdata")] = _temps[pp.attr("sdata")]; */
                //是否勾选一级
                var PCC = pp.parents(".content");
                if($("label[pdata]",PCC).find("input").not(":checked").length == 0){
                    var pre = PCC.prev();
                    pre.find("input").attr("checked",pi);
                    checkboxs[pre.attr("sdata")] = _temps[pre.attr("guid")];
                }
            }
        }else{
            //点中的是二级
            //是否勾选一级
            var PC = $(this).parents(".content");
            if($("label[pdata]",PC).find("input").not(":checked").length == 0){
                var p = PC.prev();
                p.find("input").attr("checked",pi);
                checkboxs[p.attr("sdata")] = _temps[p.attr("guid")];
                // 查看其他id 一样的是否选中 
                var  tempId = _temps[p.attr("guid")].id;
                $('input[name='+tempId+']').attr("checked",pi);
            }
            //处理三级
            if($(this).next().children().length != 0){
                $(this).next().children().each(function(){
                    var cid=$(this).attr('guid');
                    if(!_temps[cid].checked){
                        $(this).find('input').attr('checked',pi);
                        var  tempId = _temps[cid].id;
                        $('input[name='+tempId+']').attr("checked",pi);
                        if(!pi){
                            delete checkboxs[tempId];
                        }else{
                            checkboxs[tempId]=_temps[cid];
                        }
                    }
                });
            } 
        }
        
        checkboxs[_temps[id].id]=_temps[id];
    }else{
        /******三级处理*********************/
        if($(this).parent().hasClass("deeplayout")){
        	//暂时 注释  三级取消 ,二级不取消
           /*  var pp = $(this).parent().prev();
                pp.find("input").attr("checked",pi);
                delete checkboxs[pp.attr("sdata")];
           		var superid =  pp.attr('pdata');
                $('[sdata='+superid+']').find("input").attr("checked",pi);
                delete checkboxs[superid]; */
        }else{
            //处理二级
            var superid =  $(this).attr('pdata');
            $('[sdata='+superid+']').find("input").attr("checked",pi);
            delete checkboxs[superid];
            if($(this).next().children().length != 0){
            	 delete checkboxs[_temps[id].id];
                $(this).next().children().each(function(){
                	
                    var cid=$(this).attr('sdata');
                    delete checkboxs[cid];
                    $(this).find("input").attr("checked",pi);
                    var  tempId = _temps[$(this).attr('guid')].id;
                    $('input[name='+tempId+']').attr("checked",pi);
                });
            } 
        }
//        var pid = $(this).attr('pdata');
//        $('[sdata='+pid+']').find("input").attr("checked",pi);
//        delete checkboxs[pid];
        delete checkboxs[_temps[id].id];
    }
});
/* .live('mousedown',function(ev){
            //单元素拖拽事件
            var t=ev.srcElement||ev.target;
            var disalbe=$(this).find('input').attr('checked');
            var disabled = $(this).find('input').attr('disabled');
            if(t.nodeName=='LABEL'&&!disalbe&&!disabled){
                var id=$(this).attr('sdata');
                var guid = $(this).attr('guid');
                var name=_temps[guid].name;
                var parentid=_temps[guid].parentid;
                var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'" class="tempbox" guid="'+guid+'" ><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'" />'+name+'</label>';
                var _el=$(html).appendTo('body');
                ev = ev || window.event;
                var mousePos = mousePosition(ev);
                _el.css({left:mousePos.x-30,top:mousePos.y-10});

                $(document).bind('mousemove',function(ev){
                    ev = ev || window.event;
                    var mousePos = mousePosition(ev);
                    _el.css({left:mousePos.x-30,top:mousePos.y-10});

                }).bind('mouseup',function(ev){
                            ev = ev || window.event;
                            var mousePos = mousePosition(ev);
                            var _x=mousePos.x ,_y=mousePos.y;
                            //minL:540 minT:40 maxL:
                            var minL=$('#fw_right').position().left ,minT=$('#fw_right').position().top;
                            var maxL=minL+$('#fw_right').width(),maxT=minT+$('#fw_right').height();

                            if(_x>minL&&_x<maxL&&_y>minT&&_y<maxT){
                                _addOne(_temps[_el.attr('guid')]);
                            }
                            $(document).unbind('mousemove').unbind('mouseup');
                            _el.remove();
                            delete _el;
                            //开启可选中
                            document.onselectstart = null;
                            if(!$.browser.msie) window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
                        });
                //禁止可选中
                document.onselectstart = new Function("return false");
                if(!$.browser.msie) window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
            }
        }) */

//右侧单元素
//修改发文数
$('label[pdata]',$('#fw_right')).find('.nums').live('blur',function(e,data){
    resultbox[$(this).attr('nid')].nums=chk_nums.call($(this),$(this).val(),'nums');
})
//点击待删
$('label[pdata]',$('#fw_right')).live('click',function(){
    var pi=$(this).find('input').attr('checked');
    if(pi){
        delboxs[$(this).attr('sdata')]=_temps[$(this).attr('guid')];
    }else{
        delete delboxs[$(this).attr('sdata')];
    }
});
/* .live('mousedown',function(ev){
            //单元素反向拖拽事件
            var t=ev.srcElement||ev.target;
            if(t.nodeName=='LABEL'){
                var id=$(this).attr('sdata');
                var guid= $(this).attr('guid');
                var name=_temps[guid].name;
                var parentid=_temps[guid].parentid;
                var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'" class="tempbox" guid="'+guid+'"><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'" />'+name+'</label>';//var bghtml='<div class="tempboxbg"></div>';
                //var _elbg=$(bghtml).appendTo('body');
                var _el=$(html).appendTo('body');

                ev = ev || window.event;
                var mousePos = mousePosition(ev);
                _el.css({left:mousePos.x-30,top:mousePos.y-10});
                //_elbg.css({height:$(document).height()});

                $(document).bind('mousemove',function(ev){
                    ev = ev || window.event;
                    var mousePos = mousePosition(ev);
                    _el.css({left:mousePos.x-30,top:mousePos.y-10});

                }).bind('mouseup',function(ev){
                            ev = ev || window.event;
                            var mousePos = mousePosition(ev);
                            var _x=mousePos.x ,_y=mousePos.y;
                            var $this=$('#fw_left');
                            var $that=$('#fw_right');
                            var id=_el.attr('sdata');
                            //minL:540 minT:40 maxL:
                            var minL=$this.position().left ,minT=$this.position().top;
                            var maxL=minL+$this.width(),maxT=minT+$this.height();
                            if(_x>minL&&_x<maxL&&_y>minT&&_y<maxT){
                                //clearUseNum.call($('input[sid='+id+']')); //释放不用的文号 2011-11-04
                                $('label[for=_r_'+id+']',$that).remove();
                                _delOne(resultbox[id]);
                            }
                            $(document).unbind('mousemove').unbind('mouseup');
                            _el.remove();
                            delete _el;
                            //开启可选中
                            document.onselectstart = null;
                            if(!$.browser.msie) window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
                        });
                //禁止可选中
                document.onselectstart = new Function("return false");
                if(!$.browser.msie) window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
            }
        }) */

//构造场景
function creatHtml(o){
	var unInsertDate = {};
    if(!o) return;
    for(n in o){
        var id=o[n].id;
        var name=o[n].name;
        var checked=o[n].checked;
        var greek = o[n].greek;
        var parentid=o[n].parentid;
        var sdhNum  = o[n].csnum;
        var hasChild = o[n].hasChild;
        if(parentid==null||parentid==id){
            var html= '';
            if(checked){
                checkboxs[id]=_temps[n];
                html = '<div class="t_'+id+' title" sdata="'+id+'" id="t_'+id+'" guid="'+n+'"><label for="'+id+'"><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" checked="checked"/>'+name+'</label></div>';
            }else{
                /* if(greek){
                 html = '<div class="t_'+id+' title" sdata="'+id+'" id="t_'+id+'"><label for="'+id+'"><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" disabled="disabled"/>'+name+'</label></div>';
                 }else{ */
                html = '<div class="t_'+id+' title" sdata="'+id+'" id="t_'+id+'"  guid="'+n+'"><label for="'+id+'"><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" />'+name+'</label></div>';
                //}
            }
            var el=$(html).appendTo($('#fw_left'));
            $('<div class="c_'+id+' content"></div>').appendTo($('#fw_left'));
        }else{
            var html ='';
            //处理二级
            //用父对象的parentid为Null或者等于id来判断这个对象属于二级对象
            if(_temps[n].seconddept){
                if(checked){
                    checkboxs[id]=_temps[n];
                    html='<div  class="second_'+id+' secondlayout"><label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" checked />'+name+'</label><div class="deep_'+id+' deeplayout none"></div></div>';
                }else{
                    if(greek){
                        //html='<div  class="second_'+id+' secondlayout"><label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'" class="fcc"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" disabled="disabled" />'+name+'</label><div class="deep_'+id+' deeplayout none"></div></div>';
                    	html='<div class="second_'+id+' secondlayout"><label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" />'+name+'</label><div class="deep_'+id+' deeplayout none"></div></div>';
                    }else{
                        html='<div class="second_'+id+' secondlayout"><label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" />'+name+'</label><div class="deep_'+id+' deeplayout none"></div></div>';
                    }
                }
                $(html).appendTo($('.c_'+parentid));
            }else{
                var deep = '';
                //处理3级
                if(checked){
                    checkboxs[id]=_temps[n];
                    deep='<label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" checked />'+name+'</label>';
                }else{
                    if(greek){
                        deep='<label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'" class="fcc"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" disabled="disabled" />'+name+'</label>';
                    }else{
                        deep='<label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'"  guid="'+n+'" ><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" />'+name+'</label>';
                    }
                }
                if($('.deep_'+parentid).size()>0){
                	$(deep).appendTo($('.deep_'+parentid));
                    $('.deep_'+parentid).removeClass("none");
                }else{
                	unInsertDate[n] = new unNode(parentid,deep);
                }
                
            }

            //var el=$(html).appendTo($('.c_'+parentid));
            /**增加事件**/
            /**拖拽实现**/
            /**end**/
        }
        _add(sdhNum);
    }
    // 处理 没有插入的子部门
    for(var sss in unInsertDate){
    
    	 $(unInsertDate[sss].html).appendTo($('.deep_'+unInsertDate[sss].id));
        $('.deep_'+unInsertDate[sss].id).removeClass("none");
    }
}
var  guids = {};
// 根据id 查找 _temp[n] 获取 n 数组
function searchGuid(id){
	guids = {};
	for(n in _temps){
		if(_temps[n].id == id){
			guids[n] = n;
		}
	}
}
//拖拽添加
function _addOne(obj){
    /* if (isNaN($("#fenshu").val()) || $("#fenshu").val() <-1) {
     alert("请输入正确发送分数！");
     return false;
     } */

    if(!obj) return;

    var o=obj;
    var id=o.id;
    var guid = o.guid;
    var name=o.name;
    var checked=o.checked;
    var parentid=o.parentid;
    var nums=o.nums!=null?o.nums:defalut_nums;
    o.nums=nums;
    var serial=o.serial;
    _temps[guid].checked=true;
    depids += id+",";
    depnames += name+",";
    if(parentid!=null){
        //var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'" guid="'+guid+'" ><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'"/>'+name+'[ <input class="nums" name="nums" type="text" nid="'+id+'" value="'+nums+'" />]</label>';
        var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'" guid="'+guid+'" ><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'"/>'+name+'</label>';
        var el=$(html).appendTo($('#fw_right'));

        //保存待发送的单位
        resultbox[id]=_temps[guid];
        checkboxs[id]=_temps[guid];
        var $this=$('#fw_left');
        $('label[for='+id+']',$this).addClass('fcc').find('input').attr('checked',true).attr('disabled',true);
        searchGuid(_temps[guid].id);
        for (var n in guids){
        	if(guid != n){
        		var html='<label for="_r_'+_temps[n].id+'" pdata="'+_temps[n].parentid+'" sdata="'+_temps[n].id+'" guid="'+_temps[n].guid+'" ><input name="r_'+_temps[n].id+'" type="checkbox" id="r_'+_temps[n].id+'" value="'+_temps[n].id+'"/>'+_temps[n].name+'</label>';

        		//var html='<label for="_r_'+_temps[n].id+'" pdata="'+_temps[n].parentid+'" sdata="'+_temps[n].id+'" guid="'+_temps[n].guid+'" ><input name="r_'+_temps[n].id+'" type="checkbox" id="r_'+_temps[n].id+'" value="'+_temps[n].id+'"/>'+_temps[n].name+'[ <input class="nums" name="nums" type="text" nid="'+_temps[n].id+'" value="'+nums+'" />]</label>';
                var el=$(html).appendTo($('#fw_right'));

                //保存待发送的单位
                resultbox[id]=_temps[n];
                checkboxs[id]=_temps[n];
                var $this=$('#fw_left');
                $('label[for='+_temps[n].id+']',$this).addClass('fcc').find('input').attr('checked',true).attr('disabled',true);
        	}
        	
        }
        // 判断是否还有id 一样的 数据
        //判断是否全选
        searchGuid(_temps[guid].parent);
        for (var n in guids){
        	if(!_temps[n].checked) isCheckAll(parentid);
        }
      
        
        /**增加事件**/
        /**end**/
        //反向拖拽实现
        //反向拖拽实现
        /**end**/
    }
}

//添加单位
function _add(obj){
    /* if (isNaN($("#fenshu").val()) || $("#fenshu").val() <-1) {
     alert("请输入正确发送分数！");
     return;
     } */
    var ckAll={};
    var isr=false;
    if(!obj) {
        obj=checkboxs;
        isr=true;
    }
    $('#fw_right').html('');
    //已发送单位
    var valueOfSend = '${valueOfSend}';
    depids = "";
    depnames = "";
    var n = "";
    for( n in obj){
        var o = obj[n];
        if(o){
        	  var id=o.id;
              var guid = o.guid;
              var name=o.name;
              var checked=o.checked;
              var parentid=o.parentid;
              //alert(parentid);
              var nums=o.nums!=null?o.nums:defalut_nums;
              o.nums=nums;
              var serial=o.serial;
              //保存返回的nums、serial
              if(!isr){
                  _temps[guid].nums=defalut_nums;
                  _temps[guid].serial=serial;
                  checkboxs[id]=_temps[guid];
              }
              _temps[guid].checked=true;
              if(parentid!= null){
                  depids += id+",";
                  depnames += name+",";
              }
              if(parentid!=null){
            	  
            	  // 查找相同的 办件
            	//   searchGuid(id);
            	  guids = {};
            	  guids[guid]= _temps[guid];
            	 
            	   for (var n in guids){
		                   var tempNum = _temps[n].nums!=null?_temps[n].nums:defalut_nums;
		                   	    var html = '';
		                   	    //已发送单位灰化
                       			if(valueOfSend.indexOf(_temps[n].id) < 0 ){
		                    		//html='<label for="_r_'+_temps[n].id+'" pdata="'+_temps[n].parentid+'" sdata="'+_temps[n].id+'" guid="'+_temps[n].guid+'" ><input name="r_'+_temps[n].id+'" type="checkbox" id="'+_temps[n].id+'" value="'+_temps[n].id+'"/>'+_temps[n].name+'[ <input class="nums" name="nums" type="text" nid="'+_temps[n].id+'" value="'+tempNum+'" />]</label>';
			                   		html='<label for="_r_'+_temps[n].id+'" pdata="'+_temps[n].parentid+'" sdata="'+_temps[n].id+'" guid="'+_temps[n].guid+'" ><input name="r_'+_temps[n].id+'" type="checkbox" id="'+_temps[n].id+'" value="'+_temps[n].id+'"/>'+_temps[n].name+'</label>';
		                    		var el=$(html).appendTo($('#fw_right'));
			                   		//保存待发送的单位
			                        resultbox[id]=_temps[n];
		                   		}
		                        //
		                        var $this=$('#fw_left');
		                        $('label[for='+_temps[n].id+'][guid='+n+']',$this).addClass('fcc').find('input').attr('checked',true).attr('disabled',true);
		                       //判断是否全选
		                   		 //if(!_temps[parentid].checked)
		                   			 // 如果是2级部门 则  判断一级部门
		                   			 if(_temps[n].seconddept){
		                   				ckAll[_temps[n].parentid]=_temps[n].parentid;
		                   			 }
		                       
                   }
                 
                  /**增加事件**/
                  /**end**/
                  //反向拖拽实现
                  //反向拖拽实现
                  /**end**/
              }
        }
      
    }
    /* if(depids!=""){depids = depids.substr(0,depids.length-1);}
     if(depnames!=""){depnames = depnames.substr(0,depnames.length-1);} */

    //优化后的全选检查
    for(c in ckAll){
        isCheckAll(c);
    }
    ckAll={};
    delete ckAll;
}


//检查子项是否全选
function isCheckAll(pid){
	
    var allc=true;
    var $this=$('#fw_left');
    
    if($('.c_'+pid,$this).size()>0){
    	$('.c_'+pid,$this).find('label').each(function(){
            var id=$(this).attr('sdata');
            var guid = $(this).attr('guid');
            if(!_temps[guid].checked)
                allc=false;
        });
    }else{
    	$('.deep_'+pid,$this).find('label').each(function(){
            var id=$(this).attr('sdata');
            var guid = $(this).attr('guid');
            if(!_temps[guid].checked)
                allc=false;
        });
    }
    if(allc){
        $('label[for='+pid+']',$this).find('input').attr('checked',true).attr('disabled',true);
         searchGuid(pid);
       	for(var n in guids){
       		if(pid  !=  _temps[n].id){
       		 _temps[n].checked=true;
            // checkboxs[pid]=_temps[n];
             if(_temps[n].id != pid){
                 $('label[for='+_temps[n].id+']',$this).find('input').attr('checked',false).attr('disabled',true);
             }
       		}
        } 
       
    }else{
        $('label[for='+pid+']',$this).find('input').attr('checked',false).attr('disabled',false);
       /*  searchGuid(pid);
        for(var n in guids){
        	if(pid  ==  _temps[n].id){
        		_temps[n].checked=false;
           		 delete checkboxs[pid] ;
                if(_temps[n].id != pid){
                    $('label[for='+_temps[n].id+']',$this).find('input').attr('checked',true).attr('disabled',true);
                }
        	}
        	 
        } */
    }
}

//删除全部单位
function _allDel(){
    var ckAll={};
    for(n in resultbox){
        var o=resultbox[n];
        var id=o.id;
        var parentid=o.parentid;
        o.checked=false;
        o.nums=defalut_nums;
        o.serial='';
        // 判断是否 是 disable  的
        var currentNode  = $('label[guid='+n+']').find('input');
        // 没有check 但是disable 
        if(!currentNode.attr('checked')&& currentNode.attr('disabled')){
        	
        }else{
        	$('label[for='+id+']').removeClass('fcc').find('input').attr('checked',false).attr('disabled',false);
        }
        
        ckAll[parentid]=parentid;
        delete checkboxs[id];
    }
    $('#fw_right').html('');
    resultbox={};
    //优化后的全选检查
    for(c in ckAll){
        isCheckAll(c);
    }
    depids = "";
    depnames = "";
    ckAll={};
    delete ckAll;
}

//删除选择的单位
function _del(){
    for(n in delboxs){
        var o=delboxs[n];
        var id=o.id;
        var guid = o.guid;
        var pid=o.parentid;
        var name = o.name;
        o.checked=false;
        o.nums=defalut_nums;
        o.serial='';
        $('label[for=_r_'+id+']' ,$('#fw_right')).remove();
        $('input[id='+id+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
        $('label[for='+id+']' ,$('#fw_left')).removeClass('fcc');
		// 处理父  
		searchGuid(_temps[guid].parentid);
		for(var n in guids){
			if(_temps[n].checked&&!_temps[n].seconddept){
	            _temps[n].checked=false;
	            $('input[id='+_temps[n].pid+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
	        }
		}
        

        if(depids!=""){
            var pattern = id+",";
            depids = depids.replace(new RegExp(pattern), "");
        }

        if(depnames!=""){
            var pattern = name+",";
            depnames = depnames.replace(new RegExp(pattern), "");
        }
        delete checkboxs[id];
        delete resultbox[id];
    }
    delboxs={};
}
//删除单个单位
function _delOne(obj){
    if(obj){
        var o=obj;
        var id=o.id;
        var name = o.name;
        var pid=o.parentid;
        o.checked=false;
        o.nums=defalut_nums;
        o.serial='';
        if(depids!=""){
            var pattern = id+",";
            depids = depids.replace(new RegExp(pattern), "");
        }

        if(depnames!=""){
            var pattern = name+",";
            depnames = depnames.replace(new RegExp(pattern), "");
        }
        $('input[id='+id+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
        $('label[for='+id+']' ,$('#fw_left')).removeClass('fcc');

        searchGuid(pid);
		for(var n in guids){
			if(_temps[n].checked){
	            _temps[n].checked=false;
	            $('input[id='+_temps[n].pid+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
	        }
		}
        
       /*  if(_temps[pid].checked){
            _temps[pid].checked=false;
            $('input[id='+pid+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
        } */
        delete checkboxs[id];
        delete resultbox[id];
        delete delboxs[id];
    }
}

//获得信息
//demo _getValue(['id','name','parentid'],'|')
function _getValue(fields,splitstr){
    var result=[],str='';
    for(n in resultbox){
        var o=resultbox[n];
        var parentid=o.parentid;
        if(parentid!=null){
            str="";
            var i=0,l=fields.length;
            for(i;i<l;i++){
                if(str!=''&&l>1){
                    str+=splitstr;
                }
                str+=o[fields[i]];
            }
            result.push(str);
        }
    }
    return result;
}

//保存分组信息
function _saveGroup(){
    var submit_str = _getValue(['name','id','nums','parentid'],'|').join(';');
    if(submit_str=="")
    {
        alert("请添加收文单位");
        return;
    }
    var obj = new Object();
    obj.name=submit_str;
    window.showModalDialog("${curl}/selectTree_toSaveGroup.do?rnd="+Math.random(),obj,
            'dialogWidth: 300px;dialogHeight: 30px; status: no; scrollbars: no; Resizable: no; help: no;');
}

//选择存在的组
function _getGroup(){
    var retVal="";
    retVal=window.showModalDialog("${curl}/selectTree_showGroup.do?rnd="+Math.random(), "",
            'dialogWidth: 300px;dialogHeight: 350px; status: no; scrollbars: no; Resizable: no; help: no;');
    if 	(retVal != null){
        tmparry = retVal.split(";");
        var temarr = new Array();
        for(var i=0,l=tmparry.length;i<l;i++)
        {
            var id=tmparry[i].split('|')[1];
            var name=tmparry[i].split('|')[0];
            checkboxs[id]=_temps[id];
            resultbox[id]=_temps[id];
        }
        _add(resultbox);
    }
}

var depValue = "";
//提交操作
//var isSubmit=false;
//window.onbeforeunload=function(){};
function _submit(){
    //isSubmit=true;
    var o={};
    o.src= resultbox;
    var depId = "";
    var  depName = "";
    var _value = "";
    for(var a in o.src ){
    	var t_num =o.src[a]["nums"];
    	/* if(!o.src[a]["nums"]){
    		t_num = document.getElementById("fs").value;
    	} */
    	t_num = document.getElementById("fs").value;
        depId += o.src[a]["id"]+"["+t_num+"]"+",";
        depName += o.src[a]["name"]+",";
        //depId+= "{"+o.src[a]["id"]+"}"+",";
        //  depName += o.src[a]["name"] +"["+o.src[a]["nums"]+"],";
    }
    if(depId!=''){
        depId = depId.substr(0, depId.length-1);
        depName = depName.substr(0, depName.length-1);
        _value = depId+"*"+depName;
    }
    var sdtjbm = document.getElementById("sdtjbm").value;
    depValue = _value;
    if(sdtjbm!=''){
        if(depValue!=''){
            depValue +=",";
        }
        depValue  += sdtjbm ;
    }
}

//html模式页面返回值
function getHtmlRetValue(){
	/* 
	window.close();
	window.returnValue=depValue; */
	_submit();
	
	if(top.window && top.window.process && top.window.process.type){
        var remote = top.window.nodeRequire('remote');
        var browserwindow = remote.require('browser-window');
        var win = browserwindow.fromId(parseInt($.Request('focusedId')));
		if(win){
			win.webContents.send('message-departmentTree',depValue);
        }
    }else{
    	opener.window.returnValue = depValue;
	    window.close();
	}
}

//重设默认单位
$(document).ready(function (){
    creatHtml(_temps);
});
function chk_nums(str,type,bool){
    var str = str.replace(/[^\d]/g,'');
    if (str < 1){str = defalut_nums}
    return str;
}
//获取鼠标位置
function mousePosition(ev){
    if(ev.pageX || ev.pageY){
        return {x:ev.pageX, y:ev.pageY};
    }
    return {
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
        y:ev.clientY + document.body.scrollTop - document.body.clientTop
    };
}


//控件调用返回值
function cdv_getvalues(){
    _submit();
    return depValue;
}

function send(){
	  var o={};
	    o.src= resultbox;
	    var depId = "";
	    var  depName = "";
	    var _value = "";
	    var num = "";
	    for(var a in o.src ){
	        depId += o.src[a]["id"]+",";
	        depName += o.src[a]["name"] +",";
	         num += o.src[a]["nums"] +",";
	    }
	    if(depId!=''){
	        depId = depId.substr(0, depId.length-1);
	        depName = depName.substr(0, depName.length-1);
	        num = num.substr(0, num.length-1);
	    }
	    depValue = depId+"*"+depName+"*"+num;
	    
	    if(top.window && top.window.process && top.window.process.type){
            var remote = top.window.nodeRequire('remote');
            var browserwindow = remote.require('browser-window');
            var win = browserwindow.fromId(parseInt($.Request('focusedId')));
			if(win){
                win.webContents.send('message-departmentTree',depValue);
            }
        }else{
        	opener.window.returnValue = depValue;
		    window.close();
		}
	   /*  window.returnValue = depValue;
		window.close(); */
}

function setNum(){
    var fs = document.getElementById("fs").value;
    if(fs!=""){
        defalut_nums = fs;
    }
}
</script>
<script>
(function ($) {
 $.extend({
  Request: function (m) {
   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
   return sValue ? sValue[1] : sValue;
  },
  UrlUpdateParams: function (url, name, value) {
   var r = url;
   if (r != null && r != 'undefined' && r != "") {
    value = encodeURIComponent(value);
    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
    var tmp = name + "=" + value;
    if (url.match(reg) != null) {
     r = url.replace(eval(reg), tmp);
    }
    else {
     if (url.match("[\?]")) {
      r = url + "&" + tmp;
     } else {
      r = url + "?" + tmp;
     }
    }
   }
   return r;
  }
 
 });
})(jQuery);
</script>
</html>