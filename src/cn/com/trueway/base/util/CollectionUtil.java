/**
 * 文件名称：CollectionUtil.java<br>
 * 作者：吴新星<br>
 * 创建时间：Apr 13, 2010 3:37:53 PM<br>
 * CopyRight (c)2009-2011:江苏中威科技信息系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：集合的工具类<br>
 * 作者：吴新星<br>
 * 创建时间：Apr 13, 2010 3:37:53 PM<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
public class CollectionUtil {

	/**
	 * 把一个list集合按指定的length分成多个集合
	 * 
	 * @param list
	 * @param length
	 * @return
	 */
	public static List<List<Object>> listOne2many(List<Object> list, int length) {
		List<List<Object>> disList = new ArrayList<List<Object>>();
		int cLength = list.size();
		if (cLength <= length) {
			disList.add(list);
			return disList;
		} else {
			int offset = 0;// offset表示list的位移
			while (offset < cLength) {
				int l = offset + length;
				if (l > cLength) {
					l = cLength;
				}
				List<Object> temp = new ArrayList<Object>();
				for (int i = offset; i < l; i++) {
					temp.add(list.get(i));
				}
				offset += length;
				disList.add(temp);
				temp = null;
			}
			return disList;
		}
	}

//	public static void main(String[] args) {
//		List l = new ArrayList();
//		l.add(0);
//		l.add(1);
//		l.add(2);
//		l.add(3);
//		l.add(4);
//		l.add(5);
//		l.add(6);
//		l.add(7);
//		l.add(8);
//		l.add(9);
//		l.add(10);
//		l.add(11);
//		l.add(12);
//		l.add(13);
//		List ll = CollectionUtil.listOne2many(l, 7);
//		for (List list : (List<List>) ll) {
//			System.out.println(list);
//		}
//	}
}
