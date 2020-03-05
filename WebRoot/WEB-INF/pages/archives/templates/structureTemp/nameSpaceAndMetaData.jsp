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
			width:100%;
			position:absolute;
			top:0px;
			left:0px;
			border-bottom: solid 1.5px #e4eef5;
		}
		.bottomRight {
			height:60%;
			width:100%;
			position:absolute;
			top:40%;
			left:0px;
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
	<input type="hidden" name="strId" id="modelId" value="${id }"/>
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
			<!-- <button onclick="getMetaDataVal()" type="button" class="blueBut">选中</button> -->
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
		iframeNames = spaceName + ":" + iframeNames;
		var json = {};
		json['iframeNames']=iframeNames;
		json['iframeIds']=iframeIds;
		return json;
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