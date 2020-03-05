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
	</style>
</head>
<body style="width: 100%;">
	<div class="tw-layout">
		<div class="tw-container">
		    <form method="get" id="deptForm" name="deptForm" action="#" class="tw-form">
		    	<input id="siteId" name="siteId" value="${siteId }" type="hidden"></input>
		    	<input id="isadmin" name="isadmin" value="${isadmin }" type="hidden"></input>
		    	<input id="id" name="id" value="${returnSite.id }" type="hidden"></input>
		    	<input id="isSite" name="isSite" value="${returnSite.issite }" type="hidden"></input>
				<table class="tw-table-form tm-table-form">
			        <colgroup>
			            <col width="20%" />
			            <col />
			        </colgroup>
					<tr>
				   		<th>部门名称</th>
			    	 	<td>
			    	 		<input id="name"  name="name"  <c:if test="${isadmin ne '1' }">readonly="readonly"</c:if> style="width: 260px;" value="${returnSite.name }"></input>
			    	 	</td>
				    </tr>
		    	 	<tr>
		    	 		<th>上级部门</th>
			    	 	<td>
			    	 		<input id="name"  name="pname" value="${returnSite.pname }" style="width: 260px;" readonly="readonly"></input>
			    	 	</td>
		    	 	</tr>
			    	 <tr>
			    	 	<th>排序</th>
			    	 	<td><input id="seq"  name="seq"  value="${returnSite.seq }" style="width: 260px;" <c:if test="${isadmin ne '1' }">readonly="readonly"</c:if>></input></td>
			    	 </tr>
			    	 <%-- <tr>
			    	 	<th>编码</th>
			    	 	<td><input id="code"  name="code"  value="${returnSite.code }" style="width: 260px;" <c:if test="${isadmin ne '1' }">readonly="readonly"</c:if>></input></td>
			    	 </tr> --%>
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
		var isadmin = '${isadmin}';
		if(isadmin!='1'){ 
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
			return;
		}
		
		$.ajax({
			url : 'siteManage_updateDep.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :$('#deptForm').serialize(),
			error : function(e) {  
				alert('AJAX调用错误(siteManage_updateDep.do)');
			},
			success : function(msg) {
				if(msg == 'success'){
					alert('修改成功！');
				}else{
					alert('修改失败！');
				}
				window.parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		});
	}
</script>
</html>