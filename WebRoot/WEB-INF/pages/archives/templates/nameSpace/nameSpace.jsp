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
		
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/met_toNameSpacePage.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
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
    				<button type="button" onclick="javascript:showMetaData();" target="_self" class="btn btn-meta-data" style="float:left">
    					<i class="fa fa-file-text-o"></i>编辑元数据
    				</button>
    				<div class="fr">
        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="输入命名空间名称查询">
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
	    	  		<th align="center" width="14%">作者</th>
	    	  		<th align="center" width="14%">名称</th>
	    	  		<th align="center" width="24%">描述文件位置</th>
	    	  		<th align="center" width="14%">描述</th>
	    	  		<th align="center" width="12%">编号</th>
	    	  		<th align="center" width="12%">日期</th>
	    	  		<th align="center" width="8%">版本</th>
                   </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="data" varStatus="status">
	    		<tr>
	    			<td align="center" >
						<input type="checkbox"  name="nameSpaceId"  value="${data.id}" class="check_view_state" id="${data.id}" style="display: none;">
						<label for="${data.id}"></label>
					</td>
					<td align="center"  title ="${data.esCreator}" >${data.esCreator }</td>
					<td align="center"  title ="${data.esTitle}" >${data.esTitle}</td>
					<td align="center"  title ="${data.esUrl}" >${data.esUrl}</td>
					<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
					<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
					<td align="center"  title ="${data.esDate}" >${data.esDate}</td>
					<td align="center"  title ="${data.esVersion}" >${data.esVersion}</td>
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
		skipUrl="<%=request.getContextPath()%>"+"/met_toNameSpacePage.do";			//提交的url,必须修改!!!*******************
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
			title:'新增命名空间',
			type:2,
			area:['500px','510px'],
			closeBtn:0,
			content:"${ctx}/met_toNameSpaceAddPage.do?"
		});
	});
	
	//展示元数据
	function showMetaData() {
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='nameSpaceId' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		} else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		var index = layer.open({
			type : 2,
			shade: 0.4,
			title : "元数据",
			content : "${ctx}/met_toMetaDataPage.do?nameSpaceId="+ids,
			cancel: function(){
		        //右上角关闭回调
		   		window.location.reload();
		    }
		})
		// 改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function() {
			layer.full(index);
		})
		layer.full(index);
	}
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='nameSpaceId' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		} else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm('确定删除所选数据吗')){
			$.ajax({
				url:"${ctx}/met_checkNameSpaceDelete.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:{"id":ids},
				success:function(data){
					var sfv = data.flag;
					if(sfv=="delete"){
						// 验证
						$.ajax({
					        async:true,//是否异步
					        type:"POST",//请求类型post\get
					        cache:false,//是否使用缓存
					        dataType:"json",
					        data:{"ids":ids},
					        url:"${ctx}/met_deleteNameSpace.do",
					        success:function(data){
					        	 var sfv = data.flag;
									if(sfv=="yes"){
										window.location.reload();
									}else{
										alert("删除失败");
									}
					        }
					    })
					}else{
						alert("不可删除");
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			});
		}
	}
	
	function update(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='nameSpaceId' && objs[i].checked==true ){
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
		 openPending("修改命名空间","/met_toNameSpaceEditPage.do?id="+ids);
	}
	
	function openPending(title,url){
		layer.open({
		    type: 2,
		    title: title,
		    shade: 0.4,
		    area: ['500px','510px'],
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