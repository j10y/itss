package com.zsgj.itil.config.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.entity.CIBatchModify;
import com.zsgj.itil.config.entity.CIBatchModifyPlan;
import com.zsgj.itil.config.entity.CIRelationShip;
import com.zsgj.itil.config.entity.ConfigItem;

public interface ConfigItemDao extends Dao{
	
	/**
	 * ����������ҳ��ѯ����������
	 * ��Ҫ�����ģ�
	 * 1.���α���������½��ķǹ���״̬��
	 * 2.���������н��������������Ϊ�ǹ�����
	 * ���ܰ�����
	 * 1.���������н��ǹ�����Ϊ������
	 * 2.������������
	 * @Methods Name selectConfigItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param modifyId �������id
	 * @param name ����������
	 * @param cisn ��������
	 * @param configItemTypeId ����������id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectConfigItem(String modifyId,String  name,String cisn,Long configItemTypeId, int pageNo, int pageSize);
	/**
	 * ����������ҳ��ѯ���з�����
	 * @Methods Name selectServiceItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param name ����������
	 * @param code	��������
	 * @param serviceItemTypeId ����������id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectServiceItem(String  name,String code,Long serviceItemTypeId, int pageNo, int pageSize);
	/**
	 * ���ݸ��������������Ų�ѯֱ�����һ���ӣ�
	 * @Methods Name selectDirectChildRel
	 * @Create In Feb 23, 2010 By duxh
	 * @param itemCode
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectDirectChildRel(String itemCode);
	/**
	 * �ݹ��ȡ���������Ű�������
	 * @Methods Name selectAllChildCode
	 * @Create In Feb 25, 2010 By duxh
	 * @param itemCode ��������
	 * @param relId ��ѯʱ���ų��Ĺ�ϵid����
	 * @param ignoreRid ���ԵĹ�ϵ
	 * @Return Set<String> ����������������
	 */
	public Set<String> selectAllChildCode(String itemCode,List<Long> relId,List<Long> ignoreRid);
	/**
	 * �ݹ��ȡ���и����Ű�������
	 * @Methods Name selectAllParentCode
	 * @Create In Feb 25, 2010 By duxh
	 * @param itemCode ��������
	 * @param relId ��ѯʱ���ų��Ĺ�ϵid����
	 * @param ignoreRid ���ԵĹ�ϵ
	 * @Return Set<String> ����������������
	 */
	public Set<String> selectAllParentCode(String itemCode,List<Long> relId,List<Long> ignoreRid);
	/**
	 * ��ҳ��ѯ���������Ϣ
	 * @Methods Name selectBatchModifyInfo
	 * @Create In Mar 1, 2010 By duxh
	 * @param id �����Դ��id
	 * @param type �����Դ�����⣬����
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectBatchModifyInfo(Long id,String type,int pageNo, int pageSize);
	/**
	 *  ��ѯ�������������������
	 * @Methods Name selectBatchModifyCIList
	 * @Create In Mar 2, 2010 By duxh
	 * @param modifyId �������id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectBatchModifyCIList(Long modifyId, int pageNo, int pageSize) ;
	/**
	 * ��ѯ������������Ĺ�ϵ
	 * @Methods Name selectBatchModifyRelList
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId �������id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectBatchModifyRelList(Long modifyId, int pageNo, int pageSize) ;

	/**
	 * ��ѯ������ӹ�ϵ
	 * @Methods Name findAllRelationShip
	 * @Create In Mar 4, 2010 By duxh
	 * @param cisn ��������������
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectAllRelationShip(List<String> itemCode);
	/**
	 * ��������ʵ��
	 * @Methods Name saveOrUpdateObjects
	 * @Create In Mar 5, 2010 By duxh
	 * @param col ʵ�弯�� 
	 * @Return void
	 */
	public void saveOrUpdateObjects(Collection entity);
	/**
	 * ��ѯ���������Ϣ
	 * @Methods Name selectBatchModify
	 * @Create In Mar 5, 2010 By duxh
	 * @param modifyNo ������
	 * @param name ��������
	 * @param applyDateStart �ύ���ڿ�ʼ
	 * @param applyDateEnd �ύ���ڽ���
	 * @param status ״̬
	 * @param start
	 * @param size
	 * @Return Page
	 */
	public Page selectBatchModify(String modifyNo, String name, Date applyDateStart,Date applyDateEnd, Integer status, int start, int size);
	/**
	 * ����������ҳ��ѯ�������ϵ
	 * @Methods Name selectRelList
	 * @Create In Apr 14, 2010 By duxh
	 * @param basicMap ������ѯ����
	 * @param advancedMap �߼���ѯ����
	 * @Return Page
	 */
	public Page selectRelList(Map<String,String> basicMap, Map<String,String> advancedMap, int pageNo, int pageSize);
	/**
	 * ĳ���������Ч��ϵ
	 * @Methods Name selectRelList
	 * @Create In May 28, 2010 By duxh
	 * @param ignoreCode ���Ժ���Щ����������صĹ�ϵ
	 * @param itemCode ĳ��������
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectRelList(String itemCode,List<String> ignoreCode, int pageNo, int pageSize);
	/**
	 * ĳ���������Ч��ϵ
	 * @Methods Name selectRelList
	 * @Create In May 28, 2010 By duxh
	 * @param ignoreCode ���Ժ���Щ����������صĹ�ϵ
	 * @param itemCode �����������
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectRelList(String itemCode,List<String> ignoreCode);
	/**
	 * ĳ�α�����������й�ϵ���
	 * @Methods Name selectModifyRel
	 * @Create In May 28, 2010 By duxh
	 * @param modifyId
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectModifyRel(Long modifyId);
	/**
	 * ĳ�α�������к�ĳ��������ص�������ɾ���Ĺ�ϵ����ƻ�
	 * @Methods Name selectNewAndDeleteRel
	 * @Create In May 30, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectNewAndDeleteRel(Long modifyId,String cisn);
	/**
	 * ���ҳ־û�����
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity ��Ҫ��ѯ��Class����
	 * @param propertyName �������������Ƕ༶�����ԣ��磺userInfo.department.name��
	 * @param propertyValues ����ֵ����
	 * @param fetchProperty ��Ҫץȡ�����ԣ���ѡ������Ҫ��null.��
	 * @Return List<T>
	 */
	public <T> List<T> selectObjects(Class<T> entity,String propertyName,Object[] propertyValues,String fetchProperty);
	/**
	 * ���ҳ־û�����
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity ��Ҫ��ѯ��Class����
	 * @param propertysNameAndValue ������������ֵ�ļ�ֵ�ԣ�����ֵ����Ϊ����ֵ��һ�����ϻ�һ������,�����������Ƕ༶�����ԣ�
	 * @param fetchPropertys��Ҫץȡ�����Լ��ϣ���ѡ������Ҫ��null��
	 * @param orderProperty ��Ҫ���������
	 * @param isAsc������
	 * @Return List<T>
	 */
	public <T> List<T> selectObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys,String orderProperty,boolean isAsc);
	/**
	 * ���ҳ־û�����
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity ��Ҫ��ѯ��Class����
	 * @param propertysNameAndValue ������������ֵ�ļ�ֵ�ԣ�����ֵ����Ϊ����ֵ��һ�����ϻ�һ�����飬�����������Ƕ༶�����ԣ�
	 * @param fetchPropertys��Ҫץȡ�����Լ��ϣ���ѡ������Ҫ��null��
	 * @Return List<T>
	 */
	public <T> List<T> selectObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys);
	/**
	 * ɾ�����г־û�����
	 * @Methods Name deleteObjects
	 * @Create In Mar 6, 2010 By duxh
	 * @param entities 
	 * @Return void
	 */
	public void deleteObjects(Collection entities);
	/**
	 * ͨ���������ϵ�����ҵ�ǰ��½����ʦ����Ӧ�Ľ����Ŷӵļ���������
	 * @Methods Name selectDeliveryTeamTechnicalLeader
	 * @Create In Jul 7, 2010 By duxh
	 * @return Set<String> �û���
	 */
	public Set<String> selectDeliveryTeamTechnicalLeader();
	/**
	 * ��ѯ��ĳ�α�����Ƿ�������ĳ��������ı��
	 * @Methods Name selectHasCIModifyDraft
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId ���id
	 * @param cisn ��������
	 * @param ignoreCid ���Ե�������
	 * @Return CIBatchModifyPlan ���ڷ��ر���ƻ�
	 */
	public CIBatchModifyPlan selectHasCIModifyDraft(Long modifyId, String cisn,Long ignoreCid);
	/**
	 * ��ѯ��ĳ�α�����Ƿ�������ĳ���������ϵ�ı��
	 * @Methods Name selectHasCIRelModifyDraft
	 * @Create In Mar 29, 2010 By duxh
	 * @param modifyId ���id
	 * @param parentCode ��ϵ�����
	 * @param childCode ��ϵ�ӱ��
	 * @Return CIBatchModifyPlan ���ڷ��ر���ƻ�
	 */
	public CIBatchModifyPlan selectHasCIRelModifyDraft(Long modifyId, String parentCode,String childCode);
	/**
	 * �������к�ĳЩ��������صĹ�ϵ
	 * @Methods Name selectCIRelationShip
	 * @Create In Mar 25, 2010 By duxh
	 * @param cisn ��������
	 * @Return CIRelationShip
	 */
	public List<CIRelationShip> selectCIRelationShip(List<String> cisn);
	/**
	 * �������������ʽ��ϵ
	 * @Methods Name selectValidCIRelationShip
	 * @Create In Apr 21, 2010 By duxh
	 * @param cisn ��������
	 * @Return List<CIRelationShip> ��ϵ
	 */
	public List<CIRelationShip> selectValidCIRelationShip(String cisn);
	/**
	 * ������������ĳһʱ���Ĺ�ϵ
	 * �����߼����£�
	 *(
	 *	��ǰ��Ч�Ĺ�ϵ 
	 *	and (
	 * 	��ϵ�޸Ĺ��ҹ�ϵ���޸�ʱ��С�����ʱ���
	 * 	or
	 * 	��ϵδ�޸Ĺ��ҹ�ϵ�Ĵ���ʱ��С�����ʱ���
	 *  )
	 * ) or (
	 *  ��ǰ��ɾ���Ĺ�ϵ
	 *  and 
	 * 	����ʱ��С�����ʱ���
	 *  and
	 * 	�޸�ʱ��������ʱ���
	 * )
	 * @Methods Name selectCIRelationShipByDate
	 * @Create In May 13, 2010 By duxh
	 * @param cisn ��������
	 * @param date ʱ���
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectCIRelationShipByDate(String cisn,Date date);
	/**
	 * �½�����δ����ɹ�����������
	 * @Methods Name selectNewCIModifyUnsuccess
	 * @Create In Mar 31, 2010 By duxh
	 * @param bm ��������id
	 * @param cisn �½�����������
	 * @Return List<String> �½�����δ����ɹ�����������
	 */
	public List<String> selectNewCIModifyUnsuccess(CIBatchModify bm,List<String> cisn);
	/**
	 * ���ұ���ɹ�����״̬Ϊ"���á����á��ѹ鵵�����"��������
	 * @Methods Name selectOrphanCIModifySuccess
	 * @Create In Apr 23, 2010 By duxh
	 * @param bm
	 * @param cisn
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> selectOrphanCIModifySuccess(CIBatchModify bm,List<String> cisn);
	/**
	 * �鿴ĳ�α�������г���֮���Ƿ���ڴ��ֹ�ϵ�ݸ�
	 * @Methods Name selectHasSameDraft
	 * @Create In Apr 1, 2010 By duxh
	 * @param modifyId �������id
	 * @param parentCode �����
	 * @param childCode  �ӱ��
	 * @param ignoreRid Ҫ���ԵĹ�ϵid
	 * @Return boolean �Ƿ����
	 */
	public boolean selectHasSameDraft(Long modifyId,String parentCode,String childCode,Long ignoreRid);
	/**
	 * ��ѯ��ʽ,�����й�ϵ���Ƿ���ڴ��ֹ�ϵ
	 * @Methods Name selectHasSameValidAndProcessingRel
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode �����
	 * @param childCode  �ӱ��
	 * @param rids �����ԵĹ�ϵ
	 * @param ignoreRid Ҫ���ԵĹ�ϵ
	 * @Return boolean �Ƿ����
	 */
	public boolean selectHasSameValidAndProcessingRel(String parentCode,String childCode,List<Long> rids,Long ignoreRid);
	/**
	 * ��ѯ��ʽ��ϵ���Ƿ���ڴ��ֹ�ϵ
	 * @Methods Name selectHasSameValidRel
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode �����
	 * @param childCode  �ӱ��
	 * @Return CIRelationShip �����򷵻أ���null
	 */
	public CIRelationShip selectHasSameValidRel(String parentCode,String childCode);
	/**
	 * ���ڱ�������еĹ�ϵ����ƻ�
	 * @Methods Name selectProcessingRelPlan
	 * @Create In Apr 2, 2010 By duxh
	 * @param modifyId �������id
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectProcessingRelPlan(Long modifyId);
	/**
	 * ��Щ���������ڱ��������
	 * @Methods Name selectProcessingRel
	 * @Create In Apr 2, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<String>
	 */
	public List<String> selectCIProcessing(Long modifyId,List<String> cisn);
	/**
	 * ��ѯĳ�α�������У�cisn����ʾ�����������½�����Ϊ"���á����á��ѹ鵵�����"������Щ
	 * @Methods Name selectOrphanCI
	 * @Create In Apr 20, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> selectOrphanCI(Long modifyId,List<String> cisn);
	/**
	 * ��ѯ�����еı�����룬cisn����ʾ�����������½�������״̬Ϊ"���á����á��ѹ鵵�����"������Щ
	 * @Methods Name selectProcessingOrphanCI
	 * @Create In Apr 23, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> selectProcessingOrphanCI(Long modifyId,Set<String> cisn) ;
	/**
	 * ��ĳ��������ɾ��������cisn��ϵ�ı���ƻ�
	 * @Methods Name selectRelPlan
	 * @Create In Apr 22, 2010 By duxh
	 * @param modifyId ����id
	 * @param cisn ��������
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectDeleteRelPlan(Long modifyId,String cisn);
	/**
	 * ��ǰ�����ɾ�����������ϵ
	 * @Methods Name selectDeleteRel
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId �������id
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectDeleteRel(Long modifyId);
	/**
	 *	��ѯĳ�α�������б��ΪitemCode��������
	 * @Methods Name selectModifyConfigItem
	 * @Create In May 5, 2010 By duxh
	 * @param modifyId �������id
	 * @param itemCode ��������
	 * @Return ConfigItem
	 */
	public ConfigItem selectModifyConfigItem(Long modifyId,String itemCode);
	/**
	 * ��ҳ��ѯ��Ч����������Ի���Ϣ
	 * @Methods Name selectValidConfigItemExtendInfo
	 * @Create In Jun 9, 2010 By duxh
	 * @param entity ʵ�����ȫ�޶���
	 * @param map	��ѯ����
	 * @param fuzzyQuery ģ����ѯ�ֶ�
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	public Page selectValidConfigItemExtendInfo(String entity,Map map, List<String> fuzzyQuery,int pageNo, int pageSize);
	/**
	 * ͨ���������ϵ����ҳ��ѯ����ʦ
	 * @Methods Name selectServiceEngineer
	 * @Create In Jul 7, 2010 By duxh
	 * @param deliveryTeamId �����Ŷ�id
	 * @param map	��ѯ����
	 * @param fuzzyQuery ģ����ѯ�ֶ�
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page selectServiceEngineer(Long deliveryTeamId,Map map, List<String> fuzzyQuery,int pageNo, int pageSize);
	/**
	 * ��ѯĳ�α����ĳ������ά���ı�Ҫ��ϵ�ı���ƻ�
	 * @Methods Name selectMaintenanceRelPlan
	 * @Create In Jun 3, 2010 By duxh
	 * @param modifyId �������id
	 * @param maintenanceCIs ά����ϵ��������
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectMaintenanceRelPlan(Long modifyId,List<ConfigItem> maintenanceCIs);
	/**
	 * ά����ϵ��������ı���ƻ�
	 * @Methods Name selectMaintenanceConfigItem
	 * @Create In Jun 3, 2010 By duxh
	 * @param modifyId �������id
	 * @param ci ά����ϵ��������
	 * @param result ʵʩ���
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectMaintenanceConfigItem(Long modifyId, ConfigItem ci,Integer result);
	/**
	 * ��ѯrels����ʾ�Ĺ�ϵ�ж����ڱ��α����ɾ��
	 * @Methods Name selectDeleteRel
	 * @Create In Jun 11, 2010 By duxh
	 * @param modifyId �������id
	 * @param rels ��ϵ
	 * @return List<CIRelationShip> �ڱ��α����ɾ���Ĺ�ϵ
	 */
	public List<CIRelationShip> selectDeleteRel(Long modifyId,List<CIRelationShip> rels);
	/**
	 * ĳ��������Ϊ����״̬�Զ�ɾ���Ĺ�ϵ����ƻ�
	 * @Methods Name selectMaintenanceDeleteRelPlan
	 * @Create In Jun 12, 2010 By duxh
	 * @param modifyId  �������id
	 * @param maintenance ά���Զ�ɾ����ϵ��������
	 * @return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectMaintenanceDeleteRelPlan(Long modifyId,ConfigItem maintenance);

    /**
     * ����ȱ�ٱ�Ҫ��ϵ��������
     * @Methods Name selectConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @param paramMap ��ѯ����
     * @return void
     */
	public Page selectConfigItemNecessaryRelation(Map paramMap, int pageNo, int pageSize);
	/**
	 * ͨ���������ϵ���ҷ�������Ӧ��ά������ʦ
	 * @Methods Name selectServerEngineer
	 * @Create In Jul 30, 2010 By duxh
	 * @param cisn ��������������
	 * @return List<Long> ����ʦ��Ӧ���û���id
	 */
	public List<Long> selectServerEngineer(String cisn);
}
