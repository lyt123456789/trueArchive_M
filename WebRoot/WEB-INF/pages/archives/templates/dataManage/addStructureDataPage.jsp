<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>详情页</title>
    <link rel="stylesheet" href="css/list.css?t=123">
     <link rel="stylesheet" href="css/form.css?t=111">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style type="text/css">
    </style>
</head>
<body>

<div class="table2" style="height: 65%;">
<input type="button" id="save"   value="保存">
	<form id="dataForm">
	<input type="hidden" id="treeId" name="treeId" value="${treeId}">
	<input type="hidden" id="structureId" name="structureId" value="${structureId}">
	<input type="hidden" id="parentId" name="parentId" value="${parentId}">
	<input type="hidden" id="projectStructureId" name="projectStructureId" value="${projectStructureId}">
	<input type="hidden" id="fileStructureId" name="fileStructureId" value="${fileStructureId}">
	<input type="hidden" id="innerFileStructureId" name="innerFileStructureId" value="${innerFileStructureId}">
    <table cellspacing="0" cellpadding="0">
		<tr>
		<c:set var="step" value="0"></c:set>
		<c:set var="isNext" value="false"></c:set>
		<c:forEach items="${etList}" var="tag" varStatus="state">
			<c:set var="key" value="C${tag.id}"></c:set>
			<c:if test="${(step+state.count)%2!=0}">
				<c:if test="${tag.esLength>200||tag.esLength==200}">
					<td width="110" class="name "> ${tag.esIdentifier}</td>
					<td width="323" colspan="3">
						<textarea rows="3" id="C${tag.id}" name="C${tag.id}" 
							<c:if test="${tag.esIsNotNull eq '1'}">style="background-color: #ffeeee;border: 1px solid #ff7870;"</c:if>
						></textarea>
					</td>
					</tr>
					<tr>
					<c:set var="isNext" value="true"></c:set>
				</c:if>
				<c:if test="${tag.esLength<200}">
					<td width="110" class="name "> ${tag.esIdentifier}</td>
					<td width="323">
					<c:set var="key_DMZ" value="${tag.id}_DMZ"></c:set>
					<c:set var="key_YSJ" value="${tag.id}_YSJ"></c:set>
					<c:choose>
					    <c:when test="${map_DMZ[key_DMZ] ne null}">
					         <select>
					        	<c:forEach items="${map_DMZ[key_DMZ]}" var="dmz" varStatus="state">
					        		<option value="${dmz['tagCodeValue']}">${dmz['tagPropValue']}</option>
					        	</c:forEach>
					        </select>
					    </c:when>
					    <c:when test="${map_YSJ[key_YSJ] ne null}">
					        <select>
					        	<c:forEach items="${map_YSJ[key_YSJ]}" var="ysj" varStatus="state">
					        		 <option value="${ysj.esIdentifier}">${ysj.esTitle}</option>
					        	</c:forEach>
					        </select>
					    </c:when>
					    <c:otherwise>
					        <input type="text" id="C${tag.id}" name="C${tag.id}" value=""
							<c:if test="${tag.esIsNotNull eq '1'}">style="background-color: #ffeeee;border: 1px solid #ff7870;"</c:if>/>
					    </c:otherwise>
					</c:choose>
					</td>
				</c:if>
			</c:if>
			<c:if test="${(step+state.count)%2==0 }">
				<c:if test="${tag.esLength>200||tag.esLength==200}">
					</tr>
					<tr>
					<td width="110" class="name "> ${tag.esIdentifier}</td>
					<td width="323" colspan="3">
						<textarea rows="3" id="C${tag.id}" name="C${tag.id}" 
							<c:if test="${tag.esIsNotNull eq '1'}">style="background-color: #ffeeee;border: 1px solid #ff7870;"</c:if>
						></textarea>
					</td>
					</tr>
					<tr>
				</c:if>
				<c:if test="${tag.esLength<200}">
					<td width="110" class="name "> ${tag.esIdentifier}</td>
					<td width="323">
						<c:set var="key_DMZ" value="${tag.id}_DMZ"></c:set>
						<c:set var="key_YSJ" value="${tag.id}_YSJ"></c:set>
						<c:choose>
							 <c:when test="${map_DMZ[key_DMZ] ne null}">
						         <select>
						        	<c:forEach items="${map_DMZ[key_DMZ]}" var="dmz" varStatus="state">
						        		<option value="${dmz['tagCodeValue']}">${dmz['tagPropValue']}</option>
						        	</c:forEach>
						        </select>
						    </c:when>
						    <c:when test="${map_YSJ[key_YSJ] ne null}">
						        <select>
						        	<c:forEach items="${map_YSJ[key_YSJ]}" var="ysj" varStatus="state">
						        		 <option value="${ysj.esIdentifier}">${ysj.esTitle}</option>
						        	</c:forEach>
						        </select>
					   		 </c:when>
						    <c:otherwise>
						        <input type="text" id="C${tag.id}" name="C${tag.id}" value=""
								<c:if test="${tag.esIsNotNull eq '1'}">style="background-color: #ffeeee;border: 1px solid #ff7870;"</c:if>/>
						    </c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
				</c:if>
			</c:if>
			<c:if test="${isNext eq true }">
				<c:set var="step" value="${step+1}"></c:set>	
			</c:if>
			<c:set var="isNext" value="false"></c:set>
		</c:forEach>
		</tr> 
	</table>
	</form>
</div>
<c:if test="">
<div>
	<span>文件附件</span>
	<input type="button" id="save"   value="挂接文件">
	<input type="button" id="save"   value="附加文件">
	<input type="button" id="save"   value="删除">
	</div>
</c:if>

</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
$(document).ready(function (){
	$(".show").css("height",$(window).height()+10);
	$(".list_table").css("height",$(".show").height()-$(".table2").height()-$(".dz").height()-10);
});

//子页面在top层被打开的，子页面刷新父页面的方法
function refresh(){
	var iframe = refreshFatherPage(top.frames); 
	$(iframe.frameElement)[0].contentWindow.refreshStructureDataPage();
}

function refreshFatherPage(array){
	  var elem; 
	  for (var i = 0, len = array.length; i < len; i++) { //contentWindow.istruePage()
	 	  if ($(array[i].frameElement).contents().find("#istruePage${refreshFlag}").length!=0) 
	 		  return array[i]; 
	 	  else { 
	  		  elem =  refreshFatherPage(array[i].frames); 
		  if (!!elem) 
			  return elem 
		  } 
	  } 
	  return elem; 
}
</script>
</html>