var tpl={
	node:'<div style="height:500px;overflow:auto;"><table><tbody>'
		+'<tr>'
			+'<td align="right"style="width:120px;">节点名称&nbsp;:&nbsp;</td>'
			+'<td><input type="text"id="lineAttr_node_name"value="${a.node_name}"maxlength="50"class="prop"></td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">创建时间&nbsp;:&nbsp;</td>'
			+'<td><input type="text"disabled="false"id="lineAttr_node_create_time"value="${a.node_create_time}"class="prop"></td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">修改时间&nbsp;:&nbsp;</td>'
			+'<td><input type="text"disabled="false"id="lineAttr_node_modified_time"value="${a.node_modified_time}"class="prop"></td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">节点期限&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input type="text"id="lineAttr_node_dead_line"value="${a.node_dead_line}"style="width: 50px;margin-right: 10px;">'
				+'<select id="lineAttr_node_dead_line_unit">'
					+'{@each a._node_dead_line_unit as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">期限仅限工作日&nbsp;:&nbsp;</td>'
			+'<td><input type="checkbox"id="lineAttr_node_deadline_isworkday"${a.node_deadline_isworkday|coverChecked}></td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">起始任务&nbsp;:&nbsp;</td>'
			+'<td><input type="checkbox"id="lineAttr_node_initiate_tasks"${a.node_initiate_tasks|coverChecked}></td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">全局流程状态&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_global_process_custom"class="prop">'
					+'<option value="">请选择...</option>'
					+'{@each a._node_global_process_custom as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">当前流程状态&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_current_process_custom"class="prop">'
					+'<option value="">请选择...</option>'
					+'{@each a._node_current_process_custom as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">默认表单&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_default_form"class="prop">'
					+'<option value="">请选择...</option>'
					+'{@each a._node_default_form as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">默认模板&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input onclick="showTemplateList(\'lineAttr_node_default_template\',this)" type="button" id="" value="选择模板" />'
				+'<input type="hidden" id="lineAttr_node_default_template" value="${a.node_default_template}" maxlength="50" class="prop"/>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">红头模板&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input onclick="showRedTapeTemplateList(\'lineAttr_node_redtape_template\',this)" type="button" id="" value="选择红头模板" />'
				+'<input type="hidden" id="lineAttr_node_redtape_template" value="${a.node_redtape_template}" maxlength="50" class="prop"/>'
			+'</td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">允许协商&nbsp;:&nbsp;</td>'
			+'<td><input type="checkbox"id="lineAttr_node_allow_consultation"${a.node_allow_consultation|coverChecked}></td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">允许委托&nbsp;:&nbsp;</td>'
			+'<td><input type="checkbox"id="lineAttr_node_allow_delegation"${a.node_allow_delegation|coverChecked}></td>'
		+'</tr>'
		+'<tr style="display:none">'
			+'<td align="right">允许抄送&nbsp;:&nbsp;</td>'
			+'<td><input type="checkbox"id="lineAttr_node_allow_cc"${a.node_allow_cc|coverChecked}></td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">存储过程&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_procedure_list"class="prop">'
				+'<option value="">请选择...</option>'
				+'{@each a._node_procedure_list as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">路由类型&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select onchange="resetUser(\'lineAttr_node_bdUser\')"id="lineAttr_node_route_type"class="prop">'
					+'<option value="">请选择...</option>'
					+'{@each a._node_route_type as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">节点用户组&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input onclick="showUserGroupList(\'lineAttr_node_staff\',this)" type="button" id="" value="选择用户组" />'
				+'<input type="hidden" id="lineAttr_node_staff" value="${a.node_staff}" maxlength="50" class="prop"/>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">默认人员&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input onclick="showNodeList(\'lineAttr_node_bdUser\',this,\'lineAttr_node_staff\')"type="button"id=""value="${a._node_bdUser.gdjdmes}"/>'
				+'<input type="hidden"id="lineAttr_node_bdUser"value="${a._node_bdUser.node_bdUser}"maxlength="50"class="prop"/>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">发送至人员类型&nbsp;:&nbsp;</td>'
		+'<td>'
			+'<select id="lineAttr_node_emptype"class="prop">'
				+'<option value="">请选择...</option>'
				+'{@each a._node_emptype as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
			+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否默认部门&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isdefdep"class="prop">'
				+'{@each a._node_isdefdep as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">发起交办&nbsp;:&nbsp;</td>'
			+'<td>'
			+'<select id="lineAttr_node_startJb" class="prop">'
				+'<option value="">请选择...</option>'
				+'{@each a._node_startJb as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
			+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否回复&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isReply"class="prop">'
				+'{@each a._node_isReply as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">待办类型&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_work_state" class="prop">'
					+'<option value="">请选择...</option>'
					+'{@each a._node_work_state as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">公文交换&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_isexchange"class="prop">'
					+'<option value="">请选择...</option>'
					+'{@each a._isexchange as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">自动分发&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_child_merge"class="prop">'
				+'{@each a._node_child_merge as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否自循环&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_self_loop"class="prop">'
				+'{@each a._node_self_loop as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">表单延用&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_form_continue"class="prop">'
				+'{@each a._node_form_continue as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否作废&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_iszf"class="prop">'
				+'{@each a._node_iszf as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否双屏幕展示&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_doubleScreen"class="prop">'
				+'{@each a._node_doubleScreen as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">允许清稿&nbsp;:&nbsp;</td>'
		+'<td>'
			+'<select id="lineAttr_node_allowfair"class="prop">'
			+'{@each a._node_allowfair as item}'
				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
			+'{@/each}'
			+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否套打&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_allowprint"class="prop">'
				+'{@each a._node_allowprint as item}'
				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否选择正文模板&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_zwTemSel"class="prop">'
				+'{@each a._node_zwTemSel as item}'
				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">发文&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_send_file" class="prop">'
				+'{@each a._node_sendfile as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否设置完成时限&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_iswcsx"class="prop">'
				+'{@each a._node_iswcsx as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否办结提醒&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isEndRemind"class="prop">'
				+'{@each a._node_isEndRemind as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否联合发文&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_islhfw"class="prop">'
				+'{@each a._node_islhfw as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否复签&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_iscountersign"class="prop">'
				+'{@each a._node_iscountersign as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否自动走完第一步&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isoverfirststep"class="prop">'
				+'{@each a._node_isoverfirststep as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否盖章&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isseal"class="prop">'
				+'{@each a._node_isseal as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否必须上传附件&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isUploadAttach"class="prop">'
				+'{@each a._node_isUploadAttach as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">允许上传附件&nbsp;:&nbsp;</td>'
		+'<td>'
			+'<select id="lineAttr_node_allowUpload"class="prop">'
			+'{@each a._node_allowUpload as item}'
			+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
			+'{@/each}'
			+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">附件名作为标题&nbsp;:&nbsp;</td>'
		+'<td>'
			+'<select id="lineAttr_node_isAttachAsTitle"class="prop">'
			+'{@each a._node_isAttachAsTitle as item}'
			+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
			+'{@/each}'
			+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">附件是否编辑&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isEdit"class="prop">'
				+'{@each a._node_isEdit as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否隐藏&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isdisplay"class="prop">'
				+'{@each a._node_isdisplay as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否可以关注&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isfollow"class="prop">'
				+'{@each a._node_isfollow as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否可以补发&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isreissue"class="prop">'
				+'{@each a._node_isreissue as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否支持表单复制&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_form_copy"class="prop">'
				+'{@each a._form_copy as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否支持强制拿回&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_forceback"class="prop">'
				+'{@each a._node_forceback as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">是否可在移动端办理&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_doinmobile"class="prop">'
				+'{@each a._node_doinmobile as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">是否自动关闭窗口&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isautoclosewin"class="prop">'
				+'{@each a._node_isautoclosewin as item}'
					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">意见是否排序&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_comment_sort" class="prop">'
					+'{@each a._node_comment_sort as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">意见关联的相关节点&nbsp;:&nbsp;</td>'
			+'<td>'
			+'<input onclick="showChildWfNodeList(\'lineAttr_node_child_nodeIds\',this)" type="button" id="" value="选择关联意见节点" />'
			+'<input type="hidden" id="lineAttr_node_child_nodeIds" value="${a.node_child_nodeIds}" maxlength="50" class="prop"/>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否分批&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_send_again" class="prop">'
					+'{@each a._node_send_again as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">是否使用请阅意见框&nbsp;:&nbsp;</td>'
		+'<td>'
		+'<select id="lineAttr_node_new_input" class="prop">'
		+'{@each a._node_new_input as item}'
		+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
		+'{@/each}'
		+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否退回&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_send_back" class="prop">'
					+'{@each a._node_send_back as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否一键办理&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_one_key_handle" class="prop">'
					+'{@each a._node_one_key_handle as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td align="right">是否一键办理无落款&nbsp;:&nbsp;</td>'
		+'<td>'
		+'<select id="lineAttr_node_auto_noname" class="prop">'
		+'{@each a._node_auto_noname as item}'
		+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
		+'{@/each}'
		+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否过虑非本部门人员&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_skip_user" class="prop">'
					+'{@each a._node_skip_user as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否自动办理&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_auto_send" class="prop">'
					+'{@each a._node_auto_send as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">多少天后自动办理&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input type="text"id="lineAttr_node_auto_send_days"value="${a.node_auto_send_days}"style="width: 50px;margin-right: 10px;">天'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否展示痕迹按钮&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_show_markbtn" class="prop">'
					+'{@each a._node_show_markbtn as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否过滤下一步节点&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_skip_nextnodes" class="prop">'
					+'{@each a._node_skip_nextnodes as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">上一节点用户组&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input onclick="showUserGroupList(\'lineAttr_node_lastStaff\',this)" type="button" id="" value="选择用户组" />'
				+'<input type="hidden" id="lineAttr_node_lastStaff" value="${a.node_lastStaff}" maxlength="50" class="prop"/>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">下一节点是否自动办理&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_isautosend" class="prop">'
					+'{@each a._node_isautosend as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">是否为督办节点&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_issupervision" class="prop">'
					+'{@each a._node_issupervision as item}'
						+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">排序号&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input type="text"id="lineAttr_node_sort_number"value="${a.node_sort_number}"style="width: 50px;margin-right: 10px;">'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">节点提前提醒时间&nbsp;:&nbsp;</td>'
			+'<td>'
				+'<input type="text"id="lineAttr_node_tqtxsj_line"value="${a.node_tqtxsj_line}"style="width: 50px;margin-right: 10px;">小时'
			+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td align="right">设置提醒内容:&nbsp;&nbsp;</td>'
			+'<td>'
				+'<select id="lineAttr_node_txnr_column" class="prop">'
				+'<option value="">请选择...</option>'
					+'{@each a._node_txnr_column as item}'
						+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
					+'{@/each}'
				+'</select>'
				+'<input type="button" value="√" onclick="addTxnr()"><br>'
				+'<textarea type="text" style="height:20px" id="lineAttr_node_txnr_txnrNames"  class="prop">${a.node_txnr_txnrNames}</textarea>'
				+'<input type="hidden" id="lineAttr_node_txnr_txnrIds" value="${a.node_txnr_txnrIds}" class="prop">'
				+'<input type="button" value="×" onclick="deleteAllTxnr()">'
				+'<div style="color:red;">例：2013年{姓名}3月{性别}4日<br>字段如有修改请重新编辑</div>'
			+'</td>'
		+'</tr>'
		+'</tbody></table></div>',
    canvas:'<table><tbody>'
    	+'<tr>'
    		+'<td align="right" style="width:80px;">流程名称&nbsp;:&nbsp;</td>'
    		+'<td><input type="text" id="lineAttr_flow_name" value="${a.flow_name}" maxlength="50" class="prop"></td>'
    	+'</tr>'
    	+'<tr>'
    		+'<td align="right">创建时间&nbsp;:&nbsp;</td>'
    		+'<td><input type="text" disabled="false" id="lineAttr_flow_create_time" value="${a.flow_create_time}" class="prop"></td>'
    	+'</tr>'
    	+'<tr>'
    		+'<td align="right">修改时间&nbsp;:&nbsp;</td>'
    		+'<td><input type="text" disabled="false" id="lineAttr_flow_modified_time" value="${a.flow_modified_time}" class="prop"></td>'
    	+'</tr>'
    	+'<tr>'
    		+'<td align="right">工作日历&nbsp;:&nbsp;</td>'
    		+'<td>'
    			+'<select id="lineAttr_flow_work_calendar" class="prop">'
    			+'{@each a._flow_work_calendar as item}'
    				+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
    			+'{@/each}'
    			+'</select>'
    		+'</td>'
    	+'</tr>'
    	+'<tr style="display:none">'
    		+'<td align="right">起始任务&nbsp;:&nbsp;</td>'
    		+'<td><input type="text" id="lineAttr_flow_initiate_tasks" value="${a.flow_initiate_tasks}" class="prop"></td>'
    	+'</tr>'
    	+'<tr style="display:none">'
	    	+'<td align="right">缺省查询表格&nbsp;:&nbsp;</td>'
	    	+'<td>'
	    		+'<select id="lineAttr_flow_default_query_form" class="prop">'
	    			+'<option value="">请选择...</option>'
	    			+'{@each a._flow_default_query_form as item}'
	    			+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
	    			+'{@/each}'
	    		+'</select>'
	    	+'</td>'
    	+'</tr>'
    	+'<tr>'
    	+'<td align="right">流程状态&nbsp;:&nbsp;</td>'
    	+'<td>'
    		+'<select id="lineAttr_flow_status" class="prop">'
    			+'{@each a._flow_status as item}'
    				+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
    			+'{@/each}'
    		+'</select>'
    	+'</td>'
    	+'</tr>'
    	+'<tr>'
	    	+'<td align="right">标题:&nbsp;&nbsp;</td>'
	    	+'<td>'
	    		+'<select id="lineAttr_flow_title_table" onchange="BaseTool.prototype.setOption(this);" class="prop">'
	    			+'<option value="">请选择...</option>'
	    			+'{@each a._flow_title_table as item}'
	    				+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
	    			+'{@/each}'
	    		+'</select><br>'
	    		+'<select id="lineAttr_flow_title_column" class="prop">'
	    			+'<option value="">请选择...</option>'
	    			+'{@each a._flow_title_column as item}'
	    				+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
	    			+'{@/each}'
	    		+'</select>'
	    		+'<input type="button" value="√" onclick="addTitle()"><br>'
	    		+'<input type="text" id="lineAttr_flow_initiate_titleNames" value="${a.flow_initiate_titleNames}" class="prop">'
	    		+'<input type="hidden" id="lineAttr_flow_initiate_titleIds" value="${a.flow_initiate_titleIds}" class="prop">'
	    		+'<input type="button" value="×" onclick="deleteAllTitle()">'
	    		+'<div style="color:red;">例：2013年{姓名}3月{性别}4日<br>字段如有修改请重新编辑</div>'
	    	+'</td>'
    	+'</tr>'
    	+'</tbody></table>',
    line:'<table><tbody>'
    	+'<tr style="display:none">'
    		+'<td align="right" style="width:100px;">路由类型&nbsp;:&nbsp;</td>'
    		+'<td>'
    			+'<select id="lineAttr_line_route_type" class="prop">'
    			+'{@each a._line_route_type as item}'
    				+'<option value="${item.ov}" ${item.osd}>${item.on}</option>'
    			+'{@/each}'
    			+'</select>'
    		+'</td>'
    	+'</tr>'
    	+'<tr>'
    		+'<td align="right">人员选择&nbsp;:&nbsp;</td>'
    		+'<td><textarea id="lineAttr_line_conditions" cols="25" rows="4" value="" class="prop" uid="${a.line_id}">${a.line_conditions}</textarea><input type="button" value="选择人员" onclick="chooseCondition();"></input></td>'
    	+'</tr>'
    	+'<tr>'
		+'<td align="right">条件选择&nbsp;:&nbsp;</td>'
		+'<td><input type="hidden" id="lineAttr_line_choice_rule" value="${a.line_choice_rule}"></input><textarea id="lineAttr_line_choice_condition" cols="25" rows="4" value="" class="prop">${a.line_choice_condition}</textarea><input type="button" value="条件设置" onclick="chooseNextNode();"></input></td>'
		+'</tr>'
    	+'<tr>'
	    	+'<td align="right">方向&nbsp;:&nbsp;</td>'
	    	+'<td>'
	    		+'<input type="checkbox" value="1" id="lineAttr_line_arrow1" ${a.line_arrow|arrowFX}>反向'
	    		+'<input type="checkbox" value="2" id="lineAttr_line_arrow2" ${a.line_arrow|arrowZX}>正向'
	    	+'</td>'
	    +'</tr>'
	    +'<tr>'
	    	+'<td align="right">备注&nbsp;:&nbsp;</td>'
	    	+'<td><input type="text" id="lineAttr_line_remark" value="${a.line_remark}" class="prop"></td>'
	    +'</tr>'
	    +'</tbody></table>',
    childnode:'<table><tbody>'
    	+'<tr>'
    		+'<td align="right" style="width:120px;">节点名称&nbsp;:&nbsp;</td>'
    		+'<td><input type="text" id="lineAttr_node_name" value="${a.node_name}" maxlength="50" class="prop"/></td>'
    	+'</tr>'
    	+'<tr>'
	    	+'<td align="right"style="width:120px;">子流程&nbsp;:&nbsp;</td>'
	    	+'<td>'
    			+'<input type="hidden" id="lineAttr_node_name" />'
    			+'<select onchange="return false;document.getElementById(\'lineAttr_node_name\').value=this.options[this.options.selectedIndex].text;" id="lineAttr_node_wfc_name" class="prop">'
    				+'<option value="">请选择...</option>'
    				+'{@each a._node_wfc_name as item}'
    					+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
    				+'{@/each}'
    			+'</select>'
    		+'</td>'
    	+'</tr>' 
    	+'<tr>'
    	+'<td align="right">子流程类型&nbsp;:&nbsp;</td>'
    	+'<td>'
    		+'<select id="lineAttr_node_wfc_ctype"class="prop">'
    			+'<option value="">请选择...</option>'
	    		+'{@each a._node_wfc_ctype as item}'
	    			+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
	    		+'{@/each}'
	    	+'</select>'
    	+'</td>'
    +'</tr>'
	+'<tr>'
	+'<td align="right"style="width:120px;">办结自动跳转节点&nbsp;:&nbsp;</td>'
	+'<td>'
		+'<select  id="lineAttr_node_wfc_nodeName" class="prop">'
			+'<option value="">请选择...</option>'
			+'{@each a._node_wfc_nodeName as item}'
				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
			+'{@/each}'
		+'</select>'
	+'</td>'
	+'</tr>' 
    +'<tr>'
	    +'<td align="right">与父流程关系&nbsp;:&nbsp;</td>'
	    +'<td>'
	    	+'<select id="lineAttr_node_wfc_relation"class="prop">'
	    		+'<option value="">请选择...</option>'
	    		+'{@each a._node_wfc_relation as item}'
	    			+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
	    		+'{@/each}'
	    	+'</select>'
	    +'</td>'
   +'</tr>'
   +'<tr >'
   		+'<td align="right">正文合并&nbsp;:&nbsp;</td>'
   		+'<td>'
   			+'<select id="lineAttr_node_wfc_mainmerger"class="prop">'
   				+'{@each a._node_wfc_mainmerger as item}'
   				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
   				+'{@/each}'
   			+'</select>'
   		+'</td>'
   +'</tr>'
   +'<tr >'
		+'<td align="right">是否合并父流程表单&nbsp;:&nbsp;</td>'
		+'<td>'
			+'<select id="lineAttr_node_wfc_need_f_form"class="prop">'
				+'{@each a._node_wfc_need_f_form as item}'
				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
			+'</select>'
		+'</td>'
	+'</tr>'
   +'<tr style="display:none;">'
		+'<td align="right">脱离父流程&nbsp;:&nbsp;</td>'
		+'<td>'
			+'<select id="lineAttr_node_wfc_outparwf"class="prop">'
				+'{@each a._node_wfc_outparwf as item}'
				+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
				+'{@/each}'
			+'</select>'
		+'</td>'
  +'</tr>'
  +'<tr style="display:none;">'
	+'<td align="right">返回待办&nbsp;:&nbsp;</td>'
	+'<td>'
		+'<select id="lineAttr_node_return_pend"class="prop">'
			+'{@each a._node_return_pend as item}'
			+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
			+'{@/each}'
		+'</select>'
	+'</td>'
	+'</tr>'
	+'<tr style="display:none;">'
	+'<td align="right">是否发文&nbsp;:&nbsp;</td>'
	+'<td>'
		+'<select id="lineAttr_node_isSend"class="prop">'
		+'{@each a._node_isSend as item}'
		+'<option value="${item.ov}"${item.osd}>${item.on}</option>'
		+'{@/each}'
		+'</select>'
	+'</td>'
	+'</tr>'
	
   +'</tbody></table>'
};
juicer.register('coverChecked', function(data){
    if(data=="true"){
        return 'checked';
    }else{
        return '';
    }
});
juicer.register('arrowFX', function(data){
    if(data%2){
        return 'checked';
    }else{
        return '';
    }
});
juicer.register('arrowZX', function(data){
    if(data/2 >= 1){
        return 'checked';
    }else{
        return '';
    }
});