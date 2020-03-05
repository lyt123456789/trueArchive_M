<!DOCTYPE HTML>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<html>
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<link rel="stylesheet" href="${ctx}/css/gwdb.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/common.css" type="text/css" />
<script src="${ctx}/js/jquery-1.8.2.min.js"></script><!--表单样式-->
<script src="${ctx}/widgets/common/js/god_paging.js"></script>
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/images/core.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/newstyle/style/css/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
<style>
	.conditionDiv{
		float: left;
	}
	.select_box {
		margin-right: 5px;
	}
	.clearfix:after {
		content:"";
		display: block;
		height: 0px;
		font-size: 0px;
		clear: both;
		visible: hidden;
	}
	.label-1 {
		display: block;
		width: 26px;
		height: 14px;
		padding: 5px 0px;
		margin: 8px auto;
		font: 500 14px/14px "Microsoft Yahei";
		color: #fff;
		text-align: center;
		background: url(./images/bg.png) no-repeat;
	}
</style>
</head>	
<body style="overflow-x: hidden; overflow-y: auto;">
<form name="overList" id="overList" action="${ctx }/table_getOverList.do" method="post">
<input type="hidden" name="status" id="status" value="${status}">
<div class="zlgx-content">
	<div class="zlgx-con" id="pageContent" style="overflow-y: auto;overflow-x: hidden;">
		<table border="1" width="100%" class="list">
			<tbody>
				<tr>
			         	<th width="12%">序号</th>
						<th >标题</th>
						<th width="25%">当前办理人员</th>
			        </tr>

		
			      <c:forEach var="item" items="${wfList}" varStatus="status"> 
					<tr>
					<td align="center">${status.count}</td>
					<td align="left" title="${item.processTitle }" style="text-align: left; padding-left: 10px;">
					        <span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
							<c:if test="${!('1' eq item.isChildWf)}">[主]</c:if>
			   			 	<c:if test="${('1' eq item.isChildWf)}">[子]</c:if>
			   			 	</span>
								<span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item.wfProcessUid}','${item.itemId}','${item.formId}','${item.favourite}');">
								<c:choose>  
    							<c:when test="${fn:length(item.processTitle) > 24}">  
        							<c:out value="${fn:substring(item.processTitle, 0, 24)}..." />  
    							</c:when>  
   								<c:otherwise>  
      								<c:out value=" ${item.processTitle}" />  
    							</c:otherwise>  
							</c:choose>
								</a></span>
							</td>
						<td>${item.userName}</td>
					</tr>
				</c:forEach>
		        </tbody>
	        </table>
	      
	</div>
</div>

<div class="formBar pa" style="bottom:0px;width:100%;">  
 		<div id="div_god_paging" class="cbo pl5 pr5"></div> 
	</div>
	</form>
<script type="text/javascript">
//收回办件
function recycleTask(instanceId,stepIndex){
	// 判读办件下一步是否 办件（如果是超管 pass，普通用户 go
	var flag = false;
	if(confirm("确定要收回吗？")){
		flag = true;
	}
	if(!flag){
		return;
	}
	$.ajax({   
		url : 'table_nextStepIsOver.do',
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {  
			alert('AJAX调用错误(table_nextStepIsOver.do)');
		},
		data : {
			'instanceId':instanceId,'stepIndex':stepIndex
		},    
		success : function(msg) {  
			if(msg == 'yes'){
				// 说明 下一步没有办理,或者超管执行收回操作
				$.ajax({
					url : '${ctx}/table_recycleTask.do',
					type : 'POST',
					cache : false,
					async : false,
					data : {
						'instanceId':instanceId,'stepIndex':stepIndex
					},
					error : function() {
						alert('AJAX调用错误');
					},
					success : function(ret) {
						if (ret == 'yes') {
							alert("办件收回成功");
							window.location.reload();
						} else {
							alert("办件收回失败");
						}
					}
				});
			}else{
				alert("该办件下一步骤，已办理，无法回收");
			}
			
		}
	});
	
}
function openForm(processId,itemId,formId,favourite){
	 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&isShowRelDoFile=1&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
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
var selects=$('select1');//获取select
for(var i=0;i<selects.length;i++){
	createSelect(selects[i],i);
}
function createSelect(select_container,index){
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
		tag_option.text(txt_option).css('cursor','pointer').appendTo(ul_list);
		//为被选中的元素添加class为selected
		if(n==selected_index){
			tag_option.attr('class','selected');
		}
	}
}
})(jQuery);
</script>
<script>
	$('#pageContent').height($(window).height()-60);

</script>
		<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
</body>
</html>