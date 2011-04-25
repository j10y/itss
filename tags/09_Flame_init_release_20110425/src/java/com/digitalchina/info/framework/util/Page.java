package com.digitalchina.info.framework.util;



/**
 * 分页器。
 * action中使用Page的方式：<br>
 * <pre>
 *   int pageIndex = HttpUtil.getInt(request, "page", 1);
 *   Page page = new Page(pageIndex, 20);
 * </pre>
 * dao方法中使用Page:<br>
 * <pre>
 *   public List selectAllSubContracts(Long contractType, Page page){
 *      return getDaoSupport().selectForList(
 *         "select count(*) from contract", 
 *         "select c from contract c where c.contractType=?", new Object[]{contractType}, page);
 *   }
 * </pre>
 * @deprecated
 * @Class Name Page
 * @author xiaofeng
 * @Create In 2007-10-30
 */
public class Page
{

    public static final int DEFAULT_PAGE_SIZE = 10;
    private int pageIndex;
    private int pageSize;
    private int totalCount;
    private int pageCount;

    public Page(int pageIndex, int pageSize)
    {
        if(pageIndex < 1)
            pageIndex = 1;
        if(pageSize < 1)
            pageSize = 1;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Page(int pageIndex)
    {
        this(pageIndex, 10);
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public int getFirstResult()
    {
        return (pageIndex - 1) * pageSize;
    }

    public boolean getHasPrevious()
    {
        return pageIndex > 1;
    }

    public boolean getHasNext()
    {
        return pageIndex < pageCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
        pageCount = totalCount / pageSize + (totalCount % pageSize != 0 ? 1 : 0);
        if(totalCount == 0)
        {
            if(pageIndex != 1)
                throw new IndexOutOfBoundsException("Page index out of range.");
        } else
        if(pageIndex > pageCount)
            throw new IndexOutOfBoundsException("Page index out of range.");
    }

    public boolean isEmpty()
    {
        return totalCount == 0;
    }
}
