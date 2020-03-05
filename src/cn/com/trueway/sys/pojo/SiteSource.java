package cn.com.trueway.sys.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 站点资源实体类<br>
 * 作者：赵坚<br>
 * 创建时间：2017年4月25日14:17:55<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "SITE_SOURCE")
public class SiteSource implements Serializable{

	private static final long serialVersionUID = -5092766038150589116L;

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "SITE_ID", unique = true, nullable = false, length = 38)
	private String siteId;

	@Column(name = "HT_LOGO")
	private BigDecimal htLogo;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public BigDecimal getHtLogo() {
		return htLogo;
	}

	public void setHtLogo(BigDecimal htLogo) {
		this.htLogo = htLogo;
	}
	
}
