<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
			<div class="wf-search-bar" style="height: 30px;">
				<div class="wf-top-tool">
		            <a class="wf-btn" href="javascript:location.href='${ctx}/template_toAddJsp.do?lcid=${lcid}'" external="true" rel="CreatingForms" target="_self">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		           <!--  <a class="wf-btn-green" onclick="javascript:refTable();" href="javascript:;">
		                <i class="wf-icon-check-circle"></i> 选择
		            </a> -->
		            <a class="wf-btn-primary" href="javascript:xg_row();">
		                <i class="wf-icon-pencil"></i> 修改
		            </a>
		            <a class="wf-btn-danger" href="javascript:del();" external="true" target="_self" rel="CreatingForms" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		        </div>
			</div>
		</div>
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/template_getTemplateList.do" >
		<input type="hidden"  value="" name="ids" id="ids"/>
		<input type="hidden" name="type" value="${type }"/>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr>
					    	<th width="5%">
					    		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
					    	</th>
				    		<th width="5%" align="center">
					    		序号
					    	</th>
					    	<th width="20%" align="center">
					    		模板中文名
					    	</th>
					    	<th width="20%" align="center">
					    		模板英文名
					    	</th>
					    	<th width="10%" align="center">
								创建时间
					    	</th>
					    	<th></th>
				    	</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${list}" var="d" varStatus="n">
					    	<tr>
					    		<td align="center">
					    			<input type="checkbox" name="selid" value="${d.id}">
					    		</td>
						    	<td align="center" templateid="${d.id}">
						    		${(selectIndex-1)*10+n.count}
						    	</td>
						    	<td>
						    		${d.vc_cname}
						    	</td>
						    	<td>
						    		${d.vc_ename}
						    	</td>
						    	<td align="center">
						    	<fmt:formatDate value="${d.c_createdate}"  pattern="yyyy-MM-dd"/>
						    	</td>
						    	<td></td>
					    	</tr>
					    </c:forEach>
					</tbody>
				</table>
			
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<%@ include file="/common/widgets.jsp"%>
 <script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/template_getTemplateList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};

	function xg_row(){
		 var id=$(".wf-actived td:eq(1)").attr("templateid");
		 if(id){
		 	location.href = "${ctx}/template_toEditJsp.do?id="+id;
		 }else{
			alert('请选择要修改的模板！');
	     }
	}
	
	function refTable(){
		var url ="${ctx}/template_toRefJsp.do?num="+Math.random();
		//用layer的模式打开
		layer.open({
			title:'选择其他流程模板',
			type:2,
			area:['350px', '200px'],
			btn:["确定","取消"],
			content: url,
			yes:function(index,layero){
			 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
	            iframeWin.ref();
			 	layer.close(index);
			 	window.location.href = "${ctx}/template_getTemplateList.do";
			}
		}); 
		/* 
		var ret=window.showModalDialog("${ctx}/template_toRefJsp.do?num="+Math.random(),null,"dialogWidth:350px;dialogHeight:150px;help:no;status:no");
		if(true==ret){
			window.location.href="${ctx}/template_getTemplateList.do";
		}else{
			
		} */
	}
	
	
		function sel(){
			var selAll = document.getElementById('selAll');
			var selid = document.getElementsByName('selid');
			for(var i = 0 ; i < selid.length; i++){
				if(selAll.checked){
					selid[i].checked = true;
				}else{
					selid[i].checked = false;
				}
			}
		}
	
		function del(){
			var selid = document.getElementsByName('selid');
			var ids = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					ids += selid[i].value + ",";
				}
			}
			if(ids.length > 0){
				ids=ids.substr(0,ids.length-1);
			}else {
				var id=$(".wf-actived td:eq(1)").attr("templateid");
				if(id!=null && id.length>0){
					ids = id;
				}else{
					alert('请选择一个删除对象！');
					return;
				}
				
			}
			if(!confirm('确定删除所选吗？'))return;
			location.href = "${ctx}/template_del.do?ids="+ids;
		}
	</script>
</html>
