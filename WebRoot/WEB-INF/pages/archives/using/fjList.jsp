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
	.doLeft {
		margin-left:15px;
	}
    </style>
</head>
<body style="overflow: auto;">
<div class="search">
	<form name="list"  id="list" action="${ctx }/model_metaDataList.do?" method="post" style="display:inline-block;">
		    <input type="hidden" id="formbh" name="formbh" value="${formbh}">
		    <div class="wf-top-tool doLeft">			
					<a class="wf-btn-primary" onclick="javascript:downLoad();" target="_self">
						<i class="wf-icon-pencil" style="display:inline-block;"></i> 下载
					</a>
					<a class="wf-btn-danger" onclick="javascript:deleteFj();" target="_self">
						<i class="wf-icon-trash" style="display:inline-block;"></i> 删除
					</a>
					<a class="wf-btn"  id="addnum"  href="javascript:scanFj()">
						<i class="wf-icon-plus-circle" style="display:inline-block;"></i> 扫描
					</a>
				</div>
	</form>
</div>
<div class="clear"></div>
<div class="table">
    <table cellspacing="0" cellpadding="0">
        <tr class="fr_tr">
            <td width="10%"><input type="checkbox" onclick="chooseAll(this)"/></td>
            <td width="30%">利用单标号</td>
            <td width="60%">原文路径</td>
        </tr>
        <c:forEach items="${list}" var="item" varStatus="state">
        	<tr>
	  			<td align="center"><input type="checkbox" name="checkbox" value="${item[0]}"/>
	  			<input type="hidden" id="${item[0]}" name="${item[0]}" value="${item[2]}">
	  			</td>	
	  			<td>${item[1]}</td>
	            <td>${item[2]}</td>
			</tr>
    	</c:forEach>
    </table>
</div>
<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
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
	
<%-- 	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/model_metaDataList.do";//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('list');								//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}; --%>
</script>
<script type="text/javascript">
function downLoad(){
	var objs = document.getElementsByName('checkbox');
	var ids = '';
	var n = 0;
	for(var i=0; i<objs.length; i++) {
	   if(objs[i].checked==true ){
		  ids += ""+objs[i].value+",";
		  n ++;
	   }
	}
	if(ids==""){
		alert("请选择一条数据");
		return;
	}else if(n != 1&& n!=0){
		alert("请选择一条数据");
		return;
	}
	ids = ids.substring(0, ids.length-1);
	document.getElementById("download_iframe").src='${ctx}/using_download.do?id='+ids;
	/* $.ajax({
        async:true,//是否异步
        type:"POST",//请求类型post\get
        cache:false,//是否使用缓存
        dataType:"text",//返回值类型
        data:{"ids":ids},
        url:"${ctx}/using_download.do?id="+ids,
        success:function(text){
        	 //window.location.reload();
        }
    }); */
}
function scanFj(){
	var url = '${ctx}/using_toScanJsp.do?formbh=${formbh}';
	layer.open({
		title:"扫描附件",
	    type: 2,
	    content: url,
	    area: ['95%', '95%'],
	    cancel: function(){
            //右上角关闭回调
       	 window.location.reload();
       }
	});
	//parent.layer.full(index);
}

function deleteFj(){
	var objs = document.getElementsByName('checkbox');
	var ids = '';
	for(var i=0; i<objs.length; i++) {
	   if(objs[i].checked==true ){
		  ids += "'"+objs[i].value+"',";
	   }
	}
	if(ids==""){
		alert("请选择一条数据");
		return;
	}
	ids = ids.substring(0, ids.length-1);
	$.ajax({
        async:true,//是否异步
        type:"POST",//请求类型post\get
        cache:false,//是否使用缓存
        dataType:"text",//返回值类型
        data:{"ids":ids},
        url:"${ctx}/using_deleteJydFj.do?id="+ids,
        success:function(text){
        	 window.location.reload();
        }
    });
}
function chooseAll(_this){
	var checkboxs = document.getElementsByName("checkbox");
	for(var i=0;i<checkboxs.length;i++){
		if(_this.checked){
			checkboxs[i].checked=true;
		}else{
			checkboxs[i].checked=false;
		}
	}
}
</script>
</html>