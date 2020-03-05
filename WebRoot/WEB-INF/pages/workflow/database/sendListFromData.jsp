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
<base target="_self"/>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form id="sendform"  name="sendform"  method="post" action="${ctx}/dataCenter_toSendListFromData.do" style="font-family: 宋体; font-size: 12pt;">
			<input type="hidden" id="modId" name="modId" value="${modId}" />
			<input type="hidden" id="matchId" name="matchId" value="${matchId}" />
			<input type="hidden" id="dicId" name="dicId" value="${dicId}" />
			
			<c:forEach var="cp" items="${cpLists}"  varStatus="cpn"> 
				<c:if test="${'2' != cp.columnType}" >
				<label class="wf-form-label" for="">${cp.name_local }：</label>
				<input type="text" class="wf-form-text"  id="${cp.columnName }" name="${cp.columnName}" value="${cp.searchValue}"/>
				</c:if>
				<c:if test="${'2' == cp.columnType}" >
				<label class="wf-form-label" for="">${cp.name_local }：</label>
				<input type="text" class="wf-form-date" readonly="readonly"  id="${cp.columnName}begin" name="${cp.columnName}begin" value="${cp.searchValue_begin}"/>
				-
				<input type="text" class="wf-form-date" readonly="readonly"  id="${cp.columnName}end" name="${cp.columnName}end" value="${cp.searchValue_end}"/>
				</c:if>
			</c:forEach>
			<button class="wf-btn-primary" onclick="document.forms.sendform.submit();" >
                <i class="wf-icon-search"></i> 搜索
            </button>
	            
            </form>
		</div>
	</div>
	<div  class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
			    		<th style="width:5%">序号</th>
			    		<c:forEach var="t" items="${hLists}" varStatus="n"> 
							<th>${t}</th>
						</c:forEach>
						<th style="width:8%">操作</th>
					</tr>
		    	</thead>
		    	<c:forEach var="s" items="${sLists_new}"  varStatus="sn"> 
					<tr>
						<td align="center">${sn.count}</td>
						<c:forEach var="t" items="${hLists}" varStatus="n"> 
							<td title="${s[n.count-1]}">
								${s[n.count-1]}
								<!--<c:choose>
										<c:when test="${fn:length(s[n.count-1]) > 25}">
											<c:out value="${fn:substring(s[n.count-1], 0, 25)}.." />
										</c:when>
										<c:otherwise>
											<c:out value=" ${s[n.count-1]}" />
										</c:otherwise>
								</c:choose>-->
							</td>
						</c:forEach>
						<td align="center">
							<c:if test="${num_lcId>1}">
								<a href="#" class="wf-btn wf-btn-primary" onclick="toDicItemList('${allLcId}','${s[hEnLists_size+1]}');"><i class="wf-icon-recycle"></i> 发起</a>
							</c:if>
							<c:if test="${!(num_lcId>1)}">
								<a href="#" class="wf-btn wf-btn-primary" onclick="openForm('${allLcId}','${s[hEnLists_size]}','${s[hEnLists_size+1]}');" ><i class="wf-icon-recycle"></i> 发起</a>
							</c:if>
						</td>
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
			skipUrl="<%=request.getContextPath()%>"+"/dataCenter_toSendListFromData.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('sendform');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
	</script>
<script type="text/javascript">

function toDicItemList(allLcId,value){
	var modId = document.getElementById("modId").value;
	var matchId = document.getElementById("matchId").value;
	var dicId = document.getElementById("dicId").value;
	var retVal=window.showModalDialog("${ctx}/item_getDicItemList.do?allLcId="+allLcId+"&dicValue="+value+"&modId="+modId+"&matchId="+matchId+"&dicId="+dicId+"&d="+Math.random(),window,'dialogWidth:500px;dialogHeight:300px; status: no; scrollbars: no; Resizable: no; help: no;center:yes;');
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
    	document.forms.sendform.submit();
	}
}

function openForm(lcid,itemid,value){
	var modId = document.getElementById("modId").value;
	var matchId = document.getElementById("matchId").value;
	var dicId = document.getElementById("dicId").value;
	//document.getElementById("dicValue").value=value;
	//document.getElementById("workflowid").value=lcid;
	//document.getElementById("itemid").value=itemid;
	var no = '${no}';
	var status = checkStatus(lcid);
	if(status!='0'){
		 var ret = window.showModalDialog('${ctx}/table_openFirstForm.do?workflowid='+lcid+'&itemid='+itemid+'&dicValue='+value+'&modId='+modId+'&matchId='+matchId+'&dicId='+dicId+'&no='+no+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
		 if(ret == "undefined" || typeof(ret) == "undefined"){
	        	//window.location.reload();
	 	 }else if(ret == "refresh"){
	 		document.forms.sendform.submit();
	 	 }
		 
		//document.forms.sub_form.submit();
		//window.location.href = "${ctx}/table_openFirstForm.do?workflowid="+lcid+"&itemid="+itemid+"&dicValue="+value+"&modId="+modId+"&matchId="+matchId+"&dicId="+dicId+"&no="+no;
	}else{
		alert("该流程为暂停状态，不可创建实例");
	}
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
</html>
