<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
	<head>
		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
         <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function submitFilter(){
				var whsort=document.getElementById('whSort').value;
				if(whsort.length>0){
					if(whsort.match(/^\d*$/)==null){
						alert("排序号应为数字,请重填");
						document.getElementById('whSort').select();	
						return false;
					}
					if(whsort.length>6){
						alert("排序号应小于999999");
						document.getElementById('whSort').select();	
						return false;
					}
				}else{
					alert("排序号不能为空,请填写");
					document.getElementById('whSort').focus();	
					return false;
				}
				document.getElementById('form1').submit();
			}
		</script>
	</head>

	<body>
		<div>
		  <form id="form1" action="${ctx}/docNumberManager_updateModel.do"  method="post" >
			<input type="hidden" value="${whModel.modelid }" name="modelid"/>
	        <div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;文号实例:</span>
						</td> 
						<td>
							<script type="text/javascript">
			             		WHContent='${whModel.content}';
			             		tempWH=WHContent.match(/^.*;/)[0].replace(/,/g,"");
			             		document.write(tempWH.substr(0,tempWH.length-1));
			             	</script>
						</td> 
					</tr>
					<tr>
						<td>
							<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;排序号:</span>	
						</td> 
						<td>
							<input type="text" mice-input="input" value="${whModel.modelindex}" name="whSort" id="whSort"/>
							<span style="color: red;">(*最大不超过999999)</span>
						</td> 
					</tr>
					<!-- <tr>
						<td colspan="2" align="right">
							<input type="button" mice-btn="icon-apply" value="更 改" class="btn" onclick="submitFilter();"/>
						</td>
					</tr> -->
				</table>
			  </div>
          </form>
        </div>
		<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
		<script type="text/javascript">
		function subform(){
			$.ajax({
				url : '${ctx}/docNumberManager_updateModel.do',
				type : 'POST',
				cache : false,
				async : false,
				data : $("#form1").serialize(),
				error : function() {
					alert('AJAX调用错误(docNumberManager_updateModel.do)');
				},
				success : function(msg) {
					alert(msg);
				}
			});
		}
		</script>
	</body>
</html>
