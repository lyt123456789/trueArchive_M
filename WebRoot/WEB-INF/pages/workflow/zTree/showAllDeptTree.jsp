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
<script src="${ctx}/widgets/zTree/assets/js/function.zTree.unifiledUser.js?t=2018" type="text/javascript" charset="utf-8"></script>
<!-- 引入layer  -->
<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
<!--移动端touch事件-->
<script src="${ctx}/widgets/zTree/assets/js/touch.min.js" type="text/javascript" charset="utf-8"></script>
<!--树状图原生样式表-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/zTreeStyle/zTreeStyle.css?t=2018"/>
<!--原树状图通用样式类-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/common.css?t=2018"/>
<!--新版树状图样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/OAzTree.css?t=2018"/>
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
	overflow:auto;
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
	background-size:80px 68px;
}

.piaochecked {
	background-image: url(widgets/zTree/assets/css/zTreeStyle/img/diy/tree.png);
	background-position: 0 -24px;
	width: 20px;
	height: 30px;
	display: inline-block;
	float: right;
	margin-top: -4px;
	margin-right: 4px;
}

.on_check {
	background-image: url(widgets/zTree/assets/css/zTreeStyle/img/diy/tree.png);
	background-position: 0 5px;
	width: 20px;
	height: 30px;
	display: inline-block;
	float: right;
	margin-top: -4px;
	margin-right: 4px;
}
.radioclass {
	opacity: 0;
	cursor: pointer;
	-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
	filter: alpha(opacity=0);
	margin-top:4px;
}
.tw-icon-times-circle:before{
	content: "";
	background-image: url(widgets/zTree/assets/css/zTreeStyle/img/closeBtn.png);
	background-size:19px 19px;
	width:19px;
	height:19px;
	display: inline-block;
}
.tw-usertree-choice .tw-usertree-search {
	padding: 10px;
	margin: 0;
}
.tw-usertree-tabs .tw-tab-panel {
	padding: 0;
}
.no-childs > ul > li {
	display: inline-block;
	float: left;
	width: 25%;
	min-width: 140px;
}
.no-childs > ul:after {
	content: '';
	display: block;
	height: 0;
	clear: both;
}
/*.no-childs > .switch {
	visibility: hidden;
}*/

.ztree li a,
.ztree li a.curSelectedNode {
	padding-top: 0;
}
.ztree li ul {
	padding: 10px 0 10px 18px;
}

.ztree li .button.switch.noline_close {
	width: 12px;
	height: 12px;
	margin-left: 10px;
	margin-right: 5px;
	background: url(${ctx}/widgets/zTree/assets/img/rarr.png) no-repeat center center;
}
.ztree li .button.switch.noline_open {
	width: 12px;
	height: 12px;
	margin-left: 10px;
	margin-right: 5px;
	background: url(${ctx}/widgets/zTree/assets/img/barr.png) no-repeat center center;
}
.ztree {
	padding: 0;
}
.ztree > li {
	background: #f1f1f1;
}
.ztree > li > a,
.ztree > li > a.curSelectedNode{
	color: #248be8;
}
.ztree > li > ul {
	background: #fff;
}
.no-childs.two-childs {
	width: 100%;
}
.tw-usertree-choice .tw-usertree-search .tw-form-text {
	width: 70%;
}
.tw-usertree-search {
	position: absolute;
	top: 0;
	right: 0;
}
.ztree li span.node_name {
	min-width: 32px;
	overflow: hidden;
}
.tw-usertree-list li span {
	width: 80px;
	overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.tw-panel-cnt,
.JS_wrap {
	min-height:400px;
	height: 416px;
	overflow:auto;
}
.JS_scrollt {
	min-height:357px;
}
</style>
<script type="text/javascript">
var status = '${status}';
var state = "0";
var isZf = '${isZf}';
$(document).ready(function() {
	var jsonurl = '';
	jsonurl = '${ctx}/widgets/zTree/ajaxData/getAllEmpTree.json?t=' + new Date().getTime();
	//从接口获取TAB组信息
	//$('.loading-container').show();
	$.ajax({
		//测试用本地json数据
		//url: './zTree/tongzhou/getTreeGroupDebug.json?t=' + new Date().getTime(),
		//接口数据
		url: jsonurl,
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
						_url = addUrlPara(_url,'allEmp', $("#allEmp").val());
						_url = addUrlPara(_url,'isDeptTree', $("#isDeptTree").val());
						_url = addUrlPara(_url,'searchSiteName', $("#searchSiteName").val());
						
						_url = addUrlPara(_url,'isZf', isZf);
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
										addDiyDom:addDiyDom,
										showIcon: false,
										showLine: false
									},
									callback: {
										onClick: nodeOnClick, //点击事件
										onCheck: nodeOnCheck, //checkbox事件
										beforeExpand: beforeExpand,
										beforeCheck: beforeCheck
										//,beforeDrag: beforeDrag//拖动前触发
										//,onDrop: onDrop//拖动结束触发ajax,提交排序方法保存数据
									}
								};
								var zNodes = _globalObject.nodeCache[e.domId];
								
								treeObj = $.fn.zTree.init(t, setting, zNodes);

								var checkedNodes = treeObj.getCheckedNodes();
								for(var i=0;i<checkedNodes.length;i++){
									nodeOnCheck('', e.domId, checkedNodes[i])
								}
								if(e.expandAll){
									treeObj.expandAll(true);
								}
								var nodes = treeObj.getNodes();
								if (nodes.length > 0) {
									for (var i = 0; i < nodes.length; i++) { //设置节点展开
										if(nodes[i].children) {
											treeObj.expandNode(nodes[i], true, false, true);
											beforeExpand(e.domId, '')
											for(var j = 0; j < nodes[i].children.length; j++) {
												//treeObj.expandNode(nodes[i].children[j], true, false, true);
												//beforeExpand(e.domId, '')
											}
										}
									}
								}
								defaultSelect('${selectedUserIds}');//默认选中
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
						_globalObject.initScrollPos();
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
	    }
	});
});
</script>
</head>
<body>
<input type="hidden" name="macaddr" id="macaddr" value="${mac }"/>
<div class="tw-usertree-choice clearfix" style="height: auto;background-position: 70% 50%;">
    <input type="hidden" id="allEmp" value="${allEmp}"/>
    <input type="hidden" id="isDeptTree" value="${isDeptTree}"/>
    <input type="hidden" id="searchSiteName" value="${searchSiteName}"/>
    
	<div class="tw-usertree-wrap" style="width: 65%;background: #fff;padding:0;padding-bottom:20px">
		<div class="tw-usertree-tabs" style="position:relative">
			<ul class="tw-tabs-nav">
	            
	        </ul>
			<div class="tw-usertree-search">
			</div>
	        <div class="tw-tabs-bd">
	            
	        </div>

	        <div class="loading-container">
			</div>
		</div>
		
	</div>
	<div class="tw-usertree-select" style="width: 25%;">
		<div class="tw-mod">
			<div class="tw-hd" style="font-size:16px;">已经选择 <span>(<b id="countNum">0</b>)</span><span class="fr tw-act" onclick="delAll()">清除全部</span></div>
			<div class="tw-bd">
				<c:if test="${routType != '2'}">
					<div class="JS_wrap">
						<ul class="tw-usertree-list JS_hover active JS_treeulist JS_scrollt" data-keyName='A'></ul>
						<div class="clearfix"></div>
					</div>
				</c:if>
				<c:if test="${routType == '2'}">
					<div class="JS_wrap">
						<div style="margin-bottom: 5px">主送</div>
						<ul class="tw-usertree-list JS_hover active JS_treeulist JS_scrollt" data-keyName='A'></ul>
						<div class="clearfix"></div>
						<div style="margin-top: 5px">抄送</div>
						<ul class="tw-usertree-list-copy JS_hover JS_treeulist JS_scrollc" data-keyName='B'></ul>
					</div>
				</c:if>
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
<!-- <div class="tw-search-bar tar">
	<div class="tw-top-tool" style="font-size:14px;">
		<div class="tw-btn-primary " style="cursor:pointer;font-size:14px;" onclick="sendNext();">
			<i class="tw-icon-save"></i> 确认选择
		</div>
		<div class="tw-btn" style="cursor:pointer;font-size:14px;" onclick="closeSelfLayer();">
			<i class="tw-icon-remove"></i> 取消选择
		</div>
	</div>
</div> -->

<script type="text/javascript">
function onSelectAllCheck(){
	var checked = event.currentTarget.checked;
	var pNode = event.currentTarget.parentNode;
	if(checked){
		$(pNode).removeClass('on_check').addClass('piaochecked');
	} else {
		$(pNode).removeClass('piaochecked').addClass('on_check');
	}
}
	
	function closeSelfLayer(){
		if(parent.document.getElementById("ifsave")){
			parent.document.getElementById("ifsave").value = "0";
		}
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
	
	function onSelectAllChange(){
		var checked = event.currentTarget.checked;
		var pNode = event.currentTarget.parentNode;
		if(checked){
			$(pNode).removeClass('on_check').addClass('piaochecked');
		} else {
			$(pNode).removeClass('piaochecked').addClass('on_check');
		}
		//var input = $('.tw-top-tool #sendMsg')[0];
		$(".tw-usertree-list li").each(function(){
			var input2 = $(this).find('input')[0];
			input2.checked = checked;
			var pNode = input2.parentNode;
			if(checked){
				$(pNode).removeClass('on_check').addClass('piaochecked');
			} else {
				$(pNode).removeClass('piaochecked').addClass('on_check');
			}
		});
		$(".tw-usertree-list-copy li").each(function(){
			var id = $(this).attr('data-id');
			var input2 = $(this).children('input')[0];
			input2.checked = checked;
		});
	}
	
	function sendNext(){
		if(parent.document.getElementById("ifsave")){
			parent.document.getElementById("ifsave").value = "1";
		}
		function getIsChecked(e){
			if(state == '0'){
				return '';
			} else {
				var input = $(e).find('input')[0];
				return '@'+(input.checked ? 1 : 0);
			}
		}
		function getCheckedIds(){
			var ids = "@";
			$(".tw-usertree-list li").each(function(){
				$(this).parent().prev()
				var id = $(this).attr('data-id');
				var input = $(this).find('input')[0];
				if(null != input && input.checked){
					ids = ids+id+',';
				}
			});
			$(".tw-usertree-list-copy li").each(function(){
				var id = $(this).attr('data-id');
				var input = $(this).find('input')[0];
				if(null != input && input.checked){
					ids = ids+id+',';
				}
			});
			ids = ids.substring(0,ids.length-1);
			return ids;
		}
		var isTra = '${isTra}';
		var neRouteType = '${routType}'*1;
		if(null != status && status=='all'){
			neRouteType = 1;
		}
		var xtoName = "";
		var userName = "";
		var pIds = "";
		if(neRouteType == 1 || neRouteType == 3 || neRouteType == 4 || neRouteType == 5 || neRouteType == 6){
			/* if($(".tw-usertree-list li").length < 1){
				layer.msg("请选择人员！",{time:1000,icon:0});
				return false;
			} */
			if(isTra == '1'){
				 if($(".tw-usertree-list li").length != 1){
						layer.msg("只能同步一人",{time:1000,icon:0});
						return false;
					} 
			}
			
			$(".tw-usertree-list li").each(function(i,e){
				userName += $(this).find('span').text() + ",";
				xtoName += $(this).attr("data-id")+",";
				pIds += $(this).attr("data-pId")+",";
			});
			alert(pIds);
			pIds = pIds.substring(0, pIds.length - 1);
			userName = userName.substring(0, userName.length - 1);
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$("#ret").val(xtoName + ";" + userName + ";" + pIds + getCheckedIds());
		}
		var isDeptTree = '${isDeptTree}';
		if(isDeptTree == '1'){
			parent.document.getElementById("deptIdAndNames").value = $("#ret").val();
		}else{
			parent.document.getElementById("userIdAndNames").value = $("#ret").val();
		}
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
	
	function test(){
		function getIsChecked(e){
			if(state == '0'){
				return '';
			} else {
				var input = $(e).find('input')[0];
				return '@'+(input.checked ? 1 : 0);
			}
		}
		function getCheckedIds(){
			var ids = "@";
			$(".tw-usertree-list li").each(function(){
				$(this).parent().prev()
				var id = $(this).attr('data-id');
				var input = $(this).find('input')[0];
				if(null != input && input.checked){
					ids = ids+id+',';
				}
			});
			$(".tw-usertree-list-copy li").each(function(){
				var id = $(this).attr('data-id');
				var input = $(this).find('input')[0];
				if(null != input && input.checked){
					ids = ids+id+',';
				}
			});
			ids = ids.substring(0,ids.length-1);
			return ids;
		}
		var isTra = '${isTra}';
		var neRouteType = '${routType}'*1;
		if(null != status && status=='all'){
			neRouteType = 1;
		}
		var xtoName = "";
		var userName = "";
		var pIds = "";
		if(neRouteType == 1 || neRouteType == 3 || neRouteType == 4 || neRouteType == 5 || neRouteType == 6){
			/* if($(".tw-usertree-list li").length < 1){
				layer.msg("请选择人员！",{time:1000,icon:0});
				return false;
			} */
			if(isTra == '1'){
				 if($(".tw-usertree-list li").length == 0){
						layer.msg("请选择一人授予角色",{time:1000,icon:0});
						return false;
					} 
				 if($(".tw-usertree-list li").length > 1){
						layer.msg("只能授予一人角色",{time:1000,icon:0});
						return false;
					} 
			}
			
			$(".tw-usertree-list li").each(function(i,e){
				userName += $(this).find('span').text() + ",";
				xtoName += $(this).attr("data-id")+",";
				pIds += $(this).attr("data-pId")+",";
			});
			pIds = pIds.substring(0, pIds.length - 1);
			userName = userName.substring(0, userName.length - 1);
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$("#ret").val(xtoName + ";" + userName + ";" + pIds + getCheckedIds());
		}
		
		return $("#ret").val();
	}
	
	function editGroup(){
		var link = document.createElement("a");
		link.href = '${ctx}/ztree_toSetUserGroup.do?nodeId=${nodeId}&type=${type}';
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
	
	function ThirdChat(userId){
		/* var macaddr = document.getElementById("macaddr").value;
		if(macaddr==null || macaddr==''){
			return;
		}
		if(type!=null && type=='0'){
			alert("该人员不在线,只能发送离线消息！");
		}
		var userId = o.id;
		if(macaddr!=null){
			macaddr = macaddr.replaceAll(':','-');
		} */
		//ajax异步调取后台
		$.ajax({
			url: '${ctx}/ztree_ThirdChat.do',
	        type: 'POST',
	        cache: false,
	        async: false,
	        data:{
	        	'userId':userId,'macaddr':$("#macaddr").val()
	        },
	   		error: function(){
	   			 alert('AJAX调用错误');
	   		},
	   		success: function(result){
	   			if(result!=null && result=='unOnline'){
	   				alert("当前人员实时通讯系统不在线,请登录");
	   			}
	   		}
		});
	}
	//默认选中
	function defaultSelect(defIds){
		if(defIds == null || defIds == ''){
			defIds = sessionStorage.getItem("userIds");
			if(defIds != null && defIds != ''){
				sessionStorage.removeItem("userIds");
			}
		}
		if(defIds != null && defIds != ''){
			var defIdArr = defIds.split(',');
			var ztree = $.fn.zTree.getZTreeObj('allOrg');
			var node=new Object();
			var defId = '';
			for(var i = 0; i < defIdArr.length; i++){
				defId = defIdArr[i];
				node= ztree.getNodeByParam('id',defId);
				//ztree.selectNode(node);
				nodeOnClick(null,'allOrg',node);
			}
			superEndDomBetter();
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
</body>
</html>
