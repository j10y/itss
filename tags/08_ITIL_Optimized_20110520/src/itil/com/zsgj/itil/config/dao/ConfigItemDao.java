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
	 * 根据条件分页查询所有配置项
	 * 需要包含的：
	 * 1.本次变更申请中新建的非孤立状态的
	 * 2.本次申请中将孤立的配置项变为非孤立的
	 * 不能包含：
	 * 1.本次申请中将非孤立变为孤立的
	 * 2.孤立的配置项
	 * @Methods Name selectConfigItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param name 配置项名称
	 * @param cisn 配置项编号
	 * @param configItemTypeId 配置项类型id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectConfigItem(String modifyId,String  name,String cisn,Long configItemTypeId, int pageNo, int pageSize);
	/**
	 * 根据条件分页查询所有服务项
	 * @Methods Name selectServiceItem
	 * @Create In Feb 22, 2010 By duxh
	 * @param name 服务项名称
	 * @param code	服务项编号
	 * @param serviceItemTypeId 服务项类型id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectServiceItem(String  name,String code,Long serviceItemTypeId, int pageNo, int pageSize);
	/**
	 * 根据父配置项或服务项编号查询直接子项（一级子）
	 * @Methods Name selectDirectChildRel
	 * @Create In Feb 23, 2010 By duxh
	 * @param itemCode
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectDirectChildRel(String itemCode);
	/**
	 * 递归获取所有子项编号包括本身
	 * @Methods Name selectAllChildCode
	 * @Create In Feb 25, 2010 By duxh
	 * @param itemCode 配置项编号
	 * @param relId 查询时不排除的关系id集合
	 * @param ignoreRid 忽略的关系
	 * @Return Set<String> 子配置项或服务项编号
	 */
	public Set<String> selectAllChildCode(String itemCode,List<Long> relId,List<Long> ignoreRid);
	/**
	 * 递归获取所有父项编号包括本身
	 * @Methods Name selectAllParentCode
	 * @Create In Feb 25, 2010 By duxh
	 * @param itemCode 配置项编号
	 * @param relId 查询时不排除的关系id集合
	 * @param ignoreRid 忽略的关系
	 * @Return Set<String> 父配置项或服务项编号
	 */
	public Set<String> selectAllParentCode(String itemCode,List<Long> relId,List<Long> ignoreRid);
	/**
	 * 分页查询批量变更信息
	 * @Methods Name selectBatchModifyInfo
	 * @Create In Mar 1, 2010 By duxh
	 * @param id 变更来源的id
	 * @param type 变更来源（问题，需求）
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectBatchModifyInfo(Long id,String type,int pageNo, int pageSize);
	/**
	 *  查询所有批量变更的配置项
	 * @Methods Name selectBatchModifyCIList
	 * @Create In Mar 2, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectBatchModifyCIList(Long modifyId, int pageNo, int pageSize) ;
	/**
	 * 查询所有批量变更的关系
	 * @Methods Name selectBatchModifyRelList
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectBatchModifyRelList(Long modifyId, int pageNo, int pageSize) ;

	/**
	 * 查询配置项父子关系
	 * @Methods Name findAllRelationShip
	 * @Create In Mar 4, 2010 By duxh
	 * @param cisn 配置项或服务项编号
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectAllRelationShip(List<String> itemCode);
	/**
	 * 批量保存实体
	 * @Methods Name saveOrUpdateObjects
	 * @Create In Mar 5, 2010 By duxh
	 * @param col 实体集合 
	 * @Return void
	 */
	public void saveOrUpdateObjects(Collection entity);
	/**
	 * 查询批量变更信息
	 * @Methods Name selectBatchModify
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
	public Page selectBatchModify(String modifyNo, String name, Date applyDateStart,Date applyDateEnd, Integer status, int start, int size);
	/**
	 * 根据条件分页查询配置项关系
	 * @Methods Name selectRelList
	 * @Create In Apr 14, 2010 By duxh
	 * @param basicMap 基本查询条件
	 * @param advancedMap 高级查询条件
	 * @Return Page
	 */
	public Page selectRelList(Map<String,String> basicMap, Map<String,String> advancedMap, int pageNo, int pageSize);
	/**
	 * 某配置项的有效关系
	 * @Methods Name selectRelList
	 * @Create In May 28, 2010 By duxh
	 * @param ignoreCode 忽略和这些配置项编号相关的关系
	 * @param itemCode 某配置项编号
	 * @param pageNo
	 * @param pageSize
	 * @Return Page
	 */
	public Page selectRelList(String itemCode,List<String> ignoreCode, int pageNo, int pageSize);
	/**
	 * 某配置项的有效关系
	 * @Methods Name selectRelList
	 * @Create In May 28, 2010 By duxh
	 * @param ignoreCode 忽略和这些配置项编号相关的关系
	 * @param itemCode 相关配置项编号
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectRelList(String itemCode,List<String> ignoreCode);
	/**
	 * 某次变更申请中所有关系变更
	 * @Methods Name selectModifyRel
	 * @Create In May 28, 2010 By duxh
	 * @param modifyId
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectModifyRel(Long modifyId);
	/**
	 * 某次变更申请中和某配置项相关的新增和删除的关系变更计划
	 * @Methods Name selectNewAndDeleteRel
	 * @Create In May 30, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectNewAndDeleteRel(Long modifyId,String cisn);
	/**
	 * 查找持久化对象
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity 需要查询的Class对象
	 * @param propertyName 属性名（可以是多级的属性，如：userInfo.department.name）
	 * @param propertyValues 属性值数组
	 * @param fetchProperty 需要抓取的属性（可选：不需要传null.）
	 * @Return List<T>
	 */
	public <T> List<T> selectObjects(Class<T> entity,String propertyName,Object[] propertyValues,String fetchProperty);
	/**
	 * 查找持久化对象
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity 需要查询的Class对象
	 * @param propertysNameAndValue 属性名和属性值的键值对（属性值可以为单个值、一个集合或一个数组,属性名可以是多级的属性）
	 * @param fetchPropertys需要抓取的属性集合（可选：不需要传null）
	 * @param orderProperty 需要排序的属性
	 * @param isAsc升降序
	 * @Return List<T>
	 */
	public <T> List<T> selectObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys,String orderProperty,boolean isAsc);
	/**
	 * 查找持久化对象
	 * @Methods Name selectObjects
	 * @Create In Mar 31, 2010 By duxh
	 * @param <T>
	 * @param entity 需要查询的Class对象
	 * @param propertysNameAndValue 属性名和属性值的键值对（属性值可以为单个值、一个集合或一个数组，属性名可以是多级的属性）
	 * @param fetchPropertys需要抓取的属性集合（可选：不需要传null）
	 * @Return List<T>
	 */
	public <T> List<T> selectObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys);
	/**
	 * 删除所有持久化对象
	 * @Methods Name deleteObjects
	 * @Create In Mar 6, 2010 By duxh
	 * @param entities 
	 * @Return void
	 */
	public void deleteObjects(Collection entities);
	/**
	 * 通过配置项关系，查找当前登陆工程师所对应的交付团队的技术负责人
	 * @Methods Name selectDeliveryTeamTechnicalLeader
	 * @Create In Jul 7, 2010 By duxh
	 * @return Set<String> 用户名
	 */
	public Set<String> selectDeliveryTeamTechnicalLeader();
	/**
	 * 查询在某次变更中是否存在针对某个配置项的变更
	 * @Methods Name selectHasCIModifyDraft
	 * @Create In Mar 25, 2010 By duxh
	 * @param modifyId 变更id
	 * @param cisn 配置项编号
	 * @param ignoreCid 忽略的配置项
	 * @Return CIBatchModifyPlan 存在返回变更计划
	 */
	public CIBatchModifyPlan selectHasCIModifyDraft(Long modifyId, String cisn,Long ignoreCid);
	/**
	 * 查询在某次变更中是否存在针对某个配置项关系的变更
	 * @Methods Name selectHasCIRelModifyDraft
	 * @Create In Mar 29, 2010 By duxh
	 * @param modifyId 变更id
	 * @param parentCode 关系父编号
	 * @param childCode 关系子编号
	 * @Return CIBatchModifyPlan 存在返回变更计划
	 */
	public CIBatchModifyPlan selectHasCIRelModifyDraft(Long modifyId, String parentCode,String childCode);
	/**
	 * 查找所有和某些配置项相关的关系
	 * @Methods Name selectCIRelationShip
	 * @Create In Mar 25, 2010 By duxh
	 * @param cisn 配置项编号
	 * @Return CIRelationShip
	 */
	public List<CIRelationShip> selectCIRelationShip(List<String> cisn);
	/**
	 * 查找配置项的正式关系
	 * @Methods Name selectValidCIRelationShip
	 * @Create In Apr 21, 2010 By duxh
	 * @param cisn 配置项编号
	 * @Return List<CIRelationShip> 关系
	 */
	public List<CIRelationShip> selectValidCIRelationShip(String cisn);
	/**
	 * 查找配置项在某一时间点的关系
	 * 处理逻辑如下：
	 *(
	 *	当前有效的关系 
	 *	and (
	 * 	关系修改过且关系的修改时间小于这个时间点
	 * 	or
	 * 	关系未修改过且关系的创建时间小于这个时间点
	 *  )
	 * ) or (
	 *  当前已删除的关系
	 *  and 
	 * 	创建时间小于这个时间点
	 *  and
	 * 	修改时间大于这个时间点
	 * )
	 * @Methods Name selectCIRelationShipByDate
	 * @Create In May 13, 2010 By duxh
	 * @param cisn 配置项编号
	 * @param date 时间点
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectCIRelationShipByDate(String cisn,Date date);
	/**
	 * 新建并且未变更成功的配置项编号
	 * @Methods Name selectNewCIModifyUnsuccess
	 * @Create In Mar 31, 2010 By duxh
	 * @param bm 变更申请的id
	 * @param cisn 新建的配置项编号
	 * @Return List<String> 新建并且未变更成功的配置项编号
	 */
	public List<String> selectNewCIModifyUnsuccess(CIBatchModify bm,List<String> cisn);
	/**
	 * 查找变更成功并且状态为"备用、禁用、已归档、租出"的配置项
	 * @Methods Name selectOrphanCIModifySuccess
	 * @Create In Apr 23, 2010 By duxh
	 * @param bm
	 * @param cisn
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> selectOrphanCIModifySuccess(CIBatchModify bm,List<String> cisn);
	/**
	 * 查看某次变更申请中除此之外是否存在此种关系草稿
	 * @Methods Name selectHasSameDraft
	 * @Create In Apr 1, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param parentCode 父编号
	 * @param childCode  子编号
	 * @param ignoreRid 要忽略的关系id
	 * @Return boolean 是否存在
	 */
	public boolean selectHasSameDraft(Long modifyId,String parentCode,String childCode,Long ignoreRid);
	/**
	 * 查询正式,审批中关系中是否存在此种关系
	 * @Methods Name selectHasSameValidAndProcessingRel
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode 父编号
	 * @param childCode  子编号
	 * @param rids 不忽略的关系
	 * @param ignoreRid 要忽略的关系
	 * @Return boolean 是否存在
	 */
	public boolean selectHasSameValidAndProcessingRel(String parentCode,String childCode,List<Long> rids,Long ignoreRid);
	/**
	 * 查询正式关系中是否存在此种关系
	 * @Methods Name selectHasSameValidRel
	 * @Create In Apr 1, 2010 By duxh
	 * @param parentCode 父编号
	 * @param childCode  子编号
	 * @Return CIRelationShip 存在则返回，无null
	 */
	public CIRelationShip selectHasSameValidRel(String parentCode,String childCode);
	/**
	 * 正在变更处理中的关系变更计划
	 * @Methods Name selectProcessingRelPlan
	 * @Create In Apr 2, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectProcessingRelPlan(Long modifyId);
	/**
	 * 哪些配置项正在变更处理中
	 * @Methods Name selectProcessingRel
	 * @Create In Apr 2, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<String>
	 */
	public List<String> selectCIProcessing(Long modifyId,List<String> cisn);
	/**
	 * 查询某次变更申请中，cisn所表示的配置项中新建或变更为"备用、禁用、已归档、租出"的有哪些
	 * @Methods Name selectOrphanCI
	 * @Create In Apr 20, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> selectOrphanCI(Long modifyId,List<String> cisn);
	/**
	 * 查询处理中的变更申请，cisn所表示的配置项中新建或变更的状态为"备用、禁用、已归档、租出"的有哪些
	 * @Methods Name selectProcessingOrphanCI
	 * @Create In Apr 23, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param cisn 配置项编号
	 * @Return List<ConfigItem>
	 */
	public List<ConfigItem> selectProcessingOrphanCI(Long modifyId,Set<String> cisn) ;
	/**
	 * 在某次申请中删除配置项cisn关系的变更计划
	 * @Methods Name selectRelPlan
	 * @Create In Apr 22, 2010 By duxh
	 * @param modifyId 申请id
	 * @param cisn 配置项编号
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectDeleteRelPlan(Long modifyId,String cisn);
	/**
	 * 当前变更中删除的配置项关系
	 * @Methods Name selectDeleteRel
	 * @Create In May 31, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @Return List<CIRelationShip>
	 */
	public List<CIRelationShip> selectDeleteRel(Long modifyId);
	/**
	 *	查询某次变更申请中编号为itemCode的配置项
	 * @Methods Name selectModifyConfigItem
	 * @Create In May 5, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param itemCode 配置项编号
	 * @Return ConfigItem
	 */
	public ConfigItem selectModifyConfigItem(Long modifyId,String itemCode);
	/**
	 * 分页查询有效的配置项个性化信息
	 * @Methods Name selectValidConfigItemExtendInfo
	 * @Create In Jun 9, 2010 By duxh
	 * @param entity 实体类的全限定名
	 * @param map	查询条件
	 * @param fuzzyQuery 模糊查询字段
	 * @param pageNo
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	public Page selectValidConfigItemExtendInfo(String entity,Map map, List<String> fuzzyQuery,int pageNo, int pageSize);
	/**
	 * 通过配置项关系，分页查询工程师
	 * @Methods Name selectServiceEngineer
	 * @Create In Jul 7, 2010 By duxh
	 * @param deliveryTeamId 交付团队id
	 * @param map	查询条件
	 * @param fuzzyQuery 模糊查询字段
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page selectServiceEngineer(Long deliveryTeamId,Map map, List<String> fuzzyQuery,int pageNo, int pageSize);
	/**
	 * 查询某次变更中某配置项维护的必要关系的变更计划
	 * @Methods Name selectMaintenanceRelPlan
	 * @Create In Jun 3, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param maintenanceCIs 维护关系的配置项
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectMaintenanceRelPlan(Long modifyId,List<ConfigItem> maintenanceCIs);
	/**
	 * 维护关系的配置项的变更计划
	 * @Methods Name selectMaintenanceConfigItem
	 * @Create In Jun 3, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param ci 维护关系的配置项
	 * @param result 实施结果
	 * @Return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectMaintenanceConfigItem(Long modifyId, ConfigItem ci,Integer result);
	/**
	 * 查询rels所表示的关系有多少在本次变更中删除
	 * @Methods Name selectDeleteRel
	 * @Create In Jun 11, 2010 By duxh
	 * @param modifyId 变更申请id
	 * @param rels 关系
	 * @return List<CIRelationShip> 在本次变更中删除的关系
	 */
	public List<CIRelationShip> selectDeleteRel(Long modifyId,List<CIRelationShip> rels);
	/**
	 * 某配置项变更为孤立状态自动删除的关系变更计划
	 * @Methods Name selectMaintenanceDeleteRelPlan
	 * @Create In Jun 12, 2010 By duxh
	 * @param modifyId  变更申请id
	 * @param maintenance 维护自动删除关系的配置项
	 * @return List<CIBatchModifyPlan>
	 */
	public List<CIBatchModifyPlan> selectMaintenanceDeleteRelPlan(Long modifyId,ConfigItem maintenance);

    /**
     * 查找缺少必要关系的配置项
     * @Methods Name selectConfigItemNecessaryRelation
     * @Create In 22 7, 2010 By zhangzy
     * @param paramMap 查询条件
     * @return void
     */
	public Page selectConfigItemNecessaryRelation(Map paramMap, int pageNo, int pageSize);
	/**
	 * 通过配置项关系查找服务器对应的维护工程师
	 * @Methods Name selectServerEngineer
	 * @Create In Jul 30, 2010 By duxh
	 * @param cisn 服务器配置项编号
	 * @return List<Long> 工程师对应的用户的id
	 */
	public List<Long> selectServerEngineer(String cisn);
}
