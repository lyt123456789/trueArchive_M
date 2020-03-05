/**
 * 文件名称:ExampleDaoImpl.java
 * 作者:吴新星<br>
 * 创建时间:2011-8-7 下午06:50:16
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.example.dao.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.example.dao.ExampleDao;
import cn.com.trueway.document.example.model.Emp;
import cn.com.trueway.document.example.model.User;
import cn.com.trueway.document.example.model.vo.UserVO;


/**
 * 描述：JdbcTemplateExample dao impl <br>
 * 作者：吴新星<br>
 * 创建时间：2011-8-7 下午06:50:16<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class ExampleDaoImpl extends BaseDao implements ExampleDao {

	/**
	 * 描述：增<br>
	 *
	 * @param u 
	 */
	public void addUser(User u) {
		for(int i=0,l=100000;i<l;i++){
			User uu = new User();
			uu.setUserName("吴新星");
			uu.setSex("男");
			this.getEm().persist(uu);
			if(i%10000 == 0) {
				this.getEm().flush();
				this.getEm().clear();
			}
		}
//		this.getEm().persist(u);
	}

	/**
	 * 描述：删<br>
	 *
	 * @param where 
	 */
	public void deleteUser(String where) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from jte_user where ").append(where);
		this.getEm().createNamedQuery(sql.toString()).executeUpdate();
	}
	
	/**
	 * 描述：改<br>
	 *
	 * @param u void
	 */
	public void updateUser(User u) {
		this.getEm().merge(u);
	}

	/**
	 * 描述：查单个<br>
	 *
	 * @param userId
	 * @return User
	 */
	public User queryUser(final String userId) {
		return this.getEm().find(User.class, userId);
	}
	
	/**
	 * 描述：查<br>
	 *
	 * @return List<User>
	 */
	@SuppressWarnings("unchecked")
	public List<User> queryAllUser() {
		StringBuilder hql = new StringBuilder();
		hql.append("from User");
		return this.getEm().createQuery(hql.toString()).getResultList();
	}
	
	/**
	 * 描述：查 + 分页（基于sql语句实现）<br>
	 *
	 * @param currentPage -- 当前页码
	 * @param numPerPage -- 一页显示的记录数
	 * @return List<User>
	 */
	public DTPageBean queryAllUser(int currentPage, int numPerPage) {
		return pagingQuery("select count(*) from example_user u,example_emp e where u.user_id = e.id", "select u.user_id as userId,u.user_name as username,u.sex as sex ,e.name as empName,e.img as empimg,e.description as empdesc from example_user u,example_emp e where u.user_id = e.id", currentPage, numPerPage, "UserVOResults");
	}
	
	/**
	 * 描述：查 + 分页（基于sql语句实现）<br>
	 *
	 * @param currentPage -- 当前页码
	 * @param numPerPage -- 一页显示的记录数
	 * @return List<User>
	 */
	public DTPageBean queryAllUser(String totalRowsSql, String querySql,int currentPage, int numPerPage) {
		return pagingQuery(totalRowsSql, querySql, currentPage, numPerPage, User.class);
	}
	
	public void saveEmp(Emp e){
		this.getEm().persist(e);
	}
	
	@SuppressWarnings("unchecked")
	public List<Emp> queryEmp(){
		return this.getEm().createQuery("from Emp e").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserVO> queryVO(){
		return getEm().createNativeQuery("select u.user_id as userId,u.user_name as username,u.sex as sex ,e.name as empName,e.img as empimg,e.description as empdesc from example_user u,example_emp e where u.user_id = e.id", "UserVOResults").getResultList();
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("%5B2012%5D%E6%9D%A5%E5%AD%9701009%E5%8F%B7","UTF-8"));
		System.out.println(URLDecoder.decode("我是","UTF-8"));
	}
}
