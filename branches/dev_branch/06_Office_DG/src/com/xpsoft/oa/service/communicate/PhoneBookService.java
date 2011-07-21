package com.xpsoft.oa.service.communicate;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.communicate.PhoneBook;
import java.util.List;

public abstract interface PhoneBookService extends BaseService<PhoneBook>
{
  public abstract List<PhoneBook> sharedPhoneBooks(String paramString1, String paramString2, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.communicate.PhoneBookService
 * JD-Core Version:    0.6.0
 */