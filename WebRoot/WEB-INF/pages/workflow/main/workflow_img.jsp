<!DOCTYPE html>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
    <meta http-equiv="X-UA-Compatible" content="IE=5">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<link rel="stylesheet" type="text/css" href="${ctx }/flow/themes/default/easyui.css">
	<link href="${ctx }/flow/css/flowPath.css" type="text/css" rel="stylesheet" />
	<style>
			v\:*{behavior:url(#default#VML);}
	</style>
</head>

<body class="easyui-layout bodySelectNone" id="body" onselectstart="return false">
	
	<form method="post" id="postForm" name="postForm" style="display:none;">
		<input type="text" name="xml" id="xml" />
	</form>
	
	<div id="title" region="north" split="true" border="false" title="工具栏" class="titleTool">
		<img alt="后退"	title="后退" src="flow/images/retreat.png" onClick="graphUtils.undo();" class="buttonStyle"/>
		<img alt="前进"	title="前进" src="flow/images/advance.png" onClick="graphUtils.redo();" class="buttonStyle"/>
		<img alt="保存"	title="保存" src="flow/images/save1.png" onClick="graphUtils.saveXml();" class="buttonStyle"/>
		<!--<img id="baseLine2" src="flow/images/line1.png" class="buttonStyle"/>
		<img id="baseLine3" src="flow/images/line2.png" class="buttonStyle"/>
		--><img alt="单向箭头"	title="单向箭头" id="baseLine1" src="flow/images/line8.png" class="buttonStyle"/>
		<!--<img id="baseLine5" src="flow/images/line4.png" class="buttonStyle"/>
		<img id="baseLine6" src="flow/images/line5.png" class="buttonStyle"/>
		--><img alt="双向箭头"	title="双向箭头" id="baseLine4" src="flow/images/line7.png" class="buttonStyle"/>
		<img alt="开始"	title="开始" id="start" divType="mode" src="flow/images/start.png" class="buttonStyle"/>
		<img alt="进程"	title="进程" id="baseMode5" divType="mode" src="flow/images/rectangle.png" class="buttonStyle"/>
		<img alt="子流程" title="子流程" id="baseMode6" divType="mode" src="flow/images/diamond.png" class="buttonStyle"/>
		<img alt="结束"	title="结束" id="end" divType="mode" src="flow/images/end.png" class="buttonStyle"/>
	</div>
	
	<div region="center" title="绘图区" id="contextBody" class="mapContext" >
			
		<!-- Line右键菜单 -->
		<div id="lineRightMenu" class="modeRight">
			<div class="modeRightTop"></div>
			<div class="modeRightPro" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.showLinePro();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">属 性</span></div>
			<div class="modeRightDel" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.removeNode();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">删 除</span></div>
			<div class="modeRightButtom"></div>
		</div>
				
		<!-- Mode右键菜单 -->
		<div id="rightMenu" class="modeRight">
			<div class="modeRightTop"></div>
			<div class="modeRightPro" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.showModePro();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">属 性</span></div>
			<div class="modeRightDel" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.removeNode();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">删 除</span></div>
			<div class="modeRightButtom"></div>
		</div>
		
		<!-- ChildProcessMode右键菜单 -->
		<div id="childProcesRightMenu" class="modeRight">
			<div class="modeRightTop"></div>
			<div class="modeRightPro" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.showModeLct();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">观看子流程</span></div>
			<div class="modeRightPro" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.showModePro();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">属 性</span></div>
			<div class="modeRightDel" onMouseMove="this.style.backgroundColor='#c5e7f6'" onClick="graphUtils.removeNode();" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">删 除</span></div>
			<div class="modeRightButtom"></div>
		</div>

		<!-- PathBody右键菜单 -->
		<div id="pathBodyRightMenu" class="modeRight">
			<div class="modeRightTop"></div>
			<div class="modeRightPro" id="isPixel" onClick="graphUtils.showProcessPro();" onMouseMove="this.style.backgroundColor='#c5e7f6'" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">属 性</span></div>
			<div class="modeSave" onClick="graphUtils.saveXml();" onMouseMove="this.style.backgroundColor='#c5e7f6'" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">保 存</span></div>
			<div class="modeUndo" onClick="graphUtils.undo();" onMouseMove="this.style.backgroundColor='#c5e7f6'" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">后 退</span></div>
			<div class="modeRedo" onClick="graphUtils.redo();" onMouseMove="this.style.backgroundColor='#c5e7f6'" onMouseOut="this.style.backgroundColor=''"><span class="menuSpan">前 进</span></div>
			<div class="modeRightButtom"></div>
		</div>
		
	 	<div id="topCross"></div>
    	<div id="leftCross"></div>
	
	</div>
	
	<!-- 移动时的图象 -->
	<div id="moveBaseMode" class="moveBaseMode">
		<img id="moveBaseModeImg"  src="flow/images/Favourite.png" class="nodeStyle" />
	</div>
	
	<div id="prop" style="visibility: hidden;">
				Dialog Content.  
	</div>
	</body>
	
	<script src="${ctx }/flow/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${ctx }/flow/js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx }/flow/js/juicer.min.js" type="text/javascript"></script>
	<script src="${ctx }/flow/js/strawberry.dialog.js?t=20180530" type="text/javascript"></script>
	<script src="${ctx }/flow/js/strawberry.js?t=20180530" type="text/javascript"></script>
	<script>
		//重新选择条件
		function chooseCondition(){
			var id = $("#lineAttr_line_conditions").attr("uid");
			layer.open({
				type:2,
				content:"${ctx}/group_toUserDepartmentJsp.do?id="+id+"&d="+Math.random(),
				area:["800px","500px"],
				title:"选择人员",
				btn:["完成"],
				yes:function(index,layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var ret = iframeWin.choose();
					document.getElementById("lineAttr_line_conditions").value =  ret;
					layer.close(index);
				}
			});
		}
		
		
		function seeLct(id){
			//window.showModalDialog("${ctx}/mobileTerminalInterface_wrokFlowChildImgOpen.do?id="+id+"&date="+new Date(),null,"dialogWidth="+window.screen.width+"px;dialogHeight="+window.screen.height+"px");
			
			top.layer.open({
				type:2,
				content:"${ctx}/mobileTerminalInterface_wrokFlowChildImgOpen.do?id="+id+"&date="+new Date(),
				area:[(window.screen.width-50)+"px",(window.screen.height-150)+"px"],
				title:"观看子流程"
			});
		}
		
		
		function showTemplateList(id,src){
			var obj=document.getElementById(id);
			/* var returnvalue=window.showModalDialog("${ctx}/template_getTemplateForNode.do?ids="+obj.value+"&d="+Math.random(),window,'dialogWidth: 550px;dialogHeight: 400px; status: no; scrollbars: yes; Resizable: no; help: no;center:yes;');
			if(typeof(returnvalue)!='undefined'){
				obj.value=returnvalue;
				src.value='编辑';
			} */
			layer.open({
				type:2,
				content:"${ctx}/template_getTemplateForNode.do?ids="+obj.value+"&d="+Math.random(),
				area:["550px","400px"],
				title:"选择模板",
				btn:["完成"],
				yes:function(index,layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var ret = iframeWin.choose();
					obj.value=ret;
					src.value='编辑';
					layer.close(index);
				}
			});
		};

		//选择红头模板
		function showRedTapeTemplateList(id,src){
			var obj=document.getElementById(id);
			/* var returnvalue=window.showModalDialog("${ctx}/template_getTemplateForNode.do?redtape=1&ids="+obj.value+"&d="+Math.random(),window,'dialogWidth: 550px;dialogHeight: 400px; status: no; scrollbars: yes; Resizable: no; help: no;center:yes;');
			if(typeof(returnvalue)!='undefined'){
				obj.value=returnvalue;
				src.value='编辑红头模板';
			} */
			layer.open({
				type:2,
				content:"${ctx}/template_getTemplateForNode.do?redtape=1&ids="+obj.value+"&d="+Math.random(),
				area:["550px","400px"],
				title:"选择红头模板",
				btn:["完成"],
				yes:function(index,layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var ret = iframeWin.choose();
					obj.value=ret;
					src.value='编辑红头模板';
					layer.close(index);
				}
			});
		}
		
		// 选择节点用户组
		function showUserGroupList(id,src){
			var obj=document.getElementById(id);
			/* var returnvalue=window.showModalDialog("${ctx}/group_getInnerGroupForNode.do?ids="+obj.value+"&d="+Math.random(),window,'dialogWidth: 550px;dialogHeight: 400px; status: no; scrollbars: yes; Resizable: no; help: no;center:yes;');
			if(typeof(returnvalue)!='undefined'){
				obj.value=returnvalue;
				src.value='编辑';
			} */
			layer.open({
				type:2,
				content:"${ctx}/group_getInnerGroupForNode.do?workflowId=${workflowId}&ids="+obj.value+"&d="+Math.random(),
				area:["550px","400px"],
				title:"选择用户组",
				btn:["完成"],
				yes:function(index,layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var ret = iframeWin.choose();
					obj.value=ret;
					src.value='编辑';
					layer.close(index);
				}
			});
		};
		
		/**unknow added**/
		//根据用户组id获取绑定的用户
		function synGetUser(src){
			if(src.value!=''){
				$.ajax({
					url: '${ctx}/group_synGetUser.do',
	                type: 'POST',
	                cache: false,
	                async: false,
	                data:"id="+src.value,
	   			    error: function(){
	   			        alert('AJAX调用错误');
	   			    },
	   			    success: function(msg){
	   			        if(msg=='-1'){
	   						alert("数据库错误，请联系管理员!!!");
	   			        }else{ 
		   			        //先清除原先select内容
		   			        var oldSelect=document.getElementById('lineAttr_node_bdUser');
		   			     	oldSelect.options.length=0;
		   			        //添加最新select内容
	   			        	var jsobj=eval('('+msg+')');
	   			        	//var jsobj=(new Function("return "+msg))();alert(jsobj);
							if(jsobj){
								oldSelect.options.add(new Option("------请选择------",""));
								for(var i=0;i<jsobj.length;i++){
									oldSelect.options.add(new Option(jsobj[i].employee_name,jsobj[i].employee_id));
								};
							};
	   			        }
	   			    }
	   			});
			};
		};
		function addTitle(){
			var option=document.getElementById('lineAttr_flow_title_column');
			var titlename=document.getElementById('lineAttr_flow_initiate_titleNames');
			var titleid=document.getElementById('lineAttr_flow_initiate_titleIds');
			var name=option.options[option.selectedIndex].text;
			var id=option.value;
			titlename.value+="{"+name+"}";
			titleid.value+=titleid.value==''?id:','+id;
		};
		function deleteAllTitle(){
			document.getElementById('lineAttr_flow_initiate_titleNames').value='';
			document.getElementById('lineAttr_flow_initiate_titleIds').value='';
		};
		function addTxnr(){
			var option=document.getElementById('lineAttr_node_txnr_column');
			var titlename=document.getElementById('lineAttr_node_txnr_txnrNames');
			var titleid=document.getElementById('lineAttr_node_txnr_txnrIds');
			var name=option.options[option.selectedIndex].text;
			var id=option.value;
			titlename.value+="{"+name+"}";
			titleid.value+=titleid.value==''?id:','+id;
		};
		function deleteAllTxnr(){
			document.getElementById('lineAttr_node_txnr_txnrNames').value='';
			document.getElementById('lineAttr_node_txnr_txnrIds').value='';
		};
		
	</script>
	<script type="text/javascript">
	function showNodeList(id,src,nodegroupid){
		var nodegroup=document.getElementById(nodegroupid);
		if(nodegroup.value==''){
			alert('请先选择节点人员!');
			return false;
		};
		//获取被选中的路由类型lineAttr_node_route_type 
		//node_route_type 0:单人; 1:并行抢占式; 2:并行完全式;
		var node_route_type = $("#lineAttr_node_route_type option:selected").val();
		var obj=document.getElementById(id);
		//必须在url后面追加随机参数Math.random(),防止出现缓存，网页对话框可接受返回值
        //如需在子页面操作父页面，则必须把父页面window作为参数传给子页面
		//var returnvalue=window.showModalDialog("${ctx}/group_getListForNode.do?node_route_type="+node_route_type+"&nodegroup="+nodegroup.value+"&ids="+obj.value+"&d="+Math.random(),window,'dialogWidth: 700px;dialogHeight: 500px; status: no; scrollbars: yes; Resizable: no; help: no;center:yes;');
		/* if(typeof(returnvalue)!='undefined'){
			obj.value=returnvalue;
			src.value='编辑';
		} */
		
		layer.open({
			type:2,
			content:"${ctx}/group_getListForNode.do?node_route_type="+node_route_type+"&nodegroup="+nodegroup.value+"&ids="+obj.value+"&d="+Math.random(),
			area:["716px","593px"],
			title:"选择默认人员",
			btn:["完成"],
			yes:function(index,layero){
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var ret = iframeWin.choose();
				obj.value=ret;
				src.value='编辑';
				layer.close(index);
			}
		});
	};
	
	
	//更换路由类型 : 需重置人员信息
	function resetUser(id){
		var obj=document.getElementById(id);
		obj.value = "";
	}
	
	
	</script>
	<script>
		$("#flowid").bind("change",function(){
			global.baseBase.prop.flow_id = this.value;
		})
		
		jQuery(document).ready(function () {

			
			
			graphUtils = com.xjwgraph.Utils.create({
				
				contextBody : 'contextBody',
				width : 1600,
				height : 1000,
				smallMap : 'smallMap',
				mainControl : 'mainControl',
				prop : 'prop'
			
			});
			var global = com.xjwgraph.Global;
			global.parameter = {
					flow_work_calendar: [],
					flow_default_query_form: [],
					node_global_process_custom: [],
					node_current_process_custom: [],
					node_default_calendar: [],
					node_default_form: [],
					node_default_template: [],
					node_procedure_list: [],
					node_procedure: [],
					node_staff: [],
					node_lastStaff: [],
					node_emptype : []
			};
			graphUtils.nodeDrag($id("baseLine1"), true, 1);
			//graphUtils.nodeDrag($id("baseLine2"), true, 2);
			//graphUtils.nodeDrag($id("baseLine3"), true, 3);
			graphUtils.nodeDrag($id("baseLine4"), true, 4);
			//graphUtils.nodeDrag($id("baseLine5"), true, 5);
			//graphUtils.nodeDrag($id("baseLine6"), true, 6);

			var modes = jQuery("[divType='mode']");
			var modeLength = modes.length;
			
			for (var i = 0; i < modeLength; i++) {
				graphUtils.nodeDrag(modes[i]);
			}
			
			document.body.onclick = function () {

				if (!stopEvent) {
					global.modeTool.clear();
				} 
				
			};

			document.onkeydown = KeyDown;
			//alert('${xml}');
			graphUtils.loadTextXml('${xml}');
			
			global.baseBase.prop.flow_name = "${flow_name}";
			global.parameter.flow_work_calendar = jQuery.parseJSON('${calendar_list}');
			var form_list = jQuery.parseJSON('${form_list}');
			global.parameter.flow_default_query_form = form_list;
			var emp_list = jQuery.parseJSON('${emp_list}');
			global.parameter.node_emptype =emp_list;
			var title_table_list = jQuery.parseJSON('${title_table_list}');
			global.parameter.flow_title_table = title_table_list;
			var title_column_list = jQuery.parseJSON('${title_column_list}');
			global.parameter.flow_title_column = title_column_list;
			var txnr_column_list = jQuery.parseJSON('${title_column_list}');
			global.parameter.node_txnr_column = txnr_column_list;
			
			global.parameter.node_default_form = form_list;
			global.parameter.node_global_process_custom = jQuery.parseJSON('${global_list}');
			global.parameter.node_current_process_custom = jQuery.parseJSON('${current_list}');
			global.parameter.node_default_template = jQuery.parseJSON('${template_list}');
			global.parameter.node_staff = jQuery.parseJSON('${role_list}');
			global.parameter.node_lastStaff = jQuery.parseJSON('${role_list}');
			global.parameter.node_procedure_list = jQuery.parseJSON('${flow_procedure_list}');//存储过程

			//普通节点属性需要修改   使用procedure_list关键字  查询strawberry.js文件
			//******************注意***************** 工作流属性修改后无须重画流程  而节点属性相关修改后需要重画流程
			global.baseBase.prop.flow_id = "${flow_id}";
			global.baseBase.prop.flow_create_time = "${flow_create_time}";
			global.baseBase.prop.flow_modified_time = "${flow_modified_time}";
			global.baseBase.prop.flow_status = "${flow_status}";
			global.baseBase.prop.flow_title_table = "${flow_title_table}";
			global.baseBase.prop.flow_title_column = "${flow_title_column}";
			global.baseBase.prop.node_txnr_column = "${flow_title_column}";
			
			global.baseBase.prop.flow_initiate_titleNames = "${flow_initiate_titleNames}";
			global.baseBase.prop.flow_initiate_titleIds = "${flow_initiate_titleIds}";
		});
		
		
		//用于选择下个节点的判断条件
		function chooseNextNode(){
			var condition = document.getElementById("lineAttr_line_choice_condition").value;	//下个节点出现的选择条件
			var rule = document.getElementById("lineAttr_line_choice_rule").value;	//下个节点出现的选择条件
			condition = condition.replaceAll("&","%26");
			condition = condition.replace(/\+/g,"%2B");
			condition = encodeURI(condition);
			rule = encodeURI(rule);
			layer.open({
				type:2,
				content:"${ctx}/mobileTerminalInterface_toSetLineCondition.do?condition="+condition+"&rule="+rule+"&d="+Math.random(),
				area:["500px","400px"],
				title:"条件设置",
				btn:["完成"],
				yes:function(index,layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var ret = iframeWin.sub();
					var names = ret.split("^");
					document.getElementById("lineAttr_line_choice_condition").value = names[0];
					document.getElementById("lineAttr_line_choice_rule").value = names[1];
					layer.close(index);
				}
			});
		}
		
		//选择关联节点
		function showChildWfNodeList(id,src){
			var wfUId = '${wfUId}';
			var obj = document.getElementById(id);
			var url =  "${ctx}/mobileTerminalInterface_getChildWfNodeList.do?wfUId="+wfUId+"&ids="+obj.value+"&d="+Math.random();
			layer.open({
				type:2,
				content:url,
				area:["550px","400px"],
				title:"条件设置",
				btn:["完成"],
				yes:function(index,layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var ret = iframeWin.choose();
					obj.value=ret;
					layer.close(index);
				}
			});
		}
	</script>
</html>