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
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" method="post" style="display:inline-block;width: 100%;">
			    <div class="search">
			    	<button type="button" id="addnum" class="btn btn-add" style="float:left">
			    		<i class="fa fa-plus"></i>添加
			    	</button>
			    	<button type="button" onclick="javascript:update();" target="_self" class="btn btn-write" style="float:left">
			    		<i class="fa fa-pencil-square-o fa-lg"></i>修改
			    	</button>
    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
    					<i class="fa fa-trash-o fa-lg"></i>删除
    				</button>
	            </div>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width: 100%;">
			<thead>
				<tr>
	    	  		<th width="4%"></th>
	    	  		<th align="center" width="16%">角色名称</th>
	    	  		<th align="center" width="26%">角色描述</th>
	    	  		<th align="center" width="8%">序号</th>
	    	  		<th align="center" width="13%">创建时间</th>
	    	  		<th align="center" width="13%">修改时间</th>
	    	  		<th align="center" width="20%">操作</th>
                   </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="data" varStatus="status">
	    		<tr>
	    			<td align="center" >
						<input type="checkbox" name="role" id="${data.id}"  value="${data.id}"  >
					</td>
					<td align="center"  title ="${data.name}" >${data.name}</td>
					<td align="center"  title ="${data.roleDescibe}" >${data.roleDescibe}</td>
					<td align="center"  title ="${data.roleIndex}" >${data.roleIndex}</td>
					<td align="center"  title ="${data.createTime}" >${data.createTime}</td>
					<td align="center"  title ="${data.changeTime}" >${data.changeTime}</td>
					<td align="center">
						<button type="button" class="btn btn-add" onclick="addMenu(this)" style="float:left" data_roloid="${data.id}">
			    			<i class="fa fa-plus"></i>配置菜单
			    		</button>
			    		<button type="button" class="btn btn-add" onclick="addUser(this)" style="float:left" data_roloid="${data.id}">
			    			<i class="fa fa-plus"></i>配置用户
			    		</button>
					</td>
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
		skipUrl="<%=request.getContextPath()%>"+"/role_toRoleManagePage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	$("#addnum").bind("click",function(){
		layer.open({
			title:'添加角色',
			type:2,
			area:['460px','280px'],
			content:"${ctx}/role_toRoleManageAddPage.do"
		});
	});
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='role' && objs[i].checked==true ){
			  ids += objs[i].value+",";
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm('确定删除所选数据吗')){
			//异步获取上传成功后的doc信息
			$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"ids":ids},
			        url:"${ctx}/role_delRole.do",
			        success:function(text){
			        	 window.location.reload();
			        }
			    })
		}
	}
	
	function update(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='role' && objs[i].checked==true ){
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
		 openPending("修改角色","/role_toUpdateRole.do?id="+ids);
	}
	
	function openPending(title,url){
		layer.open({
		    type: 2,
		    title: title,
		    shade: 0.4,
		    area: ['460px','280px'],
		    content: "${ctx}" + url,
		    cancel: function(){
		        //右上角关闭回调
		   	 window.location.reload();
		   }
		});
	}
	
	//打开配置菜单页面  
	function addMenu(dom) {
		var roleId = $(dom).attr("data_roloid");
		layer.open({
		    type: 2,
		    title: "角色菜单配置",
		    shade: 0.4,
		    area: ['750px','580px'],
		    content: "${ctx}" + "/role_toSetRoleMenu.do?roleId="+roleId,
		});
	}
	
	//打开配置员工页面
	function addUser(dom) {
		var roleId = $(dom).attr("data_roloid");
		layer.open({
		    type: 2,
		    title: "角色人员配置",
		    shade: 0.4,
		    area: ['30%','85%'],
		    content: "${ctx}" + "/role_toSetRoleEmployee.do?roleId="+roleId,
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