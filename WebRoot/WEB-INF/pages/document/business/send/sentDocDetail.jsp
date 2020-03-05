 <!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>
<%@ include file="/common/header.jsp" %>
<%@ page import="cn.com.trueway.base.util.*" %>
<html>
    <head>
        <title>已收公文详细信息</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <META HTTP-EQUIV="Expires" CONTENT="0">
        <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011"/>
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012"/>
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012"/>
        <script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
        <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/jquery.tab.js"></script>
        <script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
    </head>
    <body >
    	<!-- 导航 start -->
    	<div class="bread-box"><div class="bread-title"><span class="bread-icon"></span><span class="bread-links"><span class="bread-start">当前位置：</span><span class="bread">公文管理 </span><span class="bread-split">&raquo;</span><span class="bread">已发公文详细信息</span></span><span class="bread-right-border"></span></div></div>
        <!-- 导航end -->
        <div id="bodyXmlView" class="jQtabs" >
        	<table class="formTable">
        		<tr>
        			<td style="font-weight:bold" class="jQtabmenu">发文登记信息</td>
        			<td style="font-weight:bold" class="jQtabmenu">收取信息</td>
        			<td style="font-weight:bold;cursor: pointer;"  title="点击返回" onclick="javascript:history.back();"  >返回</td> 
        		</tr>
        	</table>
        	<div class="jQtabcontent">
            <table class="formTable">
            	<!-- <tr>
            		<td align="center" colspan="2" style="font-weight:bold">发文登记信息</td>
            		<td align="center" colspan="2" style="font-weight:bold"><a href="${ctx}/docDataProcess_showDocStatus.do?docxg_docguid=${doc.resultFlag}&departmentId=${departmentId}" style="text-align: center;">收取信息 </a></td>
            	</tr> -->
                <tr>
                    <td width="10%" align="center" style="font-weight:bold">主送</td>
                    <td colspan="3" align="left">${doc.xtoName }&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" style="font-weight:bold">抄送</td>
                    <td colspan="3" align="left">${doc.xccName }&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" style="font-weight:bold">标题词</td>
                    <td colspan="3" align="left">${doc.title }&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" style="font-weight:bold">
                   	     印发单位
                        <br></td>
                    <td colspan="3" align="left">${doc.yfdw }&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" style="font-weight:bold">
                     	   文号
                        <br></td>
                    <td colspan="3" align="left">${doc.jgdz} ${doc.fwnh} ${doc.fwxh}&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" style="font-weight:bold">类型</td>
                    <td align="left">${doc.doctype }&nbsp;</td>
                    <td align="center" width="10%" style="font-weight:bold">优先级</td>
                    <td align="left"><c:if test="${doc.priority==0}">
                    	特急
					</c:if>
					<c:if test="${doc.priority==1}">
                    	紧急
					</c:if>
					<c:if test="${doc.priority==2}">
                    	急件
					</c:if>
					<c:if test="${doc.priority==3}">
                    	平件
					</c:if></td>
                </tr>
                 <tr>
                    <td align="center" style="font-weight:bold">
                        	正文
                        <br></td>
                    <td colspan="3" align="left">
                    	<trueway:att id="${doc.docguid}att" docguid="${doc.docguid}" ismain="true" downloadAble="true" previewAble="true" />
                    </td>
                </tr>
                 <tr>
                    <td align="center" style="font-weight:bold">
                        	附件
                        <br></td>
                    <td colspan="3" align="left">
                    <trueway:att id="${doc.docguid}attext" docguid="${doc.docguid}" downloadAble="true" previewAble="true" />
                    </td>
                </tr>
            </table>
            </div>
            <div class="jQtabcontent">
			<%String weijieshou=Constant.SENDSTATUS_WEIJIESHOU;
              String yijieshou=Constant.SENDSTATUS_YIJIESHOU;
              String yiyuedu=Constant.SENDSTATUS_YIYUEDU;
              String yichuli=Constant.SENDSTATUS_YICHULI;
              String yituiwen=Constant.SENDSTATUS_YITUIWEN;
              pageContext.setAttribute("weijieshou",weijieshou); pageContext.setAttribute("yijieshou",yijieshou); pageContext.setAttribute("yiyuedu",yiyuedu);
              pageContext.setAttribute("yichuli",yichuli); pageContext.setAttribute("yituiwen",yituiwen);
            %>
                <display:table name="requestScope.docStatusList" pagesize="${pageSize}" class="displayTable" cellspacing="0" cellpadding="0" htmlId="displayTable" id="element" partialList="true" export="false" size="requestScope.size" excludedParams="_chk" requestURI="docDataProcess_showDocStatus.do?docxg_docguid=${element.resultFlag}&departmentId=${departmentId}">
                    <display:column title="序号" class="docType" sortable="false" nulls="false" style="width:100px;text-align:center;">${element_rowNum}</display:column>
                    <display:column title="单位名称" class="docTitle"  sortable="false"  style="text-align:left;padding:0 5px;">${element.xtoName}</display:column>
                    <display:column title="状态" class="docType"  sortable="false"  style="width:100px;text-align:center"><c:if test="${element.status eq yiyuedu}">${element.status}</c:if><c:if test="${element.status ==weijieshou}"><font color="FF1818">${element.status}</font></c:if><c:if test="${element.status ==yijieshou}"><font color="21D121">${element.status}</font></c:if><c:if test="${element.status ==yituiwen}"><font color="FF1764">${element.status}</font></c:if> </display:column>
                    <display:column title="收取时间" class="docSendTime"  sortable="false"  style="width:150px;text-align:center">${element.yfrq}</display:column>
                </display:table>
			</div>
        </div>
      	<script type="text/javascript">
        	$('.jQtabs').tabBuild();
        </script>
      </body>
</html>
