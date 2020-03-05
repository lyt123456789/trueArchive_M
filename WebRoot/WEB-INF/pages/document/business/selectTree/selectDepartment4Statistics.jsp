<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<title>选择单位</title>
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

#fw_right,#fw_left {
	height: 400px;
	width: 600px;
	overflow: auto;
	overflow-x: hidden;
	border: 1px #999999 solid;
	background: #FFF
}

#fw_right {
	width: 220px;
	padding-left: 10px
}

.button input {
	height: 24px;
	background: #EAEAEA;
	cursor: hand;
}
.nbutton{border:1px solid #ccc; background-color:#EFEFEF; width:100px; text-align:center;}
.title{ padding:5px 10px 5px 5px;background-color:#EFEFEF; border-bottom:1px solid #CCC; color:#0C5DAC;}
.content{ border-bottom:1px solid #CCC; padding:5px; overflow:hidden; zoom:1;}
.content label{/*overflow: hidden;text-overflow: ellipsis;white-space:nowrap;*/width:49%; float:left; display:block; line-height:24px; margin-right:1%;}
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
    <div class="cbo p5">
    	<div class="fl">
			<table id="keyform"><tr><td width="100">&nbsp;&nbsp;&nbsp;&nbsp;快速定位</td><td><input type="text" id="keywords" value="输入单位名称查询" style="width:240px;" /></td><td width="60" align="center"><button id="keybutton">查 询</button></td><td id="keypos"></td></tr></table>
    	</div>
        <div align="right"></div>
    </div>
    <table width="100% cellpadding="0" cellspacing="0" border="0">
    	<tr>
        	<td>
            	<div id="fw_left"></div>
            </td>
            <td valign="top" align="center" width="100%"  class="button">
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
            <td><div id="fw_right"></div></td>
        </tr>
    </table>
<!--new html end-->   	
		<div align="center" style="margin-top: 10px" class="button">
			<input class="btn" name="确定" type="button" value=" 确 定 " class="nbutton" onclick="_submit()" />
		</div>
	</body>
<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
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
	var defalut_nums=5;
	
	if(window.dialogArguments.num>-1){
		defalut_nums=window.dialogArguments.num;
	}
	var node=function(id,name,checked,parentid,nums,gzname){
		this.id=id;					//主键
		this.name=name;				//节点名
		this.checked=!!checked;		//是否选中
		this.parentid=parentid;		//父级id
		this.nums=nums;				//发件数
		this.serial='';				//扩展的编号
		this.gzname=gzname;
	};
	var json=${json};
	var js=0,jl=json.length;
	for(js;js<jl;js++){
		var pid=json[js].id;
		var pname=json[js].name;
		var children=json[js].children;
		var si=0,sl=children.length;
		_temps[pid]=new node(pid,pname,false,null,defalut_nums,'');
		for(si;si<sl;si++){
			var sid=children[si].id;
			var sname=children[si].name;
			_temps[sid]=new node(sid,sname,false,pid,defalut_nums,children[si].gzname);
		}
	}
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
			if(!_temps[id].checked){
				$(this).find('input').attr('checked',pi);
				if(!pi){
					delete checkboxs[id];
				}else{
					checkboxs[id]=_temps[id];
				}
			}
		});
	}).live('mousedown',function(ev){
		//拖拽全选事件
		var t=ev.srcElement||ev.target;
		var disalbe=$(this).find('input').attr('checked');
		if(t.nodeName=='LABEL'&&!disalbe){
			var id=$(this).attr('sdata');
			var name=_temps[id].name;
			var html='<label for="_r_'+id+'" sdata="'+id+'" class="tempbox"><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'" />'+name+'</label>';
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
						if(!_temps[id].checked){
							$(this).find('input').attr('checked',true);
							_addOne(_temps[id]);
						}
					});
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
	});
	//单元素
	$('label[pdata]',$('#fw_left')).live('click',function(){
		//单元素选择事件
		var pi=$(this).find('input').attr('checked');
		var id=$(this).attr('sdata');
		if(pi){
			checkboxs[id]=_temps[id];
		}else{
			delete checkboxs[id];
		}
	}).live('mousedown',function(ev){
		//单元素拖拽事件
		var t=ev.srcElement||ev.target;
		var disalbe=$(this).find('input').attr('checked');
		if(t.nodeName=='LABEL'&&!disalbe){
			var id=$(this).attr('sdata');
			var name=_temps[id].name;
			var parentid=_temps[id].parentid;
			var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'" class="tempbox"><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'" />'+name+'</label>';
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
					_addOne(_temps[_el.attr('sdata')]);
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
	});
	
	//右侧单元素
	//修改发文数
	$('label[pdata]',$('#fw_right')).find('.nums').live('blur',function(e,data){
		resultbox[$(this).attr('nid')].nums=chk_nums.call($(this),$(this).val(),'nums');
	})
	//点击待删
	$('label[pdata]',$('#fw_right')).live('click',function(){
		var pi=$(this).find('input').attr('checked');
		if(pi){
			delboxs[$(this).attr('sdata')]=_temps[$(this).attr('sdata')];
		}else{
			delete delboxs[$(this).attr('sdata')];
		}
	}).live('mousedown',function(ev){
		//单元素反向拖拽事件
		var t=ev.srcElement||ev.target;
		if(t.nodeName=='LABEL'){
			var id=$(this).attr('sdata');
			var name=_temps[id].name;
			var parentid=_temps[id].parentid;
			var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'" class="tempbox"><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'" />'+name+'</label>';					//var bghtml='<div class="tempboxbg"></div>';
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
	});
	
	//构造场景
	function creatHtml(o){
		if(!o) return;
		for(n in o){
			var id=o[n].id;
			var name=o[n].name;
			var checked=o[n].checked;
			var parentid=o[n].parentid;
			if(parentid==null||parentid==id){
				var html='<div class="t_'+id+' title" sdata="'+id+'" id="t_'+id+'"><label for="'+id+'"><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" />'+name+'</label></div>';
				var el=$(html).appendTo($('#fw_left'));
				$('<div class="c_'+id+' content"></div>').appendTo($('#fw_left'));
				/**增加事件**/
				/**end**/
				/**全选拖拽实现**/
				/**end**/
			}else{
				var html='<label for="'+id+'" pdata="'+parentid+'" sdata="'+id+'"><input name="'+id+'" type="checkbox" id="'+id+'" value="'+id+'" />'+name+'</label>';
				var el=$(html).appendTo($('.c_'+parentid));
				/**增加事件**/
				/**拖拽实现**/
				/**end**/
			}
		}
	}
	
	//拖拽添加
	function _addOne(obj){
		if(!obj) return;
		var o=obj;
		var id=o.id;
		var name=o.name;
		var checked=o.checked;
		var parentid=o.parentid;
		var nums=o.nums;
		var serial=o.serial;
		_temps[id].checked=true;
		if(parentid!=null){
			var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'"><input name="r_'+id+'" type="checkbox" id="r_'+id+'" value="'+id+'"/>'+name+'</label>';
			var el=$(html).appendTo($('#fw_right'));
			
			//保存待发送的单位
			resultbox[id]=_temps[id];
			checkboxs[id]=_temps[id];
			var $this=$('#fw_left');
			$('label[for='+id+']',$this).addClass('fcc').find('input').attr('checked',true).attr('disabled',true);
			//判断是否全选
			if(!_temps[parentid].checked) isCheckAll(parentid);
			/**增加事件**/
			/**end**/
			//反向拖拽实现
			//反向拖拽实现
			/**end**/
		}
	}
	
	//添加单位
	function _add(obj){
		var ckAll={};
		var isr=false;
		if(!obj) {
			obj=checkboxs;
			isr=true;
		}
		$('#fw_right').html('');

		for(n in obj){
			var o=obj[n];
			var id=o.id;
			var name=o.name;
			var checked=o.checked;
			var parentid=o.parentid;
			var nums=o.nums;
			var serial=o.serial;
			//保存返回的nums、serial
			if(!isr){
				_temps[id].nums=nums;
				_temps[id].serial=serial;
				checkboxs[id]=_temps[id];
			}
			_temps[id].checked=true;
			if(parentid!=null){
				var html='<label for="_r_'+id+'" pdata="'+parentid+'" sdata="'+id+'"><input name="r_'+id+'" type="checkbox" id="'+id+'" value="'+id+'"/>'+name+'</label>';
				var el=$(html).appendTo($('#fw_right'));
				//保存待发送的单位
				resultbox[id]=_temps[id];
				
				var $this=$('#fw_left');
				$('label[for='+id+']',$this).addClass('fcc').find('input').attr('checked',true).attr('disabled',true);
				//判断是否全选
				//if(!_temps[parentid].checked) 
				ckAll[parentid]=parentid;
				/**增加事件**/
				/**end**/
				//反向拖拽实现
				//反向拖拽实现
				/**end**/
			}
		}
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
		$('.c_'+pid,$this).find('label').each(function(){
			var id=$(this).attr('sdata');
			if(!_temps[id].checked)
				allc=false;
		});
		if(allc){
			$('label[for='+pid+']',$this).find('input').attr('checked',true).attr('disabled',true);
			_temps[pid].checked=true;
			checkboxs[pid]=_temps[pid];
		}else{
			$('label[for='+pid+']',$this).find('input').attr('checked',false).attr('disabled',false);
			_temps[pid].checked=false;
			delete checkboxs[pid];
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
			$('label[for='+id+']').removeClass('fcc').find('input').attr('checked',false).attr('disabled',false);
			ckAll[parentid]=parentid;
			delete checkboxs[id];
		}
		$('#fw_right').html('');
		resultbox={};
		//优化后的全选检查
		for(c in ckAll){
			isCheckAll(c);
		}
		ckAll={};
		delete ckAll;
	}
	
	//删除选择的单位
	function _del(){
		for(n in delboxs){
			var o=delboxs[n];
			var id=o.id;
			var pid=o.parentid;
			o.checked=false;
			o.nums=defalut_nums;
			o.serial='';
			$('label[for=_r_'+id+']' ,$('#fw_right')).remove();
			$('input[id='+id+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
			$('label[for='+id+']' ,$('#fw_left')).removeClass('fcc');
			
			if(_temps[pid].checked){
				_temps[pid].checked=false;
				$('input[id='+pid+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
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
			var pid=o.parentid;
			o.checked=false;
			o.nums=defalut_nums;
			o.serial='';
			$('input[id='+id+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
			$('label[for='+id+']' ,$('#fw_left')).removeClass('fcc');
			
			if(_temps[pid].checked){
				_temps[pid].checked=false;
				$('input[id='+pid+']' ,$('#fw_left')).attr('checked',false).attr('disabled',false);
			}
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
	//提交操作
	//var isSubmit=false;
	//window.onbeforeunload=function(){};
	function _submit(){
			//isSubmit=true;
			var o={};
			o.src= resultbox;
			o.num=defalut_nums;
			window.returnValue = o;
			window.close();
	}
	//重设默认单位
	$(document).ready(function (){
		creatHtml(_temps);
		var objDlg = window.dialogArguments;	//接主窗口传参
		if(typeof objDlg.dep=='object' ){
			//checkboxs=objDlg.dep;
			resultbox=objDlg.dep;
			
			$('#fw_left').focus();
			
			
			_add(resultbox);
			$('#keyform').searchpos('#fw_left');
		}
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
</script>
</html>