<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top" style="height:62px;">
		<div class="wf-search-bar">
			<div class="wf-top-tool">
	            <a class="wf-btn" href="javascript:sub();">
	                <i class="wf-icon-plus-circle"></i> 授权
	            </a>
	        </div>
		</div>
	</div>
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
			<input type="hidden" name="nodeid" id="nodeid"/>
			<input type="hidden" name="formid" id="vc_formid"/>
			<input type="hidden" name="vc_roletype" id="vc_roletype"/>
			<input type="hidden" name="vc_role" id="vc_role"/>
			<input type="hidden" name="vc_limit" id="vc_limit"/>
			<input type="hidden" name="isbt" id="isbt"/>
			<input type="hidden" name="ispy" id="ispy"/>
			<input type="hidden" name="vc_tagname" id="vc_tagname"/>
			<input type="hidden" name="type" id="type" value="${type }"/>
    		<table class="list" width="100%" >
			    <tr>
			    	<td style="width: 10%;overflow: auto;">
					    <iframe src="${ctx }/permition_permitLeft.do?formid=${formid }&type=${type }&workflowid=${workflowid}" id="iframe_left" name="iframe_left" style="border: 0px;width: 100%;height: 500px;" frameborder="0"></iframe>
					</td>
			    	<td style="width: 30%;overflow: auto;">
					    <iframe src="" id="iframe_middle" name="iframe_middle" style="border: 0px;width: 100%;height: 500px;" frameborder="0"></iframe>
					 </td>
					 <td style="overflow: auto;">
					   	 <iframe src="" id="iframe_right"  name="iframe_right" style="border: 0px;width: 100%;height: 500px;" frameborder="0"></iframe>
					 </td>
			    </tr>
		    </table>
	</form>
</div>
</body> 
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
    	function sub(){ 
        	try{
        		var iframe_left=window.frames['iframe_left'];
    			if(iframe_left){
    				g.g('nodeid').value=iframe_left.clickNodeId;
    				g.g('vc_formid').value=iframe_left.formid;
    				//alert(g.g('nodeid').value);
    				//alert(g.g('vc_formid').value);
    			};
    			var iframe_middle=window.frames['iframe_middle'];
    			if(iframe_middle){
    				g.g('vc_roletype').value=iframe_middle.document.getElementById('vc_roletype').value;
    				g.g('vc_role').value=iframe_middle.document.getElementById('vc_rolename').value;
    				//alert(g.g('vc_roletype').value);
    				//alert(g.g('vc_role').value); 
    				
    				if(g.g('vc_roletype').value==''||g.g('vc_role').value==''){
						alert('请先点击添加或修改按钮');
						return;
    				};
    				
    				if(g.g('vc_roletype').value>=6){
    					var isok=iframe_middle.check();
    					if(!isok)return;
    					var id=iframe_middle.itemid;
    					var name=iframe_middle.itemName;
    					g.g('vc_role').value=id+','+name;
    					
    				}
    			};
    			var iframe_right=window.frames['iframe_right'];
    			if(iframe_right){
    				var vc_limit='';
    				var isbt='';
    				var vc_tagname='';
    				var ispy = '';
    				var isbts=iframe_right.document.getElementsByName('isbt');
    				var ispys=iframe_right.document.getElementsByName('ispy');
    				var vc_limits=iframe_right.document.getElementsByName('vc_limit');
    				var vc_tagnames=iframe_right.document.getElementsByName('vc_tagname');
    				//alert(vc_limits);alert(vc_tagnames);
    				for(var i=0;i<vc_limits.length;i++){
    					vc_limit+=i==vc_limits.length-1?vc_limits[i].value:vc_limits[i].value+",";
    					isbt+=i==isbts.length-1?isbts[i].value:isbts[i].value+",";
    					vc_tagname+=i==vc_tagnames.length-1?vc_tagnames[i].value:vc_tagnames[i].value+",";
    					ispy+=i==ispys.length-1?ispys[i].value:ispys[i].value+",";
    				};
    				g.g('vc_limit').value=vc_limit;
    				g.g('vc_tagname').value=vc_tagname;
    				g.g('isbt').value=isbt;
    				g.g('ispy').value=ispy;
    				//alert(g.g('vc_limit').value);
    				//alert(g.g('vc_tagname').value);
    			};
    		//	if(!confirm('确定授权吗？'))return;
    			alert('开始授权,请稍后。。。。');
    			g.g('thisForm').action='${ctx }/permition_addPermit.do?workflowid=${workflowid}';
    			g.g('thisForm').submit();
        	}catch(e){
				alert(e);
        	};
    	};
    
    </script>
</html>
