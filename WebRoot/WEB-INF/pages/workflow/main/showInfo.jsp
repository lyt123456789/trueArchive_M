<!DOCTYPE html>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
    <meta http-equiv="X-UA-Compatible" content="IE=5">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<link rel="stylesheet" type="text/css" href="${ctx }/flow/themes/default/easyui.css">
	<link href="${ctx }/flow/css/flowPath_show.css" type="text/css" rel="stylesheet" />
	<style>
		body{
            margin: 0;
            padding: 0;
        }
        .container{
            width: 100%;
            margin: 0 auto 0 auto;
        }

        .node_name_container{
            height: 25px;
            padding: 10px 30px 10px 30px;
            background-color: #aaa;
        }

        .node_name{
            font-size: 20px;
            color: #FFF;
            line-height: 25px;
        }

        .fl{
            float: left;
        }
        .fr{
            float: right;
        }
        .detail_container{
            width: 435px;
            margin: 10px auto 0 auto;
            height: 50px;
            border-bottom:1px solid #ccc;


        }
        .arrow-container{
            margin: auto;
            background-image: url('./imgs/arrow_r.png');
        }

        .arrow-node{
            margin: auto;
        }
        .line{
            width: 200px;
            height: 2px;
            background-color: #ccc;
            margin-top: 10px;
        }

        .detail_container img{
            width: 15px;
            height: 10px;
            display: block;
            position: absolute;
            margin-top: 45px;
            margin-left: 424px;
        }

        .content-container{
            width: 100%;
        }
        .to_node{
            text-align: right;
        }
	</style>
</head>

<body>
	
	<div class="container"></div>
	<script>
	var href = window.location.href;
    var args = href.split('?')[1].split('&');
    var arg1 = decodeURI(args[0].split('=')[1]);
    var arg2 =decodeURI(args[1].split('=')[1]);
    var arg3 = args[2].split('=')[1];
    console.log(arg1,arg2,arg3);
    var arr = JSON2.parse('${json}');
    var html = '<div class="node_name_container"><div class="node_name fl">';
    html+=arg1;
    html+='</div><div class="node_name fr">';
    html+=arg2+'</div></div><div class="content-container">'
    for(var i=0;i<arr.length;i++){
        var item = arr[i];
        var itemHtml = '<div class="detail_container"><div class="from_node fl"><div class="node_detail_name">';
        itemHtml+=item.name1;
        itemHtml+='</div><div class="node_detail_time">';
        itemHtml+=item.time1;
        itemHtml+='</div></div><div class="to_node fr"><div class="node_detail_name">';
        itemHtml+=item.name2;
        itemHtml+='</div><div class="node_detail_time">';
        itemHtml+=item.time2;
        itemHtml+='</div></div><img src="./imgs/triangle_right_gray.png"></div>'
        console.log(itemHtml);
        html += itemHtml;
    }
    html+='</div>'
    document.getElementsByClassName('container')[0].innerHTML = html;
	</script>
		
</body>
</html>