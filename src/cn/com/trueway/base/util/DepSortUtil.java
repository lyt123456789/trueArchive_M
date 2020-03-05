/**
 * 文件名称:DepSortUtil.java
 * 作者:吴新星<br>
 * 创建时间:2010-4-22 下午05:07:37
 * CopyRight (c)2009-2011:江苏中威科技信息系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import cn.com.trueway.workflow.core.pojo.Department;


/**
 * 描述：DepSortUtil<br>
 * 作者：吴新星<br>
 * 创建时间：2010-4-22 下午05:07:37<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class DepSortUtil {
	@SuppressWarnings("unchecked")
	synchronized public static final List<Department> sortDepartment(
			List<Department> Departments) {
		List<Department> result = new ArrayList<Department>();
		if (Departments == null)
			return result;
		Object[] obj = Departments.toArray();
		Arrays.sort(obj, new Comparator() {
			public int compare(final Object obj1, final Object obj2) {
				Department o1 = (Department) obj1;
				Department o2 = (Department) obj2;

				final long depId1 = o1.getTabindex();
				final long depId2 = o2.getTabindex();
				return Long.valueOf(depId1).compareTo(Long.valueOf(depId2));
			}
		});
		for (int i = 0; i < obj.length; i++) {
			Department Department = (Department) obj[i];
			result.add(Department);
		}
		return result;
	}
}
