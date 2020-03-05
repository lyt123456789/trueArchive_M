<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>目录</title>
		<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerindex.jsp"%>
		<style type="text/css">
			* {
				text-align: center;
				margin: 0px;
				margin: auto;
			}
			.tbl{
				width: 99%;
				margin: auto;
				border-collapse: collapse;
				border: 0px;
			}
			.tbl td{
				border: 0px;
			}
			.tbl_in{
				font-size:12px;
				width: 100%;
				margin: auto;
				border-collapse: collapse;
				border: solid #ccccc;
				border-width: 1px 0 0 1px;
			}
			.tbl_in td{
				border: solid #ccccc;
				border-width: 0 1px 1px 0;
			}
			.input_none{
				width: 99%;
				border: 0px;
				text-align: left;
				background-color: transparent;
			}
	</style>
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script src="json2.js"></script>
	</head> 
	<base target="_self"/>   
	<body style="overflow:auto;"> 
	 <div class="panelBar"> 
		<ul class="toolBar">
			<li><a onclick="" href="javascript:save();" class="add"><span>保存设置</span></a></li>
		</ul> 
	</div>
	 <form id="form1" action="${ctx }/form_saveStringToHTML.do" method="post" >
	 		<input type="hidden" name="itemId" id="itemId" value="${itemId}"/>
	 		<table class="tbl">
	 			<tr>
	 				<td colspan="2">
	 					<div style="width: 100%;border: 1px solid #cccccc;height:100%;overflow: auto;">
		 					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		 						<thead>
		 							<tr>
		 								<Th style="width: 30%">公文交换元素名称</Th>
		 								<Th style="width: 20%">字段描述</Th>
		 								<Th style="width: 20%">表名</Th>
		 								<Th style="width: 20%">字段</Th>
		 							</tr>
		 					    </thead>
		 					    <tbody>
		 					    <c:forEach items="${maplist}" var="item" varStatus="in">
		 					     	<tr>
		 					    		<td align="center">
		 					    			<input id="tagnames_${in.index}" name="formtagnames" type="text" readonly="readonly" class="input_none" value="${item.tagName}"/>
		 					    		</td>
		 					    		<td align="center">
		 					    			<input id="tagdescs_${in.index}" name="formtagdescs" type="text" readonly="readonly" class="input_none" value="${item.tagDesc}"/>
		 					    		</td>
		 					    		<td>	
		 					    			<select id="tagTables_${in.index}" name="tagTables" class="input_none" style="text-align: left;">
		 					    				<c:forEach items="${tableList}" var="d">
		 					    					<option value="${d.vc_tablename}" <c:if test="${d.vc_tablename ==item.tableName}">selected="selected"</c:if>>${d.vc_tablename}</option>
		 					    				</c:forEach>
		 					    			</select>
		 					    		</td>
		 					    		<td>	
				 							<select id="tagFields_${in.index}" name="tagFields" class="input_none">
				 							<option value="null">无</option>  
			 									<c:forEach var="d" items="${tableList}" varStatus="i">
				 									<c:forEach var="f" items="${d.fields}">
				 										<option value="${f.vc_fieldname}" <c:if test="${item.fieldName == f.vc_fieldname}">selected="selected"</c:if>>${f.vc_fieldname}</option>
				 									</c:forEach>
			 								</c:forEach>
				 							</select>
		 					    		</td>
		 					   		  </tr>
		 					    </c:forEach>
		 						</tbody>
		 					</table>
	 					</div>
	 				</td>
	 			</tr>
	 		</table>
	  </form>
	</body>
	  <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	  <script src="/trueWorkFlow/dwz/style/js/jquery-1.7.1.min.js"></script>
	  <script type="text/javascript">
	  function save(){
		  document.getElementById("form1").action = "${ctx}/fieldMatching_addDocxgColoumRelationship.do";
		  document.getElementById("form1").submit();
	  }
	  </script>

	<%@ include file="/common/function.jsp"%>
</html>