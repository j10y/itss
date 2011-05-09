package com.zsgj.info.appframework.metadata.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.ExtData;
import com.zsgj.info.appframework.metadata.entity.ExtOptionData;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.util.BeanUtil;

public class SystemColumnServiceImpl extends BaseDao implements SystemColumnService {
	
	private SystemMainTableService systemMainTableService;
	private SystemMainColumnService systemMainColumnService;
	//private SystemExtColumnServcie systemExtColumnService;	
	private SystemMainAndExtColumnService systemMainAndExtColumnService;
	
	public void setSystemMainAndExtColumnService(
			SystemMainAndExtColumnService systemMainAndExtColumnService) {
		this.systemMainAndExtColumnService = systemMainAndExtColumnService;
	}

	public void saveSystemFileColumnInit(Column column) {
		SystemMainTableColumn smtc = (SystemMainTableColumn) column;
		smtc.setUploadUrl("/upload/serviceItem");
		smtc.setFileNamePrefix("scid_");
		
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("className", "com.zsgj.info.appframework.metadata.entity.SystemFile"));
		SystemMainTable smtFile = (SystemMainTable) c.uniqueResult();
		
		smtc.setForeignTable(smtFile);
		smtc.setForeignTableKeyColumn(smtFile.getPrimaryKeyColumn());
		
		Criteria cName = super.getCriteria(SystemMainTableColumn.class);
		cName.add(Restrictions.eq("systemMainTable", smtFile));
		cName.add(Restrictions.eq("propertyName", "fileName"));
		SystemMainTableColumn smtcName = (SystemMainTableColumn) cName.uniqueResult();
		
		smtc.setForeignTableValueColumn(smtcName);
		
		cName = super.getCriteria(SystemMainTableColumn.class);
		cName.add(Restrictions.eq("systemMainTable", smtFile));
		cName.add(Restrictions.eq("propertyName", "systemFileName"));
		SystemMainTableColumn smtcSysFile = (SystemMainTableColumn) cName.uniqueResult();
		
		smtc.setFileNameColumn(smtcName);
		smtc.setSystemFileNameColumn(smtcSysFile);
		super.save(smtc);
		
		
	}

	public String findClassNameByDisc(String discValue, String fdiscTable) {
		String hql = "from SystemMainTable smt where smt.tableName = ?";
		List list = super.find(hql, fdiscTable);
		SystemMainTable smt = null;
		if(!list.isEmpty()){
			smt = (SystemMainTable) list.iterator().next();
		}
		String className = smt.getClassName();
		Class clazz = this.getClass(className);
		Criteria c = super.getCriteria(clazz);
		c.add(Restrictions.eq("discValue", discValue));
		c.setProjection(Projections.property("systemMainTable"));
		SystemMainTable result = (SystemMainTable) c.uniqueResult();
		if(result==null) return null;
		return result.getClassName();
	}

	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}
	
	public Column findSystemTableColumnById(String smtcId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findSystemTableColumns(SystemMainTable smt) {
		/**
		List mainColumns = systemMainColumnService.findSystemMainTableColumns(smt);
		List extColumns = systemExtColumnService.findSystemExtendColumns(smt);
		if(!extColumns.isEmpty()){
			mainColumns.addAll(extColumns);
		}
		*/
		//查处主表字段和扩展字段
		List columns=systemMainAndExtColumnService.findAllColumnBySysMainTable(smt);
		return columns;
	}

	public List findSystemTableExportColumns(SystemMainTable smt) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeSystemTableColumn(String smtcId) {
		// TODO Auto-generated method stub

	}

	public Column saveSystemTableColumn(Column column) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSystemMainColumnService(
			SystemMainColumnService systemMainColumnService) {
		this.systemMainColumnService = systemMainColumnService;
	}

//	public void setSystemExtColumnService(
//			SystemExtColumnServcie systemExtColumnService) {
//		this.systemExtColumnService = systemExtColumnService;
//	}
   
//	public void removeDateByObject(Object object,Integer num){
//		boolean flag=false;
//		BeanWrapper bw = new BeanWrapperImpl(object);
//		for(int j=1;j<num;j++){
//			Object object1=bw.getPropertyValue("extColumn"+j);
//			if(object1!=null){
//				flag=true;
//				break;
//			}	
//		}
//		if(flag==false){
//			for(int j=num+1;j<=100;j++){
//				Object object1=bw.getPropertyValue("extColumn"+j);
//				if(object1!=null){
//					flag=true;
//					break;
//				}
//			}
//		}
//		if(flag==false){
//			this.remove(object);
//		}else{
//			bw.setPropertyValue("extColumn"+num, null);
//			this.save(object);
//		}
//	}
	
	public void removeMainAndExtData(Class clazz, String objectId){
		SystemMainTable smt =  systemMainTableService.findSystemMainTableByClazz(clazz);
//		Criteria c = createCriteria(UserTableSetting.class);
//		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
//		c.add(Restrictions.eq("systemMainTable", smt));
//		c.add(Restrictions.isNotNull("extendTableColumn"));
//		c.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
//		c.addOrder(Order.asc("order"));
//		List listExt = c.list();
//		for(int i=0; i<listExt.size(); i++){
//			UserTableSetting uts = (UserTableSetting) listExt.get(i);
//			Column column = uts.getColumn();
//			SystemMainTableColumnType mtype = column.getSystemMainTableColumnType();
//			Integer extColumnId=Integer.parseInt(uts.getExtendTableColumn().getId().toString());
//			//Integer num=uts.getExtendTableColumn().getExtendTableColumnNum();
//			Criteria c1 = createCriteria(ExtData.class);
//			c1.add(Restrictions.eq("mainTableRowID", Long.parseLong(objectId)));
//			c1.add(Restrictions.eq("extendTableId", extColumnId));
//			Object ExtData=(ExtData) c1.uniqueResult();
//			this.remove(ExtData);
//			
//		}
		Criteria c = createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("isExtColumn", SystemMainTableColumn.isExt));
		List <SystemMainTableColumn> list=c.list();
		for(SystemMainTableColumn smtc:list){
			Criteria c1 = createCriteria(ExtData.class);
			c1.add(Restrictions.eq("mainTableRowID", Long.parseLong(objectId)));
			c1.add(Restrictions.eq("extendTableId", Integer.parseInt(smtc.getId().toString())));
		}
		this.removeObject(clazz, Long.parseLong(objectId));
	}

	/**
	 * @Param SystemMainTableService systemMainTableService to set
	 */
	public void setSystemMainTableService(
			SystemMainTableService systemMainTableService) {
		this.systemMainTableService = systemMainTableService;
	}

	
}
