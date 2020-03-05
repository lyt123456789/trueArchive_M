<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
    <script src="${ctx }/pdfocx/json2.js?x=2022" type="text/javascript"></script>
</head>

<body>
    <div class="tw-layout">
    	<c:if test="${isPrint eq '1' }">
	    	<div class="tw-news-btns" style="margin: auto;width: 61px;margin-top: 10px;">
				<span class="tw-btn-primary" onclick="Print();"><i class="tw-icon-print"></i> 打 印</span>
			</div>
		</c:if>
        <div class="tw-list-wrap" id="printdiv">
            <table class="tw-fixtable" layoutH="140">
                <thead>
                    <tr>
                        <th align="left" width="10%">步骤</th>
                        <th align="left" width="10%">办理人员</th>
                        <th align="left" width="18%">收到时间</th>
                        <th align="left" width="18%">处理时间</th>
                        <th align="left"  width="15%">办理意见</th>
                        <th align="left" width="10%">提交人员</th>
                        <th align="left" width="9%">备注</th>
                    </tr>
                </thead>
                <tbody>
	                <c:forEach items="${processList}" var="process">
				                    <tr id="${process.wfProcessUid}Tr">
				                    	<td align="left">
				                    		${process.nodeName}
				                    		<c:if test="${process.isHaveChild == '1'}">
				                    			<span id="${process.wfProcessUid}Href">
					                    			<a href="#" onclick="openChildWf('${process.wfProcessUid}','${process.wfInstanceUid}');">
					                    				<font color="red">
					                    					<span style="font-size:25px;vertical-align:middle;line-height:24px;">+</span>
					                    				</font>
					                    			</a>
				                    			</span>
				                    		</c:if>
				                    	</td>
				                        <td align="left">${process.userName}</td>
				                        <td align="left">${process.str_applyTime}</td>
				                        <td align="left">${process.str_finshTime}</td>
				                        <td align="left">
				                        	<c:if test="${process.commentText == null || process.commentText == ''}">[未签署]</c:if>
				                        	<c:if test="${process.commentText != null && process.commentText != ''}">${process.commentText}</c:if>
				                        </td>
				                        <td align="left">${process.fromUserName}</td>
				                        <td align="left">
				                        	<c:if test="${process.isReturnStep eq '1' }">退回过程</c:if>
				                        	<c:if test="${process.isReturnStep != '1' }">
							            		<c:if test="${process.isBack eq '2' }">废弃过程</c:if>
							            	</c:if>
				                        </td>
				                    </tr>
		                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <script type="text/javascript">
		function Print() {
			window.document.body.innerHTML = document.getElementById("printdiv").innerHTML;
			window.print();
			window.location.reload();
		}
		
		function openChildWf(processId,instanceId){
			$.ajax({   
				url : '${ctx }/table_openChildProcess.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_openChildProcess.do)');
				},
				data : {
					'processId'	:processId,
					'instanceId':instanceId,
					'userId'	:'${userId}'
				},    
				success : function(childWfJson) {  
					if(childWfJson != ''){
						childWfJson = JSON2.parse(childWfJson);
						var i=0,l=childWfJson.length;
						var trBak = "<tr id=\""+processId+"NewTr\"><td colspan=\"7\"><table id=\""+processId+"NewTable\" class=\"infotan mt10\"  width=\"96%\" style=\"line-height:10px;\">";
						trBak += "<thead><tr><th width=\"10%\">步骤</th> <th width=\"10%\">办理人员</th> <th width=\"18%\">收到时间</th>";
						trBak += "<th width=\"18%\">处理时间</th><th width=\"15%\">办理意见</th><th width=\"10%\">提交人员</th><th width=\"9%\">备注</th></tr></thead>";
						trBak += "<tbody></tbody></table></td></tr>";
						//当前行后面添加tr
					    $('#'+processId+"Tr").after(trBak);
					    //取第一个instanceId
					    var childInstanceId = childWfJson[0].wfInstanceUid;
					    for(;i<l;i++){
					    	//用于显示流程分割行
						    if(childInstanceId != childWfJson[i].wfInstanceUid){
						    	childInstanceId = childWfJson[i].wfInstanceUid;
						    	$('#'+processId+"NewTable").append("<tr><td colspan=\"7\">&nbsp;&nbsp;</td></tr>");
							}
						    var tableContent = "";
						    tableContent += "<tr><td align='left'>"+childWfJson[i].nodeName;
						    if(childWfJson[i].isHaveChild == '1'){
						    	tableContent += "<span id='"+childWfJson[i].wfProcessUid+"Href'>"
						    	 			+ "<a href='#' onclick='openChildWf(\""+childWfJson[i].wfProcessUid+"\",\""+childWfJson[i].wfInstanceUid+"\");'>"
						    	 			+ "<font color='red><span style='font-size:25px;vertical-align:middle;line-height:24px;'>+</span>"
						    	 			+ "</font></a></span>";
						    }
							tableContent += "</td><td align='left'>"+childWfJson[i].userName+"</td>";
							
							tableContent += "<td align='left'>"+childWfJson[i].str_applyTime+"</td>";
							tableContent += "<td align='left'>"+childWfJson[i].str_finshTime+"</td>";
							var commentText = "[未签署]";
							if(null != childWfJson[i].commentText && '' != childWfJson[i].commentText){
								commentText = childWfJson[i].commentText;
							}
							tableContent += "<td align='left'>"+commentText+"</td>";
							tableContent += "<td align='left'>"+childWfJson[i].fromUserName+"</td>";
							var isReturnStep = childWfJson[i].isReturnStep;
							var isBack = childWfJson[i].isBack;
							var status = "";
							if(isReturnStep == '1'){
								status = '退回过程';
							}else{
								if(isBack == '2'){
									status = '废弃过程';
								}
							}
							tableContent += "<td align='left'>"+status+"</td></tr>";
							$('#'+processId+"NewTable").append(tableContent);
						}
						var newHerf = '<a href="#" onclick="closeChildWf(\''+processId+'\',\''+instanceId+'\');"><font color="red" ><span id="'+processId+'Href" style="font-size:35px;vertical-align: middle;line-height:24px;"> -</span></font></a>';
						$('#'+processId+"Href").html(newHerf);
					}
				}
			});
		}
	
		function closeChildWf(processId,instanceId){
			$('#'+processId+"NewTr").html("");	 
			$('#'+processId+"Href").html('<a href="#" onclick="openChildWf(\''+processId+'\',\''+instanceId+'\');"><font color="red"><span id="'+processId+'Href"  style="font-size:25px;vertical-align: middle;line-height:24px;">+</span></font></a>');
		}
    </script>
</body>
</html>
