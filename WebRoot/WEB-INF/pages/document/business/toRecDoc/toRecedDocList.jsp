<!DOCTYPE HTML>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			<form name="docList"  id="docList" action="${ctx }/toRecDoc_getToRecedDocList.do" method="post">
 	        	<input type="hidden" name="isAdmin"  value="${isAdmin}" />
 	        	<label class="wf-form-label" for="">标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
               	<label class="wf-form-label" for="">来文单位：</label>
	            <input type="text" class="wf-form-text" id="fileunit" name="fileunit" value="${fileunit}" placeholder="输入来文单位">
	            <label class="wf-form-label" for="">文件原号：</label>
	            <input type="text" class="wf-form-text" id="filenum" name="filenum" value="${filenum}" placeholder="输入文件原号">
	            <button class="wf-btn-primary" type="button"  onclick="search();">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/toRecDoc_getToRecedDocList.do" >
			<table class="wf-fixtable" layoutH="140" >
				<thead>
		    	  	<tr>
                        <th width="5%">序号</th>
						<th width="10%">文号</th>
						<th>标题</th>
						<th width="10%">发文单位</th>
						<th width="10%">发文时间</th>
						<th width="10%">公文类型</th>
						<th width="15%">事项类别</th>
						<th width="10%">收取</th>
                    </tr>
		    	</thead>
		    	<c:forEach var="item" items="${list}" varStatus="status"> 
			       	  <tr height="40px;">
						<td style="text-align:center;" >${(selectIndex-1)*pageSize+status.count}</td>
						<td style="text-align:center;">${item.wh}</td>
						<td style="text-align:center;">
							<a href="#" onclick="toViewToRecDocInfo('${item.id}');">${item.bt}</a>
						</td>
						<td style="text-align:center;">${item.fwdw}</td>
						<td style="text-align:center;">${item.sendTime}</td>
						<td style="text-align:center;">${item.type}</td>
						<td align= "center">
							<c:forEach items="${map}" var="map" varStatus="sta">
										<c:if test="${map.key == item.docguid}">
											<c:if test="${map.value.status == '0'}">
												<select name="itemLcId" id="itemLcId${item.docguid}">  
										<c:forEach items="${itemList}" var="list">
												<option value="${list.lcid},${list.id},${list.vc_sxlx}">${list.vc_sxmc}</option>
										</c:forEach>
						            </select>
											</c:if>
											<c:if test="${map.value.status == '1'}">
													${map.value.itemName}
											</c:if>
										</c:if>
						</c:forEach>
						</td>
						<td   style="text-align:center;">
								<c:forEach items="${map}" var="map" varStatus="sta">
										<c:if test="${map.key == item.docguid}">
										<c:choose>
											<c:when test="${map.value.status == '0'}">
												<button class="wf-btn-primary"  type="button"
													onclick="innerPending('${item.docguid}');"
													title="收入待办">
										                	收入待办
									            </button>
											</c:when>
											<c:when test="${map.value.status == '3'}">
						                		<font color="red">待办已删除</font>
											</c:when>
											<c:otherwise>
												<font color="blue">已进入待办</font>
											</c:otherwise>
										</c:choose>
										</c:if>
								</c:forEach>
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
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/toRecDoc_getToRecedDocList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('docList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		function checkTime(){
			var commitTimeFrom = document.getElementById("commitTimeFrom").value;
			var commitTimeTo = document.getElementById("commitTimeTo").value;
			if ((""!=commitTimeFrom&&""!=commitTimeTo)&&commitTimeFrom > commitTimeTo){
				alert("开始时间不能大于结束时间！");
				document.getElementById("commitTimeFrom").value="";
				document.getElementById("commitTimeTo").value="";
			}
		}
		
		function search(){
			document.forms.docList.action="${ctx}/toRecDoc_getToRecedDocList.do";
			document.forms.docList.submit();
		}
		
		//预览公文交换元素信息
		function toViewToRecDocInfo(id){
			var time = new Date();
			var url = "${ctx}/toRecDoc_toViewToRecDocInfo.do?id="+id+'&time='+time;
			parent.layer.open({
	            title :'详细信息',
	            content: url,
	            type: 2, 
	            area: ['1000px', '700px'],
	            btn: ['关闭']
			});
			//window.location.href = "${ctx}/toRecDoc_toViewToRecDocInfo.do?id="+id;
		}
		function innerPending(recId){
			var obj = document.getElementById("itemLcId"+recId);
			var sxName = obj.options[obj.selectedIndex].innerHTML;
			if(confirm("确定收取到 '"+sxName+"' 吗？")){
				var selValue = obj.options[obj.selectedIndex].value;
				var workFlowId = "";
				var itemId = "";
				var vc_sxlx = "";//事项类型，用于判断是否是传阅---打开待办为传阅
				if(selValue != null || selValue != ""){
					workFlowId = selValue.split(",")[0];
					itemId = selValue.split(",")[1];
					vc_sxlx = selValue.split(",")[2];
				}
				$.ajax({
					url:'${ctx}/toRecDoc_innerPending.do',
					type:'POST',
					cache:false,
					async:true,
					data : {
						'workFlowId':workFlowId,'itemId':itemId,'receiveType':'one','recId':recId
					}, 
					error : function() {
						alert('AJAX调用错误(toRecDoc_innerPending.do)');
					},
					success : function(msg) {
							if(msg=='no'){
								alert("选择的事项类型所属工作流节点为空,请创建后再收取！");
								return false;
							}else{
								if(msg.split(";")[0] == 'success'){
									alert("收入待办成功!");
									//不是传阅流程时，收取时生成pdf
									var msgs = msg.split(";");
									var formId = msgs[1];
									var instanceId = msgs[2];
									var processId = msgs[3];
									var title = msgs[4];
									$.ajax({
										url:'${ctx}/table_receiveToPdf.do',
										type:'POST',
										cache:false,
										async:true,
										data : {
											'workFlowId':workFlowId,'formId':formId,'processId':processId,'instanceId':instanceId
										}, 
										error : function() {
											alert('AJAX调用错误(table_receiveToPdf.do)');
										},
										success : function(msg) {
											openPendingForm(processId,itemId,formId,title,"");
										}
									});
								}else{
									alert("收取失败,请联系管理员!");
									return false;
								}
						}
					}
				});
			}
		}
		
		
		//打开待办
		function openPendingForm(processId,itemId, formId,process_title,imgPath){
			//1,断定目前处理人员；ajax
			$.ajax({   
				url : '${ctx}/table_checkIsDealIng.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('校验是否可以立即办理出错');
				},
				data : {
					'processId':processId
				},    
				success : function(result) { 
					if(result==''){
						openForm(processId,itemId,formId,process_title,imgPath);
					}else{
						alert(result+"正在办理之中！");
						return;
					}
				}
			});
		}
		
	  function openForm(processId,itemId,formId,process_title,imgPath){
		var url = '${curl}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&t='+new Date();
		openLayerTabs(processId,screen.width,screen.height,process_title,url,imgPath);
	 }
	 
  	function openLayerTabs(processId,width,height,title,url,imgPath){		
	   window.parent.topOpenLayerTabs(processId,1200,600,title,url,imgPath);
	}
	</script>
	<script type="text/javascript">
 $(document).ready(function(){
  $(".zlgx-con tr").mousemove(function(){
   $(this).css("background","#e8eff9");
  });
  
  $(".zlgx-con tr").mouseout(function(){
   $(this).css("background","none");
  }); 
 }); 
$("#chkall").click(
function(){
    if(this.checked){
        $("input[name='checkbox[]']").each(function(){this.checked=true;});
    }else{
        $("input[name='checkbox[]']").each(function(){this.checked=false;});
    }
});	
</script>
</html>