package com.xpsoft.core.service;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.paging.PagingBean;
import java.io.Serializable;
import java.util.List;

public abstract interface GenericService<T, PK extends Serializable>
{
  public abstract T save(T paramT);

  public abstract T merge(T paramT);

  public abstract void evict(T paramT);

  public abstract T get(PK paramPK);

  public abstract List<T> getAll();

  public abstract List<T> getAll(PagingBean paramPagingBean);

  public abstract List<T> getAll(QueryFilter paramQueryFilter);

  public abstract void remove(PK paramPK);

  public abstract void remove(T paramT);

  public abstract void flush();
  
  
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.service.GenericService
 * JD-Core Version:    0.6.0
 */