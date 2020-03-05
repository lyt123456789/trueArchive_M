 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
 <%@ include file="/common/headerbase.jsp"%>
<html>
    <head>
        <title>身份验证</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    </head>
    <body>
  		<form id="verifyForm" method="post" name="verifyForm" >
  			<input type="hidden" id="zzcdFlag" name="zzcdFlag" value="${zzcdFlag }">
  			<div class="add_verify">
			<div class="dw" style="margin-top:38px;">
		        <span>姓名：</span>
		        <input id="usinglyr"  type="text" name="usinglyr" value=""/>
		    </div>
		    <div class="dw">
		        <span>证件名称：</span>
		        <select name="usingzj"  id="usingzj">
		        	<c:forEach items="${dataList}" var="item" varStatus="status">
						<option value="${item.feilName }">${item.dataName }</option>
					</c:forEach>
		        </select>
		    </div>
		    <div class="dw">
		        <span>证件号码：</span>
		        <input id="usingzjhm"  type="text" name="usingzjhm" value=""/>
		    </div>
		    <div style="text-align:center;">
		        <button id="reset" type="reset" class="btn_qx">重置</button>
		        <button id="getinfo" type="button" class="btn_ok">读取</button>
		        <button id="checking" type="button" class="btn_yz">验证</button>
		    </div>
		</div>
  	</form>
  	<OBJECT ID="TrueWaySeal" WIDTH=1 HEIGHT=1 CLASSID="CLSID:12AF3A99-8F8E-4D61-AA4E-4B08FEFEA635"></OBJECT>
	<script type="text/javascript">
		$("#reset").bind("click",function(){
			$("#usinglyr").val("");
			$("#usingzjhm").val("");
		});
	
		$("#getinfo").bind("click",function(){
			var s = TrueWaySeal.GetCartInfo();
			var obj = eval("(" + s + ")");
			if(obj.result=="0"){
				alert("设备未连接");
			}else if(obj.result=="2"){
				alert("未读取到证件");
			}else if(obj.result=="1"){
				document.getElementById("usinglyr").value=obj.name.replace(/\s+/g,"");
				document.getElementById("usingzjhm").value=obj.num.replace(/\s+/g,"");
			}
		});
		
		$("#checking").bind("click",function(){
			$.ajax({
				url:"${ctx}/using_addVerifyInfo.do?zzcdFlag=${zzcdFlag}",
				type:"post",
				async:false,
				cache: false,
				data:$("#verifyForm").serialize(),
				success:function(msg){
					var result = eval("("+msg+")");
					if(result.success=="success"){
						//调用父页面方法
						if("${zzcdFlag}"=="1"){
							parent.showSearchJsp(result.id,result.flag)
						}else{
							parent.addlayer(result.id)
						}
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					}else{
						alert("新增失败");
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			});
		});
	</script>
</body>
</html>
