<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
	<style type="text/css">
	.tw-btn-green {
	padding: 4px 8px;
	font-size: 15px;
	color: #fff;
	background-color: #5eb95e;
	border-color: #5eb95e;
	}	
	
	</style>
	
</head>
<body>
    <div class="wf-layout">
    	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/monitor_getExceedPendingList2ForDept.do" >
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr height="30px">
				    		
					    	<th width="15%">
					    		当前步骤
					    	</th>
					    	<th width="15%" align="center">
					    		办理人
					    	</th>
					    	
					    	<th width="15%" align="center">
					    		操作
					    	</th>
					    	<!-- 
					    	<th  width="15%" align="center">
								统一设置
							</th>
							<th class="tdrs" width="15%" align="center">
								明细设置
							</th> -->
				    	</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${list}" var="d" varStatus="n">
					    	<tr height="30px">
						    	
						    	<td align="center">
						    		${d[2]}
						    	</td>
						    	<td align="center">
						    		${d[4]}
						    	</td>
						    	
								<td align="center">
									<a href="#" class="tw-btn-green" onclick="sendMsg('${d[3]}','${d[5] }','${intervalDate }');"><i class="wf-icon-send"></i> 发送短信</a>
								</td>
					    	</tr>
					    </c:forEach>
					</tbody>
				</table>
			
		</div>
		</form>
	</div>
	<script>
		$(document).ready(function(){
			setTimeout(function(){
				$('.j-resizeGrid').height(304)
				$('.wf-fixtable-scroller').height(264)
			},600)
		})
	</script>
</body>
   <script type="text/javascript">
   function sendMsg(userId,dofileTitle,intervalDate){
	   if(confirm("确定发送短信吗？")){
		   $.ajax({  
				url : '${ctx}/monitor_sendMsgForUnresolved.do',
				type : 'POST',   
				cache : false,
				data:{"userId":userId,"intervalDate":intervalDate,"dofileTitle":dofileTitle},
				async : false,
				error : function() {  
					alert('AJAX调用错误(monitor_sendMsgForUnresolved.do)');
				},
				success : function(msg) {
					if(msg == 'true'){
						alert('发送成功！');
					}else{
						alert('发送失败！');
					}
					window.parent.location.reload();
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				}
			});
	   }else{
		   return;
	   }
	   
   }
   
   </script>
</html>
