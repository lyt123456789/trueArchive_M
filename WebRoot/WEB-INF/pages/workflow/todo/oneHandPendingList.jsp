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
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
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
			width:115px!important;
			min-width: 100px!important;
			max-width: 120px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		.w-auto-10 {
			width: 9% !important;
			min-width: 9% !important;
		}
		.wf-form-label{
			margin-left: 0px;
		}
		
		.auto-date-width{
			width:120px!important;
		}
		.high-search-btn{
			font-size:14px;
			color:#4284ce;
			margin-left:10px;
		}
		#high-search{
			top:0;
		}
	</style>
</head>
<body>
<div id="loadMask" style="position:fixed;left:0;top:0;z-index:9999;display:none;width:100%;height:100%;background:rgba(255,255,255,.65);">
	<div style="position:absolute;left:50%;top:50%;width:100px;height:100px;margin-top:-50px;margin-left:-50px;text-align:center;">
		<img style="margin-bottom:8px;" src="${ctx}/assets/plugins/layer/skin/default/loading-3.gif" />
		<p style="font-size:14px;line-height:28px;color:#4a4a4a;text-indent:14px">正在办理中…</p>
	</div>
</div>
<object style="display: list-item;" id="OCXpdfobj2"  TYPE="application/x-itst-activex"  width="0"  height="0" clsid="{ECCC5C8C-8DA0-4FAC-935A-CD5229A14BCC}"></object>

<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="pendlist"  id="pendlist" action="${ctx }/table_getPendingList.do?siteId=${siteId}&laterSearch=1&a=Math.random();" method="post" style="display:inline-block;width: 100%;">
	 	        <input type="hidden" name="isPortal" value="${isPortal}"/>
	 	        <input type="hidden" name="isShowWH" value="${isShowWH}"/>
	 	        <input type="hidden" name="laterSearch" value="${laterSearch}"/>
	 	        <input type="hidden" name="siteId" value="${siteId}"/>
	 	        <input type="hidden" name="oneHandNodeId" value="${oneHandNodeId}"/>
			    <div class="wf-top-tool">
					<a class="wf-btn-primary" href="#" onclick="operateBatch();" target="_self">
						<i class="wf-icon-pencil" style="display:inline-block;"></i> 批量办理
					</a>
				</div>
 	        	<label class="wf-form-label" for="">标题：</label>
                <input type="text" class="wf-form-text w-auto-10" id="wfTitle" name="wfTitle" style="width: 90px" value="${wfTitle}" placeholder="输入关键字">
                <label class="wf-form-label" for="">事项类型：</label>
               <%-- <select class="wf-form-select" id="itemType" name="itemType">
                	<option value=""></option>
	 				<option value="0" <c:if test="${itemType eq '0'}">selected="selected"</c:if>>发文</option>
	 				<option value="1" <c:if test="${itemType eq '1'}">selected="selected"</c:if>>收文</option>
	 				<option value="3" <c:if test="${itemType eq '3'}">selected="selected"</c:if>>客情报告</option>
	 				<option value="4" <c:if test="${itemType eq '4'}">selected="selected"</c:if>>用车申请</option>
                </select> --%>
                
               <select class="wf-form-select" id="itemName" name="itemName" style="width: 123px;">
                	<option value=""></option>
                	<c:forEach var="m" items='${myPendItems}'>
	 					<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
	 				</c:forEach> 
                </select>
				<label class="wf-form-label" for="">发文日期：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}" readonly="readonly" placeholder="输入日期">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}" readonly="readonly" placeholder="输入日期">
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
			    <c:if test="${not empty wh}">
				    <input type="hidden" name="wh" value="${wh}" />
			    </c:if>
			    <c:if test="${not empty lwdw}">
			    	<input type="hidden" name="lwdw" value="${lwdw}" />
			    </c:if>
			    <!--<input type="hidden" id="wfTitle2" name="wfTitle2"  value="${wfTitle2}"/>
			    <input type="hidden" id="itemType2" name="itemType2"  value="${itemType2}"/>
			    <input type="hidden" id="commitTimeFrom2" name="commitTimeFrom2"  value="${commitTimeFrom2}"/>
			    <input type="hidden" id="commitTimeTo2" name="commitTimeTo2"  value="${commitTimeTo2}"/>
			    <input type="hidden" id="wh" name="wh"  value="${wh}"/>
				<input type="hidden" id="lwdw" name="lwdw"  value="${lwdw}"/>
				<input type="hidden" id="state" name="state"  value="${state}"/>-->
	            <button class="wf-btn-primary" type="button" onclick="checkForm('2');">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
	            <a class="high-search-btn" href="#" onclick="showSearch();">
	               	  高级搜索
	            </a>
				 </form>
				 <!-- 分页用查询form -->
			 <form name="pendlistH"  id="pendlistH" action="${ctx }/table_getPendingList.do?laterSearch=1&a=Math.random();" method="post" style="display:none;">
	 	        <input type="hidden" name="isPortal" value="${isPortal}"/>
	 	        <input type="hidden" name="isShowWH" value="${isShowWH}"/>
	 	        <input type="hidden" name="laterSearch" value="${laterSearch}"/>
                <input type="hidden" class="wf-form-text" id="wfTitle" name="wfTitle" style="width: 90px" value="${wfTitle}" placeholder="输入关键字">
                <input type="hidden" name="itemName" value="${itemName}"/>
                <input type="hidden" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}" >
				<input type="hidden" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}" >
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
			    <input type="hidden" id="siteId" name="siteId" value="${siteId}" />
			    <input type="hidden" id="oneHandNodeId" name="oneHandNodeId" value="${oneHandNodeId}" />
			    <c:if test="${not empty wh}">
				    <input type="hidden" name="wh" value="${wh}" />
			    </c:if>
			    <c:if test="${not empty lwdw}">
			    	<input type="hidden" name="lwdw" value="${lwdw}" />
			    </c:if>
		    </form> 
				 
				<span style="position:relative;">
					<div id="high-search">
						<!--<iframe style="border:0;margin:15px 2% 0;" id="hsearch-iframe" width="96%" height="240"></iframe>-->
						<form method="post" id="form" name="form"
							action="${ctx }/table_getPendingList.do?laterSearch=1&siteId=${siteId}&a=Math.random();"
							class="tw-form">
							<input type="hidden" id="oneHandNodeId" name="oneHandNodeId" value="${oneHandNodeId}" />
							<table class="new-htable">
								<tr>
									<th width="85">标题：</th>
									<td><input type="text" placeholder="请输入标题关键字"
										class="tw-form-text" id="wfTitle2" name="wfTitle"
										value="${wfTitle}"></td>
								</tr>
								<tr>
									<th>事项类型：</th>
									<td><select class="tw-form-select" id="itemName2"
										name="itemName">
											<option value=""></option>
											<c:forEach var="m" items='${myPendItems}'>
												<option value="${m.vc_sxmc}"
													<c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<th>发文日期：</th>
									<td><input type="text"
										class="tw-form-text wf-form-date auto-date-width"
										id="commitTimeFrom2" name="commitTimeFrom2"
										value="${commitTimeFrom}" style="width: 163px;"> 至 <input
										type="text" class="tw-form-text wf-form-date auto-date-width"
										id="commitTimeTo2" name="commitTimeTo2"
										value="${commitTimeTo}" style="width: 163px;"></td>
								</tr>

								<tr>
									<th>文号：</th>
									<td><input type="text" placeholder="请输入文号"
										class="tw-form-text" id="wh" name="wh" value="${wh}"></td>
								</tr>
								<tr>
									<th>来文单位：</th>
									<td><input type="text" placeholder="请输入来文单位"
										class="tw-form-text" id="lwdw" name="lwdw" value="${lwdw}"></td>
								</tr>
								<tr>
									<th></th>
									<td><button class="wf-btn-primary" type="button"
											onclick="checkForm('1');">
											<i class="wf-icon-search"></i> 搜索
										</button> <input type="reset" class="high-search"
										style="border: 0; background: none;" value="清空搜索条件" /></td>
								</tr>
							</table>
						</form>
					</div>
				</span>
	            <!--<c:if test="${state != null && state eq '1' }">
	            <a class="wf-btn-primary" href="#" onclick="startWf();">
	                <i class="wf-icon-pencil"></i> 拟新文件
	            </a>
	            </c:if>-->
           
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getPendingList.do" >
		<input type="hidden" name="siteId" value="${siteId}"/>
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
		    	  		<th width="4%"></th>
                        <th align="center" width="4%">序号</th>
						<th >标题</th>
						<th width="30"></th>
						<c:choose>
							<c:when test="${isShowWH == '1' }">
							   <th width="8%">文号</th>
							</c:when>
							<c:otherwise>
							   <th width="7%">当前步骤</th>
							</c:otherwise>
						</c:choose>
						
						
						<!-- <th width="10%">提交处室</th> -->
						<th width="7%">提交人</th>
						<th width="18%">提交时间</th>
						<th width="5%">收藏</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
			    		<td align="center" itemid="${item.wf_process_uid}">
							<input type="checkbox" name="selid" id="${item.wf_process_uid}" class="regular-checkbox"  value="${item.wf_process_uid}" <c:if test="${oneHandOperate eq 'false'}">disabled="true"</c:if> >
							<label for="${item.wf_process_uid}"></label>
						</td>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}">
							<c:choose>
								<c:when test="${item.item_type == '0'}">
									<span class="tp-info-cate FW">${item.item_name }</span>
								</c:when>
								<c:when test="${item.item_type == '1'}">
									<span class="tp-info-cate SW">${item.item_name }</span>
								</c:when>
								<c:when test="${item.item_type == '3'}">
									<span class="tp-info-cate XMSP">${item.item_name }</span>
								</c:when>
								<c:when test="${item.item_type == '4'}">
									<span class="tp-info-cate FKSQ">${item.item_name }</span>
								</c:when>
								<c:otherwise>
									<span class="tp-info-cate">${item.item_name }</span>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${'3' eq item.urgency}">
									<img src="${ctx}/images/jinji_tj.png">
								</c:when>
								<c:when test="${'2' eq item.urgency}">
									<img src="${ctx}/images/jinji_ji.png">
								</c:when>
								<c:otherwise>
								
								</c:otherwise>
							</c:choose>
							<c:if test="${('1' eq item.isHaveChild)}">
							<span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
							<c:if test="${!('1' eq item.isChildWf)}"><span class="wf-badge-main">主</span></c:if>
			   			 	<c:if test="${('1' eq item.isChildWf)}"><span class="wf-badge-sub">子</span></c:if>
			   			 	</span>
			   			 	</c:if>
							
							<font <c:if test="${item.jssj == null or item.jssj == '' }">style="font-weight: 700;"</c:if>>
			   			 	<c:if test="${containsMail != null && containsMail != '' && containsMail == '1' }">
				   			 	<c:if test="${item.message_type != '2' }">
									<c:choose>
										<c:when test="${item.isDelaying != '1'}">
											<a href="#" onclick="openPendingForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.isCanPush}', '${item.stepIndex}', '${item.isChildWf}', '${item.item_type}','${isCy}','${item.isHaveChild}','${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}','${item.imgPath}');">
												${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}
											</a>
										</c:when>
										<c:otherwise>
											${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${item.message_type == '2' }">
										<a href="#" onclick="openOverForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.favourite}','${item.isHaveChild}','${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}');">
											${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}（办结待阅）
										</a>
								</c:if>
							</c:if>
							<c:if test="${containsMail == null || containsMail == '' || containsMail != '1' }">
								<c:choose>
									<c:when test="${item.isDelaying != '1'}">
										<a href="#" onclick="openPendingForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.isCanPush}', '${item.stepIndex}', '${item.isChildWf}', '${item.item_type}','${isCy}','${item.isHaveChild}','${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}','${item.imgPath}');">
											<font <c:if test="${item.jssj == null or item.jssj == '' }">style="font-weight: 700;"</c:if>>${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}</font>
										</a>
									</c:when>
									<c:otherwise>
										<font <c:if test="${item.jssj == null or item.jssj == '' }">style="font-weight: 700;"</c:if>>${fn:contains(item.process_title,'*')&&fn:indexOf(item.process_title,'*')==36?fn:substring(item.process_title,37,-1):item.process_title}</font>
									</c:otherwise>
								</c:choose>
							</c:if>
							</font>
						</td>
						<td>
						<c:if test="${item.isDel == '1' }"><i class="wf-icon-trash" title="删除" onclick="delone('${item.dofileId}');"></i></c:if>
						</td>
						<c:choose>
							<c:when test="${isShowWH == '1' }"><!-- 当为残联待办页面时，当前步骤改为文号 -->
							    <td align="center" title="${item.siteName}">${item.siteName}</td>
							</c:when>
							<c:otherwise>
								<td align="center" title="${item.nodeName}">${item.nodeName}</td>
							</c:otherwise>
						</c:choose>
						<%-- <td align="center" title="${item.userDeptId}">${item.userDeptId}</td> --%>
						<td align="center" title="${item.employee_name}">${item.employee_name }</td>
						<td align="center" title="${fn:substring(item.apply_time,0,16)}">${fn:substring(item.apply_time,0,16) }</td>
						<td align="center">
							<c:if test="${item.favourite == '0'}">
								<span class="collect" id="${item.wf_process_uid}fva" onclick="addCollection('${item.wf_instance_uid}','${item.wf_workflow_uid}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');"></span>
								<span class="collected" id="${item.wf_process_uid}fvadel" onclick="deleteCollection('${item.dofileId}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');" style="display: none;"></span>
							</c:if>
							<c:if test="${item.favourite == '1'}">
								<span class="collect" id="${item.wf_process_uid}fva" onclick="addCollection('${item.wf_instance_uid}','${item.wf_workflow_uid}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');" style="display: none;"></span>
								<span class="collected" id="${item.wf_process_uid}fvadel" onclick="deleteCollection('${item.dofileId}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');"></span>
							</c:if>
							
							
						</td>
					</tr>	
		    	</c:forEach>
			</table>
		</form>
		<c:if test="${fn:length(list)==0 && laterSearch == '1'}">
		<div style="width:100%;padding-top:100px;font-size:18px;text-align:center">
			您搜索的文件不存在！
		</div>
		</c:if>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			if(parent._selectIndex){
				selectIndex = parent._selectIndex
				parent._selectIndex = null
			} else {
				selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);
			}
			skipUrl="<%=request.getContextPath()%>"+"/table_getPendingList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('pendlistH');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
			setTimeout(function(){
				var list2 = document.getElementsByClassName('wf-input-datepick');
				for(var i=0;i<2;i++){
					list2[i].style.width = '125px';
				}
				for(var i=2;i<4;i++){
					list2[i].style.width = '123px';
				}
			}, 500);
		};
	</script>
	<script type="text/javascript">
		function sel(){
			var selAll = document.getElementById('selAll');
			var selid = document.getElementsByName('selid');
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].getAttribute('disabled') == "disabled"){
					continue;
				}
				if(selAll.checked){
					
					selid[i].checked = true;
				}else{
					selid[i].checked = false;
				}
			}
		}
		var hopen=false;
		function showSearch(){
			if(!hopen){
				document.getElementById("wfTitle2").value=document.getElementById("wfTitle").value;
				document.getElementById("itemName2").value=document.getElementById("itemName").value;
				document.getElementById("commitTimeFrom2").value=document.getElementById("commitTimeFrom").value;
				document.getElementById("commitTimeTo2").value=document.getElementById("commitTimeTo").value;
				
				document.getElementById("wfTitle").value="";
			$('#itemName option:selected').val("");
			document.getElementById("commitTimeFrom").value="";
			document.getElementById("commitTimeTo").value="";
			var wh = document.getElementById("wh").value;
			var lwdw = document.getElementById("lwdw").value;
			var wfTitle = document.getElementById("wfTitle").value;
			var commitTimeFrom2 = document.getElementById("commitTimeFrom2").value;
			var commitTimeTo2 = document.getElementById("commitTimeTo2").value;
			var itemName2 = document.getElementById("itemName2").value;
			document.getElementById("high-search").style.display="block";
			hopen=true;
			}else{
				document.getElementById("high-search").style.display="none";
				hopen=false;
			}
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
		
		
		
		function autoCheck(){
			if(parent.checkStatus){
				checkForm(parent.checkStatus)
				parent.checkStatus = null
			} else {
				window.location.reload()
			}
		}
		
		function checkForm(status){
			var timeCheck=checkTime();
			if(timeCheck!=null&&timeCheck!='undefined'){
				return;
			}
			parent.checkStatus = status
			if(status == '2'){
				document.getElementById("wfTitle2").value="";
				document.getElementById("itemName2").value="";
				document.getElementById("commitTimeFrom2").value="";
				document.getElementById("commitTimeTo2").value="";
				document.getElementById("wh").value="";
				document.getElementById("lwdw").value="";
			}else{
				hopen=false;
			}
			radions_fw();
			if(status == '1'){
				document.getElementById('form').submit();
			}else{
				document.getElementById('pendlist').submit();
			}
		}

		
		function removeSpec(str){
			if(str==null || str==''){
				return "";
			}
			return str.replace(/[\r\n]/g, "");
		}
		
		function checkTime(){
			var commitTimeFrom = document.getElementById("commitTimeFrom").value;
			var commitTimeTo = document.getElementById("commitTimeTo").value;
			var commitTimeFrom2 = document.getElementById("commitTimeFrom2").value;
			var commitTimeTo2 = document.getElementById("commitTimeTo2").value;
			
			if ((""!=commitTimeFrom&&""!=commitTimeTo)&&commitTimeFrom > commitTimeTo||(""!=commitTimeFrom2&&""!=commitTimeTo2)&&commitTimeFrom2 > commitTimeTo2){
				alert("开始时间不能大于结束时间！");
				document.getElementById("commitTimeFrom").value="";
				document.getElementById("commitTimeTo").value="";
				document.getElementById("commitTimeFrom2").value="";
				document.getElementById("commitTimeTo2").value="";
				return 1;
			}
		}
		
		//待阅打开已办
		function openOverForm(processId,itemId,formId,favourite,isHaveChild,process_title){
			var isps = '1';
			var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&isps='+isps+'&isHaveChild='+isHaveChild+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
			openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}
		
		
		var _selectedIndex = 0
		
		//打开待办
		function openPendingForm(processId,itemId, formId, isCanPush, stepIndex, isChildWf, item_type, isCy,isHaveChild,process_title,imgPath){
			parent._selectIndex = paging.selectIndex
			if(item_type!='2'){
				if(stepIndex!='' || isChildWf=='1'){
					openForm(processId,itemId,formId,isCanPush,isHaveChild,process_title,imgPath);
				}
			}else if(item_type =='2' || (stepIndex=='1' && isChildWf == '')){
				window.location.href = "${ctx}/table_openPendingForm.do?processId="+processId
					+"&isDb=true&itemId="+itemId+"&formId="+formId+"&isCy="+isCy+"&isHaveChild="+isHaveChild+'&t='+new Date();
			}
					
		}
		
	  function openForm(processId,itemId,formId,isCanPush,isHaveChild,process_title,imgPath){
		var url = '${curl}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+"&isHaveChild="+isHaveChild+'&t='+new Date();
		openLayerTabs(processId,screen.width,screen.height,process_title,url,imgPath);
	 }
	 
	 //查询当前节点人员组内除本身以外的人员
	 function pushDb(nodeId,processId){
			var ret = window.showModalDialog('${ctx}/group_getInnerOtherUsers.do?nodeId='+nodeId+'&processId='+processId+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no;scroll:no");
			if(ret == 'success'){
				alert("推送成功！");
				window.location.href="${ctx}/table_getPendingList.do";
			}else if(ret == 'fail'){
				alert("推送失败,请联系管理员！");
			}
	 }
	 
	 function openPendingProcess(instanceId){ 	//查看子流程的进程
		 
	 	//根据instanceId 查询申请延期的 待办信息
	 	$.ajax({   
				url : '${ctx}/itemRelation_getBjYqInfo.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(item_getBjYqInfo.do)');
				},
				data : {
					'instanceId':instanceId
				},    
				success : function(result) {  
					var value = result.split(";");
					var instanceid = value[0];		
					var workFlowId = value[1];		
					var ret = window.showModalDialog('${ctx}/table_showProcess.do?instanceId='+instanceid+'&workFlowId='+workFlowId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
				}
			});
	 }
	 
	 function openOverProcess(instanceId){
		 $.ajax({   
				url : '${ctx}/itemRelation_getBjYqOverInfo.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(itemRelation_getBjYqOverInfo.do)');
				},
				data : {
					'instanceId':instanceId
				},    
				success : function(result) {  
					var value = result.split(";");
					var instanceid = value[0];		
					var workFlowId = value[1];		
					var ret = window.showModalDialog('${ctx}/table_showProcess.do?instanceId='+instanceid+'&workFlowId='+workFlowId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
				}
			});
		 
		 
	 }
	 
	 function openDelayForm(lcid,instanceId,delayitemid){
		 var status = checkStatus(lcid);
		 if(status!='0'){
				window.location.href = "${ctx}/table_openFirstForm.do?yqinstanceid="+instanceId+"&workflowid="+lcid+"&itemid="+delayitemid;
		 }else{
				alert("该流程为暂停状态，不可创建实例");
		 }
	 }
	 
	//检查流程状态，“暂停”则不可新建实例
	function checkStatus(lcid){
			var status = "";
			$.ajax({   
				url : '${ctx }/item_getWfStatus.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(item_getWfStatus.do)');
				},
				data : {
					'workflowid':lcid
				},    
				success : function(result) {  
					status = result;
				}
			});
			return status;
		}
	
		function openStartForm(id,url,title){
			openLayerTabs(id,screen.width,screen.height,title,url);
		}
		
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
				var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
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

		function openLayerTabs(processId,width,height,title,url,imgPath){		
		   window.parent.topOpenLayerTabs(processId,1200,600,title,url,imgPath);
		}
		
		function checkCanDel(id){
			$.ajax({   
				url : '${ctx }/table_checkCanDel.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_checkCanDel.do)');
				},
				data : {
					'id':id
				},    
				success : function(result) {  
					if(result == 'success'){
						
					}else if(result == 'fail'){
						alert("该办件不能删除");
						$("[id="+id+"]:checkbox").prop("checked", false);
					}
				}
			});
		}
		function delone(id){
			if(confirm('确定删除所选办件吗')){
				//异步获取上传成功后的doc信息
				$.ajax({
				        async:true,//是否异步
				        type:"POST",//请求类型post\get
				        cache:false,//是否使用缓存
				        dataType:"text",//返回值类型
				        data:{"id":id},
				        url:"${ctx}/table_falseDeleteDoFile.do",
				        success:function(text){
				        	 window.location.reload();
				        }
				    });
			}
		}
		function del(){
			var objs = document.getElementsByTagName('input');
			var ids = '';
			for(var i=0; i<objs.length; i++) {
			   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
				  ids += objs[i].value+",";
			   }
			}
			if(ids==""){
				alert("请选择一条办件");
				return;
			}
			ids = ids.substring(0, ids.length-1);
			if(confirm('确定删除所选办件吗')){
				//异步获取上传成功后的doc信息
				$.ajax({
				        async:true,//是否异步
				        type:"POST",//请求类型post\get
				        cache:false,//是否使用缓存
				        dataType:"text",//返回值类型
				        data:{
				        	"id":ids,
				        	"fromExcute":"pendingList"
				        },
				        url:"${ctx}/table_falseDeleteDoFile.do",
				        success:function(text){
				        	 window.location.reload();
				        }
				    });
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
		
		var clickStatus = "0";
		var count = 0;
		function operateBatch(){
			if(clickStatus == "1"){
				alert("正在提交数据，请稍后。");
				return;
			}
			try {
				var objs = document.getElementsByName("selid");
				var ids = '';
				for(var i=0; i<objs.length; i++) {
				   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].checked==true ){
					  ids += objs[i].value+",";
				   }
				}
				if(ids==""){
					alert("请选择一条办件");
					return;
				}
				$('#loadMask').css("display","block");
				setTimeout(function(){
					clickStatus = "1";
					ids = ids.substring(0, ids.length-1);
					var id = ids.split(",");
					for (var int = 0; int < id.length; int++) {
						operateAuto(id[int]);
					}
					if(count>0){
						alert(count+"个办件由于他人正在办理，未能一键办理");
					}else{
						alert("办理成功");
					}
					count=0;
					clickStatus = "0";
					window.location.reload();
					$('#loadMask').css("display","none");
				}, 100);
			} catch (e) {
				clickStatus = "0";
			}
			
		}
		
		function operateAuto(processId,status,instanceId){
			//var trueJson = document.getElementById(processId+"json").value;
			if($('#loadMask').css("display")=='none'){
				$('#loadMask').css("display","block");
			}
			var trueJson = "";

			$.ajax({
				url : '${ctx}/tableExtend_getAutoJson.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : {
					'processId' : processId,
					'instanceId' : instanceId
				},
				success : function(msg) {
					var json = eval("("+msg+")");
					var result = JSON.stringify(json.result);
					//获取最新意见(原先是列表查询sql查出，现改为点击一键办理查出)
					trueJson = JSON.stringify(json.trueJson);
					if("false" == result){
						count++;
						var userName = json.userName;
						if(null != status && '' != status && status=='1'){
							if(null != userName && '' != userName){
								alert(userName+"正在办理，请稍后。");
							}
						}
						return;
					}
					
					var commentjson = "";
					commentjson = trueJson;
					
					//取得意见后办理
					$.ajax({  
						url : '${ctx}/table_onlySave4List.do',
						type : 'POST',   
						cache : false,
						async : false,
						data : {
							'processId' : processId,
							'json'		: commentjson
						},
						error : function(){
							
						},
						success : function(msg) {
							if(null != status && '' != status && status=='1'){
								alert("办理成功");
								window.location.reload();
								$('#loadMask').css("display","none");
							}
						}
					});
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