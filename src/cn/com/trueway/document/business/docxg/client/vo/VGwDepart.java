package cn.com.trueway.document.business.docxg.client.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 描述：公文部门的VO对象<br>
 * 作者：吴新星<br>
 * 创建时间：Mar 4, 2010 6:37:45 PM<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
public class VGwDepart implements Serializable {
	private static final long serialVersionUID = 5644198640881358340L;

	private GwDepart depart;

	private List<GwDepartext> subDepartextList;

	public GwDepart getDepart() {
		return depart;
	}

	public void setDepart(GwDepart depart) {
		this.depart = depart;
	}

	public List<GwDepartext> getSubDepartextList() {
		return subDepartextList;
	}

	public void setSubDepartextList(List<GwDepartext> subDepartextList) {
		this.subDepartextList = subDepartextList;
	}

}
