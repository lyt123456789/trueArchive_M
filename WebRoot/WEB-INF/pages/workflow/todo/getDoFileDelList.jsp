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
			width:113px!important;
			min-width: 100px!important;
			max-width: 120px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
			display:none;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		
		.w-auto-10 {
			width: 9% !important;
			min-width: 9% !important;
		}
		
		.wf-search-bar .wf-form-label{
			margin-left: 0px;
		}
		
		.auto-date-width{
			width:120px!important;
		}
		
		#high-search{
			top:0;
		}
		.high-search-btn{
			font-size:14px;
			color:#4284ce;
			margin-left:2px;
		}
		
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="dofileList"   id="dofileList" action="${ctx}/table_getDoFileList.do?type=del" method="post" >
			<div class="wf-top-tool">
				<c:choose>
					<c:when test="${favourite=='1'}">
					</c:when>
					<c:otherwise>
						<a class="wf-btn-danger" onclick="javascript:shanchu();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
			                <i class="wf-icon-minus-circle"></i> 删除
			            </a>
					</c:otherwise>
				</c:choose>	
	        </div>
	            <label class="wf-form-label" for="">办件标题：</label>
	            <input type="text" class="wf-form-text w-auto-10" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">事项类型：</label>
                <select class="wf-form-select" id="itemName" name="itemName" style="margin-right: 0px;width: 123px;">
                	<option value=""></option>
                	<c:forEach var="m" items='${myPendItems}'>
	 					<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
	 				</c:forEach> 
                </select>
				<label class="wf-form-label" for="">发文日期：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至             
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
			    <input type="hidden" id="itemid" name="itemid" value="${itemid}" />
	           <button class="wf-btn-primary" type="button" onclick="checkForm('2');" style="margin-left: 5px;">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
	            <a class="high-search-btn" href="#" onclick="showSearch();">
	                 	高级搜索
	            </a>
				<input type="hidden" name="favourite" value="${favourite}"/>
				<input type="hidden" name="itemName" value="${itemName}"/>
				<input type="hidden" name="isAdmin" value="${isAdmin}"/>
				<input type="hidden" name="type" value="${type}"/>
				<input type="hidden" name="isShowWH" value="${isShowWH}"/>
				<input type="hidden" name="redirect" value="${redirect}"/>
            
            <span style="position:relative;">
				<div id="high-search" style="background-size: cover;height: 355px;">
						<input type="hidden" name="itemid" value="${itemid}" />
						<input type="hidden" name="favourite" value="${favourite}"/>
						<input type="hidden" name="isShowExp" value="${isShowExp}"/>
	            		<table class="new-htable">
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
			                    <th>发文日期：</th>
			                    <td><input type="text" class="tw-form-text wf-form-date auto-date-width" id="commitTimeFrom2" name="commitTimeFrom" value="${commitTimeFrom}" style="width:163px;">
									至
									<input type="text" class="tw-form-text wf-form-date auto-date-width" id="commitTimeTo2" name="commitTimeTo" value="${commitTimeTo}" style="width:163px;">
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
						            <input type="reset" class="high-search" style="border: 0;background: none;" value="清空搜索条件"/>
							 		<!-- <a class="high-search" href="#" onclick="form.reset()">
				                 		清空搜索条件
				            		</a> -->
								</td>
			                </tr>
						</table>
				</div>
			</span>
			</form>
			<form name="dofileListH"  id="dofileListH" action="${ctx}/table_getDoFileList.do?type=del" method="post">
			<input type="hidden" name="favourite" value="${favourite}"/>
			<input type="hidden" name="itemName" value="${itemName}"/>
			<input type="hidden" name="isAdmin" value="${isAdmin}"/>
			<input type="hidden" name="type" value="${type}"/>
			<input type="hidden" name="isShowWH" value="${isShowWH}"/>
			<input type="hidden" name="redirect" value="${redirect}"/>
			<input type="hidden" name="wfTitle" value="${wfTitle}"/>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getDoFileList.do?type=del" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
						<th width="5%"><input id='chc_all' type='checkbox' onclick='checkAllchc()'/></th>
						<th align="center" width="5%">序号</th>
						<th width="30%">办件标题</th>
						<c:if test="${isShowWH == '1' }">
						<th width="10%">文号</th>
						</c:if>
						<th width="10%">事项类别</th>
						<th width="10%">当前步骤</th>
						<th width="10%">办件状态</th>
						<th width="15%">办理时间</th>
						<c:if test="${redirect =='1' }">
							<th  width="10%">操作</th>
						</c:if>
						<c:if test="${favourite=='1'}">
							<th  width="10%">操作</th>
						</c:if>
		        	</tr>
		    	</thead>
		    	<c:forEach items="${doFileList}" var="doFile" varStatus="status">
			    	<tr>
					<td align="center"><input type="checkbox" name="typeid" value="${doFile.doFile_id}"/></td>
					<td>${(selectIndex-1)*pageSize+status.count}</td>
					<td style="text-align: left;" title="${fn:contains(doFile.doFile_title,'*')&&fn:indexOf(doFile.doFile_title,'*')==36?fn:substring(doFile.doFile_title,37,-1):doFile.doFile_title}"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
					 <a href="#" onclick="openForm('${doFile.processId}','${doFile.itemId}','${doFile.formId}','${favourite}','${fn:contains(doFile.doFile_title,'*')&&fn:indexOf(doFile.doFile_title,'*')==36?fn:substring(doFile.doFile_title,37,-1):doFile.doFile_title}');">
						<c:choose>  
							<c:when test="${fn:length(fn:contains(doFile.doFile_title,'*')&&fn:indexOf(doFile.doFile_title,'*')==36?fn:substring(doFile.doFile_title,37,-1):doFile.doFile_title) > 25}">  
							<c:out value="${fn:substring(fn:contains(doFile.doFile_title,'*')&&fn:indexOf(doFile.doFile_title,'*')==36?fn:substring(doFile.doFile_title,37,-1):doFile.doFile_title, 0, 25)}.." />  
							</c:when>  
							<c:otherwise>  
							<c:out value=" ${fn:contains(doFile.doFile_title,'*')&&fn:indexOf(doFile.doFile_title,'*')==36?fn:substring(doFile.doFile_title,37,-1):doFile.doFile_title}" />  
							</c:otherwise>  
						</c:choose></a>
						</span>
						</td>
						<c:if test="${isShowWH == '1' }">
						<td align="center" title="${doFile.wh}">${doFile.wh}</td>
						</c:if>
						<td>${doFile.itemName}</td>
						<td align="center">${doFile.nodeName}</td>
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
						<td>${fn:substring(doFile.applyTime,0,16)}</td>
						<c:if test="${redirect =='1' }">
							<td><a href="#" onclick="redirect('${doFile.instanceId}');">重定向</a></td>
						</c:if>
						<c:if test="${favourite=='1'}">
							<td><a href="#" onclick="deleteCollection('${doFile.instanceId}','${doFile.workflowId}');">取消</a></td>
						</c:if>
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
			skipUrl="<%=request.getContextPath()%>"+"/table_getDoFileList.do?type=del";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('dofileList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
			setTimeout(function(){
				var list2 = document.getElementsByClassName('wf-input-datepick');
				for(var i=0;i<2;i++){
					list2[i].style.width = '123px';
				}
				for(var i=2;i<4;i++){
					list2[i].style.width = '123px';
				}
			}, 500);
		};
</script>
<script type="text/javascript">
	var ids="";
	function checkAllchc(){ 
		var chc_all=document.getElementById('chc_all');
		var chcs=document.getElementsByName('typeid');
		for(var i=0;i<chcs.length;i++){
			if(chc_all.checked==true){
				chcs[i].checked=true;
			}else{
				chcs[i].checked=false;
			}
		}
	}
	
	function shanchu(){
		var objs = document.getElementsByTagName('input');
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='typeid' && objs[i].checked==true ){
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
			        	"fromExcute":"getDoFileDelList"
			        },
			        url:"${ctx}/table_falseDeleteDoFile.do",
			        success:function(text){
			        	 window.location.reload();
			        }
			    });
		}
	}
	function openForm(processId,itemId,formId,favourite,process_title){
		if(processId!=''){
		    var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
		    openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}
	}
	
	function redirect(instanceId){
		window.location.href="${ctx}/table_getRedirectDetail.do?instanceId="+instanceId;
	}
	
	function openLayerTabs(processId,width,height,title,url){
	   parent.topOpenLayerTabs(processId,1200,600,title,url);
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
		}
		radions_fw();
		document.getElementById('dofileList').submit();
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
			return 1;;
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
		}
		else{
			document.getElementById("high-search").style.display="none";
			hopen=false;
		}
	}
	
</script>
</html>