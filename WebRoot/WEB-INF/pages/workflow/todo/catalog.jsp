<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
	<link href="images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
	<Link href="images/common.css" type="text/css" rel="stylesheet" />
	<link href="images/index.css" type="text/css" rel="stylesheet" />
	<style>
	.bl_n_nav a:nth-child(2n+1){
	background:#efefef
	}
	.bl_n_nav a:nth-child(2n){
	background:#FFFFFF
	}
	.bl_n_nav{
	box-shadow:0px 3px 4px 0 rgba(0,0,0,0.15)
	width:80px;
	}
	</style>
	<head>
		<title>表单目录</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
			$(document).ready(function(){
				var html = "";
				var data = '${filePages}';
				var jsonArr = eval("("+data+")");
				var page = 1;
				var pages = '${pages}';
				if('' != pages && null != pages){
					var qppages = pages.split(',');
					for(var i=0;i<jsonArr.length;i++){
						var jsonObj = jsonArr[i];
						
						var childHtml = '';
						if(null != jsonObj){
							for(var j=1;j<=jsonObj.pageCount;j++){
								var flag = false;
								for(var k=0;k<qppages.length;k++){
									if(page == (parseInt(qppages[k])+1)){
										flag = true;
										break;
									}
								}
								if(flag){
									childHtml += "<a href=\"#\" onclick=\"changePage('"+page+"')\" id=\"ahref"+page+"\" >第"+j+"页<span class=\"edit\" style=\"font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;\" title=\"内容有改动\"></span></a>";
								}
								page++;
							}
						}
						if('' != childHtml){
							html += "<a href=\"#\"  style=\"color: #000;font-size:14px;\"  class=\"cur\">"+jsonObj.name+"</a>" + childHtml;
						}
					}
					document.getElementById("bl_nav_zj").innerHTML = html;
				}
			});
			
			function changePage(i){
				parent.changePage(i);
                var container = $('#bl_nav_zj'),
                scrollTo = $('.hot');
                container.scrollTop(scrollTo.offset().top - container.offset().top + container.scrollTop());				
			}
		</script>
	</head>
	<body>
		<div class="bl_n_nav" style="z-index:11;"  >
			<div class="bl_nav_zj" id="bl_nav_zj"  style="z-index:11;" >
				
			</div>
		</div>
	</body>
</html>