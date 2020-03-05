package cn.com.trueway.sys.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.sys.pojo.vo.PYBean;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYin4jUtil {
	
	/**
	 * 将汉字转换为全拼
	 * 
	 * @param bean
	 * @return String
	 */
	public static PYBean getPinYin(PYBean bean) {
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		
		char[] t1 = null;
		t1 = bean.getName().toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式

		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符

				// System.out.println(t1[i]);
				List<String> list = new ArrayList<String>();
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					if(t2.length>1){
						for(int j=0;j<t2.length;j++){
							list.add(t2[j]);
						}
						list = removeList(list);
						if(list.size()>1){
							bean.setSfdy("is");
						}
						map.put(String.valueOf(t1[i]), list);
					}

					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后

				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后

					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		bean.setMap(map);
		bean.setPinyin(t4);
		return bean;
	}

	/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str
	 * @return String
	 */
	public static PYBean getPinYinHeadChar(PYBean pyBean) {
		String str = pyBean.getName();
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母

			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		
		pyBean.setPyHead(convert);
		return pyBean;
	}

	/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列

		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码

			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}
	
	
	public static List<String> removeList(List<String> list){
		List<String> newList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			String str = list.get(i);
			if(!newList.contains(str)){
				newList.add(str);
			}
		}
		return newList; 
	}
	public static void main(String[] args) {
		getPinYin("薄扁单");
	}

	
	public static List<String> getPinYin(String name) {
		char[] names = null;
		names = name.toCharArray();

		List<String[]> list = new ArrayList<String[]>();

		// 设置汉字拼音输出的格式

		HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
		pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		try {
			for (int i = 0; i < names.length; i++) {
				// 判断能否为汉字字符
				if (Character.toString(names[i]).matches("[\\u4E00-\\u9FA5]+")) {
					String[] str = PinyinHelper.toHanyuPinyinStringArray(
							names[i], pyFormat);// 将汉字的几种全拼都存到t2数组中
					list.add(str);
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					String[] str = { String.valueOf(names[i]) };
					list.add(str);
				}
			}
			
			String strs = "";
			for(int i=0;i<list.size();i++){
				for(int j = 0;j<list.get(i).length;j++){
					if(j==list.get(i).length-1 && i<list.size()-1){
						strs += (list.get(i)[j]+";");
					}else if(j==list.get(i).length-1 && i==list.size()-1){
						strs += (list.get(i)[j]);
					}else{
						strs += (list.get(i)[j]+",");
					}
				}
			}
			
			System.out.println(strs);
			
			List<String> result = descartes(strs);
			System.out.println(result.size());
			System.out.println(result);
			return removeList(result);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static List<String> descartes(String str) {
		String[] list = str.split(";");
		List<List> strs = new ArrayList<List>();
		for (int i = 0; i < list.length; i++) {
			strs.add(Arrays.asList(list[i].split(",")));
		}
		System.out.println(strs);
		int total = 1;
		for (int i = 0; i < strs.size(); i++) {
			total *= strs.get(i).size();
		}
		String[] mysesult = new String[total];
		int now = 1;
		// 每个元素每次循环打印个数
		int itemLoopNum = 1;
		// 每个元素循环的总次数
		int loopPerItem = 1;
		for (int i = 0; i < strs.size(); i++) {
			List temp = strs.get(i);
			now = now * temp.size();
			// 目标数组的索引值
			int index = 0;
			int currentSize = temp.size();
			itemLoopNum = total / now;
			loopPerItem = total / (itemLoopNum * currentSize);
			int myindex = 0;
			for (int j = 0; j < temp.size(); j++) {

				// 每个元素循环的总次数
				for (int k = 0; k < loopPerItem; k++) {
					if (myindex == temp.size())
						myindex = 0;
					// 每个元素每次循环打印个数
					for (int m = 0; m < itemLoopNum; m++) {
						mysesult[index] = (mysesult[index] == null ? ""
								: mysesult[index] + ",")
								+ ((String) temp.get(myindex));
						index++;
					}
					myindex++;

				}
			}
		}
		return Arrays.asList(mysesult);
	}
	
	
	public static PYBean getPinYinNew(PYBean bean, Map<String, String> mapDic) {
		char[] t1 = null;
		t1 = bean.getName().toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式

		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符

				// System.out.println(t1[i]);
				List<String> list = new ArrayList<String>();
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					if (t2.length > 1) {
						String str = mapDic.get(String.valueOf(t1[i]));
						t4 += str;
					}else{
						t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
					}
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		bean.setPinyin(t4);
		return bean;
	}

	public static PYBean getPinYinHeadCharNew(PYBean bean, Map<String, String> mapDic) throws BadHanyuPinyinOutputFormatCombination {
		String str = bean.getName();
		String convert = "";
		
		char[] t1 = null;
		t1 = bean.getName().toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			
			if (Character.toString(word).matches("[\\u4E00-\\u9FA5]+")) {
				t2 = PinyinHelper.toHanyuPinyinStringArray(word, t3);// 将汉字的几种全拼都存到t2数组中
				String pingyin = mapDic.get(String.valueOf(word));
				if (t2.length > 1 && pingyin != null && "".equals(pingyin)) {
					String szm = String.valueOf(pingyin.charAt(0));
					convert += szm;
				} else {

					String[] pinyinArray = PinyinHelper
							.toHanyuPinyinStringArray(word);
					if (pinyinArray != null) {
						convert += pinyinArray[0].charAt(0);
					} else {
						convert += word;
					}
				}
			}else{
				convert += word;
			}

			
		}
		
		bean.setPyHead(convert);
		return bean;
	}
}
