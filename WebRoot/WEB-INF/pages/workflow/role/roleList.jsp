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
</head>

<body>
	<div class="wf-layout">
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" href="${ctx}/employeeRole_toadd.do" target="_self">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-primary" href="javascript:toupdate();">
		                <i class="wf-icon-pencil"></i> 修改
		            </a>
		            <a class="wf-btn-danger" href="javascript:del();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		        </div>
			</div>
		</div>
		<div class="wf-list-wrap">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/group_getInnerUserList.do" >
				<input type="hidden"  value="" name="ids" id="ids"/>
				<input type="hidden" name="type" value="${type }"/>
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	<tr>
						<th width="50">
								<input type="checkbox" name="selid" value="${bean.id}" >
						</th>
						<th width="250">角色名</th>
						<th width="80">排序号</th>
						<th width="100">工作流权限</th>
						<th class="tdrs"></th>
					</tr>
			    	</thead>
			    	<tbody>
						<c:forEach var="bean" items="${list}">
							<tr>
								<td align="center"><input type="checkbox" name="selid" value="${bean.id}"/></td>
								<td align="center" beanid="${bean.id}" >${bean.roleName}</td>
								<td align="center">${bean.zindex}</td>
								<td align="center">
									<a href="#" onclick="showLcs('${bean.id}');">选择流程</a>
								</td>
								<td class="tdrs"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
	</div>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/group_getInnerUserList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	

	//复选框批选取
	function chcChecked(parentCheckboxId,checkboxesName){
		var p=g.g(parentCheckboxId);
		var arr=g.gbn(checkboxesName);
		if(p&&arr){
			for(var i=0;i<arr.length;i++){
				arr[i].checked=p.checked;
			};
		}
	};
	
	function del(){
		if(confirm("确定要删除吗？")){
			var selid = document.getElementsByName('selid');
			var ids = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					ids += selid[i].value + ",";
				}
			}
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
			}else {
				var id=$(".wf-actived td:eq(1)").attr("beanid");
				if(id!=null && id.length>0){
					ids = id;
				}else{
					alert('请选择一个删除对象');
					return;
				}
				
			}
			$.ajax({
				url : '${ctx}/employeeRole_deleteEmployeeRole.do?ids='+ids,
				type : 'POST',  
				cache : false,
				async : false,
				error : function() {
				alert('AJAX调用错误(item_del.do)');
					return false;
				},
				success : function(msg) {   
					if(msg =='success'){
						alert("删除成功！");
						window.location.href = '${ctx}/employeeRole_getRoleList.do' ;
					}
				}
			});
		}
	}
	
	function toupdate(){
		 var id=$(".wf-actived td:eq(1)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/employeeRole_toupdate.do?id="+id;
		 }else{
			alert('请选择要修改的组！');
	     }
		
		
	}
	
	//批删除提示
	function deleteSelected(checkboxesName){
		var chcs=g.gbn(checkboxesName);
		var ids='';
		for(var i=0;i<chcs.length;i++){
			if(chcs[i].checked==true){
				ids+=chcs[i].value+',';
			}
		}
		if(ids==''){
			alert('请至少选择一条记录删除!');
			return;
		}
		ids=ids.substr(0,ids.length-1);
		if(!confirm("确认删除所选记录吗?")){
			return;
		}
		g.g('ids').value=ids;
		g.g('thisForm').action='${ctx}/group_deleteInnerUser.do';
		g.g('thisForm').submit();
	};
	
	//打开流程选择页面
	function showLcs(roleId){
		var retVal = window.showModalDialog("${ctx}/mobileTerminalInterface_getAllWfMainList.do?roleId="+roleId+'&a=' + Math.random(),null,"dialogWidth:600px;dialogHeight:400px;help:no;status:no;scroll:no");
		if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
	         
	    }else{
	    	 window.location.href="${ctx}/employeeRole_getRoleList.do"; 
		}
	}
	</script>
</body>
	<%@ include file="/common/function.jsp"%>
</html>
