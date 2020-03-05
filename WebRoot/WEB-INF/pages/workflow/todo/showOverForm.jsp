<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head> 
	<meta charset="utf-8" />
	<!--[if IE]>
	<link href="${ctx}/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
	<![endif]-->
	<!--JS -->
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>

	<%@page import="net.sf.json.JSONArray"%>
	<script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js?t=2016" type="text/javascript"></script>
	<link href="${ctx}/images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
	<Link href="${ctx}/images/common.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/images/index.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/assets-common/css/common.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/assets-common/css/floatDiv.css?t=101" type="text/css" rel="stylesheet" />
	<style>
		.tw-step {
		margin-left: 10px;
		margin-top:0;
		height:29px;
		line-height:29px;
	}
	.tw-btn-sm {
		border-radius: 4px;
		font-size: 16px;
		padding: 0 5px;
		height:29px;
		line-height:29px;
		display:inline-block;
		text-align:center;
		border:0;
		vertical-align:top;
	}
	.pull-left, .fl{
		width: auto;
		margin-right: 0px;
		margin-top: 0;
		height: 29px;
		border-radius: 4px;
		font-size: 16px;
	}
	.frame1{
		position:fixed !important;
		left:0;
	}
	</style>
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
		function loadCEB(path){
			document.getElementById("frame1").contentWindow.loadCEB(path);
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
<body onload="init();" id="body">
	<textarea style="display:none;" id="commentJson" name="commentJson">${commentJson}</textarea>
	<textarea style="display:none;" id="employeeSort" name="employeeSort">${employeeSort}</textarea>
	<input type="hidden" id="isFlexibleForm" name="isFlexibleForm" value="${isFlexibleForm}" />
	<input type="hidden" id="title" name="title" value="${title}" />
	<input type="hidden" id="status" name="status" value="${status}" />
	<textarea style="display:none;" id="formPageJson" name="formPageJson">${formPageJson}</textarea>
	<textarea style="display:none;" id="pdfPath" name="pdfPath">${pdfPath}</textarea>
	<textarea style="display:none;" id="allPdfPath" name="allPdfPath">${allPdfPath}</textarea>
	<textarea style="display:none;" id="toUserName" name="toUserName">${toUserName}</textarea>
	<input type="hidden" id="imageCount" name="imageCount" value="${imageCount}">
	<input type="hidden" id="fontSize" name="fontSize" value="${fontSize}">
	<input type="hidden" id="verticalSpacing" name="verticalSpacing" value="${verticalSpacing}">
	<input type="hidden" id="dateFormat" name="dateFormat" value="${dateFormat}">
	<input type="hidden" id="font" name="font" value="${font}">
	<div class="warp1">
		<div class="dh">
			<c:if test="${isds!='1'}">
				<div class="bl_tab lc fl ml30">
					<a style="cursor:pointer"> <span >流程图</span></a>
				</div>
			</c:if>
			<div class="bl_tab bl fl">
				<a style="cursor:pointer"><span >办理</span></a>
			</div>
		</div>
		<div class="content">
    		 
			<%-- <!-- 左右跳转图片显示 start -->
			<iframe height="60px" id="mliframe1" width="40px"   style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:50px;top:350px;z-index:9;" src="${ctx}/mlzg.jsp"></iframe>
			<div id="gzl_pageLeftBox"><a id="leftPage" class="b_disable" onclick="changePageToLeft()" style="cursor:pointer;"></a></div>
			<div id="gzl_pageRightBox"><a id="rightPage" onclick="changePageToRight()" style="cursor:pointer;"></a></div>
			<!-- 左右跳转图片显示    end -->    --%>
			<div id="toTop" style="position: absolute;width: 33px;height: 58px;z-index: 2;right: 40px;top: 500px;">
		 		<img src="${ctx}/assets-common/img/toTop.png" onclick="changePage(1)" />
		 	</div>
    		<iframe height="203px" id="mliframe" width="99px"  frameborder=no  style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:10px;top:72px;z-index:10;" src="${ctx}/table_toCatalog.do?allInstanceId=${allInstanceId}&pages=${pages}"></iframe>	 
			<div class="bl_nav" >
				
				<ul class="clearfix">
					<c:if test="${pages != null && pages != ''}">
						<li id="li_dir" style="padding-bottom:  6px;">
				      		<a  href="#" class="contents fl ml8">批阅页</a>
				      		<div class="bl_n_nav" style="z-index:11;top:32px;display:none;width: 99px"  >
					      		<div class="bl_nav_top" style="z-index:11;" ></div>
					      	</div>
					 	</li>
				 	</c:if>
			      	<li>
			    	  	<a href="#"  class="accessory ml5" onclick="changeClass()" >附件<em id="fjCount"></em></a>
			      	</li>
				</ul>
				<div class="fl xx" style="width: auto;margin-right:20px;">
					<!-- <div class="fl zc">
		     			<span onclick="javascript:handRound();" style="cursor: pointer;">传阅</span>
		     		</div>
		     		<span class="sx fl">|</span> -->
					<c:choose>
						<c:when test="${replyId!=null && replyId!='' }">
				    	 	<div class="fl zc">
								<span onclick="javascript:updateReplay('${replyId}');" style="cursor: pointer;">完成</span>
					     	</div>
					     	<span class="sx fl">|</span>
					      	<div class="fl zc">
					     		<span onclick="javascript:sendFile();" style="cursor: pointer;">发文</span>
					     	</div>
				    	</c:when>
				    	<c:otherwise>
				    		<c:if test="${disfavourite==null || disfavourite=='' }">
								<!-- <a class="tw-btn-red tw-btn-sm">
					                <i class="tw-icon-arrow-left"></i> 上一步
					            </a>
					            <a class="tw-btn-secondary tw-btn-sm">
					                                                      下一步  <i class="tw-icon-arrow-right"></i>
					            </a> -->
					    		<button class="tw-btn-secondary tw-btn-sm" onclick="downloadPdf('${processId}');"><i class="tw-icon-download"></i> 下载</button>
					    		<button class="tw-btn-secondary tw-btn-sm" onclick="PrintPDF(1);"><i class="tw-icon-print"></i> 打印</button>
								<button class="tw-btn-secondary tw-btn-sm" onclick="PrintCurPage();"><i class="tw-icon-print"></i> 打印当前页</button>
				     			<!-- 待阅模式下不支持收藏 -->
				     			<c:if test="${isps != '1'}">
					     			<c:choose>
					     				<c:when test="${favourite == '1'}">
											<span class="tw-btn-secondary tw-btn-sm"><i class="tw-icon-star-o"></i> 已收藏</span>
					     				</c:when>
					     				<c:otherwise>
											<span class="tw-btn-secondary tw-btn-sm" onclick="addCollection();"><i class="tw-icon-star"></i> 收藏</span>
					     				</c:otherwise>
									</c:choose>
									<c:if test="${null == status || '' == status || status != '6'}">
										<span class="tw-btn-secondary tw-btn-sm" onclick="sendToChat('${processId}');"><i class="tw-icon-send"></i> 转发</span>
										<span class="tw-btn-secondary tw-btn-sm" onclick="sendToGroup('${processId}');"><i class="tw-icon-send"></i> 转发组</span>
									</c:if>
								</c:if>					
							</c:if>
						</c:otherwise>
				    </c:choose>
				    <c:if test="${canReissue != null && canReissue eq '1' }">
				    	<span style="background-color: red;border-color: red;" class="tw-btn-secondary tw-btn-sm" onclick="reissue();"><i class="tw-icon-send"></i> 补发</span>
				    </c:if>
				</div>
			 	<span class="spanColor fl">|</span>     
				<div class="fl tw-tools" id="flexibleForm" style="margin-left:20px;">
					<!-- <button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('enlarge');"><i class="tw-icon-search-plus"></i>放大</button>
					<button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('narrow');"><i class="tw-icon-search-plus"></i>缩小</button>
					<button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('enlargeOneTime');"><i class="tw-icon-expand"></i>最大化</button>
					<button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('narrowOneTime');"><i class="tw-icon-compress"></i>最小化</button>        -->
				</div>	 
			</div>
			<div style=" background-color: rgb(152, 152, 152);">
		        	<div  class="bl_n_nav bl_a_nav fouce" style="z-index: 2;position: relative;max-height: 130px;overflow: auto;width: 800px;margin:auto;top:0px;left: -75px;display:none;" id="fjml">
		          		<div class="bl_nav_fj" >
							<ul class="file-list">
								<c:if test="${itemType eq '0' }">
									<li class="item">
										<div class="file-attr">
								      		<div id="attzwshow" style="min-height:25px;"></div>
								      	</div>
								 		<div class="file-name">
								 			<trueway:att onlineEditAble="false" id='${docguid}zw' docguid='${docguid}attzw' showId='attzwshow'  nodeId="${nodeId}"  ismain="true" downloadAble="true" isZw="true" uploadAble="false" deleteAble="false" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
								      	</div>
									</li>
								</c:if>
								<li class="item">
									<div class="file-attr">
						         		<div id="fjshow" style="min-height:25px;"></div>
						         	</div>
						         	<div class="file-name">
							 			<trueway:att onlineEditAble="false" id='${docguid}fj' docguid='${docguid}fj' showId='fjshow'  nodeId="${nodeId}"  ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss" isZw="fj"/>
						        	</div> 
								</li>
							</ul>
		                </div>
		            	<div class="bl_nav_bottom"></div>
		        	</div>
		 	</div>		
			<div>
				<div style="overflow: hidden;">
					<iframe height="100%" id="lcbox" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:97%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getWfps.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
					<c:choose>
						<c:when test="${(isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' )}">
							<!-- <iframe height="100%" id = "frame2" class="frame2" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:96%; " src="${ctx}/business_getHandRoundShips.do?instanceId=${instanceId}"></iframe>
							 -->
							<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;    background-color: #989898;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&userId=${userId}&loginname=${loginname}&isWriteNewValue=false&allInstanceId=${allInstanceId}&atts=''&isOver=${isOver}"></iframe>
							<%-- <iframe id="frame1"  class="frame1"  frameborder="auto" marginheight="0" marginwidth="0" border="0" style="width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_frameForm.do?formLocation=${formLocation}&workFlowId=${workFlowId}&formId=${formId}&processId=${processId}&instanceId=${instanceId}&att_comment_id=${att_comment_id}&value=${value}&nodeId=${nodeId}&isFirst=${isFirst}&deptId=${webId}&userId=${userId}"></iframe> --%>
						</c:when>
						<c:otherwise>
					 		<!-- 
					 		<iframe height="100%" id = "frame2" class="frame2" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:96%; " src="${ctx}/business_getHandRoundShips.do?instanceId=${instanceId}"></iframe>
					 		 -->
					 		<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;    background-color: #989898;" src="${ctx}/pdfocx/1.jsp?usbkey=${usbkey}&sealurl=${sealurl}&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&userId=${userId}&loginname=${loginname}&isWriteNewValue=${isWriteNewValue}&newInstanceId=${newInstanceId}&newProcessId=${newProcessId}&allInstanceId=${allInstanceId}&atts=&isOver=${isOver}"></iframe>
				 		</c:otherwise>
					</c:choose>
				</div>
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
	<div class="quick_links_wrap">
	   <ul class="quick_links" class="quick_links">        
			<li id="qprxx" onclick="showHandWrite(1);" style="border-bottom:none;"><a href="#"  title="签批人信息"><i class="icon-qprxx"></i><span>签批人信息</span></a></li>
			<li onclick="OnRotate('1');" id="turnleft"><a href="#"  title="左旋转"><i class="icon-reply"></i><span>左旋转</span></a></li>  
		 	<li onclick="OnRotate('0');" id="turnright"><a href="#"  title="右旋转"><i class="icon-forward"></i><span>右旋转</span></a></li>	     
		</ul>
	</div>
</body>
<script src="images/jquery.min.js" type="text/javascript"></script>
<script src="images/common.js" type="text/javascript"></script>
<script src="images/jquery.tab.js" type="text/javascript"></script>
<script type="text/javascript">
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
</script>
<script type="text/javascript">
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
		    var win = new browserwindow(winProps);
			//console.info(focusedId);
	        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
		    //win.openDevTools();
			win.on('closed',function(){
			    win = null;
			    SetIsOpenDlg(0);
			});				    
		    ipc.on('message-departmentTree',function(args){
				if(win){
					SetIsOpenDlg(0);
	                win.close();
					win = null;
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
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
					//console.info(window.returnValue);
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
</script>
<script type="text/javascript">
	function sendToChat(processId){
		SetIsOpenDlg(1);
		var url4UserChose='${curl}/ztree_showDepartmentTree.do?isZf=1&status=all&routType=4&t='+new Date();
		var url = url4UserChose;
		var WinWidth = 800;
		var WinHeight = 600;
		//var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
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
		    var win = new browserwindow(winProps);
			//console.info(focusedId);
	        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
		    //win.openDevTools();
			win.on('closed',function(){
			    win = null;
			    SetIsOpenDlg(0);
			});				    
		    ipc.on('message-departmentTree',function(args){
				if(win){
					SetIsOpenDlg(0);
	                win.close();
					win = null;
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
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
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
		    },500);
		}
		/* window.open('${ctx}/table_userGroup.do?click=true&nodeId=${nodeId}&send=${send}&isTreeAll=1&routType=4&t='+new Date(),'newwindow','height=500, width=800,top='+(screen.height-600)/2+',left='+(screen.width-1000)/2+',toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no,status=no');
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
		} */
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
	        document.getElementById("frame1").contentWindow.document.getElementById("mianDiv").style.height="30px"; 
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
	        if(imageCount>0){
	            $('#mliframe').show();
	        }	
	        mlstatus ++;
	    }
	    
	    function out(event) {        
	        $(this).removeClass("fouce");
	        $('.bl_n_nav', this).hide();
	        $('#mliframe').hide();
	        mlstatus ++;
		}
	    
		var cd =  document.body.clientWidth/2-405-123;
	   	if("${!((isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' ))}"!='true'){
		   	cd =  document.body.clientWidth/2-405-20;
	   	}
	   	/* if(document.getElementById('fjml')){
			document.getElementById('fjml').style.left=cd+"px";
	    	document.getElementById('fjml').style.top="50px";
	    } */
	    
	    if('${isds}'!='1'){
			$('.bl_tab').each(function(i){
		    	$(this).click(function(){
		    		if(i==0){
		    			out();
		    			$('.quick_links_wrap').hide();			    			
		    			$(this).addClass("bl").removeClass("lc");
	    			    $('.bl_tab').eq(1).addClass("lc").removeClass("bl");
	    			    $('.bl_tab').eq(2).addClass("lc").removeClass("bl");
		    			$('#lcbox').css("z-index","9").css('display','block');
		    			$('#lcbox').attr('src',$('#lcbox').attr('src'));		    			
		    			$('#frame1').height(0);
						document.getElementById("gzl_pageLeftBox").style.display="none";
						document.getElementById("gzl_pageRightBox").style.display="none";
		    		}else{
		    			over();
		    			$('.quick_links_wrap').show();			    			
		    			$(this).addClass("bl").removeClass("lc");
	    			    $('.bl_tab').eq(0).addClass("lc").removeClass("bl");
	    			    $('.bl_tab').eq(2).addClass("lc").removeClass("bl");
		    			$('#lcbox').css("z-index","0").css('display','none');
		    			$('#frame1').height($('.content').height()-40);
	    			    if(imageCount>1){
						    document.getElementById("gzl_pageLeftBox").style.display="";
						    document.getElementById("gzl_pageRightBox").style.display="";
	    			    }
		    		}
		    	});
		    });
	    }
		
		if(imageCount == 1){
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

	function PrintCurPage(){
		 document.getElementById("frame1").contentWindow.PrintCurPage();
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
		$('.frame1').height($('.content').height()-40);  
		$('.frame1').width(bW);
		if(imageCount<2){
		    $('#rightPage').addClass('b_disable');
		}
		if(imageCount>0){
			if(null != '${pages}' && '' != '${pages}'){
				$('#mliframe').show();
			}
		}
		
		if('${isFlexibleForm}' == '1'){
			$('#flexibleForm').css("display","none");
			$('#turnleft').css("display","none");
			$('#turnright').css("display","none");
		}
	});

	$(window).bind('resize',function(){
		var bH=$(window).height();
		var bW=$(window).width();  
		$('.content').height(bH-30);			
		//$('.frame1').height($('.content').height()-50);
		$('.frame1').height(0);
		console.log($('.bl_tab'));		
		$('.frame1').width(bW);
		$('.bl_tab').each(function(i,item){		
			console.log(i)		
			console.log($(item).hasClass('bl'));		
			if(i == 0){
				if($(item).hasClass('bl')){		
					$('.frame2').height($('.content').height()-10);		
					$('.frame2').width(bW);		
				}		
			} else if(i==1){		
				if($(item).hasClass('bl')){		
					$('.frame1').height($('.content').height()-10);		
					$('.frame1').width(bW);		
				}		
			}		
		});	
	    if($('body').width()<1340){
	    	$('.quick_links_wrap').addClass('easy_wrap');
	    }else{
	    	$('.quick_links_wrap').removeClass('easy_wrap');
	    }	
	});

	$('.quick_links li').click(function(){
		$('.quick_links li').removeClass('active');
		if( $(this).attr('id') == 'qprxx'){
			if(handWriteIsShow == 1){
				$(this).addClass('active');
			}
		}else{
			$(this).addClass('active');
		}
	});	

	if($('body').width()<1340){
	    $('.quick_links_wrap').addClass('easy_wrap');
	}else{
	    $('.quick_links_wrap').removeClass('easy_wrap');
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
		/* if(value == 1){
			document.getElementById("leftPage").className="b_disable";
			document.getElementById("rightPage").className="";
		}else if(value == imageCount){
			document.getElementById("leftPage").className="";
			document.getElementById("rightPage").className="b_disable";
		}else{
			document.getElementById("leftPage").className="";
			document.getElementById("rightPage").className="";
		} */
		document.getElementById("frame1").contentWindow.ymChange(value);
		/* document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+value)).className="hot"; */
		yys=value;
		if(yys != 1){
			$('#flexibleForm').css("display","");
			$('#turnleft').css("display","");
			$('#turnright').css("display","");
		}else{
			if('${isFlexibleForm}' == '1'){
				$('#flexibleForm').css("display","none");
				$('#turnleft').css("display","none");
				$('#turnright').css("display","none");
			}
		}
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
			if(val < imageCount){
				document.getElementById("rightPage").className="";
			}
			yys=val;
			if(yys != 1){
				$('#flexibleForm').css("display","");
				$('#turnleft').css("display","");
				$('#turnright').css("display","");
			}else{
				if('${isFlexibleForm}' == '1'){
					$('#flexibleForm').css("display","none");
					$('#turnleft').css("display","none");
					$('#turnright').css("display","none");
				}
			}		
		}else{
			if('${isFlexibleForm}' == '1'){
				$('#flexibleForm').css("display","none");
				$('#turnleft').css("display","none");
				$('#turnright').css("display","none");
			}
		}
	}
	
	function changePageToRight(){
		if(yys<imageCount){
			var val = parseInt(yys) + 1 ;
			document.getElementById("frame1").contentWindow.ymChange(val);
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
			document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
			if(val > 1){
				document.getElementById("leftPage").className="";
			}
			if(imageCount == val){
				document.getElementById("rightPage").className="b_disable";
			}
			yys=val;
			if(yys != 1){
				$('#flexibleForm').css("display","");
				$('#turnleft').css("display","");
				$('#turnright').css("display","");
			}else{
				if('${isFlexibleForm}' == '1'){
					$('#flexibleForm').css("display","none");
					$('#turnleft').css("display","none");
					$('#turnright').css("display","none");
				}
			}
		}
	}
	
	function mergeJson(a,b){
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
	
	//查找相关办件
	function getRelatedDoFile(instanceId){
		window.showModalDialog('${ctx}/table_getRelatedDoFile.do?instanceId='+instanceId+'&t='+new Date(),null,"dialogTop=200;dialogLeft=400;dialogWidth=600px;dialogHeight=360px;help:no;status:no");
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
	
	
	function supmax(){
		var bH = $(window).height();
		$('.dh,.bl_nav').hide();
		$('.supmin').show();
	    parent.$('.nav-tabs,.layui-layer-setwin').hide();
	    parent.maxOrNotToMax();
		$('#mliframe').css('top','0');
		$('.quick_links_wrap').css('top','60px');	
		len1=1;
		$('#fjml').removeClass("fouce");
		document.getElementById("fjml").style.display="none";
	    document.getElementById("frame1").contentWindow.document.getElementById("mianDiv").style.height="0px"; 
	}
	
	function supmin(){
		var bH = $(window).height();
		$('.dh,.bl_nav,supmax').show();
		$('.supmin').hide();
	    parent.$('.nav-tabs,.layui-layer-setwin').show();
	    parent.maxOrNotToMin();
		$('#mliframe').css('top','72px');
		$('.quick_links_wrap').css('top','120px');	
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
</script>
<script type="text/javascript">
	//function openPortalTab(processId,title,url){
	function openPortalTab(type){
		var processId='abc';
		var workflowid='';
		var itemid='';
		var title="督办";
		if(type=='cb'){
			processId="bcd";
			workflowid='${cbWorkflowId}';
			itemid='${cbItemId}';
			title="催办";
		}else if(type=='db'){
			processId="cde";
			workflowid='${dbWorkflowId}';
			itemid='${dbItemId}';
			title="督办";
		}
		var url="${ctx}/table_openFirstForm.do?wfType="+type+"&oldInstanceId=${instanceId}&status=${status}&workflowid="+workflowid+"&itemid="+itemid+"&no=";
		parent.topOpenLayerTabs(processId,'1200','600',title,url,'');
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
		var workFlowId = '${workFlowId}';
		noid_anid='${nextNodeid}';
		$.ajax({   
			url : 'table_getWfLineOfType.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_getWfLineOfType.do)');
			},
			data : {
				'nextNodeId':noid_anid,
				'nodeId':'${nodeId}',
				'workFlowId':workFlowId
			},    
			success : function(nodeInfo) { 
				var msg = nodeInfo.split(",")[0];
				var exchange = nodeInfo.split(",")[1];   //给发送下一步传所点击的按钮的属性值--下一步的节点值
				var url4UserChose='${curl}/ztree_showDepartmentTree.do?exchange='+exchange+'&click=true&nodeId='+noid_anid+'&send=${send}&routType='+msg;
				var url = url4UserChose;
				var WinWidth = 800;
				var WinHeight = 600;
				SetIsOpenDlg(1);
				//var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
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
				    var win = new browserwindow(winProps);
					//console.info(focusedId);
			        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
				    //win.openDevTools();
					win.on('closed',function(){
					    win = null;
					    SetIsOpenDlg(0);
					});				    
				    ipc.on('message-departmentTree',function(args){
						if(win){
							SetIsOpenDlg(0);
			                win.close();
							win = null;
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
				    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				    var loop = setInterval(function(){
					    if(winObj.closed){
							//console.info(window.returnValue);
						    clearInterval(loop);
						    //---------------------------
						    var ret = window.returnValue;
						    SetIsOpenDlg(0);
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
			url : 'tableExtend_doReissue.do',
			type : 'POST',
			cache : false,
			async : false,
			error : function() {  
				//alert('AJAX调用错误(tableExtend_doReissue.do)');
			},
			data : {
				'processId':'${processId}',
				'userId':userId
			},    
			success : function(msg) { 
				if(msg!=null && msg == 'success'){
					alert('补发成功');
					parent.closeTabsInLayer();
				}
			}
		});
	}
	//下载
	var isDownload = false;
	function downloadPdf(processId){
		if(isDownload){
			return;
		}
		isDownload = true;
		/* var canDownload = '${showCyBtn}';
		if(canDownload == 'false'){
			alert("尚未发送或保存，不能下载！");
			return;
		} */
		var obj = document.getElementById("download_iframe");
		if(obj){
			obj.src='${ctx}/table_downloadPdf.do?processId='+processId;
		}
	}
	
	/**
	 * 传阅
	 */
	function handRound(){
		var url4UserChose='${curl}/ztree_showDepartmentTree.do?status=all&t='+new Date();
		var url = url4UserChose;
		var WinWidth = 800;
		var WinHeight = 600;
		//var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		var obj = document.getElementById("frame1").contentWindow;
		SetIsOpenDlg(1);
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
		    var win = new browserwindow(winProps);
			//console.info(focusedId);
	        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
		    //win.openDevTools();
			win.on('closed',function(){
			    win = null;
			    SetIsOpenDlg(0);
			});				    
		    ipc.on('message-departmentTree',function(args){
				if(win){
					SetIsOpenDlg(0);
	                win.close();
					win = null;
					var ret = '';
					var canSendMsg = '';
					if(args.split("#").length>1){
						canSendMsg = args.split("#")[1];
						ret = args.split("#")[0];
					}else{
						ret = args;
					}
					 if(ret){
							document.getElementById('toName').value = ret;
							var params ={};
							params['name']=document.getElementById('toName').value;
							params['type'] = 0;
							$.ajax({   
								url : 'table_getUsersByIds.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_getUsersByIds.do)');
								},
								data : params,  
								success : function(data) {  
									if(confirm(data+"？")){
										$.ajax({   
											url : 'business_addHandRoundShip.do',
											type : 'POST',   
											cache : false,
											async : false,
											error : function() {  
												alert('AJAX调用错误(business_addHandRoundShip.do)');
											},
											data : {
												userIds:ret,
												instanceId:'${instanceId}'
											},  
											success : function(data) { 
												var obj = eval('('+data+')');
												if(obj.result=='success'){
													alert('发送成功。');
												}
											}
										});
									}
								}
							});
						}else{
							if(document.getElementById(noid_anid)){
								document.getElementById(noid_anid).disabled='';
							}
						}
				}
		    });
		}else{
			console.info("window.open普通打开方式");
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
					//console.info(window.returnValue);
				    clearInterval(loop);
				    //---------------------------
				    var rets = window.returnValue;
					var ret = '';
					var canSendMsg = '';
					SetIsOpenDlg(0);
					if(rets.split("#").length>1){
						canSendMsg = rets.split("#")[1];
						ret = rets.split("#")[0];
					}else{
						ret = rets;
					}
					 if(ret){
							document.getElementById('toName').value = ret;
							var params ={};
							params['name']=document.getElementById('toName').value;
							params['type'] = 0;
							$.ajax({   
								url : 'table_getUsersByIds.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_getUsersByIds.do)');
								},
								data : params,  
								success : function(data) {  
									if(confirm(data+"？")){
										$.ajax({   
											url : 'business_addHandRoundShip.do',
											type : 'POST',
											cache : false,
											async : false,
											error : function() {  
												alert('AJAX调用错误(business_addHandRoundShip.do)');
											},
											data : {
												userIds:ret,
												instanceId:'${instanceId}'
											},  
											success : function(data) { 
												var obj = eval('('+data+')');
												if(obj.result=='success'){
													alert('发送成功。');
												}
											}
										});
									}
								}
							});
						}else{
							if(document.getElementById(noid_anid)){
								document.getElementById(noid_anid).disabled='';
							}
						}
			    }
		    },500);
		}
	}
	
	function OnRotate(status){
		document.getElementById("frame1").contentWindow.OnRotate(status);
	}
	
	function SetIsOpenDlg(status) {
		try{
			document.getElementById("frame1").contentWindow.SetIsOpenDlg(status);
		}catch (e) {
		}
	}
	
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
