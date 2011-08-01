package com.xpsoft.oa.dao.info.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.xpsoft.core.Constants;
import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.info.NoticeNewsDao;
import com.xpsoft.oa.model.info.NoticeNews;

public class NoticeNewsDaoImpl extends BaseDaoImpl<NoticeNews> implements NoticeNewsDao {
	public NoticeNewsDaoImpl() {
		/* 19 */super(NoticeNews.class);
	}

	public List<NoticeNews> findByTypeId(Long typeId, PagingBean pb) {
		/* 24 */String hql = "from NoticeNews n where n.newsType.typeId=?";
		/* 25 */Object[] params = { typeId };
		/* 26 */return findByHql("from NoticeNews n where n.newsType.typeId=?",
				params, pb);
	}

	public List<NoticeNews> findBySearch(String searchContent, PagingBean pb) {
		/* 31 */ArrayList params = new ArrayList();
		/* 32 */StringBuffer hql = new StringBuffer(
				"from NoticeNews n where n.status = ?");
		/* 33 */params.add(Constants.FLAG_ACTIVATION);
		/* 34 */if (StringUtils.isNotEmpty(searchContent)) {
			/* 35 */hql.append(" and (n.subject like ? or n.content like ?)");
			/* 36 */params.add("%" + searchContent + "%");
			/* 37 */params.add("%" + searchContent + "%");
				}
		
				//if(searchContent==null){
					hql.append(" and n.isAll=1 and (n.newsId not in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId="+ContextUtil.getCurrentUserId()+" and comment.flag=2 and comment.content='1')) or n.newsId in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId="+ContextUtil.getCurrentUserId()+" and comment.flag=2 and comment.content='0')");
				//}
		/* 39 */hql.append(" order by n.updateTime desc");
		/* 40 */return findByHql(hql.toString(), params.toArray(), pb);
	}
	
	public List<NoticeNews> findBySearch(String searchContent, PagingBean pb, boolean hasRead) {
		/* 31 */ArrayList params = new ArrayList();
		/* 32 */StringBuffer hql = new StringBuffer(
		"from NoticeNews n where n.status = ?");
		/* 33 */params.add(Constants.FLAG_ACTIVATION);
		/* 34 */if (StringUtils.isNotEmpty(searchContent)) {
			/* 35 */hql.append(" and (n.subject like ? or n.content like ?)");
			/* 36 */params.add("%" + searchContent + "%");
			/* 37 */params.add("%" + searchContent + "%");
		}
		
		//if(searchContent==null){
		//hql.append(" and n.isAll=1 and (n.newsId not in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId="+ContextUtil.getCurrentUserId()+" and comment.flag=2 and comment.content='1')) or n.newsId in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId="+ContextUtil.getCurrentUserId()+" and comment.flag=2 and comment.content='0')");
		//}
		if(hasRead){
			hql.append(" and n.newsId in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId=? and comment.flag=2 and comment.content='1')");
			params.add(ContextUtil.getCurrentUserId());
		}else{
			hql.append(" and n.isAll=1 and (n.newsId not in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId=? and comment.flag=2 and comment.content='1')) or n.newsId in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId=? and comment.flag=2 and comment.content='0')");
			params.add(ContextUtil.getCurrentUserId());
			params.add(ContextUtil.getCurrentUserId());
		}
		/* 39 */hql.append(" order by n.updateTime desc");
		/* 40 */return findByHql(hql.toString(), params.toArray(), pb);
	}
	
	public List<NoticeNews> findByReadSearch(String subject, String searchContent, String deptName, Long typeId, PagingBean pb) {
				ArrayList params = new ArrayList();
				StringBuffer hql = new StringBuffer(
				"from NoticeNews n where n.status = ?");
				params.add(Constants.FLAG_ACTIVATION);
				
				if (StringUtils.isNotEmpty(subject)&&StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and (n.subject like ? or n.content like ?)");
					params.add("%" + searchContent + "%");
					params.add("%" + searchContent + "%");
				}else if (StringUtils.isNotEmpty(subject) && !StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and n.subject like ?");
						params.add("%" + subject + "%");
				}else if (!StringUtils.isNotEmpty(subject) && StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and n.content like ?");
					params.add("%" + searchContent + "%");
				}
				
				if(StringUtils.isNotEmpty(deptName)){
					hql.append(" and n.dept.depName like ?");
					params.add("%" + deptName + "%");
				}
				
				if(typeId!=null){
					hql.append(" and n.newsType.typeId=?");
					params.add(typeId);
				}
				
				hql.append(" and n.newsId in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId=? and comment.flag=2 and comment.content='1')");
				params.add(ContextUtil.getCurrentUserId());
				
				hql.append(" order by n.updateTime desc");
				return findByHql(hql.toString(), params.toArray(), pb);
	}
	
	public List<NoticeNews> findByNoReadSearch(String subject, String searchContent, String deptName, Long typeId, PagingBean pb) {
				ArrayList params = new ArrayList();
				StringBuffer hql = new StringBuffer(
				"from NoticeNews n where n.status = ?");
				params.add(Constants.FLAG_ACTIVATION);
				
				if (StringUtils.isNotEmpty(subject)&&StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and (n.subject like ? or n.content like ?)");
					params.add("%" + searchContent + "%");
					params.add("%" + searchContent + "%");
				}else if (StringUtils.isNotEmpty(subject) && !StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and n.subject like ?");
						params.add("%" + subject + "%");
				}else if (!StringUtils.isNotEmpty(subject) && StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and n.content like ?");
					params.add("%" + searchContent + "%");
				}
				
				if(StringUtils.isNotEmpty(deptName)){
					hql.append(" and n.dept.depName like ?");
					params.add("%" + deptName + "%");
				}
				
				if(typeId!=null){
					hql.append(" and n.newsType.typeId=?");
					params.add(typeId);
				}
				
				hql.append(" and n.isAll=1 and (n.newsId not in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId=? and comment.flag=2 and comment.content='1')) or n.newsId in (select comment.news.newsId from NoticeNewsComment comment where comment.appUser.userId=? and comment.flag=2 and comment.content='0'");
				
				params.add(ContextUtil.getCurrentUserId());
				params.add(ContextUtil.getCurrentUserId());
				if (StringUtils.isNotEmpty(subject)&&StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and (comment.news.subject like ? or comment.news.content like ?)");
					params.add("%" + searchContent + "%");
					params.add("%" + searchContent + "%");
				}else if (StringUtils.isNotEmpty(subject) && !StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and comment.news.subject like ?");
						params.add("%" + subject + "%");
				}else if (!StringUtils.isNotEmpty(subject) && StringUtils.isNotEmpty(searchContent)) {
					hql.append(" and comment.news.content like ?");
					params.add("%" + searchContent + "%");
				}
				if(typeId!=null){
					hql.append(" and comment.news.newsType.typeId=?");
					params.add(typeId);
				}
				if(StringUtils.isNotEmpty(deptName)){
					hql.append(" and comment.news.dept.depName like ?");
					params.add("%" + deptName + "%");
				}	
				hql.append(")");
				
				hql.append(" order by n.updateTime desc");
				return findByHql(hql.toString(), params.toArray(), pb);
	}

	public List<NoticeNews> findImageNews(PagingBean pb) {
		/* 45 */String hql = "from NoticeNews vo where vo.isDeskImage=1 order by vo.updateTime desc";
		/* 46 */return findByHql(hql, new Object[0], pb);
	}
}
