<!DOCTYPE HTML>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
</head>		
<body style="overflow-x: hidden; overflow-y: auto;">
    <div class="wf-layout">
    	<form name="pendingList" id="pendingList" action="${ctx }/replay_getReplayList.do" method="post">
		<div class="wf-list-top">
			<div class="wf-search-bar">
		            <label class="wf-form-label" for="">标题：</label>
		            <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
		            <input type="hidden" name="itemid" class="filejieshu-text" value="${itemid}" />
		            <button class="wf-btn-primary" onclick="document.forms.pendingList.submit();">
		                <i class="wf-icon-search"></i> 搜索
		            </button>
			</div>
		</div>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	<tr>
						<th width="5%">序号</th>
						<th>标题</th>
						<th width="30%">提交人</th>
						<th width="20%">提交时间</th>
						
			        </tr>
			    	</thead>
			    	<c:forEach var="item" items="${list}" varStatus="status"> 
			       	  <tr style="height: 25px;">
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td>
							<a href="#" onclick="openReplyForm('${item.id}','${item.replayProcessid}');">${item.title}</a>
						</td>
						<td>${item.replayUserName}</td>
						<td>
						<fmt:formatDate value="${item.replayTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
						</td>
					 </tr>
			       	</c:forEach>
				</table>
			
		</div>
		</form>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
	</div>
	
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/replay_getReplayList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('pendingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	function openReplyForm(replyId, processId){
		var ret = window.showModalDialog('${ctx}/table_openOverForm.do?processId='+processId+'&replyId='+replyId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
		if(ret == "refresh"){
	    	window.location.reload();
		}
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
</body>
</html>