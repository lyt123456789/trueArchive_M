<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<link href="images/core.css" rel="stylesheet" type="text/css" media="screen"/>   
<Link href="images/common.css" type="text/css" rel="stylesheet" />
<link href="images/index.css" type="text/css" rel="stylesheet" />
<head>
<title></title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
 	function changePage(i){
		parent.changePage(i);
	}
</script>
</head>
<body>
	<div class="bl_n_nav" style="z-index:11;">
		            <div class="bl_nav_zj"  style="z-index:11;" >
		            	 <% 
		            	 	String pages = request.getParameter("pages");
		            		System.out.println(pages);
		            	 	if(!("").equals(pages) && pages != null){
			            	 	String[] pag =  pages.split(",");
			            	 	if(pag.length > 0){
				            	 	for(int i=0;i<pag.length;i++){
				            	 		int ind = Integer.parseInt(pag[i]);
						        		if(i==0){
								           %>	
								            	<a href="#"  style="padding-top:5px;"  onclick="changePage(<%=(ind+1)%>)" id="ahref<%=(ind+1) %>" class="hot">第<%=(ind+1) %>页<span class='edit' style='font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;' title='内容有改动'></span></a>
								           <%
						            	}else{
							           %>
							            	<a href="#" onclick="changePage(<%=(ind+1)%>)" id="ahref<%=(ind+1) %>" onclick="">第<%=(ind+1) %>页<span class='edit' style='font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;' title='内容有改动'></span></a>
							           <%}
					        		}	
			            	 	}
		            	 	}
		            	   %>
	</div>
	</body>
</html>