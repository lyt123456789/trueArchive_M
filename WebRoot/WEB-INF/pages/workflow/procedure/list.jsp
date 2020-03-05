<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
</head>

<body>
	<div class="wf-layout">
		<div class="wf-list-top" style="height:62px;">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" href="${ctx}/procedure_toAdd.do"  target="_self">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-primary" href="javascript:xg_row();">
		                <i class="wf-icon-pencil"></i> 修改
		            </a>
		            <a class="wf-btn-danger" href="javascript:deleteSelected('chcs');" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		            <a class="wf-btn-orange"  href="javascript:ck_row();" >
		                <i class="wf-icon-info-circle"></i> 详细
		            </a>
		            <a class="wf-btn-blue" href="javascript:sd_row();"  >
		                <i class="wf-icon-crosshairs"></i> 设置
		            </a>
		        </div>
			</div>
		</div>
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/group_getInnerUserList.do" >
		<input type="hidden"  value="" name="ids" id="ids"/>
		<input type="hidden" name="type" value="${type }"/>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr>
							<th width="50">
								<input type="checkbox" id="pchc" onclick="chcChecked(this.id,'chcs')"/>
							</th>
							<th width="80">排序号</th>
							<th width="250">
								存储过程英文名称
							</th>
							<th width="250">
								存储过程英文名称
							</th>
							<th class="tdrs"></th>
						</tr>
			    	</thead>
			    	<tbody>
						<c:forEach var="bean" items="${list}" varStatus="i">
							<tr>
								<td align="center"><input type="checkbox" name="chcs" value="${bean.id }"/></td>
								<td align="center">${i.index+1 }</td>
								<td align="center" beanid="${bean.id}" style="text-align: left;"> 
									${bean.pname }
								</td>
								<td align="center"  style="text-align: left;"> 
									${bean.pcname }
								</td>
								<td class="tdrs"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/procedure_getList.do";			//提交的url,必须修改!!!*******************
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

	function xg_row(){
		 var id=$(".wf-actived td:eq(2)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/procedure_toUpdate.do?id="+id+"&type=${type }";
		 }else{
			alert('请选择要修改的组！');
	     }
	}

	function ck_row(){
		 var id=$(".wf-actived td:eq(2)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/procedure_view.do?id="+id+"&type=${type }";
		 }else{
			alert('请选择要查看的组！');
	     }
	}

	function sd_row(){
		 var id=$(".wf-actived td:eq(2)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/group_toMapEmployeeJsp.do?id="+id+"&type=${type }";
		 }else{
			alert('请选择要设定的组！');
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
		if(ids.length > 0){
			ids=ids.substr(0,ids.length-1);
		}else {
			var id=$(".wf-actived td:eq(2)").attr("beanid");
			if(id!=null && id.length>0){
				ids = id;
			}else{
				alert('请至少选择一条记录删除!');
				return;
			}
			
		}
		if(!confirm("确认删除所选记录吗?")){
			return;
		}
		g.g('ids').value=ids;
		g.g('thisForm').action='${ctx}/procedure_delete.do';
		g.g('thisForm').submit();
	};
	</script>
</body>
</html>
