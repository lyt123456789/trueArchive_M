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
<script>
	//if (typeof module === 'object') {window.module = module; module = undefined;}
	try {
	    if(!window.hasOwnProperty("nodeRequire") && window.hasOwnProperty("require") ){
			window.nodeRequire = require;
			delete window.require;
			delete window.exports;
			delete window.module;
		}
	} catch (e) {
	}

</script>
<script>
	window.alert= top.alert;
	window.confirm= top.confirm;
</script>
<script>
	function getHttpServerUrl(){
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPath = curWwwPath.substring(0, pos);
		var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
		var httpUrl = localhostPath+projectName;
		return httpUrl;
	}
	
	//判断字符是否为空 空 true 非空 false
	function isEmpty(obj) {
		if(typeof obj == "undefined" || obj == null || obj == "") {
			return true;
		} else {
			return false;
		}
	}
	
	//判断是否是含零正整数 不是 true 是 false`
	function isNotNumber(obj) {
		var reg = /(^[0-9]\d*$)/;
		if(reg.test(obj)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 说明：比较两个日期的先后  
	 * ps:日期需要先进行非空判断
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * ps:参数格式 yyyy-MM-dd HH:mm:ss 或 yyyy/MM/dd HH:mm:ss
	 * @param type 时间类型（day：日期和日期时间型型；time：时间型）
	 * @returns {Boolean} 开始时间在结束时间之后 false  开始时间在结束时间之前 true 
	 */
	function compareDate(startDate,endDate,type) {
		//包含日期时间，或只包含日期
		if(type == "dayTime") {
			//替换- ，组成yyyy/mm/dd格式
			var st=new Date(startDate.replace("-","/"));
			var et=new Date(endDate.replace("-","/"));
			if(st>et){
				return false;
			} else {
				return true;
			}
		//不包含日期，只有时间
		} else if(type == "time") {
			var date = new Date();
			//获取当前年月日
			var day = date.toLocaleDateString();
			//进行拼接 yyyy/mm/dd hh:mm:ss
			var newST = day +" "+ startDate;
			var newET = day +" "+ endDate;
			var st=new Date(newST);
			var et=new Date(newET);
			if(st>et){
				return false;
			} else {
				return true;
			}
		}
	}
	//去左空格;
	 function ltrim(s){
	     return s.replace(/(^\s*)/g, "");
	 }
	 //去右空格;
	 function rtrim(s){
	     return s.replace(/(\s*$)/g, "");
	 }
	 //去左右空格;
	 function trim(s){
	     return s.replace(/(^\s*)|(\s*$)/g, "");
	 }
</script>


