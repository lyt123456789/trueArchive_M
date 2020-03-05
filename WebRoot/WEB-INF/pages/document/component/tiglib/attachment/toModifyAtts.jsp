<!DOCTYPE html >
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
		<link rel="stylesheet" href="${ctx}/widgets/theme/ued.base.css?t=2011"/>
		<link rel="stylesheet" href="${ctx}/widgets/theme/dm/css/ued.module.css?t=2012"/>
		<link rel="stylesheet" href="${ctx}/widgets/theme/mice/form.css?t=2012"/>
		<link rel="stylesheet" href="${ctx}/widgets/theme/mice/module.css?t=2012"/>
		<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
    </head>
    <body>
    	<div class="bread-box">
			<div class="bread-title"><span class="bread-icon"></span><span class="bread-links"><span class="bread-start">当前位置：</span><span class="bread">附件修改</span><span class="bread-split">&raquo;</span><span class="bread">修改</span></span><span class="bread-right-border"></span></div>
		</div>	
		<form id="workCalendarForm" action="${ctx}/attachment_modifyAtts.do?attId=${sendAtt.id}" method="post">
			<div class="displayTableForm">
				<table class="formTable">
					<tr>
						<td >
							文件标题：<input type="text"  mice-input="input"  id="title" name="title" value="${sendAtt.title}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							 文件类型：<select mice-select="select" name="type" id="type" value="${sendAtt.type}">
						 		<c:forEach items="${attsextTypeList}" var="attType">
                                	 <option <c:if test="${attType.type == '${type}'}">selected</c:if>>${attType.type}</option>
						 		</c:forEach>
                             </select>
						</td>
					</tr>
					<tr>
						<td><input mice-btn="icon-apply" style="align:right" id="apply" type="submit" class="getJSON" value="修改"/></td>
					</tr>
				</table>
			</div>	
		</form>
        
        <script src="${ctx}/widgets/plugin/js/sea.js"></script>
        <script type="text/javascript">
        	seajs.use('lib/form',function(){
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
            });
        	seajs.use('lib/hovercolor',function(){
				$('table.displayTable').hovercolor({target:'tbody>tr'});
            });

        </script>
    </body>
</html>
