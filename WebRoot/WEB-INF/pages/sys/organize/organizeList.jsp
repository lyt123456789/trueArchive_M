<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<script type="text/javascript" src="${ctx }/widgets/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx }/widgets/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<link rel="stylesheet" href="${ctx }/widgets/ztree/css/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/widgets/ztree/css/demo.css" type="text/css">

</head>
<body>
<div class="tw-layout">
	<div class="tw-container tm-container">
    	<div class="tm-layout-left">
			<div class="tw-list-top">
					<form name="doItemList"  id="doItemList" action="${ctx }/item_getItemList.do" method="post">
					<input type="hidden" name="no" id="no" value="${no}">
					<input type="hidden" name="ids" id="ids" value="${ids}">

		            </form>
			</div>
			<div class="tw-container">
		    	<ul id="treeDemo" class="ztree" style="margin-top:0;"></ul>
		    </div>
		</div>


		<div class="tm-layout-right">
			<div class="tw-container" style="padding:0;" layoutH="40">
				<iframe id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" 
					src = ""
				style="width:98%; height:98%;border-width:0px 0px 0px 0px;border-style:solid;border-color:#B8D0D6;" 
				scrolling="no"></iframe>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
var setting = {
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback:{
			onClick:getTyxmList
		}
	};

	var code;

	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		py = $("#py").attr("checked")? "p":"",
		sy = $("#sy").attr("checked")? "s":"",
		pn = $("#pn").attr("checked")? "p":"",
		sn = $("#sn").attr("checked")? "s":"",
		type =  { "Y" : "ps", "N" : "ps" };
		zTree.setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
	}
	function showCode(str) {
		if (!code) code = $("#code");
		code.empty();
		code.append("<li>"+str+"</li>");
	}
		
	$(document).ready(function(){
		var name = $('#name_organize_list').val();
		var url = "${ctx}/organize_getDeptTreeJson.do?zzjgName="+encodeURI(name)+"&t="+(new Date()).getTime();
		$.ajax({
			"url":url,
			cache : false,
			async: false,
			dataType:'json',
			data:{'chked':parent.zgbmchked},
			success:function(json){
				$.fn.zTree.init($("#treeDemo"), setting, json);
				$("#treeDemo").find("li:first").find("a:first").click();
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("错误信息："+errorThrown+"("+textStatus+")!");
			}
		});
		$('#treeDemo_1_switch').trigger("click");
		setCheck();
		$("#py").bind("change", setCheck);
		$("#sy").bind("change", setCheck);
		$("#pn").bind("change", setCheck);
		$("#sn").bind("change", setCheck);
	});

	
	function getTyxmList(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes=treeObj.getSelectedNodes();
		name=nodes[0].id;
		//获取项目类别代码
		var xmlxdm=name.split(" ")[0];
		initPersonList(xmlxdm);
	}


	
	function initPersonList(id){
		document.getElementById("frame1").src="${ctx}/organize_getEmployeeList.do?depId="+id;
	}
</script>
</html>