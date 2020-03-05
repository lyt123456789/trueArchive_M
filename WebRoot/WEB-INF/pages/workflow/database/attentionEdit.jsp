 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>

<html>
    <head>
        <title>关注设置</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
		<script src="${cdn_js}/base/jquery.js" type="text/javascript"></script>
	    <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
    </head>
    <base target="_self"> 
    <body>
    	<div id="bodyer" style="margin-left:5px;">
    	<div class="displayTableForm"><input type="button"" id="add" type="button" onclick="addViewSzTodo('${szId}');" value="新增关联"/></div>
    	<form id="searchForm" action="${ctx}/attention_viewAttention.do?szId=${szId}" method="post">
	        <display:table form="searchForm"  name="requestScope.recodes" pagesize="10" class="displayTable" cellspacing="0" cellpadding="0"  htmlId="displayTable" id="element" partialList="true" export="false" size="requestScope.size" requestURI="attention_viewAttention.do?szId=${szId}" excludedParams="*">
	            <display:column headerClass="split" class="docNum" title="关联人姓名" style="width:110px;padding-left:40px;" sortable="false" nulls="false">${element.userName}</display:column>
	            <display:column headerClass="split" class="docNum" title="操作" style="width:100px;padding-left:40px;" nulls="false" sortable="false"><a href="javascript:setViewSzTodo('${element.id}','${element.szId}');">撤消关联</a>
	            </display:column>
	        </display:table>
        </form>
            
       	<a id="flush" href="#"></a>
       </div>
    </body>
   
    <script type="text/javascript">
		function setViewSzTodo(id,szId){
			$.ajax({
   			    url: 'attention_delAttention.do',
   			    type: 'POST',
   			    cache:false,
   			    async:false,
   			    data:{'id':id},
   			    error: function(){
   			        alert('AJAX调用错误');
   			    },
   			    success: function(msg){
   			        if(msg=='1'){
   						alert("撤消成功！");
   			        }else if(msg=='0'){
   			        	alert("撤消失败！");
   			        }
   			    }
   			});
			document.getElementById('flush').href='${ctx}/attention_viewAttention.do?szId='+szId+'&t='+Math.random()*1000;
			document.getElementById('flush').click();
		}


		function addViewSzTodo(szId){
			var winoption ="dialogHeight:"+1002+"px;dialogWidth:"+ 746 +"px;status:no;scroll:no;resizable:no;center:yes";
            var retVal = window.showModalDialog('${ctx}/selectTree_showEmpTree.do?t='+Math.random()*1000, window, winoption);
			if(retVal){
	            var userIds = '';
	            $.each(retVal.data,function(k,v){
					userIds += v.value+",";
				});
	            userIds = userIds.substring(0,userIds.length-1);
	            if(userIds != '')
            	$.ajax({
    			    url: 'attention_updateAttention.do',
    			    type: 'POST',
    			    cache:false,
    			    async:false,
    			    data:{'szId':szId,'userIds':userIds.toString()},
    			    error: function(){
    			        alert('AJAX调用错误');
    			    },
    			    success: function(msg){
    			        if(msg=='success'){
    						alert("添加成功！");
    						document.getElementById('flush').href='${ctx}/attention_viewAttention.do?szId='+szId;
    						document.getElementById('flush').click();
    			        }
    			    }
    			});
			}
		}

        
    </script>
</html>
