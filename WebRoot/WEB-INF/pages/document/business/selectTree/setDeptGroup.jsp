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
<base target="_self">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<!--  告诉浏览器，页面用的是UTF-8编码 -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<script src="${ctx}/widgets/zTree/assets/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
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
<!--页面独立树状图生成及操作函数-->
<script src="${ctx}/widgets/zTree/assets/js/function.zTree4rd.js" type="text/javascript" charset="utf-8"></script>
<!--移动端touch事件-->
<script src="${ctx}/widgets/zTree/assets/js/touch.min.js" type="text/javascript" charset="utf-8"></script>
<!--树状图原生样式表-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/zTreeStyle/zTreeStyle.css"/>
<!--原树状图通用样式类-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/common.css"/>
<!--新版树状图样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/zTree/assets/css/OAzTree.css"/>

<style type="text/css">
.tw-usertree-list-style {
	height: 100%;
	width: 45%;
	margin-left: 2%;
	float: left;
	border: 1px solid #bdbdbd;
}

.tw-usertree-list-style .bd,.tw-usertree-select .tw-bd {
  padding:10px;
}

/*reset*/
.tw-usertree-list{
	border:0 !important;
	width:100%;
}
.tw-usertree-group .tw-bd ul li a {
	font-size:14px;
	color:#000;
}	
.tw-usertree-group .tw-bd {
	overflow-y:auto;
	/*height:422px;*/
	/*padding:0px 10px;*/
}
.tw-usertree-group .tw-bd ul li a {
	display:inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	max-width: 92%;
	_width: 92%;  
	white-space: nowrap;
    height: 30px;
    line-height: 30px;
}   
.tw-usertree-group .tw-bd ul li {
	position:relative;
	overflow:hidden;
}    
.tw-usertree-group .tw-usertree-del {
	position:absolute;
	right:5px;
	top:0px;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	//从接口获取TAB组信息
	$.ajax({
		//测试用本地json数据
		//url: './zTree/ajaxData/getTreeGroupDebug.json?t=' + new Date().getTime(),
		//接口数据
		url: './widgets/zTree/njgc/getTreeGroupSet.json?t=' + new Date().getTime(),
		dataType: "json",
		async:false,
		success: function(data) {
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
					//获取每个tab标签下的节点json
					$.each(data, function(i,e){
						var _url = addUrlPara(e.apiUrl,'t', new Date().getTime());
						$.ajax({
							url:_url,//加上随机参数
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
								selectedMulti: false
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
	<body>
    <div class="tw-usertree-wrap clearfix" style="/*height: 468px;*/">
    <input type="hidden" id="nodeId" value="${nodeId}"/>
    <input type="hidden" id="type" value="${type}"/>
	    <div class="tw-usertree-left">
		    <div class="tw-usertree-group">
			    <div class="tw-hd">
				    <div class="tw-title">联系组</div>
					<div class="tw-more">
					    <i class="tw-icon-pencil" onclick="modifyGName()"></i>
					    <i class="tw-icon-plus-circle" onclick="addCommonGroup()"></i>
	                    <div class="bd" style="display: none;padding:10px" id="newGroupNameDiv">
						    <div class="tw-usertree-search">
							    名称：<input type="text" class="tw-form-text" id="newGroupName">
							</div>
						</div>
					</div>
				</div>
				<div class="tw-bd">
					<div class="JS_scroll">
						<ul>
						<c:forEach var="item" items="${cgs}" varStatus="status">
							<li>
								<a style="cursor:pointer" onclick="" item-id-data="${item.id}">${item.name}</a>
								<i class="tw-usertree-del tw-icon-trash" onclick="deleteGroup('${item.id}');"></i>
							</li>
						</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	    <div class="tw-usertree-right">
		    <div class="tw-usertree-box clearfix">
				<div class="tw-usertree-list-style">
				    <div class="hd">选择联系人</div>
                    <div class="bd" style="padding:0">
						<div class="tw-usertree-tabs">
							<ul class="tw-tabs-nav" style="height: 0;overflow: hidden;padding: 0;border: none;"></ul>
							<div class="tw-tabs-bd">
								
							</div>
						</div>
					</div>					
				</div>
				<div class="tw-usertree-select">
				    <div class="tw-mod">
					    <div class="tw-hd">已经添加到该组的部门<span>(<b id="countNum">0</b>)</span><span class="fr" onclick="delAll()">全部移除</span></div>
						<div class="tw-bd">
							<div class="JS_scroll">
								<ul class="tw-usertree-list JS_hover active JS_treeulist">
								</ul>
								<ul class="tw-usertree-deplist" style="display:none">
								</ul>
							</div>
						</div>
						<select id="oldSelect" size="20" style="display:none; width:100%;height: 290px;border: 1px dashed #C2C2C2" multiple="multiple">
<%-- 							<c:forEach var="m" items="${mapList}">
								<option value="${m.employee_id }|${m.employee_name }|${m.employee_shortdn }">${m.employee_name} { ${m.employee_shortdn} }</option>
							</c:forEach> --%>
						</select>
						<select id="depSelect" size="20" style="display:none; " multiple="multiple">
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="tw-search-bar tar">
	    <div class="tw-top-tool">
		     <div class="tw-btn-primary" style="cursor:pointer" onclick="saveUsers();">
                <i class="tw-icon-save"></i> 保存
            </div>
            <div class="tw-btn" style="cursor:pointer" onclick="go_link(document.referrer);">
                <i class="tw-icon-remove"></i> 返回
            </div>
		</div>
	</div>
	<script type="text/javascript"> 
	var groupId;
	//设置点击效果
	function bindHtmlEvent(){
		$(".tw-usertree-group .tw-bd ul li").off('hover');
		$(".tw-usertree-group .tw-bd ul li").hover(function() {
			$(".tw-usertree-group .tw-bd ul li").removeClass("gray");    	
			$(this).addClass("gray");
			$(this).find(".tw-usertree-del").show();
	   	},function(){
	   		$(this).removeClass("gray");
	   		$(this).find(".tw-usertree-del").hide();
	   	});
		$(".tw-usertree-group .tw-bd ul li").off('click');
	   	$(".tw-usertree-group .tw-bd ul li").on('click', function() { 
	    	$(".tw-usertree-group .tw-bd ul li").removeClass("active");
	      	$(".tw-usertree-group .tw-usertree-del").hide();
	      	$(this).addClass("active");
	      	$(this).find(".tw-usertree-del").show();
	      	groupId = $(this).find("a").attr('item-id-data');
	      	showUsers($(this).find("a"));
		});
	}
	$(document).ready(function() {
		bindHtmlEvent();
	});

	function deleteGroup(gid){
		layer.confirm("确定要删除吗？",function(){
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        data:{"gid":gid},
		        url:"${ctx}/ztree_deleteUserGroup.do",
		        success:function(text){
					$('.tw-usertree-group a[item-id-data='+gid+']').parent('li').remove();
					delAll();
					dataRelation();
		        	layer.msg("删除成功",{time:1000,icon:1},function(){
						//window.location.reload();
		        	});
		        }
		    });
		});
	}
	
	function modifyGName(){
		if(groupId){
			layer.open({
				title:"修改常用组名",
				type:1,
				area:['250px','140px'],
				content:$('#newGroupNameDiv'),
				btn:['确认','取消'],
				yes:function(index,layero){
					var name = $.trim(layero.find('#newGroupName').val());
					if(name!=''){
						$.ajax({
							async:true,//是否异步
							type:"POST",//请求类型post\get
							cache:false,//是否使用缓存
							dataType:"text",//返回值类型
							data:{"groupId":groupId,"name":name,"deptFlag":"1","userId":"${userId}"},
							url:"${ctx}/ztree_modifyUserGroup.do",
							success:function(text){
								if(text=='ok'){
									layer.close(index);
									$('.tw-usertree-group a[item-id-data='+groupId+']').text(name);
									layer.msg("修改成功",{time:1000,icon:1},function(){
										//window.location.reload();
									});
								}else{
									layer.msg("请输入名称",{time:1000,icon:0});
								}
							}
						});
					}else{
						layer.msg("名称只能输入非空字符",{time:1000,icon:0});
					}
				}
			});
		}else{
			layer.msg("请选择一个组",{time:1000,icon:0});
		}
	}
	 
	function addCommonGroup(){
		layer.open({
			title:"新增常用组",
			type:1,
			area:['250px','140px'],
			content:$('#newGroupNameDiv'),
			btn:['确认','取消'],
			yes:function(index,layero){
				var name = $.trim(layero.find('#newGroupName').val());
				if(name!=''){
					$.ajax({
						async:true,//是否异步
						type:"POST",//请求类型post\get
						cache:false,//是否使用缓存
						dataType:"text",//返回值类型
						data:{"name":name,"deptFlag":"1","userId":"${userId}"},
						url:"${ctx}/ztree_addUserGroup.do",
						success:function(text){
							if(text!==''||text!==null||!text){
								var id = text;
								layer.close(index);
	   							$(".tw-usertree-group .tw-bd ul").append('<li><a style="cursor:pointer" item-id-data="'+id+'">'+name+'</a>'+
									'<i class="tw-usertree-del tw-icon-trash" onclick="deleteGroup(\''+id+'\');" style="display: none;"></i></li>');
								bindHtmlEvent();
								layer.msg("新增成功",{time:1000,icon:1},function(){
									//window.location.reload();
								});
							}else{
								layer.msg("请输入名称",{time:1000,icon:0});
							}
						}
					});
				}else{
					layer.msg("名称只能输入非空字符",{time:1000,icon:0});
				}
			}
		});
	}
	
	function showUsers(o){
		//var oldSelect = document.getElementById('oldSelect');
		delAll();
		$.ajax({
			async:true,//是否异步
			type:"POST",//请求类型post\get
			cache:false,//是否使用缓存
			dataType:"text",//返回值类型
			data:{"groupId":$(o).attr('item-id-data')},
			url:"${ctx}/ztree_getGroupUsers.do",
			success:function(text){
				if(text){
					var ret = eval("("+text+")");
					var domId = $('.tw-tabs-bd').find('.tw-tab-panel:eq(0)').find('.ztree').attr('id');
					for(var i=0,l=ret.length;i<l;i++){
						//var _html = '<li data-id='+ ret[i].empId +'><span>'+ ret[i].empName +'</span> <i class="tw-usertree-del //tw-icon-times-circle" onclick="deltree(this,0)"></i></li>';
						//$(".tw-usertree-list").append(_html);
						//oldSelect.options.add(new Option(ret[i].empName,ret[i].empId));
						addIdCheck(ret[i].empId);
						_globalObject.changeList[domId?domId:_globalObject.mainNodeId] = $.union(_globalObject.changeList[domId?domId:_globalObject.mainNodeId], [ret[i].empId])
					}
					//加载
					dataRelation(domId?domId:_globalObject.mainNodeId);
					setTimeout(superEndDomBetter,1);
				}
			}
		});
	}
	
	function saveUsers(){
		if(groupId){
			//var oldSelect = document.getElementById('oldSelect');
			var $li = $(".tw-usertree-list li");
			if($li.length<=0){
				layer.msg("请为组选择内容",{time:1000,icon:0});
			}else{
				var persons = [];
				for(var i = 0 ; i < $li.length; i++){
					var person = {};
					person.id = $li.eq(i).attr("data-id");
					person.name = $li.eq(i).find("span").text();
					persons.push(person);
				}
				$.ajax({
					async:true,//是否异步
					type:"POST",//请求类型post\get
					cache:false,//是否使用缓存
					dataType:"text",//返回值类型
					data:{"persons":JSON.stringify(persons),"groupId":groupId,"userId":"${userId}"},
					url:"${ctx}/ztree_addGroupUsers.do",
					success:function(text){
						if(text=='ok'){
							layer.msg("更新组成功",{time:1000,icon:1});
						}else{
							layer.msg("更新组失败",{time:1000,icon:0});
						}
					}
				});
			}
		}else{
			layer.msg("请选择组",{time:1000,icon:0});
		}
	}
	
	function go_link(url){
		var link = document.createElement("a");
		link.href = url;
		document.body.appendChild(link);
		link.click();
	}

	</script>	
    </body>
<%@ include file="/common/widgets.jsp"%>
</html>