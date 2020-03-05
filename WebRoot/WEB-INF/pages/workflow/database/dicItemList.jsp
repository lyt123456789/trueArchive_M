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
	<script type="text/javascript">
		function openForm(lcid,itemid){
			var dicValue = document.getElementById("dicValue").value;
			var modId = document.getElementById("modId").value;
			var matchId = document.getElementById("matchId").value;
			var dicId = document.getElementById("dicId").value;
			dicValue=encodeURI(dicValue);
			var no = '${no}';
			var status = checkStatus(lcid);
			if(status!='0'){
				 var ret = window.showModalDialog('${ctx}/table_openFirstForm.do?workflowid='+lcid+'&itemid='+itemid+'&dicValue='+dicValue+'&modId='+modId+'&matchId='+matchId+'&dicId='+dicId+'&no='+no+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
				 if(ret == "undefined" || typeof(ret) == "undefined"){
			        	//window.location.reload();
			 	 }else if(ret == "refresh"){
			 		
			 	 }
				 window.close();
				//window.location.href = "${ctx}/table_openFirstForm.do?workflowid="+lcid+"&itemid="+itemid+"&dicValue="+dicValue+"&modId="+modId+"&matchId="+matchId+"&dicId="+dicId+"&no="+no;
			}else{
				alert("该流程为暂停状态，不可创建实例");
			}
		}
		//检查流程状态，“暂停”则不可新建实例
		function checkStatus(lcid){
			var status = "";
			$.ajax({   
				url : '${ctx }/item_getWfStatus.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(item_getWfStatus.do)');
				},
				data : {
					'workflowid':lcid
				},    
				success : function(result) {  
					status = result;
				}
			});
			return status;
		}
		
	</script>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/item_getDicItemList.do" >
		<input type="hidden" name="dicValue" id="dicValue" value="${dicValue}"/>
		<input type="hidden" name="modId" id="modId" value="${modId}"/>
		<input type="hidden" name="matchId" id="matchId" value="${matchId}"/>
		<input type="hidden" name="dicId" id="dicId" value="${dicId}"/>
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
			    	<th>事项类别</th>
		    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="d" varStatus="n">
			    	<tr target="sid_user" rel="1">
				    	<td  class="workflowidtitle" workflownnameid="1">
				    		<a href="javascript:openForm('${d.lcid}','${d.id}');">
				    		 <c:choose>  
    							<c:when test="${fn:length(d.vc_sxmc) > 40}">  
      							  <c:out value="${fn:substring(d.vc_sxmc, 0, 40)}..." />  
    							</c:when>  
   								<c:otherwise>  
      								<c:out value=" ${d.vc_sxmc}" />  
    							</c:otherwise>  
							</c:choose>
				    		</a>
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
			skipUrl="<%=request.getContextPath()%>"+"/dataCenter_getbusModuleList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	</script>
		
	<%@ include file="/common/function.jsp"%>
	<script>
		$('#pageContent').height($(window).height()-70);
	</script>
</html>