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
   <link href="${ctx}/assets-common/css/common.css" type="text/css" rel="stylesheet" />
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
		.tw-table-form th, .tw-table-form td {
			padding: 5px;
		}
		
	</style>
</head>
<body style="width: 100%;">
	<div class="tw-layout">
		<div class="tw-container">
		    <form method="get" id="roleForm" name="roleForm" action="#" class="tw-form">
		    	<input id="roleId" name="roleId" value="${roleId }" type="hidden"></input>
		    	<input id="searchSiteName" name="searchSiteName" value="${searchSiteName }" type="hidden"></input>
		    	<input id="ifsave" name="ifsave" value="" type="hidden"></input>
		    	<input id="userIdAndNames" name="userIdAndNames" value="" type="hidden"></input>
		    	<input id="deptIdAndNames" name="deptIdAndNames" value="" type="hidden"></input>
		    	<input id="portalRes" name="portalRes" value="" type="hidden"></input>
		    	<input id="oaRes" name="oaRes" value="" type="hidden"></input>
		    	<input id="wfRes" name="wfRes" value="" type="hidden"></input>
				<table class="tw-table-form tm-table-form">
			        <colgroup>
			            <col width="20%" />
			            <col />
			        </colgroup>
					<tr>
				   		<th>角色名称</th>
			    	 	<td>
			    	 		<input id="roleName"  name="roleName"  style="width: 260px;" value=""></input>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>所属站点</th>
			    	 	<td>
			    	 	<c:choose>
			    	 	<c:when test="${searchSiteName!=''&&searchSiteName!=null}">
			    	 		<select id="siteId" name="siteId" disabled="disabled">
			    	 			<option value="">-请选择-</option>
			    	 			<c:forEach var="item" items="${siteList}" varStatus="status">
			    	 				<option id="${item.id }" value="${item.id }" <c:if test="${item.id eq searchSiteName }">selected='selected'</c:if>>${item.name }</option>
			    	 			</c:forEach>
			    	 		</select>
			    	 	</c:when>
			    	 	<c:otherwise>
			    	 		<select id="siteId" name="siteId" disabled="disabled">
			    	 			<option value="">-请选择-</option>
			    	 			<c:forEach var="item" items="${siteList}" varStatus="status">
			    	 				<option id="${item.id }" value="${item.id }" <c:if test="${item.id eq siteId}">selected='selected'</c:if>>${item.name }</option>
			    	 			</c:forEach>
			    	 		</select>
			    	 	 </c:otherwise>
			    	 	</c:choose>
			    	 	</td>
				    </tr>
		    	 	<tr>
		    	 		<th>绑定部门</th>
			    	 	<td>
			    	 		<textarea id="roleDepts"  name="roleDepts"  style="width: 560px;height:30px;" onclick="showDeptTree();"></textarea>
			    	 	</td>
		    	 	</tr>
			    	 <tr>
			    	 	<th>绑定人员</th>
			    	 	<td>
			    	 		<textarea id="roleEmps"  name="roleEmps"  style="width: 560px;height:30px;" onclick="showEmpTree();"></textarea>
			    	 	</td>
			    	 </tr>
			    	 <tr>
			    	 	<th>门户权限</th>
			    	 	<td>
			    	 		<textarea id="ptlRight"  name="ptlRight"  style="width: 560px;height:30px;" onclick="showPortalRscList('1');"></textarea>
			    	 	</td>
			    	 </tr>
			    	 <tr>
			    	 	<th>办公自动化</th>
			    	 	<td>
			    	 		<textarea id="oaRight"  name="oaRight"  style="width: 560px;height:30px;"  onclick="showPortalRscList('2');"></textarea>
			    	 	</td>
			    	 </tr>
			    	 <!-- <tr>
			    	 	<th>流程</th>
			    	 	<td>
			    	 		<textarea id="wfRight"  name="wfRight"  style="width: 560px;height:30px;"  onclick="showPortalRscList('3');"></textarea>
			    	 	</td>
			    	 </tr> -->
			    	 <tr>
			    	 	<th>排序</th>
			    	 	<td>
			    	 		<input id="seq" name="seq" style="width: 260px;"></input> 
			    	 	</td>
			    	 </tr>
			    	 <tr>
			    	 	<th>默认</th>
			    	 	<td>
			    	 		<input id="ifDefault" name="ifDefault" type="checkbox" value="1"></input> 
			    	 	</td>
			    	 </tr>
			    	 <tr>
			    	 	<td align="center" colspan="2">
			    	 		<button class="wf-btn-primary" type="button"  style="size: 16px;margin-top: 10px;" onclick="save();">
			                    <i class="wf-icon-plus-circle" ></i> 确认
			                </button>
			    	 	</td>
			    	 </tr>
					
			    </table>
		    </form>
		</div>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	function save(){
		var seq = $('#seq').val();
		if(!isRealNum(seq)){
			alert('排序应为数字');
			return;
		}
		var roleName = $('#roleName').val();
		if(roleName == "" || roleName == null){
			alert("角色名称不能为空!");
			return;
		}
		var siteId = $('#siteId').val();
		if(siteId == "" || siteId == null){
			alert("所属站点不能为空!");
			return;
		}
		
		$("#siteId").removeAttr("disabled");
		$.ajax({
			url : 'siteManage_addRole.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :$('#roleForm').serialize(),
			error : function(e) {  
				alert('AJAX调用错误(siteManage_addRole.do)');
			},
			success : function(msg) {
				var ret = msg.split(';');
				if(ret[0] == 'success'){
					alert('添加成功！');
				}else if(ret[0] == 'fail'){
					alert(ret[1]);
				}
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
				window.parent.location.reload();
			}
		});
	}
	
	function isRealNum(val){
		if(val === "" || val == null){
			return false;
		}
		if(!isNaN(val)){
			return true;
		}else{
			return false;
		}
	}
	
	
	//显示部门树
	function showDeptTree(){
		var deptIds = $('#deptIdAndNames').val();
		var deptIdArr = deptIds.split(';');
		deptIds = deptIdArr[0];
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&isDeptTree=1&userIds=' + deptIds + '&t='+new Date();
		var searchSiteName = $('#searchSiteName').val();
		if(searchSiteName!=null&&searchSiteName!=undefined&&searchSiteName!=""){
			url += '&searchSiteName='+searchSiteName;
		}
		top.layer.open({
            title: '添加部门',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            btn:["确认选择","取消选择"],
            yes:function(index,layer){
            	var iframeWin = top[layer.find('iframe')[0]['name']];
            	var deptIdAndNames = iframeWin.test();
            	var deptIdAndNameArr = deptIdAndNames.split(';');
            	var deptNames = "";
            	if(deptIdAndNameArr.length > 1){
            		deptNames = deptIdAndNameArr[1];
            	}
            	$('#roleDepts').val(deptNames);
        		document.getElementById("deptIdAndNames").value = deptIdAndNames;
            	top.layer.close(index);
            	/* top.$('.layui-layer-shade').css('display', 'none');
            	top.$('.layui-layer-setwin').css('display', 'none'); */
            	/* if(top.$('.layui-layer-iframe')[1]){
            		top.$('.layui-layer-iframe')[1].style.setProperty('width', '500px', 'important');
            		top.$('.layui-layer-iframe')[1].style.setProperty('top', '250px', 'important');
            	} */
            },
            end:function(){
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1]
            	sheet.deleteRule(0)
            	//console.log('end==========================')
            },
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1]
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            }
        });
	}
	
	//显示人员树
	function showEmpTree(){
		var userIds = $('#userIdAndNames').val();
		var userIdArr = userIds.split(';');
		userIds = userIdArr[0];
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&userIds=' + userIds + '&t='+new Date();
		var searchSiteName = $('#searchSiteName').val();
		if(searchSiteName!=null&&searchSiteName!=undefined&&searchSiteName!=""){
			url += '&searchSiteName='+searchSiteName;
		}
		top.layer.open({
            title: '添加人员',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            btn:["确认选择","取消选择"],
            yes:function(index,layer){
            	var iframeWin = top[layer.find('iframe')[0]['name']];
            	var userIdAndNames = iframeWin.test();
            	var userIdAndNameArr = userIdAndNames.split(';');
            	var userNames = "";
            	if(userIdAndNameArr.length > 1){
            		userNames = userIdAndNameArr[1];
            	}
            	$('#roleEmps').val(userNames);
            	document.getElementById("userIdAndNames").value = userIdAndNames;
            	top.layer.close(index);
            	/* top.$('.layui-layer-shade').css('display', 'none');
            	top.$('.layui-layer-setwin').css('display', 'none'); */
            	/* if(top.$('.layui-layer-iframe')[1]){
            		top.$('.layui-layer-iframe')[1].style.setProperty('width', '500px', 'important');
            		top.$('.layui-layer-iframe')[1].style.setProperty('top', '250px', 'important');
            	} */
            },
            end:function(){
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1]
            	sheet.deleteRule(0)
            	//console.log('end==========================')
            },
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1]
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            }
           
        });
	}
	
	//门户资源页面
	function showPortalRscList(resType){
		var siteId = $('#siteId').val();
		var title = "";
		var selectedIds = "";
		if('1' == resType){
			title = "门户资源";
			var portalRes = $('#portalRes').val();
        	var portalResArr = portalRes.split(';');
        	selectedIds = portalResArr[0];
		}else if('2' == resType){
			title = "办公自动化资源";
			var oaRes = $('#oaRes').val();
        	var oaResArr = oaRes.split(';');
        	selectedIds = oaResArr[0];
		}else if('3' == resType){
			title = "流程资源";
			var wfRes = $('#wfRes').val();
        	var wfResArr = wfRes.split(';');
        	selectedIds = wfResArr[0];
		}
		var url = '${curl}/siteManage_getResourceList.do?siteId='+siteId+'&resType=' + resType + '&selectedIds=' + selectedIds + '&t='+new Date();
		layer.open({
            title: title,
            area: ['300px', '400px'],
            type: 2,
            content: url,
            end: function(index){
            	
            	if($("#ifsave").val()=='0'){
            		return;
            	}
            	if('1' == resType){
            		var portalRes = $('#portalRes').val();
                	var portalResArr = portalRes.split(';');
                	var ptlRight = "";
                	if(portalResArr.length > 1){
                		ptlRight = portalResArr[1];
                	}
                	$('#ptlRight').val(ptlRight);
        		}else if('2' == resType){
        			var oaRes = $('#oaRes').val();
                	var oaResArr = oaRes.split(';');
                	var oaRight = "";
                	if(oaResArr.length > 1){
                		oaRight = oaResArr[1];
                	}
                	$('#oaRight').val(oaRight);
        		}else if('3' == resType){
        			var wfRes = $('#wfRes').val();
                	var wfResArr = wfRes.split(';');
                	var wfRight = "";
                	if(wfResArr.length > 1){
                		wfRight = wfResArr[1];
                	}
                	$('#wfRight').val(wfRight);
        		}
            }
        });
	}
</script>
</html>