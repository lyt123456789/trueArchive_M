<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <meta name="description" content="">
	    <meta name="keywords" content="">
		<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerbase.jsp"%>
        <title>Document</title>
        <style>
            html,body {
                overflow:hidden;
            }        
			body{
				margin:0;
				padding:0;
				font-size:12px;
				font-family: "Microsoft Yahei", Helvetica, Tahoma, sans-serif;				
			}	
		    .tw-ulList {
				height: 35px;
				overflow: hidden;
				border-bottom: #3b62a5 2px solid;
				padding: 5px 0 0;
				margin: 0 0 5px 0;	
			}
            .tw-ulList li {
				font-family: "Microsoft Yahei", Helvetica, Tahoma, sans-serif;
				height: 30px;
				line-height: 30px;
				float: left;
				overflow: hidden;
				margin:0 5px 0 0;
				background-color: #b2b2b2;
				color: #FFF;
				font-size: 14px;
				padding: 0 10px;
				cursor: pointer;			
			}
            .tw-ulList li.cur {
			    background-color: #345ba0;
			}
			.tw-list-box {
			    margin-top:5px;
			}							
		</style>
    </head>
    <body>
        <div class="tw-wrapper">
		    <ul class="tw-ulList">
		    	<c:forEach var="item" items="${sites}" varStatus="status">
		    		 <li <c:if test="${status.count == 1}">class="cur"</c:if> data-siteid="${item[0]}">${item[1]}</li>
		    	</c:forEach>
			</ul>
		</div>
		<div class="tw-list-box"></div>
    </body>
<script type="text/javascript">
$(function(){
	var siteId = $('.tw-ulList li').attr('data-siteid');
    $('.tw-ulList li').on('click',function(){
    	$('.tw-ulList li').removeClass('cur');
    	$(this).addClass('cur');
    	siteId = $(this).attr('data-siteid');
    	getMenus(siteId);
    });
    
    getMenus(siteId);
    
});

function getMenus(siteId){
	$.ajax({
		async:true,//是否异步
		type:"POST",//请求类型post\get
		cache:false,//是否使用缓存
		dataType:"text",//返回值类型
		data:{
			"siteId":siteId
		},
		url:"${ctx}/role_getRoles.do",
		success:function(result){
			var data = eval("("+result+")");
			var dataHtml = '';			
			dataHtml += '<table class="tw-table-list tw-table-select"><thead><tr>'
			         +  '<th width="15%">序号</th>'
			         +  '<th align="center">角色名称</th>'
			         +  '<th align="center" width="30%">状态</th>'
			         +  '<th align="center" width="20%">排序号</th>'
			         +  '</tr></thead><tbody>';
			         
			$.each(data,function(i,item){
				dataHtml += '<tr>'
				         +  '<td align="center" id="'+item.roleId+'" class="item" >'+(i+1)+'</td>'
				         +  '<td >'+item.roleName+'</td>';
				if(item.roleStatus == '1'){
					dataHtml += '<td align="center"><font style="color: green;">启用</font></td>';
				}else if(item.roleStatus == '0'){
					dataHtml += '<td align="center"><font style="color: red;">禁用</font></td>';
				}else{
					dataHtml += '<td align="center">暂无数据</td>';
				}         
			    dataHtml += '<td align="center">'+item.roleSort+'</td>'
			        	 +  '</tr>'
			});		        
			dataHtml += '</tbody></table>';
			$('.tw-list-box').html(dataHtml);	
			
			setTimeout(function(){
				var scrollHeight = $(window).height()-45;
				$('.tw-list-box').css({
					'height': scrollHeight,
					'overflow-y': 'auto'
				});
		        $('.tw-list-box table tr').on('click',function(){
		    		$('.tw-list-box table tr').filter(".tw-actived").removeClass("tw-actived");
		    		$(this).addClass("tw-actived");
		        });
			},500);				
		}
	});
	
}
</script>
</html>