<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="panelBar"> 
		<ul class="toolBar">
		<!-- 
			<li><a onclick="javascript:location.href='${ctx}/table_toAddJsp.do?vc_parent=${vc_parent}'" href="javascript:;" class="add"><span>创建</span></a></li>
			<li><a onclick="javascript:refTable();" href="javascript:;" class="edit"><span>选择</span></a></li>
			<li><a onclick="javascript:del();" href="javascript:;" class="delete"><span>删除</span></a></li>
		-->			
		</ul>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
    	<div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<th width="5%">
				    		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
				    	</th>
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="25%">
				    		表单英文名
				    	</th>
				    	<th width="25%" align="center">
				    		表单中文名
				    	</th>
				    	<th width="10%" align="center">
				    		创建时间
				    	</th>
				    	<th  width="10%" align="center">
							许可设置
						</th>
						<th class="tdrs"></th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${list}" var="d" varStatus="n">
				    	<tr height="30px">
				    		<td align="center">
				    			<input type="checkbox" name="selid" value="${d.id}">
				    		</td>
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
							<td class="tdrs" align="center">
								<a href="${ctx}/permit_getTagList.do?vc_formid=${d.id}&type=1" class="uedset">设置</a>
							</td>
							<td ></td>
				    	</tr>
				    </c:forEach>
				</tbody>
		    </table>
    	</div>
    	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<div id="div_god_paging" class="cbo pl5 pr5"></div> 
		</div>
		</form>
    </body>
   
	<script type="text/javascript">
		$("table.list", document).cssTable();
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
