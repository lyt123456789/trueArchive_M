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
<body style="overflow:auto">
 <div class="panelBar"> 
		<ul class="toolBar">
			<li><a onclick="" href="javascript:sub();" class="add"><span>保存</span></a></li>		
		</ul> 
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_freeSet.do" >
		    		<table class="list" width="100%" >
			    	<tr >
			    		<td style="width: 15%;">
					    	<iframe src="${ctx }/freeSet_itemLeft.do" id="iframe_left" name="iframe_left" style="border: 1px;width: 100%;height: 380px;" frameborder="0" ></iframe>
					    </td>
			    		<td style="width: 15%;overflow: auto;">
					    	<iframe src="" id="iframe_middle" name="iframe_middle" style="border: 0px;width: 100%;height: 380px;" frameborder="0"></iframe>
					    </td>
					    <td style="width: 60%;overflow: auto;">
					    	<iframe src="" id="iframe_right"  name="iframe_right" style="border: 0px;width: 100%;height: 380px;" frameborder="0"></iframe>
					    </td>
			    	</tr>
			    	<tr>
			    	<td colspan="3" style="overflow: auto;">
					    	<iframe src="" id="iframe_bottom"  name="iframe_bottom" style="border: 0px;width: 100%;height: 170px;" frameborder="0"></iframe>
					    </td>
			    	</tr>
		    </table>
	</form>
    </body> 
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
    <script type="text/javascript">
     	var condition_old_p= null;
		var result_old_p= null;
		var order_old_p= null;
		var sql_old_p_daiban= null;
		var sql_old_p_yiban= null;
		var sql_old_p_yibanjie= null;
		//保存
    	function sub(){ 
        	try{
        		var iframe_right= window.frames['iframe_right'];
        		var iframe_bottom= window.frames['iframe_bottom'];
    			var ir_condition_old = null;
    			var ir_result_old = null;
    			var ir_order_old= null;
    			sql_old_p_daiban = iframe_bottom.document.getElementById("daibansql").value;
    			sql_old_p_yiban = iframe_bottom.document.getElementById("yibansql").value;
    			sql_old_p_yibanjie = iframe_bottom.document.getElementById("yibanjiesql").value;
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
    					condition_old_p = temp_condition;
    					
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
         				result_old_p = temp_result;
    				}
    				ir_order_old =  iframe_right.document.getElementById('orderSelect');
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
    	  				order_old_p = temp_order;
    				}
    					
    			};
    			
    			if(sql_old_p_daiban.indexOf("{error:result is null}")>=0){
    				alert("SQL语句中无结果集字段！");
    				return;
    			}
    			
    			if(iframe_right.document.getElementById("changeState")){
				   iframe_right.document.getElementById("changeState").value = "0";
				}
    			// 获取事项id
    			var iframe_left=window.frames['iframe_left'];
    			var itemId = iframe_left.clickItemId;
    			$.ajax({   
    				url : '${ctx }/freeSet_addFree.do',
    				type : 'POST',   
    				cache : false,
    				async : false,
    				error : function() {
    					alert('AJAX调用错误(freeSet_addFree.do)');
    				},
    				data : {
    					'itemId':itemId,
    					'condition':condition_old_p,
    					'result':result_old_p,
    					'order':order_old_p,
    					'daibansql':sql_old_p_daiban,
    					'yibansql':sql_old_p_yiban,
    					'yibanjiesql':sql_old_p_yibanjie
    				},    
    				success : function(result) {  
    					alert("保存成功！");
    				}
    			});
    			
        		
        	}catch(e){
				alert(e);
        	};
    	};
    
    </script>
</html>
