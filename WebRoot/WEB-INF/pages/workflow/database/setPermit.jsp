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
	<script type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>
<base target="_self"/>
<body>
<form id="permitform"  name="permitform"  method="post" action="${ctx}/dataCenter_addPermit.do" style="font-family: 宋体; font-size: 12pt;">
	<input type="hidden" id="modId" name="modId" value="${modId}">
	<input type="hidden" id="dicId" name="dicId" value="${dicId}">
	<input type="hidden" id="tableInfoSize" name="tableInfoSize" value="${tableInfoSize}">
	<input type="hidden" id="matchId" name="matchId" value="${matchId}">
      	<table id="tabletitleInfo" style="height:10px;width:98%;table-layout:fixed;"cellspacing="0" cellpadding="0" >
			<tr style="  background-color: #999999;height: 30px;width:100%;color:#fff;">
				<th>本地表字段</th>
				<th>字段中文名</th>
				<th>是否显示</th>
				<th>是否为查询条件</th>
				<th>是否设为回写字段</th>
				<th>过滤条件</th>
				<th>排序号</th>
			</tr>
		</table>
		<div style="width:100%;height:320px; overflow-y:scroll; overflow-x:hidden;">
			<table id="tableInfo"  border="0" cellspacing="0" >
			<c:forEach var="c" items="${cpLists}" varStatus="n">
				<tr>
				<td style="width:18%;text-align:center;">
					<input  type="hidden" id="id_cp${n.count-1}" name="id_cp${n.count-1}" value="${c.id}" />
					<input  type="hidden" id="matchId_cp${n.count-1}" name="matchId_cp${n.count-1}" value="${c.matchId}" />
					<input  type="hidden" id="colName_cp${n.count-1}" name="colName_cp${n.count-1}" value="${c.colName}" />
					<input  type="hidden" id="colName_Ccp${n.count-1}" name="colName_Ccp${n.count-1}" value="${c.colName_C}" />
					<input  type="hidden" id="columnType_cp${n.count-1}" name="columnType_cp${n.count-1}" value="${c.columnType}" />
					${c.colName}
				</td>
				<td style="width:15%;text-align:center;">
					${c.colName_C}
				</td>
				<td style="width:15%;text-align:center;">
					<input type="radio" id="is_Show_cp${n.count-1}" name="is_Show_cp${n.count-1}" value="0" <c:if test="${'0' eq c.is_Show}">checked</c:if>>否
					<input type="radio" id="is_Show_cp${n.count-1}" name="is_Show_cp${n.count-1}" value="1" <c:if test="${'1' eq c.is_Show}">checked</c:if>>是
				</td>
				<td style="width:15%;">
					<input type="radio" id="is_Search_cp${n.count-1}" name="is_Search_cp${n.count-1}" value="0" <c:if test="${'0' eq c.is_Search}">checked</c:if>>否
					<input type="radio" id="is_Search_cp${n.count-1}" name="is_Search_cp${n.count-1}" value="1" <c:if test="${'1' eq c.is_Search}">checked</c:if>>是
				</td>
				<td style="width:15%;">
					<input type="radio" id="is_Back_cp${n.count-1}" name="is_Back_cp${n.count-1}" value="0" <c:if test="${'0' eq c.is_Back}">checked</c:if>>否
					<input type="radio" id="is_Back_cp${n.count-1}" name="is_Back_cp${n.count-1}" value="1" <c:if test="${'1' eq c.is_Back}">checked</c:if>>是
				</td>
				<td style="width:28%;">
					<c:if test="${'2' != c.columnType}" >
					<input type="text" id="filter_cp${n.count-1}" name="filter_cp${n.count-1}" value="${c.filter}"/>
					</c:if>
					<c:if test="${'2' == c.columnType}" >
					<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" readonly="readonly"  id="filter_cp${n.count-1}_begin" name="filter_cp${n.count-1}_begin" value="${c.filter_begin}"/>
					-
					<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" readonly="readonly"  id="filter_cp${n.count-1}_end" name="filter_cp${n.count-1}_end" value="${c.filter_end}"/>
					</c:if>
				</td>
				<td style="width:15%;">
					<input type="text" style="width:80px;" id="sort_cp${n.count-1}" name="sort_cp${n.count-1}"  value="${c.sort}" /> 
				</td>
				</tr>
			</c:forEach>
		    </table>
		</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
		<li><a class="buttonActive" onclick="savePermit();" id="save" ><span>保存</span></a></li>
		<li><a class="buttonActive" onclick="javascript:window.close();"  ><span>关闭</span></a></li>
		
		</ul>
</div>
</form>
</body>
<script type="text/javascript">
function savePermit(){
	var flag = true;
	if(flag){
		$.ajax({   
	        url : '${ctx}/dataCenter_addPermit.do',
	        type : 'POST',   
	        cache : false,
	        async : false,
	        error : function() {  
	            alert('dataCenter_addPermit.do调用错误！');
	        },
	        data : $('#permitform').serialize(),
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
