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
	
	.tw-btn-green {
	padding: 4px 8px;
	font-size: 15px;
	color: #fff;
	background-color: #5eb95e;
	border-color: #5eb95e;
	}	
	
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
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar" style="padding: 20px 20px 15px 5px">
			<form name="pendlist"  id="pendlist" action="${ctx }/monitor_getExceedPendingList2.do?a=Math.random();" method="post" style="display:inline-block;">
	 	        <input type="hidden" name="isPortal" value="${isPortal}"/>
 	        	
 	        	
 	        	<label class="wf-form-label" for="">标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" style="width: 90px;min-width: 90px" value="${wfTitle}" placeholder="输入关键字">
                
            	<label class="wf-form-label" for="">时间：</label>
                <select class="wf-form-select" id="intervalDate"  name="intervalDate" onchange="searchExceedPending()">
                	<option value=""></option>
	 				<option value="5"  <c:if test="${intervalDate == '5' }">selected</c:if>>一周</option>
	 				<option value="10" <c:if test="${intervalDate == '10' }">selected</c:if>>两周</option>
	 				<option value="15" <c:if test="${intervalDate == '15' }">selected</c:if>>三周</option>
	 				<option value="20" <c:if test="${intervalDate == '20' }">selected</c:if>>一个月</option>
	 				<option value="40" <c:if test="${intervalDate == '40' }">selected</c:if>>两个月</option>
                </select>
            		
                
                 <label class="wf-form-label" for="">站点：</label>
               <select class="wf-form-select" id="departmentGuid" name="departmentGuid">
                	<option value=""></option>
                	<c:forEach var="m" items='${depts}'>
	 					<option value="${m.departmentGuid}" <c:if test="${departmentGuid ==m.departmentGuid}">selected="selected"</c:if>>${m.departmentName}</option>
	 				</c:forEach> 
                </select> 
          
             
				<label class="wf-form-label" for="">日期：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
	            <button class="wf-btn-primary" type="submit" onclick="checkForm('2');"><i class="wf-icon-search"></i> 搜索</button>
			</form>
			<%-- <span style="position:relative;">
	            <a class="high-search" href="#" onclick="showSearch();"> 高级搜索</a>
				<div id="high-search">
					<form method="post" id="form" name="form" action="#" class="tw-form">
            			<table class="new-htable" >
            			
			                <tr>
			                    <th width="85">标题：</th>
			                    <td><input type="text"   placeholder="请输入标题关键字" class="tw-form-text" id="wfTitle2" name="wfTitle" value="${wfTitle}"></td>
			                </tr>
			                <tr>
			                    <th>事项类型：</th>
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
			                    <th>办件接受时间：</th>
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
			                    <td><input type="text" placeholder="请输入来文单号" class="tw-form-text" id="lwdw" name="lwdw" value="${lwdw}"></td>
			                </tr>
							<tr>
			                   <th></th>
			                   <td>
			                   	<button class="wf-btn-primary" type="submit" onclick="checkForm('1');"><i class="wf-icon-search"></i> 搜索</button>
							 	<a class="high-search" href="#" onclick="form.reset()">清空搜索条件</a>
								</td>
			                </tr>
            			</table>
            		</form>
				</div>
			</span> --%>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/monitor_getExceedPendingList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="4%">序号</th>
						<th width="19%">办件标题</th>
						<th width="7%">当前步骤</th>
						<th width="18%">办理人</th>
						<th width="10%">所属站点</th>
						
						<th width="12%">提交时间</th>
						<th width="9%">未办理周期</th>
						<th width="9%">催办</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${item[5]}">
							<c:choose>
								<c:when test="${item[0] == '0'}">
									<span class="tp-info-cate FW">${item[1] }</span>
								</c:when>
								<c:when test="${item[0] == '1'}">
									<span class="tp-info-cate SW">${item[1] }</span>
								</c:when>
								<c:when test="${item[0] == '3'}">
									<span class="tp-info-cate XMSP">${item[1] }</span>
								</c:when>
								<c:when test="${item[0] == '4'}">
									<span class="tp-info-cate FKSQ">${item[1] }</span>
								</c:when>
								<c:otherwise>
									<span class="tp-info-cate">${item[1] }</span>
								</c:otherwise>
							</c:choose>
							<a href="#" onclick="openForm('${item[6] }','','','', '${item[5] }');">
											${item[5]}
							</a>
							
						</td>
						<td align="center">${item[9]}</td>

						<td align="center" >${item[8] }</td>
						<td align="center">${item[12]}</td>
						<td align="center" title="${fn:substring(item[10],0,16)}">${fn:substring(item[10],0,16)} </td>
						<td align="center">${item[11] }天</td>
						<td align="center"> <a class="tw-btn-green" onclick="sendMsg('${item[3]}','${item[11] }');" >
	                <i class="wf-icon-send"></i> 催办
	            </a></td>
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
	
	function searchExceedPending(){
		document.getElementById('pendlist').submit();
	}
	</script>
	
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			if(parent._selectIndex){
				selectIndex = parent._selectIndex
				parent._selectIndex = null
			} else {
				selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);
			}
			skipUrl="<%=request.getContextPath()%>"+"/monitor_getExceedPendingList2.do";			//提交的url,必须修改!!!*******************
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
	function sendMsg(instanceId,intervalDate){
		layer.open({
            title: '发送人员列表',
            area: ['500px', '350px'],
            type: 2,
           	// btn: ['确认', '取消'],
            content: '${ctx}/monitor_getSendUser.do?instanceId='+instanceId+'&intervalDate='+intervalDate,
        });
	}
	
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
			debugger;
			if(!hopen){
				document.getElementById("wfTitle2").value=document.getElementById("wfTitle").value;
/* 				document.getElementById("itemName2").value=document.getElementById("itemName").value;
 */				document.getElementById("commitTimeFrom2").value=document.getElementById("commitTimeFrom").value;
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
/* 			var itemName2 = document.getElementById("itemName2").value;
 */			document.getElementById("high-search").style.display="block";
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
			document.getElementById('pendlist').submit();
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
			if ((""!=commitTimeFrom&&""!=commitTimeTo)&&commitTimeFrom > commitTimeTo){
				alert("开始时间不能大于结束时间！");
				document.getElementById("commitTimeFrom").value="";
				document.getElementById("commitTimeTo").value="";
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
			//1,断定目前处理人员；ajax
			processId = processId.split(',')[0];
			
			$.ajax({   
				url : '${ctx}/table_checkIsDealIng.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('校验是否可以立即办理出错');
				},
				data : {
					'processId':processId
				},
				success : function(result) { 
					if(result==''){
						if(item_type!='2'){
							if(stepIndex!='' || isChildWf=='1'){
								openForm(processId,itemId,formId,isCanPush,isHaveChild,process_title,imgPath);
							}
						}else if(item_type =='2' || (stepIndex=='1' && isChildWf == '')){
							window.location.href = "${ctx}/table_openPendingForm.do?processId="+processId
								+"&isDb=true&itemId="+itemId+"&formId="+formId+"&isCy="+isCy+"&isHaveChild="+isHaveChild+'&t='+new Date();
						}
					}else{
						alert(result+"正在办理之中！");
						return;
					}
				}
			});
		}
		
	/*   function openForm(processId,itemId,formId,isCanPush,isHaveChild,process_title,imgPath){
		var url = '${curl}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+"&isHaveChild="+isHaveChild+'&t='+new Date();
		openLayerTabs(processId,screen.width,screen.height,process_title,url,imgPath);
	 } */
	 
	  
	  function openForm(processId,itemId,formId,favourite,process_title){
			if(processId!=''){
				 var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
				 openLayerTabs(processId,screen.width,screen.height,process_title,url);
			}
		}

	 //查询当前节点人员组内除本身以外的人员
	 function pushDb(nodeId,processId){
			var ret = window.showModalDialog('${ctx}/group_getInnerOtherUsers.do?nodeId='+nodeId+'&processId='+processId+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no;scroll:no");
			if(ret == 'success'){
				alert("推送成功！");
				window.location.href="${ctx}/monitor_getExceedPendingList.do";
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
				        data:{"id":ids},
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