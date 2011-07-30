package com.xpsoft.oa.service.info;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNews;
import java.util.List;

public abstract interface NoticeNewsService extends BaseService<NoticeNews>
{
  public abstract List<NoticeNews> findByTypeId(Long paramLong, PagingBean paramPagingBean);

  public abstract List<NoticeNews> findBySearch(String paramString, PagingBean paramPagingBean);

  public abstract List<NoticeNews> findImageNews(PagingBean paramPagingBean);
  
  /**
	 * 查询通知
	 * @param subject 标题，根据标题like %%检索
	 * @param searchContent 内容，根据内容like %%检索
	 * @param deptName 部门，根据部门名称like %%检索
	 * @param pb 分页器
	 * @param hasRead true已读取 false未读
	 * @return
	 */
	public List<NoticeNews> findByForPadSearch(String subject, String searchContent, String deptName, Long typeId, PagingBean pb, boolean hasRead);
}
