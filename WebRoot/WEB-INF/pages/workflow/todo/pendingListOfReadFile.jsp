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
	<script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
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
			<form name="pendlist"  id="pendlist" action="${ctx }/tableExtend_getPendingListOfReadFile.do?a=Math.random();" method="post" style="display:inline-block;">
	 	        <input type="hidden" name="isPortal" value="${isPortal}"/>
	 	        <input type="hidden" name="siteId" value="${siteId}"/>
			    <div class="wf-top-tool">
					<a class="wf-btn-primary" href="#" onclick="operateBatch();" target="_self">
						<i class="wf-icon-pencil" style="display:inline-block;"></i> 批量办理
					</a>
				</div>
 	        	<label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" style="width: 90px" value="${wfTitle}" placeholder="输入关键字">
				<label class="wf-form-label" for="">发文日期：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
			    <input type="hidden" id="siteId" name="siteId" value="${siteId}" />
	            <button class="wf-btn-primary" type="submit" onclick="checkForm('2');">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</form>
			<span style="position:relative;">
	            <a class="high-search" href="#" onclick="showSearch();">
	               	  高级搜索
	            </a>
				<div id="high-search">
					<form method="post" id="form" name="form" action="#" class="tw-form">
					<input type="hidden" name="siteId" value="${siteId}"/>
            		<table class="new-htable">
               
		                <tr>
		                    <th width="85">标题：</th>
		                    <td><input type="text"   placeholder="请输入标题关键字" class="tw-form-text" id="wfTitle2" name="wfTitle" value="${wfTitle}"></td>
		                </tr>
		                <tr>
		                    <th>事项名称：</th>
		                    <td>
		                    	<select class="tw-form-select" id="itemName2" name="itemName">
					 				<option value=""></option>
				                	<c:forEach var="m" items='${myPendItems}'>
					 					<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
					 				</c:forEach> 
				                </select>
		                    </td>
		                </tr>
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
		             		<th></th>
		                    <td>
		                    	<button class="wf-btn-primary" type="submit" onclick="checkForm('1');">
			                		<i class="wf-icon-search"></i> 搜索
			            		</button>
						 		<a class="high-search" href="#" onclick="form.reset()"> 清空搜索条件</a>
							</td>
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
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/tableExtend_getPendingListOfReadFile.do" >
		<input type="hidden" name="siteId" value="${siteId}"/>
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
		    	  		<th width="4%"><input type="checkbox" name="selAll" class="regular-checkbox" id="selAll" onclick="sel();"><label for="selAll"></label></th>
                        <th align="center" width="4%">序号</th>
						<th >标题</th>
						<th width="7%">当前步骤</th>
						<!-- <th width="10%">提交处室</th> -->
						<th width="7%">提交人</th>
						<th width="18%">提交时间</th>
						<th width="5%">收藏</th>
						<th width="10%">操作</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
			    		<td align="center" itemid="${item.wf_process_uid}">
							<input type="checkbox" name="selid" id="${item.wf_process_uid}" class="regular-checkbox"  value="${item.wf_process_uid}" >
							<label for="${item.wf_process_uid}"></label>
						</td>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${item.process_title}">
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
											<a href="#" onclick="openPendingForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.isCanPush}', '${item.stepIndex}', '${item.isChildWf}', '${item.item_type}','${isCy}','${item.isHaveChild}','${item.process_title}','${item.imgPath}');">
												${item.process_title}
											</a>
										</c:when>
										<c:otherwise>
											${item.process_title}
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${item.message_type == '2' }">
										<a href="#" onclick="openOverForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.favourite}','${item.isHaveChild}','${item.process_title}');">
											${item.process_title}（办结待阅）
										</a>
								</c:if>
							</c:if>
							<c:if test="${containsMail == null || containsMail == '' || containsMail != '1' }">
								<c:choose>
									<c:when test="${item.isDelaying != '1'}">
										<a href="#" onclick="openPendingForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.isCanPush}', '${item.stepIndex}', '${item.isChildWf}', '${item.item_type}','${isCy}','${item.isHaveChild}','${item.process_title}','${item.imgPath}');">
											<font <c:if test="${item.jssj == null or item.jssj == '' }">style="font-weight: 700;"</c:if>>${item.process_title}</font>
										</a>
									</c:when>
									<c:otherwise>
										<font <c:if test="${item.jssj == null or item.jssj == '' }">style="font-weight: 700;"</c:if>>${item.process_title}</font>
									</c:otherwise>
								</c:choose>
							</c:if>
							</font>
						</td>
						<td align="center">${item.nodeName}</td>
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
						<td>
							<textarea style="display: none;" id="${item.wf_process_uid}json">${item.commentJson}</textarea>
							<div class="wf-btn-primary" onclick="operate('${item.wf_process_uid}','1','${item.wf_instance_uid}');">
				                <i class="wf-icon-pencil"></i> 一键办理
				            </div>
						</td>
					</tr>	
		    	</c:forEach>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/tableExtend_getPendingListOfReadFile.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('pendlist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
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
				document.getElementById("commitTimeFrom2").value=document.getElementById("commitTimeFrom").value;
				document.getElementById("commitTimeTo2").value=document.getElementById("commitTimeTo").value;
				
				document.getElementById("wfTitle").value="";
			document.getElementById("commitTimeFrom").value="";
			document.getElementById("commitTimeTo").value="";
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
		
		function checkForm(status){
			var timeCheck=checkTime();
			if(timeCheck!=null&&timeCheck!='undefined'){
				return;
			}
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
			document.getElementById('pendlist').submit();
		}

		//待阅打开已办
		function openOverForm(processId,itemId,formId,favourite,isHaveChild,process_title){
			var isps = '1';
			var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&isps='+isps+'&isHaveChild='+isHaveChild+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
			openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}
		
		//打开待办
		function openPendingForm(processId,itemId, formId, isCanPush, stepIndex, isChildWf, item_type, isCy,isHaveChild,process_title,imgPath){
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
	 
		function openLayerTabs(processId,width,height,title,url,imgPath){		
		   window.parent.topOpenLayerTabs(processId,1200,600,title,url,imgPath);
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
		
		var count = 0;
		function operate(processId,status,instanceId){
			//var trueJson = document.getElementById(processId+"json").value;
			if($('#loadMask').css("display")=='none'){
				$('#loadMask').css("display","block");
			}
			var trueJson = "";

			setTimeout(function(){
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
						var isAutoNoname = JSON.stringify(json.autoNoname);//无落款
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
						
						var areaId = json.areaId;
						var userInfo = json.userInfo;
						var info = JSON2.stringify(userInfo);
						var commentjson = "";
						if(isAutoNoname=='true'||isAutoNoname=='"true"'){
							commentjson = trueJson;
						}else{
							OCXpdfobj2.SetSignType(2);
							commentjson = OCXpdfobj2.GetAutoJson(trueJson,info,"",areaId);
						}
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
			}, 30);
		}
		
		var clickStatus = "0";
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
					var isAutoNoname = JSON.stringify(json.autoNoname);//无落款
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
					
					var areaId = json.areaId;
					var userInfo = json.userInfo;
					var info = JSON2.stringify(userInfo);
					var commentjson = "";
					if(isAutoNoname=='true'||isAutoNoname=='"true"'){
						commentjson = trueJson;
					}else{
						OCXpdfobj2.SetSignType(2);
						commentjson = OCXpdfobj2.GetAutoJson(trueJson,info,"",areaId);
					}
					/*OCXpdfobj2.SetSignType(2);
					var commentjson = OCXpdfobj2.GetAutoJson(trueJson,info,"",areaId);*/
					
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
		    			} else {
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