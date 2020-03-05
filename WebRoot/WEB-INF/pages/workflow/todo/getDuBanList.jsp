<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<style>
		.new-htable {
			margin-top:20px;
			width:96%;
			margin-left:3%;
		}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:40px;
		    vertical-align: middle;

		}
		.new-htable tr td{
			text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:40px;
		    vertical-align: middle;
		}
		.new-htable .tw-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:133px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
			display:none;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
	</style>
	<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
		  	<form name="doItemList"  id="doItemList" action="${ctx }/table_getDuBanList.do" method="post" style="display:inline-block;">
				<input type="hidden" name="isPortal" value="${isPortal}"/>
				<input type="hidden" name="favourite" value="${favourite}"/>
				<input type="hidden" name="isShowExp" value="${isShowExp}"/>
				<input type="hidden" name="status" value="${status}"/>
				<input type="hidden" name="departId" value="${departId}"/>
				<input type="hidden" name="isShowWH" value="${isShowWH}"/>
			    <div class="wf-top-tool">
					<c:if test="${favourite == '1'}">
						<a class="wf-btn-danger" onclick="javascript:deleteCollectionBacth();" target="_self">
							<i class="wf-icon-trash" style="display:inline-block;"></i> 取消收藏
						</a>
					</c:if>
					<c:if test="${isShowExp == '1'}">
					<a class="wf-btn-primary" onclick="javascript:downloadBatch();" target="_self">
						&nbsp;导&nbsp;&nbsp;出&nbsp;
					</a>
					</c:if>
				</div>
 	        	<label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" style="width: 75px" value="${wfTitle}" placeholder="输入关键字">
               <%--  <label class="wf-form-label" for="">事项类型：</label>
                <select class="wf-form-select" id="itemName" name="itemName">
                	<option value=""></option>
                	<c:forEach var="m" items='${myPendItems}'>
	 					<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
	 				</c:forEach> 
                </select> --%>
				<label class="wf-form-label" for="">发文日期：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
	            <button class="wf-btn-primary" type="submit" onclick="checkForm('2');">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</form>
		  	<form name="doItemListH"  id="doItemListH" action="${ctx }/table_getDuBanList.do" method="post" style="display:none;">
				<input type="hidden" name="isPortal" value="${isPortal}"/>
				<input type="hidden" name="favourite" value="${favourite}"/>
				<input type="hidden" name="isShowExp" value="${isShowExp}"/>
				<input type="hidden" name="status" value="${status}"/>
				<input type="hidden" name="departId" value="${departId}"/>
				<input type="hidden" name="isShowWH" value="${isShowWH}"/>
                <input type="hidden" class="wf-form-text" id="wfTitle" name="wfTitle" style="width: 75px" value="${wfTitle}" >
                <input type="hidden" name="itemName" value="${itemName}"/>
                <input type="hidden" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}" >
				<input type="hidden" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}" >
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
			</form>
			<span style="position:relative;">
	            <a class="high-search" href="#" onclick="showSearch();">
	                 	高级搜索
	            </a>
				<div id="high-search" style="height:320px;background-size: 100% 100%;">
					<form method="post" id="form" name="form" action="#" class="tw-form">
						<input type="hidden" name="itemid" value="${itemid}" />
						<input type="hidden" name="favourite" value="${favourite}"/>
						<input type="hidden" name="isShowExp" value="${isShowExp}"/>
	            		<table class="new-htable">
			                <tr>
			                    <th width="85">标题：</th>
			                    <td><input type="text"   placeholder="请输入标题关键字" class="tw-form-text" id="wfTitle2" name="wfTitle" value="${wfTitle}"></td>
			                </tr>
			                <%-- <tr>
			                    <th>事项类型：</th>
			                    <td>
			                    	<select class="tw-form-select" id="itemName2" name="itemName">
					                	<option value=""></option>
					                	<c:forEach var="m" items='${myPendItems}'>
						 					<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
						 				</c:forEach> 
					                </select>
			                    </td>
			                </tr> --%>
			                <tr>
			                    <th>发文日期：</th>
			                    <td><input type="text" class="tw-form-text wf-form-date" id="commitTimeFrom2" name="commitTimeFrom" value="${commitTimeFrom}" style="width:163px;">
									至
									<input type="text" class="tw-form-text wf-form-date" id="commitTimeTo2" name="commitTimeTo" value="${commitTimeTo}" style="width:163px;">
								</td>
			                </tr>
			               
			                <tr>
			                    <th>文号：</th>
			                    <td><input type="text" placeholder="请输入文号" class="tw-form-text" id="wh" name="wh" value="${wh}"></td>
			                </tr>
			                <tr>
			                    <th>来文单位：</th>
			                    <td><input type="text" placeholder="请输入来文单位" class="tw-form-text" id="lwdw" name="lwdw" value="${lwdw}"></td>
			                </tr>
			                <tr>
			                    <th>办件状态：</th>
			                    <td>
			                    	<select class="tw-form-select" name="status" id="status">
					                	<option value=""></option>
						 				<option value="4" <c:if test="${status eq '4'}">selected="selected"</c:if>>在办</option>
						 				<option value="2" <c:if test="${status eq '2'}">selected="selected"</c:if>>办结</option>
					                </select>
			                    </td>
			                </tr>
			                <c:if test="${isFgw == '1'}">
			                <tr>
			                    <th>督办类型：</th>
			                    <td>
			                    	<select class="tw-form-select" name="dubanType" id="dubanType">
					                	<option value=""></option>
						 				<option value="市委"  <c:if test="${dubanType=='市委'}">selected="selected"</c:if>>市委</option>
						 				<option value="市政府" <c:if test="${dubanType=='市政府'}">selected="selected"</c:if>>市政府</option>
						 				<option value="委领导" <c:if test="${dubanType=='委领导'}">selected="selected"</c:if>>委领导</option>
					                </select>
			                    </td>
			                </tr>
			                </c:if>
			                <c:if test="${isShowExp == '1'}">
			                <tr>
			                    <th>部门：</th>
			                    <td>
			                    	<select class="tw-form-select" name="departId" id="departId">
					                	<option value=""></option>
					                	<c:forEach items="${depts}" var="dept">
						 				<option value="${dept.departmentGuid }" <c:if test="${dept.departmentGuid eq departId}">selected="selected"</c:if>>${dept.departmentName }</option>
						 				</c:forEach>
					                </select>
			                    </td>
			                </tr>
			                </c:if>
							<tr>
			                    <th></th>
			                    <td>
			                    	<button class="wf-btn-primary" type="submit" onclick="checkForm('1');">
						                <i class="wf-icon-search"></i> 搜索
						            </button>
						            <!-- <a class="wf-btn-primary" href="javascript:;" onclick="exportEx();">
						                <i class="fa fa-file-excel"></i> Excel导出
						            </a> -->
							 		<a class="high-search" href="#" onclick="form.reset()">
				                 		清空搜索条件
				            		</a>
								</td>
			                </tr>
						</table>
					</form>
				</div>
			</span>
			<c:if test="${favourite!='1'}">
           	<!--<a class="nxwj" href="#" onclick="startNX();">
                
            </a>
           	 <div class="nx_div">
					<div class="title">拟新文件</div>
					<!--<ul class="tp-event-list"><li title="付款申请" id="F0D5DADD246A43A895D74D8D185B2E67" link="http://61.155.85.78:5088/trueWorkFlow//table_openFirstForm.do?workflowid=5D51AAD0-30A0-4F4A-994F-3E3CD07917F4&amp;itemid=86483931-A357-4578-B80D-3C9BFC73B9A9" identify="" sort="10"> <i class="tp-event-icon"> <img src="http://61.155.85.78:5088/trueOA/widgets/css/treeicons/images/icons/icon_fksq.png" style="top: 0px;"> </i> 付款申请</li><li title="进修审批" id="E49F3E7F559C49F8BA14061891864F0C" link="http://61.155.85.78:5088/trueWorkFlow//table_openFirstForm.do?workflowid=80AB028F-EF66-40FD-9ABF-9207DFDB895C&amp;itemid=C4B41331-A2BC-499C-BA5D-9DD600D1EFC0" identify="" sort="10"> <i class="tp-event-icon"> <img src="http://61.155.85.78:5088/trueOA/widgets/css/treeicons/images/icons/icon_jxsp.png"> </i> 进修审批</li></ul>-->
					 <!--<div class="tp-main-top menu_ul_img none">
			    <div class="JS_eventList tp-event-list-wrap">
				</div>
				<div class="tp-event-page-box">
				
                   </div>
			</div>
			 </div>-->
           	</c:if>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getDuBanList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
		    			<c:if test="${favourite == '1' || isShowExp == '1'}">
						<th width="3%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
						</c:if>
						<th width="3%" >序号</th>
						<th >标题</th>
						<c:if test="${isShowWH == '1' }">
						   <th width="15%">文号</th>
						</c:if>
						
						<c:if test="${isShowWH != '1' }">
						   <th width="10%" >当前步骤</th>
						</c:if>
						
						<th width="15%" >当前步骤办理人</th>
						<c:if test="${isFgw == '1'}">
							<th width="15%" >督办时限</th>
							<th width="8%" >督办类型</th>
						</c:if>
						<th width="4%" >状态</th>
						<c:if test="${favourite != '1'}">
						<c:if test="${isShowWH != '1' }">
						   <th width="4%" >操作</th>
						</c:if>
						
						</c:if>
					</tr>
		    	</thead>
		    	<c:forEach items="${doFileList}" var="doFile" varStatus="status">
			    	<tr onclick="dianji('${doFile.doFile_id}')">
			    		<c:if test="${favourite == '1' || isShowExp == '1'}">
						<td align="center">
    						<input type="checkbox" name="selid" value="${doFile.doFile_id}" >
    					</td>
    					</c:if>
						<td style="text-align:center;" dofileId="${doFile.doFile_id}" instanceId="${doFile.instanceId}" wfUId='${doFile.workflowId}'>${(selectIndex-1)*pageSize+status.count}</td>
						<td style="text-align: left;" title="${doFile.doFile_title}">
							<c:choose>
								<c:when test="${doFile.itemType == '0'}">
									<span class="tp-info-cate FW">${doFile.itemName }</span>
								</c:when>
								<c:when test="${doFile.itemType == '1'}">
									<span class="tp-info-cate SW">${doFile.itemName }</span>
								</c:when>
								<c:when test="${doFile.itemType == '3'}">
									<span class="tp-info-cate XMSP">${doFile.itemName }</span>
								</c:when>
								<c:when test="${doFile.itemType == '4'}">
									<span class="tp-info-cate FKSQ">${doFile.itemName }</span>
								</c:when>
								<c:otherwise>
									<span class="tp-info-cate">${doFile.itemName }</span>
								</c:otherwise>
							</c:choose>
							
								<a href="#" onclick="openForm('${doFile.processId}','${doFile.itemId}','${doFile.formId}','${favourite}','${doFile.doFile_title}');">
									 <c:choose>  
										<c:when test="${fn:length(doFile.doFile_title) > 24}">  
	   										<c:out value="${fn:substring(doFile.doFile_title, 0, 24)}..." />  
										</c:when>  
										<c:otherwise>  
	 										<c:out value=" ${doFile.doFile_title}" />  
										</c:otherwise>  
									</c:choose>
								</a>
						</td>
						<c:if test="${isShowWH == '1' }">
						   <td align="center">${doFile.wh}</td>
						</c:if>
						<c:if test="${isShowWH != '1' }">
						   <td align="center">${doFile.nodeName}</td>
						</c:if>
						
						<td align="center">${doFile.entrustName }</td>
						<%-- <c:if test="${not empty isAdmin}"> --%>
						<c:if test="${isFgw == '1'}">
							<td align="center">${doFile.dubanTime}</td>
							<%-- </c:if> --%>
							<%-- <c:if test="${empty isAdmin}">
								<td align="center">${fn:substring(doFile.do_time, 0, 16)}</td>
							</c:if> --%>
							<td align="center">${not empty doFile.dubanType&&doFile.dubanType!='null'?doFile.dubanType:""}</td>
						</c:if>
						<td align="center">
							<c:if test="${doFile.doFile_result==0}">
								<span class="wf-badge-not">未</span>
							</c:if>
							<c:if test="${doFile.doFile_result==1}">
								<span class="wf-badge-doing">办</span>
							</c:if>
							<c:if test="${doFile.doFile_result==2}">
								<span class="wf-badge-over">结</span>
							</c:if>
						</td>
						<c:if test="${favourite != '1'}">
						<c:if test="${isShowWH != '1' }">
						  <td align="center">
							<c:if test="${doFile.favourite == '0'}">
								<span class="collect" id="${doFile.processId}fva" onclick="addCollection('${doFile.instanceId}','${doFile.workflowId}','${doFile.processId}fva','${doFile.processId}fvadel');"></span>
								<span class="collected" id="${doFile.processId}fvadel" onclick="deleteCollection('${doFile.doFile_id}','${doFile.processId}fva','${doFile.processId}fvadel');" style="display: none;"></span>
							</c:if>
							<c:if test="${doFile.favourite == '1'}">
								<span class="collect" id="${doFile.processId}fva" onclick="addCollection('${doFile.instanceId}','${doFile.workflowId}','${doFile.processId}fva','${doFile.processId}fvadel');" style="display: none;"></span>
								<span class="collected" id="${doFile.processId}fvadel" onclick="deleteCollection('${doFile.doFile_id}','${doFile.processId}fva','${doFile.processId}fvadel');"></span>
							</c:if>
						</td>
						</c:if>
						</c:if>
					</tr>
		    	</c:forEach>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>

<div id="loadMask" style="position:fixed;left:0;top:0;z-index:9999;display:none;width:100%;height:100%;background:rgba(255,255,255,.65);">
	<div style="position:absolute;left:50%;top:50%;width:100px;height:100px;margin-top:-50px;margin-left:-50px;text-align:center;">
		<img style="margin-bottom:8px;" src="${ctx}/assets/plugins/layer/skin/default/loading-3.gif" />
		<p style="font-size:14px;line-height:28px;color:#4a4a4a;text-indent:14px">文件生成中…</p>
	</div>
</div>

</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/table_getDuBanList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doItemListH');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
	var classObj=
     	{
       		ToUnicode:function(str){
        	return escape(str).replace(/%/g,"\\").toLowerCase();
       	},
       	UnUnicode:function(str){
        	return unescape(str.replace(/\\/g, "%"));
      	},
      	copyingTxt:function(str){
       		document.getElementById(str).select(); 
       		document.execCommand("Copy"); 
      	}
    };

	var hopen=false;
	function showSearch(){
		if(!hopen){
			document.getElementById("wfTitle2").value=document.getElementById("wfTitle").value;
			/* document.getElementById("itemName2").value=document.getElementById("itemName").value; */
			document.getElementById("commitTimeFrom2").value=document.getElementById("commitTimeFrom").value;
			document.getElementById("commitTimeTo2").value=document.getElementById("commitTimeTo").value;
			
			document.getElementById("wfTitle").value="";
		/* $('#itemName option:selected').val(""); */
		document.getElementById("commitTimeFrom").value="";
		document.getElementById("commitTimeTo").value="";
		var wh = document.getElementById("wh").value;
		var lwdw = document.getElementById("lwdw").value;
		var wfTitle = document.getElementById("wfTitle").value;
		var commitTimeFrom2 = document.getElementById("commitTimeFrom2").value;
		var commitTimeTo2 = document.getElementById("commitTimeTo2").value;
		/* var itemName2 = document.getElementById("itemName2").value; */
		
		document.getElementById("high-search").style.display="block";
		hopen=true;
		}
		else{
			document.getElementById("high-search").style.display="none";
			hopen=false;
		}
	}
	
	function openStartForm(id,url,title){
		openLayerTabs(id,screen.width,screen.height,title,url);
	}
	var nxopen=true;
	function startNX(){
		if(nxopen){
			$(".nx_div").animate({right:"0px"});
			nxopen=false;
		}
		else{
			$(".nx_div").animate({right:"-216px"});
			nxopen=true;
		}
		
	}
	function topOpenLayerTabs(processId,width,height,title,url){
	    if ($('.layui-layer').length == 1) {
			if(document.all && document.addEventListener && window.atob){
		    
			}else {
                      addTabsInLayer(processId,title,url);
                      layer.restore(taskid);						
			}				
		}else{			
                  layer.open({
                      type: 1,
                      title: false,
                      shade: false,
                      maxmin: true,
				move: '.nav-tabs',
				moveType: 1,
                      area: [$(window).width()-100+'px','90%'],
                      content: $('#tabs'),
			    success: function(){
	                $('#tabs').addtabs({
		                iframeHeight: $(window).height()*0.9-47+'px',
		                callback: function () { //关闭tabs后回调函数
		                    if($('.layui-layer li[role = "presentation"].active').length == 0){
			                    layer.close(taskid);
				                $('#tabs .nav-tabs').html('');
				                $('#tabs .tab-content').html('');
			                }
							document.getElementById('Mcontainer').src = MyIframe;
							//var workflowurl ="http://61.155.85.78:5088/trueWorkFlow/";
							//workflowurl += "/table_getPendingList.do";
							//alert(workflowurl);
							//document.getElementById('subIframe').src = workflowurl;
                             }				
		            });						
                          layid = $('.layui-layer').attr("id");
                          taskid = parseInt(layid.replace("layui-layer",""));						
                          addTabsInLayer(processId,title,url);
					$('#Mcontainer').contents().find('body').css('overflow-y','hidden');
			    },
				min: function(){
					$('.layui-layer').height('38px');
					$('.layui-layer').width($(window).width()-100+'px');
					$('.iframeClass').height('0px');
				    $('#Mcontainer').contents().find('body').css('overflow-y','auto');
				},
				cancel: function(){
				   $('#tabs .nav-tabs').html('');
				   $('#tabs .tab-content').html('');
				   $('#Mcontainer').contents().find('body').css('overflow-y','auto');
				   
				   if(url.indexOf('ns_toSeeNoticeContent.do') < 0){
						var iframe = $('.tab-pane.tabon').find('iframe')[0]
						if(iframe){
							setTimeout(function(){
								iframe.src = iframe.src
							},1000);
						}
					}
				},
				full: function(){
				    $('.layui-layer').height($(window).height());
				    $('.layui-layer-content').height($(window).height());
				    $('.iframeClass').height($(window).height()-47);
				    isFull = 1;
				},
				restore: function(){
				    $('.layui-layer').height($(window).height()*0.9);
				    $('.layui-layer-content').css('height',$(window).height()*0.9);
				    $('.iframeClass').height($(window).height()*0.9-47);
					$('#Mcontainer').contents().find('body').css('overflow-y','hidden');
					isFull = 0;
				}
                  });				
		}
	}
	
	$(".tp-event-list li").on("click",function(){	//给每一个发起事项配置showmodal的链接地址
		var link = $(this).attr("link");
		var title = $(this).attr("title");
		//var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
		parent.topOpenLayerTabs(parseInt(Math.random()*100+1),1200,600,title,link);
		initContentModule();
	})
	
	function startWf(){
		var url = "${curl}/item_getItemList.do?status=1";
		var WinWidth = 250;
		var WinHeight = 350;
		if(top.window && top.window.process && top.window.process.type){
			console.info("封装打开方式");
			var ipc = top.window.nodeRequire('ipc');
			var remote = top.window.nodeRequire('remote');
			var browserwindow = remote.require('browser-window');
			var winProps = {};
			winProps.width = 250;
			winProps.height = 350;
			winProps['web-preferences'] = {'plugins':true};
			var focusedId = browserwindow.getFocusedWindow().id;
			var win = new browserwindow(winProps);
			//console.info(focusedId);
			win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
			//win.openDevTools();
			win.on('closed',function(){
				win = null;
			});				    
			ipc.on('message-departmentTree',function(args){
				if(win){
					win.close();
					win = null;
					var ret = args;
					
					if(ret){
						var rets = ret.split("##");
						openStartForm(rets[0],rets[1],rets[2]);
					}
				}
			});
		}else{
			console.info("window.open普通打开方式");
			var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) + ",top=" + (window.screen.height - WinHeight - 100) / 2);
			var loop = setInterval(function(){
				if(winObj.closed){
					//console.info(window.returnValue);
					clearInterval(loop);
					//---------------------------
					var ret = window.returnValue;
					if(ret){
						var rets = ret.split("##");
						openStartForm(rets[0],rets[1],rets[2]);
					}
				}
			},500);
		}
	}
	
	function prompt(){
		alert("此公文你没有权限查看");
	}
	
	function radions_fw(){
		var fields = "isFw".split(",");
		for(var i = 0; i < fields.length; i++){
			var radios = document.getElementsByName(fields[i]);
			for(var j = 0; j < radios.length; j++){
				if(radios[j].checked){
					document.getElementById("b_"+fields[i]).value = radios[j].value;
				}
			}
		}
	}
	
	function checkForm(status){
		if(status == '2'){
			document.getElementById("wfTitle2").value="";
			document.getElementById("itemName2").value="";
			document.getElementById("commitTimeFrom2").value="";
			document.getElementById("commitTimeTo2").value="";
			document.getElementById("wh").value="";
			document.getElementById("lwdw").value="";
		}
		radions_fw();
		document.getElementById('doItemList').submit();
	}
	
	/**
	 * 重定向：检索办件处于的步骤信息
	 */
	function redirectPending(instanceId){
		window.location.href="${ctx}/table_getRedirectDetail.do?instanceId="+instanceId;
	}
	
	var ids="";
	function dianji(id){
		ids =id;
	}
	
	function sel(){
		var selAll = document.getElementById('selAll');
		var selid = document.getElementsByName('selid');
		for(var i = 0 ; i < selid.length; i++){
			if(selAll.checked){
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
	}
	
	function del(){
		if(confirm("确定要删除吗？")){
			var selid = document.getElementsByName('selid');
			var ids = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					ids += selid[i].value + ",";
				}
			}
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
			}else {
				alert('请选择一个删除对象');
				return;
			}
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        data:{"id":ids},
		        url:"${ctx}/table_deleteDoFile.do",
		        success:function(text){
		        	 alert("办件删除成功！");
		        	 window.location.reload();
		        }
		    });
		}
	}
	
	function openForm(processId,itemId,formId,favourite,process_title){
		if(processId!=''){
			 var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
			$.ajax({
				type:'post',
				url:'${ctx}/table_isPendingFile.do',
				async:false,
				data:{
					'processId':processId
				},
				success:function(data){
					var result = data;
					if(result && result=='true'){
						url = '${curl}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&t='+new Date();
					}
				},error:function(){
					console.log('AJAX调用错误(table_isPendingFile.do)');
				}
			});
			 openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}
	}
	
	function exportEx() {
		$('#loadMask').show();
		var wfTitle = document.getElementById("wfTitle2").value;
		var itemName = document.getElementById("itemName2").value;
		var commitTimeFrom = document.getElementById("commitTimeFrom2").value;
		var commitTimeTo = document.getElementById("commitTimeTo2").value;
		var wh = document.getElementById("wh").value;
		var lwdw = document.getElementById("lwdw").value;
		var status = document.getElementById("status").value;
		
		var obj = document.getElementById("download_iframe");

		if ($('#loadMask').css('display') === 'block') {
			$.ajax({  
				url : "${ctx}/tableExtend_expExcelOfDofileList.do?wfTitle="+encodeURI(encodeURI(wfTitle))
				+"&itemName="+encodeURI(encodeURI(itemName))+"&commitTimeFrom="+commitTimeFrom+"&status="+status
				+"&commitTimeTo="+commitTimeTo+"&wh="+encodeURI(encodeURI(wh))+"&lwdw="+encodeURI(encodeURI(lwdw)),
				type : 'POST',   
				cache : false,
				async : true,
				error : function() {  
					alert('AJAX调用错误(tableExtend_expExcelOfDofileList.do)');
				},
				success : function(msg) {
					var downUrl = "${ctx}/tableExtend_downloadForExcel.do?fileNameWithPath="+encodeURI(encodeURI(msg));
					if(obj){
						obj.src=downUrl;
					}else{
						window.location.href=downUrl;
					}
					$('#loadMask').hide();
				}
			}); 
		}
		 
	}
	
	//取消收藏
	function deleteCollectionBacth(){
		var selid = document.getElementsByName('selid');
		var ids = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				ids += selid[i].value + ",";
			}
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length - 1);
		}else {
			ids = $(".wf-actived td:eq(1)").attr("dofileId");
		}
		if(ids){
			if(confirm("确定要取消收藏吗？")){
				$.ajax({  
					url : '${ctx}/table_deletefavourite.do?dofileId='+ids,
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_deletefavourite.do)');
					},
					success : function(msg) {
						if(msg == '1'){
							alert("取消收藏成功！");
							window.location.reload();
						}else if(msg == '2'){
							alert("取消收藏失败！");
						}
					}
				});
			}
		}else{
			alert("请选择需要取消收藏的办件");
		}
	}
	
	function openLayerTabs(processId,width,height,title,url){
	   parent.topOpenLayerTabs(processId,1200,600,title,url);
	}
	
	function downloadBatch(){
		var selid = document.getElementsByName('selid');
		var ids = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				ids += selid[i].value + ",";
			}
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length - 1);
		}else {
			ids = $(".wf-actived td:eq(1)").attr("dofileId");
		}
		if(ids){
			var obj = document.getElementById("download_iframe");
			var downUrl = "${ctx}/table_downloadBatch.do?ids="+ids;
			if(obj){
				obj.src=downUrl;
			}else{
				window.location.href=downUrl;
			}	
		}else{
			alert("请选择需要导出的办件");
		}
		
	}
	
	function addCollection(instanceId,workFlowId,id,id2){
		$.ajax({  
			url : 'table_favourite.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_favourite.do)');
			},
			data : {
				instanceId : instanceId,
				workFlowId : workFlowId
			},
			success : function(msg) {
				if(msg == '1'){
					alert("已收藏！");
					return false;
				}else if(msg == '2'){
					alert("收藏成功！");
					$("#"+id2).css('display','');
				    $("#"+id).css('display','none');
				}else if(msg == '3'){
					alert("收藏失败");
				}
			}
		});
	}
	
	function deleteCollection(dofileId,id,id2){
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
					$("#"+id).css('display','');
				    $("#"+id2).css('display','none');
				}else if(msg == '2'){
					
				}
			}
		});
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