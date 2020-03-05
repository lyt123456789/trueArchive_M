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
			<li><a class="add" href="${ctx}/field_toAddJsp.do" target="_self"><span>添加</span></a></li>
			<li><a class="delete" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象"><span>删除</span></a></li>
			<li class="line">line</li>
		-->
		</ul>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/field_getFieldList.do" >
    	<div id="w_list_print" align="center">
    		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		    	<thead>
			    	<tr>
		    		<th width="5%" align="center">
			    		序号
			    	</th>
			    	<th width="20%" align="center">
			    		标签名
			    	</th>
			    	<th width="20%" align="center">
			    		中文名称
			    	</th>
			    	<th width="20%" align="center">
			    		标签类型
			    	</th>
			    	<th width="10%" align="center">
			    			权限
			    			<select id="vc_limit_all" style="width:80px">
				    			<option value="1">只读</option>
				    			<option value="2">读写</option>
				    			<option value="0">隐藏</option>
				    		</select>
			    	</th>
			    	<th >
			    	</th>
		    	</tr>
		    </thead>
	    	<c:forEach items="${list}" var="d" varStatus="n">
		    	<tr>
			    	<td align="center">
			    		${n.count}
			    	</td>
			    	<td>
			    		${d.formtagname}
			    	</td>
			    	<td>
			    		${d.columnCname}
			    	</td>
			    	<td >
			    		${d.formtagtype}
			    	</td>
			    	<td align="center">
			    		<select name="vc_limit" style="width:80px">
			    			<option value="1">只读</option>
			    			<option value="2">读写</option>
			    			<option value="0">隐藏</option>
			    		</select>
			    	</td>
			    	<td ></td>
		    	</tr>
		    </c:forEach>
    		</table>
    	</div>
    	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<div id="div_god_paging" class="cbo pl5 pr5"></div> 
		</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li><a class="buttonActive" href="javascript:history.go(-1);"><span>返回</span></a></li>
			</ul> 
		</div>
	</form>
</body>
   
	<script type="text/javascript">
		$("table.list", document).cssTable();
	</script>
	<%@ include file="/common/function.jsp"%>
</html>