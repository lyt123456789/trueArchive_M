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
	 <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
	 <script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
	 <script type="text/javascript">

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
		
	 	//阅读协商消息
	 	function readConsult(){
	 		var id=$(".selected td:eq(1)").attr("itemid");
	 		 if(id){
	 			art.dialog({
					title: '阅读协商消息',
					lock: true,
				    content: '<'+'iframe id="consultInfoFrame" name="consultInfoFrame" src="${ctx}/table_readConsult.do?id='+id+'" height="300" width="500"></'+'iframe>',
				    id: 'EF893K',
				    button: [{name:'关闭',callback:function(){
				    	var conditionMsg = document.getElementById('message').value;
						location.href="${ctx }/table_getConsultList.do?message="+conditionMsg;
				    }}]	
				});
			 }else{
					alert('请选择要查看的消息！');
			 }
	 		
	 	}

		//回复协商消息
	 	function replyConsult(){
		 	var id = $(".selected td:eq(1)").attr("itemid");
		 	var userId = $(".selected td:eq(3)").attr("fromUserId");
		 	var userName = $(".selected td:eq(3)").attr("fromUserName");
		 	if(id){
		 		art.dialog({
					title: '回复协商消息',
					lock: false,
				    content: '<'+'iframe id="consultReplyFrame" name="consultReplyFrame" src="${ctx}/table_replyConsult.do?id='+id+'" height="300" width="500"></'+'iframe>',
				    id: 'EF893K',
				    button: [{name:'发送',callback:function(){
					    	var consultReplyFrame = document.getElementById("consultReplyFrame").contentWindow;
					    	var message = consultReplyFrame.document.getElementById("message").value;
					    	if(message==null || message==''){
								alert('请填写回复内容');
								return false;
					    	}
					    	$.ajax({   
								url : '${ctx }/table_sendReply.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_sendReply.do)');
								},
								data : {
									'msg':message,'relateId':id,'userId':userId,'userName':userName
								},     
								success : function(msg) {  
									alert('发送成功');
									var conditionMsg = document.getElementById('message').value;
									location.href="${ctx }/table_getConsultList.do?message="+conditionMsg;
								}
							});
							
					    }},{name:'取消'}]
				});
			 }else{
					alert('请选择要回复的消息！');
			 }
	 		
	 	}

	 	function deleteConsult(){
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
				alert('请选择一个删除对象');
				return;
			}
			if(!confirm("是否确认删除")){
				return;
			}
			location.href = "${ctx}/table_deleteConsult.do?ids="+ids;
	 	}
	 </script>
</head>		
<body>
 <div class="pageHeader">
	<form name="consultListF" id="consultListF" action="${ctx }/table_getConsultList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr> 
				<td>
					消息：<input type="text" name="message" />
				</td> 
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div> 
<div class="panelBar">
		<ul class="toolBar">
			<li><a href="javascript:readConsult();" class="auxiliary"><span>详细</span></a></li> 
			<li><a href="javascript:replyConsult();" class="edit"><span>回复</span></a></li>  
			<li><a class="delete" href="javascript:deleteConsult()" target="_self" title="确定要删除吗？" warn="请选择一个对象"><span>删除</span></a></li>
			<li class="line">line</li>
			
		</ul>
	</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getConsultList.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
			<th width="5%">
					    <input type="checkbox" name="selAll" id="selAll" onclick="sel();">
					</th>
				<th width="5%" align="center">序号</th>
				<th width="40%" align="center">消息内容</th>
				<th width="15%" align="center">发送人</th>
				<th width="15%" align="center">发送日期</th>
				<th width="10%" align="center">阅读状态</th>
				<th width="10%" align="center">回复状态</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${consultList}" varStatus="status"> 
			<tr >
			<td align="center">
			    	<input type="checkbox" name="selid" value="${item.id}" > 
			    </td>
				<td align="center" itemid="${item.id}">${(selectIndex-1)*pageSize +status.count }</td>
				<td align="left"><a href="javascript:readConsult();">${item.message }</a></td>
				<td align="center" fromUserId="${item.fromUserId }" fromUserName="${item.fromUserName }">${item.fromUserName }</td>
				<td align="center">${fn:substring(item.sendTime,0,16) }</td>
				<td align="center"><c:if test="${item.isRead=='true'}">已读</c:if><c:if test="${item.isRead=='false'}"><font color="red" >未读</font></c:if></td>
				<td align="center"><c:if test="${item.isReplied=='true'}">已回复</c:if><c:if test="${item.isReplied=='false'}"><font color="red" >未回复</font></c:if></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
 <div id="div_god_paging" class="cbo pl5 pr5"></div> 
</div> 
</body>
<%@ include file="/common/function.jsp"%> 
<script>
 
 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getConsultList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	} 
$("table.list", document).cssTable();

</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
		$('#pageContent').height($(window).height()-70);
</script>
</html>