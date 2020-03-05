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
		body {
			width:450px;
			height:450px;
			overflow-y: auto;
		}
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
		.wf-fixtable-scroller::-webkit-scrollbar {/*滚动条整体样式*/
	        width: 5px;     /*高宽分别对应横竖滚动条的尺寸*/
	        height: 1px;
		}
		.wf-fixtable-scroller::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
	        border-radius: 13px;
	        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
	        background: #00BFFF;
		}
		.wf-fixtable-scroller::-webkit-scrollbar-track {/*滚动条里面轨道*/
	        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
	        border-radius: 13px;
	        background: #E0FFFF;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/met_toMetaDataPage.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
				<div class="search">
			    	<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
			    		<i class="fa fa-plus"></i>添加
			    	</button>
			    	<button type="button" onclick="javascript:update();" target="_self" class="btn btn-write" style="float:left">
			    		<i class="fa fa-pencil-square-o fa-lg"></i>修改
			    	</button>
    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
    					<i class="fa fa-trash-o fa-lg"></i>删除
    				</button>
    				<input type="hidden" id="metaDataId" name="metaDataId" value="${metaDataId }"/>
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
	    	  		<th align="center" width="32%">属性值</th>
	    	  		<th align="center" width="32%">代码值</th>
	    	  		<th align="center" width="32%">描述</th>
                </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="data" varStatus="status">
	    		<tr>
	    			<td align="center" >
						<input type="checkbox" name="id" id="${data.id}"  value="${data.id}"  >
					</td>
					<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier }</td>
					<td align="center"  title ="${data.esTitle}" >${data.esTitle}</td>
					<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
	    		</tr>
	    	</c:forEach>
		</table>
	</div>
</div>
</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	$("#addTemplate").bind("click",function(){
		layer.open({
			title:'新增元数据属性',
			type:2,
			area:['400px','268px'],
			closeBtn:0,
			content:"${ctx}/met_toMeDaPropertyAddPage.do?metaDataId=${metaDataId }"
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
		        url:"${ctx}/met_deleteMeDaProPertyReocrd.do",
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
		 openPending("修改元数据属性","/met_toMeDaPropertyEditPage.do?id="+ids);
	}
	
	function openPending(title,url){
		layer.open({
		    type: 2,
		    title: title,
		    shade: 0.4,
		    area:['400px','268px'],
		    content: "${ctx}" + url,
		    cancel: function(){
		        //右上角关闭回调
		   		window.location.reload();
		   }
		});
	}
	</script>
</html>