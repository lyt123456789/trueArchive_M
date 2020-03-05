<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="">
<meta name="keywords" content="">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<base target="_self">
<%@ include file="/common/header.jsp"%>
<!--表单样式-->
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<!--  告诉浏览器，页面用的是UTF-8编码 -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<script src="${ctx}/widgets/zTree/assets/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/widgets/zTree/assets/js/GL.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/widgets/zTree/assets/js/jquery.arrayUtilities.min.js" type="text/javascript" charset="utf-8"></script>
<!--原人员树通用库-->
<script src="${ctx}/widgets/zTree/assets/js/common-ui.js" type="text/javascript" charset="utf-8"></script>
<!--滚动条-->
<script src="${ctx}/widgets/zTree/assets/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<!--树状图核心库-->
<script src="${ctx}/widgets/zTree/assets/js/zTree/jquery.ztree.core.min.js" type="text/javascript" charset="utf-8"></script>
<!--多选框拓展-->
<script src="${ctx}/widgets/zTree/assets/js/zTree/jquery.ztree.excheck.min.js" type="text/javascript" charset="utf-8"></script>
<!--隐藏拓展-->
<script src="${ctx}/widgets/zTree/assets/js/zTree/jquery.ztree.exhide.min.js" type="text/javascript" charset="utf-8"></script>
<!--拖动编辑-->
<script src="${ctx}/widgets/zTree/assets/js/zTree/jquery.ztree.exedit.min.js" type="text/javascript" charset="utf-8"></script>
<!--页面独立树状图生成及操作函数-->
<script src="${ctx}/widgets/zTree/assets/js/function.zTree4rd.js" type="text/javascript" charset="utf-8"></script>
<!-- 引入layer  -->
<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
<!--移动端touch事件-->
<script src="${ctx}/widgets/zTree/assets/js/touch.min.js" type="text/javascript" charset="utf-8"></script>
<!--树状图原生样式表-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/zTreeStyle/zTreeStyle.css"/>
<!--原树状图通用样式类-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/common.css"/>
<!--新版树状图样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/OAzTree.css"/>
<style type="text/css">
.tw-usertree-list,.tw-usertree-list-copy{
    margin-left: 0;
}
.tw-usertree-list-copy{
	margin-top: 5px;
	height: 100%;
}
.JS_treeulist.JS_hover{
	cursor: pointer;
	background: #fff;
	border:1px solid #bdbdbd!important;
}
.JS_treeulist.JS_hover.active{
	background: #fff;
	border:2px solid rgb(55, 167, 255)!important;
}
.tw-usertree-choice .tw-usertree-select .tw-mod .tw-bd{
	padding: 20px 15px 0 15px;
}
.tw-usertree-list-copy li {
  padding: 5px 10px;
}

.tw-usertree-list-copy li span {
  display: inline-block;
  vertical-align: middle;
  *display: inline;
  *zoom: 1;
  max-width: 90%;
}

.tw-usertree-list-copy li .tw-usertree-del {
  float: right;
  font-size: 16px;
  margin-top: 2px;
  padding-right: 8px;
  cursor: pointer;
}

.tw-usertree-list-copy li:hover {
  background: #f1f1f1;
}

.tw-usertree-list-copy li:hover .tw-usertree-del {
  color: #f64949;
}
.tw-usertree-list {width:100%!important;}
.tw-online {
    display: inline-block;
    vertical-align: middle;
    text-align: center;
    border: 0 none;
    box-sizing: border-box;
    border-radius: 2px;
    font-weight: 400;
    line-height: 1.2;
    white-space: nowrap;
    background-image: none;
    border: 1px solid transparent;
    cursor: pointer;
    outline: 0;
    -webkit-appearance: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}
.tw-online-true {
    padding: 3px 5px;
    font-size: 12px;
    color: #5eb95e;
    background: #fff;
    border: none;
    border-radius: 50%;
}
.tw-online-flase {
    padding: 3px 5px;
    font-size: 12px;
    color: #444;
    background-color: #e6e6e6;
    border: 1px solid #e6e6e6;    
    border-radius: 50%; 
}
.loading-container{
	position: absolute;
	top: 0;
	left: 0;
	z-index: 999999;
	width: 100%;
	height: 100%;
	margin-left: 2%;
	background:url(assets/plugins/layer/skin/default/loading-3.gif) center center no-repeat;
	background-size:180px 130px;
}

</style>
<script type="text/javascript">
var status = '${status}';

$(document).ready(function() {
	//从接口获取TAB组信息
	//$('.loading-container').show();
	$.ajax({
		//测试用本地json数据
		url: './widgets/zTree/njgc/getTreeGroup.json?t=' + new Date().getTime(),
		//接口数据
		dataType: "json",
		async:true,
		success: function(data) {
			//layer.close(index);
			//$('.loading-container').hide();
			if(data.length>0){
				//存储分组信息
				_globalObject.groupList = data;
				_globalObject.nodeCache = {};
				_globalObject.idList = {};
				_globalObject.nameList = {};
				_globalObject.asyncGetUsers = {};
				//生成tab标签组
				createTabsHtml(data);
				//缓冲加载防止页面卡死
				setTimeout(function(){
					laterLoad();
				}, 1);

				var laterLoad = function(){
					var finishNum = 0;
					var successDataArr = [];
					
					//获取每个tab标签下的节点json
					$.each(data, function(i,e){
						var _url = addUrlPara(e.apiUrl,'t', new Date().getTime());
						_url = addUrlPara(_url,"userid",$("#userid").val());
						//$('.loading-container').show();
						$.ajax({
							url:_url,//加上随机参数
							dataType: "json",
							async: true,
							success: function(chilData){
								finishNum++;
								
								if(chilData.length>0){
									
									
									//测试==>>重组了json//缓存起来
									_globalObject.nodeCache[e.domId] = reBuildJson(chilData,[]);
									//初始化人员对比表
									_globalObject.changeList[e.domId] = [];
									//所有id
									_globalObject.idList[e.domId] = reBuildIdList(chilData);
									//$('body').append('<code>'+JSON.stringify(_globalObject.nodeCache[e.domId])+'</code>');
									
								}else{
									//alert(e.name+'人员数据拉取异常');
								}
								if(finishNum == data.length){
									$('.loading-container').hide();
									$.each(data, function(i,e){
						var t = $("#"+e.domId);
						var setting = {
							check: {
								enable: true//开启多选框
							},
							edit: {//编辑、拖动拓展
								drag: {
									autoExpandTrigger: true//自动展开时是否触发回调事件
									,isCopy: false
									,isMove: true//所有拖拽都是move
									,inner: false//不能成为子节点
								}
								,enable: false//默认关闭
								,showRemoveBtn: false
								,showRenameBtn: false
							},
							data: {
								simpleData: {
									enable: true//json格式化
								}
							},
							view: {
								dblClickExpand: false,//关闭双击事件
								expandSpeed: '',//取消展开动画
								autoCancelSelected: false,
								selectedMulti: false,
								addDiyDom:addDiyDom
							},
							callback: {
								onClick: nodeOnClick//点击事件
								,onCheck: nodeOnCheck//checkbox事件
								,beforeExpand:beforeExpand
								,beforeCheck:beforeCheck
								//,beforeDrag: beforeDrag//拖动前触发
								//,onDrop: onDrop//拖动结束触发ajax,提交排序方法保存数据
							}
						};
						var zNodes = _globalObject.nodeCache[e.domId];
						$.fn.zTree.init(t, setting, zNodes);

					})
					//绑定tab切换事件
					$('.tw-tabs-nav').find('[data-tab-idx]').click(function(){
						var tab_idx = $(this).attr('data-tab-idx');
						var $zTreeObj = $('.tw-tab-panel[data-tab-idx='+tab_idx+']').find('.ztree'); 
						if( $zTreeObj.length > 0 ){
							dataRelation($zTreeObj.attr('id'));
						}else{
							alert('tab切换数据联动异常');
						}
					})
					//console.log(JSON.stringify(_globalObject.nodeCache['ownOrg']));
					//绑定移动端hold长按事件
					if(_globalObject.isMobiles){
						touch.on('.ztree li a span', 'touchstart touchend hold', function(ev){
							//阻止默认行为
							if(ev.type=='touchstart'){
								ev.preventDefault();
								_globalObject.touchStartTime = ev.timeStamp;
							}
							//判断时长，若小于长按，则触发点击事件
							if(ev.type=='touchend'){
								_globalObject.touchEndTime = ev.timeStamp;
								var t = _globalObject.touchEndTime - _globalObject.touchStartTime;
								if(t<650){
									touch.trigger(ev.target, 'click');
								}
							}
							//长按事件，获取父级li标签id，获取node，触发check事件
							if(ev.type=='hold'){
								var obj = $(ev.target).parentsUntil('.ztree').parent('.ztree');
								if(obj.length>0){
									var zTree = $.fn.zTree.getZTreeObj(obj.attr('id'));
									var tObj = $(ev.target).parentsUntil('li').parent('li');
									if(tObj.length>0){
										var tId = tObj.attr('id');
										var ckNode = zTree.getNodeByTId(tId);
										if(ckNode!==null){
											zTree.checkNode(ckNode, null, true, true);
										}else{
											alert('tId node is null');
										}
									}else{
										alert('tId is null');
									}
								}else{
									alert('hold error');
								}
							}
						});
					}else{
						//setValueOfPerson();
					}
								}
							}
						})
					})
					//创建树结构
					
				}
			}else{
				$('.loading-container').hide();
				alert('分组信息接口异常');
			}
			document.getElementsByClassName("tw-btn-primary")[0].style.display='none';
	    }
	
	});
});
</script>
</head>
<body >
<input type="hidden" name="macaddr" id="macaddr" value="${mac }"/>
<div class="tw-usertree-choice clearfix" style="height: auto">
    <input type="hidden" id="userid" value="${userid}"/>
	<div class="tw-usertree-wrap" style="/*width: 96%;*/background: #fff;padding:0;padding-bottom:20px">
		<div class="tw-usertree-tabs" style="position:relative">
			<ul class="tw-tabs-nav">
	            
	        </ul>
	        <div class="tw-tabs-bd">
	            
	        </div>
	        <div class="loading-container">
			</div>
		</div>
		
	</div>
	<div class="tw-usertree-select" style="/*display: none;*/">
		<div class="tw-mod">
			<div class="tw-hd">已经选择 <span>(<b id="countNum">0</b>)</span><span class="fr tw-act" onclick="delAll()">清除全部</span></div>
			<div class="tw-bd">
				<div class="JS_wrap">
					<ul class="tw-usertree-list JS_hover active JS_treeulist JS_scrollt" data-keyName='A'></ul>
					<div class="clearfix"></div>
					手动添加部门:
					<input type="text" id="others" style="width:100%; margin-top:7px; height:22px; padding:2px 0; line-height:22px; border:2px solid rgb(55, 167, 255)" />
				</div>                 
				<div>
					
				</div>
			</div>
			<select id="oldSelect" size="20" style="display:none; width:100%;height: 290px;border: 1px dashed #C2C2C2" multiple="multiple">
<!-- 				<c:forEach var="m" items="${mapList}">
					<option value="${m.employee_id }|${m.employee_name }|${m.employee_shortdn }">${m.employee_name} { ${m.employee_shortdn} }</option>
				</c:forEach> -->
			</select>
			<select id="depSelect" size="20" style="display:none; " multiple="multiple">
			</select>
		</div>
	</div>
	
</div>
 <input type="hidden" id="ret" value=""/>

<script type="text/javascript">
	//var selfIndex = parent.layer.getFrameIndex(window.name);
	
	function closeSelfLayer(){
        window.close();
	}
/* 	function save(){
		var xtoId = "";
		var xtoName = "";
		$(".tw-usertree-list li").each(function(i,e){
			xtoId += $(this).attr("data-id")+";";
			xtoName += $(this).find("span").text()+";";
		});
		parent.$("#"+nameType).val(xtoName);
		parent.$("#"+idType).val(xtoId);
		parent.layer.close(selfIndex);
	} */
	
	function sendNext(){
		var neRouteType = '${routType}'*1;
		if(null != status && status=='all'){
			neRouteType = 1;
		}
		var xtoName = "";
		var xccName = "";
		if(neRouteType == 0){
			if($(".tw-usertree-list li").length > 1){
				layer.msg("此节点类型发送人只能有一个，请重新选择！",{time:1000,icon:0});
				return false;
			}else if($(".tw-usertree-list li").length < 1){
				layer.msg("请选择发送人！",{time:1000,icon:0});
				return false;
			}
			$(".tw-usertree-list li").each(function(i,e){
				xtoName = $(this).attr("data-id")+",";
			});
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$("#ret").val(xtoName);
		}else if(neRouteType == 1 || neRouteType == 3 || neRouteType == 4 || neRouteType == 5 || neRouteType == 6){
			if($(".tw-usertree-list li").length < 1){
				layer.msg("请选择发送人！",{time:1000,icon:0});
				return false;
			}
			$(".tw-usertree-list li").each(function(i,e){
				xtoName += $(this).attr("data-id")+",";
			});
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$("#ret").val(xtoName);
		}else if(neRouteType == 2){
			if($(".tw-usertree-list li").length < 1){
				layer.msg("请选择主送人！",{time:1000,icon:0});
				return false;
			}
			if($(".tw-usertree-list li").length > 1){
				layer.msg("只能选择一个主送人！",{time:1000,icon:0});
				return false;
			}
			$(".tw-usertree-list li").each(function(i,e){
				xtoName += $(this).attr("data-id")+",";
			});
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$(".tw-usertree-list-copy li").each(function(i,e){
				xccName += $(this).attr("data-id")+",";
			});
			xccName = xccName.substring( 0, xccName.length - 1);
			if(xccName == xtoName){
				layer.msg("主送人和抄送人不能为同一人",{time:1000,icon:0});
				return false;
			}
			$("#ret").val(xtoName+";"+xccName);
		}
		var sendMsg = "";
		if(document.getElementById("sendMsg") && document.getElementById("sendMsg").checked){
			sendMsg = document.getElementById("sendMsg").value;
		}
			if(top.window && top.window.process && top.window.process.type){
                var remote = top.window.nodeRequire('remote');
	            var browserwindow = remote.require('browser-window');
	            var win = browserwindow.fromId(parseInt($.Request('focusedId')));
				if(win){
					if(null != sendMsg && '' != sendMsg){
	                	win.webContents.send('message-departmentTree',$("#ret").val()+"#"+sendMsg);
	                }else{
	                	win.webContents.send('message-departmentTree',$("#ret").val());
	                }
                }
            }else{
            	if(null != sendMsg && '' != sendMsg){
            		opener.window.returnValue = $("#ret").val()+"#"+sendMsg;
            	}else{
            		opener.window.returnValue = $("#ret").val();
            	}
			    window.close();
			}
	}
	
	function editGroup(){
		var link = document.createElement("a");
		link.href = '${ctx}/selectTree_setDeptGroup.do?userid=${userid}';
		document.body.appendChild(link);
		link.click();
	}

	//是否可排序的开关
	function editEnabled(obj){
		var $obj = $(obj);
		//所有自定义组都将开启排序功能
		$.each(_globalObject.groupList, function(i,e) {
			if(e.isCustomGroup==true){
				editStatusChange(e.domId,$obj);
			}
		});
	}
	//状态切换，暂时是存全局变量的，将来可以定义在json内，放到后台配置项
	function editStatusChange(d,o){
		var treeObj = $.fn.zTree.getZTreeObj(d);
		treeObj.setEditable(_globalObject.editStatus?false:true);
		o.text(_globalObject.editStatus?'开启排序':'关闭排序');
		_globalObject.editStatus = !_globalObject.editStatus;
	}
	
	
	//79885a7f-b25f-4420-89f7-0a3e25d24e30[6],72296cd1-8f17-4bea-a966-532fc2391d88[6]*区委办,区政协办
	//EB0BA931-986A-4092-8455-7065C54393D9[1],ABB77AF6-40BA-40FA-ADB3-5BE853ED705E[1],*国土资源局,气象局,
	//控件调用返回值
	function cdv_getvalues(){
		var depid = "";
		var depname = "";
		$(".tw-usertree-list li").each(function(i,e){
			depid += $(this).attr("data-id")+"[1],";
			depname += $(this).find("span").text()+",";
		});
		depid = depid.substring(0, depid.length-1);
		depname = depname.substring(0, depname.length-1);
	    return depid+"*"+depname+","+$("#others").val();
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
</body>
</html>
