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
	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js"></script>
</head>
<body  style="overflow-x: hidden; overflow-y: auto;">
<div class="zlgx-con" id="pageContent" style="overflow-y: auto;overflow-x: hidden;">
<table class="infotan" width="100%">
	<tr>
		<td width="20%" class="bgs ls">主送:</td>
		<td width="80%">
			${recDoc.xtoName}
		</td>
	</tr>
	<tr>
		<td class="bgs ls">抄送:</td>
		<td>
			${recDoc.xccName}
		</td>
	</tr>
	<tr>
		<td class="bgs ls">标题词:</td>
		<td>
			${recDoc.title}
		</td>
		</tr>
	<tr>
		<td class="bgs ls">发文单位:</td>
		<td>
				${recDoc.fwjg}
		</td>
	</tr>
	<tr>
		<td class="bgs ls">印发单位:</td>
		<td>
			${recDoc.yfdw}
		</td>
	</tr>
	<tr>
		<td class="bgs ls">文号:</td>
		<td>
			${recDoc.wh}
		</td>
	</tr>
	<tr>
		<td class="bgs ls">类型:</td>
		<td>
			${recDoc.doctype }
		</td>
	</tr>
	<tr>
		<td class="bgs ls">优先级:</td>
		<td>
			<c:if test="${recDoc.priority==0}">
                    	特急
					</c:if>
					<c:if test="${recDoc.priority==1}">
                    	紧急
					</c:if>
					<c:if test="${recDoc.priority==2}">
                    	急件
					</c:if>
					<c:if test="${recDoc.priority==3}">
                    	平件
					</c:if> 
		</td>
	</tr>
   <tr>
        <td class="bgs ls">附件:</td>
            <td colspan="3">
            	<c:forEach var="item" items="${attlist}">
            	<a href="#" onclick="downAttachment('${item.fileName}','${item.localation}');" style="height: 30px; padding-top: 10px;">
            		${item.fileName}
            	</a>
				<p>&nbsp;</p>
            	</c:forEach>
            </td>
   </tr>
   <tr>
        <td class="bgs ls">正文:</td>
        <td colspan="3" >
            	<c:forEach var="item" items="${zwlist}">
            		<a href="#" onclick="downAttachment('${item.fileName}','${item.localation}');" style="height: 30px; padding-top: 10px;">
            			${item.fileName}
            		</a>
            		<p>&nbsp;</p>
            	</c:forEach>
            </td>
   </tr>
</table>
</div>
</body>
<%@ include file="/common/function.jsp"%>
<script type="text/javascript">
	function downAttachment(fileName, location){
		var serverUrl = "${fileDownloadUrl}";
		//调用down方法
		download(location,serverUrl+"?name="+fileName+"&location="+location);
	}
</script>
<script>
	$('#pageContent').height($(window).height()-30);
</script>
</html>
