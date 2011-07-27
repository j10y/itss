package com.xpsoft.oa.model.info;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import java.util.Set;

public class NoticeNewsType extends BaseModel {

	@Expose
	private Long typeId;

	@Expose
	private String typeName;

	@Expose
	private Short sn;
	private Set<NoticeNews> news;

	public Long getTypeId() {
		/* 27 */return this.typeId;
	}

	public void setTypeId(Long typeId) {
		/* 30 */this.typeId = typeId;
	}

	public String getTypeName() {
		/* 33 */return this.typeName;
	}

	public void setTypeName(String typeName) {
		/* 36 */this.typeName = typeName;
	}

	public Short getSn() {
		/* 39 */return this.sn;
	}

	public void setSn(Short sn) {
		/* 42 */this.sn = sn;
	}

	public Set<NoticeNews> getNews() {
		/* 45 */return this.news;
	}

	public void setNews(Set<NoticeNews> news) {
		/* 48 */this.news = news;
	}
}
