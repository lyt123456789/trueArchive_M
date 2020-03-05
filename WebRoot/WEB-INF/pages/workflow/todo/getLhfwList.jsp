<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>		
<body style="overflow-x: hidden; overflow-y: auto;">

<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lhfwList" id="lhfwList" action="${ctx}/table_getLhfwList.do" method="post">
 	        	<label class="wf-form-label" for="">标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
                <label class="wf-form-label" for="">提交时间：</label>
                <input type="text" class="wf-form-text wf-form-date" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
				<label class="wf-form-label" for="">事项类别：</label>
                <select class="wf-form-select" id="itemName" onchange="changeCondition()" name="itemName">
                	<option value=""></option>
                	<c:forEach var="m" items='${myPendItems}'>
	 					<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
	 				</c:forEach> 
                </select>
			    <input type="hidden" name="itemid" class="filejieshu-text" value="${itemid}" />
	            <button class="wf-btn-primary" onclick="document.forms.lhfwList.submit();">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
						<th width="3%">序号</th>
						<th>标题</th>
						<th width="10%">提交部门</th>
						<th width="10%">提交人</th>
						<th width="10%">提交时间</th>
						<th width="15%">事项类别</th>
						<th width="10%">状态</th>
						
			        </tr>
		    	</thead>
		    	<c:forEach var="item" items="${list}" varStatus="status"> 
		       	  <tr>
					<td align="center">${(selectIndex-1)*pageSize+status.count}</td>
					<td style="text-align: left;" title="${item.process_title}">
						<c:choose>
							<c:when test="${item.isDelaying != '1'}">
									<a href="#" onclick="openLhfwForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}',
											'${item.isCanPush}', '${item.stepIndex}', '${item.isChildWf}', '${item.item_type}','${isCy}');">
									${item.process_title}
									</a>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${fn:length(item.process_title) > 25}">
										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />
									</c:when>
									<c:otherwise>
										<c:out value=" ${item.process_title}" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${item.userDeptId}</td>
					<td>${item.employee_name }</td>
					<td>${fn:substring(item.apply_time,0,16) }</td>
					<td style="color:#FF9900;">${item.item_name }</td>
					<td>
						<c:if test="${item.status == '0'}">
							<div class="gwdbIco State2"></div>
						</c:if>
						<c:if test="${item.status == '1'}">
							<div class="gwdbIco State3"></div>
						</c:if>
						<c:if test="${item.status == '3'}">
							<span class="label-1">超</span>
						</c:if>
					</td>
				 </tr>
		       </c:forEach>
			</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getPendingList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lhfwList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	//打开待办
	function openLhfwForm(processId,itemId, formId, isCanPush, stepIndex, isChildWf, item_type, isCy){
		openForm(processId,itemId,formId,isCanPush);
	}
 	function openForm(processId,itemId,formId,isCanPush){
 		 var status = document.getElementById("status").value;
		 var ret = window.showModalDialog('${ctx}/table_openLhfwForm.do?status='+status+'&processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
	 	 if(ret == "undefined" || typeof(ret) == "undefined"){
 	 	}else if(ret == "refresh"){
 		 	window.location.href="${ctx}/table_getLhfwList.do?itemid=${itemid}";
 		 }
 	}
 
 function changeCondition(condition){
	var divs =  $('#conditionDiv .conditionDiv');
	for(var i = 0; i < divs.length; i++){
		if(i != condition){
			divs[i].style.display="none";
		}else{
			divs[i].style.display="";
		}
	}
	$('#searchCondition').val(condition);
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
(function($){
	debugger;
var selects=$('#searchCondition');//获取select
for(var i=0;i<selects.length;i++){
	createSelect(selects[i],i);
}
function createSelect(select_container,index){
	select_container.style.display="none";
	//创建select容器，class为select_box，插入到select标签前
	var tag_select=$('<div></div>');//div相当于select标签
	tag_select.attr('class','select_box');
	tag_select.insertBefore(select_container);
	//显示框class为select_showbox,插入到创建的tag_select中
	var select_showbox=$('<div></div>');//显示框
	select_showbox.css('cursor','pointer').attr('class','select_showbox').appendTo(tag_select);
	//创建option容器，class为select_option，插入到创建的tag_select中
	var ul_option=$('<ul></ul>');//创建option列表
	ul_option.attr('class','select_option');
	ul_option.appendTo(tag_select);
	createOptions(index,ul_option);//创建option
	//点击显示框
	tag_select.toggle(function(){
		ul_option.show();
	},function(){
		ul_option.hide();
	});
	var li_option=ul_option.find('li');
	li_option.on('click',function(){
		
		$(this).addClass('selected').siblings().removeClass('selected');
		var value=$(this).text();
		changeCondition($(this).val());
		select_showbox.text(value);
		ul_option.hide();
	});
	li_option.hover(function(){
		$(this).addClass('hover').siblings().removeClass('hover');
	},function(){
		li_option.removeClass('hover');
	});
}
function createOptions(index,ul_list){
	//获取被选中的元素并将其值赋值到显示框中
	var options=selects.eq(index).find('option'),
		selected_option=options.filter(':selected'),
		selected_index=selected_option.index(),
		showbox=ul_list.prev();
		showbox.text(selected_option.text());
	//为每个option建立个li并赋值
	for(var n=0;n<options.length;n++){
		var tag_option=$('<li></li>'),//li相当于option
			txt_option=options.eq(n).text();
		tag_option.text(txt_option).val(options.eq(n).val()).css('cursor','pointer').appendTo(ul_list);
		//为被选中的元素添加class为selected
		if(n==selected_index){
			tag_option.attr('class','selected');
		}
	}
}
})(jQuery);
</script>
<script>
	$('#pageContent').height($(window).height()-100);
</script>
</body>
</html>