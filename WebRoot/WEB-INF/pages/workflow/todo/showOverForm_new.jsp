﻿﻿﻿<!DOCTYPE html>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
  	<head>
    	<meta charset="utf-8"><!-- 国际编码 -->
    	<meta http-equiv="X-UA-Compatible" content="IE=edge"><!-- 最高版本ie渲染 -->
    	<meta name="viewport" content="width=device-width, initial-scale=1"><!-- 网站适应各种浏览设备 -->
    	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    	<%@ include file="/common/header.jsp"%>
		<%@page import="net.sf.json.JSONArray"%>
		<%@page import="net.sf.json.JSONArray"%>
    	<link href="${ctx}/assets-common/css/bootstrap.min.css" rel="stylesheet">
    	<link href="${ctx}/assets-common/css/index.css?t=1234" rel="stylesheet">
    	<link href="${ctx}/assets-common/css/font.css" rel="stylesheet">
    	<link href="${ctx}/assets-common/css/pagesdome.css?t=201805031704" rel="stylesheet">
    	<link href="${ctx}/images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
		<link href="${ctx}/images/common.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/images/index.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/assets-common/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/assets-common/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
    	<style type="text/css">
			.list ul li ul li a {
		    	min-width: 70px;
		    }    
		    .yiji > li > ul > li {
				min-width: 120px;
				text-align: left;
			}	
    	</style>
    	<script src="${ctx}/images/jquery.min.js" type="text/javascript"></script>
    	<script src="${ctx}/images/jquery.tab.js" type="text/javascript"></script>
    	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js?t=201803291634" type="text/javascript"></script>
		<script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
    	<script type="text/javascript" defer="defer">
    		var treeWin = null	
    	
	    	parent.$('.nav-tabs').hide()
			var itemType='${itemType}';
			var status = "0";
			if(itemType=='0'){
				status='1';
			}
			
			function closeKj(){
				document.getElementById("frame1").contentWindow.objClose();
			}
			
			function objOpen(){
				document.getElementById("frame1").contentWindow.openClose();
			}
			
			function checknumber(){
				var wcsx=$('#szsx').val();
				if(wcsx){
					var reg=new RegExp("^[0-9]*$");
					if(!reg.test(wcsx)){
						$('#szsx').val('');
					}
				}
			}
		
			function onSeal(){
				document.getElementById("frame1").contentWindow.onSeal();
			}
			
			function upFileToServer(){
				document.getElementById("frame1").contentWindow.upFileToServer();
			}
			
			function loadCEB(path){
				document.getElementById("frame1").contentWindow.loadCEB(path);
			}
			
			var fileWidthLevel = 1;
			function changeBigger(){
				if(fileWidthLevel == 3){
					fileWidthLevel == 3
				}else{
					fileWidthLevel ++;
				}
				document.getElementById("frame1").contentWindow.changeOCXpdfobjWd(fileWidthLevel);
			}
			
			function changeLitter(){
				if(fileWidthLevel == 1){
					fileWidthLevel == 1
				}else{
					fileWidthLevel --;
				}
				document.getElementById("frame1").contentWindow.changeOCXpdfobjWd(fileWidthLevel);
			}
		</script>
		<script type="text/javascript">
			if(top.window && top.window.process && top.window.process.type){
			    var imported = document.createElement('script');
				imported.src = '${ctx}/common-assets/js/eleCommon.js';
				document.head.appendChild(imported);
			}
		</script>
  	</head>
  	<body onload="init();" style="overflow:hidden;background-color:#989898">
  		<input type="hidden" id="closeWay" name="closeWay" value="${closeWay}">
		<input type="hidden" id="params" name="params" value="${params}">
		<textarea style="display:none;" id="commentJson" name="commentJson">${commentJson}</textarea>
		<input type="hidden" id="isFlexibleForm" name="isFlexibleForm" value="${isFlexibleForm}" />
		<input type="hidden" id="title" name="title" value="${title}" />
		<input type="hidden" id="status" name="status" value="${status}" />
		<div style="display:none;">
		<textarea id="formPageJson" name="formPageJson">${formPageJson}</textarea>
		</div>
		<textarea style="display:none;" id="pdfPath" name="pdfPath">${pdfPath}</textarea>
		<textarea style="display:none;" id="allPdfPath" name="allPdfPath">${allPdfPath}</textarea>
		<textarea style="display:none;" id="toUserName" name="toUserName">${toUserName}</textarea>
		<textarea style="display:none;" id="employeeSort" name="employeeSort">${employeeSort}</textarea>
		<textarea style="display:none;" id="processSort" name="processSort">${processSort}</textarea>
		<input type="hidden" id="imageCount" name="imageCount" value="${imageCount}">
		<input type="hidden" id="modId" name="modId" value="${modId}">
		<input type="hidden" id="matchId" name="matchId" value="${matchId}">
		<input type="hidden" id="dicId" name="dicId" value="${dicId}">
		<input type="hidden" id="dicValue" name="dicValue" value="${dicValue}">
		<input type="hidden" id="note_ids" name="note_ids" value="${note_ids}">
		<input type="hidden" id="note_names" name="note_names" value="${note_names}">
		<input type="hidden" id="fontSize" name="fontSize" value="${fontSize}">
		<input type="hidden" id="font" name="font" value="${font}">
		<input type="hidden" id="verticalSpacing" name="verticalSpacing" value="${verticalSpacing}">
		<input type="hidden" id="dateFormat" name="dateFormat" value="${dateFormat}">
		<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
		<input type="hidden" id="neNodeId" name="neNodeId" value="">
		<input type="hidden" id="neRouteType" name="neRouteType" value="">
		<input type="hidden" id="toName" name="toName">
		<input type="hidden" id="xtoName" name="xtoName">
		<input type="hidden" id="xccName" name="xccName">
		<input type="hidden" id="sendMsgUserId" name="sendMsgUserId">
		<input type="hidden" id="yqinstanceid" name="yqinstanceid" value="${yqinstanceid}">
		<input type="hidden" id="closeframe" name="closeframe" value="0">
		<input type="hidden" id="flowinfo" name="flowinfo" value="">
    	<div class="wrap" style="margin:0">
      		<div class="gray-header"></div>
      		<div class="header">
      			<button class="ys"></button>
      			<c:if test="${itemType eq '0' }">
					<div class="fj btn-not"><img src='${ctx}/assets-common/newImage2/fj.png'>附件<b id="fjCount"></b></div>
	      			<div class="zw btn-not"><img src="${ctx}/assets-common/newImage2/zw.png"><a>正文</a></div>
				</c:if>
				<c:if test="${itemType eq '1' || itemType eq '3' || itemType eq '4'}">
					<c:if test="${isFirst == true}">
						<div class="fj btn-not"><img src='${ctx}/assets-common/newImage2/fj.png'>附件<b id="fjCount"></b></div>
					</c:if>
					<c:if test="${isFirst != true}">
						<div class="fj btn-not"><img src='${ctx}/assets-common/newImage2/fj.png'>附件<b id="fjCount"></b></div>
					</c:if>
				</c:if>
      			<h1 style="margin-top: -36px;"></h1>
      			<div class="navmen">
		        	<span style="overflow:hidden;white-space:nowrap;-o-text-overflow:ellipsis;text-overflow:ellipsis">流程图</span>
		        </div>
		        <img src="${ctx}/assets-common/newImage2/xt.png" class="dw">
		        <div class="zw1" onclick="PrintPDF(0);"><img src="${ctx}/assets-common/newImage2/dy.png"><a href="javascript:void(0)">打印</a></div>
		        <div class="zw1" style="right:138px;width:122px;" onclick="PrintCurPage();"><img src="${ctx}/assets-common/newImage2/dy.png"><a href="javascript:void(0)">打印当前页</a></div>
		        
		        <div class="zw1" style="right: 106px;width: 24px;color: white;cursor: pointer;" onclick="changeBigger();" title="放大">
		        	<i class="iconfont icon-fangda" style="padding: 0 4px 0 4px;position: relative;top: 6px;" title="放大"></i>
		        </div>
		        <div class="zw1" style="right: 74px;width: 24px;color: white;cursor: pointer;" onclick="changeLitter();" title="缩小">
		        	<i class="iconfont icon-suoxiao" style="padding: 0 4px 0 4px;position: relative;top: 6px;" title="缩小"></i>
		        </div>
		        
				<i class="close-tab glyphicon glyphicon-remove right-close-btn" style="cursor:pointer;position: absolute;right: 20px;top: 9.5px;font-size: 25px;font-weight: 100;color: white;border:1px solid"></i>
      		</div>
      		<div class="main clearfix">
        		<div class="cg_con fl" style="width: 1024px;margin: 0 auto;z-index: 30;left:0;right:0;position:fixed;background-color:#F8F8F8;">
          			<div class="cg_info jQtabcontent" style="margin-top:35px">
            			<div class="fj_load" style="overflow-y: hidden;">
              				<c:if test="${itemType eq '0' }">
								<!-- 可编辑，并且为word文件，后缀attzw 可下载 根据allowUpload字段判断是否可上传 第一步可删除 -->
								<div class="fj_file file-type-2" style="display: none;">
								<c:forEach items="${zwAtts}" var="zwAtt">
	              					<div class="file-item">
	              						<div class="progress-bar"></div>
		                				<span class="pt">【正    文】</span>
		                				<span class="pt-title">${zwAtt.filename}</span>
		                				<span class="nc">[${zwAtt.fileSize}] </span>
		                				<c:if test="${zwAtt.filetype ne 'ceb'&&zwAtt.filetype ne 'pdf'&&zwAtt.filetype ne 'PDF'&&zwAtt.filetype ne 'CEB' }">
		                				<span style="vertical-align:-6px;"  id="${zwAtt.id}"><img src="${ctx}/assets-common/newImage2/xz.png" onclick="download('${zwAtt.localation}','${zwAtt.id}','${zwAtt.filename}','${downloadUrl}?name=${zwAtt.filename}&location=${zwAtt.localation}&fileId=${zwAtt.id}',downloadCb);"></span>
		                				</c:if>
		                				<c:if test="${zwAtt.filetype eq 'ceb' or zwAtt.filetype eq 'pdf' or zwAtt.filetype eq 'PDF' or zwAtt.filetype eq 'CEB' }">
		                					<c:if test="${fn:contains(zwAtt.editer,userId)||fgwDownload}">
		                						<span style="vertical-align:-6px;"  id="${zwAtt.id}"><img src="${ctx}/assets-common/newImage2/xz.png" onclick="download('${zwAtt.localation}','${zwAtt.id}','${zwAtt.filename}','${downloadUrl}?name=${zwAtt.filename}&location=${zwAtt.localation}&fileId=${zwAtt.id}',downloadCb);"></span>
			                				</c:if>
		                				</c:if>
		               					<c:if test="${zwAtt.filetype eq 'doc' || zwAtt.filetype eq 'docx'}"><!-- 是否是word  -->
		               						<span style="vertical-align:-6px;"><img src="${ctx}/assets-common/newImage2/history.png" onclick="viewHistory('${zwAtt.docguid}','${zwAtt.id}');"></span>
		               					</c:if>
	               					</div>
	              				</c:forEach>
	              				</div>
							</c:if>
							<!-- 不可编辑，后缀 fj 可下载，第一步可上传，第一步可删除 -->
							<div class="fj_file file-type-1" style="display: none;">
							<c:forEach items="${fjAtts}" var="fjAtt">
              					<div class="file-item">
              						<div class="progress-bar"></div>
	                				<span class="pt">【普通附件】</span>
	                				<span class="pt-title">${fjAtt.filename}</span>
	                				<span class="nc">[${fjAtt.fileSize}]</span>
	                				<c:if test="${fjAtt.filetype ne 'ceb'&&fjAtt.filetype ne 'pdf'&&fjAtt.filetype ne 'PDF'&&fjAtt.filetype ne 'CEB'}">
		                			<span style="vertical-align:-6px;"  id="${fjAtt.id}">
	               						<img src="${ctx}/assets-common/newImage2/xz.png" onclick="download('${fjAtt.localation}','${fjAtt.id}','${fjAtt.filename}','${downloadUrl}?name=${fjAtt.filename}&location=${fjAtt.localation}&fileId=${fjAtt.id}',downloadCb);">
	               					</span>
		                			</c:if>
		                			 <c:if test="${fjAtt.filetype eq 'ceb' or fjAtt.filetype eq 'pdf' or fjAtt.filetype eq 'PDF' or fjAtt.filetype eq 'CEB' }">
		               					 	<c:if test="${fn:contains(fjAtt.editer,userId)||fgwDownload}">
		               							<span style="vertical-align:-6px;" id=${fjAtt.id} ><img src="${ctx}/assets-common/newImage2/xz.png" onclick="download('${fjAtt.localation}','${fjAtt.id}','${fjAtt.filename}','${downloadUrl}?name=${fjAtt.filename}&location=${fjAtt.localation}',downloadCb);"></span>
		               						</c:if>
		               					</c:if>
               					</div>
              				</c:forEach>
              				</div>
              				<div class="biaoge">
              					<iframe height="100%" id="lcbox" style="padding:0px;margin:0px;display:none;z-index:9;width:100%;height:85%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;position: fixed;left: 0;right: 0;margin: 0 auto;" src="${ctx}/table_getWfps.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
								<c:choose>
									<c:when test="${(isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' )}">
										<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:88%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;background-color: #989898;position: fixed;left: 0;right: 0;margin: 0px auto;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&userId=${userId}&loginname=${loginname}&isWriteNewValue=false&allInstanceId=${allInstanceId}&atts=''&isOver=${isOver}"></iframe>
									</c:when>
									<c:otherwise>
								 		<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:88%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;background-color: #989898;position: fixed;left: 0;right: 0;margin: 0px auto;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&userId=${userId}&loginname=${loginname}&isWriteNewValue=${isWriteNewValue}&newInstanceId=${newInstanceId}&newProcessId=${newProcessId}&allInstanceId=${allInstanceId}&atts=&isOver=${isOver}"></iframe>
							 		</c:otherwise>
								</c:choose>
              				</div>
            			</div>
          			</div>
        		</div>
        		
      		</div>
      		<c:if test="${pages != null && pages != ''}">
      		<div class="nav fl" style="z-index:40;position:absolute;width:100px;margin-top:40px;">
       			<div class="nav_list">
           			<div class="sin_nav sin_nav_list">
           				<i class="icon-list-ul"></i>
           				<span>批阅页</span>
           				<em></em>
           			</div>
           			<div class="sort sort-list" style="overflow-y: scroll;">
            			<div class="bl_n_nav" style="z-index:11;"  >
							<div class="bl_nav_zj" id="bl_nav_zj"  style="z-index:11;width: 91px;" >
							
							</div>
						</div>
           			</div>
       			</div>
       		</div>
       		</c:if>
       		<div class="nav2 fl" style="margin-top:40px;position:fixed;z-index:40;right:0px;min-height: 800px;width:120px;">
				<div class="list">
 						<ul class="yiji">
   						<li>
   							<a href="#" class="inactive inactives" style="width: 100%;">操作</a>
     							<ul style="display: inline;">
        							<li onclick="javascript:downloadPdf('${processId}');"><i class="iconfont iconfont-41download"></i><a href="#">下载</a></li>
        							<c:if test="${favourite != '1'}">
        								<li onclick="javascript:addCollection();"><i class="iconfont iconfont-starton"></i><a href="#" id="collect">收藏</a></li>
        							</c:if>
        							<c:if test="${favourite == '1'}">
        								<li onclick="javascript:addCollection();"><i class="iconfont iconfont-starton"></i><a href="#" id="collected">取消收藏</a></li>
        							</c:if>
        							<c:if test="${null == status || '' == status || status != '6'}">
        							<li onclick="javascript:sendToChat('${processId}');"><i class="iconfont iconfont-share"></i><a href="#">转发</a></li>
        							<li onclick="javascript:sendToGroup('${processId}');"><i class="iconfont iconfont-share"></i><a href="#">转发组</a></li>
        							<li onclick="OnRotate('1');" class="JS_hidden"><i class="iconfont iconfont-iconfontchexiao1"></i><a href="#" title="左旋转"><span>左旋转</span></a></li>  
	 								<li onclick="OnRotate('0');" class="JS_hidden"><i class="iconfont iconfont-iconfontchexiao"></i></i><a href="#" title="右旋转"><span>右旋转</span></a></li>
	 								<li style="display: none;" onclick="javascript:SearchText();"><i class="iconfont iconfont-41download"></i><a href="#">检索</a></li>
        							</c:if>
        							<!-- <li title="放大">
                                   		<i class="iconfont icon-fangda"></i>
                                   		<a style="min-width:83px" href="#" onclick="changeBigger();"><span>放大</span></a>
                                   	</li>
                                   	<li title="缩小">
                                   		<i class="iconfont icon-suoxiao"></i>
                                   		<a style="min-width:83px" href="#" onclick="changeLitter();"><span>缩小</span></a>
                                   	</li>  -->   
     							</ul>
   						</li>
   						<li onclick="showHandWrite(1);" style="background-color:#008cee;border-radius: 5px;text-align: center;">
 							<a href="#" title="签批人" style="text-align: center;padding: 0;"><span>签批人</span></a>
 						</li>
					</ul>
				</div>
				<div id="toTop" style="position: fixed;z-index: 2;right: 40px;bottom: 100px;" onmouseover="toTopMouseOn();" onmouseout="toTopMouseOut();">
			 		<img id="toTopOut" src="${ctx}/assets-common/img/toTop.png" onclick="changePage(1)" />
			 		<img id="toTopOn" style="display: none;" src="${ctx}/assets-common/img/toTopOn.png" onclick="changePage(1)" />
			 	</div>
       		</div>
    	</div>
    	<embed id="sealObj" type="application/x-founder-npsealstamp" width=1 height=1 />
		<script src="images/jquery.min.js" type="text/javascript"></script>
		<script src="images/common.js" type="text/javascript"></script>
		<script src="images/jquery.tab.js" type="text/javascript"></script>
    	<script type="text/javascript">
	    	$('.inactive').click(function(){
	    		var list = $('.yiji').find('ul')
	    		$(list).toggle()
	    	});
	    	
	    	//获取设备屏幕尺寸
			function getPageSize() {
	    		debugger;
				var xScroll, yScroll, pageHeight, pageWidth, arrayPageSize;
				if (window.innerHeight && window.scrollMaxY) {
					xScroll = window.innerWidth + window.scrollMaxX;
					yScroll = window.innerHeight + window.scrollMaxY
				} else {
					if (document.body.scrollHeight > document.body.offsetHeight) {
						xScroll = document.body.scrollWidth;
						yScroll = document.body.scrollHeight
					} else {
						xScroll = document.body.offsetWidth;
						yScroll = document.body.offsetHeight
					}
				}
				var windowWidth, windowHeight;
				if (self.innerHeight) { if (document.documentElement.clientWidth) { windowWidth = document.documentElement.clientWidth } else { windowWidth = self.innerWidth } windowHeight = self.innerHeight } else {
					if (document.documentElement && document.documentElement.clientHeight) {
						windowWidth = document.documentElement.clientWidth;
						windowHeight = document.documentElement.clientHeight
					} else {
						if (document.body) {
							windowWidth = document.body.clientWidth;
							windowHeight = document.body.clientHeight
						}
					}
				}
				if (yScroll < windowHeight) { pageHeight = windowHeight } else { pageHeight = yScroll }
				if (xScroll < windowWidth) { pageWidth = xScroll } else { pageWidth = windowWidth } arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
				return arrayPageSize
			}
	    	
    		var isOpen = false;	
    		var layerBtns = top.document.getElementsByClassName('layui-layer-setwin')[0]
    		if(layerBtns) layerBtns.style.display='none'
       		$('.cg_con').tabBuild({currentClass:'fouce'});
    		var size = getPageSize()
       		$(document).ready(function(){
       			$('.right-close-btn').click(function(){
       				if(treeWin){
       					treeWin.close()
       					SetIsOpenDlg(0);
       				}
       				top.closeTabsInLayer();				
					top.layer.closeAll();
       				layerBtns.style.display='inline-block';
       				
       				
       			})
       			$('.header .ys').click(function(){
					top.closeTabsInLayer();				
					top.layer.closeAll();
       				layerBtns.style.display='inline-block';
       			})
				
       			$('.header .navmen').click(function(){
       				if($('#frame1').css('display') == 'none'){
       					$('.header .navmen span').text('流程图')
       					$('#frame1').css({'display':'block'})
       					$('#lcbox').css({'display':'none'})
       				} else {
       					$('.header .navmen span').text('办理')
       					$('#frame1').css({'display':'none'})
       					$('#lcbox').css({'display':'block'})
       				}
       			})
       			
       			//$('.fj_file').css({'visibility':'hidden','height':0,'min-height':0})
       			$('.fj_file').css({'display':'none'})
       			$('.c_btn').css({'display':'none'})
       			
       			if("${attSize}" != 0){
       			 	document.getElementById("fjCount").innerHTML = "(${attSize})";
       			}
       			
       			if($("#toNextStep ul").css('display')=='none'){
       				$("#toNextStep ul").slideDown(300);
       				$("#toNextStep").addClass('inactives');
    			}
       			
       			var html = "";
				var data = '${filePages}';
				var jsonArr = eval("("+data+")");
				var page = 1;
				var pages = '${pages}';
				if('' != pages && null != pages){
					var qppages = pages.split(',');
					for(var i=0;i<jsonArr.length;i++){
						var jsonObj = jsonArr[i];
						
						var childHtml = '';
						if(null != jsonObj){
							for(var j=1;j<=jsonObj.pageCount;j++){
								var flag = false;
								for(var k=0;k<qppages.length;k++){
									if(page == (parseInt(qppages[k])+1)){
										flag = true;
										break;
									}
								}
								if(flag){
									childHtml += "<a href=\"#\" onclick=\"changePage('"+page+"')\" id=\"ahref"+page+"\" >第"+j+"页<span class=\"edit\" style=\"font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;\" title=\"内容有改动\"></span></a>";
								}
								page++;
							}
						}
						if('' != childHtml){
							html += "<a href=\"#\"  style=\"color: #000;font-size:14px;\"  class=\"cur\">"+jsonObj.name+"</a>" + childHtml;
						}
					}
					document.getElementById("bl_nav_zj").innerHTML = html;
				}
       			
       			
				var href = window.location.href;
				var arr1 = href.split('?')
				var params = arr1[1] ? arr1[1].split('&') :[]
				var title = '${title}';
				if(title && title.length > 0){
					$('.header h1').text(title)
				} else {
					for(var i=0;i<params.length;i++){
						if(params[i].split('=')[0] == 'title'){
							$('.header h1').text(decodeURI(params[i].split('=')[1]))
							break;
						}
					}
				}
  				
  				$('.sin_nav_list').click(function(){
  					$('.sort-list').toggle()
  				})
  				$('.sin_nav_file').click(function(){
					
  					if($('.fj_file').css('visibility') == 'visible'){
  						$('.fj_file').css({'visibility':'hidden','height':0,'min-height':0})
  						$('.c_btn').css({'display':'none'})
  					} else {
  						$('.fj_file').css({'visibility':'visible','min-height':'0','height':''})
  						$('.c_btn').css({'display':'inline-block'})
  					}
  				})
  				
  				
  				$('.header .fj').click(function(){
  					var fjHeight = $(".file-type-1").height()
					var c_btn_height = $(".btn-type-1").height()
  					$('.file-type-1').css({'display':''});
  					if($('.header .fj').hasClass('btn-selected')){
  						$('.header .fj').addClass('btn-not').removeClass('btn-selected')
  						$('.header .zw').addClass('btn-not').removeClass('btn-selected')
  						
  						//$('.fj_file').css({'visibility':'hidden','height':0,'min-height':0})
  						$('.fj_file').css({'display':'none'})
  						$('.c_btn').css({'display':'none'})
  						document.getElementById("frame1").contentWindow.setPageSize(size[3] + 30, 1024);
  					} else {
  						$('.header .fj').addClass('btn-selected').removeClass('btn-not')
  						$('.header .zw').addClass('btn-not').removeClass('btn-selected')
  						
  						//$('.fj_file').css({'visibility':'visible','min-height':'0','height':''})
  						$('.fj_file').css({'display':'block'})
       					$('.c_btn').css({'display':'inline-block'})
       					//$('.file-type-2').css({'visibility':'hidden','height':0,'min-height':0})
       					$('.file-type-2').css({'display':'none'})
       					$('.btn-type-2').css({'display':'none'})
       					document.getElementById("frame1").contentWindow.setPageSize(size[3] + 30 - fjHeight - c_btn_height, 1024);
  					}
  				})
  				$('.header .zw').click(function(){
  					var fjHeight = $(".file-type-2").height()
					var c_btn_height = $(".btn-type-2").height()
  					$('.file-type-2').css({'display':''});
  					if($('.header .zw').hasClass('btn-selected')){
  						$('.header .fj').addClass('btn-not').removeClass('btn-selected')
  						$('.header .zw').addClass('btn-not').removeClass('btn-selected')
  						
  						//$('.fj_file').css({'visibility':'hidden','height':0,'min-height':0})
  						$('.fj_file').css({'display':'none'})
  						$('.c_btn').css({'display':'none'})
  						document.getElementById("frame1").contentWindow.setPageSize(size[3] + 30, 1024);
  					} else {
  						$('.header .zw').addClass('btn-selected').removeClass('btn-not')
  						$('.header .fj').addClass('btn-not').removeClass('btn-selected')
  						
  						//$('.fj_file').css({'visibility':'visible','min-height':'0','height':''})
  						$('.fj_file').css({'display':'block'})
       					$('.c_btn').css({'display':'inline-block'})
       					//$('.file-type-1').css({'visibility':'hidden','height':0,'min-height':0})
       					$('.file-type-1').css({'display':'none'})
       					$('.btn-type-1').css({'display':'none'})
       					document.getElementById("frame1").contentWindow.setPageSize(size[3] + 30 - fjHeight - c_btn_height, 1024);
  					}
        		})
			})
		</script>


		<script>
			if($($('.file-name')[0]).find('img').length == 2){
				
				$($('.file-attr')[0]).css({'padding-left':'140px'})
			}
		</script>
		<script type="text/javascript">
			var dontShowFinish = 0;
			var imageCount = parseInt('${imageCount}');
		
			//drawType:类别,  0:键盘输入; 1:手写； 3：橡皮擦
			function setDrawType(drawType,red, green, blue, width){
				document.getElementById("frame1").contentWindow.ShowHandWrite(0);
				document.getElementById("frame1").contentWindow.SetDrawType(drawType, red, green, blue, width);
			}
			//签批人信息是否展示： 1:展示
			var handWriteIsShow = 0;
			function showHandWrite(show){
				if(handWriteIsShow == 0){
					handWriteIsShow = 1;
					document.getElementById("frame1").contentWindow.ShowHandWrite(handWriteIsShow);
				}else{
					handWriteIsShow = 0;
					document.getElementById("frame1").contentWindow.ShowHandWrite(handWriteIsShow);
				}
			}
			
			function onRedo(){
				document.getElementById("frame1").contentWindow.OnRedo();
			}
			
			function onUndo(){
				document.getElementById("frame1").contentWindow.OnUndo();
			}
			
			function GetLocalMac(){
				/* return document.getElementById("frame1").contentWindow.GetLocalMac(); */
			}
		</script>
		<script>
			var isopened=false;
		
			var len1=0;
			function  changeClass(){
				
			};
			
			var lastState = null
			var arr = []
			var intervalId = null
			var processId = null
		
			function downloadCb(arr2,id,msgStr){
				lastState = msgStr
				arr = arr2
				processId = id
				
				console.log(arr2)
				
				if(!intervalId){
					intervalId = setInterval(function(){
						if(lastState){
							var btn = document.getElementById(processId);
							var fileItem = $(btn).parent();
							var progressBar = $(fileItem).find('.progress-bar');
							
							if(lastState && lastState.indexOf('fail') >=0){
								lastState = null
								$(progressBar).css({width:'0px'});
								alert('下载失败!');
								
								lastState = null
								arr = []
								intervalId = null
								processId = null
							} else {
								var complete = arr[0];
								var total = arr[1];
								var process = Number((complete / total).toFixed(4));
								$(progressBar).css({width:process*100+'%'});
							}
							if(lastState && lastState.indexOf('success') >=0){
								$(progressBar).css({width:100+'%'});
								clearInterval(intervalId)
								setTimeout(function(){
									lastState = null
									$(progressBar).css({width:'0px'});
									alert('下载完成 !');
									
									lastState = null
									arr = []
									intervalId = null
									processId = null
								},1000);
							}
						}
					},500)
				}
			}
			
			$(document).ready(function() {
				var mlstatus = 1;
				$('#li_dir').click(function(){
				 	if(mlstatus %2 == 0){
					 	over();
				 	}else{
					 	out();
				 	}
				});
				 
			    function over(event) {
			        $(this).addClass("fouce");
			        $('.bl_n_nav', this).show();
			        mlstatus ++;
			    }
			    
			    function out(event) {        
			        $(this).removeClass("fouce");
			        $('.bl_n_nav', this).hide();
			        mlstatus ++;
				}
			    
				if(imageCount == 1){
					if(document.getElementById("ahref1")){
						document.getElementById("ahref1").className="";
						document.getElementById("ahref1").className="hot";
					}
				}
				if("${attSize}" != 0){
				 	document.getElementById("fjCount").innerHTML = "(${attSize})";
				}
			});
			
			function PrintPDF(isOver){
				 document.getElementById("frame1").contentWindow.PrintPDF(isOver);
			}
			
			function PrintCurPage(){
				 document.getElementById("frame1").contentWindow.PrintCurPage();
			}
			
			</script>
			<script type="text/javascript">		//办件发送、暂存、办结等一些操作
				function PrintFrame(){
					if(window.frames['frame1'] == null){
						document.getElementById('frame1').focus();
						document.getElementById('frame1').contentWindow.print();
					}else{
						document.frames('frame1').window.focus();
						window.print();
					}
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
			   	 //判断有没有设置表单对应关系
			   	 function init(){
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
				parent.$('.nav-tabs').hide()
				
				
				
				var bH=$(window).height();
				var bW=$(window).width();  
				//$('.content').height(bH-30); 
				$('.frame1').height(bH-120);  
				$('.frame1').width(bW);
				
				
				$('.frame2').height(bH-120);  
				$('.frame2').width(bW);
				//初始化右箭头状态
				if(imageCount<2){
				    $('#rightPage').addClass('b_disable');
				}
				if(imageCount>0){
				     $('#mliframe').show();
				}
				if('${isFlexibleForm}' == '1'){
					//$('.JS_hidden').css("display","none");
				}
			});
			$(window).bind('resize',function(){
				var bH=$(window).height();
				var bW=$(window).width();  
				$('.frame1').height(bH-120);
				$('#frame2').height(bH-120);
				$('.bl_tab').each(function(i,item){		
					if(i == 1){		
						if($(item).hasClass('bl')){		
							$('.frame2').height(bH);		
							$('.frame2').width(bW);		
						}		
					} else if(i==0){		
						if($(item).hasClass('bl')){		
							$('.frame1').height(bH);		
							$('.frame1').width(bW);		
						}		
					}		
				});	
			})
			
			$('.quick_links li').click(function(){
				$('.quick_links li').removeClass('active');
				if( $(this).attr('id') == 'qprxx'){
					if(handWriteIsShow == 1){
						$(this).addClass('active');
					}
				}else{
					$(this).addClass('active');
				}
			})	
			
			$('.popover-content button').click(function(){
				$('.popover-content button').removeClass('tw-active');
				$(this).addClass('tw-active');
				var color = $(this).attr('data-value');
				if(color==1){
					setDrawType(1,255,0,0,3);
				}else{
					setDrawType(1,0,0,0,3);
				}
			})	
			
			function setColor() {
			 	var color = $('.popover-content button.tw-active').attr('data-value');
				if(color==1){	
					setDrawType(1,255,0,0,3);
				}else{	
					setDrawType(1,0,0,0,3);
				}
			}
			
			</script>
			<script>
			$('.bdxlid').mouseenter(function(){
				$(this).find('.bdxlcon').slideDown(100);
			});
			$('.bdxlid').mouseleave(function(){
				$(this).find('.bdxlcon').slideUp(100);
			});
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
				
				document.getElementById("frame1").contentWindow.ymChange(value);
				document.getElementById(("ahref"+yys)).className="";
				document.getElementById(("ahref"+value)).className="hot";
				yys=value;
				if(yys != 1){
					//$('.JS_hidden').css("display","");
				}else{
					if('${isFlexibleForm}' == '1'){
						//$('.JS_hidden').css("display","none");
					}
				}
			}
			
			function getScrollTop(){
			    var conDiv = $(".bl_nav_zj"),
			        scrollTo= $(".hot");
			
			        //$(conDiv).animate({scrollTop: $(scrollTo).offset().top - $("#mliframe").offset().top + $(conDiv).scrollTop()});
			}
			
			//关闭灰色JS遮罩层和操作窗口
			function closeCover() {
				$("#fullbg").css("display", "none");
				$("#dialog").css("display", "none");
			}
			
			
			
			//console.error("${ctx}/table_getWfps.do?instanceId=${instanceId}&workFlowId=${workFlowId}&t=")
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
				
				//以上必须有
			</script>
			<script type="text/javascript">
				//套打模板,调用js
				function beforePrint(nodeId, workFlowId){
					$.ajax({//先判断 节点是否包含套打模板
						url : '${ctx}/template_nodeIsHaveTemplate.do?nodeId=${nodeId}&d='+ new Date(),
						type : 'POST',
						cache : false,
						async : false,
						error : function() {
							alert('AJAX调用错误(table_onlySave.do)');
							return false;
						},
						success : function(msg) {
							if (msg == '1') {//节点包含套打模板
								var returnvalue = window.showModalDialog(
												"${ctx}/template_getTemplateForPrint.do?nodeId=${nodeId}&d="+ new Date(),
												window,'dialogWidth: 550px;dialogHeight: 200px; status: no; scrollbars: yes; Resizable: no; help: no;center:yes;');
								if (typeof (returnvalue) != 'undefined'&& returnvalue != '') {
									PrintNoHdr(nodeId, workFlowId, returnvalue);
								}
							} else {//节点不包含模板
								alert('没有模板，请先设置模板');
							}
						}
					});
				}
				
				//套用打印（先保存数值）
				function PrintNoHdr(nodeId, workFlowId, templateId) {
					var instanceId = '${instanceId}';
					var isHaveTitle = "havetitle";
					if (isHaveTitle != null && isHaveTitle != "") {
						$.ajax({
								url : '${ctx}/table_onlySaveOfPrint.do',
									type : 'POST',
									cache : false,
									async : false,
									error : function() {
										alert('AJAX调用错误(table_onlySaveOfPrint.do)');
										return false;
									},
									success : function(msg) {
										if (msg != '') {
											var a = window.showModalDialog("${ctx}/template_toDyJsp.do?nodeId="+ nodeId+ "&templateId="+ templateId+"&instanceId="+instanceId,
													window,'dialogWidth: 800px;dialogHeight: 600px; status: no; scrollbars: no; Resizable: no; help: no');
										} 
									}
								});
					} else {
						alert("流程标题所对应的存储字段不能为空!");
					}
				}
			</script>
			<script type="text/javascript">
			//查找相关办件
			function getRelatedDoFile(instanceId){
				window.showModalDialog('${ctx}/table_getRelatedDoFile.do?instanceId='+instanceId+'&t='+new Date(),null,"dialogTop=200;dialogLeft=400;dialogWidth=600px;dialogHeight=360px;help:no;status:no");
			}
			</script>
			<script type="text/javascript">
			document.onkeydown = check;
			function check(e) {
			    var code;
			    if (!e) var e = window.event;
			    if (e.keyCode) code = e.keyCode;
			    else if (e.which) code = e.which;
			    if (((event.keyCode == 8) &&                                                    //BackSpace 
			         ((event.srcElement.type != "text" && 
			         event.srcElement.type != "textarea" && 
			         event.srcElement.type != "password") || 
			         event.srcElement.readOnly == true)) || 
			        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR 
			        (event.keyCode == 116) ) {                                                   //F5 
			        event.keyCode = 0; 
			        event.returnValue = false; 
			    }
				return true;
			}
			</script>
			<script type="text/javascript">
				function choiceCondition(nodeId){
					var obj = document.getElementById("frame1").contentWindow;
					var o = '${isbt}';
					if(o){
						var error = obj.isCheckBt(o.split(";"));
						if(error!=""){
							alert(error);
							return;
						}
					}
					//获取workflowId, 获取页面表单中值
					var workFlowId = obj.document.getElementById("workFlowId").value;
					var instanceId = obj.document.getElementById("instanceId").value;
					//获取页面填写的值
					var tagValue = getProValue();
					var pageValue =tagValue;
					var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
					formjson = trueJSON.formjson;
					if(formjson != ''){
						pageValue = mergeJson(tagValue,formjson);
					}
					if(document.getElementById("flowNode")){
						document.getElementById("flowNode").disabled='disabled';
					}
					var params = pageValue;
					// from  url 
					params['workflowId'] = workFlowId;
					params['instanceId'] = instanceId;
					params['nodeId'] = nodeId;
					//ajax异步调整数据
					$.ajax({   
						url : '${ctx}/table_choiceCondition.do',
						type : 'POST',   
						cache : false,
						async : false,
						data : params,
						error : function() {  
							alert('AJAX调用错误(table_choiceCondition.do)');
						},
						success : function(result) { 
							if(result!=null && result!=''){
								var vals = result.split(",");
								var nodeId = vals[0];
								var child_merge =  vals[1];
								routeType(nodeId,'node','','',child_merge);
							}else{
								alert("请重新添加页面表单值！");
								document.getElementById("flowNode").disabled='';
								return false;
							}
						}
					});
				}
			</script>
			<script>
			setTimeout(function(){
				supmax()
			},500)
			function supmax(){
			
				var bH = $(window).height();
				$('.dh,.bl_nav').hide();
				$('.supmin').show();
			    if(parent.maxOrNotToMax) {parent.maxOrNotToMax();}
			}
			
			function supmin(){
				var bH = $(window).height();
				$('.dh,.bl_nav,supmax').show();
				$('.supmin').hide();
			    parent.$('.nav-tabs,.layui-layer-setwin').show();
			    parent.maxOrNotToMin();
			}
			
			var curtHeight = 1448; 
			var curtWidth = 1024;
			//放大或者缩小页面
			function enlargeOrNarrowPage(type){
				var percent = 0.9;
				var iframeHeight = document.getElementById("frame1").style.height;
				iframeHeight = iframeHeight.replace('px','');
				if('enlarge' == type){
					curtHeight = curtHeight/percent;
					curtWidth = curtWidth/percent;
					if(curtHeight > 1448){
						curtHeight = 1448;
					}
					if(curtWidth > 1024){
						curtWidth = 1024;
					}
				}else if('narrow' == type){
					curtHeight = curtHeight*percent;
					curtWidth = curtWidth*percent;
					if(curtHeight < iframeHeight){
						curtHeight = iframeHeight;
						curtWidth = (1024/1448)*iframeHeight;
					}
				}else if('enlargeOneTime' == type){
					curtHeight = 1448; 
					curtWidth = 1024;
				}else if('narrowOneTime' == type){
					curtHeight = iframeHeight;
					curtWidth = (1024/1448)*iframeHeight;
				}
				document.getElementById("frame1").contentWindow.setPageSize(Math.round(curtHeight), Math.round(curtWidth));
			}
			
			
			/**
			 * 补发操作
			 */
			 
			function reissue(){
				var obj = document.getElementById("frame1").contentWindow;
				var trueJSON = obj.getPageData();
				var json = JSON2.stringify(trueJSON.pdfjson);
				if(json == null ||json == "null" || json == 'null'){
					alert("表单未加载完成");
					return ;
				}
				$.ajax({   
					url : 'table_getWfLineOfType.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_getWfLineOfType.do)');
					},
					data : {
						'nextNodeId':'${nodeId}'
					},    
					success : function(nodeInfo) { 
						var msg = nodeInfo.split(",")[0];
						var exchange = nodeInfo.split(",")[1];   //给发送下一步传所点击的按钮的属性值--下一步的节点值
						var url4UserChose='${curl}/ztree_showDepartmentTree.do?siteId=${siteId}&exchange='+exchange+'&click=true&nodeId=${nodeId}&send=${send}&routType='+msg;
						var url = url4UserChose;
						var WinWidth = 1000;
						var WinHeight = 620;
						SetIsOpenDlg(1);
						if(top.window && top.window.process && top.window.process.type){
							console.info("封装打开方式");
						    var ipc = top.window.nodeRequire('ipc');
						    var remote = top.window.nodeRequire('remote');
						    var browserwindow = remote.require('browser-window');
				            var winProps = {};
			                winProps.width = 1000;
			                winProps.height = 620;
				            winProps['web-preferences'] = {'plugins':true};
			                var focusedId = browserwindow.getFocusedWindow().id;
			                treeWin = new browserwindow(winProps);
							//console.info(focusedId);
					        treeWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
						    //win.openDevTools();
							treeWin.on('closed',function(){
								treeWin = null;
							    SetIsOpenDlg(0);
							});				    
						    ipc.on('message-departmentTree',function(args){
								if(treeWin){
									SetIsOpenDlg(0);
									treeWin.close();
									treeWin = null;
									var ret = args;
									if(ret){
										var params ={};
										params['name']=ret;
										params['type'] = 0;
										$.ajax({   
											url : 'table_getUsersByIds.do',
											type : 'POST',   
											cache : false,
											async : false,
											error : function() {  
												//alert('AJAX调用错误(table_getUsersByIds.do)');
											},
											data : params,  
											success : function(data) {  
												if(confirm(data+"？")){
													doReissue(ret);
												}
											}
										});
									}
								}
						    });
						}else{
							console.info("window.open普通打开方式");
							treeWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
						    var loop = setInterval(function(){
							    if(treeWin.closed){
							    	SetIsOpenDlg(0);
									//console.info(window.returnValue);
								    clearInterval(loop);
								    //---------------------------
								    var ret = window.returnValue;
									if(ret){
										var params ={};
										params['name']=ret;
										params['type'] = 0;
										$.ajax({   
											url : 'table_getUsersByIds.do',
											type : 'POST',   
											cache : false,
											async : false,
											error : function() {  
												//alert('AJAX调用错误(table_getUsersByIds.do)');
											},
											data : params,
											success : function(data) {
												if(confirm(data+"？")){
													doReissue(ret);
												}
											}
										});
									}
									
							    }
						    },500);
						}
					}
				});
			}
			
			
			function doReissue(userId){
				$.ajax({   
					url : 'tableExtend_doSendAgain.do',
					type : 'POST',
					cache : false,
					async : false,
					error : function() {  
					},
					data : {
						'processId':'${processId}',
						'userIds':userId
					},    
					success : function(msg) { 
						if(msg!=null && msg == 'success'){
							operateForm(1,'');
						}
					}
				});
			}
			
			function toBack(){
				var obj = document.getElementById("frame1").contentWindow;
				var o = '${isbt}';
				if(o){
					var error = obj.isCheckBt(o.split(";"));
					if(error!=""){
						alert(error);
						return;
					}
				}
				var url = '${curl}/table_toSendBackPage.do?instanceId=${instanceId}&processId=${processId}';
				var WinWidth = 450;
				var WinHeight = 300;
				SetIsOpenDlg(1);
				if(top.window && top.window.process && top.window.process.type){
					console.info("封装打开方式");
					var ipc = top.window.nodeRequire('ipc');
					var remote = top.window.nodeRequire('remote');
					var browserwindow = remote.require('browser-window');
					var winProps = {};
					winProps.width = 450;
					winProps.height = 300;
					winProps['web-preferences'] = {'plugins':true};
					var focusedId = browserwindow.getFocusedWindow().id;
					treeWin = new browserwindow(winProps);
					//console.info(focusedId);
					treeWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
					//win.openDevTools();
					treeWin.on('closed',function(){
						treeWin = null;
						SetIsOpenDlg(0);
					});				    
					ipc.on('message-departmentTree',function(args){
						if(treeWin){
							SetIsOpenDlg(0);
							treeWin.close();
							treeWin = null;
							var ret = args;
							if(ret){
								doBack(ret);
							}
						}
					});
				}else{
					console.info("window.open普通打开方式");
					treeWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
					var loop = setInterval(function(){
						if(treeWin.closed){
							SetIsOpenDlg(0);
							//console.info(window.returnValue);
							clearInterval(loop);
							var ret = window.returnValue;
							if(ret){
								doBack(ret);
							}
						}
					},500);
				}
			}
			
			function doBack(ret){
				var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
				var json = JSON2.stringify(trueJSON.pdfjson);
				var returnVal = ret.split(";");
				var nodeId = returnVal[0];
				var sendMsg = returnVal[1];
				var instanceId = '${instanceId}';
				var processId = '${processId}';
				var routeType = '${wfNode.wfn_route_type}';
				$.ajax({
					url : '${curl}/table_doSendBack.do',
					type : 'POST',   
					cache : false,
					async : false,
					data :{
						'instanceId':instanceId,
						'processId':processId,
						'routeType':routeType,
						'nodeId':nodeId,
						'sendMsg':sendMsg,
						'json':json
					},
					success : function(msg) {
						parent.closeTabsInLayer();
					}
				});
			}
			
			function OnRotate(status){
				document.getElementById("frame1").contentWindow.OnRotate(status);
			}
			
			function changePage4ml(i){
				changePage(i);
                var container = $('#bl_nav_zj'),
                scrollTo = $('.hot');
                container.scrollTop(scrollTo.offset().top - container.offset().top + container.scrollTop());				
			}
			
			function SetIsOpenDlg(status) {
				try{
					document.getElementById("frame1").contentWindow.SetIsOpenDlg(status);
				}catch (e) {
				}
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
							//alert("已收藏！");
							deleteCollection();
							$('#collect').html("收藏");
							$('#collected').html("收藏");
							return false;
						}else if(msg == '2'){
							alert("收藏成功！");
							$('#collect').html("取消收藏");
							$('#collected').html("取消收藏");
						}else if(msg == '3'){
							alert("收藏失败");
						}
					}
				});
			}
			
			//取消收藏
			function deleteCollection(){
				var dofileId='${dofileId}';
				$.ajax({  
					url : 'table_deletefavourite.do',
					type : 'POST',   
					cache : false,
					async : false,
					data : {
						dofileId : dofileId
					},
					success : function(msg) {
						if(msg == '1'){
							alert("已取消收藏！");
						}else if(msg == '2'){
							
						}
					}
				});
			}
			
			//下载
			var isDownload = false;
			function downloadPdf(processId){
				if(isDownload){
					//return;
				}
				isDownload = true;
				var obj = document.getElementById("download_iframe");
				if(obj){
					$.ajax({
						url : '${ctx}/table_downloadPdf.do',
						type : 'POST',   
						cache : false,
						async : false,
						data :{
							'processId':processId
						},
						success : function(msg) {
							if(msg&&(msg.indexOf("table_downloadError.do")!=-1)){
								alert('附件可能包含加密文件，无法下载！');
							}else{
								obj.src='${ctx}/table_downloadPdf.do?processId='+processId;
							}
						}
					});
					//obj.src='${ctx}/table_downloadPdf.do?processId='+processId;
					/*$.ajax({
						url : 'table_getPdfFjCount.do',
						type : 'POST',   
						cache : false,
						async : false,
						data : {
							"processId":processId
						},
						error : function() {  
							alert('AJAX调用错误(table_getPdfFjCount.do)');
						},
						success : function(data) {
							if(data=="0"){
								alert("暂无下载内容");
								return;
							}else{
								obj.src='${ctx}/table_downloadPdfOnlyFj.do?processId='+processId;
							}
						}
					});*/
				}
			}
			
			function sendToChat(processId){
				var canSend = true;
				$.ajax({
					url : '${ctx}/table_downloadPdfOnlyFj.do',
					type : 'POST',   
					cache : false,
					async : false,
					data :{
						'processId':processId
					},
					success : function(msg) {
						if(msg&&(msg.indexOf("table_downloadError.do")!=-1)){
							canSend = false;
						}
					}
				});
				if(!canSend){
					alert('附件可能包含加密文件，无法转发！');
					return;
				}
				
				SetIsOpenDlg(1);
				var url4UserChose='${curl}/ztree_showDepartmentTree.do?siteId=${siteId}&isZf=1&status=all&routType=4&t='+new Date();
				var url = url4UserChose;
				var WinWidth = 1000;
				var WinHeight = 620;
				//var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				if(top.window && top.window.process && top.window.process.type){
					console.info("封装打开方式");
				    var ipc = top.window.nodeRequire('ipc');
				    var remote = top.window.nodeRequire('remote');
				    var browserwindow = remote.require('browser-window');
		            var winProps = {};
		            winProps.width = 1000;
		            winProps.height = 620;
		            winProps['web-preferences'] = {'plugins':true};
		            var focusedId = browserwindow.getFocusedWindow().id;
		            treeWin = new browserwindow(winProps);
					//console.info(focusedId);
			        treeWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
				    //win.openDevTools();
					treeWin.on('closed',function(){
						treeWin = null;
					    SetIsOpenDlg(0);
					});

				    ipc.on('message-departmentTree',function(args){
						if(treeWin){
							SetIsOpenDlg(0);
							treeWin.close();
							treeWin = null;
							var ret = args;
							if(ret){
								var userId = ret;
								var commentJson = $("#commentJson").val();
								//ajax以后获取数据,转换文件
								$.ajax({   
									url : '${ctx}/table_changeTrueToPdf.do',
									type : 'POST',   
									cache : false,
									async : false,
									error : function() {  
										//alert('AJAX调用错误(table_changeTrueToPdf.do)');
									},
									data : {
										'processId':processId, 'commentJson':commentJson, 'xto_names':userId
									},
									success : function(data) {  
										
									}
								});
							}
						}
				    });
				}else{
					console.info("window.open普通打开方式");
					treeWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				    var loop = setInterval(function(){
					    if(treeWin.closed){
							//console.info(window.returnValue);
						    clearInterval(loop);
						    //---------------------------
						    var ret = window.returnValue;
						    SetIsOpenDlg(0);
							if(ret){
								var userId = ret;
								var commentJson = $("#commentJson").val();
								//ajax以后获取数据,转换文件
								$.ajax({   
									url : '${ctx}/table_changeTrueToPdf.do',
									type : 'POST',   
									cache : false,
									async : false,
									error : function() {  
									},
									data : {
										'processId':processId, 'commentJson':commentJson, 'xto_names':userId
									},
									success : function(data) {  
										
									}
								});
							}
							
					    }
				    },500);
				}
			}
			
			//获取人员信息列表
			function sendToGroup(processId){
				SetIsOpenDlg(1);
				var url = '${curl}/table_getGroupDataList.do?t='+new Date();
				var WinWidth = 800;
				var WinHeight = 600;
				//window.open('${ctx}/table_getGroupDataList.do?t='+new Date(),'newwindow','height=500, width=800,top='+(screen.height-600)/2+',left='+(screen.width-1000)/2+',toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no,status=no');
				if(top.window && top.window.process && top.window.process.type){
					console.info("封装打开方式");
				    var ipc = top.window.nodeRequire('ipc');
				    var remote = top.window.nodeRequire('remote');
				    var browserwindow = remote.require('browser-window');
		            var winProps = {};
		            winProps.width = '800';
		            winProps.height = '600';
		            winProps['web-preferences'] = {'plugins':true};
		            var focusedId = browserwindow.getFocusedWindow().id;
		            treeWin = new browserwindow(winProps);
					//console.info(focusedId);
			        treeWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
				    //win.openDevTools();
					treeWin.on('closed',function(){
						treeWin = null;
					    SetIsOpenDlg(0);
					});				    
				    ipc.on('message-departmentTree',function(args){
						if(treeWin){
							SetIsOpenDlg(0);
							treeWin.close();
							treeWin = null;
							var ret = args;
							var obj = document.getElementById("frame1").contentWindow;
							if(ret == "undefined" || typeof(ret) == "undefined"){
							 }else{
								 if(obj.document.getElementById("jzk1")){
									obj.zzfs();
								 }
								 var data = ret.split(',');
								 var groupId = data[0];
								 var groupName = data[1];
								 var commentJson = $("#commentJson").val();
							     //ajax以后获取数据,转换文件
								 $.ajax({   
									url : '${ctx}/table_changeTrueToPdf.do',
									type : 'POST',   
									cache : false,
									async : false,
									error : function() {  
										alert('AJAX调用错误(table_changeTrueToPdf.do)');
										if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}
									},
									data : {
										'processId':processId, 'commentJson':commentJson, 'groupId':groupId,'groupName':groupName
									},
									success : function(data) {
										alert("发送成功！");
										if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}
									}
								});
							 }
						}
				    });
				}else{
					console.info("window.open普通打开方式");
				    var treeWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				    var loop = setInterval(function(){
					    if(treeWin.closed){
						    clearInterval(loop);
						    //---------------------------
						    var ret = window.returnValue;
						    SetIsOpenDlg(0);
							var obj = document.getElementById("frame1").contentWindow;
							if(ret == "undefined" || typeof(ret) == "undefined"){
							 }else{
								 if(obj.document.getElementById("jzk1")){
									obj.zzfs();
								 }
								 var data = ret.split(',');
								 var groupId = data[0];
								 var groupName = data[1];
								 var commentJson = $("#commentJson").val();
							     //ajax以后获取数据,转换文件
								 $.ajax({   
									url : '${ctx}/table_changeTrueToPdf.do',
									type : 'POST',   
									cache : false,
									async : false,
									error : function() {  
										alert('AJAX调用错误(table_changeTrueToPdf.do)');
										if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}
									},
									data : {
										'processId':processId, 'commentJson':commentJson, 'groupId':groupId,'groupName':groupName
									},
									success : function(data) {
										alert("发送成功！");
										if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}
									}
								});
							 }
							
					    }
				    },500);
				}
				
			}
			
			function SearchText(status) {
				try{
					document.getElementById("frame1").contentWindow.SearchText();
				}catch (e) {
				}
			}
			
			function toTopMouseOn(){
				$('#toTopOut').hide();
				$('#toTopOn').show();
			}
			function toTopMouseOut(){
				$('#toTopOut').show();
				$('#toTopOn').hide();
			}
		</script>
		<script>
			(function ($) {
			 $.extend({
			  Request: function (m) {
			   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
			   return sValue ? sValue[1] : sValue;
			  },
			  UrlUpdateParams: function (url, name, value) {
			   var r = url;
			   if (r != null && r != 'undefined' && r != "") {
			    value = encodeURIComponent(value);
			    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
			    var tmp = name + "=" + value;
			    if (url.match(reg) != null) {
			     r = url.replace(eval(reg), tmp);
			    }
			    else {
			     if (url.match("[\?]")) {
			      r = url + "&" + tmp;
			     } else {
			      r = url + "?" + tmp;
			     }
			    }
			   }
			   return r;
			  }
			 
			 });
			})(jQuery);
		</script>

  	</body>
</html>