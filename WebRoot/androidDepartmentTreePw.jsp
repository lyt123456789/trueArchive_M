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
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
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
<%-- <script src="${ctx}/widgets/zTree/assets/js/function.zTree.android.js?t=20180524" type="text/javascript" charset="utf-8"></script> --%>
<script src="${ctx}/widgets/zTree/assets/js/function.zTree.android.new.js?t=20180524" type="text/javascript" charset="utf-8"></script>
<!--移动端touch事件-->
<script src="${ctx}/widgets/zTree/assets/js/touch.min.js" type="text/javascript" charset="utf-8"></script>
<!--树状图原生样式表-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/zTreeStyle/zTreeStyle.css"/>
<!--原树状图通用样式类-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/common.css"/>
<!--新版树状图样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/OAzTree.css"/>

<style type="text/css">
/*reset*/
.tw-nav-item .tw-btn-secondary{
	padding: 1px 2px;
	margin-left: 9px;
    margin-bottom: 4px;
}
.tw-usertree-choice .tw-usertree-wrap {
	border: none;
}
.tw-usertree-tabs .tw-tabs-nav {
	height: 2rem;
	padding: 0;
	border-bottom: 1px solid #d0d0d0;
	background: #fff;
}
.tw-usertree-tabs .tw-nav-item {
	float: left;
	width: 25%;
	padding: 0 1.4rem;
	height: 2rem;
	line-height: 2rem;
	font-size: .8rem;
	text-align: center;
}
.tw-usertree-tabs .tw-nav-item a {
	display: block;
	width: 100%;
	height: 2rem;
	line-height: 2rem;
	color: #1a1a1a;
}
.tw-usertree-tabs .tw-nav-item.tw-active {
	background: none;
	border: none;
	
}
.tw-usertree-tabs .tw-nav-item.tw-active a {
	color: #248be8;
	border-bottom: 3px solid #248be8;
}
.tw-usertree-search {
	padding: .6rem 0;
	text-align: center;
}
.tw-usertree-tabs .tw-tab-panel {
	padding: 0;
}
.ztree {
	padding: 0;
}
.ztree *{
	font-size: .8rem;
}
.ztree li {
	/*padding: 6px 0 0 0;*/
	border-bottom: 1px solid #e4e4e4;
}
.ztree li .button.switch {
	float: right;
}
.ztree li span.button.switch {
	margin-top: .3rem;
	margin-right: .6rem;
}
.ztree li span.button.chk {
	margin-left: .4rem;
	margin-top: .1rem;
	margin-bottom: .1rem;
}
.ztree li .button.switch.noline_close {
	width: .8rem;
	height: 1.08rem;
	background: url(${ctx}/widgets/zTree/assets/img/larrow.png) no-repeat center center/100%;
}
.ztree li .button.switch.noline_open {
	width: 1.08rem;
	height: .8rem;
	background: url(${ctx}/widgets/zTree/assets/img/barrow.png) no-repeat center center/100%;
}
.icon-radio1  {
	display: inline-block;
	width: 20px;
	height: 20px;
	vertical-align: middle;
	margin-right: .1rem;
	margin-left: .4rem;
}
.ztree li span.button.chk.checkbox_false_part,
.ztree li span.button.chk.checkbox_false_part_focus,
.ztree li span.button.chk.checkbox_true_part {
	background: url(${ctx}/widgets/zTree/assets/img/ckbb.png) no-repeat center center/100%;
}
.ztree li span.button.chk.checkbox_false_full,
.ztree li span.button.chk.checkbox_false_full_focus,
.icon-radio1 {
	background: url(${ctx}/widgets/zTree/assets/img/ckb.png) no-repeat center center/100%;
}
.ztree li span.button.chk.checkbox_true_full,
.ztree li span.button.chk.checkbox_true_full_focus,
.tw-bottom-bar .on .icon-radio1 {
	background: url(${ctx}/widgets/zTree/assets/img/ckbh.png) no-repeat center center/100%;
}
.ztree li ul {
	padding: .3rem 0 .3rem 1rem;
}
.ztree li a,
.ztree li a.curSelectedNode {
	height: 30px;
	padding: 0;
	margin-top: .1rem;
}
.ztree > li {
	background: #f6f6f6;
	border-top: 1px solid #e4e4e4;
}
.ztree > li > ul {
	background: #fff;
	border-top: 1px solid #e4e4e4;
}
.ztree > li > a,
.ztree > li > a.curSelectedNode{
	padding: 0;
	color: #248be8;
}
.no-childs > ul > li {
	display: inline-block;
	float: left;
	/*width: 33%;*/
	border: none;
	min-width: 100px;
}
.no-childs > .level2.line {
	background: none;
}
.no-childs > ul:after {
	content: '';
	display: block;
	height: 0;
	clear: both;
}
.clearfix:after {
	content: '';
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}
.no-childs > ul > li .switch {
	visibility: hidden;
}
.slimScrollBar_new{
    height: 50px !important;
}
.tw-form-text {
	height: 28px;
}
.tw-bottom-bar {
	position: fixed;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 2rem;
	background: #f0f0f0;
	line-height: 2rem;
	font-size: .6rem;
}
.w-bottom-btn {
	float: right;
	width: 4rem;
	height: 100%;
	background: #028cef;
	text-align: center;
	line-height: 2rem;
	border: none;
	color: #fff;
	font-size: .6rem;
}
.tw-bottom-bar a {
	display: inline-block;
	color: #858585;
	line-height: 2rem;
	margin-right: .6rem;
}
.color-blue {
	color: #028cef;
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
</style>
<style>
.msg-switch {
    width: 56px;
    height: 22px;
    line-height: 20px;
    border-radius: 22px;
    vertical-align: middle;
    border: 1px solid #ccc;
    background-color: #ccc;
    position: relative;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    transition: all .2s ease-in-out;
}
.msg-switch:after {
    content: "";
    width: 18px;
    height: 18px;
    border-radius: 18px;
    background-color: #fff;
    position: absolute;
    left: 2px;
    top: 1px;
    cursor: pointer;
    transition: left .2s ease-in-out,width .2s ease-in-out;
}
.msg-switch-inner {
    color: #fff;
    font-size: 12px;
    position: absolute;
    left: 23px;
    top: 1px;
}
.msg-switch-checked {
    border-color: #2d8cf0;
    background-color: #2d8cf0;
}
.msg-switch-checked:after {
	left: 35px;
}
.msg-switch-checked .msg-switch-inner {
    left: 7px;
}
#sendMsg{
	position: absolute;
    right: 1%;
    top: 96.1%;
}
</style>


<script type="text/javascript">
var status = '${status}';
var siteId = '${siteId}';
var pleaseOrWatch = '${pleaseOrWatch}';
$(document).ready(function() {
	var url = '';
	if(status != null && status == 'all'){
		url = '${ctx}/widgets/zTree/ajaxData/getTreeGroup4All.json.json?t=' + new Date().getTime();
		if(pleaseOrWatch){
			url = '${ctx}/widgets/zTree/ajaxData/getTreeGroup4AllPw.json?t=' + new Date().getTime();
		}
	}else{
		url = '${ctx}/widgets/zTree/ajaxData/getTreeGroup4M.json?t=' + new Date().getTime();
	}
	//从接口获取TAB组信息
	$.ajax({
		//测试用本地json数据
		//url: './zTree/ajaxData/getTreeGroupDebug.json?t=' + new Date().getTime(),
		//接口数据
		url: url,
		dataType: "json",
		async:false,
		success: function(data) {
			if(data.length>0){
				//存储分组信息
				_globalObject.groupList = data;
				_globalObject.nodeCache = {};
				_globalObject.idList = {};
				_globalObject.nameList = {};
				//塞值代办
				_globalObject.waitTimer = null;
				_globalObject.waitTimerData = null;
				//生成tab标签组
				createTabsHtml(data);
				
				//缓冲加载防止页面卡死
				setTimeout(function(){
					laterLoad();
				}, 1);
				
				var laterLoad = function(){
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
					//获取每个tab标签下的节点json
					$.each(data, function(i,e){
						var _url = addUrlPara(e.apiUrl,'t', new Date().getTime());
						_url = addUrlPara(_url,'nodeId', $("#nodeId").val());
						_url = addUrlPara(_url,'type', $("#type").val());
						_url = addUrlPara(_url,'userId', $("#userId").val());
						_url = addUrlPara(_url,'siteId',siteId);
						_url = addUrlPara(_url,'isRole', e.isRole);
						$.ajax({
							url:_url,
							dataType: "json",
							async: false,
							success: function(chilData){

								if(chilData.length>0){
									//测试==>>重组了json//缓存起来
									_globalObject.nodeCache[e.domId] = reBuildJson(chilData,[]);
									//初始化人员对比表
									_globalObject.changeList[e.domId] = [];
									//所有id
									var temp = reBuildIdList(chilData);
									if(temp && temp.length>0){
										_globalObject.idList[e.domId] = temp;
									}
								}else{
									//alert(e.name+'人员数据拉取异常');
								}
							}
						})
					})
					//创建树结构
					$.each(data, function(i,e){
						var t = $("#"+e.domId);
						var setting = {
							check: {
								enable: true//开启多选框
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
								showIcon: false,
								showLine: false
							},
							callback: {
								onClick: nodeOnClick,//点击事件
								onCheck: nodeOnCheck,//checkbox事件
								beforeExpand:beforeExpand,
								beforeCheck:beforeCheck,
								//onExpand: nodeOnExpand
								//,beforeClick:beforeClick
							}
						};
						var zNodes = _globalObject.nodeCache[e.domId];
						treeObj = $.fn.zTree.init(t, setting, zNodes);
						var checkedNodes = treeObj.getCheckedNodes();
						for(var i=0;i<checkedNodes.length;i++){
							nodeOnCheck('', e.domId, checkedNodes[i])
						}
						treeObj.expandAll(true); 
						setNoChildStyle(e.domId)
						var nodes = treeObj.getNodes();
						if (nodes.length > 0) {
							$('#' + e.domId).parents('.tw-panel-cnt').next('.tw-bottom-bar').find('#checkAll').on('click', function() {
								var treeObj = $.fn.zTree.getZTreeObj(e.domId);
								var numVal = 0;
								var idList = [];
								for(var key in _globalObject.idList) {
									if(e.domId == key) {
										numVal = _globalObject.idList[key].length;
										idList = _globalObject.idList[key];
									}
								}
								var isChecked = $(this).hasClass('on');
								if(isChecked) {
									_globalObject.selectedList = [];
									$('#' + e.domId).parents('.tw-panel-cnt').next('.tw-bottom-bar').find('#num').text(0);
									$(this).removeClass('on')
									treeObj.checkAllNodes(false);
								} else {
									_globalObject.selectedList = idList;
									$('#' + e.domId).parents('.tw-panel-cnt').next('.tw-bottom-bar').find('#num').text(numVal);
									$(this).addClass('on')
									treeObj.checkAllNodes(true);
								}
							})
						}
					})
					
					

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
								if(t<500){
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
						//setTimeout(superEndDomBetter,1);
						//移动端塞值
						_globalObject.waitTimerItm = null;
						_globalObject.waitTimerItm = setInterval(function() {
							if(_globalObject.waitTimer==true){
								if($('.tw-tabs-bd').find('.tw-tab-panel').length>0){
									$.each(_globalObject.waitTimerData,function(i,e){
										_globalObject.selectedList.push(e);
									})
									$.each(_globalObject.groupList,function(i,e){
										if(_globalObject.idList[e.domId] && _globalObject.idList[e.domId].length>0){
											var cList = $.intersect(_globalObject.idList[e.domId], _globalObject.selectedList);//交集
											_globalObject.changeList[e.domId] = $.union(_globalObject.changeList[e.domId], cList);//去重合并
										}
									})
									var domId = $('.tw-tabs-bd').find('.tw-tab-panel:eq(0)').find('.ztree').attr('id');
									dataRelation(domId?domId:_globalObject.mainNodeId);
									//superEndDomBetter();
									setTimeout(superEndDomBetter,1);
									_globalObject.waitTimer=false;
									window.clearInterval(_globalObject.waitTimerItm);
								}
							}
						}, 50);
						$('.loading-container').hide();
						_globalObject.initScrollPos();
					}else{
						//PC塞值
						setValueOfPerson();
					}
				}
			}else{
				alert('分组信息接口异常');
			}
	    }
	});
	
	/*$('.tw-bottom-bar').append($('#sendMsg'));
	$('#sendMsg').show();
	$(".msg-switch").bind('click',function() {
		var Dom = document.getElementById("sendMsg");
		var isClass = Dom.getAttribute("class");
		var hiddenVal = document.querySelector("#msg_switch_input");
		if (isClass == "msg-switch") {
			Dom.setAttribute("class", "msg-switch msg-switch-checked")
			hiddenVal.value = true
		} else {
			Dom.setAttribute("class", "msg-switch")
			hiddenVal.value = false
		}
	});*/
	
});
</script>

</head>
<body > 
<div class="tw-usertree-choice" style="width:100%; background:none;">
    <input type="hidden" id="nodeId" value="${nodeId}"/>
    <input type="hidden" id="type" value="${type}"/>
    <input type="hidden" id="routType" value="${routType}" />
    <input type="hidden" id="userId" value="${userId}" />
    <input type="hidden" id="status" value="${status}" />
	<div class="tw-usertree-wrap" style="margin:0 auto; width:100%; background: #fff;padding:0;">
		<div class="tw-usertree-tabs">
			<ul class="tw-tabs-nav"></ul>
	        <div class="tw-tabs-bd"></div>
			
			<div class="loading-container">
			</div>
		</div>
	</div>
	<div class="tw-usertree-select" style="display:none;">
		<div class="tw-mod">
			<div class="tw-hd">已经选择<span>(<b id="countNum">0</b>)</span><span class="fr tw-act" onclick="delAll()">清除全部</span></div>
			<div class="tw-bd">
				<ul class="tw-usertree-list"></ul>
				<ul class="tw-usertree-deplist" style="display:none"></ul>
			</div>
				<select id="oldSelect" size="20" style="display:none; width:100%;height: 290px;border: 1px dashed #C2C2C2" multiple="multiple">
 				<%--	<c:forEach var="m" items="${mapList}">
						<option value="${m.employee_id }|${m.employee_name }|${m.employee_shortdn }">${m.employee_name} { ${m.employee_shortdn} }</option>
					</c:forEach> --%>
				</select>
		</div>
	</div>
</div>
<div class="tw-search-bar tar" style="display:none">
        <div class="tw-top-tool">
            <div class="tw-btn-primary" style="cursor:pointer" onclick="save();">
                <i class="tw-icon-save"></i> 确认选择
            </div>
            <div class="tw-btn" style="cursor:pointer" onclick="window.close();">
                <i class="tw-icon-remove"></i> 取消选择
            </div>
        </div>
</div>
<!--<div class="tw-bottom-bar clearfix">
    <a class="" id="checkAll"><i class="icon-radio1"></i><span>全选</span></a>
	<span class="people-nums">已选择<span class="color-blue">22</span>人</span>
	<button class="w-bottom-btn">确认</button>
</div>-->
	<div class="msg-switch"  id="sendMsg">
		<input type="hidden" value="false" id="msg_switch_input">
		<span class="msg-switch-inner">
			<span>短信</span>
		</span>
	</div>


<script type="text/javascript">
	function setValueOfPerson(ids, names){
		//var oldSelect = document.getElementById('oldSelect');
		//alert(ids);
		//ids = '{BFA8003A-0000-0000-58CB-EDE100000005},{BFA8003A-0000-0000-58CF-8BF700000011},{BFA8003A-0000-0000-598C-730E0000006E},{BFA80077-0000-0000-0AA0-12E500000005},{BFA80077-0000-0000-0870-6D6000000002},{BFA80077-0000-0000-4E79-AF8E00000003},{BFA8003A-0000-0000-58D2-438100000013},{BFA8003A-0000-0000-58D3-E90800000019},{BFA8003A-FFFF-FFFF-F332-456600000014},{BFA8003A-0000-0000-58D3-9C2000000018},{BFA80077-0000-0000-3A81-3C5C00000002},{BFA80077-0000-0000-0A9E-9E4200000004},{BFA8003A-0000-0000-58D1-A6F800000015},{BFA8003A-FFFF-FFFF-F330-75FF00000011},{BFA8003A-0000-0000-58D1-FA7D00000016},{BFA80077-0000-0000-315E-DBD200000001},{BFA8003A-0000-0000-58E6-2D5800000047},{BFA80077-0000-0000-71A0-3B6400000001},{BFA8003A-0000-0000-58E4-AA4700000045},{BFA8003A-0000-0000-5985-8FB10000006D},{BFA8003A-FFFF-FFFF-F187-104600000002},{BFA80077-FFFF-FFFF-9818-A21000000004},{BFA8003A-0000-0000-5998-F927FFFFFF8C},{BFA80077-FFFF-FFFF-A68D-D50B0000000A},{BFA8003A-0000-0000-58E4-D8E800000046},{BFA8003A-0000-0000-58E4-2BF400000043},{BFA80077-FFFF-FFFF-95AA-36A300000003},{BFA8003A-0000-0000-58FD-41F30000005E},{BFA8003A-0000-0000-59A2-5698FFFFFF9F},{BFA8003A-0000-0000-58FD-6D4F0000005F},{BFA8003A-FFFF-FFFF-F33C-EB2F00000023},{BFA8003A-0000-0000-58E3-E2E000000044},{BFA8003A-FFFF-FFFF-F315-1ED900000008},{BFA8003A-0000-0000-58DE-D5D800000039},{BFA8003A-0000-0000-58E1-75DC0000003D},{BFA8003A-FFFF-FFFF-F32F-0B430000000F},{BFA8003A-FFFF-FFFF-F32E-445F0000000E},{BFA8003A-0000-0000-58DE-818500000037},{BFA8003A-0000-0000-599A-A17EFFFFFF91},{BFA80077-0000-0000-78EA-CA7E00000012},{BFA8003A-0000-0000-58DF-0E0400000036},{BFA8003A-0000-0000-58DD-E7C500000035},{BFA8003A-0000-0000-58D9-DC5F0000002A},{BFA8003A-0000-0000-58FE-C6D900000067},{BFA8003A-FFFF-FFFF-F30E-E82200000005},{BFA8003A-FFFF-FFFF-F32E-986600000010},{BFA8003A-FFFF-FFFF-F311-A56400000007},{BFA80077-FFFF-FFFF-9AD0-D31C00000005},{BFA8003A-0000-0000-58E1-9F1A00000040},{BFA8003A-0000-0000-58DD-9F2D00000034},{BFA8003A-0000-0000-5993-5EF7FFFFFF83},{BFA8003A-FFFF-FFFF-F310-FFCD00000006},{BFA80077-0000-0000-33D2-D10000000002},{BFA8003A-0000-0000-58FE-FDD700000068},{BFA80077-0000-0000-78EA-FD5600000013},{BFA8003A-0000-0000-58DA-B8130000002C},{BFA8003A-0000-0000-58E3-0B9600000042},{BFA8003A-0000-0000-58DB-8BC40000002E},{BFA8003A-0000-0000-58DA-EBBB0000002D},{BFA8003A-0000-0000-599C-8FF9FFFFFF95},{BFA8003A-0000-0000-58E0-D6B00000003E},{BFA8003A-FFFF-FFFF-F31A-EB350000000A},{BFA8003A-0000-0000-58E2-49F50000003F},{BFA8003A-0000-0000-58EA-ED8300000055},{BFA8003A-0000-0000-58D9-609C00000025},{BFA8003A-0000-0000-58D7-CC9900000023},{BFA8003A-FFFF-FFFF-F1F0-5CA800000004},{BFA8003A-FFFF-FFFF-F1EE-A02800000003},{BFA8003A-0000-0000-58D8-8E8200000026},{BFA8003A-0000-0000-58D9-054D00000024},{BFA8003A-0000-0000-58D8-59F400000022},{BFA80077-FFFF-FFFF-C14E-D3B700000003},{BFA80077-FFFF-FFFF-940F-D39B00000001},{BFA8003A-FFFF-FFFF-F33A-7B630000001E},{BFA80077-0000-0000-1690-512600000005},{BFA80077-FFFF-FFFF-A68C-83F200000007},{BFA8003A-0000-0000-58EA-87FC00000054},{BFA8003A-FFFF-FFFF-F340-710000000027},{BFA8003A-FFFF-FFFF-F34B-39FC0000003B},{BFA80077-0000-0000-122D-25800000000A},{BFA8003A-FFFF-FFFF-F344-B52500000031},{BFA8003A-FFFF-FFFF-F336-2B1E00000018},{BFA8003A-0000-0000-59A5-DEA1FFFFFFA3},{BFA8003A-FFFF-FFFF-F334-3EFD00000015},{BFA8003A-FFFF-FFFF-F340-B9000000002A},{BFA8003A-0000-0000-59EA-0537FFFFFFA4},{BFA8003A-0000-0000-58D9-DC5F0000002A},{BFA80077-0000-0000-4BBF-11FD00000003},{BFA80077-0000-0000-16EB-6BBE00000006},{BFA80077-0000-0000-12AE-29B60000000C},{BFA8003A-0000-0000-58FD-D5D200000064},{BFA8003A-0000-0000-5A00-4E6E0000006A},{BFA80077-0000-0000-122B-F3B200000009},{BFA8003A-FFFF-FFFF-F335-BF9400000019},{BFA80077-FFFF-FFFF-F3FB-F9A200000007},{BFA80077-FFFF-FFFF-A68C-F62400000008},{BFA8003A-0000-0000-5999-FE33FFFFFF8F},{BFA80077-FFFF-FFFF-E459-C35100000005},{BFA8003A-FFFF-FFFF-F338-19B50000001B},{BFA8003A-0000-0000-58DB-09160000002B}';
		if(ids && ids!=='' && ids.length>1){
			var id = ids.split(",");
			if(id && id.length>0){
				//alert(id);
				_globalObject.waitTimer = true;
				_globalObject.waitTimerData = id;
			}
		}   
	}
	
	function getValue(){
		//var oldSelect = document.getElementById('oldSelect');
		var persons = new Array();
		/*$(".tw-usertree-list li").each(function(i,e){
			var person = {};
			if($(this).attr("data-id")){
				person.id = $(this).attr("data-id");
				person.name = $(this).find("span").text();
				persons.push(person);
			}
		});*/
		$.each(_globalObject.selectedList,function(i,e){
			if(e && e!==null && e!==''){
				persons.push({id:e,name:_globalObject.nameList[e]});
			}
		});
		//alert(JSON.stringify(persons));
		return JSON.stringify(persons);
	}
	
	function cdv_getvalues(){
		var ids = "";
		var names = "";
		if("${routType}"=="2"){
			$.each(_globalObject.selectedList,function(i,e){
				if(i==0){
					ids += e+";";
					names += _globalObject.nameList[e]+";";
				}else{
					ids += e+",";
					names += _globalObject.nameList[e]+"、";
				}
			});
			ids = ids.substring(0,ids.length-1);
		}else if("${routType}"=="0"){
			if(_globalObject.selectedList.length>1){
				ids = "10001";
				return ids;
			}else{
				$.each(_globalObject.selectedList,function(i,e){
					ids = e;
					names = _globalObject.nameList[e];
				});
			}
		}else{
			$.each(_globalObject.selectedList,function(i,e){
				if(e && e!==null && e!==''){
					ids += e+",";
					names += _globalObject.nameList[e]+"、";
				}
			});
			ids = ids.substring(0,ids.length-1);
			names = names.substring(0,names.length-1);
		}
		var isSendMsg=document.querySelector("#msg_switch_input").value;
		if(isSendMsg=='true'){
			ids+="#1";
		}else{
			ids+="#0";
		}
		ids+="||请"+names;
		return ids;
	}
	
	var Dom = document.querySelector(".msg-switch");
	Dom.onclick = function() {
		var isClass = Dom.getAttribute("class");
		var hiddenVal = document.querySelector("#msg_switch_input");
		if (isClass == "msg-switch") {
			Dom.setAttribute("class", "msg-switch msg-switch-checked")
			hiddenVal.value = true
		} else {
			Dom.setAttribute("class", "msg-switch")
			hiddenVal.value = false
		}
	}
</script>
</body>
</html>
