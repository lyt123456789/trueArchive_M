package cn.com.trueway.archives.templates.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import net.sf.json.JSONObject;

public class DemosAction extends BaseAction {
	
	
	/**
	 * 跳转到档案信息订阅
	 * @return
	 */
	public String toDaxxdyJsp() {
		int pageSize = 10;
		int count = 10;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toDaxxdyJsp";
	}
	/**
	 * 跳转到订阅信息查看
	 * @return
	 */
	public String toDyxxckJsp() {
		int pageSize = 10;
		int count = 10;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toDyxxckJsp";
	}
	/**
	 * 跳转到我的收藏
	 * @return
	 */
	public String toWdscJsp() {
		int pageSize = 10;
		int count = 10;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toWdscJsp";
	}
	
	/**
	 * 跳转到环境变量配置
	 * @return
	 */
	public String toHujblpzJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toHujblpzJsp";
	}

	/**
	 * 跳转到通用数据文件装换
	 * @return
	 */
	public String toTysjwjzhJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toTysjwjzhJsp";
	}
	
	/**
	 * 跳转到离线数据著录工具制作
	 * @return
	 */
	public String toLxsjzlgjzzJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toLxsjzlgjzzJsp";
	}
	/**
	 * 跳转到全宗管理
	 * @return
	 */
	public String toQuzglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toQuzglJsp";
	}
	
	/**
	 * 跳转到档案接收
	 * @return
	 */
	public String toDajsJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toDajsJsp";
	}
	
	/**
	 * 跳转到移出管理
	 * @return
	 */
	public String toYcglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toYcglJsp";
	}
	
	/**
	 * 跳转到接收管理
	 * @return
	 */
	public String toJsglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toJsglJsp";
	}
	
	/**
	 * 跳转到移出接收管理
	 * @return
	 */
	public String toYcjstjJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toYcjstjJsp";
	}
	
	/**
	 * 跳转到接收移出台账
	 * @return
	 */
	public String toJsyctzJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toJsyctzJsp";
	}
	
	/**
	 * 跳转到保管管理
	 * @return
	 */
	public String toBgglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toBgglJsp";
	}
	
	/**
	 * 跳转到库房实体档案管理
	 * @return
	 */
	public String toKufstdaglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toKufstdaglJsp";
	}
	
	/**
	 * 跳转到库房管理台账
	 * @return
	 */
	public String toKufgltzJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toKufgltzJsp";
	}
	
	/**
	 * 跳转到销毁管理
	 * @return
	 */
	public String toXiaohglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toXiaohglJsp";
	}
	
	/**
	 * 跳转到档案销毁管理
	 * @return
	 */
	public String toDaxhtzJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toDaxhtzJsp";
	}
	
	/**
	 * 跳转到划控鉴定管理
	 * @return
	 */
	public String toHuakjdglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toHuakjdglJsp";
	}
	
	/**
	 * 跳转到修裱管理
	 * @return
	 */
	public String toXiubglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toXiubglJsp";
	}
	
	/**
	 * 跳转到馆藏统计
	 * @return
	 */
	public String toGuanctjJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toGuanctjJsp";
	}
	
	/**
	 * 跳转到年报管理
	 * @return
	 */
	public String toNianbglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toNianbglJsp";
	}
	
	/**
	 * 跳转到档案数字化
	 * @return
	 */
	public String toDangaszhJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toDangaszhJsp";
	}
	
	/**
	 * 跳转到信息发布管理
	 * @return
	 */
//	public String toXinxfbglJsp() {
//		int pageSize = 10;
//		int count = 1;
//		Paging.setPagingParams(getRequest(), pageSize, count);
//		return "toXinxfbglJsp";
//	}
	
	/**
	 * 跳转到命名空间管理
	 * @return
	 */
//	public String toMingmkjglJsp() {
//		int pageSize = 10;
//		int count = 1;
//		Paging.setPagingParams(getRequest(), pageSize, count);
//		return "toMingmkjglJsp";
//	}
	
	/**
	 * 跳转到业务管理
	 * @return
	 */
//	public String toYewglJsp() {
//		int pageSize = 10;
//		int count = 1;
//		Paging.setPagingParams(getRequest(), pageSize, count);
//		return "toYewglJsp";
//	}
	
	/**
	 * 跳转到报表维护
	 * @return
	 */
	public String toBaobwhJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toBaobwhJsp";
	}
	
	/**
	 * 跳转到全宗群管理
	 * @return
	 */
	public String toQuanzqglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toQuanzqglJsp";
	}
	
	/**
	 * 跳转到综合业务查询
	 * @return
	 */
	public String toZhywcxJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toZhywcxJsp";
	}
	
	/**
	 * 跳转到关键词查询
	 * @return
	 */
	public String toGuanjccxJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toGuanjccxJsp";
	}
	
	/**
	 * 跳转到档案编研管理
	 * @return
	 */
	public String toDangabyglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toDangabyglJsp";
	}
	
	/**
	 * 跳转到查看编研文档
	 * @return
	 */
	public String toChakbywdJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toChakbywdJsp";
	}
	
	/**
	 * 跳转到查阅管理
	 * @return
	 */
	public String toChayglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toChayglJsp";
	}
	
	/**
	 * 跳转到借阅管理
	 * @return
	 */
	public String toJieyglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toJieyglJsp";
	}
	
	/**
	 * 跳转到借阅模板设置
	 * @return
	 */
	public String toJieymbszJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toJieymbszJsp";
	}
	
	/**
	 * 跳转到索引库管理
	 * @return
	 */
	public String toSuoykglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toSuoykglJsp";
	}
	
	/**
	 * 跳转到机构和用户管理
	 * @return
	 */
	public String toJighyhglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toJighyhglJsp";
	}
	
	/**
	 * 跳转到角色管理
	 * @return
	 */
	public String toJuesglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toJuesglJsp";
	}
	
	/**
	 * 跳转到功能授权
	 * @return
	 */
	public String toGonnsqJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toGonnsqJsp";
	}
	
	/**
	 * 跳转到数据授权
	 * @return
	 */
	public String toShujsqJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toShujsqJsp";
	}
	
	/**
	 * 跳转到查看用户权限
	 * @return
	 */
	public String toChakyhqxJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toChakyhqxJsp";
	}
	
	/**
	 * 跳转到文件服务器设置
	 * @return
	 */
	public String toWenjfwqszJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toWenjfwqszJsp";
	}
	
	/**
	 * 跳转到日志管理
	 * @return
	 */
	public String toRizglJsp() {
		int pageSize = 10;
		int count = 1;
		Paging.setPagingParams(getRequest(), pageSize, count);
		return "toRizglJsp";
	}
	
	
}
