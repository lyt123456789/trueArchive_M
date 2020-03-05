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
<body>
	<div class="tw-layout">
		<div class="tw-container">
		    <form method="get" id="deptForm" name="deptForm" action="#" class="tw-form">
		    	<input id="siteId" name="siteId" value="${siteId }" type="hidden"></input>
				<table class="tw-table-form tm-table-form">
			        <colgroup>
			            <col width="20%" />
			            <col />
			        </colgroup>
					<tr>
				   		<th>部门名称</th>
			    	 	<td>
			    	 		<input id="name"  name="name" style="width: 260px;"></input>
			    	 		<input type="hidden" id="id"  name="id"></input>
			    	 	</td>
				    </tr>
		    	 	<tr>
		    	 		<th>上级部门</th>
			    	 	<td>
			    	 		<input id="pName"  name="pName" value="${pname }" style="width: 260px;"></input>
			    	 		<input type="hidden" id="pId"  name="pId"  value="${pid }"></input>
			    	 	</td>
		    	 	</tr>
			    	 <tr>
			    	 	<th>排序</th>
			    	 	<td><input id="seq"  name="seq"  value="${seq+1 }" style="width: 260px;"></input></td>
			    	 </tr>
			    	 <!-- <tr>
			    	 	<th>编码</th>
			    	 	<td><input id="code"  name="code"  value="" style="width: 260px;"></input></td>
			    	 </tr> -->
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
		$.ajax({
			url : 'siteManage_addDept.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :$('#deptForm').serialize(),
			error : function(e) {  
				alert('AJAX调用错误(siteManage_addDept.do)');
			},
			success : function(msg) {
				if(msg == 'success'){
					alert('保存成功！');
				}else{
					alert('保存失败！');
				}
				window.parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		});
	}
</script>
</html>