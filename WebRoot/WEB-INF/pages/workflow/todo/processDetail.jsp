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
<body onload="setFrameUrl();">
 <div class="panelBar"> 
		<ul class="toolBar">
			<li><a onclick="" href="javascript:sub();" class="add"><span>保存设置</span></a></li>
		</ul> 
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_freeSet.do" >
	
		    		<table class="list" width="100%" >
			    	<tr >
			    		<td style="width: 30%; vertical-align: top;">
					    	<table id="table1" class="list" width="100%" >
    							<tr > 
			    					<td style="width: 30%;text-align: center; background: A5A764;">办理人员</td>
			    					<td style="width: 40%;text-align: center; background: A5A764;">主办(协办)</td>
			    					<td style="width: 40%;text-align: center; background: A5A764;">结果</td>
				  			  	</tr>
		    				<c:forEach var="item" items="${list}" varStatus="i">
	    						<tr > 
			    					<td style="text-align: center;cursor: pointer;">${item.userName}</td>
			    					<td style="text-align: center;cursor: pointer;">
			    					<c:choose>
			    						<c:when test="${item.isMaster =='1'}">
			    								主办
			    						</c:when>
			    						<c:otherwise>
			    								协办
			    						</c:otherwise>
			    					</c:choose>
			    					</td>
			    					<td style="text-align: center;cursor: pointer;">
			    					<c:choose>
			    						<c:when test="${item.isOver =='OVER'}">
			    								已办
			    						</c:when>
			    						<c:otherwise>
			    								<font color="red">未办</font>
			    						</c:otherwise>
			    					</c:choose>
			    					</td>
				  				</tr>
	    					</c:forEach>
	    					</table>
					    </td>
					    <td>
					    	<iframe style="width: 98%; height:450px;display: none;" src="" id="detail"></iframe>
					    </td>
			    	</tr>
		    </table>
	</form>
    </body> 
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
    <script type="text/javascript">
    	function setFrameUrl(){
    		var nodeid = '${nodeid}';
    		var exchange = '${exchange}';
    		var iframe=document.getElementById("detail");
    		iframe.style.display = "";
    		iframe.src="${ctx}/table_getUserInfoByNode.do?nodeId="+nodeid+"&exchange="+exchange;
    	}
    	
    	//保存数据
    	function sub(){
    		//获取instanceId
    		var instanceId = '${instanceId}';
    		var stepIndex = '${stepIndex}';
    		var frame = document.getElementById("detail").contentWindow;
    		//发送的人员
    		var userId = frame.sendNext();
    		//检查主办人员是否 已办的协办人员
    		var routType = frame.document.getElementById("routType").value;
    		var flag = true;
    		if(routType=='2'){	//表示为主送抄送
    			//校验被选中为主送的人员是否已经    办理
    			var xttName = userId.split(",")[0];
    			$.ajax({   
        			url : '${ctx}/table_canSelect.do',
        			type : 'POST',   
        			cache : false,
        			async : false,
        			error : function() {  
        				alert('AJAX调用错误(table_canSelect.do)');
        			},
        			data : {
        				'instanceId':instanceId, 'stepIndex':stepIndex
        			},    
        			success : function(msg) { 
        				if(msg=='false'){
        					if(xttName==''){
        						alert("请选择主办人员！");
            					flag = false;
        					}
        					
        				}
        			}
        		});
    		}
    	 	if(flag){
        		$.ajax({   
        			url : '${ctx }/table_addProcessInfo.do',
        			type : 'POST',   
        			cache : false,
        			async : false,
        			error : function() {  
        				alert('AJAX调用错误(table_addProcessInfo.do)');
        			},
        			data : {
        				'instanceId':instanceId, 'userId':userId, 'stepIndex':stepIndex,'routType':routType
        			},    
        			success : function(msg) { 
        				if(msg=='success'){
        					alert("重定向成功！");
        					parent.window.location.href = "${ctx}/table_getDoFileList.do?redirect=1";
        				}
        				
        			}
        		}); 
    		} 
    	}
    </script>
</html>
