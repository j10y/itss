package com.xpsoft.oa.service.info.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.info.NoticeNewsDao;
import com.xpsoft.oa.model.info.NoticeNews;
import com.xpsoft.oa.service.info.NoticeNewsService;
import java.util.List;

public class NoticeNewsServiceImpl extends BaseServiceImpl<NoticeNews> implements
	NoticeNewsService {
	private NoticeNewsDao newsDao;

	public NoticeNewsServiceImpl(NoticeNewsDao dao) {
		super(dao);
		this.newsDao = dao;
	}

	public List<NoticeNews> findByTypeId(Long typeId, PagingBean pb) {
		return this.newsDao.findByTypeId(typeId, pb);
	}

	
	/**
	 * searchContent为null是portal中传来的，这里需要过滤是否自己的
	 */
	public List<NoticeNews> findBySearch(String searchContent, PagingBean pb) {
		return this.newsDao.findBySearch(searchContent, pb);
	}
	
	/**
	 * 查询通知
	 * @param subject 内容，根据内容like %%检索
	 * @param searchContent 内容，根据内容like %%检索
	 * @param deptName 部门名称，根据部门名称like %%检索
	 * @param pb 分页器
	 * @param hasRead true已读取 false未读
	 * @return
	 */
	public List<NoticeNews> findByForPadSearch(String subject, String searchContent, String deptName, Long typeId, PagingBean pb, boolean hasRead) {
		if(hasRead){
			return this.newsDao.findByReadSearch(subject, searchContent, deptName, typeId, pb);		
		}else{
			return this.newsDao.findByNoReadSearch(subject, searchContent, deptName, typeId, pb);
		}
	}

	public List<NoticeNews> findImageNews(PagingBean pb) {
		return this.newsDao.findImageNews(pb);
	}
}
