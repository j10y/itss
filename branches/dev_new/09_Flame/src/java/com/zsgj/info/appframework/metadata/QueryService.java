package com.zsgj.info.appframework.metadata;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.zsgj.info.framework.dao.support.Page;

/**
 * ��ѯ����ӿڣ�������ڶ��ƿ�ܵ�ͨ�ò�ѯ�ӿڡ�
 * ��QueryServiceΪMetaDataManager֮��Ŀ��API�ӿ�
 * ע�������ѯ�����޷���������ҵ�񳡾���������ʵ��QueryService�ӿڡ�����취��
 * <pre>
 *   ����ColumnQueryServiceImpl�࣬дһ����̳�ColumnQueryService�����࣬������
 *   middle��������ֻ��ֱ���޸Ĳ�ѯ����������middle(Criteria criteria)��
 *   ���Ҫ��������Ĳ���������������븲��middle(Criteria criteria, Map extParams)����.
 * </pre>
 * 
 * @Class Name QueryService
 * @Author peixf
 * @Create In 2008-5-30
 */
public interface QueryService {

	/**
	 * �Բ�ѯ���������ƣ���ǰ�˲�ѯ�������������า�Ǵ˷�����
	 * @param criteria
	 * @param extParams
	 */
	void middle(Criteria criteria, Map extParams);
	
	/**
	 * �Բ�ѯ���������ƣ�����ǰ�˲�ѯ�������������า�Ǵ˷�����
	 * ���������з����������
	 * @param criteria
	 */
	void middle(Criteria criteria);

	/**
	 * ��ѯ����ʵ�ʵ��õĲ�ѯ�������ײ��ȵ���before��
	 * ����middle(Criteria criteria)��end������
	 * @param clazz
	 * @param params ���ڶ��ƿ�ܹ���õĲ�ѯ����, ��key����ΪsystemQueryColumn��string
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page query(Class clazz, Map<Object,Object> params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	
	
	/**
	 * ��ѯ����ʵ�ʵ��õĲ�ѯ�������ײ��ȵ���before��
	 * ����middle(Criteria criteria,Map extParams)��end������
	 * @param clazz
	 * @param params ���ڶ��ƿ�ܹ���õĲ�ѯ����, ��key����ΪsystemQueryColumn��string
	 * @param extParams ���ڲ������⹦�ܣ�������ܿɿط�Χ�����Ӵ˲������������������⴦��
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page query(Class clazz, Map<Object,Object> params, Map extParams, 
					int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	
	/**
	 * ʹ�ò�����ֵ�Խ��в�ѯ, param��ʽ����������Ϊkey������ֵ��Ϊvalue.
	 * �˷���������ɲ��������Զ�ת��. <br>�ײ��ȵ���before��
	 * ����middle(Criteria criteria,Map extParams)��end����.
	 * @Methods Name queryByParams
	 * @Create In 2008-10-17 By sa
	 * @param clazz
	 * @param params
	 * @param extParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page queryByParams(Class clazz, Map<String,Object> params, Map<String,Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * ʹ�ò�����ֵ�Խ��в�ѯ, param��ʽ����������Ϊkey������ֵ��Ϊvalue.
	 * �˷���������ɲ��������Զ�ת��. <br>�ײ��ȵ���before��
	 * ����middle(Criteria criteria,Map extParams)��end����.
	 * @Methods Name queryByParams
	 * @Create In 2009-1-12 By sa
	 * @param clazz
	 * @param params
	 * @param extParams
	 * @param orderProp
	 * @param isAsc
	 * @return List
	 */
	List queryByParams(Class clazz, Map<String,Object> params, Map<String, Object> extParams, 
			String orderProp, boolean isAsc);
	
	/**
	 * Ϊ���νṹ��ѯ��������ѯ���ݣ���Ҫ����ĳ�����Խ���������ʾ������
	 * �������ԱߵļӺ���ʾ�������е������ݡ�
	 * @Methods Name queryForTree
	 * @Create In 2009-1-12 By sa
	 * @param clazz
	 * @param params
	 * @param extParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page queryForTree(Class clazz, Map<String,Object> params, Map<String,Object> extParams, 
				int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	
	/**
	 * ʹ�ò�ѯ��������������ݶ��ƿ�ܵ�Criteria
	 * @param entityClazz
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Criteria ����ֵ���ᴫ�ݸ�middle(Criteria criteria)����
	 */
	Criteria before(Class entityClazz, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * ������ѯ�����ط�ҳ�������а�����ҳ��Ϣ�����յĲ�ѯ���
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page end(Criteria criteria, int pageNo, int pageSize);
	
	
	Page end(Criteria criteria, Map params, int pageNo, int pageSize);
	/**
	 * ��ѯ����ʵ�ʵ��õĲ�ѯ�������ײ��ȵ���before��
	 * ����middle(Criteria criteria,Map extParams)��end������
	 * @Methods Name queryByParamsForUser
	 * @Create In Aug 18, 2009 By lee
	 * @param clazz
	 * @param queryParams
	 * @param object
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @param propertyName
	 * @return Page
	 */
	Page queryByParamsForUser(Class clazz, Map<String, Object> queryParams,
			Object object, int pageNo, int pageSize, String orderProp,
			boolean isAsc, String propertyName);

}
