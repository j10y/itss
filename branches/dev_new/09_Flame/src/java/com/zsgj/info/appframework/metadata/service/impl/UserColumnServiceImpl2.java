package com.zsgj.info.appframework.metadata.service.impl;
//package com.digitalchina.info.appframework.metadata.service.impl;
//
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.hibernate.Criteria;
//import org.hibernate.Query;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemTableQuery;
//import com.digitalchina.info.appframework.metadata.entity.SystemTableQueryColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
//import com.digitalchina.info.appframework.metadata.entity.UserTableQueryColumn;
//import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
//import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
//import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
//import com.digitalchina.info.appframework.metadata.service.UserColumnService;
//import com.digitalchina.info.framework.context.UserContext;
//import com.digitalchina.info.framework.dao.BaseDao;
//
//public class UserColumnServiceImpl2 extends BaseDao implements UserColumnService {
//
//	private SystemMainTableService systemMainTableService;
//	private SystemMainColumnService systemMainColumnService;
//	private SystemExtColumnServcie systemExtColumnServcie;
//	
//	@SuppressWarnings("unchecked")
//	public List<SystemTableSetting> findSystemColumns(
//			SystemMainTable mainTable, Integer settingType) {
//		
//		Criteria c = super.createCriteria(SystemTableSetting.class);
//		c.add(Restrictions.eq("systemMainTable", mainTable));
//		c.add(Restrictions.eq("settingType", settingType));
//		c.addOrder(Order.asc("order"));
//		List list = c.list();
//		if(list.isEmpty()){ //如果没有，读取主表和扩展表字段，初始化
//			List<SystemMainTableColumn> mainColumns = systemMainColumnService.findSystemMainTableColumns(mainTable);
//			int i=1;
//			for(SystemMainTableColumn smtc : mainColumns){
//				SystemTableSetting sts = new SystemTableSetting();
//				sts.setSystemMainTable(mainTable);
//				sts.setMainTableColumn(smtc);
//				sts.setSettingType(settingType);
//				sts.setIsDisplay(Integer.valueOf(1));
//				sts.setExtendTableColumn(null);
//				sts.setOrder(Integer.valueOf(i++));
//				super.save(sts);
//			}
//			List<SystemMainTableExtColumn> extColumns = systemExtColumnServcie.findSystemExtendColumns(mainTable);
//			int j=1;
//			for(SystemMainTableExtColumn smtc : extColumns){
//				SystemTableSetting sts = new SystemTableSetting();
//				sts.setSystemMainTable(mainTable);
//				sts.setExtendTableColumn(smtc);
//				sts.setSettingType(settingType);
//				sts.setIsDisplay(Integer.valueOf(1));
//				sts.setMainTableColumn(null);
//				sts.setOrder(Integer.valueOf(j++));
//				super.save(sts);
//			}
//			//以后修改字段后级联修改此表
//			
//		}
//		return list;
//	}
//
//	public List<SystemTableQuery> findSystemTableQueryAll(SystemMainTable smt) {
//		String hql = "from SystemTableQuery stq where stq.systemMainTable=? ";
//		Query query = super.createQuery(hql, new Object[]{smt});
//		List list = query.list();
//		return list;
//	}
//
//	public SystemTableQuery findSystemTableQuery(SystemMainTable smt) {
//		String hql = "from SystemTableQuery stq where stq.systemMainTable=? and stq.queryType=? ";
//		Query query = super.createQuery(hql, new Object[]{smt, SystemTableQuery.QUERY_TYPE_SINGLE});
//		SystemTableQuery result = (SystemTableQuery) query.uniqueResult();
//		return result;
//	}
//
//	/**
//	 * 通过系统表查询获取其所有查询字段，此处只是单表查询
//	 */
//	@SuppressWarnings("unchecked")
//	public List<SystemTableQueryColumn> findSystemTableQueryColumn(
//			SystemTableQuery utq) {
//		String hql = "from SystemTableQueryColumn stqc where stqc.systemTableQuery=? order by stqc.systemMainTable asc, stqc.order ";
//		Query query = super.createQuery(hql, new Object[]{utq});
//		List list = query.list();
//		return list;
//		
//	}
//
//	private SystemTableQueryColumn findSystemTableQueryColumn(SystemTableQuery stq, Integer orderFlag){
//		Criteria c = this.getCriteria(SystemTableQueryColumn.class);
//		c.add(Restrictions.eq("systemTableQuery", stq));
//		c.add(Restrictions.eq("order", orderFlag));
//		SystemTableQueryColumn stqc = (SystemTableQueryColumn) c.uniqueResult();
//		return stqc;
//		
//	}
//	public void saveSystemTableQueryColumnSort(SystemTableQuery stq, String targetOrderFlag, String[] sourceOrderFlags) {
//		for(int i=0; i<sourceOrderFlags.length; i++){
//			if(StringUtils.isNotBlank(sourceOrderFlags[i])){
////				源排序flag
//				Integer currentSourceOrderFlag = Integer.valueOf(sourceOrderFlags[i]);
//				//源查询字段
//				SystemTableQueryColumn stqc = findSystemTableQueryColumn(stq, currentSourceOrderFlag);
//				//目标flag
//				Integer targetOrderFlagLong = Integer.valueOf(targetOrderFlag); 
//				//目标查询字段
//				SystemTableQueryColumn stqcTarget = findSystemTableQueryColumn(stq, targetOrderFlagLong);
//				
//
//					if(targetOrderFlagLong> currentSourceOrderFlag){ //目标排序在源排序的下面
//						StringBuffer bf= new StringBuffer();
//						bf.append(" update SystemTableQueryColumn stqc set stqc.order=stqc.order-1");
//						bf.append(" where stqc.systemTableQuery=? ");
//						bf.append(" and stqc.order> ? and stqc.order<=? ");
//						this.executeUpdate(bf.toString(), new Object[]{stq, currentSourceOrderFlag, targetOrderFlagLong});
//						
////						目标查询字段
//						//SystemTableQueryColumn stqcTarget = findSystemTableQueryColumn(stq, targetOrderFlagLong);
//						//目标查询字段的order
//						Integer targetOrder = stqcTarget.getOrder();
//						//源查询字段排序标记修改
//						stqc.setOrder(targetOrderFlagLong);
//						this.save(stqc);
//						this.evict(stqc);
//
//					}else{
//						StringBuffer bf= new StringBuffer();
//						bf.append(" update SystemTableQueryColumn stqc set stqc.order=stqc.order+1");
//						bf.append(" where stqc.systemTableQuery=? ");
//						bf.append(" and stqc.order>? and stqc.order<? ");
//						
//						this.executeUpdate(bf.toString(), new Object[]{stq, targetOrderFlagLong, currentSourceOrderFlag});
//						
////						目标查询字段
//						//SystemTableQueryColumn stqcTarget = findSystemTableQueryColumn(stq, targetOrderFlagLong);
////						目标查询字段的order
//						Integer targetOrder = stqcTarget.getOrder();
//						//源查询字段排序标记修改
//						stqc.setOrder(targetOrderFlagLong+1);
//						this.save(stqc);
//						this.evict(stqc);
//					}
//	
//				
//			}
//
//		}
//		
////		for(long j=stqcId+1; j<=targetId; j++ ){
////		
////	}
////		String hql = "from SystemTableQueryColumn stqc where stqc.systemTableQuery=? order by stqc.systemMainTable asc, stqc.order ";
////		Query query = super.createQuery(hql, new Object[]{stq});
////		List list = query.list();
////		Iterator iter = list.iterator();
////		while(iter.hasNext()){
////			SystemTableQueryColumn stqc = (SystemTableQueryColumn) iter.next();
////			
////		}
//		
//	}
//
//	public List<SystemTableQueryColumn> findSystemTableQueryColumn(SystemMainTable smt) {
//		//删除此主表的所有系统查询字段？？？？？？？？？？？
//		//super.executeUpdate("delete from SystemTableQueryColumn stqc where stqc.systemMainTable=?", smt);
//		List<SystemMainTableColumn> mainColumns = systemMainColumnService.findSystemMainTableColumns(smt);
//		/*int i=1;
//		for(SystemMainTableColumn fmtc: mainColumns){
//			if(fmtc.getIsSearchItem().intValue()==1){ //字段是查询项才初始化到系统查询字段
//				SystemTableQueryColumn stqc = new SystemTableQueryColumn();
//				stqc.setSystemMainTable(fmtc.getSystemMainTable());
//				stqc.setIsDisplay(1);
//				stqc.setOrder(Integer.valueOf(i++));
//				stqc.setMainTableColumn(fmtc);
//				stqc.setExtendTableColumn(null);
//				//考虑将代码移到一处
//				if(fmtc.getPropertyType()==null){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//				}else if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("String")){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//				}else if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Integer")){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}else if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Long")){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}else if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("BaseObject")){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}else if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Double")){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}else if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Date")){
//					stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}
//				super.save(stqc);
//			}
//		}*/
//		return null;
//	}
//
//	/**
//	 * 获取用户可见字段
//	 */
//	@SuppressWarnings("unchecked")
//	public List<UserTableSetting> findUserColumns(SystemMainTable sysmt,
//			Integer settingType) {
//		
//		Criteria c = createCriteria(UserTableSetting.class);
//		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
//		c.add(Restrictions.eq("systemMainTable", sysmt));
//		c.add(Restrictions.eq("settingType", settingType));
//		c.addOrder(Order.asc("order"));
//		if(settingType==UserTableSetting.LIST){
//			c.add(Restrictions.eq("isDisplay", new Integer(1)));
//		}
//		List list = c.list(); 
//		
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<UserTableQueryColumn> findUserQueryColumn(SystemTableQuery stq) {
//		Criteria c = createCriteria(UserTableQueryColumn.class);
//		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
//		c.add(Restrictions.eq("systemTableQuery", stq));
//		c.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
//		c.addOrder(Order.asc("order"));
//		List list = c.list(); 
//		return list;
//	}
//
//	public void setSystemMainColumnService(
//			SystemMainColumnService systemMainColumnService) {
//		this.systemMainColumnService = systemMainColumnService;
//	}
//
//	public void setSystemMainTableService(
//			SystemMainTableService systemMainTableService) {
//		this.systemMainTableService = systemMainTableService;
//	}
//
//}
