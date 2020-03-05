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
</head>
<body>
    <div class="menu">
    	<form id="customStatusform" action="${ctx}/customStatus_add.do" method="post" onsubmit="return checkForm();">
			<input type="hidden" id="b_global" name="b_global" value="${b_global }">
			<input type="hidden" name="customStatus.lcid" id="customStatus.lcid">
    	    <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		    	 <tbody id="tbodyid">	
			    	 <tr valign="middle" >
			    		 <td align="right" width="40%">
		    	 			状态代码（KEY）:
		    	 		</td>
		    	 		<td align="center" >
		    	 			<input type="text" name="customStatus.vc_key" id="customStatus.vc_key" style="height: 22px;width:150px">
		    	 		</td>
		    	 	</tr> 
		    	 	 <tr valign="middle" >
		    	 	 	<td align="right" >
		    	 			状态名称（VALUE）:
		    	 		</td>
		    	 		<td align="center" >
		    	 			<input type="text" name="customStatus.vc_value" id="customStatus.vc_value" style="height: 22px;width:150px">
		    	 		</td>
		    	 	</tr>
	    		</tbody>
    	    </table>
    	</form>
    </div>
    <div id="showSql" style="border: 1px;width: 60%;height: 20%">
    </div>
	<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
			<li><a onclick="checkForm()" name="CmdView" class="buttonActive" href="javascript:;"><span>保存</span></a></li>
			<li><a onclick="cancel()" name="CmdView" class="buttonActive"><span>关闭</span></a></li>
		</ul>
	</div>
</body>
    <script type="text/javascript">

	    function checkForm(){
			//字段名
			var key = document.getElementById('customStatus.vc_key');
			if(!key.value){
				alert('请将状态代码填写完整');
				return;
			}
			
			
			//字段名
			var value = document.getElementById('customStatus.vc_value');
			if(!value.value){
				alert('请将状态名称填写完整');
				return;
			}
			var b_global = document.getElementById('b_global').value;
			var lcid = document.getElementById('customStatus.lcid').value;
			$.ajax({
				url:"${ctx}/customStatus_add.do",
				type:"post",
				data:{'b_global':b_global,'customStatus.vc_key':key.value,'customStatus.vc_value':value.value,'customStatus.lcid':lcid},
				async : false,
				cache : false,
				error : function() {
					alert('AJAX调用错误(customStatus_add.do)');
					return false;
				},
				success : function(msg) {
					window.returnValue=true;
					window.close();
				}
			});
			//document.getElementById('customStatusform').submit();
			
		}
 
	    function cancel(){
			window.returnValue=false;
			window.close();
		}	     
    </script>
    <script type="text/javascript">
    var hh=$(window).height();
    $('#w_list_print').height(hh-70); 
    </script>
</html>
