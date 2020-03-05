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
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="tw-layout">
        <div class="tw-container">
			<form id="depFform" method="post" action="${ctx}/depConfig_addDep_F.do" class="tw-form">
			<input type="hidden" id="docXgDepartment.pid" name="docXgDepartment.pid" value="${docXgDepartment.pid}" />
			<input type="hidden" id="docXgDepartment.isSub" name="docXgDepartment.isSub" value="${docXgDepartment.isSub}" />
			<input type="hidden" id="docXgDepartment.hasSub" name="docXgDepartment.hasSub" value="${docXgDepartment.hasSub}" />
			
			<table class="tw-table tw-table-form">
				<colgroup>
                    <col  width="20%" />
                    <col />
                </colgroup>
				<tr>
					<th>部门名称：</th>
					<td>
						<input type="text" name="docXgDepartment.name" id="docXgDepartment.name" class="tw-form-text">
					</td>
				</tr>
				<c:choose>
				<c:when test="${docXgDepartment.pid=='1'}" >
				<tr>
					<th>自定义部门id：</th> 
					<td>
						<input type="text" name="docXgDepartment.deptGuid" id="docXgDepartment.deptGuid" class="tw-form-text" /> 
					</td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr>
					<th>原始部门名称：</th> 
					<td>
						<input type="text" name="deptName" id="deptName" class="tw-form-text" /> 
						<a class="tw-btn-primary" href="javascript:selDep();"><i class="tw-icon-search"></i> 选择</a>
						<input type="hidden" name="docXgDepartment.deptGuid" id="docXgDepartment.deptGuid">
					</td>
				</tr>
				</c:otherwise>
				</c:choose>
				<tr>
					<th>排序号：</th>
					<td>
						<input type="text" name="docXgDepartment.orderNum" id="docXgDepartment.orderNum" class="tw-form-text" >（排序号越小, 部门展示越靠前）
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function checkForm(){
		var name = document.getElementById("docXgDepartment.name").value;
		if(!name){
			alert('请填写部门名称');
			return;
		}
		var deptGuid = document.getElementById("docXgDepartment.deptGuid").value;
		if(!deptGuid){
			alert('请选择原始部门');
			return;
		}
		var orderNum = document.getElementById("docXgDepartment.orderNum").value;
		if(orderNum.length>0){
			if(orderNum.match(/^\d*$/)==null){
				alert("排序号应为数字,请重填");
				document.getElementById('docXgDepartment.orderNum').value="";
				document.getElementById('docXgDepartment.orderNum').focus();
				return false;
			}
			if(orderNum.length>6){
				alert("排序号应小于999999");
				document.getElementById('docXgDepartment.orderNum').value="";
				document.getElementById('docXgDepartment.orderNum').focus();	
				return false;
			}
		}else{
			alert("排序号不能为空,请填写");
			document.getElementById('docXgDepartment.orderNum').focus();	
			return false;
		}
		$.ajax({
			url : '${ctx}/depConfig_isSameDep.do',
			type : 'POST',  
			cache : false,
			async : false,
			data :  $('#depFform').serialize(),
			error : function() {
				alert('AJAX调用错误(depConfig_isSameDep.do)');
			},
			success : function(msg) { 
				var obj = eval('('+msg+')');
				if(obj.success==false){
					alert("所选择部门id已被使用，请重新选择！");
					document.getElementById("docXgDepartment.deptGuid").focus();
					return false;
				}else{
					$.ajax({
						url : '${ctx}/depConfig_addDep_F.do',
						type : 'POST',  
						cache : false,
						async : false,
						data :  $('#depFform').serialize(),
						error : function() {
							alert('AJAX调用错误(depConfig_addDep_F.do)');
						},
						success : function(msg) { 
							alert("保存成功！");
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						}
					});	
				}
			}
		});	
	}

	function selDep(){
		var url = "${ctx}/item_showTree.do?isBigDep=0&notEmployee=1";
		//用layer的模式打开
		layer.open({
			title:'选择部门树',
			type:2,
			area:['300px', '350px'],
			btn:["确定","取消"],
			content: url,
			yes:function(index,layero){
			 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
	            var ret = iframeWin.selDep();
	        	var item = ret.split(",");
	    		document.getElementById("deptName").value=item[1];
	    		document.getElementById("docXgDepartment.deptGuid").value=item[0];
	    		layer.close(index);
			}
		}); 
	}
</script>
<%@ include file="/common/function.jsp"%>
</html>
