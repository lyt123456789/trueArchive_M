<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
</head>
<body>
<br>
<div class="wf-layout" style="height:150px;">
	<div class="wf-list-top" style="height:30px;">
		<span style="height:18px">◆个人信息</span>
	</div>
	<div class="wf-list-wrap" style="height:90px;">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/comment_getCommentList.do" >
			<table class="wf-fixtable" >
				<thead>
		    	<tr>
					<th width="15%" align="center" style="font-weight: bold;">登录者</th>
					<th width="25%" align="center" style="font-weight: bold;">所在部门</th>
					<th align="center" width="15%" style="font-weight: bold;">登录时间</th>
				</tr>
		    	</thead>
		    	<tr>
					<td align="center">${userName}</td>
					<td align="center">${deptName}</td>
					<td align="center">${login_date}</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="wf-list-top">
		<span style="height:18px">◆委托设置</span>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/comment_getCommentList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
					<th width="5%" align="center" style="font-weight: bold;">序号</th>
					<th width="25%" align="center" style="font-weight: bold;">事项类别</th>
					<th width="25%" align="center" style="font-weight: bold;">委托人</th>
					<th width="10%" align="center" style="font-weight: bold;">设置</th>
					<th width="" ></th>
				</tr>
		    	</thead>
		    	<c:forEach var="item" items="${iteList}" varStatus="index">
					<tr>
						<td align="center">${index.count }</td>
						<td align="left">${item.wfItem.vc_sxmc}</td>
						
						<td align="left">
							${item.empName}
						</td>
						<Td align="center"><a href="javascript:showEmpTree('${item.wfItem.id}');" class="uedset">设置</a></Td>
						<Td></Td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
	</div>
</div>
<div class="pageContent">
<br>
<div id="empTreeDiv" style="display: none;">
<div id="title" style="height: 20px;"></div>
<div id="frame"></div>
</div>
</div>
</body>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
<script type="text/javascript">
	
	function showEmpTree(itemId){
			art.dialog({  
				title: '选择抄送人',
				lock: false,
			    content: '<iframe id="treeframe"  frameborder="no" marginheight="0" marginwidth="0" style="width:800px;height:500px;;" border="0" src="${ctx}/table_showPersonMessTree.do"></iframe>',
			    id: 'EF893K',
			    ok: function(){
			    	var treeframe = document.getElementById("treeframe").contentWindow;
					var obj = treeframe.document.getElementById('oldSelect');
					var str='';
					if(obj){
						for(var i=0;i<obj.options.length;i++){
							str+=obj.options[i].value+'#';
						};
						if(str!='')str=str.substring(0,str.length-1);
					}
					/* if(str==''){
						alert('请选择人员');
						return false;
					} */
					$.ajax({   
						url : '${ctx }/table_saveXccUserOfPerMes.do',
						type : 'POST',   
						cache : false,
						async : false,
						error : function() {  
							alert('AJAX调用错误(table_saveXccUserOfPerMes.do)');
						},
						data : {
							'employeeinfo':str,'itemId':itemId  
						},    
						success : function(msg) {  
							if(msg == 'yes'){
								alert('设置成功');
								window.location.href="${ctx}/table_personMessage.do";
							}else{
								alert('设置失败,请联系管理员！');
							}
						}
					});
			    }
			});
	}
</script>
<script>
		$('#pageContent').height($(window).height()-120);
</script>
</html>