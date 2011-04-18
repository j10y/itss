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
			throw new ServiceException("获取pageModel发生异常");
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
			throw new ServiceException("获取pageModel发生异常");
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
			throw new ServiceException("获取pageModel发生异常");
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
					this.initChildPanels(panel); //初始化好PagePanel下的PagePanelRelation
				}
			}
			result.setPagePanels(list); //初始化好model下的PageModelPanel
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("获取pageModel发生异常");
		}		
		return result;
	}

	public void removePageModel(String[] pagePanelIds) {
		if(pagePanelIds==null||pagePanelIds.length==0){
			throw new ServiceException("删除PageModel发生异常");
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
			throw new ServiceException("删除pageModel发生异常");
		}		
	}

	public PageModel savePageModel(PageModel pageModel) {
		PageModel result = null;
		try {
			if(pageModel.getId()==null){
				String pagePath = pageModel.getPagePath();
				boolean extis = this.existPageModelCountByPagePath(pagePath);
				if(extis){
					throw new ServiceException("默认生成的页面路径与现有路径重复，请手动输入页面路径");
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
			throw new ServiceException("保存pageModel发生异常");
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
					.add(Restrictions.ilike("name", pageName, MatchMode.ANYWHERE)) //忽略大小写
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
					.add(Restrictions.ilike("name", pageName, MatchMode.ANYWHERE)) //忽略大小写
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
	 * 在保存pageModel的同时也要相应保存pagemModelBtn,根据settingType来判断什么样的按钮应该被相应
	 * 的保存。
	 */
	
	public void savePageModelBtn(PageModel pageModel) {		
		SystemMainTable smt = pageModel.getSystemMainTable();
		Integer settingType = pageModel.getSettingType();
		super.executeUpdate("delete PageModelBtn where pageModel=?", pageModel);
		if(settingType==1){//列表页面
			//事先随即获取一个表单输入页面，settingType=2
			PageModel pageModelForm = null;
			String hql = "from PageModel pm where pm.systemMainTable=? and pm.settingType=?";
			List list = super.find(hql, new Object[]{smt, SystemTableSetting.INPUT});
			if(!list.isEmpty()){
				pageModelForm = (PageModel) list.iterator().next();
			}
			
			PageModelBtn addButton = new PageModelBtn();
			addButton.setBtnName("新增");
			addButton.setPageModel(pageModel);
			addButton.setMethod("addByPage");
			addButton.setImageUrl("add");
			addButton.setOrder(1);
			addButton.setIsDisplay(1);
			if(pageModelForm!=null){
				addButton.setNextPageModel(pageModelForm);//新增按钮的目标页面是输入页面
				addButton.setLink(pageModelForm.getPagePath());
			}
			super.save(addButton);
			
			PageModelBtn searchButton = new PageModelBtn();
			searchButton.setBtnName("查询");
			searchButton.setPageModel(pageModel);
			searchButton.setMethod("search");
			searchButton.setImageUrl("search");
			searchButton.setOrder(2);
			searchButton.setIsDisplay(1);
			searchButton.setNextPageModel(pageModel);//查询页面的目标页面就是本身
			searchButton.setLink(pageModel.getPagePath());
			super.save(searchButton);
			
			PageModelBtn modifyButton = new PageModelBtn();
			modifyButton.setBtnName("修改");
			modifyButton.setPageModel(pageModel);
			modifyButton.setMethod("modifyByPage");
			modifyButton.setImageUrl("edit");
			modifyButton.setOrder(3);
			modifyButton.setIsDisplay(1);
			if(pageModelForm!=null){
				modifyButton.setNextPageModel(pageModelForm);//修改按钮的目标页面是输入页面
				modifyButton.setLink(pageModelForm.getPagePath()+"?dataId=");
			}
			super.save(modifyButton);
			
			PageModelBtn resetButton = new PageModelBtn();
			resetButton.setBtnName("重置");
			resetButton.setPageModel(pageModel);
			resetButton.setMethod("reset");
			resetButton.setImageUrl("reset");
			resetButton.setOrder(4);
			resetButton.setIsDisplay(1);
			resetButton.setNextPageModel(pageModel);//重置的是查询条件表单本身，故目标页面还是本身
			resetButton.setLink(pageModel.getPagePath());
//			if(pageModelForm!=null){
//				resetButton.setNextPageModel(pageModelForm);
//				resetButton.setLink(pageModelForm.getPagePath());
//			}
			super.save(resetButton);
			
			PageModelBtn delButton = new PageModelBtn();
			delButton.setBtnName("删除");
			delButton.setPageModel(pageModel);
			delButton.setMethod("removeForModel");
			delButton.setImageUrl("remove");
			delButton.setOrder(5);
			delButton.setIsDisplay(1);
			delButton.setNextPageModel(pageModel);//重置的是查询条件表单本身，故目标页面还是本身
			delButton.setLink(pageModel.getPagePath());
//			if(pageModelForm!=null){
//				delButton.setNextPageModel(pageModelForm);
//				delButton.setLink(pageModelForm.getPagePath());
//			}
			super.save(delButton);
			
			PageModelBtn exportButton = new PageModelBtn();
			exportButton.setBtnName("导出");
			exportButton.setPageModel(pageModel);
			exportButton.setMethod("export");
			exportButton.setImageUrl("export");
			exportButton.setOrder(6);
			exportButton.setIsDisplay(1);
			exportButton.setNextPageModel(pageModel);//重置的是查询条件表单本身，故目标页面还是本身
			exportButton.setLink(pageModel.getPagePath());
			super.save(exportButton);

		}else if(settingType==2){//相当于表单页面
			PageModel pageModelList = null;
			String hql = "from PageModel pm where pm.systemMainTable=? and pm.settingType=?";
			List list = super.find(hql, new Object[]{smt, Integer.valueOf(1)});
			if(!list.isEmpty()){
				pageModelList = (PageModel) list.iterator().next();
			}
			
			PageModelBtn addButton = new PageModelBtn();
			addButton.setBtnName("保存");
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
//			modifyButton.setBtnName("修改");
//			modifyButton.setPageModel(pageModel);
//			modifyButton.setOrder(2);
//			modifyButton.setNextPageModel(pageModel);	
//			super.save(modifyButton);
//			
			PageModelBtn resetButton = new PageModelBtn();
			resetButton.setBtnName("重置");
			resetButton.setMethod("reset");
			resetButton.setPageModel(pageModel);
			resetButton.setOrder(3);
			resetButton.setIsDisplay(1);
			resetButton.setNextPageModel(pageModel);
			resetButton.setLink(pageModel.getPagePath());
			super.save(resetButton);
			
			PageModelBtn returnButton = new PageModelBtn();
			returnButton.setBtnName("返回");
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
			delButton.setBtnName("删除");
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
			throw new ServiceException("保存pagePanel发生异常");
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
