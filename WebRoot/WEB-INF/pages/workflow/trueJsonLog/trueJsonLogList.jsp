<!DOCTYPE HTML>
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
		<div class="wf-search-bar">
			<form name="pendlist"  id="pendlist" action="${ctx }/trueJson_getTrueJsonLogList.do" method="post">
 	          	<label class="wf-form-label" for="">标题：</label>
                <input type="text" style="width: 100px;" class="wf-form-text" id="title" name="title" value="${title}" placeholder="输入关键字">
                <label class="wf-form-label" for="">人员姓名：</label>
                <input style="width: 100px;" type="text" class="wf-form-text" id="empName" name="empName" value="${empName}" placeholder="输入关键字">
                <label class="wf-form-label" for="">操作步骤：</label>
                <input style="width: 50px;" type="text" class="wf-form-text" id="actionStep" name="actionStep" value="${actionStep}" placeholder="输入关键字">
                <label class="wf-form-label" for="">执行方法：</label>
                <input style="width: 50px;"  type="text" class="wf-form-text" id="exeMethod" name="exeMethod" value="${exeMethod}" placeholder="输入关键字">
               <label class="wf-form-label" for="">操作方法：</label>
                <select class="wf-form-select" id="actMethod"  name="actMethod" style="width: 80px;">
                	<option value="">--请选择--</option>
					<option value="0" <c:if test="${actMethod == '0'}">selected</c:if>>读</option>
					<option value="1" <c:if test="${actMethod == '1'}">selected</c:if>>写</option>
                </select>
                <label class="wf-form-label" for="">执行时间：</label>
              	 从<input type="text" style="width: 80px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange="checkTime();" class="wdate" readonly="readonly"  id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}" style="width:75px;"/>
				  到<input type="text"  style="width: 80px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange="checkTime();" class="wdate" readonly="readonly"  id="commitTimeTo" name="commitTimeTo" value="${commitTimeTo}" style="width:75px;"/>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/trueJson_getTrueJsonLogList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th width="6%">序号</th>
						<th>标题</th>
						<th width="10%">人员姓名</th>
						<th width="15%">操作步骤</th>
						<th width="15%">执行方法</th>
						<th width="5%">操作方法</th>
						<th width="15%">执行时间</th>
                    </tr>
		    	</thead>
		    	 <c:forEach var="item" items="${list}" varStatus="status"> 
			       	  <tr style="height: 30px;">
						<td align="center">${(selectIndex-1)*pageSize+status.count}</td>
						<td align="left"><a href="#" onclick="openForm('${item.id}','${item.process_title}');">${item.process_title}</a></td>
						<td>${item.username}</td>
						<td>${item.nodeName}</td>
						<td>${item.excute}</td>
						<td>
							<c:if test="${item.readOrWrite=='0'}">读</c:if>
							<c:if test="${item.readOrWrite=='1'}">写</c:if>
						</td>
						<td><fmt:formatDate value="${item.readOrWriteDate}" pattern="yyyy-MM-dd HH:mm"/>
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
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			skipUrl="<%=request.getContextPath()%>"+"/trueJson_getTrueJsonLogList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('pendlist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		
		function openForm(id,title){
			 title = title + '-意见';
			 var url = '${curl}/trueJson_getTrueJsionById.do?id=' + id + '&t='+new Date();
			 openLayerTabs('',screen.width,screen.height,title,url);
		}	
	
	function openLayerTabs(processId,width,height,title,url){
   		window.parent.topOpenLayerTabs(processId,1200,600,title,url);
	}
	</script>
</html>