package com.zsgj.info.framework.service;

import java.util.List;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

/**
 * �÷���ӿڼ̳У�extends���˻�������Service����
 * �������е�actionͨ���κη���ӿڶ����Է��ʵ�Service��
 * �������ͨ��ҵ�񷽷����Ӷ������ڸ����������ظ����峣�ù��ܵķ�����<br>
 * �����BaseDispatchAction�п���ֱ��ʹ��getService()��ȡService��
 * �Ӷ���Action�Դ˻��������ܵĻ�ȡ��
 * @Class Name Service
 * @Author xiaofeng
 * @Create In 2008-4-11
 */
public interface Service {
    
   /**
    * Ϊ��������ע�����DAO
    * @Methods Name setDao
    * @Create In 2008-8-22 By sa
    * @param dao void
    */
    public void setDao(Dao dao);
    
    /**
     * ��ȡָ��������г־û�ʵ��
     * @Methods Name findAll
     * @Create In 2008-7-1 By peixf
     * @param clazz
     * @return List
     */
    public List findAll(Class clazz);
    
    /**
     * ��ȡָ��������г־û�ʵ��,������ʽ
     * @Methods Name findAllBy
     * @Create In 2008-7-1 By peixf
     * @param clazz
     * @param orderBy
     * @param isAsc
     * @return List
     */
    public List findAllBy(Class clazz, String orderBy, boolean isAsc);
    
    /**
     * ��ȡ��������ʵ�塣Ҫ��ֻ�������Ķ��󣬼�������Ϊnull�Ķ���ͬʱ����ָ������ʽ��
     * �������ָ������ʽ����orderByΪnull���ɡ�
     * @param clazz
     * @param parentPropName ��ʾ���������ƣ���TradeWay��parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List findAllTopBy(Class clazz, String parentPropName, String orderBy, boolean isAsc);
    
    /**
     * ��ȡ��������ʵ�塣Ҫ��ֻ���طǶ���Ķ��󣬼�������Ϊnull�Ķ���ͬʱ����ָ������ʽ��
     * �������ָ������ʽ����orderByΪnull���ɡ�
     * @param clazz
     * @param parentPropName ��ʾ���������ƣ���TradeWay��parentTradeWay
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List findAllChildBy(Class clazz, String parentPropName, String orderBy, boolean isAsc);
    
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
    public List findAllBy(Class clazz, String orderBy1, boolean isAsc1, String orderBy2, boolean isAsc2);
    
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
    public Page findByPagedQuery(Class clazz, String orderBy, boolean isAsc, int pageNo, int pageSize);
    
    /**
     * ͨ���������ƺ�����ֵ�������ж���ע��propValue����������Ҫ����������һ��
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName ��������
     * @param propValue ����ֵ
     * @return List
     */
    public List find(Class clazz, String propName, Object propValue);
    
    /**
     * ͨ���������ƺ�����ֵ����Ψһ����ע��propValue����������Ҫ����������һ��
     * @Methods Name findUnique
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param propName
     * @param propValue
     * @return Object
     */
    public Object findUnique(Class clazz, String propName, Object propValue);
    
    /**
     * ��ȡָ��ID�Ķ��󣬴˷������ȴӻ�����ȡ���������������û����ֱ�ӷ������ݿ�
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId �����id
     * @return Object
     */
    public Object find(Class clazz, String objectId);
      
    /**
     * ��ȡָ��ID�Ķ���isDirectAccessDb���������Ƿ�ֱ�ӷ������ݿ⣬isDirectAccessDb=false�����ش���
     * @Methods Name find
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId
     * @param isDirectAccessDb
     * @return Object
     */
    public Object find(Class clazz, String objectId, boolean isDirectAccessDb);
    
    /**
     * ����־û�����
     * @Methods Name save
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
    public Object save(Object object);

    /**
     * ɾ��ָ��ID�ĳ־û�����
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param clazz
     * @param objectId void
     */
    public void remove(Class clazz, String objectId);
    
    /**
     * ɾ���־û�����
     * @Methods Name remove
     * @Create In 2008-8-22 By peixf
     * @param object void
     */
    public void remove(Object object);
}
