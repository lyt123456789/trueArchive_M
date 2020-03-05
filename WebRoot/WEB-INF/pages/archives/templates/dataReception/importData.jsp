<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<title>导入数据</title>
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.2.min.js" ></script>
		<script src="${pageContext.request.contextPath}/assets/plugins/layer/layer.js" type="text/javascript"></script>
	</head>
	<body>
	<form name="form1" action="" method="post" enctype="multipart/form-data">
	    <input type="hidden" name="structureId" value="${structureId }" />
	    <input type="hidden" name="treeId" value="${treeId }" />
		<table width="99%" class="table table-border table-bordered table-bg table-sort table-condensed">
			    <tr>
			        <td align="center" class="text-r">
						第一行是否为表头:
					</td>
				    <td class="text-l">
					 是
					
						<input name="excelType" type="radio" value="0"
							checked="checked" />
					</td>
				</tr>
				<tr>
					<td align="center" class="text-r">
						导入数据:
					</td>
					<td class="text-l">
					<input type="file" value="导入EXCEL文件" name="file"
							id="file" class="input-text radius size-S" onchange="readExcel(this.value)"
							onkeyup="checkValue(this.value)" />
					</td>
				</tr>
				<tr>
					<td colspan="2"  class="text-r" align="center">
						<input type="button" class="btn btn-success" height=50
							value="下一步" onclick="submitForm()" disabled id="btn" />&nbsp;&nbsp;&nbsp;&nbsp;
						<a class='maincolor' href="javascript:void(0);" onclick = "javascript:downmb();" style = "color:red">模板下载</a>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
			function checkValue(tar){
				if(!tar || tar==""){
					return false;
				}else {
					return true;
				}
			}
			function readExcel(tar){
				if(checkValue(tar) &&(tar.substr(tar.length-3) =='xls' || tar.substr(tar.length-4) =='xlsx')){   
					document.getElementById("btn").disabled=false;
					return true;
				}else{
					layer.alert('文件格式不对,请上传excel文件');   
					document.getElementById("btn").disabled=true;
					return false;
				}  
			}
			
			function submitForm(){
				var file = $('#file').val();
				var eT = "";
				if(file.substr(file.length-3) == 'xls'){
					eT = 'xls';
				}else if(file.substr(file.length-4) == 'xlsx'){
					eT = 'xlsx';
				}
				var url = "/trueArchive/dataReception_toImportDataTwo.do?eT="+eT;
				url = encodeURI(url);
			    document.forms[0].action=url;
			    document.forms[0].submit(); 
			}
		</script>
			
			<%--/*window.onbeforeunload = function (){
				 window.opener.document.getElementById("childWindowStatu").value="";
			}*/
			function downmb(){
			  var tableName=document.form1.tableName.value;
			  if(tableName=="JFXX_AJAYHZB"){
				     window.open('${ctx}/importMB/'+'ajay1.xls');
				  }else if(tableName=="JFXX_QKRYXX"){
				     window.open('${ctx}/importMB/'+'qkry1.xls');
				  }else if(tableName=="JFXX_TSJB"){
				     window.open('${ctx}/importMB/'+'tsjb1.xls');
				  }else if(tableName=="JFXX_JQXX"){
				     window.open('${ctx}/importMB/'+'jqxx1.xls');
				  }else if(tableName=="T_YW_JBQD"){
				     window.open('${ctx}/importMB/'+'jbqd.xls');
				  }else if(tableName=="T_YW_AJ_XZCFAJMXB"){
				     window.open('${ctx}/importMB/'+'xzcf.xls');
				  }else if(tableName=="T_YW_ZYWRWPFHD"){
				     window.open('${ctx}/importMB/'+'wrwpf.xls');
				  }else if(tableName=="T_YW_CFDWMD"){
				     window.open('${ctx}/importMB/'+'cfdw.xls');
				  }else if(tableName=="T_YW_WFLYCZQY"){
				     window.open('${ctx}/importMB/'+'czqy.xls');
				  }else if(tableName=="T_YW_HDZDY_CXJG"){
				     window.open('${ctx}/importMB/'+'hdzdy.xls');
				  }else if(tableName=="T_YW_CJJJD_YFLYCZ"){
				     window.open('${ctx}/importMB/'+'ylfw.xls');
				  }else if(tableName=="T_YW_CJJJD_GFLYCZ"){
				     window.open('${ctx}/importMB/'+'gtfw.xls');
				  }else if(tableName=="T_YW_SYJG_AJAYXXB"){
				     window.open('${ctx}/importMB/'+'syajay.xls');
				  } 
			  
			/*   if(tableName=="JFXX_AJAYHZB"){
			     window.open('${ctx}/importMB/'+'ajay1.xls');
			  }else if(tableName=="JFXX_QKRYXX"){
			     window.open('${ctx}/importMB/'+'qkry1.xls');
			  }else if(tableName=="JFXX_TSJB"){
			     window.open('${ctx}/importMB/'+'tsjb1.xls');
			  }else if(tableName=="JFXX_JQXX"){
			     window.open('${ctx}/importMB/'+'jqxx1.xls');
			  } */
			}--%>
		
	</body>
</html>