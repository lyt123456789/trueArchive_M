<%@ page contentType="text/html; charset=gb2312" pageEncoding="gb2312" import="cn.com.trueway.base.util.*"%>
<!--ʵ���� -->
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil" %>
<!-- JSTL ��ǩ -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- struts2 ��ǩ -->
<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- �Զ����ǩ�� -->
<%@ taglib uri="http://trueway.cn/taglib" prefix="trueway"%>

<!-- sitemesh ��ǩ -->
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>

<!-- displaytag ��ǩ -->
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!-- ����д -->
<c:set var="dateFormatDisplay">yyyy.MM.dd</c:set>
<c:set var="timeFormatDisplay">yyyy-MM-dd HH:mm:ss</c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="curl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<!-- UI base path ,author: purecolor@foxmail.com ,date: 2011/12/27 AM 9:21 -->
<% String RSUrl=SystemParamConfigUtil.getParamValueByParam("RSUrl"); pageContext.setAttribute("RSUrl",RSUrl);%>
<c:set var="cdn_js" value="${RSUrl}/plugin/js"/>
<c:set var="cdn_as" value="${RSUrl}/plugin/as"/>
<c:set var="cdn_imgs" value="${RSUrl}/theme"/>

