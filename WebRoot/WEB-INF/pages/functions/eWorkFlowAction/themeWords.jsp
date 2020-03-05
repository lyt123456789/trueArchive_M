<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<style>
body{-moz-user-select: none;font-size:14px;}
body {
	background: #E8F0F1;
	margin: 0;
}

#fw_left label span {
	color: #FF0000;
	visibility: hidden;
	display:none;
}

.fenlei input {
	margin-right: 6px;
}

.fenlei_children table {
	margin-bottom: 6px;
	width: 410px;
	margin-left: 2px
}

.fenlei_children td {
	vertical-align: top;
}

#fw_right {
	height: 300px;
	width: 600px;
	overflow: auto;
	overflow-x: hidden;
	border: 1px #999999 solid;
	background: #FFF
}
#fw_left{
	height: 400px;
	width: 600px;
	overflow: auto;
	overflow-x: hidden;
	border: 1px #999999 solid;
	background: #FFF
}
#fw_last{
	height: 100px;
	width: 220px;
	overflow: auto;
	overflow-x: hidden;
	border: 1px #999999 solid;
	background: #FFF
}
#fw_right {
	width: 220px;
	padding-left: 10px
}

.button input {
	height: 24px;
	background: #EAEAEA;
	cursor: hand;
}
.nbutton{border:1px solid #ccc; background-color:#EFEFEF; width:100px; text-align:center;}
.title{ padding:5px 10px 5px 5px;background-color:#EFEFEF; border-bottom:1px solid #CCC; color:#0C5DAC;}
.content{ border-bottom:1px solid #CCC; padding:5px; overflow:hidden; zoom:1;}
.content label{/*overflow: hidden;text-overflow: ellipsis;white-space:nowrap;*/width:49%; float:left; display:block; line-height:24px; margin-right:1%;}
#fw_right input{vertical-align:middle; margin-right:2px;}
#fw_right label{display:block; line-height:24px;}
#fw_left input{vertical-align:middle; margin-right:5px;}
.nums{width:30px;}
.serial{width:50px;}
.nums{width:30px;}
.fcc{color:#CCC;}
.nbutton{cursor:pointer;}
.tempbox{position:absolute; left:0; top:0; z-index:3; width:auto; display:block; background-color:#FFF; padding:5px;}
.tempboxbg{position:absolute; left:0; top:0; z-index:2; width:100%; display:block; background-color:#FFF;opacity:0.7;_filter:alpha(opacity=70)}
button{width:50px;line-height:18px;background:#5876AD;color:#FFF;border:1px solid #15274B;margin-left:10px;}
</style>
</head>
<body>
<!--new html start-->  
    <table width="100% cellpadding="0" cellspacing="0" border="0">
    <div id="themes">
    </div>
    </table>
	</body>
<script src="${curl}/widgets/plugin/js/base/jquery.js"></script>
<script language="javascript">
	//控件调用返回值
	function getValue(){
		var chcs= $("input[type=checkbox]")
		var doctypeids='';
		for(var i=0;i<chcs.length;i++){
			if($(chcs[i]).attr("checked")){
				doctypeids+= chcs[i].value+',';
			}
		}
		if(doctypeids != ''){
			doctypeids = doctypeids.substring(0, doctypeids.length-1);
		}
		return doctypeids;
	}
	function cdv_getvalues(){
		return getValue();
	}
	$(document).ready(function() {
		var json=${json};
		var html = '';
		for(var i = 0 ; i <json.length; i++){
			if(json[i].checked){
				html += '<tr><td><input type="checkbox" id='+json[i].key+' name='+json[i].key+' value='+json[i].value+' checked="checked" />'+json[i].value+'</td></tr>';
			}else{
				html += '<tr><td><input type="checkbox" id='+json[i].key+' name='+json[i].key+' value='+json[i].value+' />'+json[i].value+'</td></tr>';
			}
		}
		var el=$(html).appendTo($('#themes'));
	});
</script>
</html>