package com.xpsoft.oa.dao.document;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.document.Document;
import com.xpsoft.oa.model.system.AppUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract interface DocumentDao extends BaseDao<Document>
{
  public abstract List<Document> findByIsShared(Document paramDocument, Date paramDate1, Date paramDate2, Long paramLong1, ArrayList<Long> paramArrayList, Long paramLong2, PagingBean paramPagingBean);

  public abstract List<Document> findByPublic(String paramString, Document paramDocument, Date paramDate1, Date paramDate2, AppUser paramAppUser, PagingBean paramPagingBean);

  public abstract List<Document> findByPersonal(Long paramLong, Document paramDocument, Date paramDate1, Date paramDate2, String paramString, PagingBean paramPagingBean);

  public abstract List<Document> findByFolder(String paramString);

  public abstract List<Document> searchDocument(AppUser paramAppUser, String paramString, boolean paramBoolean, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.document.DocumentDao
 * JD-Core Version:    0.6.0
 */