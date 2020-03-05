package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

public class ChatDofile {

	private String count;
	
	private String itemName;
	
	// 消息类型
	private String xxlx;
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	private List<Dofile> list;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<Dofile> getList() {
		return list;
	}

	public void setList(List<Dofile> list) {
		this.list = list;
	}

	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}
	
	
}
