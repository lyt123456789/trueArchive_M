 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>已收列表</title>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
       	<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
    </head>
    <body>
    	<div class="jQtabs">
    		<ul class="jQtabs-naverPanel">
 				<li class="jQtabmenu"><span>省委来文</span></li>
 				<li class="jQtabmenu"><span>省政府来文</span></li>
 				<li class="jQtabmenu"><span>市政府来文</span></li>
 				<li class="jQtabmenu"><span>其他单位来文</span></li>
 				<li class="jQtabmenu"><span>全部来文</span></li>
    		</ul>
	    	<div class="jQtabs-containerPanel">
	    		<div class="jQtabcontent"> <iframe class="iframec" id="formFrame0" name="formFrame" height="550px" align="middle" scrolling="auto" src="${ctx}/rec_receivedDocList4SW.do?fromType=shengw" width="100%" frameborder="0"></iframe> </div>
				<div class="jQtabcontent"> <iframe class="iframec" id="formFrame1" name="formFrame" height="550px" align="middle" scrolling="auto" src="${ctx}/rec_receivedDocList4SW.do?fromType=shengzf" width="100%" frameborder="0"></iframe> </div>
				<div class="jQtabcontent"> <iframe class="iframec" id="formFrame2" name="formFrame" height="550px" align="middle" scrolling="auto" src="${ctx}/rec_receivedDocList4SW.do?fromType=shizf" width="100%" frameborder="0"></iframe> </div>
				<div class="jQtabcontent"> <iframe class="iframec" id="formFrame3" name="formFrame" height="550px" align="middle" scrolling="auto" src="${ctx}/rec_receivedDocList4SW.do?fromType=others" width="100%" frameborder="0"></iframe> </div>
				<div class="jQtabcontent"> <iframe class="iframec" id="formFrame3" name="formFrame" height="550px" align="middle" scrolling="auto" src="${ctx}/rec_receivedDocList.do" width="100%" frameborder="0"></iframe> </div>
	    	</div>
		</div>
		<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
	   	<script src="${cdn_js}/sea.js"></script>
	    <script>   
	    	seajs.use('lib/tabs',function(){
	        	$('.jQtabs').tab();
	        });
	    </script>
    </body>
</html>
