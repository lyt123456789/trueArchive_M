<!DOCTYPE HTML>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			<form name="docList"  id="docList" action="${ctx }/toRecDoc_getToRecDocList.do" method="post">
 	        	<input type="hidden" name="isAdmin"  value="${isAdmin}" />
 	        	<label class="wf-form-label" for="">标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="button"  onclick="search();">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
	            <button class="wf-btn-green" type="button" onclick="batchCharg();">
	                	批量收取
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/toRecDoc_getToRecDocList.do" >
			<table class="wf-fixtable" layoutH="140" >
				<thead>
		    	  	<tr>
                        <th width="3%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
						<th width="5%">序号</th>
						<th width="10%">文号</th>
						<th>标题</th>
						<th width="10%">发文单位</th>
						<th width="10%">发文时间</th>
						<th width="7%">公文类型</th>
						<th width="7%">收取</th>
                    </tr>
		    	</thead>
		    	<c:forEach var="item" items="${list}" varStatus="status"> 
			       	  <tr height="50px;">
			       	  	<td align="center">
			    			<input type="checkbox" name="selid" value="${item.id}" >
			    		</td>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td>${item.wh}</td>
						<td><a href="#" onclick="toViewToRecDocInfo('${item.id}');">${item.bt}</a></td>
						<td align="center">${item.fwdw}</td>
						<td align="center">${item.sendTime}</td>
						<td align="center">${item.type}</td>
						<td align="center">
							<button class="wf-btn-primary"  type="button"
								onclick="updateStatus('${item.id}');" 
								title="收取">
					                	收取
				            </button>
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
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/toRecDoc_getToRecDocList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('docList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		function checkTime(){
			var commitTimeFrom = document.getElementById("commitTimeFrom").value;
			var commitTimeTo = document.getElementById("commitTimeTo").value;
			if ((""!=commitTimeFrom&&""!=commitTimeTo)&&commitTimeFrom > commitTimeTo){
				alert("开始时间不能大于结束时间！");
				document.getElementById("commitTimeFrom").value="";
				document.getElementById("commitTimeTo").value="";
			}
		}
		
		function search(){
			document.forms.docList.action="${ctx}/toRecDoc_getToRecDocList.do";
			document.forms.docList.submit();
		}
		
		function sel(){
			var selAll = document.getElementById('selAll');
			var selid = document.getElementsByName('selid');
			for(var i = 0 ; i < selid.length; i++){
				if(selAll.checked){
					selid[i].checked = true;
				}else{
					selid[i].checked = false;
				}
			}
		}

		//批量收取
		function batchCharg(){
			var selid = document.getElementsByName('selid');
			var ids = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					ids += selid[i].value + ",";
				}
			}
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
			}else {
				alert('请选择一个收取的公文');
				return;
			}
			
			if(confirm("确定要收取吗？")){
				//ajax异步处理数据
				$.ajax({
					url : '${ctx}/toRecDoc_updateToRecDocStatus.do',
					type : 'POST',
					cache : false,
					async : false,
					error : function() {
						alert('收取失败');
					},
					data : {
						'ids' : ids
					},
					success : function(result) {
						var res = eval("("+result+")");
						if(res.success){
							alert("收取成功！");
							window.location.href = '${ctx}/toRecDoc_getToRecDocList.do';
						} else{
							alert('收取待办失败');
						}
					}
				}); 
			}
		}

		function updateStatus(id){
			$.ajax({
				url : '${ctx}/toRecDoc_updateToRecDocStatus.do',
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('收取失败');
				},
				data : {
					'ids' : id
				},
				success : function(result) {
					var res = eval("("+result+")");
					if(res.success){
						alert("收取成功！");
						window.location.href = '${ctx}/toRecDoc_getToRecDocList.do';
					} else{
						alert('收取待办失败');
					}
				}
			}); 
		}


		function toViewToRecDocInfo(id){
			var time = new Date();
			var url = "${ctx}/toRecDoc_toViewToRecDocInfo.do?id="+id+'&time='+time;
			parent.layer.open({
	            title :'详细信息',
	            content: url,
	            type: 2, 
	            area: ['1000px', '700px'],
	            btn: ['关闭']
			});
			//window.location.href = "${ctx}/toRecDoc_toViewToRecDocInfo.do?id="+id;
		}
	</script>
</html>