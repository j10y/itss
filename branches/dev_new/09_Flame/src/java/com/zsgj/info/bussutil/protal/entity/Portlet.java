package com.zsgj.info.bussutil.protal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

@SuppressWarnings("serial")
public class Portlet extends BaseObject {
	private Long id;
	private String name;

	// portlet将要显示位置的x坐标
	// private String x;
	// private String y;
	// //本portlet所属portalColumn
	// private PortalColumn protalColumn;
	// 生成本portlet所需要的javascript代码
	private String source;

	private Date createTime = new Date();

	/**
	 * 源码路径
	 */
	private String url;

	private String imageSrc;

	/**
	 * 本js的入口方法
	 */
	private String init;
	
	private Long defalutRefersh;

	private Set portletSubscribes = new HashSet(0);

	/**
	 * 本portlet是否已经被当前用户订阅 true/false=已订阅/未订阅
	 */
	private String flag = "false";

	public Set getPortletSubscribes() {
		return portletSubscribes;
	}

	public void setPortletSubscribes(Set portletSubscribes) {
		this.portletSubscribes = portletSubscribes;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String json() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"id\":");
		sb.append("\"");
		sb.append(this.getId());
		sb.append("\"");
		sb.append(",");
		sb.append("\"name\":");
		sb.append("\"");
		sb.append(this.getName());
		sb.append("\"");
		sb.append(",");
		sb.append("\"source\":");
		sb.append("\"");
		sb.append(this.getSource());
		sb.append("\"");
		sb.append(",");
		sb.append("\"url\":");
		sb.append("\"");
		sb.append(this.getUrl());
		sb.append("\"");
		sb.append(",");
		sb.append("\"imageSrc\":");
		sb.append("\"");
		sb.append(this.getImageSrc());
		sb.append("\"");
		sb.append(",");
		sb.append("\"createTime\":");
		sb.append("\"");
		sb.append(this.getCreateTime());
		sb.append("\"");
		sb.append(",");
		sb.append("\"init\":");
		sb.append("\"");
		sb.append(this.getInit());
		sb.append("\"");
		sb.append(",");
		sb.append("\"flag\":");
		sb.append("\"");
		sb.append(this.getFlag());
		sb.append("\"");
		sb.append("}");
		return sb.toString();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = PRIME * result + ((flag == null) ? 0 : flag.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((imageSrc == null) ? 0 : imageSrc.hashCode());
		result = PRIME * result + ((init == null) ? 0 : init.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((source == null) ? 0 : source.hashCode());
		result = PRIME * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Portlet other = (Portlet) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageSrc == null) {
			if (other.imageSrc != null)
				return false;
		} else if (!imageSrc.equals(other.imageSrc))
			return false;
		if (init == null) {
			if (other.init != null)
				return false;
		} else if (!init.equals(other.init))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	/**
	 * @Return the Long defalutRefersh
	 */
	public Long getDefalutRefersh() {
		return defalutRefersh;
	}

	/**
	 * @Param Long defalutRefersh to set
	 */
	public void setDefalutRefersh(Long defalutRefersh) {
		this.defalutRefersh = defalutRefersh;
	}
}
