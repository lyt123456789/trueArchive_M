<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
    <title>工作流列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="../trueWorkflow/demo.css" rel="stylesheet" type="text/css" />
    
    <script src="../../trueWorkflow/scripts/boot.js" type="text/javascript"></script> 
    
    <!--引入皮肤样式-->
    <link href="../../trueWorkflow/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
    <style>
    .tab{
    border-collapse:collapse;
    }
    .tab td{
    border:1px solid #eeeeee;
    line-height:2em;
    padding:3px 5px;
    text-align:center;
    }
    .tab thead td{
    line-height:1.2em;
    background-color:#eeeeee;
    }
    </style>
</head>
<body>
<form action="../trueWorkflow/mobileTerminalInterface_updatewf.do">

        <table  cellspacing="0" cellpadding="0" border="0" class="tab" width="100%"><thead><tr><td>工作流名称</td><td>状态</td><td>创建时间</td></tr></thead><tbody>
              <%JSONObject ja = JSONObject.fromObject(request.getAttribute("workflow"));
	%>
	<tr id="<%=ja.getString("uuid") %>">
	<input type="hidden" name="id" id="id" value="<%=ja.getString("uuid") %>">
	<td width="50"><input id="wfName" name="wfName" value="<%=ja.getString("workFlowName") %>"></td>
	<td><%=ja.getInt("status")==0?"调试":"运行" %></td>
	<td width="180"><%=ja.get("createTime")%></td>
	</tr>
	<button type="submit">提交</button>
<button onclick="javascript:fanhui();">返回</button>
	</tbody></table></form>
    <script>
    function sub(){
        window.location.href="../trueWorkflow/mobileTerminalInterface_updatewf.do?wfName="+document.getElementById("wfName").Value+"&id=<%=ja.getString("uuid") %>";
        }
    function fanhui(){
        window.location.href="../trueWorkflow/mobileTerminalInterface_listWF.do";
            }
    </script>
</body>
</html>
