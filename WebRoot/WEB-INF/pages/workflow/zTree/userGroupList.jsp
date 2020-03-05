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
    <div class="bd" style="display: none;padding:10px" id="newGroupNameDiv">
	    <div class="tw-usertree-search">
		    名称：<input type="text" class="tw-form-text" id="newGroupName">
		</div>
	</div>
    <div class="wf-layout">
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" onclick="addCommonGroup();">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-danger" onclick="batchDeleteGroup();">
						<i class="wf-icon-minus-circle"></i> 批量删除
					</a>
		        </div>
			</div>
		</div>
		<div class="wf-list-wrap">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/ztree_toUserGroupList.do" >
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr >
				    		<th width="5%">
					    		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
					    	</th>
				    		<th width="5%">
					    		序号
					    	</th>
					    	<th width="20%">
					    		常用组名称
					    	</th>
					    	<th width="20%">创建时间</th>
					    	<th></th>
				    	</tr>
			    	</thead>
			    	<c:forEach items="${cgs}" var="d" varStatus="n">
				    	<tr target="sid_user" rel="1">
					    	<td align="center">
					    		<input type="checkbox" name="selid" value="${d.id}">
					    	</td>
					    	<td align="center" date_id="${d.id}">
					    		${(selectIndex-1)*pageSize+n.count}
					    	</td>
					    	<td title="${d.name}">
					    		${d.name}
					    	</td>
					    	<td align="center">
					    		<fmt:formatDate value="${d.createTime}" dateStyle="full" pattern="yyyy年MM月dd日 hh:mm"/>
					    	</td>
					    	<td>
    				            <a class="wf-btn-primary" onclick="selectPerson('${d.id}');">
					                <i class="wf-icon-pencil"></i> 修改
					            </a>
					    		<a class="wf-btn-danger" onclick="deleteGroup('${d.id}',this);">
					                <i class="wf-icon-minus-circle"></i> 删除
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
			skipUrl="<%=request.getContextPath()%>"+"/ztree_toUserGroupList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	
	</script>
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

		function modify_row(){
			 var id=$(".wf-actived  td:eq(1)").attr("id");
			 if(id){
			 	location.href = "${ctx}/dictionary_toEditJsp.do?id=" + id;
			 }else{
				alert('请选择要修改的字典表！');
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
				var id=$(".wf-actived td:eq(1)").attr("dictionaryid");
				if(id!=null && id.length>0){
					ids = id;
				}else{
					alert('请选择一个删除对象');
					return;
				}
				
			}
			if(!confirm('确定删除所选吗？'))return;
			location.href = "${ctx}/dictionary_del.do?ids="+ids;
		}
		
		function addCommonGroup(){
			layer.open({
				title:"新增常用组",
				type:1,
				area:['250px','140px'],
				content:$('#newGroupNameDiv'),
				btn:['确认','取消'],
				yes:function(index,layero){
					var name = $.trim(layero.find('#newGroupName').val());
					if(name!=''){
						$.ajax({
							async:true,//是否异步
							type:"POST",//请求类型post\get
							cache:false,//是否使用缓存
							dataType:"text",//返回值类型
							data:{"name":name},
							url:"${ctx}/ztree_addUserGroup.do",
							success:function(text){
								if(text!==''){
									var id = text;
									layer.close(index);
									layer.msg("新增成功",{time:1000,icon:1},function(){
										//window.location.reload();
										selectPerson(id);
									});
								}else{
									layer.msg("请输入名称",{time:1000,icon:0});
								}
							}
						});
					}else{
						layer.msg("名称只能输入非空字符",{time:1000,icon:0});
					}
				}
			});
		}
		
		function deleteGroup(gid,obj){
			layer.confirm("确定要删除吗？",function(){
				$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"gid":gid},
			        url:"${ctx}/ztree_deleteUserGroup.do",
			        success:function(text){
			        	layer.msg("删除成功",{time:1000,icon:1},function(){
							//window.location.reload();
							$(obj).parents("tr").remove();
			        	});
			        }
			    });
			});
		}
		
		function batchDeleteGroup(){
			layer.confirm("确定要删除吗？",function(){
				var objArray=new Array()
				var selid = document.getElementsByName('selid');
				var ids = "";
				var j=0;
				for(var i = 0 ; i < selid.length; i++){
					if(selid[i].checked){
						ids += selid[i].value + ",";
						objArray[j]=selid[i];
						j++;
					}
				}
				if(ids.length > 0){
					ids = ids.substring(0, ids.length - 1);
				}else {
					var id=$(".wf-actived td:eq(1)").attr("date_id");
					if(id!=null && id.length>0){
						ids = id;
						objArray[0]=$(".wf-actived td:eq(1)");
					}else{
						alert('请选择一个删除对象');
						return;
					}
					
				}
				
				$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"gid":ids},
			        url:"${ctx}/ztree_deleteUserGroup.do",
			        success:function(text){
			        	layer.msg("删除成功",{time:1000,icon:1},function(){
							//window.location.reload();
							for (var int = 0; int < objArray.length; int++) {
								$(objArray[int]).parents("tr").remove();
							}
			        	});
			        }
			    });
			}); 
		}
		
		function selectPerson(gid){
			layer.open({
				title:"人员选择",
				type:2,
				area:['800px','500px'],
				content:"${ctx}/ztree_toSetUserGroup.do?showgroup=1&gid="+gid,
				yes:function(index,layero){
				},
				end:function(){
					window.location.reload();
				}
			});
		}
	</script>
	
</html>