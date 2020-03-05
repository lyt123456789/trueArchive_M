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
		.td{
			font-size: 16px;
		    color: #333333;
		    overflow: hidden;
		    font-family: "Microsoft Yahei";
		    padding: 8px 5px;
		    border-bottom: solid 1px #e4eef5;
		    vertical-align: middle;
		    line-height: 1.5;
		}
		.new-htable {
			margin-top:20px;
			width:96%;
			margin-left:3%;
		}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;
		}
		.new-htable .tw-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:115px!important;
			min-width: 100px!important;
			max-width: 120px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		.w-auto-10 {
			width: 9% !important;
			min-width: 9% !important;
		}
		.wf-form-label{
			margin-left: 0px;
		}
		
		.auto-date-width{
			width:120px!important;
		}
		.high-search-btn{
			font-size:14px;
			color:#4284ce;
			margin-left:10px;
		}
		#high-search{
			top:0;
		}
		.search {
			height:35px;
			line-height:35px;
		}
		.fts {
			font-size:14px;
		} 
		.btn-meta-data {
		    color: rgb(255, 255, 255);
		    background: #fbc756;
		    border-width: 1px;
		    border-style: solid;
		    border-color:#fbc756;
		    border-image: initial;
		}
	</style>
	<script>
	function cutString(str, len) {
		 debugger;
		 //length属性读出来的汉字长度为1
		 if(str.length*2 <= len) {
			 document.write(str);
		  return;
		 }
		 var strlen = 0;
		 var s = "";
		 for(var i = 0;i < str.length; i++) {
		  s = s + str.charAt(i);
		  if (str.charCodeAt(i) > 128) {
		   strlen = strlen + 2;
		   if(strlen >= len){
			   document.write(s.substring(0,s.length-1) + "...");
		    return;
		   }
		  } else {
		   strlen = strlen + 1;
		   if(strlen >= len){
		    return s.substring(0,s.length-2) + "...";
		   }
		  }
		 }
		 document.write(s);
		 return;
		}
	</script>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar" <c:if test="${tempFlag eq '1'}">style="padding:0px 0px 0px 10px"</c:if>>
			<form name="lendLingList"  id="lendLingList" action="${ctx }/met_toMetaDataPage.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
				<div class="search">
					<c:if test="${tempFlag ne '1'}">
						<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-plus"></i>添加
				    	</button>
				    	<button type="button" onclick="javascript:update();" target="_self" class="btn btn-write" style="float:left">
				    		<i class="fa fa-pencil-square-o fa-lg"></i>修改
				    	</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
	    					<i class="fa fa-trash-o fa-lg"></i>删除
	    				</button>
					</c:if>
    				<div class="fr">
    					<input type="hidden" id="tempFlag" name="tempFlag" value="${tempFlag }"/>
    					<input type="hidden" id="nameSpaceId" name="nameSpaceId" value="${nameSpaceId }"/>
        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="输入元数据标题查询">
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
	    	  		<th width="2%"></th>
	    	  		<th align="center" width="15%">名称</th>
	    	  		<th align="center" width="15%">唯一标识</th>
	    	  		<th align="center" width="14%">类型</th>
	    	  		<th align="center" width="14%">是否参与检索</th>
	    	  		<th align="center" width="14%">默认值</th>
	    	  		<th align="center" width="12%">是否固定值</th>
	    	  		<th align="center" width="14%">描述</th>
                </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="data" varStatus="status">
	    		<tr>
	    			<td align="center" >
						<input type="checkbox" name="id" id="${data.id}" data_name="${data.esIdentifier}"  value="${data.id}"  >
					</td>
					<td align="center"  title ="${data.esTitle}" >${data.esTitle }</td>
					<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
					<td align="center"  title ="${data.esType}" >${data.esType}</td>
					<td align="center">
						<c:if test="${data.esIsMetaDataSearch eq '0' }">是</c:if>
						<c:if test="${data.esIsMetaDataSearch eq '1' }">否</c:if>
					</td>
					<td align="center"  title ="${data.metaDefaultValue}" >${data.metaDefaultValue}</td>
					<td align="center">
						<c:if test="${data.isFixedValue eq '0' }">是</c:if>
						<c:if test="${data.isFixedValue eq '1' }">否</c:if>
					</td>
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
		skipUrl="<%=request.getContextPath()%>"+"/met_toMetaDataPage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	$("#addTemplate").bind("click",function(){
		layer.open({
			title:'新增元数据',
			type:2,
			area:['500px','530px'],
			closeBtn:0,
			content:"${ctx}/met_toMetaDataAddPage.do?nameSpaceId=${nameSpaceId}"
		});
	});
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='id' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n++;
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
		        dataType:"json",
		        data:{"ids":ids},
		        url:"${ctx}/met_deleteMetaDataRecord.do",
		        success:function(data){
		        	 var sfv = data.flag;
						if(sfv=="success"){
							window.location.reload();
						}else{
							alert("删除失败");
						}
		        }
		    })
		}
	}
	
	function update(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='id' && objs[i].checked==true ){
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
		 openPending("修改命名空间","/met_toMetaDataEditPage.do?id="+ids);
	}
	
	function openPending(title,url){
		layer.open({
		    type: 2,
		    title: title,
		    shade: 0.4,
		    area: ['900px','530px'],
		    content: "${ctx}" + url,
		    cancel: function(){
		        //右上角关闭回调
		   		window.location.reload();
		   }
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