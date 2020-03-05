<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<div class="wf-list-top">
			<form id="modelForm" action="${ctx}/docNumberManager_docNumberModelManage.do"  method="post" >
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" id="add" >
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-danger" id="delete"  title="确定要删除吗？" warn="请选择一条数据" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		        </div>
		        <label class="wf-form-label" for="">文号实例：</label>
		        <input type="text" class="wf-form-text" name="content" id="" value="${content}" placeholder="输入关键字"/>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</div>
			</form>
		</div>
		<form id="disform" method="POST" name="disform" action="${ctx}/docNumberManager_docNumberModelManage.do" >
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr>
							<th width="5%" style="font-weight:bold;text-align:center;"><input id='chc_all' type='checkbox' onclick="chooseAll();"/></th>
							<th width="30%" style="font-weight:bold;text-align:center;">文号实例</th>
							<th width="30%" style="font-weight:bold;text-align:center;">生成时间</th>
							<th width="10%" style="font-weight:bold;text-align:center;">排序号</th>
							<th width="10%" style="font-weight:bold;text-align:center;">操作</th>
						</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${docNumModelList}" var="docNumModel" varStatus="status">
							<tr > 
								<td style="text-align:center;"><input type="checkbox" name="chc" value="${docNumModel.modelid}"/></td>
								<td style="text-align:center;">
									<a href="javascript:getDetil('${docNumModel.modelid}');">${docNumModel.content}</a>
								</td>
								<td style="text-align:center;">
					             	<fmt:formatDate value="${docNumModel.time}" pattern="yyyy-MM-dd"/>
								</td>
								<td style="text-align:center;"><a href="javascript:;">${docNumModel.modelindex}</a></td>
								<td style="text-align:center;">
									<c:if test="${fn:indexOf(bindAlready, docNumModel.modelid) > -1  }">
										<button class="wf-btn-danger" type="button" onclick="disableDoc('${docNumModel.modelid}');">
							                <i class="wf-icon-stop"></i>  点击停用
							            </button>
									</c:if>
									<c:if test="${fn:indexOf(bindAlready, docNumModel.modelid) < 0 }">
										<button class="wf-btn-green" type="button" onclick="enableDoc('${docNumModel.modelid}');">
						                <i class="wf-icon-play"></i>  点击启用
						            </button>
									</c:if>
								</td>
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
<%@ include file="/common/widgets.jsp"%>
		<script>
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/docNumberManager_docNumberModelManage.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('modelForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		</script>
		<script type="text/javascript" src="${ctx}/widgets/common/js/god_Core.js" defer="defer"></script>
        <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
	    <script type="text/javascript">
	    	function getDetil(id){
	    		layer.open({
	    			title:'序号修改',
	    			type:2,
	    			area:['400px','150px'],
	    			content:"${ctx}/docNumberManager_toUpdateModel.do?modelid="+id,
	    			btn:["确定","取消"],
	    			yes: function(index, layero){
	    				var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	    				iframeWin.subform();
	    				layer.close(index);
	    				window.location.href="${ctx}/docNumberManager_docNumberModelManage.do";
					}
	    		});
	    	}
			$("#add").bind("click",function (){
				layer.open({
	    			title:'新增文号实例',
	    			type:2,
	    			area:['600px','300px'],
	    			content:"${ctx}/docNumberManager_toModelCreate.do",
	    			btn:["确定","取消","清空"],
	    			yes: function(index, layero){
	    				var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	    				iframeWin.createModel();
	    				layer.close(index);
	    				window.location.href="${ctx}/docNumberManager_docNumberModelManage.do";
					},
					btn2: function(index, layero){
	    			},
	    			btn3: function(index, layero){
	    				var iframeWin = window[layero.find('iframe')[0]['name']];
	    				iframeWin.clearItem();
						return false;
	    			}
	    		});
			});
			$("#delete").bind("click",function(){
				var chcs=document.getElementsByName('chc');
				var isHaveSelected=false;
				for(var i=0;i<chcs.length;i++){
					if(chcs[i].checked==true){
						isHaveSelected=true;
						break;
					}
				}
				if(!isHaveSelected){
					alert('请至少选择一个数据用于删除');
					return false;
				}
				if(!confirm("确认删除所选记录吗?")){
					return false;
				}

				var ids='';
				for(var i=0;i<chcs.length;i++){
					if(chcs[i].checked==true){
						ids+=chcs[i].value+',';
					}
				}
				if(ids!=''){
					ids=ids.substr(0,ids.length-1);
				}
				$.ajax({
					url : 'docNumberManager_deleteModels.do',
					type : 'POST',
					cache : false,
					async : false,
					data : {
						'ids' : ids
					},
					error : function() {
						alert('AJAX调用错误(docNumberManager_deleteModels.do)');
					},
					success : function(msg) {
						alert('删除成功');
					}
				});
				window.location.href="${ctx}/docNumberManager_docNumberModelManage.do";
			});

			function chooseAll(){
				var chc_all=document.getElementById('chc_all');
				var chcs=document.getElementsByName('chc');
				for(var i=0;i<chcs.length;i++){
					if(chc_all.checked==true){
						chcs[i].checked=true;
					}else{
						chcs[i].checked=false;
					}
				}
			}
			
			//回车监听
			document.onkeydown = keyDown;
			function keyDown(e){
				if((e ? e.which : event.keyCode)==13 ){
					$("#query").click();
				}
			}
			
			function enableDoc(id){
				$.ajax({
					url : '${ctx}/docNumberManager_startOrStopDoc.do',
					type : 'POST',
					cache : false,
					async : false,
					data : {
						'whmodelid' : id,
						'workFlowId' : '${workflowId}'
					},
					error : function() {
						alert('AJAX调用错误(${ctx}/docNumberManager_bindDocNumDoc.do)');
					},
					success : function(msg) {
						alert('启用成功');
						window.location.href="${ctx}/docNumberManager_docNumberModelManage.do";
					}
				});
			}
			
			function disableDoc(id){
				$.ajax({
					url : '${ctx}/docNumberManager_startOrStopDoc.do',
					type : 'POST',
					cache : false,
					async : false,
					data : {
						'whmodelid' : id,
						'workFlowId' : '${workflowId}'
					},
					error : function() {
						alert('AJAX调用错误(${ctx}/docNumberManager_bindDocNumDoc.do)');
					},
					success : function(msg) {
						alert('停用成功');
						window.location.href="${ctx}/docNumberManager_docNumberModelManage.do";
					}
				});
			}
		</script>
</html>
