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
<body>
	<form id="permitform" method="POST" name="thisForm" action="${ctx }/permit_editPermit.do" >
		<input type="hidden" name="vc_formid" id="vc_formid" value="${vc_formid}">
		<input type="hidden" name="type" id="type" value="${type}">
    	<div id="w_list_print" align="center">
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="25%" align="center">
				    		角色类型
				    	</th>
				    	<th width="20%" align="center">
				    		角色名称
				    	</th>
				    	<th width="25%">
				    		节点名
				    	</th>
				    	<th width="25%" align="center">
				    		权限
				    	</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${list}" var="d" varStatus="n">
				    	<tr height="30px">
					    	<td align="center">
					    		${n.count}
					    	</td>
					    	<td align="center" >
				    	 		<c:if test="${d.vc_roletype=='1'}">内置用户组</c:if>
				    	 		<c:if test="${d.vc_roletype=='2'}">全局用户组</c:if>
				    	 		<c:if test="${d.vc_roletype=='3'}">平台用户组</c:if>
				    	 		<c:if test="${d.vc_roletype=='4'}">流程用户组</c:if>
				    	 		<c:if test="${d.vc_roletype=='5'}">动态角色</c:if>
				    	 		<c:if test="${d.vc_roletype=='6'}">部门</c:if>
				    	 		<c:if test="${d.vc_roletype=='7'}">用户</c:if>
				    	 		<c:if test="${d.vc_roletype=='8'}">部门级联</c:if>
					    		<input type="hidden" name="lcid" id="lcid" value="${d.lcid}">
					    		<input type="hidden" name="vc_rolename" id="vc_rolename" value="${d.vc_roleid},${d.vc_rolename}">
					    		<input type="hidden" name="vc_roletype" id="vc_roletype" value="${d.vc_roletype}">
					    		<input type="hidden" name="vc_formid" id="vc_formid" value="${d.vc_formid}">
					    		<input type="hidden" name="vc_tagname" id="vc_tagname" value="${d.vc_tagname}">
					    	</td>
					    	<td align="center" >
				    	 		${d.vc_rolename}
					    	</td>
					    	<td >
					    		<input type="hidden" name="nodeid" id="nodeid" value="${d.vc_missionid}">
					    		<c:forEach items="${nodeList}" var="k">
						    		<c:if test="${d.vc_missionid==k.wfn_id}">
						    			${k.wfn_name}
						    			<c:if test="${k.wfn_name == null && k.wfn_type == null}">
							    			结束节点
							    		</c:if>
							    		<c:if test="${k.wfn_name == null && k.wfn_type == 'start'}">
							    			开始节点
							    		</c:if>
						    		</c:if>
						    		
					    		</c:forEach>
					    	</td>
					    	<td>
					    		<select name="vc_limit" id="vc_limit">
					    			<option value="2" <c:if test="${d.vc_limit == '2'}">selected</c:if>>读写</option>
					    			<option value="1" <c:if test="${d.vc_limit == '1'}">selected</c:if>>只读</option>
					    			<option value="0" <c:if test="${d.vc_limit == '0'}">selected</c:if>>隐藏</option>
					    		</select>
					    	</td>
				    	</tr>
				    </c:forEach>
				</tbody>
		    </table>
    	</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li><a class="buttonActive" href="javascript:checkForm();"><span>提交</span></a></li>
				<c:if test="${vc_tagname==null}">
					<li><a class="buttonActive" href="javascript:location.href='${ctx}/permit_getFormPermitList.do?vc_formid=${vc_formid}'"><span>返回</span></a></li>
				</c:if>
				<c:if test="${vc_tagname != null}">
					<li><a class="buttonActive" href="javascript:location.href='${ctx}/permit_getTagPermitList.do?vc_formid=${vc_formid}&vc_tagname=${vc_tagname}&type=${type}'"><span>返回</span></a></li>
				</c:if>
			</ul> 
		</div>
		</form>
    </body>
   
	<script type="text/javascript">
		$("table.list", document).cssTable();
		function checkForm(){
			document.getElementById('permitform').submit();
		}
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
