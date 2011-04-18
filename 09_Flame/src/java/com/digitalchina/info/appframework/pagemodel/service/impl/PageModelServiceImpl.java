package com.digitalchina.info.appframework.pagemodel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelNode;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelMiddleTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelRelation;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.appframework.pagemodel.service.PageModelService;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Module;

public class PageModelServiceImpl extends BaseDao implements PageModelService{

	/* (non-Javadoc)
	 * @see com.digitalchina.info.appframework.pagemodel.service.PageModelService#findPageModelPanelReadonlyFlag(java.lang.String, java.lang.String)
	 */
	public boolean findPageModelPanelReadonlyFlag(String model,
			String panel) {
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.createAlias("this.pageModel", "pageModel").setFetchMode("pageModel", FetchMode.JOIN);
		c.add(Restrictions.eq("pageModel.name", model));
		
		c.createAlias("this.pagePanel", "pagePanel").setFetchMode("pagePanel", FetchMode.JOIN);
		c.add(Restrictions.eq("pagePanel.name", panel));
		
		PageModelPanel result = (PageModelPanel) c.uniqueResult();
		Integer readonlyFlag= result.getReadonly();
		if(readonlyFlag==null) return false;
		return readonlyFlag.intValue()==1;
	}
	
	public Map<String, Object> findPageModelPanel(String model,
			String panel) {
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.createAlias("this.pageModel", "pageModel").setFetchMode("pageModel", FetchMode.JOIN);
		c.add(Restrictions.eq("pageModel.name", model));
		
		c.createAlias("this.pagePanel", "pagePanel").setFetchMode("pagePanel", FetchMode.JOIN);
		c.add(Restrictions.eq("pagePanel.name", panel));
		
		PageModelPanel result = (PageModelPanel) c.uniqueResult();
		Map<String, Object> returnValue = new HashMap<String, Object>();
		
		returnValue.put("pagePanel", result.getPagePanel());
		returnValue.put("readonly", result.getReadonly() == null ? 0 : result.getReadonly().intValue());
		
		return returnValue;
	}

	public PageModel findPageModelByNode(String node) {
		Criteria c = super.getCriteria(PageModelNode.class);
		c.add(Restrictions.like("nodeName", node, MatchMode.EXACT));
		c.setProjection(Projections.property("pageModel"));
		List list = c.list();
		PageModel model = null;
		if(!list.isEmpty()){
			model = (PageModel) list.iterator().next();
		}
		return model;
	}

	public boolean existPageModelCountByPagePath(String pagePath) {
		Criteria c = super.getCriteria(PageModel.class);
		c.add(Restrictions.ilike("pagePath", pagePath));
		c.setProjection(Projections.rowCount());
		Integer count = (Integer) c.uniqueResult();
		if(count==null) return false;
		if(count!=null&& count.intValue()==0) return false;
		return true;
	}

	private List<PageModelPanel> getChildPanels(PageModelPanel pmp){
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("pageModel", pmp.getPageModel()));
		c.add(Restrictions.eq("parentPagePanel", pmp.getPagePanel()));
		List<PageModelPanel> list = c.list();
		return list;
	}
	
	public PageModel findPageModel(String keyName) {
	//	super.exportDB();
		PageModel result = null;
		try {
			String hql="select pm from PageModel pm left outer join pm.systemMainTable where pm.name=?";
			List models = super.find(hql, keyName);
			if(!models.isEmpty()){
				result = (PageModel) models.iterator().next();
				Criteria c = super.getCriteria(PageModelPanel.class);
				c.add(Restrictions.eq("this.pageModel", result));
				c.add(Restrictions.isNull("this.parentPagePanel")); //get top pagePanel 
				c.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
				c.addOrder(Order.asc("order"));
				List<PageModelPanel> list = c.list();
				for(PageModelPanel pmp : list){
					List<PageModelPanel> childPagePanels = getChildPanels(pmp);
					pmp.setChildPagePanels(childPagePanels);
				}
				result.setPagePanels(list);
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("��ȡpageModel�����쳣");
		}		
		return result;
	}
	
	public PageModel findPageModel$$$$$(String keyName) {
		PageModel result = null;
		try {
			result = super.findUniqueBy(PageModel.class, "name", keyName);
			result = this.findPageModelWithPanels$$$$$(String.valueOf(result.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("��ȡpageModel�����쳣");
		}		
		return result;
	}

	public PageModel findPageModelWithPanels(String pageModelId) {
		PageModel result = null;
		try {
			result = super.get(PageModel.class, Long.valueOf(pageModelId));
			Criteria c = super.getCriteria(PageModelPanel.class);
			c.add(Restrictions.eq("this.pageModel", result));
			c.add(Restrictions.isNull("this.parentPagePanel")); //get top pagePanel 
			List<PageModelPanel> list = c.list();
			for(PageModelPanel pmp : list){
				List<PageModelPanel> childPagePanels = getChildPanels(pmp);
				pmp.setChildPagePanels(childPagePanels);
			}
			result.setPagePanels(list);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("��ȡpageModel�����쳣");
		}		
		return result;
	}
	
	private void initChildPanels(PagePanel parent){
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("id", parent.getId()));
		c.setFetchMode("childPagePanels", FetchMode.JOIN);
		parent = (PagePanel) c.uniqueResult();
		Set<PagePanelRelation> childens = parent.getChildPagePanels();
		for(PagePanelRelation item : childens){
			PagePanel childPanel = item.getPagePanel();
			if(childPanel.getGroupFlag()!=null&& childPanel.getGroupFlag().intValue()==1){
				this.initChildPanels(childPanel);
			}
			
		}
	}
	
	public PageModel findPageModelWithPanels$$$$$(String pageModelId) {
		PageModel result = null;
		try {
			result = super.get(PageModel.class, Long.valueOf(pageModelId));
			
			Criteria c = super.getCriteria(PageModelPanel.class);
			c.add(Restrictions.eq("this.pageModel", result));
			c.addOrder(Order.asc("order"));
			List<PageModelPanel> list = c.list();
			for(PageModelPanel pmp : list){
				PagePanel panel = pmp.getPagePanel();
				if(panel.getGroupFlag()!=null&& panel.getGroupFlag().intValue()==1){
					this.initChildPanels(panel); //��ʼ����PagePanel�µ�PagePanelRelation
				}
			}
			result.setPagePanels(list); //��ʼ����model�µ�PageModelPanel
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("��ȡpageModel�����쳣");
		}		
		return result;
	}

	public void removePageModel(String[] pagePanelIds) {
		if(pagePanelIds==null||pagePanelIds.length==0){
			throw new ServiceException("ɾ��PageModel�����쳣");
		}
		for(String pagePanelId: pagePanelIds){
			this.removePageModel(pagePanelId);
		}
		
		
	}

	public void removePageModel(String modelId) {
		try {
			super.executeUpdate("delete from PageModelPanel pmp where pmp.pageModel.id=?", Long.valueOf(modelId));
			super.executeUpdate("delete from PageModelBtn pmp where pmp.pageModel.id=?", Long.valueOf(modelId));		
			super.executeUpdate("update PageModelNode set pageModel=null where pageModel.id=?", Long.valueOf(modelId));	
			super.executeUpdate("update PageModelBtn set nextPageModel=null where nextPageModel.id=?", Long.valueOf(modelId));
			//super.executeUpdate("delete from PageModelPanelRelation  where pageModel.id=?", Long.valueOf(modelId));	
			
			super.removeObject(PageModel.class, Long.valueOf(modelId));
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("ɾ��pageModel�����쳣");
		}		
	}

	public PageModel savePageModel(PageModel pageModel) {
		PageModel result = null;
		try {
			if(pageModel.getId()==null){
				String pagePath = pageModel.getPagePath();
				boolean extis = this.existPageModelCountByPagePath(pagePath);
				if(extis){
					throw new ServiceException("Ĭ�����ɵ�ҳ��·��������·���ظ������ֶ�����ҳ��·��");
				}
			}
			result = (PageModel) super.save(pageModel);
			PagePanel mainPanel = result.getMainPagePanel();
			if(mainPanel!=null){
				mainPanel = super.get(PagePanel.class, mainPanel.getId());
				SystemMainTable smt = mainPanel.getSystemMainTable();
				result.setSystemMainTable(smt);
				super.save(pageModel);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("����pageModel�����쳣");
		}		
		return result;
	}

	public Page findPageModel(Module module, String pageName, int pageNo, int pageSize) {
		Page page = null;
		Criteria critera = super.createCriteria(PageModel.class);
		critera.add(Restrictions.isNotNull("id"));
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
		if(StringUtils.isNotBlank(pageName)){
			critera.add(Restrictions.disjunction()
					.add(Restrictions.ilike("name", pageName, MatchMode.ANYWHERE)) //���Դ�Сд
					.add(Restrictions.ilike("title", pageName, MatchMode.ANYWHERE)));
		}
		/*critera.addOrder(Order.asc("module"));
		critera.addOrder(Order.asc("name"));*/
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;
	}

	public Page findPageModel(Map params, int pageNo, int pageSize) {
		Page page = null;
		Module module = (Module) params.get("module");
		String pageName = (String) params.get("pageName");
		String settingType = (String) params.get("settingType");
		Criteria critera = super.createCriteria(PageModel.class);
		critera.add(Restrictions.isNotNull("id"));
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
		/*critera.addOrder(Order.asc("module"));
		critera.addOrder(Order.asc("name"));*/
		critera.addOrder(Order.desc("id"));
		critera.addOrder(Order.asc("name"));
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;
	}

	public PageModel findPageModelByPageModelName(String pageModelName) {
		Criteria c = super.getCriteria(PageModel.class);
		c.add(Restrictions.eq("title", pageModelName));
		PageModel pageModel = (PageModel)c.setMaxResults(1).uniqueResult();
		return pageModel;
	}

	public PageModelBtn findPageModelBtnByModifyId(String id) {
		Criteria c = super.getCriteria(PageModelBtn.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		PageModelBtn pageModelBtn = (PageModelBtn)c.setMaxResults(1).uniqueResult();
		return pageModelBtn;
	}
	/**
	 * �ڱ���pageModel��ͬʱҲҪ��Ӧ����pagemModelBtn,����settingType���ж�ʲô���İ�ťӦ�ñ���Ӧ
	 * �ı��档
	 */
	
	public void savePageModelBtn(PageModel pageModel) {		
		SystemMainTable smt = pageModel.getSystemMainTable();
		Integer settingType = pageModel.getSettingType();
		super.executeUpdate("delete PageModelBtn where pageModel=?", pageModel);
		if(settingType==1){//�б�ҳ��
			//�����漴��ȡһ��������ҳ�棬settingType=2
			PageModel pageModelForm = null;
			String hql = "from PageModel pm where pm.systemMainTable=? and pm.settingType=?";
			List list = super.find(hql, new Object[]{smt, SystemTableSetting.INPUT});
			if(!list.isEmpty()){
				pageModelForm = (PageModel) list.iterator().next();
			}
			
			PageModelBtn addButton = new PageModelBtn();
			addButton.setBtnName("����");
			addButton.setPageModel(pageModel);
			addButton.setMethod("addByPage");
			addButton.setImageUrl("add");
			addButton.setOrder(1);
			addButton.setIsDisplay(1);
			if(pageModelForm!=null){
				addButton.setNextPageModel(pageModelForm);//������ť��Ŀ��ҳ��������ҳ��
				addButton.setLink(pageModelForm.getPagePath());
			}
			super.save(addButton);
			
			PageModelBtn searchButton = new PageModelBtn();
			searchButton.setBtnName("��ѯ");
			searchButton.setPageModel(pageModel);
			searchButton.setMethod("search");
			searchButton.setImageUrl("search");
			searchButton.setOrder(2);
			searchButton.setIsDisplay(1);
			searchButton.setNextPageModel(pageModel);//��ѯҳ���Ŀ��ҳ����Ǳ���
			searchButton.setLink(pageModel.getPagePath());
			super.save(searchButton);
			
			PageModelBtn modifyButton = new PageModelBtn();
			modifyButton.setBtnName("�޸�");
			modifyButton.setPageModel(pageModel);
			modifyButton.setMethod("modifyByPage");
			modifyButton.setImageUrl("edit");
			modifyButton.setOrder(3);
			modifyButton.setIsDisplay(1);
			if(pageModelForm!=null){
				modifyButton.setNextPageModel(pageModelForm);//�޸İ�ť��Ŀ��ҳ��������ҳ��
				modifyButton.setLink(pageModelForm.getPagePath()+"?dataId=");
			}
			super.save(modifyButton);
			
			PageModelBtn resetButton = new PageModelBtn();
			resetButton.setBtnName("����");
			resetButton.setPageModel(pageModel);
			resetButton.setMethod("reset");
			resetButton.setImageUrl("reset");
			resetButton.setOrder(4);
			resetButton.setIsDisplay(1);
			resetButton.setNextPageModel(pageModel);//���õ��ǲ�ѯ������������Ŀ��ҳ�滹�Ǳ���
			resetButton.setLink(pageModel.getPagePath());
//			if(pageModelForm!=null){
//				resetButton.setNextPageModel(pageModelForm);
//				resetButton.setLink(pageModelForm.getPagePath());
//			}
			super.save(resetButton);
			
			PageModelBtn delButton = new PageModelBtn();
			delButton.setBtnName("ɾ��");
			delButton.setPageModel(pageModel);
			delButton.setMethod("removeForModel");
			delButton.setImageUrl("remove");
			delButton.setOrder(5);
			delButton.setIsDisplay(1);
			delButton.setNextPageModel(pageModel);//���õ��ǲ�ѯ������������Ŀ��ҳ�滹�Ǳ���
			delButton.setLink(pageModel.getPagePath());
//			if(pageModelForm!=null){
//				delButton.setNextPageModel(pageModelForm);
//				delButton.setLink(pageModelForm.getPagePath());
//			}
			super.save(delButton);
			
			PageModelBtn exportButton = new PageModelBtn();
			exportButton.setBtnName("����");
			exportButton.setPageModel(pageModel);
			exportButton.setMethod("export");
			exportButton.setImageUrl("export");
			exportButton.setOrder(6);
			exportButton.setIsDisplay(1);
			exportButton.setNextPageModel(pageModel);//���õ��ǲ�ѯ������������Ŀ��ҳ�滹�Ǳ���
			exportButton.setLink(pageModel.getPagePath());
			super.save(exportButton);

		}else if(settingType==2){//�൱�ڱ�ҳ��
			PageModel pageModelList = null;
			String hql = "from PageModel pm where pm.systemMainTable=? and pm.settingType=?";
			List list = super.find(hql, new Object[]{smt, Integer.valueOf(1)});
			if(!list.isEmpty()){
				pageModelList = (PageModel) list.iterator().next();
			}
			
			PageModelBtn addButton = new PageModelBtn();
			addButton.setBtnName("����");
			addButton.setPageModel(pageModel);
			addButton.setOrder(1);
			addButton.setNextPageModel(pageModel);
			addButton.setMethod("saveForModel");
			addButton.setImageUrl("save");
			addButton.setIsDisplay(1);
			if(pageModelList!=null){
				addButton.setNextPageModel(pageModelList);
				addButton.setLink(pageModelList.getPagePath());
			}
			super.save(addButton);
			
//			PageModelBtn modifyButton = new PageModelBtn();
//			modifyButton.setBtnName("�޸�");
//			modifyButton.setPageModel(pageModel);
//			modifyButton.setOrder(2);
//			modifyButton.setNextPageModel(pageModel);	
//			super.save(modifyButton);
//			
			PageModelBtn resetButton = new PageModelBtn();
			resetButton.setBtnName("����");
			resetButton.setMethod("reset");
			resetButton.setPageModel(pageModel);
			resetButton.setOrder(3);
			resetButton.setIsDisplay(1);
			resetButton.setNextPageModel(pageModel);
			resetButton.setLink(pageModel.getPagePath());
			super.save(resetButton);
			
			PageModelBtn returnButton = new PageModelBtn();
			returnButton.setBtnName("����");
			returnButton.setPageModel(pageModel);
			returnButton.setOrder(4);
			returnButton.setIsDisplay(1);
			returnButton.setMethod("goBack");
			if(pageModelList!=null){
				returnButton.setNextPageModel(pageModelList);
				returnButton.setLink(pageModelList.getPagePath());
			}
			super.save(returnButton);
			
			PageModelBtn delButton = new PageModelBtn();
			delButton.setBtnName("ɾ��");
			delButton.setPageModel(pageModel);
			delButton.setOrder(5);
			delButton.setIsDisplay(1);
			delButton.setMethod("removeForModel");
			delButton.setImageUrl("remove");
			if(pageModelList!=null){
				delButton.setNextPageModel(pageModelList);
				delButton.setLink(pageModelList.getPagePath());
			}
			super.save(delButton);
		}
		
	}

	public List<PageModelPanelTable> findPageModelPanelTableByModel(PageModel pageModel) {
		String hql = "from PageModelPanelTable pmpt where pmpt.pageModel=?";
		List list = super.find(hql, pageModel);
		return list;
	}

	public List<PageModelPanelTable> findPageModelPanelTable(PageModel pageModel, PagePanel pagePanel) {
		Criteria c = super.getCriteria(PageModelPanelTable.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		c.add(Restrictions.eq("subPagePanel", pagePanel));
		List list = c.list();
		return list;
	}

	public List<PageModelPanelTable> findPageModelPanelTableBySub(PageModel pageModel, PagePanel pagePanel, SystemMainTable smt) {
		Criteria c = super.getCriteria(PageModelPanelTable.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		c.add(Restrictions.eq("subPagePanel", pagePanel));
		c.add(Restrictions.eq("subPanelTable", smt));
		List list = c.list();
		return list;
	}

	public List<PageModelPanelMiddleTable> findPageModelPanelMiddleTableBySub(PageModel pageModel, 
				PagePanel subPagePanel, SystemMainTable subTable) {
		Criteria c = super.getCriteria(PageModelPanelMiddleTable.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		c.add(Restrictions.eq("subPagePanel", subPagePanel));
		c.add(Restrictions.eq("subPanelTable", subTable));
		List list = c.list();
		return list;
	}

	public List<PageModelPanelTable> findPageModelPanelTableByParent(PageModel pageModel, PagePanel parentPagePanel, SystemMainTable parentTable) {
		Criteria c = super.getCriteria(PageModelPanelTable.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		c.add(Restrictions.eq("parentPagePanel", parentPagePanel));
		c.add(Restrictions.eq("parentPanelTable", parentTable));
		List list = c.list();
		return list;
	}

	public List<PageModelBtn> findPageModelBtnByModel(PageModel pageModel) {
		Criteria c = super.getCriteria(PageModelBtn.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		//c.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
		c.addOrder(Order.asc("order"));
		List list = c.list();
		return list;
	}

	public List<SystemMainTable> findSystemMainTable(Module module) {
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("module", module));
		c.addOrder(Order.asc("tableName"));
		List list = c.list();
		return list;
	}

	public void removePageModelPanelTable(String id) {
		// TODO Auto-generated method stub
		super.executeUpdate("delete from PageModelPanelTable pmpt where pmpt.id=?",
				Long.valueOf(id));
	}

	public List findAllPagePanel() {
		// TODO Auto-generated method stub
		List list = super.getAll(PagePanel.class);
		return   list;
	}

	public List findAllMainTableByPanel(PagePanel pagePanel) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(PagePanelTable.class);
		c.add(Restrictions.eq("pagePanel", pagePanel));
		return c.list();
	}

	public PagePanel findPagePanelById(String id) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("id",Long.valueOf(id) ));
		PagePanel pagePanel = (PagePanel)c.setMaxResults(1).uniqueResult();
		return pagePanel;
	}

	public List findAllSystemMainTableColumnByName(String tableName) {
		SystemMainTable smt = null;
		Criteria ct = super.getCriteria(SystemMainTable.class);
		ct.add(Restrictions.eq("tableName", tableName));
		List<SystemMainTable> listtb = ct.list();
		if(!listtb.isEmpty()){
			smt = listtb.iterator().next();
		}
		Criteria c =createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		return c.list();
	}

	public SystemMainTable findSystemMainTable(String tableId) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id",Long.valueOf(tableId)));
		SystemMainTable systemMainTable = (SystemMainTable)c.setMaxResults(1).uniqueResult();
		return systemMainTable;
	}

	public PageModelPanelTable savePageModelPanelTable(PageModelPanelTable pageModelPanelTable) {
		PageModelPanelTable pmpt = null;
		try {
			pmpt = (PageModelPanelTable) super.save(pageModelPanelTable);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("����pagePanel�����쳣");
		}		
		return pmpt;
		// TODO Auto-generated method stub
		
	}

	public PageModelPanelTable findPageModelPanelTable(String id) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(PageModelPanelTable.class);
		c.add(Restrictions.eq("id",Long.valueOf(id)));
		PageModelPanelTable pageModelPanelTable = (PageModelPanelTable) c.setMaxResults(1).uniqueResult();
		return pageModelPanelTable;
	}

	public PageModel findPageModelById(String id) {
		if(StringUtils.isNotBlank(id)){
			Criteria c = super.getCriteria(PageModel.class);
			c.add(Restrictions.eq("id",Long.valueOf(id)));
			return (PageModel) c.uniqueResult();
		}else
			return null;
	}

//	public SystemMainTableColumn findSystemMainTablePrimaryKeyColumn(
//			SystemMainTableColumn systemMainTableColumn) {
//		// TODO Auto-generated method stub
//		Criteria c = super.getCriteria(SystemMainTableColumn.class);
//		SystemMainTableColumn s = (SystemMainTableColumn)c.setMaxResults(1).uniqueResult();
//		return s;
//	}


	
	
}
