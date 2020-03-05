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
	<%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindexnewstyle.jsp"%>
    	<%@ include file="/common/function.jsp"%>
    	
    <!--表单样式-->
	<link href="${ctx}/newstyle/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/newstyle/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	    <link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	    <script type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>
<body>
 <div class="pageHeader" >
    <form id="doFileList" method="POST" name="doFileList" action="${ctx }/table_getAllOverList.do" >
        <div class="searchBar" >
		<table class="searchContent">
			<tr>  
				<td>标题:<input type="text" name="title" value="${title}" />
		</td> 
				<td>从<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" readonly="readonly"  id="finishTimeFrom" name="finishTimeFrom" value="${finishTimeFrom}"/>
				    	到<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" readonly="readonly"  id="finishTimeTo" name="finishTimeTo" value="${finishTimeTo}"/>
				</td> 
				<td>
                <div class="btngroup fl ml5">
                    <div class="btns bluebtn search-icon">
                        <div class="btn-l"></div>
                        <div class="btn-m" onclick="document.forms.doFileList.submit();">
                            <i></i>查询
                        </div>
                        <div class="btn-r"></div>
                    </div>
                </div>				
           		</td>
			</tr>
		</table>
			</div>
	</form>
</div>
		<div id="pageContent" class="pageContent"  style="overflow:visible;">
			<div id="w_list_print">
	        <table border="1" width="100%" class="list">
	           	<thead>
		            <tr>
						<th width="5%" >序号</th>
						<th width="20%" >办件标题</th>
						<th width="20%" >事项类别</th>
						<th width="18%" >当前步骤</th>
						<th width="8%" >办件状态</th>
						<th width="14%" >办理时间</th>
					</tr>
			</thead>	
		<tbody>
				<c:forEach items="${doFileList}" var="doFile" varStatus="status">
					<tr onclick="dianji('${doFile.doFile_id}')">
							<c:choose>
								<c:when test="${favourite=='1' || isAdmin!='1'}">
								</c:when>
								<c:otherwise>
									<td align="center">
			    						<input type="checkbox" name="selid" value="${doFile.doFile_id}" >
			    					</td>
								</c:otherwise>
							</c:choose>
						<td style="text-align:center;">${(selectIndex-1)*pageSize+status.count}</td>
						<td style="text-align:left;" title="${doFile.doFile_title}">
								<span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
									<a href="#" onclick="openForm('${doFile.processId}','${doFile.itemId}','${doFile.formId}','${favourite}');">
										 <c:choose>  
    										<c:when test="${fn:length(doFile.doFile_title) > 24}">  
        										<c:out value="${fn:substring(doFile.doFile_title, 0, 24)}..." />  
    										</c:when>  
   										<c:otherwise>  
      										<c:out value=" ${doFile.doFile_title}" />  
    										</c:otherwise>  
										</c:choose>
									</a>
								</span>
						</td>
						<td>${doFile.itemName}</td>
						<td>${doFile.nodeName}</td>
						<td>
							<c:if test="${doFile.doFile_result==0}">
								<div class="gwdbIco State2"></div>
							</c:if>
							<c:if test="${doFile.doFile_result==1}">
								<div class="gwdbIco State3"></div>
							</c:if>
						</td>
						<td>${fn:substring(doFile.applyTime,0,16)}</td>
					</tr>
		        </c:forEach>
		</tbody>
	        </table>
	       <div class="formBar" style="bottom:0px;width:100%;">  
 		<div id="div_god_paging" class="cbo pl5 pr5"></div> 
	</div>
	</div>
	</form>
</div>
<script type="text/javascript">
var ids="";
function dianji(id){
	ids =id;
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

function openForm(processId,itemId,formId,favourite){
	if(processId!=''){
		 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
		 if(ret == "refresh"){
	      	window.location.reload();
		 }
	}
}

//分页相关操作代码
window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getAllOverList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('doFileList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
};
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script type="text/javascript">
/**
 * 重定向：检索办件处于的步骤信息
 */
function redirectPending(instanceId){
	window.location.href="${ctx}/table_getRedirectDetail.do?instanceId="+instanceId;
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
var selects=$('select');//获取select
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
	$('#pageContent').height($(window).height()-25);
</script>
</body>
</html>