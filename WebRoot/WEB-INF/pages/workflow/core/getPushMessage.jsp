<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<base target="_self">
<body>
<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" ><a ><span>消息列表</span></a></li>  
				</ul>
			</div>
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
    				<div id="w_list_print">
    <table class="list" width="100%" style="line-height:10px;">
    	 <thead>
           <tr>
               <td width="20%" style="font-weight:bold;text-align:center;">科室</td>
               <td width="15%" style="font-weight:bold;text-align:center;">人员</td>
               <td width="30%" style="font-weight:bold;text-align:center;">消息</td>
               <td width="20%" style="font-weight:bold;text-align:center;">时间</td>
               <td width="25%" style="font-weight:bold;text-align:center;">附件</td>
           </tr>
         </thead>  
           <c:choose>
               <c:when test="${not empty pmList}">
                   <c:forEach var="pm" items="${pmList}">
                       <tr>
                           <td align="center">
                              ${pm.pushKs}
                           </td>
                           <td align="center">
                              ${pm.pushEmpName}
                           </td>
                           <td align="center">
                               <textarea rows="5" cols="44" readonly="readonly"> ${pm.message}</textarea> 
                           </td>
                           <td align="center">
                               ${fn:substring(pm.pushTime,0,16)}
                           </td>
                           <td >
                                <c:forEach var="attList" items="${pm.attList}">
                                	<span>${attList.filename}</span>&nbsp;&nbsp;&nbsp;<img src="./dwz/button/dow.png" title="下载" style="cursor:pointer;" onclick="javascript:download('${attList.localation}','${fileDownloadUrl}?name=${attList.filename}&location=${attList.localation}')"/>
                                </c:forEach>
                           </td>
                       </tr>
                   </c:forEach>
               </c:when>
               <c:otherwise>
                   <tr>
                       <td colspan="6" align="center">
                          	无推送消息！
                       </td>
                   </tr>
               </c:otherwise>
           </c:choose>
      </table>
      </div>
      <iframe src="" id="aaa" style="display:none;"></iframe>
				</div>
				</div>

</body>
<script>
function download(fileLocation,downUrl){
	$.ajax({
		cache:false,
		async:false,
		type: "POST",
		url: "attachment_checkFileExist.do?location="+fileLocation,
		error: function(){
	   		alert('\u5f02\u6b65\u68c0\u67e5\u6587\u4ef6\u662f\u5426\u5b58\u5728\u5931\u8d25');
		},
		success: function(msg){
			if(msg=="no"){
				alert('\u9644\u4ef6\u4e0d\u5b58\u5728,\u65e0\u6cd5\u4e0b\u8f7d'); 
			}else if(msg=="yes"){
				downUrl = encodeURI(downUrl);
				document.getElementById("aaa").src = downUrl;
			}
		}
	});
}
</script>
</html>