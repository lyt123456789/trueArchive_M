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
<script type="text/javascript">
		var url=null;
		var vc_roletype=null;
		var vc_role=null;
</script>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<div class="wf-top-tool">
	            <a class="wf-btn" href="javascript:add();" target="_self">
	                <i class="wf-icon-plus-circle"></i> 添加
	            </a>
	        </div>
		</div>
	</div>
	<div class="wf-list-wrap">
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" > 
		<input type="hidden" name="vc_roletype" id="vc_roletype"/>
		<input type="hidden" name="vc_role" id="vc_rolename"/>
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="5%">序号</th>
						<th width="10%">角色类型</th>
						<th width="10%">角色名称</th>
						<th width="10%">操作</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="d" varStatus="n">
			    		<c:if test="${n.index==0}">
			    			<script type="text/javascript">
			    				url='${ctx}/permition_permitRight.do?nodeid=${nodeid}&formid=${formid}&vc_tagname=${vc_tagname}&vc_roletype=${d.vc_roletype}&vc_rolename='+window.encodeURI('${d.vc_rolename}')+'&type=${type}';
			    				vc_roletype='${d.vc_roletype}';
			    				vc_role='${d.vc_roleid},${d.vc_rolename}';
			    			</script>
			    		</c:if>
				    	<tr height="30px">
					    	<td align="center">
					    		${n.count}
					    	</td>
					    	<td >
					    		<c:if test="${d.vc_roletype == '1'}">
					    			内置用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '2'}">
					    			全局用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '3'}">
					    			平台用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '4'}">
					    			流程用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '5'}">
					    			动态角色
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '6'}">
					    			部门
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '7'}">
					    			用户
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '8'}">
					    			部门级联
					    		</c:if>
					    	</td>
					    	<td>
					    		${d.vc_rolename}
					    	</td>
					    	<td align="center">
								<a href="javascript:toUpdate('${ctx}/permition_permitRight.do?nodeid=${nodeid}&formid=${formid}&vc_tagname=${vc_tagname}&vc_roletype=${d.vc_roletype}&vc_rolename='+window.encodeURI('${d.vc_rolename}')+'&type=${type}','${d.vc_roletype}','${d.vc_roleid},${d.vc_rolename}');" class="uededit">修改</a>
								<a href="javascript:del('${ctx}/permition_deletePermit.do?nodeid=${nodeid}&formid=${formid}&vc_tagname=${vc_tagname}&vc_roletype=${d.vc_roletype}&vc_roleid=${d.vc_roleid}&type=${type}');" class="ueddelete">删除</a>
							</td>
				    	</tr>
				    </c:forEach>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
	function del(url){
		if(!confirm('确定删除吗？'))return;
		g.g('thisForm').action=url;
		g.g('thisForm').submit();
		//window.parent.location.reload(); 
	};
	function add(){
		//提交right页
		window.parent.document.getElementById('iframe_right').src='${ctx }/permition_permitRight.do?lcid=${lcid}&formid=${formid }&nodeid=${nodeid}&type=${type }';
		/* g.g('thisForm').action='${ctx }/permition_permitMiddle.do?formid=${formid }&nodeid=${nodeid}&type=${type }';
		g.g('thisForm').submit(); */
		window.location.href="${ctx }/permition_permitMiddle.do?lcid=${lcid}&formid=${formid }&nodeid=${nodeid}&type=${type }"
		
	};
	function toUpdate(url,vc_roletype,vc_role){ 
		g.g('vc_roletype').value=vc_roletype;
		g.g('vc_rolename').value=vc_role;
		window.parent.document.getElementById('iframe_right').src=url;
	};
	window.onload=function(){
		if(url&&vc_roletype&&vc_role){
			toUpdate(url,vc_roletype,vc_role);
		};
	};
	</script>
</html>
