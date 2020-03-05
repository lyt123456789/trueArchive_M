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
<body >
<table class="infotan" width="100%">
<tr>
 <td class="bgs tc fb">设置条件属性</td>
</tr>
</table>
<form>
<table>
	<!-- <tr>
		<td width="20%" class="bgs ls">自定义名称：</td>
		<td>
			<input type="text" name="dymc" id="dymc" style="width: 200px;"> </input>
		</td>
	</tr> -->
	<tr>
		<td width="20%" class="bgs ls">数据表：</td>
		<td>
			<select style="width: 200px;" id="sjb" onchange="chooseField();">
				<option value=""></option>
				<c:forEach items="${list}" var="item">
					<option value="${item.id}">${item.vc_tablename}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">数据字段：</td>
		<td>
			<select style="width: 200px;" id="oldSelect">
				
			</select>
			<input type="button" value="√" onclick="addTableAndField()"><br>
		</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">条件内容：</br><font color="red">(补充逻辑)</font></td>
		<td>
			<textarea style="width: 400px; height: 140px;" id="condition" name="condition" >${condition}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">匹配字段关系：</td>
		<td>
			<textarea style="width: 400px; height: 60px;" id="ppzd" name="ppzd" >${rule}</textarea>
		</td>
	</tr>
	<tr style="height: 80px;">
		<td width="20%" class="bgs ls">注意点：</td>
		<td style="height: 70px;">
			自定义字段,不能包含在条件内容中(条件内容只能为java语言的if条件语句)
		</td>
	</tr>
</table>
</form>
<div class="formBar pa" style="bottom:0px;width:100%;">  
<!-- <ul class="mr5"> 
	<li><a id="ok" onclick="sub();" name="CmdView" class="buttonActive" href="javascript:;"><span>保存</span></a></li>
	<li><a id="cancel" onclick="cancel()" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
</ul> -->
</div>
</body>
<script type="text/javascript">
	function chooseField(){
	//根据表获取该表对应的一系列字段
		//获取选中的表id
	var obj_sjb = document.getElementById("sjb");
	var sjb_index = obj_sjb.selectedIndex;
	var vc_sjb = obj_sjb.options[sjb_index].value;
	var oldSelect=document.getElementById('oldSelect');
	oldSelect.options.length = 0;
		//获取该表对应的字段
	$.ajax({  
		url : 'field_getFieldById.do',
		type : 'POST',   
		cache : false,
		async : false,
		data :{
			'id':vc_sjb
		},
		error : function(e) {  
			alert('AJAX调用错误(fieldInfo_getFieldById.do)');
		},
		success : function(msg) {  //遍历字段
			if(msg!=null && msg!=''){
				var msgs = msg.split(";");
				for(var i=0; i<msgs.length; i++){
					var nodeInfo = msgs[i];
					var infos = nodeInfo.split(",");
					oldSelect.options.add(new Option(infos[2],infos[1]));
				}
			}
			}
		});
	}
	//添加tableAndFeild
	function addTableAndField(){
		var sjb_value = $("#sjb option:selected").text(); 
		if(sjb_value==null || sjb_value==''){
			alert("请选择数据表");
			return ;
		}
		//var sjb = $("#sjb option:selected").val(); 
		var sjzd = $("#oldSelect option:selected").val(); 
		if(sjzd==''){
			alert("请选择数据字段");
			return;
		}
		//var condition = document.getElementById("condition");
		var sjzd_value = $("#oldSelect option:selected").text(); 
		$("#condition").insertContent(sjzd_value+" ");
		$("#ppzd").insertContent(sjzd_value+"="+sjzd+"|"+sjb_value+";");
	}
	
	function sub(){
		var condition = $("#condition").val();
		var ppzd = $("#ppzd").val();
		return condition+"^"+ppzd;
		/* window.returnValue=condition+"^"+ppzd;
		window.close(); */
	}
	
	function cancel(){
		window.returnValue="";
		window.close();
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
