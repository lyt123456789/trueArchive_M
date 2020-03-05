<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>已收公文统计</title>
		<link href="${cdn_imgs}/mice/form.css" type="text/css" rel="stylesheet" />
		<link  href="${cdn_imgs}/ued.base.css" type="text/css" rel="stylesheet" />
		<link href="${cdn_imgs}/dm/css/ued.module.css"  type="text/css" rel="stylesheet" />
	</head>
	<body>
		<div class="bread-box">
			<div class="bread-title">
				<span class="bread-icon"></span>
				<span class="bread-links">
					<span class="bread-start">当前位置</span>
					<span class="bread-split">&raquo;</span>
					<span class="bread">已收公文统计 </span>
				</span>
				<span class="bread-right-border"></span>
			</div>
		</div>
		<form id="searchform" action="${ctx}/docStatistics_toReceived.do" method="post">
			<input type="hidden" id="isExport" name="isExport" value="false">
			<div style="padding-top:10px;">
				<label>部门：</label>
				<c:if test="${fn:length(departments) eq 1}">
					${departments[0].departmentName}
				</c:if>
				<c:if test="${fn:length(departments) ne 1}">
					<select mice-select="select" id="departmentId" name="receDeptId" class="departmentId">
						<c:forEach items="${departments}" var="dep">
							<option value="${dep.departmentGuid}" <c:if test="${dep.departmentGuid eq departmentId}">selected="selected"</c:if>>${dep.departmentName}</option>
						</c:forEach>
					</select>
				</c:if>
				&nbsp;&nbsp;&nbsp;
				<select mice-select="select" id="isReceivedTime" name="isReceivedTime" >
					<option value="true">收文时间</option>
					<option value="false">发文时间</option>
				</select>：
				<input mice-input="input" type="text" id="beginTime" name="beginTime" value="${beginTime }" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly">
				&nbsp;--&nbsp;
				<input mice-input="input" type="text" id="endTime" name="endTime" value="${endTime }" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="readonly">
				&nbsp;&nbsp;&nbsp;
				<label>单位：</label>
				<input mice-input="input"  type="text" id="sendDeptIdNames" name="sendDeptIdNames" value="${sendDeptIdNames}" readonly="readonly" style="cursor:hand" size="50" ondblclick="selectSupman(990,550)" title="双击选择单位" />
				<input type="hidden" id="sendDeptIds" name="sendDeptIds" value="${sendDeptIds}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" mice-btn="icon-search" id="searchBtn" value="查询">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" mice-btn="icon-excel" id="ecport_excel" value="导出">
			</div>
		</form>
		<div style="padding-top:5px;">
		<table  class="displayTable">
            <thead>
                <tr>
                    <th class="docNum">序号</th>
                    <th class="Title">单位名称</th>
                    <th class="docNum">公文数目</th>
                    <th class="docNum">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty receivedDocList}">
                        <c:forEach var="rd" items="${receivedDocList}" varStatus="status">
                            <tr>
                                <td align="center"> ${status.count} </td>
                                <td> ${rd.deptName} </td>
                                <td align="center"> ${rd.docNum} </td>
                                <td align="center"> <a href="#" onclick="viewWh('${rd.deptId}')">查看文号</a></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" align="center">
                               	 目前还没有记录
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td colspan="6">
                    	<label style="color:red">注：公文数目为0的单位将不会显示在该列表中。</label>
                    </td>
                </tr>
               </tbody>
           </table>
		</div>
       	<script type="text/javascript" src="${cdn_js}/base/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script src="${cdn_js}/sea.js"></script>
		<script src="${cdn_js}/common/dialog/artDialog.js"></script>
		<script type="text/javascript">
	       	var mainSender={},subSender={};
	       	var mainNum=5,subNum=5;
	    	function selectSupman(fWidth, fHeight){
	    		var obj ={};
	    		obj.dep=mainSender;
	    		obj.num=mainNum; 
	    		var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment4Statistics.do?t='+new Date().getTime(), obj, 
	    			'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
				if (retVal == null){
					return;
				}
    			mainSender=retVal.src; //Object
    			mainNum=retVal.num; //String
    			$("#sendDeptIds").val(_getValue(retVal.src,['id'],'|').join(','));
    			$("#sendDeptIdNames").val(_getValue(retVal.src,['name'],'|').join(','));
    			alert($("#sendDeptIds").val());
	    	}

	    	function _getValue(obj,fields,splitstr){
	    		var result=[],str='';
	    		for(n in obj){
	    			var o=obj[n];
	    			var children=o.children;
	    			if(children==null){
	    				str="";
	    				var i=0,l=fields.length;
	    				for(i;i<l;i++){
	    					if(str!=''&&l>1){
	    						str+=splitstr;
	    					}
	    					str+=o[fields[i]];
	    				}
	    				result.push(str);
	    			}
	    		}
	    		return result; //Array
	    	}
	    </script>
		<script type="text/javascript">
			function viewWh(sendDeptId){
				var isReceivedTime=$("#isReceivedTime").val();
				var beginTime = $("#beginTime").val();
				var endTime = $("#endTime").val();
				var parm = "receDeptId=${receDeptId}&isReceivedTime="+isReceivedTime+"&beginTime="+beginTime+"&endTime="+endTime+"&sendDeptId="+sendDeptId;
            	art.dialog({
    				title: '文号统计',
   					lock: true,
   				    content: '<'+'iframe src="${ctx}/docStatistics_toViewWh.do?'+parm+'" height="460" width="900" frameborder="0"></'+'iframe>',
   				    id: 'EF893K',
   				    cancelVal:'关闭',
   				    cancel:function(){}
   				});
			}

			$("#searchBtn").click(function(){
				$("#searchform").attr("action","${ctx}/docStatistics_toReceived.do");
				$("#searchform").submit();
			});
			$("#ecport_excel").click(function(){
				$("#searchform").attr("action","${ctx}/docStatistics_toReceived_Export.do");
				$("#searchform").submit();
			});
		
			seajs.use('lib/form',function(){
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
				$('.departmentId').cssSelect({trigger:true,triggerFn:function(){
					$("#isExport").val("false");
					$("#searchform").submit();
				}});
				$('#isReceivedTime').cssSelect();
		    });
			$('#departmentId').val("${receDeptId}");
			$('#isReceivedTime').val("${isReceivedTime}");
			seajs.use('lib/hovercolor',function(){
				$('table.displayTable').hovercolor({target:'tbody>tr'});
		    });
		</script>
	</body>
</html>
