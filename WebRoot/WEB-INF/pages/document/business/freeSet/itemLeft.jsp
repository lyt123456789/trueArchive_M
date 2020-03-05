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
<body style="overflow: auto;">
	<form id="thisForm" method="POST" name="thisForm" action="${ctx}/freeSet_itemLeft.do" >

    		<table id="table1" class="list" width="100%" >
    		<tr > 
			    		<td style="width: 100%;text-align: center; background: A5A764;">事项类别</td>
				    </tr>
	    		<c:forEach var="item" items="${set}" varStatus="i">
	    			<tr > 
			    		<td id="${item.itemId }" style="width: 100%;text-align: center;cursor: pointer;<c:if test="${item.select_condition != null}">background: A5A764;</c:if>"  onclick="clickTd(this)" ><span style="width:160px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.itemName}</span></td>
				    </tr>
	    		</c:forEach>
		    </table>
	</form>
    </body>
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script> 
    <script type="text/javascript">
    	window.clickId=null;
    	function clickTd(src){
    		window.parent.document.getElementById('iframe_middle').src='${ctx}/freeSet_tableMiddle.do?itemId='+src.id;
    		window.parent.document.getElementById('iframe_bottom').src='${ctx}/freeSet_sqlBottom.do?itemId='+src.id;
			
			if(window.clickedTd)window.clickedTd.style.backgroundColor=window.clickedTd.oldColor;
        	src.oldColor=src.style.backgroundColor;
    		src.style.backgroundColor='#E8D3E3';
    		window.clickedTd=src;
    		window.clickItemId=src.id;//节点id
    		// 切换事项时 清空数据
    		parent.condition_old_p = '';
    		parent.result_old_p = '';
    		parent.order_old_p = '';
    		parent.sql_old_p = '';
    		
    		var iframe_right=window.parent.frames['iframe_right'];
    		var iframe_bottom=window.parent.frames['iframe_bottom'];
 			if(iframe_right){
 				if(iframe_right.document.getElementById('conditonSelect')){
 					iframe_right.document.getElementById('conditonSelect').options.length = 0; 
 				}
 				if(iframe_right.document.getElementById('resultSelect')){
 	 				iframe_right.document.getElementById('resultSelect').options.length = 0; 
 				}
 				if(iframe_right.document.getElementById('orderSelect')){
 	 				iframe_right.document.getElementById('orderSelect').options.length = 0; 
 				}
 				
 			};
 			if(iframe_bottom){
 				if(iframe_bottom.document.getElementById('sql'))
 					iframe_bottom.document.getElementById('sql').value = ""; 
 			};
    	};
    	window.onload=function(){
			var tbl=g.g('table1');
			if(tbl.rows.length>0)clickTd(tbl.rows[1].cells[0]);
    	};
    </script>
</html>
