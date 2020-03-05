 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
    <head>
        <title>文号管理</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
		<div class="pageContent" >
			<form id="bigDocNumForm" method="post" name="bigDocNumForm" >
		        <div class="w_list_print">
					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<tr>
							<td>
								<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;名称:</span>
							</td> 
							<td>
								<input id="name"  type="text" name="name" value="${type.name}"/>
							</td>
						</tr>
						<tr>
							<td>
								<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;描述:</span>
							</td> 
							<td>
								<input id="type"  type="text" name="type" value="${type.type}"/>
							</td>
						</tr>
							<!-- <tr>
								<td></td>
								<td>
									<input id="send" type="button" value="提  交" class="btn">
								</td>
							</tr> -->
					</table>
				  </div>
				  <input id="webid" type="hidden" name="webid"  value="${webId}" >
				  <input id="parentid" type="hidden" name="parentid"  value="0" >
				  <input id="isparent" type="hidden" name="isparent"  value="0" >
				  <input id="typeid" type="hidden" name="typeid"  value="${type.typeid}" >
            </form>
        </div>
		<script type="text/javascript">
			$("#send").bind("click",function(){
					if($.trim($("#name").val())==""){
						alert("名称不能为空！");
						return false;
					}
					$.ajax({
						url:"${ctx}/docNumberManager_docNumTypeManage.do",
						type:"post",
						async:false,
						cache: false,
						data:$("#bigDocNumForm").serialize(),
						success:function(msg){
							if(msg=="addOk")alert("新增成功");
							if(msg=="modifyOk")alert("修改成功");
							if(msg=="fail")alert("已有同名文号");
						},
						error:function(){
							alert("系统错误请重试");
						}	
					});
				});
		</script>
    </body>
</html>
