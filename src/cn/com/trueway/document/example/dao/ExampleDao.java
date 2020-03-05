package cn.com.trueway.document.example.dao;

import java.util.List;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.example.model.Emp;
import cn.com.trueway.document.example.model.User;

/**
 * 描述：JdbcTemplateExample dao<br>
 * 作者：吴新星<br>
 * 创建时间：2011-8-7 下午06:32:09<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface ExampleDao {
	
	/**
	 * 描述：增<br>
	 *
	 * @param u 
	 */
	public void addUser(User u);
	
	/**
	 * 描述：删<br>
	 *
	 * @param where 
	 */
	public void deleteUser(String where);
	
	/**
	 * 描述：改<br>
	 *
	 * @param u void
	 */
	public void updateUser(User u);
	
	/**
	 * 描述：查单个<br>
	 *
	 * @param userId
	 * @return User
	 */
	public User queryUser(String userId);
	
	/**
	 * 描述：查<br>
	 *
	 * @return List<User>
	 */
	public List<User> queryAllUser();
	
	/**
	 * 描述：查 + 分页<br>
	 *
	 * @param currentPage -- 当前页码
	 * @param numPerPage -- 一页显示的记录数
	 * @return List<User>
	 */
	public DTPageBean queryAllUser(int currentPage , int numPerPage);
	
	/**
	 * 描述：查 + 分页<br>
	 *
	 * @param currentPage -- 当前页码
	 * @param numPerPage -- 一页显示的记录数
	 * @return List<User>
	 */
	public DTPageBean queryAllUser(String totalRowsSql, String querySql,int currentPage , int numPerPage);
	
	public void saveEmp(Emp e);
	
	public List<Emp> queryEmp();
	
	@SuppressWarnings("unchecked")
	public List queryVO();
}
