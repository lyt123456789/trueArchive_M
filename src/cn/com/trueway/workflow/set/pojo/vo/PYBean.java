package cn.com.trueway.workflow.set.pojo.vo;

import java.util.List;
import java.util.Map;

public class PYBean {
	String name;
	String id;
	String sfdy; // is 存在多音字 no 不存在多音字
	String pinyin;
	String pyHead;

	String duoyin;

	Map<String, List<String>> map;

	Map<String, String> sureMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSfdy() {
		return sfdy;
	}

	public void setSfdy(String sfdy) {
		this.sfdy = sfdy;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPyHead() {
		return pyHead;
	}

	public void setPyHead(String pyHead) {
		this.pyHead = pyHead;
	}

	public String getDuoyin() {
		return duoyin;
	}

	public void setDuoyin(String duoyin) {
		this.duoyin = duoyin;
	}

	public Map<String, List<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}

	public Map<String, String> getSureMap() {
		return sureMap;
	}

	public void setSureMap(Map<String, String> sureMap) {
		this.sureMap = sureMap;
	}

}
