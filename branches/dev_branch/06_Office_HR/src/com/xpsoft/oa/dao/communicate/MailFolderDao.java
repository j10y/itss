package com.xpsoft.oa.dao.communicate;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.communicate.MailFolder;
import java.util.List;

public abstract interface MailFolderDao extends BaseDao<MailFolder>
{
  public abstract List<MailFolder> getUserFolderByParentId(Long paramLong1, Long paramLong2);

  public abstract List<MailFolder> getAllUserFolderByParentId(Long paramLong1, Long paramLong2);

  public abstract List<MailFolder> getFolderLikePath(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.communicate.MailFolderDao
 * JD-Core Version:    0.6.0
 */