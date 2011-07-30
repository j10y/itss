package com.xpsoft.oa.dao.info;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNews;
import java.util.List;

public abstract interface NoticeNewsDao extends BaseDao<NoticeNews>
{
  public abstract List<NoticeNews> findByTypeId(Long paramLong, PagingBean paramPagingBean);

  public abstract List<NoticeNews> findBySearch(String paramString, PagingBean paramPagingBean);

  public abstract List<NoticeNews> findImageNews(PagingBean paramPagingBean);
  
  /**
   * for pad 已读取
   * @param subject
   * @param searchContent
   * @param deptName
   * @param pb
   * @return
   */
  public List<NoticeNews> findByReadSearch(String subject, String searchContent, String deptName, PagingBean pb);
  
  /**
   * for pad 未读取
   * @param subject
   * @param searchContent
   * @param deptName
   * @param pb
   * @return
   */
  public List<NoticeNews> findByNoReadSearch(String subject, String searchContent, String deptName, PagingBean pb);
}