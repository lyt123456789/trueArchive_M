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
	<form id="permitform" method="POST" name="thisForm" action="${ctx}/freeSet_tableMiddle.do?itemId='+src.id+'" >
	    <input type="hidden" name="itemId" id="itemId" value="${itemId}">
    	<div id="w_list_print" align="center">
		     <div>
		    	<table id="table2" class="list" width="100%" >
		    	<tr > 
			    		<td  style="width: 100%;text-align: center;">表名</td>
				    </tr>
	    		<c:forEach var="table" items="${tables.tables}" varStatus="i">
	    			<tr > 
			    		<td id="${table.id }" style="width: 100%;text-align: center;cursor: pointer;<c:forEach items="${selectTable}" var="module">  
                                   <c:if test="${fn:contains(module,table.vc_tablename)}">  
                                      background: A5A764;
                                    </c:if> 
                                </c:forEach>"  onclick="getField(this)" >${table.vc_tablename}</td>
				    </tr>
	    		</c:forEach>
		    </table>
			
		</div>
    	</div>
    </form>
    </body>
       <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript">
		$("table.list", document).cssTable();
    	window.clickTableId=null;
    	window.condition_old= null;
		window.result_old= null;
		window.order_old= null;
		window.sql_old= null;
		function getField(src){
			
			var iframe_right= window.parent.frames['iframe_right'];

			var ir_condition_old = null;
			var ir_result_old = null;
			var ir_order_old = null;

			if(iframe_right){
				ir_condition_old = iframe_right.document.getElementById('conditonSelect');
				var temp_condition = '';
				if(ir_condition_old != null){
					// 遍历保存到 json 数组里面
					for(var j=0;j<ir_condition_old.options.length;j++){
						//'{"name":"'+field+'","type":"'+type+'"
							if(j==0){
								temp_condition +='{"value":"'+ir_condition_old.options[j].value+'","text":"'+ir_condition_old.options[j].text+'"}';
							}else{
								temp_condition +=',{"value":"'+ir_condition_old.options[j].value+'","text":"'+ir_condition_old.options[j].text+'"}';
							}
	  				}
	  				temp_condition = '['+temp_condition+']';
					parent.condition_old_p = temp_condition;
					
				}
				ir_result_old =  iframe_right.document.getElementById('resultSelect');
				var temp_result = '';
				if(ir_result_old != null){
					// 遍历保存到 json 数组里面
					for(var j=0;j<ir_result_old.options.length;j++){
						//'{"name":"'+field+'","type":"'+type+'"
							if(j==0){
								temp_result +='{"value":"'+ir_result_old.options[j].value+'","text":"'+ir_result_old.options[j].text+'"}';
							}else{
								temp_result +=',{"value":"'+ir_result_old.options[j].value+'","text":"'+ir_result_old.options[j].text+'"}';
							}
	  				}
	  				temp_result = '['+temp_result+']';
     				parent.result_old_p = temp_result;
				}
				ir_result_old =  iframe_right.document.getElementById('resultSelect');
				var temp_order = '';
				if(ir_order_old != null){
					// 遍历保存到 json 数组里面
					for(var j=0;j<ir_order_old.options.length;j++){
						//'{"name":"'+field+'","type":"'+type+'"
							if(j==0){
								temp_order +='{"value":"'+ir_order_old.options[j].value+'","text":"'+ir_order_old.options[j].text+'"}';
							}else{
								temp_order +=',{"value":"'+ir_order_old.options[j].value+'","text":"'+ir_order_old.options[j].text+'"}';
							}
	  				}
	  				temp_order = '['+temp_order+']';
     				parent.order_old_p = temp_order;
				}
					
			};
			// 通过一个参数 来设置 add/ modify
			if(iframe_right.document.getElementById("changeState") &&iframe_right.document.getElementById("changeState").value == "1"){
				window.parent.frames['iframe_right'].location = '${ctx}/freeSet_fieldRight.do?tableId='+src.id+'&itemId=${itemId}&changeState=1';
			}else{
				window.parent.frames['iframe_right'].location = '${ctx}/freeSet_fieldRight.do?tableId='+src.id+'&itemId=${itemId}&changeState=0';
			}
		
        	src.oldColor=src.style.backgroundColor;
    		src.style.backgroundColor='#E8D3E3';
    		window.clickedTable=src;
    		window.clickTableId=src.id;//节点id
    		 
		}
		window.onload=function(){
			var tb2=g.g('table2');
			if(tb2.rows.length>0)getField(tb2.rows[1].cells[0]);
		}; 
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
