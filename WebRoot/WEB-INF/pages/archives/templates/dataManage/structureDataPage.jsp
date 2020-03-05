<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
   
	<style>
	.td{
		font-size: 16px;
	    color: #333333;
	    overflow: hidden;
	    font-family: "Microsoft Yahei";
	    padding: 8px 5px;
	    border-bottom: solid 1px #e4eef5;
	    vertical-align: middle;
	    line-height: 1.5;
	   
	}
	.wf-search-bar {
    font-size: 16px;
    padding: 8px 0px 0px 10px;
    text-align: right;
}
	.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.inp{
			text-align:center;
			width: 100px;
			border: none; 
			display:none;
			background-color: transparent;
		}
		.td.checked span{
			display:none;
		}
	
		.td.checked .inp{
			display:inline;
			border:1px solid #ccc;
		}
		.td.changed{
			background:#ccc;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="dataList"  id="dataList" action="${ctx }/dataManage_toStructureDataPage.do" method="post" style="display:inline-block;width: 100%;">	    
			    <div class="wf-top-tool">
					<a class="wf-btn" onclick="javascript:add();" target="_self">
						<i class="wf-icon-plus-circle" style="display:inline-block;"></i> 添加
					</a>
					<a  class="wf-btn-danger del" onclick="javascript:del();" target="_self">
						<i class="wf-icon-trash" style="display:inline-block;"></i> 删除
					</a>
					<button type="button" class="btn_searzh" onclick="showSearch();"><img src="img/sear_ico.png">综合查询</button>
					<c:if test="${zhcxCondition ne null && zhcxCondition ne ''}">
						<button type="button" class="btn_searzh" onclick="cancleZhcx();"><img src="img/sear_ico.png">还原</button>
					</c:if>
					<c:if test="${structureId eq innerFileStructureId}">
						<button type="button" class="btn_searzh" onclick="openDocumentDialog();"><img src="img/sear_ico.png">附加文件</button>
					</c:if>
					
				 	<div class="fr">
        				<input type="text" id="keyWord" name="keyWord" value="${keyWord}" placeholder="输入关键字查询">
        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
        				</button>
    				</div>
				</div>
				<input type="hidden" id="treeId" name="treeId" value="${treeId}">
				<input type="hidden" id="parentId" name="parentId" value="${parentId}">
				<input type="hidden" id="structureId" name="structureId" value="${structureId}">
				<input type="hidden" id="projectStructureId" name="projectStructureId" value="${projectStructureId}">
				<input type="hidden" id="fileStructureId" name="fileStructureId" value="${fileStructureId}">
				<input type="hidden" id="innerFileStructureId" name="innerFileStructureId" value="${innerFileStructureId}">
				<input type="hidden" id="flag" name="flag" value="${flag}">
				<input type="hidden" id="zhcxCondition" name="zhcxCondition" value="${zhcxCondition}">
				<input type="hidden" id="zhcxConditionSql" name="zhcxConditionSql" value="${zhcxConditionSql}">
				<input type="hidden" id="row" name="row" value="${row}">
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
			<thead>
                   	<th></th>
                   	<th>操作</th>
                  	  <c:forEach items="${etList}" var="tagitem" varStatus="status">
                  	  	<th align="center" >${tagitem.esIdentifier}</th>
                  	  </c:forEach>
    	            
                    <c:forEach items="${list}" var="item" varStatus="status">	
                    	<c:set var="id" value="id"></c:set>
                    	<tr onclick="showdata('${item[id]}','${type}')">
                    		<td align="center">
                    			<input type="checkbox" name="selid"  value="${item[id]}"  >
                    		</td>
                    		<td align="center">
                    			<button type="button" onclick="javascript:update('${item[id]}');" target="_self" class="btn btn-write" style="float:left">
						    		<i class="fa fa-pencil-square-o fa-lg"></i>编辑
						    	</button>
                    		</td>
                    		 <c:forEach items="${etList}" var="tagitem" varStatus="status">
                    		 	<c:set var="key" value="C${tagitem.id}"></c:set>
	                    		 <td align="center">
	                    			${item[key]}
	                    		</td>
                    		</c:forEach>
                    	</tr>
                    </c:forEach>
                 </thead>
		</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/dataManage_toStructureDataPage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('dataList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};

	function showdata(id,type){
		if(type=="2"){
			parent.document.getElementById("frame2").src='${ctx}/dataManage_toStructureDataPage.do?treeId=${treeId}&structureId=${fileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}&parentId='+id;
			parent.document.getElementById("frame1").src='${ctx}/dataManage_toStructureDataPage.do?treeId=${treeId}&structureId=${innerFileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}';
		}else if(type=="1"){
			parent.document.getElementById("frame1").src='${ctx}/dataManage_toStructureDataPage.do?treeId=${treeId}&structureId=${innerFileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}&parentId='+id;
		}
	}
	$(document).ready(function (){	
		$(".wf-list-wrap").height($(window).height()-68);
	});
	//输入值校验
	function inValueTest(data) {
		var flag = false;
		if(data.indexOf(",") != -1) {
			flag = true;
		} else if(data.indexOf(";") != -1) {
			flag = true;
		} else if(data.indexOf("'") != -1) {
			flag = true;
		}
		return flag;
	}
		
	function add(){
		if("${flag}"!="all"&&("${parentId}"=="")){
			top.layer.msg("请选择父结构再添加子结构数据！");
			return;
		}
		
		var title ="";
		if("${type}"=="2"){
			title="项目";
		}else if("${type}"=="1"){
			title="案卷(盒)";
		}else if("${type}"=="0"){
			title="卷内(件)";
		}
		top.layer.open({
			title:'添加数据【'+title+'】',
			type:2,
			area:['80%','80%'],
			content:"${ctx}/dataManage_toAddStructureDataPage.do?treeId=${treeId}&structureId=${structureId}&parentId=${parentId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}",
			success: function(layero, index){
            	 $(layero).find("iframe").contents().find("#save").on("click",function(){
            		 $.ajax({
            			  async:false,//是否异步
            	          type: "POST",//方法类型
            	          dataType: "text",//预期服务器返回的数据类型
            	          url: "${ctx}/dataManage_saveStructureData.do" ,//url
            	          data:  $(layero).find("iframe").contents().find("#dataForm").serialize(),
            	          success: function (result) {
            	        	  if(result=="success"){
            	        		  top.layer.msg("保存成功");
            	        		  window.location.reload();
                      	          top.layer.close(index);
            	        	  }else{
            	        		  top.layer.msg("保存失败");
            	        	  }
            	          },
            	          error : function() {
            	        	  top.layer.msg("异常");
            	          }
            	      });	
            	    });
			}
		});
	}
	
	function update(id){
		var title ="";
		if("${type}"=="2"){
			title="项目";
		}else if("${type}"=="1"){
			title="案卷(盒)";
		}else if("${type}"=="0"){
			title="卷内(件)";
		}
		top.layer.open({
			title:'编辑数据【'+title+'】',
			type:2,
			area:['80%','80%'],
			content:"${ctx}/dataManage_toEditStructureDataPage.do?id="+id+"&treeId=${treeId}&structureId=${structureId}&parentId=${parentId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}",
			success: function(layero, index){
            	 $(layero).find("iframe").contents().find("#save").on("click",function(){
            		 $.ajax({
            			  async:false,//是否异步
            	          type: "POST",//方法类型
            	          dataType: "text",//预期服务器返回的数据类型
            	          url: "${ctx}/dataManage_saveStructureData.do" ,//url
            	          data:  $(layero).find("iframe").contents().find("#dataForm").serialize(),
            	          success: function (result) {
            	        	  if(result=="success"){
            	        		  top.layer.msg("修改成功");
            	        		  window.location.reload();
                      	          top.layer.close(index);
            	        	  }else{
            	        		  top.layer.msg("修改失败");
            	        	  }
            	          },
            	          error : function() {
            	        	  top.layer.msg("异常");
            	          }
            	      });	
            	    });
			}
		});
	}
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var count=0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=="selid" && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  count++;
		   }
		}
		if(ids==""){
			top.layer.alert("请选择要删除的数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		var flag = "0";
		if(confirm("是否只删除已挂接的电子文件条目")){
			flag="1";
		}
		 if(confirm("是否删除这"+count+"条数据？")){
				$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"ids":ids,"structureId":"${structureId}","treeId":"${treeId}","flag":flag},
			        url:"${ctx}/dataManage_deleteStructureData.do",
			        success:function(result){
			        	if(result=="success"){
			        		window.location.reload();
			        	}else{
			        		layer.msg("删除失败");
			        	}
			        }
			    });
		 }
		
	}

	function showSearch(){
		var title ="";
		if("${type}"=="2"){
			title="项目";
		}else if("${type}"=="1"){
			title="案卷(盒)";
		}else if("${type}"=="0"){
			title="卷内(件)";
		}
		top.layer.open({
			title:'综合查询【'+title+'】',
			type:2,
			area:['50%','40%'],
			content:"${ctx}/dataManage_toShowZhcxPage.do?treeId=${treeId}&structureId=${structureId}&zhcxCondition=${zhcxCondition}&row=${row}",
			btn: ['确定', '取消检索'],
            yes: function(index, layero){
            	$(layero).find("iframe")[0].contentWindow.checkZhcxCondition();
            	var zhcxCondition = $(layero).find("iframe")[0].contentWindow.document.getElementById("zhcxCondition").value;
            	var zhcxConditionSql = $(layero).find("iframe")[0].contentWindow.document.getElementById("zhcxConditionSql").value;
            	var row = $(layero).find("iframe")[0].contentWindow.document.getElementById("row").value;
            	if(zhcxCondition!=""&&zhcxConditionSql!=""){
            		document.getElementById("zhcxCondition").value=zhcxCondition;
            		document.getElementById("zhcxConditionSql").value=zhcxConditionSql;
            		document.getElementById("row").value=row;
            		document.getElementById('dataList').submit();
            		top.layer.close(index);
            	}
            },
            btn2: function(index, layero){
            	document.getElementById("zhcxCondition").value="";
        		document.getElementById("zhcxConditionSql").value="";
        		document.getElementById("row").value="";
        		document.getElementById('dataList').submit();
            },
		});
	}
	
	function cancleZhcx(){
		document.getElementById("zhcxCondition").value="";
		document.getElementById("zhcxConditionSql").value="";
		document.getElementById("row").value="";
		document.getElementById('dataList').submit();
	}
	function openDocumentDialog(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var count=0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=="selid" && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  count++;
		   }
		}
		if(ids==""||count>1){
			top.layer.msg("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		var url = getHttpServerUrl()+'/dataManage_openDocumentDialog.do?structureId=${structureId}&pid='+ids;
		top.layer.open({
			title:'附加电子文件',
			type:2,
			area:['40%','60%'],
			content:url
		});
	}
	
	</script>
	<script>
	(function ($) {
	 $.extend({
	  Request: function (m) {
	   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
	   return sValue ? sValue[1] : sValue;
	  },
	  UrlUpdateParams: function (url, name, value) {
	   var r = url;
	   if (r != null && r != 'undefined' && r != "") {
	    value = encodeURIComponent(value);
	    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
	    var tmp = name + "=" + value;
	    if (url.match(reg) != null) {
	     r = url.replace(eval(reg), tmp);
	    }
	    else {
	     if (url.match("[\?]")) {
	      r = url + "&" + tmp;
	     } else {
	      r = url + "?" + tmp;
	     }
	    }
	   }
	   return r;
	  }
	 
	 });
	})(jQuery);
	</script>
</html>