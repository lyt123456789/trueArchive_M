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
	<link rel="stylesheet" href="${ctx}/assets-common/css/reset.css">
	<link rel="stylesheet" href="${ctx}/assets-common/css/scan.css">
	<script src="${ctx}/assets-common/js/jquery.nicescroll.js" type="text/jscript"></script>
</head>
<body>
    <div class="wrapper">
	    <div class="tw-container cl">
		    <div class="tw-container-aside">
			    <div class="tw-aside-box">
					<ul class="tw-aside-menu">
						<li class="active">
							<a href="javascript:void(0)" onclick="showFormPage();" target="myFrame" id="createForm"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/table-icon.png" /></i><p class="pd8">表单构建</p></a>
						</li>	
						<li>
							<a href="javascript:void(0)" onclick="showTablePage();" target="myFrame" id="createTable"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/sjb-icon.png" /></i><p>数据表创建</p></a>
						</li>
						<li>
							<a href="javascript:void(0)" onclick="setColumn();"  target="myFrame" id="setColumn"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/bdzd-icon.png" /></i><p>表单字段匹配</p></a>
						</li>
						<li>
							<a href="javascript:void(0)" onclick="showUserCreate();" target="myFrame"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/jscj-icon.png" /></i><p class="pd8">角色创建</p></a>
						</li>
						<li>
							<a href="${ctx}/mobileTerminalInterface_wrokFlowImgOpen.do?id=${workflowId}" target="myFrame"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/lcgj-icon.png" /></i><p class="pd8">流程构建</p></a>
						</li>
						<li>
							<a href="javascript:void(0)" onclick="showPermition();"  target="myFrame"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/bdqx-icon.png" /></i><p class="pd8">表单权限</p></a>
						</li>
						<li>
							<a href="${ctx}/mobileTerminalInterface_workFlowScan.do?id=${workflowId}" target="myFrame"><i class="tw-aside-icon"><img src="${ctx}/assets-common/image/lcjc-icon.png" /></i><p class="pd8">流程检测</p></a>
						</li>						
					</ul>
				</div>
			</div>
			<div class="tw-container-main">
                <iframe id="myFrame" name="myFrame" src="${ctx}/mobileTerminalInterface_workFlowScan.do?id=${workflowId}" class="myFrame" width="99%" height="100%" frameborder="0" scrolling="auto"></iframe>
			</div>
		</div>       
	</div>
<script>
    $(document).ready(function(){
	    var len = $('.tw-aside-menu li').length;
        $('.tw-aside-menu li').each(function(index){
		    if(index == len-1) {
			    $(this).addClass('end');
			}
		});
		
		$('.tw-aside-menu li').click(function(){
		    $('.tw-aside-menu li').removeClass('active');
			$(this).addClass('active');
		});
		
        $('.tw-aside-box').niceScroll({
            cursorcolor: "#1E9FFF",//#CC0071 光标颜色
            cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
            touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
            cursorwidth: "3px", //像素光标的宽度
            cursorborder: "0", // 	游标边框css定义
            cursorborderradius: "3px",//以像素为光标边界半径
            autohidemode: true //是否隐藏滚动条
        });
        $("#createForm").click();
	});	
    
    function showFormPage(){
    	var addFormUrl = "${ctx}/form_toAddFormJsp.do";
    	var formListUrl = "${ctx}/form_getFormList.do";
    	var updateFormUrl = "${ctx}/form_toUpdateFormJsp.do"
    	var workflowId = '${workflowId}';
    	$.ajax({
			url:"${ctx}/form_getFormCount.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":workflowId
			},
			async : false,
			error: function(){
				alert("ajax错误：form_getFormCount.do");
			},
			success: function(msg){
				if(msg.count == 0){
					$("#myFrame").attr("src",addFormUrl + "?workflowId="+workflowId);
				}else if(msg.count==1){
					$("#myFrame").attr("src",updateFormUrl + "?workflowId="+workflowId+"&formid="+msg.formIds[0]);
				}else{
					$("#myFrame").attr("src",formListUrl + "?workflowId="+workflowId);
				}
			}
		});
    } 
    
    function showTablePage(){
    	var addTableUrl = '${ctx}/table_toAddJsp.do';
    	var tableListUrl = '${ctx}/table_getTableList.do';
    	var updateTableUrl = '${ctx}/table_toEditJsp.do';
    	var workflowId = '${workflowId}';
    	$.ajax({
			url:"${ctx}/table_getTableCount.do",
			dataType : "json",
			type:"post",
			data:{
				"workflowId":workflowId
			},
			async : false,
			error: function(){
				alert("ajax错误：table_getTableCount.do");
			},
			success: function(msg){
				console.log(msg.count);
				if(msg.count == 0){
					$("#myFrame").attr("src",addTableUrl + "?workflowId="+workflowId);
				}else if(msg.count==1){
					$("#myFrame").attr("src",updateTableUrl + "?workflowId="+workflowId+"&id="+msg.ids[0]);
				}else{
					$("#myFrame").attr("src",tableListUrl + "?workflowId="+workflowId);
				}
			}
		});
    }
    
    function setColumn(){
    	var setColumnUrl = "${ctx}/form_toHTMLTag2ColumnJsp.do";
    	var formListUrl = "${ctx}/form_getFormList.do";
    	var workflowId = '${workflowId}';
    	$.ajax({
			url:"${ctx}/form_getFormCount.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":workflowId
			},
			async : false,
			error: function(){
				alert("ajax错误：form_getFormCount.do");
			},
			success: function(msg){
				if(msg.count == 0){
					alert("请先设置表单！");
				}else if(msg.count==1){
					$("#myFrame").attr("src",setColumnUrl + "?formid="+msg.formIds[0]);
				}else{
					$("#myFrame").attr("src",formListUrl + "?workflowId="+workflowId);
				}
			}
		});
    }
    
    function showUserCreate(){
    	var addInnerUser = "${ctx}/group_toAddInnerUserJsp.do";
    	var updateInnerUser = "${ctx}/group_toUpdateInnerUserJsp.do";
    	var innerUserList = "${ctx}/group_getInnerUserList.do";
    	var workflowId = '${workflowId}';
    	$.ajax({
			url:"${ctx}/group_getInnerUserCount.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":workflowId
			},
			async : false,
			error: function(){
				alert("ajax错误：group_getInnerUserCount.do");
			},
			success: function(msg){
				if(msg.count == 0){
					$("#myFrame").attr("src",addInnerUser + "?id="+workflowId);
				}else if(msg.count==1){
					$("#myFrame").attr("src",updateInnerUser + "?id="+msg.userIds[0]);
				}else{
					$("#myFrame").attr("src",innerUserList + "?id="+workflowId);
				}
			}
		});
    }
    
    function showPermition(){
    	var setpermition = "${ctx}/permition_toSetPermitJsp.do?type=1";
    	var permitListUrl = "${ctx}/permition_getFormList.do?type=1";
    	var workflowId = '${workflowId}';
    	$.ajax({
			url:"${ctx}/form_getFormCount.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":workflowId
			},
			async : false,
			error: function(){
				alert("ajax错误：form_getFormCount.do");
			},
			success: function(msg){
				if(msg.count == 0){
					alert("请先设置表单！");
				}else if(msg.count==1){
					$("#myFrame").attr("src",setpermition + "&formid="+msg.formIds[0]+"&workflowid="+workflowId);
				}else{
					$("#myFrame").attr("src",permitListUrl + "&id="+workflowId);
				}
			}
		});
    }
</script>	
</body>
</html>