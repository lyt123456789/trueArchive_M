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
<body  id="body">
<textarea style="display:none;" id="commentJson" name="commentJson">${commentJson}</textarea>
<textarea style="display:none;" id="pdfPath" name="pdfPath">${pdfPath}</textarea>
<input type="hidden" id="imageCount" name="imageCount" value="${imageCount}">
<input type="hidden" id="fontSize" name="fontSize" value="${fontSize}">
<input type="hidden" id="verticalSpacing" name="verticalSpacing" value="${verticalSpacing}">
<input type="hidden" id="dateFormat" name="dateFormat" value="${dateFormat}">
<input type="hidden" id="font" name="font" value="${font}">
<div class="warp1">
<div class="dh">
<c:if test="${isds!='1'}">
<div class="bl_tab lc fl ml30"><a style="cursor:pointer">
<span >历程</span></a>
</div></c:if>
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
    	<div style="display: none;">
    	  <%
    		if(js!=null){
	 			for(int i=0;i<js.size();i++){
	 				%>
	 				  <trueway:att onlineEditAble="true" id='<%=(instanceId+js.getString(i))%>' docguid='<%=(instanceId+js.getString(i))%>' showId='<%=(js.getString(i)+"show")%>'   ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
	 				<%
	 			}
	 		}
    	%>
    		<c:if test="${instanceId!=allInstanceId }">
    		<trueway:att onlineEditAble="true" id='${instanceId}newfj' docguid='${instanceId}newfj' showId="newfjshow" ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
    		</c:if><trueway:att onlineEditAble="true" id='${instanceId}oldfj' docguid='${instanceId}oldfj' showId="oldfjshow" ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
    	</div>
         </div>
            <div class="bl_nav_bottom"></div>
        </div>
      </li>
    </ul>
 	<c:if test="${status !='1' }"> 
 		<div class="nr fl ml10">
  		 	<span class="fl">下一步:</span>
			 <div class="fl step_box">
				<a style="cursor: pointer;" href="javascript:operate();"><span>完成</span></a>
	 		</div>
 		</div>
 	  <div class="fl xx">
    	 	<div class="fl ml8 btn_send">
    	 		<span onclick="onSeal();" style="cursor: pointer;">盖章</span>
    		 </div>
    	 </div>
    </c:if>
</div>
<div>
<div style="overflow: hidden;">
	<iframe height="100%" id="lcbox" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:97%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getProcess.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
	<c:choose>
		<c:when test="${(isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' )}">
			<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&isWriteNewValue=false&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&atts=''&isOver=${isOver}"></iframe>
		</c:when>
		<c:otherwise>
			<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&isWriteNewValue=${isWriteNewValue}&newInstanceId=${newInstanceId}&newProcessId=${newProcessId}&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&atts=${atts}&isOver=${isOver}"></iframe>
		</c:otherwise>
	</c:choose>
</div>
</div>
</div>
</div>
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

function operate(){
	//获取页面json
	var obj = document.getElementById("frame1").contentWindow;
	var json = "";
	var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
	json = JSON2.stringify(trueJSON.pdfjson);
	var processId = eval(obj.document.getElementById("processId")).value;
	var params ={};
	params['json'] = json;
	params['processId'] = processId;
	//ajax用户获取章
	$.ajax({
		url : '${ctx}/table_operateForm.do',
		type : 'POST',  
		cache : false,
		async : false,
		data : params,
		error : function() {
			alert('AJAX调用错误(table_operateForm.do)');
		},
		success: function(msg) {
			if(msg == 'success'){
				alert("盖章办理成功！");
				window.close();
				window.returnValue='refresh';
			}
		}
	});
}
//<script>
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

function onSeal(){
	document.getElementById("frame1").contentWindow.onSeal();
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
