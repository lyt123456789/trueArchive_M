<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<style>
	.new-htable {
		margin-top:20px;
		width:96%;
		margin-left:3%;
	}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;
		}
		.new-htable .wf-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:133px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		.wf-btn-orange{
			padding: 6px 8px;
			font-size: 14px;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="deptForm" id="deptForm" action="${ctx }/siteManage_showAllDep.do?a=Math.random();" method="post" style="display:inline-block;">
			    <input id="isadmin" name="isadmin" type="hidden" value="${isadmin }"></input>
			    <input id="depId" name="depId" type="hidden" value="${depId }"></input>
			    <div class="wf-top-tool">
	               <%--  <c:if test="${isadmin eq '1' }"><button class="wf-btn-primary" type="button" onclick="add();"><i class="wf-icon-plus-circle" ></i> 添加</button></c:if> --%>
	               <c:if test="${isadmin eq '1' }"> <button class="wf-btn-danger" type="button" onclick="deleteDept();" title="确定要删除吗？"><i class="wf-icon-minus-circle" ></i> 删除</button></c:if>
				</div>
				<c:if test="${isadmin eq '1' }">
				    <label class="wf-form-label" for="" style="size: 16px" >站点选择：</label>
				    <select id="searchSiteName" name="searchSiteName">
				    	<c:forEach var="item" items="${siteList}" varStatus="status">
	    	 				<option id="${item.id }" value="${item.id } " <c:if test="${item.id eq siteId}">selected='selected'</c:if>>${item.name }</option>
	    	 			</c:forEach>
				    </select>
			  	</c:if>
				<label class="wf-form-label" for="" style="size: 16px;margin-left: 25px;" >部门名称：</label>
				<input id="name" name="name" type="text" value="${name}" placeholder="输入关键字查询" class="wf-form-text" style="width: 120px">
				
	            <button class="wf-btn-primary" type="submit" onclick="doSearch();"><i class="wf-icon-search"></i> 搜索</button>
	            <button class="wf-btn-primary" type="button" onclick="clean();"><i class="wf-icon-remove" ></i> 清空</button>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getPendingList.do" >
			<table class="wf-fixtable" layoutH="140" >
				<thead>
		    	  	<tr>
		    	  	<th width="5%"></th>
		    	  	
			    	<th width="15%">部门名称</th>
			    	
			    	<th width="15%">排序</th> 
			    	<th width="15%">操作</th>
                   </tr>
		    	</thead>
		    	<c:forEach var="item" items="${returnList}" varStatus="status">
						<c:if test="${item.issite eq '1' }">
							<tr onclick="   '${item.id }')" id="${item.id }" style="cursor: pointer;">
								<td align="center"><c:if test="${isadmin eq '1' }"><input type="checkbox" name="selid" id="${item.id }" value="${item.id }" onclick="checkDept('${item.id }','${isadmin }');" ></c:if></td>
								<td  onclick="selectRow('${item.id}')">${item.name }</td>
								
								<td align="center" onclick="selectRow('${item.id}')">${item.seq }</td>
								<td align="center">
								<c:if test="${isadmin eq '1' }">
								<button class="wf-btn-primary" type="button" onclick="add('${item.id}');"><i class="wf-icon-plus-circle" ></i> 添加子部门</button>
								</c:if>
								</td>
							</tr>
						</c:if>
						<c:if test="${item.issite ne '1' && item.fatherDep eq item.siteId }">
							<tr id="${item.id }" >
								<td align="center"><c:if test="${isadmin eq '1' }"><input type="checkbox" name="selid" id="${item.id }" value="${item.id }" onclick="checkDept('${item.id }','${isadmin }');" ></c:if></td>
								<td style="padding-left: 40px;" onclick="showDep('${item.id}','${isadmin }',1)">${item.name }</td>
								
								<td style="text-align: center;" onclick="showDep('${item.id}','${isadmin }',1)">${item.seq }</td>
								<td style="text-align: center;">
									<c:if test="${isadmin eq '1' }">
										<button class="wf-btn-orange" type="button" onclick="update('${item.id}');"><i class="wf-icon-pencil" ></i> 修改</button>
										<button class="wf-btn-primary" type="button" onclick="add('${item.id}');"><i class="wf-icon-plus-circle" ></i> 添加子部门</button>
										<%-- <button class="wf-btn-danger" type="button" onclick="del('${item.id}');"><i class="wf-icon-trash" ></i> 删除</button> --%>
		                			</c:if>
		                			<c:if test="${isadmin ne '1' }">
										<button class="wf-btn-primary" type="button" onclick="check('${item.id}');"><i class="wf-icon-search" ></i> 查看</button>
		                			</c:if>
								</td>
							</tr>
						</c:if>
					
				</c:forEach>
			</table>
		</form>
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
    function selectRow(id){
    	$("#depId").val(id);
    }
	function update(id){
		var deptId = $("#depId").val();
		if(id == '' || id == null){
			if(deptId == '' || deptId == null){
				alert("请选择需要修改的部门");
				return;
			}else{
				id = deptId;
			}
		}
		layer.open({
            title: '修改部门',
            area: ['500px', '300px'],
            type: 2,
           	// btn: ['确认', '取消'],
            content: '${ctx}/siteManage_toUpdateDep.do?depId='+id+'&isadmin=1&siteId=${siteId}'
        });
	}
		
	function check(id){
		layer.open({
            title: '查看部门',
            area: ['500px', '300px'],
            type: 2,
           	// btn: ['确认', '取消'],
            content: '${ctx}/siteManage_toUpdateDep.do?depId='+id
        });
	}
	
	function doSearch(){
		var sitename=$("#sitename").val();
		var username=$("#name").val();
		var myform = document.getElementById("deptForm");
  		//myform.action="${ctx}/siteManage_showAllDep.do?username="+username+"&siteId="+sitename;
  		myform.action="${ctx}/siteManage_showAllDep.do";
  		myform.submit();
  	}
	
	function add(depId){
		//var depId=$("#depId").val();
		if(depId != null && depId != ''){
			layer.open({
	            title: '添加部门',
	            area: ['500px', '300px'],
	            type: 2,
	           	// btn: ['确认', '取消'],
	            content: '${ctx}/siteManage_toAddDep.do?depId='+depId + "&siteId=${siteId}"
	        });
		}else{
			alert("请选择父级部门！");
		}
	}
		
	function clean(){
		$("#name").val("");
		$("#seq").val("");
	}
		
		
	function showDep(id,isadmin,level){
		var distance = 65 + (parseInt(level)-1)*30;
		var newLevel = parseInt(level)+1;
		//添加背景，记录表id
		$("tr").removeClass("wf-actived");
		var flag = $("tr").hasClass(id+"tr");
		var tr = $("#"+id);
		var colorFlag =  $("tr").hasClass("wf-actived");
		if(colorFlag){
			$("tr").removeClass("wf-actived");
		}else{
			$("#"+id).addClass("wf-actived");
		}
		$("#depId").val(id);
		if(flag){
			var trClass = $("."+id+"tr").remove();
		}else{
			var str = "";
			  $.ajax({  
			        type : "post",
			        url: "${ctx}/siteManage_getDepByPid.do?",  
			        dataType : "text",//数据类型为jsonp    
			        data : {"pid":id},
			        success : function(data){
			        	data = eval("("+data+")");
			        	var success = data.success;
			        	if(success=='success'){
			        		 json = data.list;
			        		 $.each(json,function(j,item){
			        			 str +="<tr id='"+item.id+"' class='"+id+"tr' > " +  "<td align='center'>";
			        			 if(isadmin=='1'){
			        				 str += "<input type='checkbox' name='selid' id=\""+item.id+"\" value=\""+item.id +"\" onclick='checkDept(\"" +item.id +"\");' >"   
			        			 }
			        			 str += "</td><td onclick='showDep(\""+item.id+"\",\""+isadmin+"\",\""+newLevel+"\")'><div style='margin-left:"+distance+"px;'>"+item.name+"</div></td><td style='text-align: center;' onclick='showDep(\""+item.id+"\",\""+isadmin+"\",\""+newLevel+"\")'>"+item.seq+"</td>";
			        			 if(isadmin=='1'){
			        				 str += "<td style='text-align: center;'><button class='wf-btn-orange' type='button' onclick='update(\""+item.id+"\");'><i class='wf-icon-pencil' ></i> 修改</button> "
			        				 + "<button class=\"wf-btn-primary\" type=\"button\" onclick=\'add(\""+item.id+"\");'><i class='wf-icon-plus-circle' ></i> 添加子部门</button>";
			        				 //+"<button class='wf-btn-danger' type='button' onclick='del(\""+item.id+"\" );'><i class='wf-icon-trash' ></i> 删除</button></td></tr>"
			        				 ;
			        			 }else{
			        				 str += "<td style='text-align: center;'><button class='wf-btn-primary' type='button' onclick='check(\""+item.id+"\");'><i class='wf-icon-search' ></i> 查看</button></td></tr>";
			        			 }
			        			 
			        		 });

			        		 tr.after(str);
			        	}
			        },  
			        error:function(data){  
			        	alert('调用错误');
			        }  
			    }); 
		}
	}
		
	function changeVal(id){
		$("#depId").val(id);
	}
	
	function del(id){
		//判断部门下是否包含人员
		$.ajax({  
	        type : "post",
	        url: "${ctx}/siteManage_judge.do?",  
	        dataType : "text",//数据类型为jsonp    
	        data : {"depId":id},
	        success : function(data){
	        	data = eval("("+data+")");
	        	var success = data.success;
	        	if(success=='success'){
	        		alert("请先删除该部门下的人员！");
	        	}else{
	        		var r = confirm("是否删除该部门");
	        		 if (r==true){
	        			 cutOff(id);
	        		 }
	        	}
	        },  
	        error:function(data){  
	        }  
	    }); 
	}
	
	function cutOff(id){
		$.ajax({  
	        type : "post",
	        url: "${ctx}/siteManage_judge.do?",  
	        dataType : "text",//数据类型为jsonp    
	        data : {"depId":id},
	        success : function(data){
	        	data = eval("("+data+")");
	        	var success = data.success;
	        	if(success=='success'){
	        		alert("删除成功！");
	        	}else{
	        		alert("删除失败！");
	        	}
	        	
	        },  
	        error:function(data){  
	        }  
	    }); 
	}
	
	function checkDept(deptId){
		$.ajax({  
			url : '${ctx}/siteManage_checkDept2.do',
			type : 'POST',   
			cache : false,
			data:{"deptId":deptId},
			async : false,
			error : function() {  
				alert('AJAX调用错误(siteManage_checkDept2.do)');
			},
			success : function(msg) {
				if(msg == 'success'){
				}else{
					alert("此部门下有子部门或人员,不能删除");
					$("[id="+deptId+"]").prop("checked", false);
				}
			}
		});
	}
	function deleteDept(){
		var selid = document.getElementsByName('selid');
		var deptIds = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				deptIds += selid[i].value.split(",") + ",";
			}
		}
		
		if(deptIds.length > 0){
			deptIds = deptIds.substring(0, deptIds.length - 1);
		}else {
			alert('请选择一个删除对象');
			return;
		}
		
		
		$.ajax({  
			url : '${ctx}/siteManage_deleteDept.do',
			type : 'POST',   
			cache : false,
			data:{"deptIds":deptIds},
			async : false,
			error : function() {  
				alert('AJAX调用错误(siteManage_deleteDept.do)');
			},
			success : function(msg) {
				if(msg =='success'){
					alert("删除部门成功！")
					window.location.href = '${ctx}/siteManage_showAllDep.do' ;
				}else{
					alert("删除部门失败！")
				}
			}
		});
	}
	
</script>
</html>