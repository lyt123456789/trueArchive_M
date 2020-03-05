<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.trueway.base.util.*"%>
<html>
<head>
<meta charset="UTF-8" />
<%@ include file="/common/header.jsp"%>
<title>已收公文</title>
<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
<script src="${cdn_js}/base/jquery.js"></script>
<Script type="text/javascript">
	//删除左右两端的空格
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
	$('#searchcontent').live('blur', function() {
		this.value = trim(this.value);
	});
</Script>
<script language="javascript" for="document" event="onkeydown">
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which
			: event.charCode;
	if (keyCode == 13) {
		$("#searchBtn").click();
	} 
</script>

</head>
<body>
<% 
	request.setAttribute("ygl",Constant.RECEIVE_STATUS_YIGUANLIAN);
%>
<form id="chaxunform" name="chaxunform" method="post" action="${ctx}/rec_receivedDocList4SW.do?fromType=${fromType}&type=${type}&departmentId=${departmentId}">
<div class="displayTableForm">
	<div class="fl"><input mice-btn='icon-copy' name="importHand" type="button" value="手动导入" onclick="window.location='${ctx}/rec_toImportDoc.do?departmentId=${departmentId}'" /></div>
	<ul class="searchUL">
		<li>
		<label>部门</label>
			<select mice-select="select" id="departmentId" name="departmentId" class="departmentId">
				<c:forEach items="${deps}" var="dep">
					<option value="${dep.departmentGuid}" <c:if test="${dep.departmentGuid eq departmentId}">selected="selected"</c:if>>${dep.departmentName}</option>
				</c:forEach>
				<option value="">全部</option>
			</select>
		</li>
		<li>
			<label>来文号</label><input type="text" mice-input="input" id="wh" name="wh" value=""/>
		</li>
		<li>
			<label>标题</label><input value="" type="text" mice-input="input" id="title" name="title" />
		</li>
		<li>
			<label>来文单位</label><input value="" type="text" mice-input="input" size="12" id="sendername" name="sendername" />
		</li>
		
		<div style="display:none">
			<label>状态:</label>
			<select mice-select="select" name="statuskey" id="statuskey">
				<option value="">请选择</option>
				<option value='${RECEIVE_WEICHULI}'>未办理</option>
				<option value='${RECEIVE_ZHENGBANLI}'>办理中</option>
				<option value='${RECEIVE_BANJIE}'>已办理</option>
				<option value='${RECEIVE_GUANLIAN}'>已关联</option>
				<option value='${RECEIVE_TUIWEN}'>已退文</option>
				<option value='${RECEIVE_WUGUAN}'>其他</option>
			</select>
			<select mice-select="select" name="timeType" id="timeType" value="${timeType}">
				<option value="recDate">收文时间</option>
				<option value="submittm">来文时间</option>
			</select>
			<input mice-input="input" type="text" id="startTime" name="startTime" value="${docbw.startTime}" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'startTime\')}'})" readonly="readonly">-
			<input mice-input="input" type="text" id="endTime" name="endTime" value="${docbw.endTime}" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly">
			<label>关键字:</label>
			<input value="" type="text" mice-input="input" size="30" id="keyword" name="keyword" />
		</div>
	</ul>
	<div align="right">
		<input mice-btn='icon-search' id="searchBtn" name="searchBtn" type="button" value="查 询" onclick="displaytagform('chaxunform',[{f:'${pageIndexParamName}',v:'1'}])"/>
		<input mice-btn='icon-search' id="super_search" name="super_search" type="button" value="高级查询"/>
		<input type="hidden" id="type" name="type" value="${type}" />
		<input mice-btn='icon-search' id="exportRecDoc" type="button" value="导 出"/>
		<input type="hidden" name="isExport" id="isExport" value="" />
	</div>
</div>
<display:table class="displayTable" name="requestScope.docList" 
	pagesize="10" htmlId="displayTable" id="element" partialList="true"
	export="false" size="requestScope.size" excludedParams="*"
	requestURI="rec_receivedDocList4SW.do?fromType=${fromType}" form="chaxunform">
	<display:column headerClass="split" class="docNum" title="来文号" sortable="false" maxLength="20"><label id="${element.docguid}wh" value="${element.wh}"><c:if test="${element.status==0}"><span style="color: #1C488E">${element.wh}&nbsp;</span></c:if><c:if test="${element.status==1}"><span style="color: #3B5F00">${element.wh}&nbsp;</span></c:if><c:if test="${element.status==2 or element.status==3 or element.status==ygl}">${element.wh}&nbsp;</c:if><c:if test="${element.status==4}"><span style="color: #C90F1F">${element.wh}&nbsp;</span></c:if></label></display:column>
	<display:column headerClass="split" class="docSender" title="来文单位" maxLength="8" sortable="false" nulls="false"><label id="${element.docguid}sender" value="${element.sendername}"><c:if	test="${element.status==0}"><span style="color: #1C488E">${element.sendername}&nbsp;</span></c:if><c:if test="${element.status==1}"><span style="color:#3B5F00">${element.sendername}&nbsp;</span></c:if><c:if	test="${element.status==2 or element.status==3 or element.status==ygl}">${element.sendername}&nbsp;</c:if><c:if test="${element.status==4}"><span style="color: #C90F1F">${element.sendername}&nbsp;</span></c:if></label></display:column>
	<display:column headerClass="split" class="docTitle" title="标题"sortable="false"><label id="${element.docguid}title" value="${element.title}"><a href="javascript:if('${departmentId}'==='') window.location.href='${ctx}/rec_receivedDocDetail.do?id=${element.docguid}&deptId=${element.queueXto}'; else window.location.href='${ctx}/rec_receivedDocDetail.do?id=${element.docguid}&deptId=${departmentId}';" target="_self"><c:if test="${element.status==0}"><span style="color: #1C488E">${element.title}&nbsp;</span></c:if><c:if test="${element.status==1}"><span style="color: #3B5F00">${element.title}&nbsp;</span>	</c:if><c:if test="${element.status==2 or element.status==3 or element.status==ygl}">${element.title}&nbsp;</c:if><c:if	test="${element.status==4}"><span style="color: #C90F1F">${element.title}&nbsp;</span></c:if></a></label></display:column>
	<display:column headerClass="split" class="docSendTime" title="来文时间" sortable="false" nulls="false"><label id="${element.docguid}sub" value="${fn:substring(element.submittm,0,19)}"><c:if test="${element.status==0}"><span style="color: #1C488E">${fn:substring(element.submittm,0,19)}</span></c:if><c:if test="${element.status==1}"><span style="color: #3B5F00">${fn:substring(element.submittm,0,19)}</span></c:if><c:if	test="${element.status==2 or element.status==3 or element.status==ygl}">${fn:substring(element.submittm,0,19)}</c:if><c:if test="${element.status==4}"><span style="color: #C90F1F">${fn:substring(element.submittm,0,19)}</span></c:if></label></display:column>
	<display:column headerClass="split" class="docSendTime" title="收文时间" sortable="false" nulls="false"><c:if test="${element.status==0}"><span style="color: #1C488E">${fn:substring(element.recDate,0,19)}</span>	</c:if><c:if test="${element.status==1}"><span style="color: #3B5F00">${fn:substring(element.recDate,0,19)}</span></c:if><c:if test="${element.status==2 or element.status==3 or element.status==ygl}">${fn:substring(element.recDate,0,19)}</c:if><c:if test="${element.status==4}"><span style="color: #C90F1F">${fn:substring(element.recDate,0,19)}</span></c:if></display:column>
	<display:column class="docType" title="状态">
		<c:if test="${element.status==RECEIVE_WEICHULI}"><span style="color: #1C488E">未办理</span></c:if>
		<c:if test="${element.status==RECEIVE_ZHENGBANLI}"><span style="color: #3B5F00">办理中</span></c:if>
		<c:if test="${element.status==RECEIVE_BANJIE}">已办理</c:if>
		<c:if test="${element.status==RECEIVE_WUGUAN}">其他</c:if>
		<c:if test="${element.status==RECEIVE_TUIWEN}"><span style="color: #C90F1F">已退文</span></c:if>
		<c:if test="${element.status==RECEIVE_GUANLIAN}"><span style="color: #C90F1F">已关联</span><br/>[<a href="javascript:chexiao('${element.docguid}');">撤销关联</a>]</c:if>
	</display:column>
</display:table>

</form>

<!-- 高级搜索 -->
	<div id="superDiv" style="display:none;">
	<form id="chaxunform2" name="chaxunform2" method="post" action="${ctx}/rec_receivedDocList.do?type=${type}&departmentId=${departmentId}">
		<table width="100%" class="searchUL">
			<tr>
				<td width="50%" height="30">
				<label>部门:</label><select mice-select="select" id="departmentId2" class="departmentId" name="departmentId">
						<c:forEach items="${deps}" var="dep">
							<option value="${dep.departmentGuid}" <c:if test="${dep.departmentGuid eq departmentId}">selected="selected"</c:if>>${dep.departmentName}</option>
						</c:forEach>
						<option value="">全部</option>
					</select>
				</td>
				<td width="50%">
					<label>状态:</label><select mice-select="select" name="statuskey" id="statuskey">
						<option value="">请选择</option>
						<option value='${RECEIVE_WEICHULI}'>未办理</option>
						<option value='${RECEIVE_ZHENGBANLI}'>办理中</option>
						<option value='${RECEIVE_BANJIE}'>已办理</option>
						<option value='${RECEIVE_GUANLIAN}'>已关联</option>
						<option value='${RECEIVE_TUIWEN}'>已退文</option>
						<option value='${RECEIVE_WUGUAN}'>其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" height="30">
					<label>来文号:</label><input type="text" mice-input="input" id="wh" name="wh" value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="2" height="30">
					<select mice-select="select" name="timeType" id="timeType" value="${timeType}">
						<option value="recDate">收文时间</option>
						<option value="submittm">来文时间</option>
					</select><input mice-input="input" type="text" id="startTime" name="startTime" value="${docbw.startTime}" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'startTime\')}'})" readonly="readonly">-
					<input mice-input="input" type="text" id="endTime" name="endTime" value="${docbw.endTime}" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td width="50%" height="30">
				<label>标题:</label><input value="" type="text" mice-input="input" id="title" name="title" />
				</td>
				<td width="50%">
				<label>来文单位:</label><input value="" type="text" mice-input="input" size="12" id="sendername" name="sendername" />
				</td>
			</tr>
			<tr>
				<td height="30">
				<label>关键字:</label><input value="" type="text" mice-input="input" size="30" id="keyword" name="keyword" />
				</td>
				<td>
				<input mice-btn='icon-search' id="searchBtn" name="searchBtn" type="button" value="查 询" onclick="displaytagform('chaxunform2',[{f:'${pageIndexParamName}',v:'1'}])"/>
				</td>
			</tr>
		</table>
		</form>
	</div>
<script type="text/javascript" src="${ctx}/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script src="${cdn_js}/sea.js"></script>
<script src="${cdn_js}/common/dialog/artDialog.js"></script>
<script type="text/javascript">
	$('#super_search').bind('click',function(){
		art.dialog({
		    content: document.getElementById('superDiv'),
		    id: 'EF893L'
		});
	});

	seajs.use('lib/form',function(){
		$('input[mice-btn]').cssBtn();
		$('input[mice-input]').cssInput();
		$('.departmentId').cssSelect({trigger:true,triggerFn:function(){
			window.location.href="${ctx}/rec_receivedDocList4SW.do?fromType=${fromType}&type=webid&departmentId="+$(this).val();
		}});
		$('#searchword').cssSelect();
		$('#statuskey').cssSelect();
		$('#timeType').cssSelect();
    });
	
	function chexiao(docguid) {
		if (window.confirm('确认撤销!')) {
			$.ajax( {
				url : 'pm_updateResultDocVo.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'docguid' : docguid
				},
				error : function() {
					alert('AJAX调用错误(pm_updateResultDocVo.do)');
				},
				success : function(msg) {
					if (msg == 'success') {
						alert("成功撤销！");
						window.location.href="${ctx}/rec_receivedDocList4SW.do?fromType=${fromType}&type=webid&departmentId="+$('#departmentId').val();
					} else if (msg == 'error') {//异常情况
					alert("撤销失败!");
				}
			}
			});
		}
	}

	

	$("#exportRecDoc").bind('click',function(){
		var isPass=true;
		$.ajax( {
			url : '${ctx}/rec_checkExportRownum4SW.do?fromType=${fromType}',
			type : 'POST',
			cache : false,
			async : false,
			data : {
				'departmentId' : "${departmentId}",
				'type' : "${type}",
				'wh' : $('#wh').val(),
				'title' : $('#title').val(),
				'sendername' : $('#sendername').val(),
				'statuskey' : $('#statuskey').val(),
				'timeType' : $('#timeType').val(),
				'startTime' : $('#startTime').val(),
				'endTime' : $('#endTime').val(),
				'keyword' : $('#keyword').val()
			},
			error : function() {
				alert('检查导出的公文数失败');
			},
			success : function(msg) {
				if (msg != 'isPass') {
					alert("要导出的数据过多，无法导出！");
					isPass=false;
				}
			}
		});
		if(isPass){
			$("#isExport").val("yes");
			$("#chaxunform").submit();
			$("#isExport").val("");
		}
	});
    
	seajs.use('lib/hovercolor',function(){
		$('table.displayTable').hovercolor({target:'tbody>tr'});
    });
	$("#wh_nh").bind('focus',function(){$("#wh_nh").val("");}).val("["+new Date().getYear()+"]");
	//$('#departmentId').val("${departmentId}");
	$('#wh').val('${wh}');
	$('#title').val('${title}');
	$('#sendername').val('${sendername}');
	$('#statuskey').val("${statuskey}");
	$('#timeType').val("${timeType}");
	$('#startTime').val('${startTime}');
	$('#endTime').val('${endTime}');
	$('#keyword').val('${keyword}');
	$('#departmentId').val("${departmentId}");
	$('#departmentId2').val("${departmentId}");
	$("#wh_nh").bind('focus',function(){$("#wh_nh").val("");}).val(""+new Date().getYear());
	/**
	 * 自定义displaytagform表单查询 2012-02-06 purecolor@foxmail.com
	 */
	function displaytagform(id, fields){
		var objfrm = document.getElementById(id);
		for (j=fields.length-1;j>=0;j--){var f= objfrm.elements[fields[j].f];if (f){f.value=fields[j].v};}
		objfrm.submit();
	} 
</script>
</body>
</html>
