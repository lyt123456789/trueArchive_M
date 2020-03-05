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
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <title>文号表单</title>
	    
    	
		<link rel="stylesheet" href="${ctx}/assets/themes/default/css/style.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/assets/themes/default/css/other.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/assets/themes/css/print.css" type="text/css" media="print"/> 
		<link rel="stylesheet" href="${curl}/docStatic/css/reset.css" type="text/css">
    	<link rel="stylesheet" href="${curl}/docStatic/css/titanic.css" type="text/css">
		<script src="${ctx}/assets/js/jquery.min.js" type="text/javascript"></script>
		<style>
		    .wf-page-list li a {
		    	margin-right:0;
		    }
		    .wf-page-list li {
		    	margin-right:5px;
		    	cursor: pointer;
		    }
		    .wf-page-info{
		    	display:none;
		    }
		    .wf-pagination{
		    	height: 30px;
		    }
		    .wf-list-wrap{
		    	overflow: hidden;
		    }
		</style>
	</head>
        <script>
        	//实例模型值
        	var model = '${model}';
        	//小类数组
        	var classArray = model.split(";")[0].split(",");
        	//字段数组
        	var filedArray = model.split(";")[1].split(",");
        	//序号长度
        	var size = '${modelVals.content}';
        	if(size.indexOf("_")!=-1){
            	size=size.substring(size.indexOf("_")+1);
        	}
        	//var model = '(2012)方法啊啊啊啊啊1111不';
        	function use(nh,zh,lwdwlx,xh){
        		var str = formate(nh,zh,lwdwlx,xh);
            	if(str.indexOf('号')===-1) str += '号';
            	// 正则 取 后面值
            	var regx= /\d+号/g;
            	// 获取元素id号
            	var wenhao = classArray[2].substring(classArray[2].indexOf("$")+1,classArray[2].lastIndexOf("$"));;
            	var matchs =str.match(regx);
            	if(matchs != ''){
            		 var  sd =this.parent.document.getElementById(wenhao);
            		 sd.value =matchs[0].substring(0,matchs[0].length-1);
            	}
        	}
        	
        	function set(xh){
        		parent.$(".xh-class").val(xh);
        	}
        	
        	
			//gjdz,fwnh,fwxh
        	function formate(fwnh,gjdz,lwdwlx,fwxh){
            	//如果序号的size是0则不格式化
        		if(size === '0'){
                	while(fwxh.indexOf('0')===0){
                		fwxh = fwxh.replace('0','');
                	}
            	}
            	var value = "";
            	for(var i = 0, j = classArray.length; i < j; i++){
                	var v = classArray[i];
                	var rep = eval(""+filedArray[i]+"");
                	//value += v.replace(/\$\w+\$/g,rep);
                	if(v.indexOf("$")===-1){
                    	value += v;
                	}else{
                    	if(v.indexOf("$$")!==-1){
                        	v1 = v.split("$$")[0]+"$";//lwdwlx
                    		value += v1.split("$")[0]+lwdwlx+v1.split("$")[2];
                        	v = "$"+v.split("$$")[1];//fwxh
                    		value += v.split("$")[0]+rep+v.split("$")[2];
                    	}else{
                			value += v.split("$")[0]+rep+v.split("$")[2];
                    	}
                	}
            	}
            	
            	return value;
        	}
        </script>
        
        <body>
<div class="wf-layout">
	<div class="wf-list-top">
			<form  id="numUnused" name="numUnused"  action="${curl}/docNumber_getDocNumUnused.do" method="post">
				<div class="searchBar" >
				</div>
				<input type="hidden" name="defid" value="${defid}">
				<input type="hidden" name="xh" value="${xh}">
				<input type="hidden" name="gjdz" value="${gjdz}">
				<input type="hidden" name="nh" value="${nh}">
				<input type="hidden" name="lwdwlx" value="${lwdwlx}">
				<input type="hidden" name="modelVal" value="${modelVal}">
				<input type="hidden" name="isChildWf" value="${isChildWf}">
				<input type="hidden" name="itemId" value="${itemId}">
				<input type="hidden" name="model" value="${model}">
				<input type="hidden" name="webid" value="${webid}">
			</form>
	</div>
	
	<c:if test="${bwlist ne null}">
			<div class="wf-list-wrap">
				<form id="thisForm" name="thisForm" action="${curl}/docNumber_getDocNumUnused.do"  method="post" >
	       		 	<div class="titanic-list-area">
			            <p>您还可以选择下列暂未使用的文号：</p>
			            <dl class="titanic-list-dl">
			                <dt class="clearfix">
			                    <span class="w-60">未使用的文号</span>
			                    <span class="w-40">操作</span>
			                </dt>
			                <c:forEach items="${bwlist}" var="bw" varStatus="status">
								
								<dd class="clearfix">
				                    <span class="w-60">${bw.bwlx}[${bw.bwnh}]${bw.bwxh}号</span>
				                    <span class="w-40"><a href="javascript:;" data-num="153" class="use-btn" onclick="set('${bw.bwxh}')">使用</a></span>
				                </dd>
							</c:forEach>
			                <!-- <dd class="clearfix">
			                    <span class="w-60">宁政发〔2018〕153号</span>
			                    <span class="w-40"><a href="javascript:;" data-num="153" class="use-btn" onclick="useTitanicNum(this)">使用</a></span>
			                </dd>
			                <dd class="clearfix">
			                    <span class="w-60">宁政发〔2018〕154号</span>
			                    <span class="w-40"><a href="javascript:;" data-num="154" class="use-btn" onclick="useTitanicNum(this)">使用</a></span>
			                </dd> -->
			            </dl>
			        </div>
			    </form>
				<!-- <form id="thisForm" name="thisForm" action="${curl}/docNumber_getDocNumUnused.do"  method="post" >
						<table class="wf-fixtable" layoutH="116">
							<thead>
								<tr>
									<td width="30%" style="font-weight:bold;text-align:center;">未使用的文号</td>
									<td width="30%" style="font-weight:bold;text-align:center;">操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bwlist}" var="bw" varStatus="status">
									<tr > 
										<%-- <td style="text-align:center;"><script type="text/javascript">document.write(formate('${bw.bwnh}','${bw.bwlx}','${bw.lwdwlx}','${bw.bwxh}'));</script></td> --%>
										<td style="text-align:center;">${bw.bwlx}<c:if test="${bw.bwnh !=null && bw.bwnh != undefined && bw.bwnh != '' }">[</c:if>${bw.bwnh}<c:if test="${bw.bwnh !=null && bw.bwnh != undefined && bw.bwnh != '' }">]</c:if>${bw.bwxh}号</td>
										<%-- <td style="text-align:center;"><a href="JavaScript:use('${bw.bwnh}','${bw.bwlx}','${bw.lwdwlx}','${bw.bwxh}');">使用</a></td> --%>
										<td style="text-align:center;"><a href="JavaScript:set('${bw.bwxh}');">使用</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</form> -->
			</div>
			
		</c:if>
        <c:if test="${fwlist ne null}">
       		 <div class="wf-list-wrap">
       		 	<form id="thisForm" name="thisForm" action="${curl}/docNumber_getDocNumUnused.do"  method="post" >
       		 	<div class="titanic-list-area">
		            <p>您还可以选择下列暂未使用的文号：</p>
		            <dl class="titanic-list-dl">
		                <dt class="clearfix">
		                    <span class="w-60">未使用的文号</span>
		                    <span class="w-40">操作</span>
		                </dt>
		                <c:forEach items="${fwlist}" var="fw" varStatus="status">
							
							<dd class="clearfix">
			                    <span class="w-60">${fw.jgdz}[${fw.fwnh}]${fw.fwxh}号</span>
			                    <span class="w-40"><a href="javascript:;" data-num="153" class="use-btn" onclick="set('${fw.fwxh}')">使用</a></span>
			                </dd>
						</c:forEach>
		                <!-- <dd class="clearfix">
		                    <span class="w-60">宁政发〔2018〕153号</span>
		                    <span class="w-40"><a href="javascript:;" data-num="153" class="use-btn" onclick="useTitanicNum(this)">使用</a></span>
		                </dd>
		                <dd class="clearfix">
		                    <span class="w-60">宁政发〔2018〕154号</span>
		                    <span class="w-40"><a href="javascript:;" data-num="154" class="use-btn" onclick="useTitanicNum(this)">使用</a></span>
		                </dd> -->
		            </dl>
		        </div>
		        </form>
       		 	
       			 <!-- <form id="thisForm" name="thisForm" action="${curl}/docNumber_getDocNumUnused.do"  method="post" >
					<table class="wf-fixtable" layoutH="116">
						<thead>
							<tr>
								<td width="30%" style="font-weight:bold;text-align:center;">未使用的文号</td>
								<td width="30%" style="font-weight:bold;text-align:center;">操作</td>
							</tr>
						</thead>
						
						<c:forEach items="${fwlist}" var="fw" varStatus="status">
							<tr > 
								<%-- <td style="text-align:center;"><script type="text/javascript">document.write(formate('${fw.fwnh}','${fw.jgdz}','','${fw.fwxh}'));</script></td> --%>
								<td style="text-align:center;">${fw.jgdz}[${fw.fwnh}]${fw.fwxh}号</td>
								<%-- <td style="text-align:center;"><a href="JavaScript:use('${fw.fwnh}','${fw.jgdz}','','${fw.fwxh}');">使用</a></td> --%>
								<td style="text-align:center;"><a href="JavaScript:set('${fw.fwxh}');">使用</a></td>
							</tr>
						</c:forEach>
				    	
					</table>
				</form> -->
			</div>
        </c:if>
	
	
	<div class="wf-list-ft" id="pagingWrap"  style="position: fixed;left: 0;right: 0;bottom: 20px;height:30px">
		
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script>
	 //分页相关操作代码
	 window.onload=function(){ 
		 	if(parent._selectIndex){
				selectIndex = parent._selectIndex
				parent._selectIndex = null
			} else {
				selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);
			}
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/docNumber_getDocNumUnused.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('numUnused');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		 
</script>

</html>
