package com.xpsoft.oa.dao.archive;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import java.util.List;

public abstract interface ArchivesDocDao extends BaseDao<ArchivesDoc>
{
  public abstract List<ArchivesDoc> findByAid(Long paramLong);
}
