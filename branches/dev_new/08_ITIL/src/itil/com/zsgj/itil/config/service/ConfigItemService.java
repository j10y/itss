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
	 * 保存、更新配置项信息包括基本信息，财务信息，扩展信息
	 * @Methods Name saveOrUpdateConfigItem
	 * @Create In Jan 26, 2010 By duxh
	 * @param basicMap基本信息
	 * @param financeMap财务信息
	 * @param extendMap扩展信息
	 * @return 配置项id
	 */
	public ConfigItem saveOrUpdateConfigItem(Map basicMap,Map financeMap,Map extendMap);
	/**
	 * 查找哪些必要关系还不存在
	 * @Methods Name findNotExistNecessaryRel
	 * @Create In Jul 10, 2010 By duxh
	 * @param necessaryRels 所有必要关系
	 * @param rels 已存在的关系
	 * @return List<ConfigItemNecessaryRel> 不存在的必要关系
	 */
	public List<ConfigItemNecessaryRel> findNotExistNecessaryRel(List<ConfigItemNecessaryRel> necessaryRels,List<CIRelationShip> rels);
	/**
	 * 查找是否有不存在的可选必要关系
	 * @Methods Name findHasNotExistOptionalRel
	 * @Create In Jul 8, 2010 By duxh
	 * @param modifyId 变更申请的id
	 * @param configItemTypeId 需要查询的配置项类型
	 * @param cisn 需要查询的配置项编号
	 * @return List<ConfigItemNecessaryRel> 不存在的可选必要关系
	 */
	public List<ConfigItemNecessaryRel> findHasNotExistOptionalRel(Long modifyId,Long configItemTypeId,String cisn);
	/**
	 * 保存、更新配置项信息包括基本信息，财务信息，扩展信息、变更计划
	 * @Methods Name saveOrUpdateConfigItemAndPlan
	 * @Create In Mar 25, 2010 By duxh
	 * @param basicMap 基本信息
	 * @param financeMap 财务信息
	 * @param extendMap 扩展信息
	 * @param planMap 变更计划
	 * @param modifyId 变更id
	 * @param createAllNecessaryRel  是否生成所有必要关系
	 * @Return CIBatchModifyPlan 返回变更计划
	 */
	public CIBatchModifyPlan saveOrUpdateConfigItemAndPlan(Map basicMap,Map financeMap,Map extendMap,Map planMap,String modifyId,boolean createAllNecessaryRel);
	/**
	 * 保存或更新关系及其变更计划
	 * @Methods Name saveOrUpdateRelAndPlan
	 * @Create In Mar 26, 2010 By duxh
	 * @param relMap 关系
	 * @param relPlanMap 变更计划
	 * @param modifyId 变更申请id
	 * @Return CIBatchModifyPlan 变更计划
	 */
	public CIBatchModifyPlan saveOrUpdateRelAndPlan(Map relMap,Map relPlanMap,String modifyId);

	/**
	 * 根据条件分页查询所有配置项
	 * @Methods Name findConfigItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param name 配置项名称
	 * @param cisn 配置项编号
	 * @param configItemTypeId 配置项类型
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findConfigItem(String modifyId,String name,String cisn,Long configItemTypeId, int pageNo, int pageSize);
	/**
	 * 根据条件分页查询所有服务项
	 * @Methods Name findServiceItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param name 服务项名称
	 * @param code 服务项编号
	 * @param serviceItemTypeId 服务项类型
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findServiceItem(String  name,String code,Long serviceItemTypeId, int pageNo, int pageSize);
	/**
	 * 根据条件分页查询配置项关系
	 * @Methods Name findRelList
	 * @Create In Apr 15, 2010 By duxh
	 * @param basicMap 基本查询条件
	 * @param advancedMap 高级查询条件
	 * @Return Page
	 */
	public Page findRelList(Map<String,String> basicMap, Map<String,String> advancedMap, int pageNo, int pageSize);
	/**
	 * 分页获取某配置项可以替换的关系信息
	 * @Methods Name findReplaceRelList
	 * @Create In May 28, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param itemCode 配置项编号
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findReplaceRelList(String itemCode,Long modifyId, int pageNo, int pageSize);
	/**
	 * 获取某配置项可以替换的关系信息
	 * @Methods Name findReplaceRelList
	 * @Create In May 28, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param itemCode 配置项编号
	 * @Return List<CIRelationShip> 关系
	 */
	public List<CIRelationShip> findReplaceRelList(String itemCode,Long modifyId);

	/**
	 * 根据父配置项或服务项查询直接子项（一级子）
	 * @Methods Name findDirectChildRel
	 * @Create In Feb 23, 2010 By duxh
	 * @param itemCode 配置项编号
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> findDirectChildRel(String itemCode);
	/**
	 * 获取批量变更数据
	 * @Methods Name findBatchModifyInfo
	 * @Create In Mar 1, 2010 By duxh
	 * @param id 变更来源的id
	 * @param type 变更来源类型（问题，需求）
	 * @Return Page
	 */
	public Page findBatchModifyInfo(Long id,String type,int pageNo, int pageSize);
	/**
	 * 保存批量变更实体及批量变更来源实体
	 * @Methods Name saveCIBatchModify
	 * @Create In Mar 1, 2010 By duxh
	 * @param batchModifyMap 变更信息
	 * @param typeId 变更来源id
	 * @param type 变更来源类型（问题，需求）
	 * @Return Long
	 */
	public Long saveCIBatchModify(Map batchModifyMap,Long typeId,String type);
	/**
	 * 查询所有批量变更关联的配置项
	 * @Methods Name findBatchModifyCIList
	 * @Create In Mar 2, 2010 By duxh
	 * @param modifyId
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findBatchModifyCIList(Long modifyId,int pageNo, int pageSize);
	/**
	 * 查询所有批量变更关联的关系
	 * @Methods Name findBatchModifyRelList
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findBatchModifyRelList(Long modifyId, int pageNo, int pageSize) ;
	/**
	 * 查询在某次变更中是否存在针对某个配置项的变更
	 * @Methods Name findHasCIModifyDraft
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId 变更id
	 * @param cisn 配置项编号
	 * @param ignoreCid 忽略的配置项id
	 * @Return CIBatchModifyPlan
	 */
	public CIBatchModifyPlan findHasCIModifyDraft(Long modifyId,String cisn,Long ignoreCid);
	/**
	 * 查询在某次变更中是否存在针对某个配置项关系的变更
	 * @Methods Name findHasCIRelModifyDraft
	 * @Create In Mar 29, 2010 By duxh
	 * @param modifyId 变更id
	 * @param parentCode 关系父编号
	 * @param childCode 关系子编号
	 * @Return CIBatchModifyPlan
	 */
	public CIBatchModifyPlan findHasCIRelModifyDraft(Long modifyId, String parentCode,String childCode);
	
	/**
	 * 删除变更计划及其关联的新建的配置项和关系
	 * @Methods Name removeBatchModifyPlans
	 * @Create In Mar 4, 2010 By duxh
	 * @param planId 变更计划id
	 * @Return void
	 */
	public void removeBatchModifyPlans(Long[] planId);
	/**
	 * 保存变更成功的变更计划
	 * 变更配置项为孤立状态并且变更成功时，其变更删除的关系必须变更成功
	 * @Methods Name saveSuccessCIBatchModifyPlan
	 * @Create In Mar 5, 2010 By duxh
	 * @param plans
	 * @Return void
	 */
	public void saveSuccessCIBatchModifyPlan(List<CIBatchModifyPlan> plans);
	/**
	 * 保存未变更成功的变更计划
	 * 新建的配置项未变更成功时，需要将其关系变更未成功。
	 * @Methods Name saveUnSuccessCIBatchModifyPlan
	 * @Create In Apr 22, 2010 By duxh
	 * @param plans 
	 * @Return void
	 */
	public void saveUnSuccessCIBatchModifyPlan(List<CIBatchModifyPlan> plans);
	/**
	 * 保存正常处理结束的变更申请
	 * @Methods Name saveSuccessModify
	 * @Create In Mar 5, 2010 By duxh 
	 * @param modifyId 变更申请id
	 * @Return void
	 */
	public CIBatchModify saveSuccessModify(Long modifyId);
	/**
	 * 保存撤销的变更申请（放弃的）
	 * @Methods Name saveUnSuccessModify
	 * @Create In Jun 10, 2010 By duxh
	 * @param bm 
	 * @Return void
	 */
	public void saveUnSuccessModify(CIBatchModify bm);
	/**
	 * 查询批量变更信息
	 * @Methods Name findBatchModify
	 * @Create In Mar 5, 2010 By duxh
	 * @param modifyNo 申请编号
	 * @param name 申请名称
	 * @param applyDateStart 提交日期开始
	 * @param applyDateEnd 提交日期结束
	 * @param status 状态
	 * @param start
	 * @param size
	 * @Return Page
	 */
	public Page findBatchModify(String modifyNo, String name, Date applyDateStart,Date applyDateEnd, Integer status, int start, int size);
	/**
	 * 删除批量变更草稿并级联删除变更计划、新建和变更的配置项、关系
	 * @Methods Name removeBatchModifyDraft
	 * @Create In Mar 6, 2010 By duxh
	 * @param modifyIdArray 
	 * @Return void
	 */
	public void removeBatchModifyDraft(Long[] modifyIdArray);

	/**
	 * 查找当前登陆用户所在交付团队的技术负责人
	 * @Methods Name findDeliveryTeamTechnicalLeader
	 * @Create In Jul 7, 2010 By duxh
	 * @return Set<String>用户名
	 */
	public Set<String> findDeliveryTeamTechnicalLeader();
	/**
	 * 查找持久化对象
	 * @Methods Name selectObjects
	 * @Create In Mar 6, 2010 By duxh
	 * @param entity  实体Class对象
	 * @param propertyName 属性名
	 * @param propertyValues 属性值
	 * @param fetchProperty 需要抓取的对象（可选，不需要是传null）
	 * @Return List<Object> 有返回，无返回空的集合
	 */
	public <T> List<T> findObjects(Class<T> entity,String propertyName,Object[] propertyValues,String fetchProperty);
	/**
	 * 查询新建的关系中涉及的配置项为新建并且未变更成功的编号
	 * @Methods Name findNewCIModifyUnsuccess
	 * @Create In Mar 31, 2010 By duxh
	 * @param plans 新建关系相关的变更计划
	 * @Return List<String> 新建的关系中涉及的配置项为新建并且未变更成功的编号
	 */
	public List<String> findNewCIModifyUnsuccess(List<CIBatchModifyPlan> plans);
	/**
	 * 变更删除的关系涉及的配置项哪些在本次变更中变为孤立状态并且已经变更成功
	 * @Methods Name findOrphanCIModifySuccess
	 * @Create In Apr 23, 2010 By duxh
	 * @param plans 变更删除的关系的变更计划
	 * @Return List<ConfigItem> 孤立并且变更成功的配置项
	 */
	public List<ConfigItem> findOrphanCIModifySuccess(List<CIBatchModifyPlan> plans);
	/**
	 * 批量保存实体
	 * @Methods Name saveOrUpdateObjects
	 * @Create In Mar 5, 2010 By duxh
	 * @param entity 实体集合
	 * @Return void
	 */
	public void saveOrUpdateObjects(Collection entity);
	/**
	 * 查找持久化对象
	 * @Methods Name findObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity 需要查询的Class对象
	 * @param propertysNameAndValue 属性名和属性值的键值对（属性值可以为单个值、一个集合或一个数组）
	 * @param fetchPropertys需要抓取的属性集合（可选：不需要传null）
	 * @Return List<T>
	 */
	public <T> List<T> findObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys);
	/**
	 * 查找持久化对象
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity 需要查询的Class对象
	 * @param propertysNameAndValue 属性名和属性值的键值对（属性值可以为单个值、一个集合或一个数组）
	 * @param fetchPropertys需要抓取的属性集合（可选：不需要传null）
	 * @param orderProperty 需要排序的属性
	 * @param isAsc升降序
	 * @Return List<T>
	 */
	public <T> List<T> findObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys,String orderProperty,boolean isAsc);
	/**
	 * 查询关系是否有环存在。
	 * @Methods Name findLoop
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode 父编号
	 * @param childCode 子编号
	 * @param relsId 不忽略的关系id
	 * @param ignoreRid 要忽略的关系id
	 * @Return Set<String> 存在环的编号
	 */
	public Set<String> findLoop(String parentCode,String childCode,List<Long> relsId,List<Long> ignoreRid);
	/**
	 * 查看某次变更申请中是否存在此种关系草稿
	 * @Methods Name selectHasSameDraft
	 * @Create In Apr 1, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param parentCode 父编号
	 * @param childCode  子编号
	 * @param ignoreRid 要忽略的关系id
	 * @Return boolean 是否存在
	 */
	public boolean findHasSameDraft(Long modifyId,String parentCode,String childCode,Long ignoreRid);
	/**
	 * 查询正式关系中是否存在此种关系
	 * @Methods Name findHasSameValidRel
	 * @Create In May 28, 2010 By duxh
	 * @param parentCode 父编号
	 * @param childCode	 子编号
	 * @Return CIRelationShip 有则返回，无则null
	 */
	public CIRelationShip findHasSameValidRel(String parentCode,String childCode);
	/**
	 * 查询正式,审批中关系中是否存在此种关系
	 * @Methods Name findHasSameValidAndProcessingRel
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode 父编号
	 * @param childCode	子编号
	 * @param rids 不忽略的关系
	 * @param ignoreRid 要忽略的关系
	 * @Return boolean 是否存在
	 */
	public boolean findHasSameValidAndProcessingRel(String parentCode,String childCode,List<Long> rids,Long ignoreRid);
	/**
	 * 正在变更处理中的关系变更计划
	 * @Methods Name findProcessingRelPlan
	 * @Create In Apr 2, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @Return List<CIBatchModifyPlan> 变更计划
	 */
	public List<CIBatchModifyPlan> findProcessingRelPlan(Long modifyId);
	
	/**
	 * 哪些配置项正在变更处理中
	 * @Methods Name selectProcessingRel
	 * @Create In Apr 2, 2010 By duxh
	 * @Return List<String>
	 */
	public List<String> findCIProcessing(Long modifyId,List<String> cisn);
	/**
	 * 1.已存在此种关系
	 * 2.其他申请变更成功了本次申请中变更的配置项
	 * 3.配置项被删除
	 * 4.其他申请变更成功了本次申请中变更的关系
	 * 5.其他申请变更删除并且变更成功了本次变更中变更的关系
	 * 如果配置项或关系已存在或已被变更,继续提交视为在已存在或已被变更的配置项或关系的基础之上做变更!
	 * 如果原配置项或关系已被删除,本次申请中状态为变更,继续提交视为新建!
	 * @Methods Name saveOrUpdateOldCIAndOldRel
	 * @Create In Apr 3, 2010 By duxh
	 * @param plans
	 */
	public void saveOrUpdateOldCIAndOldRel(List<CIBatchModifyPlan> plans);
	/**
	 * 查询某次变更申请中，cisn所表示的配置项中新建或变更为"备用、禁用、已归档、租出"的有哪些
	 * @Methods Name findOrphanCI
	 * @Create In Apr 20, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> findOrphanCI(Long modifyId,List<String> cisn);
	/**
	 * 查询处理中的变更申请，cisn所表示的配置项中新建或变更为"备用、禁用、已归档、租出"的有哪些
	 * @Methods Name findProcessingOrphanCI
	 * @Create In Apr 23, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> findProcessingOrphanCI(Long modifyId,Set<String> cisn);
	/**
	 * 查找配置项的正式关系
	 * @Methods Name findValidCIRelationShip
	 * @Create In Apr 21, 2010 By duxh
	 * @param cisn 配置项编号
	 * @Return List<CIRelationShip> 关系
	 */
	public List<CIRelationShip> findValidCIRelationShip(String cisn);
	/**
	 * 查找某配置项的关系
	 * 1.如果配置项已生效，查询已生效的关系
	 * 2.如果配置项已删除，查询删除那个时间点的关系
	 * @Methods Name findCIRelationShip
	 * @Create In Apr 21, 2010 By duxh
	 * @param ci 配置项
	 * @Return List<CIRelationShip> 关系
	 */
	public List<CIRelationShip> findCIRelationShip(ConfigItem ci);
	/**
	 * 根据编号查找配置项或服务项的名称，如果没有有效的，则从当前变更申请中查找
	 * @Methods Name findItemName
	 * @Create In May 5, 2010 By duxh
	 * @param item 标识是配置项还是服务项
	 * @param itemCode 配置项服务项编号
	 * @param modifyId 变更id
	 * @Return String 配置项服务项名称
	 */
	public String findItemName(String item,String itemCode,Long modifyId);
	/**
	 * 分页查询有效的配置项个性化信息
	 * @Methods Name findValidConfigItemExtendInfo
	 * @Create In May 12, 2010 By duxh
	 * @param <T>
	 * @param entity 实体类的全限定名
	 * @param map	查询条件
	 * @param fuzzyQuery 模糊查询字段
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page findValidConfigItemExtendInfo(String entity,Map map,List<String> fuzzyQuery, int pageNo, int pageSize);
	/**
	 * 分页查询工程师
	 * @Methods Name findServiceEngineer
	 * @Create In Jul 7, 2010 By duxh
	 * @param deliveryTeamId 交付团队id
	 * @param map	查询条件
	 * @param fuzzyQuery 模糊查询字段
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findServiceEngineer(Long deliveryTeamId,Map map,List<String> fuzzyQuery, int pageNo, int pageSize);
	/**
	 * 当前变更中删除的配置项关系
	 * @Methods Name findDeleteRel
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @Return List<CIRelationShip> 删除的关系
	 */
	public List<CIRelationShip> findDeleteRel(Long modifyId);
	/**
	 * 删除指定id的持久化对象
	 * @Methods Name removeObjects
	 * @Create In May 25, 2010 By duxh
	 * @param entitieClass 实体的class对象
	 * @param ids id数组
	 * @Return void
	 */
	public void removeObjects(Class entitieClass,Long[] ids);
	/**
	 * 保存配置项关系替换
	 * @Methods Name saveReplaceRel
	 * @Create In May 28, 2010 By duxh
	 * @param source 原关系
	 * @param target 替换之后的关系
	 * @param modifyId 变更申请id
	 * @Return void
	 */
	public void saveReplaceRel(List<CIRelationShip> source,List<CIRelationShip> target,Long modifyId);
	/**
	 * 查询某配置项的关系=有效的关系+本申请新建-本申请删除
	 * @Methods Name findCIRelationShip
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<CIRelationShip> 配置项关系
	 */
	public List<CIRelationShip> findCIRelationShip(Long modifyId,String cisn);
	/**
	 * 查询某次变更申请中针对某配置项的变更
	 * @Methods Name findModifyConfigItem
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param itemCode 配置项编号
	 * @Return ConfigItem 返回该配置项
	 */
	public ConfigItem findModifyConfigItem(Long modifyId, String itemCode);
	/**
	 * 保存维护关系的变更计划并生成必要关系
	 * @Methods Name savePlanAndCreateNecessaryRel
	 * @Create In Jun 2, 2010 By duxh
	 * @param planMap 变更计划信息
	 * @param modifyId 变更申请id
	 * @param ci 维护关系的配置项
	 * @param createAllNecessaryRel 是否生成所有必要关系包括可选的
	 * @Return void
	 */
	public void savePlanAndCreateNecessaryRel(Map planMap,Long modifyId,ConfigItem ci,boolean createAllNecessaryRel);
	/**
	 * 查询是否拥有所有必要关系
	 * @Methods Name findHasAllNecessaryRel
	 * @Create In Jun 3, 2010 By duxh
	 * @param ci 需要查询的配置项
	 * @param modifyId 变更申请id
	 * @Return boolean 是否全部存在
	 */
	public boolean findHasAllNecessaryRel(ConfigItem ci,Long modifyId);
	/**
	 *  维护关系的配置项的变更计划
	 * @Methods Name findMaintenanceConfigItem
	 * @Create In Jun 3, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param ci 配置项
	 * @param result 实施结果
	 * @Return List<CIBatchModifyPlan> 变更计划
	 */
	public List<CIBatchModifyPlan> findMaintenanceConfigItem(Long modifyId, ConfigItem ci,Integer result);
	/**
	 * 查询某次变更中某配置项维护的必要关系
	 * @Methods Name findMaintenanceRelPlan
	 * @Create In Jun 4, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param maintenanceCIs 维护关系的配置项
	 * @Return List<CIRelationShip> 维护的关系
	 */
	public List<CIRelationShip> findMaintenanceRelPlan(Long modifyId,ConfigItem maintenanceCI);
	/**
	 * 生成必要关系
	 * @Methods Name saveNecessaryRel
	 * @Create In Jun 4, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param ci 生成关系的配置项
	 * @param ciPlan 生成关系的配置项变更计划
	 * @param createAllNecessaryRel 是否生成所有必要关系包括可选的
	 * @Return void
	 */
	public void saveNecessaryRel(Long modifyId,ConfigItem ci,CIBatchModifyPlan ciPlan,boolean createAllNecessaryRel);
	/**
	 * 查询rels所表示的关系有多少在本次变更中删除
	 * @Methods Name findDeleteRel
	 * @Create In Jun 11, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param rels 关系id
	 * @return List<CIRelationShip>
	 */
	public List<CIRelationShip> findDeleteRel(Long modifyId,List<CIRelationShip> rels);
	
    /**
     * 查找缺少必要关系的配置项
     * @Methods Name getConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @return void
     */
	public Page getConfigItemNecessaryRelation(Map paramMap,int pageNo,int pageSize);
	
	
    /**
     * 查找缺少必要关系的配置项
     * @Methods Name getConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @return void
     */	
	public List<CIRelationShip>  saveNecessaryRelation(String[] necessaryIds,String batchModifyId,String[] configItemCode,List<ConfigItem> cis);
	/**
	 * 通过配置项关系查找服务器对应的维护工程师
	 * @Methods Name findServerEngineer
	 * @Create In Jul 30, 2010 By duxh
	 * @param cisn 服务器配置项编号
	 * @return List<Long> 工程师对应的用户的id
	 */
	public List<Long> findServerEngineer(String cisn);
	/**
	 * 通过模块类型和流程类型来确定走的流程要用哪个
	 * @param modleType
	 * @param processStatusType
	 * @return
	 */
	public ModleToProcess findProcessByParm(String modleType,String processStatusType);
}
