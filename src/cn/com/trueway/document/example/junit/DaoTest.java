package cn.com.trueway.document.example.junit;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.example.dao.ExampleDao;
import cn.com.trueway.document.example.model.Emp;
import cn.com.trueway.document.example.model.vo.UserVO;


/**
 * 描述：JdbcTemplateExample 测试<br>
 * 作者：吴新星<br>
 * 创建时间：2011-8-7 下午06:22:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class DaoTest {
	
	//private static ApplicationContext ctx;
	private static ExampleDao ed;
	private long t = 0;
	
	@Before
	public void init(){
		t = System.currentTimeMillis();
		System.out.println("测试开始 ..."+ t);
		//加载 spring 配置文件
		System.out.println("开始加载配置文件...");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/common/dataAccessContext.xml","classpath:spring/example/serviceContext_dao.xml"});
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/common/dataAccessContext.xml","classpath:spring/workflow/core/serviceContext_wf_core_dao.xml"});
		ed = (ExampleDao)ctx.getBean("exampleDao");
//		ed = (WorkflowDefineDao)ctx.getBean("workflowDefineDao");
		System.out.println("加载配置文件成功");
	}
	
	@After
	public void destroy(){
		System.out.println("测试结束 ..."+System.currentTimeMillis());
		t = System.currentTimeMillis() - t ;
		System.out.println("耗时：" + t + "ms");
	}

	@Test
	public void add(){
//		ed.addUser(new User());
//		ed.queryAllUser();
	}
	
//	@Test
//	public void queryDTPageBean(){
//		List<String> deps=new ArrayList<String>();
//		deps.add("{BFA811EA-0000-0000-4557-98D600000881}");
//		List<Define> result =  ed.getDefinesByType(deps, "1");
//		for(Define define:result){
//			System.out.println(define.getWfName());
//		}
//		System.out.println(result.toString());
//	}
	
	@Test
	public void addEmp(){
		Emp e = new Emp();
		e.setName("nova woo");
		e.setImg("nova woo".getBytes());
		e.setDescription("nova woo good");
	//	ed.saveEmp(e);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryVO(){
		List<UserVO> results = ed.queryVO();
		for(UserVO uv : results){
			System.out.println(uv);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryPageBeanVO(){
		DTPageBean dt = ed.queryAllUser(1,2);
		for(UserVO uv : (List<UserVO>)dt.getDataList()){
			System.out.println(uv);
		}
		
	}
	
/*	@Test
	public void queryEmp(){
		List<Emp> emps = ed.queryEmp();
		for(Emp e : emps){
			System.out.println(new String(e.getImg()) +"--"+e.getDescription());
		}
	}*/
}
