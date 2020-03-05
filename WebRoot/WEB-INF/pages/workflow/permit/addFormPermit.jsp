<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body onload="getRole()">
	<form id="permitform" method="POST" name="thisForm" action="${ctx }/permit_addFormPermit.do" >
		<input type="hidden" name="vc_formid" id="vc_formid" value="${vc_formid}">
    	<div id="w_list_print" align="center">
    		
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="25%">
				    		节点名
				    	</th>
				    	<th width="10%" align="center">
				    		权限
				    	</th>
				    	<th width="" align="center">
				    		
				    	</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${list}" var="d" varStatus="n">
				    	<tr height="30px">
					    	<td align="center">
					    		${n.count}
					    		<input type="hidden" name="nodeid" id="nodeid" value="${d.wfn_id}">
					    	</td>
					    	<td >
					    		${d.wfn_name}
					    		<c:if test="${d.wfn_name == null && d.wfn_type == null}">
					    			结束节点
					    		</c:if>
					    		<c:if test="${d.wfn_name == null && d.wfn_type == 'start'}">
					    			开始节点
					    		</c:if>
					    	</td>
					    	<td  align="center">
					    		<select name="vc_limit" id="vc_limit" style="width:80px">
					    			<option value="1">只读</option>
					    			<option value="2">读写</option>
					    			
					    			<option value="0">隐藏</option>
					    		</select>
					    	</td>
					    	<td>
					    	</td>
				    	</tr>
				    </c:forEach>
				</tbody>
		    </table>
		    <div>
		    <br>
		    <table width="100%" class="infotan">
		    	<Tr>
		    		<Td width="10%" align="right" class="bgs ls">角色类型:</Td>
		    		<Td width="90%">
			    		<select onchange="getRole();" name="vc_roletype" id="vc_roletype" style="width: 200px">
							<option value="1">内置用户组</option>
							<option value="2">全局用户组</option>
							<option value="3">平台用户组</option>
							<option value="4">流程用户组</option>
							<option value="5">动态角色</option>
							<option value="6">部门</option>
							<option value="7">用户</option>
							<option value="8">部门级联</option>
						</select>
					</Td>
		    	</Tr>
		    	<Tr  id="rolediv" >	
		    		<Td width='10%' align='right' class='bgs ls'>角色名称:</td> 
		    		<td id="rolenameTd"> 
		    			<select name='vc_rolename' id='vc_rolename' style='width: 200px'>
		    			</select>
		    		</td>	
		    	</Tr>
		    	<Tr >	
		    		<Td colspan="2">
		    			<iframe style="display: none;overflow:auto;" id="roletree" src="${ctx}/permit_showDepartmentTree.do" width="350px" height="300px" scrolling="auto"></iframe>
		    		</td>	
		    	</Tr>
		    </table>
			
		</div>
    	</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li><a class="buttonActive" href="javascript:checkForm();"><span>提交</span></a></li>
				<li><a class="buttonActive" href="javascript:location.href='${ctx}/permit_getFormPermitList.do?vc_formid=${vc_formid}'"><span>返回</span></a></li>
			</ul> 
		</div>
		</form>
    </body>
   
	<script type="text/javascript">
		$("table.list", document).cssTable();

		function getRole( ){
			var type = document.getElementById('vc_roletype').value;
			if(type * 1 <= 5){
				document.getElementById('rolediv').style.display="";
				document.getElementById('roletree').style.display="none";
				$.post("${ctx}/permit_getRole.do?type="+type, null, function(value) {
					//document.getElementById('vc_rolename').innerHTML = value;
					var s1 = "<select name='vc_rolename' id='vc_rolename' style='width: 200px'>";
					var s2 = "</select>";
					document.getElementById('rolenameTd').innerHTML = s1+value+s2;
				});
			}else{
				document.getElementById('rolediv').style.display="none";
				document.getElementById('roletree').style.display="";
			}
		}

		function checkForm(){
			var type = document.getElementById('vc_roletype').value;
			if(type*1 == 6){
				var dore = document.getElementById('dore').value;
				if(dore*1 != 0){
					alert('请选择部门');
					return;
				}
			}
			if(type*1 == 7){
				var dore = document.getElementById('dore').value;
				if(dore*1 != 1){
					alert('请选择人员');
					return;
				}
			}
			document.getElementById('permitform').submit();
		}
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
