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
			<li><a onclick="" href="javascript:sub();" class="add"><span>保存</span></a></li>
			<input type="hidden" name="type" id="type" value="${type}"/>		
		</ul> 
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_freeSet.do" >
		    		<table class="list" width="100%" >
			    	<tr >
			    		<td style="width: 15%;">
					    	<iframe src="${ctx }/table_itemLeft.do?type=${type}" id="iframe_left" name="iframe_left" style="border: 1px;width: 100%;height: 100%;" frameborder="0" ></iframe>
					    </td>
			    		<td style="width: 15%;overflow: auto;">
					    	<iframe src="" id="iframe_middle" name="iframe_middle" style="border: 0px;width: 100%;height: 500px;" frameborder="0"></iframe>
					    </td>
					    <td style="width: 60%;overflow: auto;">
					    	<iframe src="" id="iframe_right"  name="iframe_right" style="border: 0px;width: 100%;height: 500px;" frameborder="0"></iframe>
					    </td>
			    	</tr>
		    </table>
	</form>
    </body> 
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
    <script type="text/javascript">
     	var condition_old_p= null;
		var result_old_p= null;
		function clear(){
			 parent.condition_old_p = '';
     		parent.result_old_p = ''; 
 			var iframe_right=window.frames['iframe_right'];
 			if(iframe_right){
 				iframe_right.document.getElementById('conditonSelect').options.length = 0; 
 				iframe_right.document.getElementById('resultSelect').options.length = 0; 
 				if(iframe_right.document.getElementById("changeState")){
 					 iframe_right.document.getElementById("changeState").value = "0";
 				}
 			}; 
		}
    	function sub(){ 
        	try{
        		var iframe_right= window.frames['iframe_right'];
    			var ir_condition_old = null;
    			var ir_result_old = null;
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
    					
    			};
    			if(condition_old_p == null || condition_old_p == '' ||condition_old_p == '[]'){
    				alert("请设置查询条件");
    				return;
    			}
    			if(result_old_p == null || result_old_p == ''||result_old_p =='[]'){
    				alert("请设置结果集");
    				return;
    			}
    			if(iframe_right.document.getElementById("changeState")){
				   iframe_right.document.getElementById("changeState").value = "0";
				}
    			// 获取事项id
    			var iframe_left=window.frames['iframe_left'];
    			var itemId = iframe_left.clickItemId;
    			$.ajax({   
    				url : '${ctx }/table_addFree.do?type=${type}',
    				type : 'POST',   
    				cache : false,
    				async : false,
    				error : function() {  
    					alert('AJAX调用错误(table_addFree.do)');
    				},
    				data : {
    					'itemId':itemId,
    					'condition':condition_old_p,
    					'result':result_old_p
    				},    
    				success : function(result) {  
    				}
    			});
    			
    		/* 	if(!confirm('确定执行吗？'))return;
    			g.g('thisForm').action='${ctx }/table_addFree.do';
    			g.g('thisForm').submit(); */
    			
    			// 切换事项时 清空数据
    			clear();
        		
        	}catch(e){
				alert(e);
        	};
    	};
    
    </script>
</html>
