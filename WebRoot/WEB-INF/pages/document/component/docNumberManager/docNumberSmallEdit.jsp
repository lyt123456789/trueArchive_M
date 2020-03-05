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
		<style type="text/css">
		input {
    	    border: 1px solid;
    	    border-color: #A2BAC0 #B8D0D6 #B8D0D6 #A2BAC0;
    	}
		</style>
    </head>
    <body>
    	<div class="pageContent" >
			<form id="docForm" method="post" name="form1">
		        <div class="w_list_print">
					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<tr>
							<td style="width: 20%">
								<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;名称:</span>
							</td>
							<td >
								<input type="hidden" name="name"  id="name"value="${type.name}"/>
								<input type="text"  id="begin" name="" value=""/>
								<select name="middle" id="middle">
									<option value="" >--请选择--</option>
									<option value="$year$" >自增年号</option>
									<option value="$num$" >自增序号</option>
									<option value="$num3$" >自增序号(长度3)</option>
									<option value="$num4$" >自增序号(长度4)</option>
									<option value="$chus$">处室</option>
								</select>
								<input name="doctype" id="doctype" type="hidden">
								<input type="text"  id="end" name="" value=""/>
								<font color="red" >*</font>
							</td>
						</tr>
						<tr>
							<td >
								<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;所属文号大类:</span>
							</td>
							<td >
								<select name="parentid" id="parentid" onchange="changeArea(this);">
									<option value="" >--请选择--</option>
			            			<c:forEach var="bigtype" items="${bigTypeList}">
			            				<option value="${bigtype.typeid}" >${bigtype.name}</option>
			            			</c:forEach>
			            		</select>
			            		<font color="red" >*</font>
							</td>
						</tr>
						<tr>
							<td >
								<span style="font-size: 14; font-family: 黑体;">&nbsp;&nbsp;描述:</span>
							</td>
							<td >
								<input type="text" name="type" id="type" maxlength="5" value="${type.type}" />
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" name="webid"  value="${webId}" />
				<input id="isparent" type="hidden" name="isparent"  value="1" />
				<input id="typeid" type="hidden" name="typeid"  value="${type.typeid}"/>
				<input id="workflowId" type="hidden" name="workflowId"  value="${workflowId}" >
			 </form>
        </div>
		<script type="text/javascript">
			/* $("#middle").dblclick(function(){
				parent.layer.open({
	    			title:'编辑文号小类',
	    			type:2,
	    			area:['580px','380px'],
	    			content:"${ctx}/docNumberManager_toStrategyList.do",
	    			btn:["确定","取消"],
	    			yes:function(index, layero){
	    				$(this).val(retVal.biaoshi);
	    			}
	    		});
			}); */
			$("#send").bind("click",function send(){
				$("#name").val($("#begin").val()+$("#middle").val()+$("#end").val());
				 if($.trim($("#name").val())==""){
						alert("名称不能为空！");
						$('#name').focus();
						return false;
				 }
				 if($.trim($("#name").val()).match(/[,;]/)!=null){
					 	alert("名称不能包含特殊字符(,和;)");
						$('#name').select();
						return false;
				 }
				 if($.trim($("#parentid").val())==""){
						alert("所属文号类别不能为空！");
					$('#parentid').focus();
						return false;
				 }
				 if($.trim($("#doctype").val())==""){
						alert("所属公文类别不能为空！");
					$('#doctype').focus();
						return false;
				 }
				 $.ajax({
						url:"${ctx}/docNumberManager_docNumTypeManage.do",
						type:"post",
						async:false,
						cache: false,
						data:$("#docForm").serialize(),
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
			
			function changeArea(obj){
				var val = $(obj).find("option:selected").text()
				if(val=="文号"){
					$("#middle").hide();
					$("#end").hide();
					$("#doctype").val("gjdz");
				}else if(val == "处室"){
					$("#middle").show();
					$("#begin").hide();
					$("#end").hide();
					$("#doctype").val("chus");//文号类型为处室
				}else{
					$("#middle").show();
					$("#end").show();
					if(val=="年号"){
						$("#doctype").val("fwnh");
					}
					if(val=="序号"){
						$("#doctype").val("fwxh");
					}
				}
			}
		</script>
		<script type="text/javascript">
			$(function(){
				if("${msg}"!==""){
					alert("${msg}");
					window.location.href="${ctx}/docNumberManager_docNumberSmallClass.do";
				}
				$("#parentid").val('${type.parentid}');
				$("#doctype").val('${type.doctype}');
			});
			$(function(){
				var str = "${type.name}";
				var reVal = new Array();
				if(str.indexOf("$")==-1){
					reVal[0]=str;
				}else{
					var beginIndex = str.indexOf("$");
					var endIndex = str.lastIndexOf("$");
					reVal[0] = str.substring(0,beginIndex);
					reVal[1] = str.substring(beginIndex, endIndex+1);
					reVal[2] = str.substring(endIndex+1,str.length);
				}
				$("#begin").val(reVal[0]);
				$("#middle").val(reVal[1]);
				$("#end").val(reVal[2]);
				
				var val = $("#parentid").find("option:selected").text();
				if(val=="文号"){
					$("#middle").hide();
					$("#end").hide();
					$("#doctype").val("gjdz");
				}else if(val == "处室"){
					$("#middle").show();
					$("#end").hide();
					$("#begin").hide();
					$("#doctype").val("chus");//文号类型为处室
				}else{
					$("#middle").show();
					$("#end").show();
					if(val=="年号"){
						$("#doctype").val("fwnh");
					}
					if(val=="序号"){
						$("#doctype").val("fwxh");
					}
				}
			});
		</script>
    </body>
</html>
