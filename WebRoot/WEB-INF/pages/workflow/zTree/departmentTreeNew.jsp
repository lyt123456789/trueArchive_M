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
<link rel="stylesheet" type="text/css" href="${ctx}/assets-common/css/reset-tree.css?t=201805091840" />
<script src="${ctx}/widgets/zTree/assets/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/widgets/zTree/assets/js/GL.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/widgets/zTree/assets/js/jquery.arrayUtilities.min.js" type="text/javascript" charset="utf-8"></script>
<!--原人员树通用库-->
<script src="${ctx}/widgets/zTree/assets/js/common-ui.js" type="text/javascript" charset="utf-8"></script>
<!--滚动条-->
<script src="${ctx}/widgets/zTree/assets/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<!-- 引入layer  -->
<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
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
.tw-btn-orange:hover {
	background-color: #ea6e0c;
	border-color: #ea6e0c;
	color: #fff;
}
.tw-search-bar .tw-top-tool {
	display: inline-block;
	padding-right: 10px;
}
.tw-search-bar {
	padding: 6px 10px 10px 2px;
}
.tw-search-bar {
	font-size: 12px;
	padding: 10px 10px 10px 5px;
	border-top: 1px solid #eee;
	text-align: left;
}
.tar {
	text-align: right !important;
}
.tw-btn, .tw-btn-border-primary, .tw-btn-border-secondary, .tw-btn-border-orange, .tw-btn-border-green, .tw-btn-border-blue, .tw-btn-border-dangerly, .tw-btn-border-danger, .tw-btn-border-purple, .tw-btn-border-orangered, .tw-btn-primary, .tw-btn-secondary, .tw-btn-orange, .tw-btn-green, .tw-btn-blue, .tw-btn-dangerly, .tw-btn-danger, .tw-btn-purple, .tw-btn-orangered, .tw-page-go-btn {
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

label.checkbox-label:before{
	top:-1px !important;
}

.choose-ul{
	overflow:auto;
	overflow-x:hidden;
}

.choose-ul::-webkit-scrollbar
{
    width: 6px;
    height: 6px;
}
.choose-ul::-webkit-scrollbar-track-piece
{
    background-color: #CCCCCC;
    -webkit-border-radius: 6px;
}
.choose-ul::-webkit-scrollbar-thumb:vertical
{
    height: 5px;
    background-color: #999999;
    -webkit-border-radius: 6px;
}
.choose-ul::-webkit-scrollbar-thumb:horizontal
{
    width: 5px;
    background-color: #CCCCCC;
    -webkit-border-radius: 6px;
}
.choose-ul{
	width:175px;
	height:435px;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		getTreeData('${nodeId}','${defUserId}','','');
	});
	
	function getTreeData(nodeId,defUserId,type,userId){
		var mc = $('#mc').val();
		var dbUserId = "";
		//获取流程人员
		$.ajax({
			url : 'ztree_getArrData.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				"nodeId"	: nodeId,
				"defUserId"	: defUserId,
				"mc"		: mc,
				"type"		: type,
				"userId"	: userId
			},
			success : function(data) {
				var arr = eval('(' + data + ')');
				var html = "";
				var html2 = "";
				var count = 0;
				$.each( arr, function(index, content){ 
					html += "<div class=\"tree-ul\">"
						+"<h1 class=\"tree-head\">"
						+"<span class=\"checkbox\">"
						+"<input id=\""+content.id+"\" type=\"checkbox\"  name=\"selAll\" class=\"checks\" value=\""+content.id+"\" onclick=\"sel('"+content.id+"','"+content.id+"')\">"
						+"<label for=\""+content.id+"\">"+content.name+"</label>"
						+"</span></h1><ul class=\"user-list\">";
						var arr2 = content.empArr;
						var id = content.id;
						$.each(arr2, function(index, content){
							html += "<li><span class=\"checkbox\">";
							var checked = content.checked;
							if(checked == "true"){
								dbUserId += content.id+",";
								html += "<input id=\""+content.id+"\" type=\"checkbox\" name=\""+id+"\" class=\"checks\" checked=\"checked\" value=\""+content.id+"\" onclick=\"superEndDomBetter();\">"
								var labelid = content.id + "label";
								html2 += '<li data-id='+ content.id +'><span class="checkbox"><input id="'+ labelid +'" type="checkbox" name="check" class="checks" onchange="checkAllChe();"><label for="'+ labelid +'">'+ content.name +'</label></span><label for="'+ content.id +'"><a class="cancel-btn"></a></label></li>';
								count++;
							}else{
								html += "<input id=\""+content.id+"\" type=\"checkbox\" name=\""+id+"\" class=\"checks\" value=\""+content.id+"\" onclick=\"superEndDomBetter();\">"
							}
							html += "<label for=\""+content.id+"\">"+content.name+"</label></span></li>";
						});
						html+= "</ul></div>";
				});
				if(null != html2 && '' != html2){
					$(".choose-ul ul").empty().append(html2);
					$('.JS_num')[0].innerText='('+count+')';					
				}
				$("#bl_nav_zj").empty().append(html);
			}
		});
		
		//获取常用组数据
		$.ajax({
			url : 'ztree_getGroupsArrData.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				"nodeId"	: nodeId,
				"defUserId"	: defUserId,
				"mc"		: mc,
				"type"		: type,
				"userId"	: userId,
				"siteId"	: "${siteId}"
			},
			success : function(data) {
				var arr = eval('(' + data + ')');
				var html = "";
				$.each( arr, function(index, content){ 
					html += "<div class=\"tree-ul\">"
						+"<h1 class=\"tree-head\">"
						+"<span class=\"checkbox\">"
						+"<input id=\""+content.id+"group\" type=\"checkbox\"  name=\"selAll\" class=\"checks\" value=\""+content.id+"\" onclick=\"sel2('"+content.id+"group','"+content.id+"group')\">"
						+"<label for=\""+content.id+"group\">"+content.name+"</label>"
						+"</span></h1><ul class=\"user-list\">";
						var arr2 = content.empArr;
						var id = content.id+"group";
						$.each(arr2, function(index, content){
							html += "<li><span class=\"checkbox\">";
							if("" != dbUserId && dbUserId.indexOf(content.id) != -1){
								html += "<input id=\""+content.id+"group\" type=\"checkbox\" name=\""+id+"\" class=\"checks\" checked=\"checked\" value=\""+content.id+"\"  onclick=\"superEndDomBetter2();\">";
							}else{
								html += "<input id=\""+content.id+"group\" type=\"checkbox\" name=\""+id+"\" class=\"checks\" value=\""+content.id+"\"  onclick=\"superEndDomBetter2();\">";
							}
							html += "<label for=\""+content.id+"group\">"+content.name+"</label></span></li>";
						});
						html+= "</ul></div>";
				});
				$("#bl_nav_zj2").empty().append(html);
			}
		});
		
		
		//获取常用组数据
		$.ajax({
			url : 'ztree_getGroupsArrData.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				"nodeId"	: nodeId,
				"defUserId"	: defUserId,
				"mc"		: mc,
				"type"		: type,
				"userId"	: userId,
				"siteId"	: "${siteId}",
				"isRole"	: "1"
			},
			success : function(data) {
				var arr = eval('(' + data + ')');
				var html = "";
				$.each( arr, function(index, content){ 
					html += "<div class=\"tree-ul\">"
						+"<h1 class=\"tree-head\">"
						+"<span class=\"checkbox\">"
						+"<input id=\""+content.id+"group\" type=\"checkbox\"  name=\"selAll\" class=\"checks\" value=\""+content.id+"\" onclick=\"sel3('"+content.id+"group','"+content.id+"group')\">"
						+"<label for=\""+content.id+"group\">"+content.name+"</label>"
						+"</span></h1><ul class=\"user-list\">";
						var arr2 = content.empArr;
						var id = content.id+"group";
						$.each(arr2, function(index, content){
							html += "<li><span class=\"checkbox\">";
							if("" != dbUserId && dbUserId.indexOf(content.id) != -1){
								html += "<input id=\""+content.id+"group\" type=\"checkbox\" name=\""+id+"\" class=\"checks\" checked=\"checked\" value=\""+content.id+"\"  onclick=\"superEndDomBetter3();\">";
							}else{
								html += "<input id=\""+content.id+"group\" type=\"checkbox\" name=\""+id+"\" class=\"checks\" value=\""+content.id+"\"  onclick=\"superEndDomBetter3();\">";
							}
							html += "<label for=\""+content.id+"group\">"+content.name+"</label></span></li>";
						});
						html+= "</ul></div>";
				});
				$("#bl_nav_zj3").empty().append(html);
			}
		});
	}
</script>
</head>
<body>
	<input type="hidden" name="macaddr" id="macaddr" value="${mac}"/>
	<div class="content">
		<div class="left-box fl">
			<div class="tab infor-tab">
                <a class="name act" href="#tab1">流程人员</a>
                <a class="name" href="#tab2">常用组</a>
                <a class="name" href="#tab3">角色</a>
                <div class="search-box">
                    <input type="text" name="search" id="mc" class="search-input">
                    <span class="search-btn" onclick="getTreeData('${nodeId}','${defUserId}','','');">搜索</span>
                </div>
            </div>
            <div class="panel tab_container" id="inner-content-div">
                <div class="tab_content" id="tab1">
                    <div class="tree-list" id="bl_nav_zj">
                       
                    </div>
                </div>
            	<div class="tab_content" id="tab2">
                    <div class="tree-list" id="bl_nav_zj2">
                       
                    </div>
                </div>
                <div class="tab_content" id="tab3">
                    <div class="tree-list" id="bl_nav_zj3">
                       
                    </div>
                </div>
            </div>
		</div>
		<div class="n-right fl">
			<img src="${ctx}/assets-common/img/n-right.png">
		</div>
		<div class="right-box fr">
			<div class="choose-box">
				已经选择<span class="blue-color JS_num">(0)</span>
				<a href="javascript:clear();" class="blue-color clear-all fr">清除全部</a>
			</div>
			<div class="choose-ul">
				<ul>
					
				</ul>
			</div>
		</div>
	</div>
 <input type="hidden" id="ret" value=""/>
<div class="tw-search-bar tar">
	<div class="tw-top-tool" style="font-size:14px;">
		<div class="tw-btn-orange" style="font-size:14px;padding:0 8px;height:28px;padding: 5px 8px;font-size: 12px;color: #fff;background-color: #F37B1D;border-color: #F37B1D;">
			<span class="checkbox">
				<input type="checkbox" id="need_inv" class="checks" value="1" title="发短信" onchange="onSelectAllChange()">
				<label class="checkbox-label" for="need_inv">全部短信通知</label>
			</span>
		</div> 
		<div class="tw-btn-primary " style="cursor:pointer;font-size:14px;padding: 5px 8px;font-size: 12px;color: #fff;background-color: #1b74ce;border-color: #1b74ce;" onclick="sendNext();">
			<i class="tw-icon-save"></i> 确认选择
		</div>
		<div class="tw-btn" style="cursor:pointer;font-size:14px;padding: 5px 8px;font-size: 12px;color: #444;background-color: #e6e6e6;border-color: #e6e6e6;" onclick="closeSelfLayer();">
			<i class="tw-icon-remove"></i> 取消选择
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
	    $(".tab_content").hide(); 
	    $(".infor-tab .name:first").addClass("act").show(); 
	    $(".tab_content:first").show(); 
	    
	    $(".infor-tab .name").click(function() {
	        $(".infor-tab .name").removeClass("act"); 
	        $(this).addClass("act");
	        $(".tab_content").hide(); 
	        var activeTab = $(this).attr("href");
	        $(activeTab).fadeIn();
	        return false;
	    });
	
	    $("#inner-content-div").slimScroll({
	         height: '420px'
	    });
	});
	
	function closeSelfLayer(){
        window.close();
	}
	
	function getCheckedIds(){
		var ids = "@";
		$(".choose-ul li").each(function(){
			var id = $(this).attr('data-id');
			var input = $(this).find('input')[0];
			if(null != input && input.checked){
				ids = ids+id+','
			}
		});
		ids = ids.substring(0,ids.length-1);
		return ids;
	}
	
	function sendNext(){
		var neRouteType = '${routType}'*1;
		if(null != status && status=='all'){
			neRouteType = 1;
		}
		var xtoName = "";
		var xccName = "";
		if(neRouteType == 0){
			if($(".choose-ul li").length > 1){
				layer.msg("此节点类型发送人只能有一个，请重新选择！",{time:1000,icon:0});
				return false;
			}else if($(".choose-ul li").length < 1){
				layer.msg("请选择发送人！",{time:1000,icon:0});
				return false;
			}
			$(".choose-ul li").each(function(i,e){
				xtoName = $(this).attr("data-id")+",";
			});
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$("#ret").val(xtoName+getCheckedIds());
		}else if(neRouteType == 1 || neRouteType == 3 || neRouteType == 4 || neRouteType == 5 || neRouteType == 6){
			if($(".choose-ul li").length < 1){
				layer.msg("请选择发送人！",{time:1000,icon:0});
				return false;
			}
			$(".choose-ul li").each(function(i,e){
				xtoName += $(this).attr("data-id")+",";
			});
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$("#ret").val(xtoName+getCheckedIds());
		}else if(neRouteType == 2){
			if($(".choose-ul li").length < 1){
				layer.msg("请选择主送人！",{time:1000,icon:0});
				return false;
			}
			if($(".choose-ul li").length > 1){
				layer.msg("只能选择一个主送人！",{time:1000,icon:0});
				return false;
			}
			$(".choose-ul li").each(function(i,e){
				xtoName += $(this).attr("data-id")+",";
			});
			xtoName = xtoName.substring(0, xtoName.length - 1);
			$(".choose-ul-copy li").each(function(i,e){
				xccName += $(this).attr("data-id")+",";
			});
			xccName = xccName.substring( 0, xccName.length - 1);
			if(xccName == xtoName){
				layer.msg("主送人和抄送人不能为同一人",{time:1000,icon:0});
				return false;
			}
			$("#ret").val(xtoName+";"+xccName+getCheckedIds());
		}
		var sendMsg = "";
		if($("#need_inv") && $('#need_inv')[0] && $('#need_inv')[0].checked){
			sendMsg = $("#need_inv").val();
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
	
	function sel(id,name){
		var selAll = document.getElementById(id);
		var selid = document.getElementsByName(name);
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].getAttribute('disabled') == "disabled"){
				continue;
			}
			if(selAll.checked){
				
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
		
		superEndDomBetter();
	}
	
	function sel2(id,name){
		var selAll = document.getElementById(id);
		var selid = document.getElementsByName(name);
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].getAttribute('disabled') == "disabled"){
				continue;
			}
			if(selAll.checked){
				
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
		superEndDomBetter2();
	}
	
	function sel3(id,name){
		var selAll = document.getElementById(id);
		var selid = document.getElementsByName(name);
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].getAttribute('disabled') == "disabled"){
				continue;
			}
			if(selAll.checked){
				
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
		superEndDomBetter3();
	}
	
	function checkDept(){
		$.each($(".tree-ul"), function(index){
			var flag = true;
			var qxFlag = true;
			$.each($(this).find('ul li'), function(index){
				var inputObj = $(this).find('span input');
				if(inputObj[0].checked){
					flag = false;
				}
				
				if(!inputObj[0].checked){
					qxFlag = false;
				}
			});
			if(flag){
				$(this).find('h1 span input')[0].checked=false;
			}
			
			$(this).find('h1 span input')[0].checked=qxFlag;
		});
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
	
	//dom优化
	function superEndDomBetter(){
		var checkSpanArr = [];
		$.each($("#tab1 .user-list span"), function(index){
			var input = $(this).find("input")[0];
			if(input.checked){
				var id = input.value;
				var name = $(this).find('label')[0].innerText;
				var checkSpanObj = {'id':id,'name':name};
				checkSpanArr.push(checkSpanObj);
			}
		});
		$.each($("#tab2 input"), function(index){
			this.checked=false;
		});
		$.each($("#tab3 input"), function(index){
			this.checked=false;
		});
		var _html = '';
		if(checkSpanArr.length>0){
			$.each(checkSpanArr, function(i,e){
				var labelid = e.id + "label";
				_html += '<li data-id='+ e.id +'><span class="checkbox"><input id="'+ labelid +'" type="checkbox" name="check" class="checks" onchange="checkAllChe();"><label for="'+ labelid +'">'+ e.name +'</label></span><label for="'+ e.id +'"><a class="cancel-btn"></a></label></li>';
				if($("#"+ e.id + "group")){
					$("#"+ e.id + "group").attr("checked","checked");
				}
			})
			$(".choose-ul ul").empty().append(_html);
		}else{
			$(".choose-ul ul").empty();
		}
		
		$('.JS_num')[0].innerText='('+checkSpanArr.length+')';
		
		checkDept();
	}
	
	//dom优化
	function superEndDomBetter2(){
		var checkSpanArr = [];
		$.each($("#tab2 .user-list span"), function(index){
			var input = $(this).find("input")[0];
			if(input.checked){
				var id = input.value;
				var name = $(this).find('label')[0].innerText;
				var checkSpanObj = {'id':id,'name':name};
				checkSpanArr.push(checkSpanObj);
			}
		});
		$.each($("#tab1 input"), function(index){
			this.checked=false;
		});
		$.each($("#tab3 input"), function(index){
			this.checked=false;
		});
		var _html = '';
		if(checkSpanArr.length>0){
			$.each(checkSpanArr, function(i,e){
				var labelid = e.id + "label";
				_html += '<li data-id='+ e.id +'><span class="checkbox"><input id="'+ labelid +'" type="checkbox" name="check" class="checks" onchange="checkAllChe();"><label for="'+ labelid +'">'+ e.name +'</label></span><label for="'+ e.id +'"><a class="cancel-btn"></a></label></li>';
				if($("#"+ e.id)){
					$("#"+ e.id).attr("checked","checked");
				}
			})
			$(".choose-ul ul").empty().append(_html);
		}else{
			$(".choose-ul ul").empty();
		}
		
		$('.JS_num')[0].innerText='('+checkSpanArr.length+')';
		
		checkDept();
	}
	
	//dom优化
	function superEndDomBetter3(){
		var checkSpanArr = [];
		$.each($("#tab3 .user-list span"), function(index){
			var input = $(this).find("input")[0];
			if(input.checked){
				var id = input.value;
				var name = $(this).find('label')[0].innerText;
				var checkSpanObj = {'id':id,'name':name};
				checkSpanArr.push(checkSpanObj);
			}
		});
		$.each($("#tab1 input"), function(index){
			this.checked=false;
		});
		$.each($("#tab2 input"), function(index){
			this.checked=false;
		});
		var _html = '';
		if(checkSpanArr.length>0){
			$.each(checkSpanArr, function(i,e){
				var labelid = e.id + "label";
				_html += '<li data-id='+ e.id +'><span class="checkbox"><input id="'+ labelid +'" type="checkbox" name="check" class="checks" onchange="checkAllChe();"><label for="'+ labelid +'">'+ e.name +'</label></span><label for="'+ e.id +'"><a class="cancel-btn"></a></label></li>';
				if($("#"+ e.id)){
					$("#"+ e.id).attr("checked","checked");
				}
			})
			$(".choose-ul ul").empty().append(_html);
		}else{
			$(".choose-ul ul").empty();
		}
		
		$('.JS_num')[0].innerText='('+checkSpanArr.length+')';
		
		checkDept();
	}
	
	
	function clear(){
		$(".choose-ul ul").empty();
		$.each($(".tree-ul input"), function(index){
			$(this)[0].checked=false;
		});
		$('.JS_num')[0].innerText='(0)';
	}
	
	function onSelectAllChange(){
		$.each($(".choose-ul ul input"),function(index){
			$(this)[0].checked = $('#need_inv')[0].checked;
		});
	}
	
	function checkAllChe(){
		var flag = true;
		$.each($(".choose-ul ul input"),function(index){
			if(flag){
				flag = $(this)[0].checked;
			}
		});
		$('#need_inv')[0].checked = flag;
	}
	
	document.onkeyup = function (e) {//按键信息对象以函数参数的形式传递进来了，就是那个e  
	    var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)  
	    if (code == 13) {  
	    	getTreeData('${nodeId}','${defUserId}','','');
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
