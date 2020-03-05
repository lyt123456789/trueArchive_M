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
</head>		
<body style="overflow-x: hidden; overflow-y: auto;">
  <form name="pendingList" id="pendingList" action="${ctx }/toRecDoc_getToSendDocList.do" method="post">
    <div class="zlgx-content">
        <div class="zlgx-top">
		    <div class="zlgx-right">
			    <span>查询条件：</span>
				<span>标题&nbsp;</span>
				<input type="text" name="wfTitle" class="filejieshu-text" value="${wfTitle }" />
				<span>&nbsp;&nbsp;&nbsp;发文单位&nbsp;</span>
				<input type="text" name="fwdw" class="filejieshu-text" value="${fwdw}" />
                <div class="btngroup fl ml5">
                    <div class="btns bluebtn search-icon">
                        <div class="btn-l"></div>
                        <div class="btn-m" onclick="document.forms.pendingList.submit();">
                            <i></i>检索
                        </div>
                        <div class="btn-r"></div>
                    </div>
                </div>				
            </div>			
		</div>
		<div class="zlgx-con" id="pageContent" style="overflow-y: auto;overflow-x: hidden;">
	        <table border="1" width="100%" class="list">
	            <tbody>
		            <tr>
						<th width="5%">序号</th>
						<th width="10%">文号</th>
						<th>标题</th>
						<th width="10%">发文单位</th>
						<th width="10%">发文时间</th>
						<th width="5%">操作</th>
			        </tr>
			       <c:forEach var="item" items="${list}" varStatus="status"> 
			       	  <tr height="40px;">
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td>${item.wh}</td>
						<td>${item.bt}</td>
						<td>${item.fwdw}</td>
						<td>${item.creatTime}</td>
						<td>
							<a href="#" onclick="updateStatus('${item.instanceId}');">
								查看
							</a>
						</td>
					  </tr>
					 </c:forEach>
		        </tbody>
	        </table>
	 </div>
 </div>
</form>
 <div class="formBar pa" style="bottom:0px;width:100%;">  
 <div id="div_god_paging" class="cbo pl5 pr5"></div> 
</div> 
<script type="text/javascript">
//分页相关操作代码
window.onload=function(){ 
		skipUrl="<%=request.getContextPath()%>"+"/toRecDoc_getToSendDocList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('pendingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
</script>
<script type="text/javascript">
function updateStatus(instanceId){
	 var ret = window.showModalDialog('${ctx}/toRecDoc_getRecDepList.do?instanceId='+instanceId+'&t='+new Date(),null,"dialogWidth=450px;dialogHeight=300px;help:no;status:no;scroll:no");
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
	$('#pageContent').height($(window).height()-100);
</script>
</body>
</html>