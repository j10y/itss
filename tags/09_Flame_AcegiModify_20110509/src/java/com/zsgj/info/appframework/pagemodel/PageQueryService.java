package com.zsgj.info.appframework.pagemodel;

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
public interface PageQueryService {

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
	 * ʹ�ò�����ֵ�Խ��в�ѯ, params��ʽ������$��������Ϊkey������ֵ��Ϊvalue.
	 * �˷���������ɲ��������Զ�ת��. <br>�ײ��ȵ���before��
	 * ����middle(Criteria criteria,Map extParams)��end����.
	 * @Methods Name queryByParams
	 * @Create In 2008-10-17 By sa
	 * @param panelName �������
	 * @param params  ����������$��������Ϊkey������ֵ��Ϊvalue
	 * @param extParams ��������
	 * @param pageNo ��ʼҳ��
	 * @param pageSize ҳ���С
	 * @param orderProp ��������
	 * @param isAsc �Ƿ�����
	 * @return Page
	 */
	Page query(String panelName, Map<String,Object> params, Map<String,Object> extParams, 
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
	Criteria before(String panelName, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc);
	
	/**
	 * ������ѯ�����ط�ҳ�������а�����ҳ��Ϣ�����յĲ�ѯ���
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page end(Criteria criteria, int pageNo, int pageSize);
	
	
	Page end(Criteria criteria, Map params, int pageNo, int pageSize);
}
