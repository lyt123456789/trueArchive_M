<!DOCTYPE HTML >
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ page import="cn.com.trueway.base.util.*" %>
<html>
    <head>
        <title>直接发文</title>
        <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
        <script src="${cdn_js}/base/jquery.js" type="text/javascript"></script>
        <script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
        <script type="text/javascript">
	       	var mainSender={},subSender={};
	       	var mainNum=5,subNum=5;
	       	//主送选择	
	    	function selectSupman(fWidth, fHeight){
	    		var obj ={};
	    		obj.dep=mainSender;
	    		obj.num=mainNum; 
	    		var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment.do?t='+new Date().getTime(), obj, 
	    			'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
				if (retVal == null)
	    				return;
	    			mainSender=retVal.src; //Object
	    			mainNum=retVal.num; //String
	    			$("#xto").val(_getValue(retVal.src,['name','id','nums','gzname'],'|').join(';'));
	    			$("#xtoname").val(_getValue(retVal.src,['name'],'|').join(';'));
	    			//alert( document.getElementById("xto").value);
	    	}

	    	//抄送选择
	    	function selectSubman(fWidth, fHeight){
	    		var obj ={};
	    		obj.dep=subSender;
	    		obj.num=subNum;
	    		var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment.do?t='+new Date().getTime(), obj, 
	    			'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
	    			if (retVal == null)
	    				return;
	    			subSender=retVal.src; //Object
	    			subNum=retVal.num; //String
	    			$("#xcc").val(_getValue(retVal.src,['name','id','nums','gzname'],'|').join(';'));
	    			$("#xccname").val(_getValue(retVal.src,['name'],'|').join(';'));
	    	}
	
	    	//获得信息
	    	//demo _getValue(obj,['id','name','parentid'],'|')
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
        <script language="javascript" type="text/javascript">
	     function trim(str){
	        return str.replace(/^\s+|\s+$/g, "");
	     }
			
		function init(){
			var msg = '${msg}';
			if(msg=='success'){
				alert("发送成功！");
				 window.close();
			}else if(msg=='error'){
				alert("发送失败！");
			}
		}
    </script>
    </head>
    <base target="_self"/>
    <body onload="init()" style="width: 700px;">
    <div id="mask" class="formTable" style="width:700px;">
       	 <form id="sendForm" action="${ctx}/send_directSendDoc.do?instanceId=${instanceId}"  method="post" style="width:700px;">
            	<center>
                       <table class="tbl-main" >
                       <!-- 
                           <tr>
                               <td height="24" colspan="4" align="center" valign="middle" ><font size="5">中共南通市委(<input mice-input="input" type="text" id="gzdbt" name="doc.gzdbt" size="10" style="font-size:20px; " value="${doc.gzdbt}" />)发文稿纸</font></td>
                           </tr>
                        -->
                           <tr>
                               <td align="right" width="29%">
                                   <font color="#FF0000"></font>主送：
                               </td>
                               <td colspan="3"  align="left">
                                   <input mice-input="input" type="text" id="xtoname" name="doc.xtoName" size="50" value="${doc.xtoName}" readonly="readonly"/>
                                   <input mice-btn="icon-search" id="selectXTO" type="button" class="btn" value="选 择" onClick="selectSupman(990,550)">
                                   <input name="doc.xtoFull" type="hidden" id="xto" value="${doc.xtoFull }" />
                               </td>
                           </tr>
                           <tr>
                               <td align="right">抄送：</td>
                               <td colspan="3" align="left">
                                   <input mice-input="input" type="text" id="xccname" name="doc.xccName" size="50" value="${doc.xccName}" readonly="readonly"/>
                                   <input mice-btn="icon-search" id="selectXCC" type="button" class="btn" value="选 择" onClick="selectSubman(990,550)">
                                   <input name="doc.xccFull" type="hidden" id="xcc" value="${doc.xccFull }" />
                               </td>
                           </tr>
                           <tr>
                               <td align="right">
                                   <font color="#FF0000">*</font>标题 ：
                               </td>
                               <td colspan="3" align="left">    
                                   <input mice-input="input" type="text" name="doc.title" id="title" size="50" maxlength="100" value="${doc.title }" />
                               </td>
                           </tr>
                           <tr>
	                            <td align="right">
	                            	<font color="#FF0000"></font> 请上传正文至服务器：
	                            </td>
	                    		<td colspan="3" align="left">
	                    			<input id="cebid" name="doc.cebid" type="hidden" value="${doc.cebid}" />
	                        		<trueway:att id="${instanceId}att" docguid="${instanceId}" ismain="true" showId="attshow"  uploadAble="true" deleteAble="true" previewAble="true" tocebAble="true" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
	                    		</td>
               			   </tr>
                           <tr>
	                            <td align="right">
	                            </td>
	                    		<td colspan="3" align="left" id="attshow"></td>
               			   </tr>
			                <tr>
			                     <td align="right">
	                                 	 请上传附件至服务器：
	                             </td>
	                    		 <td colspan="3" align="left">
			             			  <trueway:att id="${instanceId}attext" docguid="${instanceId} "  uploadAble="true" showId="attextshow" deleteAble="true" previewAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss" />
			                    </td>
			                </tr>
			                <tr>
	                            <td align="right">
	                            </td>
	                    		<td colspan="3" align="left" id="attextshow">
	                    		</td>
               			   </tr>
                           <tr>
                               <td align="right">
                               	<font color="#FF0000"></font> 主题词：
                               </td>
                               <td colspan="3" align="left" >
                                   <input mice-input="input" type="text" name="doc.keyword" id="keyword" size="50" maxlength="100" value="${doc.keyword }" />
                               </td>
                           </tr>
                           
                             <tr>
                               <td align="right"> 
                               	<font color="#FF0000">*</font>紧急程度：  
                               </td>
                                <td align="left">
                                   <select mice-select="select" name="doc.priority" id="priority" value="${doc.priority }">
                                       <option value="3" <c:if test="${doc.priority == '3'}">selected</c:if>>平件</option>
                                       <option value="2" <c:if test="${doc.priority == '2'}">selected</c:if>>急件</option>
                                       <option value="1" <c:if test="${doc.priority == '1'}">selected</c:if>>紧急</option>
                                       <option value="0" <c:if test="${doc.priority == '0'}">selected</c:if>>特急</option>
                                   </select>
                               </td>
                               <td align="right">公开方式：</td>
                               <td colspan="3" align="left">
                                    <select mice-select="select" name="doc.gkfs" id="gkfs" >
                                	<option value="主动公开" <c:if test="${doc.gkfs =='主动公开'}">selected="selected"</c:if>>主动公开</option> 
                                       <option value="依申请公开" <c:if test="${doc.gkfs =='依申请公开'}">selected="selected"</c:if>>依申请公开</option>
                                   </select>
                               </td>
                           </tr> 
                           <tr>
                               <td  align="right">
                            	   <font color="#FF0000"></font>  印发单位：
                               </td>
                               <td align="left">
                                   <input mice-input="input" type="text" name="doc.yfdw" id="yfdw" size="20" value="中共南通市委"/>
                               </td>
                               <td  align="right">&nbsp;签发人：</td>
                               <td align="left">
                                   <input mice-input="input" type="text" name="doc.qfr" id="qfr" size="15" value="${doc.qfr}" maxlength="20"/>
                               </td>
                           </tr>
                           <tr>
                           	<td  align="right">&nbsp;印发日期：</td>
                               <td align="left">
                            	  <input mice-input="input" type="text" name="doc.yfrq" id="yfrq" class="Wdate" onclick="WdatePicker()" class="wdate" onkeydown="return false;"/>	
                               </td>
                               <td align="right">印发份数：</td>
                               <td colspan="3" align="left">
                                   <input mice-input="input" type="text" name="doc.yffs" id="yffs" size="3" maxlength="5" value="${doc.yffs }"/>
                               </td>
                           </tr>
                         
                           <tr>
                           	  <td align="right"> 	
                               	发文号：
                               </td>
                               <td align="left" colspan="2">
                              	 <trueway:dn value="" name="doc.fwxh" defineId="${defineId}" tagId="dn" webId="${webId}" displayVlaue="生成文号"/>
                               </td>
                               <td></td>
                           </tr>
                             <tr>
                             <td align="right">类型：</td>  
                               <td align="left">
                                   <select mice-select="select" name="doc.doctype" id="doctype" value="${doc.doctype }">
                                       <option value="通知" <c:if test="${doc.doctype == '通知'}">selected</c:if>>通知</option>
                                       <option value="命令" <c:if test="${doc.doctype == '命令'}">selected</c:if>>命令</option>
                                       <option value="决定" <c:if test="${doc.doctype == '决定'}">selected</c:if>>决定</option>
                                       <option value="通告" <c:if test="${doc.doctype == '通告'}">selected</c:if>>通告</option>
                                       <option value="通报" <c:if test="${doc.doctype == '通报'}">selected</c:if>>通报</option>
                                       <option value="议案" <c:if test="${doc.doctype == '议案'}">selected</c:if>>议案</option>
                                       <option value="报告" <c:if test="${doc.doctype == '报告'}">selected</c:if>>报告</option>
                                       <option value="请示" <c:if test="${doc.doctype == '请示'}">selected</c:if>>请示</option>
                                       <option value="批复" <c:if test="${doc.doctype == '批复'}">selected</c:if>>批复</option>
                                       <option value="意见" <c:if test="${doc.doctype == '意见'}">selected</c:if>>意见</option>
                                       <option value="函" <c:if test="${doc.doctype == '函'}">selected</c:if>>函</option>
                                       <option value="会议纪要" <c:if test="${doc.doctype == '会议纪要'}">selected</c:if>>会议纪要</option>
                                       <option value="办文" <c:if test="${doc.doctype == '办文'}">selected</c:if>>办文</option>
                                       <option value="简报" <c:if test="${doc.doctype == '简报'}">selected</c:if>>简报</option>
                                   </select>
                               </td>
                               <td  align="right">发文机构：</td>
                               <td colspan="3" align="left">
                                   <select mice-select="select" name="doc.sender" id="docsender" value="${doc.sender}">
                                       <c:choose>
                                           <c:when test="${empty webInfos}">
                                               <option>出现问题，请联系管理员</option>
                                           </c:when>
                                           <c:otherwise>
                                               <c:forEach var="webInfo" items="${webInfos}">
                                                   <option value="${webInfo.departmentId}">${webInfo.departmentName}</option>
                                               </c:forEach>
                                           </c:otherwise>
                                       </c:choose>
                                   </select>
                               </td>
                           </tr>
               			<tr>
			                <td colspan="5" align="center" >
	                     		<input mice-btn="icon-docs" class="btn" id="btnsubmit" type="button" value="提交"/>
	                     	</td>
               			</tr>
                       </table>
                  </center>
            <!-- 一定要有的隐藏字段 -->
            <input type="hidden" id="instanceId" name="instanceId" value="${instanceId }" />
            <input type="hidden" id="doc.docguid" name="doc.docguid" value="${instanceId }" />
            <input type="hidden" id="type" name="type" value="no" />
        </form>
        </div> 
        <script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
        <script type="text/javascript" src="${cdn_js}/sea.js"></script>
        <script>
        	$('#btnsubmit').bind('click',function(){
                var title = $('#title').val(); 
                if (title == "" || title == null || trim(title).length == 0) {
                    alert("标题不能为空");
                    return false;
                }
                $('#sendForm').submit();
                window.returnValue = 'ok';
                window.close();
            });
            $(function(){
				init();
            });
        	function loadCss(){
	        	seajs.use('lib/form',function(){
	    			$('input[mice-btn]').cssBtn();
	    			$('input[mice-input]').cssInput();
	    			$('select[mice-select]').cssSelect();
	    	    });
        	}
        	loadCss();
        </script>
    </body>
</html>
