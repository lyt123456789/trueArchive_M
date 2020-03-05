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
			<form name="lendLingList"  id="lendLingList" action="${ctx }/using_showTransferList.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
				<c:if test="${'1' eq shFlag}">
			    	<input type="hidden" name="statusSe" value="${statusSe }"/>
					<input type="hidden" name="shFlag" value="${shFlag }">
			    </c:if>
			    <div class="search">
			    	<c:if test="${'1' ne shFlag}">
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
    					<i class="fa fa-file-text-o"></i>调卷单明细
    				</button>
    				<button type="button" onclick="javascript:detailed('1');" target="_self" class="btn btn-detail" style="float:left">
    					<i class="fa fa-file-text-o"></i>归卷
    				</button>
    				<c:if test="${'1' ne shFlag}">
    					<select id="statusSe" name="statusSe" class="sel" style="float:left" onchange="searchlist();">
	        				<option value=""  <c:if test="${statusSe=='' || statusSe == null }"> selected="selected"</c:if> >全部</option>
							<option value="1" <c:if test="${statusSe=='1'}"> selected="selected"</c:if>>待审核</option>
							<option value="2" <c:if test="${statusSe=='2'}"> selected="selected"</c:if>>已审核</option>
							<option value="3" <c:if test="${statusSe=='3'}"> selected="selected"</c:if>>已归卷</option>
   					</select>
    				</c:if>
    				<div class="fr">
        				<input type="text" id="content" name="content" value="${content}" placeholder="输入关键字查询">
        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
        				</button>
    				</div>
	            </div>
			</form>
		</div>
	</div>
	
	
	<div class="table" style="overflow: auto;">
		<div>
		    <table cellspacing="0" cellpadding="0">
		  
		    	<tr class="fr_tr">	
		        	<td style="width:25px;"><div class="checkbox"><input type="checkbox" name="checkboxAll" onclick="chooseAll(this);"/></div></td>
		        	<td>调阅单号</td>
		        	<td>申请人</td>
		        	<td>申请时间</td>
		        	<td>调卷类型</td>
		        	<td>接待人</td>
		        	<td>状态</td>
		        </tr>
		        
		        <c:forEach items="${tflist}" var="data" varStatus="status">
		        	<tr>
		    			<td>
							<input type="checkbox" name="selid" id=""  value="${data.id }"  >
						</td>
						<td>${data.formId }</td>
						<td>${data.applyName }</td>
						<td>${data.applyTime }</td>
						<td>${data.transferType }</td>
						<td>${data.registrantName }</td>
						<td>
						<c:if test="${'1' eq  data.formStatus }">待审核</c:if>
						<c:if test="${'2' eq  data.formStatus }">已审核</c:if>
						<c:if test="${'3' eq  data.formStatus }">已归卷</c:if>
						</td>
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
		skipUrl="<%=request.getContextPath()%>"+"/using_showTransferList.do";			//提交的url,必须修改!!!*******************
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
		if(confirm('确定删除所选数据吗')){
			$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"ids":ids},
			        url:"${ctx}/using_deleteDJD.do",
			        success:function(text){
			        	window.location.href="${ctx}/using_showTransferList.do?statusSe=${statusSe}&shFlag=${shFlag}";
			        }
			    })
		}
	}
	
	function searchlist(){
		var form = document.getElementById("lendLingList");
		form.submit();
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
		layer.open({
            type: 2,
            title: "修改调卷单",
            shadeClose: true,
            shade: 0.4,
            area: ['90%', '50%'],
            content: "${ctx}/using_toAddTransferForm.do?id="+ids
        });
	}
	
	$("#addnum").bind("click",function(){
		layer.open({
            type: 2,
            title: "新增调卷单",
            shadeClose: true,
            shade: 0.4,
            area: ['70%', '50%'],
            content: "${ctx}/using_toAddTransferForm.do"
        });
	});
	
	function detailed(gjFlag){
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
		var title = "";
		if(gjFlag == "1") {
			title = "归卷单明细";
		} else {
			title = "调卷单明细";
		}
		layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.4,
            area: ['100%', '100%'],
            content: "${ctx}/using_showTransferForm.do?id="+ids+"&statusSe=${statusSe}&gjFlag="+gjFlag,
            cancel: function(){
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