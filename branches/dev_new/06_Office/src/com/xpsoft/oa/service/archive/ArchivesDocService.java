package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import java.util.List;

public abstract interface ArchivesDocService extends BaseService<ArchivesDoc>
{
  public abstract List<ArchivesDoc> findByAid(Long paramLong);
}
