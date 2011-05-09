package com.zsgj.info.framework.service;

import java.util.List;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

/**
 * 让服务接口继承（extends）此基础服务（Service），
 * 这样所有的action通过任何服务接口都可以访问到Service中
 * 所定义的通用业务方法，从而避免在各个服务中重复定义常用功能的方法。<br>
 * 另外从BaseDispatchAction中可以直接使用getService()获取Service，
 * 从而简化Action对此基础服务功能的获取。
 * @Class Name Service
 * @Author xiaofeng
 * @Create In 2008-4-11
 */
public interface Service {
    
   /**
    * 为基础服务注入基础DAO
    * @Methods Name setDao
    * @Create In 2008-8-22 By sa
    * @param dao void
    */
    public void setDao(Dao dao);
    
    /**
     * 获取指定类的所有持久化实例
     * @Methods Name findAll
     * @Create In 2008-7-1 By peixf
     * @param clazz
     * @return List
     */
    public List findAll(Class clazz);
    
    /**
     * 获取指定类的所有持久化实例,有排序方式
     * @Methods Name findAllBy
     * @Create In 2008-7-1 By peixf
     * @param clazz
     * @param orderBy
     * @param isAsc
     * @return List
     */
    public List findAllBy(Class clazz, String orderBy, boolean isAsc);
    
    /**
     * 获取父子类型实体。要求只返回最顶层的对象，及父对象为null的对象，同时可以指定排序方式。
     * 如果不想指定排序方式，让orderBy为null即可。
     * @param clazz
     * @param parentPropName 表示父属性名称，如TradeWay的parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List findAllTopBy(Class clazz, String parentPropName, String orderBy, boolean isAsc);
    
    /**
     * 获取父子类型实体。要求只返回非顶层的对象，及父对象不为null的对象，同时可以指定排序方式。
     * 如果不想指定排序方式，让orderBy为null即可。
     * @param clazz
     * @param parentPropName 表示父属性名称，如TradeWay的parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List findAllChildBy(Class clazz, String parentPropName, String orderBy, boolean isAsc);
    
    /**
     * 返回指定类的所有持久化实例，此查询指定2个属性的排序方式
     * @Methods Name findAllBy
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param orderBy1
     * @param isAsc1
     * @param orderBy2
     * @param isAsc2
     * @return List
     */
    public List findAllBy(Class clazz, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2);
    
    /**
     * 不指定查询参数的简单分页查询
     * @Methods Name findByPagedQuery
     * @Create In 2008-8-22 By peixf
     * @param clazz 
     * @param orderBy 排序属性名称
     * @param isAsc 是否升序
     * @param pageNo 起始分页号
     * @param pageSize 一页大小
     * @return Page
     */
    public Page findByPagedQuery(Class clazz, String orderBy, boolean isAsc, int pageNo, int pageSize);
    
    /**
     * 通过属性名称和属性值返回所有对象，注意propValue参数的类型要与属性类型一致
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName 属性名称
     * @param propValue 属性值
     * @return List
     */
    public List find(Class clazz, String propName, Object propValue);
    
    /**
     * 通过属性名称和属性值返回唯一对象，注意propValue参数的类型要与属性类型一致
     * @Methods Name findUnique
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName
     * @param propValue
     * @return Object
     */
    public Object findUnique(Class clazz, String propName, Object propValue);
    
    /**
     * 获取指定ID的对象，此方法优先从缓冲中取对象，如果缓冲中里没有则直接访问数据库
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId 对象的id
     * @return Object
     */
    public Object find(Class clazz, String objectId);
      
    /**
     * 获取指定ID的对象，isDirectAccessDb参数决定是否直接访问数据库，isDirectAccessDb=false将返回代理
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId
     * @param isDirectAccessDb
     * @return Object
     */
    public Object find(Class clazz, String objectId, boolean isDirectAccessDb);
    
    /**
     * 保存持久化对象
     * @Methods Name save
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
    public Object save(Object object);

    /**
     * 删除指定ID的持久化对象
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId void
     */
    public void remove(Class clazz, String objectId);
    
    /**
     * 删除持久化对象
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
    public void remove(Object object);
}
