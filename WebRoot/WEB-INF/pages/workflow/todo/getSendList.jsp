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
<body>
 	<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
	<div class="wf-layout">
		<form name="doFileList" id="doFileList"  action="${ctx}/table_getDofileReceiveList.do" method="post">
		<div class="wf-list-top">
			<div class="wf-search-bar">
	 	        	<label class="wf-form-label" for="">标题：</label>
	                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
		            <button class="wf-btn-primary" onclick="document.forms.doFileList.submit();">
		                <i class="wf-icon-search"></i> 搜索
		            </button>
			</div>
		</div>
		<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
			            <th width="5%" >序号</th>
						<th>标题</th>
						<th width="15%" >所属事项</th>
						<th width="10%">发送时间</th>
						<th width="15%">操作</th>
						<!-- <th width="20%">文件下载</th> -->
						<c:if test="${isSync!=null && isSync==true }">
							<th width="10%">通知告示</th>
						</c:if>
			        </tr>
		    	</thead>
		    	<c:forEach var="item"  items="${list}" varStatus="status">
					<tr>
						<td align="center">${(selectIndex-1)*pageSize+status.count }</td>	
					    <td>
					     	<a href="#" onclick="openform('${item[3]}');">
					   		  	<c:choose>  
									<c:when test="${fn:length(item[0]) > 34}">  
					 			 		<c:out value="${fn:substring(item[0], 0, 34)}..." />  
									</c:when>  
									<c:otherwise>  
										<c:out value=" ${item[0]}" />  
									</c:otherwise>  
								</c:choose>
					     	</a>
                       	</td>	
					    <td align="left">${item[1]}</td>	
					    <td align="center">${item[2]}</td>	
					    <td align="center">
					    	<a href="#" class="wf-btn wf-btn-primary" onclick="searchDeatil('${item[3]}','${item[2]}','${item[0]}');">查看</a>
					    	<a href="#" class="wf-btn wf-btn-primary" onclick="reissue('${item[3]}','${item[2]}','${item[0]}');">补发</a>
					    	<a href="#" class="wf-btn wf-btn-danger" onclick="reback('${item[3]}','${item[2]}','${item[0]}');">撤销</a>
					   </td>
					   <%-- <td>
					   	<c:choose>
					   		<c:when test="${item[5]=='0'}">
					   			&nbsp;&nbsp;<a href="#"  style="color: #0e91d5;"  onclick="download('${item[3]}','${item[0]}','0');">下载</a>&nbsp;&nbsp;
					   			 &nbsp;&nbsp;<a href="#"  style="color: #0e91d5;display:none;"  onclick="download('${item[3]}','${item[0]}','1');">红章下载</a>&nbsp;&nbsp;
					   		</c:when>
					   		<c:otherwise>
					   			&nbsp;&nbsp;<a href="#"  style="color: #0e91d5;"  onclick="download('${item[3]}','${item[0]}','0');">下载</a>&nbsp;&nbsp;
					   		</c:otherwise>
					   	
					   	</c:choose>
					   </td> --%>
					   <c:if test="${isSync!=null && isSync==true }">
							<td >
								<c:if test="${item[5]=='0'}"> <!-- 发文该有的接口地址 -->
									<c:choose>
										<c:when test="${item[4]==null}"><a href="#" onclick="sycnToTzgg('${item[3]}');">同步</a></c:when>
										<c:when test="${item[4]=='0'}"><a href="#" onclick="updateNoticeInfoStatus('${item[3]}', 1);">隐藏</a></c:when>
										<c:when test="${item[4]=='1'}"><a href="#" onclick="updateNoticeInfoStatus('${item[3]}', 0);">展示</a></c:when>
									</c:choose>
								</c:if>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		</form>
		<div class="wf-list-ft" id="pagingWrap">
			
		</div>
	</div>    
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	//分页相关操作代码
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getDofileReceiveList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('doFileList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
</script>
<script type="text/javascript">
	//通知告示
	function sycnToTzgg(fprocessid){
		$.ajax({
			cache:false,
			async:false,
			type: "POST",
			url: "${ctx}/noticeInfo_saveNoticeInfo.do",
			data:{
				'fProcessId':fprocessid
			},
			error: function(){
		   		alert('同步到通知告示失败！');
			},
			success: function(msg){
				 var result=eval('('+msg+')');
				 if(result.success){
					 alert("同步成功");
					 window.location.href="${ctx}/table_getDofileReceiveList.do";
				 }else{
					 alert("同步失败");
				 }
			}
		});
	}
	
	//隐藏或者同步
	function updateNoticeInfoStatus(fprocessid, status){
		$.ajax({
			cache:false,
			async:false,
			type: "POST",
			url: "${ctx}/noticeInfo_updateNoticeInfoStatus.do",
			data:{
				'fprocessId':fprocessid,'status':status
			},
			error: function(){
		   		alert('同步到通知告示失败！');
			},
			success: function(msg){
				 var result=eval('('+msg+')');
				 if(result.success){
					 if(status=='1'){
						 alert("隐藏成功！");
					 }else{
						 alert("展示成功！");
					 }
					 window.location.href="${ctx}/table_getDofileReceiveList.do";
				 }else{
					 alert("操作失败！");
				 }
			}
		});
	}
	
	//文件的下载
	function download(fprocessid, title, type){
		//获取文件的地址
		$.ajax({
		cache:false,
		async:false,
		type: "POST",
		url: "${ctx}/table_getRedChapterPdf.do",
		data:{
			'fprocessid':fprocessid, 'title':title, "type":type
		},
		error: function(){
	   		alert('获取文件地址失败！');
		},
		success: function(msg){
			var path = "";
			var filename = "";
			if(msg!=null && msg!=''){
				path = msg.split(";")[0];
				filename = msg.split(";")[1];
			}
			var	filename = encodeURI(filename);
			var obj = document.getElementById("download_iframe");
			obj.src = "${ctx}/attachment_download.do?name="+filename+"&location="+path;
		}
	});
  }

	
  //打开form表单: 类似于openform
  function openform(fProcessId){
	  var url = '${ctx}/table_openOverForm.do?disfavourite=1&processId='+fProcessId+'&t='+new Date();
	  /* var ret = window.showModalDialog('${ctx}/table_openOverForm.do?disfavourite=1&processId='+fProcessId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
	  if(ret == "refresh"){
	    	window.location.reload();
	  } */
	  var title = "详细情况";
	  openLayerTabs(fProcessId,screen.width,screen.height,title,url);
  }
	
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script type="text/javascript">
	//执行补发操作(弹出补发页面)
	function reissue(fprocessid,applydate, title){
		/* var ret = window.showModalDialog('${ctx}/table_toReissueJsp.do?fprocessid='+fprocessid+'&title='+encodeURI(title)+'&t='+new Date(),null,"dialogTop=120;dialogLeft=200;dialogWidth=1000px;dialogHeight=500px;help:no;status:no;scroll:yes;");
		if(ret == "refresh"){
	 		window.location.reload();
		} */
		
		var url = '${ctx}/table_toReissueJsp.do?fprocessid='+fprocessid+'&title='+encodeURI(title)+'&t='+new Date();
		parent.layer.open({
	        title :'办件补发',
	        content: url,
	        type: 2, 
	        area: ['1000px', '500px'],
		});
	}

//强制收回
function reback(fprocessid,applydate, title){
	if(confirm("是否确认强制收回？")){
		//ajax异步去提交数据
		$.ajax({   
			url : '${ctx }/table_rebackSendInfo.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_rebackSendInfo.do)');
			},
			data : {
				'fprocessid':fprocessid
			},    
			success : function(msg) { 
				var result=eval('('+msg+')');
				 if(result.success){
					 alert("收回成功！");
					 window.location.href = "${ctx}/table_getDofileReceiveList.do";
				 }
			}
		});
	}
}

function openForm(processId,itemId,formId){
	var ret = window.showModalDialog('${ctx}/table_openPendingForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	if(ret == "refresh"){
 		window.location.reload();
	}
}

//检查该发送信息的接收情况
function searchDeatil(fprocessid,applydate,title){
	var url = '${ctx}/table_getDetailReceiveInfo.do?applydate='+applydate+'&fprocessid='+fprocessid+'&title='+encodeURI(title)+'&t='+new Date();
	var title = '发文监控情况';
	openLayerTabs(fprocessid,screen.width,screen.height,title,url);
	//window.showModalDialog('${ctx}/table_getDetailReceiveInfo.do?applydate='+applydate+'&fprocessid='+fprocessid+'&t='+new Date(),null,"dialogTop=120;dialogLeft=200;dialogWidth=1000px;dialogHeight=500px;help:no;status:no;scroll:yes;");
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

function openLayerTabs(processId,width,height,title,url){
   window.parent.topOpenLayerTabs(processId,1200,600,title,url);
}
</script>
<script>
	$('#pageContent').height($(window).height()-120);
</script>
</html>