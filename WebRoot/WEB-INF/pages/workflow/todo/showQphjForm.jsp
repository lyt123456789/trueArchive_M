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
	<link href="images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
<Link href="images/common.css" type="text/css" rel="stylesheet" />
<link href="images/index.css" type="text/css" rel="stylesheet" />
	<script src="pdfocx/json2.js"></script>
</head>
<body style="overflow:auto;">
  <div class="content">
    <div class="bl_nav">
    <!-- 左右跳转图片显示 start -->
	<iframe height="60px" id="mliframe1" width="40px"  allowTransparency="true"	style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:50px;top:350px;z-index:9;" src="${ctx}/mlzg.jsp"></iframe>
	<div id="gzl_pageLeftBox" >
			<a id="leftPage" class="b_disable" onclick="changePageToLeft()" style="cursor:pointer;"></a></div>
	<div id="gzl_pageRightBox">
			<a id="rightPage" onclick="changePageToRight()" style="cursor:pointer;"></a></div>
	 <!-- 左右跳转图片显示    end -->
		<ul class="clearfix">
	      <li id="li_dir" style="padding-bottom:  10px;">
	      	<a  href="#" class="contents fl ml8">目录</a>
	      	<div class="bl_n_nav" style="z-index:11;top:32px;display:none;width: 99px"  >
	      		<div class="bl_nav_top" style="z-index:11;" ></div>
	      	</div>
	      	<iframe height="203px" id="mliframe" width="99px"   style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:0px;top:42px;z-index:111;" src=""></iframe>
	     </li>  
	     </ul>
	   </div>
	   <div style="overflow: hidden;">
	   	<script type="text/javascript">
	  		$(function(){
	  			var h = window.top.document.body.clientHeight?window.top.document.body.clientHeight:window.top.document.documentElement.clientHeight;
	  			$("#frame3").height(h-100);
	  		});
	  	</script>
	  		<iframe id="frame3" name="frame3" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;z-index:0;" src="${ctx}/pdfocx/1.jsp?isCheck=1&usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&isWriteNewValue=false&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&atts=''&isOver=${isOver}"></iframe>
	  </div>
	 </div>
<input type="hidden" id="imageCount" name="imageCount" value="${imageCount}">
<textarea style="display:none;" id="pdfPath" name="pdfPath">${pdfPath}</textarea>
<input type="hidden" id="workflowId" name="workflowId" value="${workflowId}">
<textarea style="display:none;" id="commentJson" name="commentJson">${commentJson}</textarea>
	</body>
	<script type="text/javascript">
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
	    if(  document.getElementById('fjml')){
	    	  document.getElementById('fjml').style.left=cd+"px";
	    	  document.getElementById('fjml').style.top="50px";
	    }
	</script>
	<script type="text/javascript">
	var pages = "";
	var yys = 0;
	var ps = "";
	$(document).ready(function() {
		var commentJson = '${commentJson}';
		if(commentJson != ''){
			var l = '${imageCount}';
			for(var i=1;i<l;i++){
				if(commentJson.indexOf('"page":'+i) > -1){
					pages += i +",";
				}
			}
		}
		if(pages.length>0){
			pages = pages.substr(0, pages.length-1);
		}
		var iframe = document.getElementById("mliframe");
		ps = pages.split(",");
		yys = parseInt(ps[0])+1;
		iframe.src="${ctx}/qpml.jsp?pages="+pages+"&t="+new Date();
		
	});
	
	function changePageToFirst(){
		changePage(yys);
	}
	
	
	//重新选择页面
	function changePage(value){
		if(ps!=null && ps.length>0){
			if(value == parseInt(ps[0])+1){
				document.getElementById("leftPage").className="b_disable";
				document.getElementById("rightPage").className="";
			}else if(value == ps[ps.length-1]){
				document.getElementById("leftPage").className="";
				document.getElementById("rightPage").className="b_disable";
			}else{
				document.getElementById("leftPage").className="";
				document.getElementById("rightPage").className="";
			}
			if(ps.length==1){
				document.getElementById("leftPage").className="b_disable";
				document.getElementById("rightPage").className="b_disable";
			}
			document.getElementById("frame3").contentWindow.ymChange(value);
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+value)).className="hot";
			yys=value;
		}
	}
	
	
	function changePageToLeft(){
		var max =  parseInt(ps[ps.length-1])+1;
		if(yys>ps[0]){
			var val = yys;
			document.getElementById("frame3").contentWindow.ymChange(val);
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
			if(val == (parseInt(ps[0])+1)){
				document.getElementById("leftPage").className="b_disable";
			}
			if(val < max){
				document.getElementById("rightPage").className="";
			}
			yys=val;
		}
	}

	function changePageToRight(){
		var max =  parseInt(ps[ps.length-1])+1;
		if(yys<max){
			var val = yys + 1 ;
			document.getElementById("frame3").contentWindow.ymChange(val);
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
			if(val > 1){
				document.getElementById("leftPage").className="";
			}
			if(max == val){
				document.getElementById("rightPage").className="b_disable";
			}
			//ps = val;
		}
	}
	</script>
</html>
