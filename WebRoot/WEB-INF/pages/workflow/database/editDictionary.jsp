<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="cn.com.trueway.workflow.core.pojo.WfDictionary"%><html>
<head>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerindex.jsp"%>
	<!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body style="overflow:auto;">
	<div class="menu">
		<form id="dictionaryform" action="${ctx}/dictionary_edit.do" method="post">
			<table class="infotan" width="100%">
				<tr>
					<td width="20%" class="bgs ls">字典名称：</td>
					<td width="80%">
						<input type="text" name="dictionary.vc_name" id="dictionary.vc_name" value="${dictionary.vc_name}" onchange="checkDictionaryName();" style="height: 22px">
						<input type="hidden" name="dictionary.vc_key" id="dictionary.vc_key">
						<input type="hidden" name="dictionary.vc_value" id="dictionary.vc_value">
						<input type="hidden" name="dictionary.vc_default" id="dictionary.vc_default">
						<input type="hidden" name="dictionary.id" id="dictionary.id" value="${dictionary.id}">
						<input type="hidden" name="dictionary.lcid" id="dictionary.lcid" value="${dictionary.lcid}">
					</td>
				</tr>
				<tr>
					<td class="bgs ls">字典类型：</td>
					<td>
						<input type="hidden" id="b_public" name="b_public">
						<input type="hidden" id="b_public" name="b_public">
						<input type="radio" name="b_public_rad" id="b_public_rad1" value="1" <c:if test="${dictionary.lcid == null}">checked</c:if>>公共字典 
						<input type="radio" name="b_public_rad" id="b_public_rad0" value="0" <c:if test="${dictionary.lcid != null}">checked</c:if>>私有字典 
					</td>
				</tr>
			</table>
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
			<thead>
				<tr>
					<th align="center" width="400px">字典代码(KEY)</th>
					<th align="center" width="400px">字典值(VALUE)</th>
					<th align="center" width="200px">默认值(DEFAULT)</th>
					<th></th>
				</tr>
			</thead>
				<tbody id="tbodyid">
					<c:forEach items="${outList}" var="itme" varStatus="status">	
					<tr valign="middle" onclick="c1(this)">
						<td align="center">
							<input type="text" name="vc_keys" id="vc_keys" style="height: 22px;width:380px" value="${itme.key}" >
						</td>
						<td align="center">
							<input type="text" name="vc_values" id="vc_values" style="height: 22px;width:380px" value="${itme.value}">
						</td>
						<td align="left" >
			    	 		<input type="radio" name="vc_default" id="vc_default" style="height: 22px;width:200px" value="${status.index + 1 }" <c:if test="${null != default && default eq  status.index+1}">checked='checked'</c:if> />
			    	 	</td>
						<td></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div id="showSql" style="border: 1px; width: 60%; height: 20%"></div>
	<div class="formBar pa" style="bottom: 0px; width: 100%;">
		<ul class="mr5">
			<li><a onclick="checkForm()" name="CmdView" class="buttonActive" href="javascript:;"><span>保存</span></a></li>
			<li><a onclick="ConterDel()" name="CmdView" class="buttonActive" href="javascript:;"><span>删除</span></a></li>
			<li><a onclick="ConterAdd()" name="CmdView" class="buttonActive" href="javascript:;"><span>新增</span></a></li>
			<li><a onclick="moveUp()" name="CmdView" class="buttonActive" href="javascript:;"><span>向上</span></a></li>
			<li><a onclick="moveDown()" name="CmdView" class="buttonActive" href="javascript:;"><span>向下</span></a></li>
			<li><a onclick="goHistroy();;" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
		</ul>
	</div>
</body>

    <script type="text/javascript">
	function checkDictionaryName() {
		var vc_name = document.getElementById('dictionary.vc_name').value;
		if (vc_name != "") {
			$.post("${ctx}/dictionary_checkDictionary.do?vc_name=" + vc_name,
					null, function(value) {
						if (value == "yes") {
							alert(' 此字典已存在!');
							$('#vc_name').val('');
							return false;
						}
					});
		}
	}

	function checkForm() {
		var name = document.getElementById('dictionary.vc_name').value;
		if (!name) {
			alert('请填字典名');
			return;
		}

		//字段名
		var keys = document.getElementsByName('vc_keys');
		var key = "";
		for ( var i = 0; i < keys.length; i++) {
			if (!keys[i].value) {
				alert('请将字典代码填写完整');
				return;
			} else {
				key += keys[i].value + ",";
			}
		}
		if (key.length > 1) {
			document.getElementById('dictionary.vc_key').value = key.substring(
					0, key.length - 1);
		}

		//字段名
		var values = document.getElementsByName('vc_values');
		var value = "";
		for ( var i = 0; i < values.length; i++) {
			if (!values[i].value) {
				alert('请将字典值填写完整');
				return;
			} else {
				value += values[i].value + ",";
			}
		}
		if (value.length > 1) {
			document.getElementById('dictionary.vc_value').value = value
					.substring(0, value.length - 1);
		}
		
		//默认值
		var defaultV = $("#tbodyid input:radio:checked").val();
		if(null == defaultV){
			/* alert('请选择默认值');
			return; */
		}
		document.getElementById('dictionary.vc_default').value = defaultV;
		
		//公共还是私有
		var pub = document.getElementsByName('b_public_rad');
		if(pub[0].checked){
			document.getElementById('b_public').value = 1;
		}else{
			document.getElementById('b_public').value = 0;
		}
		
		document.getElementById('dictionaryform').submit();
	}

	var selectedTr = null;

	function c1(obj) {
		obj.style.backgroundColor = '#cccccc'; //把点到的那一行变希望的颜色; 
		if (selectedTr != null)
			selectedTr.style.removeAttribute("backgroundColor");
		if (selectedTr == obj)
			selectedTr = null;//加上此句，以控制点击变白，再点击反灰 
		else
			selectedTr = obj;
	}

	function ConterAdd(){   
		$('#tbodyid').append('<tr onclick="c1(this)">'+$('#tbodyid').find('tr').last().html()+'</tr>') 
		$('#tbodyid').find('tr').last().find('input').val('');
		var count = $('#tbodyid tr').length;
		$('#tbodyid').find('tr').last().find('input:radio').val(count);
	}

	function ConterDel(trs){   
		var hs=$('#tbodyid tr');
		if(hs.length<=1){alert('默认最后一行不可删！')}
		else{$(selectedTr).remove()}
	}

	function moveUp() {
		//通过链接对象获取表格行的引用 
		var _row = selectedTr;
		//如果不是第一行，则与上一行交换顺序 
		if (_row.previousSibling) {
			swapNode(_row, _row.previousSibling);
		}
	}
	function moveDown() {
		//通过链接对象获取表格行的引用 
		var _row = selectedTr;
		//如果不是最后一行，则与下一行交换顺序 
		if (_row.nextSibling) {
			swapNode(_row, _row.nextSibling);
		}
	}
	//定义通用的函数交换两个结点的位置 
	function swapNode(node1, node2) {
		//获取父结点 var 
		_parent = node1.parentNode;
		//获取两个结点的相对位置 
		var _t1 = node1.nextSibling;
		var _t2 = node2.nextSibling;
		//将node2插入到原来node1的位置 
		if (_t1) {
			_parent.insertBefore(node2, _t1);
		} else {
			_parent.appendChild(node2);
		}
		//将node1插入到原来node2的位置 
		if (_t2) {
			_parent.insertBefore(node1, _t2);
		} else {
			_parent.appendChild(node1);
		}
	}
	
	function goHistroy(){
		parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
	}	
</script>
<script type="text/javascript">
    var hh=$(window).height();
    $('#w_list_print').height(hh-70); 
    </script>
</html>
