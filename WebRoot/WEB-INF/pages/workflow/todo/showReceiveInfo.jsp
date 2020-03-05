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
    <form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getDoFileReceiveList.do" >
    	<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
					<tr>
                        <th width="5%" style="font-weight:bold;text-align:center;">序号</th>
						<th style="font-weight:bold;text-align:center;">部门</th>
						<th width="15%" style="font-weight:bold;text-align:center;">发送时间</th>
						<th width="10%" style="font-weight:bold;text-align:center;">是否收取</th>
						<th width="20%" style="font-weight:bold;text-align:center;">收取/拒收时间</th>
						<th width="6%" style="font-weight:bold;text-align:center;">催办次数</th>
						<th width="10%" style="font-weight:bold;text-align:center;">操作</th>
						<!-- <th width="10%" style="font-weight:bold;text-align:center;">是否回复</th>
						<th width="20%" style="font-weight:bold;text-align:center;">回复时间</th> -->
					</tr>
				</thead>
				<tbody>
				 <c:forEach var="item"  items="${list}" varStatus="status">
					<tr>
                        <td align="center">${status.count}</td>	
					    <td align="center">${item[1]}</td>	
					    <td align="center">
					     	${item[3]}
					     </td>
					    <td align="center">
					   		 <c:choose>
					    	  <c:when test="${item[0]=='0'}">
					    	  		未收取
					    	  </c:when>
					    	  <c:when test="${item[0]=='4'}">
					    	  		已拒收
					    	  </c:when>
					    	  <c:when test="${item[0]=='1' && item[9] == 1}">
					    	  		已办理
					    	  </c:when>
					    	  <c:otherwise>
					    	  		<font color="red">已收取</font>
					    	  </c:otherwise>
					    	</c:choose>
					    </td>	
					     <td align="center">
					     	${item[2]}
					     </td>
					     <td align="center">
					     	${item[12]}
					     </td>
					     <td align="center">
					     	<c:if test="${item[2] == null || item[2] == '' }">
					     		<a href="#" class="wf-btn wf-btn-primary" onclick="sendMsg('${item[10]}','${item[11]}');">短信催办</a>
					     	</c:if>
					     </td>
					    <%--   <td align="center">
					      <c:if test="${item[4]== '4' && item[6]== '2'&& item[7] == '1' }">已回复</c:if>
					       <c:if test="${item[0]!='0' && item[4] != '4' &&  item[6]== '2' && item[7] == '1'}">未回复</c:if>
					     </td>
					     <td align="center">
					      <c:if test="${item[4]== '4' && item[6]== '2' && item[7] == '1'}">${item[5]}</c:if>
					     </td> --%>
					</tr>
				</c:forEach> 
				</tbody>
			</table>
		</div>
	</form>
</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<div id="div_god_paging" class="cbo pl5 pr5"></div> 
		</div>
    </body>
<%@ include file="/common/function.jsp"%> 
<script>
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script type="text/javascript">	
	function sendMsg(deptId,id){
		var title = '${title}';
		$.ajax({
			url : 'table_sendCbMsg.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'deptId':deptId,
				'title':title,
				'id':id
			},
			success : function(msg) {
				if(msg == 'success'){
					alert("催办成功");
					window.reload();
				}
				//alert(msg);				
			}
		});
	}
</script>
</html>
