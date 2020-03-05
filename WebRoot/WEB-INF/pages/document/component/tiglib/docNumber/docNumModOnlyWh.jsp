 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="cn.com.trueway.base.util.*"%>
<!--实体类 -->
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil" %>
<!-- JSTL 标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- struts2 标签 -->
<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- 自定义标签库 -->
<%@ taglib uri="http://trueway.cn/taglib" prefix="trueway"%>

<!-- sitemesh 标签 -->
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>

<!-- displaytag 标签 -->
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!-- 简化书写 -->
<c:set var="dateFormatDisplay">yyyy.MM.dd</c:set>
<c:set var="timeFormatDisplay">yyyy-MM-dd HH:mm:ss</c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="curl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<!-- UI base path ,author: purecolor@foxmail.com ,date: 2011/12/27 AM 9:21 -->
<% String RSUrl=SystemParamConfigUtil.getParamValueByParam("RSUrl"); pageContext.setAttribute("RSUrl",RSUrl);%>
<c:set var="cdn_js" value="${RSUrl}/plugin/js"/>
<c:set var="cdn_as" value="${RSUrl}/plugin/as"/>
<c:set var="cdn_imgs" value="${RSUrl}/theme"/>

<html>
    <head>
        <title>文号表单</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <!--表单样式-->
		<link href="${curl}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${curl}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<link href="${curl}/tabInLayer/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="${curl}/tabInLayer/css/bootstrap-addtabs.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="${curl}/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="${curl}/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="${curl}/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
		<link href="${curl}/dwz/button/ued.button.css" rel="stylesheet" type="text/css" />
		<!--JS -->
		<script src="${curl}/tabInLayer/js/jquery.min.js" type="text/javascript"></script>
		<script src="${curl}/tabInLayer/js/jquery-migrate-1.2.1.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/jquery.validate.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/dwz.min.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
		<script src="${curl}/pdfocx/json2.js" type="text/javascript"></script>
		<style>
		 .wf-fixtable {
			 border-left: solid 1px #ededed;
			 margin: 10px;
		 }
		.wf-fixtable tr th {
			background: #e8eff9;
			font-size: 12px;
			border-color: #d0d0d0;
			padding: 8px 5px;
			border-style: solid;
			border-width: 1px 1px 1px 0;
			vertical-align: top;
			white-space: nowrap;
			line-height: 1.5;
			cursor: default;		
		}
		.wf-fixtable tr td {
			font-size: 12px;
			border-right: solid 1px #ededed;
			overflow: hidden;
			padding: 8px 5px;
			border-bottom: solid 1px #ededed;
			vertical-align: middle;
			line-height: 1.5;		
		}
		</style>
    </head>
	<base target="_self">  
    <body >
    <a id="wenhao" href="#"></a>
    	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="80">
			<tr>
			<td>
				<table >
					<tr>
						<td >
							<span style="font-family:黑体;font-size:13px;">文号:</span>
						</td>
						<td >
							<div id="tempWH" style="font-size: 15px;display: inline;">
							<input id="wh" name="wh" value="${oldWh}"/>号
							</div>
						</td>
						<td >	
							<a class="buttonActive" href="javascript:scxh();"><span>生成序号</span></a>
							<%-- <a class="buttonActive" href="javascript:cdv_getvalues();"><span>确认</span></a> --%>
						</td>
					</tr>
				</table>
			</td>
			</tr>
    	</table>
		<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="116" style="width:98%">                                                                                                                                            			
				<thead>                                                                                                                                                                          			
					<tr>                                                                                                                                                                         			
						<th width="48%" style="font-weight:bold;text-align:center;">未使用的文号</th>                                                                                                  			
						<th width="48%" style="font-weight:bold;text-align:center;">操作</th>                                                                                                      			
					</tr>                                                                                                                                                                        			
				</thead>                                                                                                                                                                         			
				<tbody>                                                                                                                                                                          			
					<c:forEach items="${list}" var="bw" varStatus="status">                                                                                                                    			
						<tr >                                                                                                                                                                    			
							<td style="text-align:center;">${bw.wh}号</td>                                                                                                			
							<td style="text-align:center;"><a href="JavaScript:set('${bw.wh}');">使用</a></td>                                                                                   			
						</tr>                                                                                                                                                                    			
					</c:forEach>                                                                                                                                                                 			
				</tbody>                                                                                                                                                                         			
			</table>                                                                                                                                                                             			
		</div>
			<div class="wf-list-ft" id="pagingWrap">
	</div>
		
		<input type="hidden" id='msg' value='' name='msg'/>
        <script type="text/javascript">
     
			
			function scxh(){
				$('#wh').val('${wh}');
			}
			
        	function set(xh){
        		$('#wh').val(xh);
        	}
			
			function cdv_getvalues(){
				var xh = $("#wh").val()==undefined?null:$("#wh").val();
				//校验序号
				if(xh!=null){
					if(xh==""){
						alert("请先生成序号!");
						return "";
					}
		
					//alert(fxh);
					if(!xh.match(/^\d*$/)){
						alert('文号填写有误!');
						$("#wh").val("");
						return "";
					}
					$.ajax({
					    url: 'docNumber_addDocNumModOnlyWh.do',
					    type: 'POST',
					    cache:false,
					    async:false,
					    dataType:'text',
					    data:{
					    	wh : xh,
					    	workflowId : '${workflowId}',
					    	oldWh :'${oldWh}'
					    },
					    error: function(){
					        alert('AJAX调用错误(docNumber_addDocNumModOnlyWh.do)');
					        $('#msg').val('error');
					    },
					    success: function(msg){
					    	$('#msg').val(msg);
					    }
					});
					if($('#msg').val()=='0'){
						alert("文号不能为空!");
			    		return "";
					}else if($('#msg').val()=='2'){
						alert("文号已被使用!");
						$('#wh').val('');
						return "";
					}
				}
				
				return xh;
			}
        </script>
        
    </body>
</html>
