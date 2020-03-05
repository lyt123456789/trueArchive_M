<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
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
		.new-htable .wf-form-text{
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
			<div class="wf-list-top" style="position: relative;">
				<div class="wf-search-bar">
					<form action="${ctx}/siteManage_showAllEmp.do" method="post" name="queryForm" id="queryForm">
						<c:if test="${admin eq '1' }">
						    <label class="wf-form-label" for="" style="size: 16px" >站点选择：</label>
						    <select id="sitename" name="sitename" onchange="changeSite()">
						    	<c:forEach var="item" items="${siteList}" varStatus="status">
			    	 				<option id="${item.id }" value="${item.id }" <c:if test="${item.id eq siteId}">selected='selected'</c:if>>${item.name }</option>
			    	 			</c:forEach>
						    </select>
						  </c:if>
							<label class="wf-form-label" for="" style="size: 16px;margin-left: 25px;" >姓名：</label>
							<input id="name" name="name" type="text" value="${name}" placeholder="输入关键字查询" class="wf-form-text">
							<%-- <label class="wf-form-label" for="" style="size: 16px;margin-left: 25px;" >创建时间：</label>
							<input name="begintime" id="begintime" type="text" value="${beginTime }" class="wf-form-text wf-form-date" placeholder="选择查询时间" >
							<label class="wf-form-label" for="" style="size: 16px;margin-left: 25px;" >至：</label>
							<input name="endtime" id="endtime" type="text" value="${endTime}"   class="wf-form-text wf-form-date" placeholder="选择查询时间"> --%>
			               	<button class="wf-btn-primary" type="submit" onclick="doSearch();"><i class="wf-icon-search"></i> 搜索</button>
		            		<button class="wf-btn-primary" type="button" onclick="clean();"><i class="wf-icon-remove" ></i> 清空</button>
		            		
		            		<button class="wf-btn-primary JS_add" type="button" onclick="add();"><i class="wf-icon-plus-circle" ></i> 添加</button>
				          <!--   <button class="wf-btn-primary JS_add" type="button" onclick="add();"><i class="wf-icon-cloud-download" ></i> 导入</button>
				            <button class="wf-btn-primary JS_add" type="button" onclick="add();"><i class="wf-icon-cloud-upload" ></i> 导出</button> -->
				            <button style="font-size:14px;padding:6px 8px;" class="wf-btn-purple JS_add" type="button" onclick="batchChangePW();"><i class="wf-icon-unlock" ></i> 批量设置密码</button>
				            <button style="font-size:14px;padding:6px 8px;" class="wf-btn-orangered JS_add" type="button" onclick="batchLeave();"><i class="wf-icon-sign-out" ></i> 离职</button>
				           <!--  <button class="wf-btn-primary JS_add" type="button" onclick="add();"><i class="wf-icon-hand-o-right" ></i> 绑定key</button>
				            <button class="wf-btn-primary JS_add" type="button" onclick="add();"><i class="wf-icon-hand-o-left" ></i> 解除绑定</button> -->
					</form>
				</div>
			</div>
			<div class="">
				<table class="" layoutH="140" style="width: 100%;" cellspacing="0" id="allDiv">
					<tbody>
						<tr>
							<td style="width: 15%;" >
								<div class="wf-list-wrap" id="leftDiv" > 
									<table class="wf-fixtable" layoutH="140"  style="width: 100%;"  cellspacing="0">
										<thead>
						                    <tr>
										    	<th width="15%">组织机构/人员</th>
						                    </tr>
						                </thead>
										<tbody>
											<c:forEach var="item" items="${returnList}" varStatus="status">
												<c:choose>
													<c:when test="${item.issite eq '1' }">
														<tr onclick="searchSite('${item.id }')" id="${item.id }"><td title="${item.name }">${item.name }</td></tr>
													</c:when>
													<c:when test="${item.issite ne '1' && item.fatherDep eq item.siteId }">
													
														<tr id="${item.id }"><td onclick="showDep('${item.id}',1)" style="padding-left:40px;" title="${item.name }">${item.name }</td></tr>
													</c:when>
												</c:choose>
											</c:forEach>
						                </tbody>
									</table>
								</div>
							</td>
							<td style="width: 85%;">
								<iframe src="${ctx}/siteManage_showEmp.do?depId=${deptId}&siteId=${siteId }&name=${name}"  id="rightDiv" scrolling="auto" frameborder="0" style="width: 100%;"></iframe>
							</td>
						</tr>
	                </tbody>
				</table>
			</div>
	</div>
	<input id="depId" value="" type="hidden"></input>
	</body>
	
	<script src="${ctx}/widgets/js/pagingForUnifiledUser.js?t=12261" type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/laydate/laydate.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	
 	$("#rightDiv").height($(window).height()-105+'px');
	$("#leftDiv").height($(window).height()-102+'px'); 
	
		function update(id){
			layer.open({
	            title: '添加站点',
	            area: ['500px', '300px'],
	            type: 2,
	           	// btn: ['确认', '取消'],
	            content: '${ctx}/siteManage_updateDep.do?depId=${siteId}'+id+'&isadmin=1'
	        });
		}
		
		function check(id){
			layer.open({
	            title: '添加站点',
	            area: ['500px', '300px'],
	            type: 2,
	           	// btn: ['确认', '取消'],
	            content: '${ctx}/siteManage_updateDep.do?depId='+id
	        });
		}
	
		function doSearch(){
			var sitename=$("#sitename").val();
			var username=$("#name").val();
			var myform = document.getElementById("queryForm");
	  		myform.action="${ctx}/siteManage_showAllEmp.do";
	  		myform.submit();
	  	}
		
		function changeSite(){
			var sitename=$("#sitename").val();
			var username=$("#name").val();
			var myform = document.getElementById("queryForm");
	  		myform.action="${ctx}/siteManage_showAllEmp.do";
	  		myform.submit();
		}
	
		function add(){
			var depId=$("#depId").val();
			
			if(depId != null && depId != ''){
				layer.open({
		            title: '添加人员',
		            area: ['800px', '500px'],
		            type: 2,
		           	// btn: ['确认', '取消'],
		            content: '${ctx}/siteManage_toAddEmp.do?depId='+depId
		        });
			}else{
				alert("请选择部门！");
			}
		}
		
		//批量修改密码
		function batchChangePW(){
			debugger;
			var selid = window.frames[0].document.getElementsByName('selid');
			var personIds = "";
			var passWords = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					personIds += selid[i].value.split(",")[0] + ",";
				}
			}
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					passWords += selid[i].value.split(",")[1] + ",";
				}
			}
			
			if(passWords.length > 0){
				passWords = passWords.substring(0, passWords.length - 1);
			}
			if(personIds.length > 0){
				personIds = personIds.substring(0, personIds.length - 1);
			}else {
				alert('请选择一个人员对象');
				return;
			}
			
			layer.open({
	            title: '批量设置密码',
	            area: ['400px', '200px'],
	            type: 2,
	           	// btn: ['确认', '取消'],
	            content: '${ctx}/siteManage_toChangePW.do?personIds='+personIds+'&passWords='+passWords
	        });
	       }
		
		//批量离职
		function batchLeave(){
			debugger;
			var selid = window.frames[0].document.getElementsByName('selid');
			var personIds = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					personIds += selid[i].value.split(",")[0] + ",";
				}
			}
			
			if(personIds.length > 0){
				personIds = personIds.substring(0, personIds.length - 1);
			}else {
				alert('请选择一个离职对象');
				return;
			}
			//ajax调用
			$.ajax({
				url : '${ctx}/siteManage_batchLeave.do?personIds='+personIds,
				type : 'POST',  
				cache : false,
				async : false,
				error : function() {
				alert('AJAX调用错误(siteManage_batchLeave.do)');
					return false;
				},
				success : function(msg) {   
					if(msg =='success'){
						alert("离职成功！")
						window.location.href = '${ctx}/siteManage_showAllEmp.do' ;
					}
				}
			 });
	       }
		
		function clean(){
			$("#name").val("");
			
		}
	
		function showDep(id,level){
			var distance = 65 + (parseInt(level)-1)*30;
			var newLevel = parseInt(level)+1;
			//添加背景，记录表id
			$("tr").removeClass("tw-actived");
			var flag = $("tr").hasClass(id+"tr");
			var tr = $("#"+id);
			var colorFlag =  $("tr").hasClass("tw-actived");
			if(colorFlag){
				$("tr").removeClass("tw-actived");
			}else{
				$("#"+id).addClass("tw-actived");
			}
			changeVal(id);
			if(flag){
				var trClass = $("."+id+"tr").remove();
			}else{
				var str = "";
				  $.ajax({  
				        type : "post",
				        url: "${ctx}/siteManage_getDepByPid.do?",  
				        dataType : "text",//数据类型为jsonp    
				        data : {"pid":id},
				        success : function(data){
				        	data = eval("("+data+")");
				        	var success = data.success;
				        	if(success=='success'){
				        		 json = data.list;
				        		 $.each(json,function(j,item){
				        			 str +="<tr id='"+item.id+"' class='"+id+"tr' ><td onclick='showDep(\""+item.id+"\",\""+newLevel+"\")' style='border-bottom: none !important;'><div style='margin-left:"+distance+"px;'>"+item.name+"</div></td></tr>";
				        		 });
				        		 tr.after(str);
				        	}
				        },  
				        error:function(data){  
				        }  
				    }); 
			}
		}
		
	function changeVal(id){
		$("#depId").val(id);
		//刷新rightDiv
		$('iframe').attr('src','${ctx}/siteManage_showEmp.do?depId='+id);  
	} 
	
	function searchSite(id){
		//刷新rightDiv
		$('iframe').attr('src','${ctx}/siteManage_showEmp.do?siteId='+id);  
	} 
	
	</script>
	
	<script type="text/javascript">
	window.onload = function(){
		setTimeout(function(){
			$('.wf-fixtable-scroller').height($('.wf-list-wrap').height()-40);
			$('.wf-fixtable-scroller').width($('.wf-fixtable-wrap').width());
			$('.wf-fixtable-tbody table').width($('.wf-fixtable-tbody').width());
			$('.wf-fixtable-thead table').width($('.wf-fixtable-tbody').width());
			
		},800)
	}
	
	</script>
</html>