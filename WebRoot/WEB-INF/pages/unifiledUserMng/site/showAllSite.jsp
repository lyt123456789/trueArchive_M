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
			<form name="pendlist"  id="pendlist" action="${ctx }/siteManage_showAllSite.do?a=Math.random();" method="post" style="display:inline-block;">
	 	        <input type="hidden" name="isPortal" value="${isPortal}"/>
			    <div class="wf-top-tool">
	                <button class="wf-btn-primary" type="button" onclick="add();"><i class="wf-icon-plus-circle" ></i> 添加</button>
	                <button class="wf-btn-danger" type="button" onclick="deleteSite();"><i class="wf-icon-minus-circle" ></i> 删除</button>
				</div>
                <label class="wf-form-label" for="" style="size: 16px" >站点：</label>
				<input id="sitename" name="sitename" type="text" value="${sitename}" placeholder="站点" class="wf-form-text">
				<%-- <label class="wf-form-label" for="" style="size: 16px;margin-left: 25px;" >姓名：</label>
				<input id="username" name="username" type="text" value="${username}" placeholder="姓名" class="wf-form-text"> --%>
                
	            <button class="wf-btn-primary" type="submit" onclick="doSearch();"><i class="wf-icon-search"></i> 搜索</button>
	            <button class="wf-btn-primary" type="button" onclick="clean();"><i class="wf-icon-remove" ></i> 清空</button>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getPendingList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
		    	  		<th width="5%"></th>
		    	  		<th width="10%" style="size: 16px">序号</th>
				    	<th width="" style="size: 16px">站点名称</th>
				    	<!-- <th width="15%" style="size: 16px">站点管理员</th>
				    	<th width="15%" style="size: 16px">部门管理员</th> 
				    	<th width="15%" style="size: 16px">人员管理员</th>
				    	<th width="15%" style="size: 16px">角色管理员</th> -->
				    	<th width="15%" style="size: 16px">操作</th>
                    </tr>
		    	</thead>
		    	<c:forEach var="item" items="${list}" varStatus="status"> 
		    	
			    	<tr class="tm-open" id="${item.id}">
			    	<td align="center"><input type="checkbox" name="selid" id="${item.id }" value="${item.id }" onclick="checkDept('${item.id }');" ></td>
			    	 	<td align="center">${status.count}</td>
			    	 	<td align="center">${item.name}</td>
			    	 	<%-- <td align="center">${item.siteAdminName } </td>
			    	 	<td align="center">${item.depAdminName}</td>
			    	 	<td align="center">${item.empAdminName}</td>
			    	 	<td align="center">${item.roleAdminName}</td> --%>
			    	    <td align="center"><button class="wf-btn-orange" type="button" onclick="update('${item.id}');"><i class="wf-icon-pencil" ></i> 修改</button></td>
			    	 </tr>
			    </c:forEach>
			</table>
		</form>
	</div>
</div>
</body>
	<%@ include file="/common/widgets.jsp"%>
	<script>
	(function ($) {
	 $.extend({
	  Request: function (m) {
	   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
	   return sValue ? sValue[1] : sValue;
	  },
	  UrlUpdateParams: function (url, name, value) {
	   var r = url;
	   if (r != null && r != 'undefined' && r != "") {
	    value = encodeURIComponent(value);
	    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
	    var tmp = name + "=" + value;
	    if (url.match(reg) != null) {
	     r = url.replace(eval(reg), tmp);
	    }
	    else {
	     if (url.match("[\?]")) {
	      r = url + "&" + tmp;
	     } else {
	      r = url + "?" + tmp;
	     }
	    }
	   }
	   return r;
	  }
	 
	 });
	})(jQuery);
	</script>
	
	<script type="text/javascript">
		function doSearch(){
			/* var sitename=$("#sitename").val();
			var username=$("#username").val(); */
			var myform = document.getElementById("pendlist");
	  		//myform.action="${ctx}/siteManage_showAllSite.do?username="+username+"&sitename="+sitename;
	  		myform.submit();
	  	}
	
		function add(){
			top.layer.open({
	            title: '添加站点',
	            area: ['500px', '260px'],
	            type: 2,
	           	// btn: ['确认', '取消'],
	            content: '${ctx}/siteManage_toAddSite.do',
	            end:function(){
	            	window.location.reload();
	            /* 	top.$('.layui-layer-shade').css('display', 'none');
	            	top.$('.layui-layer-setwin').css('display', 'none'); */
	            	var sheets = top.document.styleSheets
	        		var sheet = sheets[sheets.length-1]
	            	sheet.deleteRule(0)
	            },
	            success:function(){
	            	top.$('.layui-layer-shade').css('display', 'inherit');
	            	top.$('.layui-layer-setwin').css('display', 'inherit');
	            	/* if(top.$('.layui-layer-iframe')[0]){ */
	            		var sheets = top.document.styleSheets
	            		var sheet = sheets[sheets.length-1]
	            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
	            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
	            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
	            	/* } */
	            }
	        });
		}
		
		function clean(){
			$("#sitename").val("");
			$("#username").val("");
		}
		
		function update(id){
			if(id != null){
				top.layer.open({
		            title: '修改站点信息',
		            area: ['500px', '260px'],
		            type: 2,
		           	// btn: ['确认', '取消'],
		            content: '${ctx}/siteManage_toUpdateSite.do?id='+id,
		            end:function(){
		            	window.location.reload();
		            /* 	top.$('.layui-layer-shade').css('display', 'none');
		            	top.$('.layui-layer-setwin').css('display', 'none'); */
		            	var sheets = top.document.styleSheets
		        		var sheet = sheets[sheets.length-1]
		            	sheet.deleteRule(0)
		            },
		            success:function(){
		            	top.$('.layui-layer-shade').css('display', 'inherit');
		            	top.$('.layui-layer-setwin').css('display', 'inherit');
		            	/* if(top.$('.layui-layer-iframe')[0]){ */
		            		var sheets = top.document.styleSheets
		            		var sheet = sheets[sheets.length-1]
		            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
		            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
		            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
		            	/* } */
		            }
		        });
			}else{
				alert("请选择修改站点！");
			}
		}
		
		 //批量操作的checkbox方法
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
		 
		function checkDept(siteId){   
			$.ajax({  
				url : '${ctx}/siteManage_checkDept.do',
				type : 'POST',   
				cache : false,
				data:{"siteId":siteId},
				async : false,
				error : function() {  
					alert('AJAX调用错误(siteManage_checkDept.do)');
				},
				success : function(msg) {
					if(msg == 'success'){
						
					}else{
						alert("此站点下有部门，不能删除");
						$("[id="+siteId+"]").prop("checked", false);
					}
					
				}
			});
		}
		
		function deleteSite(){
			debugger;
			var selid = document.getElementsByName('selid');
			var siteIds = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					siteIds += selid[i].value.split(",")[0] + ",";
				}
			}
			
			if(siteIds.length > 0){
				siteIds = siteIds.substring(0, siteIds.length - 1);
			}else {
				alert('请选择一个站点');
				return;
			}
			
			if(confirm("确定要删除这些站点吗？")){
				//ajax调用
				$.ajax({
					url : '${ctx}/siteManage_deleteSite.do?siteIds='+siteIds,
					type : 'POST',  
					cache : false,
					async : false,
					error : function() {
					alert('AJAX调用错误(siteManage_deleteSite.do)');
						return false;
					},
					success : function(msg) {   
						if(msg =='success'){
							alert("删除成功！")
							window.location.href = '${ctx}/siteManage_showAllSite.do' ;
						}else{
							alert("删除站点失败！")
						}
						
					}
				 });
			}else{
				  return;				
			}
		}
		
	</script>
</html>