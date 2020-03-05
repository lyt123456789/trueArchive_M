<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
	<style>
		.search {
			height:35px;
			line-height:35px;
		} 
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/str_toStructureTempPage.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
				<div class="search">
					<c:if test="${'1' ne quoteFlag}">
						<button type="button" onclick="javascript:chooseStructure();" class="btn btn-add" style="float:left">
				    		<i class="fa fa-plus"></i>选择模板
				    	</button>
					</c:if>
			    	<c:if test="${'1' eq quoteFlag}">
				    	<button type="button" onclick="javascript:quoteStructure();" class="btn btn-add" style="float:left">
				    		<i class="fa fa-plus"></i>引用
				    	</button>
			    	</c:if>
			    	
    				<div class="fr">
        				<input type="text" id="esIdentifier" name="esIdentifier" value="${esIdentifier }" placeholder="输入模板名称查询">
        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
        				</button>
    				</div>
	            </div>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
			<thead>
				<tr>
					<th width="4%"></th>
	    	  		<th align="center" width="16%">操作</th>
	    	  		<th align="center" width="20%">所属业务</th>
	    	  		<th align="center" width="20%">模板类型</th>
	    	  		<th align="center" width="20%">模板名称</th>
	    	  		<th align="center" width="20%">模板描述</th>
	            </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="data" varStatus="status">
	    		<tr>
	    			<td align="center" >
						<input type="checkbox" name="strTemplate" id="${data.id}"  value="${data.id}"  >
					</td>
					<td align="center" title="查看著录项">
						<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;" onclick="javascript:setZLX('${data.id}','${data.esType}');">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">
        				</button>				
					</td>
					<td align="center"  title ="${data.esBusinessName}" >${data.esBusinessName }</td>
					<td align="center"  title ="${data.esType}" >
						<c:choose>
							<c:when test="${data.esType == 'innerFile'}">
								卷内-文件
							</c:when>
							<c:when test="${data.esType == 'file'}">
								案卷-卷内-文件
							</c:when>
							<c:otherwise>
							   	项目-案卷-卷内-文件                         
							</c:otherwise>
						</c:choose>
					</td>
					<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
					<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
	    		</tr>
	    	</c:forEach>
		</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/str_toStructureTempPage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	$(document).ready(function (){
		$(".table").css("height",$(window).height()-140);
	});
	
	function chooseStructure(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='strTemplate' && objs[i].checked==true ){
			  ids += objs[i].value;
			  n ++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		layer.load();
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids},
	        url:"dataTemp_addChooseStructure.do?nodeId=${nodeId}&modelId="+ids,
	        success:function(data){
	        	layer.closeAll('loading');
				if(data=="success"){
					parent.refreshTreeNode("${parentId}");
					parent.layer.closeAll();
				}else{
					layer.msg("关联失败")
				}
	        },error:function () {
            	layer.closeAll('loading');
                layer.msg("关联失败")
            }
	    });
	}
	function quoteStructure(){
		var objs = document.getElementsByTagName('input');
		var nodeId = parent.document.getElementById('nodeId').value;
		var parentId = parent.document.getElementById('parentId').value;
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='strTemplate' && objs[i].checked==true ){
			  ids += objs[i].value;
			  n ++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		layer.load();
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids},
	        url:"dataTemp_addQuoteStructure.do?quoteNodeId=${nodeId}&modelId="+ids+"&nodeId="+nodeId,
	        success:function(data){
	        	layer.closeAll('loading');
				if(data=="success"){
					parent.parent.refreshTreeNode(parentId);
					parent.parent.layer.closeAll();
				}else{
					layer.msg("引用失败")
				}
	        },error:function () {
            	layer.closeAll('loading');
                layer.msg("引用失败")
            }
	    });
	}
	function setZLX(modelId,esType){
		top.layer.open({
			title:'查看著录项',
			type:2,
			area:['85%','85%'],
			//closeBtn:0,
			content:"${ctx}/str_toSetModelTagsMainPage.do?modelId="+modelId+"&esType="+esType+"&onlyLook=1"
		});
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
</html>