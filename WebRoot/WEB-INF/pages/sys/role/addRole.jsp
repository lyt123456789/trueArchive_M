<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
	<script src="${ctx}/widgets/js/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/js/true.validate.js" type="text/javascript"></script>
	<style type="text/css">
		.tw-form-horizontal-lg .tw-form-label {
			width: 120px;
		}
		.tw-form-horizontal-lg .tw-form-field{
			padding-left: 120px;
		}
	</style>
</head>
<body>
	<div class="tw-super-search">
		<form id="roleFrom" action="" method="post" class="tw-form tw-form-horizontal-lg">
			<input type="hidden" name="roleStatus" value="1">
			<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>角色名称：</label>
				<div class="tw-form-field">
					<input name="roleName" id="roleName" type="text" maxlength="100" class="textInput width_205"/>
					
				</div>
			</div>
		    <div class="tw-form-item">
              	<label class="tw-form-label">排序号：</label>
		        <div class="tw-form-field">
		         	<input name="roleSort" id="roleSort" type="text" maxlength="6" class="textInput width_205" />
				</div>
			</div>  
			<div class="tw-form-action wf-action-left">
                <div id="btnSuperSearch" class="tw-btn-primary" onclick="saveRole();" >
                    <i class="tw-icon-search"></i> 保存
                </div>
                <div class="tw-btn" onclick="javascript:parent.layer.closeAll();">
                    <i class="tw-icon-minus-circle"></i> 关闭
                </div>
       		</div>
		</form>
	</div>
	<input type="hidden" id="siteId" name="siteId" value="${siteId }" />
</body>
<script type="text/javascript">	
	function saveRole(){
		if(document.getElementById("roleName").value.replace(/(^\s*)|(\s*$)/g, "")==""){ 
			alert("角色名称不能为空！");
			document.getElementById("roleName").focus();
		   	return;
		}
		var siteid = document.getElementById("siteId").value;
		
		$.ajax({
			url:"${ctx}/role_saveRole.do?siteId="+siteid,
			type:"post",
			async:false,
			cache: false,
			data:$("#roleFrom").serialize(),
			success:function(result){	//保存
				var res = eval("("+result+")");
				if(res.success){
					parent.layer.confirm('新增角色成功',{
						btn:['确定']
					},function(){
						parent.location.reload();
						parent.layer.closeAll();
					});
				}else{
					layer.alert("添加失败");
				}
			},
			error:function(){
				layer.alert("系统错误请重试");
			}	
		});
	}

	function changeClass(id){
		$('#'+id).removeClass("formTip");
		$('#'+id)[0].innerHTML = '<span style="color: red;">*</span>';
		return true;
	}
</script>