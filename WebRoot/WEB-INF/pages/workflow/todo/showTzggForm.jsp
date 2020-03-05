<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<!--[if IE]>
<link href="${ctx}/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="${ctx}/dwz/js/speedup.js" type="text/javascript"></script>
<![endif]-->
<!--JS -->

<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
</head>
<%@ include file="/common/header.jsp"%>
<%@page import="net.sf.json.JSONArray"%>
<script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
<link href="${ctx}/images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
<Link href="${ctx}/images/common.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/images/index.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/form/jsp/widgets/common/js/doublelist.js" type="text/javascript"></script>
	<script type="text/javascript" defer="defer">
	window.onload=function(){
		var listValues='<%=request.getSession().getAttribute("listValues")==null?"":request.getSession().getAttribute("listValues")%>';
		var selects='<%=request.getParameter("selects")==null?"":request.getParameter("selects")%>';
		var limitValue='${limitValue}';
		var valuestr='<%=request.getParameter("values")==null?"":request.getParameter("values")%>';	
		var instanceId = '${instanceId}';
		tagvalues(listValues,selects,limitValue,valuestr,instanceId);//标签赋值，权限控制
	};
	function closeKj(){
		document.getElementById("frame1").contentWindow.objClose();
	}
	function objOpen(){
		document.getElementById("frame1").contentWindow.openClose();
	}
</script>
<body onload="init();" id="body">
<textarea style="display:none;" id="commentJson" name="commentJson">${commentJson}</textarea>
<textarea style="display:none;" id="pdfPath" name="pdfPath">${pdfPath}</textarea>
<input type="hidden" id="imageCount" name="imageCount" value="${imageCount}">
<div class="warp1">
<div class="dh">
<c:if test="${isds!='1'}">
</c:if>
<div class="bl_tab bl fl"><a style="cursor:pointer">
<span >表单</span></a>
</div>
</div>
<div class="content">
<div class="bl_nav" >
<!-- 左右跳转图片显示 start -->
	<iframe height="60px" id="mliframe1" width="40px"   style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:50px;top:350px;z-index:9;" src="${ctx}/mlzg.jsp"></iframe>
	<div id="gzl_pageLeftBox"><a id="leftPage" class="b_disable" onclick="changePageToLeft()" style="cursor:pointer;"></a></div>
	<div id="gzl_pageRightBox"><a id="rightPage" onclick="changePageToRight()" style="cursor:pointer;"></a></div>
	<!-- 左右跳转图片显示    end -->
	<ul class="clearfix">
      <li id="li_dir" style="padding-bottom:  6px;">
      	<a  href="#" class="contents fl ml8">目录</a>
      	<div class="bl_n_nav" style="z-index:11;top:35px;display:none;"  >
	      		<div class="bl_nav_top" style="z-index:11;" ></div>
	      	</div>
      <iframe height="203px" id="mliframe" width="99px"   style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:0px;top:40px;z-index:10;" src="${ctx}/ml.jsp?imageCount=${imageCount}"></iframe>		
 </li>   
      <li>
    	  <a href="#"  class="accessory ml5" onclick="changeClass()" >附件<em id="fjCount"></em></a>
        <div class="bl_n_nav bl_a_nav fouce" style="display:none;z-index:2;width: 810px; height : 100px;overflow: auto;" id="fjml">
          <div class="bl_nav_fj" >
          <%  Object object =request.getAttribute("atts");
          	  String str = "";
          	  if(object!=null){
          		  str = (String)object;
          	  }
          	Object obj =request.getAttribute("instanceId");
        	String instanceId = "";
        	if(instanceId!=null){
        		instanceId=(String)obj;
        	}
        	JSONArray js =null;
			 if(str!=null){
				 str =str.replace(",;|", "\"");
				 js =JSONArray.fromObject(str);
			 }
		 	if(js!=null){
		 		for(int i=0;i<js.size();i++){
		 			%>
   			   	    	<span id='<%=js.getString(i)+"show"%>'></span>
    	    		<%
		 		}
		 	}
          %>
          <span id="newfjshow"></span>
    	  <span id="oldfjshow"></span>
   		 <!--   <span id="newfjshow1"></span> -->
    	<div style="display: none;">
    	  <%
    		if(js!=null){
	 			for(int i=0;i<js.size();i++){
	 				%>
	 				  <trueway:att onlineEditAble="false" id='<%=(instanceId+js.getString(i))%>' docguid='<%=(instanceId+js.getString(i))%>' showId='<%=(js.getString(i)+"show")%>'   ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
	 				<%
	 			}
	 		}
    	%>
    		<c:if test="${instanceId!=allInstanceId }">
    		<trueway:att onlineEditAble="false" id='${instanceId}newfj' docguid='${instanceId}newfj' showId="newfjshow" ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
    		</c:if><trueway:att onlineEditAble="false" id='${instanceId}oldfj' docguid='${instanceId}oldfj' showId="oldfjshow" ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
    	</div>
         </div>
            <div class="bl_nav_bottom"></div>
        </div>
      </li>
    </ul>
</div>
<div>
	<div style="overflow: hidden;">
	<iframe  height="100%" id="lcbox" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:97%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getProcess.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
			<c:choose>
				<c:when test="${(isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' )}">
					<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&isWriteNewValue=false&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&atts=''&isOver=${isOver}"></iframe>
				</c:when>
				<c:otherwise>
					 <iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&isWriteNewValue=${isWriteNewValue}&newInstanceId=${newInstanceId}&newProcessId=${newProcessId}&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&atts=${atts}&isOver=${isOver}"></iframe>
				 </c:otherwise>
			</c:choose></div>
</div>
</div>
</div>
<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
<input type="hidden" id="neNodeId" name="neNodeId" value="">
<input type="hidden" id="neRouteType" name="neRouteType" value="">
<input type="hidden" id="toName" name="toName">
<input type="hidden" id="xtoName" name="xtoName">
<input type="hidden" id="xccName" name="xccName">
<input type="hidden" id="yqinstanceid" name="yqinstanceid" value="${yqinstanceid}">

<input type="hidden" id="closeframe" name="closeframe" value="0">
<input type="hidden" id="flowinfo" name="flowinfo" value="">
</body>
<script src="images/jquery.min.js" type="text/javascript"></script>
<script src="images/common.js" type="text/javascript"></script>
<script src="images/jquery.tab.js" type="text/javascript"></script>
<script type="text/javascript">
function sendToChat(processId){
	var ret = window.showModalDialog('${ctx}/table_userGroup.do?click=true&nodeId=${nodeId}&send=${send}&isTreeAll=1&routType=4&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
	if(ret == "undefined" || typeof(ret) == "undefined"){
     	//window.location.reload();
	}else {
		//var res = ret.split(";");
		var userId = ret;
		var commentJson = $("#commentJson").val();
		//ajax以后获取数据,转换文件
		$.ajax({   
			url : '${ctx}/table_changeTrueToPdf.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_changeTrueToPdf.do)');
			},
			data : {
				'processId':processId, 'commentJson':commentJson, 'xto_names':userId
			},
			success : function(data) {  
				
				
			}
		});
	}
}

function updateReplay(replayId){
	$.ajax({   
		url : '${ctx}/replay_updateReplayStatus.do',
		type : 'POST',   
		cache : false,
		async : false,
		data:{
			'replayId':replayId
		},
		error : function() {  
			alert('AJAX调用错误(replay_updateReplayStatus.do)');
		},
		success : function(msg) {  
			 var result=eval('('+msg+')');
			 if(result.success){
				 alert("办理成功！");
				 window.close();
				 window.returnValue = "refresh";
			 }
		}
	});
}

function sendFile(){
	//获取部门 树
	var obj = document.getElementById("frame1").contentWindow;
	var instanceId = eval(obj.document.getElementById("instanceId")).value;
	var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
	var formId = eval(obj.document.getElementById("formId")).value;
	var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
	json = JSON2.stringify(trueJSON.pdfjson);
	formjson = trueJSON.formjson;
	var tagValue = getProValue();
	var pageValue = tagValue;
	if(formjson != ''){
		pageValue = mergeJson(tagValue,formjson);
	}
	ret=window.showModalDialog('${ctx}/selectTree_showDepartment.do?isSend=1&deptId=${deptId}&t='+new Date(),null,"dialogWidth:980px;dialogHeight:500px;help:no;status:no");
	// ret  为 id 加  name id*name
	var res = ret.split("*");
	document.getElementById('xtoName').value = res[0];
	var params = pageValue;
	if(ret){
		var ids = res[0].split(",");
		// ids 的个数等于1  直接设置为回复人 , 超过1 则弹出框 选择 回复人
		params['rebacker'] = res[0];
		params['dffss'] = res[2];
		// from  url 
		params['instanceId'] = instanceId;
		params['workFlowId'] = workFlowId;
		params['processId'] = '${processId}';
		params['nodeId'] = '${nodeId}';
		params['formId'] = formId; 
		params['stepIndex'] = '${stepIndex}';
		params['xtoName'] = document.getElementById('xtoName').value;
		params['xccName'] = document.getElementById('xccName').value;
		// form data 
		params['json'] = json; 
		params['itemId'] = '${itemId}';
		$.ajax({  
			url : 'table_doFileDoc.do',
			type : 'POST',   
			cache : false,
			async : false,
			data : params,
			error : function() {  
				alert('AJAX调用错误(table_doFileDoc.do)');
			},
			success : function(msg) {
				alert("发送成功！");
				window.close();
				window.returnValue='refresh';
			}
		});
	}
}
</script>
<script>
var len1=1;
function  changeClass(){
	if(len1==0){
		len1=1;
		$('#fjml').removeClass("fouce");
		document.getElementById("fjml").style.display="none";
	    document.getElementById("frame1").contentWindow.document.getElementById("mianDiv").style.height="0px"; 
	}else{
		len1=0;
		$('#fjml').addClass("fouce");
		document.getElementById("fjml").style.display="";
        document.getElementById("frame1").contentWindow.document.getElementById("mianDiv").style.height="100px"; 
	}
};
$(document).ready(function() {
	if(document.getElementById('${instanceId}newfjattachmentDel')){
		document.getElementById('${instanceId}newfjattachmentDel').style.display="none" ;
	}
	if(document.getElementById('${instanceId}fjattachmentDel')){
		document.getElementById('${instanceId}fjattachmentDel').style.display="none" ;
	}
	if(document.getElementById('${instanceId}attzwfjattachmentDel')){
		document.getElementById('${instanceId}attzwfjattachmentDel').style.display="none" ;
	}
	if(document.getElementById('${instanceId}oldfjattachmentDel')){
		document.getElementById('${instanceId}oldfjattachmentDel').style.display="none" ;
	}
    $('#li_dir').hover(over, out);
	    function over(event) {
	        $(this).addClass("fouce");
	        $('.bl_n_nav', this).show();
	        $('#mliframe').show();
	    }
	    function out(event) {        
	        $(this).removeClass("fouce");
	        $('.bl_n_nav', this).hide();
	        $('#mliframe').hide();
			}
	    var cd =  document.body.clientWidth/2-405-123;
	   if("${!((isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' ))}"!='true'){
		   cd =  document.body.clientWidth/2-405-20;
	   }
	    if(  document.getElementById('fjml')){
	    	  document.getElementById('fjml').style.left=cd+"px";
	    	  document.getElementById('fjml').style.top="50px";
	    }
	    if('${isds}'!='1'){
	    	$('.bl_tab').each(function(i){
		    	$(this).click(function(){
		    		if(i==0){
		    			$(this).addClass("bl").removeClass("lc");
		    			$('.bl_tab').eq(1).addClass("lc").removeClass("bl");
		    			$('#lcbox').show();
						document.getElementById("gzl_pageLeftBox").style.display="none";
						document.getElementById("gzl_pageRightBox").style.display="none";
		    		}else{
		    			$(this).addClass("bl").removeClass("lc");
		    			$('.bl_tab').eq(0).addClass("lc").removeClass("bl");
		    			$('#lcbox').hide();
						document.getElementById("gzl_pageLeftBox").style.display="";
						document.getElementById("gzl_pageRightBox").style.display="";
		    		}
		    	});
		    });
	    }
	    /**var commentJson = '${commentJson}';
		if(commentJson != ''){
			var json = JSON2.parse(commentJson);
			if(json.length > 0){
				for(var i=1;i<json.length;i++){
					var page = json[i].page+1;
					document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+page)).innerHTML = "第"+page+"页<span class='edit' style=\"font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;\" title=\"内容有改动\"></span>";
				}
			}
		}**/
		
		if('${imageCount}' == 1){
			if(document.getElementById("mliframe").contentWindow.document.getElementById("ahref1")){
				document.getElementById("mliframe").contentWindow.document.getElementById("ahref1").className="";
				document.getElementById("mliframe").contentWindow.document.getElementById("ahref1").className="hot";
				document.getElementById("gzl_pageLeftBox").style.display="none";
				document.getElementById("gzl_pageRightBox").style.display="none";
			}
		}
		
		if("${attSize}" != 0){
		 	document.getElementById("fjCount").innerHTML = "(${attSize})";
		}
});
function PrintPDF(isOver){
	 document.getElementById("frame1").contentWindow.PrintPDF(isOver);
}

var directOver  = 0; // pdf 是否是正的
function rotate(direction,state){
	directOver =  (directOver+1)%2;
	 document.getElementById("frame1").contentWindow.rotate(state);
	 if(direction == "L"){
		 if(directOver == 1){
			 document.getElementById("rotateL").disabled='disabled';
		 }
		 document.getElementById("rotateR").disabled = ''; 
	 }else if(direction == "R"){
		 document.getElementById("rotateL").disabled = '';
		 if(directOver == 1){
			 document.getElementById("rotateR").disabled='disabled';
		 }
		
	 }
}
//<script>

function updateMeetingState(state){
	var obj = document.getElementById("frame1").contentWindow;
	var instanceId = eval(obj.document.getElementById("instanceId")).value;
	$.ajax({  
		url : 'meetings_updateMeetingState.do',
		type : 'POST',   
		cache : false,
		async : false,
		data :{
			'instanceId' : instanceId,
			'state' :state
		},
		error : function() {  
			alert('AJAX调用错误(meetings_updateMeetingState.do)');
		},
		success : function(msg) {
			if(msg == 'no'){
				alert("更新状态失败，请联系管理员！");
				return false;
			}else if(msg == 'yes'){
				//alert("取消申请成功！");
				//window.location.href="${ctx}/meeting_getDeptMeetingList.do?itemIds=${itemIds}";
			}
		}
	});
}

//套打，并可以保存到待办(城管定制)
function PrintNoHdr_ToDb(nodeId, workFlowId){    
	var operate = 0;
	var obj = document.getElementById("frame1").contentWindow;
	var tagValue = getProValue();//得到属性值
	var instanceId = eval(obj.document.getElementById("instanceId")).value;
	var formId = eval(obj.document.getElementById("formId")).value;
	var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
	var att_comment_id = "";
	if('${isFirst}' == 'true'){
		var title = '${title_column}';
		var titles = title.split(",");
		var isHaveTitle = "";
		for(var i=0;i < titles.length;i++){
			var filedValue = tagValue[titles[i]];
			if(filedValue !=""){
				isHaveTitle +=	filedValue;
			}
		}
	}else{
		isHaveTitle = "ishave"; //如果不是第一步，就不用判断
	}
	var params = tagValue;
	params['instanceId'] = instanceId;
	params['formId'] = formId;
	params['oldFormId'] = '${oldFormId}';
	params['itemId'] = '${itemId}';
	params['nodeId'] = '${nodeId}';
	params['workFlowId'] = workFlowId;
	params['processId'] = '${processId}';
	params['operate'] = operate;
	if(isHaveTitle != null && isHaveTitle != ""){
		$.ajax({
			url :'${ctx}/table_onlySaveOfPrint.do',
			//url : '${ctx}/table_onlySaveOfPrint.do?instanceId='+instanceId+'&formId='+formId+'&oldFormId=${oldFormId}&itemId=${itemId}'+'&nodeId=${nodeId}'+'&workFlowId='+workFlowId+'&processId=${processId}&operate='+operate+tagValue,
			type : 'POST',  
			cache : false,
			async : false,
			dataType:'json',
			data : params,
			error : function() {
				alert('AJAX调用错误(table_onlySave.do)');
				return false;
			},
			success : function(msg) {   
				if(msg != ''){
					var a=window.open("${ctx}/template_toDyJsp.do?isToDb=1&nodeId="+nodeId+"&instanceId="+instanceId,'newwindow','height=600, width=1000,top='+(screen.height-600)/2+',left='+(screen.width-1000)/2+',toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no,status=no');
				}else{
					if(operate==1){
						alert("完成办理有误,请联系管理员！");
					}else {
						alert("取消有误,请联系管理员！");
					}
					return false;
				}
			}
		});
	}else {
		alert("流程标题所对应的存储字段不能为空!");
	}
    
	
}   

function showTrueJson(){
	
}
	function initRedirect(){
		
		$.ajax({
			url : '${ctx}/table_getAllnodes.do',
			type : 'POST',  
			cache : false,
			async : true,
			dataType:'json',
			data :{
				'workFlowId':'${workFlowId}'
			},
			error : function() {
				alert('AJAX调用错误(table_getAllnodes.do)');
			},
			success : function(obj) {
				var html = "";
				for(var i=0;i<obj.length;i++){
					html +=  '<a class="back" href="javascript:routeType(\''+obj[i].wfn_id+'\');">'+obj[i].wfn_name+'</a>';
				} 
				document.getElementById("redirMenu").innerHTML=html; 
			}
		});
	}
	
	function routeType(nextNodeId,type,relation,cType,isMergeChild){
		var obj = document.getElementById("frame1").contentWindow;
		var formId = obj.document.getElementById("formId").value;
		var o = '${isbt}';
		if(o){
			var error =obj.isCheckBt(o.split(";"));
			if(error!=""){
				alert(error);
				return;
			}
		}
		
		/* if('${isFirst}' == 'true'){
			var json = getLocationJson();
			// 报错html 位置入库
			$.ajax({
				url : '${ctx}/form_updateFormLocationJson.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'formid':formId
				    ,'locations':JSON2.stringify(json)
				},
				error : function() {
					alert('AJAX调用错误');
				},
				success : function(ret) {
				}
			}); 
		}
		 */
	
		var workFlowId = obj.document.getElementById("workFlowId").value;
		var instanceId = obj.document.getElementById("instanceId").value;
		
		var processId = obj.document.getElementById("processId").value;
		var neRouteType = document.getElementById("neRouteType").value ;
		var hsFlag = true;
		var isSend = 0;
		var ret=null;
		if(type=='node'){
			// ------- add by yuxl  判断 办件是否被收回-------
			/* $.ajax({   
				url : 'table_stepIsOver.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_stepIsOver.do)');
				},
				data : {
					'instanceId':instanceId,'processId':processId
				},    
				success : function(msg) {  
					if(msg == 'yes'){
		
					}else{
						alert("该步骤已被回收,不可办理");
						hsFlag = false;
					}
					
				}
			}); */
			
			// --------end -------
		
		/**
		//意见必填
		$.ajax({
			url : '${ctx}/table_isOrNotWriteComment.do',
			type : 'POST',  
			cache : false,
			async : true,
			error : function() {  
				alert('AJAX调用错误(table_isOrNotWriteComment.do)');
			},
			data : {
				'workFlowId':workFlowId,'nodeId':'${nodeId}','instanceId':instanceId,'formId':formId,'processId':processId
			},
			success : function(msg) {
				if(msg != ''){
					var info = "";
					var arr = msg.split(";");
					for(var m=0;m < arr.length;m++){
						info += arr[m]+"、";
					}
					if(info.length > 0){
						info = info.substring(0,info.length-1);
					}
					alert(info+"为空,请填写！");
					return ;
				}else{
					**/
					//判断该节点是否设置了固定人和是否设置了返回指定节点
			if(hsFlag){
				$.ajax({   
					url : 'table_isOnlyPerson.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_isOnlyPerson.do)');
					},
					data : {
						'workFlowId':workFlowId,
						'nodeId':'${nodeId}',
						'nextNodeId':nextNodeId,
						'instanceId':instanceId,
						'processId':processId,
						'formId':formId
					},    
					success : function(person) {  
						if(person != ''){
							var persons  = person.split("=");
							if(persons[0] == 'more'){
								alert("该发送节点既设置了固定人员也设定了指定节点,因只能设置一种,请重新设置！");
							}else if(persons[0] == 'bxwqs'){
								//获取person[1];
								var msg = persons[1];
								var res = msg.split(";");
								document.getElementById('xtoName').value = res[0];
								document.getElementById('xccName').value = res[1];
								document.getElementById("neNodeId").value = nextNodeId;
								document.getElementById("neRouteType").value = "2";
								isSend ++;
								//sendNext();
							}else {
								var zdPerson = persons[1].split(";");
								var zdPersonXtoName = zdPerson[0];
								var zdPersonXccName = new Array();
								if(zdPerson[1] != ''){
									zdPersonXccName = zdPerson[1].split(",");
								}
								if(persons[0] == 'gdPerson'){
									document.getElementById('toName').value = persons[1].substring(0,persons[1].length-1);
									document.getElementById("neNodeId").value = nextNodeId;
									//单人模式
									document.getElementById("neRouteType").value = 0;
									isSend ++;
									//sendNext();
								}else if(persons[0] == 'zdPerson'){
									if(zdPersonXtoName != '' && zdPersonXccName.length == 0){//指定节点只有一个人，且为主送
										document.getElementById('toName').value = zdPersonXtoName;
										document.getElementById("neNodeId").value = nextNodeId;
										//单人模式
										document.getElementById("neRouteType").value = 0;
										isSend ++;
										//sendNext();
									}else if(zdPersonXtoName == '' && zdPersonXccName.length == 1){//指定节点只有一个人，但是为抄送
										document.getElementById('toName').value = zdPersonXccName;
										document.getElementById("neNodeId").value = nextNodeId;
										//单人模式
										document.getElementById("neRouteType").value = 0;
										isSend ++;
										//sendNext();
									} else{
										$.ajax({   
											url : 'table_getWfLineOfType.do',
											type : 'POST',   
											cache : false,
											async : false,
											error : function() {  
												alert('AJAX调用错误(table_getWfLineOfType.do)');
											},
											data : {
												'nextNodeId':nextNodeId,
												'nodeId':'${nodeId}',
												'workFlowId':workFlowId
											},    
											success : function(nodeInfo) {  
												var msg = nodeInfo.split(",")[0];
												//指定人员大于1，有主送，有抄送
												if(zdPersonXtoName != '' && zdPersonXccName.length > 0){
													if(msg*1 == 2){
														document.getElementById('xtoName').value = persons[1].split(";")[0];
														document.getElementById('xccName').value = persons[1].split(";")[1];
													}else{
														document.getElementById('toName').value = persons[1].split(";")[1];
													}
													document.getElementById("neNodeId").value = nextNodeId;
													document.getElementById("neRouteType").value = msg;
													isSend ++;
													//sendNext();
												}else{
													//给人员选择iframe传值
												//	document.getElementById("frame3").src = '${ctx}/table_userGroup.do?click=true&nodeId='+nextNodeId+'&send=${send}&routType='+msg;
													//给发送下一步传所点击的按钮的属性值--下一步的节点值
													ret=window.showModalDialog('${ctx}/table_userGroup.do?click=true&nodeId='+nextNodeId+'&send=${send}&routType='+msg+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
													if(ret){
														if(msg*1 == 2){
															var res = ret.split(";");
															document.getElementById('xtoName').value = res[0];
															document.getElementById('xccName').value = res[1];
														}else{
															document.getElementById('toName').value = ret;
														}
														document.getElementById("neNodeId").value = nextNodeId;
														//所选择的路由类型
														if(msg==null || msg =='' || msg=='null'){//重定向时
															document.getElementById("neRouteType").value = 0;
														}else {
															document.getElementById("neRouteType").value = msg;
														}
														isSend ++;
														//sendNext();
													}
												}
											}
										});
									}
								}
							}
						}else{
							$.ajax({   
								url : 'table_getWfLineOfType.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_getWfLineOfType.do)');
								},
								data : {
									'nextNodeId':nextNodeId,
									'nodeId':'${nodeId}',
									'workFlowId':workFlowId
								},    
								success : function(nodeInfo) { 
									var msg = nodeInfo.split(",")[0];
									var exchange = nodeInfo.split(",")[1];
									//给人员选择iframe传值
								//	document.getElementById("frame3").src = '${ctx}/table_userGroup.do?click=true&nodeId='+nextNodeId+'&send=${send}&routType='+msg;
									//给发送下一步传所点击的按钮的属性值--下一步的节点值
									ret=window.showModalDialog('${ctx}/table_userGroup.do?exchange='+exchange+'&click=true&nodeId='+nextNodeId+'&send=${send}&routType='+msg+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
									if(ret){
										if(obj.document.getElementById("jzk1")){
										obj.zzfs();
										}
										if(msg*1 == 2){
											var res = ret.split(";");
											document.getElementById('xtoName').value = res[0];
											document.getElementById('xccName').value = res[1];
										}else{
											document.getElementById('toName').value = ret;
										}
										document.getElementById("neNodeId").value = nextNodeId;
										//所选择的路由类型
										if(msg==null || msg =='' || msg=='null'){//重定向时
											document.getElementById("neRouteType").value = 0;
										}else {
											document.getElementById("neRouteType").value = msg;
										}
										isSend ++;
									}
								}
							});
						}
						if(isMergeChild == '1'){
							if(ret){
							var xtoName = document.getElementById("xtoName").value;
							var xccName = document.getElementById("xccName").value;
							var tagValue = getProValue();
							//获取最新的包含意见的pdf地址
							//var pdfNewPath = document.getElementById("frame1").contentWindow.getPrintPdfPath();
							var pdfNewPath = "";
							//json
							var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
							var json = JSON2.stringify(trueJSON.pdfjson);
							var formjson = trueJSON.formjson;
							var pageValue =tagValue;
							if(formjson != ''){
								//tagValue = tagValue+"&"+formjson;
								pageValue = mergeJson(tagValue,formjson);
							}
							//判断是否走公文交换平台--针对只有一个主送,一个抄送
							var params = pageValue;
							params['xtoName'] = xtoName;
							params['xccName'] = xccName;
							params['workFlowId'] = workFlowId;
							params['nextNodeId'] = nextNodeId;
							params['oldProcessId'] = '${processId}';
							params['pdfNewPath'] = pdfNewPath;
							params['json'] = json;
							params['formId'] = '${formId}';
							$.ajax({   
								url : 'table_isGoExChange.do',		//分发：插入需要保存表单值
								type : 'POST',   
								cache : false,
								async : false,
								data : params,
								//data : 'xtoName='+xtoName+'&xccName='+xccName+'&workFlowId='+workFlowId+'&nextNodeId='+nextNodeId+'&oldProcessId=${processId}&pdfNewPath='+pdfNewPath+'&json='+json+'&formId=${formId}'+tagValue,
								error : function() {  
									alert('AJAX调用错误(table_isGoExChange.do)');
									if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}
								},
								success : function(msg) {
									if(msg != '' && msg.indexOf('noInfo')!=0){
										var msgArr = msg.split(";");
										var isSuccess = 0;
										//json
										//var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
										//var json = JSON2.stringify(trueJSON.pdfjson);
										//针对只有一个主送,一个抄送
										for(var i=0,len=msgArr.length;i<len;i++){
											var isMerge = msgArr[i].split(",")[0];
											var userId = msgArr[i].split(",")[1];
											var newInstanceId = msgArr[i].split(",")[2];
											var newProcessId = msgArr[i].split(",")[3];
											var newFormId = msgArr[i].split(",")[4];
											var childWorkflowId = msgArr[i].split(",")[5];
											var childNodeId = msgArr[i].split(",")[6];
											var doType = msgArr[i].split(",")[7];
											//为了入库时取新的id,把原来的get参数名换了=废弃不用
											//tagValue = tagValue.replace("instanceId","instanceIdOld");
											//tagValue = tagValue.replace("workFlowId","workFlowIdOld");
											//tagValue = tagValue.replace("processId","processIdOld");
											//tagValue = tagValue.replace("formId","formIdOld");
											var isLast=0;
											if(i==len-1){
												isLast=1;
											}
											
											// 参数
											var params = pageValue;
											// from url 
											params['isLast'] = isLast;
											params['isMerge'] = isMerge;
											params['isSmsRemind'] = isSmsRemind;
											// from data
											params['newInstanceId'] = newInstanceId;
											params['newFormId'] = newFormId;
											params['newProcessId'] = newProcessId;
											params['newWorkFlowId'] = childWorkflowId;
											params['childNodeId'] = childNodeId;
											params['userId'] = userId;
											params['oldProcessId'] = '${processId}';
											params['nextNodeId'] = nextNodeId;
											params['json'] = json;
											params['nodeId'] = '${nodeId}';
											params['doType'] = doType;
											$.ajax({   
												//url : 'table_getNextIsMerge.do?isLast='+isLast+'&isMerge='+ isMerge,
												url : 'table_getNextIsMerge.do',
												type : 'POST',   
												cache : false,
												async : false,
												data :params,
												/* data: 'newInstanceId='+newInstanceId+'&newFormId='+newFormId+'&newProcessId='+newProcessId
													+'&newWorkFlowId='+childWorkflowId+'&childNodeId='+childNodeId+'&userId='+userId+'&oldProcessId=${processId}'
													+'&nextNodeId='+nextNodeId+'&json='+json+'&nodeId=${nodeId}&doType='+doType+tagValue,
												 */error : function() {  
													if(obj.document.getElementById("jzk1")){
														obj.fsjs();
													}
													alert('AJAX调用错误(table_getNextIsMerge.do)');
												},
												success : function(msg) {
													if(msg == 'success'){
														isSuccess ++;
													}
												}
											});
										}
										if(isSuccess > 0){
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											alert("发送成功！");
											window.close();
											window.returnValue='refresh';
										}
									}else if(msg.indexOf('noInfo')==0){
										$("#closeframe").val("1");
										//alert($("#closeframe").val());
										$("#flowinfo").val(workFlowId+";"+nextNodeId+";"+xtoName+";"+xccName);
										var empGuid = msg.split(",")[1];
										document.getElementById('xtoName').value = empGuid;
										document.getElementById('xccName').value = '';
										sendNext();	//保存中间步骤值
									}else{
										alert("人员选择不正确,请重新选择!");
										return ;
									}
								}
							});
						}else{
							if(obj.document.getElementById("jzk1")){
								obj.fsjs();
							}
						}
						}else{
							if(isSend > 0){
								sendNext();
							}
						}
					}
				});
			}else{
				window.location.href="${ctx}/table_getPendingList.do";
			}
					
					/**
				}
			}
		});
					**/
		}else if(type=='childWf'){
			//子流程打开第一步填表单
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
   			var commentJson = JSON2.stringify(trueJSON.pdfjson);
   			//commentJson = encodeURI(commentJson);
			var retVal = window.showModalDialog('${ctx}/table_openFirstForm.do?nodeId=${nodeId}&isChildWf=true&newPdfPath=${pdfPath}&commentJson='+commentJson+'&formId=${formId}&isWriteNewValue=false&workflowid='+nextNodeId+'&itemid=${itemId}&cType='+cType+'&relation='+relation+'&processId='+processId+'&finstanceId='+instanceId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
			if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
		        	// return ;
		     }else{
		    	 if(relation == '1'){//同步
					window.close();
					window.returnValue='refresh';
				 }
		     }
		}else{
			window.location.href="${ctx}/table_getPendingList.do";
		}
	}
	
	function goExChangeSendVlaue(){
		var flowinfo = $("#flowinfo").val();
		var info = flowinfo.split(";");
		var workFlowId = info[0];
		var nextNodeId = info[1];
		var xtoName = info[2];
		var xccName = info[3];		
		var allInstanceId = '${finstanceId}';
		var processId = "";
		$.ajax({  
			url : 'table_getMaxProcessIdByAllInstanceId.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'allInstanceId':allInstanceId
			},
			error : function() {  
				alert('AJAX调用错误(table_getMaxProcessIdByAllInstanceId.do)');
			},
			success : function(msg) {
				processId = msg ;
		var tagValue = getProValue();
		var pageValue =tagValue;
		//获取最新的包含意见的pdf地址
		//var pdfNewPath = document.getElementById("frame1").contentWindow.getPrintPdfPath();
		var pdfNewPath = "";
		//json
		var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
		var json = JSON2.stringify(trueJSON.pdfjson);
		var formjson = trueJSON.formjson;
		if(formjson != ''){
			pageValue = mergeJson(tagValue,formjson);
			//tagValue = tagValue+"&"+formjson;
		}
		// 参数
		var params = {};
		params['xtoName'] = xtoName;
		params['xccName'] = xccName;
		params['workFlowId'] = workFlowId;
		params['nextNodeId'] = nextNodeId;
		params['oldProcessId'] = processId;
		params['pdfNewPath'] = pdfNewPath;
		params['json'] = json;
		params['formId'] = '${formId}';
		params['worktype'] = '1';
		//判断是否走公文交换平台--针对只有一个主送,一个抄送
		$.ajax({   
			url : 'table_isGoExChange.do',		//分发：插入需要保存表单值
			type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_isGoExChange.do)');
					if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}
				},
				data : params,
				//data : 'xtoName='+xtoName+'&xccName='+xccName+'&workFlowId='+workFlowId+'&nextNodeId='+nextNodeId+'&oldProcessId='+processId+'&pdfNewPath='+pdfNewPath+'&json='+json+'&formId=${formId}&worktype=1',
				success : function(msg) {
					if(msg != ''){
						var msgArr = msg.split(";");
						var isSuccess = 0;
						//针对只有一个主送,一个抄送
						for(var i=0,len=msgArr.length;i<len;i++){
							var isMerge = msgArr[i].split(",")[0];
							var userId = msgArr[i].split(",")[1];
							var newInstanceId = msgArr[i].split(",")[2];
							var newProcessId = msgArr[i].split(",")[3];
							var newFormId = msgArr[i].split(",")[4];
							var childWorkflowId = msgArr[i].split(",")[5];
							var childNodeId = msgArr[i].split(",")[6];
							var doType = msgArr[i].split(",")[7];
							//为了入库时取新的id,把原来的get参数名换了=废弃不用
							var isLast=0;
							if(i==len-1){
								isLast=1;
							}
							
							// 参数
							var params = pageValue;
							params['isLast'] = isLast;
							params['isMerge'] = isMerge;
							params['newInstanceId'] = newInstanceId;
							params['newFormId'] = newFormId;
							params['newProcessId'] = newProcessId;
							params['newWorkFlowId'] = childWorkflowId;
							params['childNodeId'] = childNodeId;
							params['userId'] = userId;
							params['oldProcessId'] = processId;
							params['nextNodeId'] = nextNodeId;
							params['type'] = '1';
							params['json'] = json;
							params['nodeId'] = '${nodeId}';
							params['doType'] = doType;
							$.ajax({   
								url : 'table_getNextIsMerge.do',
								type : 'POST',   
								cache : false,
								async : false,
								data : params,
								/* data: 'newInstanceId='+newInstanceId+'&newFormId='+newFormId+'&newProcessId='+newProcessId
								+'&newWorkFlowId='+childWorkflowId+'&childNodeId='+childNodeId+'&userId='+userId+'&oldProcessId='+processId
								+'&nextNodeId='+nextNodeId+'&type=1&json='+json+'&nodeId=${nodeId}&doType='+doType+tagValue,
								 */error : function() {  
									if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}
									alert('AJAX调用错误(table_getNextIsMerge.do)');
								},
								success : function(msg) {
									if(msg == 'success'){
										isSuccess ++;
									}
								}
							});
						}
						if(isSuccess > 0){
							alert("发送成功！");
							window.close();
							window.returnValue='refresh';
						}
					}else{
						alert("人员选择不正确,请重新选择!");
						return ;
					}
				}
			});
			}
		});
	}
	
	
	
	
	function end(){
		var nodeNameNew = "";
		if('${childWfAfterNode}' == 'true'){
			nodeNameNew = '发送下一步';
		}else{
			nodeNameNew = '办结';
		}
		if(confirm("确定要"+nodeNameNew+"吗？")){
			var obj = document.getElementById("frame1").contentWindow;
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
			var formId = eval(obj.document.getElementById("formId")).value;
			var nextNodeId = document.getElementById("neNodeId").value ;
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
			json = JSON2.stringify(trueJSON.pdfjson);
			formjson = trueJSON.formjson;
			var o = '${isbt}';
			if(o){
				var error =obj.isCheckBt(o.split(";"));
				if(error!=""){
					alert(error);
					return;
				}
			}
			var tagValue = getProValue();
			var pageValue =tagValue;
			if(formjson != ''){
				//	tagValue = "&"+formjson;
					pageValue = mergeJson(tagValue,formjson);
			}
			if(obj.document.getElementById("jzk1")){
				obj.zzfs();
			}
			
			var params = pageValue;
			// from  url 
			params['instanceId'] = instanceId;
			params['workFlowId'] = workFlowId;
			params['processId'] = '${processId}';
			params['nodeId'] = '${nodeId}';
			params['finstanceId'] = '${finstanceId}';
			params['formId'] = formId; 
			// form data 
			params['json'] = json; 
			params['oldProcessId'] = '${oldProcessId}'; 
			params['isWriteNewValue'] = '${isWriteNewValue}'; 
			$.ajax({  
				url : 'table_end.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}
					alert('AJAX调用错误(table_end.do)');
				},
				data : params,
				//data : 'json='+ json +'&oldProcessId=${oldProcessId}&isWriteNewValue=${isWriteNewValue}'+tagValue,
				success : function(msg) {
					if(msg == 'no'){
						alert("办结有误，请联系管理员！");
						return false;
					}else if(msg == 'yes'){
						if(obj.document.getElementById("jzk1")){
							obj.fsjs();
						}
						if('${childWfAfterNode}' != 'true'){
							alert("完成办结！");
						}else{
							alert("发送成功！");
						}
						if('${isFirst}' == 'true' && '${finstanceId}'==''){
							window.location.href="${ctx}/table_getPendingList.do";
						}else{
							window.close();
							window.returnValue='refresh';
						}
					}
				}
			});
		}
	}

	function invalid(){
		if(confirm("确定要作废吗？")){
			var obj = document.getElementById("frame1").contentWindow;
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var allInstanceId= '${allInstanceId}';	
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
			if(obj.document.getElementById("jzk1")){
				obj.zzfs();
			}
			var params ={};
			params['instanceId'] = instanceId;
			params['workFlowId'] = workFlowId;
			params['allInstanceId'] = allInstanceId;
			$.ajax({  
				url : 'table_invalid.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : params,
				//data :'instanceId='+instanceId+'&workFlowId='+workFlowId+'&allInstanceId='+allInstanceId,
				error : function() {  
					if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}
					alert('AJAX调用错误(table_invalid.do)');
				},
				success : function(msg) {
					if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}
					if(msg == 'no'){
						alert("作废失败，请联系管理员！");
						return false;
					}else if(msg == 'yes'){
						alert("作废成功！");
						window.close();
						window.returnValue='refresh';
					}
				}
			});
		}
	}
	
	//发文中发送-先保存表单再发送
	function send(){
		if(confirm("确定要发送吗？")){
			var obj = document.getElementById("frame1").contentWindow;
			if(obj.document.getElementById("jzk1")){
				obj.zzfs();
			}
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
			var formId = eval(obj.document.getElementById("formId")).value;
			var nextNodeId = document.getElementById("neNodeId").value ;
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
			json = JSON2.stringify(trueJSON.pdfjson);
			formjson = trueJSON.formjson;
			var tagValue = getProValue();
			var pageValue = tagValue;
			if(formjson != ''){
				pageValue = mergeJson(tagValue,formjson);
				//tagValue = "&"+formjson;
			}
			var params = pageValue;
			// from  url 
			params['instanceId'] = instanceId;
			params['workFlowId'] = workFlowId;
			params['processId'] = '${processId}';
			params['nodeId'] = '${nodeId}';
			params['finstanceId'] = '${finstanceId}';
			params['formId'] = formId; 
			// form data 
			params['json'] = json; 
			params['oldProcessId'] = '${oldProcessId}'; 
			params['isWriteNewValue'] = '${isWriteNewValue}';
			
			$.ajax({
				url : 'table_end.do',
				type : 'POST',  
				cache : false,
				async : false,
				data : params,
				//data : 'json='+ json +'&oldProcessId=${oldProcessId}&isWriteNewValue=${isWriteNewValue}'+tagValue,
				error : function() {
					if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}
					alert('AJAX调用错误(table_onlySave.do)');
				},
				success : function(msg) {  
					var params = pageValue;
					// form data 
					params['instanceId'] = instanceId;
					params['workFlowId'] = workFlowId;
					params['processId'] = '${processId}';
					params['nodeId'] = '${nodeId}';
					params['formId'] = formId; 
					if(msg != ''){
						$.ajax({  
							url : 'table_sendDoc.do',
							type : 'POST',   
							cache : false,
							async : false,
							data : params,
							//data : 'instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&formId='+formId+tagValue,
							error : function() {  
								if(obj.document.getElementById("jzk1")){
									obj.fsjs();
								}
								alert('AJAX调用错误(table_sendDoc.do)');
							},
							success : function(msg) {
								if(msg == 'no'){
									if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}
									alert("发送失败，请联系管理员！");
									return false;
								}else if(msg == 'yes'){
									if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}
									alert("发送成功！");
									/*
									$.ajax({  
										url : 'table_end.do?instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&formId='+formId+tagValue,
										type : 'POST',   
										cache : false,
										async : false,
										error : function() {  
											alert('AJAX调用错误(table_end.do)');
										},
										success : function(msg) {
											*/
											if('${isFirst}' == 'true'){
												window.location.href="${ctx}/table_getPendingList.do";
											}else{
												window.close();
												window.returnValue='refresh';
												if('${isPortal}'=='1'){
													window.location.href="${ctx}/table_getPendingList.do";
												}
											}
											/*
										}
									});
									*/
								}
							}
						});
					}else{
						if(obj.document.getElementById("jzk1")){
							obj.fsjs();
						}
						alert("发送失败，请联系管理员！");
						return false;
					}
				}
			});
		}
	}

	//发送下一步
	function sendNext(){
		var closeframe= $("#closeframe").val();
		//意见json-----start--------
		var json = '';
		var formjson = ''; //表单元素
		var isHaveFormjson = ''; 
		//if(('${isFirst}' != 'true' &&  '${isCy}' != 'true' && '${stepIndex}'!='1') || '${cType}'=='1'){
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
			json = JSON2.stringify(trueJSON.pdfjson);
			formjson = trueJSON.formjson;
			isHaveFormjson = 'true';
		//}
		//意见json------end--------
		var isSmsRemind = 0 ;//是否短信提醒
		if(document.getElementById("isSmsRemind").checked){
			isSmsRemind=1;
		}
		var nextNodeId = document.getElementById("neNodeId").value ;
		var neRouteType = document.getElementById("neRouteType").value ;
		var xtoName = "";
		var xccName = "";
		if(neRouteType==0){
			xtoName = document.getElementById("toName").value;
		}else if(neRouteType ==1){
			xtoName = document.getElementById("toName").value;
		}else if (neRouteType == 2){
			//==============判断主送抄送====================
			xtoName = document.getElementById("xtoName").value;
			xccName = document.getElementById("xccName").value;
			//====================================//		
		}
		var tagValue = getProValue();
		var pageValue =tagValue;
		if(formjson != ''){
			//tagValue = "&"+formjson;
			pageValue = mergeJson(tagValue,formjson);
		}
		var yqinstanceid = $("#yqinstanceid").val();
		pageValue['yqinstanceid'] = yqinstanceid;	
		var obj = document.getElementById("frame1").contentWindow;
		//----------表单非空校验方法-----------
		var isPassCheck = 'true';
		if (typeof obj.sendNextOfCheckForm != 'undefined' ) {
		    isPassCheck = obj.sendNextOfCheckForm();
		}
		
		if(isPassCheck){
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var formId = eval(obj.document.getElementById("formId")).value;
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
			var att_comment_id = "";
			var processId = eval(obj.document.getElementById("processId")).value;
			if('${isFirst}' == 'true'){
				var title = '${title_column}';
				var titles = title.split(",");
				var isHaveTitle = "";
				for(var i=0;i < titles.length;i++){
					var filedValue = pageValue[titles[i]];
					if(filedValue !=""){
						isHaveTitle +=	filedValue;
					}
				}
			}else{
				isHaveTitle = "ishave"; //如果不是第一步，就不用判断
			}
			
			//检查办件是否处于暂停中
			$.ajax({
				url : '${ctx}/itemRelation_getIsDelaying.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'instanceId':instanceId
				},
				error : function() {
					alert('AJAX调用错误(itemRelation_getIsDelaying.do)');
				},
				success : function(msg) {
					if(msg=='1'){
						alert("待办处于延期过程中,请等待!");
						return false;
					}
				}
			});
	
			if(isHaveTitle != null && isHaveTitle != ""){
				$.ajax({
					url : '${ctx}/table_modelIsOrNot.do',
					type : 'POST',
					cache : false,
					async : false,
					data : {
						'workFlowId':workFlowId,
						'nodeId':'${nodeId}',
						'nextNodeId':nextNodeId,
						'xtoName':xtoName,
						'xccName':xccName
					},
					error : function() {
						alert('AJAX调用错误(table_modelIsOrNot.do)');
					},
					success : function(msg) {
						if(msg == 'no'){
							alert("所选择的发送人的个数，和路由类型不一致！");
							return false;
						}else if(msg == 'yes'){
							if(obj.document.getElementById("jzk1")){
								obj.zzfs();
							}else{
								addLoadCover();
							}
							var params = pageValue;
							//from  url
							params['isDb'] = '${isDb}';
							params['isHaveFormjson'] = isHaveFormjson;
							params['itemId'] = '${itemId}';
							params['xtoName'] = xtoName;
							params['xccName'] = xccName;
							params['instanceId'] = instanceId;
							params['formId'] = formId;
							params['nodeId'] = '${nodeId}';
							params['nextNodeId'] = nextNodeId;
							params['workFlowId'] = workFlowId;
							params['processId'] = '${processId}';
							params['att_comment_id'] = '${att_comment_id}';
							// from data 
							params['json'] = json;
							params['oldFormId'] = '${oldFormId}';
							params['isSmsRemind'] = isSmsRemind;
							params['isFirst'] = '${isFirst}';
							params['isCy'] = '${isCy}';
							params['firstStep'] = '${firstStep}';
							params['isChildWf'] = '${isChildWf}';
							params['finstanceId'] = '${finstanceId}';
							params['cType'] = '${cType}';
							params['relation'] = '${relation}';
							params['f_proceId'] = '${f_proceId}';
							params['closeframe'] = closeframe;
								setTimeout(function(){
									$.ajax({  
										//url : '${ctx}/table_sendNext.do?isDb=${isDb}&isHaveFormjson='+isHaveFormjson+'&itemId=${itemId}&xtoName='+xtoName+"&xccName="+xccName+"&instanceId="+instanceId+"&formId="+formId+"&nodeId=${nodeId}"+"&nextNodeId="+nextNodeId+"&workFlowId="+workFlowId+"&processId=${processId}&att_comment_id=${att_comment_id}",
										url : '${ctx}/table_sendNext.do',
										type : 'POST',   
										cache : false,
										async : false,
										data : params,
										/* data : 
											"json="+json+"&oldFormId=${oldFormId}&isSmsRemind="+isSmsRemind+"&isFirst=${isFirst}&isCy=${isCy}&firstStep=${firstStep}&isChildWf=${isChildWf}&finstanceId=${finstanceId}&cType=${cType}&relation=${relation}&f_proceId=${f_proceId}&closeframe="+closeframe+tagValue,
										 *///{
											//'json':json,'oldFormId':'${oldFormId}','isSmsRemind':isSmsRemind,'isFirst':'${isFirst}','isCy':'${isCy}','firstStep':'${firstStep}','isChildWf':'${isChildWf}','finstanceId':'${finstanceId}','cType':'${cType}','relation':'${relation}','f_proceId':'${f_proceId}','closeframe':closeframe
										//},
										error : function() {  
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											alert('AJAX调用错误(table_sendNext.do)');
											closeCover();
										},
										success : function(msg) {
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											if(msg == 'no'){
												alert("发送下一步有误，请联系管理员！");
												return false;
											}else if(msg == 'yes'){
												
												if(closeframe=='0'){
													alert("发送成功！");
													if('${isChildWf}' == 'true'){	//子流程
														window.close();
														window.returnValue='choseparent';
													}else{
														var itemid= '${itemId}';
														if(('${isFirst}' == 'true' || '${isCy}' == 'true' || '${stepIndex}'=='1') && '${cType}' != '1'){
															window.location.href="${ctx}/table_getPendingList.do?itemid="+itemid;
														}else{
															window.close();
															window.returnValue='refresh';
															if('${isPortal}'=='1'){
																parent.window.location.href="${ctx}/table_getPendingList.do";
															}
														}
													}
												}else if(closeframe==1){
													goExChangeSendVlaue();
												}
											}
										}
									});	
							},1000);
						}
					}
				});
			}else {  
				alert("流程标题所对应的存储字段不能为空!");  
			}
		}
	}

    //保存当前节点表单
	function operateForm(operate){
		//意见json-----start--------
		var json = '';
		var formjson = ''; //表单元素
		var isHaveFormjson = ''; 
		//修改的到子流程的修改json不保存
		//if('${isFirst}' != 'true' &&  '${isCy}' != 'true' ){
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
			json = JSON2.stringify(trueJSON.pdfjson);
			formjson = trueJSON.formjson;
			isHaveFormjson = 'true';
		//}
		//意见json------end--------	
		var obj = document.getElementById("frame1").contentWindow;
		var tagValue = getProValue();//得到属性值
		var pageValue =tagValue;
		if(formjson != ''){
			// 合并 json 对象
			pageValue = mergeJson(tagValue,formjson);
		}
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var formId = eval(obj.document.getElementById("formId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		var att_comment_id = "";
		if('${isFirst}' == 'true'){
			var title = '${title_column}';
			var titles = title.split(",");
			var isHaveTitle = "";
			for(var i=0;i < titles.length;i++){
						var filedValue = pageValue[titles[i]];
						if(filedValue !=""){
							isHaveTitle +=	filedValue;
						}
			}
		}else{
			isHaveTitle = "ishave"; //如果不是第一步，就不用判断
		}
		var params  = pageValue;
		// from url
		params['instanceId'] = '${instanceId}';
		params['isHaveFormjson'] = isHaveFormjson;
		params['formId'] = formId;
		params['oldFormId'] = '${oldFormId}';
		params['itemId'] = '${itemId}';
		params['nodeId'] = '${nodeId}';
		params['workFlowId'] = workFlowId;
		params['processId'] = '${processId}';
		params['operate'] = operate;
		//from data
		params['isFirst'] = '${isFirst}';
		params['json'] = json;
		params['cType'] = '${cType}';
		
		if(isHaveTitle != null && isHaveTitle !="" && isHaveTitle != 'undefined'){
			$.ajax({
				//url : '${ctx}/table_onlySave.do?instanceId=${instanceId}&isHaveFormjson='+isHaveFormjson+'&formId='+formId+'&oldFormId=${oldFormId}&itemId=${itemId}'+'&nodeId=${nodeId}'+'&workFlowId='+workFlowId+'&processId=${processId}&operate='+operate,
				url : '${ctx}/table_onlySave.do',
				type : 'POST',  
				cache : false,
				async : false,
				data : params,
				//data : "isFirst=${isFirst}&json="+json+"&cType=${cType}"+tagValue,
				error : function() {
					alert('AJAX调用错误(table_onlySave.do)');
				},
				success : function(msg) {   
					if(msg != ''){
						if(operate==1){
							alert("完成办理！");
						}else {
							alert("保存成功！");
						}
						if('${isFirst}' == 'true' || '${isCy}' == 'true'){
							window.location.href="${ctx}/table_getPendingList.do?itemid=${itemId}";
						}else{
							window.close();
							window.returnValue='refresh';
						}
					}else{
						if(operate==1){
							alert("完成办理有误,请联系管理员！");
						}else {
							alert("保存有误,请联系管理员！");
						}
						return false;
					}
				}
			});
		}else {
			alert("流程标题所对应的存储字段不能为空!");
		}
	}
	
	//查询当前节点人员组内除本身以外的人员
	function pushDb(){
		var ret = window.showModalDialog('${ctx}/group_getInnerOtherUsers.do?nodeId=${nodeId}&processId=${processId}&t='+new Date(),null,"dialogWidth:800px;dialogHeight:650px;help:no;status:no");
		if(ret == 'success'){
			alert("推送成功,请办结！");
		}else if(ret == 'fail'){
			alert("推送失败,请联系管理员！");
		}
	}
	
	//查看推送消息
	function lookMessage(){
			window.showModelessDialog('${ctx}/table_getPushMessage.do?processId=${processId}&t='+new Date(),window,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
			document.getElementById("xxlx").className ="fl ml8 xs";
	}
    
    //获取属性和属性值--------！！！！！！！！！-pdf版时，列表类型有待修改-！！！！！！！！！----
	function getProValue(){
		var tagHaveName = '${tagHaveName}';//列表型
		var tagName = '${tagName}';//非列表型
		var specialTagName = '${specialTagName}';//特殊类型的input
		var allValue = '${value}';
		var tagValue= "";
		var tagHaveValue= "";
		
		var tags ={};
		var obj=document.getElementById("frame1").contentWindow;
		if(!!tagHaveName){
			var tagHaveNames= new Array(); //定义一数组
			tagHaveNames = tagHaveName.split(","); //格式：jl_gzsj,jl_gzdd,jl_zw,jl_xz,ry_xm,ry_nl,ry_sr
			for(var j=0;j<=tagHaveNames.length-1;j++){   
				if(obj.document.getElementsByName(tagHaveNames[j])){
					var tagHaves = new Array();
					tagHaves = obj.document.getElementsByName(tagHaveNames[j]);//所有的属性
					tagHaveValue += "&"+ tagHaveNames[j]+"=";  //格式：jl_gzsj=a,b,c&jl_gzdd=d,f,g
					for(var k=0;k<tagHaves.length;k++){
						var ttval = tagHaves[k].value;
						if(ttval=="" || ttval==null){
							ttval = "null";
						}
						if(tagHaves.length > 1){ //多列
							if(tagHaveNames[j] == tagHaves[k]){
								tagHaveValue += ttval+";";
							}else{
								tagHaveValue += ttval+";";
							}
						}else{//单列
							tagHaveValue += ttval+";";  //拼起来，一个列对应多个值
						}
					}
					tagHaveValue = tagHaveValue.substring(0,tagHaveValue.length-1);
				}
			}  
		}
		if(!!tagName){
				var tagNames = new Array(); //定义一数组
				tagNames = tagName.split(",");
				var foval = "";
				if(allValue != ''){
					allValue = allValue.substring(0,allValue.length-1);
				}
				
				// 拆分成对象
				var allValues = allValue.split(";");
				var valObj = {};
				if(allValue != ''){
					for(var i = 0; i < allValues.length; i++){
						if(allValues[i] != 'undefined' && typeof(allValues[i]) != 'undefined'){
							var tempKeyValue = allValues[i].split(":");
							if(tempKeyValue.length > 1){
								if(typeof(valObj[tempKeyValue[0].toLowerCase()]) == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == ''){
									valObj[tempKeyValue[0].toLowerCase()] = tempKeyValue[1];
								}
								
							}else{
								if(typeof(valObj[tempKeyValue[0].toLowerCase()]) == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == ''){
									valObj[tempKeyValue[0].toLowerCase()] = "";
								}
							}
						}
					}
				}
				
				for(var i=0;i <= tagNames.length-1;i++){   
					if(obj.document.getElementById(tagNames[i])){
						foval = eval(obj.document.getElementById(tagNames[i])).value ;
						tagValue += "&"+tagNames[i]+"="+encodeURI(foval);  
						tags[tagNames[i]] =foval;
					}else{
						//pdf版已经没有表单，getElementById读取不到值
						tagValue += "&"+tagNames[i]+"="+encodeURI(valObj[tagNames[i].toLowerCase()]);
						tags[tagNames[i]] = valObj[tagNames[i].toLowerCase()];
						}
				}  
			}
			if(!!specialTagName){
				var specialTagNames = new Array(); //定义一数组
				specialTagNames = specialTagName.split("##");
				for(var m=0;m <= specialTagNames.length-1;m++){
					var arr = obj.document.getElementsByName(specialTagNames[m]);
					var val = "";
					if(!!arr){
						for(var k=0;k < arr.length;k++){
							if(arr[k].checked){
								val += arr[k].value+"^";//用不常用的符号("^")连接checkbox的值,如果有冲突，需修改
							}
						}
					}
					if(val != '' && val.length > 0){
						tagValue += "&"+specialTagNames[m]+"="+encodeURI(val.substring(0,val.length-1));
						tags[specialTagNames[m]] = val.substring(0,val.length-1);
					}
				}  
			}
		//}
    /* alert(tags);
    alert(tagValue + tagHaveValue); */
		return tags;
	}
    
	//打印
	function PrintNoHdr(nodeId, workFlowId){    
        //t = new ActiveXObject("WScript.Shell");    
        //t.RegWrite("HKCU\\Software\\Microsoft\\InternetEXPlorer\\PageSetup\\header","");    
        //t.RegWrite("HKCU\\Software\\Microsoft\\InternetExplorer\\PageSetup\\footer","");    
        //window.print();
        var operate = 0;
		var obj = document.getElementById("frame1").contentWindow;
		var tagValue = getProValue();//得到属性值
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var formId = eval(obj.document.getElementById("formId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
	//	var title = eval(obj.document.getElementById('${title_column}')).value;
		var att_comment_id = "";
		var pageValue =tagValue;
		if('${isFirst}' == 'true'){
			var title = '${title_column}';
			var titles = title.split(",");
			var isHaveTitle = "";
			for(var i=0;i < titles.length;i++){
				var filedValue = pageValue[titles[i]];
				if(filedValue !=""){
					isHaveTitle +=	filedValue;
				}
			}
		}else{
			isHaveTitle = "ishave"; //如果不是第一步，就不用判断
		}
		if(isHaveTitle != null && isHaveTitle != ""){
			var params = pageValue;
			// from  data
			params['instanceId'] = instanceId;
			params['formId'] = formId;
			params['oldFormId'] = '${oldFormId}';
			params['itemId'] = '${itemId}';
			params['nodeId'] = '${nodeId}';
			params['workFlowId'] = workFlowId;
			params['processId'] = '${processId}';
			params['operate'] = operate;
			$.ajax({
				url : '${ctx}/table_onlySaveOfPrint.do',
				type : 'POST',  
				cache : false,
				async : false,
				data:  params,
				//data:  'instanceId='+instanceId+'&formId='+formId+'&oldFormId=${oldFormId}&itemId=${itemId}&nodeId=${nodeId}&workFlowId='+workFlowId+'&processId=${processId}&operate='+operate+tagValue,
				error : function() {
					alert('AJAX调用错误(table_onlySave.do)');
					return false;
				},
				success : function(msg) {   
					if(msg != ''){
						var a=window.open("${ctx}/template_toDyJsp.do?nodeId="+nodeId+"&instanceId="+instanceId,'newwindow','height=600, width=1000,top='+(screen.height-600)/2+',left='+(screen.width-1000)/2+',toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no,status=no');
					}else{
						if(operate==1){
							alert("完成办理有误,请联系管理员！");
						}else {
							alert("取消有误,请联系管理员！");
						}
						return false;
					}
				}
			});
		}else {
			alert("流程标题所对应的存储字段不能为空!");
		}
	    
		
	}   
	function PrintFrame(){
		if(window.frames['frame1'] == null){
			document.getElementById('frame1').focus();
			document.getElementById('frame1').contentWindow.print();
		}else{
			document.frames('frame1').window.focus();
			window.print();
		}
	}

	//重定向
	function redirect(){
		art.dialog({
			title: '重定向节点',
			lock: true,
		    content: '<'+'iframe id="newFrame" name="newFrame" src="${ctx}/table_getAllnodes.do?workFlowId=${workFlowId}" height="315" width="450"></'+'iframe>',
		    id: 'EF893K',
		    ok: function(){
		    	var obj = document.getElementById("newFrame").contentWindow;
				var radios = obj.document.getElementsByName("nodeName");
				var redirectNodeId = "";  
				for(var i=0;i<radios.length;i++){
					if(radios[i].checked){
						redirectNodeId = radios[i].value;
					}
				}
				if(redirectNodeId=="" || typeof(redirectNodeId)=="undefined"){
					alert("请选择重定向的节点！");
					return false;
				}else {
					routeType(redirectNodeId);
				}
		    }
		});
	}

	function assist(type){
		var height = 305;
		if(type=='xs'){
			height = 355;
		}
		art.dialog({
			title: '辅助功能',
			drag:true,
			lock: false,
			resize:true,
		    content: '<'+'iframe id="assistFrame" name="assistFrame" src="${ctx}/table_toAssist.do?type='+type+'" height="'+height+'" width="580"></'+'iframe>',
		    id: 'EF893K',
		    ok: function(){
		    	var assistFrame = document.getElementById("assistFrame").contentWindow;
		    	if(!assistFrame.document.getElementById("treeframe")){
					if(confirm("未做任何操作，关闭窗口?")){
						return true;
					}else{
						return false; 
					}
					
		    	}
		    	var treeFrame = assistFrame.document.getElementById("treeframe").contentWindow;
				var obj=treeFrame.document.getElementById('oldSelect');
				var message_obj = assistFrame.document.getElementById('assistMsg');
				var assistType = assistFrame.document.getElementById('assistType').value;
				var msg = "";
				if(message_obj){
					msg = message_obj.value;
					if(msg==null || msg==''){
						alert('请填写消息');
						return false;
					}
				}
				var str='';
				if(obj){
					for(var i=0;i<obj.options.length;i++){
						str+=obj.options[i].value+'#';
					};
					if(str!='')str=str.substr(0,str.length-1);
					//alert(str);
					//document.getElementById('employeeinfo').value=str;
					
				}
				if(str==''){
					alert('请选择人员');
					return false;
				}
				if(!confirm("确定发送?")){
					return false;
				}
				$.ajax({   
					url : '${ctx }/table_saveAssistUser.do?processId=${processId}',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_saveAssistUser.do)');
					},
					data : {
						'type':assistType,
						'employeeinfo':str,
						'msg':msg
					},    
					success : function(msg) {  
						alert('发送成功');
						if('${isFirst}' == 'true' || '${isCy}' == 'true'){
							window.location.href="${ctx}/table_getPendingList.do";
						}else{
							window.close();
							window.returnValue='refresh';
						}
					}
				});
		    }
		});
	}

	//设置传阅名单
	function setCyName(){
		var ret = window.showModalDialog('${ctx}/table_cyPersonNameList.do?instanceId=${instanceId}&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
		if(ret){
			var cyPersonName = ret.split(";")[0];
			var cyOfficeName = ret.split(";")[1];
			if(cyPersonName.length > 0){
				$.ajax({   
					url : '${ctx }/table_addPersonName.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_addPersonName.do)');
					},
					data : {
						'itemId':'${itemId}',
						'instanceId':'${instanceId}',
						'cyPersonName':cyPersonName
					},    
					success : function(personNames) {  
						$.ajax({   
							url : '${ctx }/table_addOfficeName.do',
							type : 'POST',   
							cache : false,
							async : false,
							error : function() {  
								alert('AJAX调用错误(table_addOfficeName.do)');
							},
							data : {
								'itemId':'${itemId}',
								'instanceId':'${instanceId}',
								'cyOfficeName':cyOfficeName
							},    
							success : function(officeNames) {  
									alert('设置成功!');
								//============显示出设置的传阅名单=======start============
								var persons = new Array();
								var offices = new Array();
								if(personNames != ""){
									persons = personNames.split(",");
								}
								if(officeNames != ""){
								    offices = officeNames.split(",");
								}
								var names = "";
								//判断哪一个个数大,用哪个循环
								if(persons.length < offices.length){
									names = offices;
								}else{
									names = persons;
								}
								//如果重新设置,删除已经生成的行
								//alert($(window.frames['frame1'].document).find("#cytable tr").length);---判断有多少行
								$(window.frames['frame1'].document).find("#cytable tr").each(function(){
									$(this).remove("#add");
								});
								//$(window.frames['frame1'].document).find("#cytable tr:not(:first)").empty();
								//如果循环超出,用空格代替
								for(var i = 0;i < names.length; i++){
									var person = "";
									var office = "";
									if(i < persons.length){
										person = persons[i];
									}else{
										person = "&nbsp;";
									}
									if(i < offices.length){
										office = offices[i];
									}else{
										office = "&nbsp;";
									}
									$(window.frames['frame1'].document).find("#cytable").append("<tr id=\"add\"><td style=\"text-align:center\">"+person+"</td><td id=\""+person+"${instanceId}\">&nbsp;</td><td style=\"text-align:center\">"+office+"</td><td  id=\""+office+"${instanceId}\">&nbsp;</td></tr>");
								}
								var obj = document.getElementById("frame1").contentWindow;
								obj.document.getElementById("cy").style.display="block";
								//============显示出设置的传阅名单========end===========
							}
						});
					}
				});
			}
		}
	}
	
   	//设置iframe高度  
        var winW=$(window).width(),winH=$(window).height();
        $('iframe.iframec').height(winH-75);
        window.onresize=function(){
       	var winH=$(window).height()-75;
       	$('iframe.iframec').height(winH);
   	 };

   //判断有没有设置表单对应关系
   	 function init(){
		/* if('${value}' == '' && '${tagName}' == ''){
			alert("表单对应关系设置有误，请重新设置！");
			window.location.href="${ctx}/guidPage.jsp";
		} */
		if('${isCy}' == 'true'){
			$.ajax({   
				url : '${ctx }/table_getCyName.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_addOfficeName.do)');
				},
				data : {
					'instanceId':'${instanceId}'
				},    
				success : function(cyNames) {  
					var personNames = cyNames.split(";")[0];
					var officeNames = cyNames.split(";")[1];
					//============显示出设置的传阅名单=======start============
					var persons = new Array();
					var offices = new Array();
					if(personNames != ""){
						persons = personNames.split(",");
					}
					if(officeNames != ""){
					    offices = officeNames.split(",");
					}
					var names = "";
					//判断哪一个个数大,用哪个循环
					if(persons.length < offices.length){
						names = offices;
					}else{
						names = persons;
					}
					//如果重新设置,删除已经生成的行
					//alert($(window.frames['frame1'].document).find("#cytable tr").length);---判断有多少行
					$(window.frames['frame1'].document).find("#cytable tr").each(function(){
						$(this).remove("#add");
					});
					//$(window.frames['frame1'].document).find("#cytable tr:not(:first)").empty();
					//如果循环超出,用空格代替
					for(var i = 0;i < names.length; i++){
						var person = "";
						var office = "";
						if(i < persons.length){
							person = persons[i];
						}else{
							person = "&nbsp;";
						}
						if(i < offices.length){
							office = offices[i];
						}else{
							office = "&nbsp;";
						}
						$(window.frames['frame1'].document).find("#cytable").append("<tr id=\"add\"><td style=\"text-align:center\">"+person+"</td><td id=\""+person+"${instanceId}\" style=\"text-align:center\">&nbsp;</td><td style=\"text-align:center\">"+office+"</td><td  id=\""+office+"${instanceId}\" style=\"text-align:center\">&nbsp;</td></tr>");
					}
					var obj = document.getElementById("frame1").contentWindow;
					obj.document.getElementById("cy").style.display="block";
					//============显示出设置的传阅名单========end===========
				}
			});
		}
   	 }

   //打印份数入库
		function daoruPrintNum(){
			//获取CEBID
			var strCebID = document.getElementById("frame1").contentWindow.document.getElementById("cebid").value;
			if(strCebID != ''){//份数入库和保持CEBID到box库
				var keyAble = GetCAKeyInfnFun();
				if(keyAble!=true){  
					alert("读取CA证书失败，请检查CA是否正确插入");
					return;
				}
				
				// 份数、接收单位、接收单位个数入库
				var r="";
				if(document.getElementById("xcc").value!=""){
					r = document.getElementById("xto").value+";"+document.getElementById("xcc").value;
				}else{
					r = document.getElementById("xto").value
				}
				var arr = r.split(";");
		        var receiver = "";//收文单位字符串.接收单位名称，多个单位间用;分开
				var printNum = "";//打印份数字符串.接收单位对应份数，多个份数间用;分开
		        var counts = arr.length;//接收单位个数
				for(var i=0;i<arr.length;i++){
				  var arrary = arr[i].split("|");
				  printNum += arrary[2] + ";";
				  receiver += arrary[3] + ";"; 
				} 
				receiver=receiver.substring(0,receiver.length-1);
				printNum=printNum.substring(0,printNum.length-1);
				var StampServer_PrintLic2DB = "<%=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintLic2DB")%>";
				var ss = fenshuruku(strCebID,receiver,printNum,counts,StampServer_PrintLic2DB);
				if(ss == false){
					alert("份数入库失败");
					return;
				}
			}
		}
		
</script>
<script>
$("div.tabs", document).each(function(){
	var $this = $(this);
	var options = {};
	options.currentIndex = $this.attr("currentIndex") || 0;
	options.eventType = $this.attr("eventType") || "click";
	$this.tabs(options);
});
</script>
<script>
$(document).ready(function(){
	var bH=$(window).height();
	var bW=$(window).width();  
	$('.content').height(bH-30); 
	$('.frame1').height($('.content').height()-50);  
	$('.frame1').width(bW);
	//$('.frame2').width(bW*0.3+28); 
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	var bW=$(window).width();  
	$('.content').height(bH-30);  
	$('.frame1').height($('.content').height()-50);
	$('.frame1').width(bW); 
	//$('.frame2').width(bW*0.3+28);
})
</script>
<script>
$('.bdxlid').mouseenter(function(){
	$(this).find('.bdxlcon').slideDown(100);
});
$('.bdxlid').mouseleave(function(){
	$(this).find('.bdxlcon').slideUp(100);
});
function sqdj(instanceId){
	window.showModalDialog('${ctx}/table_sqdj.do?instanceId='+instanceId,null,"dialogTop=200;dialogLeft=400;dialogWidth=600px;dialogHeight=410px;help:no;status:no");
}

function jbd(instanceId){
	window.showModalDialog('${ctx}/table_jbd.do?instanceId='+instanceId,null,"dialogTop=200;dialogLeft=400;dialogWidth=600px;dialogHeight=500px;help:no;status:no");
}
</script>
<!-- js等待加载动画 start -->
<script type="text/javascript">
function addLoadCover() {
	$("#body").append("<div id='fullbg'><div style='text-align:center;margin-top: 250px;'><img src='${ctx}/dwz/style/images/quanq.gif'>正在发送中，请稍候...</div></div> ");
	showBg('dialog', 'dialog');
}

function showBg(ct, content) {
	var bH = $("#body").height();
	var bW = $("#body").width();
	var objWH = getObjWh(ct);
	$("#fullbg").css({
		width : bW,
		height : bH,
		display : "block"
	});
	var tbT = objWH.split("|")[0] + "px";
	var tbL = objWH.split("|")[1] + "px";
	$("#"+ct).css({
		top : "350px",
		left : tbL,
		display : "block"
	});
	$(window).scroll(function() {
		resetBg();
	});
	$(window).resize(function() {
		resetBg();
	});
}
function getObjWh(obj) {
	var st = document.documentElement.scrollTop;// 滚动条距顶部的距离
	var sl = document.documentElement.scrollLeft;// 滚动条距左边的距离
	var ch = document.documentElement.clientHeight;// 屏幕的高度
	var cw = document.documentElement.clientWidth;// 屏幕的宽度
	var objH = $("#" + obj).height();// 浮动对象的高度
	var objW = $("#" + obj).width();// 浮动对象的宽度
	var objT = Number(st) + (Number(ch) - Number(objH)) / 2;
	var objL = Number(sl) + (Number(cw) - Number(objW)) / 2;
	return ch + "|" + cw;
}
function resetBg() {
	var fullbg = $("#fullbg").css("display");
	if (fullbg == "block") {
		var bH2 = $("body").height();
		var bW2 = $("body").width() + 16;
		$("#fullbg").css({
			width : bW2,
			height : bH2
		});
		var objV = getObjWh("dialog");
		var tbT = objV.split("|")[0] + "px";
		var tbL = objV.split("|")[1] + "px";
		$("#dialog").css({
			top : tbT,
			left : tbL
		});
	}
}
var yys=1;
function changePage(value){
	if(value == 1){
		document.getElementById("leftPage").className="b_disable";
		document.getElementById("rightPage").className="";
	}else if(value == '${imageCount}'){
		document.getElementById("leftPage").className="";
		document.getElementById("rightPage").className="b_disable";
	}else{
		document.getElementById("leftPage").className="";
		document.getElementById("rightPage").className="";
	}
	document.getElementById("frame1").contentWindow.ymChange(value);
	document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
	document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+value)).className="hot";
	yys=value;
}

function changePageToLeft(){
	if(yys>1){
		var val = yys - 1 ;
		document.getElementById("frame1").contentWindow.ymChange(val);
		document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
		if(val == 1){
			document.getElementById("leftPage").className="b_disable";
		}
		if(val < '${imageCount}'){
			document.getElementById("rightPage").className="";
		}
		yys=val;
	}
}

function changePageToRight(){
	if(yys<'${imageCount}'){
		var val = yys + 1 ;
		document.getElementById("frame1").contentWindow.ymChange(val);
		document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
		if(val > 1){
			document.getElementById("leftPage").className="";
		}
		if('${imageCount}' == val){
			document.getElementById("rightPage").className="b_disable";
		}
		yys=val;
	}
}

function mergeJson(a,b){
	// a tagvalue
	// b formjson
	// 将a 的内容 合并到 a 里面	
	for(var c in a){
		if(b[c] == ''){
			b[c] = a[c];
		}
		
	}
	return b;
}
//关闭灰色JS遮罩层和操作窗口
function closeCover() {
	$("#fullbg").css("display", "none");
	$("#dialog").css("display", "none");
}

//添加收藏
function addCollection(){
	$.ajax({  
		url : 'table_favourite.do?instanceId=${instanceId}&workFlowId=${workFlowId}',
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {  
			alert('AJAX调用错误(table_favourite.do)');
		},
		success : function(msg) {
			if(msg == '1'){
				alert("已收藏！");
				return false;
			}else if(msg == '2'){
				alert("收藏成功！");
			}else if(msg == '3'){
				alert("收藏失败");
			}
		}
	});
}
</script>
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
</html>
