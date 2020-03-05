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
	    	line-height: 0px;
	    	height: 0px;
	    }
	      .wf-search-bar {
	    	padding: 0px 0px 0px 10px;
	    }
	    .btn {
		    height: 25px;
		    line-height: 25px;
		}
		.fa-plus {
			margin-top: 3px;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="" method="post" style="display:inline-block;width: 100%;">
			    <input type="hidden" id="treeNodes1" name="treeNodes1" value="${treeNodes1}"/>
			    <input type="hidden" id="treeNodes1" name="treeNodes1" value="${treeNodes2}"/>
			    <div class="search">  
			      <span style="float: left;font-size: 17px;margin-right: 30px;">未创建索引库的节点</span> 	
			    	<button type="button" id="addnum" class="btn btn-add" style="float:left">
			    		<i class="fa fa-plus"></i>创建
			    	</button>
		    
		    	<!-- <button type="button" onclick="javascript:detailed();" target="_self" class="btn btn-detail" style="float:left">
  						<i class="fa fa-file-text-o"></i>重建
  						</button> -->	
	            </div>
			</form>
		</div>
	</div>
	
	
	<div class="table" style="overflow: auto;">
		<div>
		    <table cellspacing="0" cellpadding="0">
		  
		    	<tr class="fr_tr">	
		 			<td style="width:25px;"><div class="checkbox"><input type="checkbox" name="checkboxAll" onclick="chooseAll(this);"/></div></td>
		        	<td>索引节点名称</td>
		        	<td>索引节点路径</td>
		        </tr>    
		        <c:forEach items="${indexNoList}" var="data" varStatus="status">
		        	<tr>
		    			<td>
							<input type="checkbox" name="selid" id=""  value="${data[1]}"  >
						</td>
						<td>${data[2]}</td>
						<td>${data[3]}</td>
		    		</tr>
		    	</c:forEach>
		    </table>
		</div>
	</div>
	
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/ftm_showFUllTextIndexNoList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	$(document).ready(function (){
		$(".table").css("height",$(window).height()-80);
	});
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
			  ids += "'"+objs[i].value+"',";
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm('确定删除所选数据吗')){
			$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"ids":ids},
			        url:"${ctx}/ftm_deleteIndex.do",
			        success:function(text){
			        	if(text=="success"){
			        		window.location.reload();
			        	} 
			        }
			    })
		}
	}

	
	$("#addnum").bind("click",function(){
		alert("后台创建索引中,创建完成后消息提示您。。。")
		var objs = document.getElementsByTagName('input');
		var ids = '';
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
			  ids += objs[i].value+",";
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"treeIds":ids},
	        url:"${ctx}/ftm_createIndex.do",
	        success:function(text){
	        	//
	        }
	    })
	});

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