package com.zsgj.info.appframework.metadata;

import java.util.List;
import java.util.Map;

import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * ϵͳ�������ݹ���������ǰ�˶Զ��ƿ��API��ʹ�á� 
 * ��֤���ݶ��ƿ�ܵĿ�����ǰ�˿������롣
 * 
 * ���������������������Ĺ��ܣ��������Զ������࣬�̳б�MetaDataManager
 * �ӿڵ�Ĭ��ʵ����MetaDataManagerImpl��������صķ�����
 * query����ʹ�õ���QueryService�е�query��������ҵ��������Ҫ�޸Ĳ�ѯ��
 * ����ֱ��ʹ��QueryService��QueryService
 * @Class Name MetaDataManager
 * @Author peixf
 * @Create In 2008-6-30
 */
public interface MetaDataManager {
	
	/**
	 * ʹ��Map<String,String>���͵Ĳ�ѯ����queryParams�����ɲ�ѯ�ֶΡ�
	 * ǰ��ʹ��HttpUtil.requestParam2Map������request�еĲ�����װ��Map<String,String>
	 * ���أ���map��Ϊ��������˷������������ɻ���ϵͳ��ѯ��ܵĲ�ѯ����,�Ա�
	 * ����Page query(Class clazz, Map queryParams ...)�����ĵ�2������ʹ�á�
	 * @Methods Name genQueryParamValues
	 * @Create In 2008-6-30 By peixf
	 * @param clazz
	 * @param requestParams ���������Map��ʽ
	 * @return Map<Object,Object> ϵͳ��ѯ�ֶΣ�����
	 */
	Map<Object,Object> genQueryParams(Class clazz, Map<String,String> requestParams);
	
	/**
	 * ���2��ʹ�ã����±�¶�����˷���
	 * @Methods Name genPropParams
	 * @Create In 2008-12-21 By sa
	 * @param clazz
	 * @param requestParams
	 * @return Map<String,Object>
	 */
	Map<String, Object> genPropParams(Class clazz, Map<String, Object> requestParams);
	//Map<String, Object> genPropParams(Class clazz, Map<String, Object> requestParams);
	
	/**
	 * ʹ���������ƣ�����ֵ�ļ�ֵ�Խ��в�ѯ���˷���ʡȥ���ɲ�ѯ�ֶεĲ��裬
	 * ֻҪqueryParams������key�������������һ�£�����Ϊ��ѯ�������в�ѯ��
	 * �������ںͻ��ҿ���ʹ�������ѯ�������ַ���������ͬ�����Խ������ŵ�����
	 * ����ж�ֵIN��ѯ
	 * @Methods Name query
	 * @Create In 2008-10-16 By sa
	 * @param clazz
	 * @param queryParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderProp
	 * @param isAsc
	 * @return Page
	 */
	Page query(Class clazz, Map<String,Object> queryParams, 
			int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	List query(Class clazz, Map<String,Object> queryParams, String orderProp, boolean isAsc);

	
	/**
	 * ʹ������ѯ��������չ��ѯ���������ۺϲ�ѯ���ײ����QueryService��query������
	 * ���û����չ��������null�������˷�����queryParams����������genQueryParams�����ķ���ֵ
	 * �������ط�ҳ��������page.list()��page.getData���ɻ�ȡ����
	 * @Methods Name query
	 * @Create In 2008-6-30 By peixf
	 * @param clazz
	 * @param queryParams ����ϵͳ��ѯ��ܵĲ�ѯ����
	 * @param extParams ��չ��ѯ����
	 * @param pageNo ��ʼ��ҳ����
	 * @param pageSize ҳ��С
	 * @param orderProp ����������
	 * @param isAsc �Ƿ�����
	 * @return Page 
	 */
	Page query(Class clazz, Map<Object,Object> queryParams, Map extParams, 
					int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * ʹ������ѯ��������չ��ѯ���������ۺϲ�ѯ���ײ����QueryService��query������
	 * �Զ���ȡ��̨��ɫȨ�����ݣ��鿴ȫ�����ǲ鿴�Լ���
	 * ���û����չ��������null�������˷�����queryParams����������genQueryParams�����ķ���ֵ
	 * �������ط�ҳ��������page.list()��page.getData���ɻ�ȡ����
	 * @Methods Name queryForUser
	 * @Create In 2009-8-18 By lee
	 * @param clazz
	 * @param queryParams ����ϵͳ��ѯ��ܵĲ�ѯ����
	 * @param pageNo ��ʼ��ҳ����
	 * @param pageSize ҳ��С
	 * @param orderProp ����������
	 * @param isAsc �Ƿ�����
	 * @param propertyName ����������
	 * @return Page 
	 */
	Page queryForUser(Class clazz, Map<String,Object> queryParams, int pageNo, int pageSize, String orderProp, boolean isAsc, String propertyName);
	
	
	/**
	 * ��ӽ��棬��ʼ���ֶεĹ����б���Map��ʽ���ع������ݡ�
	 * @Methods Name getEntityDataForAdd
	 * @Create In 2008-6-30 By peixf
	 * @param smt
	 * @return Map<String,Object> �������Ե����ƣ���������ֵ
	 */
	Map<String, Object> getEntityDataForAdd(Class clazz);
	
	/**
	 * ��ȡ���������������Ĺ������ݡ�
	 * �༭���棬�ӹ������󣬳�ʼ��ҳ������б����ݣ���Map��ʽ���ؽ����
	 * ǰ�˹̶�����ID������Ϊ id��
	 * ������չ��ʹ��Map requestParams�������Խ���������Ϊ��
	 * Map<String,Object> getEntityDataForEdit(Class clazz, Map requestParams);
	 * @Methods Name getEntityDataForEdit
	 * @Create In 2008-6-30 By peixf
	 * @param object
	 * @param smt
	 * @return Map<String,Object> �������Ե����ƣ���������ֵ
	 */
	Map<String,Object> getEntityDataForEdit(Class clazz, String objectId);
	
	
	/**
	 * ��ȡ���������������Ĺ������ݣ���getEntityDataForEdit(Class clazz, String objectId)
	 * �������Ǵ������
	 * @Methods Name getEntityDataForEdit
	 * @Create In 2008-9-5 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForEdit(Object object);
	
	/**
	 * ��ȡ���������������Ĺ�������
	 * @Methods Name getFormDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormDataForEdit(Object object);
	
	/**
	 * ��ȡ���������������Ĺ�������
	 * @Methods Name getFormDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormDataForEdit(Object object, String tableName);
	
	/**
	 * ��ȡ���������������Ĺ�������
	 * @Methods Name getFormDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormDataForEdit(Class clazz, String objectId);
	
	/**
	 * ��ȡ��ҳ���Ԥ������
	 * @Methods Name getEntityDataForLook
	 * @Create In Mar 11, 2009 By Administrator
	 * @param object
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForLook(Object object);
	
	
	Map<String,Object> getEntityDataForLook(Class clazz, String id);
	
	
	/**
	 * �˷�������getEntityDataForEdit(Object object)�����ṩ��2�ڿ��ʹ��, ���������ǰ׺�͹������ݷ���
	 * @Methods Name getEntityDataForEdit
	 * @Create In 2008-12-10 By sa
	 * @param object
	 * @param tableName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForEdit(Object object, String tableName);
	
	
	/**
	 * 2�ڿ��Ԥ������ʹ�õķ���
	 * @Methods Name getEntityDataForLook
	 * @Create In 2009-3-13 By sa
	 * @param object
	 * @param tableName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getEntityDataForLook(Object object, String tableName);

	/**
	 * ���б��ѯ�����map����ʽ���أ�������չ�ֶε����ݡ�
	 * Ŀǰ��ӦmetaDataUtil��getEntityDataForList2����
	 * @Methods Name getEntityMapDataForList
	 * @Create In 2008-6-30 By peixf
	 * @param mainList
	 * @param smt
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getEntityMapDataForList(Class clazz, List mainList);
	
	/**
	 * ���б��ѯ�����map����ʽ���أ�������չ�ֶε����ݡ�2������������
	 * @param clazz
	 * @param mainList
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getEntityMapDataForList(Class clazz, List mainList, String tableName);
	/**
	 * ��ʱ�����ǿ�����ǰ��ʹ������ǰ�ͳ�ʼ�����е�ʵ��������ݣ������ǹ��������ֹ������Ե������
	 * Ϊ��������⣬�����Գ�ַ���Hibernate�ӳټ������⣬���ṩ�˷�����
	 * ���б��ѯ�����ʵ����ʽ���أ���չ�ֶε����ݻ��Զ���װ������BaseObject��extendProps�����
	 * @Methods Name getEntityDataForList
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param mainList
	 * @return List<Object>
	 */
	List<Object> getEntityDataForList(Class clazz, List mainList);
	
	/**
	 * �������ݵ�excel�ļ��������������ɵĵ����ļ����ƣ���product_0998484843.xls��
	 * �����ļ����Ʒ�����ҳ�棬ʹ��Ӧ��ǰ׺+fileRootPath+product_0998484843.xls���������ļ�<br>
	 * @Methods Name outPutDatas
	 * @Create In 2008-5-21 By peixf
	 * @param clazz ����
	 * @param fileRootPath �����ļ������Ӧ�ø�Ŀ¼�����·������output/product/
	 * @param sheetName ����Excel�ļ���sheet����
	 * @param filePrefix ����Excel�ļ�����ǰ׺���ײ�ʵ���Դ�ǰ׺��ʱ����������������ɵ�product_0998484843.xls��ǰ׺Ϊproduct
	 * @param mainList Ҫ����������
	 * @return String ���ɵĵ����ļ�����
	 */
	String exportData(Class clazz, List mainList, String fileRootPath, String sheetName, String filePrefix);
	
	/**
	 * ����ʵ�����ݣ��������������Map�����ݸ��÷�����
	 * @Methods Name saveEntityData
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param requestParams void
	 */
	Object saveEntityData(Class clazz, Map requestParams);
	
	/**
	 * ����ɾ��ʵ�����ݣ�����ɾ����չ���ݡ�
	 * @Methods Name removeEntityData
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param id void
	 */
	void removeEntityData(Class clazz, String objectId);
	
	/**
	 * �߼����ɣ�ɾ���������������ʷ����
	 * @Methods Name removeEntityData
	 * @Create In 2008-7-22 By peixf
	 * @param clazz
	 * @param objectId void
	 */
	void removeEntityData(Class clazz, Class eventClazz, String objectId);
	
	/**
	 * ��ȡ�û��ɼ��������б��ֶΣ����б�ҳ����ʾ��Щ�ֶΡ�
	 * @Methods Name getUserColumnForList
	 * @Create In 2008-7-28 By sa
	 * @param clazz
	 * @return List<UserTableSetting>
	 */
	List<UserTableSetting> getUserColumnForList(Class clazz);
	
	/**
	 * ��ȡ����������û��������ֶ����ã���������޸�ҳ�棨ͨ����Ӧһ��JSPҳ�棩��ʾ��Щ�ֶΡ�
	 * @Methods Name getUserColumnForEdit
	 * @Create In 2008-7-28 By peixf
	 * @param clazz
	 * @return List<UserTableSetting>
	 */
	List<UserTableSetting> getUserColumnForEdit(Class clazz);
	
	/**
	 * ��ȡ�û��ɼ��Ĳ�ѯ�ֶΣ������б�ҳ�涥��Ҫ��ʾ��Щ�ֶ���Ϊ��ѯ������
	 * ע���ѯ�����ı�Ԫ�����Ϊ�����б�����Ҫ��ʼ�������ݣ������б�ҳ�涥����Ҫ�в�ѯ���ܣ�
	 * ������Ҫ���淽������ʼ����ѯ�ֶι�������
	 * @Methods Name getUserColumnForQuery
	 * @Create In 2008-7-28 By peixf
	 * @param clazz
	 * @return List<UserTableQueryColumn>
	 */
	List<UserTableQueryColumn> getUserColumnForQuery(Class clazz);
	
	/**
	 * ��ȡ�û��ɼ���ѯ�ֶε����й�������
	 * @Methods Name getUserColumnDataForQuery
	 * @Create In 2008-7-28 By peixf
	 * @param clazz
	 * @return Map<String,Object>
	 */
	Map<String,Object> getUserColumnDataForQuery(Class clazz);
	
	/**
	 * ����ʵ�����ݣ��������������Map�����ݸ��÷���,�����û���Ϣ������UserContext��ȡ��
	 * @param clazz
	 * @author tongjp
	 * @param requestParams
	 * @param user
	 * @return
	 */
	Object saveEntityDataForUser(Class clazz, Map requestParams,UserInfo user);
}
