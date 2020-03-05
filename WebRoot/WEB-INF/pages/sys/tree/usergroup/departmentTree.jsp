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
<%@ include file="/common/headerbase.jsp"%>
<!--表单样式-->
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<!--  告诉浏览器，页面用的是UTF-8编码 -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<script src="${ctx}/zTree/assets/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/zTree/assets/js/jquery.arrayUtilities.min.js" type="text/javascript" charset="utf-8"></script>
<!--原人员树通用库-->
<script src="${ctx}/zTree/assets/js/common-ui.js" type="text/javascript" charset="utf-8"></script>
<!--滚动条-->
<script src="${ctx}/zTree/assets/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<!--树状图核心库-->
<script src="${ctx}/zTree/assets/js/zTree/jquery.ztree.core.min.js" type="text/javascript" charset="utf-8"></script>
<!--多选框拓展-->
<script src="${ctx}/zTree/assets/js/zTree/jquery.ztree.excheck.min.js" type="text/javascript" charset="utf-8"></script>
<!--隐藏拓展-->
<script src="${ctx}/zTree/assets/js/zTree/jquery.ztree.exhide.min.js" type="text/javascript" charset="utf-8"></script>
<!--拖动编辑-->
<script src="${ctx}/zTree/assets/js/zTree/jquery.ztree.exedit.min.js" type="text/javascript" charset="utf-8"></script>
<!--页面独立树状图生成及操作函数-->
<script src="${ctx}/zTree/assets/js/function.zTree.js?t=20190514" type="text/javascript" charset="utf-8"></script>
<!--移动端touch事件-->
<script src="${ctx}/zTree/assets/js/touch.min.js" type="text/javascript" charset="utf-8"></script>
<!--树状图原生样式表-->
<link rel="stylesheet" type="text/css" href="${ctx}/zTree/assets/css/zTreeStyle/zTreeStyle.css"/>
<!--原树状图通用样式类-->
<link rel="stylesheet" type="text/css" href="${ctx}/zTree/assets/css/common.css"/>
<!--新版树状图样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/zTree/assets/css/OAzTree.css"/>

<style>

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
	background: url(${ctx}/widgets/ztree/img/rarr.png) no-repeat center center;
}
.ztree li .button.switch.noline_open {
	width: 12px;
	height: 12px;
	margin-left: 10px;
	margin-right: 5px;
	background: url(${ctx}/widgets/ztree/img/barr.png) no-repeat center center;
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
.ztree li.no-childs span.node_name {
	overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 110px;
}
.tw-usertree-list li span {
	width: 80px;
	overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.tw-panel-cnt,
.JS_wrap {
/* 	min-height:400px; */
}
.JS_scrollt {
/* 	min-height:357px; */
}
.tw-usertree-tabs .tw-tabs-nav {
	height: 46px;
}
.tw-usertree-tabs .tw-nav-item {
	height: 37px;
	line-height: 37px;
}
.tw-usertree-choice .tw-usertree-search {
	padding: 10px;
    margin: 0;
}
.tw-usertree-choice .tw-usertree-select .tw-mod {
	padding: 0;
}
.tw-usertree-choice .tw-usertree-select .tw-mod .tw-bd {
	padding-top: 16px;
}
.tw-btn-primary {
	margin: 0;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	_isfgw=$("#isfgw").val();
	_idsAdmin=$("#idsAdmin").val();
	var isXxzx=$("#isXxzx").val();
	var url="";
	//下列判断顺序勿动
	if(_isfgw==1){
		url='${ctx}/zTree/ajaxData/getTreeGroupForFgw.json?t=' + new Date().getTime();
	}else{
		url='${ctx}/zTree/ajaxData/getTreeGroup.json?t=' + new Date().getTime();
	}
	
	if(_idsAdmin==1){
		url='${ctx}/zTree/ajaxData/getTreeGroup4Super.json?t=' + new Date().getTime();
	}
	
	if(isXxzx=="1"){
		url='${ctx}/zTree/ajaxData/getTreeGroupForXxzx.json?t=' + new Date().getTime();
	}else if(isXxzx=="2"){
		url='${ctx}/zTree/ajaxData/getTreeGroup.json?t=' + new Date().getTime();
	}else{
		url='${ctx}/zTree/ajaxData/getTreeGroup.json?t=' + new Date().getTime();
	}
	
	
	//从接口获取TAB组信息
	$.ajax({
		//测试用本地json数据
		//url: './zTree/tongzhou/getTreeGroupDebug.json?t=' + new Date().getTime(),
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
				//生成tab标签组
				createTabsHtml(data);
				//缓冲加载防止页面卡死
				setTimeout(function(){
					laterLoad();
				}, 1);
				var mc=$('#mc').val();
				var laterLoad = function(){
					//获取每个tab标签下的节点json
					$.each(data, function(i,e){
						var url=addUrlPara(e.apiUrl,'t', new Date().getTime());//加上随机参数
// 						url=addUrlPara(url,'mc', mc);//加上查询条件
						$.ajax({
							url:url,
							dataType: "json",
							async: false,
							success: function(chilData){
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
								showIcon: false,
								showLine: false
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
						treeObj = $.fn.zTree.init(t, setting, zNodes);
						
						var checkedNodes = treeObj.getCheckedNodes();
						for(var i=0;i<checkedNodes.length;i++){
							nodeOnCheck('', e.domId, checkedNodes[i])
						}
						treeObj.expandAll(true);
						setNoChildStyle(e.domId);
						
						//console.log(treeNodesAll);
						/*if(!$.browser.msie) {
							//非IE内核可以修正chkDisabled属性，IE下运算太卡最好还是从json源修改
							var zTree = $.fn.zTree.getZTreeObj(e.domId);
							var treeNodes = zTree.getNodesByFilter(function(node){
								var id = node.id;
								var ns = zTree.getNodesByFilter(function(nodes){
									return (nodes.pId == id);
								}, false);
								//能选中并且是folder的并且不存在子元素
								return (node.chkDisabled == false) && (node.type=="folder") && (ns.length<1);
							}, false);
							$.each(treeNodes, function(i,e) {
								zTree.setChkDisabled(e,true);
							});
						}*/
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
					 	var timeeach = function(){
							//console.log(_globalObject.receiveArr);
							if(_globalObject.receiveArr){ 
								setValueOfPerson(_globalObject.receiveArr);
			 					//_globalObject.receiveArr = 'end';
								//timeeach();
							}else{
								timeeach();
							}
						};
						timeeach(); 
						
						
					}
				}
			}else{
				alert('分组信息接口异常');
			}
	    }
	});
});
</script>
</head>
<body >
<div class="tw-usertree-choice clearfix" style="height: 100%;background-position: 70% 50%;" >
    <input id="isfgw" type="hidden" value="${isfgw }">
    <input id="idsAdmin" type="hidden" value="${idsAdmin }">
	 <input id="isXxzx" type="hidden" value="${isXxzx }">
	<div class="tw-usertree-wrap" style="width: 65%;background: #fff;padding:0;">
		<div class="tw-usertree-tabs" style="position:relative">
			<ul class="tw-tabs-nav">
	            
	        </ul>
			<div class="tw-usertree-search">
			</div>
	        <div class="tw-tabs-bd">
	            
	        </div>
		</div>
	</div>
	<div class="tw-usertree-select" style="width: 25%;">
		<div class="tw-mod" >
			<div class="tw-hd">已经选择 <span>(<b id="countNum">0</b>)</span><span class="fr tw-act" onclick="delAll()">清除全部</span></div>
			<div class="tw-bd">
				<div class="JS_scroll">
					<ul class="tw-usertree-list JS_hover active JS_treeulist JS_scrollt">
					</ul>
					<ul class="tw-usertree-deplist" style="display:none">
					</ul>
				</div>
			</div>
			<select id="oldSelect" size="20" style="display:none; width:100%;height: 310px;border: 1px dashed #C2C2C2" multiple="multiple">
<!-- 				<c:forEach var="m" items="${mapList}">
					<option value="${m.employee_id }|${m.employee_name }|${m.employee_shortdn }">${m.employee_name} { ${m.employee_shortdn} }</option>
				</c:forEach> -->
			</select>
			<select id="depSelect" size="20" style="display:none; " multiple="multiple">
			</select>
		</div>
	</div>
	
</div>
<!-- <div class="tw-search-bar tar">
	<div class="tw-top-tool">
		<div class="tw-btn-primary" style="cursor:pointer" onclick="save();">
			<i class="tw-icon-save"></i> 确认选择
		</div>
		<div class="tw-btn" style="cursor:pointer" onclick="window.close();">
			<i class="tw-icon-remove"></i> 取消选择
		</div>
	</div>
</div> -->

<script type="text/javascript">
	function setValueOfPerson(value){
		if(value){
			var domId = $('.tw-tabs-bd').find('.tw-tab-panel:eq(0)').find('.ztree').attr('id');
			//循环遍历人员下拉框
			var arrL = [];
			var _html = '';
			for(var i=0;i<value.length;i++){
				_html += '<li data-id='+ value[i].id +'><span>'+ value[i].name +'</span> <i class="tw-usertree-del tw-icon-times-circle" onclick="deltree(this)"></i></li>';
				// oldSelect.options.add(new Option(value[i].name,value[i].id));
				//addIdCheck(value[i].id);
				arrL.push(value[i].id);
			};
			console.log(_html)
			$(".tw-usertree-list").empty().append(_html);
			_globalObject.selectedList = $.union(_globalObject.selectedList, arrL)
			addChangeAll(arrL);
			//加载
			setTimeout(function(){
				dataRelation(domId?domId:_globalObject.mainNodeId);
			},1);
		};
	}
	
	function editGroup(){
		var visibleWidth = document.body.clientWidth;
	    var visibleHeight = document.body.clientHeight;
		layer.open({
	        title: '设置常用人员组',
	        area: [visibleWidth+'px', visibleHeight+'px'],
	        type: 2,
	        maxmin:true,
	        scrollbar:false,
	        content: '${ctx}/departmentTree_toSetUserGroup.do?t='+new Date(),
	        success:function(layero,index){//页面加载完成后，将本页面中已经选中的人员带入到人员树页面中去
	        },
	        btn:['完成','取消'],
	        yes:function(index,layero){
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
	        	console.log(layero.find('iframe'));
	           	value = iframeWin.saveUsers();
	        	window.location.reload();
	        },
	        btn2:function(index,layero){
	        	layer.close(index);
	        },
			cancel:function(){
			}        
		});
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
	
	//供父页面调用，获取已选择的人员的json;
	function getJsonOfTheSelectedPerson(){
		var value = new Array();
		$(".tw-usertree-list li").each(function(i,e){
			var person = {};
			person.name = $(this).find("span").text();
    		person.id = $(this).attr("data-id");
    		value.push(person);
		});
		//再此处可以手动批量维护
		/* var sql='select wm_concat(xm||';'||gmsfhm) from (select * from  (select xm,gmsfhm,rownum as rn from t_yw_jy_jyxx where sjqx='3')  where rn >400 and rn <475)'
		var sss = '杭艺秋;320601199502030352,陆灿华;320683199409055314,张帅;32068319941119003X,朱敏生;320622196701224238,吴倩;320682199505020203,孔钰璋;320602199205011012,李晓新;320682198501243894,殷海坤;320921199212235036,石锐;320682199412235774,侯文远;610324198310090513,王俊杰;320625197908085910,刘吉;320682199201048274,乔栋;321283199212156217,陈鑫鑫;32068219921224545X,马旌;32068219950715001X,徐远程;320682199601051739,朱林森;320682199410195975,陈立;320682199510106773,李嘉豪;320682199512123753,李明豫;320682199510234337,朱斌;320682199410094074,章鑫;320682199211118950,申旸;320682199510196270,郭峰;320682199307240491,贲淼;320682199405100011,刘小伟;321083198210021073,马徐杨;320623199405147653,任斌;622426198410060014,张涛;320382199006125215,贺俊文;320586199408138015,何国杨;330621199104137135,张辰;320581198808110939,孙海祥;320682199108268635,孙允栋;37092319831212391X,吴佳屹;321081199409173615,吴建峰;320683198609256458,黄建生;320626195911300017,陈曦;320626198111010038,伍志勇;43052819900704735X,彭从祥;430521198306205216,孙彦磊;320125199102170712,陆健;320681198706043435,马越;32128219951231003X,朱晶;320682198310183438,苏小君;420881198909281710,陈俊平;350301198706101153,侯波;34112419890726383X,廖昌国;421023198405157517,陈冀思博;330903199206030914,朱孙;320525198711125017,骆昌龙;320821198910201319,袁帅;320684199509056679,王欢欢;320683198605234315,徐宏滨;320623198610070451,范钦浩;321282198902260010,许季;320623199307137179,周子敬;320981199607094473,王磊;32062119950112351X,陈俊;320602199612240517,杨嘉豪;320623199506126819,施胜尧;320602199603282010,张杨阳;320623199609090011,潘阳杨;320623199601296453,缪叔涛;320623199503174217,黄泰龙;32068419961109441X,王锡坡;32062319960518861X,董春昕;320684199603120016,周亚斌;320682199604245774,陶孙凡;320682199607191732,张刘洋;320682199601261736,朱津;320681199306303634,陈栋;320684198407045915,李文卿;32120219931222061X,仇嘉诚;320684199511250016';
		var arr = sss.split(',');
		for(var i=0;i<arr.length;i++){
			var person = {};
			person.name = arr[i].split(';')[0];
			person.id = arr[i].split(';')[1];
			value.push(person);
		} */
		
		return value;
	}
	
	//供父页面调用，为人员树设置默认选中的人员
	function setTheSelectedPersonToTree(value){
		if(value){
			_globalObject.receiveArr = value;
		};
	}
</script>
</body>
</html>
