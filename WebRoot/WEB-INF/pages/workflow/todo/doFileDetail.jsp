<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/headerindex.jsp"%>
</head>
<body  onload="init();">
<div style="padding:5px;">
<div class="pageContent">  
	<div class="tabs" currentIndex="1" eventType="click">
		<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
			<div>
			<iframe id="frame0" class="frame0" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getProcess.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
			</div>
			<div>
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" href="javascript:window.location.href='${ctx}/table_getProcess.do?instanceId=${instanceId}&workFlowId=${workFlowId}'"><span>返回跟踪列表</span></a></li>  
				</ul>
			</div>
			<div style="overflow:hidden;">
			<iframe id="frame1" class="frame1" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_frameForm.do?formLocation=${formLocation}&workFlowId=${workFlowId}&formId=${formId}&processId=${processId}&instanceId=${instanceId}&att_comment_id=${att_comment_id}&value=${value}&nodeId=${nodeId}&dofile=true"></iframe>
			</div>
			</div>
			<input type="hidden" id="neNodeId" name="neNodeId" value="">
			<input type="hidden" id="neRouteType" name="neRouteType" value="">
		</div>
	</div>
	
</div>
</div>
</body>
<script>
   	//设置iframe高度  
    var winW=$(window).width(),winH=$(window).height();
    $('iframe.iframec').height(winH-75);
    window.onresize=function(){
	   	var winH=$(window).height()-75;
	   	$('iframe.iframec').height(winH);
   	};

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
					obj.document.getElementById(tagNames[i]).readOnly=true;
				}
			}
		}
		if(luruTagIds!=null && luruTagIds!=''){
			var tagIds= new Array(); //定义一数组
			tagIds = luruTagIds.split(",");
			for(var i=0;i<tagIds.length;i++){   
				obj.document.getElementById(tagIds[i]+'luru').readOnly=true;
			}
			//obj.document.getElementById('2222A0DC-2F86-4489-835A-71379135771Fcsyjluru').style.display='none';
			
		}
		var array = obj.document.getElementsByName('yjluru');
		if(array.length !=0 && array != null && array != ""){
			for (var j = 0; j < array.length; j++) {
				array[j].style.display='none';
			}
		}
		var deleteAbleds = obj.document.getElementsByName('deleteAbled');
		if(deleteAbleds.length !=0 && deleteAbleds != null && deleteAbleds != ""){
			for (var k = 0; k < deleteAbleds.length; k++) {
				deleteAbleds[k].value = false;
			}
		}
		if(obj.document.getElementById('${instanceId}att')){
			obj.document.getElementById('${instanceId}att').style.display='none';
		}
		var delbtns = obj.document.getElementsByName('attachmentDel');
		for(var i=0;i<delbtns.length;i++){
			delbtns[i].style.display='none';
		}

		//---------------发文单文号------------
		if(obj.document.getElementById("exteriordn")){
			obj.document.getElementById("exteriordn").style.display="none";
		}
		//---------------办文单文号------------
		if(obj.document.getElementById("exteriordn_tagid_zhu")){
			obj.document.getElementById("exteriordn_tagid_zhu").style.display="none";
		}
		//主送
		if(obj.document.getElementById("selectXTO")){
			obj.document.getElementById("selectXTO").style.display="none";
		}
		//抄送
		if(obj.document.getElementById("selectXCC")){
			obj.document.getElementById("selectXCC").style.display="none";
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
	var bW=$(window).width()-15;  
	$('.tabsContent').height(bH); 
	$('iframe').height($('.tabsContent').height()-27);  
	$('.frame1').width(bW);
	$('.frame2').width(bW*0.2);
	$('.frame3').width(bW*0.2);
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	var bW=$(window).width()-15;  
	$('.tabsContent').height(bH);  
	$('iframe').height($('.tabsContent').height()-27);  
	$('.frame1').width(bW); 
	$('.frame2').width(bW*0.2);
	$('.frame3').width(bW*0.2);
})
</script>
</html>