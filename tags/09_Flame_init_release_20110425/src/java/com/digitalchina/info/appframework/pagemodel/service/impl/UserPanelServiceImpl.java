package com.digitalchina.info.appframework.pagemodel.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelRelation;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.appframework.pagemodel.service.UserPanelService;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Module;

public class UserPanelServiceImpl extends BaseDao implements UserPanelService {
	
	public PagePanel findPagePanelByTable(SystemMainTable smt, Integer settingType) {
		PagePanel result = null;
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", settingType));
		List<PagePanel> list = c.list();
		if(!list.isEmpty()){
			result = list.iterator().next();
		}
		return result;
	}

	public PagePanel findPagePanel(String keyName) {
		PagePanel panel = null;
		Criteria c = super.getCriteria(PagePanel.class);
		c.setFetchMode("pagePanelColumns", FetchMode.JOIN);
		c.add(Restrictions.eq("name", keyName));
		List list = c.list(); //防止有重复记录
		if(!list.isEmpty()){
			panel = (PagePanel) list.iterator().next();
		}
		return panel;
	}

	public List<SystemMainTable> findTableByModule(Module module) {
		String hql = "select smt from SystemMainTable smt where smt.module=?";
		List list = super.find(hql, module);
		return list;
	}

	public Page findPagePanel(Map params, int pageNo, int pageSize) {
		Page page = null;
		Module module = (Module) params.get("module");
		String pageName = (String) params.get("pageName");
		String settingType = (String) params.get("settingType");
		
//		Criteria c = super.getCriteria(ConfigItemType.class);
//		c.setProjection(Projections.property("pagePanel"));
//		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		List<PagePanel> citypePanels = c.list();
		
		String hql = "select distinct cit.pagePanel.id from ConfigItemType cit";
		List<Long> citPanelIds = super.find(hql, null);
				
		Criteria critera = super.createCriteria(PagePanel.class);
		critera.add(Restrictions.in("id", citPanelIds));
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
		if(StringUtils.isNotBlank(pageName)){
			critera.add(Restrictions.ilike("titile", pageName));
		}
		
		if(StringUtils.isNotBlank(settingType)){
			critera.add(Restrictions.eq("settingType", Integer.valueOf(settingType)));
		}
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;
	}

	public Page findPagePanel(Module module, String title, int pageNo, int pageSize) {
		Page page = null;
		try {
			Criteria c = super.getCriteria(PagePanel.class);
			if(module!=null){
				c.add(Restrictions.eq("module", module));
			}
			if(title!=null){
				c.add(Restrictions.eq("title", title));
			}
			c.addOrder(Order.asc("order"));
			page = super.pagedQuery(c, pageNo, pageSize);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("查询PagePanel时发生异常");
		}		
		return page;
	}

	public List findPagePanelAndColumnByPagePanel(PagePanel parentPagePanel) {
		// TODO Auto-generated method stub
		return null;
	}

	public PagePanel findPagePanelById(String pagePanelId) {
		PagePanel result = null;
		try {
			result = super.get(PagePanel.class, Long.valueOf(pagePanelId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("获取PagePanel发生异常");
		}		
		return result;
	}

	private List<PageModelPanel> getChildPanels(PageModelPanel pmp){
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("pageModel", pmp.getPageModel()));
		c.add(Restrictions.eq("parentPagePanel", pmp.getPagePanel()));
		List<PageModelPanel> list = c.list();
		return list;
	}
	
	public List<PagePanel> findPagePanelByPageModel(PageModel pageModel) {
		List<PagePanel> list = null;
		try {
			Criteria c = super.getCriteria(PagePanel.class);
			c.add(Restrictions.eq("pageModel", pageModel));
			c.addOrder(Order.asc("order"));
			list = c.list();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("通过pageModel获取PagePanel发生异常");
		}		
		return list;
	}

	public PagePanel savePagePanel(PagePanel pagePanel, List childPanelIds, String smtId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){

		if(parentId!=null&& parentId.intValue()!=0){
			PagePanel parent = this.get(PagePanel.class, parentId);
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update PagePanel m set m.order=m.order+1 where m.parentMenu = ?");
			List paramsList = new ArrayList();
			paramsList.add(parent);
			
			if(maxIndex != -1){
				hql.append(" and m.order < ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.order >= ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}else{
			StringBuffer hql = new StringBuffer("update PagePanel m set m.order=m.order+1 where m.parentMenu is null ");
			List paramsList = new ArrayList();
			//paramsList.add(parent);
			
			if(maxIndex != -1){
				hql.append(" and m.order < ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.order >= ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}
		
	}
	
	public void downNode(Long parentPanelId, Integer minIndex, Integer maxIndex){
		//如果上级Panel不等于null，即至少为2级panel
		if(parentPanelId!=null&& parentPanelId.intValue()!=0){
			//begin 获取上级Panel
			PagePanel parent = null;
			Criteria c = super.getCriteria(PagePanel.class);
			c.add(Restrictions.eq("id", parentPanelId));
			parent = (PagePanel) c.uniqueResult();
			//end
			//指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update PagePanel m set m.order=m.order-1 where m.parentPagePanel=?");
			List paramsList = new ArrayList();
			paramsList.add(parent);
			
			if(maxIndex != -1){
				hql.append(" and m.order <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.order > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}else{//移动的是PageModel下面的顶级Panel
			
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update PagePanel m set m.order=m.order-1 where m.parentPagePanel is null");
			List paramsList = new ArrayList();
			
			if(maxIndex != -1){
				hql.append(" and m.order <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.order > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}
		
		
	}
	

	public void savePagePanelMove(String panelId, String oldPid, String newPid, String nodeIndx) {
		Long menuId = Long.valueOf(panelId);
		Long oldParentId = Long.valueOf(oldPid);
		Long newParentId = Long.valueOf(newPid);
		int nodeIndex = Integer.parseInt(nodeIndx);
		PagePanel obj = this.get(PagePanel.class, menuId);
		PagePanel newParent = super.get(PagePanel.class, newParentId);
		int minIndex = 1/*obj.getMenuOrder().intValue()*/;
		int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
		if(oldParentId.intValue() == newParentId.intValue() && minIndex != maxIndex){
			// 在同一个父节点下发生移动
			if(minIndex < maxIndex){
				// 当要移动的节点的序号小于要移动到的目标序号，则下移
				this.downNode(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// 当要移动的节点的序号大于要移动到的目标序号，则上移
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNode(oldParentId, minIndex, maxIndex);
			}
			// 节点本身的序号设置成要移动到的目标序号
			/*obj.setMenuOrder(nodeIndex);*/
			this.savePagePanel(obj);
		}
		if(oldParentId.intValue() != newParentId.intValue()){
			// 在不同父节点下发生移动
			//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
			this.downNode(oldParentId, minIndex, -1);
			//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
			this.upNode(newParentId, maxIndex, -1);
			// 节点本身的序号设置成要移动到的目标序号
			/*obj.setMenuOrder(nodeIndex);
			obj.setParentMenu(newParent);
			this.saveMenu(obj);*/
		}
		
		
	}

	public void removePagePanel(String[] pagePanelIds) {
		if(pagePanelIds==null||pagePanelIds.length==0){
			throw new ServiceException("删除PagePanel发生异常");
		}
		for(String pagePanelId: pagePanelIds){
			this.removePagePanel(pagePanelId);
		}
		
	}
	
	public void removePagePanel(String pagePanelId) {
		PagePanel pagePanel = super.get(PagePanel.class, Long.valueOf(pagePanelId));
		String hql = "select count(*) from ConfigItemType cit where cit.pagePanel=?";
		Long cicount = (Long) super.find(hql, pagePanel).iterator().next();
		if(cicount!=null && cicount.intValue()>0){
			throw new  ServiceException("当前面板被配置项类型引用，不可以删除");
		}
		
		super.executeUpdate("delete from PagePanelColumn ppc where ppc.pagePanel.id=?", 
					Long.valueOf(pagePanelId));
		super.executeUpdate("delete from PagePanelBtn ppb where ppb.pagePanel.id=?",
					Long.valueOf(pagePanelId));
		super.removeObject(PagePanel.class, Long.valueOf(pagePanelId));
		
	}

	public PagePanel savePagePanel(PagePanel panel) {
		PagePanel result = null;
		try {
			result = (PagePanel) super.save(panel);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanel发生异常");
		}		
		return result;
	}

	public List<Column> findColumns(SystemMainTable smt) {
		List<Column> columns = new ArrayList<Column>();
		Criteria c = super.getCriteria(SystemTableSetting.class);
	//	c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
		c.add(Restrictions.eq("isDisplay", 1));
		List list = c.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			SystemTableSetting uts = (SystemTableSetting) iter.next();
			Column column = uts.getColumn();
			columns.add(column);
		}
		return columns;
	}

	public List<Column> findColumns(SystemMainTable smt, int settingType) {
		List<Column> columns = new ArrayList<Column>();
		Criteria c = super.getCriteria(SystemTableSetting.class);
		//c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", new Integer(settingType)));
		c.add(Restrictions.eq("isDisplay", 1));
		c.setFetchMode("mainTableColumn", FetchMode.JOIN);
		List list = c.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			SystemTableSetting uts = (SystemTableSetting) iter.next();
			Column column = uts.getColumn();
			columns.add(column);
		}
		return columns;
	}
	/*
	 * 根据制定的Module来查找相应的pagePanel
	 * */
	public List findPagePanelByModule(Module module) {
		List list = null;
		try {
			Criteria c = super.getCriteria(PagePanel.class);
			c.add(Restrictions.eq("module", module));
			c.addOrder(Order.asc("title"));
			list = c.list();	
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("查询PagePanel时发生异常");
		}		
		return list;
	}

	public List<PagePanelColumn> saveColumnToPanelColumn(PagePanel panel, List<Column> columns) {
		List<PagePanelColumn> pagePanelColumns=new ArrayList<PagePanelColumn>();
		int i=1;
		for(Column column: columns){
			PagePanelColumn ppc = new PagePanelColumn();
			ppc.setPagePanel(panel);
			ppc.setSystemMainTable(column.getSystemMainTable());
			ppc.setIsDisplay(Integer.valueOf(1));
			ppc.setOrder(Integer.valueOf(i++));
			ppc.setIsMustInput(Integer.valueOf(1));
			//if(column instanceof SystemMainTableColumn){
				ppc.setMainTableColumn((SystemMainTableColumn)column);
//			}else if(column instanceof SystemMainTableExtColumn){
//				ppc.setExtendTableColumn((SystemMainTableExtColumn)column);
//			}
			pagePanelColumns.add(ppc);
		}
		return pagePanelColumns;
	}

	public List<PagePanelTable> findPagePanelTable(PagePanel panel) {
		String hql = "select ppt from PagePanelTable ppt where ppt.pagePanel=?";
		List list = super.find(hql, panel);
		return list;
	}

	public List<SystemMainTable> findMainTableByPanel(PagePanel panel) {
		String hql = "select ppt.systemMainTable from PagePanelTable ppt where ppt.pagePanel=?";
		List list = super.find(hql, panel);
		return list;
	}

	public List findMainTableRelationByPanel(PagePanel panel) {
		String hql = "select ppt from PagePanelTableRelation ppt where ppt.pagePanel=?";
		List list = super.find(hql, panel);
		return list;
	}

	public List<PagePanelColumn> findPagePanelColumnNoParent(PagePanel pp) {
		String hql="select ppc from PagePanelColumn ppc where ppc.parentPagePanelColumn is null and ppc.pagePanel=?  order by ppc.order";
		List<PagePanelColumn> pagePanelColumns = super.find(hql, pp);
		List<PagePanelColumn> returnList = new ArrayList<PagePanelColumn>();
		
		for(PagePanelColumn ppc:pagePanelColumns){
			PagePanelColumn parentPagePanelColumn = new PagePanelColumn();
			parentPagePanelColumn.setId(new Long(0));
			ppc.setParentPagePanelColumn(parentPagePanelColumn);
			returnList.add(ppc);
		}
		return returnList;
	}

	public List<PagePanelColumn> findChildenColumnByParentId(String parentId) {
		PagePanelColumn ppc=super.get(PagePanelColumn.class,Long.valueOf(parentId));
		String hql="select ppc from PagePanelColumn ppc where ppc.parentPagePanelColumn =? order by ppc.order";
		List<PagePanelColumn> itemList = super.find(hql, ppc);
		return itemList;
	}
	public List<SystemMainTable> findTableByPanel(PagePanel panel) {
		List list = null;
		String hql = "select distinct ppt.systemMainTable from PagePanelTable ppt where ppt.pagePanel=?";
		list = super.find(hql, panel);
		return list;
	}

	public void savePanelColumnsFormSysMainTable(String ppId, String smtId) {
		//System.out.println(ppId+smtId);
		PagePanel pp=findPagePanelById(ppId);
		SystemMainTable smt=super.get(SystemMainTable.class,Long.valueOf(smtId));
		List<Column> columns=findColumns(smt,pp.getSettingType());
		String hql="select ppc from PagePanelColumn ppc where " +
				"ppc.parentPagePanelColumn is null and ppc.pagePanel=?  order by ppc.order";
		List<PagePanelColumn> headcolumns = super.find(hql, pp);
		int maxOrder=1;
		for(PagePanelColumn column:headcolumns){
			if(column.getOrder()>maxOrder)
				maxOrder=column.getOrder()+1;
		}
		for(Column column:columns){
			PagePanelColumn ppc = new PagePanelColumn();
			ppc.setPagePanel(pp);
			ppc.setSystemMainTable(smt);
			ppc.setOrder(maxOrder++);
			ppc.setIsDisplay(1);
			ppc.setIsMustInput(0);
//			if(column instanceof SystemMainTableColumn)
				ppc.setMainTableColumn((SystemMainTableColumn)column);
//			if(column instanceof SystemMainTableExtColumn)
//				ppc.setExtendTableColumn((SystemMainTableExtColumn)column);
			if(column.getSystemMainTableColumnType()!=null){
				SystemMainTableColumnType smtct = column.getSystemMainTableColumnType();
				if(smtct.getColumnTypeName().equals("hidden")){
					ppc.setIsMustInput(1);	
				}
			}
			
			super.save(ppc);
		}
	}

	public List<PagePanelTableRelation> findPanelTableRelByParent(PagePanel panel, SystemMainTable parentSmt) {
		Criteria c = super.getCriteria(PagePanelTableRelation.class);
		c.add(Restrictions.eq("pagePanel", panel));
		c.add(Restrictions.eq("foreignTable", parentSmt));
		List list = c.list();
		return list;
	}

	public List<PagePanelTableRelation> findPanelTableRelBySub(PagePanel panel, SystemMainTable subSmt) {
		Criteria c = super.getCriteria(PagePanelTableRelation.class);
		c.add(Restrictions.eq("pagePanel", panel));
		c.add(Restrictions.eq("systemMainTable", subSmt));
		List list = c.list();
		return list;
	}
	
	public SystemMainTable findSystemMainTable(String tableId){
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id",Long.valueOf(tableId)));
		SystemMainTable systemMainTable = (SystemMainTable)c.setMaxResults(1).uniqueResult();
		return systemMainTable;
	}

public SystemMainTable findSystemMainTableByName(String tableName){
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("tableName",tableName));
		SystemMainTable systemMainTable = (SystemMainTable)c.setMaxResults(1).uniqueResult();
		return systemMainTable;
		
	}
	
	public PagePanelType findPagePanelTypeByXtype(String xtype) {
		Criteria c = super.getCriteria(PagePanelType.class);
		c.add(Restrictions.eq("id",Long.valueOf(xtype)));
		PagePanelType pagePanelType = (PagePanelType)c.setMaxResults(1).uniqueResult();
		return pagePanelType;
	}

	public PagePanel findPagePanelByPanelName(String panelName) {
		Criteria criteria = super.getCriteria(PagePanel.class);
		criteria.add(Restrictions.eq("title", panelName));
		PagePanel pagePanel = (PagePanel)criteria.setMaxResults(1).uniqueResult();
		return pagePanel;
	}

	

	public Page findPagePanelByPage(String factor , int pageNo, int pageSize) {
		
		Criteria c = super.getCriteria(PagePanel.class);
		if(factor!=null && !factor.equals("")){
			c.add(Restrictions.like("title", "%"+factor+"%"));
		}		
		//查寻条件		
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
/**
 * StringBuffer hql = new StringBuffer("from PagePanelType ppt where ppt.id>? and ppt.id<=?");
//		List listParams = new ArrayList();
//		if(flag==1){//是分组面板			
//			listParams.add(Long.valueOf(1l));
//			listParams.add(Long.valueOf(4l));			
//		}else{
//			listParams.add(Long.valueOf(1l));
//			listParams.add(Long.valueOf(6l));
//		}
//		Object[] obj = listParams.toArray();
//		List list = super.createQuery(hql.toString(), obj).list();		
 */
	public List searchPagePanelByPanelName(String panelRelationId) {
		
		PagePanelRelation ppr = super.get(PagePanelRelation.class, Long.valueOf(panelRelationId));
		String panelName = ppr.getPagePanel().getTitle();
		PagePanel pagePanel = this.findPagePanelByPanelName(panelName);
		int flag = pagePanel.getGroupFlag();
		
		Criteria criteria = super.getCriteria(PagePanelType.class);
		if(flag==1){
			criteria.add(Restrictions.eq("groupFlag", 1));
		}
		List list = criteria.list();		
		return list;
	}

	public List findAllTable(){
		//String hql = "select from SystemMainTable";
		List list = super.getAll(SystemMainTable.class);
		return   list;
		
	}
	public SystemMainTable findSystemMainTableByCnName(String cnName) {
		// TODO Auto-generated method stub
		Criteria c =createCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("tableCnName", cnName));		
		return (SystemMainTable) c.uniqueResult();
	}

	public List findAllSystemMainTableColumnByName(String tableName) {
		// TODO Auto-generated method stub
		Criteria c =createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("tableName", tableName));
		return c.list();
	}

	public SystemMainTableColumn findSystemMainTableColumn(String CID) {
		// TODO Auto-generated method stub
		
		Criteria c =createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("id",Long.valueOf(CID) ));
		SystemMainTableColumn systemMainTableColumn = (SystemMainTableColumn)c.setMaxResults(1).uniqueResult();
		return systemMainTableColumn;
	}

	public PagePanelTableRelation savePagePanelTableRelation(PagePanelTableRelation pptr) {
		// TODO Auto-generated method stub
		//getHibernateTemplate().setFlushMode(getHibernateTemplate().FLUSH_AUTO);
		PagePanelTableRelation pagePanelTableRelation = null;
		try {
			pagePanelTableRelation = (PagePanelTableRelation) super.save(pptr);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanel发生异常");
		}		
		return pagePanelTableRelation;
	}

	public void removePagePanelTableRelation(String id) {
		// TODO Auto-generated method stub
		super.executeUpdate("delete from PagePanelTableRelation pptr where pptr.id=?",
				Long.valueOf(id));
//		super.removeObject(PagePanelTableRelation.class, Long.valueOf(id));
		
	}

	//add by peixf
	public List<PagePanelType> findAllBasePanelTypes() {
		Criteria c = super.getCriteria(PagePanelType.class);
		c.add(Restrictions.eq("groupFlag", Integer.valueOf(0)));
		List list = c.list();
		return list;
	}

	public List<PagePanelType> findAllGroupPanelTypes() {
		Criteria c = super.getCriteria(PagePanelType.class);
		c.add(Restrictions.eq("groupFlag", Integer.valueOf(1)));
		List list = c.list();
		return list;
	}
	//end

}
