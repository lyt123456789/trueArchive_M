<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>详情页</title>
    <link rel="stylesheet" href="css/list.css?t=123">
     <link rel="stylesheet" href="css/form.css?t=111">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style type="text/css">
    </style>
</head>
<body>
<div class="show">
<div class="table2" style="height: 65%;">
    <table cellspacing="0" cellpadding="0">
		<tr>
		<c:forEach items="${tags}" var="tag" varStatus="state">
			<c:set var="key" value="C${tag.id}"></c:set>
			<c:if test="${state.count%2!=0}">
				<td width="110" class="name "> ${tag.esIdentifier}</td>
				<td width="323"> ${dataMap[key]}</td>
			</c:if>
			<c:if test="${(state.index+1)%2==0 }">
				<td width="110" class="name "> ${tag.esIdentifier}</td>
				<td width="323"> ${dataMap[key]}</td>
				</tr>
				<tr>
			</c:if>
		</c:forEach>
		</tr> 
	</table>
</div>
<div class="dz">
    <span>
    	<c:if test="${'3' eq sonStructType }">卷内目录</c:if>
		<c:if test="${'4' eq sonStructType }">电子文件目录</c:if>
    </span>
    <c:if test="${null ne returnId }">
   		<a href="javascript:void(0);" onclick="returnBack();" style="height: 42px;line-height: 42px;"><img src="img/ret.png">返回上一级</a>
    </c:if>
</div>

<form method="post" id="form" name="form" action="${ctx }/model_showDetail.do" >
<input type="hidden" id="id" name="id" value="${id }">
<input type="hidden" id="structId" name="structId" value="${structId }">
<input type="hidden" id="fatherId" name="fatherId" value="${fatherId }">
<input type="hidden" id="returnStructId" name="returnStructId" value="${returnStructId }">
<input type="hidden" id="returnId" name="returnId" value="${returnId }">
<div class="list_table">
    <table cellspacing="0" cellpadding="0">
        <tr class="fr_tr">
        	<td>操作</td>
        	<c:forEach items="${tags2}" var="tag" varStatus="state">
	    		<td>${tag.esIdentifier}</td>
	    	</c:forEach>	
        </tr>
        <tbody>
        <c:forEach items="${sonDataMapList}" var="item" varStatus="state">
        	<c:set var="idd" value="${item['id']}"></c:set>
        	<tr>
	  			 <td>
	  			 	<c:if test="${'4' eq sonStructType}">
	  			 		<button type="button" class="btn_yw" onclick="showFile('${idd}')"></button>
	  			 	</c:if>
	  			 	<c:if test="${'4' ne sonStructType}">
	  			 		<button type="button" class="btn_xq" onclick="showDetail('${idd}')"></button>
	  			 	</c:if>
	  			 	<c:if test="${'4' eq sonStructType}">
	  			 		<c:if test="${'1' ne zzcdFlag}">
	  			 			<button type="button" class="btn_xz"   onclick="downloadFile('${idd}')"></button>
	  			 		</c:if>	
	  			 	</c:if>
	            </td>
	  			<c:forEach items="${tags2}" var="tag" varStatus="state">
	  				<c:set var="key" value="C${tag.id}"></c:set>
		    		<td align="center">${item[key]}</td>
		    	</c:forEach>
			</tr>
    	</c:forEach>
    	</tbody>
    </table>
</div>
</form>
</div>
<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
<OBJECT ID="TrueWaySeal" WIDTH=1 HEIGHT=1 CLASSID="CLSID:12AF3A99-8F8E-4D61-AA4E-4B08FEFEA635"></OBJECT>
<div class="wf-list-ft" id="pagingWrap">
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	<%-- window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/model_showDetail.do";//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('form');								//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
	};  --%>
</script>
<script type="text/javascript">
$(document).ready(function (){
	$(".show").css("height",$(window).height()+10);
	$(".list_table").css("height",$(".show").height()-$(".table2").height()-$(".dz").height()-10);
});
	function searchData(){
		document.getElementById('list').submit();
	}
	function searchDataOfZH(){
		var row = $("#conditiontable").find("tr").length;
		document.getElementById('row').value=row;
		var action = document.getElementById('zhform').action;
		document.getElementById('zhform').action=action+"&structId="+document.getElementById('structId').value+"&searchType=zh";
		document.getElementById('zhform').submit();
	}
	var hopen=false;
	function showSearch(){
		if(!hopen){
			document.getElementById("high-search").style.display="block";
			hopen=true;
		}
		else{
			document.getElementById("high-search").style.display="none";
			hopen=false;
		}
	}
	
	function showDetail(id){
		var url = '${ctx}/model_showDetail.do?id='+id+'&structId=${sonStructId}&fatherId=${fatherId}&returnStructId=${structId}&returnId=${id}';
		window.location.href=url; 
		/* var index = layer.open({
		    type: 2,
		    content: url,
		    area: ['300px', '195px'],
		    maxmin: true
		});
		layer.full(index); */
	}
	function returnBack(){
		var url = '${ctx}/model_showDetail.do?id=${returnId}&structId=${returnStructId}&fatherId=${fatherId}';
		window.location.href=url;
	}
	
	function showFile(id){
		var url = '${ctx}/model_getFilePath.do?id='+id+'&structId=${sonStructId}';
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        url:url,
	        success:function(text){
	        	var type = text.split(".")[text.split(".").length-1];
	        	var params="";
	        	if("${zzcdFlag}"=="1"){
	        		params=text+" "+type+" 0";
	        	}else{
	        		params=text+" "+type+" 1";
	        	}
	        	TrueWaySeal.FilePreview(params);
	        }
	    }) 	
	}
	
	function downloadFile(id){
		/* var zzcdFlag = "${zzcdFlag}";
		if(zzcdFlag=="1"){
			alert("无权限下载");
		}else{ */
			document.getElementById("download_iframe").src= '${ctx}/model_downloadFile.do?id='+id+'&structId=${sonStructId}';
		//}
	}
</script>
</html>