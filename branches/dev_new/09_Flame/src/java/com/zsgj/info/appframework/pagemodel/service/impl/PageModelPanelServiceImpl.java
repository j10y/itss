package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelBtn;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.PageModelPanelService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;

public class PageModelPanelServiceImpl extends BaseDao implements PageModelPanelService {

	public List<PageModelPanel> getChildPanels(PageModelPanel pmp){
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("pageModel", pmp.getPageModel()));
		c.add(Restrictions.eq("parentPagePanel", pmp.getPagePanel()));
		c.addOrder(Order.asc("order"));
		List<PageModelPanel> list = c.list();
		return list;
	}
	
	
	public List<PageModelPanel> findPagePanelByNoParent(PageModel pageModel) {
		List<PageModelPanel> list = null;
		try {
			Criteria c = super.getCriteria(PageModelPanel.class);
			c.add(Restrictions.eq("pageModel", pageModel));
			c.add(Restrictions.isNull("parentPagePanel")); 
			c.addOrder(Order.asc("order"));
			list = c.list();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("通过pageModel获取PagePanel发生异常");
		}		
		return list;
	}
	
	public void downNode(Long modelId, Long parentPanelId, Integer minIndex, Integer maxIndex) {
//		如果上级Panel不等于null，即至少为2级panel
		if(parentPanelId!=null&& parentPanelId.intValue()!=-100){
			//begin 获取上级Panel
			PagePanel parent = super.get(PagePanel.class, parentPanelId);
			PageModel model = super.get(PageModel.class, modelId);
			//end
			//指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer(
					"update PageModelPanel m set m.order=m.order-1 where m.pageModel=? and m.parentPagePanel=?");
			List paramsList = new ArrayList();
			paramsList.add(model);
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
			PageModel model = super.get(PageModel.class, modelId);
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer(
					"update PageModelPanel m set m.order=m.order-1 where m.pageModel=? and m.parentPagePanel is null");
			List paramsList = new ArrayList();
			paramsList.add(model);
			
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

	private PageModelPanel getPageModelPanel(Long modelId, Long panelId){
		PageModelPanel result = null;
		String hql = "select pmp from PageModelPanel pmp where pmp.pageModel.id=? and pmp.pagePanel.id=?";
		Query query = super.createQuery(hql, new Object[]{modelId, panelId});
		Object object = query.uniqueResult();
		if(object==null) return null;
		result = (PageModelPanel) object;
		return result;
	}
	
	public PageModelPanel savePageModelPanel(PageModelPanel panel) {
		PageModelPanel result = null;
		try {
			result = (PageModelPanel) super.save(panel);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存PageModel时发生异常");
		}		
		return result;
	}

	public void savePageModelPanelMove(String pmid, String ppid, String oldPid,
			String newPid, String nodeIndx) {
		if(oldPid!=null && !oldPid.equals("-100") && !newPid.equals("-100")){
			Long modelId = Long.valueOf(pmid);//pageModel id
			Long panelId = this.findPageModelPanelById(ppid).getPagePanel().getId();//Long.valueOf(ppid); pagePanel id
	 		Long oldParentId = this.findPageModelPanelById(oldPid).getPagePanel().getId(); //pagePanel old parent id
			Long newParentId = this.findPageModelPanelById(newPid).getPagePanel().getId();//Long.valueOf(newPid); pagePanel new parent id
			int nodeIndex = Integer.parseInt(nodeIndx);
			PageModelPanel panel = this.getPageModelPanel(modelId, panelId);
			PageModelPanel newParent = this.getPageModelPanel(modelId, newParentId); 
			//super.get(PagePanel.class, newParentId);
			int minIndex = panel.getOrder().intValue();
			int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
			if(oldParentId.intValue() == newParentId.intValue() && minIndex != maxIndex){
				// 在同一个父节点下发生移动
				if(minIndex < maxIndex){
					// 当要移动的节点的序号小于要移动到的目标序号，则下移
					this.downNode(modelId, oldParentId, minIndex, maxIndex);
				}else if(minIndex > maxIndex){
					// 当要移动的节点的序号大于要移动到的目标序号，则上移
					maxIndex = minIndex;
					minIndex = nodeIndex;
					this.upNode(modelId, oldParentId, minIndex, maxIndex);
				}
				// 节点本身的序号设置成要移动到的目标序号
				panel.setOrder(nodeIndex);
				this.savePageModelPanel(panel);
			}
			if(oldParentId.intValue() != newParentId.intValue()){
				// 在不同父节点下发生移动
				//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
				this.downNode(modelId, oldParentId, minIndex, -1);
				//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
				this.upNode(modelId, newParentId, maxIndex, -1);
				// 节点本身的序号设置成要移动到的目标序号
				panel.setOrder(nodeIndex);
				panel.setParentPagePanel(newParent.getPagePanel());
				this.savePageModelPanel(panel);
			}
		}else if(oldPid.equals("-100")&&!newPid.equals("-100")){
			PageModelPanel panel = this.findPageModelPanelById(ppid);
			Long newParentId = this.findPageModelPanelById(newPid).getPagePanel().getId();
			Long modelId = Long.valueOf(pmid);
			int nodeIndex = Integer.parseInt(nodeIndx);
			int minIndex = panel.getOrder();		
			int maxIndex = Integer.valueOf(nodeIndex);
			Long oldParentId = Long.parseLong(oldPid);
			PageModelPanel newParent = this.getPageModelPanel(modelId, newParentId); 
				if(minIndex < maxIndex){
					// 当要移动的节点的序号小于要移动到的目标序号，则下移
					this.downNode(modelId, oldParentId, minIndex, maxIndex);
				}else if(minIndex > maxIndex){
					// 当要移动的节点的序号大于要移动到的目标序号，则上移
					maxIndex = minIndex;
					minIndex = nodeIndex;
					this.upNode(modelId, oldParentId, minIndex, maxIndex);
				}
				panel.setOrder(nodeIndex);	
				panel.setParentPagePanel(newParent.getPagePanel());
				this.savePageModelPanel(panel);
				
		}else if(!oldPid.equals("-100") && newPid.equals("-100")){
			Long modelId = Long.valueOf(pmid);
			Long panelId = this.findPageModelPanelById(ppid).getPagePanel().getId();
			Long oldParentId = this.findPageModelPanelById(oldPid).getPagePanel().getId();
			int nodeIndex = Integer.parseInt(nodeIndx);
			PageModelPanel panel = this.getPageModelPanel(modelId, panelId);
			/*得到拖动节点,当拖动到根结点的时候,其值为第一层的个数；而其他为减一
			 * */
			Integer minIndex = Integer.parseInt(nodeIndx);
			formLeafToRoot(oldParentId ,modelId , nodeIndex,minIndex);
			/*找到根结点下的所有的数据
			 * */
			int size =findPageModelPanelByPageModelAndParentId(modelId+"");
			panel.setOrder(size);
			panel.setParentPagePanel(null);
			this.savePageModelPanel(panel);
		}else if(oldPid.equals("-100") && oldPid.equals("-100")){
			PageModelPanel panel = this.findPageModelPanelById(ppid);
			Long newParentId = -100l;
			Long modelId = Long.valueOf(pmid);
			int nodeIndex = Integer.parseInt(nodeIndx);
			int minIndex = panel.getOrder();		
			int maxIndex = Integer.valueOf(nodeIndex);
			Long oldParentId = Long.parseLong(oldPid);
				if(minIndex < maxIndex){
					// 当要移动的节点的序号小于要移动到的目标序号，则下移
					this.downNode(modelId, oldParentId, minIndex, maxIndex);
				}else if(minIndex > maxIndex){
					// 当要移动的节点的序号大于要移动到的目标序号，则上移
					maxIndex = minIndex;
					minIndex = nodeIndex;
					this.upNode(modelId, oldParentId, minIndex, maxIndex);
				}
				panel.setOrder(nodeIndex);	
				panel.setParentPagePanel(null);
				this.savePageModelPanel(panel);
		}
		

	}

	public void upNode(Long modelId, Long parentId, Integer minIndex, Integer maxIndex) {
		if(parentId!=null&& parentId.intValue()!=-100){
			PagePanel parent = super.get(PagePanel.class, parentId);
			PageModel model = super.get(PageModel.class, modelId);
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer(
					"update PageModelPanel m set m.order=m.order+1 where m.pageModel=? and m.parentPagePanel = ?");
			List paramsList = new ArrayList();
			paramsList.add(model);
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
			PageModel model = super.get(PageModel.class, modelId);
			StringBuffer hql = new StringBuffer(
					"update PageModelPanel m set m.order=m.order+1 where m.pageModel=? and m.parentPagePanel is null ");
			List paramsList = new ArrayList();
			paramsList.add(model);
			
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
	
	/*根据id来查找相应的的实体信息
	 * */
	public PagePanel findTemplateItemById(String id) {
		Criteria criteria = super.getCriteria(PagePanel.class);
		criteria.add(Restrictions.eq("id", Long.valueOf(id)));
		criteria.setFetchMode("id", FetchMode.JOIN);
		return (PagePanel) criteria.uniqueResult();
	}

	public PageModel findPageModelById(String id) {
		Criteria criteria = super.getCriteria(PageModel.class);
		criteria.add(Restrictions.eq("id", Long.valueOf(id)));
		criteria.setFetchMode("mainPagePanel", FetchMode.JOIN);
		return (PageModel) criteria.uniqueResult();
	}

	public PageModelPanel findPageModelPanelById(String id) {
		PageModelPanel pageModelPanel = null;
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		c.setFetchMode("parentPagePanel", FetchMode.JOIN);
		pageModelPanel = (PageModelPanel) c.uniqueResult();
		return pageModelPanel;
	}
	
	public PagePanel findPagePanelById(String id) {
		PagePanel pagePanel = null;
		pagePanel = this.findUniqueBy(PagePanel.class, "id", Long.valueOf(id));//this.get(PagePanel.class, Long.valueOf(id));
		return pagePanel;
	}


	public List<PageModelPanel> findPageModelPanelByDoubleId(String pageModelPanelId) {//pagePanelId
			
		List<PageModelPanel> list = null;
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("id", Long.valueOf(pageModelPanelId)));
		c.setFetchMode("parentPagePanel", FetchMode.JOIN);
		PageModelPanel pageModelPanel = (PageModelPanel) c.uniqueResult();
		list = getChildPanels(pageModelPanel);
		return list;
	}

	//删除叶子节点
	public void removeLeafPageModelPanel(PageModelPanel pageModelPanel , int nodeIndex) {
		String pageModelId = pageModelPanel.getPageModel().getId()+"";
		String ordParentId ="-100";
		if(pageModelPanel.getParentPagePanel()!=null){
			ordParentId = pageModelPanel.getParentPagePanel().getId()+"";
		}		
		int selfOrder = pageModelPanel.getOrder();
		if(!ordParentId.equals("-100") && ordParentId!=null){
			this.changeOrderByDelete(ordParentId, selfOrder, nodeIndex, pageModelId);
			super.removeById(PageModelPanel.class, pageModelPanel.getId());
			super.flush();
		}else{
			ordParentId = "-100";
			this.changeOrderByDelete(ordParentId, selfOrder, nodeIndex, pageModelId);
			super.removeById(PageModelPanel.class, pageModelPanel.getId());
			super.flush();
		}
		
	}
	
	//删除非叶子节点
	public void removePageModelPanel(PageModelPanel pageModelPanel) {
		List<PageModelPanel> panel = getChildPanels(pageModelPanel);
		if(panel.size()==0){
			//removeLeafPageModelPanel(pageModelPanel);
		}else{
			for(int i=0;i<panel.size();i++){
				PageModelPanel childPanel = panel.get(i);
				removePageModelPanel(childPanel);
			}			
		}	
	}


	public String findPageModelByName(String name) {
		Criteria c = super.getCriteria(PageModel.class);
		c.add(Restrictions.eq("title", name));
		PageModel pageModel = (PageModel)c.setMaxResults(1).uniqueResult();
		String id = pageModel.getId()+"";
		return id;
	}


	public PagePanelType findPagePanelTypeById(String id) {
		PagePanelType pagePanelType = null;
		pagePanelType = this.get(PagePanelType.class, Long.valueOf(id));
		return pagePanelType;
	}

	//此方法只去了顶层panel
	public List<PageModelPanel> findPageModelPanelByPageModel(PageModel pageModel) {
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		List<PageModelPanel> pageModelPanel = c.list();
		for(PageModelPanel pmp : pageModelPanel){
			List<PageModelPanel> childPagePanels = getChildPanels(pmp);
			pmp.setChildPagePanels(childPagePanels);
		}
		return pageModelPanel;
	}
	
	public int findPageModelPanelByPageModelAndParentId(String modelId){
		int num = 0;
		PageModel pageModel = findPageModelById(modelId);
		Criteria c = super.getCriteria(PageModelPanel.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		c.add(Restrictions.isNull("parentPagePanel"));
		List<PageModelPanel> pageModelPanel = c.list();
		num = pageModelPanel.size();
		return num;
		
	}
	
	public void formLeafToRoot(Long oldParentId , Long pageModelId ,int nodeIndex ,int minIndex){
		PagePanel parent = super.get(PagePanel.class, oldParentId);
		PageModel model = super.get(PageModel.class, pageModelId);
		//指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer(
				"update PageModelPanel m set m.order=m.order-1 where m.pageModel=? and m.parentPagePanel=?");
		List paramsList = new ArrayList();
		paramsList.add(model);
		paramsList.add(parent);
		
		hql.append(" and m.order <= ? ");
		paramsList.add(nodeIndex);
		
		if(minIndex != -1){
			hql.append(" and m.order > ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params);
	}


	public int searchChildAmountByParentIdAndPageModelId(String parentId,
			String PageModelId) {
		int sum = 0;
		if(!parentId.equals("-100")&&parentId!=null){
			PagePanel pagePanel = this.findPagePanelById(parentId);
			PageModel pageModel = this.findPageModelById(PageModelId);
			Criteria c = super.getCriteria(PageModelPanel.class);
			c.add(Restrictions.eq("parentPagePanel", pagePanel));
			c.add(Restrictions.eq("pageModel", pageModel));
			List list = c.list();
			sum = list.size();
			
		}else{
			PageModel pageModel = this.findPageModelById(PageModelId);
			Criteria c = super.getCriteria(PageModelPanel.class);
			c.add(Restrictions.eq("pageModel", pageModel));
			c.add(Restrictions.isNull("parentPagePanel"));
			List list = c.list();
			sum = list.size();
		}
		return sum;
		
	}
	
	/*删除节点的时候来改变相应的顺序位
	 * */
	public void changeOrderByDelete(String ordParentId , int minIndex ,int nodeIndex ,String pageModelId){
		if(!ordParentId.equals("-100")&&ordParentId!=null){
			PagePanel parent = super.get(PagePanel.class, Long.valueOf(ordParentId));
			PageModel model = super.get(PageModel.class, Long.valueOf(pageModelId));
			StringBuffer hql = new StringBuffer(
					"update PageModelPanel m set m.order=m.order-1 where m.pageModel=? and m.parentPagePanel=?");
			List paramsList = new ArrayList();
			paramsList.add(model);
			paramsList.add(parent);
			
			if(nodeIndex != -1){
				hql.append(" and m.order <= ? ");
				paramsList.add(nodeIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.order > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}else{
			PageModel model = super.get(PageModel.class, Long.valueOf(pageModelId));
			StringBuffer hql = new StringBuffer(
					"update PageModelPanel m set m.order=m.order-1 where m.pageModel=? and m.parentPagePanel is null");
			List paramsList = new ArrayList();
			paramsList.add(model);			
			
			if(nodeIndex != -1){
				hql.append(" and m.order <= ? ");
				paramsList.add(nodeIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.order > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}
		
	}
	/*当删根结点的时候，我会全部一个一个删除不用考虑任何顺序位
	 * */
	public void removeRootNode(PageModelPanel pageModelPanel){
		super.removeById(PageModelPanel.class, pageModelPanel.getId());
		super.flush();
	}


	public List<PageModelBtn> findPageModelBtnByPageModel(PageModel pageModel) {
		Criteria c = super.getCriteria(PageModelBtn.class);
		c.add(Restrictions.eq("pageModel", pageModel));
		List<PageModelBtn> list = c.list();
		return list;
	}

	
	public String findPageModelByRealName(String pageModelName) {
		Criteria c = super.getCriteria(PageModel.class);
		c.add(Restrictions.eq("name", pageModelName));
		PageModel pageModel = (PageModel)c.setMaxResults(1).uniqueResult();
		String id = pageModel.getId()+"";
		return id;
	}
	/**
	 * 用来判断是否已经存在主面板
	 * @Methods Name findPageModelPanelByPageModelAndPagePanel
	 * @Create In Dec 17, 2008 By sai
	 * @return boolean
	 */
	public boolean findPageModelPanelByPageModelAndPagePanel(PageModel pageModel ,PagePanel pagePanel){
		
		Criteria criteria = super.getCriteria(PageModelPanel.class);
		criteria.add(Restrictions.eq("pageModel", pageModel));
		criteria.add(Restrictions.eq("pagePanel",pagePanel));
		PageModelPanel pageModelPanel = (PageModelPanel)criteria.setMaxResults(1).uniqueResult();
		if(pageModelPanel==null){
			return false;
		}
		return true;
	}


}
