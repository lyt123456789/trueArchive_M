<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>列表</title>
    <link rel="stylesheet" href="css/list.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style type="text/css">
    #high-search {
	    background: url("../img/high_search.png") no-repeat;
	    width: 1000px;
	    height: 250px;
	    position: absolute;
	    right: 10%;
	    z-index: 2000;
	    top: 50%;
	    display: none;
	    text-align: center;
	    background-color: white; 
	}
    </style>
</head>
<body style="overflow: auto;">
<div class="search">
	<form name="list"  id="list" action="${ctx }/model_metaDataList.do?" method="post" style="display:inline-block;">
		    <span>选择命名空间：</span>
		    <select id="nameSpaceid" name="nameSpaceid" onchange="searchData()">
		    	<option value="">--请选择--</option>
		    	<c:forEach var="m" items='${nameSpacelist}'>
					<option value="${m[0]},${m[2]}" <c:if test="${nameSpaceId ==m[0]}">selected="selected"</c:if>>${m[7]}</option>
				</c:forEach> 
			</select>
			<input type="hidden" id="meta" name="meta">
			<input type="hidden" id="nameSpace" name="nameSpace">
			<input type="hidden" id="nameSpaceId" name="nameSpaceId" value="${nameSpaceId}">
	</form>
</div>
<div class="clear"></div>
<div class="table">
    <table cellspacing="0" cellpadding="0">
        <tr class="fr_tr">
        	<td width="10%">序号</td>
            <!-- //<td><input type="checkbox" /></td> -->
            <td width="30%">名称</td>
            <td width="20%">唯一标识</td>
            <td width="40%">描述</td>
        </tr>
        <c:forEach items="${metaDatalist}" var="item" varStatus="state">
        	<tr>
	  			<td align="center">${(selectIndex-1)*pageSize+state.count }
	  			&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="checkbox" value="${item[0]}"/></td>	
	  			<!-- <td></td> -->
	  			<td>${item[1]}</td>
	            <td>${item[2]}</td>
	            <td>${item[3]}</td>
			</tr>
    	</c:forEach>
    </table>
</div>
<div class="wf-list-ft" id="pagingWrap">
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">

	var callbackdata = function () {
			debugger
			var name = '';
			var val = '';
			 $('input[name="checkbox"]:checked').each(function(){
		    	val=$(this).val();
		    	name = $(this).parent().next().next().text();
		    });
		    var data = {
		    		name: name,
		        	val:val
		    };
		    return data;
		}
	
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/model_metaDataList.do";//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('list');								//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
</script>
<script type="text/javascript">

	function searchData(){
		var ss = document.getElementById('nameSpaceid').value.split(",");
		document.getElementById('nameSpaceId').value=ss[0];
		document.getElementById('nameSpace').value=ss[1];
		document.getElementById('list').submit();
	}
</script>
</html>