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
            <form method="post" id="itemform" action="${ctx}/item_edit.do" class="tw-form">
            <table class="tw-table tw-table-form">
                <colgroup>
                    <col  width="20%" />
                    <col />
                </colgroup>
                <tr>
                    <th>事项类别：</th>
                    <td>
                        <input type="text" class="tw-form-text" id="item.vc_sxmc" name="item.vc_sxmc" value="${item.vc_sxmc}">
                        <input type="hidden" name="item.id" id="item.id" value="${item.id}">
                    </td>
                </tr>
                <tr>
                    <th>所属部门：</th>
                    <td>
                    	<input type="text" class="tw-form-text" id="item.vc_ssbm" name="item.vc_ssbm" value="${item.vc_ssbm}">
                        <a class="tw-btn-primary" href="javascript:selDep();"><i class="tw-icon-search"></i> 选择</a>
                        <input type="hidden" name="item.vc_ssbmid" id="item.vc_ssbmid" value="${item.vc_ssbmid}">    
                    </td>
                </tr>
                <tr>
                    <th>完成时限：</th>
                    <td><input type="text" class="tw-form-text" id="item.vc_wcsx" name="item.vc_wcsx" maxlength="3" value="${item.vc_wcsx}" onpaste="return false;" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" maxlength="4" onkeyup="this.value=this.value.replace(/[^\d*]/,'')"><span class="tw-warning"><i></i>（数字,单位为工作日）</span></td>
                </tr>
                <tr>
                    <th>事项分类：</th>
                    <td><select name="item.vc_sxlx" class="tw-form-select" id="item.vc_sxlx">
				            <option value="0" <c:if test="${item.vc_sxlx == '0'}">selected</c:if>>公文(发文)</option>
				            <option value="1" <c:if test="${item.vc_sxlx == '1'}">selected</c:if>>公文(办文)</option>
				            <option value="2" <c:if test="${item.vc_sxlx == '2'}">selected</c:if>>公文(传阅)</option>
				            <option value="3" <c:if test="${item.vc_sxlx == '3'}">selected</c:if>>客情报告</option>
							<option value="4" <c:if test="${item.vc_sxlx == '4'}">selected</c:if>>用车申请</option>
                        </select></td>
                </tr>
                <tr>
                    <th>消息分类：</th>
                    <td><select name="item.vc_xxlx" id="item.vc_xxlx" class="tw-form-select">
							<option value="2" <c:if test="${item.vc_xxlx == '2'}">selected</c:if>>公文管理</option>
							<option value="4" <c:if test="${item.vc_xxlx == '4'}">selected</c:if>>工作任务</option>
							<option value="5" <c:if test="${item.vc_xxlx == '5'}">selected</c:if>>工作计划</option>
							<option value="8" <c:if test="${item.vc_xxlx == '8'}">selected</c:if>>工作动态</option>
							<option value="11" <c:if test="${item.vc_xxlx == '11'}">selected</c:if>>依申请公开</option>
							<option value="17" <c:if test="${item.vc_xxlx == '17'}">selected</c:if>>工作日志</option>
							<option value="19" <c:if test="${item.vc_xxlx == '19'}">selected</c:if>>车辆管理</option>
							<option value="20" <c:if test="${item.vc_xxlx == '20'}">selected</c:if>>会议管理</option>
							<option value="21" <c:if test="${item.vc_xxlx == '21'}">selected</c:if>>资产管理</option>
							<option value="22" <c:if test="${item.vc_xxlx == '22'}">selected</c:if>>支出管理</option>
                        </select></td>
                </tr>                
                <tr>
                    <th>是否参与预警报警：</th>
                    <td>
                    	<div class="tw-form-field-rc">
                        	<input type="hidden" name="item.b_yj" id="item.b_yj" value="1"> 
	                        <label class="tw-form-label-rc">
	                        	<input type="radio" <c:if test="${item.b_yj == '1'}">checked="checked"</c:if> value="1" class="tw-form-radio" name="yj">
	                        	<span>是</span>
	                        </label>
	                        <label class="tw-form-label-rc">
	                        	<input type="radio" <c:if test="${item.b_yj == '0'}">checked="checked"</c:if> value="0" class="tw-form-radio" name="yj">
	                        	<span>否</span>
	                        </label>
	                    </div>
					</td>
                </tr>
                <tr>
                    <th>所属工作流：</th>
                    <td><select name="item.lcid" id="item.lcid" class="tw-form-select">
							<c:forEach items="${list}" var="d">
								<option value="${d.wfm_id}" <c:if test="${d.wfm_id==item.lcid}">selected</c:if>>${d.wfm_name}</option>
							</c:forEach>
                        </select></td>
                </tr>
                <tr>
                    <th>关联事项：</th>
                    <td><select name="item.relatedItemId" id="item.relatedItemId" class="tw-form-select">
							<option value="">--请选择--</option>
							<c:forEach items="${itemList}" var="i">
								<option value="${i.id}" <c:if test="${i.id==item.relatedItemId}">selected</c:if>>${i.vc_sxmc}</option>
							</c:forEach>
                     </select></td>
                </tr>
               <%--  <tr>
					<th >是否使用弹性表单：</th>
					<td>
						<div class="tw-form-field-rc">
							<label class="tw-form-label-rc">
								<input type="radio" name="item.isFlexibleForm" value="1" class="tw-form-radio"  <c:if test="${item.isFlexibleForm=='1'}">checked="checked"</c:if>>
								<span>是</span>
							</label>
							<label class="tw-form-label-rc">
								<input type="radio" name="item.isFlexibleForm" value="0" class="tw-form-radio"  <c:if test="${item.isFlexibleForm=='0'}">checked="checked"</c:if>>
								<span>否</span>
							</label>
						</div>
					</td>
				</tr> --%>
				<tr>
					<th>向第三方推送数据的接口：</th>
					<td>
						<input type="text" name="item.interfaceUrl" id="item.interfaceUrl" class="tw-form-text" value="${item.interfaceUrl}" >
					</td>
				</tr>
				<tr>
					<th>待推送数据的tableName：</th>
					<td>
						<input type="text" name="item.outSideTabName" id="item.outSideTabName" class="tw-form-text" value="${item.outSideTabName}" >
					</td>
				</tr>
				<tr>
                    <th>事项排序号：</th>
                    <td>
                        <input type="text" class="tw-form-text" id="item.index" name="item.index" value="${item.index}"> 	（用于事项排序： 排序号越小, 越靠前）
                    </td>
                </tr>  
                <tr>
					<th>待办排序号：</th>
					<td>
						<input type="text" name="item.itemSort" id="item.itemSort" class="tw-form-text"  value="${item.itemSort}">（用于待办置顶： 排序号越小, 待办展示越靠前）
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
