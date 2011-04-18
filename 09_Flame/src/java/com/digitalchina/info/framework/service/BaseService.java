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
 * ҵ������Ļ����� - ʵ��һ����Ļ��������ͻ�������ɾ�Ĳ顣
 * ������Ա����еķ���ʵ����̳У��Ի�ȡ���õķ��񷽷���
 * ���ĳЩDao����������������ʹ�ã����Կ��ǽ���Щͨ�õ�Daoʵ��ע�뵽 BaseSevice�У�Ȼ�������ӷ���̳д�BaseService��

 * @Class Name BaseService
 * @author xiaofeng
 * @param <T>
 * @Create In 2007-10-26 TODO
 */
public class BaseService implements Service {
	/**
	 * �������־��¼��
	 */
	protected final Log logger = LogFactory.getLog("servicelog");

	/**
	 * �����������DAO�ӿ�
	 */
	protected Dao dao;
	
	/**
	 * ����spring����ķ���service
	 * @Methods Name getBean
	 * @Create In 2008-3-3 By peixf
	 * @param name
	 * @return Object
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
	
	/**
    * Ϊ��������ע�����DAO
    * @Methods Name setDao
    * @Create In 2008-8-22 By sa
    * @param dao void
    */
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	 /**
     * ��ȡָ��ID�Ķ��󣬴˷������ȴӻ�����ȡ���󣬼����ص���һ�����������ʵ��ʹ�ö���ʱ�ż��ء�
     * �����������û����ֱ�ӷ������ݿ⡣��˷������޷����ض���������������
     * find(Class clazz, String objectId, boolean isDirectAccessDb)������ָ����3������Ϊtrue
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId �����id
     * @return Object
     */
	public Object find(Class clazz, String objectId) {
		return dao.getObject(clazz, Long.valueOf(objectId));
	}

	/**
     * ��ȡָ��ID�Ķ���isDirectAccessDb���������Ƿ�ֱ�ӷ������ݿ⣬isDirectAccessDb=false�����ش���
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
     * ��ȡָ��������г־û�ʵ��
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
     * ����ָ��������г־û�ʵ�����˲�ѯָ��2�����Ե�����ʽ
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
     * ��ȡָ��������г־û�ʵ��,������ʽ
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
     * ͨ���������ƺ�����ֵ�������ж���ע��propValue����������Ҫ����������һ��
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName ��������
     * @param propValue ����ֵ
     * @return List
     */
	public List find(Class clazz, String propName, Object propValue) {
		List list = null;
		try {
			list = dao.getObjects(clazz, propName, propValue);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("ͨ������ֵ��ѯ�������ʱ�����쳣");
		}
		return list;
	}

	/**
     * ͨ���������ƺ�����ֵ����Ψһ����ע��propValue����������Ҫ����������һ��
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
					System.out.println("findUnique ���س���1����¼");
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("ͨ������ֵ��ѯΨһ����ʱ�����쳣");
		}
		return object;
	}

	/**
     * ��ȡ��������ʵ�塣Ҫ��ֻ���طǶ���Ķ��󣬼�������Ϊnull�Ķ���ͬʱ����ָ������ʽ��
     * �������ָ������ʽ����orderByΪnull���ɡ�
     * @param clazz
     * @param parentPropName ��ʾ���������ƣ���TradeWay��parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
	public List findAllChildBy(Class clazz, String parentPropName, String orderBy, boolean isAsc) {
		return dao.getChildObjectsBy(clazz, parentPropName, orderBy, isAsc);
	}

	/**
     * ��ȡ��������ʵ�塣Ҫ��ֻ�������Ķ��󣬼�������Ϊnull�Ķ���ͬʱ����ָ������ʽ��
     * �������ָ������ʽ����orderByΪnull���ɡ�
     * @param clazz
     * @param parentPropName ��ʾ���������ƣ���TradeWay��parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
	public List findAllTopBy(Class clazz, String parentPropName, String orderBy, boolean isAsc) {
		return dao.getTopObjectsBy(clazz, parentPropName, orderBy, isAsc);
	}

	/**
     * ��ָ����ѯ�����ļ򵥷�ҳ��ѯ
     * @Methods Name findByPagedQuery
     * @Create In 2008-8-22 By peixf
     * @param clazz 
     * @param orderBy ������������
     * @param isAsc �Ƿ�����
     * @param pageNo ��ʼ��ҳ��
     * @param pageSize һҳ��С
     * @return Page
     */
	public Page findByPagedQuery(Class clazz, String orderBy, boolean isAsc, int pageNo, int pageSize) {
		return dao.getByPagedQuery(clazz, orderBy, isAsc, pageNo, pageSize);
	}

	/**
     * ɾ��ָ��ID�ĳ־û�����
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId void
     */
	public void remove(Class clazz, String objectId) {
		dao.removeObject(clazz,Long.valueOf(objectId));
	}

	/**
     * ɾ���־û�����
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
	public void remove(Object object) {
		dao.remove(object);
	}

	/**
     * ����־û�����
     * @Methods Name save
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
	public Object save(Object o) {
		dao.save(o);
		return o;
	}

	/**
	 * ��ȡ��ǰ��¼�û�
	 * @Methods Name getUser
	 * @Create In 2008-10-20 By sa
	 * @return UserInfo
	 */
	public UserInfo getUser() {
		return UserContext.getUserInfo();
	}

	/**
	 * ��ȡDAO��־��¼��
	 */
	public Log getLogger() {
		return logger;
	}

	/**
	 * ��ȡ��Դ��Ϣ
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            ��Դ�ļ�Key
	 * @return String
	 */
	protected String getProperties(String Key) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		
		return appContext.getMessage(Key, new Object[0], ContextHolder
				.getInstance().getLocal());
	}

	/**
	 * ��ȡ��Դ�ļ���Ϣ
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            ��Դ�ļ�Key
	 * @param defaultValue
	 *            Ĭ����Ϣ
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
