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
			<form id="itemform" method="post" action="${ctx}/item_add.do" class="tw-form">
			<table class="tw-table tw-table-form">
				<colgroup>
                    <col  width="20%" />
                    <col />
                </colgroup>
				<tr>
					<th>事项类别：</th>
					<td>
						<input type="text" name="item.vc_sxmc" id="item.vc_sxmc" class="tw-form-text">
					</td>
				</tr>
				<tr>
					<th>所属部门：</th> 
					<td>
						<input type="text" name="item.vc_ssbm" id="item.vc_ssbm" class="tw-form-text" /> 
						<a class="tw-btn-primary" href="javascript:selDep();"><i class="tw-icon-search"></i> 选择</a>
						<input type="hidden" name="item.vc_ssbmid" id="item.vc_ssbmid">
					</td>
				</tr>
				<tr>
					<th>完成时限：</th>
					<td>
						<input type="text" name="item.vc_wcsx" id="item.vc_wcsx" maxlength="3"  class="tw-form-text" value="0"  onpaste="return false;"  onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" maxlength="4" onkeyup="this.value=this.value.replace(/[^\d*]/,'')">（数字,单位为工作日）
					</td>
				</tr>
				<tr>
					<th>事项分类：</th>
					<td>
						<select name="item.vc_sxlx" id="item.vc_sxlx" class="tw-form-select">
							<option value=""></option>
							<option value="0">公文(发文)</option>
							<option value="1">公文(办文)</option>
							<option value="2">公文(传阅)</option>
							<option value="3">客情报告</option>
							<option value="4">用车申请</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>消息分类：</th>
					<td><select name="item.vc_xxlx" id="item.vc_xxlx" class="tw-form-select">
						<option value="2">公文管理</option>
						<option value="4">工作任务</option>
						<option value="5">工作计划</option>
						<option value="8">工作动态</option>
						<option value="11">依申请公开</option>
						<option value="17">工作日志</option>
						<option value="19">车辆管理</option>
						<option value="20">会议管理</option>
						<option value="21">资产管理</option>
						<option value="22">支出管理</option>
					</select></td>
				</tr>
				<tr>
					<th>是否参与预警报警：</th>
					<td>
                    	<div class="tw-form-field-rc"> 
							<input type="hidden" name="item.b_yj" id="item.b_yj" value="1">
							<label class="tw-form-label-rc">
								<input type="radio" name="yj" value="1" class="tw-form-radio" checked="checked" >
								<span>是</span>
							</label>
							<label class="tw-form-label-rc">
								<input type="radio" name="yj" value="0" class="tw-form-radio" >
								<span>否</span>
							</label>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>所属工作流：</th>
					<td>
						<select name="item.lcid" id="item.lcid" class="tw-form-select">
							<c:forEach items="${list}" var="d">
								<option value="${d.wfm_id}">${d.wfm_name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>关联事项：</th>
					<td>
						<select name="item.relatedItemId" id="item.relatedItemId" class="tw-form-select">
							<option value="">--请选择--</option>
							<c:forEach items="${itemList}" var="d">
								<option value="${d.id}">${d.vc_sxmc}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<!-- <tr>
					<th>是否使用弹性表单：</th>
					<td>
						<div class="tw-form-field-rc">
							<label class="tw-form-label-rc">
								<input type="radio" name="item.isFlexibleForm" value="1" class="tw-form-radio" >
								<span>是</span>
							</label>
							<label class="tw-form-label-rc">
								<input type="radio" name="item.isFlexibleForm" value="0" class="tw-form-radio" selected >
								<span>否</span>
							</label>
						</div>
					</td>
				</tr> -->
				<tr>
					<th>向第三方推送数据的接口：</th>
					<td>
						<input type="text" name="item.interfaceUrl" id="item.interfaceUrl" class="tw-form-text" >
					</td>
				</tr>
				<tr>
					<th>待推送数据的tableName：</th>
					<td>
						<input type="text" name="item.outSideTabName" id="item.outSideTabName" class="tw-form-text" >
					</td>
				</tr>
				<tr>
					<th>事项排序号：</th>
					<td>
						<input type="text" name="item.index" id="item.index" class="tw-form-text" >（用于事项排序： 排序号越小, 越靠前）
					</td>
				</tr>
				<tr>
					<th>待办排序号：</th>
					<td>
						<input type="text" name="item.itemSort" id="item.itemSort" class="tw-form-text" >（用于待办置顶： 排序号越小, 待办展示越靠前）
					</td>
				</tr>
				<tr>
                    <th></th>
                    <td>
                    	<button id="btnSuperSearch" class="tw-btn-primary tw-btn-lg" type="submit" onclick="checkForm();">
                       		 <i class="tw-icon-send"></i> 提交
                   		 </button>
                   		 <button class="tw-btn tw-btn-lg" onclick="window.history.go(-1);">
                        	<i class="tw-icon-minus-circle"></i> 返回
                   		 </button>
                    </td>
                </tr>
			</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function checkForm(){
		var fields = "yj".split(",");
		for(var i = 0; i < fields.length; i++){
			var radios = document.getElementsByName(fields[i]);
			for(var j = 0; j < radios.length; j++){
				if(radios[j].checked){
					document.getElementById("item.b_"+fields[i]).value = radios[j].value;
				}
			}
		}

		var sxmc = document.getElementById("item.vc_sxmc").value;
		if(!sxmc){
			alert('请填写事项类别');
			return;
		}
		var ssbm = document.getElementById("item.vc_ssbm").value;
		if(!ssbm){
			alert('请填写所属大部门名称');
			return;
		}
		document.getElementById('itemform').submit();
	}

	function selDep(){
		var url = "${ctx}/item_showTree.do?isBigDep=0&notEmployee=1";
		//用layer的模式打开
		layer.open({
			title:'选择部门树',
			type:2,
			area:['300px', '450px'],
			btn:["确定","取消"],
			content: url,
			yes:function(index,layero){
			 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
	            var ret = iframeWin.selDep();
	        	var item = ret.split(",");
	    		document.getElementById("item.vc_ssbm").value=item[1];
	    		document.getElementById("item.vc_ssbmid").value=item[0];
	    		layer.close(index);
			}
		}); 
	}
</script>
<%@ include file="/common/function.jsp"%>
</html>
