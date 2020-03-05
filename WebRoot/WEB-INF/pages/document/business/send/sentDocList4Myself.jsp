<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>已发公文</title>
        <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
    </head>
    <body>
        <div class="bread-box"><div class="bread-title"><span class="bread-icon"></span><span class="bread-links"><span class="bread-start">当前位置：</span><span class="bread">公文管理 </span><span class="bread-split">&raquo;</span><span class="bread">已发公文(${deptName})</span></span><span class="bread-right-border"></span></div></div>
	            
             <form id="searchForm" action="${ctx}/send_sentDocList.do?departmentId=${departmentId}&listType=userId" method="post">
             <div class="displayTableForm">
            	<table class="fr" cellspacing="0" cellpadding="0">
            		<tr>
            			<td class="vm"></td>
            			<td class="vm">
            				<select mice-select="select" id="colName" name="colName">
		                        <option value="title">标题</option>
		                       <!-- <option value="keyword">关键字</option>-->
		                    </select>
            			</td>
            			<td class="vm"><input mice-input="input" type="text" id="colVlue" name="colValue" value="${colValue}"/></td>
            			<td>
            				<label style="font-size: 14; font-family: 黑体;" for="wh_xh">文号：</label>
            				<select mice-select="select" id="wh_bs" name="wh_bs">
		                        <option value="">请选择</option>
		                        <c:forEach items="${dnModelList}" var="model">
			                        <option value="${model.content}">${model.content}</option>
		                        </c:forEach>
		                    </select>
            				 [<input type="text" mice-input="input"  id="wh_nh" name="wh_nh" size="1" maxlength="4" value="">]
            				<input type="text" mice-input="input"  id="wh_xh" name="wh_xh" size="4" value="">
            			</td>
            			<td class="vm"><input type="button" id="searchBtn" mice-btn='icon-search' value="查  询" onclick="displaytagform('searchForm',[{f:'${pageIndexParamName}',v:'1'}])" /></td>
            		</tr>
            	</table>
            </div>
                <display:table name="requestScope.sendedList" class="displayTable" pagesize="10" htmlId="displayTable" id="element" partialList="true" export="false" size="requestScope.size" excludedParams="*" requestURI="send_sentDocList.do?listType=userId" form="searchForm">
                    <display:column headerClass="split" class="docType" title="序号" sortable="false" nulls="false">${element_rowNum}</display:column>
                    <display:column headerClass="split" class="docNum" title="文号" sortable="false" maxLength="20">${element.jgdz}<c:if test="${element.fwnh!=null}">[${element.fwnh}]</c:if>${element.fwxh}</display:column>
                  <display:column headerClass="split" class="docTitle" title="标题" sortable="false"><a href="${ctx}/send_sentDocDetail.do?docguid=${element.docguid}&departmentId=${departmentId}"><c:if test="${element.sendStatus ne null}"><c:if test="${element.sendStatus=='0'}"><font color="red">${element.title}</font></c:if><c:if test="${element.sendStatus=='1'}">${element.title}</c:if></c:if><c:if test="${element.sendStatus eq null}">${element.title}</c:if></a></display:column>
                    <display:column headerClass="split" class="docSendTime" title="发文时间" sortable="false" nulls="false">${fn:substring(element.submittm,0,19)}</display:column>
                    <display:column headerClass="split" class="docType" title="公文类型" sortable="false" nulls="false">${element.doctype} </display:column>
                </display:table>
              </form>	
              
      <script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
      <script src="${cdn_js}/sea.js"></script>
      <script type="text/javascript">
      		$(document).ready(function(){
	      		$('#colName').val('${colName}');
      		});
      		//回车监听
      		document.onkeydown = keyDown;
   			function keyDown(e){ 
   				if((e ? e.which : event.keyCode)==13 ){
   					$('#searchBtn').click();
   				}
      		}
   			seajs.use('lib/form',function(){
   				$('input[mice-btn]').cssBtn();
   				$('input[mice-input]').cssInput();
   				$('#colName').cssSelect();
   				$('#wh_bs').cssSelect({trigger:true,triggerFn:function(){
					$("#wh_nh").val(new Date().getYear());
					$("#wh_xh").focus();
				}});
   				$('#departmentId').cssSelect({trigger:true,triggerFn:function(){
					window.location.href="${ctx}/send_sentDocList.do?departmentId="+$(this).val();
				}});

   		    });
   		    
   			seajs.use('lib/hovercolor',function(){
   				$('table.displayTable').hovercolor({target:'tbody>tr'});
   		    });
   			$('#departmentId').val("${epartmentId}");
   			$('#colName').val("${colName}");
   			$('#colValue').val("${colValue}");
   			$('#wh_bs').val("${wh_bs}");
   			$('#wh_nh').val("${wh_nh}");
   			$('#wh_xh').val("${wh_xh}");
   		    
      </script>
    </body>
</html>