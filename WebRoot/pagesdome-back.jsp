<!DOCTYPE html>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
    <%@ include file="/common/header.jsp"%>
    <%@page import="net.sf.json.JSONArray"%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctx}/assets-common/css/frame.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${ctx}/assets-common/css/pagedome.css" rel="stylesheet" type="text/css" media="screen"/>
    <script src="${ctx}/assets-common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
    <!-- 将后台的参数放入json -->
    <script type="text/javascript">
    	var jsonurl='${ctx}/table_getFormValueAndLocationOfWeb.do?no=${no}';
    	var flexibleurl = '${ctx}/table_getFlexibleFormValue.do';
    	var personalComments='${sessionScope.personalComments}',
    	userName = '${sessionScope.userName}',
    	isWriteNewValue = '${isWriteNewValue}',
    	isUploadAttach = '${isUploadAttach}',
    	isFlexibleForm = '${isFlexibleForm}',
    	newInstanceId = '${newInstanceId}',
    	formPageJson = '${formPageJson}',
    	commentJson = '${commentJson}',
    	workflowId = '${workFlowId}',
    	instanceId = '${instanceId}',
    	toUserName = '${toUserName}',
    	isContinue = '${isContinue}',
    	origProcId = '${origProcId}',
    	imageCount = '${imageCount}',
    	processId = '${processId}',
    	loginname = '${loginname}',
    	pdfurl = '${allPdfPath}',
    	dicValue = '${dicValue}',
    	matchId = '${matchId}',
    	sealUrl = '${sealurl}',
    	isOver = '${isOver}',
    	userId = '${userId}',
    	nodeId = '${nodeId}',
    	formId = '${formId}',
    	status = '${status}',
    	usbkey = '${usbkey}',
    	title = '${title}',
    	modId = '${modId}',
    	dicId = '${dicId}',
    	isbt = '${isbt}',
    	ctx = '${ctx}',
    	toUserId = '${toUserId}';
    	var methodurl = "${curl}/pdfocx/method.html";
    </script>
</head>
<body>
    <div class="tw-wrap tw-pagedome">
        <div class="dh">
           <div class="dh_tab fl"><span>历程</span></div>
           <div class="dh_tab cur fl"><span>办理<em>&nbsp;&nbsp;(${dqNodeName})</em></span></div>
        </div>
        <div class="content">
            <div class="content_nav">
                <div class="btn-group keep-open">
                    <button class="dropdown" data-toggle="dropdown"><i class="tw-icon-th-list"></i>目录</button>
                    <textarea id="filePages" style="display:none;">${filePages}</textarea>
                    <ul role="menu" id="bl_nav_zj" class="dropdown-menu">
                                           
                    </ul>
                </div> 
                <div class="btn-group">
                    <button class="dropdown" onclick="changeClass();" data-toggle="dropdown"><i class="tw-icon-paperclip"></i>附件</button>
                    <div  class="bl_n_nav bl_a_nav fouce" style="display: none;z-index:2;width: 810px; height : 100px;overflow: auto;" id="fjml">
	          			<%-- <div class="bl_nav_fj" >
	 						<trueway:att onlineEditAble="true" id='${instanceId}fj' scanAble='false' docguid='${instanceId}fj' showId='fjshow'  nodeId="${nodeId}"  ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
	          				<%  
	          					Object object =request.getAttribute("atts");
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
		 				  				<trueway:att onlineEditAble="true" id='<%=(instanceId+js.getString(i))%>' docguid='<%=(instanceId+js.getString(i))%>' showId='<%=(js.getString(i)+"show")%>'  nodeId="${nodeId}"  ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
		 					<%
		 							}
		 						}
	    					%>
	    					<c:if test="${instanceId!=allInstanceId }">
    							<trueway:att onlineEditAble="true" id='${instanceId}newfj' docguid='${instanceId}newfj' showId="newfjshow"  nodeId="${nodeId}" ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
    						</c:if>
    						<trueway:att onlineEditAble="true" id='${instanceId}oldfj' docguid='${instanceId}oldfj' showId="oldfjshow"  nodeId="${nodeId}" ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="true" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
	    					</div>
	         			</div> --%>
	            		<div class="bl_nav_bottom"></div>
	        		</div>
               	</div>
                <div class="tw-nav-step ml10">
                	<c:if test="${!(isSend == true && isEndProcess == true && send !=0)}">
                    <label class="tw-form-label" for="">下一步：</label>
                    </c:if> 
                    <a class="tw-btn-secondary tw-btn-sm">返回申请人<i class="tw-icon-arrow-right"></i></a> 
                    <span class="tw-separate">|</span>
                    <a class="tw-btn-green tw-btn-sm">放大</a>  
                    <a class="tw-btn-danger tw-btn-sm">缩小</a>                                
                </div> 
                <span class="tw-separate">|</span> 
                <a class="tw-btn-blue tw-btn-sm">按钮</a>                                 
                <div class="tw-nav-step fr mr10">
                    <a class="tw-btn-orange tw-btn-sm">撤销</a>
                    <a class="tw-btn tw-btn-sm">还原</a>
                </div>  
                <div class="tw-nav-step">
                    <a class="tw-btn-green tw-btn-sm" onclick="enlargeOrNarrowPage('enlarge');" href="javascript:void(0);"><i class="tw-icon-search-plus"></i></a>  
                    <a class="tw-btn-green tw-btn-sm" onclick="enlargeOrNarrowPage('narrow');" href="javascript:void(0);"><i class="tw-icon-search-minus"></i></a>  
                    <a class="tw-btn-green tw-btn-sm" onclick="enlargeOrNarrowPage('enlargeOneTime');" href="javascript:void(0);"><i class="tw-icon-expand"></i></a>  
                    <a class="tw-btn-green tw-btn-sm" onclick="enlargeOrNarrowPage('narrowOneTime');" href="javascript:void(0);"><i class="tw-icon-compress"></i></a>                                                                                        
                </div>                                                                      
            </div>
            <div class="contentWrap">
                <div id="gzl_pageLeftBox"><a id="leftPage" onclick="changePageToLeft()" class="b_disable" style="cursor:pointer;"></a></div>
                <div id="gzl_pageRightBox"><a id="rightPage" onclick="changePageToRight()" style="cursor:pointer;"></a></div>
               	<div class="attachment-box" style="display:none">附件内容</div>
               	<div class="content_box">
	                <div style="width:1024px;margin:0 auto;">
	                    <object id="OCXpdfobj" type="application/x-itst-activex" style="width:1024px;height:1448px;" clsid="{ECCC5C8C-8DA0-4FAC-935A-CD5229A14BCC}" title="ActiveX hosting plugin for Firefox"></object>   
	                </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 右侧悬浮菜单开始  -->
	<div class="quick_links_wrap">
	   <ul class="quick_links" class="quick_links">
		     <li class="active" onclick="setDrawType(0,0,0,0,0);"><a href="#" title="键盘输入"><i class="icon-jpsr"></i><span>键盘输入</span></a></li>	        
			 <li onclick="setColor();">
			     <a href="#"  title="手写"><i class="icon-sx"></i><span>手写</span></a>
			     <div class="popover">
			         <div class="arrow"></div>
			         <div class="popover-content">
			         	 <input type="hidden" name="color" class="popover-color" value="0">
			             <button class="tw-border-black tw-active" data-value="0">黑</button>
			             <button class="tw-border-red" data-value="1">红</button>
			         </div>
			     </div>
			 </li>	        
			 <li onclick="showHandWrite(1);"><a href="#"  title="签批人信息"><i class="icon-qprxx"></i><span>签批人信息</span></a></li>	        
			 <li onclick="setDrawType(3,0,0,0,3);"><a href="#"  title="橡皮擦"><i class="icon-xpc"></i><span>橡皮擦</span></a></li>      
		</ul>
	</div>
	<!-- 右侧悬浮菜单结束  -->
    <script src="${ctx}/assets-common/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/assets-common/js/pagedome.js" type="text/javascript"></script>
	<script>
		$('.JS-attachment-button').click(function(){
			if($('.attachment-box').is(':hidden')){
				$('.attachment-box').show();
			}else{
				$('.attachment-box').hide();
			}		
		});    
    </script>

</body>
</html>