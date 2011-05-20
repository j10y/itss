package com.zsgj.info.framework.web.json;
import net.sf.json.JSONException;

import org.hibernate.collection.PersistentSet;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**//**
 * 支持Hibernate的JSONUtil.
 * 自动检测是否已经代理加载，如果未加载，则将对象仅加载为OID
 * @author Jim Wu
 *
 */
public class HibernateJsonUtil extends JsonUtil {

    private static HibernateJsonUtil instance = null;

    static public String toJSONString(Object obj) throws JSONException{
        return toJSONString(obj, false);
    }
    
    static public String toJSONString(Object obj, boolean useClassConvert) throws JSONException{
        if(instance == null)
            instance = new HibernateJsonUtil();
        return instance.getJSONObject(obj, useClassConvert).toString();
    }


    protected Object proxyCheck(Object bean) {
        System.out.println("Class is " + bean.getClass().getName());
        if(bean instanceof HibernateProxy){
            LazyInitializer lazyInitializer = ((HibernateProxy)bean).getHibernateLazyInitializer();
            if(lazyInitializer.isUninitialized()){
                System.out.println(">>>>>lazyInitializer.getIdentifier()="+ lazyInitializer.getIdentifier());
                return lazyInitializer.getIdentifier();
            }
        }
        if(bean instanceof PersistentSet){
            return new String[]{}; //忽略hibernate one-to-many
        }
        return bean;
    }
}
