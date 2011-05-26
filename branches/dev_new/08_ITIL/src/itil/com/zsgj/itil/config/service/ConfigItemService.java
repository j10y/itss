package com.zsgj.itil.config.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.entity.CIBatchModify;
import com.zsgj.itil.config.entity.CIBatchModifyPlan;
import com.zsgj.itil.config.entity.CIRelationShip;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemNecessaryRel;
import com.zsgj.itil.config.entity.ModleToProcess;

public interface ConfigItemService {
	/**
	 * ���桢������������Ϣ����������Ϣ��������Ϣ����չ��Ϣ
	 * @Methods Name saveOrUpdateConfigItem
	 * @Create In Jan 26, 2010 By duxh
	 * @param basicMap������Ϣ
	 * @param financeMap������Ϣ
	 * @param extendMap��չ��Ϣ
	 * @return ������id
	 */
	public ConfigItem saveOrUpdateConfigItem(Map basicMap,Map financeMap,Map extendMap);
	/**
	 * ������Щ��Ҫ��ϵ��������
	 * @Methods Name findNotExistNecessaryRel
	 * @Create In Jul 10, 2010 By duxh
	 * @param necessaryRels ���б�Ҫ��ϵ
	 * @param rels �Ѵ��ڵĹ�ϵ
	 * @return List<ConfigItemNecessaryRel> �����ڵı�Ҫ��ϵ
	 */
	public List<ConfigItemNecessaryRel> findNotExistNecessaryRel(List<ConfigItemNecessaryRel> necessaryRels,List<CIRelationShip> rels);
	/**
	 * �����Ƿ��в����ڵĿ�ѡ��Ҫ��ϵ
	 * @Methods Name findHasNotExistOptionalRel
	 * @Create In Jul 8, 2010 By duxh
	 * @param modifyId ��������id
	 * @param configItemTypeId ��Ҫ��ѯ������������
	 * @param cisn ��Ҫ��ѯ����������
	 * @return List<ConfigItemNecessaryRel> �����ڵĿ�ѡ��Ҫ��ϵ
	 */
	public List<ConfigItemNecessaryRel> findHasNotExistOptionalRel(Long modifyId,Long configItemTypeId,String cisn);
	/**
	 * ���桢������������Ϣ����������Ϣ��������Ϣ����չ��Ϣ������ƻ�
	 * @Methods Name saveOrUpdateConfigItemAndPlan
	 * @Create In Mar 25, 2010 By duxh
	 * @param basicMap ������Ϣ
	 * @param financeMap ������Ϣ
	 * @param extendMap ��չ��Ϣ
	 * @param planMap ����ƻ�
	 * @param modifyId ���id
	 * @param createAllNecessaryRel  �Ƿ��������б�Ҫ��ϵ
	 * @Return CIBatchModifyPlan ���ر���ƻ�
	 */
	public CIBatchModifyPlan saveOrUpdateConfigItemAndPlan(Map basicMap,Map financeMap,Map extendMap,Map planMap,String modifyId,boolean createAllNecessaryRel);
	/**
	 * �������¹�ϵ�������ƻ�
	 * @Methods Name saveOrUpdateRelAndPlan
	 * @Create In Mar 26, 2010 By duxh
	 * @param relMap ��ϵ
	 * @param relPlanMap ����ƻ�
	 * @param modifyId �������id
	 * @Return CIBatchModifyPlan ����ƻ�
	 */
	public CIBatchModifyPlan saveOrUpdateRelAndPlan(Map relMap,Map relPlanMap,String modifyId);

	/**
	 * ����������ҳ��ѯ����������
	 * @Methods Name findConfigItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param modifyId �������id
	 * @param name ����������
	 * @param cisn ��������
	 * @param configItemTypeId ����������
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findConfigItem(String modifyId,String name,String cisn,Long configItemTypeId, int pageNo, int pageSize);
	/**
	 * ����������ҳ��ѯ���з�����
	 * @Methods Name findServiceItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param name ����������
	 * @param code ��������
	 * @param serviceItemTypeId ����������
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findServiceItem(String  name,String code,Long serviceItemTypeId, int pageNo, int pageSize);
	/**
	 * ����������ҳ��ѯ�������ϵ
	 * @Methods Name findRelList
	 * @Create In Apr 15, 2010 By duxh
	 * @param basicMap ������ѯ����
	 * @param advancedMap �߼���ѯ����
	 * @Return Page
	 */
	public Page findRelList(Map<String,String> basicMap, Map<String,String> advancedMap, int pageNo, int pageSize);
	/**
	 * ��ҳ��ȡĳ����������滻�Ĺ�ϵ��Ϣ
	 * @Methods Name findReplaceRelList
	 * @Create In May 28, 2010 By duxh
	 * @param modifyId �������id
	 * @param itemCode ��������
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findReplaceRelList(String itemCode,Long modifyId, int pageNo, int pageSize);
	/**
	 * ��ȡĳ����������滻�Ĺ�ϵ��Ϣ
	 * @Methods Name findReplaceRelList
	 * @Create In May 28, 2010 By duxh
	 * @param modifyId �������id
	 * @param itemCode ��������
	 * @Return List<CIRelationShip> ��ϵ
	 */
	public List<CIRelationShip> findReplaceRelList(String itemCode,Long modifyId);

	/**
	 * ���ݸ��������������ѯֱ�����һ���ӣ�
	 * @Methods Name findDirectChildRel
	 * @Create In Feb 23, 2010 By duxh
	 * @param itemCode ��������
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> findDirectChildRel(String itemCode);
	/**
	 * ��ȡ�����������
	 * @Methods Name findBatchModifyInfo
	 * @Create In Mar 1, 2010 By duxh
	 * @param id �����Դ��id
	 * @param type �����Դ���ͣ����⣬����
	 * @Return Page
	 */
	public Page findBatchModifyInfo(Long id,String type,int pageNo, int pageSize);
	/**
	 * �����������ʵ�弰���������Դʵ��
	 * @Methods Name saveCIBatchModify
	 * @Create In Mar 1, 2010 By duxh
	 * @param batchModifyMap �����Ϣ
	 * @param typeId �����Դid
	 * @param type �����Դ���ͣ����⣬����
	 * @Return Long
	 */
	public Long saveCIBatchModify(Map batchModifyMap,Long typeId,String type);
	/**
	 * ��ѯ�����������������������
	 * @Methods Name findBatchModifyCIList
	 * @Create In Mar 2, 2010 By duxh
	 * @param modifyId
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findBatchModifyCIList(Long modifyId,int pageNo, int pageSize);
	/**
	 * ��ѯ����������������Ĺ�ϵ
	 * @Methods Name findBatchModifyRelList
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findBatchModifyRelList(Long modifyId, int pageNo, int pageSize) ;
	/**
	 * ��ѯ��ĳ�α�����Ƿ�������ĳ��������ı��
	 * @Methods Name findHasCIModifyDraft
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId ���id
	 * @param cisn ��������
	 * @param ignoreCid ���Ե�������id
	 * @Return CIBatchModifyPlan
	 */
	public CIBatchModifyPlan findHasCIModifyDraft(Long modifyId,String cisn,Long ignoreCid);
	/**
	 * ��ѯ��ĳ�α�����Ƿ�������ĳ���������ϵ�ı��
	 * @Methods Name findHasCIRelModifyDraft
	 * @Create In Mar 29, 2010 By duxh
	 * @param modifyId ���id
	 * @param parentCode ��ϵ�����
	 * @param childCode ��ϵ�ӱ��
	 * @Return CIBatchModifyPlan
	 */
	public CIBatchModifyPlan findHasCIRelModifyDraft(Long modifyId, String parentCode,String childCode);
	
	/**
	 * ɾ������ƻ�����������½���������͹�ϵ
	 * @Methods Name removeBatchModifyPlans
	 * @Create In Mar 4, 2010 By duxh
	 * @param planId ����ƻ�id
	 * @Return void
	 */
	public void removeBatchModifyPlans(Long[] planId);
	/**
	 * �������ɹ��ı���ƻ�
	 * ���������Ϊ����״̬���ұ���ɹ�ʱ������ɾ���Ĺ�ϵ�������ɹ�
	 * @Methods Name saveSuccessCIBatchModifyPlan
	 * @Create In Mar 5, 2010 By duxh
	 * @param plans
	 * @Return void
	 */
	public void saveSuccessCIBatchModifyPlan(List<CIBatchModifyPlan> plans);
	/**
	 * ����δ����ɹ��ı���ƻ�
	 * �½���������δ����ɹ�ʱ����Ҫ�����ϵ���δ�ɹ���
	 * @Methods Name saveUnSuccessCIBatchModifyPlan
	 * @Create In Apr 22, 2010 By duxh
	 * @param plans 
	 * @Return void
	 */
	public void saveUnSuccessCIBatchModifyPlan(List<CIBatchModifyPlan> plans);
	/**
	 * ����������������ı������
	 * @Methods Name saveSuccessModify
	 * @Create In Mar 5, 2010 By duxh 
	 * @param modifyId �������id
	 * @Return void
	 */
	public CIBatchModify saveSuccessModify(Long modifyId);
	/**
	 * ���泷���ı�����루�����ģ�
	 * @Methods Name saveUnSuccessModify
	 * @Create In Jun 10, 2010 By duxh
	 * @param bm 
	 * @Return void
	 */
	public void saveUnSuccessModify(CIBatchModify bm);
	/**
	 * ��ѯ���������Ϣ
	 * @Methods Name findBatchModify
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
	public Page findBatchModify(String modifyNo, String name, Date applyDateStart,Date applyDateEnd, Integer status, int start, int size);
	/**
	 * ɾ����������ݸ岢����ɾ������ƻ����½��ͱ�����������ϵ
	 * @Methods Name removeBatchModifyDraft
	 * @Create In Mar 6, 2010 By duxh
	 * @param modifyIdArray 
	 * @Return void
	 */
	public void removeBatchModifyDraft(Long[] modifyIdArray);

	/**
	 * ���ҵ�ǰ��½�û����ڽ����Ŷӵļ���������
	 * @Methods Name findDeliveryTeamTechnicalLeader
	 * @Create In Jul 7, 2010 By duxh
	 * @return Set<String>�û���
	 */
	public Set<String> findDeliveryTeamTechnicalLeader();
	/**
	 * ���ҳ־û�����
	 * @Methods Name selectObjects
	 * @Create In Mar 6, 2010 By duxh
	 * @param entity  ʵ��Class����
	 * @param propertyName ������
	 * @param propertyValues ����ֵ
	 * @param fetchProperty ��Ҫץȡ�Ķ��󣨿�ѡ������Ҫ�Ǵ�null��
	 * @Return List<Object> �з��أ��޷��ؿյļ���
	 */
	public <T> List<T> findObjects(Class<T> entity,String propertyName,Object[] propertyValues,String fetchProperty);
	/**
	 * ��ѯ�½��Ĺ�ϵ���漰��������Ϊ�½�����δ����ɹ��ı��
	 * @Methods Name findNewCIModifyUnsuccess
	 * @Create In Mar 31, 2010 By duxh
	 * @param plans �½���ϵ��صı���ƻ�
	 * @Return List<String> �½��Ĺ�ϵ���漰��������Ϊ�½�����δ����ɹ��ı��
	 */
	public List<String> findNewCIModifyUnsuccess(List<CIBatchModifyPlan> plans);
	/**
	 * ���ɾ���Ĺ�ϵ�漰����������Щ�ڱ��α���б�Ϊ����״̬�����Ѿ�����ɹ�
	 * @Methods Name findOrphanCIModifySuccess
	 * @Create In Apr 23, 2010 By duxh
	 * @param plans ���ɾ���Ĺ�ϵ�ı���ƻ�
	 * @Return List<ConfigItem> �������ұ���ɹ���������
	 */
	public List<ConfigItem> findOrphanCIModifySuccess(List<CIBatchModifyPlan> plans);
	/**
	 * ��������ʵ��
	 * @Methods Name saveOrUpdateObjects
	 * @Create In Mar 5, 2010 By duxh
	 * @param entity ʵ�弯��
	 * @Return void
	 */
	public void saveOrUpdateObjects(Collection entity);
	/**
	 * ���ҳ־û�����
	 * @Methods Name findObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity ��Ҫ��ѯ��Class����
	 * @param propertysNameAndValue ������������ֵ�ļ�ֵ�ԣ�����ֵ����Ϊ����ֵ��һ�����ϻ�һ�����飩
	 * @param fetchPropertys��Ҫץȡ�����Լ��ϣ���ѡ������Ҫ��null��
	 * @Return List<T>
	 */
	public <T> List<T> findObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys);
	/**
	 * ���ҳ־û�����
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity ��Ҫ��ѯ��Class����
	 * @param propertysNameAndValue ������������ֵ�ļ�ֵ�ԣ�����ֵ����Ϊ����ֵ��һ�����ϻ�һ�����飩
	 * @param fetchPropertys��Ҫץȡ�����Լ��ϣ���ѡ������Ҫ��null��
	 * @param orderProperty ��Ҫ���������
	 * @param isAsc������
	 * @Return List<T>
	 */
	public <T> List<T> findObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys,String orderProperty,boolean isAsc);
	/**
	 * ��ѯ��ϵ�Ƿ��л����ڡ�
	 * @Methods Name findLoop
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode �����
	 * @param childCode �ӱ��
	 * @param relsId �����ԵĹ�ϵid
	 * @param ignoreRid Ҫ���ԵĹ�ϵid
	 * @Return Set<String> ���ڻ��ı��
	 */
	public Set<String> findLoop(String parentCode,String childCode,List<Long> relsId,List<Long> ignoreRid);
	/**
	 * �鿴ĳ�α���������Ƿ���ڴ��ֹ�ϵ�ݸ�
	 * @Methods Name selectHasSameDraft
	 * @Create In Apr 1, 2010 By duxh
	 * @param modifyId �������id
	 * @param parentCode �����
	 * @param childCode  �ӱ��
	 * @param ignoreRid Ҫ���ԵĹ�ϵid
	 * @Return boolean �Ƿ����
	 */
	public boolean findHasSameDraft(Long modifyId,String parentCode,String childCode,Long ignoreRid);
	/**
	 * ��ѯ��ʽ��ϵ���Ƿ���ڴ��ֹ�ϵ
	 * @Methods Name findHasSameValidRel
	 * @Create In May 28, 2010 By duxh
	 * @param parentCode �����
	 * @param childCode	 �ӱ��
	 * @Return CIRelationShip ���򷵻أ�����null
	 */
	public CIRelationShip findHasSameValidRel(String parentCode,String childCode);
	/**
	 * ��ѯ��ʽ,�����й�ϵ���Ƿ���ڴ��ֹ�ϵ
	 * @Methods Name findHasSameValidAndProcessingRel
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode �����
	 * @param childCode	�ӱ��
	 * @param rids �����ԵĹ�ϵ
	 * @param ignoreRid Ҫ���ԵĹ�ϵ
	 * @Return boolean �Ƿ����
	 */
	public boolean findHasSameValidAndProcessingRel(String parentCode,String childCode,List<Long> rids,Long ignoreRid);
	/**
	 * ���ڱ�������еĹ�ϵ����ƻ�
	 * @Methods Name findProcessingRelPlan
	 * @Create In Apr 2, 2010 By duxh
	 * @param modifyId �������id
	 * @Return List<CIBatchModifyPlan> ����ƻ�
	 */
	public List<CIBatchModifyPlan> findProcessingRelPlan(Long modifyId);
	
	/**
	 * ��Щ���������ڱ��������
	 * @Methods Name selectProcessingRel
	 * @Create In Apr 2, 2010 By duxh
	 * @Return List<String>
	 */
	public List<String> findCIProcessing(Long modifyId,List<String> cisn);
	/**
	 * 1.�Ѵ��ڴ��ֹ�ϵ
	 * 2.�����������ɹ��˱��������б����������
	 * 3.�����ɾ��
	 * 4.�����������ɹ��˱��������б���Ĺ�ϵ
	 * 5.����������ɾ�����ұ���ɹ��˱��α���б���Ĺ�ϵ
	 * �����������ϵ�Ѵ��ڻ��ѱ����,�����ύ��Ϊ���Ѵ��ڻ��ѱ��������������ϵ�Ļ���֮�������!
	 * ���ԭ��������ϵ�ѱ�ɾ��,����������״̬Ϊ���,�����ύ��Ϊ�½�!
	 * @Methods Name saveOrUpdateOldCIAndOldRel
	 * @Create In Apr 3, 2010 By duxh
	 * @param plans
	 */
	public void saveOrUpdateOldCIAndOldRel(List<CIBatchModifyPlan> plans);
	/**
	 * ��ѯĳ�α�������У�cisn����ʾ�����������½�����Ϊ"���á����á��ѹ鵵�����"������Щ
	 * @Methods Name findOrphanCI
	 * @Create In Apr 20, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> findOrphanCI(Long modifyId,List<String> cisn);
	/**
	 * ��ѯ�����еı�����룬cisn����ʾ�����������½�����Ϊ"���á����á��ѹ鵵�����"������Щ
	 * @Methods Name findProcessingOrphanCI
	 * @Create In Apr 23, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> findProcessingOrphanCI(Long modifyId,Set<String> cisn);
	/**
	 * �������������ʽ��ϵ
	 * @Methods Name findValidCIRelationShip
	 * @Create In Apr 21, 2010 By duxh
	 * @param cisn ��������
	 * @Return List<CIRelationShip> ��ϵ
	 */
	public List<CIRelationShip> findValidCIRelationShip(String cisn);
	/**
	 * ����ĳ������Ĺ�ϵ
	 * 1.�������������Ч����ѯ����Ч�Ĺ�ϵ
	 * 2.�����������ɾ������ѯɾ���Ǹ�ʱ���Ĺ�ϵ
	 * @Methods Name findCIRelationShip
	 * @Create In Apr 21, 2010 By duxh
	 * @param ci ������
	 * @Return List<CIRelationShip> ��ϵ
	 */
	public List<CIRelationShip> findCIRelationShip(ConfigItem ci);
	/**
	 * ���ݱ�Ų�������������������ƣ����û����Ч�ģ���ӵ�ǰ��������в���
	 * @Methods Name findItemName
	 * @Create In May 5, 2010 By duxh
	 * @param item ��ʶ��������Ƿ�����
	 * @param itemCode �������������
	 * @param modifyId ���id
	 * @Return String ���������������
	 */
	public String findItemName(String item,String itemCode,Long modifyId);
	/**
	 * ��ҳ��ѯ��Ч����������Ի���Ϣ
	 * @Methods Name findValidConfigItemExtendInfo
	 * @Create In May 12, 2010 By duxh
	 * @param <T>
	 * @param entity ʵ�����ȫ�޶���
	 * @param map	��ѯ����
	 * @param fuzzyQuery ģ����ѯ�ֶ�
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findValidConfigItemExtendInfo(String entity,Map map,List<String> fuzzyQuery, int pageNo, int pageSize);
	/**
	 * ��ҳ��ѯ����ʦ
	 * @Methods Name findServiceEngineer
	 * @Create In Jul 7, 2010 By duxh
	 * @param deliveryTeamId �����Ŷ�id
	 * @param map	��ѯ����
	 * @param fuzzyQuery ģ����ѯ�ֶ�
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findServiceEngineer(Long deliveryTeamId,Map map,List<String> fuzzyQuery, int pageNo, int pageSize);
	/**
	 * ��ǰ�����ɾ�����������ϵ
	 * @Methods Name findDeleteRel
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId �������id
	 * @Return List<CIRelationShip> ɾ���Ĺ�ϵ
	 */
	public List<CIRelationShip> findDeleteRel(Long modifyId);
	/**
	 * ɾ��ָ��id�ĳ־û�����
	 * @Methods Name removeObjects
	 * @Create In May 25, 2010 By duxh
	 * @param entitieClass ʵ���class����
	 * @param ids id����
	 * @Return void
	 */
	public void removeObjects(Class entitieClass,Long[] ids);
	/**
	 * �����������ϵ�滻
	 * @Methods Name saveReplaceRel
	 * @Create In May 28, 2010 By duxh
	 * @param source ԭ��ϵ
	 * @param target �滻֮��Ĺ�ϵ
	 * @param modifyId �������id
	 * @Return void
	 */
	public void saveReplaceRel(List<CIRelationShip> source,List<CIRelationShip> target,Long modifyId);
	/**
	 * ��ѯĳ������Ĺ�ϵ=��Ч�Ĺ�ϵ+�������½�-������ɾ��
	 * @Methods Name findCIRelationShip
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId �������id
	 * @param cisn ��������
	 * @Return List<CIRelationShip> �������ϵ
	 */
	public List<CIRelationShip> findCIRelationShip(Long modifyId,String cisn);
	/**
	 * ��ѯĳ�α�����������ĳ������ı��
	 * @Methods Name findModifyConfigItem
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId �������id
	 * @param itemCode ��������
	 * @Return ConfigItem ���ظ�������
	 */
	public ConfigItem findModifyConfigItem(Long modifyId, String itemCode);
	/**
	 * ����ά����ϵ�ı���ƻ������ɱ�Ҫ��ϵ
	 * @Methods Name savePlanAndCreateNecessaryRel
	 * @Create In Jun 2, 2010 By duxh
	 * @param planMap ����ƻ���Ϣ
	 * @param modifyId �������id
	 * @param ci ά����ϵ��������
	 * @param createAllNecessaryRel �Ƿ��������б�Ҫ��ϵ������ѡ��
	 * @Return void
	 */
	public void savePlanAndCreateNecessaryRel(Map planMap,Long modifyId,ConfigItem ci,boolean createAllNecessaryRel);
	/**
	 * ��ѯ�Ƿ�ӵ�����б�Ҫ��ϵ
	 * @Methods Name findHasAllNecessaryRel
	 * @Create In Jun 3, 2010 By duxh
	 * @param ci ��Ҫ��ѯ��������
	 * @param modifyId �������id
	 * @Return boolean �Ƿ�ȫ������
	 */
	public boolean findHasAllNecessaryRel(ConfigItem ci,Long modifyId);
	/**
	 *  ά����ϵ��������ı���ƻ�
	 * @Methods Name findMaintenanceConfigItem
	 * @Create In Jun 3, 2010 By duxh
	 * @param modifyId �������id
	 * @param ci ������
	 * @param result ʵʩ���
	 * @Return List<CIBatchModifyPlan> ����ƻ�
	 */
	public List<CIBatchModifyPlan> findMaintenanceConfigItem(Long modifyId, ConfigItem ci,Integer result);
	/**
	 * ��ѯĳ�α����ĳ������ά���ı�Ҫ��ϵ
	 * @Methods Name findMaintenanceRelPlan
	 * @Create In Jun 4, 2010 By duxh
	 * @param modifyId �������id
	 * @param maintenanceCIs ά����ϵ��������
	 * @Return List<CIRelationShip> ά���Ĺ�ϵ
	 */
	public List<CIRelationShip> findMaintenanceRelPlan(Long modifyId,ConfigItem maintenanceCI);
	/**
	 * ���ɱ�Ҫ��ϵ
	 * @Methods Name saveNecessaryRel
	 * @Create In Jun 4, 2010 By duxh
	 * @param modifyId �������id
	 * @param ci ���ɹ�ϵ��������
	 * @param ciPlan ���ɹ�ϵ�����������ƻ�
	 * @param createAllNecessaryRel �Ƿ��������б�Ҫ��ϵ������ѡ��
	 * @Return void
	 */
	public void saveNecessaryRel(Long modifyId,ConfigItem ci,CIBatchModifyPlan ciPlan,boolean createAllNecessaryRel);
	/**
	 * ��ѯrels����ʾ�Ĺ�ϵ�ж����ڱ��α����ɾ��
	 * @Methods Name findDeleteRel
	 * @Create In Jun 11, 2010 By duxh
	 * @param modifyId �������id
	 * @param rels ��ϵid
	 * @return List<CIRelationShip>
	 */
	public List<CIRelationShip> findDeleteRel(Long modifyId,List<CIRelationShip> rels);
	
    /**
     * ����ȱ�ٱ�Ҫ��ϵ��������
     * @Methods Name getConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @return void
     */
	public Page getConfigItemNecessaryRelation(Map paramMap,int pageNo,int pageSize);
	
	
    /**
     * ����ȱ�ٱ�Ҫ��ϵ��������
     * @Methods Name getConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @return void
     */	
	public List<CIRelationShip>  saveNecessaryRelation(String[] necessaryIds,String batchModifyId,String[] configItemCode,List<ConfigItem> cis);
	/**
	 * ͨ���������ϵ���ҷ�������Ӧ��ά������ʦ
	 * @Methods Name findServerEngineer
	 * @Create In Jul 30, 2010 By duxh
	 * @param cisn ��������������
	 * @return List<Long> ����ʦ��Ӧ���û���id
	 */
	public List<Long> findServerEngineer(String cisn);
	/**
	 * ͨ��ģ�����ͺ�����������ȷ���ߵ�����Ҫ���ĸ�
	 * @param modleType
	 * @param processStatusType
	 * @return
	 */
	public ModleToProcess findProcessByParm(String modleType,String processStatusType);
}
