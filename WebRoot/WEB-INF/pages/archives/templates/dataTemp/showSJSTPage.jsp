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
    <link rel="stylesheet" href="${ctx}/dtree/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/font/dtreefont.css">
   <style type="text/css">
	   .wf-search-bar {
		    padding: 8px 0px 0px 10px;
	    }
	    .search {
		    height: 0px;
		}
		.layui-tab {
		    margin: 4px 0;
		}
   </style>

</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/dataTemp_toShowSJSTPage.do?treeId=${treeId}" method="post" style="display:inline-block;width: 100%;">
				<input type="hidden" id="treeId" name="treeId" value="${treeId}"/>
				<div class="search">
				    <span style="float: left">子节点信息</span>
				    <c:if test="${ empty structureId}">
					    <button type="button" onclick="add();" class="btn btn-add" style="float:left">
				    		<i class="fa fa-plus"></i>添加
				    	</button>
				    	<button type="button" onclick="update();" class="btn btn-write" style="float:left">
				    		<i class="fa fa-pencil-square-o fa-lg"></i>修改
				    	</button>
	    				<button type="button" onclick="del();" target="_self" class="btn btn-danger" style="float:left">
	    					<i class="fa fa-trash-o fa-lg"></i>删除
	    				</button>
				    </c:if>
	            </div>
			</form>
		</div>
	</div>
	
	<div class="table" style="overflow: auto;">
		<div>
		    <table cellspacing="0" cellpadding="0">
		    	<tr class="fr_tr">	
		        	<td style="width:25px;"><div class="checkbox"><input type="checkbox" name="checkboxAll" onclick="chooseAll(this);"/></div></td>
			    	<td>排序号</td>
			    	<td>名称</td>
		        </tr> 
		        <c:forEach items="${treeList}" var="tree" varStatus="status">
		        	<tr>
		    			<td>
							<input type="checkbox" name="selid"  value="${tree.id}"  >
						</td>
						<td>${tree.esOrder}</td>
						<td>${tree.title}</td>	
		    		</tr>
		    	</c:forEach>
		    </table>
		</div>
	</div>
	
	<div class="wf-list-ft" id="pagingWrap"></div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/dataTemp_toShowSJSTPage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	$(document).ready(function (){
		$(".wf-layout").css("height",$(window).height());
		$(".table").css("height",$(window).height()-$(".wf-list-top").height()-45);
	});
	
	function add(){		
		layer.open({
	           type: 2,
	           title: "新增节点",
	           shadeClose: true,
	           shade: 0.4,
	           area: ['500px', '300px'],
	           content: "${ctx}/dataTemp_toEditeSJSTPage.do?editFlag=add&parentId=${treeId}",
	           cancel: function(){
	           }
		});
	}
	function del(){
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
		//校验节点下是否录入收据
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids},
	        url:"${ctx}/dataTemp_checkDeleteNode.do",
	        success:function(text){
	        	layer.confirm(text, { btn: ['确定','取消'] //按钮
		    	}, function(){
					layer.closeAll('dialog');
					layer.load();
					$.ajax({
				        async:true,//是否异步
				        type:"POST",//请求类型post\get
				        cache:false,//是否使用缓存
				        dataType:"text",//返回值类型
				        data:{"ids":ids},
				        url:"${ctx}/dataTemp_deleteTreeNode.do",
				        success:function(text){
				        	window.parent.parent.refreshTreeNode("${treeId}");
				        	window.location.reload();
				        }
				    })
				});		
	        }
	    });
	}
	
	function update(){
		var objs = document.getElementsByTagName('input');
		var id = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
			  id += objs[i].value;
			  n ++;
		   }
		}
		if(id==""){
			alert("请选择一条数据");
			return;
		}else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		layer.open({
	           type: 2,
	           title: "修改节点",
	           shadeClose: true,
	           shade: 0.4,
	           area: ['500px', '300px'],
	           content: "${ctx}/dataTemp_toEditeSJSTPage.do?editFlag=update&treeId="+id,
	           cancel: function(){
	           }
		 });
	}
	
	function chooseAll(obj){
		var choose = document.getElementsByName("selid");
		if(obj.checked){
			for(var i=0;i<choose.length;i++){
				choose[i].checked=true;
			}
		}else{
			for(var i=0;i<choose.length;i++){
				choose[i].checked=false;
			}
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
</html>