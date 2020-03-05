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
 	    <link href="${ctx}/assets-common/plugins/jquery.treetable/jquery.treetable.css" rel="stylesheet" type="text/css" media="screen"/>
        <script src="${ctx}/assets-common/plugins/jquery.treetable/jquery.treetable.js" type="text/javascript"></script>		
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
		url:"${ctx}/menu_getMenus.do",
		success:function(result){
			var data = eval("("+result+")");
			var dataHtml = '';
			dataHtml += '<table id="example-advanced" class="tw-table-normal tw-table-select"><thead></tr>'
			         +  '<th width="20%">菜单名称</th>'
			         +  '<th>菜单链接</th>'
			         +  '<th width="10%">外部链接</th>'
			         +  '<th width="10%">排序号</th>'
			         +  '<th width="18%">状态</th>'
			         +  '</tr></thead><tbody>'
			$.each(data,function(i,item){
				if(item.menuFaterId != item.menuId){
	                dataHtml += '<tr target="selectId"  data-tt-id="'+item.menuId+'_menu_list" data-tt-parent-id="'+item.menuFaterId+'_menu_list" rel="'+item.menuId+'">';						
				}else{
	                dataHtml += '<tr target="selectId"  data-tt-id="'+item.menuId+'_menu_list" rel="'+item.menuId+'">';					
				}
				dataHtml += '<td style="width:20%;" class="tl" menuId="'+item.menuId+'">';
				if(item.havaChild){
					dataHtml +='<span controller="true">'+item.menuName+'</span>';
				}else{
					dataHtml += item.menuName;
				}
				dataHtml +='</td><td class="tl">&nbsp;&nbsp;&nbsp;&nbsp;';
				if(item.menuExtLinks == '1'){
					dataHtml += item.foreignAppAddress+'/'+item.menuUrl;
				}else{
					dataHtml += item.menuUrl;
				}
				dataHtml +='</td><td align="center">';
				if(item.menuExtLinks == '1'){
					dataHtml += '是';
				}else{
					dataHtml += '否';
				}
				dataHtml += '</td><td  align="center">'
				         +  item.menuSort
				         +  '</td>'
				         +  '<td  align="center">';
				if(item.menuStatus == '1'){
					dataHtml += '<span style="color: green;">启用</span>';
				}else {
					dataHtml += '<span style="color: red;">禁用</span>';
				}
				dataHtml += '</td></tr>'
	        });
			dataHtml += '</tbody></table>';
			$('.tw-list-box').html(dataHtml);
			
			setTimeout(function(){
				$("#example-advanced").treetable({ expandable: true }).treetable("expandAll");
				var scrollHeight = $(window).height()-45;
				$('.tw-list-box').css({
					'height': scrollHeight,
					'overflow-y': 'auto'
				});
		        $('.tw-list-box table tr').on('click',function(){
		    		$('.tw-list-box table tr').filter(".tw-actived").removeClass("tw-actived");
		    		$(this).addClass("tw-actived");
		        });
			},500)			
		}
	});
}
</script>
</html>