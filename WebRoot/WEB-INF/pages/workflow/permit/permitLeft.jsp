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
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
    		<table id="table1" class="list" width="100%" >
	    		<c:forEach var="node" items="${gllist}" varStatus="i">
	    			<tr> 
			    		<td id="${node.wfn_id}" style="width: 100%;text-align: left;cursor: pointer;<c:if test="${node.ishavePermit==true}">background: A5A764;</c:if>"  onclick="clickTd(this)" >${node.wfn_name}(${node.wfn_sortNumber})</td>
				    </tr>
	    		</c:forEach>
		    </table>
	</form>
    </body>
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script> 
    <script type="text/javascript">
    	window.clickNodeId=null;
    	window.formid='${formid}';
    	function clickTd(src){
    		window.parent.document.getElementById('iframe_middle').src='${ctx }/permition_permitMiddleList.do?lcid=${lcid}&formid=${formid }&type=${type }&nodeid='+src.id;
    		//window.parent.document.getElementById('iframe_right').src='${ctx }/permition_permitRight.do?formid=${formid }';
			if(window.clickedTd)window.clickedTd.style.backgroundColor=window.clickedTd.oldColor;
        	src.oldColor=src.style.backgroundColor;
    		src.style.backgroundColor='#E8D3E3';
    		window.clickedTd=src;
    		window.clickNodeId=src.id;//节点id
    	};
    	window.onload=function(){
			var tbl=g.g('table1');
			if(tbl.rows.length>0)clickTd(tbl.rows[0].cells[0]);
    	};
    </script>
</html>
