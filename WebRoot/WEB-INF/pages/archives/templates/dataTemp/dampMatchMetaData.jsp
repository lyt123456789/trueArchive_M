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
		.leftDiv {
			height:100%;
			width:25%;
			overflow-y:auto;
			border-right: solid 1.5px #e4eef5;
		}
		.topRight {
			height:40%;
			width:75%;
			position:absolute;
			top:0px;
			left:25%;
			border-bottom: solid 1.5px #e4eef5;
		}
		.bottomRight {
			height:60%;
			width:75%;
			position:absolute;
			top:40%;
			left:25%;
		}
		.fontTitle {
			padding-left: 10px;
		    font-size: 20px;
		    font-weight: 900;
		    background: #f8f8f8;
		}
		.td{
			font-size: 16px;
		    color: #333333;
		    overflow: hidden;
		    font-family: "Microsoft Yahei";
		    padding: 8px 5px;
		    border-bottom: solid 1px #e4eef5;
		    vertical-align: middle;
		    line-height: 1;
		}
		.wf-search-bar{
			padding: 0px 10px 5px 1px;
		}
		.new-htable {
			margin-top:20px;
			width:96%;
			margin-left:3%;
		}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
			text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;
		}
		.new-htable .tw-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:115px!important;
			min-width: 100px!important;
			max-width: 120px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		.w-auto-10 {
			width: 9% !important;
			min-width: 9% !important;
		}
		.wf-form-label{
			margin-left: 0px;
		}
		
		.auto-date-width{
			width:120px!important;
		}
		.high-search-btn{
			font-size:14px;
			color:#4284ce;
			margin-left:10px;
		}
		#high-search{
			top:0;
		}
		.search {
			height:35px;
			line-height:35px;
		}
		.fts {
			font-size:14px;
		} 
		.btn-meta-data {
		    color: rgb(255, 255, 255);
		    background: #fbc756;
		    border-width: 1px;
		    border-style: solid;
		    border-color:#fbc756;
		    border-image: initial;
		}
		.ifraClass {
			display:none;
			width:100%;
			height:100%;
		}
		.blueBut {
   			float: right;
		    width: 76px;
		    height: 32px;
		    color: #fff;
		    background: #68c1e4;
		    border-radius: 5px;
		    border: solid 1px #68c1e4;
		    font-size: 16px;
		    font-weight: 900;
		    margin-right: 20px;
   		}
	</style>
</head>
<body>
	<div class="leftDiv">
		<div class="fontTitle">字段列表</div>
		<div class="wf-layout">
			<div class="wf-list-top">
				<div class="wf-search-bar">
					<form name="nodeList"  id="nodeList" method="post" style="display:inline-block;width: 100%;">
					    <input type="hidden" id="structureId" name="structureId" value="${structureId}"/>
					    <div class="wf-top-tool">
							<a class="wf-btn-primary del" onclick="saveAllMetaData()" target="_self">
								<i class="wf-icon-pencil" style="display:inline-block;"></i> 保存
							</a>
							<a class="wf-btn-primary del" onclick="changeForStart();" target="_self">
								<i class="wf-icon-pencil" style="display:inline-block;"></i> 还原
							</a>
							<a class="wf-btn-primary del" onclick="autoChangeFor();" target="_self">
								<i class="wf-icon-pencil" style="display:inline-block;"></i> 自动对应
							</a>
						</div>
					</form>
				</div>
			</div>
			<div class="wf-list-wrap">
				<div class="loading"></div>
				<table class="wf-fixtable" id="dataTable" layoutH="140" style="width:100%!important;">
					<thead>
						<tr>
			    	  		<th width="4%"></th>
		                    <th align="center" width="32%">字段名称</th>
		                    <th align="center" width="32%">字段类型</th>
		                    <th align="center" width="32%">元数据</th>
		                </tr>
	                 </thead>
	                 <c:forEach items="${list}" var="item" varStatus="status">
                    	<tr>
                    		<td align="center"><input type="checkbox" name="id" value="${item.id}"/></td>
                    		<td align="center" title ="${item.esIdentifier}">${item.esIdentifier}</td>
                    		<td align="center" title ="${item.esType}">${item.esType}</td>
                    		<td align="center" axis="0" id="${item.id}" title="${item.idMetadata }">${item.nameMetadata}</td>
                    	</tr>
                    </c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="topRight">
		<div class="fontTitle">命名空间</div>
		<div class="wf-layout">
			<div class="wf-list-wrap">
				<div class="loading"></div>
				<table class="wf-fixtable" id="nameSpaceTable" layoutH="140" style="width:100%!important;">
					<thead>
						<tr>
			    	  		<th width="2%"></th>
			    	  		<th align="center" width="14%">作者</th>
			    	  		<th align="center" width="14%">名称</th>
			    	  		<th align="center" width="24%">描述文件位置</th>
			    	  		<th align="center" width="14%">描述</th>
			    	  		<th align="center" width="12%">编号</th>
			    	  		<th align="center" width="12%">日期</th>
			    	  		<th align="center" width="8%">版本</th>
		                </tr>
			    	</thead>
			    	<c:forEach items="${nameSpaceList}" var="data" varStatus="status">
			    		<tr>
			    			<td align="center" >
								<input type="checkbox" data_name="${data.esIdentifier}" onclick="checkBoxClick(this)" name="nameSpaceId" id="${data.id}"  value="${data.id}"  >
							</td>
							<td align="center"  title ="${data.esCreator}" >${data.esCreator }</td>
							<td align="center"  title ="${data.esTitle}" >${data.esTitle}</td>
							<td align="center"  title ="${data.esUrl}" >${data.esUrl}</td>
							<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
							<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
							<td align="center"  title ="${data.esDate}" >${data.esDate}</td>
							<td align="center"  title ="${data.esVersion}" >${data.esVersion}</td>
			    		</tr>
				    </c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="bottomRight">
		<div class="fontTitle">
			<span>元数据</span>
			<button onclick="getMetaDataVal()" type="button" class="blueBut">选中</button>
		</div>
		<iframe class="ifraClass" id="metaDataframe" name="metaDataframe" src="${ctx}/met_toMetaDataPage.do" frameborder="0">come here</iframe>
	</div>
</body>
<script type="text/javascript">
	var spaceName = null;
	function checkBoxClick(dom) {
		var checked = $(dom).is(':checked');
		if(checked) {
			$("input[name='nameSpaceId']").prop("checked", false);
			$(dom).prop("checked", true);
			var nameSpaceId = $(dom).val();
			spaceName = $(dom).attr("data_name");
			$("#metaDataframe").attr("src","${ctx}/met_toMetaDataPage.do?tempFlag=1&nameSpaceId="+nameSpaceId);
			$("#metaDataframe").css("display","inline");
		}
	}
	
	function getMetaDataVal() {
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='id' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n++;
		   }
		}
		if(ids==""){
			layer.msg("请选择一条字段列表数据");
			return;
		} else if(n != 1&& n!=0){
			layer.msg("只能选择一条字段列表数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(isEmpty(spaceName)) {
			layer.msg("请选择具体命名空间");
			return;
		}
		//获取iframe中CheckBox值
		var iframeObjs =  $(window.frames["metaDataframe"].document).find("input[name='id']");
		var iframeIds = '';
		var iframeNames = '';
		n = 0;
		for(var i=0; i<iframeObjs.length; i++) {
		   if(iframeObjs[i].type.toLowerCase() == 'checkbox' && iframeObjs[i].name=='id' && iframeObjs[i].checked==true ){
			   iframeIds += iframeObjs[i].value+",";
			   iframeNames += $(iframeObjs[i]).attr("data_name")+",";
			  	n++;
		   }
		}
		if(iframeIds==""){
			layer.msg("请选择一条元数据信息");
			return;
		} else if(n != 1&& n!=0){
			layer.msg("只能选择一条元数据信息");
			return;
		}
		iframeIds = iframeIds.substring(0, iframeIds.length-1);
		iframeNames = iframeNames.substring(0, iframeNames.length-1);
		var metaFullTags = spaceName + ":" + iframeNames;
		var json = getAllTableData();
		var metaArr = json.metaArr;
		if(metaArr.indexOf(metaFullTags)>-1) {
			layer.msg("本字段列表中已存在，相同命名空间、相同元数据匹配的字段");
			return;
		}
		$("#"+ids).empty();
		$("#"+ids).append("<div style='background:#aee6df'>"+ spaceName + ":" + iframeNames +"</div>");
		$("#"+ids).attr("title",iframeIds);
		$("#"+ids).attr("axis","1");
	}
	
	/**
	* 获取所有匹配信息
	* ps:注意，注意
	* 此处因为表格的重载，采用last选择器，获取最后一个表格的数据
	* 如有修改，请仔细核对此处！！！！！
	**/
	function getAllTableData() {
		var json = {};
		var jsonArr = [];
		var metaArr = [];
		var table = $(".leftDiv").find('table:last');
		var trList = $(table).find("tr");
		for(var i = 0; i < trList.length; i++) {
			var tdArr = trList.eq(i).find("td");
			var tdLast = tdArr.eq(3);
			var oneJson = {};
			var tagId = $(tdLast).attr("id");
			var metaId = $(tdLast).attr("title");
			var metaVal = $(tdLast).find("div").text();
			metaArr.push(metaVal);
			var changeFlag = $(tdLast).attr("axis");
			if(changeFlag == "0" || changeFlag == 0) {
				continue;
			}
			oneJson['id']=tagId;
			oneJson['id_MetaData']=metaId;
			oneJson['metaDataFullName']=metaVal;
			jsonArr.push(oneJson);
		}
		json['jsonArr']=jsonArr;
		json['metaArr']=metaArr;
		return json;
	}
	
	//保存
	function saveAllMetaData() {
		var json = getAllTableData();
		var jsonArr = json.jsonArr;
		$.ajax({
			url:"${ctx}/dataTemp_saveMetaData.do",
			type:"post",
			async:false,
			cache: false,
			dataType:"json",
			data:{"jsonArr":JSON.stringify(jsonArr)},
			success:function(data){
				console.log(data);
				var flag = data.flag;
				if(flag == "success") {
					layer.msg("保存成功");
					window.location.reload();
				} else {
					layer.msg("保存失败");
				}
			},
			error:function(){
				layer.msg("系统错误请重试");
			}	
		}); 
	}
	
	//改为原样
	function changeForStart() {
		window.location.reload();
	}
	
	//自动对应元数据
	function autoChangeFor() {
		var structureId =  "${structureId}";
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='nameSpaceId' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n++;
		   }
		}
		if(ids==""){
			layer.msg("请选择一条命名空间");
			return;
		} else if(n != 1&& n!=0){
			layer.msg("只能选择一条命名空间");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		
		var obj = {
			structureId:structureId,
			nameSpaceId:ids,
			spaceName:spaceName
		}
		$.ajax({
			url:"${ctx}/dataTemp_autoMetaDataOf.do",
			type:"post",
			async:false,
			cache: false,
			dataType:"json",
			data:obj,
			success:function(data){
				console.log(data);
				var retList = data.retList;
				if(retList.length == 0) {
					layer.msg("找不到字段名相同的字段");
				} else {
					for(var c = 0; c < retList.length; c++) {
						var esmt = retList[c];
						$("#"+esmt.id).empty();
						$("#"+esmt.id).append("<div style='background:#aee6df'>"+ esmt.nameMetadata +"</div>");
						$("#"+esmt.id).attr("title",esmt.idMetadata);
						$("#"+esmt.id).attr("axis","1");
					}
					layer.msg("已匹配"+retList.length+"条数据");
				}
			},
			error:function(){
				layer.msg("系统错误请重试");
			}	
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