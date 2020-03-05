<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/component/writeByHand/js/websign.js"></script>
	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/widgets/component/taglib/comment/js/comment.js"></script>
	<script src="${ctx}/widgets/plugin/js/sea.js"></script>  
	<script src="pdfocx/json2.js"></script>
</head>
    <body style="overflow:auto;">
     <div align="left" style="padding-right: 10px;padding-top:10px;">
    	<input type="button" value="返回" onclick="seeZxProcess()">
		</div>
		<table class="infotan mt10" width="100%" style="line-height:10px;">
			<tr>
				<td width="5%" style="font-weight:bold;text-align:center;">序号</td>
				<td width="10%" style="font-weight:bold;text-align:center;">步骤名称</td>
				<td width="15%" style="font-weight:bold;text-align:center;">发送人</td>
				<td width="15%" style="font-weight:bold;text-align:center;">接收人</td>
				<td width="15%" style="font-weight:bold;text-align:center;">接收时间</td>
				<td width="15%" style="font-weight:bold;text-align:center;">办理时间</td>
				<!-- <td width="15%" style="font-weight:bold;text-align:center;">办理意见</td> -->
				<!-- <td width="15%" style="font-weight:bold;text-align:center;">状态</td> -->
			</tr>
			<c:forEach items="${processList}" var="processLists">
				<c:forEach items="${processLists}" var="process">
				<tr id="${process.wfProcessUid}Tr">
					<td style="text-align:center;">${process.stepIndex}</td>
						<td align="center" title="${process.nodeName}">
							<input  type="button" id="${process.wfProcessUid}" onclick="openProcessForm('${process.nodeId}','${process.wfProcessUid}','${process.wfInstanceUid}','${process.formId}','','${process.userId}','${process.workflowId}','${process.pdfPath}');"value="${process.nodeName}"/>
							<c:if test="${process.isHaveChild == '1'}"><span id="${process.wfProcessUid}Href"><a href="#" onclick="openChildWf('${process.wfProcessUid}','${process.wfInstanceUid}','${process.toNodeId}');"><font color="red"><span  style="font-size:25px">+</span></font></a></span></c:if>
						</td>
					<td style="text-align:center;">${process.fromUserName}</td>
					<td style="text-align:center;">${process.userName}
					<c:if test="${process.isRedirect == '1'}"><font color="red">（已被重新指派）</font></c:if>
					</td>
					<td style="text-align:center;">${fn:substring(process.jssj,0,16)}</td>
					<td style="text-align:center;">${fn:substring(process.finshTime,0,16)}</td>
<%-- 					<td style="text-align:center;"><c:if test="${process.isBack==2}">废弃过程</c:if><c:if test="${process.isBack==1}">回收过程</c:if></td>
 --%>				</tr>
				
				<c:forEach var="item" items="${map}">
					<c:if test="${item.key == process.stepIndex}">
					<c:set value="${item.value}" var="item2"></c:set>
						<tr>
							<td colspan="8">
								<table width="100%">
										<tr>
											<td width="10%" style="text-align:center;">${item2.stepIndex}</td>
											<td align="center"  width="10%"><font color="blue">${item2.nodeName}</font></td>
											<td width="15%" style="text-align:center;">${item2.fromUserName}</td>
											<td width="15%" style="text-align:center;">${item2.userName}</td>
											<td width="15%" style="text-align:center;">${fn:substring(item2.applyTime,0,16)}</td>
											<td width="15%" style="text-align:center;">${fn:substring(item2.finshTime,0,16)}</td>
											<%-- <td width="15%" style="text-align:center;"></td>--%>
											<%-- <td width="15%" style="text-align:center;">
												<c:if test="${item2.isBack==2}">废弃过程</c:if><c:if test="${item2.isBack==1}">回收过程</c:if>
											</td>  --%>
										</tr>
								</table>
							</td>
						</tr>
					</c:if> 
					 <br>   
				</c:forEach>
				<c:forEach var="item" items="${map2}">  
					 <c:if test="${item.key == process.stepIndex}">
					<c:set value="${item.value}" var="process"></c:set>
						<tr>
							<td colspan="8">
								<table width="100%">
								<c:forEach var="item1" items="${process}">
										<c:forEach var="item2" items="${item1}">
										<tr>
											<td width="10%" style="text-align:center;">${item2.stepIndex}</td>
											<td align="center"  width="10%"><font color="blue">${item2.nodeName}</font></td>
											<td width="15%" style="text-align:center;">${item2.fromUserName}</td>
											<td width="15%" style="text-align:center;">${item2.userName}</td>
											<td width="15%" style="text-align:center;">${fn:substring(item2.applyTime,0,16)}</td>
											<td width="15%" style="text-align:center;">${fn:substring(item2.finshTime,0,16)}</td>
											<%-- <td width="15%" style="text-align:center;"></td>--%>
										<%-- 	<td width="15%" style="text-align:center;">
												<c:if test="${item2.isBack==2}">废弃过程</c:if><c:if test="${item2.isBack==1}">回收过程</c:if>
											</td>  --%>
										</tr>
										</c:forEach>
								</c:forEach>
								</table>
							</td>
						</tr>
					</c:if>
					 <br>   
				</c:forEach>
			</c:forEach><tr height="20px;"></tr>
			</c:forEach>
		</table>
    </body>
    <script type="text/javascript">
		//以下必须有
		function loadCss(){  
	   		seajs.use('lib/form',function(){  
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
				$('select[mice-select]').cssSelect();
		    });
		}
		//以上必须有
	</script>


    <script type="text/javascript">
    
    function seeZxProcess(){
		jsonObj = {};
		window.location.href='${ctx}/table_getProcessOfMobile.do?instanceId=${instanceId}&workFlowId=${workFlowId}'; 
	}
		function seeHandWrite(tagId,instanceId,processId){
			window.showModalDialog('${ctx}/table_showHandWrite.do?tagId='+tagId+'&instanceId='+instanceId+'&processId='+processId+'&t='+new Date(),null,"dialogWidth:890px;dialogHeight:450px;help:no;status:no");
		}
		 var jsonObj = {};
		function openProcessForm(nodeId,processId,instanceId,formId,oldFormId,userId,workflowId,pdfPath){
			$(':button').css("backgroundColor","#FFFFFF");
			//$(':button').style.backgroundColor="#FFFFFF";
			this.document.getElementById(processId).style.backgroundColor="#61D7FA";
			//this.document.getElementById(processId).style.FontColor="blue";
			jsonObj = {};
			jsonObj['nodeId'] = nodeId;
			jsonObj['processId'] = processId;
			jsonObj['instanceId'] = instanceId;
			jsonObj['formId'] = formId;
			//jsonObj['oldFormId'] = oldFormId;
			jsonObj['userId'] = userId;
			jsonObj['workflowId'] = workflowId;
			jsonObj['pdfPath'] = pdfPath; 
		//	alert(JSON2.stringify(jsonObj));
		}
		function openChildWf(processId,instanceId,toNodeId){
			$.ajax({   
				url : '${ctx }/table_openChildProcess.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_openChildProcess.do)');
				},
				data : {
					'processId':processId,'instanceId':instanceId,'toNodeId':toNodeId
				},    
				success : function(childWfJson) {  
					if(childWfJson != ''){
						 childWfJson = JSON2.parse(childWfJson);
						 var i=0,l=childWfJson.length;
						 var trBak = "<tr id=\""+processId+"NewTr\"><td colspan=\"8\"><table id=\""+processId+"NewTable\" class=\"infotan mt10\" width=\"100%\" style=\"line-height:10px;\"><tbody></tbody></table></td></tr>";
						 //当前行后面添加tr
					     $('#'+processId+"Tr").after(trBak);
					     //取第一个instanceId
					     var childInstanceId = childWfJson[0].wfInstanceUid;
					     for(;i<l;i++){
					    	 //用于显示流程分割行
						     if(childInstanceId != childWfJson[i].wfInstanceUid){
						    	 childInstanceId = childWfJson[i].wfInstanceUid;
						    	 $('#'+processId+"NewTable").append("<tr><td colspan=\"8\">&nbsp;&nbsp;</td></tr>");
							 }
						     var tableContent = "";
						     tableContent += "<tr id=\""+childWfJson[i].wfProcessUid+"Tr\">";
						     tableContent += "<td width=\"5%\" style=\"text-align:center;\">"+childWfJson[i].stepIndex+"</td>";
						     tableContent += "<td width=\"10%\" style=\"text-align:center;\">";
						     if(childWfJson[i].nodeName == '推送' ){
						    	 tableContent += childWfJson[i].nodeName;
							 }else{
								 tableContent += "<input type=\"button\" id="+childWfJson[i].wfProcessUid+" onclick=\"openProcessForm('"+childWfJson[i].nodeId+"','"+childWfJson[i].wfProcessUid+"','"+childWfJson[i].wfInstanceUid+"','"+childWfJson[i].formId+"','','"+childWfJson[i].userId+"','"+childWfJson[i].workflowId+"','"+childWfJson[i].pdfPath+"');\" value='"+childWfJson[i].nodeName+"'\>";
							 }
						      if(childWfJson[i].isHaveChild == '1'){
							     tableContent += "<span id=\""+childWfJson[i].wfProcessUid+"Href\"><a href=\"#\" onclick=\"openChildWf('"+childWfJson[i].wfProcessUid+"','"+childWfJson[i].wfInstanceUid+"','"+childWfJson[i].toNodeId+"');\"><font color=\"red\" style=\"font-size:25px\">+</font></a></span>";
							 }
						     tableContent += "</td>";
						     tableContent += "<td width=\"15%\" style=\"text-align:center;\">"+childWfJson[i].fromUserName+"</td>";
						     tableContent += "<td width=\"15%\" style=\"text-align:center;\">"+childWfJson[i].userName+"</td>";
						     if(childWfJson[i].jssj !='null' && childWfJson[i].jssj != null){
							     tableContent += "<td width=\"15%\" style=\"text-align:center;\">"+childWfJson[i].jssj+"</td>";
							 }else{
							     tableContent += "<td width=\"15%\" style=\"text-align:center;\">&nbsp;&nbsp;</td>";
						     }
						     if(childWfJson[i].finshTime !='null' && childWfJson[i].finshTime != null){
							     tableContent += "<td width=\"15%\" style=\"text-align:center;\">"+childWfJson[i].finshTime+"</td>";
							 }else{
							     tableContent += "<td width=\"15%\" style=\"text-align:center;\">&nbsp;&nbsp;</td>";
						     }
						    // tableContent += "<td width=\"15%\" style=\"text-align:center;\">"+childWfJson[i].commentList+"</td>";
						     if(childWfJson[i].isBack == '2'){
							     tableContent += "<td width=\"15%\" style=\"text-align:center;\">废弃过程</td>";
							 }else if(childWfJson[i].isBack == '1'){
							     tableContent += "<td width=\"15%\" style=\"text-align:center;\">回收过程</td>";
						     }else{
							    tableContent += "<td width=\"15%\" style=\"text-align:center;\">&nbsp;&nbsp;</td>";
						     }
						     tableContent += "</tr>";
							 $('#'+processId+"NewTable").append(tableContent);
						 }
						 var newHerf = '<a href="#" onclick="closeChildWf(\''+processId+'\',\''+instanceId+'\',\''+toNodeId+'\');"><font color="red" ><span id="'+processId+'Href" style="font-size:35px"> -</span></font></a>';
						 $('#'+processId+"Href").html(newHerf);
					}
				}
			});
		}

		function closeChildWf(processId,instanceId,toNodeId){
			$('#'+processId+"NewTr").html("");	 
			$('#'+processId+"Href").html('<a href="#" onclick="openChildWf(\''+processId+'\',\''+instanceId+'\',\''+toNodeId+'\');"><font color="red"><span id="'+processId+'Href"  style="font-size:25px">+</span></font></a>');
		}
		
		function cdv_getvalues(){
			//	alert(JSON2.stringify(jsonObj));
			/* var jsonArray = [];
			jsonArray.push(jsonObj); */
				return JSON2.stringify(jsonObj);
		}
		
	</script>
</html>
