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
			<form name="doItemList"  id="doItemList" action="${ctx }/mobileTerminalInterface_listWF.do" method="post">
			<div class="wf-top-tool">
	            <a class="wf-btn" href="javascript:add_row();">
	                <i class="wf-icon-plus-circle"></i> 新建
	            </a>
	            <a class="wf-btn-primary" href="javascript:modify_row();">
	                <i class="wf-icon-pencil"></i> 修改
	            </a>
	            <a class="wf-btn-danger" href="javascript:delete_row();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-minus-circle"></i> 删除
	            </a>
	            <a class="wf-btn-orangered" onclick="javascript:copyToAdd();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-crosshairs"></i> 复制
	            </a>
	            <a class="wf-btn-orangered" onclick="javascript:exportZip();">
	                <i class="wf-icon-copy"></i> 导出
	            </a>
	            <a class="wf-btn-orangered" onclick="javascript:importZip();">
	                <i class="wf-icon-copy"></i> 导入
	            </a>
	        </div>
	            <label class="wf-form-label" for="">流程名称：</label>
	            <input type="text" class="wf-form-text" name="wfname" value="${wfname}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/mobileTerminalInterface_listWF.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
				   	 	<th width="30" ><input type="checkbox" value="1" name="c1" onclick="choose();"></th>
				    	<th width="50" align="center">序号</th>
						<th width="300" align="center">工作流名称</th>
						<th align="center" width="80" >状态</th>
						<th align="center" width="150" >更新日期</th>
						<th>  
							</th>
					</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
					<tr target="sid_user" rel="1">
						<td align="center"><input type="checkbox" value="${item.wfm_id}" name="c1"></td>
						<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
						<td class="workflowidtitle" workflownnameid="${item.wfm_id }">${item.wfm_name }</td>
						<td align="center">${item.wfm_status }</td>
						<td align="center">${item.wfm_modifytime }</td>
						<td>
							<a class="wf-btn-primary" href="#" onclick="editWfMain('${item.wfm_id}');">
	                			<i class="wf-icon-pencil"></i> 编辑流程
	           				 </a>
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
			skipUrl="<%=request.getContextPath()%>"+"/mobileTerminalInterface_listWF.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doItemList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
	$("body").delegate(".workflowidtitle","dblclick",function(){
		 var flag = checkIsInValidity();
		 if(!flag){
			alert("license已过期,请联系管理员！");
		 }else{
			 $("body",parent.document).find('#workflowname').html("<span>Folder</span>"+$(this).first().html());
			   $("body",parent.document).find('#workflowname').attr("workflowid",$(this).first().attr('workflownnameid'));
			   $("body",parent.document).find('#workflowimg').attr("href","${ctx}/mobileTerminalInterface_wrokFlowImgOpen.do?id="+$(this).first().attr('workflownnameid'));
			   $("body",parent.document).find('#tmcon').hide();
			   $("body",parent.document).find('#tmconfj').css("overflow","auto");
			   $.ajax({
				  url:"${ctx}/mobileTerminalInterface_setSessionId.do",
				  type:"post",
				  data:{session_id:$(this).first().attr('workflownnameid')}
			   });
			   alert("选择工作流'《"+$(this).first().text()+"》'成功！");	
		}
	 });
	
	
	//编辑流程整体的属性
	function editWfMain(id){
		var url ="${ctx}/mobileTerminalInterface_initWfMain.do?workflowId="+id;
		parent.layer.open({
			title:'编辑流程',
			type:2,
			area:[parent.window.screen.width+"px",(parent.window.screen.height-150)+"px"],
			content: url
		}); 
	}
	
	//设置全选
	 function choose(){
		 var obj = document.getElementsByName("c1")[0];
		 var objs=document.getElementsByName("c1");
		 if(obj.checked==true){
			 for(var i=1;i<objs.length;i++){
				 objs[i].checked=true;
			 }
		 }else{
			 for(var i=1;i<objs.length;i++){
				 objs[i].checked=false;
			 }
		 }
	 }
	 
	 //检查是否是在有效期时间之内
	 function checkIsInValidity(){
		 var validity = "${map['validity']}";
		 var date = new Date();
		 var time = date.getYear()+'-'+date.getMonth()+'-'+date.getDate();
		 return true;
		 if(validity>=time){
			 return true;
		 }else{
			 return false;
		 }
	 }
	 
	 //删除工作流
	 function delete_row(){
		var objs = document.getElementsByTagName("input");
		var ids="";
		for(var i=0; i<objs.length; i++) {
			if(objs[i].type.toLowerCase() == "checkbox" && objs[i].checked == true ){
				ids+=objs[i].value+",";
			}
		}
		var id=$(".wf-actived td:eq(2)").attr("workflownnameid");
		if(ids.length==0){
			ids = id;
		}
		if(ids.length == 0){
			  alert("请选择删除项！");
			  return;
		}else{
			if (window.confirm("你确定要删除?")){
				$.ajax({
					url:"${ctx}/mobileTerminalInterface_deleteWorkFlow.do",
					type:"post",
					data:{"id":ids},
					success:function(result){
						alert(result);
						window.location.href="${ctx}/mobileTerminalInterface_listWF.do";
					},
					async : false
				});
			}
		}
	}
	 
	 //添加工作流
	function add_row(){
		var flag = checkIsInValidity();
		if(!flag){
			alert("license已过期,请联系管理员！");
			return;
		}
		layer.open({
             title :'新建工作流',
             type: 2,
             content: '${ctx}/mobileTerminalInterface_addWorkFlow.do',
             area: ['350px', '130px'],
             end:function(){
				window.location.reload();
            }
         }); 
	}
	 
	 
	//复制且新增信的流程
	function  copyToAdd(){
		 var id=$(".wf-actived td:eq(2)").attr("workflownnameid");
		 var workflowname=$(".wf-actived td:eq(2)").text();
		 if(typeof(id)!="undefined"){
			 layer.open({
		            title :'复制工作流程',
		            type: 2,
		            content: "${ctx}/mobileTerminalInterface_toCopyWorkFlow.do?id="+id+"&workflowname="+encodeURI(encodeURI(workflowname)),
		            area: ['350px', '130px'],
		            end:function(){
						window.location.href="${ctx}/mobileTerminalInterface_listWF.do";
		            }
		        });
		 }else if("undefined"==typeof(id)){
			  alert("请选择需要复制的流程...");
		 }
	 }
	 
	 //修改工作流
	 function modify_row(){
		 var id=$(".wf-actived td:eq(2)").attr("workflownnameid");
		 var workflowname=$(".wf-actived td:eq(2)").text();
		 if(typeof(id)!="undefined"){
			 var flag = checkIsInValidity();
			if(!flag){
				alert("license已过期,请联系管理员！");
				return;
			}
			layer.open({
	            title :'修改工作流',
	            type: 2,
	            content: "${ctx}/mobileTerminalInterface_modifyWorkFlow.do?id="+id+"&workflowname="+encodeURI(encodeURI(workflowname)),
	            area: ['350px', '130px'],
	            end:function(){
	            	window.location.href="${ctx}/mobileTerminalInterface_listWF.do";
	            }
	        });
		 }else if("undefined"==typeof(id)){
				alert("请选择修改对象...");
		}
	 }

	//导出流程 
	 function exportZip(){
		 var id=$(".wf-actived td:eq(2)").attr("workflownnameid");
		 var path = '${ctx}';
		 if(typeof(id)!="undefined"){
				$.ajax({
					 async:true,//是否异步
				     type:"POST",//请求类型post\get
				     cache:false,//是否使用缓存
			         dataType:"text",//返回值类型
				     data:{"id":id},
				     url:"${ctx}/mobileTerminalInterface_exportWorkFlow.do",
				     success:function(url){
				    	 window.location.href=path+"/"+url;
				     }
					
				});
		 }else if("undefined"==typeof(id)){
				alert("请选择修改对象...");
				return ;
		}
	 }

	 //导入流程
	 function importZip(){
		layer.open({
            title :'导入',
            content: '${ctx}/mobileTerminalInterface_toUploadZip.do',
            type: 2,
		    area: ['650px','300px'],
			maxmin: true,
			scrollbar: false,
			end:function(){
            	window.location.reload();
			}
        }); 
		//if("success"==ret){
		//	window.location.href="${ctx}/mobileTerminalInterface_listWF.do";
		//}else{
		//}
	 }
</script>
</html>