package com.zsgj.info.framework.orm.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.zsgj.info.framework.util.Page;

/**
 * 分页Criteria 能够执行类似 "select count(*)" 和 
   "select * from" 这样的查询，并且自动设置Page对象. 
   这个类使用反射来移除或重新加载 order by 条件。
 * @Class Name PaginationCriteria
 * @author xiaofeng
 * @Create In 2007-10-30
 */
public class PaginationCriteria {

    private static Log log = LogFactory.getLog(PaginationCriteria.class);
    private static Field orderEntriesField = getField(Criteria.class, "orderEntries");

    public static List query(Criteria c, Page page) {
        // first we must detect if any Order defined:
        // Hibernate code: private List orderEntries = new ArrayList();
        List _old_orderEntries = (List)getFieldValue(orderEntriesField, c);
        boolean restore = false;
        if(_old_orderEntries.size()>0) {
            restore = true;
            setFieldValue(orderEntriesField, c, new ArrayList());
        }
        c.setProjection(Projections.rowCount());
        int rowCount = ((Long)c.uniqueResult()).intValue();
        page.setTotalCount(rowCount);
        if(rowCount==0) {
            // no need to execute query:
            return Collections.EMPTY_LIST;
        }
        // query:
        if(restore) {
            // restore order conditions:
            setFieldValue(orderEntriesField, c, _old_orderEntries);
        }
        return c.setFirstResult(page.getFirstResult())
                .setMaxResults(page.getPageSize())
                .setFetchSize(page.getPageSize())
                .list();
    }

    private static Field getField(Class clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (Exception e) {
            log.warn("Class.getDeclaredField(String) failed.", e);
            throw new RuntimeException(e);
        }
    }

    private static Object getFieldValue(Field field, Object obj) {
        field.setAccessible(true);
        try {
            return field.get(obj);
        }
        catch (Exception e) {
            log.warn("Field.get(Object) failed.", e);
            throw new RuntimeException(e);
        }
    }

    private static void setFieldValue(Field field, Object target, Object value) {
        field.setAccessible(true);
        try {
            field.set(target, value);
        }
        catch (Exception e) {
            log.warn("Field.set(Object, Object) failed.", e);
            throw new RuntimeException(e);
        }
    }
}