// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   BaseService.java

package com.digitalchina.info.framework.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.Dao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 业务操作的基础类 - 实现一个类的基本操作和基本的增删改查。
 * 此类可以被所有的服务实现类继承，以获取常用的服务方法。
 * 如果某些Dao经常被各个服务类使用，可以考虑将这些通用的Dao实现注入到 BaseSevice中，然后具体的子服务继承此BaseService。

 * @Class Name BaseService
 * @author xiaofeng
 * @param <T>
 * @Create In 2007-10-26 TODO
 */
public class BaseService implements Service {
	/**
	 * 服务层日志记录器
	 */
	protected final Log logger = LogFactory.getLog("servicelog");

	/**
	 * 服务层依赖的DAO接口
	 */
	protected Dao dao;
	
	/**
	 * 返回spring管理的服务service
	 * @Methods Name getBean
	 * @Create In 2008-3-3 By peixf
	 * @param name
	 * @return Object
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
	
	/**
    * 为基础服务注入基础DAO
    * @Methods Name setDao
    * @Create In 2008-8-22 By sa
    * @param dao void
    */
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	 /**
     * 获取指定ID的对象，此方法优先从缓冲中取对象，即返回的是一个对象代理，当实际使用对象时才加载。
     * 如果缓冲中里没有则直接访问数据库。如此方法有无法加载对象情况发生请调用
     * find(Class clazz, String objectId, boolean isDirectAccessDb)方法，指定第3个参数为true
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId 对象的id
     * @return Object
     */
	public Object find(Class clazz, String objectId) {
		return dao.getObject(clazz, Long.valueOf(objectId));
	}

	/**
     * 获取指定ID的对象，isDirectAccessDb参数决定是否直接访问数据库，isDirectAccessDb=false将返回代理
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId
     * @param isDirectAccessDb
     * @return Object
     */
	public Object find(Class clazz, String objectId, boolean isDirectAccessDb) {
		return dao.getObject(clazz, Long.valueOf(objectId), isDirectAccessDb);
	}

	/**
     * 获取指定类的所有持久化实例
     * @Methods Name findAll
     * @Create In 2008-7-1 By peixf
     * @param clazz
     * @return List
     */
	public List findAll(Class clazz) {
		if(clazz.getName().equalsIgnoreCase("com.digitalchina.info.framework.security.entity.UserInfo")){
			logger.info("userInfo findAll");
		}else if(clazz.getName().equalsIgnoreCase("com.digitalchina.info.framework.security.entity.Department")){
			logger.info("department findAll");
		}
		return dao.getObjects(clazz);
	}

	/*public List findAll(Class clazz, boolean lazyFlag) {
		return dao.getObjects(clazz, lazyFlag);
	}*/

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
	public List findAllBy(Class clazz, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2) {
		List list = null;
		try {
			list = dao.getObjectsBy(clazz, orderBy1, isAsc1, orderBy2, isAsc2);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
     * 获取指定类的所有持久化实例,有排序方式
     * @Methods Name findAllBy
     * @Create In 2008-7-1 By peixf
     * @param clazz
     * @param orderBy
     * @param isAsc
     * @return List
     */
	public List findAllBy(Class clazz, String orderBy, boolean isAsc) {
		List list = null;
		list = dao.getObjectsBy(clazz, orderBy, isAsc);
		return list;
	}

	/**
     * 通过属性名称和属性值返回所有对象，注意propValue参数的类型要与属性类型一致
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName 属性名称
     * @param propValue 属性值
     * @return List
     */
	public List find(Class clazz, String propName, Object propValue) {
		List list = null;
		try {
			list = dao.getObjects(clazz, propName, propValue);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("通过属性值查询多个对象时发生异常");
		}
		return list;
	}

	/**
     * 通过属性名称和属性值返回唯一对象，注意propValue参数的类型要与属性类型一致
     * @Methods Name findUnique
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName
     * @param propValue
     * @return Object
     */
	public Object findUnique(Class clazz, String propName, Object propValue) {
		Object object = null;
		try {
			List list = dao.getObjects(clazz, propName, propValue);
			if(!list.isEmpty()){
				object = dao.getObjects(clazz, propName, propValue).iterator().next();
				if(list.size()>1){
					System.out.println("findUnique 返回超过1条记录");
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("通过属性值查询唯一对象时发生异常");
		}
		return object;
	}

	/**
     * 获取父子类型实体。要求只返回非顶层的对象，及父对象不为null的对象，同时可以指定排序方式。
     * 如果不想指定排序方式，让orderBy为null即可。
     * @param clazz
     * @param parentPropName 表示父属性名称，如TradeWay的parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
	public List findAllChildBy(Class clazz, String parentPropName, String orderBy, boolean isAsc) {
		return dao.getChildObjectsBy(clazz, parentPropName, orderBy, isAsc);
	}

	/**
     * 获取父子类型实体。要求只返回最顶层的对象，及父对象为null的对象，同时可以指定排序方式。
     * 如果不想指定排序方式，让orderBy为null即可。
     * @param clazz
     * @param parentPropName 表示父属性名称，如TradeWay的parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
	public List findAllTopBy(Class clazz, String parentPropName, String orderBy, boolean isAsc) {
		return dao.getTopObjectsBy(clazz, parentPropName, orderBy, isAsc);
	}

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
	public Page findByPagedQuery(Class clazz, String orderBy, boolean isAsc, int pageNo, int pageSize) {
		return dao.getByPagedQuery(clazz, orderBy, isAsc, pageNo, pageSize);
	}

	/**
     * 删除指定ID的持久化对象
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId void
     */
	public void remove(Class clazz, String objectId) {
		dao.removeObject(clazz,Long.valueOf(objectId));
	}

	/**
     * 删除持久化对象
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
	public void remove(Object object) {
		dao.remove(object);
	}

	/**
     * 保存持久化对象
     * @Methods Name save
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
	public Object save(Object o) {
		dao.save(o);
		return o;
	}

	/**
	 * 获取当前登录用户
	 * @Methods Name getUser
	 * @Create In 2008-10-20 By sa
	 * @return UserInfo
	 */
	public UserInfo getUser() {
		return UserContext.getUserInfo();
	}

	/**
	 * 获取DAO日志记录器
	 */
	public Log getLogger() {
		return logger;
	}

	/**
	 * 获取资源信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @return String
	 */
	protected String getProperties(String Key) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		
		return appContext.getMessage(Key, new Object[0], ContextHolder
				.getInstance().getLocal());
	}

	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	protected String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try{
			message = appContext.getMessage(Key, new Object[0],
				ContextHolder.getInstance().getLocal());

			return (message != null && !message.equals("") ? message : defaultValue);
		}catch(Exception e){
			logger.error(e.getMessage());
			return defaultValue;
		}
	}

	public Dao getDao() {
		return dao;
	}
	
	
	
}
