<!DOCTYPE html>
<%@include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="cn.com.trueway.base.util.*" %>
<%@page import="java.util.List" %>
<%@page import="cn.com.trueway.workflow.core.pojo.Department"%>
<html>
<head>
<title>公文详细信息</title>
<style>
</style>
<!--表单样式-->
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
</head>
<body>
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="#" target="_self" onclick="history.back()"><span>返回</span></a></li>
		</ul>
	</div>
	 <div class="pageHeader" >
		<div class="searchBar" >
			<span class="bread-icon"></span><span class="bread-links"><span
				class="bread-start">当前位置：</span><span class="bread">公文管理 </span><span
				class="bread-split">&raquo;</span><span class="bread">公文详细信息</span></span><span
				class="bread-right-border"></span>
		</div>
	</div>
        <div id="bodyXmlView" >
            <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
                <tr >
                    <td class="bgs ls">主送:</td>
                    <td colspan="3" >${recDoc.xtoName }&nbsp;</td>
                </tr>
                <tr>
                    <td class="bgs ls">抄送:</td>
                    <td colspan="3">${recDoc.xccName }&nbsp;</td>
                </tr>
                <tr>
                    <td class="bgs ls">标题词:</td>
                    <td colspan="3">${recDoc.title }&nbsp;</td>
                </tr>
                <tr>
                    <td class="bgs ls">发文单位:</td>
                    <td colspan="3">${recDoc.sendername }&nbsp;</td>
                </tr>
                <tr>
                    <td class="bgs ls"> 印发单位:</td>
                    <td colspan="3">${recDoc.yfdw }&nbsp;</td>
                </tr>
                <tr>
                    <td class="bgs ls">文号:</td>
                    <td colspan="3">${recDoc.wh }&nbsp;</td>
                </tr>
                <tr>
                    <td class="bgs ls" width="25%;" >类型:</td>
                    <td  width="25%;">${recDoc.doctype }&nbsp;</td>
                    <td class="bgs ls"  width="25%;" >优先级:</td>
                    <td  width="25%;"><c:if test="${recDoc.priority==0}">
                    	特急
					</c:if>
					<c:if test="${recDoc.priority==1}">
                    	紧急
					</c:if>
					<c:if test="${recDoc.priority==2}">
                    	急件
					</c:if>
					<c:if test="${recDoc.priority==3}">
                    	平件
					</c:if></td>
                </tr>
                 <tr>
                    <td class="bgs ls">正文附件:</td>
                    <td colspan="3">
                     <span id="attzwshow"></span>
                    <trueway:att onlineEditAble="false" id='${recDoc.docguid}attzw' docguid='${recDoc.docguid}attzw' showId='attzwshow'  nodeId=""  ismain="true" downloadAble="true" uploadAble="false" deleteAble="false" previewAble="false" tocebAble="false" toStampAble="false" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
               </td>
                </tr>
               	<c:if test="${not empty recDoc.beizhu}">
               		<tr>
              			<td class="label">备注:</td>
               			<td colspan="3">&nbsp;&nbsp;${recDoc.beizhu}</td>
               		</tr>
               	</c:if>
            </table>
        </div>
        <div id="unconcernedView" style="display: none">
        	<form id="unconcernedForm" action="${ctx}/rec_receivedDocList.do" method="post">
        		<input type="hidden" id="type" name="type" value="webid"/>
        		<input type="hidden" id="departmentId" name="departmentId" value="${departmentId}"/>
				<table>
					<tr>
						<td>请输入备注信息：<span id="bzError" style="color:red"></span></td>
					</tr>
					<tr>
						<td><textarea id="bzText" name="bzText" rows="10" cols="30" ></textarea></td>
					</tr>
					<tr>
						<td align="right">
							<input class="btn jQcancel" type="button"  value="取消"/>
							<input class="btn" type="button" id="backDocBtn" name="backDocBtn" value="提交"/> 
						</td>
					</tr>
				</table>  
        	</form>
        </div>
        <div id="backDocView" style="display: none">
        	<form id="backDocForm" action="rec_receivedDocList.do" method="post">
        		<input type="hidden" id="departmentId" name="departmentId" value="${departmentId}"/>
        		<input type="hidden" id="type" name="type" value="webid"/>
				<table>
					<tr>
						<td>请输入退文理由：<span id="remarkError" style="color:red"></span></td>
					</tr>
					<tr>
						<td><textarea id="remark" name="remark" rows="10" cols="30"></textarea></td>
					</tr>
					<tr>
						<td><span id="departIdError" style="color: red"></span></td>
					</tr>
					<tr>
						<td>
						<% int i=0; %>
						<select name="departId" id="departId">
							<option selected="selected" value="">请选择退文部门</option>
							<c:forEach items="<%=pageContext.getSession().getAttribute(Constant.DEPARMENT_NAMES) %>"  var="depName" varStatus="status" >
						<% List<Department> deps=(List<Department>)pageContext.getSession().getAttribute(Constant.DEPARMENT_IDS); %>
							<option  value="<%=deps.get(i) %>" >${depName}</option> 
						<% i++; %>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							<input class="btn jQcancel" type="button"  value="取消"/> 
							<input class="btn" type="button" id="untreadDocBtn" name="untreadDocBtn" value="退文"/>
						</td>
					</tr>
				</table>  
				   <input id="docguid" name="docguid" type="hidden"  value="${recDoc.docguid}"/>      	
        	</form>
        </div>
        <script type="text/javascript" src="${cdn_js}/sea.js"></script>
       <script	type="text/javascript">
       
       //触发退文
       	$('#untreadDocBtn').bind('click',function(){
			if($('#remark').val()===""){
					$('#remarkError').html('*退文理由不能为空！');
					return false;
				}
			if($('#departId').val()===""){
				$('#departIdError').html('*请选择退文的部门！');
				return false;
			}
			if(window.confirm("确定要退文吗?")){
				$.ajax({
    			    url: 'untread_untreadDoc.do',
    			    type: 'POST',
    			    cache:false,
    			    async:false,
    			    data:{'departId':$('#departId').val(),'docguid':$('#docguid').val(),'remark':$('#remark').val(),'departmentId':'${departmentId}'},
    			    error: function(){
    			        alert('AJAX调用错误');
    			    },
    			    success: function(msg){
    			        if(msg.indexOf("FAIL") ==-1){
    				        alert("退文成功");
    				        document.getElementById("backDocForm").submit();
    			        }else {
    			        	var ms=msg.split(";");
    						alert(ms[1]);
    			        }
    			    }
    			});
			}
         });
       
		//触发按钮    去掉警示提示       
		$('#remark').bind('focus',function(){
				$('#remarkError').html('');
			});
		$('#departId').bind('change',function(){
				$('#departIdError').html('');
			});
		$('#bzText').bind('focus',function(){
			$('#bzError').html('');
		});

		$('.jQcancel').bind('click',function(){
            $('#backDocView').hide();
            $('#unconcernedView').hide();
            $('#bodyXmlView').show();
			});
		$('.jQuntread').bind('click',function(){
			$('#backDocView').show();
            $('#unconcernedView').hide();
            $('#bodyXmlView').hide();
			});
		$('.jQrest').bind('click',function(){
			$('#backDocView').hide();
            $('#unconcernedView').show();
            $('#bodyXmlView').hide();
			});
		$('#backDocBtn').bind('click',function(){
			if( $('#bzText').val()==""){
					$('#bzError').html('*备注信息不能为空!');
					return false;
				}
			if(window.confirm("确定进行此操作吗？")){
				$.ajax({
    			    url: 'rec_unconcernedDoc.do',
    			    type: 'POST',
    			    cache:false,
    			    async:false,
    			    data:{'departmentId':$('#departmentId').val(),'docguid':$('#docguid').val(),'bzText':$('#bzText').val()},
    			    error: function(){
    			        alert('AJAX调用错误');
    			    },
    			    success: function(msg){
    			        if(msg.indexOf("FAIL") ==-1){
    				        alert("操作成功");
    				        $('#unconcernedForm').submit();
    			        }
    			    }
    			});
			}
		});

		$('.jQcancelUnconcern').bind('click',function(){
			if(window.confirm("确定进行“还原”操作吗？")){
				$.ajax({
    			    url: 'rec_cancelUnconcerned.do',
    			    type: 'POST',
    			    cache:false,
    			    async:false,
    			    data:{'departmentId':$('#departmentId').val(),'docguid':$('#docguid').val()},
    			    error: function(){
    			        alert('AJAX调用错误');
    			    },
    			    success: function(msg){
    			        if(msg.indexOf("FAIL") ==-1){
    				        alert("还原成功");
    				        $('#unconcernedForm').submit();
    			        }
    			    }
    			});
			}
		});
       </script>
            <script language="javascript">
		//办文
        $("#bw_entrance").bind('click',function(){
           	window.location.href = "${ctx}/pm_toBw.do?guid=" + '${recDoc.docguid}' + "&workflowDefineId=4918E77E-84B4-4A1A-938D-7C4315280DC1";
        });
        $("#bwdetail").bind('click',function(){
           	window.location.href = "${ctx}/pm_getBwdDetail.do?bwguid=${bw.bwGuid}&bwtype=${bw.bwtype}&viewFromReceive=true";  
        });
        <c:if test="${recDoc.status == 0}">
      	//检查该文是否已经有相同的处理过的文
        function checkbw(title,wh){
	        var arr = null;
        	$.ajax({
			    url: 'send_checkbw.do',
			    type: 'POST',
			    cache:false,
			    async:false,
			    data:{'title':title,'wh':wh},
			    error: function(){
			        alert('服务器或网络异常,请重试');
			    },
			    success: function(msg){
				    arr = eval(""+msg+"");
			    }
			});
			return arr;
        }
    	var arr = checkbw('${recDoc.title}','${recDoc.wh}');
    	if(arr!==null&&arr){
        	var str = '<table><tr><td>该文已经有类似的处理过的文</td></tr>';
        	for(var i in arr){
            	var index = Number(i)+1;
            	var alink = "<td><a href='${ctx}/pm_getBwdDetail.do?bwguid="+arr[i].bwguid+"&bwtype="+arr[i].bwtype+"&viewFromReceive=true' target='_self'>查看办文单"+index+"</a></td>";
				str += "<tr>"+alink+"</str>";
            }
            str += "</table>";
        	art.dialog({
        		left: '50%',
        		top: '10%',	
				lock: false,
			    content: str,
			    id: 'EF893L'
			});
    	}
    	</c:if>
   	</script>
      </body>
</html>