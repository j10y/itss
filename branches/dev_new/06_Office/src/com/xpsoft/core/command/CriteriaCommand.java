package com.xpsoft.core.command;

import org.hibernate.Criteria;

public abstract interface CriteriaCommand
{
  public static final String SORT_DESC = "desc";
  public static final String SORT_ASC = "asc";

  public abstract Criteria execute(Criteria paramCriteria);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.command.CriteriaCommand
 * JD-Core Version:    0.6.0
 */