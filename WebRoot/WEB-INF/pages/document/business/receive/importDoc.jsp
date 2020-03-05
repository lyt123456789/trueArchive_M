 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>手动导入</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
		<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011"/>
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012"/>
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012"/>
        <script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
        <script type="text/javascript" src="${ctx}/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
        <script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/js/attachment.js"></script>
        <script type="text/javascript">
            //删除左右两端的空格
            function trim(text){
                return (text || "").replace(/^\s+|\s+$/g, "");
            }
            function isOk(){
             	var title = $("#title").val();
                if (title== "undefined" || trim(title).length == 0) {
                    alert("文件标题不能为空");
                    return false;
                }
             	var senderName = $("#senderName").val();
                if (senderName == "undefined"  || trim(senderName).length == 0) {
                    alert("来文单位不能为空");
                    return false;
                }
             	var yfdw = $("#yfdw").val();
                if (yfdw == "undefined" || trim(yfdw).length == 0) {
                    alert("印发单位不能为空");
                    return false;
                }

             	var submittm = $("#submittm").val();
                if (submittm == "undefined" || trim(submittm).length == 0) {
                    alert("发文时间不能为空");
                    return false;
                }
                 
                var recDate = $("#recDate").val();
                if (recDate == "undefined" || trim(recDate).length == 0) {
                    alert("收文时间不能为空");
                    return false;
                }                 
              
                var yffs = $("#yffs").val();
				var reg = /^[0-9]*[1-9][0-9]*$/;
                if(trim(yffs)!=""){
					if (!reg.test(yffs.replace(/(^\s*)|(\s*$)/g, ""))) {
		                alert("印发份数为正整数，请输入正确格式");
		                $("#yffs").val("");
		                return false;
	               	}
				}
            	var attsCount=getAttCounts("${instanceId}atts");
 				if(attsCount==0){
 					alert("请上传上文");
 					return false;
 				}
                return true;
            }
        </script>
        <script>
        	function setTime(){
            	var time=new Date();
				var y=time.getFullYear();
				var m=time.getMonth()+1;
				var d=time.getDate();
				var h=time.getHours();
				var mi=time.getMinutes();
				var s=time.getSeconds();
				if(m<10)m='0'+m;
				if(d<10)d='0'+d;
				if(h<10)h='0'+h;  
				if(mi<10)mi='0'+mi;  
				if(s<10)s='0'+s;  
				$("#recDate").val(y+'-'+m+'-'+d+' '+h+':'+mi+':'+s);   
            }
        </script>  
    </head>
    <body  onload="setTime();">
    <div class="bread-box"><div class="bread-title"><span class="bread-icon"></span><span class="bread-links"><span class="bread-start">当前位置：</span><span class="bread">公文管理 </span><span class="bread-split">&raquo;</span><span class="bread">手动导入</span></span><span class="bread-right-border"></span></div></div>
        <form id="importForm" action="${ctx}/rec_importDoc.do?type=webid&departmentId=${departmentId}" method="post">
				<div class="formTable" >
				<center>
					<table style="margin-top:5px;">
                         <tr>
                             <td colspan="4" class="title"><label style="height:40px;font-size:30px;line-height:40px;">来文信息</label></td>  
                         </tr>
                         <tr>
                             <td class="label"><label style="color:#FF0000">*</label>来文标题：
                             </td>
                             <td colspan="3" class="label">
                                 <input mice-input="input" type="text" id="title" name="title" size="80" />
                             </td>
                         </tr>
                        <!-- <tr>
                             <td class="label">                                   
                                                                                           主题词： 
                           	</td>
                             <td colspan="3" >
                                 <input mice-input="input" type="text" name="keyword" id="keyword" size="80"  />
                             </td>
                         </tr> -->
                         <tr>
                             <td class="label"> <label style="color:#FF0000">*</label>来文单位： </td>
                             <td colspan="3" align="left">
                                 <input mice-input="input" type="text" name="senderName" id="senderName" size="80" />
                             </td>
                         </tr>
                         <tr>
                             <td class="label"> <label style="color:#FF0000">*</label>印发单位： </td>
                             <td colspan="3" align="left">
                                 <input mice-input="input" type="text" name="yfdw" id="yfdw" size="80"/>
                             </td>
                         </tr>
                         <tr>
                             <td class="label"><font color="#FF0000"></font>来文号：</td>
                             <td colspan="3" align="left">
                                 <input mice-input="input" type="text" id="docguid" name="wh"  value="${doc.docguid}" />
                             </td>
                         </tr>
						<tr>
		                    <td class="label"><label style="color:#FF0000">*</label>发文时间：</td>
		                    <td colspan="3" align="left">	
								  <input mice-input="input" type="text" name="submittm" id="submittm" size="22" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'recDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="cursor: pointer;" />								
		                    </td>
		                </tr>
						<tr>
		                    <td class="label"><label style="color:#FF0000">*</label>收文时间：</td>
		                    <td colspan="3" align="left">			                     
								  <input mice-input="input" type="text" name="recDate" id="recDate"  size="22" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'submittm\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style="cursor: pointer;" value=""/>								
		                    </td>
		                </tr>			                
	                	<tr>
		                    <td class="label">份数：</td>
		                    <td colspan="3" align="left">			                     
								  <input mice-input="input" type="text" name="yffs" id="yffs" />								
		                    </td>
		                </tr>	
						 <tr> 
		                    <td class="label"><label style="color:#FF0000">*</label>公文类型：</td>
		                    <td colspan="3" class="label">			                     
								    <select mice-select="select" name="doctype" id="doctype" >
                                       <option value="通知" >通知</option>
                                       <option value="命令" >命令</option>
                                       <option value="决定" >决定</option>
                                       <option value="通告" >通告</option>
                                       <option value="通报" >通报</option>
                                       <option value="议案" >议案</option>
                                       <option value="报告" >报告</option>
                                       <option value="请示" >请示</option>
                                       <option value="批复" >批复</option>
                                       <option value="意见" >意见</option>
                                       <option value="函" >函</option>
                                       <option value="会议纪要" >会议纪要</option>
                                       <option value="办文" >办文</option>
                                       <option value="简报">简报</option>
                                   </select>								
		                    </td>
		                </tr>
		                <tr>
                            <td class="label">
                             <label style="color:#FF0000">*</label> 请上传正文至服务器： 
                            </td>
                    		<td colspan="3" class="label">
                    			<trueway:att id="${instanceId}atts" docguid="${instanceId}" ismain="true" uploadAble="true" showId="atts" previewAble="true" deleteAble="true" isreciveAtt="true"/>
                    		</td>
               			</tr>
		                <tr>
		                    <td class="label">正   文：</td>
		                    <td colspan="3" id="atts">
		                    </td>
		                </tr>
		                <tr>
		                     <td class="label">请上传附件至服务器：</td>
                    		 <td colspan="3" align="left">
		                        <trueway:att id="${instanceId}attsext" docguid="${instanceId}" uploadAble="true" showId="attsext" deleteAble="true" isreciveAtt="true"/> 
		                    </td>
		                </tr>
		                <tr>
		                    <td class="label">附件文件：</td>
							<td colspan="3"  id="attsext">
		                    </td>
		                </tr>
                        <tr>
                            <td colspan="4" class="label">
                              <input mice-btn="icon-export" type="button" id="toImport" value="导入">
                            </td>
                        </tr>
                 </table>
				</center>
				</div>
            <!-- 一定要有的隐藏字段 -->
            <input type="hidden"  name="writeDate" id="writeDate">
			<input type="hidden" name="doc.sendstat" id="sendstat" size="100" maxlength="100" value="${doc.sendstat}"/>
            <input type="hidden" id="instanceId" name="instanceId" value="${instanceId}" />
            <input type="hidden" id="sourceStatus" name="sourceStatus" value="${sourceStatus}" />
             <input type="hidden" id="keyword" name="keyword" value="空" />
        </form>
        <script type="text/javascript" src="${cdn_js}/sea.js"></script>
         <script type="text/javascript">
			$("#toImport").bind('click',function (){
				if(!isOk()){
                	return false;
            	}
                var today=new Date(); 
                var y=today.getFullYear();
                var m=today.getMonth()+1;
                var d=today.getDate();
                if(m<10){
                  m="0"+m;
                }
                if(d<10){
                    d="0"+d;
                }
                $("#writeDate").val(y+"-"+m+"-"+d); 
                $("#importForm").submit();            
			});
			seajs.use('lib/form',function(){
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
				$('select[mice-select]').cssSelect();
		    });  
        </script>
    </body>
</html>
