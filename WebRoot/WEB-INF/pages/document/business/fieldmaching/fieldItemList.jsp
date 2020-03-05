<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<body>
	<div class="wf-layout">
		<div class="wf-list-top">
			<form name="doItemList"  id="doItemList" action="${ctx }/fieldMatching_getAllItemList.do" method="post">
			<div class="wf-search-bar">
	 	       		<label class="wf-form-label" for="">事项类别：</label>
	 	       		<input type="text" class="wf-form-text"  name="itemName" value="${itemName}" placeholder="输入关键字"/>
		            <button class="wf-btn-primary" type="submit" >
		                <i class="wf-icon-search"></i> 搜索
		            </button>
			</div>
			</form>
		</div>
		<div class="wf-list-wrap">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/fieldMatching_getAllItemList.do" >
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	  	<tr>
				    		<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
				    		<th width="5%">序号</th>
					    	<th width="60%">事项类别</th>
					    	<th width="30%">设置字段匹配</th>
				    	</tr>
			    	</thead>
			    	<c:forEach items="${list}" var="d" varStatus="n">
				    	<tr target="sid_user" rel="1">
				    		<td align="center">
				    			<input type="checkbox" name="selid" value="${d.id}" >
				    		</td>
					    	<td align="center" itemid="${d.id}">
					    		${(selectIndex-1)*pageSize+n.count}
					    	</td>
					    	<td  class="workflowidtitle" workflownnameid="1">
					    		${d.vc_sxmc }
					    	</td>
					    	<td align="center">
					    		<a href="#" onclick="toSetfieldMatching('${d.id}');">设置</a>
					    	</td>
					    </tr>
			    	</c:forEach>
				</table>
			</form>
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
	</div>
</body>
   	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
   	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
				//获得后台分页相关参数-必须
				skipUrl="<%=request.getContextPath()%>"+"/fieldMatching_getAllItemList.do";			//提交的url,必须修改!!!*******************
				submitForm=document.getElementById('doItemList');									//提交的表单,必须修改!!!*******************
				innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
				MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
				pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
				selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
				sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
				if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
			};

	</script>
	<script type="text/javascript">
	 function toSetfieldMatching(itemId){
		 window.location.href="${ctx}/fieldMatching_toSetFieldMatching.do?itemId="+itemId;
	 }
	
	</script>
</html>