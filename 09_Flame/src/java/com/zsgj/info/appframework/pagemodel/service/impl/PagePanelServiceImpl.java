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
		List list = c.list(); //��ֹ���ظ���¼
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
		critera.add(Restrictions.ne("groupFlag", 1)); //�Ƿ������
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
	
		if(StringUtils.isNotBlank(pageName)){
			critera.add(Restrictions.disjunction()
					.add(Restrictions.ilike("name", pageName, MatchMode.ANYWHERE)) //���Դ�Сд
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
			throw new ServiceException("��ѯPagePanelʱ�����쳣");
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
			throw new ServiceException("��ȡPagePanel�����쳣");
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
			throw new ServiceException("ͨ��pageModel��ȡPagePanel�����쳣");
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
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
		//����ϼ�Panel������null��������Ϊ2��panel
		if(parentPanelId!=null&& parentPanelId.intValue()!=0){
			//begin ��ȡ�ϼ�Panel
			PagePanel parent = null;
			Criteria c = super.getCriteria(PagePanel.class);
			c.add(Restrictions.eq("id", parentPanelId));
			parent = (PagePanel) c.uniqueResult();
			//end
			//ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
		}else{//�ƶ�����PageModel����Ķ���Panel
			
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
			// ��ͬһ�����ڵ��·����ƶ�
			if(minIndex < maxIndex){
				// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
				this.downNode(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNode(oldParentId, minIndex, maxIndex);
			}
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			/*obj.setMenuOrder(nodeIndex);*/
			this.savePagePanel(obj);
		}
		if(oldParentId.intValue() != newParentId.intValue()){
			// �ڲ�ͬ���ڵ��·����ƶ�
			//1���൱��Ҫ�ƶ��Ľڵ���ԭ���ڵ������Ƶ������ɾ���������Ҫָ���ƶ�����ʱ�ڵ����ڵ�λ��
			this.downNode(oldParentId, minIndex, -1);
			//2���൱��Ҫ�ƶ��Ľڵ����¸��ڵ������Ƶ�ָ����λ�ã������Ҫָ��Ҫ�ƶ�����λ��
			this.upNode(newParentId, maxIndex, -1);
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			/*obj.setMenuOrder(nodeIndex);
			obj.setParentMenu(newParent);
			this.saveMenu(obj);*/
		}
		
		
	}

	public void removePagePanel(String[] pagePanelIds) {
		if(pagePanelIds==null||pagePanelIds.length==0){
			throw new ServiceException("ɾ��PagePanel�����쳣");
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
			throw new ServiceException("����pagePanel�����쳣");
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
	 * �����ƶ���Module��������Ӧ��pagePanel
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
			throw new ServiceException("��ѯPagePanelʱ�����쳣");
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
	 * ��Ҫ�����������1.����һ�ν���ҳ���ʱ���ʱ�ı���Ϊ"",comboxΪnull;
	 * 2.��������һ�������ʱ��{
	 * 		2.1.��ֻ�����ı����ʱ��comboxΪ�մ� 
	 * 		2.2.��ֻ����combox��ʱ���ı���Ϊ�մ�
	 * }
	 * 3.���߶�����
	 * 4.��������õ�ʱ������Ϊ�մ�
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
		//��Ѱ����		
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
/**
 * StringBuffer hql = new StringBuffer("from PagePanelType ppt where ppt.id>? and ppt.id<=?");
//		List listParams = new ArrayList();
//		if(flag==1){//�Ƿ������			
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
			throw new ServiceException("����pagePanel�����쳣");
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
			throw new ServiceException("����pagePanel�����쳣");
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
		pagePanelColumn.setFieldSetFlag(pagePanelColumn.FIELD_SET_FALSE);//��fieldSet
		pagePanelColumn.setIsDisplay(Integer.parseInt("1"));//����ʾ�ֶ�
		pagePanelColumn.setIsMustInput(Integer.parseInt("0"));//�Ǳ�����
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
