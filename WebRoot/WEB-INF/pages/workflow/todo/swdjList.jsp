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
<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
<body>
    <div class="zlgx-content">
    <form name="pendingList" id="pendingList" action="${ctx}/table_getSwdjList.do" method="post">
		<input type="hidden" id="swdjlx" name="swdjlx" value="${swdjlx}">
        <div class="zlgx-top">
		    <div  style="float: left; width: 1000px; margin-left: 20px;"  class="zlgx-all">
			    <span>查询条件：</span>
				<span>登记号</span>
				<input type="text" name="djh" class="filejieshu-text" value="${djh}" />
				<span>文件标题</span>
				<input type="text" name="wjbt" class="filejieshu-text" value="${wjbt}" />
				<span>来文单位</span>
				<input type="text" name="lwdw" class="filejieshu-text" value="${lwdw}" />
				<span>&nbsp;&nbsp;从</span>
				<input type="text" class="filekaishi-inp" name="beginTime" id="beginTime" value="${beginTime}" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate: '#F{$dp.$D(\'endTime\')}'})" />
				<span>至</span>	
					<input type="text"  class="filekaishi-inp" name="endTime" id="endTime" value="${endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate: '#F{$dp.$D(\'beginTime\')}'})" />
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
            <div style="width: 8px;float:left;" >
                <div  class="btns yellowbtn export-icon" style="padding-rigth: 10px;">
                    <div class="btns redbtn delete-icon">
                        <div class="btn-l"></div>
                            <div class="btn-m" style="width: 38px;" onclick="exportexcel();">
                                <i></i>导出
                        </div>
                    </div>
                </div>
            </div>		
		</div>
<div class="zlgx-con" id="pageContent" style="overflow-y: auto;overflow-x: hidden;">
	        <table border="1" width="100%" class="list">
	            <tbody>
		            <tr>
			            <th width="4%">序号</th>
						<th width="10%">登记号</th>
						<th width="24%">文件标题</th>
						<th width="12%">收文时间</th>
						<th width="14%">来文单位</th>
						<th width="18%">来文号</th>
						<th width="10%">成文日期</th>
						<th width="8%">紧急程度</th>
			        </tr>
				<c:forEach items="${sws}" var="sw" varStatus="status">
					<tr>
						<td align="center" style="height: 40px;">${(selectIndex-1)*pageSize+status.count}</td>
						<td  align="center">${nf}年${sw[0]}号</td>
						<td  align="left" title='${sw[1]}' style="text-align: left; padding-left: 10px;">
							<c:if test="${sw[10] =='1'}">
								<a href="javascript:openReceiveForm('${sw[9]}')">
									<c:choose>  
    									<c:when test="${fn:length(sw[1]) > 20}">  
        									<c:out value="${fn:substring(sw[1], 0, 20)}.." />  
    									</c:when>  
   									<c:otherwise>  
      										<c:out value=" ${sw[1]}" />  
    								</c:otherwise>  
									</c:choose>
								</a>
							</c:if>		
							<c:if test="${sw[10] =='2'}">
								<c:choose>  
    								<c:when test="${fn:length(sw[1]) > 20}">  
        								<c:out value="${fn:substring(sw[1], 0, 20)}.." />  
    								</c:when>  
   									<c:otherwise>  
      									<c:out value=" ${sw[1]}" />  
    								</c:otherwise>  
								</c:choose>
							</c:if>	
					</td>
				<td  align="center">${sw[2]}</td>
				<td  align="center">${sw[3]}</td>
				<td  align="center">${sw[4]}</td>
				<td  align="center">${sw[5]}</td>
				<td  align="center">${sw[6]}</td>
			</tr>
		</c:forEach>			
		        </tbody>
	        </table>
		</div>
	</form>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
 <div id="div_god_paging" class="cbo pl5 pr5"></div> 
</div>
<script type="text/javascript">
//分页相关操作代码
window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getSwdjList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('pendingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}
function queryfile(){
	document.forms.pendingList.action="${ctx}/table_getSwdjList.do";
	document.forms.pendingList.submit();
}

function exportexcel(){
	document.forms.pendingList.action="${ctx}/table_exportSwdjList.do";
	document.forms.pendingList.submit();
}
//待收已收待办打开
function openReceiveForm(instanceid){
	$.ajax({   
		url : '${ctx }/table_getPathByInstanceid.do',
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {  
			alert('AJAX调用错误(table_addOfficeName.do)');
		},
		data : {
			'instanceid':instanceid
		},    
		success : function(cyNames) { 
			var res = eval("("+cyNames+")");
			window.showModalDialog('${ctx}/table_openReceiveForm.do?path='+res.path+'&receiveId='+res.receiveId+'&status=2&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
		}
	});
	
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
$('#pageContent').height($(window).height()-80);
</script>
</body>
</html>