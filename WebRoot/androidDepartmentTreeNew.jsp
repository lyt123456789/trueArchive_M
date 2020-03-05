<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<base target="_self">
<%@ include file="/common/header.jsp"%>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="x5-fullscreen" content="true">
<meta name="x5-page-mode" content="app">
<meta name="format-detection" content="telephone=no, address=no, email=no">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<!--  告诉浏览器，页面用的是UTF-8编码 -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets-common/css/reset-tree-mobile.css" />
<script src="${ctx}/widgets/zTree/assets/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/widgets/zTree/assets/js/GL.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/widgets/zTree/assets/js/jquery.arrayUtilities.min.js" type="text/javascript" charset="utf-8"></script>
<!--原人员树通用库-->
<script src="${ctx}/widgets/zTree/assets/js/common-ui.js" type="text/javascript" charset="utf-8"></script>
<!--滚动条-->
<script src="${ctx}/widgets/zTree/assets/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<!-- 引入layer  -->
<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		getTreeData('${nodeId}','${defUserId}','','${userId}');
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
						+"<div class=\"tree-head\">"
						+"<span class=\"checkbox\">"
						+"<input id=\""+content.id+"\" type=\"checkbox\"  name=\"selAll\" class=\"checks\" value=\""+content.id+"\" onclick=\"sel('"+content.id+"','"+content.id+"')\">"
						+"<label for=\""+content.id+"\">"+content.name+"</label>"
						+"</span></div><ul class=\"user-list\">";
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
				"userId"	: userId
			},
			success : function(data) {
				var arr = eval('(' + data + ')');
				var html = "";
				$.each( arr, function(index, content){ 
					html += "<div class=\"tree-ul\">"
						+"<div class=\"tree-head\">"
						+"<span class=\"checkbox\">"
						+"<input id=\""+content.id+"group\" type=\"checkbox\"  name=\"selAll\" class=\"checks\" value=\""+content.id+"\" onclick=\"sel2('"+content.id+"group','"+content.id+"group')\">"
						+"<label for=\""+content.id+"group\">"+content.name+"</label>"
						+"</span></div><ul class=\"user-list\">";
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
		
		
		//获取角色数据
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
				"isRole"	: "1"
			},
			success : function(data) {
				var arr = eval('(' + data + ')');
				var html = "";
				$.each( arr, function(index, content){ 
					html += "<div class=\"tree-ul\">"
						+"<div class=\"tree-head\">"
						+"<span class=\"checkbox\">"
						+"<input id=\""+content.id+"group\" type=\"checkbox\"  name=\"selAll\" class=\"checks\" value=\""+content.id+"\" onclick=\"sel3('"+content.id+"group','"+content.id+"group')\">"
						+"<label for=\""+content.id+"group\">"+content.name+"</label>"
						+"</span></div><ul class=\"user-list\">";
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
	<div class="content">
		<div class="tab infor-tab">
            <a class="name act" href="#tab1">所有</a>
            <a class="name" href="#tab2">常用</a>
            <a class="name" href="#tab3">角色</a>
        </div>
        <div class="mui-search">
            <input type="search" id='mc' class="mui-input-clear" placeholder="搜索">
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
        <div class="right-box fr" style="display:none;">
			<div class="choose-ul">
				<ul>
					
				</ul>
			</div>
		</div>
	</div>
    <div class="check-info">
        <span class="checkbox">
            <input id="chooseAll" type="checkbox" name="check" class="checks" onclick="selAll();">
            <label for="chooseAll">全选</label>
        </span>
        <span class="ml20">已选择<span class="blue-color JS_num"></span>人</span>
       <!--  <button class="fixed-btn">完成</button> -->
    </div>
</body>
<script type="text/javascript" src="${ctx}/widgets/zTree/assets/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $(".tab_content").hide(); 
        $(".infor-tab .name:first").addClass("act").show(); 
        $(".tab_content:first").show(300); 
        
        $(".infor-tab .name").click(function() {
            $(".infor-tab .name").removeClass("act"); 
            $(this).addClass("act");
            $(".tab_content").hide(); 
            var activeTab = $(this).attr("href");
            $(activeTab).fadeIn();
            return false;
        });
    });
</script>
<script type="text/javascript">
	function getValue(){
		var persons = new Array();
		$.each(_globalObject.selectedList,function(i,e){
			if(e && e!==null && e!==''){
				persons.push({id:e,name:_globalObject.nameList[e]});
			}
		});
		return JSON.stringify(persons);
	}
	
	function cdv_getvalues(){
		var ids = "";
		if("${routType}"=="2"){
			$(".choose-ul li").each(function(i,e){
				if(i==0){
					ids += $(this).attr("data-id")+";";
				}else{
					ids += $(this).attr("data-id")+",";
				}
			});
			ids = ids.substring(0,ids.length-1);
		}else{
			$(".choose-ul li").each(function(i,e){
				if(e && e!==null && e!==''){
					ids += $(this).attr("data-id")+",";
				}
			});
			ids = ids.substring(0,ids.length-1);
		}
		return ids;
	}
</script>
<script type="text/javascript">
	function selAll(){
		var selAll = $('#chooseAll');
		var selid = $('input[type=checkbox]');
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].getAttribute('disabled') == "disabled"){
				continue;
			}
			if(selAll[0].checked){
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
		
		superEndDomBetter();
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
				$(this).find('.tree-head span input')[0].checked=false;
			}
			
			$(this).find('.tree-head span input')[0].checked=qxFlag;
		});
	}
	
	function editGroup(){
		var link = document.createElement("a");
		link.href = '${ctx}/ztree_toSetUserGroup.do?nodeId=${nodeId}&type=${type}';
		document.body.appendChild(link);
		link.click();
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
	    	$('.JS_num')[0].innerText='(0)';
	    	$(".choose-ul ul").empty();
	    	getTreeData('${nodeId}','${defUserId}','','${userId}');
	    }  
	}
</script>
</html>