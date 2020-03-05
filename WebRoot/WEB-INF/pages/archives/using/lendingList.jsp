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
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/using_showLendingList.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
				<c:if test="${'1' eq applyFlag }">
					<input type="hidden" name="statusSe" value="${status }"/>
				</c:if>
				<input type="hidden" name="applyFlag" value="${applyFlag }"/>
				<div class="search">
					<c:if test="${'1' ne applyFlag }">
				    	<button type="button" id="addnum" class="btn btn-add" style="float:left">
				    		<i class="fa fa-plus"></i>添加
				    	</button>
				    </c:if>
			    	<button type="button" onclick="javascript:update();" target="_self" class="btn btn-write" style="float:left">
			    		<i class="fa fa-pencil-square-o fa-lg"></i>修改
			    	</button>
    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
    					<i class="fa fa-trash-o fa-lg"></i>删除
    				</button>
    				<button type="button" onclick="javascript:detailed();" target="_self" class="btn btn-detail" style="float:left">
    					<i class="fa fa-file-text-o"></i>借阅单明细
    				</button>
    				<c:if test="${'1' ne applyFlag }">
    					<button type="button"  id="addnumjs" onclick="javascript:searchData()" class="btn btn-js" style="float:left">
							<img src="${ctx}/img/sear_ico.png" style="vertical-align:middle;margin-right:5px;width:15px;height:15px;">检索
						</button>
	    				<button type="button" onclick="javascript:showFjList();" target="_self" class="btn btn-fj" style="float:left">
	    					<i class="fa fa-chain"></i>附件操作
	    				</button>
	    				<!-- <button type="button" onclick="javascript:;" target="_self" class="btn btn-print" style="float:left">
	    					<i class="fa fa-print fa-lg"></i>报表打印
	    				</button> -->
	    				<select id="statusSe" name="statusSe" class="sel" style="float:left">
	        				<option value=""  <c:if test="${status=='' || status == null }"> selected="selected"</c:if> >全部</option>
							<option value="1" <c:if test="${status=='1'}"> selected="selected"</c:if>>待审核</option>
							<option value="2" <c:if test="${status=='2'}"> selected="selected"</c:if>>已审核</option>
							<option value="3" <c:if test="${status=='3'}"> selected="selected"</c:if>>已结束</option>
	    				</select>
	    				<div class="fr">
	        				<input type="text" id="contect" name="contect" value="${contect}" placeholder="输入关键字查询">
	        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
	        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
	        				</button>
	    				</div>
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
		        	<c:forEach items="${nodeList}" var="item" varStatus="statu">
			    		<td>${item.vc_name }</td>
			    	</c:forEach>	
		        </tr>
		        
		        <c:forEach items="${valList}" var="data" varStatus="status">
		        	<tr>
		    			<td>
							<input type="checkbox" name="selid" id="${data[0]}"  value="${data[0]}"  >
						</td>
						<c:forEach var="i" begin="1" end="${fn:length(nodeList)}" step="1">
							<td><script>cutString('${data[i]}',40)</script></td>
							<%-- <td>${data[i]}</td> --%>
						</c:forEach>
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
		skipUrl="<%=request.getContextPath()%>"+"/using_showLendingList.do";			//提交的url,必须修改!!!*******************
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
		//校验是否是自己的借阅单或者自主查档的借阅单
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids,"checkType":"delete"},
	        url:"${ctx}/using_checkOwnerForm.do",
	        success:function(text){
				if(text=="no"){
					alert("不能对其他人的借阅单进行操作！");
				}else{
					if(confirm('确定删除所选数据吗')){
						//异步获取上传成功后的doc信息
						$.ajax({
					        async:true,//是否异步
					        type:"POST",//请求类型post\get
					        cache:false,//是否使用缓存
					        dataType:"text",//返回值类型
					        data:{"ids":ids},
					        url:"${ctx}/using_delLendingList.do",
					        success:function(text){
					        	 window.location.reload();
					        }
					    })
					}
				}
	        }
	    });
	}
	
	function update(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
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
		//校验是否是自己的借阅单或者自主查档的借阅单
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids,"checkType":"update"},
	        url:"${ctx}/using_checkOwnerForm.do",
	        success:function(text){
				if(text=="no"){
					alert("不能对其他人的借阅单进行操作！");
				}else{
					updatelayer(ids);
				}
	        }
	    });
	}
	
	function searchData(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
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
		//校验是否是自己的借阅单或者自主查档的借阅单
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids,"checkType":"search"},
	        url:"${ctx}/using_checkOwnerForm.do",
	        success:function(text){
				if(text=="no"){
					alert("不能对其他人的借阅单进行操作！");
				}else{
					var src = "${ctx}/model_toSearchJsp.do?jydId="+ids;
					parent.changeSrc(src);
				}
	        }
	    });
	}
	
	$("#addnum").bind("click",function(){
		layer.open({
			title:'身份验证',
			type:2,
			area:['460px','280px'],
			content:"${ctx}/using_toVerifyJsp.do"
		});
	});
	
	$("#statusSe").change(function(){
		var status = $("#statusSe option:selected").val();
		window.location.href="${ctx}/using_showLendingList.do?statusSe="+ status;
	});
	
	function detailed(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
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
		//校验是否是自己的借阅单或者自主查档的借阅单
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids,"checkType":"detail"},
	        url:"${ctx}/using_checkOwnerForm.do",
	        success:function(text){
				if(text=="no"){
					alert("不能对其他人的借阅单进行操作！");
				}else{
					layer.open({
			            type: 2,
			            title: "借阅单明细",
			            shadeClose: true,
			            shade: 0.4,
			            area: ['90%', '90%'],
			            content: "${ctx}/using_showUsingForm.do?vc_table=1&type=&id="+ids,
			            cancel: function(){
			                //右上角关闭回调
			                window.location.reload();
			           }
			        });
				}
	        }
	    });
	}
	
	function addlayer(_this){
		 layer.open({
            type: 2,
            title: "新增借阅单",
            shadeClose: true,
            shade: 0.4,
            area: ['100%', '100%'],
            content: "${ctx}/using_showUsingForm.do?vc_table=1&type=1&id="+_this,
            cancel: function(){
                //右上角关闭回调
           	 window.location.reload();
           }
        });
	}
	function updatelayer(_this){
		 layer.open({
           type: 2,
           title: "修改借阅单",
           shadeClose: true,
           shade: 0.4,
           area: ['100%', '100%'],
           content: "${ctx}/using_showUsingForm.do?vc_table=1&type=1&id="+_this,
           cancel: function(){
               //右上角关闭回调
          	 window.location.reload();
          }
       });
	}
	
	function showFjList(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
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
		//校验是否是自己的借阅单或者自主查档的借阅单
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"ids":ids,"checkType":"fj"},
	        url:"${ctx}/using_checkOwnerForm.do",
	        success:function(text){
				if(text=="no"){
					alert("不能对其他人的借阅单进行操作！");
				}else{
					layer.open({
			            type: 2,
			            title: "附件",
			            shadeClose: true,
			            shade: 0.4,
			            area: ['100%', '100%'],
			            content: "${ctx}/using_showFjList.do?id="+ids
			        });
				}
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