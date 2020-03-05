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

<div align="left"><a href="../trueWorkflow/mobileTerminalInterface_add.do">新增</a></div>
        <table  cellspacing="0" cellpadding="0" border="0" class="tab" width="100%"><thead><tr><td>序号</td><td>工作流名称</td><td>状态</td><td>创建时间</td><td>操作</td></tr></thead><tbody>
              <%JSONArray ja = JSONArray.fromObject(request.getAttribute("list"));
	JSONObject jo = null;
	for(int i=0;i<ja.size();i++){
		jo = new JSONObject();
		jo = (JSONObject)ja.get(i);
		System.out.println(jo);
		
	%>
	<tr id="<%=jo.getString("wfm_id") %>">
	<td><input name="svaluebox" value="<%=jo.getString("wfm_id") %>" type="checkbox" /></td>
	
	<td ><%=jo.getString("wfm_name") %></td>
	<td ><%=jo.getString("wfm_status").equals("0")?"调试":"运行" %></td>
	<td ><%=jo.get("wfm_createtime")%></td>
	<td ><button onclick="javascript:del('<%=jo.getString("wfm_id") %>');">删除</button>
	<a href="../trueWorkflow/mobileTerminalInterface_getWf.do?id=<%=jo.getString("wfm_id") %>">修改</a>
	<a href="../trueWorkflow/mobileTerminalInterface_openWF.do?id=<%=jo.getString("wfm_id") %>">打开</a>
	<a href="../trueWorkflow/flow.jsp">查看流程图</a>
	
	</td>
	</tr>
	<%} %>
	</tbody></table>

    <script>
    function del(id){
    	  window.location.href="../trueWorkflow/mobileTerminalInterface_deleteWF.do?id="+id;
    }
    function update(id){
        window.location.href="#";
        }
    function update(id){
        window.location.href="../trueWorkflow/mobileTerminalInterface_openWF.do?id="+id;
        }
    </script>
</body>
</html>
