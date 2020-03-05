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
	<link rel="stylesheet" href="${ctx}/ued.base.css?t=2511" />
    <link rel="stylesheet" href="${ctx}/widgets/theme/dm/css/ued.module.css?t=2512" />
	<link rel="stylesheet" href="${ctx}/widgets/theme/mice/form.css?t=2512" />   
	<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/component/writeByHand/js/websign.js"></script>
	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/widgets/component/taglib/comment/js/comment.js"></script>
	<script src="${ctx}/widgets/plugin/js/sea.js"></script>  
</head>
    <body style="overflow:auto;">
		<table class="infotan mt5" width="100%" >
			<tr>
				<td class="tdcol docformTableNoBD">
					<div>
						<trueway:comment typeinAble="false" deleteAbled="false" id="${tagId}" instanceId="${instanceId}" currentStepId="${processId}"/>
					</div>
				</td>
			</tr>
		</table>
    </body>
    <script type="text/javascript">
		//以下必须有
		function loadCss(){  
	   		seajs.use('lib/form',function(){  
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
				$('select[mice-select]').cssSelect();
		    });
		}
		//以上必须有
	</script>

    <script type="text/javascript">
		function seeHandWrite(tagId,instanceId,processId){
			var ret=window.showModalDialog('${ctx}/table_showHandWrite.do?tagId='+tagId+'&instanceId='+instanceId+'&processId='+processId+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
		}
	</script>
</html>
