package com.xpsoft.oa.dao.archive;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.system.AppRole;
import java.util.List;
import java.util.Set;

public abstract interface ArchivesDao extends BaseDao<Archives>
{
  public abstract List<Archives> findByUserOrRole(Long paramLong, Set<AppRole> paramSet, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.ArchivesDao
 * JD-Core Version:    0.6.0
 */