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
<!--页面样式-->
<script type="text/javascript">
	function openForm(lcid,itemid,itemName){
		var no = '${no}';
		var status = checkStatus(lcid);
		if(status!='0'){
			var url = "${ctx}/table_openFirstForm.do?workflowid="+lcid+"&itemid="+itemid+"&no="+no;
			
			openLayerTabs(lcid,screen.width,screen.height,itemName,url);
		}else{
			alert("该流程为暂停状态，不可创建实例");
		}
	}
	
	function openStartForm(id,url,title){
		openLayerTabs(id,screen.width,screen.height,title,url);
	}
	
	function openLayerTabs(processId,width,height,title,url,imgPath){		
	   window.parent.topOpenLayerTabs(processId,1200,600,title,url,imgPath);
	}
	
	//检查流程状态，“暂停”则不可新建实例
	function checkStatus(lcid){
		var status = "";
		$.ajax({   
			url : '${ctx }/item_getWfStatus.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
			alert('AJAX调用错误(item_getWfStatus.do)');
			},
			data : {
				'workflowid':lcid
			},    
			success : function(result) {  
				status = result;
			}
		});
		return status;
	}
</script>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="doItemList"  id="doItemList" action="${ctx }/item_getItemList.do" method="post">
			<input type="hidden" name="no" id="no" value="${no}">
			<input type="hidden" name="ids" id="ids" value="${ids}">
			<c:choose>
			<c:when test="${no!=null && no!='' }">
			</c:when>
			<c:otherwise>
			<div class="wf-top-tool">
	            <a class="wf-btn" href="${ctx}/item_toAddJsp.do">
	                <i class="wf-icon-plus-circle"></i> 新建
	            </a>
	            <a class="wf-btn-primary" href="javascript:xg_row();">
	                <i class="wf-icon-pencil"></i> 修改
	            </a>
	            <a class="wf-btn-danger" href="javascript:del()" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-minus-circle"></i> 删除
	            </a>
	        </div>
	        </c:otherwise>
			</c:choose>
	            <label class="wf-form-label" for="">事项类别：</label>
	            <input type="text" class="wf-form-text" name="itemName" value="${itemName}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
		    		<th width="5%">序号</th>
			    	<th width="25%">事项类别</th>
			    	<th width="10%">事项类型</th>
			    	<th width="10%">完成时限(工作日)</th> 
			    	<th width="18%">所属部门</th>
			    	<th width="12%">创建时间</th>
			    	<th width="10%">申请延期</th>
			    	<th width="5%">排序号</th>
		    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="d" varStatus="n">
			    	<tr target="sid_user" rel="1">
			    		<td align="center">
			    			<input type="checkbox" name="selid" value="${d.id}" >
			    		</td>
				    	<td align="center" itemid="${d.id}">
				    		${(selectIndex-1)*pageSize+n.count}
				    	</td>
				    	<td  class="workflowidtitle" workflownnameid="1">
				    		<a href="javascript:openForm('${d.lcid}','${d.id}');">
				    		 <c:choose>  
    							<c:when test="${fn:length(d.vc_sxmc) > 40}">  
      							  <c:out value="${fn:substring(d.vc_sxmc, 0, 40)}..." />  
    							</c:when>  
   								<c:otherwise>  
      								<c:out value=" ${d.vc_sxmc}" />  
    							</c:otherwise>  
							</c:choose>
				    		</a>
				    	</td>
				    	<td align="center" class="workflowidtitle" workflownnameid="1">
				    		<c:if test="${d.vc_sxlx == '0'}">公文(发文)</c:if>
							<c:if test="${d.vc_sxlx == '1'}">公文(办文)</c:if>
							<c:if test="${d.vc_sxlx == '3'}">客情报告</c:if>
							<c:if test="${d.vc_sxlx == '4'}">用车申请</c:if>
				    	</td>
				    	<td align="center"  class="workflowidtitle" workflownnameid="1">
				    		${d.vc_wcsx}
				    	</td>
				    	<td  class="workflowidtitle" workflownnameid="1">
				    		${d.vc_ssbm}
				    	</td>
				    	<td  align="center">
				    		${fn:substring(d.c_createdate,0,16)} 
				    	</td>
				    	<td align="center">
				    	<c:choose>
				    		<c:when test="${d.delayItemName eq null}">
				    			<a href="#" class="wf-btn wf-btn-primary" onclick="addItemRelation('${d.id}');"><i class="wf-icon-anchor"></i> 绑定</a>
				    		</c:when>
				    		<c:otherwise>
				    			<a href="#" class="wf-btn wf-btn-primary" onclick="updateItemRelation('${d.id}');"><i class="wf-icon-anchor"></i>${d.delayItemName}</a>
				    		</c:otherwise>
				    	</c:choose>
				    	</td>
				    	<td align="center">${d.index}</td>
			    	</tr>
		    	</c:forEach>
			</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
	</div>
</div>
</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/item_getItemList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doItemList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		function addItemRelation(itemid){
			var retVal = window.showModalDialog("${ctx}/itemRelation_toadd.do?itemid="+itemid+'&a=' + Math.random(),null,"dialogWidth:600px;dialogHeight:200px;help:no;status:no;scroll:no");
			if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
		         
		    }else{
		    	 window.location.href="${ctx}/item_getItemList.do"; 
			}
		}
		
		function updateItemRelation(itemid){
			var retVal = window.showModalDialog("${ctx}/itemRelation_toupdate.do?itemid="+itemid+'&a=' + Math.random(),null,"dialogWidth:600px;dialogHeight:200px;help:no;status:no;scroll:no");
			if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
		         
		    }else{
		    	 window.location.href="${ctx}/item_getItemList.do"; 
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

		function xg_row(){
			 var id=$(".wf-actived td:eq(1)").attr("itemid");
			 if(id){
			 	location.href = "${ctx}/item_toEditJsp.do?id="+id;
			 }else{
				alert('请选择要修改的组！');
		     }
		}
	
		function del(){
			if(confirm("确定要删除吗？")){
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
				//ajax调用
				$.ajax({
					url : '${ctx}/item_del.do?ids='+ids,
					type : 'POST',  
					cache : false,
					async : false,
					error : function() {
					alert('AJAX调用错误(item_del.do)');
						return false;
					},
					success : function(msg) {   
						if(msg =='success'){
							window.location.href = '${ctx}/item_getItemList.do' ;
						}
					}
				});
			}
		}
	</script>
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
</html>