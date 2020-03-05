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
	<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getTableList.do" >
			<input type="hidden" name="formid" id="formid" value="${formid}"/>
			<input type="hidden" name="type" id="type" value="2"/>
<fieldset style="width: 98%">
    <table style="padding-left: 400px;margin-top:15px;" width="40%" align="center">
    		<tr>
    			<td width="40%">选择节点:&nbsp;
    					<select id="node" style="text-align: left; max-width: 100px;">
	 						<option value=""></option>
	 						<c:forEach var="d" items="${nodes}">
	 							<option value="${d.wfn_id}" <c:if test="${zform.nodeId ==d.wfn_id }">selected="selected"</c:if>>${d.wfn_name}</option>
	 						</c:forEach>
	 					</select>
	 			</td>
	 			<td></td>
    		</tr>
			<tr >
			    	<td>选择字段:&nbsp;
			    	<select id="field" style="text-align: left;width: 180px;">
	 						<option value=""></option>
	 						<c:forEach var="f" items="${fieldList}">
	 							<option value="${f.id}" <c:if test="${zform.fieldId ==f.id }">selected="selected"</c:if>>${f.formtagname}(${f.formtagtype})</option>
	 						</c:forEach>
	 					</select>
			    	</td>
			    	<td></td>
			   </tr>
			    	<tr >
			    	<td align="center">
			    	<input type="button" onclick="add()" class="add" value="添加">
			    	</td>
			    	<td>
			    	</td>
			    	</tr> 
		    </table>
  </fieldset>
	</form>
    </body> 
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
    <script type="text/javascript">
    	function add(){ 
    		
    		// 验证非空
    	var nodeName = $("#node  option:selected").text();
		var nodeId = $("#node  option:selected").val();

		var fieldName = $("#field  option:selected").text();
		var fieldId = $("#field  option:selected").val();
		
		if(nodeId == '' ||fieldId == ''){
			alert("请输入节点,附件字段");
		}else{
			fieldName = fieldName.substring(0,fieldName.indexOf("("));
			$.ajax({
				url : '${ctx}/permition_setFormAtt.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'nodeName':nodeName,'nodeId':nodeId,
					'fieldName':fieldName,'fieldId':fieldId,
					'formid':$("#formid").val(),'type':$("#type").val()
				},
				error : function() {
					alert('AJAX调用错误');
				},
				success : function(ret) {
					alert("success");
				}
			}); 
		}
		
    	}
    
    </script>
</html>
