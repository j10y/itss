package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelFieldSet;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Module;

public class PagePanelServiceImpl extends BaseDao implements PagePanelService {
	
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
		
		Criteria critera = super.createCriteria(PagePanel.class);
		//critera.add(Restrictions.isNotNull("id"));
		critera.add(Restrictions.ne("groupFlag", 1)); //非分组面板
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
	
		if(StringUtils.isNotBlank(pageName)){
			critera.add(Restrictions.disjunction()
					.add(Restrictions.ilike("name", pageName, MatchMode.ANYWHERE)) //忽略大小写
					.add(Restrictions.ilike("title", pageName, MatchMode.ANYWHERE)));
		}

		if(StringUtils.isNotBlank(settingType)){
			critera.add(Restrictions.eq("settingType", Integer.valueOf(settingType)));
		}
		critera.addOrder(Order.desc("id"));
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
			c.addOrder(Order.desc("id"));
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
		super.executeUpdate("delete from PagePanelColumn ppc where ppc.pagePanel.id=?", 
					Long.valueOf(pagePanelId));
		
		super.executeUpdate("delete from PageGroupPanelTable ppc where ppc.pagePanel.id=? or ppc.subPagePanel.id=? or " +
				"ppc.parentPagePanel.id=?", 
				new Object[]{Long.valueOf(pagePanelId), Long.valueOf(pagePanelId), Long.valueOf(pagePanelId)});
		
		super.executeUpdate("delete from PagePanelRelation ppc where ppc.parentPagePanel.id=? or ppc.pagePanel.id=?", 
				new Object[]{Long.valueOf(pagePanelId), Long.valueOf(pagePanelId)});
		
		
		super.executeUpdate("delete from PagePanelBtn ppb where ppb.pagePanel.id=?",
					Long.valueOf(pagePanelId));
		try {
			super.executeUpdate(
					"update ConfigItemType set pagePanel=null where pagePanel.id=?",
					Long.valueOf(pagePanelId));
					
			super.executeUpdate(
					"update ConfigItemType set groupPanel=null where groupPanel.id=?",
					Long.valueOf(pagePanelId));
					
		} catch (Exception e) {
			e.printStackTrace();
		}		
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
		Criteria c = super.getCriteria(PagePanelTable.class);
		c.add(Restrictions.eq("pagePanel", panel));
		c.setProjection(Projections.property("systemMainTable"));
		c.setFetchMode("systemMainTable", FetchMode.JOIN);
		//String hql = "select ppt.systemMainTable from PagePanelTable ppt where ppt.pagePanel=?";
		List list = c.list(); //= super.find(hql, panel);
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
		String temp = "";
		for(Column column:columns){
			try {
				temp = column.getPropertyName();
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
			
				if (column.getSystemMainTableColumnType() != null) {
					SystemMainTableColumnType smtct = column
							.getSystemMainTableColumnType();
					if (smtct.getColumnTypeName().equals("hidden")) {
						ppc.setIsMustInput(1);
					}
				}
					
			super.save(ppc);
			} catch (Exception e) {
				e.printStackTrace();
			}	
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
		String tableCnName = subSmt.getTableCnName();
		String panelTitle = panel.getTitle();
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

	/**
	 * 需要分三种情况：1.当第一次进入页面的时候此时文本域为"",combox为null;
	 * 2.当两者由一个输入的时候{
	 * 		2.1.当只输入文本域的时候，combox为空串 
	 * 		2.2.当只输入combox的时候，文本域为空串
	 * }
	 * 3.两者都输入
	 * 4.当点击重置的时候，两个为空串
	 * @Methods Name findPagePanelByPage
	 * @Create In Jan 9, 2009 By sai
	 * @return Page
	 */
	public Page findPagePanelByPage(String factor ,String box, int pageNo, int pageSize) {
		
		Criteria c = super.getCriteria(PagePanel.class);
		if(!factor.equals("")&& box!=null && !box.equals("")){
			Module module = (Module)super.get(Module.class, Long.valueOf(box));
			c.add(Restrictions.like("title", "%"+factor+"%"));
			c.add(Restrictions.like("module", module));
		}else if(!factor.equals("")){
			c.add(Restrictions.like("title", "%"+factor+"%"));
		}else if(box!=null&&!box.equals("")){
			Module module = (Module)super.get(Module.class, Long.valueOf(box));
			c.add(Restrictions.like("module", module));
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

	public Page findSystemMainTable(int pageNo, int pageSize) {
		Criteria criteria = super.getCriteria(SystemMainTable.class);
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
//		List list = page.list();
		return page;
	}

	public Page findForeignKey(Long sysTabelId, int pageNo, int pageSize) {
		SystemMainTable mainTabel = super.get(SystemMainTable.class, sysTabelId);
		Criteria criteria = super.getCriteria(SystemMainTableColumn.class);
		criteria.add(Restrictions.eq("systemMainTable", mainTabel));
		Page page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	
	public Page findPanelByPageModule(Module module, int pageNo, int pageSize) {
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("module", module));
		c.addOrder(Order.asc("title"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
	public List<PageGroupPanelTable> findPageGroupPanelTableByPanel(PagePanel pagePanel) {
//		String hql = "select from PageGroupPanelTable pgpt where pgpt.pagePanel=?";
//		List list = super.find(hql, pagePanel);
//		return list;
//		List<PageGroupPanelTable> list=super.findBy(PageGroupPanelTable.class, "pagePanel", pagePanel);
		Criteria c = super.getCriteria(PageGroupPanelTable.class);
		c.add(Restrictions.eq("pagePanel", pagePanel));
		List<PageGroupPanelTable> list=c.list();
		return list;
	}

	public void removePageGroupPanelTable(String id) {
		// TODO Auto-generated method stub
		super.executeUpdate("delete from PageGroupPanelTable pgpt where pgpt.id=?",
				Long.valueOf(id));
	}
	public List findAllPagePanel() {
		// TODO Auto-generated method stub
		List list = super.getAll(PagePanel.class);
		return   list;
	}
	public PageGroupPanelTable savePageGroupPanelTable(PageGroupPanelTable pageGroupPanelTable) {
		PageGroupPanelTable pgpt = null;
		try {
			pgpt = (PageGroupPanelTable) super.save(pageGroupPanelTable);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanel发生异常");
		}		
		return pgpt;
		// TODO Auto-generated method stub
		
	}
	public PageGroupPanelTable findPageGroupPanelTable(String id) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(PageGroupPanelTable.class);
		c.add(Restrictions.eq("id",Long.valueOf(id)));
		PageGroupPanelTable pageGroupPanelTable = (PageGroupPanelTable) c.setMaxResults(1).uniqueResult();
		return pageGroupPanelTable;
	}

	public PagePanelColumn savePagePanelColumn(PagePanel pagePanel, String index) {
		PagePanelColumn pagePanelColumn = new PagePanelColumn();
		pagePanelColumn.setPagePanel(pagePanel);
		pagePanelColumn.setOrder(Integer.parseInt(index));
		pagePanelColumn.setFieldSetFlag(pagePanelColumn.FIELD_SET_FALSE);//是fieldSet
		pagePanelColumn.setIsDisplay(Integer.parseInt("1"));//是显示字段
		pagePanelColumn.setIsMustInput(Integer.parseInt("0"));//是必填项
		super.save(pagePanelColumn);
		return pagePanelColumn;
	}

	public PagePanelFieldSet savePagePanelFieldSet(PagePanelColumn pagePanelColumn,
			PagePanel pagePanel, String title) {
		PagePanelFieldSet pagePanelFieldSet = new PagePanelFieldSet();
		pagePanelFieldSet.setPagePanelColumn(pagePanelColumn);
		pagePanelFieldSet.setTitle(title);
		pagePanelFieldSet.setPagePanel(pagePanel);
		super.save(pagePanelFieldSet);
		return pagePanelFieldSet;
	}

}
