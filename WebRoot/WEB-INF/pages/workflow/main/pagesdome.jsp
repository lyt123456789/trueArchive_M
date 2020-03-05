<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/headerindex.jsp"%>
    <style>
    .bdxlcon{ display:none;position:absolute;overflow:hidden;width:80px;top:22px;left:0px;border:1px #cccccc solid;z-index:1000;background:#E5EDEF;}
    .bdxlcon a{line-height:25px;display:block;overflow:hidden;width:68px;padding:0px !important;padding-left:10px !important;height:25px;text-align:left;margin:1px;}
    .bdxlcon a:hover{color:#760a0a;background:#cccccc;} 
    </style>
</head>
<body onload="init();initRedirect();">
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
			<div class="panelBar" style="height:27px !important;">
				<ul class="toolBar">
					<li><a class="close" href="javascript:parent.navTab.closeCurrentTab();"><span>关闭窗口</span></a></li>
					<li><a class="sav" href="javascript:operateForm(0);"><span>保存流程</span></a></li>
					<c:if test="${send !='' && send !=0 && isEndProcess == false}">
						<c:forEach items="${nodes}" var="node">  
							<c:if test="${node.wfn_id != endNodeId}"> 
								<li><a class="adv" href="#" onclick="routeType('${node.wfn_id}')"><span>${node.wfn_name}</span></a></li>
							</c:if>
		    			</c:forEach>
	    			</c:if>
					<li><a class="word" href="javascript:javascript:PrintNoHdr('${nodeId}','${workFlowId}');"><span>套打表单</span></a></li>
					<li><a class="print" href="javascript:javascript:PrintFrame();"><span>打印表单</span></a></li>
					   
					<c:if test="${isEnd != 'is'}">  
						<li><a class="sav" href="#" onclick="end()"><span>办结</span></a></li>  
					</c:if>
					<li class="bdxlid" style="position:relative;">
					 <div class="bdxlcon" id="redirMenu">
<!--					  <a class="back" href="javascript:;">受理</a>-->
<!--					  <a class="back" href="javascript:;">初审</a>-->
<!--					  <a class="back" href="javascript:;">再次初审</a>-->
<!--					  <a class="back" href="javascript:;">复审</a>-->
<!--					  <a class="back" href="javascript:;">办结</a>-->
					 </div>
					<a class="redirect" id="redir" href="#" ><span>重定向</span></a></li>  
					<c:if test="${firstStep != 'true'}">  
					  <li class="bdxlid" style="position:relative;">
					  <div class="bdxlcon" >
					  <a class="adv"  href="javascript:assist('xs');">协 商</a>
					  <a class="adv"  href="javascript:assist('cs');">抄 送</a>
					  <a class="adv"  href="javascript:assist('wt');">委 托</a>
					 </div>
						<a class="folder" id="assistLink" href="#" ><span>辅助功能</span></a></li>  
					</c:if>
					<c:if test="${send==0 && isEndProcess == false}">
						<li style="float:right !important;margin-right:180px;"><a class="icon" href="javascript:operateForm(1)"><span>完成</span></a></li>
					</c:if>
										<li class="line">line</li>
					
				</ul>
			</div>
			<div style="overflow:hidden;">
			<iframe id="frame1" class="frame1" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:auto;height:auto;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_frameForm.do?formLocation=${formLocation}&workFlowId=${workFlowId}&formId=${formId}&processId=${processId}&instanceId=${instanceId}&att_comment_id=${att_comment_id}&value=${value}&nodeId=${nodeId}"></iframe>
			<iframe id="frame2" class="frame2" frameborder="no" marginheight="0" marginwidth="0" border="0" style="float:left;width:auto;height:auto;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_reader.do?nodeId=${nodeId}&instanceId=${instanceId}&firstStep=${firstStep}&processId=${processId}"></iframe>
		<!--  
			<iframe id="frame3" class="frame3" frameborder="no" marginheight="0" marginwidth="0" border="0" style="float:left;width:auto;height:auto;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_userGroup.do?nodeId=${nodeId}&send=${send}"></iframe> 
		-->
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
</body>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
<script>
	function initRedirect(){
		$.ajax({
			url : '${ctx}/table_getAllnodes.do?workFlowId=${workFlowId}',
			type : 'POST',  
			cache : false,
			async : true,
			dataType:'json',
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

	function routeType(nextNodeId){
		var obj = document.getElementById("frame1").contentWindow;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		var neRouteType = document.getElementById("neRouteType").value ;
		$.ajax({   
			url : 'table_getWfLineOfType.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_getWfLineOfType.do)');
			},
			data : {
				'workFlowId':workFlowId,'nodeId':'${nodeId}','nextNodeId':nextNodeId
			},    
			success : function(msg) {  
				//给人员选择iframe传值
			//	document.getElementById("frame3").src = '${ctx}/table_userGroup.do?click=true&nodeId='+nextNodeId+'&send=${send}&routType='+msg;
				//给发送下一步传所点击的按钮的属性值--下一步的节点值
				var ret=window.showModalDialog('${ctx}/table_userGroup.do?click=true&nodeId='+nextNodeId+'&send=${send}&routType='+msg+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
				if(ret){
				if(neRouteType*1 == 2){
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
					sendNext();
				}
				
			}
		});
	}

	function end(){
		if(confirm("确定要办结吗？")){
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
				url : 'table_end.do',
				//url : 'table_end.do?instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&formId='+formId+tagValue,
				type : 'POST',   
				cache : false,
				async : false,
				data :params,
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
	}
	
	function sendNext(){
		var nextNodeId = document.getElementById("neNodeId").value ;
		var neRouteType = document.getElementById("neRouteType").value ;
		var xtoName = "";
		var xccName = "";
		if(neRouteType==0){
			xtoName = document.getElementById("toName").value;
		}else if(neRouteType ==1){
			xtoName = objFrame3.document.getElementById("toName").value;
		}else if (neRouteType == 2){
			//==============判断主送抄送====================
			xtoName = document.getElementById("xtoName").value;
			xccName = document.getElementById("xccName").value;
			//====================================//		
		}
		var tagValue = getProValue();
		var obj = document.getElementById("frame1").contentWindow;
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var formId = eval(obj.document.getElementById("formId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		var att_comment_id = "";
		var processId = eval(obj.document.getElementById("processId")).value;
		var title = eval(obj.document.getElementById('${title_column}')).value;
		if(title != null && title !=""){
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
						var params = pageValue;
						//from  url
						params['isDb'] = '${isDb}';
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
						$.ajax({  
							url :'${ctx}/table_sendNext.do',
							//url : '${ctx}/table_sendNext.do?isDb=${isDb}&itemId=${itemId}&xtoName='+xtoName+"&xccName="+xccName+"&instanceId="+instanceId+"&formId="+formId+"&nodeId=${nodeId}"+"&nextNodeId="+nextNodeId+"&workFlowId="+workFlowId+"&processId=${processId}&att_comment_id=${att_comment_id}"+tagValue,
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
									alert("发送成功！");
									window.location.href="${ctx}/table_getPendingList.do";
								}
							}
						});
						//window.location.href="${ctx}/table_sendNext.do?xtoName="+xtoName+"&xccName="+xccName+"&title="+vc_title+"&instanceId="+instanceId+"&formId="+formId+"&nodeId=${nodeId}"+"&nextNodeId="+nextNodeId+"&workFlowId="+workFlowId+"&processId=${processId}&att_comment_id=${att_comment_id}"+tagValue;
					}
				}
			});
		}else {  
			alert("流程标题所对应的存储字段不能为空!");  
		}
		
	}

    //保存当前节点表单
	function operateForm(operate){
		var obj = document.getElementById("frame1").contentWindow;
		var tagValue = getProValue();//得到属性值
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var formId = eval(obj.document.getElementById("formId")).value;
		var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
		var title = eval(obj.document.getElementById('${title_column}')).value;
		var att_comment_id = "";
		var pageValue =tagValue;
		if(formjson != ''){
			// 合并 json 对象
			pageValue = mergeJson(tagValue,formjson);
		}
		
		var params  = pageValue;
		// from url
		params['instanceId'] = '${instanceId}';
		params['formId'] = formId;
		params['oldFormId'] = '${oldFormId}';
		params['itemId'] = '${itemId}';
		params['nodeId'] = '${nodeId}';
		params['workFlowId'] = workFlowId;
		params['processId'] = '${processId}';
		params['operate'] = operate;
		if(title != null && title !=""){
			$.ajax({
				url : '${ctx}/table_onlySave.do',
				//url : '${ctx}/table_onlySave.do?instanceId='+instanceId+'&formId='+formId+'&oldFormId=${oldFormId}&itemId=${itemId}'+'&nodeId=${nodeId}'+'&workFlowId='+workFlowId+'&processId=${processId}&operate='+operate+tagValue,
				type : 'POST',  
				cache : false,
				async : false,
				data : params,
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
						window.location.href="${ctx}/table_getPendingList.do"; 
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

    
    //获取属性和属性值
	function getProValue(){
		var tagHaveName = '${tagHaveName}';//列表型
		var tagName = '${tagName}';//非列表型
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
					for(var k=1;k<tagHaves.length;k++){
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
			var tagNames= new Array(); //定义一数组
			tagNames = tagName.split(",");
			for(var i=0;i<=tagNames.length-1;i++){   
				if(obj.document.getElementById(tagNames[i])){
					tagValue += "&"+tagNames[i]+"="+encodeURI(eval(obj.document.getElementById(tagNames[i])).value);  
					tags[tagNames[i]] = eval(obj.document.getElementById(tagNames[i]));
				}
			}  
		}
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
	//	alert(title);
	//	if(title != null && title !=""){
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
				url : '${ctx}/table_onlySave.do',
				//url : '${ctx}/table_onlySave.do?instanceId='+instanceId+'&formId='+formId+'&oldFormId=${oldFormId}&itemId=${itemId}'+'&nodeId=${nodeId}'+'&workFlowId='+workFlowId+'&processId=${processId}&operate='+operate+tagValue,
				type : 'POST',  
				cache : false,
				async : false,
				data : params,
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
		//}else {
		//	alert("流程标题所对应的存储字段不能为空!");
		//}
	    
		
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
						'type':assistType,'employeeinfo':str,'msg':msg
					},    
					success : function(msg) {  
						alert('发送成功');
					}
				});
		    }
		});
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
		if('${tagName}' == ''){
			alert("设置有误，请重新设置！");
			window.location.href="${ctx}/guidPage.jsp";
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
	$('.frame1').width(bW*0.7-45);
	$('.frame2').width(bW*0.3+28); 
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	var bW=$(window).width()-15;  
	$('.tabsContent').height(bH-42);  
	$('iframe').height($('.tabsContent').height()-27);
	$('.frame1').width(bW*0.7-45); 
	$('.frame2').width(bW*0.3+28);
})
</script>
<script>
$('.bdxlid').mouseenter(function(){
	$(this).find('.bdxlcon').slideDown(100);
});
$('.bdxlid').mouseleave(function(){
	$(this).find('.bdxlcon').slideUp(100);
});
</script>
</html>