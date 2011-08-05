package com.xpsoft.oa.dao.archive;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.ArchDispatch;
import com.xpsoft.oa.model.system.AppUser;
import java.util.List;

public abstract interface ArchDispatchDao extends BaseDao<ArchDispatch>
{
  public abstract List<ArchDispatch> findByUser(AppUser paramAppUser, PagingBean paramPagingBean);

  public abstract List<ArchDispatch> findRecordByArc(Long paramLong);
  
  /**
   * 0,传阅 1承办
   * @param archivesId
   * @param type
   * @return
   */
  public List<ArchDispatch> findRecordByArc(Long archivesId, Short type);
}
