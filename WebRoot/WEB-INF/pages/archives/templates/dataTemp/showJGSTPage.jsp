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
    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/font/dtreefont.css">
   <style type="text/css">
	  
   </style>

</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/dataTemp_toShowJGSTPage.do?treeId=${treeId}&structureId=${structureId}" method="post" style="display:inline-block;width: 100%;">
				<input type="hidden" id="treeId" name="treeId" value="${treeId}"/>
				<input type="hidden" id="structureId" name="structureId" value="${structureId}"/>
				
				结构id：<input type="text" id="id" name="id" value="${structure.id}"/>
				标志：<input type="text" id="esIdentifire" name="esIdentifire" value="${structure.esIdentifire}"/>
				标题：<input type="text" id="esTitle" name="esTitle" value="${structure.esTitle}"/>
				类型：<input type="text" id="esType" name="esType" value="${structure.esType}"/>
				<br>
				创建者：<input type="text" id="esCreator" name="esCreator" value="${structure.esCreator}"/>
				日期：<input type="text" id="esDate" name="esDate" value="${structure.esDate}"/>
				发布者：<input type="text" id="esPublisher" name="esPublisher" value="${structure.esPublisher}"/>
				描述：<input type="text" id="esDescription" name="esDescription" value="${structure.esDescription}"/>
				<br>
				状态：<input type="text" id="esStatus" name="esStatus" value="${structure.esStatus}"/>
				<input type="button" onclick="saveStructure()" value="保存" />
			</form>
		</div>
	</div>
	
	<div style="height: 500px;">
		<iframe id="iframe" name="iframe" width="100%" height="100%" src="${ctx}/dataTemp_toShowJGSTPage_field.do?treeId=${treeId}&structureId=${structureId}"></iframe>
	</div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	function saveStructure(){
		$.ajax({
            url:'${ctx}/dataTemp_saveStructure.do',
            async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
            data:$('#lendLingList').serialize(),
            success:function(data){
                if(data == 'success'){
                	window.location.reload();
                }else{
                    layer.msg("保存失败")
                }
            },
            error:function () {
                layer.msg("添加失败")
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