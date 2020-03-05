<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/headerindex.jsp"%>
</head>
<body onload="init();">
<div style="padding:5px;">
<div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>跟踪</span></a></li>  
					<li><a href="javascript:;"><span>表单</span></a></li>  
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
			<div>
			<iframe id="frame0" class="frame0" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getProcess.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
			</div>
			<div>
			<!-- <div class="panelBar">
				<ul class="toolBar">
					<li><a class="sav" href="#" onclick="invalid()"><span>作废</span></a></li>  
				</ul>
			</div> -->
			<div style="overflow:hidden;">  
				<c:if test="${isFirst == true || isCy == true || stepIndex=='1'}">
					<iframe id="frame1" class="frame1" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:auto;height:auto;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_frameForm.do?formLocation=${formLocation}&workFlowId=${workFlowId}&formId=${formId}&processId=${processId}&instanceId=${instanceId}&att_comment_id=${att_comment_id}&value=${value}&nodeId=${nodeId}&isFirst=${isFirst}&deptId=${webId}&userId=${userId}"></iframe>
				</c:if>
			<!-- 版式文件 -start-->
				<c:if test="${isFirst != true && isCy != true && stepIndex!='1'}">
					<iframe id="frame1" class="frame1" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:auto;height:auto;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&workFlowId=${workFlowId}&formId=${formId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&userId=${userId}&userName=${userName}&webId=${webId}&limitValue=${limitValue}&isOver=${isOver}"></iframe>
				</c:if>
			<!-- 版式文件 -end-->
			</div>
			</div>
			<input type="hidden" id="neNodeId" name="neNodeId" value="">
			<input type="hidden" id="neRouteType" name="neRouteType" value="">
		</div>
	</div>
	
</div>
</div>
<input type="hidden" id="toName" name="toName">
<input type="hidden" id="xtoName" name="xtoName">
<input type="hidden" id="xccName" name="xccName">
<textarea style="display:none;" id="commentJson" name="commentJson">${commentJson}</textarea>
<textarea style="display:none;" id="pdfPath" name="pdfPath">${pdfPath}</textarea>
</body>
<script>
	function routeType(nextNodeId){
		var obj = document.getElementById("frame1").contentWindow;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		$.ajax({   
			url : 'table_getWfLineOfType.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_getWfLineOfType.do)');
			},
			data : {
				'workFlowId':workFlowId,
				'nodeId':'${nodeId}',
				'nextNodeId':nextNodeId
			},    
			success : function(msg) {  
				//给人员选择iframe传值
				document.getElementById("frame3").src = '${ctx}/table_userGroup.do?nodeId='+nextNodeId+'&send=${send}&routType='+msg;
				//给发送下一步传所点击的按钮的属性值--下一步的节点值
				document.getElementById("neNodeId").value = nextNodeId;
				//所选择的路由类型
				document.getElementById("neRouteType").value = msg;
				
			}
		});
	}

	function end(){
		var obj = document.getElementById("frame1").contentWindow;
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		$.ajax({  
			url : 'table_end.do',
			type : 'POST',   
			cache : false,
			async : false,
			data : {
				'instanceId':instanceId,
				'workFlowId':workFlowId
			},
			error : function() {  
				alert('AJAX调用错误(table_over.do)');
			},
			success : function(msg) {
				if(msg == 'no'){
					alert("办结有误，请联系管理员！");
					return false;
				}else if(msg == 'yes'){
					alert("完成办结！");
					window.location.href="${ctx}/table_getPendingList.do";
				}
			}
		});
	}

	function invalid(){
		if(confirm("确定要作废吗？")){
			var obj = document.getElementById("frame1").contentWindow;
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
			var formId = eval(obj.document.getElementById("formId")).value;
			var tagValue = getProValue();
			var params = tagValue;
			params['instanceId'] = instanceId;
			params['workFlowId'] = workFlowId;
			params['processId'] = '${processId}';
			params['nodeId'] = '${nodeId}';
			params['formId'] = formId;
			$.ajax({  
				//url : 'table_invalid.do?instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&formId='+formId+tagValue,
				url : 'table_invalid.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : params,
				error : function() {  
					alert('AJAX调用错误(table_invalid.do)');
				},
				success : function(msg) {
					if(msg == 'no'){
						alert("作废失败，请联系管理员！");
						return false;
					}else if(msg == 'yes'){
						alert("作废成功！");
						window.location.href="${ctx}/table_getPendingList.do";
					}
				}
			});
		}
	}
	
	function sendNext(){
		var nextNodeId = document.getElementById("neNodeId").value ;
		var neRouteType = document.getElementById("neRouteType").value ;
		var objFrame3 = document.getElementById("frame3").contentWindow;
		var xtoName = "";
		var xccName = "";
		if(neRouteType==0 || neRouteType ==1){
			var toName = objFrame3.document.getElementsByName("toName");
			if(toName == ""){
				alert("请选择发送人！");
			}else {
				for (var i = 0; i < toName.length; i++) {
					xtoName+=toName[i].value+",";
				}
				if(xccName.length>0){
					xtoName=xtoName.substring(0,xtoName.length-1);
				}
			}   
		}else if(neRouteType==0 || neRouteType ==1){
			var toName = objFrame3.document.getElementsByName("toName");
			if(toName == ""){
				alert("请选择发送人！");
			}else {
				for (var i = 0; i < toName.length; i++) {
					xccName+=toName[i].value+",";
				}
				if(xccName.length>0){
					xccName=xccName.substring(0,xccName.length-1);
				}
			}
		}else if (neRouteType == 2){
			//==============判断主送抄送====================
			var arrayXto = objFrame3.document.getElementsByName("xtoName");
			var arrayXcc = objFrame3.document.getElementsByName("xccName");
			var x = 0;
			if(arrayXto != null && arrayXto !=""){
				for (var i = 0; i < arrayXto.length; i++) {
		            if(arrayXto[i].checked==true){
		                x++;
		                if(arrayXto[i].value!=''){
		                    if(x>1){  
					        	alert("主送只能有一个,请重新选择！");
					        	return false;
		                    }
							xtoName = arrayXto[i].value;
		                }
		         	}
				}
			}else{
				alert("请选择主送人！");
			}
			
			if(arrayXto != null && arrayXto !=""){
				for (var i = 0; i < arrayXcc.length; i++) {
		            if(arrayXcc[i].checked==true){
		                if(arrayXcc[i].value!=''){
		                    if(xtoName == arrayXcc[i].value){
		                        alert("主送和抄送不能是同一个,请重新选择！");
		                        return false;
		                    }
			            	xccName+=arrayXcc[i].value+",";
		                }
		            }
				}
				if(xccName.length>0){
					xccName=xccName.substring(0,xccName.length-1);
				}
			}
			//====================================//		
		}
		var tagValue = getProValue();
		var pageValue =tagValue;
		if(formjson != ''){
			pageValue = mergeJson(tagValue,formjson);
		}
		var obj = document.getElementById("frame1").contentWindow;
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var formId = eval(obj.document.getElementById("formId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		var att_comment_id = eval(obj.document.getElementById("att_comment_id")).value;
		var processId = eval(obj.document.getElementById("processId")).value;
		var vc_title = '';
		if(obj.document.getElementById("VC_TITLE")){
			vc_title = pageValue(obj.document.getElementById("VC_TITLE"));
		}else{
			vc_title='null';
		}

		$.ajax({
			url : 'table_modelIsOrNot.do',
			type : 'POST',
			cache : false,
			async : false,
			data : {
				'workFlowId':workFlowId,
				'nodeId':'${nodeId}',
				'nextNodeId':nextNodeId
			},
			error : function() {
				alert('AJAX调用错误(table_modelIsOrNot.do)');
			},
			success : function(msg) {
				if(msg == 'no'){
					alert("节点所绑定的人员组内的人员不止一个，和路由类型不一致！");
					return false;
				}else if(msg == 'yes'){
					var params = pageValue;
					//from  url
					params['isDb'] = '${isDb}';
					params['itemId'] = '${itemId}';
					
					params['xtoName'] = xtoName;
					params['xccName'] = xccName;
					params['title'] = vc_title;
					params['instanceId'] = instanceId;
					params['formId'] = formId;
					params['nodeId'] = '${nodeId}';
					params['nextNodeId'] = nextNodeId;
					params['workFlowId'] = workFlowId;
					params['processId'] = '${processId}';
					params['att_comment_id'] = '${att_comment_id}';
					/* // from data 
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
					params['closeframe'] = closeframe; */
					$.ajax({  
						url : 'table_sendNext.do',
						//url : 'table_sendNext.do?isDb=${isDb}&xtoName='+xtoName+"&xccName="+xccName+"&title="+vc_title+"&instanceId="+instanceId+"&formId="+formId+"&nodeId=${nodeId}"+"&nextNodeId="+nextNodeId+"&workFlowId="+workFlowId+"&processId=${processId}&att_comment_id=${att_comment_id}"+tagValue,
						type : 'POST',   
						cache : false,
						async : false,
						data : params,
						error : function() {  
							alert('AJAX调用错误(table_sendNext.do)');
						},
						success : function(msg) {
							if(msg == 'no'){
								alert("发送下一步有误，请联系管理员！");
								return false;
							}else if(msg == 'yes'){
								window.location.href="${ctx}/table_getPendingList.do";
							}
						}
					});
					//window.location.href="${ctx}/table_sendNext.do?xtoName="+xtoName+"&xccName="+xccName+"&title="+vc_title+"&instanceId="+instanceId+"&formId="+formId+"&nodeId=${nodeId}"+"&nextNodeId="+nextNodeId+"&workFlowId="+workFlowId+"&processId=${processId}&att_comment_id=${att_comment_id}"+tagValue;
				}
			}
		});
		
	}

    //保存当前节点表单
	function operateForm(operate){
		var obj = document.getElementById("frame1").contentWindow;
		var tagValue = getProValue();//得到属性值
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var formId = eval(obj.document.getElementById("formId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		var vc_title = '';
		if(obj.document.getElementById("VC_TITLE")){
			vc_title = tagValue(obj.document.getElementById("VC_TITLE"));
		}else{
			vc_title='null';
		}
		
		var att_comment_id = eval(obj.document.getElementById("att_comment_id")).value;

		$.ajax({
			url : 'table_onlySave.do',
			type : 'POST',  
			cache : false,
			async : false,
			data : {
				'formId':formId,
				'workFlowId':workFlowId,
				'instanceId':instanceId,
				'processId':'${processId}',
				'title':vc_title,
				'nodeId':'${nodeId}',
				'operate':operate
			},
			error : function() {
				alert('AJAX调用错误(table_onlySave.do)');
			},
			success : function(msg) {   
				if(msg != ''){
					if(operate==1){
						alert("完成办理！");
					}else {
						alert("取消成功！");
					}
					window.location.href="${ctx}/table_getPendingList.do"; 
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
	}

    
    //获取属性和属性值
	function getProValue(){
		var tagName = '${tagName}';
		var tagValue= "";
		var tags ={};
		var obj=document.getElementById("frame1").contentWindow;
		if(!!tagName){
			var tagNames= new Array(); //定义一数组
			tagNames = tagName.split(",");
			for(var i=0;i<=tagNames.length-1;i++){   
				if(obj.document.getElementById(tagNames[i])){
					tagValue += "&"+tagNames[i]+"="+eval(obj.document.getElementById(tagNames[i])).value;  
					tags[tagNames[i]] = eval(obj.document.getElementById(tagNames[i])).value;
				}
			}  
		}
		//return tagValue;
		return tags;
	}

	function init(){
		var tagName = '${tagNameForm}';
		var luruTagIds = '${luruTagIds}';
		var instanceId = '${instanceId}';
		var obj=document.getElementById("frame1").contentWindow;
		if(!!tagName){
			var tagNames= new Array(); //定义一数组
			tagNames = tagName.split(",");
			for(var i=0;i<=tagNames.length-1;i++){   
				if(obj.document.getElementById(tagNames[i])){
					obj.document.getElementById(tagNames[i]).disabled=true;
				}
			}
		}
		if(luruTagIds!=null && luruTagIds!=''){
			var tagIds= new Array(); //定义一数组
			tagIds = luruTagIds.split(",");
			for(var i=0;i<tagIds.length;i++){   
				if(obj.document.getElementById(tagIds[i]+'luru')){
					obj.document.getElementById(tagIds[i]+'luru').style.display='none';
				}
				
			}
			//obj.document.getElementById('2222A0DC-2F86-4489-835A-71379135771Fcsyjluru').style.display='none';
			
		}
		var array = obj.document.getElementsByName('yjluru');
		if(array.length !=0 && array != null && array != ""){
			for (var j = 0; j < array.length; j++) {
				array[j].style.display='none';
			}
		}
		var deleteAbleds = obj.document.getElementsByName('doubleClick');
		if(deleteAbleds.length !=0 && deleteAbleds != null && deleteAbleds != ""){
			for (var k = 0; k < deleteAbleds.length; k++) {
				deleteAbleds[k].ondblclick=function(){};
			}
		}
		if(obj.document.getElementById('${instanceId}att')){
			obj.document.getElementById('${instanceId}att').style.display='none';
		}
		var delbtns = obj.document.getElementsByName('attachmentDel');
		for(var i=0;i<delbtns.length;i++){
			delbtns[i].style.display='none';
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
	//打印
	function PrintNoHdr(){    
	      t = new ActiveXObject("WScript.Shell");    
	      t.RegWrite("HKCU\\Software\\Microsoft\\InternetEXPlorer\\PageSetup\\header","");    
	      t.RegWrite("HKCU\\Software\\Microsoft\\InternetExplorer\\PageSetup\\footer","");    
	      window.print();    
	}   
		
   	//设置iframe高度  
        var winW=$(window).width(),winH=$(window).height();
        $('iframe.iframec').height(winH-75);
        window.onresize=function(){
       	var winH=$(window).height()-75;
       	$('iframe.iframec').height(winH);
   	 };
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
	var bW=$(window).width()-15;  
	$('.tabsContent').height(bH-42); 
	$('iframe').height($('.tabsContent').height()-27);  
	$('.frame1').width(bW);
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	var bW=$(window).width()-15;  
	$('.tabsContent').height(bH-42);  
	$('iframe').height($('.tabsContent').height()-27);  
	$('.frame1').width(bW); 
})
</script>
</html>