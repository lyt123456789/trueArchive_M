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
<div class="wf-super-search" style="display:none" id="hideDiv">
	<form id="commentform" method="post" action="" class="wf-form wf-form-horizontal-lg">
		<input type="hidden" name="personalComment.cmnt_id" id="cmnt_id">
		<input type="hidden" name="personalComment.user_id" id="user_id">
		<div class="wf-form-item">
			<label class="wf-form-label" for="" style="width:100px"><em>*</em>意见内容：</label>
			<div class="wf-form-field" style="margin-left: 110px">
				<input type="text" class="wf-form-text" id="content" name="personalComment.content">
			</div>
		</div>
		<div class="wf-form-item">
			<label class="wf-form-label" for="" style="width:100px"><em>*</em>序号：</label>
			<div class="wf-form-field" style="margin-left: 110px">
				<input type="text" class="wf-form-text" id="sort_index" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  name="personalComment.sort_index">
			</div>
		</div>
	</form>
</div>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="doItemList"  id="doItemList" action="${ctx}/comment_getCommentList.do" method="post">
			<input type="hidden" name="no" id="no" value="${no}">
			<input type="hidden" name="ids" id="ids" value="${ids}">
			<div class="wf-top-tool">
	            <a class="wf-btn" href="javascript:add()" target="_self">
	                <i class="wf-icon-plus-circle"></i> 新建
	            </a>
	            <a class="wf-btn-primary" href="javascript:update();">
	                <i class="wf-icon-pencil"></i> 修改
	            </a>
	            <a class="wf-btn-danger" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-minus-circle"></i> 删除
	            </a>
	        </div>
	            <label class="wf-form-label" for="">内容：</label>
	            <input type="text" class="wf-form-text" name="content" value="${content}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/comment_getCommentList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
		    		<th width="5%">序号</th>
			    	<th width="35%">内容</th>
			    	<th width="5%">排序号</th>
			    	<th></th>
		    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="comm" varStatus="n">
			    	<tr target="sid_user" rel="1">
			    		<td align="center" >
			    			<input type="checkbox" name="selid" value="${comm.cmnt_id}" onclick="changeCheck();" >
			    		</td>
				    	<td align="center" cmnt_id="${comm.cmnt_id}">
				    		${(selectIndex-1)*pageSize+n.count}
				    	</td>
				    	<td>${comm.content}</td>
				    	<td align="center">${comm.sort_index}</td>
				    	<td></td>
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
			skipUrl="<%=request.getContextPath()%>"+"/comment_getCommentList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		function update(){
			var id=$(".wf-actived td:eq(1)").attr("cmnt_id");
			if(id){
				$.ajax({   
					url : '${ctx}/comment_toupdate.do?cmnt_id='+id,
					type : 'POST',   
					cache : false,
					global:false,
					error : function() {
					},
					success : function(result) {
						var ob = eval("("+result+")");
						layer.open({
							title:'修改',
							type:1,
							area:['370px','182px'],
							content:$("#hideDiv"),
							btn:["确定","取消"],
							success:function(){
								$("#content").val(ob.content);
								$("#sort_index").val(ob.sort_index);
								$("#cmnt_id").val(ob.cmnt_id);
								$("#user_id").val(ob.user_id);
							},
							yes:function(){
								/*if($("#content").val()==""){
									alert("意见内容不能为空");
									return;
								}
								$("#commentform").attr("action","${ctx}/comment_updatePersonalComment.do");
								$("#commentform").submit();*/
								
								var content = $("#content").val();
								var sort_index= $("#sort_index").val();
								if(content.trim()==""){
									alert("意见内容不能为空");
									return;
								}
								if(sort_index.trim()==""){
									alert("排序号不能为空");
									return;
								}
								$.ajax({
									type:'post',
									url:'${ctx}/comment_commentIsIn.do',
									data:{
										"content":content,
										"sortIndex":sort_index
									},
									success:function(data){
										if(data=='yes'){
											alert("该意见已存在！");
										}else{
											$("#commentform").attr("action","${ctx}/comment_updatePersonalComment.do");
											$("#commentform").submit();
										}
									},
									error:function(){
										alert("AJAX调用错误(comment_commentIsIn.do)");
									}
								});
							}
						});
					}
				});
			}else{
				alert("请选择需要修改的项");
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
					ids = ids.substring(0, ids.length - 1);
				}else {
					var id=$(".wf-actived td:eq(1)").attr("cmnt_id");
					if(id!=null && id.length>0){
						ids = id;
					}else{
						alert('请选择一个删除对象');
						return;
					}
					
				}
				if(confirm("确定删除该意见常用语吗？")){
				 	location.href = "${ctx}/comment_delete.do?cmnt_id="+ids;
				}
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
		
		function add(){
			$("#content").val("");
			$("#sort_index").val("");
			$("#cmnt_id").remove();
			$("#user_id").remove();
			layer.open({
				title:'新增',
				type:1,
				area:['370px','182px'],
				content:$("#hideDiv"),
				btn:["确定","取消"],
				yes:function(){
					addComment();
					
				},
				cancel:function(){
					var content = $("#content").val();
					var sort_index= $("#sort_index").val();
					if(content.trim()==""){
						return;
					}
					if(sort_index.trim()==""){
						return;
					}
					if(confirm("是否需要保存？")){
						addComment();
					}
				}
			});
		}
		function addComment(){
			var content = $("#content").val().trim();
			var sort_index= $("#sort_index").val();
			if(content.trim()==""){
				alert("意见内容不能为空");
				return;
			}
			if(sort_index.trim()==""){
				alert("排序号不能为空");
				return;
			}
			$.ajax({
				type:'post',
				url:'${ctx}/comment_commentIsIn.do',
				data:{
					"content":content,
					"sortIndex":sort_index
				},
				success:function(data){
					if(data=='yes'){
						alert("该意见已存在！");
					}else{
						$("#commentform").attr("action","${ctx}/comment_addPersonalComment.do");
						$("#commentform").submit();
						return 1;
					}
				},
				error:function(){
					alert("AJAX调用错误(comment_commentIsIn.do)");
				}
			});
		}
		function changeCheck(){
			var checkboxes = document.getElementsByName("selid");
			var selAllObj = document.getElementById("selAll");
			for(var i=0;i<checkboxes.length;i++){
				if(!checkboxes[i].checked){
					selAllObj.checked=false;
					return;
				}
			}
			selAllObj.checked=true;
		}
	</script>
</html>