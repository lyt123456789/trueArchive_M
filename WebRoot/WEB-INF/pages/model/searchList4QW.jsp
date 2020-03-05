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
	lable{
		color: red;
	    font-weight: bold;
	}
    </style>
</head>
<body style="overflow: auto;">
<div class="search">
	<form name="list" onsubmit="return false;" id="list" action="${ctx }/model_toModelTask4QW.do" method="post" style="display:inline-block;">
        	<input type="hidden" id="zzcdFlag" name="zzcdFlag" value="${zzcdFlag}"/><!-- 判断是否是临时用户 -->
        	<input type="hidden" id="treeNodeIds" name="treeNodeIds" value="${treeNodeIds}"/>
        	<input type="hidden" id="jydId" name="jydId" value="${jydId}"/><!-- 判断是否从借阅单进来的 -->
        	<input type="hidden" id="djdId" name="djdId" value="${djdId}"/><!-- 判断是否从调卷单进来的 -->
		   
		    <input type="text"  id="keyWord" name="keyWord" value="${keyWord}">
		     <input type="hidden"  id="keyWordOld" name="keyWordOld" value="${keyWordOld}">
		    <button type="button" class="btn_seargjc" onclick="searchData('1');"><img src="img/sear_ico.png">检索</button>
		    <!-- <button type="button" class="btn_searzh" onclick="searchData('2');"><img src="img/sear_ico.png">二次检索</button> -->
		     <c:if test="${!empty jydId}">
		    	<button type="button" class="btn_addpackage" onclick="addToJYK();"><img src="img/addpackage.png">加入借阅库</button>
		      	<button type="button" class="btn_formdetail" onclick="showJYD();"><img src="img/form_detail.png">借阅单明细</button>
		    </c:if>	 
		    <c:if test="${!empty djdId}">
		    	<button type="button" class="btn_addpackage" onclick="addToDJK();"><img src="img/addpackage.png">加入调卷库</button>
		   		<button type="button" class="btn_formdetail" onclick="showDJD();"><img src="img/form_detail.png">调卷单明细</button>
		    </c:if>	  
	</form>
</div>
<div class="clear"></div>
<div class="table">
    <table cellspacing="0" cellpadding="0">
        <c:forEach items="${returnList}" var="item" varStatus="state">
        	<tr>
	  			 <td style="text-align: left;padding-left: 5px;">
	  			 &nbsp;<div class="checkbox"><input type="checkbox" name="checkbox" value="${item[0]}@${item[1]}"/>
	  			 <a href="javascript:void(0);" style="color: #1157E8;" onclick="showDetail('${item[0]}','${item[1]}','${item[2]}')">${item[3]}</a></div></td>
			</tr>
			<tr><td style="text-align: left;text-indent: 2em;">${item[4]}</td></tr>
    	</c:forEach>
    </table>
</div>
<div class="wf-list-ft" id="pagingWrap">
</div>
</body>
<%@ include file="/common/widgets2.jsp"%>
<script type="text/javascript">
	 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/model_toModelTask4QW.do?treeNodeIds=${treeNodeIds}";//提交的url,必须修改!!!*******************
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
	function searchData(type){
		var treeNodeIds = parent.document.getElementById("treeNodeIds").value;
		var keyWord = document.getElementById("keyWord").value;
		var keyWordOld = document.getElementById("keyWordOld").value;
		if(treeNodeIds==""){
			alert("请选择左侧目录");
			return;
		}
		if(keyWord==""){
			alert("请输入检索词");
			return;
		}
		document.getElementById("treeNodeIds").value=treeNodeIds;
		document.getElementById('list').submit();
	}
	
	function showDetail(id,structId,fatherId){
		var url = '${ctx}/model_showDetail.do?id='+id+'&structId='+structId+'&fatherId='+fatherId;
		var index = layer.open({
		    type: 2,
		    content: url,
		    area: ['300px', '195px']
		});
		layer.full(index);
	}
	
	function addToJYK(){
		var jydId = parent.document.getElementById('jydId').value;
		if(jydId==""){
			alert("请选择借阅单！");
			return;
		}
		var ids = "";
		var choose = document.getElementsByName("checkbox");
		for(var i=0;i<choose.length;i++){
			if(choose[i].checked){
				ids+=choose[i].value+",";
			}
		}
		if(ids!=""){
			ids=ids.substring(0, ids.length-1);
		}
		if(ids==""){
			alert("请选择数据！");
			return;
		}
		
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{
	        	"jydId":jydId,
	        	"ids":ids,
	        	"fatherId":"${fatherId}",
	        	"structId":"${structId}",
	        	"type":"3"
	        },
	        url:"${ctx}/model_addToJYK.do",
	        success:function(text){
	        	 alert("加入成功！");
	        }
	    });
		
	}
	function addToDJK(){
		var djdId = parent.document.getElementById('djdId').value;
		if(djdId==""){
			alert("请选择调卷单！");
			return;
		}
		var ids = "";
		var choose = document.getElementsByName("checkbox");
		for(var i=0;i<choose.length;i++){
			if(choose[i].checked){
				ids+=choose[i].value+",";
			}
		}
		if(ids!=""){
			ids=ids.substring(0, ids.length-1);
		}
		if(ids==""){
			alert("请选择数据！");
			return;
		}
		//先检验是否能够调阅
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"djdId":djdId,"ids":ids,"structId":"${structId}","type":"3"},
	        url:"${ctx}/model_checkDJK.do",
	        success:function(text){
	        	if(text=="false"){
	        		alert("校验失败，请联系管理员！");	
	        	}else if(text==""){
	        		$.ajax({
	        	        async:true,//是否异步
	        	        type:"POST",//请求类型post\get
	        	        cache:false,//是否使用缓存
	        	        dataType:"text",//返回值类型
	        	        data:{
	        	        	"djdId":djdId,
	        	        	"ids":ids,
	        	        	"fatherId":"${fatherId}",
	        	        	"structId":"${structId}",
	        	        	"type":"3"
	        	        },
	        	        url:"${ctx}/model_addToDJK.do",
	        	        success:function(text){
	        	        	if(text=="success"){
	        	        		 alert("加入成功！");
	        	        	}else{
	        	        		 alert("加入失败！");
	        	        	}	
	        	        }
	        	    });
	        	}else if(text!=""){
	        		alert(text+",已在调卷中，无法调卷！");	
	        	}	
	        }
	    });
		
	}
	function showJYD(){
		var url = '${ctx}/using_showUsingForm.do?vc_table=1&type=&id=${jydId}&zzcdFlag=${zzcdFlag}';
		var index = parent.layer.open({
			title:"浏览数据",
		    type: 2,
		    content: url,
		    area: ['100%', '100%']
		});
	}
	function showDJD(){
		parent.layer.open({
            type: 2,
            title: "调卷单明细",
            shadeClose: true,
            shade: 0.4,
            area: ['100%', '100%'],
            content: "${ctx}/using_showTransferForm.do?id=${djdId}&statusSe=${statusSe}"
        });
	}
</script>
</html>