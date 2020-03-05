
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>

<html>
<head>
<title>新增事项</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
</head>
<body>
<form id="itemform" method="post"
	action="${ctx}/item_add.do"
	enctype="multipart/form-data" style="font-family: 宋体; font-size: 12pt;">


<table border="1" width="95%" id="table1"
	style="BORDER-COLLAPSE: collapse" borderColor=#000000 cellSpacing=0
	cellPadding=3 width=700 bgColor=#ffffff border=1>

	<tr>
		<td colSpan=2>
		<p align="center" style="font-family: 宋体; font-size: 17pt;"><b>审批事项信息</b></p>
		</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>审批事项说明:</td>
		<td><font color="#ff0000">如果没有大事项的,那么大事项和小事项类别、编号也一样。</font></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>审批大事项类别:</td>
		<td><textarea name="item.vc_dsxmc" id="item.vc_dsxmc" rows="1"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>

	<tr>
		<td align="center" width="120" nowrap>审批大事项编码:</td>
		<td><input type="text" name="item.vc_dsxbm" id="item.vc_dsxbm" maxlength="100"
			size="30" value=""></td>
	</tr>

	<tr>
		<td align="center" width="120" nowrap>审批事项类别:</td>
		<td><textarea name="item.vc_sxmc" id="item.vc_sxmc" rows="1"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>审批事项编码:</td>
		<td><input type="text" name="item.vc_sxbm" id="item.vc_sxbm" maxlength="100"
			size="30" value=""></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>事项分类:</td>
		<td><select name="item.vc_sxfl" id="item.vc_sxfl" style="WIDTH: 223px;">
			<option value=""></option>
			<option value="行政处罚">行政处罚</option>
			<option value="行政许可">行政许可</option>
			<option value="行政征收">行政征收</option>
			<option value="行政强制">行政强制</option>
			<option value="行政审批">行政审批</option>
			<option value="行政备案">行政备案</option>
			<option value="行政裁决">行政裁决</option>
			<option value="行政给付">行政给付</option>
			<option value="其他">其他</option>
			<option value="不显示">不显示</option>
		</select></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>办件类型:</td>
		<td><select name="item.vc_bjlx" id="item.vc_bjlx" style="WIDTH: 223px;">
			<option value=""></option>
			<option value="即办件">即办件</option>
			<option value="承诺件">承诺件</option>
			<option value="上报件">上报件</option>
			<option value="联办件">联办件</option>
			<option value="特殊办件">特殊办件</option>
		</select></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>主体性质:</td>
		<td><select name="item.vc_ztxz" id="item.vc_ztxz" style="WIDTH: 223px;">
			<option value=""></option>
			<option value="法定">法定</option>
			<option value="授权">授权</option>
			<option value="受委托">受委托</option>
		</select></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>办理部门:</td>
		<td><input type="text" name="item.vc_blbm" id="item.vc_blbm" maxlength="100" size="30"
			value=""></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>占地时间:</td>

		<td><input type="text" name="item.vc_zdsj" id="item.vc_zdsj" maxlength="100" size="30"
			value="">
		</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>所属委办局:</td>
		<td><select name="item.vc_sswbj" id="item.vc_sswbj" style="WIDTH: 223px;">
			<option value=""></option>
			<option value="{BFA80007-FFFF-FFFF-F573-D50400000013}">规划局</option>
			<option value="{BFA831CA-FFFF-FFFF-8A05-F819FFFFFF84}">工商局</option>
		</select></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>事项范围:</td>
		<td><input type="text" name="item.vc_sxfw" id="item.vc_sxfw" maxlength="100"
			size="30" value=""></td>
	</tr>

	<tr>
		<td align="center" width="120" nowrap>申报主体:</td>
		<td>
			<input type="hidden" name="item.vc_sbzt" id="item.vc_sbzt">
			<input type="checkbox" name="sbzt" value="个人">个人 
			<input type="checkbox" name="sbzt" value="企业">企业
			<input type="checkbox" name="sbzt" value="事业">事业 
			<input type="checkbox" name="sbzt" value="机关">机关 
			<input type="checkbox" name="sbzt" value="社团">社团
		</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>事项性质:</td>
		<td><select name="item.vc_sxxz" id="item.vc_sxxz" style="WIDTH: 223px;">
			<option value=""></option>
			<option value="即办件">即办件</option>
			<option value="承诺件">承诺件</option>
			<option value="承诺件(自办件)">承诺件(自办件)</option>
			<option value="承诺件(上报件)">承诺件(上报件)</option>
			<option value="承诺件(联办件)">承诺件(联办件)</option>
		</select></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>申办条件:</td>
		<td><textarea name="item.vc_sbtj" id="item.vc_sbtj" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>申办流程&nbsp;<br>
		文字说明:</td>
		<td><textarea name="item.vc_sbsm" id="item.vc_sbsm" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>申办流程图:</td>
		<td><input type="file" name="theFile" value=""></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>&nbsp;</td>
		<td><input type="text" name="theFileName" size="30" value="">
		<a href="ApproveItemDelPic.do?approveItemGUID=null">删 除</a></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>审批时限:</td>
		<td><input type="text" name="item.vc_spsx" id="item.vc_spsx" maxlength="3" size="30"
			value="0">（数字,单位为工作日）</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>审批时限说明:</td>
		<td><textarea name="item.vc_spsxsm" id="item.vc_spsxsm" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>法定时限:</td>
		<td><input type="text" name="item.vc_fdsx" id="item.vc_fdsx" maxlength="3"
			size="30" value="0">（数字,单位为工作日）</td>
	</tr>
	<tr style="display: none">
		<td align="center" width="120" nowrap>联办部门及事项:</td>
		<td><textarea name="item.vc_lbbm" id="item.vc_lbbm" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>收费标准:</td>
		<td><input type="text" name="item.vc_sfbz" id="item.vc_sfbz" maxlength="100"
			size="30" value=""></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>序号:</td>
		<td><input type="text" name="item.i_xh" id="item.i_xh" maxlength="6"
			size="30" value="0"></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>是否发布到外网:</td>
		<td>
			<input type="hidden" name="item.b_fbww" id="item.b_fbww">
			<input type="radio" name="fbww" value="1" checked="checked">是
			&nbsp;&nbsp;
			<input type="radio" name="fbww" value="0">否
		</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>是否网上受理:</td>
		<td>
			<input type = "hidden" name="item.b_wssl" id="item.b_wssl">
			<input type="radio" name="wssl" value="1" checked="checked">是
			&nbsp;&nbsp;
			<input type="radio" name="wssl" value="0">否</td>
	</tr>

	<tr>
		<td align="center" width="120" nowrap>联系电话：</td>
		<td><textarea name="item.vc_tel" id="item.vc_tel" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>


	<tr>
		<td align="center" width="120" nowrap>办理地点：</td>
		<td><textarea name="item.vc_bldd" id="item.vc_bldd" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>执法主体依据：</td>
		<td><textarea name="item.vc_zfztyj" id="item.vc_zfztyj" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>执法行为依据：</td>
		<td><textarea name="item.vc_zfxwyj" id="item.vc_zfxwyj" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>监督电话：</td>
		<td><textarea name="item.vc_jddh" id="item.vc_jddh" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>服务指南：</td>
		<td><textarea name="item.vc_fwzn" id="item.vc_fwzn" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>外网在线办理链接：</td>
		<td><textarea name="item.vc_bllj" id="item.vc_bllj" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>常见问题：</td>
		<td><textarea name="item.vc_cjwt" id="item.vc_cjwt" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>备注：</td>
		<td><textarea name="item.vc_bz" id="item.vc_bz" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>


	<tr>
		<td align="center" width="120" nowrap>审批事项优先级:</td>
		<td><select name="item.i_yxj" id="item.i_yxj" style="WIDTH: 223px;">
			<option value="0" selected="selected">正常</option>
			<option value="1">高</option>
			<option value="2">紧急</option>
		</select></td>
	</tr>

	<tr style="display: none">
		<td align="center" width="120" nowrap>窗口人员:</td>
		<td><input type="text" name="item.vc_ckryxm" id="item.vc_ckryxm" value=""
			style="WIDTH: 100%"></td>
	</tr>

	<!-- modify begin . 2008/12/23 yb增加定义审批属性 -->
	<tr>
		<td align="center" width="120" nowrap>是否风险点:</td>
		<td><input type="hidden" name="item.b_risk" id="item.b_risk" value="1">
			<input type="radio" name="risk" value="1" checked="checked">是
			&nbsp;&nbsp;
			<input type="radio" name="risk" value="0">否
		</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>风险点类别：</td>
		<td><input type="text" name="item.vc_risktype" id="item.vc_risktype" maxlength="6" size="30"
			value=""></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>风险点描述：：</td>
		<td><textarea name="item.vc_riskdesc" id="item.vc_riskdesc" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>风险内控的手段和结果：</td>
		<td><textarea name="item.vc_riskresult" id="item.vc_riskresult" rows="2"
			style="OVERFLOW-Y: visible; WIDTH: 100%"></textarea></td>
	</tr>
	<!-- modify end . 2008/12/23 yb增加定义审批属性 -->
	<tr>
		<td align="center" width="120" nowrap>运行状态:</td>
		<td><select name="item.vc_status" id="item.vc_status" style="WIDTH: 223px;">
			<option value="运行">运行</option>
			<option value="冻结">冻结</option>
			<option value="调试">调试</option>
			<option value="停止">停止</option>
			<option value="废止">废止</option>
			<option value="挂起">挂起</option>
		</select></td>
	</tr>

	<tr>
		<td align="center" width="120" nowrap>是否属于审批中心:</td>
		<td>
			<input type="hidden" name="item.b_spzx" id="item.b_spzx" value="1">
			<input type="radio" name="spzx" value="1" checked="checked">是
			&nbsp;&nbsp;
			<input type="radio" name="spzx" value="0">否
		</td>
	</tr>

	<tr>
		<td align="center" width="120" nowrap>是否刷卡办事:</td>
		<td>
			<input type="hidden" name="item.b_skbs" id="item.b_skbs" value="1">
			<input type="radio" name="skbs" value="1">是&nbsp;&nbsp;<input
			type="radio" name="skbs" value="0" checked="checked">否</td>
	</tr>
	<tr>
		<td align="center" width="120" nowrap>是否收费:</td>
		<td>
			<input type="hidden" name="item.b_sf" id="item.b_sf" value="1">
			<input type="radio" name="sf" value="1" checked="checked">是
			&nbsp;&nbsp;
			<input type="radio" name="sf" value="0">否</td>
	</tr>
	<tr>
		<td>收费类型</td>
		<td><select name="item.vc_sflx" id="item.vc_sflx" onchange="qta(this)"
			style="WIDTH: 223px;">
			<option value="政府规费">政府规费</option>
			<option value="税">税</option>
			<option value="中介服务收费">中介服务收费</option>
			<option value="其他">其他</option>
		</select> <input type="text" name="SFLXSM" maxlength="100" size="30" value=""
			style="display: none; float; margin-left: 5px; width: 220px;">
		</td>
	</tr>
	<tr>
		<td>收费项(财政编码)</td>
		<td><input type="text" name="item.vc_sfx" id="item.vc_sfx" maxlength="100" size="30"
			value=""></td>
	</tr>
	<tr>
		<td>收款人全称</td>
		<td><input type="text" name="item.vc_sfr" id="item.vc_sfr" maxlength="100" size="30"
			value="" style="display: none"> <select id="skzh"
			onchange="chose();">
			<option>请选择</option>


			<option value="94909808EF7C4F6A875BA00420881812">如东财政局</option>

		</select></td>
	</tr>
	<tr>
		<td>开户行</td>
		<td><input type="text" name="item.vc_khh" id="item.vc_khh" maxlength="100" size="30"
			value=""></td>
	</tr>
	<tr>
		<td>开户账号</td>
		<td><input type="text" name="item.vc_khzh" id="item.vc_khzh" maxlength="100" size="30"
			value=""></td>
	</tr>

	<tr>
		<td>办理地点</td>
		<td><input type="text" name="item.vc_khbldd" id="item.vc_khbldd" maxlength="100" size="30"
			value=""></td>
	</tr>

	<tr>
		<td>执收单位</td>
		<td><input type="text" name="item.vc_zsdw" id="item.vc_zsdw" maxlength="100" size="30"
			value="" style="display: none"> <select id="zsdws"
			onchange="chosedwmc();">
			<option>请选择</option>


			<option value="CB87F3D84E2B4188B35BFFD1C36EEB29">如东县住建局</option>

			<option value="10F18A41C83C4AF78A8264FEBFF3EE3F">房改办</option>

			<option value="5753C017D550482CA84E64F35B09DA3B">如东县海洋与渔业局</option>

			<option value="15EA5BBF776D425C8CE83420ED80D72F">如东县交运局</option>

			<option value="CB87F3D84E2B4188B35BFFD1C36EEB28">如东县工商局</option>

			<option value="10F18A41C83C4AF78A8264FEBFF3EE3D">如东县国土资源局</option>

		</select></td>
	</tr>
	<tr>
		<td>执收单位编码</td>
		<td><input type="text" name="item.vc_zsdwbm" id="item.vc_zsdwbm" maxlength="100" size="30"
			value=""></td>
	</tr>



	<tr>
		<td>其他属性</td>
		<td><input type="text" name="item.vc_qt" id="item.vc_qt" maxlength="100" size="30"
			value=""></td>
	</tr>
	<tr>
		<td>个人服务分类</td>
		<td><input type="text" name="item.vc_grfwfl" id="item.vc_grfwfl" maxlength="100" size="30"
			value=""><a href="#">选择</a></td>
	</tr>
	<tr>
		<td>企业服务分类</td>
		<td><input type="text" name="item.vc_qyfwfl" id="item.vc_qyfwfl" maxlength="100" size="30"
			value=""><a href="#">选择</a></td>
	</tr>

</table>

<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" href="javascript:checkForm();"><span>提交</span></a></li>
</ul>
</div>
</form>
</body>

<script type="text/javascript"
	src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
<script src="${cdn_js}/sea.js"></script>
<script type="text/javascript">
	function checkForm(){
		var fields = "wssl,fbww,risk,spzx,skbs,sf".split(",");
		for(var i = 0; i < fields.length; i++){
			var radios = document.getElementsByName(fields[i]);
			for(var j = 0; j < radios.length; j++){
				if(radios[j].checked){
					document.getElementById("item.b_"+fields[i]).value = radios[j].value;
				}
			}
		}

		document.getElementById('itemform').submit();
	}
</script>
</html>
