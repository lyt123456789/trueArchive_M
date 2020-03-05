<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>苏教国际信息化工作平台</title>
		<style>
			* {
				margin: 0px;
				padding: 0px;
			}
			a {
				text-decoration: none;
			}
			.search-cont-box {
				width: 100%;
				height: 284px;
				padding: 10px 0px;
				overflow: hidden;
				-webkit-box-sizing: border-box;
				-moz-box-sizing: border-box;
				box-sizing: border-box;
			}
			.search-cont-box:after {
				content: "";
				display: block;
				height: 0px;
				font-size: 0px;
				clear: both;
				visibility: hidden;
			}
			.search-left,.search-right {
				float: left;
				width: 50%;
				height: 100%;
				padding-top: 10px;
				-webkit-box-sizing: border-box;
				-moz-box-sizing: border-box;
				box-sizing: border-box;
			}
			.search-left {
				padding: 10px 33px 0px 23px;
				border-right: 1px solid #e8e9ec;
			}
			.search-right {
				border-left: 1px solid #e8e9ec;
			}
			.tr {
				width: 80%;
				margin: 0px auto 14px;
			}
			.search-cont-title {
				padding-left: 9px;
				margin-bottom: 17px;
				font: 16px/16px "Microsoft Yahei";
				color: #000;
			}
			.event-list li {
				margin-bottom: 23px;
				font: 14px/14px "Microsoft Yahei";
				overflow: hidden;
				list-style: square inside;
			}
			.search-right .search-cont-title {
				padding-left: 47px;
			}
			.tr label {
				display: inline-block;
				width: 25%;
				color: #505050;
				text-align: right;
				font: 14px "Microsoft Yahei";
			}
			.tr input,.tr select {
				width: 60%;
				height: 25px;
				border: 1px solid #d1d7db;
			}
			.tr .search-btn {
				display: block;
				width: 181px;
				height: 39px;
				margin:  0px auto;
				padding-left: 87px;
				font: 16px "Microsoft Yahei";
				color: #fff;
				text-align: left;
				background: url(${ctx}/images/search-btn-bg.png) no-repeat;
			}
			.c-type,.c-title {
				display: inline-block;
				vertical-align: middle;
			}
			.c-type {
				color: #e4492e;
			}
			.c-title {
				width:40%;
				height: 16px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				color: #505050;
			}
			.c-time {
				float: right;
				color: #a1a1a1;
			}
			.no-item {
				width: 100%;
				height: 100%;
				background: url(${ctx}/images/no-item.png) no-repeat center;
			}
		</style>
	</head>
	<body>
		<div class="search-cont-box">
			<div class="search-left">
				<h4 class="search-cont-title">最近办理事项</h4>
				<c:if test="${mySearchData == null }">
				<div class="no-item"></div>
				</c:if>
				<c:if test="${mySearchData != null }">
				<ul class="event-list">
				<c:forEach var="item" items='${mySearchData}'>
					<li><span class="c-type">[${item.itemType}]</span><a href="#" onclick="show('${item.link}')" class="c-title">${item.title}</a><span class="c-time">[${item.commitTime}]</span></li>
				</c:forEach>
				</ul>
				</c:if>
			</div>
			<!-- end search-left content -->
			<div class="search-right">
				<h4 class="search-cont-title">已办事项查询</h4>
				<form action="${ctx}/eworkflow_mySearch.do" method="post">
					<input type="hidden" name="status" value="1"/>
					<input type="hidden" name="userId" value="${userId}"/>
					<div class="tr"><label>类型：</label>
					<select id='itemType' name='itemType'>
						<option value=""></option>
						<c:forEach var="m" items='${mySearchItems}'>
	 						<option value="${m.vc_sxmc}" <c:if test="${itemType ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
	 					</c:forEach> 
					</select>
					</div>
					<div class="tr">
						<label>标题：</label>
						<input type="text" name="title" id="title" value="${title }" />
						</div>
					<div class="tr">
						<label>提交部门：</label>
						<select id="commitDept" class="select_showbox1" name="commitDept">
						<option value=""></option>
						<c:forEach var="d" items='${mySearchDepts}'>
	 						<option value="${d.departname}" <c:if test="${commitDept==d.departname}">selected="selected"</c:if>>${d.departname}</option>
	 					</c:forEach> 
						</select></div>
					<div class="tr">
						<label>提交人：</label>
						<input type="text" name="commitUser" value="${commitUser}" /></div>
					<div class="tr"><input type="submit" value="查询" class="search-btn"></div>					
				</form>
			</div>
			<!-- end search-right content -->
		</div>
	</body>
	<script>
	function show(href){
		 window.showModalDialog(href+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	}
	</script>
</html>