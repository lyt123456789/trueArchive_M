<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
</script>
</head>
<base target="_self"/>
<body>
<form id="columnMapform"  name="columnMapform"  method="post" action="${ctx}/dataCenter_addColumnMapColumn.do" style="font-family: 宋体; font-size: 12pt;">
	<input type="hidden" id="modId" name="modId" value="${modId}">
	<input type="hidden" id="dicId" name="dicId" value="${dicId}">
	<input type="hidden" id="matchId" name="matchId" value="${matchId}">
	<input type="hidden" id="tableInfoSize" name="tableInfoSize" value="${tableInfoSize}">
	<input type="hidden" id="tableName" name="tableName" value="${tableName}">
      	<table id="tabletitleInfo" style="height:10px;width:98%;table-layout:fixed;"cellspacing="0" cellpadding="0" >
			<tr style="  background-color: #999999;height: 30px;width:100%;color:#fff;">
				<th style=" width:14%">字段名称</th>
				<th style=" width:18%">描述</th>
				<th style=" width:18%">字段类型</th>
				<th style=" width:18%">匹配表名称</th>
				<th style=" width:18%">匹配表字段</th>
			</tr>
		</table>
		<div style="width:100%;height:320px; overflow-y:scroll; overflow-x:hidden;">
			<table id="tableInfo"  border="0" cellspacing="0" >
			<c:forEach var="t" items="${tableInfo_new}" varStatus="n"> 
			     <tr class="sjzdupdatelist">
			     	<td style=" width:14%">
			     	<input  style="width:90%;height:20px;line-height:20px;font-size:12px;" type="hidden" id="mapId${n.count-1}" name="mapId${n.count-1}" value="${t[9]}" />
			     	<input  style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" id="filedName${n.count-1}" name="filedName${n.count-1}" value="${t[0]}" />
			     	</td>
			     	<td style=" width:18%">
			     	<input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" id="filedEsc${n.count-1}" name="filedEsc${n.count-1}" value="${t[4]}" />
			     	</td>
			     	<td align="center" >
	   	 				<input type="hidden" name="filedType${n.count-1}" id="filedType${n.count-1}" value="${t[1]}">
	   	 			</td>
			     	<td style=" width:18%">
			     	<select id="tagTables${n.count-1}" name="tagTables${n.count-1}" style="width:90%;height:20px;line-height:20px;font-size:12px;" >
			     	<c:forEach var="d" items="${tables}">
			     	 	 <option value="${d.vc_tablename}" >${d.vc_tablename}</option>
		     	 	</c:forEach>
		     	 	</select>
			     	</td>
			     	<td style=" width:18%"	>
			     	<select id="tagFields${n.count-1}" name="tagFields${n.count-1}"  style="width:90%;height:20px;line-height:20px;font-size:12px;" >
			     	  <option value="null">无</option>
			     	  <c:forEach var="f" items="${fields}"> 
			     	  <option value="${f.vc_fieldname},${f.vc_name}" 
			     	  <c:if test="${f.vc_fieldname ==t[8] }">selected</c:if> >${f.vc_fieldname}</option>
			     	  </c:forEach>
			     	</select>
			     	</td>
			     </tr>
			</c:forEach>
		    </table>
		</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
		<li><a class="buttonActive" onclick="saveColumnMap();" id="save" ><span>保存</span></a></li>
		<li><a class="buttonActive" onclick="javascript:window.close();"  ><span>关闭</span></a></li>
		
		</ul>
</div>
</form>
</body>
<script type="text/javascript">
function saveColumnMap(){
	var flag = true;
	if(flag){
		$.ajax({   
	        url : '${ctx}/dataCenter_addColumnMapColumn.do',
	        type : 'POST',   
	        cache : false,
	        async : false,
	        error : function() {  
	            alert('链接异常，请检查网络');
	        },
	        data : $('#columnMapform').serialize(),
	        success : function(data) {
	        	var res = eval("("+data+")");
	        	if(res.success){
					alert("保存成功");
					window.returnValue= "ok"; 
					window.close();
				}else{
					alert("保存失败");
				}
	        }
	    }); 
	}
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
