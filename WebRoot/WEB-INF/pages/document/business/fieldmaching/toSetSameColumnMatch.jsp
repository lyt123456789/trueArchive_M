<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form id="itemform" method="post" action="${ctx}/item_add.do" style="font-family: 宋体; font-size: 12pt;">
<input type="hidden" name="itemId" id="itemId" value="${itemId}">
<table class="infotan" width="100%">
	<c:forEach items="${map}" var="mymap">
		<tr>
			<td width="20%" class="bgs ls">设定的表与字段：</td>
			<td width="80%">${mymap.key}</td>
		</tr>
		<tr>
			<td width="20%" class="bgs ls">选择的字段：</td>
			<td width="80%">
				<input type="hidden" name="tableIdAndFieldId" id="tableIdAndFieldId" value="${mymap.key}">
				<select style="width: 100px;" id="oldSelect">
					<c:forEach var="item" items="${mymap.value}">
						<option value="${item.tagName}">${item.tagName}</option>
					</c:forEach>
				</select>
			<input type="button" value="√" onclick="addTableAndField()"><br>
			</td>
		</tr>
		<tr>
			<td width="20%" class="bgs ls">组织内容：</td>
			<td width="80%">
				<textarea style="width: 400px; height: 50px;" id="condition" name="condition"></textarea>
			</td>
		</tr>
	</c:forEach>
</table>
<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" href="javascript:checkForm();"><span>提交</span></a></li>
<li><a class="buttonActive" href="javascript:window.history.go(-1);"><span>返回</span></a></li>
</ul>
</div>
</form>
</body>
<script type="text/javascript">
//添加tableAndFeild
function addTableAndField(){
	var sjzd_value = $("#oldSelect option:selected").text(); 
	$("#condition").insertContent(sjzd_value+"");
}


//保存页面值,ajax提交数据
function checkForm(){
	 var content = $("#condition").val();
	 var itemId = $("#itemId").val();
	 var tableIdAndFieldId = $("#tableIdAndFieldId").val();
	
	  $.ajax({
			url:"${ctx}/fieldMatching_saveRecWh.do",
			type:"post",
			async:false,
			cache: false,
			data:{
				'content':content,  'itemId':itemId, 'tableIdAndFieldId':tableIdAndFieldId
			},
			success:function(result){	//保存
				var res = eval("("+result+")");
				if(res.success){
					window.returnValue = "success";
					window.close();
				}else{
					alert("保存失败");
				}
			},	
		error:function(){
			alert("系统错误请重试");
		}	
	});
}

(function($) {
    $.fn.insertContent = function(myValue, t) {
		var $t = $(this)[0];
		if (document.selection) { //ie
			this.focus();
			var sel = document.selection.createRange();
			sel.text = myValue;
			this.focus();
			sel.moveStart('character', -l);
			var wee = sel.text.length;
			if (arguments.length == 2) {
				var l = $t.value.length;
				sel.moveEnd("character", wee + t);
				t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart("character", wee - t - myValue.length);
 
				sel.select();
			}
		} else if ($t.selectionStart || $t.selectionStart == '0') {
			var startPos = $t.selectionStart;
			
			var endPos = $t.selectionEnd;
			var scrollTop = $t.scrollTop;
			$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
			this.focus();
			$t.selectionStart = startPos + myValue.length;
			$t.selectionEnd = startPos + myValue.length;
			$t.scrollTop = scrollTop;
			if (arguments.length == 2) {

				$t.setSelectionRange(startPos - t, $t.selectionEnd + t);
				this.focus();
			}
		}
		else {
			this.value += myValue;
			this.focus();
		}       
    };
})(jQuery);
</script>
<%@ include file="/common/function.jsp"%>
</html>
