<!DOCTYPE html>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="renderer" content="ie-stand" />
</head>
<%@ include file="/common/header.jsp"%>
<%@page import="net.sf.json.JSONArray"%>
<script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js?t=201803231537" type="text/javascript"></script>
<link href="images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
<Link href="images/common.css" type="text/css" rel="stylesheet" />
<link href="images/index.css?t=20180102" type="text/css" rel="stylesheet" />
<link href="${ctx}/assets-common/css/common.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/assets-common/css/floatDiv.css?t=101" type="text/css" rel="stylesheet" />
<script src="${ctx}/form/jsp/widgets/common/js/doublelist.js" type="text/javascript"></script>
<script src="${ctx}/form/jsp/widgets/common/js/doublelist.js" type="text/javascript"></script>
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
<script type="text/javascript" defer="defer">
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
	//$('.frame1')
	
	function test(){
		/* var id = 'B84A80D9-FF01-4638-BDA8-F17585688846';
		var sealedPath = "D:/workflow/workflow_gcq/attachments/2017/09/12/B73238B9-56CA-4502-9ECE-5C9E3B59F0BA.pdf";
		document.getElementById("frame1").contentWindow.uploadLocalFile(id,sealedPath,'${curl}/attachment_uploadFile4Widget.do'); */
		var instanceId = '${instanceId}';
		var formId = '${formId}';
		var retMsg = 'noSealedFile';
		var zsbm = document.getElementById("frame1").contentWindow.getFormTagValue('zsdw');//主送部门
		var csbm = document.getElementById("frame1").contentWindow.getFormTagValue('csdw');//抄送部门
		$.ajax({
			url : 'table_getSendDeptNameAndPrintNum.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'instanceId':instanceId,
				'formId':formId,
				'zsbm':zsbm,
				'csbm':csbm
			},
			error : function(e) {  
				alert('AJAX调用错误(table_getSendDeptNameAndPrintNum.do)');
				return;
			},
			success : function(msg) {
				alert(msg);				
			}
		});
	}
	
	//方正电子公章调用
	function founderSeal(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,id){
		var path = document.getElementById("frame1").contentWindow.getFileLocalPath(id);
		if(path != null){
			var sealedPath = path.replace(".pdf", "_sealed.pdf");
    		var founderSealUrl = '${founderSealUrl}';
    		var founderSealObj = document.getElementById("sealObj");
    		var ret = founderSealObj.PDFVisualSeal(path, sealedPath, founderSealUrl, "<DeviceStyle>5</DeviceStyle>", "GCQOA", "", "");
    		if(ret != 0){
    			alert("盖章系统发生错误，盖章失败！");
    		}else{
    			document.getElementById("frame1").contentWindow.uploadLocalFile(id,sealedPath,'${curl}/attachment_uploadFile4Widget.do');
    			//刷新
    			initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
				//回调方法
				callback(tagid+"_visualStamp_callback");
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
</script>
<script type="text/javascript">
    	function formsub(){
			document.getElementById('form_nr').submit();
    	};
</script>
<script type="text/javascript">
	if(top.window && top.window.process && top.window.process.type){
	    var imported = document.createElement('script');
		imported.src = '${ctx}/common-assets/js/eleCommon.js';
		document.head.appendChild(imported);
	}
</script>
<body onload="init();formsub();" id="body" scroll="no" style="oveflow-y:hidden;">
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
<input type="hidden" id="imageCount" name="imageCount" value="${imageCount}">
<input type="hidden" id="modId" name="modId" value="${modId}">
<input type="hidden" id="matchId" name="matchId" value="${matchId}">
<input type="hidden" id="dicId" name="dicId" value="${dicId}">
<input type="hidden" id="dicValue" name="dicValue" value="${dicValue}">
<input type="hidden" id="note_ids" name="note_ids" value="${note_ids}">
<input type="hidden" id="note_names" name="note_names" value="${note_names}">
<input type="hidden" id="fontSize" name="fontSize" value="${fontSize}">
<input type="hidden" id="verticalSpacing" name="verticalSpacing" value="${verticalSpacing}">
<input type="hidden" id="dateFormat" name="dateFormat" value="${dateFormat}">
<div class="warp1">
	<div class="dh">
		<div class="bl_tab lc fl ml30"><a style="cursor:pointer"><span >流程图</span></a></div>
		<div class="bl_tab bl fl" style="display:none;"></div>
		<div class="bl_tab bl fl"><a style="cursor:pointer"><span >办理<em style="font-size:12px">&nbsp;&nbsp;(${dqNodeName})</em></span></a>  <span id="t_q" style="color:#fff;font-weight:bold; display: none;">剩余办理时间：</span><span id="t_m" style="color:#fff;font-weight:bold; display: none;">09</span><span id="t_e" style="color:#fff;font-weight:bold; display: none;">:</span><span id="t_s" style="color:#fff;font-weight:bold; display: none;">60</span></div>
		
	</div>
	<div class="content" style="">
	    <div class="supmin" style="position:absolute;right:18px;top:0;display:none;">
	        <span style="cursor:pointer;color:#FFF;" onclick="supmin();" title="全屏还原">全屏还原</span>
	    </div> 
	    <c:if test="${firstStep != 'false'}"> 
			<!-- 左右跳转图片显示 start -->
			<!-- <iframe height="60px" id="mliframe1" width="40px"  allowTransparency="true"	style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:50px;top:350px;z-index:9;" src="${ctx}/mlzg.jsp"></iframe> 
			<div id="gzl_pageLeftBox" ><a id="leftPage" class="b_disable" onclick="changePageToLeft()" style="cursor:pointer;"></a></div>
			<div id="gzl_pageRightBox"><a id="rightPage" onclick="changePageToRight()" style="cursor:pointer;"></a></div> -->
		 	<!-- 左右跳转图片显示    end -->
		</c:if>	
		 	<div id="toTop" style="position: absolute;width: 33px;height: 58px;z-index: 2;right: 40px;top: 500px;">
		 		<img src="${ctx}/assets-common/img/toTop.png" onclick="changePage(1)" />
		 	</div>
	    <iframe height="203px" id="mliframe" width="99px"  frameborder=no  style="display:none;orphans:0 ;float:left;padding:0px;margin:0px;position:absolute;left:1px;top:74px;z-index:111;" src="${ctx}/table_toCatalog.do?allInstanceId=${allInstanceId}&pages=${pages}"></iframe>
		<div class="bl_nav">
		    <!--<div class="supmax" style="position:absolute;right:18px;top:50px;">
		        <span style="cursor:pointer;color:#FFF;" onclick="supmax();" title="全屏">全屏</span>
		    </div>  -->
			<ul class="clearfix" style="width: 205px;">
				<c:if test="${pages != null && pages != ''}">
	      		<li id="li_dir" style="padding-bottom:  10px;">
	      			<a  href="#" class="contents fl ml8">批阅页</a>
	      			<div class="bl_n_nav" style="z-index:11;top:32px;display:none;width: 99px"  >
	      				<div class="bl_nav_top" style="z-index:11;" ></div>
	      			</div>
	     		</li>
	     		</c:if>
	     		<li style="width:78px;height:29px;margin-left:10px;">
	     			<c:if test="${itemType eq '0' || (itemType != '0' && allowUpload != 'true' )}">
			    	  	<a href="javascript:void(0);" style="width:105px;"  class="accessory ml5" onclick="changeClass()" >附件<em id="fjCount"></em></a>
		    	  	</c:if>
		    	  	<c:if test="${itemType != '0' && allowUpload eq 'true'}">
		    	  		<a href="javascript:void(0);" style="width:105px;"  class="accessory ml5" onclick="openAttsextUpDialog('${docguid}fj','${docguid}fj','${allowUpload}','true','false','true','false','false','','false','icon-add','icon-help','fjshow','false','${nodeId}');" >
			    	  		附件<em id="fjCount"></em>
			    	  	</a>
		    	  	</c:if>
		      	</li>
			</ul>
 			<div class="nr fl ml10" id="buttonList">
				<c:if test="${!(isSend == true && isEndProcess == true && send !=0)}">
     			<span class="fl" style="line-height:29px;">下一步:</span>
     			</c:if>
     			<div class="fl tw-step">
     				<!-- 当下节点小于当前节点排序号时，展示为上一节点 -->
     				<c:if test="${send !='' && send !=0}">
   		  			<c:forEach items="${nodes_last}" var="nodelast">  
					<c:if test="${nodelast.wfn_name!=null && nodelast.isBigNum == '0'}">
		 		   	<a class="tw-btn-red tw-btn-sm" href="#" id="${nodelast.wfn_id}" onclick="routeType('${nodelast.wfn_id}','node','','','${nodelast.wfl_child_merge}','${nodelast.wfn_route_type}')"><i class="tw-icon-arrow-left"></i> ${nodelast.wfn_name}</a> 
	 				</c:if>
 					</c:forEach>
 					</c:if>
	 			</div>
	 			<c:if test="${fn:length(nodes_last)>0}">
	 			<span class="sx fl">|</span>
	 			</c:if>
	 			<div class="fl tw-step">
	 				<!-- 主送人员 -->
					<c:if test="${send !='' && send !=0}">
					<!-- 遍历节点 -->
					<c:forEach items="${nodes}" var="node">  
		    		<c:if test="${node.wfn_name!=null && node.isBigNum != '0'}">
					<a class="tw-btn-secondary tw-btn-sm" href="#" id="${node.wfn_id}" onclick="routeType('${node.wfn_id}','node','','','${node.wfl_child_merge}','${node.wfn_route_type}')">${node.wfn_name} <i class="tw-icon-arrow-right"></i></a>
		    		</c:if>
		    		</c:forEach>
		    		<!-- 遍历子流程 -->
		    		<c:forEach items="${childs}" var="child">  
					<c:if test="${child.wfc_cname!=null && child.wfc_cname!=''}">
					<a class="tw-btn-secondary tw-btn-sm" href="#"  id="${child.wfc_cid}" onclick="routeType('${child.wfc_cid}','childWf','${child.wfc_relation}','${child.wfc_ctype}','','')"><span>${child.wfc_cname}</span></a>
		    		</c:if>
		    		</c:forEach>
		    		<!-- 多流程节点条件选择 -->
		    		<c:if test="${nodeName!=null && nodeName!='' }" >
		    		<a class="tw-btn-secondary tw-btn-sm" href="#" onclick="choiceCondition('${nodeId}');"    id="flowNode" ><span>${nodeName}</span></a>
		    		</c:if>
	    			</c:if>
					<c:if test="${childWfAfterNode == true}">  
					<a class="tw-btn-secondary tw-btn-sm" href="#"  id="end" onclick="end('end')"><span>${childWfNextNodeName}</span></a> 
					</c:if>
					<c:if test="${send==0}">
					<c:if test="${showEndBtn != 'true'}">
					<a class="tw-btn-secondary tw-btn-sm" href="javascript:operateForm(1,'')"><span>办理完成</span></a>
					</c:if>
					<c:if test="${showEndBtn == 'true'}">
					<a class="tw-btn-secondary tw-btn-sm" href="javascript:end('end')"><span>完成</span></a>
					</c:if>
					</c:if>
					<c:if test="${self_loop=='1'}">
						<a class="tw-btn-red tw-btn-sm" href="#" id="${self_node.wfn_id}" onclick="routeType('${self_node.wfn_id}','node','','','${self_node.wfl_child_merge}','${nodelast.wfn_route_type}','${self_node.wfn_self_loop}')"><i class="tw-icon-arrow-right"></i> ${self_node.wfn_name}</a>
					</c:if>
					<c:if test="${wfNode.wfn_islhfw!=null && wfNode.wfn_islhfw=='1' }">
					<a class="tw-btn-secondary tw-btn-sm" href="javascript:lhfw('${instanceId}');"><span>联合发文</span></a>
					</c:if>
					<c:if test="${isSendAgain != null && isSendAgain eq 1 }">
	     				<a class="tw-btn-secondary tw-btn-sm" href="javascript:reissue();">发送 <i class="tw-icon-arrow-right"></i></a>
	     			</c:if>
	     			<c:if test="${isSendBack != null && isSendBack eq 1 }">
	     				<a class="tw-btn-secondary tw-btn-sm" style="background-color:rgb(224,118,93)" href="javascript:toBack();">退回 </a>
	     			</c:if>
	 			</div>
     			<span class="sx fl">|</span>
     			<div class="fl zc_box ">
	     			<!-- invalid!=null && invalid!='0'&& -->
	     			<c:if test="${iszf!=null&&iszf==1}">
	     	 		<div class="fl zc">
	    		 		<span onclick="javascript:invalid();" style="cursor: pointer;">作废</span>
	    	 		</div>
	  		 		<span class="sx fl">|</span>
	     			</c:if>
	      			<c:if test="${itemId!=null}">
		      		<div class="fl zc">
		     			<span onclick="javascript:operateForm(0,'');" style="cursor: pointer;">暂存</span>
		     		</div>
	     			</c:if>
	     			<!-- 
	     			<div class="fl zc">
		     			<span onclick="javascript:handRound();" style="cursor: pointer;">传阅</span>
		     		</div>
		     		<span class="sx fl">|</span> -->
	     			<c:if test="${isPrint==1}" >
	   				<span class="sx fl">|</span>
	      			<div class="fl zc">
	     				<span onclick="javascript:beforePrint('${nodeId}','${workFlowId}');" style="cursor: pointer;">套打</span>
	     			</div>
	     			</c:if>
	     			<c:if test="${((isEndProcess == true && send !=0) || (isHaveEndNode == 'true')) && childWfAfterNode != true}">  
					<div class="fl ml8 bj">
						<span style="cursor: pointer;" id='bj' onclick="end('bj')"><c:if test="${isEndReply==true}">回复</c:if><c:if test="${isEndReply == false}">办结</c:if></span>
					</div>  
					</c:if>
					<%-- <c:if test="${isSend == true && isEndProcess == true && send !=0}">
					<div class="fl ml8 btn_send">
	     			<span onclick="send();"  style="cursor: pointer;" >发送</span>
					</div>
					</c:if> --%>
					<c:if test="${isFw == true}">
					<div class="fl ml8 btn_send">
						<span onclick="sendFile();"  style="cursor: pointer;" >分发</span>
					</div>
					</c:if>
					<%-- <c:if test="${isseal =='1'}">
					<div class="fl ml8 btn_send">
						<span onclick="onSeal();" style="cursor: pointer;" >盖章</span>
					</div>
					</c:if> --%>
				</div>
			</div> 
  			<div class="fl xx" id="msgList" style="width:auto;margin-right:30px;">
     			<div class="fl ml8 dy">     
    	 			<span class=""  onclick="PrintPDF(0);" style="cursor: pointer;">打印</span>
     			</div>  
				<c:if test="${receiveId!=null && receiveId!=''}">
 				<span class="sx fl">|</span>
     			<div class="fl zc fix-bg">
	     			<a href="#">
	     				<span onclick="javascript:downWord('${receiveId}','${receiveTitle}');" style="cursor: pointer;">原文下载</span>
	     			</a>
	 			</div>
				</c:if>     
   	 			<div class="fl dx" style="display:none;">
              		<input type="checkbox" name="isSmsRemind" value="1" id="isSmsRemind"  class="mt5 ml8 fl" />
             		<span class="fl ml5">短信提醒</span>
     			</div>
     
     			<c:if test="${iswcsx!=null&&iswcsx==1}">
     	 		<div class="fl szwcsx">
    				设置完成时限:<input type="number" id="szsx" name="szsx" onblur="checknumber()"/>工作日
    	 		</div>
     			</c:if>
     			<c:if test="${isShowRelDoFile != '1' && isHaveChild == '1' }">
	     		<%-- <div class="fl ml8 kb">     
	   	  		<span class="pl31"  style="cursor: pointer;" onclick="getRelatedDoFile('${instanceId}');" >查看相关办件</span>
		 		</div> --%>
	 			</c:if>
     		</div>
     		<div class="fl tw-tools" id="flexibleForm">
				<!-- <button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('enlarge');" title="放大"><i class="tw-icon-search-plus"></i></button>
				<button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('narrow');" title="缩小"><i class="tw-icon-search-minus"></i></button>
				<button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('enlargeOneTime');" title="最大化"><i class="tw-icon-expand"></i></button>
				<button class="tw-btn-border-green tw-btn-sm" onclick="enlargeOrNarrowPage('narrowOneTime');" title="最小化"><i class="tw-icon-compress"></i></button> -->
     		</div>
	 		<div class="fr tw-tools">
	 			<c:if test="${wfNode.wfn_formcopy == 1 && isFlexible != 1}">
	 				<button class="tw-btn-blue tw-btn-sm" onclick="copyForm();" id="copyBtn" ><i class="tw-icon-copy"></i>复制</button>
	 			</c:if>
     		</div>
		</div>
		<div style=" background-color: rgb(152, 152, 152);position:absolute;width: 100%;">
        	<div  class="bl_n_nav bl_a_nav fouce" style="display: none;z-index:2;width: 600px; margin: auto;position:relative;max-height : 100px;overflow: auto;top:0;box-shadow:0px 3px 3px 0 rgba(13,5,9,0.2)"  id="fjml">
          		<div class="bl_nav_fj" >
					<ul class="file-list">
						<c:if test="${itemType eq '0' }">
							<li class="item">
								
								<div class="file-attr">
						      		<div id="attzwshow" style="min-height:25px;"></div>
						      	</div>
						 		<div class="file-name">
									<trueway:att onlineEditAble="true" isFirst='${isFirst}' id='${docguid}zw' docguid='${docguid}attzw' showId='attzwshow' zwTemSel='${zwTemSel}' nodeId="${nodeId}" ismain="true" downloadAble="true" isZw="true" uploadAble="${allowUpload}" deleteAble="${isFirst}" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
						      	</div>
							</li>
							<li class="item">
								<div class="file-attr">
					         		<div id="fjshow" style="min-height:25px;"></div>
					         	</div>
					         	<div class="file-name">
						 			<trueway:att onlineEditAble="false" isFirst='${isFirst}' id='${docguid}fj' docguid='${docguid}fj' showId='fjshow'  nodeId="${nodeId}"  ismain="true" downloadAble="true" uploadAble="${allowUpload}" deleteAble="${allowUpload}" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss" isZw="fj"/>
					        	</div> 
							</li>
						</c:if>
						<c:if test="${itemType eq '1' || itemType eq '3' || itemType eq '4'}">
							<c:if test="${isFirst == true}">
								<li class="item">
									<div class="file-attr">
						         		<div id="fjshow" style="min-height:25px;"></div>
						         	</div>
						         	<div class="file-name">
							 			<trueway:att onlineEditAble="false" isFirst='${isFirst}' id='${docguid}fj' docguid='${docguid}fj' showId='fjshow'  nodeId="${nodeId}"  ismain="true" downloadAble="true" uploadAble="${allowUpload}" deleteAble="${allowUpload}" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss" isZw="fj"/>
						        	</div> 
								</li>
							</c:if>
							<c:if test="${isFirst != true}">
								<li class="item">
									<div class="file-attr">
						         		<div id="fjshow" style="min-height:25px;"></div>
						         	</div>
						         	<div class="file-name">
							 			<trueway:att onlineEditAble="false" isFirst='${isFirst}' id='${docguid}fj' docguid='${docguid}fj' showId='fjshow'  nodeId="${nodeId}"  ismain="true" downloadAble="true" uploadAble="${isFirst}" deleteAble="${isFirst}" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss" isZw="fj"/>
						        	</div> 
								</li>
							</c:if>
						</c:if>
						
					</ul>
                </div>
            	<div class="bl_nav_bottom"></div>
        	</div>
		</div>		
		<div>
			<div style="overflow: hidden;background-color:#eee;">
 			<iframe height="100%" id="lcbox" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:96%; " src=""></iframe>
 			<c:choose>
				<c:when test="${(isFirst == true || isCy == true || (stepIndex=='1' && cType !='1')) && (isWriteNewValue != false) && (finstanceId==null || finstanceId=='' )}">
					<form target="frame_nr" id="form_nr" method="post" action="${ctx}/pdfocx/1.jsp?isFlexibleForm=${isFlexibleForm}&usbkey=${usbkey}&sealurl=${sealurl}&isContinue=${isContinue }&origProcId=${origProcId }&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&loginname=${loginname}&isWriteNewValue=false&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&no=${no}&atts=''&isOver=${isOver}">
						<input type="hidden" name="dicValue" id="" value="${dicValue}"/>
						<input type="hidden" name="modId" id="" value="${modId}"/>
						<input type="hidden" name="dicId" id="" value="${dicId}"/>
						<input type="hidden" name="matchId" id="" value="${matchId}"/>
					</form>
					<!-- <iframe height="100%" id = "frame2" class="frame2" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:96%; " src="${ctx}/business_getHandRoundShips.do?instanceId=${instanceId}"></iframe>
					 -->
					 <iframe name="frame_nr" id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;position:fixed;left:0;background-color: #989898;" src=""></iframe>
					
				</c:when>
				<c:otherwise>
					<form target="frame_nr" id="form_nr" method="post" action="${ctx}/pdfocx/1.jsp?isFlexibleForm=${isFlexibleForm}&usbkey=${usbkey}&sealurl=${sealurl}&isContinue=${isContinue }&origProcId=${origProcId }&ischeck=${ischeck}&workFlowId=${workFlowId}&formId=${formId}&oldFormId=${oldFormId}&processId=${processId}&instanceId=${instanceId}&nodeId=${nodeId}&webId=${webId}&limitValue=${limitValue}&userId=${userId}&loginname=${loginname}&isWriteNewValue=${isWriteNewValue}&newInstanceId=&newProcessId=${newProcessId}&isCanLookAtt=${isCanLookAtt}&allInstanceId=${allInstanceId}&no=${no}&atts=&isOver=${isOver}">
						<input type="hidden" name="dicValue" id="" value="${dicValue}"/>
						<input type="hidden" name="modId" id="" value="${modId}"/>
						<input type="hidden" name="dicId" id="" value="${dicId}"/>
						<input type="hidden" name="matchId" id="" value="${matchId}"/>
					</form>
					<!-- 
					<iframe height="100%" id="frame2"  class="frame2" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="padding:0px;margin:0px;display:none;position:absolute;left:0;top:30px;z-index:9;width:100%;height:96%; " src="${ctx}/business_getHandRoundShips.do?instanceId=${instanceId}"></iframe>
					 -->
					<iframe name="frame_nr" id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:100%;width:100%;position:fixed;left:0;background-color: #989898;" src=""></iframe>
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
<input type="hidden" id="sendMsgUserId" name="sendMsgUserId">
<input type="hidden" id="yqinstanceid" name="yqinstanceid" value="${yqinstanceid}">
<input type="hidden" id="closeframe" name="closeframe" value="0">
<input type="hidden" id="flowinfo" name="flowinfo" value="">
<div class="quick_links_wrap">
   <ul class="quick_links" class="quick_links">
	     <li class="active"  onclick="setDrawType(0,0,0,0,0);"><a href="#" title="键盘输入"><i class="icon-jpsr"></i><span>键盘输入</span></a></li>	        
		 <!-- <li onclick="setColor();">
		     <a href="#"  title="手写"><i class="icon-sx"></i><span>手写</span></a>
		     <div class="popover">
		         <div class="arrow"></div>
		         <div class="popover-content">
		         	 <input type="hidden" name="color" class="popover-color" value="0">
		             <button class="tw-border-black tw-active" data-value="0">黑</button>
		             <button class="tw-border-red" data-value="1">红</button>
		         </div>
		     </div>
		 </li>	         -->
		 <li onclick="showHandWrite(1);" id="qprxx"><a href="#" title="签批人信息"><i class="icon-qprxx"></i><span>签批人信息</span></a></li>	        
		 <!-- <li onclick="setDrawType(3,0,0,0,3);"><a href="#" title="橡皮擦"><i class="icon-xpc"></i><span>橡皮擦</span></a></li>      
		 <li onclick="onRedo();"><a href="#"  title="还原"><i class="icon-reply"></i><span>还原</span></a></li>  
		 <li onclick="onUndo();"><a href="#"  title="撤销"><i class="icon-forward"></i><span>撤销</span></a></li> -->  
		 <li onclick="OnRotate('1');" id="turnleft"><a href="#"  title="左旋转"><i class="icon-reply"></i><span>左旋转</span></a></li>  
		 <li onclick="OnRotate('0');" id="turnright"><a href="#"  title="右旋转"><i class="icon-forward"></i><span>右旋转</span></a></li>
	</ul>
	</ul>
</div>
<embed id="sealObj" type="application/x-founder-npsealstamp" width=1 height=1 />

</body>
<script src="images/jquery.min.js" type="text/javascript"></script>
<script src="images/common.js" type="text/javascript"></script>
<script src="images/jquery.tab.js" type="text/javascript"></script>
<script>

	if($($('.file-name')[0]).find('img').length == 2){
		
		$($('.file-attr')[0]).css({'padding-left':'140px'})
	}
	var m = "09";//传个分钟数到这里
	var s = "59";
	function showtime(){
		document.getElementById('t_m').innerHTML = m;
		document.getElementById('t_s').innerHTML = s;
		m = parseInt(m);
		s = parseInt(s);
		s = s-1;
		if(s==0){
			m = m -1;
			s = 60
		}
		if(m<10){
			m = "0"+m;
		}
		if(s<10){
			s = "0"+s;
		}
	}
	clearInterval(settime); 
	var settime = setInterval(function(){
		showtime();
	},1000);
</script>
<script type="text/javascript">
	var dontShowFinish = 0;
	var imageCount = parseInt('${imageCount}');
	function copyForm(){
		$("#copyBtn").attr("disabled","disabled");
		$.ajax({
			url : 'table_copyForm.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'instanceId':'${instanceId}'
			},
			error : function(e) {  
				alert('AJAX调用错误(table_copyForm.do)');
			},
			success : function(msg) {
				var json = eval('(' + msg + ')');
				//console.log(msg);
				//alert("复制完成");
				document.getElementById('mliframe').contentWindow.location.reload(true);
				document.getElementById("frame1").contentWindow.copyFormFile(json.pdfPath, json.number);
				$('#leftPage').removeClass('b_disable');
				changePage(json.number+1);
				imageCount++;
				dontShowFinish = 1;
				operateForm(0,'');
				dontShowFinish = 0;
			}
		});
	}

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
<script type="text/javascript">
	function downWord(receiveId, title){
		document.getElementById("download_iframe").src='${ctx}/table_downloadTure.do?receiveId='+receiveId+'&name='+encodeURI(title);
	}
</script>
<script>
	var isopened=false;
	setTimeout(srcIframe,1000);
	function srcIframe(){
		var iframe = document.getElementById("lcbox");
		iframe.src="${ctx}/table_getWfps.do?instanceId=${instanceId}&workFlowId=${workFlowId}&t="+new Date();
	}

	var len1=1;
	function  changeClass(){
		if(len1==0){
			len1=1;
			$('#fjml').removeClass("fouce");
			document.getElementById("fjml").style.display="none";
		    document.getElementById("frame1").contentWindow.document.getElementById("mianDiv").style.height="0px"; 
		    //document.getElementById("frame2").contentWindow.document.getElementById("mianDiv").style.height="0px"; 
		}else{
			len1=0;
			$('#fjml').addClass("fouce");
			document.getElementById("fjml").style.display="";
	        document.getElementById("frame1").contentWindow.document.getElementById("mianDiv").style.height="100px"; 
	        //document.getElementById("frame2").contentWindow.document.getElementById("mianDiv").style.height="100px";
		}
	};
	
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
	    /* if(  document.getElementById('fjml')){
	    	  document.getElementById('fjml').style.left=cd+"px";
	    	  document.getElementById('fjml').style.top="0px";
	    } */
	    
		$('.bl_tab').each(function(i,item){
			console.log(i);
			var bH=$(window).height();
			var bW=$(window).width();  
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
	    			$('#frame2').css("z-index","0").css('display','none');
					document.getElementById("gzl_pageLeftBox").style.display="none";
					document.getElementById("gzl_pageRightBox").style.display="none";
					console.log(1)
	    		}else if(i==2){
	    			over();
	    			$('.quick_links_wrap').show();	    			
	    			$(this).addClass("bl").removeClass("lc");
	    			$('.bl_tab').eq(0).addClass("lc").removeClass("bl");
	    			$('.bl_tab').eq(1).addClass("lc").removeClass("bl");
	    			$('#lcbox').css("z-index","0").css('display','none');
	    			$('.frame1').height(bH-60);
	    			$('#frame2').css("z-index","0").css('display','none');
	    			if(imageCount>1){
						document.getElementById("gzl_pageLeftBox").style.display="";
						document.getElementById("gzl_pageRightBox").style.display="";
	    			}
	    			console.log(2)
	    		} else if(i==1){
	    			out();
	    			$('.quick_links_wrap').hide();	    			
	    			$(this).addClass("bl").removeClass("lc");
	    			$('.bl_tab').eq(0).addClass("lc").removeClass("bl");
	    			$('.bl_tab').eq(2).addClass("lc").removeClass("bl");
	    			$('#lcbox').css("z-index","0").css('display','none');
	    			$('.frame1').height(0);
	    			$('#frame2').css("z-index","9").css('display','block');
	    			//$('#frame2').attr('src',$('#lcbox').attr('src'));
					document.getElementById("gzl_pageLeftBox").style.display="none";
					document.getElementById("gzl_pageRightBox").style.display="none";
					console.log(1)
	    		}
	    	});
	    });
		if(imageCount == 1){
			if(document.getElementById("mliframe").contentWindow.document.getElementById("ahref1")){
				document.getElementById("mliframe").contentWindow.document.getElementById("ahref1").className="";
				//document.getElementById("mliframe").contentWindow.document.getElementById("ahref1").className="hot";
				if(document.getElementById("gzl_pageLeftBox")){
					document.getElementById("gzl_pageLeftBox").style.display="none";
				}
				if(document.getElementById("gzl_pageRightBox")){
					document.getElementById("gzl_pageRightBox").style.display="none";
				}
			}
		}
		if("${attSize}" != 0){
		 	document.getElementById("fjCount").innerHTML = "(${attSize})";
		}
		var isautoclosewin = "${isautoclosewin}";
		if(isautoclosewin == 1){
			document.getElementById("t_q").style.display="";
			document.getElementById("t_m").style.display="";
			document.getElementById("t_e").style.display="";
			document.getElementById("t_s").style.display="";
			setTimeout(function(){
				operateForm(0,'1');
				parent.closeTabsInLayer();
			},600000);	
		}
	});
	function PrintPDF(isOver){
		 document.getElementById("frame1").contentWindow.PrintPDF(isOver);
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
			'instanceId':instanceId,
			'state':state
		},
		error : function(e) {  
			alert('AJAX调用错误(meetings_updateMeetingState.do)');
		},
		success : function(msg) {
			if(msg == 'no'){
				alert("更新状态失败，请联系管理员！");
				return false;
			}else if(msg == 'yes'){
				
			}
		}
	});
}
</script>
<script type="text/javascript">		//办件发送、暂存、办结等一些操作
	var noid_anid="";
	var wfn_self_loop_all='0' ;
	function routeType(nextNodeId,type,relation,cType,isMergeChild,route_type,wfn_self_loop){
		if(null != wfn_self_loop && '1' == wfn_self_loop){
			wfn_self_loop_all='1';//自循环发送下一步
		}else{
			wfn_self_loop_all='0';
		}
		var obj = document.getElementById("frame1").contentWindow;
		var trueJSON = obj.getPageData();
		var json = JSON2.stringify(trueJSON.pdfjson);
		if(json == null ||json == "null" || json == 'null'){
			alert("表单未加载完成");
			return ;
		}
		var formId = obj.document.getElementById("formId").value;
		var o = '${isbt}';
		if(o){
			var error = obj.isCheckBt(o.split(";"));
			if(error!=""){
				alert(error);
				return;
			}
		}
		var formjson = trueJSON.formjson;
		var tagValue = getProValue();
		var pageValue = tagValue;
		if(formjson != ''){
			pageValue = mergeJson(tagValue,formjson);
		}
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
		if(isHaveTitle == null || isHaveTitle == ''){
			alert('流程标题不能为空');
			return;
		}
		var workFlowId = obj.document.getElementById("workFlowId").value;
		var instanceId = obj.document.getElementById("instanceId").value;
		var processId = obj.document.getElementById("processId").value;
		var neRouteType = document.getElementById("neRouteType").value ;
		var hsFlag = true;
		var isSend = 0;
		var ret=null;
		noid_anid=nextNodeId;
		//按钮禁止点击
		document.getElementById(noid_anid).disabled='disabled';
		//当必须上传附件时，检查附件是否为空
		var instanceId = '${instanceId}';
		var nodeId = '${nodeId}';
		var isUpload = false;
		if('${isUploadAttach}' == '1'){
			$.ajax({
				url : '${ctx}/attachment_isAttachExistByNode.do?instanceId=' + instanceId + '&nodeId='+ nodeId,
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('AJAX调用错误(itemRelation_getIsDelaying.do)');
				},
				success : function(result) {
					if(result=='fail'){
						alert("请上传附件！");
						isUpload = true;
					}
				}
			});
			if(isUpload){
				if(document.getElementById(noid_anid)){
					document.getElementById(noid_anid).disabled='';
				}
				return;
			}
		}
		if(type=='node'){
			if(hsFlag){
				$.ajax({   
					url : 'table_isOnlyPerson.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_isOnlyPerson.do)');
						document.getElementById(noid_anid).disabled='';
					},
					data : {
						'workFlowId':workFlowId,
						'nodeId':'${nodeId}',
						'nextNodeId':noid_anid,
						'instanceId':instanceId,
						'processId':processId,
						'formId':formId
					},    
					success : function(person) {
						if(person != '' && person != 'nullUserId'){			//发送时,默认选中的人员发送
							isSend = prepareForsend(person, noid_anid,route_type,isMergeChild);
						}else{
							var toUserId = '${toUserId}';
							if(null != toUserId && '' != toUserId){//督办自动发送给被督办人
								person="gdPerson="+toUserId+";";
								isSend = prepareForsend(person, noid_anid,route_type,isMergeChild);
							}else{//选择人员直接发送
								chooseForsend(noid_anid, workFlowId, isMergeChild, person);
							}
						}
						if(isSend > 0){
							sendNext(wfn_self_loop_all,'');
						}else{
							if(obj.document.getElementById("jzk1")){
								obj.fsjs();
							}
						}
					}
				});
			}else{
				parent.closeTabsInLayer();
			}
		}else if(type=='childWf'){		//选择子流程，打开第一个子节点
			sendToChild(noid_anid);
		}else{
			parent.closeTabsInLayer();
		}
	}
	//debugger
//	top.window.werr=$('.frame1')
	//console.log($('.frame1'))
	//将待办信息
	function sendPendToChild(workFlowId, noid_anid){
			var neRouteType = document.getElementById("neRouteType").value;
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
			}else if(neRouteType == 4){
				xtoName = document.getElementById("toName").value;
			}
			var tagValue = getProValue();
			var pdfNewPath = "";
			//json
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
			var json = JSON2.stringify(trueJSON.pdfjson);
			var formjson = trueJSON.formjson; //  json object
			var pageValue =tagValue;
			if(formjson != ''){
				pageValue = mergeJson(tagValue,formjson);
			}
			//判断是否走公文交换平台--针对只有一个主送,一个抄送
			var params = pageValue;
			params['xtoName'] = xtoName;
			params['xccName'] = xccName;
			params['workFlowId'] = workFlowId;
			params['nextNodeId'] = noid_anid;
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
				error : function() {  
					alert('AJAX调用错误(table_isGoExChange.do)');
					if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
				},
				success : function(msg) {
					if(msg != '' && msg.indexOf('outparwf')!=-1){			//子流程脱离父流程
						var msgArr = msg.split(":");
						if(msgArr!=null && msgArr.length==1){	//已经无任何流转节点
							
						}else{		//后面串联着节点
							var nodeIndo = msgArr[1];
							var par = nodeIndo.split(',');
							var wfn_nodeId = par[0];
							var route_type = par[1];
							var userId = par[2];
							if(wfn_nodeId!=null){
								noid_anid = wfn_nodeId;
								document.getElementById("neRouteType").value = route_type;
								if(route_type!=null && route_type=='2'){				//并行完全式
									var ids = userId.split(',');
									document.getElementById("xtoName").value==ids[0];
									var xccName = "";
									if(ids!=null && ids.length>1){
										for(var i=1; i<ids.length; i++){
											xccName += ids[i];
											if(i!=ids.length-1){
												xccName += ',' ;
											}
										}
										document.getElementById("xccName").value = xccName;
									}
								}else{
									document.getElementById("toName").value = userId;
									document.getElementById("xtoName").value = "";
									document.getElementById("xccName").value = "";
								}
								sendNext(wfn_self_loop_all,'');
							}
						}
					}else if (msg != '' && msg.indexOf('noInfo')!=0){
						var msgArr = msg.split(";");
						var isSuccess = 0;
						for(var i=0,len=msgArr.length;i<len;i++){
							var isMerge = msgArr[i].split(",")[0];
							var userId = msgArr[i].split(",")[1];
							var newInstanceId = msgArr[i].split(",")[2];
							var newProcessId = msgArr[i].split(",")[3];
							var newFormId = msgArr[i].split(",")[4];
							var childWorkflowId = msgArr[i].split(",")[5];
							var childNodeId = msgArr[i].split(",")[6];
							var doType = msgArr[i].split(",")[7];
							var isLast=0;
							if(i==len-1){
								isLast=1;
							}
							 var isSmsRemind = 0 ;//是否短信提醒
							if(document.getElementById("isSmsRemind").checked){
								isSmsRemind=1;
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
							params['oldProcessId'] = '${processId}';
							params['nextNodeId'] = noid_anid;
							params['json'] = json;
							params['nodeId'] = '${nodeId}';
							params['doType'] = doType;
							$.ajax({   
								url : 'table_getNextIsMerge.do?isSmsRemind='+isSmsRemind,
								type : 'POST',   
								cache : false,
								async : false,
								data : params,
								error : function() {  
									if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}
									if(document.getElementById(noid_anid)){
										document.getElementById(noid_anid).disabled='';
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
								debugger
								parent.closeTabsInLayer();
							}
						}else if(msg.indexOf('noInfo')==0){
							$("#closeframe").val("1");
							//alert($("#closeframe").val());
							$("#flowinfo").val(workFlowId+";"+nextNodeId+";"+xtoName+";"+xccName);
							var empGuid = msg.split(",")[1];
							document.getElementById('xtoName').value = empGuid;
							document.getElementById('xccName').value = '';
							sendNext(wfn_self_loop_all,'');	//保存中间步骤值
						}else{
							alert("人员选择不正确,请重新选择!");
							if(obj.document.getElementById("jzk1")){
								obj.fsjs();
							}
							if(document.getElementById(noid_anid)){
								document.getElementById(noid_anid).disabled='';
							}
							return ;
						}
					}
				});
		}
	
	/**
		打开子流程，进行流程嵌套发送
	*/
	function  sendToChild(noid_anid){
		//子流程打开第一步填表单
		var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
		var commentJson = JSON2.stringify(trueJSON.pdfjson);
		var obj = new Object();
		obj.commentJson=commentJson;
		$.ajax({   
			url : 'table_getSessionByCommentJson.do',
			type : 'POST',   
			cache : false,
			async : false,
			data: {
				'commentJson':commentJson
			},
			error : function() {  
				if(obj.document.getElementById("jzk1")){
					obj.fsjs();
				}
				if(document.getElementById(noid_anid)){
					document.getElementById(noid_anid).disabled='';
				}
				alert('AJAX调用错误(table_getNextIsMerge.do)');
			},
			success : function(msg) {
				var retVal = window.showModalDialog('${ctx}/table_openFirstForm.do?isChildJson=1&nodeId=${nodeId}&isChildWf=true&newPdfPath=${pdfPath}&formId=${formId}&isWriteNewValue=false&workflowid='+nextNodeId+'&itemid=${itemId}&cType='+cType+'&relation='+relation+'&processId='+processId+'&finstanceId='+instanceId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
				if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
		    	 }else{
		    	  	 if(relation == '1'){//同步
		    			 parent.closeTabsInLayer();
					 }
				  }
			}
		});
	}
	
	/**
		选择人员信息进行发送下一步的准备
	*/
	function chooseForsend(noid_anid, workFlowId, isMergeChild, defUserId){
		var obj = document.getElementById("frame1").contentWindow;
		var nextNodeId = noid_anid;
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
				var url4UserChose='${curl}/ztree_showDepartmentTree.do?showCheckbox=1&exchange='+exchange+'&click=true&defUserId='+defUserId+'&nodeId='+noid_anid+'&send=${send}&routType='+msg+'&mac='+GetLocalMac()+'&t='+new Date();
				var url = url4UserChose;
				var WinWidth = 860;
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
							var rets = '';
							var ret = '';
							var canSendMsg = '';
							var sendMsgId = '';
							if(args.split("#").length>1){
								canSendMsg = args.split("#")[1];
								rets = args.split("#")[0];
								if(rets.split("@").length>1){
									ret = rets.split("@")[0];
									sendMsgId = rets.split("@")[1];
								}else{
									ret =  rets;
								}
							}else{
								rets = args;
								if(rets.split("@").length>1){
									ret = rets.split("@")[0];
									sendMsgId = rets.split("@")[1];
								}else{
									ret = rets;
								}
								
							}
							document.getElementById('sendMsgUserId').value = sendMsgId;
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
									var params ={};
									if(document.getElementById("neRouteType").value==2){
										params['name']=document.getElementById('xtoName').value;
										params['cname']=document.getElementById('xccName').value;
										params['type'] = 2;
									}else{
										params['name']=document.getElementById('toName').value;
										params['type'] = 0;
									}
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
											//if(confirm(data+"？")){
												if(isMergeChild=='1'){
													sendPendToChild(workFlowId, noid_anid);
												}else{
													sendNext(wfn_self_loop_all,canSendMsg);
												}
											/* }else{
												if(document.getElementById(noid_anid)){
													document.getElementById(noid_anid).disabled='';
												}
												if(obj.document.getElementById("jzk1")){
													obj.fsjs();
												}
												isSend = 0;
											} */
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
							var args = window.returnValue;
							var rets = '';
							var ret = '';
							var canSendMsg = '';
							var sendMsgId = '';
							SetIsOpenDlg(0);
							if(args.split("#").length>1){
								canSendMsg = args.split("#")[1];
								rets = args.split("#")[0];
								if(rets.split("@").length>1){
									ret = rets.split("@")[0];
									sendMsgId = rets.split("@")[1];
								}else{
									ret =  rets;
								}
							}else{
								rets = args;
								if(rets.split("@").length>1){
									ret = rets.split("@")[0];
									sendMsgId = rets.split("@")[1];
								}else{
									ret = rets;
								}
								
							}
							document.getElementById('sendMsgUserId').value = sendMsgId;
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
									var params ={};
									if(document.getElementById("neRouteType").value==2){
										params['name']=document.getElementById('xtoName').value;
										params['cname']=document.getElementById('xccName').value;
										params['type'] = 2;
									}else{
										params['name']=document.getElementById('toName').value;
										params['type'] = 0;
									}
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
											//if(confirm(data+"？")){
												if(isMergeChild=='1'){
													sendPendToChild(workFlowId, noid_anid);
												}else{
													sendNext(wfn_self_loop_all,canSendMsg);
												}
											/* }else{
												if(document.getElementById(noid_anid)){
													document.getElementById(noid_anid).disabled='';
												}
												if(obj.document.getElementById("jzk1")){
													obj.fsjs();
												}
												isSend = 0;
											} */
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
		});
	}
	
	/**
		解析节点类型、选中发送人员信息（默认人员）
	*/
	function prepareForsend(person, nextNodeId, route_type, isMergeChild){
		var isSend = 0;
		var persons  = person.split("=");
		if(persons[0] == 'more'){
			alert("该发送节点既设置了固定人员也设定了指定节点,因只能设置一种,请重新设置！");
		}else if(persons[0] == 'bxwqs'){			//并行完全式发送：区分主送、抄送
			var msg = persons[1];
			var res = msg.split(";");
			document.getElementById('xtoName').value = res[0];
			document.getElementById('xccName').value = res[1];
			document.getElementById("neNodeId").value = nextNodeId;
			document.getElementById("neRouteType").value = "2";
			isSend ++;
		}else if(persons[0] == 'duban'){
			
		}else {										//其他节点模式		
			var zdPerson = persons[1].split(";");
			var zdPersonXtoName = zdPerson[0];
			var zdPersonXccName = new Array();
			if(zdPerson[1] != ''){
				zdPersonXccName = zdPerson[1].split(",");
			}
			if(persons[0] == 'gdPerson'){	
				document.getElementById('toName').value = persons[1].substring(0,persons[1].length-1);
				//单人模式
				document.getElementById("neNodeId").value = nextNodeId;
				document.getElementById("neRouteType").value = route_type;
				//根据用户id 查询人员 name 
				$.ajax({   
					url : 'table_getUserById.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_getUserById.do)');
					},
					data : {
						'userId':document.getElementById('toName').value
					},    
					success : function(emp) {  
						var choosePer = false;
						if(emp!=null && emp!=''){
							 var emp = JSON2.parse(emp);
							 var content = "";
							  for(var i=0; i<emp.length; i++){
								 content += emp[i]['departmentName'] + " " +emp[i]['employeeName'];
								 if(i!=emp.length-1){
									 content += ",";
								 }
							 }
							//if(confirm("确定要发送给 "+ emp[0]['departmentName'] + " " +emp[0]['employeeName']+" 吗？")){
								sendNext(wfn_self_loop_all,'');
							/* }else{
								choosePer = true;
							} */
						}else{		//弹出选择人员月面
							choosePer = true;
						}
						if(choosePer){			//取消默认选中人员
							SetIsOpenDlg(1);
							var url4UserChose='${curl}/ztree_showDepartmentTree.do?click=true&nodeId='+noid_anid+'&send=${send}&routType=0&t='+new Date();
							var url = url4UserChose;
							var WinWidth = 860;
							var WinHeight = 600;
							if(top.window && top.window.process && top.window.process.type){
								console.info("封装打开方式："+url);
							    var ipc = top.window.nodeRequire('ipc');
							    var remote = top.window.nodeRequire('remote');
							    var browserwindow = remote.require('browser-window');
                                var focusedWindow = browserwindow.getFocusedWindow();
                                var focusedId = focusedWindow.id;
							    var win = new browserwindow({width:WinWidth,height:WinHeight});
			                    win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
							    //win.openDevTools();
							    ipc.on('message-departmentTree',function(args){
									if(win){
										SetIsOpenDlg(0);
						                win.close();
										win = null;
										var ret = args;
								         if(ret){
												var res = ret.split(";");
												document.getElementById('toName').value = ret;
												ret = null;
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
														//if(confirm(data+"？")){
															sendNext(wfn_self_loop_all,'');
														/* }else{
															if(document.getElementById(noid_anid)){
																document.getElementById(noid_anid).disabled='';
															}
															if(obj.document.getElementById("jzk1")){
																obj.fsjs();
															}
														} */
													}
												});
											}else{
												if(document.getElementById(noid_anid)){
													document.getElementById(noid_anid).disabled='';
												}
											}
								      	//----------------------------
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
												var res = ret.split(";");
												document.getElementById('toName').value = ret;
												ret = null;
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
														//if(confirm(data+"？")){
															sendNext(wfn_self_loop_all,'');
														/* }else{
															if(document.getElementById(noid_anid)){
																document.getElementById(noid_anid).disabled='';
															}
															if(obj.document.getElementById("jzk1")){
																obj.fsjs();
															}
														} */
													}
												});
											}else{
												if(document.getElementById(noid_anid)){
													document.getElementById(noid_anid).disabled='';
												}
											}
								        //------------------------------
								    }
							    },500);
							}
						}
					}
				});
			}else if(persons[0] == 'zdPerson'){		//自动追溯功能
				if(zdPersonXtoName != '' && zdPersonXccName.length == 0){//指定节点只有一个人，且为主送
					document.getElementById('toName').value = zdPersonXtoName;
					document.getElementById("neNodeId").value = nextNodeId;
					//单人模式
					document.getElementById("neRouteType").value = 0;
					isSend ++;
					//sendNext(wfn_self_loop_all,'');
				}else if(zdPersonXtoName == '' && zdPersonXccName.length == 1){//指定节点只有一个人，但是为抄送
					document.getElementById('toName').value = zdPersonXccName;
					document.getElementById("neNodeId").value = nextNodeId;
					//单人模式
					document.getElementById("neRouteType").value = 0;
					isSend ++;
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
								
								var params ={};
								if(document.getElementById("neRouteType").value==2){
									params['name']=document.getElementById('xtoName').value;
									params['cname']=document.getElementById('xccName').value;
									params['type'] = 2;
								}else{
									params['name']=document.getElementById('toName').value;
									params['type'] = 0;
								}
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
										//if(confirm(data+"？")){
											sendNext(wfn_self_loop_all,'');
										/* }else{
											if(document.getElementById(noid_anid)){
												document.getElementById(noid_anid).disabled='';
											}
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
										} */
									}
								});
							}else{
								//给发送下一步传所点击的按钮的属性值--下一步的节点值
								var url4UserChose='${curl}/ztree_showDepartmentTree.do?click=true&nodeId='+noid_anid+'&send=${send}&routType='+msg+'&t='+new Date();
								ret=window.showModalDialog(url4UserChose,null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
								if(ret){
									if(msg*1 == 2){
										var res = ret.split(";");
										document.getElementById('xtoName').value = res[0];
										document.getElementById('xccName').value = res[1];
									}else{
										document.getElementById('toName').value = ret;
									}
									document.getElementById("neNodeId").value = noid_anid;
									//所选择的路由类型
									if(msg==null || msg =='' || msg=='null'){//重定向时
										document.getElementById("neRouteType").value = 0;
									}else {
										document.getElementById("neRouteType").value = msg;
									}
									// 异步调用 根据id 获取人员
									//neRouteType 主送抄送的值
									var params ={};
									if(document.getElementById("neRouteType").value==2){
										params['name']=document.getElementById('xtoName').value;
										params['cname']=document.getElementById('xccName').value;
										params['type'] = 2;
									}else{
										params['name']=document.getElementById('toName').value;
										params['type'] = 0;
									}
									
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
											//if(confirm(data+"？")){
												sendNext(wfn_self_loop_all,'');
											/* }else{
												isSend = 0;
												if(document.getElementById(noid_anid)){
													document.getElementById(noid_anid).disabled='';
												}
												if(obj.document.getElementById("jzk1")){
													obj.fsjs();
												}
											} */
										}
									});
								}else{
									if(document.getElementById(noid_anid)){
										document.getElementById(noid_anid).disabled='';
									}
								}
							}
						}
					});
				}
			}
		}
		return isSend;
	}
	
	function goExChangeSendValue(){
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
		params['closeframe'] = '1';
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
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
				},
				data :params,
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
								error : function() {  
									if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}
									if(document.getElementById(noid_anid)){
										document.getElementById(noid_anid).disabled='';
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
							parent.closeTabsInLayer();
						}
					}else{
						alert("人员选择不正确,请重新选择!");
						if(obj.document.getElementById("jzk1")){
							obj.fsjs();
						}
						if(document.getElementById(noid_anid)){
							document.getElementById(noid_anid).disabled='';
						}
						return ;
					}
				}
			});
			}
		});
	}
	
	
	
	
	function end(bjid){
		if(isopened){
			alert("已有别人提交请勿办理");
			return;
		}
		noid_anid=bjid;
		if(bjid!=null && bjid=='end_auto'){			//父流程自动办结
			noid_anid="end";
		}
		if(document.getElementById(noid_anid)){
			document.getElementById(noid_anid).disabled='disabled';
		}
		var nodeNameNew = "";
		if('${childWfAfterNode}' == 'true'){
			nodeNameNew = '发送下一步';
		}else{
			nodeNameNew = '办结';
		}
		if('${isEndReply}' == 'true'){
			nodeNameNew = '回复';
		}
		//if(confirm("确定要"+nodeNameNew+"吗？")){
			var obj = document.getElementById("frame1").contentWindow;
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
			var formId = eval(obj.document.getElementById("formId")).value;
			var nextNodeId = document.getElementById("neNodeId").value ;
			var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
			json = JSON2.stringify(trueJSON.pdfjson);
			
			if(json == null ||json == "null" || json == 'null'){
				alert("表单未加载完成");
				return ;
			}
			formjson = trueJSON.formjson;
			var o = '${isbt}';
			if(o){
				var error =obj.isCheckBt(o.split(";"));
				if(error!=""){
					alert(error);
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
					return;
				}
			}
			
			var ismeeting =trueJSON.formjson.ismeeting;
			if(ismeeting == 'ismeeting'){
				var b=checkmeetingtime(trueJSON);
				if(b=='1'){
					document.getElementById(noid_anid).disabled='';
					return false;
				}
			}
			var tagValue = getProValue();
			var pageValue =tagValue;
			if(formjson != ''){
				pageValue = mergeJson(tagValue,formjson);
			}
			if(obj.document.getElementById("jzk1")){
				obj.zzfs();
			}
			var params = pageValue;
			// from  url 
			params['itemId'] = '${itemId}';
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
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
					//alert('AJAX调用错误(table_end.do)');
				},
				data : params,
				success : function(msg) {
					if(msg == 'no'){
						alert("办结有误，请联系管理员！");
						if(document.getElementById(noid_anid)){
							document.getElementById(noid_anid).disabled='';
						}
						return false;
					}else if(msg == 'yes'){
						if(obj.document.getElementById("jzk1")){
							obj.fsjs();
						}
						if(nextNodeId=='957E81C4-E9EE-49FC-A34E-532D9E5CD00F'){
							updateMeetingState(1);
						}
						if(document.getElementById(noid_anid)){
							document.getElementById(noid_anid).disabled='';
						}
						
						if('${childWfAfterNode}' != 'true'){
							if('${isEndReply}' == 'true'){
								alert("回复成功！");
							}else{
								alert("完成办结！");
							}
							
						}else{
							alert("发送成功！");
						}
						if(ismeeting == 'ismeeting'){
							parent.closeTabsInLayer();
						}else{
							if('${isFirst}' == 'true' && '${finstanceId}'==''){
								parent.closeTabsInLayer();
							}else{
								parent.closeTabsInLayer();
							}
						}
					}
				}
			});
		/* }else{
			if(bjid!=null && bjid=='end_auto'){		//表示自动办结:直接办结接口
				parent.closeTabsInLayer();
			}
			if(document.getElementById(noid_anid)){
				document.getElementById(noid_anid).disabled='';
			}
		} */
	}

	function checkmeetingtime(trueJSON){
			var meetingBeginTime = trueJSON.formjson.meeting_Begin_Time;
			var meetingEndTime = trueJSON.formjson.meeting_End_Time;
			var roomName = trueJSON.formjson.roomName;
			var date = new Date();
			var b=0;
			//alert(trueJSON.formjson.userId);
			//alert(trueJSON.formjson.departmentId);
			var meetingRscount = trueJSON.formjson.meeting_Rscount;
			//alert("验证人数");
			if (isNaN(meetingRscount)){ 
				alert("会议人数为数字！"); 
				b=1;
				return b;
			}
			//alert("验证时间大于当前时间");
			if(meetingBeginTime.replace(/(^\s*)|(\s*$)/g, "") < (new Date()).Format("yyyy-MM-dd HH:mm:ss")){
				alert("会议时间应大于当前时间！");
				b=1;
				return b;
			}
			//alert("验证两个时间");
			if(meetingBeginTime.replace(/(^\s*)|(\s*$)/g, "") >= meetingEndTime.replace(/(^\s*)|(\s*$)/g, "")){
				alert("会议结束时间应大于会议开始时间！");
				b=1;
				return b;
			}	
			$.ajax({
				type : 'POST', 
				url:'${ctx}/meeting_checkMeetingTime.do',
				data:{'meetingBeginTime':meetingBeginTime,'meetingEndTime':meetingEndTime,'roomName':roomName},		
				cache: false,
				async:false,
				error:function(){
					alert("获取会议信息失败");
				},
				success : function(mes) {
					if(mes=="false"){
						alert("会议时间冲突，请重新选择时间！");
						b=1;
					}
				}
			});
		 return b;
	}
	
	//对Date的扩展，将 Date 转化为指定格式的String 
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).Format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).Format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.Format = function(fmt) 
	{ 
	  var o = { 
	    "M+" : this.getMonth()+1,                 //月份 
	    "d+" : this.getDate(),                    //日 
	    "H+" : this.getHours(),                   //小时 
	    "m+" : this.getMinutes(),                 //分 
	    "s+" : this.getSeconds(),                 //秒 
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	    "S"  : this.getMilliseconds()             //毫秒 
	  }; 
	  if(/(y+)/.test(fmt)) 
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	  for(var k in o) 
	    if(new RegExp("("+ k +")").test(fmt)) 
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	  return fmt; 
	}
	
	function invalid(){		
		if(isopened){
			alert("已有别人提交请勿办理");
			return;
		}
		//作废： 删除待办, 修改办件状态位
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
						parent.closeTabsInLayer();
					}
				}
			});
		}
	}
	
	
	
	//提交盖章打印信息到方正电子签章系统
	function subPrintCopies(){
		var instanceId = '${instanceId}';
		var formId = '${formId}';
		var retMsg = 'noSealedFile';
		var zsbm = document.getElementById("frame1").contentWindow.getFormTagValue('zsdw');//主送部门
		var csbm = document.getElementById("frame1").contentWindow.getFormTagValue('csdw');//抄送部门
		$.ajax({
			url : 'table_getSendDeptNameAndPrintNum.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'instanceId':instanceId,
				'formId':formId,
				'zsbm':zsbm,
				'csbm':csbm
			},
			error : function(e) {  
				alert('AJAX调用错误(table_getSendDeptNameAndPrintNum.do)');
				return;
			},
			success : function(msg) {
				var msgArr = msg.split('~~');
				var deptNames = msgArr[0];
				var copyNums = msgArr[1];
				var deptCount =deptNames.split(';').length;
				$.ajax({
					url : '${ctx}/table_getAllPdfPath.do',
					type : 'POST',  
					cache : false,
					async : false,
					data : {
						"instanceId":instanceId				
					},
					error : function() {
						alert('AJAX调用错误(table_getAllPdfPath.do)');
					},
					success : function(msg) {  
						if(msg!=null && msg!=''){
							document.getElementById('allPdfPath').value = msg;
						}
					}
				});
				var allPdfPath = document.getElementById('allPdfPath').value;
	    		var allPdfPathJson = eval(allPdfPath);
	    		var jsonLen = allPdfPathJson.length;
	    		var founderSealObj = document.getElementById("sealObj");
	    		var founderSealUrl = '${founderSealUrl}';
	    		//deptNames = '玄武区测试单位';
	    		//copyNums = '12';
	    		//deptCount = '1'
	    		for(var i = 0; i < jsonLen; i++){
	    			retMsg = "";
	    			var isSeal = allPdfPathJson[i].isSeal;
	    			var id = allPdfPathJson[i].id;
	    			var path = document.getElementById("frame1").contentWindow.getFileLocalPath(id);
	    			if(isSeal == '1'){
	    				var pdfUrl = allPdfPathJson[i].pdfUrl;
	    				var fileName = allPdfPathJson[i].name;
	    				//var path = document.getElementById("frame1").contentWindow.getFileLocalPath(pdfUrl);
	    				//var founderSealObj = new ActiveXObject("VisualSealStampCom.PDFSeal");    	    				
	    				var docId = founderSealObj.GetDocID(path);
	    				var printInfoXml = "";//打印信息xml
	    				if(docId != ""){
	    					printInfoXml = "<?xml version='1.0'  encoding='GB2312'  ?>";
	    					printInfoXml += "<Doc version='1.0'>";
	    					printInfoXml += "<DocumentID>" + docId + "</DocumentID>";
	    					printInfoXml += "<Receivers>" + deptNames + "</Receivers>";
	    					printInfoXml += "<PrnNums>" + copyNums + "</PrnNums>";
	    					printInfoXml += "<Count>" + deptCount + "</Count>";
	    					printInfoXml += "<SendType>" + 1 + "</SendType>";
	    					printInfoXml +=  "</Doc>";
	    					//对文件进行多米操作
	    	    			var detachRet = founderSealObj.DetachPdfEx(path, 1, 0, "<NoShowMark>1</NoShowMark>");
	    	    			if(detachRet == 0){
	    	    				//上传脱密文件
	    	    				document.getElementById("frame1").contentWindow.uploadLocalFile(id,path,'${curl}/attachment_uploadFile4Widget.do?isDetach=1');
	    	    			}
	    				}
	    				// 生成xml文件,用于向服务器上载
	    				var strXmlFileName = "c:/1.xml";
	    				//var objPubFunc = new ActiveXObject("StampPubCom.StampPubFuncCom"); 	
	    				var xmlRet = founderSealObj.BSTR2File(printInfoXml, strXmlFileName);
	    				if (xmlRet != 0 ){
	    					strErrMsg = founderSealObj.GetErrorMessage();
	    			        //alert ("重新生成xml信息文件出错原因:" + strErrMsg);
	    					alert ("方正电子公章生成xml信息文件出错！");
	    					return;
	    				}
						//上传盖章文件打印信息
	    				var retXml = "";
	    				//var objPostRecv = new ActiveXObject("ASPCom.PostRecv");
	    				var nRet = founderSealObj.HTTPUploadFileEx(strXmlFileName, founderSealUrl + '/extend/interfaces/PrintSerial2DB.aspx', retXml);
	    				retXml = founderSealObj.GetHTTPUploadFileExRetMsg();//获取上传结果
	    				retMsg = retXml;
	    			}else{
	    				retMsg="noSealedFile";
	    			}
	    		}    			
			}
		});
		return retMsg;
	}
	
	//发文中发送-先保存表单再发送
	function send(){
		if(isopened){
			alert("已有别人提交请勿办理");
			return;
		}
		//检测是否已经上传附件
		var isSed = true;
		var obj = document.getElementById("frame1").contentWindow;
		var instanceId = eval(obj.document.getElementById("instanceId")).value;
		var syntoNotice = 0 ;//是否短信提醒
		if(document.getElementById("syntoNotice")){
			if(document.getElementById("syntoNotice").checked){
				syntoNotice=1;
			} 
		}
		$.ajax({
			url : '${ctx}/attachment_getAttachmentCount.do',
			type : 'POST',  
			cache : false,
			async : false,
			data : {
				"instanceId":instanceId				
			},
			error : function() {
				alert('AJAX调用错误(attachment_getAttachmentCount.do)');
			},
			success : function(msg) {  
				if(msg!=null && msg=='success'){
					isSed = true;
				}else{
					if(confirm("未上传附件或上传文件中无可预览文件,是否继续操作？")){
						
					}else{
						isSed = false;
					}
				}
			}
		});
		if(!isSed){
			return;
		}
		if(isSed){
			if(obj.document.getElementById("jzk1")){
				obj.zzfs();
			}
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
					params['itemId'] = '${itemId}';
					//提交收文打印份数信息到方正公章系统
					var subPrintCopiesRet = subPrintCopies();
					if("success" != subPrintCopiesRet && "noSealedFile" != subPrintCopiesRet){
						alert("同步信息到公章系统失败，不能发送，请联系管理员！");
						return;
					}
					$.ajax({
						url : 'table_end.do',
						//url : 'table_end.do?instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&finstanceId=${finstanceId}&formId='+formId,
						type : 'POST',  
						cache : false,
						async : false,
						data : params,
						//data : 'json='+ json +'&oldProcessId=${oldProcessId}&isWriteNewValue=${isWriteNewValue}'+tagValue,
						error : function() {
							if(obj.document.getElementById("jzk1")){
								obj.fsjs();
							}
							//alert('AJAX调用错误(table_end.do)');
						},
						success : function(msg) {  
							var params = pageValue;
							// form data 
							params['instanceId'] = instanceId;
							params['workFlowId'] = workFlowId;
							params['processId'] = '${processId}';
							params['nodeId'] = '${nodeId}';
							params['formId'] = formId; 
							params['syntoNotice'] = syntoNotice;
							if(msg != ''){
								$.ajax({  
									url : 'table_sendDoc.do',
									type : 'POST',   
									cache : false,
									async : false,
									//	data : 'instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&formId='+formId+tagValue,
									data : params,
									error : function() {  
										if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}
										//alert('AJAX调用错误(table_sendDoc.do)');
									},
									success : function(msg) {
										var res = eval("("+msg+")");
										if(res.result =='1'){							//未选择部门时,直接办结办件,但是徐要给出提醒
											alert("未选择主送或者抄送部门,流程办结！");
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											if('${isFirst}' == 'true'){
												parent.closeTabsInLayer();
											}else{
												parent.closeTabsInLayer();
												if('${isPortal}'=='1'){
													parent.closeTabsInLayer();
												}
											}
										}else if(res.result =='2'){				//异常失败
											alert("发送异常！");
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
										}else if(res.result =='0'){				//发送成功了
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											if('${isFirst}' == 'true'){
												parent.closeTabsInLayer();
											}else{
												parent.closeTabsInLayer();
												if('${isPortal}'=='1'){
													parent.closeTabsInLayer();
												}
											}
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
		var url = '${curl}/selectTree_showDepartment.do?isSend=1&deptId=${deptId}&t='+new Date();
		var returnVal = '';
		SetIsOpenDlg(1)
		if(top.window && top.window.process && top.window.process.type){
			console.info("封装打开方式");
		    var ipc = top.window.nodeRequire('ipc');
		    var remote = top.window.nodeRequire('remote');
		    var browserwindow = remote.require('browser-window');
            var winProps = {};
            winProps.width = 1000;
            winProps.height = 480;
            winProps['web-preferences'] = {'plugins':true};
            var focusedId = browserwindow.getFocusedWindow().id;
		    var win = new browserwindow(winProps);
			//console.info(focusedId);
	        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
		    //win.openDevTools();
			win.on('closed',function(){
			    win = null;
			    SetIsOpenDlg(0)
			});				    
		    ipc.on('message-departmentTree',function(args){
				if(win){
					SetIsOpenDlg(0)
	                win.close();
					win = null;
					var ret = args;
					doSendFile(ret,pageValue,instanceId,workFlowId,formId);
				}
		    });
		}else{
			var WinWidth = 960;
			var WinHeight = 600;
			console.info("window.open普通打开方式");
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
			    	SetIsOpenDlg(0)
					//console.info(window.returnValue);
				    clearInterval(loop);
				    //---------------------------
				    var ret = window.returnValue;
					doSendFile(ret,pageValue,instanceId,workFlowId,formId);
			    }
		    },500);
		}
		
	}
	
	function doSendFile(returnVal,pageValue,instanceId,workFlowId,formId){
		if(returnVal){
			var res = returnVal.split("*");
			document.getElementById('xtoName').value = res[0];
			var params = pageValue;
			var ids = res[0].split(",");
			// ids 的个数等于1  直接设置为回复人 , 超过1 则弹出框 选择 回复人
			var isend = false;
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
						//alert('AJAX调用错误(table_doFileDoc.do)');
					},
					success : function(msg) {
					}
				});
		}else{
			if(document.getElementById(noid_anid)){
				document.getElementById(noid_anid).disabled='';
			}
		}
	}
	
	//办件发送下一步
	function sendNext(self_loop,canSendMsg){
		//将盖完章后的文件上传到服务器，是否盖章，是否上传，都是有控件端判断
		upFileToServer();
		
		var closeframe= $("#closeframe").val();
		//意见json-----start--------
		var json = '';
		var formjson = ''; //表单元素
		var isHaveFormjson = ''; 
		var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
		json = JSON2.stringify(trueJSON.pdfjson);
		formjson = trueJSON.formjson;
		isHaveFormjson = 'true';
		//意见json------end--------
		 var isSmsRemind = 0 ;//是否短信提醒
		if(document.getElementById("isSmsRemind").checked){
			isSmsRemind=1;
		} 
		var nextNodeId = noid_anid ;
		var neRouteType = document.getElementById("neRouteType").value ;
		var sendMsgId = document.getElementById("sendMsgUserId").value;
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
		}else if(neRouteType == 3){
			xtoName = document.getElementById("toName").value;
		}else if(neRouteType == 4 || neRouteType == 5 || neRouteType == 6){
			xtoName = document.getElementById("toName").value;
		}
		var tagValue = getProValue();
		var pageValue = tagValue;
		if(formjson != ''){
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
		var wcsx=$('#szsx').val();
		if(!wcsx){
			wcsx="";
		}
		if(isPassCheck){
			var instanceId = eval(obj.document.getElementById("instanceId")).value;
			var formId = eval(obj.document.getElementById("formId")).value;
			var workFlowId = eval(obj.document.getElementById("workFlowId")).value;
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
						'nextNodeId':noid_anid,
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
							var no = '${no}';
							var params = pageValue;
							var modId = document.getElementById("modId").value;
							var matchId = document.getElementById("matchId").value;
							var dicId = document.getElementById("dicId").value;
							var dicValue = document.getElementById("dicValue").value;
							//from  url
							params['wcsx'] = wcsx;
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
							params['self_loop'] = self_loop;
							params['no'] = no;
							params['modId'] = modId;
							params['matchId'] = matchId;
							params['dicId'] = dicId;
							params['dicValue'] = dicValue;
							params['canSendMsg'] = canSendMsg;
							params['sendMsgId'] = sendMsgId;
								setTimeout(function(){
									$.ajax({  
										url : '${ctx}/table_sendNext.do',
										type : 'POST',   
										cache : false,
										async : false,
										data : params,
										error : function() {  
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											//alert('AJAX调用错误(table_sendNext.do)');
											closeCover();
										},
										success : function(msg) {
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
											if(msg == 'over'){
												alert("该待办已经被办理,请重新刷新待办列表页面！");
												return false;
											}else if(msg == 'no'){
												alert("发送下一步有误，请联系管理员！");
												return false;
											}else if(msg == 'yes'){
												if(closeframe=='0'){
													//如果是续办办件，更新原办件状态
													if('${isFirst}' == 'true' && '${origProcId}' != null && '${origProcId}' != ''){
														updateOrigProc('${instanceId}','${origProcId}');
													}
													if('${directSend}' == 'true'){
														window.close();
														window.returnValue='choseCurrentprocessId=${processId}&instanceId='+instanceId;
													}else{
														if('${isChildWf}' == 'true'){	//子流程
															window.close();
															window.returnValue='choseparent';
														}else{
															var itemid= '${itemId}';
															if(('${isFirst}' == 'true' || '${isCy}' == 'true' || '${stepIndex}'=='1') && '${cType}' != '1'){
																parent.closeTabsInLayer();
																try{
																	document.getElementById('Mcontainer').contentWindow.location.reload(true);
																}catch(e){
																	
																}
															}else{
																parent.closeTabsInLayer();
																if('${isPortal}'=='1'){
																	parent.closeTabsInLayer();
																}
															}
														}
													}
													
												}else if(closeframe==1){
													goExChangeSendValue();
												}
											}
										}
									});	
							},1000);
						}
					}
				});
			}else {  
				if(obj.document.getElementById("jzk1")){
					obj.fsjs();
				}
				alert("流程标题不能为空!");  
				if(document.getElementById(noid_anid)){
					document.getElementById(noid_anid).disabled='';
				}
			}
		}
	}
	
    //保存当前节点表单
	function operateForm(operate,status){
		//将盖完章后的文件上传到服务器，是否盖章，是否上传，都是有控件端判断
    	upFileToServer();
		
		if(isopened){
			alert("已有别人提交请勿办理");
			return;
		}
		if('1' == operate){
			var obj = document.getElementById("frame1").contentWindow;
			var formId = obj.document.getElementById("formId").value;
			var o = '${isbt}';
			if(o){
				var error = obj.isCheckBt(o.split(";"));
				if(error!=""){
					alert(error);
					return;
				}
			}
		}
		
		//意见json-----start--------
		var json = '';
		var formjson = ''; //表单元素
		var isHaveFormjson = ''; 
		//修改的到子流程的修改json不保存
		var trueJSON = document.getElementById("frame1").contentWindow.getPageData();
		json = JSON2.stringify(trueJSON.pdfjson);
		formjson = trueJSON.formjson;
		isHaveFormjson = 'true';
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
						if(filedValue !="" &&  filedValue != undefined ){
							isHaveTitle +=	filedValue;
						}else if( filedValue == undefined ){
							isHaveTitle = "ishave";
							break;
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
		var no = '${no}';
		params['no'] = no;
		if(isHaveTitle != null && isHaveTitle !="" && isHaveTitle != 'undefined'){
			$.ajax({
				url : '${ctx}/table_onlySave.do',
				type : 'POST',  
				cache : false,
				async : false,
				data : params,
				error : function() {
					//alert('AJAX调用错误(table_onlySave.do)');
				},
				success : function(msg) {   
					if(msg != ''){
						if(operate==1){
							parent.closeTabsInLayer();
						}else{
							alert("保存成功!");
						}
						//如果是续办办件，更新原办件状态
						if('${isFirst}' == 'true' && '${origProcId}' != null && '${origProcId}' != ''){
							updateOrigProc('${instanceId}','${origProcId}');
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
			if(obj.document.getElementById("jzk1")){
				obj.fsjs();
			}
			alert("流程标题不能为空!");
		}
	}
	
    //获取属性和属性值--------！！！！！！！！！-pdf版时，列表类型有待修改-！！！！！！！！！----
	function getProValue(){
		var tagHaveName = '${tagHaveName}';//列表型
		var tagName = '${tagName}';//非列表型
		var specialTagName = '${specialTagName}';//特殊类型的input
		var allValue = '${value}';
		var tagValue= "";
		var tags ={};
		var obj=document.getElementById("frame1").contentWindow;
		if(!!tagHaveName){
			var tagHaveNames= new Array(); //定义一数组
			tagHaveNames = tagHaveName.split(","); //格式：jl_gzsj,jl_gzdd,jl_zw,jl_xz,ry_xm,ry_nl,ry_sr
			for(var j=0;j<=tagHaveNames.length-1;j++){   
				if(obj.document.getElementsByName(tagHaveNames[j])){
					var tagHaves = new Array();
					tagHaves = obj.document.getElementsByName(tagHaveNames[j]);//所有的属性
					//tagHaveValue += "&"+ tagHaveNames[j]+"=";  //格式：jl_gzsj=a,b,c&jl_gzdd=d,f,g
					var tagHaveValue= "";
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
					tags[tagHaveNames[j]] = tagHaveValue.substring(0,tagHaveValue.length-1);
				}
			}  
		}
		
		if(tagName){
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
						tags[tagNames[i]] = foval;
					}else{
						//pdf版已经没有表单，getElementById读取不到值
						tagValue += "&"+tagNames[i]+"="+encodeURI(valObj[tagNames[i].toLowerCase()]);
						tags[tagNames[i]] = valObj[tagNames[i].toLowerCase()];
						
					}
				}  
			}
			if(specialTagName){
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
						tags[specialTagNames[m]] =val.substring(0,val.length-1);
					}
				}  
		}
		return tags;
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
		setTimeout(function(){
	   		if('${isFirst}' == 'false'){
	   			// 显示目录
	   			$(this).addClass("fouce");
				$('.bl_n_nav', this).show();
				if(null != '${pages}' && '' != '${pages}'){
					$('#mliframe').show();
				}
	   		}
		},2000);		
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
	//$('.content').height(bH-30); 
	$('.frame1').height(bH-60);  
	$('.frame1').width(bW);
	
	
	$('.frame2').height(bH-60);  
	$('.frame2').width(bW);
	//初始化右箭头状态
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
	//$('.content').height(bH-30);			
	//$('.frame1').height($('.content').height()-50);
	$('.frame1').height(0);
	console.log($('.bl_tab'));		
	$('.frame1').width(bW);
	$('.bl_tab').each(function(i,item){		
		console.log(i)		
		console.log($(item).hasClass('bl'));		
		if(i == 1){		
			if($(item).hasClass('bl')){		
				$('.frame2').height(bH-60);		
				$('.frame2').width(bW);		
			}		
		} else if(i==2){		
			if($(item).hasClass('bl')){		
				$('.frame1').height(bH-60);		
				$('.frame1').width(bW);		
			}		
		}		
	});	
    if($('body').width()<1340){
    	$('.quick_links_wrap').addClass('easy_wrap');
    }else{
    	$('.quick_links_wrap').removeClass('easy_wrap');
    }	
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
});

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
	if(value == 1){
		if(document.getElementById("leftPage")){
			document.getElementById("leftPage").className="b_disable";
		}
		if(document.getElementById("rightPage")){
			document.getElementById("rightPage").className="";
		}
	}else if(value == imageCount){
		if(document.getElementById("leftPage")){
			document.getElementById("leftPage").className="";
		}
		if(document.getElementById("rightPage")){
			document.getElementById("rightPage").className="b_disable";
		}
	}else{
		if(document.getElementById("leftPage")){
			document.getElementById("leftPage").className="";
		}
		if(document.getElementById("rightPage")){
			document.getElementById("rightPage").className="";
		}
	}
	document.getElementById("frame1").contentWindow.ymChange(value);
	//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
	//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+value)).className="hot";
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
		var val =  parseInt(yys) - 1 ;
		document.getElementById("frame1").contentWindow.ymChange(val);
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
                getScrollTop();
		if(val == 1){
			document.getElementById("leftPage").className="b_disable";
		}
		if(val < imageCount){
			document.getElementById("rightPage").className="";
		}
		yys=val;
		/*if(yys != 1){
			$('#flexibleForm').css("display","");
			$('#turnleft').css("display","");
			$('#turnright').css("display","");
		}else{
			if('${isFlexibleForm}' == '1'){
				$('#flexibleForm').css("display","none");
				$('#turnleft').css("display","none");
				$('#turnright').css("display","none");
			}
		}		 */
		
	}/* else{
		if('${isFlexibleForm}' == '1'){
			$('#flexibleForm').css("display","none");
			$('#turnleft').css("display","none");
			$('#turnright').css("display","none");
		}
	} */
}

function changePageToRight(){
	if(yys<imageCount){
		var val = parseInt(yys) + 1 ;
		document.getElementById("frame1").contentWindow.ymChange(val);
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
                getScrollTop();
		if(val > 1){
			document.getElementById("leftPage").className="";
		}
		if(imageCount == val){
			document.getElementById("rightPage").className="b_disable";
		}
		 yys=val;
		 /*if(yys != 1){
			$('#flexibleForm').css("display","");
			$('#turnleft').css("display","");
			$('#turnright').css("display","");
		}else{
			if('${isFlexibleForm}' == '1'){
				$('#flexibleForm').css("display","none");
				$('#turnleft').css("display","none");
				$('#turnright').css("display","none");
			}
		} */
	}
}

function getScrollTop(){
    /* var conDiv = $("#mliframe").contents().find(".bl_nav_zj"),
        scrollTo= $("#mliframe").contents().find(".hot");

        $(conDiv).animate({scrollTop: $(scrollTo).offset().top - $("#mliframe").offset().top + $(conDiv).scrollTop()}); */
}

//关闭灰色JS遮罩层和操作窗口
function closeCover() {
	$("#fullbg").css("display", "none");
	$("#dialog").css("display", "none");
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
<script type="text/javascript" src="${ctx}/socket/json2.js"></script>
<script type="text/javascript" src="${ctx}/socket/socket.io.js"></script>
<script>
var isUserId = false;
var sock_connected=false;
//var pix=generateMixed(3)+'_';
var pdfPath = '${pdfPath}';
var fileId = '${processId}';
pdfPath = pdfPath.substr(pdfPath.lastIndexOf("/")+1, pdfPath.length);
var sock_uuid='${allInstanceId}';
if(null == sock_uuid || "" == sock_uuid){
	sock_uuid = '${instanceId}';
}

var noCheck = '${noCheck}';
if(noCheck){		//吴需要验证
	sock_uuid = parseInt(1000000*Math.random());
}
var sock_origin='${socketIp}';

var sock_name = '${loginEmployee.employeeName}';
var sock_device="workflow";
var live=false;
var reconn=true;
var sock;
var curtTime = Date.now();
var hasGetMsgOnUse = false;

function connect(){
	//sock= io.connect(sock_origin);
	sock = io.connect(sock_origin,{
		'force new connection':true,
		'reconnection delay':18000,
		'max reconnection attempts':10000
	})
}
connect();

sock.on('connect', function () {
	console.log('conneced=============');
	isDeal = true;
	sock_connected=true;
	//注册
	//sock.emit('signin',{userid:sock_uuid,device:sock_device,realname:sock_name});
	//心跳
	heartbeat();
	//检测是否使用中
	//checkUsed();
	signIn();

	//openFile();
});

sock.on('online',function(data){

if(data.uuid == sock_uuid){
	heartbeat();
	openFile();
}
	//console.log(data);
});

function signIn(){
	console.log('singin_',sock_uuid);
	console.log(sock_device);
	sock.emit('signin',{userid:sock_uuid,realname:sock_name,device:sock_device,time:curtTime});
}

sock.on('message', function (e) {
	alert(e.from+' say:'+e.alert);
});

sock.on('used', function (e) {
	console.log(sock_name,e.realname);
	if(sock_name!=e.realname){
		if(!hasGetMsgOnUse){
			alert(e.userName+' 正在办理中,当前模式下仅可阅读办件');
			document.getElementById("buttonList").style.display="none";
			document.getElementById("msgList").style.display="none";
			document.getElementById("t_q").style.display="none";
			document.getElementById("t_m").style.display="none";
			document.getElementById("t_e").style.display="none";
			document.getElementById("t_s").style.display="none";
		}
		hasGetMsgOnUse=true;
	}
});

//心跳,验证是否保持连接
sock.on('heartbeat', function (e) {
	live=true;
	setTimeout(function(){
		heartbeat();
	},15*1000);
});

sock.on('disconnect', function (data) {
	//console.log('disconnect_',sock_uuid);
	//var msg={userid:sock_uuid,device:sock_device,realname:sock_name};
	//sock_connected=false;
	//sock.emit('closeFile',msg)
	//if(reconn==true) connect();
});

sock.on('fileInUse',function(data){
	console.log('fileInUse',data.userid,sock_uuid);
	if(data.userid == sock_uuid && curtTime == data.time && sock_name!=data.curtUserName){
		if(!hasGetMsgOnUse){
			alert(data.curtUserName+' 正在办理中,当前模式下仅可阅读办件');
			document.getElementById("buttonList").style.display="none";
			document.getElementById("msgList").style.display="none";
		}
		hasGetMsgOnUse=true;
	}
});

function heartbeat(){
	live=false;
	sock.emit('heartbeat', 'h+');
	setTimeout(function(){
		if(live!=true){
			//if(sock) sock.disconnect();
		}
	},18*1000);
}
function generateMixed(n) {
	var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	var res = "";
	for(var i = 0; i < n ; i ++) {
		var id = Math.ceil(Math.random()*35);
	 	res += chars[id];
	}
	return res;
}

function checkUsed(){
	var msg={userid:sock_uuid,device:sock_device,realname:sock_name};
	if(sock_connected===true) sock.emit('using', msg);
}
function sendMsg(){
	var msg={userid:sock_uuid,device:sock_device,realname:sock_name};
	if(sock_connected===true) sock.emit('message', msg);
}
window.onbeforeunload=function(){
	//关闭链接
	if(sock_connected===true){
		var msg={userid:sock_uuid,device:sock_device,realname:sock_name};
		sock.emit('release to use', msg);
		sock.disconnect();
	}
};

setTimeout(outTimeColse,30*60*1000);

function outTimeColse(){
	window.close();
}

function updateOrigProc(instanceId, origProcId){
	$.ajax({   
		url : '${ctx}/table_updateOrigProcState.do',
		type : 'POST',   
		cache : false,
		async : false,
		data : {'instanceId':instanceId, 'origProcId':origProcId},
		error : function() {  
			alert('AJAX调用错误(table_updateOrigProcState.do)');
		},
		success : function(result) { 
		}
	});
}
/*setTimeout(function(){
	supmax()
},200)*/
function supmax(){
	//debugger
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

//关注办件
function followFile(){
	$.ajax({
		url : '${ctx}/tableExtend_addFollowShip.do',
		type : 'POST',  
		cache : false,
		async : false,
		data : {'instanceId':'${instanceId}'},
		success : function(msg) {
			if(null != msg && msg == 'success'){
				document.getElementById("gztx").style.cssText="display:none";
				document.getElementById("ygz").style.cssText="display:";
			}else{
				alert("关注失败,请稍后重试.");
			}
		}
	});
}

//取消关注提醒
function  deleteFile(){
	$.ajax({
		url : '${ctx}/tableExtend_deleteFollowShip.do',
		type : 'POST',  
		cache : false,
		async : false,
		data : {'instanceId':'${instanceId}'},
		success : function(msg) {
			if(null != msg && msg == 'success'){
				document.getElementById("gztx").style.cssText="display:";
				document.getElementById("ygz").style.cssText="display:none";
			}else{
				alert("关注失败,请稍后重试.");
			}
		}
	});
}

//获取第一个附件的名称作为办件标题
function getAttachBt(bt){
	if(!(bt != null && bt != '' && bt.indexOf(':') >= 0)){
		var docguid = '${docguid}'+'attzw';
		var nodeId = '${nodeId}';
		$.ajax({
			url: '${ctx}/attachment_getDocNameAsTitle.do',
               type: 'POST',
               cache: false,
               async: false,
               data:{
               	 'docguid':docguid, 'nodeId':nodeId
               },
  			    error: function(){
  			        alert('AJAX调用错误');
  			    },
  			    success: function(result){
  			    	bt = result;
  			}
  		});
	}
	if(bt != null && bt != '' && bt.indexOf(':') >= 0){
		var btArr = bt.split(':');
		var tagName = btArr[0];
		var tagValue = btArr[1];
		var origVal = document.getElementById("frame1").contentWindow.getFormTagValue(tagName);
		if(origVal == null || origVal == ''){
			document.getElementById("frame1").contentWindow.setFormValueByTag(tagName, tagValue);
		}
	}
}

/**
 * 传阅
 */
function handRound(){
	var isCyBtn = '${showCyBtn}';
	if(isCyBtn == 'false'){
		alert('办件未生成，不能传阅，请先下发或暂存！');
		return;
	}
	var url4UserChose='${curl}/ztree_showDepartmentTree.do?status=all&t='+new Date();
	var url = url4UserChose;
	var WinWidth = 860;
	var WinHeight = 600;
	//var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
	SetIsOpenDlg(1);
	var obj = document.getElementById("frame1").contentWindow;
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
				var rets = '';
				var ret = '';
				var canSendMsg = '';
				var sendMsgId = '';
				if(args.split("#").length>1){
					canSendMsg = args.split("#")[1];
					rets = args.split("#")[0];
					if(rets.split("@").length>1){
						ret = rets.split("@")[0];
						sendMsgId = rets.split("@")[1];
					}else{
						ret =  rets;
					}
				}else{
					rets = args;
					if(rets.split("@").length>1){
						ret = rets.split("@")[0];
						sendMsgId = rets.split("@")[1];
					}else{
						ret = rets;
					}
					
				}
				document.getElementById('sendMsgUserId').value = sendMsgId;
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
			var url4UserChose='${curl}/ztree_showDepartmentTree.do?exchange='+exchange+'&click=true&nodeId=${nodeId}&send=${send}&routType='+msg;
			var url = url4UserChose;
			var WinWidth = 800;
			var WinHeight = 600;
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
	SetIsOpenDlg(1)
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
		var win = new browserwindow(winProps);
		//console.info(focusedId);
		win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
		//win.openDevTools();
		win.on('closed',function(){
			win = null;
			SetIsOpenDlg(0)
		});				    
		ipc.on('message-departmentTree',function(args){
			if(win){
				SetIsOpenDlg(0)
				win.close();
				win = null;
				var ret = args;
				if(ret){
					doBack(ret);
				}
			}
		});
	}else{
		console.info("window.open普通打开方式");
		var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		var loop = setInterval(function(){
			if(winObj.closed){
				SetIsOpenDlg(0)
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

function SetIsOpenDlg(status) {
	try{
		document.getElementById("frame1").contentWindow.SetIsOpenDlg(status);
	}catch (e) {
	}
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
</html>
