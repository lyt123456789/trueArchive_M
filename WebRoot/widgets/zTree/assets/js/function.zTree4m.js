'use strict';

var _globalObject = {}
//全局人员id列表，减少dom操作
_globalObject.selectedList = [];
//人员变动比对表，减少dom比对
_globalObject.changeList = {};
//全局定时器:人员计数
_globalObject.showCountInterTime = setInterval(function(){ showCount() }, 50);

//查询条件过滤器
function filter(node) {
    return (node.level !== 0);//非根节点
}
function filterR(node){
	return isIdCheck(node.id);
}

//展开前回调
function beforeExpand(treeId, treeNode){
	//fixed bug to ChkDisabled
	/*
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var treeobj = zTree.getNodeByParam("type","folder",null);
	var treeNodesAll = zTree.transformToArray(treeobj);
	for(var $i=0,$len=treeNodesAll.length;$i<$len;$i++){
		var node = treeNodesAll[$i];
		if( node.isParent ){
			if( node.children && node.children.length>0){
				//do nothing
			}else{
				zTree.checkNode(node, false, false, false);
				zTree.setChkDisabled(node,true);
			}
		}
	}*/
//	if(treeNode.children && treeNode.children.length>0){
//		//do nothing
//	}else{
//		return false;
//	}
}

//人员变动-批量
function addChangeMulty(keyName,arrayId){
	//将变化存入其他组待办事项中，tab点击事件里触发后移除待办
	$.each(_globalObject.groupList,function(i,e){
		if(keyName!==e.domId){
			if(_globalObject.idList[e.domId] && _globalObject.idList[e.domId].length>0){
				var cList = $.intersect(_globalObject.idList[e.domId], arrayId);//交集
				_globalObject.changeList[e.domId] = $.union(_globalObject.changeList[e.domId], cList)//去重合并
			}
		}
	})
}
//人员变动-增
function addChange(keyName,valueId){
	//将变化存入其他组待办事项中，tab点击事件里触发后移除待办
	$.each(_globalObject.groupList,function(i,e){
		if(keyName!==e.domId){
			//if($.inArray(valueId,_globalObject.changeList[e.domId])<0){
				//if($.inArray(valueId,_globalObject.idList[e.domId])>-1){
					if(_globalObject.idList[e.domId]&&_globalObject.idList[e.domId].length>0){
						var tmp = $.intersect(_globalObject.idList[e.domId], [valueId]);//交集
						_globalObject.changeList[e.domId] = $.union(_globalObject.changeList[e.domId], tmp);//去重合并
					//_globalObject.changeList[e.domId].push(valueId);
					}
				//}
			//}
		}
	})
//	console.log('after_add >',_globalObject.changeList);
}
function addChangeAll(valueId){
	//将变化存入其他组待办事项中，tab点击事件里触发后移除待办
	$.each(_globalObject.groupList,function(i,e){
		if(_globalObject.idList[e.domId] && _globalObject.idList[e.domId].length>0){
			var cList = $.intersect(_globalObject.idList[e.domId], valueId);//交集
			_globalObject.changeList[e.domId] = $.union(_globalObject.changeList[e.domId], cList);//去重合并
		}
	})
//	console.log('after_add >',_globalObject.changeList);
}
//将待办变化移除
function removeChange(keyName,valueId){
//	console.log('before_remove >', _globalObject.changeList);
	//var tmp = _globalObject.changeList[keyName];
	//if($.inArray(valueId,tmp)>-1){
		_globalObject.changeList[keyName] = $.except(_globalObject.changeList[keyName], [valueId]);
	//}
//	console.log('after_remove >',_globalObject.changeList);
}

//实时查询
//mc,allOrg,查询api接口
function search(otherParam, sid, domId, apiUrl){
	//关键字
	var str = $.trim($('#'+sid).val());
	//要查询的树对象
	var zTree = $.fn.zTree.getZTreeObj(domId);
	//指定树对象下面的所有非根节点
	var myNodes = zTree.getNodesByFilter(filter, false);
	if(str === ""){
		if(myNodes.length>0){
			//全部show
			//ztree对象内置的show方法
			zTree.showNodes(myNodes);
			
			$.each(myNodes, function(j,f) {
				//折叠
				//指定节点，关闭，只影响该节点，不设置焦点，不触发回调事件
				zTree.expandNode(f, false, false, false, false);
			});
		}
		return false;
	}else{
		//全部hide
		//ztree对象内置的hide方法
		zTree.hideNodes(myNodes);
		var url = apiUrl+"?"+sid+"="+encodeURI(encodeURI(str));
		var other = new Array();
		other = otherParam.split(",");
		if(other.length > 0 ){
			$.each(other, function(e) {
				url += "&"+other[e]+"="+encodeURI(encodeURI($.trim($('#'+other[e]).val())));
			});
		}
		$.ajax({
			//url?mc=%7d%%6e%%ef%&t=1748861244056
			url: url+"&t=" + new Date().getTime(),
			dataType: "json",
			async:true,
			success: function(data) {
				if(data!==null){
					//json字符串转化为对象
					if(typeof data === 'string'){
						data = eval('('+data+')');
					}
				}
				if(data!==null&&data.length>0){
					//重组json
					var jsons = reBuildJson(data,[]);
					if(jsons.length>0){
						//遍历查询结果
						$.each(jsons, function(i,e) {
							var id = e.id;
							//如果有多个同样的ID，全部都取出来
							var treeNodes = zTree.getNodesByParam('id',id);
							if(treeNodes.length>0){
								$.each(treeNodes, function(j,f) {
									//逐个展现
									zTree.showNode(f);
									//如果是部门就展开
									if(e.type=='folder'){
										//展开路径
										//指定节点，打开，只影响该节点，不设置焦点，不触发回调事件
										zTree.expandNode(f, true, false, false, false);
									}
								});
							}
						});
						_globalObject.searchContent = str;
						$('#'+sid).trigger('propertychange');
					}else{
						console.log('json重组异常-data >>> ', data);
					}
					//window.clearInterval(_globalObject.searchInterTime[sid]);
				}
			}
		})
		return false;
	}
}

//数据联动
//这里的参数是dom的ID，例如<ul id="allOrg" class="ztree"></ul>
//如果没有指定ID，则取当前active的tab项下面的ID
function dataRelation(treeId){
	if(!treeId){
		var activeObj = $('.tw-tabs-nav .tw-nav-item.tw-active');
		//取默认打开了的tab项下面的ztree的ID
		if(activeObj.length>0){
			treeId = $('.tw-tab-panel[data-tab-idx='+activeObj.attr('data-tab-idx')+']').find('ul.ztree').attr('id');
		}
		//若没有已打开的tab则默认为全局的mainNodeId
		//这个值在初始化tab的时候生成，一般情况不出现bug都是有默认打开tab的，以防bug设置此ID
		if(!treeId){
			treeId = _globalObject.mainNodeId;
		}
	}
	var zTree = $.fn.zTree.getZTreeObj(treeId);

	var changeList = _globalObject.changeList[treeId];
	if(changeList && changeList.length>0){
		//pc塞值的优化
		if(changeList.sort().toString()===_globalObject.selectedList.sort().toString()){//callbackselect
			var treeNodes = zTree.getNodesByFilter(function(node){
				return ($.inArray(node.id,changeList)>-1)
			},false);
			//console.log(treeNodes);
			if(treeNodes.length == zTree.transformToArray(zTree.getNodes()).length){//level=0
				zTree.checkAllNodes(true);
			}else{
				$.each(treeNodes, function(i,e) {
					zTree.checkNode(e, true, true, false);
					//removeChange(treeId,e.id);
				});
			}
			_globalObject.changeList[treeId] = [];
		}else{
			$.each(changeList, function(i,e) {
				var cId = e;//变动ID
				if($.inArray(cId,_globalObject.selectedList)<0){
					//to unCheck
	//				var treeNodes = zTree.getNodesByFilter(filterR,false);
					var treeNodes = zTree.getNodesByParam('id',cId);
					$.each(treeNodes, function(i,e) {
						zTree.checkNode(e, false, true, false);
					});
	//				console.log('uck');
				}else{
					//to checked
					var treeNodes = zTree.getNodesByParam('id',cId);
					$.each(treeNodes, function(i,e) {
						zTree.checkNode(e, true, true, false);
					});
	//				console.log('ck');
				}
				removeChange(treeId,cId);
			});
		}
	}

	//全部取消
//	zTree.checkAllNodes(false);
	//取已选取select内容
	//var options = $('#oldSelect option');
	
//	var treeNodes = zTree.getNodesByFilter(filterR,false);
//	$.each(treeNodes, function(i,e) {
//		zTree.checkNode(e, true, true, false);
//	});
//	$.each(_globalObject.selectedList, function(i,e) {
//		var id = e;
//		//如果有多个同样的ID，全部都取出来
//		var treeNodes = zTree.getNodesByParam('id',id);
//		if(treeNodes.length>0){
//			$.each(treeNodes, function(j,f) {
//				//指定节点，勾选，父子联动，不触发回调事件
//				zTree.checkNode(f, true, true, false);
//			});
//		}
//	});
}

//判断是否isCustomGroup，否禁止拖动
function beforeDrag(treeId, treeNodes){
	var stop = false;
	$.each(_globalObject.groupList, function(i,e) {
		if(e.domId==treeId){
			if(e.isCustomGroup==false){
				stop = true;
			}
		}
	});
	if(treeNodes[0].isParent==true){
		stop = true;
	}
	if(stop){return false}
}

//拖拽结束事件
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy){
	//无排序产生
	if(targetNode==null){
		return false;
	}
	var fpId = treeNodes[0].pId;
	
	//var dropObj = $("#"+treeId);
	
	//要查询的树对象
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	//指定树对象下面的所有非根节点,过滤条件为同一父级下的所有子集
	var myNodesArr = zTree.transformToArray( zTree.getNodesByFilter(function(node){
		return node.pId == fpId;
	}, false) );
	//剔除无用属性
	var rtnArr = [];
	$.each(myNodesArr, function(i,e) {
		rtnArr.push({
			id:e.id,
			name:e.name
		});
	});
//	console.log(rtnArr,JSON.stringify(rtnArr));
	var data = 'persons='+JSON.stringify(rtnArr)+'&groupId='+fpId;
//	console.log(data);
	//TODO:将排序后重组的json传给后台处理数据，判断返回的成功码再通知前台
	$.ajax({
		type:"post",
		//url:_globalObject.sortUrl,//配置在json常用组中的sortUrl
		url:'',
		async:true,
		data:data,
		success:function(res){
			console.log('排序成功');
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			console.log('排序方法接口参数异常');
		}
	});
}


//dom优化
function superEndDomBetter(){
	var list = _globalObject.selectedList;
	var _html = '';
	if(list.length>0){
		$.each(list, function(i,e){
			_html += '<li data-id='+ e +'><span>'+ _globalObject.nameList[e] +'</span> <i class="tw-usertree-del tw-icon-times-circle" onclick="deltree(this)"></i></li>';
		})
		$(".tw-usertree-list").empty().append(_html);//.html(_html);
	}else{
		$(".tw-usertree-list").empty();//.html(_html);
	}
}

//修复半选状态下第一次点击为全选
function beforeCheck(treeId, treeNode){
	
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if(treeNode.check_Child_State=="1"){
		zTree.checkNode(treeNode, false, false, false);
	}
	if(treeNode.type=='folder'){

		//阻断部门的选择
		var rt = $('#routType').val();
		if(rt=='0'||rt=='2'){
			return false;
		}
		
		if(treeNode.children && treeNode.children.length>0){
			//do nothing
		}else{
			return false;
		}
	}
	
	var rt = $('#routType').val();
	if(rt=='0' && (_globalObject.selectedList.length>=1) && $.inArray(treeNode.id,_globalObject.selectedList)<0){
		alert('此节点只允许选择一个人员');
		return false;
	}else if(rt=='2'){
		if((_globalObject.selectedList.length<1)){
			if(confirm("确认选择主办？")){
				return true;
			}else{
				return false;
			}
		}
		if((_globalObject.selectedList.length>=1)){
			if(confirm("确认取消主办？")){
				return true;
			}else{
				return false;
			}
		}
		//TODO
	}
}
function beforeClick(treeId, treeNode){
	if(treeNode.type=='file'){
		var rt = $('#routType').val();
		if(rt=='0' && (_globalObject.selectedList.length>=1) && $.inArray(treeNode.id,_globalObject.selectedList)<0){
			alert('此节点只允许选择一个人员');
			return false;
		}else if(rt=='2'){
			if((_globalObject.selectedList.length<1)){
				if(confirm("确认选择主办？")){
					return true;
				}else{
					return false;
				}
			}
			if((_globalObject.selectedList.length>=1)){
				if(confirm("确认选择协办？")){
					return true;
				}else{
					return false;
				}
			}
			//TODO
		}
	}
}

//checkbox点选回调事件
//参数：event:标准的js event对象
//treeId：ztree对象内部ID
//treeNode：被操作的节点json对象
function nodeOnCheck(e, treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	//个人名称直接添加到select
	if(treeNode.type=='file'){
		var temp_checked = treeNode.checked;
		
		//select option切换
		if(temp_checked){
			_globalObject.selectedList = $.union(_globalObject.selectedList, [treeNode.id]);
		}else{
			_globalObject.selectedList = $.except(_globalObject.selectedList, [treeNode.id]);
		}
//		toggleSelectOption(treeNode.id, treeNode.name);
		setTimeout(superEndDomBetter,1);
		var id = treeNode.id;
		addChange(treeId,id);
		//如果有多个同样的ID，全部都取出来
		var treeNodes = zTree.getNodesByParam('id',id);
		if(treeNodes.length>1){
			for (var i=0, l=treeNodes.length; i < l; i++) {
				//指定节点，根据此次操作后的勾选状态是否勾选，父子联动，不触发回调事件
				zTree.checkNode(treeNodes[i], treeNode.checked, true, false);
			}
		}
	}
	//判断部门是否选中遍历子元素
	if(treeNode.type=='folder'){
		//是否有子元素的判断
		if(treeNode.children && treeNode.children.length>0){
			var $liLen = $("#"+treeId+" >li").length;
			//顶级，第一级，没有同级
			if(treeNode.level==0 && treeNode.tId==(treeId+'_1') && $liLen==1){
				//zTree.checkAllNodes(treeNode.checked);
				if(treeNode.checked){
					//合并去重
					if(_globalObject.idList[treeId] && _globalObject.idList[treeId].length>0){
						_globalObject.selectedList = $.union(_globalObject.selectedList, _globalObject.idList[treeId]);
					}
				}else{
					//去除
					if(_globalObject.idList[treeId] && _globalObject.idList[treeId].length>0){
						_globalObject.selectedList = $.except(_globalObject.selectedList, _globalObject.idList[treeId]);
					}
				}
				//添加待办
				if(_globalObject.idList[treeId] && _globalObject.idList[treeId].length>0){
					addChangeMulty(treeId, _globalObject.idList[treeId]);
				}
				//dom优化
				setTimeout(superEndDomBetter,1);
			}else{
				//取所有人员子元素
//				var nodes = zTree.getNodesByParam('type','file',treeNode);
				var nodes = zTree.getNodesByFilter(function(node){
					return (node.type=='file' && !node.isHidden);
				},false,treeNode);
				var iL = [];
				$.each(nodes, function(i,e) {
					//fixed bug
					var ctreeNodes = zTree.getNodesByParam('id',e.id);
					if(ctreeNodes.length>1){
						for (var i=0, l=ctreeNodes.length; i < l; i++) {
							//指定节点，根据此次操作后的勾选状态是否勾选，父子联动，不触发回调事件
							zTree.checkNode(ctreeNodes[i], treeNode.checked, true, false);
						}
					}
					//to
					iL.push(e.id);
				});
				
				//fixed bug
				if(iL.length>0){
					var tmp = $.union(_globalObject.idList[treeId], iL);
					if(tmp&&tmp.length>0){
						addChangeMulty(treeId,tmp)
					}
				}
				
				if(treeNode.checked){
					_globalObject.selectedList = $.union(_globalObject.selectedList, iL);
				}else{
					_globalObject.selectedList = $.except(_globalObject.selectedList, iL);
				}
				setTimeout(superEndDomBetter,1);
			}
		}
	}
}


//文字点击回调事件
//参数：event:标准的js event对象
//treeId：ztree对象内部ID
//treeNode：被操作的节点json对象
function nodeOnClick(event, treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	//个人名称直接添加到select并选中，联动父级
	if(treeNode.type=='file'){
		//select option切换
		toggleSelectOption(treeNode.id, treeNode.name);
		var id = treeNode.id;
		addChange(treeId,id);
		//如果有多个同样的ID，全部都取出来
		var treeNodes = zTree.getNodesByParam('id',id);
		if(treeNodes.length>0){
			for (var i=0, l=treeNodes.length; i < l; i++) {
				//指定节点，勾选切换，父子联动，不触发回调事件
				zTree.checkNode(treeNodes[i], null, true, false);
			}
		}
	}
	//部门名称仅展开或关闭
	if(treeNode.type=='folder'){
		//指定节点，打开切换，只影响该节点，不设置焦点，不触发回调事件
		zTree.expandNode(treeNode, null, false, false, true);
	}
}

//下拉菜单数据操作
function toggleSelectOption(id,name){
	//不存在或不重复的id才添加
	if(!isIdCheck(id)){
	//if($(".tw-usertree-select").find("[data-id='"+id+"']").length<1){
		var _html = '<li data-id='+ id +'><span>'+ name +'</span> <i class="tw-usertree-del tw-icon-times-circle" onclick="deltree(this)"></i></li>';
		$(".tw-usertree-list").append(_html);
//		var oldSelect=document.getElementById('oldSelect');
//		oldSelect.options.add(new Option(name,id));
		addIdCheck(id);
	}else{
		//已存在的删除
		delSelectOption(id);
	}
}

function delSelectOption(id){
	//存在
	delIdCheck(id);
	$(".tw-usertree-select li[data-id='"+id+"']").remove();
//	if(isIdCheck(id)){
////	if($(".tw-usertree-select").find("[data-id='"+id+"']").length>0){
//		$(".tw-usertree-select li[data-id='"+id+"']").remove();
////		$(".tw-usertree-select option[value='"+id+"']").remove();
//		delIdCheck(id)
//	}
}

//被选中项的onclick删除事件
function deltree(o){
	var $this = $(o);
	var id = $this.parent("li").attr('data-id');
	//先操作select option
	delSelectOption(id);
	//再更新每个tab下的ztree
	$.each(_globalObject.groupList, function(i,e) {
		var zTree = $.fn.zTree.getZTreeObj(e.domId);
		//如果有多个同样的ID，全部都取出来
		var treeNodes = zTree.getNodesByParam('id',id);
		if(treeNodes.length>0){
			$.each(treeNodes, function(j,f) {
				//指定节点，勾选取消，父子联动，不触发回调事件
				zTree.checkNode(f, false, true, false);
			});
		}
	});
}

function delIdCheck(id){
	_globalObject.selectedList = $.except(_globalObject.selectedList,[id]);
//	_globalObject.selectedList.splice($.inArray(id,_globalObject.selectedList),1);
}

function addIdCheck(id){
	_globalObject.selectedList = $.union(_globalObject.selectedList, [id])
//	_globalObject.selectedList.push(id);
}

//判断是否已选中
function isIdCheck(id){
	return ($.inArray(id,_globalObject.selectedList) > -1);
}

//清除全部
function delAll(){
	//先清空select option
//	var obj=document.getElementById('oldSelect');
//	obj.options.length=0;
	$(".tw-usertree-list").empty();
	_globalObject.selectedList = [];
	//取消勾选每个tab下面的全部节点
	$.each(_globalObject.groupList, function(i,e) {
		var zTree = $.fn.zTree.getZTreeObj(e.domId);
		//全部取消
		zTree.checkAllNodes(false);
	});
};

//取出所有ID
function reBuildIdList(jsons){
	var arr = [];
	if(jsons.length>0){
		$.each(jsons, function(i,e) {
			if(e.type=='file'){
				arr.push(e.id);
				_globalObject.nameList[e.id] = e.name;
			}
		})
	}
	return arr;
}

/**
 * 重组json，测试阶段，最终要修改接口json格式，减少运算时间
 * @param {Object} jsons
 * @param {Array} arr
 * @param {String} pid
 */
function reBuildJson(jsons, arr, pid){
	//fixed bug 打开第一个
	if(jsons && jsons.length>0){
		var nodeT = jsons[0];
		if(nodeT.isParent && nodeT.type=='folder'){
			jsons[0].open = true;
		}
	}
	return jsons;
}

/**
 * 分组信息创建tab标签组+滚动条初始化
 * @param data 获取分组信息接口返回的数据
 */
function createTabsHtml(data){
	//tab头部,身体,搜索
	var html='', body='', search='';
	//即时查询用的定时器数组
	//设为数组，方便以后如果有多个查询框的拓展
	_globalObject.searchInterTime = [];
	//遍历每个tab
	$.each(data, function(i,e){
		//组成头部标签
		html += '<li class="tw-nav-item"><a href="javascript: void(0)">'+e.name+'</a></li>';
		//组成搜索框，绑定时器
		if(e.searchOption!==null){
			search = '<div class="tw-usertree-search">'+
	            			'<input type="text" id="'+e.searchOption.domId+'" value="" class="tw-form-text" />'+
	            			'<button type="button" class="tw-btn-primary" onclick="search(\''+e.searchOption.otherParam+'\',\''+e.searchOption.domId+'\',\''+e.domId+'\',\''+e.searchOption.apiUrl+'\');"><i class="tw-icon-search"></i> 搜索</button>'+
	            		'</div>';
	            		
	        //绑定即时查询定时器，如果查询内容发生变化才执行查询
			_globalObject.searchInterTime[e.searchOption.domId] = window.setInterval(function() {
				var obj = $( '#'+e.searchOption.domId );
				if(obj.length>0){
					/*定时器任务*/
					/*var val = obj.val();
					if ( _globalObject.searchContent != val ) {
						_globalObject.searchContent = val;
						var sid = e.searchOption.domId, domId = e.domId, apiUrl = e.searchOption.apiUrl;
						window.search(sid, domId, apiUrl);
					}*/
					/*绑定事件方式*/
					obj.unbind("input propertychange");
					obj.bind("input propertychange", function(){
						var val = obj.val();
						if ( _globalObject.searchContent != val ) {
							_globalObject.searchContent = val;
							var sid = e.searchOption.domId, domId = e.domId, apiUrl = e.searchOption.apiUrl;
							window.search(sid, domId, apiUrl);
						}
					})
					window.clearInterval(_globalObject.searchInterTime[e.searchOption.domId]);
				}
			}, 10);
		}
		//组成tab content, ztree的容器，必须在tree初始化之前生成，domId唯一
		body += '<div class="tw-tab-panel">'
	            	+'<div class="tw-panel-cnt">'
	            		+'<div class="JS_treeBox JS_scroll">'+search
	            			+'<ul id="'+e.domId+'" class="ztree"></ul>'
	            		+'</div>'
	            	+'</div>'
	            +'</div>';
	    //是否根节点，该字段是自定义在json内的，根tree对象内部生成的level不是同一个
	    if(e.level==0){
		    //存储主节点ID
		    _globalObject.mainNodeId = e.domId;
	    }
	    //排序接口
	    if(e.sortUrl){
	    	_globalObject.sortUrl = e.sortUrl;
	    }
	    search = '';
	})
	
	//获取设备类型
	_globalObject.isMobiles = isMobile();
	
	//PC端要接上常用组编辑按钮
	if(!_globalObject.isMobiles){
		_globalObject.editStatus = false;//默认关闭编辑模式
		//html += '<li class="tw-nav-act"><span class="tw-btn-primary tw-btn-sm" onclick="editEnabled(this)">开启排序</span></li>';
		html += '<li class="tw-nav-act"><span class="tw-btn-primary tw-btn-sm" onclick="editGroup()">常用组</span></li>';
	}
	
	//tab头部生成
	$('.tw-tabs-nav').html(html);
	//tab身体生成
	$('.tw-tabs-bd').html(body);
	//创建tab效果，依赖于common-ui.js
	$('.tw-usertree-tabs').tabs();
	
	//获取窗口大小，设置滚动窗口的高度，减去的数值其实就是layer窗口内除了滚动区域以外的高度
	_globalObject.pageSize = getPageSize();
	var tmpHeight =  _globalObject.pageSize[3]-60;
	tmpHeight = tmpHeight<100?100:tmpHeight;
	tmpHeight = tmpHeight>400?400:tmpHeight;
	
	//生成滚动条效果
	$('.JS_scroll').slimScroll({height: tmpHeight+'px'});
}

//Url加参数
function addUrlPara(url, name, value) {
    var currentUrl = url;
    if (/\?/g.test(currentUrl)) {
        if (/name=[-\w]{4,25}/g.test(currentUrl)) {
            currentUrl = currentUrl.replace(/name=[-\w]{4,25}/g, name + "=" + value);
        } else {
            currentUrl += "&" + name + "=" + value;
        }
    } else {
        currentUrl += "?" + name + "=" + value;
    }
    return currentUrl;
}

//更新select选项数目
function showCount(){
	$("#countNum").text(_globalObject.selectedList.length);
}

//是否移动端设备
function isMobile(){
	var ua = navigator.userAgent;
	var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),//苹果平板
	    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),//苹果手机
	    isAndroid = ua.match(/(Android)\s+([\d.]+)/),//安卓系统
	    isBB = ua.match(/BB/i),//黑莓
	    isPlayBook = ua.match(/PlayBook/i),//黑莓平板
	    isKindleFire = ua.match(/KFAPWI/i),//亚马逊
	    isNOKIA = ua.match(/NOKIA/i),//诺基亚
	    isMobiles = ipad || isIphone || isAndroid || isBB || isPlayBook || isKindleFire || isNOKIA;
	if( isMobiles ){
		return true;
	}else{
		return false;
	}
}

//获取设备屏幕尺寸
function getPageSize(){var xScroll,yScroll,pageHeight,pageWidth,arrayPageSize;if(window.innerHeight&&window.scrollMaxY){xScroll=window.innerWidth+window.scrollMaxX;yScroll=window.innerHeight+window.scrollMaxY}else{if(document.body.scrollHeight>document.body.offsetHeight){xScroll=document.body.scrollWidth;yScroll=document.body.scrollHeight}else{xScroll=document.body.offsetWidth;yScroll=document.body.offsetHeight}}var windowWidth,windowHeight;if(self.innerHeight){if(document.documentElement.clientWidth){windowWidth=document.documentElement.clientWidth}else{windowWidth=self.innerWidth}windowHeight=self.innerHeight}else{if(document.documentElement&&document.documentElement.clientHeight){windowWidth=document.documentElement.clientWidth;windowHeight=document.documentElement.clientHeight}else{if(document.body){windowWidth=document.body.clientWidth;windowHeight=document.body.clientHeight}}}if(yScroll<windowHeight){pageHeight=windowHeight}else{pageHeight=yScroll}if(xScroll<windowWidth){pageWidth=xScroll}else{pageWidth=windowWidth}arrayPageSize=new Array(pageWidth,pageHeight,windowWidth,windowHeight);return arrayPageSize}