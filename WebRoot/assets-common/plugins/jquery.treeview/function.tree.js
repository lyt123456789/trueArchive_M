function search(sid,domId,apiUrl){
	var str=$.trim($('#'+sid).val());
	var $dom = $('#'+domId);
	if(str === ""){
		$dom.find("li").show();
		return false;
	}else{
		$.get(
			//"departmentTree_getContent.do?"+sid+"="+encodeURI(encodeURI(str))+"&t=" + new Date().getTime(),
			apiUrl+"?"+sid+"="+encodeURI(encodeURI(str))+"&t=" + new Date().getTime(),
			//addUrlPara(addUrlPara(apiUrl, 't', new Date().getTime())),
			function(data){
				var obj = eval("("+data+")");
				var arr = [obj[0].id];
				arr = getIdListFromJson2(obj[0].children,arr);
				if(arr.length>0){
					$.each(arr, function(i,e){
						openTreeById(_cache["#"+domId], e);
					})
					$dom.find(">li").find("li").hide();
					$.each(arr, function(i,e){
						$dom.find("[id='"+e+"']").show();
					})
				}else{
					$dom.find("li").show();
				}
			}
		)
	}
	return false;
}

function resetBodyHtml(response,isLay,userId){
	if(response.length>0){
		//缓存
		_recordObj.groupList = response;
		//tab头部,身体,搜索
		var html='', body='', search='', lay_body='', searchIds='';
		$.each(_recordObj.groupList, function(i,e){
			if(e.isCustomGroup){
				html += '<li class="tw-nav-item"><a href="javascript: void(0)">'+e.name+'</a>'
				+'</li>';
				//+'<a class="tw-btn-secondary" href="" target="_self"><i class="tw-icon-pencil"></i> 编辑</a></li>';
			}else{
				html += '<li class="tw-nav-item"><a href="javascript: void(0)">'+e.name+'</a></li>';
			}
			
			if(e.searchOption!==null){
				search = '<div class="tw-usertree-search">'+
		            			'<input type="text" id="'+e.searchOption.domId+'" value="" class="tw-form-text" />'+
		            			'<button type="button" class="tw-btn-primary" onclick="search(\''+e.searchOption.domId+'\',\''+e.domId+'\',\''+e.searchOption.apiUrl+'\');"><i class="tw-icon-search"></i> 搜索</button>'+
		            		'</div>';
				searchIds += ','+e.searchOption.domId;
				window.setInterval(function() {
					var obj = $( '#'+e.searchOption.domId );
					if(obj.length>0){
						var val = obj.val();
						if ( $.data( obj, 'cache') != val ) {
							$.data( obj, 'cache', val );
							obj.next('button').click();
						}
					}
				}, 1000)
			}
			body += '<div class="tw-tab-panel">'
		            	+'<div class="tw-panel-cnt">'
		            		+'<div class="JS_treeBox">'+search
		            			+'<ul id="'+e.domId+'" class="tw-usertree-ul filetree"></ul>'
		            		+'</div>'
		            	+'</div>'
		            +'</div>';
		    if(e.level==0){
			    lay_body += '<div class="JS_treeBox">'+search
		            			+'<ul id="'+e.domId+'" class="tw-usertree-ul filetree"></ul>'
		            		+'</div>';

			    //存储根标记
			    _recordObj.mainNodeId = e.domId;
		    }
		    search = '';
		})
		//html += '<li class="tw-nav-act"><span onclick="addAll()" class="tw-btn-primary tw-btn-sm">添加全部</span></li>';
		html += '<li class="tw-nav-act"><span onclick="editGroup()" class="tw-btn-primary tw-btn-sm">常用组</span></li>';
		html += '<li class="tw-nav-act" style="position:relative;position: relative;width: 38px;height: 22px">'
				+'<div class="tabsLeft" onclick="tabMoveLeft()"></div><div class="tabsRight" onclick="tabMoveRight()"></div></li>';

		//tab头部组成
		$('.tw-tabs-nav').html(html);

		//tab身体组成个
		$('.tw-tabs-bd').html(body);
		
		//弹出窗的body生成
		if(isLay==1){
			$('.tw-usertree-list-style .bd').html(lay_body);
		}else{
			//生成tab效果
			$('.tw-usertree-tabs').tabs();

			//tabmove
			tabmoveInit();
		}
		
		//获取窗口大小
		_recordObj.pageSize = getPageSize();
		//console.log(_recordObj.pageSize);

		//生成滚动条
		//$('.JS_treeBox').slimScroll({height: isLay==1?410:400+'px'});
		//$('#JS_selectBox').slimScroll({height: isLay==1?410:400+'px'});
		//$('.JS_scroll').slimScroll({height: isLay==1?410:400+'px'});
		if(isLay==1){
			var tmpHeight =  _recordObj.pageSize[3]-110;
		}else{
			var tmpHeight =  _recordObj.pageSize[3]-100;
		}
		tmpHeight = tmpHeight<100?100:tmpHeight;
		$('.JS_treeBox').slimScroll({height: tmpHeight+'px'});
		$('#JS_selectBox').slimScroll({height: tmpHeight+'px'});
		$('.JS_scroll').slimScroll({height: tmpHeight+'px'});
		
		//tree构成
		$.each(_recordObj.groupList, function(i,e){
			var tmpUrl = addUrlPara(e.apiUrl,'userId',userId);
			tmpUrl = addUrlPara(tmpUrl,'t', new Date().getTime());
			if(isLay==1){
				if(e.level==0){
					$('#'+e.domId).treeview({
						url:tmpUrl
					});
				}
			}else{
				$('#'+e.domId).treeview({
					url:tmpUrl
				});
			}
		})
		
		//等到数据缓存完毕开始绑定事件
		_recordObj.interTime = setInterval(function(){
			if(isLay==0){
				if(_cache['#'+_recordObj.groupList.reverse()[0].domId]){
					clearInterval(_recordObj.interTime);
					start(isLay);
				}
			}else{
				if(_cache['#'+_recordObj.mainNodeId]){
					clearInterval(_recordObj.interTime);
					start(isLay);
				}
			}
		}, 1);
	}
}

function tabmoveInit(){
	// $(".tw-tabs-nav .tabsLeft").hide();
	// if($(".tw-nav-item:gt(1)").length>0){
	// 	$(".tw-nav-item:gt(1)").addClass('notsee').hide();
	// }else{
	// 	$(".tw-tabs-nav .tabsRight").hide();
	// }

	//暂时屏蔽
	$(".tw-tabs-nav .tabsLeft, .tw-tabs-nav .tabsRight").hide();
}
function tabMoveLeft(){
	var obj = $(".tw-nav-item").not('.notsee');
	var _tagert = obj.prev('.tw-nav-item');
	if(_tagert.prev('.tw-nav-item').length>0){
		obj.eq(obj.length-1).addClass('notsee').hide();
		_tagert.removeClass('notsee').show();
	}
	$(".tw-nav-item.tw-active").prev(".tw-nav-item").click();

	$(".tw-tabs-nav .tabsLeft, .tw-tabs-nav .tabsRight").show();
	if($(".tw-nav-item:gt(1)").length>0){
		if($(".tw-nav-item.tw-active").index()==0){
			$(".tw-tabs-nav .tabsLeft").hide();
		}
		if($(".tw-nav-item.tw-active").index()==($(".tw-nav-item").length-1)){
			$(".tw-tabs-nav .tabsRight").hide();
		}
	}
}
function tabMoveRight(){
	var obj = $(".tw-nav-item").not('.notsee');
	var _tagert = obj.next('.tw-nav-item');
	if(_tagert.next('.tw-nav-item').length>0){
		obj.eq(0).addClass('notsee').hide();
		_tagert.removeClass('notsee').show();
	}
	$(".tw-nav-item.tw-active").next(".tw-nav-item").click();

	$(".tw-tabs-nav .tabsLeft, .tw-tabs-nav .tabsRight").show();
	if($(".tw-nav-item:gt(1)").length>0){
		if($(".tw-nav-item.tw-active").index()==0){
			$(".tw-tabs-nav .tabsLeft").hide();
		}
		if($(".tw-nav-item.tw-active").index()==($(".tw-nav-item").length-1)){
			$(".tw-tabs-nav .tabsRight").hide();
		}
	}
}

function  start(isLay){
//	if($.isFunction('setValueOfPerson2')){
//		setValueOfPerson2();
//	}
	
	//多项展开
	$('.tw-usertree-ul').on('click','span a', function(e){
		$(this).parent("span").prev("div").click();
	});

	//事件绑定部分
	//单选
	$.each(_recordObj.groupList, function(i,e){
		if(e.isCustomGroup){//自定义组处理
			//自定义组关闭修正
			if(isLay==0){
				_recordObj.interTime2 = setInterval(function(){
					var len = $('.tw-usertree-ul#'+e.domId).find("ul").length;
					if(len>0){
						$('.tw-usertree-ul#'+e.domId).find("ul").hide();
						clearInterval(_recordObj.interTime2);
					}else{
						if(_cache[e.domId]){
							if(_cache[e.domId].length<1){
								clearInterval(_recordObj.interTime2);
							}
						}
					}
				}, 50);
			}

			$('.tw-usertree-ul#'+e.domId).on('click', 'span.file b.checkbox, span.file a', function(){
				var o = $(this).parent("span");
				var $parentLi = o.parent("li");
				var id = $parentLi.attr('id');
				if($parentLi.hasClass("checked")){
					delSelectOption(id);
					loadInit();
					var fid = _recordObj.tmpReJson[id].pid;
					$parentLi.removeClass("check checked");
					$.each(_recordObj.groupList, function(j,f){
						if(e.domId!==f.domId){
							if(f.isCustomGroup){
								var $o = $('#'+f.domId).find("li[id='"+id+"']");
								checkThisStyleByOption($o, id);
							}else{
								addCheckedToFile('#'+f.domId, fid, id, false, true );
							}
						}
					})
				}else{
					addSelectOption(id, o.find("a").text());
					loadInit();
					var fid = _recordObj.tmpReJson[id].pid;
					$parentLi.addClass("checked");
					$.each(_recordObj.groupList, function(j,f){
						if(e.domId!==f.domId){
							if(f.isCustomGroup){
								var $o = $('#'+f.domId).find("li[id='"+id+"']");
								checkThisStyleByOption($o, id);
							}else{
								addCheckedToFile('#'+f.domId, fid, id, true, true );
							}
						}
					})
				}
				loadInit();
				checkOneLiStyle($parentLi.parent("ul").parent("li"));
				showCount();
			})
		}else{
			if($('.tw-usertree-ul#'+e.domId)){
				$('.tw-usertree-ul#'+e.domId).on('click', 'span.file b.checkbox, span.file a', function(){
					var o = $(this).parent("span");
					var $parentLi = o.parent("li");
					var id = $parentLi.attr('id');
					var fid = $parentLi.parent("ul").parent("li").attr("id");
					if($parentLi.hasClass("checked")){
						delSelectOption(id);
						$parentLi.removeClass("check checked");
						if(isLay==0){
							$.each(_recordObj.groupList, function(j,f){
								if(e.domId!==f.domId){
									if(f.isCustomGroup){
										var $o = $('#'+f.domId).find("li[id='"+id+"']");
										checkThisStyleByOption($o, id);
									}else{
										addCheckedToFile('#'+f.domId, fid, id, false, true );
									}
								}
							})
						}
					}else{
						addSelectOption(id, o.find("a").text());
						$parentLi.addClass("checked");
						if(isLay==0){
							$.each(_recordObj.groupList, function(j,f){
								if(e.domId!==f.domId){
									if(f.isCustomGroup){
										var $o = $('#'+f.domId).find("li[id='"+id+"']");
										checkThisStyleByOption($o, id);
									}else{
										addCheckedToFile('#'+f.domId, fid, id, true, true );
									}
								}
							})
						}
					}
					loadInit();
					checkOneLiStyle($parentLi.parent("ul").parent("li"));
					showCount();
				})
			}			
		}
	})
	
	//全选事件
	$.each(_recordObj.groupList, function(i,e){
		if(e.isCustomGroup){
			$('.tw-usertree-ul#'+e.domId).on('click','span.folder b.checkbox', function(){
				var o = $(this).parent("span");
				var clickIndex = o.parent("li").index();
				if(!o.parent("li").hasClass("hasChildren")){//空目录
					return false;
				}
				var id = o.parent("li").attr('id');
				var name = o.find("a").text();
				if(id=="0") {
					return false;
				}
				checkThisStyle( o.parent("li") );
				var result = _cache['#'+e.domId];
				$.each(result, function(idx, ele){
					if(idx==clickIndex){
						if(ele.children && ele.children.length > 0){
							$.each(ele.children, function(i, e) {
								if(o.parent("li").hasClass("checked")){
									if(e.classes=="file"){
										addSelectOption(e.id, $($(e.text)[1]).text());
									}
								}else{
									if(e.classes=="file"){
										delSelectOption(e.id);
									}
								}
							});
						}
					}
				})

				$("li.check, li.checked").removeClass("check checked");
				loadInit();
				showCount();
			})
		}else{
			if($('.tw-usertree-ul#'+e.domId)){
				$('.tw-usertree-ul#'+e.domId).on('click','span.folder b.checkbox', function(){
					var o = $(this).parent("span");
					if(!o.parent("li").hasClass("hasChildren")){//空目录
						return false;
					}
					var id = o.parent("li").attr('id');
					var name = o.find("a").text();
					if(id=="0") {
						return false;
					}
					checkThisStyle( o.parent("li") );
					getJsonById(_cache['#'+_recordObj.mainNodeId], id);
					var result = reSortListByJson(_recordObj.result.children,[{id:id,classes:'folder',name:name}]);
					$.each(result, function(i, e) {
						if(o.parent("li").hasClass("checked")){
							if(e.classes=="file"){
								addSelectOption(e.id, e.name);
							}
						}else{
							if(e.classes=="file"){
								delSelectOption(e.id);
							}
						}
					});
					$("li.check, li.checked").removeClass("check checked");
					loadInit();
					showCount();
				})
			}
		}
	})
	
	//二次选择导入option数据
	//等待加载数据
	_recordObj.interTime5 = setInterval(function(){
		if(_cache['#'+_recordObj.mainNodeId]){
			clearInterval(_recordObj.interTime5);
			//原操作
			loadInit();
			showCount();
		}
	}, 1);
}

//全局获取option数据加载进树
function loadInit(){
	$('li.check, li.checked').removeClass('check checked');
	var arr = [];
	$(".tw-usertree-list li").each(function(i,e){
		var _id =  $(this).attr("data-id");
		arr.push(_id);
	})
	if(arr.length>0){
		var jsonRight = _cache['#'+_recordObj.mainNodeId];
		var _jsonRight = reBuildJson( jsonRight, {}, null, arr );
		_recordObj.tmpReJson = _jsonRight;
		if(_jsonRight){
			$.each(arr, function(i,e) {
				if(_jsonRight[e]){
					var linked = getLinkById(_jsonRight,e,[]).reverse();
					$.each(_recordObj.groupList, function(j,f){
						var o = $("#"+f.domId).find("li[id='"+e+"']");
						if(o.length>0){
							checkThisStyleByOption(o, e);
						}
					})
					clickIt(_jsonRight, linked);
				}
			});
		}
	}else{
		$('li.check, li.checked').removeClass('check checked');
	}
}
function clickIt(jsonRight, linked){
	if(linked.length>0){
		var id = linked[0];
		if(id=="0"){
			linked = linked.slice(1);
			clickIt(jsonRight, linked);
		}else{
			$.each(_recordObj.groupList, function(j,f){
				if(!f.isCustomGroup){
					var obj = $("#"+f.domId).find("li[id='"+id+"']");
					if( obj ){
						if(0 < jsonRight[id].fileSelected){
							obj.removeClass("checked").addClass("check");
						}
						if(jsonRight[id].childFileCount == jsonRight[id].fileSelected){
							obj.removeClass("check").addClass("checked");
						}
						if(0 == jsonRight[id].fileSelected){
							obj.removeClass("check checked");
						}
					}
				}else{
					var $obj = $("#"+f.domId).find('>li');
					$.each($obj, function(i, e){
						$this = $(this);
						$this.addClass("checked");
						if($this.find('>ul >li').not(".checked").length>0){
							$this.removeClass("checked").addClass("check");
						}
						if($this.find('>ul >li.checked').length<1){
							$this.removeClass("checked check");
						}
					})
				}
			})
			linked = linked.slice(1);
			if(linked.length>0){
				clickIt(jsonRight, linked);
			}
		}
	}
}
function clickItCustom(){
	$.each(_recordObj.groupList, function(j,f){
		if(f.isCustomGroup){
			var $obj = $("#"+f.domId).find('>li');
			$obj.addClass("checked");
			if($obj.find('>ul >li').not(".checked").length>0){
				$obj.removeClass("checked").addClass("check");
			}
			if($obj.find('>ul >li.checked').length<1){
				$obj.removeClass("checked check");
			}
		}
	})
}

//获取某个子元素的父子链
function getLinkById(arr, id, linked){
	if(arr[id]){
		var obj = arr[id];
		if(obj.pid!==null){
			linked.push(obj.pid);
			linked = getLinkById(arr, obj.pid, linked);
		}
	}
	return linked;
}
//子项总数及被选数
function pidFileCountPlus(obj, pidk, selected){
	if(obj[pidk]){
		var ppid = obj[pidk].pid;
		obj[pidk].childFileCount = obj[pidk].childFileCount + 1;
		if(selected>-1){
			obj[pidk].fileSelected = obj[pidk].fileSelected + 1;
		}
		if(ppid){
			obj = pidFileCountPlus(obj, ppid, selected);
		}
	}
	return obj;
}
//重建json
function reBuildJson( jsons, obj, pid, selected ){
	$.each(jsons, function(i,e) {
		if(e.classes=="file"){
			obj[e.id] = {
				classes:e.classes,
				hasChildren:e.hasChildren,
				id:e.id,
				text:e.text,
				pid:pid
			};
			obj = pidFileCountPlus(obj, pid, $.inArray(e.id, selected));
		}
		if(e.classes=="folder"){
			obj[e.id] = {
				children:e.children,
				classes:e.classes,
				expanded:e.expanded,
				hasChildren:e.hasChildren,
				id:e.id,
				text:e.text,
				pid:pid,
				childFileCount:0,
				fileSelected:0
			};
			if(e.children && e.children.length>0){
				obj = reBuildJson( e.children, obj, e.id, selected );
			}
		}
	});
	return obj;
}

//获取某项Id的json索引
//k=key,s=status
function getIndexFromCacheById(arr, id, lv, k, s){
	$.each(arr, function(i,e) {
		if(!_recordObj[s]){
			_recordObj[k][lv] = i;
			if(e.id == id){
				_recordObj[k][lv+1] = null;
				_recordObj[s] = true;
			}else{
				if(!_recordObj[s]){
					if(e.hasChildren){
						getIndexFromCacheById(e.children, id, (lv+1), k, s);
					}
				}
			}
		}
	});
}

//同步更新状态
function checkFolderAndParent( dom, id, checked, open ){
	var jsons = _cache['#allOrg'];
	var arr = jsons[0].children;
	_recordObj.stop = false;
	_recordObj.idx = [];//存放索引
	getIndexFromCacheById(arr, id, 0, 'idx', 'stop');
	var tmp = _recordObj.idx;
	openTreeAndChecked($(dom), tmp, id, checked, open);
}

function openTreeAndChecked($content, tmp, id, checked, open){
	var len = $content.find("li[id='"+id+"']").length;
	var lenChecked = $content.find("li.checked[id='"+id+"']").length;
	if( len > 0 ){//已展开过
		if(checked){
			$content.find("li[id='"+id+"']").not(".clickDisabled").removeClass("check").addClass("checked");
		}else{
			$content.find("li[id='"+id+"']").removeClass("check checked");
		}
		checkEveryFirstChild($content.find("li[id='"+id+"']").find("ul"));
		checkEveryParent($content.find("li[id='"+id+"']").find("ul"));
	}else{//未展开
		if(open){
			$ul = $content.find("ul").eq(0);
			if(tmp!==null){
				$.each(tmp, function(i,e){//根据索引位置展开
					if(e!==null){
						var childUlLen = $ul.find(">li").eq(e).find(">ul li").length;
						var div = $ul.find(">li").eq(e).find("div");
						if(childUlLen < 1 && div ){
							div.click();
						}
					}else{
						return false;
					}
				})
				openTreeAndChecked($content, null, id, checked, open);
			}
		}
	}
}

//同步工作
function addCheckedToFile(dom, fid, id, checked, open){
	var jsons = _cache[dom];
	var arr = jsons[0].children;
	_recordObj.stop = false;
	_recordObj.idx = [];//存放索引
	getIndexFromCacheById(arr, fid, 0, 'idx', 'stop');
	var tmp = _recordObj.idx;
	openTreeAndCheckedFile($(dom), tmp, fid, id, checked, open);
}
function openTreeAndCheckedFile($content, tmp, fid, id, checked, open){
	var fileId = id;
	id = fid;//folderId
	var len = $content.find("li[id='"+id+"']").length;
	var lenChecked = $content.find("li.checked[id='"+id+"']").length;
	if( len > 0 ){//已展开过
		var childUlLen = $content.find("li[id='"+id+"']").find(">ul li").length;
		var div = $content.find("li[id='"+id+"']").find("div");
		if(childUlLen < 1 && div ){
			div.click();
		}
		if(checked){
			$content.find("li[id='"+fileId+"']").not(".clickDisabled").removeClass("check").addClass("checked");
		}else{
			$content.find("li[id='"+fileId+"']").removeClass("check checked");
		}
		checkOneLiStyle($content.find("li[id='"+fileId+"']").parent("ul").parent("li"));
		checkEveryParent($content.find("li[id='"+fileId+"']").parent("ul"));
	}else{//未展开
		if(open){
			if(tmp!==null){
				$ul = $content.find("ul").eq(0);
				$.each(tmp, function(i,e){//根据索引位置展开
					if(e!==null){
						var childUlLen = $ul.find(">li").eq(e).find(">ul li").length;
						var div = $ul.find(">li").eq(e).find("div");
						if(childUlLen < 1 && div ){
							div.click();
						}
					}else{
						return false;
					}
				})
				openTreeAndCheckedFile($content, null, id, fileId, checked, open);
			}
		}
	}
	
}
function checkOneLiStyle(obj){//li
	if(obj){
		var $parentUl = obj.find(">ul");
		var lenCheck = $parentUl.find(">li.check").not(".clickDisabled").length;
		var lenChecked = $parentUl.find(">li.checked").not(".clickDisabled").length;
		var lenEmpty = $parentUl.find(">li.hasChildren").not(".clickDisabled").not(".checked").not(".check").length;
		var lenFileEmpty = $parentUl.find(">li").not(".clickDisabled").not(".checked").not(".check").length;
		if( lenChecked > 0 ){
			obj.not(".clickDisabled").removeClass("check checked").addClass("checked");
		}
		if( lenCheck > 0 || lenEmpty > 0 || lenFileEmpty > 0 ){
			obj.not(".clickDisabled").removeClass("check checked").addClass("check");
		}
		if( lenCheck==0 && lenChecked==0 ){
			obj.removeClass("check checked");
		}
		if( !obj.parent("ul").hasClass("treeview") ){
			checkOneLiStyle( obj.parent("ul").parent("li") );
		}
	}
	
}

//打开同步树状结构
function openTreeById(jsons,id){
	var arr = jsons[0].children;
	_recordObj.stop = false;
	_recordObj.idx = [];//存放索引
	getIndexFromCacheById(arr, id, 0, 'idx', 'stop');
	var tmp = _recordObj.idx;
	var tmpob = $('#allOrg').find(">li").eq(0).find(">ul").eq(0);
	$.each(tmp, function(i,e) {//打开对应的位置
		if(e!==null){
			if(tmpob.find(">li").eq(e).find(">ul").find("li").length<1){
				var t = tmpob.find(">li").eq(e).find("div"); 
				t.trigger("click");//打开加载
			}
			tmpob = tmpob.find(">li").eq(e).find(">ul");
		}else{
			return false;
		}
	});
}
function openTreeById_rightToLeft(jsons,id){
	var arr = jsons;
	_recordObj.stop = false;
	_recordObj.idx = [];//存放索引
	getIndexFromCacheById(arr, id, 0, 'idx', 'stop');
	var tmp = _recordObj.idx;
	var tmpob = $('#ownOrg');
	$.each(tmp, function(i,e) {//打开对应的位置
		if(e!==null){
			if(tmpob.find(">li").eq(e).find(">ul").find("li").length<1){
				var t = tmpob.find(">li").eq(e).find("div");
				t.trigger("click");//打开加载
			}
			tmpob = tmpob.find(">li").eq(e).find(">ul");
		}else{
			return false;
		}
	});
}

//检查某个span项，逐层向上更新样式
function checkRelationForeach(obj){//span[class=file]
	if(obj){
		var $parentUl = obj.parent("li").parent("ul");
		var allChecked = $parentUl.find("li.checked").length;//所有全选的子元素
		var lenChecked = $parentUl.find(">li.checked").length;//所有全选的一级子元素
		var lenCheck = $parentUl.find(">li.check").length;//所有半选中的一级子元素
		var lenAll = $parentUl.find(">li").length;//所有一级子元素
		
		var spanLenAll = $parentUl.find("span.file").length;
		var spanLenChecked = $parentUl.find("li.checked").find("span.file").length;
		
		if(allChecked<1){//一个都没选
			$parentUl.parent("li").removeClass("checked check");
		}else{
			if(lenChecked<1){//一个全选的一级子元素都没有
				$parentUl.parent("li").removeClass("checked");
			}
			if(lenCheck<1){//一个扳选的一级子元素都没有
				$parentUl.parent("li").removeClass("check");
			}
			if(lenCheck>0){//有至少一个半选的一级子元素，那就不可能是全选状态了
				$parentUl.parent("li").not(".clickDisabled").addClass("check");
			}else{
				if(lenChecked == lenAll){
					$parentUl.parent("li").not(".clickDisabled").removeClass("check").addClass("checked");
				}else{
					$parentUl.parent("li").not(".clickDisabled").removeClass("checked").addClass("check");
				}
			}
		}
		if($parentUl.prev("span").length>0){
			checkRelationForeach($parentUl.prev("span"));
		}
	}
}

//全选按钮更新样式
function checkThisStyle( obj ){
	if( obj.hasClass("checked") ){
		obj.removeClass("check checked");
	}else{
		obj.not(".clickDisabled").removeClass("check checked").addClass("checked");
	}
}
//全选样式根据option更新
function checkThisStyleByOption( obj, id ){
	var len = $(".tw-usertree-list").find("li[data-id='"+id+"']").length;
	if( len > 0 ){
		obj.not(".clickDisabled").removeClass("check checked").addClass("checked");
	}else{
		obj.removeClass("check checked");
	}
}

//检查一级子目录并更改样式
function checkEveryFirstChild( obj ){//ul
	if(obj){
		var $parentLi = obj.parent("li");
		if( $parentLi.hasClass("checked") ){
			obj.find(">li").not(".clickDisabled").removeClass("check").addClass("checked");
		}else{
			if(!$parentLi.hasClass("check")){
				obj.find(">li").removeClass("checked check");
			}
		}
		if(obj.find(">li ul li").length>1){
			obj.find(">li ul").each(function(){
				checkEveryFirstChild( $(this) );
			})
		}
	}
}

//检查上级目录并更改样式
function checkEveryParent( obj ){//ul
	if(obj){
		var $parentUli = obj.parent("li").parent("ul").parent("li");
		var $parentUl = $parentUli.find(">ul");
		var lenCheck = $parentUl.find(">li.check").not(".clickDisabled").length;
		var lenChecked = $parentUl.find(">li.checked").not(".clickDisabled").length;
		var lenEmpty = $parentUl.find(">li.hasChildren").not(".clickDisabled").not(".checked").not(".check").length;
		var lenFileEmpty = $parentUl.find(">li").not(".clickDisabled").not(".checked").not(".check").length;
		if( lenChecked > 0 ){
			$parentUli.not(".clickDisabled").removeClass("check checked").addClass("checked");
		}
		if( lenCheck > 0 || lenEmpty > 0 ){
			$parentUli.not(".clickDisabled").removeClass("check checked").addClass("check");
		}
		if( lenCheck==0 && lenChecked==0 ){
			$parentUli.removeClass("check checked");
		}
		if( !obj.parent("li").parent("ul").hasClass("treeview") ){
			checkEveryParent( obj.parent("li").parent("ul") );
		}
	}
}

//从json中遍历出所有id
function getIdListFromJson2(jsons, arr){
	$.each(jsons, function(i, e) {
		if(e.id){
			arr.push(e.id);
		}
		if(e.children && e.children.length>0 ){
			arr = getIdListFromJson2(e.children, arr);
		}
	});
	return arr;
}
//将json重组用于快速将数据添加到下拉菜单
function reSortListByJson(jsons, arr){
	$.each(jsons, function(i, e) {
		if(e.id){
			arr.push({id:e.id,classes:e.classes,name:$(e.text).text()});
		}
		if(e.children && e.children.length>0 ){
			arr = reSortListByJson(e.children, arr);
		}
	});
	return arr;
}

//获取某Id下面的json数据
//forexample:getJsonById(_cache['#allOrg'],id)
function getJsonById(jsons,id){
	$.each(jsons, function(i,e) {
		if(e.id==id){
			_recordObj.result = e;
		}else{
			if(e.hasChildren){
				getJsonById(e.children, id);
			}
		}
	});
}

//更新select选项数目
function showCount(){
	var countNum = document.getElementById('oldSelect').options.length;
	$("#countNum").text(countNum);
};

//递归打开所有子级目录，仅从json加载数据，无选中操作
function openChildForeach(obj){//obj=div
	var o = obj,lis;
	if(o.hasClass("hasChildren-hitarea")){
		if(o.parent("li").attr("id")!=="0"){
			if(o.next("span").next("ul").find(">li").length<1){
				o.trigger("click");//加载子级
			}
		}
	}
	lis = o.next("span").next("ul").find(">li");
	if(lis.length>0){
		lis.each(function(i,e){
			if($(e).hasClass("hasChildren")){
				$(e).find("div").trigger("click");
				openChildForeach($(e).find("div"));
			}
		})
	}
}

//判断某项[span class=file]是否选中,并更改状态
function isTreeClassChecked(id,update,option){
	var obj = $(".tw-usertree-tabs").find("li[id='"+id+"']");
	if(obj.hasClass("checked")){
		setTreeClassUnChecked(id,update,option);
	}else{
		if(obj.hasClass("check")){
			setTreeClassUnChecked(id,update,option);
		}else{
			setTreeClassChecked(id,update,option);
		}
	}
	showCount();
}

//设置某项[span class=file]成未选中样式,关联下拉菜单数据
function setTreeClassUnChecked(id,update,option){
	var obj = $(".tw-usertree-tabs").find("li[id='"+id+"']");
	obj.each(function(i,e){
		if(!$(e).hasClass("hasChildren")){
			if($(this).find("span").hasClass("file")){
				if( typeof(option)=='undefined' ){
					delSelectOption(id);
				}
			}
		}
		$(e).removeClass("checked");
		if( typeof(update)=='undefined' ){
			checkRelationForeach($(this).find("span"));
		}
	})
}

//设置某项[span class=file]成选中样式,关联下拉菜单数据
function setTreeClassChecked(id,update,option){
	var obj = $(".tw-usertree-tabs").find("li[id='"+id+"']");
	obj.each(function(i,e){
		if($(e).hasClass("hasChildren")){
			$(e).not(".clickDisabled").addClass("checked");
		}else{
			if($(this).find("span").hasClass("file")){
				$(e).not(".clickDisabled").addClass("checked");
				if( typeof(option)=='undefined' ){
					addSelectOption(id,$(this).find("span a").text());
				}
				if( typeof(update)=='undefined' ){
					checkRelationForeach($(this).find("span"));
				}
			}
		}
	})
}

//删除右侧下拉菜单的某条数据
function delSelectOption(id){
	//存在
	if($(".tw-usertree-select").find("[data-id='"+id+"']").length>0){
		$(".tw-usertree-select li[data-id='"+id+"']").remove();
		$(".tw-usertree-select option[value='"+id+"']").remove();
	}
}
//向右侧下拉菜单添加某条数据
function addSelectOption(id,name){
	//不重复的id才添加
	if($(".tw-usertree-select").find("[data-id='"+id+"']").length<1){
		var _html = '<li data-id='+ id +'><span>'+ name +'</span> <i class="tw-usertree-del tw-icon-times-circle" onclick="deltree(this)"></i></li>';
		$(".tw-usertree-list").append(_html);
		var oldSelect=document.getElementById('oldSelect');
		oldSelect.options.add(new Option(name,id));
	}
}
function addAll(){
	$("#allOrg").find("span.folder").eq(0).trigger("click");
}
function deltree(o){
	var $this = $(o);
	var id = $this.parent("li").attr('data-id');
	delSelectOption(id);
	//setTreeClassUnChecked(id);
	loadInit();
	showCount()
}
function delAll(){
	var obj=document.getElementById('oldSelect');
	obj.options.length=0;
	$(".tw-usertree-list").empty();
	$(".tw-usertree-deplist").empty();
	$('.tw-usertree-ul span.file').removeClass("checked");
	$('.tw-usertree-ul span.folder').removeClass("checked");
	$("#allOrg, #ownOrg").find("li").removeClass("check checked");
	loadInit();
	showCount();
};

function addUrlPara(url, name, value) {
    var currentUrl = url;
    //var currentUrl = window.location.href.split('#')[0];  
    if (/\?/g.test(currentUrl)) {  
        if (/name=[-\w]{4,25}/g.test(currentUrl)) {  
            currentUrl = currentUrl.replace(/name=[-\w]{4,25}/g, name + "=" + value);  
        } else {  
            currentUrl += "&" + name + "=" + value;  
        }  
    } else {  
        currentUrl += "?" + name + "=" + value;  
    }
    /*if (window.location.href.split('#')[1]) {  
        window.location.href = currentUrl + '#' + window.location.href.split('#')[1];  
    } else {  
        window.location.href = currentUrl;  
    }*/
    return currentUrl;
}

//获取设备尺寸
function getPageSize() {
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) {
        xScroll = window.innerWidth + window.scrollMaxX;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else {
        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac    
            xScroll = document.body.scrollWidth;
            yScroll = document.body.scrollHeight;
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari    
            xScroll = document.body.offsetWidth;
            yScroll = document.body.offsetHeight;
        }
    }
    var windowWidth, windowHeight;
    if (self.innerHeight) { // all except Explorer    
        if (document.documentElement.clientWidth) {
            windowWidth = document.documentElement.clientWidth;
        } else {
            windowWidth = self.innerWidth;
        }
        windowHeight = self.innerHeight;
    } else {
        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode    
            windowWidth = document.documentElement.clientWidth;
            windowHeight = document.documentElement.clientHeight;
        } else {
            if (document.body) { // other Explorers    
                windowWidth = document.body.clientWidth;
                windowHeight = document.body.clientHeight;
            }
        }
    }       
    // for small pages with total height less then height of the viewport    
    if (yScroll < windowHeight) {
        pageHeight = windowHeight;
    } else {
        pageHeight = yScroll;
    }    
    // for small pages with total width less then width of the viewport    
    if (xScroll < windowWidth) {
        pageWidth = xScroll;
    } else {
        pageWidth = windowWidth;
    }
    arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
    return arrayPageSize;
}

$(document).keydown(function(event){
	if(event.keyCode==13){
		$("#mc").next('button').click();
	} 
});

