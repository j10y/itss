package com.xpsoft.oa.service.info;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.InMessage;
import com.xpsoft.oa.model.info.ShortMessage;
import java.util.Date;
import java.util.List;

public abstract interface InMessageService extends BaseService<InMessage>
{
  public abstract InMessage findByRead(Long paramLong);

  public abstract Integer findByReadFlag(Long paramLong);

  public abstract List<InMessage> findAll(Long paramLong, PagingBean paramPagingBean);

  public abstract List findByUser(Long paramLong, PagingBean paramPagingBean);

  public abstract List searchInMessage(Long paramLong, InMessage paramInMessage, ShortMessage paramShortMessage, Date paramDate1, Date paramDate2, PagingBean paramPagingBean);

  public abstract InMessage findLatest(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.InMessageService
 * JD-Core Version:    0.6.0
 */