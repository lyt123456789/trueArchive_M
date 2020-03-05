package cn.com.trueway.document.business.util;

public class DocXgConst {

	public final static String[] DOC_TYPES = new String[] { "通知", "命令", "决定",
			"通告", "通报", "议案", "报告", "请示", "批复", "意见", "函", "会议纪要", "办文", "简报" };

	public static boolean isLegalDocType(String docType) {
		for (int i = 0; i < DOC_TYPES.length; i++) {
			String type = DOC_TYPES[i];
			if (type.equals(docType)) {
				return true;
			}
		}
		return false;
	}

	// 优先级CODE
	public final static Long PRIORITY_CODE_0 = 0l;
	public final static Long PRIORITY_CODE_1 = 1l;
	public final static Long PRIORITY_CODE_2 = 2l;
	public final static Long PRIORITY_CODE_3 = 3l;

	// 优先级ZH
	public final static String PRIORITY_NAME_0 = "特急";
	public final static String PRIORITY_NAME_1 = "紧急";
	public final static String PRIORITY_NAME_2 = "急件";
	public final static String PRIORITY_NAME_3 = "平件";

	public static String getPriorityNameByCode(Long code) {
		if (code == PRIORITY_CODE_0) {
			return PRIORITY_NAME_0;
		} else if (code == PRIORITY_CODE_1) {
			return PRIORITY_NAME_1;
		} else if (code == PRIORITY_CODE_2) {
			return PRIORITY_NAME_2;
		} else if (code == PRIORITY_CODE_3) {
			return PRIORITY_NAME_3;
		}
		return PRIORITY_NAME_0;
	}

	public static Long getPriorityCodeByName(String name) {
		if (name == null)
			return null;
		name = name.trim();
		if (name.equals(PRIORITY_NAME_0)) {
			return PRIORITY_CODE_0;
		} else if (name.equals(PRIORITY_NAME_1)) {
			return PRIORITY_CODE_1;
		} else if (name.equals(PRIORITY_NAME_2)) {
			return PRIORITY_CODE_2;
		} else if (name.equals(PRIORITY_NAME_3)) {
			return PRIORITY_CODE_3;
		}
		return null;
	}

	public final static Long STATUS_CODE_0 = 0l;
	public final static Long STATUS_CODE_1 = 1l;
	public final static Long STATUS_CODE_2 = 2l;

	public final static String STATUS_CN_0 = "未接收";
	public final static String STATUS_CN_1 = "已接收";
	public final static String STATUS_CN_2 = "已阅读";

	/**
	 * 根据状态的code获取对应的中文
	 * 
	 * @param code
	 *            见常量定义：{@link #STATUS_CODE_0}、{@link #STATUS_CODE_1}、
	 *            {@link #STATUS_CODE_2}
	 * @return
	 */
	public static String getStatusNameByCode(Long code) {
		if (code == STATUS_CODE_0) {
			return STATUS_CN_0;
		} else if (code == STATUS_CODE_1) {
			return STATUS_CN_1;
		} else if (code == STATUS_CODE_2) {
			return STATUS_CN_2;
		}
		return STATUS_CN_0;
	}

}
