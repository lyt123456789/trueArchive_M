<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
</head>
<body>
    <div class="wf-layout">
    	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr height="30px">
				    		<th width="5%" align="center">
					    		序号
					    	</th>
					    	<th width="25%">
					    		表单英文名
					    	</th>
					    	<th width="25%" align="center">
					    		表单中文名
					    	</th>
					    	<th width="15%" align="center">
					    		创建时间
					    	</th>
					    	<th width="15%" align="center">
					    		许可设置
					    	</th>
					    	<!-- 
					    	<th  width="15%" align="center">
								统一设置
							</th>
							<th class="tdrs" width="15%" align="center">
								明细设置
							</th> -->
				    	</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${list}" var="d" varStatus="n">
					    	<tr height="30px">
						    	<td align="center">
						    		${n.count}
						    	</td>
						    	<td >
						    		${d.form_name}
						    	</td>
						    	<td>
						    		${d.form_caption}
						    	</td>
						    	<td align="center">
						    		<fmt:formatDate value="${d.intime}" pattern="yyyy-MM-dd"/>
						    	</td>
						    	<!-- 
						    	<td class="tdrs" align="center">
									<a href="${ctx}/permit_getFormPermitList.do?vc_formid=${d.id}" class="uedset">设置</a>
								</td>
								<td class="tdrs" align="center">
									<a href="${ctx}/permit_getTagList.do?vc_formid=${d.id}" class="uedset">设置</a>
								</td> -->
								<td align="center">
									<a class="wf-btn-blue" href="${ctx}/permition_toSetPermitJsp.do?formid=${d.id}&type=${type }&workflowid=${workflowid}" >
						                <i class="wf-icon-crosshairs"></i> 许可设置
						            </a>
								</td>
					    	</tr>
					    </c:forEach>
					</tbody>
				</table>
			
		</div>
		</form>
	</div>
</body>
   
</html>
